/**
 * 
 */
package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.DulChecklist;
import gov.nih.nci.cbiit.scimgmt.gds.domain.DulChecklistSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.InstitutionalCertification;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PageStatus;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectsIcMapping;
import gov.nih.nci.cbiit.scimgmt.gds.domain.StudiesDulSet;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Study;
import gov.nih.nci.cbiit.scimgmt.gds.model.MissingData;
import gov.nih.nci.cbiit.scimgmt.gds.model.ParentDulChecklist;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionStatusHelper;

import org.springframework.util.CollectionUtils;

/**
 * @author menons2
 *
 */
public class IcListSubmissionAction extends ManageSubmission {

	static Logger logger = LogManager.getLogger(IcSubmissionAction.class);
	
	
	private InstitutionalCertification instCertification;
	
	private String instCertId;
	
	private String dulIds;
	
	private String icIds = "";
	
	private File ic;
	
	private String icFileName;

	private String icContentType;
	
	private List<Document> icFileDocs = new ArrayList<Document>();
	
	private Document doc = null; // json object to be returned for UI refresh after upload
	

	
	/**
	 * Invoked for the Track IC Status page. Invoked from
	 * 1. ICs tab (if at least one IC is present in the submission (else, user will
	 * be directed to the Add IC page)
	 * 2. The Save and Next button on the Genomic Data Sharing Plan if at least one
	 *   IC is present in the Submission (else user will be directed to the Add IC page).
	 * 3. Save Study button on the Add IC Certification page.
	 * @return
	 */
	public String getIcList() {
		
		String forward = SUCCESS;
		
		
		//Retrieve IC list. If sub-project, retrieve parent IC list
		Project storedProject = retrieveSelectedProject();
		List<InstitutionalCertification> icList = storedProject.getInstitutionalCertifications();
		Long displayProjectId = storedProject.getId();
		if(ApplicationConstants.FLAG_YES.equalsIgnoreCase(storedProject.getSubprojectFlag())) {
			prepareIcListDisplay(icList);
			icList = retrieveParentProject().getInstitutionalCertifications();
			displayProjectId = storedProject.getParentProjectId();
		}
		
		storedProject.setInstitutionalCertifications(icList);
				
		List<Document> docs = 
			fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_IC, displayProjectId);
			
		if(CollectionUtils.isEmpty(icList) && !ApplicationConstants.FLAG_YES.equalsIgnoreCase(storedProject.getSubprojectFlag())) {
			forward =  ApplicationConstants.EMPTY;
		} 
		
		if(docs != null && !docs.isEmpty()) {
			for(InstitutionalCertification ic: icList) {
				for(Document doc: docs) {
					if(doc.getInstitutionalCertificationId() != null && 
							doc.getInstitutionalCertificationId().equals(ic.getId()))
						ic.addDocument(doc);								
				}	
			}
		}
		
		setProject(storedProject);
		setProjectId(storedProject.getId().toString());
		return forward;
	}
	
	
	private void prepareIcListDisplay(List<InstitutionalCertification> icList) {
		ArrayList<String> icIdList = new ArrayList<String>();
		
		//Determine which checkboxes need to be saved. These will be the ICs associated
		//with the stored sub-project (param)
		if(!CollectionUtils.isEmpty(icList)) {
			for(InstitutionalCertification ic: icList) {
				icIdList.add(ic.getId().toString());
			}
		}
		
		icIds = (new JSONArray(icIdList)).toString();
	}
	
	
	public String save() {
		
		saveIcList();
		addActionMessage(getText("project.save.success"));
		return getIcList();
	}
	
	
	public String saveAndNext() {
		
		saveIcList();
		return SUCCESS;
	}
	
	/**
	 * Saves the certificationComplete flag. Invoked from:
	 * 'Save' or 'Save and Next' button on the Track Institutional Certification
	 *  Status page.
	 * 
	 * @return
	 */
	private void saveIcList() {
		
		String certComplete = getProject().getCertificationCompleteFlag();
		
		Project storedProject = retrieveSelectedProject();
		
		storedProject.setCertificationCompleteFlag(certComplete);
		
		//If this is a sub-project, save only the ICs selected from the parent
		/*if(ApplicationConstants.FLAG_YES.equalsIgnoreCase(storedProject.getSubprojectFlag())) {
			List<ProjectsIcMapping> projectsIcMappings = getSubProjectIcs(storedProject);			
			storedProject.setProjectsIcMappings(projectsIcMappings);
		}*/
		
		setProject(saveProject(storedProject, ApplicationConstants.PAGE_CODE_IC));
		
	}
	
	
	private List<ProjectsIcMapping> getSubProjectIcs(Project storedProject) {
		
		List<ProjectsIcMapping> storedIcMappings = new ArrayList(); // storedProject.getProjectsIcMappings();
		
		//Get the IC mappings in the stored project
		HashMap<Long, ProjectsIcMapping> icMap = new HashMap();
		if(!CollectionUtils.isEmpty(storedIcMappings)) {
			for(ProjectsIcMapping projectsIcMapping: storedIcMappings) {
				icMap.put(projectsIcMapping.getInstitutionalCertification().getId(), projectsIcMapping);
			}
		}
			
		//Get the ic mappings of the project from the display - this is the same as the
		//parent project ic mappings
		List<InstitutionalCertification> parentIcList = retrieveParentProject().getInstitutionalCertifications();
		String [] icElem = ServletActionContext.getRequest().getParameterValues("ic-selected");
		if(icElem == null) {
			storedIcMappings.clear();
		} else {
		    //Add/remove based on the ones selected in the display
			List<String> selectedIcs = Arrays.asList(icElem);
			if(!CollectionUtils.isEmpty(parentIcList)) {
				for(InstitutionalCertification ic: parentIcList) {
					Long certificationId = ic.getId();
					if(selectedIcs.contains(certificationId.toString())) {			
					//This has been selected, add to subproject if not already present
						if(!icMap.containsKey(certificationId)) {
							storedIcMappings.add(new ProjectsIcMapping(
								storedProject, loggedOnUser.getAdUserId().toUpperCase(), null, ic));
						}
					} else {
						storedIcMappings.remove(certificationId);
					}
				}
			}
		}
		return storedIcMappings;
	
	}
	
	/**
	 * Deletes an IC. Invoked when the delete button is clicked on the Track
	 * Submission Status (IC List) page
	 * 
	 * @return
	 */
	public String deleteIc() {
		
		Long instCertId = Long.valueOf(getInstCertId());
		
		Project project = retrieveSelectedProject();
		
		// Delete the files for this certId
		icFileDocs = fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_IC, project.getId());
		for(Document doc: icFileDocs) {
			if(doc.getInstitutionalCertificationId() == null || doc.getInstitutionalCertificationId().longValue() == instCertId.longValue()) {
				fileUploadService.deleteFile(doc.getId());
			}		
		}
		
		manageProjectService.deleteIc(instCertId);
		
		setProject(retrieveSelectedProject());	
		getProject().setCertificationCompleteFlag(null);

		return SUCCESS;
	}
	
	
	/**
	 * @return the instCertification
	 */
	public InstitutionalCertification getInstCertification() {
		return instCertification;
	}

	/**
	 * @param instCertification the instCertification to set
	 */
	public void setInstCertification(InstitutionalCertification instCertification) {
		this.instCertification = instCertification;
	}

	/**
	 * @return the instCertId
	 */
	public String getInstCertId() {
		return instCertId;
	}

	/**
	 * @param instCertId the instCertId to set
	 */
	public void setInstCertId(String instCertId) {
		this.instCertId = instCertId;
	}



	/**
	 * @return the dulIds
	 */
	public String getDulIds() {
		return dulIds;
	}


	/**
	 * @param dulIds the dulIds to set
	 */
	public void setDulIds(String dulIds) {
		this.dulIds = dulIds;
	}


	
	
	/**
	 * @return the icIds
	 */
	public String getIcIds() {
		return icIds;
	}


	/**
	 * @param icIds the icIds to set
	 */
	public void setIcIds(String icIds) {
		this.icIds = icIds;
	}


	/**
	 * @return the icFile
	 */
	public File getIc() {
		return ic;
	}


	/**
	 * @param icFile the icFile to set
	 */
	public void setIc(File ic) {
		this.ic = ic;
	}


	/**
	 * @return the icFileName
	 */
	public String getIcFileName() {
		return icFileName;
	}


	/**
	 * @param icFileName the icFileName to set
	 */
	public void setIcFileName(String icFileName) {
		this.icFileName = icFileName;
	}


	/**
	 * @return the icFileContentType
	 */
	public String getIcContentType() {
		return icContentType;
	}


	/**
	 * @param icFileContentType the icFileContentType to set
	 */
	public void setIcContentType(String icContentType) {
		this.icContentType = icContentType;
	}


	/**
	 * @return the icFileDocs
	 */
	public List<Document> getIcFileDocs() {
		return icFileDocs;
	}


	/**
	 * @param icFileDocs the icFileDocs to set
	 */
	public void setIcFileDocs(List<Document> icFileDocs) {
		this.icFileDocs = icFileDocs;
	}


	public Document getDoc() {
		return doc;
	}


	public void setDoc(Document doc) {
		this.doc = doc;
	}
	
	public String getPageStatusCode() {
		return super.getPageStatusCode(ApplicationConstants.PAGE_CODE_IC);
	}
	

	
	protected String computePageStatus(Project project) {
		
		String status = ApplicationConstants.PAGE_STATUS_CODE_COMPLETED;
		List<InstitutionalCertification> icList = project.getInstitutionalCertifications();
		
		if(CollectionUtils.isEmpty(icList)) {
			return ApplicationConstants.PAGE_STATUS_CODE_NOT_STARTED;
		}
			
		if(!ApplicationConstants.FLAG_YES.equalsIgnoreCase(project.getCertificationCompleteFlag())) { 
			return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
		}
		
		//There is at least one IC and IC certification flag says done. So proceed to
		//check if the ICs are all ok.
		for(InstitutionalCertification ic: icList) {
			ic = manageProjectService.findIcById(ic.getId());
			
			if(!ApplicationConstants.IC_GPA_APPROVED_YES_ID.equals(ic.getGpaApprovalCode())) {
				return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
			}
			List<Study> studies = ic.getStudies();
			for(Study study: studies) {
				if(!ApplicationConstants.IC_DUL_VERIFIED_YES_ID.equals(study.getDulVerificationId())) {
					return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
				}
			}
		}
		
		return status;
	}

	public String getMissingIcListData() {
		
		setPage(lookupService.getLookupByCode(ApplicationConstants.PAGE_TYPE, ApplicationConstants.PAGE_CODE_IC));
		
		missingDataList = new ArrayList<MissingData>();
		Project project = retrieveSelectedProject();
			
		List<InstitutionalCertification> icList = project.getInstitutionalCertifications();
			
		if(!ApplicationConstants.FLAG_YES.equals(project.getCertificationCompleteFlag()) ||
					CollectionUtils.isEmpty(icList)) {
			String displayText = "Add all the required Institutional Certifications";
			MissingData missingData = new MissingData(displayText);
			missingDataList.add(missingData);
		}
			
		//Get the file list
		HashMap<Long, Document> docMap = new HashMap<Long, Document>();
		List<Document> docs = 
			fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_IC, project.getId());
		if(docs != null && !docs.isEmpty()) {
			for(Document doc: docs) {
				if(doc.getInstitutionalCertificationId() != null) {
					docMap.put(doc.getInstitutionalCertificationId(), doc);
				}			
			}
		}
			
		//There is at least one IC. So proceed to check if the ICs are all ok.
		MissingData missingData = new MissingData("The following ICs have incomplete data:");
			
		for(InstitutionalCertification ic: icList) {
			Document document = docMap.get(ic.getId());
			MissingData missingIcData = GdsSubmissionStatusHelper.getInstance().computeMissingIcData(ic, document);
									
			if(missingIcData.getChildList().size() > 0) {
				missingIcData.setDisplayText(document.getFileName());
				missingData.addChild(missingIcData);
			}
		}
		if(missingData.getChildList().size() > 0) {
			missingDataList.add(missingData);
		}
			
		return SUCCESS;
	}
	
}

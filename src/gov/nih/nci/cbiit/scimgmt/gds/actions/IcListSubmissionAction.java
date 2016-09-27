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
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsMissingDataUtil;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper;


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
	
	private String certFlag;
	
	private boolean ifIcSelected;
	
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
	
	public void validateSave(){	

		validateIcListSubmission();
	}
	
	public String save() {
		
		saveIcList();
		addActionMessage(getText("project.save.success"));
		return getIcList();
	}
	
	public void validateSaveAndNext(){	

		validateIcListSubmission();
	}	
	
	public String saveAndNext() {
		
		saveIcList();
		return SUCCESS;
	}
	
	public void validateIcListSubmission(){
		if(ApplicationConstants.FLAG_YES.equalsIgnoreCase(retrieveSelectedProject().getSubprojectFlag())) {
			if(("Y").equalsIgnoreCase(certFlag) && (!ifIcSelected)){
				this.addActionError(getText("error.ic.selection")); 
			}
				
		} if(hasActionErrors()){
			if(StringUtils.isNotBlank(getProjectId())){
				getProject().setId(Long.valueOf(getProjectId()));
			}
			getIcList();
			icIds="";
			
		}
	}
	
	public String getIcStatusCodeIndividual(Long icId) {
		List<InstitutionalCertification> certs = retrieveSelectedProject().getInstitutionalCertifications();
		for(InstitutionalCertification ic: certs) {
			if(ic.getId().equals(icId)) {
				return ic.getStatus();
			}
		}		
		return null;
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
		if(ApplicationConstants.FLAG_YES.equalsIgnoreCase(storedProject.getSubprojectFlag())) {
			storedProject.setInstitutionalCertifications(getSubProjectIcs(storedProject));
		}		
		setProject(saveProject(storedProject, ApplicationConstants.PAGE_CODE_IC));
	}
	
	
	private List<InstitutionalCertification> getSubProjectIcs(Project storedProject) {
		
		List<InstitutionalCertification> certs = storedProject.getInstitutionalCertifications();
		//Get the IC mappings in the stored project
				HashMap<Long, InstitutionalCertification> icMap = new HashMap();
				if(!CollectionUtils.isEmpty(certs)) {
					for(InstitutionalCertification ic: certs) {
						icMap.put(ic.getId(), ic);
					}
				}
		
		
		//Get the ics of the project from the display - this is the same as the
		//parent project ics
		Project parentProject =  manageProjectService.findById(Long.valueOf(storedProject.getParentProjectId()));
		
		List<InstitutionalCertification> parentIcList = parentProject.getInstitutionalCertifications();
		if(!CollectionUtils.isEmpty(parentIcList)) {
			//Get the user selected ICs
			String [] icElem = ServletActionContext.getRequest().getParameterValues("ic-selected");
			List<String> selectedIcs  = new ArrayList<String>();
			if(icElem != null) {
				//Add the ones selected
				selectedIcs = Arrays.asList(icElem);
			}
			for(InstitutionalCertification parentIc: parentIcList) {
				Long certificationId = parentIc.getId();
				if(selectedIcs.contains(certificationId.toString())) {
					if(!icMap.containsKey(certificationId)) {
						//Not present in the storedProject, hence add it
						//parentIc.addProject(storedProject);
						icMap.put(certificationId, parentIc);
					}
				} else {
					//Not selected, if present then remove
					if(icMap.containsKey(certificationId)) {
						//parentIc.removeProject(storedProject);
						icMap.remove(certificationId);
					}
				} 		
			} 
		}
		return new ArrayList<InstitutionalCertification>(icMap.values());
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
		manageProjectService.deleteIc(instCertId, project);
		setProject(retrieveSelectedProject());	
		getProject().setCertificationCompleteFlag(null);
        List<InstitutionalCertification> icList = retrieveSelectedProject().getInstitutionalCertifications();
		if(CollectionUtils.isEmpty(icList)) {
			super.saveProject(getProject(), ApplicationConstants.PAGE_CODE_IC);
			setProject(retrieveSelectedProject());
		}
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
	 * @return the icFileContentType
	 */
	public String getIcContentType() {
		return icContentType;
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
	
	public boolean isIfIcSelected() {
		return ifIcSelected;
	}


	public void setIfIcSelected(boolean ifIcSelected) {
		this.ifIcSelected = ifIcSelected;
	}


	public String getCertFlag() {
		return certFlag;
	}


	public void setCertFlag(String certFlag) {
		this.certFlag = certFlag;
	}
	
	public String getPageStatusCode() {
		return super.getPageStatusCode(ApplicationConstants.PAGE_CODE_IC);
	}
	

	
	protected String computePageStatus(Project project) {
		
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
			if(ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS.equals(ic.getStatus())) {
				return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
			}
		}
			
		return ApplicationConstants.PAGE_STATUS_CODE_COMPLETED;
	}

	
	public String getMissingIcListData() {
		
		setPage(lookupService.getLookupByCode(ApplicationConstants.PAGE_TYPE, ApplicationConstants.PAGE_CODE_IC));
		
		Project project = retrieveSelectedProject();
		setMissingDataList(GdsMissingDataUtil.getInstance().getMissingIcListData(project));
			
		return SUCCESS;
	}
	
}

/**
 * 
 */
package gov.nih.nci.cbiit.scimgmt.gds.actions;

import com.opensymphony.xwork2.ActionSupport;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.StudiesDulSet;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Study;
import gov.nih.nci.cbiit.scimgmt.gds.model.ParentDulChecklist;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper;

import org.springframework.util.CollectionUtils;

/**
 * @author menons2
 *
 */
public class IcSubmissionAction extends ManageSubmission {

	static Logger logger = LogManager.getLogger(IcSubmissionAction.class);
	
	
	private InstitutionalCertification instCertification;
	
	private String instCertId;
	
	private String dulIds;
	
	private List<ParentDulChecklist> parentDulChecklists = new ArrayList<ParentDulChecklist>();
	
	private File ic;
	
	private String icFileName;

	private String icContentType;
	
	private List<Document> icFileDocs = new ArrayList<Document>();
	
	private Document doc = null; // json object to be returned for UI refresh after upload
	
	
	
	/**
	 * Retrieves all data associated with the specified IC and redirects the user to the
	 * Edit/Add IC page. If no IC is present, then a new one is created. Invoked from:
	 * 1. The Edit link on the Track Institutional Status (IC List) page.
	 * 2. The Add New Institutional Certificate link on the Track Institutional Status page
	 * 3. The Save and Next button on the GDS Plan page if no IC is present in the 
	 *    Submission. (else user will be directed to the Track IC Status (IC List) page.
	 * 4. The ICs tab, if no IC is present in the Submission. (if an IC is present, then 
	 *  user will be directed to the Track IC Status (IC List) page.
	 * 
	 * @return forward string Takes the user back to the submissionIc.jsp page.
	 */
	public String execute() throws Exception {
						
		logger.debug("execute");
		
		setProject(retrieveSelectedProject());
		
		InstitutionalCertification instCert = retrieveIC();
		if(instCert != null) {
			loadFiles(instCert);
		} else {
			instCert = new InstitutionalCertification();
			Study study = new Study();
			StudiesDulSet studiesDulSet = new StudiesDulSet();
			study.addStudiesDulSet(studiesDulSet);
			//setTestData(study);
			instCert.addStudy(study);
		}
        
		setInstCertification(instCert);
		prepareDisplay(instCert);
        return SUCCESS;
	}
	
	
	private void loadFiles(InstitutionalCertification instCert) {
		if(instCert != null) {
			icFileDocs = new ArrayList<Document>();
			List<Document> docs = 
				fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_IC, retrieveSelectedProject().getId());
			if(docs != null && !docs.isEmpty()) {
				for(Document doc: docs) {
					if((getDocId() != null && doc.getId().equals(getDocId())) || (doc.getInstitutionalCertificationId() != null && 
							doc.getInstitutionalCertificationId().equals(instCert.getId())))
						icFileDocs.add(doc);
				}			
			}
			if(icFileDocs.isEmpty() && doc != null) {
				icFileDocs.add(doc);
			}
			instCert.setDocuments(icFileDocs);		
		}
	}
	
	private void setTestData(Study study) {
		StudiesDulSet studiesDulSet = new StudiesDulSet();
		DulChecklistSelection dul = new DulChecklistSelection();
		dul.setDulChecklist(GdsSubmissionActionHelper.getDulChecklist(Long.valueOf("2")));
		studiesDulSet.addDulChecklistSelection(dul);
		dul = new DulChecklistSelection();
		dul.setDulChecklist(GdsSubmissionActionHelper.getDulChecklist(Long.valueOf("3")));
		studiesDulSet.addDulChecklistSelection(dul);
		studiesDulSet.setStudy(study);
		study.addStudiesDulSet(studiesDulSet);
		
		studiesDulSet = new StudiesDulSet();
		dul = new DulChecklistSelection();
		dul.setDulChecklist(GdsSubmissionActionHelper.getDulChecklist(Long.valueOf("7")));
		studiesDulSet.addDulChecklistSelection(dul);
		dul = new DulChecklistSelection();
		dul.setDulChecklist(GdsSubmissionActionHelper.getDulChecklist(Long.valueOf("8")));
		studiesDulSet.addDulChecklistSelection(dul);
		studiesDulSet.setStudy(study);
		study.addStudiesDulSet(studiesDulSet);
	}
	
	
	private void prepareDisplay(InstitutionalCertification instCert) {
		//Get display Text
		setParentDulChecklists(GdsSubmissionActionHelper.getDulChecklistsSets(retrieveSelectedProject()));
		
		//Get display data.
		//We need to show the duls stored for each study
		
		ArrayList<String> dulIdList = new ArrayList<String>();
		int studyIndex = -1;
			
		for(Study study: instCert.getStudies()) {
			studyIndex++;
			int dulSetIndex = -1;
			
			for(StudiesDulSet studiesDulSet: study.getStudiesDulSets() ) {
				dulSetIndex++;
				
				List<DulChecklistSelection> dulChecklistSelections = studiesDulSet.getDulChecklistSelections();
				if(!CollectionUtils.isEmpty(dulChecklistSelections)) {
					
					Long parentDulId = studiesDulSet.getParentDulChecklist().getId();
					dulIdList.add("parentDul" + studyIndex + "-" + dulSetIndex + "-" + parentDulId);
					for(DulChecklistSelection dulChecklistSelection: dulChecklistSelections) {
						
						Long dulId = dulChecklistSelection.getDulChecklist().getId();
						
						if(dulId.equals(parentDulId)) {
							//This represents a parent row, so there should be additional text present	
							String additionalText = dulChecklistSelection.getOtherText();
							if(additionalText != null && !additionalText.isEmpty()) {
								int textlength = additionalText.length();
								dulIdList.add(textlength + "otherAddText" + studyIndex + "-" + dulSetIndex + "-" + dulId + additionalText);	
							}
						} else {
							//This has a parent, so it represents a regular child dul selection
							dulIdList.add("dul" + studyIndex + "-" + dulSetIndex + "-" + dulId);
						}
					}
				}
			}
		}

		String dulIds = (new JSONArray(dulIdList)).toString();
		setDulIds(dulIds);
		loadFiles(instCert);
				
	}
	
	
	/**
	 * Retrieve the project based on the projectId indicated in the request
	 * 
	 * @return
	 */
	private InstitutionalCertification retrieveIC() {
	
		String instCertId  = getInstCertId();
		if(instCertId != null) {
			Project project = retrieveSelectedProject();
			List<InstitutionalCertification> certs = project.getInstitutionalCertifications();
			if(!CollectionUtils.isEmpty(certs)) {
				for(InstitutionalCertification cert: certs) {
					if(cert.getId().toString().equals(instCertId)) {
						return cert;
					}
				}
			}
		} 
		
		return null;
	}
	
	
	
	public void validateSaveIc() {
		
		
		if(!StringUtils.isEmpty(icFileName)) {
			this.addActionError(getText("error.doc.fileNotUploaded"));
			if(icFileDocs.isEmpty()) {
				//If no file has been uploaded, then we do not
				//show any more fields to enter
				return;
			}
		}
		
		
		if(icFileDocs.isEmpty()) {
			this.addActionError(getText("error.doc.required"));
			return;
		}
		
		
		InstitutionalCertification instCert = getInstCertification();
		logger.info("No. of Studies in IC = " + instCert.getStudies().size());
		
		//validate the DULs in each Study
		Iterator<Study> studies = instCert.getStudies().iterator();
				
		while(studies.hasNext()) {
					
			Study study = studies.next();
			if(study == null || StringUtils.isBlank(study.getDisplayId())) {
				//This means that the study was deleted by the user
				//hence correspondingly remove it from our data structure
				studies.remove();
				continue;
			}
			processStudyAttributes(study);
			study.setInstitutionalCertification(instCert);
			int studyIndex = Long.valueOf(study.getDisplayId()).intValue();
			boolean atLeastOneDULSelected = false;
			
			//Map used to keep track of duplicate DulSets in a study
			HashMap<String, Integer> validationMap = new HashMap<String, Integer>();
			
			List<StudiesDulSet> dulSets = study.getStudiesDulSets();
			logger.info("No. of dulSets in study: " + study.getStudyName() + " = " + dulSets.size());
			if(!CollectionUtils.isEmpty(dulSets)) {
				
				Iterator<StudiesDulSet> studiesDulSets = study.getStudiesDulSets().iterator();		
				while(studiesDulSets.hasNext()) {
					StudiesDulSet dulSet = studiesDulSets.next();
					if(dulSet == null || StringUtils.isBlank(dulSet.getDisplayId())) {
						//This means that the DulSet was deleted by the user
						//hence correspondingly remove it from our data structure
						studiesDulSets.remove();
						continue;
					}
					
					if(dulSet.getId() == null || dulSet.getId().toString().isEmpty()) {
						dulSet.setCreatedBy(loggedOnUser.getAdUserId().toUpperCase());
					} else {
						dulSet.setLastChangedBy(loggedOnUser.getAdUserId().toUpperCase());				
					}
					dulSet.setStudy(study);
					
					int dulSetIndex = Long.valueOf(dulSet.getDisplayId()).intValue();
					String [] parentDulId = ServletActionContext.getRequest().getParameterValues("parentDul-" + studyIndex + "-" + dulSetIndex);
					if(parentDulId != null) {
					
						List<DulChecklistSelection> dulChecklistSelections = processDulSet(parentDulId,  
								study.getStudyName(), studyIndex, dulSet, validationMap);
						dulSet.setDulChecklistSelections(dulChecklistSelections);
						if(!dulChecklistSelections.isEmpty()) {
							atLeastOneDULSelected = true;
						}
				 	} else {
				 		if(study.getStudiesDulSets().size() > 1) {
							studiesDulSets.remove();
						}
				 	}
				}//End while-loop for iterating through dulSets				
			} 
			if(!atLeastOneDULSelected && !ApplicationConstants.IC_GPA_APPROVAL_ID_NO.equals(instCert.getGpaApprovalCode())) {
				addActionError(getText("error.ic.study.dulTypes.required", new String[]{study.getStudyName()}));
			}
		} //End for-loop for iterating through studies
		
		if(hasActionErrors()) {
			setProject(retrieveSelectedProject());
			setInstCertification(instCert);
			prepareDisplay(instCert);
			setDocId(getDocId());
		}
	}
	
	
	private void processStudyAttributes(Study study) {
		
		if(study.getStudyName() == null || study.getStudyName().isEmpty()) {
			addActionError(getText("error.ic.study.studyName.required") );
		}
		
		if(study.getId() == null || study.getId().toString().isEmpty()) {
			study.setCreatedBy(loggedOnUser.getAdUserId().toUpperCase());
		} else {
			study.setLastChangedBy(loggedOnUser.getAdUserId().toUpperCase());
		}
	}
	
	
	private List<DulChecklistSelection> processDulSet(String[] parentDulId, 
			String studyName, int studyIndex, StudiesDulSet dulSet, 
			HashMap<String, Integer> validationMap) {
		
		List<DulChecklistSelection> dulChecklistSelections = new ArrayList<DulChecklistSelection>();
		int dulSetIndex = Long.valueOf(dulSet.getDisplayId()).intValue();
		
		//Process additional text if present
		if(ApplicationConstants.IC_PARENT_DUL_ID_DISEASE_SPECIFIC.equals(Long.valueOf(parentDulId[0])) 
				|| ApplicationConstants.IC_PARENT_DUL_ID_OTHER.equals(Long.valueOf(parentDulId[0]))) {
			
			DulChecklistSelection dulChecklistSelection = 
				processAdditionalText(studyName, studyIndex, dulSet, dulSetIndex, parentDulId[0], validationMap);	
			dulChecklistSelections.add(dulChecklistSelection);	
		} 
		
		//Process DUL selections if present
		List<DulChecklistSelection> dulSelectionList = 
			processDulSelections(studyName, studyIndex, dulSet, dulSetIndex, parentDulId[0], validationMap);	
			
		dulChecklistSelections.addAll(dulSelectionList);
		
		return dulChecklistSelections;
	}
	
	
	private DulChecklistSelection processAdditionalText(String studyName,  
			int studyIndex, StudiesDulSet dulSet, int dulSetIndex, 
			String parentDulId, HashMap<String, Integer> validationMap) {
		
		String[] additionalText = ServletActionContext.getRequest().getParameterValues("otherAddText-" + studyIndex + "-" + dulSetIndex + "-" + parentDulId);					
		
		DulChecklistSelection dulChecklistSelection = createDulChecklistSelection(parentDulId, additionalText[0]);
		dulChecklistSelection.setStudiesDulSet(dulSet);							
		
		if(additionalText == null || additionalText[0].isEmpty()) {
			if(ApplicationConstants.IC_PARENT_DUL_ID_DISEASE_SPECIFIC.equals(Long.valueOf(parentDulId))) {
				this.addActionError(getText("error.ic.study.dulType.diseaseText.required", new String[]{studyName}));
			} else {	
				this.addActionError(getText("error.ic.study.dulType.additionalText.required", new String[]{studyName}));
			}
		} else {
			validationMap.put(additionalText[0], new Integer(dulSetIndex));
		}
		
		return dulChecklistSelection;
	}
	
	
	private List<DulChecklistSelection> processDulSelections(String studyName, 
			int studyIndex, StudiesDulSet dulSet, int dulSetIndex, String parentDulId, 
			HashMap<String, Integer> validationMap) {
		
		String selectedDulsParam = "dul-" + studyIndex + "-" + dulSetIndex + "-" + parentDulId;
		String [] selectedDuls = ServletActionContext.getRequest().getParameterValues(selectedDulsParam);
		
		List<DulChecklistSelection> dulSelections = new ArrayList<DulChecklistSelection>();
		
		if(selectedDuls != null && selectedDuls.length > 0) {
		
			//We have at least one selection in this this DUL Set, process them
			String dulSelectionsStr = "";
			for(int i = 0; i < selectedDuls.length; i++) {										
			
				dulSelectionsStr = dulSelectionsStr + selectedDuls[i];
			
				//for each selectedDul, create a dulChecklistSelection
				DulChecklistSelection dulChecklistSelection = createDulChecklistSelection(selectedDuls[i], null);
				dulChecklistSelection.setStudiesDulSet(dulSet);
				dulSelections.add(dulChecklistSelection);
			}
		
			//Check if this dulSet is already present
			if(validationMap.containsKey(dulSelectionsStr)) {
				//If parent dul is 13 or 21, get the additional text - validationMap.get(0);
				DulChecklist parentDul = GdsSubmissionActionHelper.getDulChecklist(Long.valueOf(parentDulId));
				this.addActionError(getText("error.ic.study.dulSelection.duplicate", new String[]{studyName}));
			} else {
				validationMap.put(dulSelectionsStr, new Integer(dulSetIndex));
			}
		}
		return dulSelections;
 	}
	
	

	
	private DulChecklistSelection createDulChecklistSelection(String dulId, String otherText) {
		
		//for each selectedDul, create a dulChecklistSelection
		DulChecklistSelection dulChecklistSelection = new DulChecklistSelection();
		if(dulChecklistSelection.getId() == null || dulChecklistSelection.getId().toString().isEmpty()) {
			dulChecklistSelection.setCreatedBy(loggedOnUser.getAdUserId().toUpperCase());
		} else {
			dulChecklistSelection.setLastChangedBy(loggedOnUser.getAdUserId().toUpperCase());
		}
		
		//get the dulChecklist with the id and attach it to this dulCheckListSelection.
		DulChecklist dulChecklist = GdsSubmissionActionHelper.getDulChecklist(Long.parseLong(dulId));
		dulChecklistSelection.setDulChecklist(dulChecklist);
		if(otherText != null && !otherText.isEmpty()) {
			dulChecklistSelection.setOtherText(otherText);
		}
		return dulChecklistSelection;
	}
	
			
			
	/**
	 * Saves the IC. Invoked from:
	 * 1. 'Save Institutional Certification' button on the  Add/Edit Institutional Certification
	 * 	page.
	 *   (IC List) page.
	 * 2.  
	 * @return
	 */
	public String saveIc() {
		Project project = retrieveSelectedProject();
		boolean matchFound = false;
		Long docId = null;
		
		InstitutionalCertification instCert = getInstCertification();
		if(instCert.getId() != null) {
			//We need to update an existing IC in the project
			for(InstitutionalCertification cert: project.getInstitutionalCertifications()) {
				if(cert.getId().equals(instCert.getId())) {
					project.getInstitutionalCertifications().remove(cert);
					project.getInstitutionalCertifications().add(instCert);
					matchFound = true;
					break;
				}
			}
			if(!matchFound) {
				//TBD - Fatal error ??
			}
			instCert.setLastChangedBy(loggedOnUser.getAdUserId().toUpperCase());
		} else {
			//The institutional certification is not in the DB, hence add it
			instCert.setCreatedBy(loggedOnUser.getAdUserId().toUpperCase());
			project.getInstitutionalCertifications().add(instCert);
			//Check if there is an file which doesn't have certId
			icFileDocs = fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_IC, new Long(getProjectId()));
			for(Document doc: icFileDocs) {
				if(doc.getInstitutionalCertificationId() == null) {
					docId = doc.getId();
					break;
				}		
			}
		}
		instCert.setProject(project);
		saveProject(project);
		setProject(retrieveSelectedProject());
		
		// Update CertId
		if(docId != null) {
			// Get latest CertId
			class InstitutionalCertificationComparator implements Comparator<InstitutionalCertification> {

				public int compare(InstitutionalCertification e1, InstitutionalCertification e2) {
					return e2.getId()
							.compareTo(e1.getId());
				}

			}

			Collections.sort(getProject().getInstitutionalCertifications(), new InstitutionalCertificationComparator());
			fileUploadService.updateCertId(docId, getProject().getInstitutionalCertifications().get(0).getId());
		}
		
		
		return SUCCESS;
	}
	
	
	/**
	 * Upload IC Document
	 * 
	 * @return forward string
	 */
	public String uploadInstCertification() {
		logger.info("uploadInstCertification()");
		
		if (!validateUploadFile(ic, icContentType))
			return INPUT;
		
		try {
			doc = fileUploadService.storeFile(
				new Long(getProjectId()), ApplicationConstants.DOC_TYPE_IC, ic, icFileName, getInstCertification().getId());
			setDocId(doc.getId());
			icFileDocs = fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_IC, new Long(getProjectId()));
			
		} catch (Exception e) {
			try {
				inputStream = new ByteArrayInputStream(getText("error.doc.upload").getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				return INPUT;
			}
			return INPUT;
		}
				
		logger.info("===> IC docId: " + doc.getId());
		logger.info("===> IC fileName: " + doc.getFileName());
		logger.info("===> IC docTitle: " + doc.getDocTitle());
		logger.info("===> IC uploadDate: " + doc.getUploadedDate());

		return SUCCESS;
	}
	
	
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
		
		Project project = retrieveSelectedProject();
		
		List<InstitutionalCertification> icList = project.getInstitutionalCertifications();
		List<Document> docs = 
			fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_IC, project.getId());
			
		if(CollectionUtils.isEmpty(icList)) {
			forward =  "empty";
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
	
		setProject(project);
		setProjectId(project.getId().toString());
		return forward;
	}
	
	
	/**
	 * Saves the certificationComplete flag. Invoked from:
	 * 'Save' or 'Save and Next' button on the Track Institutional Certification
	 *  Status page.
	 * 
	 * @return
	 */
	public String saveIcList() {
		
		String certComplete = getProject().getCertificationCompleteFlag();
		
		Project project = retrieveSelectedProject();
		
		project.setCertificationCompleteFlag(certComplete);
		
		setProject(saveProject(project));
		
		addActionMessage(getText("project.save.success"));
		
		return getIcList();
		
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
		
		if(!CollectionUtils.isEmpty(project.getInstitutionalCertifications())) {
			for(InstitutionalCertification cert: project.getInstitutionalCertifications()) {
				if(cert.getId().equals(instCertId)) {
					project.getInstitutionalCertifications().remove(cert);
					break;
				}
			}
		} else {
			//fatal error - how can we delete an IC if the project has noen
		}
		

		
		setProject(saveProject(project));

		
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
	 * @return the parentDulChecklists
	 */
	public List<ParentDulChecklist> getParentDulChecklists() {
		return parentDulChecklists;
	}


	/**
	 * @param parentDulChecklists the parentDulChecklists to set
	 */
	public void setParentDulChecklists(List<ParentDulChecklist> parentDulChecklists) {
		this.parentDulChecklists = parentDulChecklists;
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

}

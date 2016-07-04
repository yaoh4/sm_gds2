/**
 * 
 */
package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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
	
	private List<Document> icFileDocs;
	
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
			icFileDocs = new ArrayList<Document>();
			
			//Long docTypeId = lookupService.getLookupByCode(ApplicationConstants.DOC_TYPE, "IC").getId();
			List<Document> docs = 
				fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_IC, instCert.getProject().getId());
			if(docs != null && !docs.isEmpty()) {
				for(Document doc: docs) {
					if(doc.getInstitutionalCertificationId() != null && 
							doc.getInstitutionalCertificationId().equals(instCert.getId()))
						icFileDocs.add(doc);
				}			
			}
			
			instCert.setDocuments(icFileDocs);		
		} else {
			instCert = new InstitutionalCertification();
			Study study = new Study();
			StudiesDulSet studiesDulSet = new StudiesDulSet();
			study.addStudiesDulSet(studiesDulSet);
			//setTestData(study);
			instCert.addStudy(study);
		}
        
		setInstCertification(instCert);
		prepareDisplay();
        return SUCCESS;
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
	
	
	private void prepareDisplay() {
		//Get display Text
		setParentDulChecklists(GdsSubmissionActionHelper.getDulChecklistsSets());
		
		//Get display data.
		//We need to show the duls stored for each study
		
		ArrayList<String> dulIdList = new ArrayList<String>();
		int studyIndex = -1;
		for(Study study: getInstCertification().getStudies()) {
			studyIndex++;
			int dulSetIndex = -1;
			for(StudiesDulSet studiesDulSet: study.getStudiesDulSets() ) {
				dulSetIndex++;
				Long parentDulId = null;
				List<DulChecklistSelection> dulChecklistSelections = studiesDulSet.getDulChecklistSelections();
				if(!CollectionUtils.isEmpty(dulChecklistSelections)) {
					for(DulChecklistSelection dulChecklistSelection: dulChecklistSelections) {
						dulIdList.add("dul" + studyIndex + "-" + dulSetIndex + "-" + dulChecklistSelection.getDulChecklist().getId());
						if(parentDulId == null) {
							parentDulId = dulChecklistSelection.getDulChecklist().getParentDulId();
						}
					}
					dulIdList.add("parentDul" + studyIndex + "-" + dulSetIndex + "-" + parentDulId);
				}
			}
		}
		
		String dulIds = (new JSONArray(dulIdList)).toString();
		setDulIds(dulIds);
				
	}
	
	
	/**
	 * Retrieve the project based on the projectId indicated in the request
	 * 
	 * @return
	 */
	private InstitutionalCertification retrieveIC() {
	
		String instCertId  = "9"; //getInstCertId();
		if(instCertId != null) {
			Project project = retrieveSelectedProject();
			Set<InstitutionalCertification> certs = project.getInstitutionalCertifications();
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
		
		InstitutionalCertification instCert = getInstCertification();
		List<Study> studies = instCert.getStudies();
		
		logger.info("No. of Studies in IC = " + studies.size());
		
		int studyIndex = -1;
		//validate the DULs in each Study
		for(Study study: instCert.getStudies()) {
			
			studyIndex++;
			int dulSetIndex = -1;
			if(study.getId() == null || study.getId().toString().isEmpty()) {
				study.setCreatedBy(loggedOnUser.getAdUserId().toUpperCase());
			} else {
				study.setLastChangedBy(loggedOnUser.getAdUserId().toUpperCase());
			}
			study.setInstitutionalCertification(instCertification);
			//Map used to keep track of duplicate DulSets in a study
			HashMap<String, Integer> validationMap = new HashMap<String, Integer>();
			List<StudiesDulSet> dulSets = study.getStudiesDulSets();
			logger.info("No. of dulSets in study at index at " + studyIndex + " = " + dulSets.size());
			if(CollectionUtils.isEmpty(dulSets)) {
				addActionError("No DUL selection made for study at index " + studyIndex);
			}
			else {
				for(StudiesDulSet dulSet: study.getStudiesDulSets()) {
				if(dulSet.getId() == null || dulSet.getId().toString().isEmpty()) {
					dulSet.setCreatedBy(loggedOnUser.getAdUserId().toUpperCase());
				} else {
					dulSet.setLastChangedBy(loggedOnUser.getAdUserId().toUpperCase());				
				}
				dulSet.setStudy(study);
				dulSetIndex++;
				
				String [] parentDulId = ServletActionContext.getRequest().getParameterValues("parentDul-" + studyIndex + "-" + dulSetIndex);
				if(parentDulId == null) {
					//Error, no DUL selection made for study at index x, dulSet at index y
					this.addActionError("No DUL selection made for study at index " + studyIndex + " and DulSet at index " + dulSetIndex);
				} else {
					String selectedDulsParam = "dul-" + studyIndex + "-" + dulSetIndex + "-" + parentDulId[0];
					String [] selectedDuls = ServletActionContext.getRequest().getParameterValues(selectedDulsParam);
					if(selectedDuls == null) {
						this.addActionError("No DUL selection made for study at index " + studyIndex + " and DulSet at index " + dulSetIndex);
					} else {
						
						//Represents the duls selected in a dulSet
						List<DulChecklistSelection> dulChecklistSelections = new ArrayList<DulChecklistSelection>();
						
						String dulSelections = "";
						logger.info("No. of Duls selected in dulSet at index " + dulSetIndex + "for study at index" + studyIndex);
						for(int i = 0; i < selectedDuls.length; i++) {										
							
							dulSelections = dulSelections + selectedDuls[i];
							
							//for each selectedDul, create a dulChecklistSelection
							DulChecklistSelection dulChecklistSelection = new DulChecklistSelection();
							if(dulChecklistSelection.getId() == null || dulChecklistSelection.getId().toString().isEmpty()) {
								dulChecklistSelection.setCreatedBy(loggedOnUser.getAdUserId().toUpperCase());
							} else {
								dulChecklistSelection.setLastChangedBy(loggedOnUser.getAdUserId().toUpperCase());
							}
							dulChecklistSelection.setStudiesDulSet(dulSet);
							//get the dulChecklist with the id and attach it to this dulCheckListSelection.
							DulChecklist dulChecklist = GdsSubmissionActionHelper.getDulChecklist(Long.parseLong(selectedDuls[i]));
							dulChecklistSelection.setDulChecklist(dulChecklist);
							dulChecklistSelections.add(dulChecklistSelection);
						}
						
						logger.info("Value of Duls selected in dulSet at index " + dulSetIndex + "for study at index" + studyIndex + " = " + dulSelections);
						//Check if this dulSet is already present
						if(validationMap.containsKey(dulSelections)) {
							Integer duplicateDulSetIndex = validationMap.get(dulSelections);
							this.addActionError("Duplicate DULs found in DulSets at index " + duplicateDulSetIndex + " and " + dulSetIndex);
						} else {
							validationMap.put(dulSelections, new Integer(dulSetIndex));
							dulSet.setDulChecklistSelections(dulChecklistSelections);
						}
					}		
				}				
				//All selections in a dulSet retrieved
				}
			}
			//All dulSets in a study scanned
		}
		//All studies in an ic scanned
		
		if(hasActionErrors()) {
			setInstCertification(instCert);
			prepareDisplay();
		}
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
		}
		instCert.setProject(project);
		saveProject(project);
		
		return SUCCESS;
	}
	
	/**
	 * Add an empty study to the selected IC
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public String addStudy() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		
		//Get the  currently selected IC
		String jsonInstCert = 
			ServletActionContext.getRequest().getParameter("instCert");
		
		InstitutionalCertification instCert = mapper.readValue(jsonInstCert, InstitutionalCertification.class);
		
		Study study = new Study();
		StudiesDulSet studiesDulSet = new StudiesDulSet();
		study.addStudiesDulSet(studiesDulSet);
		instCert.addStudy(study);
		setInstCertification(instCert);
		
		return SUCCESS;
	}
	
	/**
	 * Add an empty Dul to the selected Study
	 */
	public String addDul() {
		
		InstitutionalCertification instCert = getInstCertification();
		String selectedStudyIndex = ServletActionContext.getRequest().getParameter("studyIndex");
		List<Study> studies = instCert.getStudies();
		Study study = studies.get(Integer.parseInt(selectedStudyIndex));
		StudiesDulSet studiesDulSet = new StudiesDulSet();
		studiesDulSet.setStudy(study);
		study.addStudiesDulSet(studiesDulSet);
		instCert.setStudies(studies);
		setInstCertification(instCert);
		
		return SUCCESS;
	}
	
	/**
	 * Remove the specified study from the IC
	 * @return
	 */
	public String removeStudy() {
		
		InstitutionalCertification instCert = getInstCertification();
		String selectedStudyIndex = ServletActionContext.getRequest().getParameter("studyIndex");
		List<Study> studies = instCert.getStudies();
		
		studies.remove(selectedStudyIndex);
		instCert.setStudies(studies);
		setInstCertification(instCert);
		
		return SUCCESS;
	}
	
	/**
	 * Remove the specified DUL from a selected study on the IC
	 * @return
	 */
	public String removeDul() {
		
		InstitutionalCertification instCert = getInstCertification();
		String parentStudyIndex = ServletActionContext.getRequest().getParameter("studyIndex");
		String selectedDulIndex = ServletActionContext.getRequest().getParameter("dulIndex");
		List<Study> studies = instCert.getStudies();
		
		studies.get(Integer.parseInt(parentStudyIndex)).getStudiesDulSets().remove(selectedDulIndex);
		instCert.setStudies(studies);
		setInstCertification(instCert);
		
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
	 * Delete Genomic Data Sharing Plan Document
	 * 
	 * @return forward string
	 */
	public String deleteInstCertificationFile() {
		logger.info("deleteInstCertificationFile()");
		
		try {
			if (getDocId() == null) {
				inputStream = new ByteArrayInputStream(
						getText("error.doc.id").getBytes("UTF-8"));

				return INPUT;
			}
			fileUploadService.deleteFile(getDocId());
			icFileDocs = fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_IC, new Long(getProjectId()));
			
		} catch (UnsupportedEncodingException e) {
			try {
				inputStream = new ByteArrayInputStream(getText("error.doc.delete").getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				return INPUT;
			}
			return INPUT;
		}

		return SUCCESS;
	}
	
	
	/**
	 * Validate Upload File
	 */
	private boolean validateUploadFile(File file, String contentType) {

		String errorMessage = "";
		
		try {
			if (file == null) {
				errorMessage = getText("error.doc.required");

			} else if (file.length() == 0) {
				errorMessage = getText("error.doc.empty");

			} else if (file.length() > 5000000) {
				errorMessage = getText("error.doc.size");

			} else if (!"application/pdf".equals(contentType)
					&& !"application/msword".equals(contentType)
					&& !"application/vnd.openxmlformats-officedocument.wordprocessingml.document"
							.equals(contentType)) {
				errorMessage = getText("error.doc.format");

			}
			if(StringUtils.isNotBlank(errorMessage)) {
				inputStream = new ByteArrayInputStream(errorMessage.getBytes("UTF-8"));
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
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
		setProject(retrieveSelectedProject());
		
		return SUCCESS;
	}
	
	
	/**
	 * Saves the certificationComplete flag. Invoked from:
	 * 'Save' or 'Save and Next' button on the Track Institutional Certification
	 *  Status page.
	 * 
	 * @return
	 */
	public String saveICCompletion() {
		
		String certComplete = getProject().getCertificationCompleteFlag();
		
		Project project = retrieveSelectedProject();
		
		project.setCertificationCompleteFlag(certComplete);
		
		setProject(saveProject(project));		
		
		return SUCCESS;
	}
	
	
	/**
	 * Deletes an IC. Invoked when the delete button is clicked on the Track
	 * Submission Status (IC List) page
	 * 
	 * @return
	 */
	public String deleteIc() {
		Project project = retrieveSelectedProject();
		if(!CollectionUtils.isEmpty(project.getInstitutionalCertifications())) {
			for(InstitutionalCertification cert: project.getInstitutionalCertifications()) {
				if(cert.getId().equals(Long.valueOf(getInstCertId()))) {
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

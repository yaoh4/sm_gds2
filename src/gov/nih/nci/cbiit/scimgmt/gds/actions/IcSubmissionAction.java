/**
 * 
 */
package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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
					if(doc.getInstitutionalCertificationId() != null && 
							doc.getInstitutionalCertificationId().equals(instCert.getId()))
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
		setParentDulChecklists(GdsSubmissionActionHelper.getDulChecklistsSets());
		
		//Get display data.
		//We need to show the duls stored for each study
		
		ArrayList<String> dulIdList = new ArrayList<String>();
		int studyIndex = -1;
		for(Study study: instCert.getStudies()) {
			studyIndex++;
			int dulSetIndex = -1;
			for(StudiesDulSet studiesDulSet: study.getStudiesDulSets() ) {
				dulSetIndex++;
				String parentDulId = null;
				List<DulChecklistSelection> dulChecklistSelections = studiesDulSet.getDulChecklistSelections();
				if(!CollectionUtils.isEmpty(dulChecklistSelections)) {
					for(DulChecklistSelection dulChecklistSelection: dulChecklistSelections) {
						
						String dulId = dulChecklistSelection.getDulChecklist().getId().toString();
						if(dulChecklistSelection.getDulChecklist().getParentDulId() == null) {
							//This has no parent, so it represents additional text.
							//associated with a parent DUL
							if(parentDulId == null) {
								parentDulId = dulId;
							}
							String additionalText = dulChecklistSelection.getOtherText();
							if(additionalText != null && !additionalText.isEmpty()) {
								int textlength = additionalText.length();
								dulIdList.add(textlength + "otherAddText" + studyIndex + "-" + dulSetIndex + "-" + dulId + additionalText);
							} 						
						} else {
							//This has a parent, so it represents a regular child dul selections
							dulIdList.add("dul" + studyIndex + "-" + dulSetIndex + "-" + dulId);
							if(parentDulId == null) {
								parentDulId = dulChecklistSelection.getDulChecklist().getParentDulId().toString();
							}
						}
					}
					if(parentDulId != null) {
						dulIdList.add("parentDul" + studyIndex + "-" + dulSetIndex + "-" + parentDulId);
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
			
		validateStudyAndDul();
	}
	
	public void validateStudyAndDul() {
		
		InstitutionalCertification instCert = getInstCertification();
		List<Study> studies = instCert.getStudies();
		
		logger.info("No. of Studies in IC = " + studies.size());
		
		int studyIndex = -1;
		//validate the DULs in each Study
		for(Study study: instCert.getStudies()) {
			if(study == null) {
				continue;
			}
			
			studyIndex++;
			
			if(study.getStudyName() == null || study.getStudyName().isEmpty()) {
				addActionError("Study Name missing for study at position " + studyIndex + 1);
			}
			
			int dulSetIndex = -1;
			if(study.getId() == null || study.getId().toString().isEmpty()) {
				study.setCreatedBy(loggedOnUser.getAdUserId().toUpperCase());
			} else {
				study.setLastChangedBy(loggedOnUser.getAdUserId().toUpperCase());
			}
			study.setInstitutionalCertification(instCert);
			
			//Map used to keep track of duplicate DulSets in a study
			HashMap<String, Integer> validationMap = new HashMap<String, Integer>();
			List<StudiesDulSet> dulSets = study.getStudiesDulSets();
			logger.info("No. of dulSets in study at index at " + studyIndex + " = " + dulSets.size());
			if(CollectionUtils.isEmpty(dulSets)) {
				addActionError("No DUL selection made for study " + study.getStudyName());
			} else {
				for(StudiesDulSet dulSet: study.getStudiesDulSets()) {
					if(dulSet == null) {
						continue;
					}
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
						int position = dulSetIndex  +1;
						this.addActionError("No DUL selection made in DUL Type at position " + position + " for study " + study.getStudyName());
					} else {
						
						//Represents the duls selected in a dulSet
						List<DulChecklistSelection> dulChecklistSelections = new ArrayList<DulChecklistSelection>();
					
						if(ApplicationConstants.PARENT_DUL_ID_DISEASE_SPECIFIC.equals(Long.valueOf(parentDulId[0])) 
							|| ApplicationConstants.PARENT_DUL_ID_OTHER.equals(Long.valueOf(parentDulId[0]))) {
						
							//These need to have additional text info, so pick that up
							String[] otherAddText = ServletActionContext.getRequest().getParameterValues("otherAddText-" + studyIndex + "-" + dulSetIndex + "-" + parentDulId[0]);					
							DulChecklistSelection dulChecklistSelection = createDulChecklistSelection(parentDulId[0], otherAddText);
							dulChecklistSelection.setStudiesDulSet(dulSet);						
							dulChecklistSelections.add(dulChecklistSelection);	
							
							if(otherAddText == null || otherAddText[0].isEmpty()) {
								if(ApplicationConstants.PARENT_DUL_ID_DISEASE_SPECIFIC.equals(Long.valueOf(parentDulId[0]))) {
									this.addActionError("Please enter additional info for Disease Specific DUL selected in study: " + study.getStudyName());
								} else if(ApplicationConstants.PARENT_DUL_ID_OTHER.equals(Long.valueOf(parentDulId[0]))) {
									this.addActionError("Please enter additional info for Other DUL selected in study: " + study.getStudyName());
								}
							}
						} 
											
						String selectedDulsParam = "dul-" + studyIndex + "-" + dulSetIndex + "-" + parentDulId[0];
						String [] selectedDuls = ServletActionContext.getRequest().getParameterValues(selectedDulsParam);
						if(selectedDuls == null) {
							if(!ApplicationConstants.PARENT_DUL_ID_OTHER.equals(Long.valueOf(parentDulId[0]))) {
								//This is not an 'Other' DUL set, so we need at least one selection
								int position = dulSetIndex + 1;
								this.addActionError("No DUL selection made in the DUL Type at position " + position + " for study: " + study.getStudyName());
							} 
						} else {
							//We have at least one selection in this this DUL Set, process them
							String dulSelections = "";
							logger.info("No. of Duls selected in dulSet at index " + dulSetIndex + " for study at index" + studyIndex);
							for(int i = 0; i < selectedDuls.length; i++) {										
							
								dulSelections = dulSelections + selectedDuls[i];
							
								//for each selectedDul, create a dulChecklistSelection
								DulChecklistSelection dulChecklistSelection = createDulChecklistSelection(selectedDuls[i], null);
								dulChecklistSelection.setStudiesDulSet(dulSet);
								dulChecklistSelections.add(dulChecklistSelection);
							}
						
							logger.info("Value of Duls selected in dulSet at index " + dulSetIndex + " for study at index " + studyIndex + " = " + dulSelections);
							//Check if this dulSet is already present
							if(validationMap.containsKey(dulSelections)) {
								Integer duplicateDulSetIndex = validationMap.get(dulSelections);
								int dupPosition = duplicateDulSetIndex + 1;
								int position = dulSetIndex + 1;
								this.addActionError("Duplicate DULs found in DUL Types at position " + dupPosition + " and " + position + " for study: " + study.getStudyName());
							} else {
								validationMap.put(dulSelections, new Integer(dulSetIndex));
							}
						} //End process selected DULs
						dulSet.setDulChecklistSelections(dulChecklistSelections);
				 	}//Done retrieving parent DUl and selections for a dulSet	
				}//End for-loop for iterating through dulSets				
			} 
			
		} //End for-loop for iterating through studies
		
		
		if(hasActionErrors()) {
			setProject(retrieveSelectedProject());
			setInstCertification(instCert);
			prepareDisplay(instCert);
			setDocId(getDocId());
		}
	}
	
	
	
	private DulChecklistSelection createDulChecklistSelection(String dulId, String[] otherText) {
		
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
		if(otherText != null && !otherText[0].isEmpty()) {
			dulChecklistSelection.setOtherText(otherText[0]);
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

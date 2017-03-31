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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsMissingDataUtil;
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
	
	private String icIds = "";
	
	private String studyIds = "";
	
	private List<ParentDulChecklist> parentDulChecklists = new ArrayList<ParentDulChecklist>();
	
	private File ic;
	
	private String icFileName;

	private String icContentType;

	private List<Document> icFileDocs = new ArrayList<Document>();
	
	private Document doc = null; // json object to be returned for UI refresh after upload
		
	private Boolean newIC = false;
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
		
		logger.debug("Studies selected: " + studyIds);
		
		setProject(retrieveSelectedProject());
		studiesForSelection = retrieveStudies();
		
		InstitutionalCertification instCert = null;
		if(instCertId != null) {
			instCert = retrieveIC(Long.valueOf(instCertId));
		}
		
		if(instCert != null) {
			loadFiles(instCert);
		} else {
			instCert = new InstitutionalCertification();
			//Retrieve and populate studies that were selected
			studyIds = StringUtils.deleteWhitespace(studyIds);
			if(StringUtils.isEmpty(studyIds)) {
				addActionError("Select a Study");
				return INPUT;
			}
			for(String studyId: Arrays.asList(StringUtils.split(studyIds, ","))) {
				Study study = getProject().getStudyById(Long.parseLong(studyId));
				StudiesDulSet studiesDulSet = new StudiesDulSet();
				study.addStudiesDulSet(studiesDulSet);
				instCert.addStudy(study);
			}		
		}
        
		setInstCertification(instCert);
		prepareDisplay(instCert);
        return SUCCESS;
	}
	
	/**
	 * Called when the user clicks Add Institutional Certificate button from IC page
	 * 
	 * @return
	 * @throws Exception
	 */
	public String retrieveStudiesForSelection() throws Exception {
		logger.debug("retrieveStudiesForSelection");
		
		newIC = true;
		
		setProject(retrieveSelectedProject());
		
		studiesForSelection = retrieveStudies();
		
		return SUCCESS;
	}
	
	private void loadFiles(InstitutionalCertification instCert) {
		if(instCert != null) {
			icFileDocs = new ArrayList<Document>();
			List<Document> docs = 
				fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_IC, Long.valueOf(getProjectId()));
			if(docs != null && !docs.isEmpty()) {
				for(Document doc: docs) {
					if((getDocId() != null && doc.getId().equals(getDocId())) || (doc.getInstitutionalCertificationId() != null && 
							doc.getInstitutionalCertificationId().equals(instCert.getId()))) {
						icFileDocs.add(doc);
					}			
				}
			}
			if(icFileDocs.isEmpty() && doc != null && doc.getId() != null) {
					icFileDocs.add(doc);
			}
			if(!icFileDocs.isEmpty()) {
				doc = icFileDocs.get(0);
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
							//This represents a parent row, so check if additional text present	
							String additionalText = dulChecklistSelection.getOtherText();
							String dulAppendix = dulChecklistSelection.getStudiesDulSet().getComments();
							if(dulAppendix != null && !dulAppendix.isEmpty()) {
								int commentsLength = dulAppendix.length();
								dulIdList.add(commentsLength + "comments" + studyIndex + "-" + dulSetIndex + "-" + dulId + dulAppendix);
							}
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
	private InstitutionalCertification retrieveIC(Long instCertId) {
	
		if(instCertId != null) {
			
			Project project = getProject();
			if(project == null) {
				project = retrieveSelectedProject();
			}
			List<InstitutionalCertification> certs = project.getInstitutionalCertifications();
			if(!CollectionUtils.isEmpty(certs)) {
				for(InstitutionalCertification cert: certs) {
					if(cert.getId().equals(instCertId)) {
						return cert;
					}
				}
			}
		} 
		
		return null;
	}
	
	
	
	public void validateSaveIc() {
		
		this.clearActionErrors();
		InstitutionalCertification instCert = getInstCertification();
		
		if(ic != null) {
			if(getDocId() == null) {
				//If no file has been uploaded yet, then we do not
				//show any more fields to enter
				setProject(retrieveSelectedProject());
				studiesForSelection = retrieveStudies();
				prepareDisplay(instCert);
				return;
			}
		}
		
		if(getDocId() == null) {
			setProject(retrieveSelectedProject());
			studiesForSelection = retrieveStudies();
			prepareDisplay(instCert);
			this.addActionError(getText("error.doc.required"));
			return;
		}
		
		logger.info("No. of Studies in IC = " + instCert.getStudies().size());
		
		if(instCert.getComments() != null && instCert.getComments().length() > 2000) {
			addActionError(getText("error.comments.size.exceeded"));
		}
		
		
		//validate the DULs in each Study
		Iterator<Study> studies = instCert.getStudies().iterator();
		Project project = retrieveSelectedProject();
		instCert.addProject(project);
		
		//If any Study is replaced or removed, remove the association to this IC and DULs associated with this Study
		Set<Long> newStudies = new HashSet<Long>();
		for(Study s: instCert.getStudies()) {
			if(s != null)
				newStudies.add(s.getId());
		}
		for(Study s: project.getStudies()) {
			if(!newStudies.contains(s.getId()) && !CollectionUtils.isEmpty(s.getInstitutionalCertifications())
					&& s.getInstitutionalCertifications().get(0).getId().equals(instCert.getId())) {
				s.getInstitutionalCertifications().clear();
			}
		}
		
		while(studies.hasNext()) {
					
			Study study = studies.next();
			if(study == null || StringUtils.isBlank(study.getDisplayId())) {
				//This means that the study was deleted by the user
				//hence correspondingly remove it from our data structure
				studies.remove();
				continue;
			}
			processStudyAttributes(study);
			study.addInstitutionalCertification(instCert);
			study.setProject(project);
			int studyIndex = Long.valueOf(study.getDisplayId()).intValue();
			boolean atLeastOneDULSelected = false;
			
			//Map used to keep track of duplicate DulSets in a study
			HashMap<String, Integer> validationMap = new HashMap<String, Integer>();
			
			List<StudiesDulSet> dulSets = study.getStudiesDulSets();
			logger.info("No. of dulSets in study: " + study.getStudyName() + " = " + dulSets.size());
			if(!CollectionUtils.isEmpty(dulSets)) {
				
				Iterator<StudiesDulSet> dulSetsIterator = dulSets.iterator();		
				while(dulSetsIterator.hasNext()) {
					StudiesDulSet dulSet = dulSetsIterator.next();
					
					if(dulSet == null || StringUtils.isBlank(dulSet.getDisplayId())) {
						//This means that the DulSet was deleted by the user
						//hence correspondingly remove it from our data structure
						dulSetsIterator.remove();
						continue;
					}
					
					int dulSetIndex = Long.valueOf(dulSet.getDisplayId()).intValue();
					String [] parentDulId = ServletActionContext.getRequest().getParameterValues("parentDul-" + studyIndex + "-" + dulSetIndex);
					
					if(dulSet.getId() == null || dulSet.getId().toString().isEmpty()) {
						dulSet.setCreatedBy(loggedOnUser.getAdUserId());
					} else {
						dulSet.setLastChangedBy(loggedOnUser.getAdUserId());				
					}
					if(parentDulId != null) {
					String dulAppendix = ServletActionContext.getRequest().getParameter("comments-" + studyIndex + "-" + dulSetIndex + "-" + parentDulId[0]);					
					if(dulAppendix == null) {
						dulAppendix = "";
					}
						dulSet.setComments(dulAppendix);
					}
					dulSet.setStudy(study);
					
					
					if(parentDulId != null) {
					
						List<DulChecklistSelection> dulChecklistSelections = processDulSet(parentDulId[0],  
								study.getStudyName(), studyIndex, dulSet, validationMap);
						dulSet.setDulChecklistSelections(dulChecklistSelections);
						if(!dulChecklistSelections.isEmpty()) {
							atLeastOneDULSelected = true;
						}
				 	} else {
				 		if(study.getStudiesDulSets().size() > 1) {
							dulSetsIterator.remove();
						}
				 	}
				}//End while-loop for iterating through dulSets				
			} 
			if(!atLeastOneDULSelected 
				&& ApplicationConstants.IC_GPA_APPROVED_YES_ID.equals(instCert.getGpaApprovalCode())
				&& ApplicationConstants.IC_PROV_FINAL_ID_FINAL.equals(instCert.getProvisionalFinalCode())) {
				addActionError(getText("error.ic.study.dulTypes.required", new String[]{study.getStudyName()}));
			}
		} //End for-loop for iterating through studies
		
		if(hasActionErrors()) {
			setProject(retrieveSelectedProject());
			studiesForSelection = retrieveStudies();
			setInstCertification(instCert);
			prepareDisplay(instCert);
			setDocId(getDocId());
		}
	}
	
	
	private void processStudyAttributes(Study study) {
		
		if(study.getStudyName() == null || study.getStudyName().isEmpty()) {
			addActionError(getText("error.ic.study.studyName.required") );
		} else if(study.getStudyName().length() > 100) {
			addActionError(getText("error.studyName.size.exceeded"));
		}
		
		if(study.getInstitution() != null && study.getInstitution().length() > 120) {
			addActionError(getText("error.institution.size.exceeded", new String[]{study.getStudyName()}));
		}
		
		if(study.getId() == null || study.getId().toString().isEmpty()) {
			study.setCreatedBy(loggedOnUser.getAdUserId());
		} else {
			study.setLastChangedBy(loggedOnUser.getAdUserId());
		}
		
		if(study.getComments() != null && study.getComments().length() > 2000) {
			addActionError(getText("error.comments.size.exceeded"));
		}
		
	}
	
	
	private List<DulChecklistSelection> processDulSet(String parentDulId, 
			String studyName, int studyIndex, StudiesDulSet dulSet, 
			HashMap<String, Integer> validationMap) {
		
		List<DulChecklistSelection> dulChecklistSelections = new ArrayList<DulChecklistSelection>();
		int dulSetIndex = Long.valueOf(dulSet.getDisplayId()).intValue();
		
		String[] additionalText = ServletActionContext.getRequest().getParameterValues("otherAddText-" + studyIndex + "-" + dulSetIndex + "-" + parentDulId);					
		if(additionalText == null) {
			additionalText = new String[]{""};
		}
		
		//Create DulChecklistSelection for parent
		DulChecklistSelection dulParentSelection = createDulChecklistSelection(parentDulId, additionalText[0]);
		dulParentSelection.setStudiesDulSet(dulSet);		
		dulChecklistSelections.add(dulParentSelection);
		
		if(additionalText == null || additionalText[0].isEmpty()) {
			if(ApplicationConstants.IC_PARENT_DUL_ID_DISEASE_SPECIFIC.equals(Long.valueOf(parentDulId))) {
				this.addActionError(getText("error.ic.study.dulType.diseaseText.required", new String[]{studyName}));
			} else if(ApplicationConstants.IC_PARENT_DUL_ID_OTHER.equals(Long.valueOf(parentDulId))) {	
				this.addActionError(getText("error.ic.study.dulType.additionalText.required", new String[]{studyName}));
			} 
		} else {
			if(ApplicationConstants.IC_PARENT_DUL_ID_DISEASE_SPECIFIC.equals(Long.valueOf(parentDulId))) {
				//Validation for disease specific text
				if (additionalText[0].length() > 200) {
					addActionError(getText("error.ic.study.dulType.diseaseText.size.exceeded", new String[]{studyName}));
				} 
			} else if(ApplicationConstants.IC_PARENT_DUL_ID_OTHER.equals(Long.valueOf(parentDulId))) {
				//Validation for other text
				if (additionalText[0].length() > 2000) {
					addActionError(getText("error.ic.study.dulType.additionalText.size.exceeded", new String[]{studyName}));
				}
			}
		}
		String dulSelectionsStr = parentDulId + additionalText[0];
		
		String selectedDulsParam = "dul-" + studyIndex + "-" + dulSetIndex + "-" + parentDulId;
		String [] selectedDuls = ServletActionContext.getRequest().getParameterValues(selectedDulsParam);
		
		//Create DulChecklistSelections for individual DUL selections
		if(selectedDuls != null && selectedDuls.length > 0) {
		
			//Proceed only if we have at least one selection in this this DUL Set
			for(int i = 0; i < selectedDuls.length; i++) {										
			
				dulSelectionsStr = dulSelectionsStr + selectedDuls[i];
			
				DulChecklistSelection dulChecklistSelection = createDulChecklistSelection(selectedDuls[i], null);
				dulChecklistSelection.setStudiesDulSet(dulSet);
				dulChecklistSelections.add(dulChecklistSelection);		
			}
		}
		
		//Check if this dulSet is already present
		if(validationMap.containsKey(dulSelectionsStr)) {
			this.addActionError(getText("error.ic.study.dulSelection.duplicate", new String[]{studyName}));
		} else {
			validationMap.put(dulSelectionsStr, Integer.valueOf(dulSetIndex));
		}
		
		return dulChecklistSelections;
	}
	
	
	private DulChecklistSelection createDulChecklistSelection(String dulId, String otherText) {
		
		//for each selectedDul, create a dulChecklistSelection
		DulChecklistSelection dulChecklistSelection = new DulChecklistSelection();
		if(dulChecklistSelection.getId() == null || dulChecklistSelection.getId().toString().isEmpty()) {
			dulChecklistSelection.setCreatedBy(loggedOnUser.getAdUserId());
		} else {
			dulChecklistSelection.setLastChangedBy(loggedOnUser.getAdUserId());
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
		logger.info("Saving IC.");
		
		Long docId = null;
		
		InstitutionalCertification instCert = getInstCertification();
		Long storedCertId = instCert.getId();
		
		if(storedCertId != null) {
			instCert.setLastChangedBy(loggedOnUser.getAdUserId());
		} else {
			instCert.setCreatedBy(loggedOnUser.getAdUserId());
			
			//Check if there is an file which doesn't have certId
			icFileDocs = fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_IC, Long.valueOf(getProjectId()));
			for(Document doc: icFileDocs) {
				if(doc.getInstitutionalCertificationId() == null) {
					docId = doc.getId();
					break;
				}		
			}
		}
		
		//Save the IC
		instCert = manageProjectService.saveOrUpdateIc(instCert);
		
		Project project = retrieveSelectedProject();
		
		// Update CertId
		if(docId != null) {
			// Get latest CertId
			class InstitutionalCertificationComparator implements Comparator<InstitutionalCertification> {

				public int compare(InstitutionalCertification e1, InstitutionalCertification e2) {
					return e2.getId().compareTo(e1.getId());
				}
			}

			List<InstitutionalCertification> icList = project.getInstitutionalCertifications();
			Collections.sort(icList, new InstitutionalCertificationComparator());
			fileUploadService.updateCertId(docId, icList.get(0).getId());
		}
		
		if(storedCertId == null) {
			//This is a new ic, so add it to the project
			//project.getInstitutionalCertifications().add(instCert);
			project = super.saveProject(project, ApplicationConstants.PAGE_CODE_IC,false);
		} else {
		project = super.saveProject(project, ApplicationConstants.PAGE_CODE_IC);
		}
		setProject(project);
		
		setProjectId(project.getId().toString());
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
				Long.valueOf(getProjectId()), ApplicationConstants.DOC_TYPE_IC, ic, icFileName, getInstCertification().getId());
			setDocId(doc.getId());
			icFileDocs = fileUploadService.retrieveFileByIcId(instCertification.getId(), Long.valueOf(getProjectId()));
			
		} catch (Exception e) {
			try {
				logger.error("Error uploading IC doc ", e);
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
		return retrieveIC(instCertification.getId()).getStatus();
	}
	

	/**
	 * Invoked while saving the IC 
	 */
	protected String computePageStatus(Project project) {
		
		String icStatus = getInstCertification().getStatus();
		
		if(!ApplicationConstants.FLAG_YES.equalsIgnoreCase(project.getCertificationCompleteFlag())
				|| ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS.equals(icStatus)) {
			//More ICs need to be added or the current one is not completed, so 
			//overall status is inprogress
			return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
		} else {
			//Else return status based on other ICs
			List<InstitutionalCertification> certs = project.getInstitutionalCertifications();
			Long instCertId = getInstCertification().getId();
			for(InstitutionalCertification ic: certs) {
				if(!ic.getId().equals(instCertId)) {
					if(ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS.equals(ic.getStatus())) {
						return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
					}
				}
			}
			
			return ApplicationConstants.PAGE_STATUS_CODE_COMPLETED;
		}
	}


	public String getMissingIcData() {
		
		setPage(getLookupByCode(ApplicationConstants.PAGE_TYPE, ApplicationConstants.PAGE_CODE_IC));
		
		Project project = retrieveSelectedProject();
		setMissingDataList(GdsMissingDataUtil.getInstance().getMissingIcData(project, Long.valueOf(instCertId)));		
			
		return SUCCESS;
	}

	public String getStudyIds() {
		return studyIds;
	}

	public void setStudyIds(String studyIds) {
		this.studyIds = studyIds;
	}

	public Boolean getNewIC() {
		return newIC;
	}

	public void setNewIC(Boolean newIC) {
		this.newIC = newIC;
	}
}

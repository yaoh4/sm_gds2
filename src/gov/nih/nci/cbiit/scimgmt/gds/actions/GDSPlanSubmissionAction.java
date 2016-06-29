package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.constants.PlanQuestionList;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanQuestionsAnswer;
import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;
import gov.nih.nci.cbiit.scimgmt.gds.model.UIList;
import gov.nih.nci.cbiit.scimgmt.gds.util.UIRuleUtil;

/**
 * Manages Submission creation, updates and deletion.
 * 
 * @author dinhys
 *
 */
@SuppressWarnings("serial")
public class GDSPlanSubmissionAction extends ManageSubmission {
	
	static Logger logger = LogManager.getLogger(GDSPlanSubmissionAction.class);
	
	@Autowired
	protected UIRuleUtil uIRuleUtil;
	
	private Map<String, UIList> uiControlMap = new HashMap<String, UIList> ();
	
	private PlanQuestionList questionList;
	
	private File dataSharingPlan;
	
	private String dataSharingPlanFileName;

	private String dataSharingPlanContentType;
	
	private File exceptionMemo;
	
	private String exceptionMemoFileName;

	private String exceptionMemoContentType;
	
	private String dataSharingPlanEditorText;
	
	private List<Document> excepMemoFile;
	
	private List<Document> gdsPlanFile;
	
	private Map<Long, List<String>> answerMap = new HashMap<Long, List<String>>();
	
	private Map<Long, List<String>> otherTextMap = new HashMap<Long, List<String>>();
	
	private Document doc = null; // json object to be returned for UI refresh after upload
	
	private String comments;

	private boolean warnOnly = true;
	
	private Set<Long> newSet = new HashSet<Long>();
		
	private Set<Long> oldSet = new HashSet<Long>();
	
	private Set<Long> otherSet = new HashSet<Long>();

	
	/**
	 * Execute method for Genomic Data Sharing Plan.  
	 * Invoked from General Info page Next button, Navigation tab for Genomic Data Sharing Plan, 
	 * Submission Details page edit.
	 * 
	 * The following will be performed.
	 * 1. Retrieve project from database based on project id.
	 * 2. Get UI rule map to control UI elements based on UI selection.
	 * 3. Navigate to Genomic Data Sharing Plan page.
	 * 
	 * @return forward string
	 */
	public String execute() throws Exception {

		logger.debug("execute");
        
		if(StringUtils.isEmpty(getProjectId())) {
			throw new Exception();
		}
		setProject(retrieveSelectedProject());
		
		setUpPageData();
		
		//logger.debug("div id 8: " + getMap().get("8").getStyle());
        return SUCCESS;
	}

	
	/**
	 * Save Genomic Data Sharing Plan 
	 * Invoked from Genomic Data Sharing Plan Save button.
	 * 
	 * The following will be performed.
	 * 1. Saves user-selected answers for Genomic Data Sharing Plan questions.
	 * 2. If any repository has been removed, need to remove it from the repository list.
	 * 2. Update UI Control based on user-selected answers.
	 * 3. Navigate to Genomic Data Sharing Plan page.
	 * 4. If Save and next button, navigate to Institutional Certification(s) page.
	 * 
	 * @return forward string
	 */
	public String save() throws Exception {
		
		logger.debug("Save GDS Plan");
		
		setProject(retrieveSelectedProject());
		
		// Save comments
		getProject().setPlanComments(comments);
		
		// Save text as documents if any
		if(StringUtils.isNotBlank(dataSharingPlanEditorText)) {
			logger.debug(dataSharingPlanEditorText);
			try {
				doc = fileUploadService.storeFile(new Long(getProjectId()), "GDSPLAN", dataSharingPlanEditorText);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		populatePlanAnswerSelection();

		// Call method to remove files and DB objects based on old and new user selection
		warnOnly = false;
		performDataCleanup();

		super.saveProject(getProject());
		
		setProject(retrieveSelectedProject());
		
		setUpPageData();
		
        return SUCCESS;
	}
	
	/**
	 * Validate Save Genomic Data Sharing Plan 
	 */
	public void validateSave() {
		
		logger.debug("Validate save GDS Plan");
		// If Other repository is selected, verify that OtherText is entered.
		if(answerMap.get(new Long(20)) != null && answerMap.get(new Long(20)).contains("25")) {
			if(otherTextMap.get(new Long(25)) == null) {
				this.addActionError(getText("error.other.specify"));
			} else {
				otherTextMap.get(new Long(25)).remove("");
				if(otherTextMap.get(new Long(25)).isEmpty()) {
					otherTextMap.remove(new Long(25));
					this.addActionError(getText("error.other.specify"));
				}
			}
		}
		
		//Comments cannot be greater than 2000 characters.
		if (!StringUtils.isEmpty(comments)) {
			if (comments.length() > ApplicationConstants.COMMENTS_MAX_ALLOWED_SIZE) {
				this.addActionError(getText("error.comments.size.exceeded"));
			}
		}

		setProject(retrieveSelectedProject());
		
	}

	/**
	 * Save Genomic Data Sharing Plan and Navigate to IC page. 
	 * Invoked from Genomic Data Sharing Plan Save & Next button.
	 * 
	 * @return forward string
	 */
	public String saveAndNext() throws Exception {
		
		save();
		
		if(showPage("ic"))
			return "ic";
		
		if(showPage("bsi"))
			return "bsi";
		
		if(showPage("repository"))
			return "repository";
		
        return SUCCESS;
	}
	
	/**
	 * Validate Save & Next Genomic Data Sharing Plan 
	 */
	public void validateSaveAndNext() {
		
		validateSave();
	}
	
	/**
	 * Upload Exception Memo Document
	 * 
	 * @return forward string
	 */
	public String uploadExceptionMemo() {
		logger.info("uploadExceptionMemo()");

		if (!validateUploadFile(exceptionMemo, exceptionMemoContentType))
			return INPUT;
			
		try {
			doc = fileUploadService.storeFile(new Long(getProjectId()), "EXCEPMEMO", exceptionMemo, exceptionMemoFileName);
			excepMemoFile = fileUploadService.retrieveFileByDocType("EXCEPMEMO", new Long(getProjectId()));
			
		} catch (Exception e) {
			try {
				inputStream = new ByteArrayInputStream("Error Uploading File".getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				return INPUT;
			}
			return INPUT;
		}
		
		logger.info("===> docId: " + doc.getId());
		logger.info("===> fileName: " + doc.getFileName());
		logger.info("===> docTitle: " + doc.getDocTitle());
		logger.info("===> uploadDate: " + doc.getCreatedDate());

		return SUCCESS;
	}
	
	/**
	 * Validate Upload File
	 */
	private boolean validateUploadFile(File file, String contentType) {

		String errorMessage = "";
		
		try {
			if (file == null) {
				errorMessage = "Upload file is required";

			} else if (file.length() == 0) {
				errorMessage = "Upload file contains no data (length = 0)";

			} else if (file.length() > 5000000) {
				errorMessage = "Upload file size is larger than maximum file size (5MB)";

			} else if (!"application/pdf".equals(contentType)
					&& !"application/msword".equals(contentType)
					&& !"application/vnd.openxmlformats-officedocument.wordprocessingml.document"
							.equals(contentType)) {
				errorMessage = "Upload file must be in Word or PDF format";

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
	 * Upload Genomic Data Sharing Plan Document
	 * 
	 * @return forward string
	 */
	public String uploadDataSharingPlan() {
		logger.info("uploadDataSharingPlan()");
		
		if (!validateUploadFile(dataSharingPlan, dataSharingPlanContentType))
			return INPUT;
		
		try {
			doc = fileUploadService.storeFile(new Long(getProjectId()), "GDSPLAN", dataSharingPlan, dataSharingPlanFileName);
			gdsPlanFile = fileUploadService.retrieveFileByDocType("GDSPLAN", new Long(getProjectId()));
			
		} catch (Exception e) {
			try {
				inputStream = new ByteArrayInputStream("Error Uploading File".getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				return INPUT;
			}
			return INPUT;
		}
				
		logger.info("===> docId: " + doc.getId());
		logger.info("===> fileName: " + doc.getFileName());
		logger.info("===> docTitle: " + doc.getDocTitle());
		logger.info("===> uploadDate: " + doc.getCreatedDate());

		return SUCCESS;
	}
	
	/**
	 * Delete Genomic Data Sharing Plan Document
	 * 
	 * @return forward string
	 */
	public String deleteGdsFile() {
		logger.info("deleteGdsFile()");
		
		try {
			if (getDocId() == null) {
				inputStream = new ByteArrayInputStream(
						"Document Id needs to be provided for delete".getBytes("UTF-8"));

				return INPUT;
			}
			fileUploadService.deleteFile(getDocId());
			gdsPlanFile = fileUploadService.retrieveFileByDocType("GDSPLAN", new Long(getProjectId()));
			
		} catch (UnsupportedEncodingException e) {
			try {
				inputStream = new ByteArrayInputStream("Error Deleting File".getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				return INPUT;
			}
			return INPUT;
		}

		return SUCCESS;
	}
	
	/**
	 * Return true if answer should be pre-selected 
	 * based on saved data for checkboxes
	 */
	public boolean getSelected(Long qId, String aId) {
		
		List<String> list = answerMap.get(qId);
		if(list != null && list.contains(aId))
			return true;
		return false;
	}
	
	/**
	 * Return true if "Why is this project being submitted?" 
	 * is "Required by GDS Policy"
	 */
	public boolean getRequiredByGdsPolicy() {
		
		return (getProject().getSubmissionReasonId() == 26 ? true : false);
	}
	
	/**
	 * This method sets up all data for Genomic Data Sharing Plan page.
	 * @throws Exception 
	 */
	private void setUpPageData() throws Exception{

		//Construct answer lists from DB objects
		populateAnswersMap();
		
		// Always reset the file upload or text radio button selection
		answerMap.remove(29L);
		
		//Get the list of files for display
		uiControlMap = uIRuleUtil.getUiRuleMap(getProject());

		excepMemoFile = fileUploadService.retrieveFileByDocType("EXCEPMEMO", getProject().getId());
		gdsPlanFile = fileUploadService.retrieveFileByDocType("GDSPLAN", getProject().getId());
		
		// Set comments
		comments = getProject().getPlanComments();
				
		logger.debug(JSONUtil.serialize(uiControlMap));
		
		logger.debug(PlanQuestionList.getQuestionById(1L).getDisplayText());
	}
	
	/**
	 * This method converts the PlanAnswerSelection objects to answers map
	 * @throws Exception 
	 */
	private void populateAnswersMap() {

		List<PlanAnswerSelection> savedList = new LinkedList<PlanAnswerSelection>(
				getProject().getPlanAnswerSelection());

		class PlanAnswerSelectionComparator implements Comparator<PlanAnswerSelection> {

			public int compare(PlanAnswerSelection e1, PlanAnswerSelection e2) {
				return e1.getPlanQuestionsAnswer().getQuestionId()
						.compareTo(e2.getPlanQuestionsAnswer().getQuestionId());
			}

		}

		Collections.sort(savedList, new PlanAnswerSelectionComparator());
 
		answerMap.clear();
		Long prevId = null;
		Long otherId = null;
		List<String> ansList = new ArrayList<String>();
		List<String> otherList = new ArrayList<String>();
		for (PlanAnswerSelection e: savedList) {
			Long qId = e.getPlanQuestionsAnswer().getQuestionId();
			Long aId = e.getPlanQuestionsAnswer().getId();
			if(prevId != null && prevId != qId) {
				answerMap.put(prevId, ansList);
				if(otherId !=  null) {
					Collections.sort(otherList);
					otherTextMap.put(otherId, otherList);
					otherList = new ArrayList<String>();
					otherId = null;
				}
				ansList = new ArrayList<String>();
			}
			prevId = qId;
			ansList.add(aId.toString());
			if(StringUtils.isNotBlank(e.getOtherText())) {
				otherId = aId;
				otherList.add(e.getOtherText());
			}
		}
		if(!ansList.isEmpty()) {
			answerMap.put(prevId, ansList);
			if(otherId !=  null) {
				Collections.sort(otherList);
				otherTextMap.put(otherId, otherList);
			}
		}

	}
	
	/**
	 * This method converts the user answers to PlanAnswerSelection objects
	 * @throws Exception 
	 */
	private void populatePlanAnswerSelection() throws Exception{
		
		populateSelectedRemovedSets();
		
		for(Long id: oldSet) {
			getProject().getPlanAnswerSelection().remove(getProject().getPlanAnswerSelectionByAnswerId(id));
		}
		
		for(Long id: otherSet) {
			while(getProject().getPlanAnswerSelectionByAnswerId(id) != null) {
				getProject().getPlanAnswerSelection().remove(getProject().getPlanAnswerSelectionByAnswerId(id));
			}
		}
		
		PlanAnswerSelection newObject = null;
		for (Long id : newSet) {
			PlanQuestionsAnswer planQuestionsAnswer = PlanQuestionList.getAnswerByAnswerId(id);
			newObject = new PlanAnswerSelection();
			newObject.setCreatedBy(loggedOnUser.getAdUserId().toUpperCase());
			newObject.setCreatedDate(new Date());
			newObject.setPlanQuestionsAnswer(planQuestionsAnswer);
			newObject.setProject(getProject());
			if(planQuestionsAnswer.getDisplayText().equalsIgnoreCase("Other")) {
				PlanAnswerSelection otherObject = null;
				for(String otherText: otherTextMap.get(id)) {
					otherObject = new PlanAnswerSelection(newObject);
					otherObject.setOtherText(otherText);
					getProject().getPlanAnswerSelection().add(otherObject);
				}
			} else {
				getProject().getPlanAnswerSelection().add(newObject);
			}
		}

	}
	
	/**
	 * Construct new and old set of answers to be used for warning and
	 * Plan Answer Selection object removal/creation
	 */
	private void populateSelectedRemovedSets() {
		Set<Long> origSet = new HashSet<Long>();
		newSet.clear();
		oldSet.clear();
		otherSet.clear();
		
		for (Entry<Long, List<String>> e : answerMap.entrySet()) {
			for(String entry: e.getValue()) {
				newSet.add(new Long(entry));
				List<String> otherList = otherTextMap.get(new Long(entry));
				if(otherList != null && !otherList.isEmpty()) {
					otherSet.add(new Long(entry));
				}
			}
		}
		for (PlanAnswerSelection e: getProject().getPlanAnswerSelection()) {
			origSet.add(e.getPlanQuestionsAnswer().getId());
		}
		
		oldSet.addAll(origSet);
		oldSet.removeAll(newSet); // deleted set
		
		newSet.removeAll(origSet); // added set
		newSet.addAll(otherSet);
		
	}
	/**
	 * Method to remove other files and db objects as necessary
	 * 		
	 * 		1. If the answer to "Will there be any data submitted?" is changed from Yes to No.
	 *
	 * 		 a) The system will delete the uploaded Data Sharing Plan and the History of Uploaded Documents.
	 * 		 b) The system will delete all uploaded Institutional Certifications documents.
	 * 		 c) The system will delete all Institutional Certifications and Data Use Limitations.
	 * 		 d) The system will delete answers to following questions: 
	 * 		  - Has the GPA reviewed the Basic Study Information?
	 * 		 e) The system will delete  the uploaded Basic Study Info and the History of Uploaded Documents.
	 *
	 * 		2. If the answer to "What specimen type does the data submission pertain to?" 
	 * 		   is set to Non-human only or is changed from Human to Non-human only
	 * 		   And
	 * 		   answer to "What type of access is the data to be made available through?" 
	 * 		   is set to Unrestricted only or is changed from Controlled to Unrestricted only.
	 *
	 * 		 a) The system will delete all DUL(s) created that contains DUL type of 
	 * 		    "Health/Medical/Biomedical", "Disease-specific" and/or "Other".
	 * 
	 * 		3. Remove repositories that were deleted
	 * 
	 * @param newSet 
	 * @param oldSet 
	 * @throws Exception 
	 */
	public String performDataCleanup() throws Exception {

		StringBuffer sb = new StringBuffer();
		
		// Called from ajax, so fetch the project
		if(getProject() == null) {
			setProject(retrieveSelectedProject());
		}
		
		populateSelectedRemovedSets();
		
		// If the answer to "Will there be any data submitted?" is changed from Yes to No.
		if(oldSet.contains(new Long(9)) && newSet.contains(new Long(10))) {
			// a) The system will delete the uploaded Data Sharing Plan and the History of Uploaded Documents.
			if(warnOnly) {
				sb.append("The system will delete the uploaded Data Sharing Plan and the History of Uploaded Documents. ");
			}
			else {
				gdsPlanFile = fileUploadService.retrieveFileByDocType("GDSPLAN", getProject().getId());
				for(Document document: gdsPlanFile) {
					setDocId(document.getId());
					deleteFile();
				}
			}

			// b) The system will delete all uploaded Institutional Certifications documents.
			if(warnOnly) {
				sb.append("The system will delete all uploaded Institutional Certifications documents. ");
			}
			else {
				List<Document> icFile = fileUploadService.retrieveFileByDocType("IC", getProject().getId());
				for(Document document: icFile) {
					setDocId(document.getId());
					deleteFile();
				}
			}
			
			
			// c) The system will delete all Institutional Certifications and Data Use Limitations.
			if(warnOnly) {
				sb.append("The system will delete all Institutional Certifications and Data Use Limitations. ");
			}
			else {
				getProject().getInstitutionalCertifications().clear();
			}
			
			// d) The system will delete answers to Has the GPA reviewed the Basic Study Information?
			if(warnOnly) {
				sb.append("The system will delete answers to Has the GPA reviewed the Basic Study Information. ");
			}
			else {
				getProject().setBsiReviewedFlag("");
			}
			
			// e) The system will delete the uploaded Basic Study Info and the History of Uploaded Documents.
			if(warnOnly) {
				sb.append("The system will delete the uploaded Basic Study Info and the History of Uploaded Documents. ");
			}
			else {
				List<Document> bsiFile = fileUploadService.retrieveFileByDocType("BSI", getProject().getId());
				for(Document document: bsiFile) {
					setDocId(document.getId());
					deleteFile();
				}
			}
			
			// f) Remove repositories that were deleted except dbGaP
			if(warnOnly) {
				sb.append("Remove repositories except dbGaP. ");
			}
			else {
				Set<Long> removeSet = new HashSet<Long>();
				removeSet.addAll(oldSet);
				removeSet.remove(new Long(21));
				removeRepositoryStatuses(removeSet);
			}
			
		} else {
			// Remove ALL repositories that were deleted
			if(!warnOnly) {
				removeRepositoryStatuses(oldSet);
			}
		}
		
		
		// If the answer to "What specimen type does the data submission pertain to?" 
		// is set to Non-human only or is changed from Human to Non-human only
		// And
		// answer to "What type of access is the data to be made available through?" 
		// is set to Unrestricted only or is changed from Controlled to Unrestricted only.
		if(newSet.contains(new Long(13)) && !newSet.contains(new Long(12)) &&
				newSet.contains(new Long(19)) && !newSet.contains(new Long(18))) {
			// a) The system will delete all DUL(s) created that contains DUL type of 
			//    "Health/Medical/Biomedical", "Disease-specific" and/or "Other". TODO
			if(warnOnly) {
				sb.append("The system will delete all DUL(s) created that contains DUL type of " +
							"Health/Medical/Biomedical, Disease-specific and/or Other. ");
			}
		}
		
		// If answer to "Was this exception approved?" is changed from "Yes" to "No" or "Pending", 
		// remove Exception Memo
		if((newSet.contains(new Long(6)) || newSet.contains(new Long(7)))
				&& oldSet.contains(new Long(5))) {
			if(warnOnly) {
				sb.append("The system will delete the uploaded Exception Memo. ");
			}
			else {
				excepMemoFile = fileUploadService.retrieveFileByDocType("EXCEPMEMO", getProject().getId());
				if(excepMemoFile != null && !excepMemoFile.isEmpty()) {
					setDocId(excepMemoFile.get(0).getId());
					deleteFile();
				}
			}
		}
		
		if(sb.length() > 0) {
			sb.append("Would you like to proceed?");
		}
		inputStream = new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));

		return SUCCESS;
	}


	/**
	 * Remove Repository that were unchecked.
	 * 
	 * @param oldSet 
	 */
	@SuppressWarnings("unused")
	private void removeRepositoryStatuses(Set<Long> oldSet){	

		if(getProject() != null) {		

			// List to hold removed repository status
			List<RepositoryStatus> removedRepositories = new ArrayList<RepositoryStatus>();	
		
			for(RepositoryStatus repository: getProject().getRepositoryStatuses()){			
				// Check if any of the repository has been removed.
				if(oldSet.contains(repository.getPlanQuestionAnswerTByRepositoryId().getId())) { 
					removedRepositories.add(repository);
				}
			}

			getProject().getRepositoryStatuses().removeAll(removedRepositories);
		}
	}
	
	public Map<String, UIList> getMap() {
		return uiControlMap;
	}

	public void setMap(Map<String, UIList> map) {
		this.uiControlMap = map;
	}

	public List<PlanQuestionsAnswer> getAnswerListByQuestionId(Long qid) {
		return PlanQuestionList.getAnswerListByQuestionId(qid);
	}

	public PlanQuestionsAnswer getQuestionById(Long qid) {
		return PlanQuestionList.getQuestionById(qid);
	}

	public PlanQuestionList getQuestionList() {
		return questionList;
	}


	public void setQuestionList(PlanQuestionList questionList) {
		this.questionList = questionList;
	}


	public File getDataSharingPlan() {
		return dataSharingPlan;
	}


	public void setDataSharingPlan(File dataSharingPlan) {
		this.dataSharingPlan = dataSharingPlan;
	}


	public String getDataSharingPlanFileName() {
		return dataSharingPlanFileName;
	}


	public void setDataSharingPlanFileName(String dataSharingPlanFileName) {
		this.dataSharingPlanFileName = dataSharingPlanFileName;
	}


	public String getDataSharingPlanContentType() {
		return dataSharingPlanContentType;
	}


	public void setDataSharingPlanContentType(String dataSharingPlanContentType) {
		this.dataSharingPlanContentType = dataSharingPlanContentType;
	}


	public File getExceptionMemo() {
		return exceptionMemo;
	}


	public void setExceptionMemo(File exceptionMemo) {
		this.exceptionMemo = exceptionMemo;
	}


	public String getExceptionMemoFileName() {
		return exceptionMemoFileName;
	}


	public void setExceptionMemoFileName(String exceptionMemoFileName) {
		this.exceptionMemoFileName = exceptionMemoFileName;
	}


	public String getExceptionMemoContentType() {
		return exceptionMemoContentType;
	}


	public void setExceptionMemoContentType(String exceptionMemoContentType) {
		this.exceptionMemoContentType = exceptionMemoContentType;
	}


	public String getDataSharingPlanEditorText() {
		return dataSharingPlanEditorText;
	}


	public void setDataSharingPlanEditorText(String dataSharingPlanEditorText) {
		this.dataSharingPlanEditorText = dataSharingPlanEditorText;
	}


	public Document getDoc() {
		return doc;
	}


	public void setDoc(Document doc) {
		this.doc = doc;
	}


	public Map<Long, List<String>> getAnswers() {
		return answerMap;
	}


	public void setAnswers(Map<Long, List<String>> answers) {
		this.answerMap = answers;
	}


	public List<Document> getExcepMemoFile() {
		return excepMemoFile;
	}


	public void setExcepMemoFile(List<Document> excepMemoFile) {
		this.excepMemoFile = excepMemoFile;
	}


	public List<Document> getGdsPlanFile() {
		return gdsPlanFile;
	}


	public void setGdsPlanFile(List<Document> gdsPlanFile) {
		this.gdsPlanFile = gdsPlanFile;
	}


	public Map<Long, List<String>> getOtherText() {
		return otherTextMap;
	}


	public void setOtherText(Map<Long, List<String>> otherText) {
		this.otherTextMap = otherText;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}

}

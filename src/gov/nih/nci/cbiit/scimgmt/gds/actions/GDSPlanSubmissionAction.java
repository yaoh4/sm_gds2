package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.constants.PlanQuestionList;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.DulChecklistSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.InstitutionalCertification;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanQuestionsAnswer;
import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;
import gov.nih.nci.cbiit.scimgmt.gds.domain.StudiesDulSet;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Study;
import gov.nih.nci.cbiit.scimgmt.gds.model.UIList;
import gov.nih.nci.cbiit.scimgmt.gds.util.UIRuleUtil;

/**
 * GDS Plan Page Action Class
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
		
        return SUCCESS;
	}

	
	/**
	 * Save Genomic Data Sharing Plan 
	 * Invoked from Genomic Data Sharing Plan Save button.
	 * 
	 * The following will be performed.
	 * 1. Saves user-selected answers for Genomic Data Sharing Plan questions.
	 * 2. If any repository has been removed, need to remove it from the repository list.
	 * 3. Update UI Control based on user-selected answers.
	 * 4. Navigate to Genomic Data Sharing Plan page.
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
				doc = fileUploadService.storeFile(new Long(getProjectId()), ApplicationConstants.DOC_TYPE_GDSPLAN, dataSharingPlanEditorText);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// Call method to remove files and DB objects based on old and new user selection
		warnOnly = false;
		performDataCleanup();

		populateSelectedRemovedSets(false); // Re-populate the new and old set for save.
		
		populatePlanAnswerSelection();

		super.saveProject(getProject(), ApplicationConstants.PAGE_CODE_GDSPLAN);
		
		setProject(retrieveSelectedProject());
		
		setUpPageData();
		
		addActionMessage(getText("project.save.success"));
		
        return SUCCESS;
	}
	
	/**
	 * Validate Save Genomic Data Sharing Plan 
	 * @throws Exception 
	 */
	public void validateSave() throws Exception {
		
		
		if(dataSharingPlan != null  || exceptionMemo != null) {
			this.addActionError(getText("error.doc.fileNotUploaded"));
		}
		logger.debug("Validate save GDS Plan");
		// If Other repository is selected, verify that OtherText is entered.
		if(answerMap.get(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID) != null && answerMap.get(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID).contains(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_OTHER_ID.toString())) {
			if(otherTextMap.get(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_OTHER_ID) == null) {
				this.addActionError(getText("error.other.specify"));
			} else {
				otherTextMap.get(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_OTHER_ID).remove("");
				if(otherTextMap.get(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_OTHER_ID).isEmpty()) {
					otherTextMap.remove(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_OTHER_ID);
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

		if(hasErrors()) {
			setProject(retrieveSelectedProject());
			populateSelectedRemovedSets(false); // Re-populate the new and old set for save.
			populatePlanAnswerSelection();
			uiControlMap = uIRuleUtil.getUiRuleMap(getProject());
		}
		
	}

	/**
	 * Save Genomic Data Sharing Plan and Navigate to IC page or other
	 * pages if tabs are hidden
	 * Invoked from Genomic Data Sharing Plan Save & Next button.
	 * 
	 * @return forward string
	 */
	public String saveAndNext() throws Exception {
		
		save();
		
		if(showPage(ApplicationConstants.PAGE_TYPE_IC))
			return ApplicationConstants.PAGE_TYPE_IC.toLowerCase();
		
		if(showPage(ApplicationConstants.PAGE_TYPE_BSI))
			return ApplicationConstants.PAGE_TYPE_BSI.toLowerCase();
		
		if(showPage(ApplicationConstants.PAGE_TYPE_STATUS))
			return ApplicationConstants.PAGE_TYPE_STATUS.toLowerCase();
		
        return SUCCESS;
	}
	
	/**
	 * Validate Save & Next Genomic Data Sharing Plan 
	 * @throws Exception 
	 */
	public void validateSaveAndNext() throws Exception {
		
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
			doc = fileUploadService.storeFile(new Long(getProjectId()), ApplicationConstants.DOC_TYPE_EXCEPMEMO, exceptionMemo, exceptionMemoFileName);
			excepMemoFile = fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_EXCEPMEMO, new Long(getProjectId()));
			
		} catch (Exception e) {
			try {
				inputStream = new ByteArrayInputStream(getText("error.doc.upload").getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				return INPUT;
			}
			return INPUT;
		}
		
		logger.info("===> docId: " + doc.getId());
		logger.info("===> fileName: " + doc.getFileName());
		logger.info("===> docTitle: " + doc.getDocTitle());
		logger.info("===> uploadDate: " + doc.getUploadedDate());

		return SUCCESS;
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
			doc = fileUploadService.storeFile(new Long(getProjectId()), ApplicationConstants.DOC_TYPE_GDSPLAN, dataSharingPlan, dataSharingPlanFileName);
			gdsPlanFile = fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_GDSPLAN, new Long(getProjectId()));
			
		} catch (Exception e) {
			try {
				inputStream = new ByteArrayInputStream(getText("error.doc.upload").getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				return INPUT;
			}
			return INPUT;
		}
				
		logger.info("===> docId: " + doc.getId());
		logger.info("===> fileName: " + doc.getFileName());
		logger.info("===> docTitle: " + doc.getDocTitle());
		logger.info("===> uploadDate: " + doc.getUploadedDate());

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
						getText("error.doc.id").getBytes("UTF-8"));

				return INPUT;
			}
			fileUploadService.deleteFile(getDocId());
			gdsPlanFile = fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_GDSPLAN, new Long(getProjectId()));
			
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
		
		return (getProject().getSubmissionReasonId().longValue() == ApplicationConstants.SUBMISSION_REASON_GDSPOLICY.longValue() ? true : false);
	}
	
	/**
	 * This method sets up all data for Genomic Data Sharing Plan page.
	 * @throws Exception 
	 */
	private void setUpPageData() throws Exception{

		//Construct answer lists from DB objects
		populateAnswersMap();
		
		// Always reset the file upload or text radio button selection
		if (answerMap.get(ApplicationConstants.PLAN_QUESTION_ANSWER_UPLOAD_OPTION_ID) != null
				&& !answerMap.get(ApplicationConstants.PLAN_QUESTION_ANSWER_UPLOAD_OPTION_ID).isEmpty()) {
			answerMap.get(ApplicationConstants.PLAN_QUESTION_ANSWER_UPLOAD_OPTION_ID).clear();
			answerMap.get(ApplicationConstants.PLAN_QUESTION_ANSWER_UPLOAD_OPTION_ID)
					.add(ApplicationConstants.PLAN_QUESTION_ANSWER_UPLOAD_OPTION_FILE_ID.toString());
		}
		
		//Get the list of files for display
		uiControlMap = uIRuleUtil.getUiRuleMap(getProject());

		excepMemoFile = fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_EXCEPMEMO, getProject().getId());
		gdsPlanFile = fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_GDSPLAN, getProject().getId());
		
		// Set comments
		comments = getProject().getPlanComments();

	}
	
	/**
	 * This method converts the PlanAnswerSelection objects to answers map
	 * @throws Exception 
	 */
	private void populateAnswersMap() {

		List<PlanAnswerSelection> savedList = new LinkedList<PlanAnswerSelection>(
				getProject().getPlanAnswerSelections());

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
				
		for(Long id: oldSet) {
			for (Iterator<PlanAnswerSelection> planAnswerSelectionIterator = getProject().getPlanAnswerSelections().iterator(); planAnswerSelectionIterator.hasNext();) {
				PlanAnswerSelection savedOther = planAnswerSelectionIterator.next();
				if(savedOther.getPlanQuestionsAnswer().getId().longValue() == id.longValue())
					planAnswerSelectionIterator.remove();			
			}
		}
		
		for(Long id: otherSet) {
			for (Iterator<PlanAnswerSelection> planAnswerSelectionIterator = getProject().getPlanAnswerSelections().iterator(); planAnswerSelectionIterator.hasNext();) {
				PlanAnswerSelection savedOther = planAnswerSelectionIterator.next();
				if(savedOther.getPlanQuestionsAnswer().getId().longValue() == id.longValue() &&
						StringUtils.isNotBlank(savedOther.getOtherText())) {
					boolean found = false;
					for(String otherText: otherTextMap.get(id)) {
						if(StringUtils.equals(savedOther.getOtherText(), otherText))
							found = true;
						break;
					}
					if(!found)
						planAnswerSelectionIterator.remove();
				}
			}
		}
		
		PlanAnswerSelection newObject = null;
		for (Long id : newSet) {
			PlanQuestionsAnswer planQuestionsAnswer = PlanQuestionList.getAnswerByAnswerId(id);
			if(planQuestionsAnswer.getDisplayText().equalsIgnoreCase(ApplicationConstants.OTHER) && otherTextMap != null && !otherTextMap.isEmpty()) {
				for(String otherText: otherTextMap.get(id)) {
					newObject = getProject().getPlanAnswerSelectionByAnswerIdAndText(id, otherText);
					if(newObject == null) {
						newObject = new PlanAnswerSelection();
						newObject.setCreatedBy(loggedOnUser.getAdUserId().toUpperCase());
						newObject.setOtherText(otherText);
						newObject.setPlanQuestionsAnswer(planQuestionsAnswer);
						newObject.setProject(getProject());
						getProject().getPlanAnswerSelections().add(newObject);
					}
				}
			} else {
				newObject = getProject().getPlanAnswerSelectionByAnswerId(id);
				if(newObject == null) {
					newObject = new PlanAnswerSelection();
					newObject.setCreatedBy(loggedOnUser.getAdUserId().toUpperCase());
					newObject.setPlanQuestionsAnswer(planQuestionsAnswer);
					newObject.setProject(getProject());
					getProject().getPlanAnswerSelections().add(newObject);
				}
			}
		}

	}
	
	/**
	 * Construct new and old set of answers to be used for warning and
	 * Plan Answer Selection object removal/creation
	 */
	private void populateSelectedRemovedSets(boolean warn) {
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
		for (PlanAnswerSelection e: getProject().getPlanAnswerSelections()) {
			origSet.add(e.getPlanQuestionsAnswer().getId());
		}
		
		oldSet.addAll(origSet);
		if(!warn) {
			oldSet.removeAll(newSet); // deleted set
		
			newSet.removeAll(origSet); // added set
		}
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
		
		populateSelectedRemovedSets(true); // keep original (stored) answers for computing warning message
		
		// If the answer to "Will there be any data submitted?" is changed from Yes to No.
		if(oldSet.contains(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_YES_ID) && newSet.contains(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_NO_ID)) {
			// a) The system will delete the uploaded Data Sharing Plan and the History of Uploaded Documents.
			gdsPlanFile = fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_GDSPLAN, getProject().getId());
			if(warnOnly) {
				if(gdsPlanFile != null && !gdsPlanFile.isEmpty())
					sb.append("Uploaded Data Sharing Plan and History of Uploaded Documents. <br>");
			}
			else {
				for(Document document: gdsPlanFile) {
					setDocId(document.getId());
					deleteFile();
				}
			}

			// b) The system will delete all uploaded Institutional Certifications documents.
			List<Document> icFile = fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_IC, getProject().getId());
			if(warnOnly) {
				if(icFile != null && !icFile.isEmpty())
					sb.append("All uploaded Institutional Certification documents. <br>");
			}
			else {
				for(Document document: icFile) {
					setDocId(document.getId());
					deleteFile();
				}
			}
			
			
			// c) The system will delete all Institutional Certifications and Data Use Limitations.
			if(warnOnly) {
				if(getProject().getInstitutionalCertifications() != null && !getProject().getInstitutionalCertifications().isEmpty())
					sb.append("All Institutional Certifications and Data Use Limitations. <br>");
			}
			else {
				getProject().getInstitutionalCertifications().clear();
			}
			
			// d) The system will delete answers to Has the GPA reviewed the Basic Study Information?
			if(warnOnly) {
				if(StringUtils.isNotBlank(getProject().getBsiReviewedFlag()))
					sb.append("Answer to Has the GPA reviewed the Basic Study Information. <br>");
			}
			else {
				getProject().setBsiReviewedFlag("");
			}
			
			// e) The system will delete the uploaded Basic Study Info and the History of Uploaded Documents.
			List<Document> bsiFile = fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_BSI, getProject().getId());
			if(warnOnly) {
				if(bsiFile != null && !bsiFile.isEmpty())
					sb.append("Uploaded Basic Study Info and the History of Uploaded Documents. <br>");
			}
			else {
				for(Document document: bsiFile) {
					setDocId(document.getId());
					deleteFile();
				}
			}
			
			// f) Remove repositories that were deleted except dbGaP, and add dbGap if it is not there.
			Set<Long> removeSet = new HashSet<Long>();
			removeSet.addAll(oldSet);
			removeSet.remove(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_DBGAP_ID);
			if(warnOnly) {
				if(!removeSet.isEmpty())
					sb.append("Repositories except dbGaP. <br>");
			}
			else {
				removeRepositoryStatuses(removeSet);
				newSet.add(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_DBGAP_ID);
				oldSet.remove(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_DBGAP_ID);
			}
			
		} else if (newSet.contains(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_NO_ID)) {
			// Answer is No, so make sure the dbGap is available.
			if(!warnOnly) {
				newSet.add(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_DBGAP_ID);
				oldSet.remove(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_DBGAP_ID);
			}
		} else {
			// Answer is Yes or not answered. Remove ALL repositories that were deleted
			if(!warnOnly) {
				removeRepositoryStatuses(oldSet);
			}
		}
		
		
		// If the answer to "What specimen type does the data submission pertain to?" 
		// is set to Non-human only or is changed from Human to Non-human only
		// And
		// answer to "What type of access is the data to be made available through?" 
		// is set to Unrestricted only or is changed from Controlled to Unrestricted only.
		if(!oldSet.isEmpty() && newSet.contains(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_NONHUMAN_ID) && !newSet.contains(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_HUMAN_ID) &&
				newSet.contains(ApplicationConstants.PLAN_QUESTION_ANSWER_ACCESS_UNRESTRICTED_ID) && !newSet.contains(ApplicationConstants.PLAN_QUESTION_ANSWER_ACCESS_CONTROLLED_ID)) {
			// a) The system will delete all DUL(s) created that contains DUL type of 
			//    "Health/Medical/Biomedical", "Disease-specific" and/or "Other". 
			if(warnOnly) {
				sb.append("All DUL(s) created that contains DUL type of " +
							"Health/Medical/Biomedical, Disease-specific and/or Other. <br>");
			} else {
				for(InstitutionalCertification ic: getProject().getInstitutionalCertifications()) {
					for(Study study: ic.getStudies()) {
						for(StudiesDulSet dul: study.getStudiesDulSets()) {
							for (Iterator<DulChecklistSelection> dulIterator = dul.getDulChecklistSelections().iterator(); dulIterator.hasNext();) {
								DulChecklistSelection selection = dulIterator.next();
								if(selection.getDulChecklist().getParentDulId() == ApplicationConstants.IC_STUDY_DUL_CHECKLIST_HEALTH_MEDICAL_BIOMEDICAL_ID ||
										selection.getDulChecklist().getParentDulId() == ApplicationConstants.IC_STUDY_DUL_CHECKLIST_DISEASE_SPECIFIC_ID ||
										selection.getDulChecklist().getParentDulId() == ApplicationConstants.IC_STUDY_DUL_CHECKLIST_OTHER_ID){	
									
									dulIterator.remove();
								}
							}
						}
					}
				}
			}
		}
		
		// If answer to "Was this exception approved?" is changed from "Yes" to "No" or "Pending", 
		// remove Exception Memo
		if((newSet.contains(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_NO_ID) || newSet.contains(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_PENDING_ID))
				&& oldSet.contains(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_YES_ID)) {
			excepMemoFile = fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_EXCEPMEMO, getProject().getId());
			if(warnOnly) {
				if(excepMemoFile != null && !excepMemoFile.isEmpty())
					sb.append("Uploaded Exception Memo. <br>");
			}
			else {
				if(excepMemoFile != null && !excepMemoFile.isEmpty()) {
					setDocId(excepMemoFile.get(0).getId());
					deleteFile();
				}
			}
		}
		
		if(sb.length() > 0) {
			sb.append("<br> Do you wish to continue?");
			String warningMessage = getText("gds.warn.message") + "<br><br>" + sb.toString();
			inputStream = new ByteArrayInputStream(warningMessage.getBytes("UTF-8"));
		} else {
			inputStream = new ByteArrayInputStream("".getBytes("UTF-8"));
		}
		
		return SUCCESS;
	}


	/**
	 * Remove Repository that were unchecked.
	 * 
	 * @param oldSet 
	 */
	private void removeRepositoryStatuses(Set<Long> oldSet){	

		if(getProject() != null) {		

			// List to hold removed repository status
			List<RepositoryStatus> removedRepositories = new ArrayList<RepositoryStatus>();	
		
			// Remove the deleted repository from PlanAnswerSelection object
			for (Iterator<PlanAnswerSelection> planAnswerSelectionIterator = getProject().getPlanAnswerSelections().iterator(); planAnswerSelectionIterator.hasNext();) {
				PlanAnswerSelection selection = planAnswerSelectionIterator.next();
				for(RepositoryStatus rep: selection.getRepositoryStatuses()) {
					// Check if any of the repository has been removed.
					if(oldSet.contains(rep.getPlanAnswerSelectionTByRepositoryId().getId())) { 
						removedRepositories.add(rep);
					}
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
	
	public String getPageStatusCode() {
		return super.getPageStatusCode(ApplicationConstants.PAGE_CODE_GDSPLAN);
	}
	
	public String getMissingGdsPlanData() {
		setPage(lookupService.getLookupByCode(ApplicationConstants.PAGE_TYPE, ApplicationConstants.PAGE_CODE_GDSPLAN));
		return SUCCESS;
	}
	
}

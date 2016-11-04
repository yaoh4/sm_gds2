package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
//import org.springframework.util.StringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.constants.PlanQuestionList;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.DulChecklistSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.InstitutionalCertification;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PageStatus;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanQuestionsAnswer;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;
import gov.nih.nci.cbiit.scimgmt.gds.domain.StudiesDulSet;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Study;
import gov.nih.nci.cbiit.scimgmt.gds.model.MissingData;
import gov.nih.nci.cbiit.scimgmt.gds.model.UIList;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsMissingDataUtil;
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
	
	private Document doc = null; // json object to be returned for UI refresh after upload
	
	private String comments;

	private boolean warnOnly = true;
	
	

	
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
		
		setupRepositoryStatuses(getProject());

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
		Map<Long, List<String>> answers = getAnswers();
		Map<Long, List<String>> otherText = getOtherText();
		
		if(answers.get(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID) != null && answers.get(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID).contains(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_OTHER_ID.toString())) {
			if(otherText.get(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_OTHER_ID) == null) {
				this.addActionError(getText("error.other.specify"));
			} else {
				otherText.get(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_OTHER_ID).removeAll(Arrays.asList("", null));;
				if(otherText.get(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_OTHER_ID).isEmpty()) {
					otherText.remove(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_OTHER_ID);
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
	 * Return true if "Why is this project being submitted?" 
	 * is "Required by GDS Policy"
	 */
	public boolean getRequiredByGdsPolicy() {
		
		return (getProject().getSubmissionReasonId().longValue() == ApplicationConstants.SUBMISSION_REASON_GDSPOLICY.longValue() ? true : false);
	}
	
	/**
	 * This method deletes all the Ics on specific conditions in Geonomic Data Sharing Plan
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void deleteIcs() throws Exception {
		List<InstitutionalCertification> icList = getProject().getInstitutionalCertifications();
		if (icList != null){
			InstitutionalCertification icdup = null;
			for(Iterator<InstitutionalCertification> i= getProject().getInstitutionalCertifications().iterator(); i.hasNext();) {
				icdup = i.next();
				manageProjectService.deleteIc(icdup.getId(), retrieveSelectedProject());
				setProject(retrieveSelectedProject());	
				i = getProject().getInstitutionalCertifications().iterator();
			}
		}
	    getProject().setCertificationCompleteFlag(null); 
		super.saveProject(retrieveSelectedProject(), ApplicationConstants.PAGE_CODE_GDSPLAN);
		setProject(retrieveSelectedProject());
	}
	
	
	/**
	 * This method sets up all data for Genomic Data Sharing Plan page.
	 * @throws Exception 
	 */
	private void setUpPageData() throws Exception{

		//Construct answer lists from DB objects
		populateAnswersMap();
		Map<Long, List<String>> answers = getAnswers();
		// Always reset the file upload or text radio button selection
		if (answers.get(ApplicationConstants.PLAN_QUESTION_ANSWER_UPLOAD_OPTION_ID) != null
				&& !answers.get(ApplicationConstants.PLAN_QUESTION_ANSWER_UPLOAD_OPTION_ID).isEmpty()) {
			answers.get(ApplicationConstants.PLAN_QUESTION_ANSWER_UPLOAD_OPTION_ID).clear();
			answers.get(ApplicationConstants.PLAN_QUESTION_ANSWER_UPLOAD_OPTION_ID)
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
			// c) The system will delete all Institutional Certifications and Data Use Limitations.
			if(warnOnly) {
				if(getProject().getInstitutionalCertifications() != null && !getProject().getInstitutionalCertifications().isEmpty())
					sb.append("All Institutional Certifications and Data Use Limitations. <br>");
			} else {
					// Deleting all the ic`s permanently.
				deleteIcs();
			}
			
			// d) The system will delete answers to Has the GPA reviewed the Basic Study Information?
			if(warnOnly) {
				if(getProject().getBsiReviewedId() != null)
					sb.append("Answer to Has the GPA reviewed the Basic Study Information. <br>");
			}
			else {
				getProject().setBsiReviewedId(null);
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
				getProject().setBsiComments("");
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
				if(getProject().getAnticipatedSubmissionDate() != null) {
					getProject().setAnticipatedSubmissionDate(null);
				}
				List<String> repositoryIds = getAnswers().get(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID);
				if(!CollectionUtils.isEmpty(repositoryIds)) {
					getAnswers().get(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID).add(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_DBGAP_ID.toString());
				} else {
					getAnswers().put(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID, Arrays.asList(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_DBGAP_ID.toString()));
				}
			}
		
		} else if (newSet.contains(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_NO_ID)) {
			// Answer is No, so make sure the dbGap is available.
			if(!warnOnly) {
				List<String> repositoryIds = getAnswers().get(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID);
				if(!CollectionUtils.isEmpty(repositoryIds)) {
					getAnswers().get(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID).add(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_DBGAP_ID.toString());
				} else {
					getAnswers().put(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID, Arrays.asList(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_DBGAP_ID.toString()));
				}
			}
		}
		
		
		// If the answer to "What specimen type does the data submission pertain to?" 
		// is set to Human only or is changed from Non-human to Human only
		// And
		// answer to "What type of access is the data to be made available through?" 
		// is set to Unrestricted only or is changed from Controlled to Unrestricted only.
		if(!oldSet.isEmpty() && !(!oldSet.contains(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_NONHUMAN_ID) && oldSet.contains(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_HUMAN_ID) &&
				oldSet.contains(ApplicationConstants.PLAN_QUESTION_ANSWER_ACCESS_UNRESTRICTED_ID) && !oldSet.contains(ApplicationConstants.PLAN_QUESTION_ANSWER_ACCESS_CONTROLLED_ID)) 
				&& !newSet.contains(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_NONHUMAN_ID) && newSet.contains(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_HUMAN_ID) &&
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
		
		// If user selects 'Non-human' only, the system will delete the ICs and not show "Institutional Certifications" page
        if(!oldSet.isEmpty() && !(oldSet.contains(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_NONHUMAN_ID) && !oldSet.contains(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_HUMAN_ID)) 
                && newSet.contains(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_NONHUMAN_ID) && !newSet.contains(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_HUMAN_ID)) {
          if(warnOnly) {
                if(getProject().getInstitutionalCertifications() != null && !getProject().getInstitutionalCertifications().isEmpty())
                       sb.append("All Institutional Certifications and Data Use Limitations. <br>");
          } else {
                // Delete the ICs
        	  deleteIcs();
          }
        }      

		// If answer to "Was this exception approved?" is changed from "Yes" to "No" or "Pending", 
        // OR If answer to "Is there a data sharing exception requested for this project?" is changed from "Yes" to "No"
		// remove Exception Memo
		if(((newSet.contains(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_NO_ID) || newSet.contains(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_PENDING_ID))
				&& oldSet.contains(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_YES_ID)) || 
				(newSet.contains(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_NO_ID) &&
						oldSet.contains(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_YES_ID))) {
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
	
	public Map<String, UIList> getMap() {
		return uiControlMap;
	}

	public void setMap(Map<String, UIList> map) {
		this.uiControlMap = map;
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
	

	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getPageStatusCode() {
		return super.getPageStatusCode(ApplicationConstants.PAGE_CODE_GDSPLAN);
	}
	
	
	public String computePageStatus(Project project) {
		String status = ApplicationConstants.PAGE_STATUS_CODE_COMPLETED;
		logger.debug("come here");
		//No data has been entered
		if(StringUtils.isBlank(project.getPlanComments()) && 
			CollectionUtils.isEmpty(project.getPlanAnswerSelections())) {
			return ApplicationConstants.PAGE_STATUS_CODE_NOT_STARTED;
		}
		
		Long submissionReasonId = project.getSubmissionReasonId();
		List<Document> exceptionMemo = 
			fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_EXCEPMEMO, project.getId());
			
		List<Document> gdsPlan = 
				fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_GDSPLAN, project.getId());
				
		if(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY.equals(submissionReasonId)
				 || ApplicationConstants.SUBMISSION_REASON_GWASPOLICY.equals(submissionReasonId)) {
		
			//Data sharing exception request not indicated, OR Data sharing exception requested  
			//but not yet approved OR data sharing exception approved but memo not loaded
			if(CollectionUtils.isEmpty(project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_ID))
				|| 
				(project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_YES_ID) != null
					&& (project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_PENDING_ID) != null
						|| CollectionUtils.isEmpty(project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_ID))))
				||
				(project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_YES_ID) != null
					&& (CollectionUtils.isEmpty(exceptionMemo) || CollectionUtils.isEmpty(project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_ID)))
						)){
				return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
			}
			
			//Data Sharing Plan not loaded or not reviewed
			if(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY.equals(submissionReasonId)
					&& 
					(project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_NO_ID) != null
					  || project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_NO_ID) != null
					  || project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_YES_ID) != null 
					)) {
				if(CollectionUtils.isEmpty(gdsPlan) || project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_GPA_REVIEWED_YES_ID) == null) {
					return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
				}
			}
				
		}		
		
		//If Exception not requested, or requested but not approved, or requested and approved but still
		//data needs to be submitted
		if(ApplicationConstants.SUBMISSION_REASON_NIHFUND.equals(submissionReasonId)
				 || ApplicationConstants.SUBMISSION_REASON_NONNIHFUND.equals(submissionReasonId)
				 || (project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_NO_ID) != null
			|| 	project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_NO_ID) != null
			||	project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_YES_ID) != null)) {
					
			if(CollectionUtils.isEmpty(project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_ID))
					|| CollectionUtils.isEmpty(project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_TYPE_ID)) 
					|| CollectionUtils.isEmpty(project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_ACCESS_ID)))  {
				
				return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;			
			}
		}		
		
		return status;
	}
	
	
	public String getMissingGdsPlanData() {
		
		setPage(lookupService.getLookupByCode(ApplicationConstants.PAGE_TYPE, 
			ApplicationConstants.PAGE_CODE_GDSPLAN));
		
		Project project = retrieveSelectedProject();
		setMissingDataList(GdsMissingDataUtil.getInstance().getMissingGdsPlanData(project));	
		
			return SUCCESS;
		}
	
}

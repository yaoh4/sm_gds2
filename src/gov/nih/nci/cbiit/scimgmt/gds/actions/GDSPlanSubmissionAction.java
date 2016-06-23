package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.io.File;
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
	
	private Map<Long, String> otherTextMap = new HashMap<Long, String>();
	
	Document doc = null; // json object to be returned for UI refresh after upload
	
	String comments;
	
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
		
		removeRepositoryStatuses();
		
		super.saveProject(getProject());
		
		setUpPageData();
		
        return SUCCESS;
	}
	
	/**
	 * Validate Save Genomic Data Sharing Plan 
	 */
	public void validateSave() {
		
		logger.debug("Validate save GDS Plan");
		// TODO YURI If Other repository is selected, verify that OtherText is entered.
		
		//Comments cannot be greater than 2000 characters.
		if (!StringUtils.isEmpty(comments)) {
			if (comments.length() > ApplicationConstants.COMMENTS_MAX_ALLOWED_SIZE) {
				this.addActionError(getText("error.comments.size.exceeded"));
			}
		}

	}

	/**
	 * Upload Exception Memo Document
	 * 
	 * @return forward string
	 */
	public void uploadExceptionMemo() {
		logger.info("uploadExceptionMemo()");

		try {
			doc = fileUploadService.storeFile(new Long(getProjectId()), "EXCEPMEMO", exceptionMemo, exceptionMemoFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		excepMemoFile = fileUploadService.retrieveFileByDocType("EXCEPMEMO", getProject().getId());
		
		logger.info("===> docId: " + doc.getId());
		logger.info("===> fileName: " + doc.getFileName());
		logger.info("===> docTitle: " + doc.getDocTitle());
		logger.info("===> uploadDate: " + doc.getCreatedDate());

		return;
	}
	
	/**
	 * Validate Upload Exception Memo Document
	 */
	public void validateUploadExceptionMemo() {
		
		logger.debug("Validate Upload Exception Memo Document");

		if (exceptionMemo == null) {
			addActionError("Upload file is required");
		} else if (exceptionMemo.length() == 0) {
			addActionError("Upload file contains no data (length = 0)");
		} else if (exceptionMemo.length() > 5000000) {
			addActionError("Upload file size is larger than maximum file size (5MB)");
		} else if (!"application/pdf".equals(exceptionMemoContentType)
				&& !"application/msword".equals(exceptionMemoContentType)
				&& !"application/vnd.openxmlformats-officedocument.wordprocessingml.document"
						.equals(exceptionMemoContentType)) {
			addActionError("Upload file must be in Word or PDF format");
		}
	}
	
	/**
	 * Upload Genomic Data Sharing Plan Document
	 * 
	 * @return forward string
	 */
	public void uploadDataSharingPlan() {
		logger.info("uploadFile()");
		
		try {
			doc = fileUploadService.storeFile(new Long(getProjectId()), "GDSPLAN", dataSharingPlan,
					dataSharingPlanFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		gdsPlanFile = fileUploadService.retrieveFileByDocType("GDSPLAN", getProject().getId());
		
		logger.info("===> docId: " + doc.getId());
		logger.info("===> fileName: " + doc.getFileName());
		logger.info("===> docTitle: " + doc.getDocTitle());
		logger.info("===> uploadDate: " + doc.getCreatedDate());

		return;
	}
	
	/**
	 * Validate Upload Genomic Data Sharing Plan
	 */
	public void validateUploadDataSharingPlan() {
		
		logger.debug("Validate Upload Genomic Data Sharing Plan Document");
		
		if (dataSharingPlan == null) {
			addActionError("Upload file is required");
		} else if (dataSharingPlan.length() == 0) {
			addActionError("Upload file contains no data (length = 0)");
		} else if (dataSharingPlan.length() > 5000000) {
			addActionError("Upload file size is larger than maximum file size (5MB)");
		} else if (!"application/pdf".equals(dataSharingPlanContentType)
				&& !"application/msword".equals(dataSharingPlanContentType)
				&& !"application/vnd.openxmlformats-officedocument.wordprocessingml.document"
						.equals(dataSharingPlanContentType)) {
			addActionError("Upload file must be in Word or PDF format");
		}
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
		List<String> ansList = new ArrayList<String>();
		for (PlanAnswerSelection e: savedList) {
			Long qId = e.getPlanQuestionsAnswer().getQuestionId();
			Long aId = e.getPlanQuestionsAnswer().getId();
			if(prevId != null && prevId != qId) {
				answerMap.put(prevId, ansList);
				ansList = new ArrayList<String>();
			}
			prevId = qId;
			ansList.add(aId.toString());
			if(StringUtils.isNotBlank(e.getOtherText())) {
				otherTextMap.put(aId, e.getOtherText());
			}
		}
		if(!ansList.isEmpty()) {
			answerMap.put(prevId, ansList);
		}

	}
	
	/**
	 * This method converts the user answers to PlanAnswerSelection objects
	 * @throws Exception 
	 */
	private void populatePlanAnswerSelection() throws Exception{

		Set<Long> newSet = new HashSet<Long>();
		Set<Long> origSet = new HashSet<Long>();
		Set<Long> oldSet = new HashSet<Long>();
		
		for (Entry<Long, List<String>> e : answerMap.entrySet()) {
			for(String entry: e.getValue()) {
				newSet.add(new Long(entry));
			}
		}
		for (PlanAnswerSelection e: getProject().getPlanAnswerSelection()) {
			origSet.add(e.getPlanQuestionsAnswer().getId());
		}
		
		oldSet.addAll(origSet);
		oldSet.removeAll(newSet); // deleted set
		
		newSet.removeAll(origSet); // added set
		
		for(Long id: oldSet) {
			getProject().getPlanAnswerSelection().remove(getProject().getPlanAnswerSelectionByAnswerId(id));
		}
		
		PlanAnswerSelection newObject = null;
		for (Long id : newSet) {
			PlanQuestionsAnswer planQuestionsAnswer = PlanQuestionList.getAnswerByAnswerId(id);
			newObject = new PlanAnswerSelection();
			newObject.setCreatedBy(loggedOnUser.getFullName());
			newObject.setCreatedDate(new Date());
			if(planQuestionsAnswer.getDisplayText().equalsIgnoreCase("Other")) {
				newObject.setOtherText(otherTextMap.get(id));
			}
			newObject.setPlanQuestionsAnswer(planQuestionsAnswer);
			newObject.setProject(getProject());
			getProject().getPlanAnswerSelection().add(newObject);
		}

	}
	
	/**
	 * Remove Repository that were unchecked.
	 */
	@SuppressWarnings("unused")
	private void removeRepositoryStatuses(){	

		if(getProject() != null) {		

			// List to hold removed repository status
			List<RepositoryStatus> removedRepositories = new ArrayList<RepositoryStatus>();	
		
			for(RepositoryStatus repository: getProject().getRepositoryStatuses()){			
				if(false) { // Check if any of the repository has been removed.
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


	public Map<Long, String> getOtherText() {
		return otherTextMap;
	}


	public void setOtherText(Map<Long, String> otherText) {
		this.otherTextMap = otherText;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}
}

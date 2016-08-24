package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;
import gov.nih.nci.cbiit.scimgmt.gds.model.MissingData;
import gov.nih.nci.cbiit.scimgmt.gds.util.DropDownOption;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsMissingDataUtil;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper;
import gov.nih.nci.cbiit.scimgmt.gds.util.RepositoryStatusComparator;

/**
 * This class is responsible for saving Repository statuses.
 * 
 * @author tembharend
 */
@SuppressWarnings("serial")
public class RepositoryStatusSubmissionAction extends ManageSubmission {

	@Autowired
	protected NedPerson loggedOnUser;
	
	private static final Logger logger = LogManager.getLogger(RepositoryStatusSubmissionAction.class);	

	private List<DropDownOption> registrationStatusList = new ArrayList<DropDownOption>();	
	private List<DropDownOption> projectSubmissionStatusList = new ArrayList<DropDownOption>();
	private List<DropDownOption> studyReleasedList = new ArrayList<DropDownOption>();
	
	private String isDbGap = "N";	
	
	private String repoStatusId;

	/**
	 * This method is responsible for loading the Repository status page and setting all the UI elements.
	 * 
	 * @return forward string
	 */
	public String execute() throws Exception { 

		setUpPageData();
		return SUCCESS;
	}
	
	/**
	 * Validates Repository submission save.
	 * @throws Exception 
	 */
	public void validateSave() throws Exception{

		boolean allStudyReleased = true;
		boolean isGdsPlanCompleted = true;
		boolean isInstitutionalCertificationsCompleted = true;
		boolean isBasciStudyCompleted = true;	
		boolean isStatusNotStartedOrInProgress = false;

		for(RepositoryStatus repositoryStatus : getProject().getRepositoryStatuses()){

			//Anticipated submission date validation.
			if((repositoryStatus.getLookupTBySubmissionStatusId().getId() == ApplicationConstants.PROJECT_SUBMISSION_STATUS_INPROGRESS_ID
					|| repositoryStatus.getLookupTBySubmissionStatusId().getId() == ApplicationConstants.PROJECT_SUBMISSION_STATUS_NOTSTARTED_ID)
						||(repositoryStatus.getLookupTByRegistrationStatusId().getId() == ApplicationConstants.REGISTRATION_STATUS_INPROGRESS_ID ||
							repositoryStatus.getLookupTByRegistrationStatusId().getId() == ApplicationConstants.REGISTRATION_STATUS_NOTSTARTED_ID)){
				isStatusNotStartedOrInProgress = true;

			}

			//Comments cannot be greater than 2000 characters.
			if(!StringUtils.isEmpty(repositoryStatus.getComments())) {
				if(repositoryStatus.getComments().length() > ApplicationConstants.COMMENTS_MAX_ALLOWED_SIZE) {
					if(!getActionErrors().contains(getText("error.comments.size.exceeded"))){
						this.addActionError(getText("error.comments.size.exceeded"));  	
					}
				}
			}

			if(repositoryStatus.getLookupTByStudyReleasedId().getId() == ApplicationConstants.PROJECT_STUDY_RELEASED_NO_ID){
				allStudyReleased = false;
			}
		}		
		
		if(getProject().getAnticipatedSubmissionDate() == null && isStatusNotStartedOrInProgress && !isAnticipatedSubDateDisabled()){
			this.addActionError(getText("anticipated.submission.date.required"));
		}
		
		if(getProject().getAnticipatedSubmissionDate() != null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(getProject().getAnticipatedSubmissionDate());
			if(cal.get(Calendar.YEAR) > 9999){
				this.addActionError(getText("error.daterange.year"));
			}
		}

		//The system will validate that the 'Genomic Data Sharing', 'Institutional Certifications' and the 'Basic Study Info' pages are completed, 
		//when the user marks the 'Study Release' on all repositories to 'Yes' and then attempts to save the Submission Status page.
		//Todo:Get the values of isGdsPlanCompleted , isInstitutionalCertificationsCompleted and isBasciStudyCompleted from some service.
		if(allStudyReleased && (!isGdsPlanCompleted || !isInstitutionalCertificationsCompleted || !isBasciStudyCompleted)){	
			this.addActionError(getText("gdsplan.ic.bsi.not.completed.error")); 			
		}

		if(hasActionErrors()){
			populateRepositoriesAfterValidationFailure();
		}
	}

	/**
	 * Saves Project Repository statuses.
	 * 
	 * @return forward string
	 */
	public String save() throws Exception { 

		logger.debug("Saving Submission Repository status.");	
		saveProject();
		addActionMessage(getText("project.save.success"));
		return SUCCESS;
	}

	/**
	 * Validates Repository submission save.
	 * @throws Exception 
	 */
	public void validateSaveAndNext() throws Exception{
		validateSave();
	}
	
	/**
	 * Saves Project Repository statuses and Navigates to next page.
	 * 
	 * @return forward string
	 */
	public String saveAndNext() throws Exception { 

		logger.debug("Saving Submission Repository status and navigating to GDS plan page.");
		saveProject();
		addActionMessage(getText("project.save.success"));
		return SUCCESS;
	}	

	/**
	 * Saves Project.
	 * 
	 * @throws Exception
	 */
	public void saveProject() throws Exception{

		if(StringUtils.isEmpty(getProjectId())) {
			throw new Exception(getText("error.projectid.null"));
		}
		Project storedProject = retrieveSelectedProject();
		Set<PlanAnswerSelection> answers = storedProject.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID);
		HashMap<Long, RepositoryStatus> repoMap = new HashMap<Long, RepositoryStatus>();
		for(PlanAnswerSelection answer: answers) {
			RepositoryStatus storedRepoStatus = new ArrayList<RepositoryStatus>(answer.getRepositoryStatuses()).get(0);
			repoMap.put(storedRepoStatus.getId(), storedRepoStatus);
		}
		
		for(RepositoryStatus repoStatus : getProject().getRepositoryStatuses()){
			RepositoryStatus storedRepoStatus = repoMap.get(repoStatus.getId());
			Long planAnswerSelectionId = storedRepoStatus.getPlanAnswerSelectionTByRepositoryId().getId();
			
				if(!repoStatus.getLookupTByRegistrationStatusId().getId().equals(storedRepoStatus.getLookupTByRegistrationStatusId().getId())
						|| !repoStatus.getLookupTBySubmissionStatusId().getId().equals(storedRepoStatus.getLookupTBySubmissionStatusId().getId())
						|| !repoStatus.getLookupTByStudyReleasedId().getId().equals(storedRepoStatus.getLookupTByStudyReleasedId().getId())) {
					//There is a change to an existing repositoryStatus
					storedProject.getPlanAnswerSelectionById(planAnswerSelectionId).getRepositoryStatuses().remove(storedRepoStatus);
					repoStatus.setLastChangedBy(loggedOnUser.getFullNameLF());
					repoStatus.setCreatedBy(storedRepoStatus.getCreatedBy());
					repoStatus.setCreatedDate(storedRepoStatus.getCreatedDate());
					repoStatus.setProject(storedProject);
					repoStatus.setPlanAnswerSelectionTByRepositoryId(storedProject.getPlanAnswerSelectionById(planAnswerSelectionId));	
					storedProject.getPlanAnswerSelectionById(planAnswerSelectionId).getRepositoryStatuses().add(repoStatus);
				} 
			
		}
		storedProject.setAnticipatedSubmissionDate(getProject().getAnticipatedSubmissionDate());
		super.saveProject(storedProject, ApplicationConstants.PAGE_CODE_REPOSITORY);
		setUpPageData();
	}
	
	/**
	 * This method sets up all data for Repository Status Page..
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private void setUpPageData() throws Exception{

		logger.debug("Setting up Repository status page data.");
		
		if(StringUtils.isEmpty(getProjectId())) {
			throw new Exception(getText("error.projectid.null"));
		}
		setProject(retrieveSelectedProject());
		setUpStatusLists();		
		setUpRepositoryStatuses();	
		Collections.sort(getProject().getRepositoryStatuses(),new RepositoryStatusComparator());
	}

	/**
	 * This method sets up all the status lists for Repository Status Page.
	 */
	private void setUpStatusLists(){

		logger.debug("Setting up Repository status Lists.");
		registrationStatusList =  GdsSubmissionActionHelper.getLookupDropDownList(ApplicationConstants.REGISTRATION_STATUS_LIST.toUpperCase());	
		projectSubmissionStatusList =  GdsSubmissionActionHelper.getLookupDropDownList(ApplicationConstants.PROJECT_SUBMISSION_STATUS_LIST.toUpperCase());	
		studyReleasedList =  GdsSubmissionActionHelper.getLookupDropDownList(ApplicationConstants.STUDY_RELEASED_LIST.toUpperCase());
	}	

	/**
	 * This method sets up Repository Statuses.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private void setUpRepositoryStatuses() {	
		logger.debug("Setting up Repository statuses.");
	
		for(PlanAnswerSelection selection: getProject().getPlanAnswerSelections()) {
			for(RepositoryStatus repositoryStatus : selection.getRepositoryStatuses()){
				getProject().getRepositoryStatuses().add(repositoryStatus);
			}		
		}
		
		if(GdsSubmissionActionHelper.willThereBeAnyDataSubmittedInGdsPlan(getProject())){
			
			setUpSelectdRepositoryStatuses();
		}
		else{
			setUpDbGapRepositoryStatus();
		}			
	}

	/**
	 * If user did not make any selections for Repositories on the GDS plan page then display DbGap repository.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private void setUpDbGapRepositoryStatus() {

		logger.debug("Adding DbGaP Repository status.");	
		PlanAnswerSelection dbGapPlanAnswerSelection = new PlanAnswerSelection();
		for(PlanAnswerSelection planAnswerSelection : getProject().getPlanAnswerSelections()){
			if( ApplicationConstants.PLAN_QUESTION_ANSWER_DBGAP_ID == planAnswerSelection.getPlanQuestionsAnswer().getId()){	
				dbGapPlanAnswerSelection = planAnswerSelection;
				break;
			}
		}
		if(!getSavedRepositoryStatuses().contains(dbGapPlanAnswerSelection.getId())){
			isDbGap = "Y";
			getProject().getRepositoryStatuses().add(createNewRepositoryStatus(true,dbGapPlanAnswerSelection));
		}
	}

	/**
	 * If user made selections for Repositories on the GDS plan page then display all the selected repositories.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private void setUpSelectdRepositoryStatuses() {

		logger.debug("Adding Repository statuses selected on GDS plan.");

		
		//Iterate through selections made on Gds plan page and add Empty repository status objects to Project if new selections are made/ its a new submission.
		for(PlanAnswerSelection planAnswerSelection : getProject().getPlanAnswerSelections()){

			if( ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID == planAnswerSelection.getPlanQuestionsAnswer().getQuestionId()){				

				if(!getSavedRepositoryStatuses().contains(planAnswerSelection.getId())){
					getProject().getRepositoryStatuses().add(createNewRepositoryStatus(false,planAnswerSelection));
				}
			}
		}		
	}
	
	/**
	 * This method returns saved repository statuses ids.
	 * @return
	 */
	private List<Long> getSavedRepositoryStatuses(){

		//List to hold saved repository statuses.
		List<Long> savedRepositoryStatuses = new ArrayList<Long>();	

		for( RepositoryStatus savedRepositoryStatus : getProject().getRepositoryStatuses()){			
			savedRepositoryStatuses.add(savedRepositoryStatus.getPlanAnswerSelectionTByRepositoryId().getId()); 
		}
		
		return savedRepositoryStatuses;
	}
	
	/**
	 * Create a new default repository status.
	 * @param isDbGap
	 * @param planAnswerSelection
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private RepositoryStatus createNewRepositoryStatus(boolean isDbGap, PlanAnswerSelection planAnswerSelection) {
		
		logger.debug("Creating a new repository status.");
		
		RepositoryStatus repositoryStatus = new RepositoryStatus();
		
		//Setting default values.
		repositoryStatus.setLookupTByRegistrationStatusId(lookupService.getLookupByCode(ApplicationConstants.REGISTRATION_STATUS_LIST, ApplicationConstants.NOT_STARTED));
		
		if(isDbGap){
			repositoryStatus.setLookupTBySubmissionStatusId(lookupService.getLookupByCode(ApplicationConstants.PROJECT_SUBMISSION_STATUS_LIST, ApplicationConstants.NOT_APPLICABLE));
		}
		else{
			repositoryStatus.setLookupTBySubmissionStatusId(lookupService.getLookupByCode(ApplicationConstants.PROJECT_SUBMISSION_STATUS_LIST, ApplicationConstants.NOT_STARTED));
		}
		
		repositoryStatus.setLookupTByStudyReleasedId(lookupService.getLookupByCode(ApplicationConstants.STUDY_RELEASED_LIST, ApplicationConstants.NO));	
		repositoryStatus.setPlanAnswerSelectionTByRepositoryId(planAnswerSelection);
		
		return repositoryStatus;		
	}
	
	/**
	 * This method decides if Anticipated submission date should be disabled.
	 * @return
	 */
	public boolean isAnticipatedSubDateDisabled(){
		
		boolean isAnticipatedSubDateDisabled = false; 
		
		//The system will disable the Anticipated Submission Date field if the user answered No to question Will there be any data submitted on the Genomica Data Sharing Plan page.
		if(retrieveSelectedProject().getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_NO_ID) != null){
			isAnticipatedSubDateDisabled = true;
		}
		return isAnticipatedSubDateDisabled;		
	}
	
	/**
	 * When validation fails, copy transient Project's repository statuses to Persistent project's repository statuses.
	 * @throws Exception
	 */
	private void populateRepositoriesAfterValidationFailure() throws Exception{		

		Project transientProject = getProject();
		setUpPageData();
		Project persistentProject = getProject();
		for(int i=0;i<transientProject.getRepositoryStatuses().size();i++){
			persistentProject.getRepositoryStatuses().get(i).setAccessionNumber(transientProject.getRepositoryStatuses().get(i).getAccessionNumber());
			persistentProject.getRepositoryStatuses().get(i).setComments(transientProject.getRepositoryStatuses().get(i).getComments());
			persistentProject.getRepositoryStatuses().get(i).setLookupTBySubmissionStatusId(transientProject.getRepositoryStatuses().get(i).getLookupTBySubmissionStatusId());
			persistentProject.getRepositoryStatuses().get(i).setLookupTByRegistrationStatusId(transientProject.getRepositoryStatuses().get(i).getLookupTByRegistrationStatusId());
			persistentProject.getRepositoryStatuses().get(i).setLookupTByStudyReleasedId(transientProject.getRepositoryStatuses().get(i).getLookupTByStudyReleasedId());				
		}
		setProject(persistentProject);	
	}

	/**
	 * @return the registrationStatusList
	 */
	public List<DropDownOption> getRegistrationStatusList() {
		return registrationStatusList;
	}

	/**
	 * @param registrationStatusList the registrationStatusList to set
	 */
	public void setRegistrationStatusList(List<DropDownOption> registrationStatusList) {
		this.registrationStatusList = registrationStatusList;
	}

	/**
	 * @return the projectSubmissionStatusList
	 */
	public List<DropDownOption> getProjectSubmissionStatusList() {
		return projectSubmissionStatusList;
	}

	/**
	 * @param projectSubmissionStatusList the projectSubmissionStatusList to set
	 */
	public void setProjectSubmissionStatusList(List<DropDownOption> projectSubmissionStatusList) {
		this.projectSubmissionStatusList = projectSubmissionStatusList;
	}

	/**
	 * @return the studyReleasedList
	 */
	public List<DropDownOption> getStudyReleasedList() {
		return studyReleasedList;
	}

	/**
	 * @param studyReleasedList the studyReleasedList to set
	 */
	public void setStudyReleasedList(List<DropDownOption> studyReleasedList) {
		this.studyReleasedList = studyReleasedList;
	}

	//Get Anticipated submission date
	public String getAnticipatedSubmissionDate() {
		if(!isAnticipatedSubDateDisabled()){
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			return format.format(getProject().getAnticipatedSubmissionDate());
		}
		return "";
		
	}

	public String getIsDbGap() {
		return isDbGap;
	}

	public void setIsDbGap(String isDbGap) {
		this.isDbGap = isDbGap;
	}
	
	public String getPageStatusCode() {
		return super.getPageStatusCode(ApplicationConstants.PAGE_CODE_REPOSITORY);
	}
	
	/**
	 * @return the repoStatusId
	 */
	public String getRepoStatusId() {
		return repoStatusId;
	}

	/**
	 * @param repoStatusId the repoStatusId to set
	 */
	public void setRepoStatusId(String repoStatusId) {
		this.repoStatusId = repoStatusId;
	}	
	
	
	
	/**
	 * Invoked during save
	 */
	public String computePageStatus(Project project) {
		String status = ApplicationConstants.PAGE_STATUS_CODE_NOT_STARTED;
		
		
		List<RepositoryStatus> repositoryStatuses = project.getRepositoryStatuses();
		for(RepositoryStatus repoStatus: repositoryStatuses) {
			
			Lookup submissionStatus = repoStatus.getLookupTBySubmissionStatusId();
			Lookup registrationStatus = repoStatus.getLookupTBySubmissionStatusId();
			Lookup studyReleased = repoStatus.getLookupTByStudyReleasedId();
			
			if(ApplicationConstants.REGISTRATION_STATUS_NOTSTARTED_ID.equals(registrationStatus.getId())) {
				//No need to check this repository further, since the submission status
				//and study released fields will be disabled in this case
				if(ApplicationConstants.PAGE_STATUS_CODE_COMPLETED.equals(status)) {
					//If previous repository is in complete status, then we are now
					//in in-progress state
					return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
				}
				continue;
			}
			
			//If we get here, then the page status is either in progress or completed.
			//Check in progress first
			
			//Submission status is not started or in progress, OR Registration 
			//status is not started or in progress, OR study released is No.
			if(ApplicationConstants.REGISTRATION_STATUS_INPROGRESS_ID.equals(registrationStatus.getId())
			||	(ApplicationConstants.PROJECT_SUBMISSION_STATUS_NOTSTARTED_ID.equals(submissionStatus.getId())
				|| ApplicationConstants.PROJECT_SUBMISSION_STATUS_INPROGRESS_ID.equals(submissionStatus.getId())) 
			|| ApplicationConstants.PROJECT_STUDY_RELEASED_NO_ID.equals(studyReleased.getId())) {
				return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
			}
			
			//Neither not started, nor in in-progress status.Hence, completed
			status = ApplicationConstants.PAGE_STATUS_CODE_COMPLETED;
			
		};
		
		if(project.getAnticipatedSubmissionDate() != null &&
				ApplicationConstants.PAGE_STATUS_CODE_NOT_STARTED.equals(status)) {
			return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
		}
		
		return status;
	}
	
	/**
	 * Invoked during display of missing data report for a specific repository.
	 * @return
	 */
	public String getMissingRepositoryData() {
		
		setPage(lookupService.getLookupByCode(ApplicationConstants.PAGE_TYPE, ApplicationConstants.PAGE_CODE_REPOSITORY));
	
		Project project = retrieveSelectedProject();
		setMissingDataList(GdsMissingDataUtil.getInstance().getMissingRepositoryData(project, Long.valueOf(repoStatusId)));
					
		return SUCCESS;
	}	


	/**
	 * Invoked during display of missing data report for all repositories.
	 * @return
	 */
	public String getMissingRepositoryListData() {
		
		setPage(lookupService.getLookupByCode(ApplicationConstants.PAGE_TYPE, ApplicationConstants.PAGE_CODE_REPOSITORY));
		
		Project project = retrieveSelectedProject();
		setMissingDataList(GdsMissingDataUtil.getInstance().getMissingRepositoryListData(project));
			
		return SUCCESS;
	}

	
}

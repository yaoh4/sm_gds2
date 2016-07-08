package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.constants.PlanQuestionList;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanQuestionsAnswer;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;
import gov.nih.nci.cbiit.scimgmt.gds.util.DropDownOption;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper;
import gov.nih.nci.cbiit.scimgmt.gds.util.RepositoryStatusComparator;

/**
 * This class is responsible for saving Repository statuses.
 * 
 * @author tembharend
 */
@SuppressWarnings("serial")
public class RepositoryStatusSubmissionAction extends ManageSubmission {

	private static final Logger logger = LogManager.getLogger(RepositoryStatusSubmissionAction.class);	

	private List<DropDownOption> registrationStatusList = new ArrayList<DropDownOption>();	
	private List<DropDownOption> projectSubmissionStatusList = new ArrayList<DropDownOption>();
	private List<DropDownOption> studyReleasedList = new ArrayList<DropDownOption>();
	
	private String isDbGap = "N";	

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
			if((repositoryStatus.getLookupTByDataSubmissionStatusId().getId() == ApplicationConstants.PROJECT_SUBMISSION_STATUS_INPROGRESS_ID
					|| repositoryStatus.getLookupTByDataSubmissionStatusId().getId() == ApplicationConstants.PROJECT_SUBMISSION_STATUS_NOTSTARTED_ID)
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

		//The system will validate that the 'Genomic Data Sharing', 'Institutional Certifications' and the 'Basic Study Info' pages are completed, 
		//when the user marks the 'Study Release' on all repositories to 'Yes' and then attempts to save the Submission Status page.
		//Todo:Get the values of isGdsPlanCompleted , isInstitutionalCertificationsCompleted and isBasciStudyCompleted from some service.
		if(allStudyReleased && (!isGdsPlanCompleted || !isInstitutionalCertificationsCompleted || !isBasciStudyCompleted)){	
			this.addActionError(getText("gdsplan.ic.bsi.not.completed.error")); 			
		}

		if(hasActionErrors()){
			setUpPageData();
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
		
		Project project = retrieveSelectedProject();
		project.setRepositoryStatuses(getProject().getRepositoryStatuses());	
		
		for(RepositoryStatus repositoryStatus : project.getRepositoryStatuses()){
			repositoryStatus.setProject(project);
			if(repositoryStatus.getId() == null) {
				repositoryStatus.setCreatedBy(loggedOnUser.getAdUserId().toUpperCase());
			} else {
				repositoryStatus.setLastChangedBy(loggedOnUser.getAdUserId().toUpperCase());
			}
			project.getPlanAnswerSelectionById(repositoryStatus.getPlanAnswerSelectionTByRepositoryId().getId()).getRepositoryStatuses().clear();
			project.getPlanAnswerSelectionById(repositoryStatus.getPlanAnswerSelectionTByRepositoryId().getId()).getRepositoryStatuses().add(repositoryStatus);
			project.setAnticipatedSubmissionDate(getProject().getAnticipatedSubmissionDate());
		}

		super.saveProject(project);
		setUpPageData();
		setIsDbGap("N");

	}
	
	/**
	 * This method sets up all data for Repository Status Page..
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public void setUpPageData() throws Exception{

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
	public void setUpStatusLists(){

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
	public void	setUpRepositoryStatuses() {	
		logger.debug("Setting up Repository statuses.");
	
		for(PlanAnswerSelection selection: getProject().getPlanAnswerSelection()) {
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
	public void setUpDbGapRepositoryStatus() {

		logger.debug("Adding DbGaP Repository status.");	
		PlanAnswerSelection dbGapPlanAnswerSelection = new PlanAnswerSelection();
		for(PlanAnswerSelection planAnswerSelection : getProject().getPlanAnswerSelection()){
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
	public void setUpSelectdRepositoryStatuses() {

		logger.debug("Adding Repository statuses selected on GDS plan.");

		
		//Iterate through selections made on Gds plan page and add Empty repository status objects to Project if new selections are made/ its a new submission.
		for(PlanAnswerSelection planAnswerSelection : getProject().getPlanAnswerSelection()){

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
	public RepositoryStatus createNewRepositoryStatus(boolean isDbGap, PlanAnswerSelection planAnswerSelection) {
		
		logger.debug("Creating a new repository status.");
		
		RepositoryStatus repositoryStatus = new RepositoryStatus();
		
		//Setting default values.
		repositoryStatus.setLookupTByRegistrationStatusId(lookupService.getLookupByCode(ApplicationConstants.REGISTRATION_STATUS_LIST, ApplicationConstants.NOT_STARTED));
		
		if(isDbGap){
			repositoryStatus.setLookupTByDataSubmissionStatusId(lookupService.getLookupByCode(ApplicationConstants.PROJECT_SUBMISSION_STATUS_LIST, ApplicationConstants.NOT_APPLICABLE));
		}
		else{
			repositoryStatus.setLookupTByDataSubmissionStatusId(lookupService.getLookupByCode(ApplicationConstants.PROJECT_SUBMISSION_STATUS_LIST, ApplicationConstants.NOT_STARTED));
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
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		return format.format(getProject().getAnticipatedSubmissionDate());
	}

	public String getIsDbGap() {
		return isDbGap;
	}

	public void setIsDbGap(String isDbGap) {
		this.isDbGap = isDbGap;
	}	
}

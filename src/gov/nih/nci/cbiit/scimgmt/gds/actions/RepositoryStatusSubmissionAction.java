package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.constants.PlanQuestionList;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;
import gov.nih.nci.cbiit.scimgmt.gds.util.DropDownOption;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper;

/**
 * This class is responsible for saving Repository statuses.
 * 
 * @author tembharend
 */
@SuppressWarnings("serial")
public class RepositoryStatusSubmissionAction extends ManageSubmission {

	private static final Logger logger = LogManager.getLogger(RepositoryStatusSubmissionAction.class);	

	List<DropDownOption> registrationStatusList = new ArrayList<DropDownOption>();	
	List<DropDownOption> projectSubmissionStatusList = new ArrayList<DropDownOption>();
	List<DropDownOption> studyReleasedList = new ArrayList<DropDownOption>();

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
	 * Saves Project Repository statuses and Navigates to next page.
	 * 
	 * @return forward string
	 */
	public String saveAndNext() throws Exception { 

		logger.debug("Saving Submission Repository status and navigating to GDS plan page.");
		saveProject();
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
			repositoryStatus.setCreatedBy(loggedOnUser.getAdUserId().toUpperCase());
			repositoryStatus.setCreatedDate(new Date());
			repositoryStatus.setProject(project);
		}		
		super.saveProject(project);
		setProject(project);
		setUpStatusLists();	

	}
	
	/**
	 * This method sets up all data for Repository Status Page..
	 * @throws Exception 
	 */
	public void setUpPageData() throws Exception{

		logger.debug("Setting up Repository status page data.");
		
		if(StringUtils.isEmpty(getProjectId())) {
			throw new Exception(getText("error.projectid.null"));
		}
		setProject(retrieveSelectedProject());
		setUpStatusLists();		
		setUpRepositoryStatuses();	
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
	 */
	public void	setUpRepositoryStatuses(){	
		logger.debug("Setting up Repository statuses.");

		if(GdsSubmissionActionHelper.isAnyRepositorySelectedInGdsPlan(getProject())){
			
			setUpSelectdRepositoryStatuses();
		}
		else{
			setUpDbGapRepositoryStatus();
		}			
	}

	/**
	 * If user did not make any selections for Repositories on the GDS plan page then display DbGap repository.
	 */
	public void setUpDbGapRepositoryStatus(){

		logger.debug("Adding DbGaP Repository status.");		
		getProject().getRepositoryStatuses().add(createNewRepositoryStatus(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID, ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_DBGAP_ID));
	}

	/**
	 * If user made selections for Repositories on the GDS plan page then display all the selected repositories.
	 */
	public void setUpSelectdRepositoryStatuses(){

		logger.debug("Adding Repository statuses selected on GDS plan.");

		//List to hold saved repository statuses.
		List<Long> savedRepositoryStatuses = new ArrayList<Long>();	

		for( RepositoryStatus savedRepositoryStatus : getProject().getRepositoryStatuses()){			
			savedRepositoryStatuses.add(savedRepositoryStatus.getPlanQuestionAnswerTByRepositoryId().getId()); 
		}

		//Iterate through selections made on Gds plan page and add Empty repository status objects to Project if new selections are made/ its a new submission.
		for(PlanAnswerSelection planAnswerSelection : getProject().getPlanAnswerSelection()){

			if( ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID == planAnswerSelection.getPlanQuestionsAnswer().getQuestionId()){	

				if(!savedRepositoryStatuses.contains(planAnswerSelection.getPlanQuestionsAnswer().getId())){

					getProject().getRepositoryStatuses().add(createNewRepositoryStatus(planAnswerSelection.getPlanQuestionsAnswer().getQuestionId(),planAnswerSelection.getPlanQuestionsAnswer().getId()));
				}
			}
		}		
	}
	
	/**
	 * Create a new default repository status.
	 * @param questionId
	 * @param id
	 * @return repositoryStatus
	 */
	public RepositoryStatus createNewRepositoryStatus(Long questionId, Long id){
		
		logger.debug("Creating a new repository status.");
		
		RepositoryStatus repositoryStatus = new RepositoryStatus();
		//Setting default values.
		repositoryStatus.setLookupTByRegistrationStatusId(lookupService.getLookupByCode(ApplicationConstants.REGISTRATION_STATUS_LIST, ApplicationConstants.NOT_STARTED));
		repositoryStatus.setLookupTByDataSubmissionStatusId(lookupService.getLookupByCode(ApplicationConstants.PROJECT_SUBMISSION_STATUS_LIST, ApplicationConstants.NOT_STARTED));
		repositoryStatus.setLookupTByStudyReleasedId(lookupService.getLookupByCode(ApplicationConstants.STUDY_RELEASED_LIST, ApplicationConstants.NO));			
		repositoryStatus.setPlanQuestionAnswerTByRepositoryId(PlanQuestionList.getAnswerById(questionId,id));
		
		return repositoryStatus;		
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
}

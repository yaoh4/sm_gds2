package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.constants.PlanQuestionList;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
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
	 * Retrieves Project from DB.
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
		
		super.saveProject(getProject());
		return SUCCESS;
	}
	
	/**
	 * Saves Project Repository statuses and Navigates to next page.
	 * 
	 * @return forward string
	 */
	public String saveAndNext() throws Exception { 
		
		super.saveProject(getProject());
		return SUCCESS;
	}	
	
	/**
	 * Navigates to previous page.
	 * 
	 * @return forward string
	 */
	public String previous() throws Exception {      
		
		return SUCCESS;
	}
		
	/**
	 * This method sets up all data for Repository Status Page..
	 */
	public void setUpPageData(){
		
		setProject(retrieveSelectedProject());
		setUpStatusLists();		
		setUpRepositoryStatuses();	
	}
	
	/**
	 * This method sets up all the status lists for Repository Status Page.
	 */
	public void setUpStatusLists(){
		
		List<Lookup> registrationStatusLookupList = lookupService.getLookupList(ApplicationConstants.PREGISTRATION_STATUS_LIST.toUpperCase());
		GdsSubmissionActionHelper.populateStatusDropDownLists(registrationStatusList,registrationStatusLookupList);

		List<Lookup> projectSubmissionStatusLookupList = lookupService.getLookupList(ApplicationConstants.PROJECT_SUBMISSION_STATUS_LIST.toUpperCase());
		GdsSubmissionActionHelper.populateStatusDropDownLists(projectSubmissionStatusList,projectSubmissionStatusLookupList);

		List<Lookup> studyReleasedLookupList = lookupService.getLookupList(ApplicationConstants.STUDY_RELEASED_LIST.toUpperCase());
		GdsSubmissionActionHelper.populateStatusDropDownLists(studyReleasedList,studyReleasedLookupList);
	}	
	
	/**
	 * This method sets up Repository Statuses.
	 */
	public void	setUpRepositoryStatuses(){	

		//List to hold saved repository statuses.
		List<Long> savedRepositoryStatuses = new ArrayList<Long>();	
		
		for( RepositoryStatus savedRepositoryStatus : getProject().getRepositoryStatuses()){			
			savedRepositoryStatuses.add(savedRepositoryStatus.getPlanQuestionAnswerTByRepositoryId().getId()); 
		}

		//Iterate through selections made on Gds plan page and add Empty repository status objects to Project if new selections are made/ its a new submission..
		for(PlanAnswerSelection planAnswerSelection : getProject().getPlanAnswerSelection()){
			
			if( ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID == planAnswerSelection.getPlanQuestionsAnswer().getQuestionId()){	
				
				if(!savedRepositoryStatuses.contains(planAnswerSelection.getPlanQuestionsAnswer().getId())){
					
					RepositoryStatus repositoryStatus = new RepositoryStatus();
					repositoryStatus.setPlanQuestionAnswerTByRepositoryId(PlanQuestionList.getAnswerById(planAnswerSelection.getPlanQuestionsAnswer().getQuestionId(), planAnswerSelection.getPlanQuestionsAnswer().getId()));;
					getProject().getRepositoryStatuses().add(repositoryStatus);
				}
			}
		}		
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

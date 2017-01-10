package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;
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
	
	private String dataSubmitted = "Y";

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
			if(((repositoryStatus.getLookupTBySubmissionStatusId().getId() == ApplicationConstants.PROJECT_SUBMISSION_STATUS_INPROGRESS_ID
					|| repositoryStatus.getLookupTBySubmissionStatusId().getId() == ApplicationConstants.PROJECT_SUBMISSION_STATUS_NOTSTARTED_ID)
						||(repositoryStatus.getLookupTByRegistrationStatusId().getId() == ApplicationConstants.REGISTRATION_STATUS_INPROGRESS_ID ||
							repositoryStatus.getLookupTByRegistrationStatusId().getId() == ApplicationConstants.REGISTRATION_STATUS_NOTSTARTED_ID)) 
					&& (getProject().getSubprojectFlag().equalsIgnoreCase("N") || getProject().getSubprojectFlag().equalsIgnoreCase("Y") && repositoryStatus.isSelected())){
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
	 * Saves Project with the updated repository statuses
	 * 
	 * @throws Exception
	 */
	public void saveProject() throws Exception{

		if(StringUtils.isEmpty(getProjectId())) {
			throw new Exception(getText("error.projectid.null"));
		}
		Project storedProject = retrieveSelectedProject();
		if(storedProject.getSubprojectFlag().equalsIgnoreCase("Y")) {
			//Add all selected parent repository (Plan Answer selection) to this project if it is already not added
			Project parent = retrieveParentProject(storedProject);
			for(PlanAnswerSelection parentAnswer: parent.getPlanAnswerSelections()) {
				if( ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID.equals(parentAnswer.getPlanQuestionsAnswer().getQuestionId())) {
					boolean found=false, selected = false;
					for(RepositoryStatus repoStatus : getProject().getRepositoryStatuses()) {
						if(repoStatus.getPlanAnswerSelectionTByRepositoryId().getId().longValue() == parentAnswer.getId().longValue()){
							if(repoStatus.isSelected()) {
								selected = true;
								break;
							}	
						}
					}
					RepositoryStatus childRepository = null;
					for(PlanAnswerSelection childAnswer: storedProject.getPlanAnswerSelections()) {
						if(childAnswer.getPlanQuestionsAnswer().getId() == parentAnswer.getPlanQuestionsAnswer().getId() && StringUtils.equals(childAnswer.getOtherText(), parentAnswer.getOtherText())) {
							found = true;
							for (RepositoryStatus r: childAnswer.getRepositoryStatuses()) {
								if(r.getProject().getId().longValue() == storedProject.getId().longValue())
									childRepository = r;
							}
							// If found but not selected, remove subproject from the answer set and also remove repository.
							if(!selected) {
								childAnswer.removeProject(storedProject);
								parentAnswer.getRepositoryStatuses().remove(childRepository);
							}
							break;
						}
					}
					if(!found && selected) {
						parentAnswer.addProject(storedProject);
						storedProject.getPlanAnswerSelections().add(parentAnswer);
					}
					
				}
			}
			
		}

		Set<PlanAnswerSelection> answers = storedProject.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID);
		HashMap<Long, RepositoryStatus> repoMap = new HashMap<Long, RepositoryStatus>();
		for(PlanAnswerSelection answer: answers) {
			if(!answer.getRepositoryStatuses().isEmpty()) {
				for(RepositoryStatus storedRepoStatus : answer.getRepositoryStatuses()) {
					if(storedRepoStatus.getProject().getId() == storedProject.getId())
						repoMap.put(storedRepoStatus.getId(), storedRepoStatus);
				}
			}
		}
		
		for(RepositoryStatus repoStatus : getProject().getRepositoryStatuses()){
		  RepositoryStatus storedRepoStatus = repoMap.get(repoStatus.getId());
		  if(storedProject.getSubprojectFlag().equalsIgnoreCase("N") || repoStatus.isSelected()) {
			if(storedRepoStatus != null) {
				Long planAnswerSelectionId = storedRepoStatus.getPlanAnswerSelectionTByRepositoryId().getId();
			
				if(!repoStatus.getLookupTByRegistrationStatusId().getId().equals(storedRepoStatus.getLookupTByRegistrationStatusId().getId())
						|| !repoStatus.getLookupTBySubmissionStatusId().getId().equals(storedRepoStatus.getLookupTBySubmissionStatusId().getId())
						|| !repoStatus.getLookupTByStudyReleasedId().getId().equals(storedRepoStatus.getLookupTByStudyReleasedId().getId()) ||
						!StringUtils.equals(repoStatus.getAccessionNumber(), storedRepoStatus.getAccessionNumber())) {
					//There is a change to an existing repositoryStatus
					storedProject.getPlanAnswerSelectionById(planAnswerSelectionId).getRepositoryStatuses().remove(storedRepoStatus);
					repoStatus.setLastChangedBy(loggedOnUser.getAdUserId());
					repoStatus.setCreatedBy(storedRepoStatus.getCreatedBy());
					repoStatus.setCreatedDate(storedRepoStatus.getCreatedDate());
					repoStatus.setProject(storedProject);
					storedProject.getPlanAnswerSelectionById(planAnswerSelectionId).getRepositoryStatuses().add(repoStatus);
				} 
			} else {
				Long planAnswerSelectionId = repoStatus.getPlanAnswerSelectionTByRepositoryId().getId();
				repoStatus.setCreatedBy(loggedOnUser.getFullName());
				repoStatus.setCreatedDate(new Date());
				repoStatus.setProject(storedProject);
				storedProject.getPlanAnswerSelectionById(planAnswerSelectionId).getRepositoryStatuses().add(repoStatus);
			}
		  }
		}
		storedProject.setAnticipatedSubmissionDate(getProject().getAnticipatedSubmissionDate());
		//Set the transient repositoryStatuses to enable computation of page status during save
		storedProject.setRepositoryStatuses(getProject().getRepositoryStatuses());
		storedProject.setVersionEligibleFlag(
				GdsSubmissionActionHelper.isProjectEligibleForVersion(storedProject));
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
		if(getProject().getSubprojectFlag().equalsIgnoreCase("Y")) {
			retrieveSubprojectRepositoryStatuses();
		} else {
			retrieveRepositoryStatuses();
		}
		Collections.sort(getProject().getRepositoryStatuses(),new RepositoryStatusComparator());
		if(GdsSubmissionActionHelper.willThereBeAnyDataSubmittedInGdsPlan(getProject())) {
			setDataSubmitted(ApplicationConstants.FLAG_YES);
		} else {
			setDataSubmitted(ApplicationConstants.FLAG_NO);
		}
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
	private void retrieveRepositoryStatuses() {	
		logger.debug("Setting up Repository statuses.");
	
		for(PlanAnswerSelection selection: getProject().getPlanAnswerSelections()) {
			for(RepositoryStatus repositoryStatus : selection.getRepositoryStatuses()){
				if(repositoryStatus.getProject().getId() == getProject().getId())
					getProject().getRepositoryStatuses().add(repositoryStatus);
			}		
		}
	}

	/**
	 * This method sets up Repository Statuses for Subprojects
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private void retrieveSubprojectRepositoryStatuses() throws Exception {	
		logger.debug("Setting up Subproject Repository statuses.");
	

		Project parent = retrieveParentProject();
		for(PlanAnswerSelection parentAnswer: parent.getPlanAnswerSelections()) {
			if( ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID.equals(parentAnswer.getPlanQuestionsAnswer().getQuestionId())) {
				boolean found = false, myAnswer = false;
				for(PlanAnswerSelection childAnswer: getProject().getPlanAnswerSelections()) {
					for (Project p: childAnswer.getProjects()) {
						if(p.getId().longValue() == getProject().getId().longValue()) {
							myAnswer = true;
							break;
						}
					}
					found = false;
					if(myAnswer && childAnswer.getPlanQuestionsAnswer().getId() == parentAnswer.getPlanQuestionsAnswer().getId() && StringUtils.equals(childAnswer.getOtherText(), parentAnswer.getOtherText())) {
						found = true;
						for(RepositoryStatus childRepo: childAnswer.getRepositoryStatuses()) {
							if(childRepo.getProject().getId() == getProject().getId()) {
								childRepo.setSelected(true);
								getProject().getRepositoryStatuses().add(childRepo);
							}
						}
						break;
					}
				}
				if(!found) {
					RepositoryStatus newRepo = createRepositoryStatus(parentAnswer);
					newRepo.setProject(getProject());
					getProject().getRepositoryStatuses().add(newRepo);
				}
			}
		}
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
			persistentProject.getRepositoryStatuses().get(i).setSelected(transientProject.getRepositoryStatuses().get(i).isSelected());
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
	 * @return the dataSubmitted
	 */
	public String getDataSubmitted() {
		return dataSubmitted;
	}

	/**
	 * @param dataSubmitted the dataSubmitted to set
	 */
	public void setDataSubmitted(String dataSubmitted) {
		this.dataSubmitted = dataSubmitted;
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

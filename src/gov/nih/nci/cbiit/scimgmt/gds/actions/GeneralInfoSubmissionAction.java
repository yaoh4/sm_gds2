package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Preparable;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.GdsGrantsContracts;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Organization;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectGrantContract;
import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectsVw;
import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;
import gov.nih.nci.cbiit.scimgmt.gds.services.SearchProjectService;
import gov.nih.nci.cbiit.scimgmt.gds.util.DropDownOption;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper;

/**
 * This class is responsible for saving Project General Information.
 * 
 * @author tembharend
 */
@SuppressWarnings("serial")
public class GeneralInfoSubmissionAction extends ManageSubmission {

	private static final Logger logger = LogManager.getLogger(GeneralInfoSubmissionAction.class);	

	private String preSelectedDOC;
	private String grantContractNum;
	private String selectedTypeOfProject;
	private String applId;
	private String valueSelected;
	private String grantSelection;
	private String searchType;
	private int grantsAdditional;
	

	private List<DropDownOption> docList = new ArrayList<DropDownOption>();
	private List<DropDownOption> progList = new ArrayList<DropDownOption>();
	
	private List<DropDownOption> projectTypes = new ArrayList<DropDownOption>();
	private List<DropDownOption> projectSubmissionReasons = new ArrayList<DropDownOption>();	
	private List<GdsGrantsContracts> grantOrContractList = new ArrayList<GdsGrantsContracts>();	
	private List<ProjectsVw> prevLinkedSubmissions = new ArrayList<ProjectsVw>();
	private GdsGrantsContracts grantOrContract;	
	private List<ProjectGrantContract> secondaryGrantNum =new ArrayList<ProjectGrantContract>();

	

	@Autowired
	protected SearchProjectService searchProjectService;	

	/**
	 * This method is responsible for loading the General Information page and setting all the UI elements.
	 * 
	 * @return forward string
	 */
	public String execute() throws Exception {  

		setUpPageData();
		return SUCCESS;
	}
	
	/**
	 * Validates save Project General Information
	 */
	public void validateSave(){	

		validateGeneralInfoSave();
	}
	
	/**
	 * Saves Project General Information.
	 * 
	 * @return forward string
	 */
	public String save() throws Exception {  	

		logger.debug("Saving Submission General Info.");
		saveProject();
		addActionMessage(getText("project.save.success"));
		setUpPageData();
		return SUCCESS;
	}
	
	/**
	 * Validates save Project General Information
	 */
	public void validateSaveAndNext(){	

		validateGeneralInfoSave();
	}	
	
	
	/**
	 * Saves Project General Information and Navigates to next page.
	 * 
	 * @return forward string
	 */
	public String saveAndNext() throws Exception {
		
		logger.debug("Saving Submission General Info and navigating to GDS plan page.");
		saveProject();
		Project project = retrieveSelectedProject();
		if(!showPage(ApplicationConstants.PAGE_TYPE_GDSPLAN, project)) {
			if(showPage(ApplicationConstants.PAGE_TYPE_IC, project)) {
				return ApplicationConstants.SHOW_IC;
			} else if(showPage(ApplicationConstants.PAGE_TYPE_BSI, project)) {
				return ApplicationConstants.SHOW_BSI;
			}
		}
		
		return SUCCESS;
	}
	
	

	/**
	 * 
	 * @throws Exception
	 */
	public void saveProject() throws Exception{
		
		/*if(grantSelection.equals(ApplicationConstants.GRANT_CONTRACT_TYPE_EXTRAMURAL)
			|| grantSelection.equals(ApplicationConstants.GRANT_CONTRACT_TYPE_BOTH)){
			getProject().addProjectGrantContract(getExtramuralGrant());
		} else {
			getProject().removePrimaryGrant(ApplicationConstants.GRANT_CONTRACT_TYPE_EXTRAMURAL);
		}
		
		if(grantSelection.equals(ApplicationConstants.GRANT_CONTRACT_TYPE_INTRAMURAL)
				|| grantSelection.equals(ApplicationConstants.GRANT_CONTRACT_TYPE_BOTH)){
			getProject().addProjectGrantContract(getIntramuralGrant());
		} else {
			getProject().removePrimaryGrant(ApplicationConstants.GRANT_CONTRACT_TYPE_INTRAMURAL);
		}*/
		
		Project project = retrieveSelectedProject();		
		if(project != null){
			ProjectGrantContract storedExtramuralContract = project.getPrimaryGrant(ApplicationConstants.GRANT_CONTRACT_TYPE_EXTRAMURAL);
			if( storedExtramuralContract != null) {
				if(getProject().getPrimaryGrant(ApplicationConstants.GRANT_CONTRACT_TYPE_EXTRAMURAL) != null) {
					getProject().getPrimaryGrant(ApplicationConstants.GRANT_CONTRACT_TYPE_EXTRAMURAL).setId(storedExtramuralContract.getId());
					getProject().getPrimaryGrant(ApplicationConstants.GRANT_CONTRACT_TYPE_EXTRAMURAL).setCreatedDate(storedExtramuralContract.getCreatedDate());
					
				}
				project.removePrimaryGrant(ApplicationConstants.GRANT_CONTRACT_TYPE_EXTRAMURAL);
			}
			
			ProjectGrantContract storedIntramuralContract = project.getPrimaryGrant(ApplicationConstants.GRANT_CONTRACT_TYPE_INTRAMURAL);
			if( storedIntramuralContract != null) {
				if(getProject().getPrimaryGrant(ApplicationConstants.GRANT_CONTRACT_TYPE_INTRAMURAL) != null) {
					getProject().getPrimaryGrant(ApplicationConstants.GRANT_CONTRACT_TYPE_INTRAMURAL).setId(storedIntramuralContract.getId());
					getProject().getPrimaryGrant(ApplicationConstants.GRANT_CONTRACT_TYPE_INTRAMURAL).setCreatedDate(storedIntramuralContract.getCreatedDate());
				}
				project.removePrimaryGrant(ApplicationConstants.GRANT_CONTRACT_TYPE_INTRAMURAL);
			}
			
			
			performDataCleanup(getProject(),project);
			project = GdsSubmissionActionHelper.popoulateProjectProperties(getProject(),project);		
		}
		else{
			project = getProject();
			// For new Sub project, populate child table from parent
			if(project.getId() == null && project.getParentProjectId() != null) {
				Project parentProject = retrieveParentProject(project);
				project.setSubmissionReasonId(parentProject.getSubmissionReasonId());
				project.setProjectGrantsContracts(parentProject.getProjectGrantsContracts());
				project.setDocAbbreviation(parentProject.getDocAbbreviation());
				project.setProgramBranch(parentProject.getProgramBranch());
			}
		}		
		project = super.saveProject(project, null);
		setProject(project);
		//loadGrantInfo();
		setProjectId(project.getId().toString());
	}

	
	
	/**
	 * Opens Create new submission page.
	 * 
	 * @return forward string
	 */
	public String createNewSubmission() throws Exception {  	
	
		logger.debug("Navigating to create new submission.");
		
		projectTypes = GdsSubmissionActionHelper.getLookupDropDownList(ApplicationConstants.PROJECT_TYPE_LIST.toUpperCase());		
		return SUCCESS;
	}	
	
	/**
	 * Validates saveAndNextSubmissionType
	 */
	public void validateSaveAndNextSubmissionType(){
		
		if(StringUtils.isBlank(getSelectedTypeOfProject())){
			this.addActionError(getText("submission.type.required"));
			projectTypes = GdsSubmissionActionHelper.getLookupDropDownList(ApplicationConstants.PROJECT_TYPE_LIST.toUpperCase());	
		}
	}
	
	/**
	 * This method navigates the user to General info page.
	 * @return
	 */
	public String saveAndNextSubmissionType(){
		
		if(getSelectedTypeOfProject().equals(ApplicationConstants.SUBMISSION_TYPE_NEW_SUBPROJECT) || 
				getSelectedTypeOfProject().equals(ApplicationConstants.SUBMISSION_TYPE_NEW_VERSION_PROJECT) ||
				getSelectedTypeOfProject().equals(ApplicationConstants.SUBMISSION_TYPE_NEW_VERSION_SUBPROJECT)){
			return ApplicationConstants.LINK_TO_PARENT_PAGE;
		}		
		
		return SUCCESS;
	}
	
	/**
	 * This method creates a new subproject and copies Parent project data to it except basic study and submission status.
	 * @return
	 */
	public String createSubproject(){

		logger.debug("Create Sub-project");
		Project subProject = new Project();
		Project parentProject = retrieveSelectedProject();

		try {
			ConvertUtils.register(new LongConverter(null), java.lang.Long.class);          
			BeanUtils.copyProperties(parentProject, subProject);

		} catch (Exception e) {
			logger.error("Error occured while creating a Subproject", e);
		}		
		
		cleanUpSubProject(subProject);
		setProject(subProject);
		loadGrantInfo();
		setUpLists();
		return SUCCESS;
	}
	
	/**
	 * This method creates a new version of an existing Project.
	 * @return
	 */
	public String createNewProjectVersion(){

		logger.debug("Create New Verion of an existing Project");
		
		Project newProject = new Project();
		Project existingProject = retrieveSelectedProject();

		try {
			ConvertUtils.register(new LongConverter(null), java.lang.Long.class);          
			BeanUtils.copyProperties(existingProject, newProject);

		} catch (Exception e) {
			logger.error("Error occured while creating a new version of an existing project", e);
		}		
		
		cleanUpSubProject(newProject);
		setProject(newProject);
		loadGrantInfo();
		setUpLists();
		return SUCCESS;
	}
	
	/**
	 * This method creates a new version of an existing Sub-Project.
	 * @return
	 */
	public String createNewSubprojectVersion(){

		logger.debug("Create New Verions of an existing Sub-Project");
		
		Project newSubproject = new Project();
		Project existingSubproject = retrieveSelectedProject();

		try {
			ConvertUtils.register(new LongConverter(null), java.lang.Long.class);          
			BeanUtils.copyProperties(existingSubproject, newSubproject);

		} catch (Exception e) {
			logger.error("Error creating a new version of an existing Subproject", e);
		}		
		
		cleanUpSubProject(newSubproject);
		setProject(newSubproject);
		loadGrantInfo();
		setUpLists();
		return SUCCESS;
	}
	
	/**
	 * Clean up of sub project.
	 * Remove id, basic study info and repository statuses.
	 */
	private void cleanUpSubProject(Project subProject){
		
		subProject.setParentProjectId(subProject.getId());	
		subProject.setId(null);
		subProject.setBsiComments(null);
		subProject.setBsiReviewedId(null);
		subProject.setRepositoryStatuses(new ArrayList<RepositoryStatus>());
		subProject.setAnticipatedSubmissionDate(null);
		subProject.setPlanComments(null);
		subProject.setPlanAnswerSelections(new HashSet());
										
	}
	
	
	
	/**
	 * This method sets up all data for General Info Page.
	 */
	private void setUpPageData(){
 
		logger.debug("Setting up page data.");
		setUpLists();	
		Project project = retrieveSelectedProject();
		if(project != null){
			setProject(project);	
			loadGrantInfo();
			setGrantSelection(getProject().getGrantSelection());
		}
		else{			
			setProject(new Project());
			//Initially set to unlinked since there is no grant number
			getExtramuralGrant().setDataLinkFlag(ApplicationConstants.FLAG_NO); 
			getExtramuralGrant().setGrantContractType(ApplicationConstants.GRANT_CONTRACT_TYPE_EXTRAMURAL);
			getExtramuralGrant().setPrimaryGrantContractFlag(ApplicationConstants.FLAG_YES);
			getIntramuralGrant().setGrantContractType(ApplicationConstants.GRANT_CONTRACT_TYPE_INTRAMURAL);
			getIntramuralGrant().setPrimaryGrantContractFlag(ApplicationConstants.FLAG_YES);
		}			
	}
	
	/**
	 * This method sets up all the lists for General Info Page.
	 */
	@SuppressWarnings("unchecked")
	private void setUpLists(){
		logger.debug("Setting up page lists.");
		if(docList.isEmpty()){
			
			List<Organization> docListFromDb = (List<Organization>) lookupService.getDocList(ApplicationConstants.DOC_LIST.toUpperCase());
			GdsSubmissionActionHelper.populateDocDropDownList(docList,docListFromDb);
			
			preSelectedDOC = GdsSubmissionActionHelper.getLoggedonUsersDOC(docListFromDb,loggedOnUser.getNihsac());
			if(preSelectedDOC.equalsIgnoreCase("DCEG") || preSelectedDOC.equalsIgnoreCase("CCR")) {
				grantSelection = ApplicationConstants.GRANT_CONTRACT_TYPE_INTRAMURAL;
			} else {
				grantSelection = ApplicationConstants.GRANT_CONTRACT_TYPE_EXTRAMURAL;
			}
			
		}
		
		if(progList.isEmpty()){
				List<String> progListFromDb = manageProjectService.getSubOrgList(preSelectedDOC);
				progList= GdsSubmissionActionHelper.populateProgDropDownList(progList,progListFromDb);
		}
		 
		if(projectSubmissionReasons.isEmpty()){			
			projectSubmissionReasons = GdsSubmissionActionHelper.getLookupDropDownList(ApplicationConstants.PROJECT_SUBMISSION_REASON_LIST.toUpperCase());	
		}		
		
		//If user is editing already saved project then pre-select saved project's DOC in the DOC dropdown list.
		if(getProject() != null && StringUtils.isNotBlank(getProject().getDocAbbreviation())){
			preSelectedDOC = getProject().getDocAbbreviation();
			progList.clear();
			List<String> progListFromDb = manageProjectService.getSubOrgList(preSelectedDOC);
			progList= GdsSubmissionActionHelper.populateProgDropDownList(progList,progListFromDb);			
		}
	}
	
	
	
	/**
	 * If the answer to "Why is the project being submitted?" is changed from:
	 * Required by GDS Policy or Required by GWAS Policy to "Optional Submission NIH Funded" or "Optional Submission non-NIH Funded". 
	 * Then delete answers to some gds plan questions.
	 * @param transientProject
	 * @param persistentProject
	 */
	private void performDataCleanup(Project transientProject, Project persistentProject){

		if(GdsSubmissionActionHelper.isSubmissionUpdated(transientProject, persistentProject)){

			logger.debug("Answer to Why is the project being submitted? has been updated for Submission with id. :"+persistentProject.getId());
			deleteExceptionMemo(persistentProject);
			deletePlanAnswers(persistentProject);
		}		
	}
	
	/**
	 * This method deletes exception memo.
	 * @param persistentProject
	 */
	private void deleteExceptionMemo(Project persistentProject){
		
		//The system will delete the uploaded exception memo
		List<Document> excepMemoFile = fileUploadService.retrieveFileByDocType("EXCEPMEMO", persistentProject.getId());
		if(excepMemoFile != null && !excepMemoFile.isEmpty()) {
			setDocId(excepMemoFile.get(0).getId());
			deleteFile();
			logger.debug("Deleted the uploaded exception memo.");
		}		
	}
	
	/**
	 * The system will delete answers to following questions: 
	 * 1.Is there a data sharing exception requested for this project?
	 * 2.Was this exception approved?
	 * 3.Will there be any data submitted?
	 * @param persistentProject
	 */
	private void deletePlanAnswers(Project persistentProject){

		for (Iterator<PlanAnswerSelection> planAnswerSelectionIterator = persistentProject.getPlanAnswerSelections().iterator(); planAnswerSelectionIterator.hasNext();) {

			PlanAnswerSelection planAnswerSelection = planAnswerSelectionIterator.next();

			if(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_ID == planAnswerSelection.getPlanQuestionsAnswer().getQuestionId()
					|| ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_ID == planAnswerSelection.getPlanQuestionsAnswer().getQuestionId()
					|| ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_ID == planAnswerSelection.getPlanQuestionsAnswer().getQuestionId()){	
				
				planAnswerSelectionIterator.remove();
				logger.debug("Deleted the answer to question with Id: "+planAnswerSelection.getPlanQuestionsAnswer().getQuestionId());
			}
		}
	}
	
	/**
	 * This method serves ajax request coming from onclick of save.
	 * @return
	 * @throws Exception
	 */
	public String isSubmissionUpdated() throws Exception {
		
		//If its a new submission then no need to do comparison between current submission and saved submission. Return control.
		if(StringUtils.isBlank(getProjectId())){
			inputStream = new ByteArrayInputStream("".getBytes("UTF-8"));
			return SUCCESS;
		}
		
		StringBuffer sb = new StringBuffer();
		Project transientProject = getProject();
		Project persistentProject = retrieveSelectedProject();
		
		if(GdsSubmissionActionHelper.isSubmissionUpdated(transientProject, persistentProject)){	
			
			if(isQuestionAnsweredInGdsPlan(persistentProject,ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_ID)
					|| isQuestionAnsweredInGdsPlan(persistentProject,ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_ID)
					|| isQuestionAnsweredInGdsPlan(persistentProject,ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_ID)){
				
				sb.append("The system will delete answers to following questions: <br />");
				if(isQuestionAnsweredInGdsPlan(persistentProject,ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_ID)){
					sb.append("Is there a data sharing exception requested for this project? <br />");
				}
				if(isQuestionAnsweredInGdsPlan(persistentProject,ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_ID)){
					sb.append("Was this exception approved? <br />");
				}
				if(isQuestionAnsweredInGdsPlan(persistentProject,ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_ID)){
					sb.append("Will there be any data submitted? <br />");
				}	
				List<Document> excepMemoFile = fileUploadService.retrieveFileByDocType("EXCEPMEMO", persistentProject.getId());
				if(excepMemoFile != null && !excepMemoFile.isEmpty()) {
					sb.append("And the system will delete the uploaded exception memo. <br />");
				}
			}
		}
		
		if(sb.length() > 0) {
			sb.append("<br> Do you wish to continue?");
			String warningMessage = "<i class=\"fa fa-exclamation-triangle\" aria-hidden=\"true\"></i> " + getText("gds.warn.message") + "<br><br>" + sb.toString();
			inputStream = new ByteArrayInputStream(warningMessage.getBytes("UTF-8"));
		} else {
			inputStream = new ByteArrayInputStream("".getBytes("UTF-8"));
		}
		
		return SUCCESS;
		
	}
	
	/**
	 * This method checks if question with questionId was answered on the GDS plan page.
	 * @param project
	 * @param questionId
	 * @return
	 */
	private boolean isQuestionAnsweredInGdsPlan(Project project, Long questionId){

		logger.debug("Checking if question with questionId was answered on the GDS plan page.");
		
		for(PlanAnswerSelection planAnswerSelection : project.getPlanAnswerSelections()){
			if( questionId == planAnswerSelection.getPlanQuestionsAnswer().getQuestionId()){	
				return true;			
			}
		}
		return false;
	}
	
	/**
	 * Validates Intramural / Grant/ Contract search
	 */
	/*public void validateSearchGrantOrContract(){	
		
		if(StringUtils.isBlank(grantContractNum)){
			this.addActionError(getText("grantContractNum.required")); 
		}
		else if(grantContractNum.length() < ApplicationConstants.GRANT_CONTRACT_NUM_MIN_SIZE){
			this.addActionError(getText("grantContractNum.min.size.error")); 
		}
	}*/	
	
	/**
	 * This method searches Intramural / Grant/ Contract Information
	 * 
	 * @return forward string
	 */
	public String searchGrantOrContract(){

		String applClassCode = null;
		if(ApplicationConstants.SEARCH_TYPE_EXTRAMURAL.equals(getSearchType())) {
			applClassCode = ApplicationConstants.APPL_CLASS_CODE_EXTRAMURAL;
		} else if(ApplicationConstants.SEARCH_TYPE_INTRAMURAL.equals(getGrantSelection())) {
			applClassCode = ApplicationConstants.APPL_CLASS_CODE_INTRAMURAL;
		}
		logger.debug("Searching grants / contracts.");
		grantOrContractList = manageProjectService.getGrantOrContractList(grantContractNum, applClassCode);
		filterSingleQuotes();
		return SUCCESS;
	}
		
	private void filterSingleQuotes() {
		for(GdsGrantsContracts grantOrContract: grantOrContractList) {
			grantOrContract.setProjectTitle(StringUtils.replaceChars(grantOrContract.getProjectTitle(), "'", ""));
			grantOrContract.setProjectTitle(StringUtils.replaceChars(grantOrContract.getProjectTitle(), "\"", ""));
		}
	}

	/**
	 * This method gets Intramural / Grant/ Contract Information based on applId
	 * 
	 * @return forward string
	 */
	public String getGrantOrContractByApplId(){

		logger.debug("Searching grants / contracts using applId: " + applId + ".");
		grantOrContract = manageProjectService.getGrantOrContract(Long.valueOf(applId));
		return SUCCESS;
	}
	
	public String getProgBranchList(){
		progList.clear();
		List<String> progListFromDb = manageProjectService.getSubOrgList(valueSelected);
		progList= GdsSubmissionActionHelper.populateProgDropDownList(progList,progListFromDb);
		return SUCCESS;
	}
	/**
	 * This method retrieves list of already linked submissions for a given grant.
	 * 
	 * @return forward string
	 * @throws Exception
	 */
	public String getPrevLinkedSubmissionsForGrant() throws Exception {
		prevLinkedSubmissions = manageProjectService.getPrevLinkedSubmissionsForGrant(grantContractNum,getProjectId());
		
		return SUCCESS;
	}
	
	/**
	 * Validates save Project General Information
	 */
	public void validateGeneralInfoSave(){	
		
		if(ApplicationConstants.GRANT_CONTRACT_TYPE_EXTRAMURAL.equals(grantSelection)
				|| ApplicationConstants.GRANT_CONTRACT_TYPE_BOTH.equals(grantSelection)) {
			//Retrieve extramural data from UI			
			getProject().setPrimaryGrant(ApplicationConstants.GRANT_CONTRACT_TYPE_EXTRAMURAL, getExtramuralGrant());
			//validate grant data
			validateGrantData(getExtramuralGrant());
			validatePrincipleInvestigator(getExtramuralGrant());
			validatePrimaryContact(getExtramuralGrant());
		}
		
		if(ApplicationConstants.GRANT_CONTRACT_TYPE_INTRAMURAL.equals(grantSelection)
				|| ApplicationConstants.GRANT_CONTRACT_TYPE_BOTH.equals(grantSelection)) {
			//Retrieve intramural grant from UI
			getProject().setPrimaryGrant(ApplicationConstants.GRANT_CONTRACT_TYPE_INTRAMURAL, getIntramuralGrant());
			//validate grant data
			validateGrantData(getIntramuralGrant());
			validatePrincipleInvestigator(getIntramuralGrant());
			validatePrimaryContact(getIntramuralGrant());
		}
		
		getProject().setAssociatedGrants(getAssociatedSecondaryGrants());
		
		validateProjectDetails();
		
		
		
		//If user selected a grant from grantContract search page and then validation failed on general info page while saving
		//then re-populate the grantContract information.
		if(hasActionErrors()){
			if(StringUtils.isNotBlank(getProjectId())){
				getProject().setId(Long.valueOf(getProjectId()));
			}
			
			loadGrantInfo();
			
			setUpLists();
		} 
	}	

	/**
	 * Validates save Project General Information
	 */
	public void validateProjectDetails(){	

		//Validation for Program/ Branch
		if(StringUtils.isBlank(getProject().getSubmissionTitle())){
			this.addActionError(getText("submissionTitle.required")); 
		}
		
		Long submissionReasonId = null;
        
		//Validation for SubmissionReason
		if(getProject().getParentProjectId() == null){
			submissionReasonId = getProject().getSubmissionReasonId();
			if(submissionReasonId == null){
				this.addActionError(getText("submissionReasonId.required")); 
				return;
			}
		} else {
			submissionReasonId = retrieveParentProject().getSubmissionReasonId();
		}
		
		//Exclude non-NIH funded submissions from the below validations
		if(!submissionReasonId.equals(ApplicationConstants.SUBMISSION_REASON_NONNIHFUND)) {
			
			String progBranch="";
			//Validation for Program/ Branch
			if(getProject().getParentProjectId() == null){
				progBranch = getProject().getProgramBranch();
				if(StringUtils.isBlank(progBranch)) {
					this.addActionError(getText("programbranch.required"));  
					return;
				}
			} else {
				progBranch = retrieveParentProject().getProgramBranch();
			}
		}
		//Comments cannot be greater than 2000 characters.
		if(!StringUtils.isBlank(getProject().getComments())) {
			if(getProject().getComments().length() > ApplicationConstants.COMMENTS_MAX_ALLOWED_SIZE) {
				this.addActionError(getText("error.comments.size.exceeded"));  			
			}
		}	
	}
		
		
	private void validateGrantData(ProjectGrantContract projectGrantContract) {
		
		//Validate only if grant is selected and it is not linked
		if(projectGrantContract != null &&
				!StringUtils.isBlank(projectGrantContract.getGrantContractNum()) && 
				!ApplicationConstants.FLAG_YES.equals(projectGrantContract.getDataLinkFlag())){
			//Validation for Title
			if(StringUtils.isBlank(projectGrantContract.getProjectTitle())){
				this.addActionError(getText("projecttitle.required")); 
			}

			//Exclude Intramural also from the below validations
			if(ApplicationConstants.APPL_CLASS_CODE_EXTRAMURAL.equals(projectGrantContract.getApplClassCode())) {
				
				//Validation for PD first name.
				if(StringUtils.isBlank(projectGrantContract.getPdFirstName())){
					this.addActionError(getText("pd.firstname.required")); 
				}

				//Validation for PD last name.
				if(StringUtils.isBlank(projectGrantContract.getPdLastName())){
					this.addActionError(getText("pd.lastname.required")); 
				}

				//Validation for Project start date.
				if(projectGrantContract.getProjectStartDate() == null){
					this.addActionError(getText("project.start.date.required")); 
				}

				//Validation for Project end date.
				if(projectGrantContract.getProjectEndDate() == null){
					this.addActionError(getText("project.end.date.required")); 
				}
	
				if(projectGrantContract.getProjectStartDate() != null 
						&& projectGrantContract.getProjectEndDate() != null) {
					Calendar startCal = new GregorianCalendar();
					startCal.setTime(projectGrantContract.getProjectStartDate());
					Calendar endCal = new GregorianCalendar();
					endCal.setTime(projectGrantContract.getProjectEndDate());
					if(startCal.get(Calendar.YEAR) > 9999 || endCal.get(Calendar.YEAR) > 9999){
						this.addActionError(getText("error.daterange.year"));
					}	
				}
	
				if(projectGrantContract.getProjectStartDate() != null 
					&& projectGrantContract.getProjectEndDate() != null 
					&& projectGrantContract.getProjectStartDate().after(projectGrantContract.getProjectEndDate())){
					this.addActionError(getText("error.daterange.outofsequence"));
				}	
			}
		}
		
	}

	
	/**
	 * Validates Principle investigator information.
	 */
	public void validatePrincipleInvestigator(ProjectGrantContract projectGrantContract){
		
		//If the grant is present and the data link flag is not present, then do the validation
		if(projectGrantContract != null && 
				!ApplicationConstants.FLAG_YES.equals(projectGrantContract.getDataLinkFlag())) {

			//If any piece of PI info is present, look for the others
			if(!StringUtils.isBlank(projectGrantContract.getPiFirstName()) || 
			   !StringUtils.isBlank(projectGrantContract.getPiLastName()) ||  
			   !StringUtils.isBlank(projectGrantContract.getPiEmailAddress()) || 
			   !StringUtils.isBlank(projectGrantContract.getPiInstitution())) {
			
				//Validation for PI first name and last name.
				if(StringUtils.isBlank(projectGrantContract.getPiFirstName())){
					this.addActionError(getText("pi.firstname.required")); 
				}
				if(StringUtils.isBlank(projectGrantContract.getPiLastName())){
					this.addActionError(getText("pi.lastname.required")); 
				}

				//Validation for PI email.
				if(StringUtils.isBlank(projectGrantContract.getPiEmailAddress())){
					this.addActionError(getText("pi.email.required")); 
				}
                   
				//Validation for PI institution.
				if((ApplicationConstants.APPL_CLASS_CODE_EXTRAMURAL).equalsIgnoreCase(
						projectGrantContract.getGrantContractType())) {
					if(StringUtils.isBlank(projectGrantContract.getPiInstitution())){
						this.addActionError(getText("pi.institution.required")); 
					}
				}
			}
		}
	}

	/**
	 * Validates Primary contact information.
	 */
	public void validatePrimaryContact(ProjectGrantContract projectGrantContract){		

		//If the grant is present and the data link flag is not present, then do the validation
		if(projectGrantContract != null && 
				!ApplicationConstants.FLAG_YES.equals(projectGrantContract.getDataLinkFlag())) {
		//Validation for Primary Contact. 
			if(StringUtils.isBlank(projectGrantContract.getPiFirstName()) && 
			   StringUtils.isBlank(projectGrantContract.getPiLastName()) &&
			   StringUtils.isBlank(projectGrantContract.getPiEmailAddress()) &&
			   StringUtils.isBlank(projectGrantContract.getPiInstitution())) {
				if(StringUtils.isBlank(projectGrantContract.getPocFirstName()) && StringUtils.isBlank(projectGrantContract.getPocLastName())){
					this.addActionError(getText("primarycontact.required")); 
				}
				else if(!StringUtils.isBlank(projectGrantContract.getPocFirstName()) && StringUtils.isBlank(projectGrantContract.getPocLastName())){
					this.addActionError(getText("primarycontact.lastname.required")); 
				}
				else if(StringUtils.isBlank(projectGrantContract.getPocFirstName()) && !StringUtils.isBlank(projectGrantContract.getPocLastName())){
					this.addActionError(getText("primarycontact.firstname.required")); 
				}
			  		

				//Validation for Primary contact.
				if(!StringUtils.isBlank(projectGrantContract.getPocFirstName()) && !StringUtils.isBlank(projectGrantContract.getPocLastName())
					&& (StringUtils.isBlank(projectGrantContract.getPocEmailAddress()))){
					this.addActionError(getText("primarycontact.email.required")); 
				}
			}
		}
	}

	/**
	 * @return the docList
	 */
	public List<DropDownOption> getDocList() {
		return docList;
	}

	/**
	 * @param docList the docList to set
	 */
	public void setDocList(List<DropDownOption> docList) {
		this.docList = docList;
	}

	public List<DropDownOption> getProjectTypes() {
		return projectTypes;
	}

	public void setProjectTypes(List<DropDownOption> projectTypes) {
		this.projectTypes = projectTypes;
	}

	/**
	 * @return the intramuralGrantOrContractList
	 */
	public List<GdsGrantsContracts> getGrantOrContractList() {
		return grantOrContractList;
	}

	/**
	 * @param intramuralGrantOrContractList the intramuralGrantOrContractList to set
	 */
	public void setGrantOrContractList(List<GdsGrantsContracts> grantOrContractList) {
		this.grantOrContractList = grantOrContractList;
	}

	/**
	 * @return the preSelectedDOC
	 */
	public String getPreSelectedDOC() {
		return preSelectedDOC;
	}

	/**
	 * @param preSelectedDOC the preSelectedDOC to set
	 */
	public void setPreSelectedDOC(String preSelectedDOC) {
		this.preSelectedDOC = preSelectedDOC;
	}

	/**
	 * @return the grantContractNum
	 */
	public String getGrantContractNum() {
		return grantContractNum;
	}

	/**
	 * @param grantContractNum the grantContractNum to set
	 */
	public void setGrantContractNum(String grantContractNum) {
		this.grantContractNum = grantContractNum;
	}

	/**
	 * @return the projectSubmissionReasons
	 */
	public List<DropDownOption> getProjectSubmissionReasons() {
		return projectSubmissionReasons;
	}

	/**
	 * @param projectSubmissionReasons the projectSubmissionReasons to set
	 */
	public void setProjectSubmissionReasons(List<DropDownOption> projectSubmissionReasons) {
		this.projectSubmissionReasons = projectSubmissionReasons;
	}
	
	/**
	 * @return the selectedTypeOfProject
	 */
	public String getSelectedTypeOfProject() {
		return selectedTypeOfProject;
	}

	/**
	 * @param selectedTypeOfProject the selectedTypeOfProject to set
	 */
	public void setSelectedTypeOfProject(String selectedTypeOfProject) {
		this.selectedTypeOfProject = selectedTypeOfProject;
	}

	/**
	 * @return the applId
	 */
	public String getApplId() {
		return applId;
	}

	/**
	 * @param applId the applId to set
	 */
	public void setApplId(String applId) {
		this.applId = applId;
	}	
	public GdsGrantsContracts getGrantOrContract() {
		return grantOrContract;
	}

	public void setGrantOrContract(GdsGrantsContracts grantOrContract) {
		this.grantOrContract = grantOrContract;
	}

	public List<ProjectsVw> getPrevLinkedSubmissions() {
		return prevLinkedSubmissions;
	}

	public void setPrevLinkedSubmissions(List<ProjectsVw> prevLinkedSubmissions) {
		this.prevLinkedSubmissions = prevLinkedSubmissions;
	}	
	
	public String getValueSelected() {
		return valueSelected;
	}

	public void setValueSelected(String valueSelected) {
		this.valueSelected = valueSelected;
	}

public List<DropDownOption> getProgList() {
		return progList;
	}

	public void setProgList(List<DropDownOption> progList) {
		this.progList = progList;
	}

	public String getGrantSelection() {
		return grantSelection;
	}

	public void setGrantSelection(String grantSelection) {
		this.grantSelection = grantSelection;
	}

	/**
	 * @return the searchType
	 */
	public String getSearchType() {
		return searchType;
	}

	/**
	 * @param searchType the searchType to set
	 */
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	
	public List<ProjectGrantContract> getSecondaryGrantNum() {
		return secondaryGrantNum;
	}

	public void setSecondaryGrantNum(List<ProjectGrantContract> secondaryGrantNum) {
		this.secondaryGrantNum = secondaryGrantNum;
	}
	

	
	public int getGrantsAdditional() {
		return grantsAdditional;
	}

	public void setGrantsAdditional(int grantsAdditional) {
		this.grantsAdditional = grantsAdditional;
	}

}

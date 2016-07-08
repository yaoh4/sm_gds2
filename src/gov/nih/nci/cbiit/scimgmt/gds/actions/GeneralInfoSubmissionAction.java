package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Preparable;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.GdsGrantsContracts;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Organization;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.services.SearchProjectService;
import gov.nih.nci.cbiit.scimgmt.gds.util.DropDownOption;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper;

/**
 * This class is responsible for saving Project General Information.
 * 
 * @author tembharend
 */
@SuppressWarnings("serial")
public class GeneralInfoSubmissionAction extends ManageSubmission implements Preparable{

	private static final Logger logger = LogManager.getLogger(GeneralInfoSubmissionAction.class);	

	private String preSelectedDOC;
	private String grantContractNum;
	private String selectedTypeOfProject;
	private String applId;
	private List<DropDownOption> docList = new ArrayList<DropDownOption>();	
	private List<DropDownOption> projectTypes = new ArrayList<DropDownOption>();
	private List<DropDownOption> projectSubmissionReasons = new ArrayList<DropDownOption>();	
	private List<GdsGrantsContracts> grantOrContractList = new ArrayList<GdsGrantsContracts>();		

	@Autowired
	protected SearchProjectService searchProjectService;	
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

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
		return SUCCESS;
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public void saveProject() throws Exception{
		
		if(StringUtils.isNotBlank(applId)){
			getProject().setApplId(Long.valueOf(applId));
		}
		
		Project project = retrieveSelectedProject();		
		if(project != null){
			performDataCleanup(getProject(),project);
			project = GdsSubmissionActionHelper.popoulateProjectProperties(getProject(),project);		
		}
		else{
			project = getProject();
		}		
		project = super.saveProject(project);
		setProject(project);
		loadGeneralInfoFromGranstContractVw();
		setProjectId(project.getId().toString());
	}

	/**
	 * If validation fails re-populate request scoped form data. 
	 */
	@Override
	public void prepare() throws Exception {		
		setUpLists();	
	}
	
	/**
	 * Opens Grants Contracts Search page.
	 * 
	 * @return forward string
	 */
	public String openGrantsContractsSearch() throws Exception {
		grantContractNum = "";
		return SUCCESS;
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
		
		return SUCCESS;
		
	}
	
	/**
	 * This method sets up all data for General Info Page.
	 */
	public void setUpPageData(){

		logger.debug("Setting up page data.");
		Project project = retrieveSelectedProject();
		if(project != null){
			setProject(project);	
			loadGeneralInfoFromGranstContractVw();
		}
		else{			
			setProject(new Project());
		}
		
		setUpLists();				
	}
	
	/**
	 * This method sets up all the lists for General Info Page.
	 */
	@SuppressWarnings("unchecked")
	public void setUpLists(){
		
		logger.debug("Setting up page lists.");
		if(docList.isEmpty()){
			
			List<Organization> docListFromDb = (List<Organization>) lookupService.getDocList(ApplicationConstants.DOC_LIST.toUpperCase());
			GdsSubmissionActionHelper.populateDocDropDownList(docList,docListFromDb);
			
			preSelectedDOC = GdsSubmissionActionHelper.getLoggedonUsersDOC(docListFromDb,loggedOnUser.getNihsac());	
		}
		
		if(projectSubmissionReasons.isEmpty()){			
			projectSubmissionReasons = GdsSubmissionActionHelper.getLookupDropDownList(ApplicationConstants.PROJECT_SUBMISSION_REASON_LIST.toUpperCase());	
		}		
		
		//If user is editing already saved project then pre-select saved project's DOC in the DOC dropdown list.
		if(getProject() != null && StringUtils.isNotBlank(getProject().getDocAbbreviation())){
			preSelectedDOC = getProject().getDocAbbreviation();
		}
	}
	
	/**
	 * This method retrieves general information data from grantsContractsw if project has grant/contract tied to it.
	 */
	public void loadGeneralInfoFromGranstContractVw(){
		
		Project project = getProject();		
		if(project.getApplId() != null){
			
			logger.debug("Retreiving Project general information data from grantsContractsw for grantContract with applId: "+project.getApplId());
			GdsGrantsContracts grantContract = manageProjectService.getGrantOrContract(project.getApplId());
			if(grantContract != null){
				getProject().setProjectTitle(grantContract.getProjectTitle());
				getProject().setPiFirstName(grantContract.getPiFirstName());
				getProject().setPiLastName(grantContract.getPiLastName());
				getProject().setPiInstitution(grantContract.getPiInstitution());
				getProject().setPiEmailAddress(grantContract.getPiEmailAddress());
				getProject().setPdFirstName(grantContract.getPdFirstName());
				getProject().setPdLastName(grantContract.getPdLastName());
				getProject().setProjectStartDate(grantContract.getProjectPeriodStartDate());
				getProject().setProjectEndDate(grantContract.getProjectPeriodEndDate());
			}
		}
	}	
	
	/**
	 * If the answer to "Why is the project being submitted?" is changed from:
	 * Required by GDS Policy or Required by GWAS Policy to "Optional Submission NIH Funded" or "Optional Submission non-NIH Funded". 
	 * Then delete answers to some gds plan questions.
	 * @param transientProject
	 * @param persistentProject
	 */
	public void performDataCleanup(Project transientProject, Project persistentProject){

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
	public void deleteExceptionMemo(Project persistentProject){
		
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
	public void deletePlanAnswers(Project persistentProject){

		for (Iterator<PlanAnswerSelection> planAnswerSelectionIterator = persistentProject.getPlanAnswerSelection().iterator(); planAnswerSelectionIterator.hasNext();) {

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
		
		StringBuffer sb = new StringBuffer();
		Project transientProject = getProject();
		Project persistentProject = retrieveSelectedProject();
		
		if(GdsSubmissionActionHelper.isSubmissionUpdated(transientProject, persistentProject)){
			sb.append("The system will delete answers to following questions: <br />");
			sb.append("1.Is there a data sharing exception requested for this project? <br />");
			sb.append("2.Was this exception approved? <br />");
			sb.append("3.Will there be any data submitted? <br />");
			sb.append("And the system will delete the uploaded exception memo. <br />");
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
	 * Validates Intramural / Grant/ Contract search
	 */
	public void validateSearchGrantOrContract(){	
		
		if(StringUtils.isEmpty(grantContractNum)){
			this.addActionError(getText("grantContractNum.required")); 
		}
		else if(grantContractNum.length() < ApplicationConstants.GRANT_CONTRACT_NUM_MIN_SIZE){
			this.addActionError(getText("grantContractNum.min.size.error")); 
		}
	}	
	
	/**
	 * This method searches Intramural / Grant/ Contract Information
	 * 
	 * @return forward string
	 */
	public String searchGrantOrContract(){

		logger.debug("Searching grants / contracts.");
		grantOrContractList = manageProjectService.getGrantOrContractList(grantContractNum);
		return SUCCESS;
	}
		
	/**
	 * Validates save Project General Information
	 */
	public void validateGeneralInfoSave(){	
		
		validateProjectDetails();
		validatePrincipleInvestigator();
		validatePrimaryContact();	
		
		//If user selected a grant from grantContract search page and then validation failed on general info page while saving
		//then re-populate the grantContract information.
		if(hasActionErrors()){
			if(StringUtils.isNotBlank(getProjectId())){
				getProject().setId(Long.valueOf(getProjectId()));
			}
			if(StringUtils.isNotBlank(applId)){
				getProject().setApplId(Long.valueOf(applId));
				loadGeneralInfoFromGranstContractVw();
			}
		}
	}	

	/**
	 * Validates save Project General Information
	 */
	public void validateProjectDetails(){	

		//Validation for SubmissionReason
		if(getProject().getSubmissionReasonId() == null){
			this.addActionError(getText("submissionReasonId.required")); 
		}
		
		//Validation for Program/ Branch
		if(StringUtils.isEmpty(getProject().getProgramBranch())){
			this.addActionError(getText("programbranch.required")); 
		}
		
		if(StringUtils.isEmpty(applId)){
			//Validation for Program/ Branch
			if(StringUtils.isEmpty(getProject().getProjectTitle())){
				this.addActionError(getText("projecttitle.required")); 
			}

			//Validation for PD first name.
			if(StringUtils.isEmpty(getProject().getPdFirstName())){
				this.addActionError(getText("pd.firstname.required")); 
			}

			//Validation for PD last name.
			if(StringUtils.isEmpty(getProject().getPdLastName())){
				this.addActionError(getText("pd.lastname.required")); 
			}

			//Validation for Project start date.
			if(getProject().getProjectStartDate() == null){
				this.addActionError(getText("project.start.date.required")); 
			}

			//Validation for Project end date.
			if(getProject().getProjectEndDate() == null){
				this.addActionError(getText("project.end.date.required")); 
			}
			
			if(getProject().getProjectStartDate() != null && getProject().getProjectEndDate() != null && getProject().getProjectStartDate().after(getProject().getProjectEndDate())){
				this.addActionError(getText("error.daterange.outofsequence"));
			}	
		}
		//Comments cannot be greater than 2000 characters.
		if(!StringUtils.isEmpty(getProject().getComments())) {
			if(getProject().getComments().length() > ApplicationConstants.COMMENTS_MAX_ALLOWED_SIZE) {
				this.addActionError(getText("error.comments.size.exceeded"));  			
			}
		}	
	}

	/**
	 * Validates Principle investigator information.
	 */
	public void validatePrincipleInvestigator(){	
		
		if(StringUtils.isEmpty(applId)){

			//Validation for PI first name and last name.
			if(!StringUtils.isEmpty(getProject().getPiFirstName()) && StringUtils.isEmpty(getProject().getPiLastName())){
				this.addActionError(getText("pi.lastname.required")); 
			}
			else if(StringUtils.isEmpty(getProject().getPiFirstName()) && !StringUtils.isEmpty(getProject().getPiLastName())){
				this.addActionError(getText("pi.firstname.required")); 
			}

			//Validation for PI email.
			if(!StringUtils.isEmpty(getProject().getPiFirstName()) && !StringUtils.isEmpty(getProject().getPiLastName())
					&& (StringUtils.isEmpty(getProject().getPiEmailAddress()))){
				this.addActionError(getText("pi.email.required")); 
			}

			//Validation for PI institution.
			if(!StringUtils.isEmpty(getProject().getPiFirstName()) && !StringUtils.isEmpty(getProject().getPiLastName())
					&& (StringUtils.isEmpty(getProject().getPiInstitution()))){
				this.addActionError(getText("pi.institution.required")); 
			}
		}
	}

	/**
	 * Validates Primary contact information.
	 */
	public void validatePrimaryContact(){		

		if(StringUtils.isEmpty(applId)){
			//Validation for Primary Contact. 
			if(StringUtils.isEmpty(getProject().getPiFirstName()) && StringUtils.isEmpty(getProject().getPiLastName())){
				if(StringUtils.isEmpty(getProject().getPocFirstName()) && StringUtils.isEmpty(getProject().getPocLastName())){
					this.addActionError(getText("primarycontact.required")); 
				}
				else if(!StringUtils.isEmpty(getProject().getPocFirstName()) && StringUtils.isEmpty(getProject().getPocLastName())){
					this.addActionError(getText("primarycontact.lastname.required")); 
				}
				else if(StringUtils.isEmpty(getProject().getPocFirstName()) && !StringUtils.isEmpty(getProject().getPocLastName())){
					this.addActionError(getText("primarycontact.firstname.required")); 
				}
			}    		

			//Validation for Primary contact.
			if(!StringUtils.isEmpty(getProject().getPocFirstName()) && !StringUtils.isEmpty(getProject().getPocLastName())
					&& (StringUtils.isEmpty(getProject().getPocEmailAddress()))){
				this.addActionError(getText("primarycontact.email.required")); 
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
	
	//Get project start date
	public String getProjectStartDate() {		
		return dateFormat.format(getProject().getProjectStartDate());
	}

	//Get project end date
	public String getProjectEndDate() {
		return dateFormat.format(getProject().getProjectEndDate());
	}
}

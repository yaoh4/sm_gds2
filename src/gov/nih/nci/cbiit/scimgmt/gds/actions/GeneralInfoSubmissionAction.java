package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Preparable;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.GdsGrantsContracts;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Organization;
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
	private String selectedGrantContract;
	private String selectedTypeOfProject;
	private List<DropDownOption> docList = new ArrayList<DropDownOption>();	
	private List<DropDownOption> projectTypes = new ArrayList<DropDownOption>();
	private List<DropDownOption> projectSubmissionReasons = new ArrayList<DropDownOption>();	
	private List<GdsGrantsContracts> grantOrContractList = new ArrayList<GdsGrantsContracts>();		

	@Autowired
	protected SearchProjectService searchProjectService;

	/**
	 * Retrieves Project from DB or creates a new Project.
	 * 
	 * @return forward string
	 */
	public String execute() throws Exception {  

		setUpPageData();
		return SUCCESS;
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
		
		Project project = retrieveSelectedProject();		
		if(project != null){
			popoulateProjectProperties(getProject(),project);
		}
		else{
			project = getProject();
		}
		project = super.saveProject(project);
		setProject(project);
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
		return SUCCESS;
	}
	
	/**
	 * Opens Create new submission page.
	 * 
	 * @return forward string
	 */
	public String createNewSubmission() throws Exception {  	
	
		logger.debug("Navigating to create new submission.");
		List<Lookup> projectTypeListFromDb =  lookupService.getLookupList(ApplicationConstants.PROJECT_TYPE_LIST.toUpperCase());
		GdsSubmissionActionHelper.populateStatusDropDownLists(projectTypes,projectTypeListFromDb);
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
			
			List<Lookup> projectSubmissionReasonsFromDb =  lookupService.getLookupList(ApplicationConstants.PROJECT_SUBMISSION_REASON_LIST.toUpperCase());
			GdsSubmissionActionHelper.populateStatusDropDownLists(projectSubmissionReasons,projectSubmissionReasonsFromDb);	
		}		
	}
	
	/**
	 * This method copies properties from UI project to DB project object.
	 * @param transientProject
	 * @param persistentProject
	 */
	public void popoulateProjectProperties(Project transientProject, Project persistentProject){
		
		logger.debug("Copying transient project properties to persistent project properties.");
		persistentProject.setSubmissionReasonId(transientProject.getSubmissionReasonId());
		persistentProject.setDocAbbreviation(transientProject.getDocAbbreviation());
		persistentProject.setProgramBranch(transientProject.getProgramBranch());
		persistentProject.setApplicationNum(transientProject.getApplicationNum());
		persistentProject.setProjectTitle(transientProject.getProjectTitle());
		persistentProject.setPiFirstName(transientProject.getPiFirstName());
		persistentProject.setPiLastName(transientProject.getPiLastName());
		persistentProject.setPiEmailAddress(transientProject.getPiEmailAddress());
		persistentProject.setPiInstitution(transientProject.getPiInstitution());
		persistentProject.setPocFirstName(transientProject.getPocFirstName());
		persistentProject.setPocLastName(transientProject.getPocLastName());
		persistentProject.setPocEmailAddress(transientProject.getPocEmailAddress());
		persistentProject.setPdFirstName(transientProject.getPdFirstName());
		persistentProject.setPdLastName(transientProject.getPdLastName());
		persistentProject.setProjectStartDate(transientProject.getProjectStartDate());
		persistentProject.setProjectEndDate(transientProject.getProjectEndDate());
	}
	
	/**
	 * This method searches Intramural / Grant/ Contract Information
	 * 
	 * @return forward string
	 */
	public String searchGrantOrContract(){

		logger.debug("Searching grants / contracts.");
		grantOrContractList = searchProjectService.getGrantOrContractList(grantContractNum);
		return SUCCESS;
	}
		
	/**
	 * Validates save Project General Information
	 */
	public void validateSave(){	

		validateProjectDetails();
		validatePrincipleInvestigator();
		validatePrimaryContact();	
	}
	
	/**
	 * Validates save Project General Information
	 */
	public void validateSaveAndNext(){	

		validateProjectDetails();
		validatePrincipleInvestigator();
		validatePrimaryContact();	
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

	/**
	 * Validates Primary contact information.
	 */
	public void validatePrimaryContact(){		

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
	 * @return the selectedGrantContract
	 */
	public String getSelectedGrantContract() {
		return selectedGrantContract;
	}

	/**
	 * @param selectedGrantContract the selectedGrantContract to set
	 */
	public void setSelectedGrantContract(String selectedGrantContract) {
		this.selectedGrantContract = selectedGrantContract;
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
}

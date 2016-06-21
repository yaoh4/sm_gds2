package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.GdsGrantsContracts;
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
public class GeneralInfoSubmissionAction extends ManageSubmission {

	private static final Logger logger = LogManager.getLogger(GeneralInfoSubmissionAction.class);	

	private String preSelectedDOC;
	private String grantContractNum;
	private List<DropDownOption> docList = new ArrayList<DropDownOption>();	
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

		super.saveProject(getProject());
		return SUCCESS;
	}

	/**
	 * Saves Project General Information and Navigates to next page.
	 * 
	 * @return forward string
	 */
	public String saveAndNext() throws Exception { 

		super.saveProject(getProject());
		return SUCCESS;
	}	

	/**
	 * This method sets up all data for General Info Page.
	 */
	@SuppressWarnings("unchecked")
	public void setUpPageData(){

		Project project = retrieveSelectedProject();
		if(project != null){
			setProject(project);					
		}
		else{			
			setProject(new Project());
		}

		List<Organization> docListFromDb = (List<Organization>) lookupService.getDocList(ApplicationConstants.DOC_LIST.toUpperCase());
		GdsSubmissionActionHelper.populateDocDropDownList(docList,docListFromDb);
		preSelectedDOC = GdsSubmissionActionHelper.getLoggedonUsersDOC(docListFromDb,loggedOnUser.getNihsac());	
	}
	
	/**
	 * This method searches Intramural / Grant/ Contract Information
	 * 
	 * @return forward string
	 */
	public String searchGrantOrContract(){

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
	public void validateProjectDetails(){	

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

	/**
	 * @return the intramuralGrantOrContractList
	 */
	public List<GdsGrantsContracts> getGrantOrContractList() {
		return grantOrContractList;
	}

	/**
	 * @param intramuralGrantOrContractList the intramuralGrantOrContractList to set
	 */
	public void setGrantOrContractList(List<GdsGrantsContracts> intramuralGrantOrContractList) {
		this.grantOrContractList = grantOrContractList;
	}

	public String getPreSelectedDOC() {
		return preSelectedDOC;
	}

	public void setPreSelectedDOC(String preSelectedDOC) {
		this.preSelectedDOC = preSelectedDOC;
	}

	public String getGrantContractNum() {
		return grantContractNum;
	}

	public void setGrantContractNum(String grantContractNum) {
		this.grantContractNum = grantContractNum;
	}
}

package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.services.ManageProjectService;
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

	List<DropDownOption> docList = new ArrayList<DropDownOption>();	
	List<Object> intramuralGrantOrContractList = new ArrayList<Object>();	
	
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
		
		List<Object> docListFromDb = (List<Object>) lookupService.getDocList(ApplicationConstants.DOC_LIST.toUpperCase());
		GdsSubmissionActionHelper.populateDocDropDownList(docList,docListFromDb);
	}
		
	/**
	 * This method searches Intramural / Grant/ Contract Information
	 * 
	 * @return forward string
	 */
	public String searchIntramuralGrantOrContract(){
		
		intramuralGrantOrContractList = searchProjectService.getIntramuralGrantOrContractList();
		return SUCCESS;
	}
	
	/**
	 * Validates save Project General Information
	 */
	public void validateSave(){
		
		//Comments cannot be greater than 2000 characters.
    	if(!StringUtils.isEmpty(getProject().getComments())) {
    		if(getProject().getComments().length() > ApplicationConstants.COMMENTS_MAX_ALLOWED_SIZE) {
    			this.addActionError(getText("error.comments.size.exceeded"));  			
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

	/**
	 * @return the intramuralGrantOrContractList
	 */
	public List<Object> getIntramuralGrantOrContractList() {
		return intramuralGrantOrContractList;
	}

	/**
	 * @param intramuralGrantOrContractList the intramuralGrantOrContractList to set
	 */
	public void setIntramuralGrantOrContractList(List<Object> intramuralGrantOrContractList) {
		this.intramuralGrantOrContractList = intramuralGrantOrContractList;
	}
}

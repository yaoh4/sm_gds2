package gov.nih.nci.cbiit.scimgmt.gds.actions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.services.ManageProjectService;

/**
 * Manages Submission creation, updates and deletion.
 * 
 * @author tembharend
 *
 */
@SuppressWarnings("serial")
public class ManageSubmission extends BaseAction {
	
	static Logger logger = LogManager.getLogger(ManageSubmission.class);
	
	@Autowired
	protected ManageProjectService manageProjectService;
	
	private Project project;
	
	/**
	 * Execute method, for now used for navigation
	 * 
	 * @return forward string
	 */
	public String execute() throws Exception {
		logger.debug("execute");
        
        return SUCCESS;
	}
	
	

	/**
	 * Save General Information
	 * 
	 * @return forward string
	 */
	public String saveGeneralInfo() throws Exception {
		logger.debug("saveGeneralInfo");
        
        return SUCCESS;
	}
	
	/**
	 * Validate Save General Info
	 */
	public void validateSaveGeneralInfo() {
		logger.debug("validateSaveGeneralInfo");
	}

	/**
	 * Save Genomic Data Sharing Plan
	 * 
	 * @return forward string
	 */
	public String saveGdsPlan() throws Exception {
		logger.debug("saveGdsPlan");
        
        return SUCCESS;
	}
	
	/**
	 * Validate Save GDS Plan
	 */
	public void validateSaveGdsPlan() {
		logger.debug("validateSaveGdsPlan");
	}
	
	/**
	 * Save Institutional Certificate
	 * 
	 * @return forward string
	 */
	public String saveIcAndDul() throws Exception {
		logger.debug("saveIcAndDul");
        
        return SUCCESS;
	}
	
	/**
	 * Validate Save Institutional Certificate
	 */
	public void validateSaveIcAndDul() {
		logger.debug("validateSaveIcAndDul");
	}
	
	/**
	 * Save Basic Study Info
	 * 
	 * @return forward string
	 */
	public String saveBasicStudyInfo() throws Exception {
		logger.debug("saveBasicStudyInfo");
        
        return SUCCESS;
	}
	
	/**
	 * Validate Save Basic Study Info
	 */
	public void validateSaveBasicStudyInfo() {
		logger.debug("validateSaveBasicStudyInfos");
	}
	
	/**
	 * Delete Institutional Certificate
	 * 
	 * @return forward string
	 */
	public String deleteIcAndDul() throws Exception {
		logger.debug("deleteIcAndDul");
        
        return SUCCESS;
	}
	
	/**
	 * Add Study to Institutional Certificate
	 * 
	 * @return forward string
	 */
	public String addStudy() throws Exception {
		logger.debug("addStudy");
        
        return SUCCESS;
	}
	
	/**
	 * Delete Study from Institutional Certificate
	 * 
	 * @return forward string
	 */
	public String deleteStudy() throws Exception {
		logger.debug("deleteStudy");
        
        return SUCCESS;
	}
	
	/**
	 * Add Data Use Limitation to Study
	 * 
	 * @return forward string
	 */
	public String addDul() throws Exception {
		logger.debug("addDul");
        
        return SUCCESS;
	}
	
	/**
	 * Delete Data Use Limitation from Study
	 * 
	 * @return forward string
	 */
	public String deleteDul() throws Exception {
		logger.debug("deleteDul");
        
        return SUCCESS;
	}



	/**
	 * @return the selectedProject
	 */
	public Project getProject() {
		return project;
	}



	/**
	 * @param selectedProject the selectedProject to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}
	
	
	/**
	 * Retrieve the project based on the projectId indicated in the request
	 * 
	 * @return
	 */
	public Project retrieveSelectedProject() {
	
		String projectId  = getProjectId();
		if(projectId != null) {
			return manageProjectService.findById(Long.valueOf(projectId));
		} 
		
		return null;
	
	}
	
	
	/**
	 * Save the project
	 */
	public void saveProject(Project project) {
		manageProjectService.saveOrUpdate(project);
		
	}
	 
	
}

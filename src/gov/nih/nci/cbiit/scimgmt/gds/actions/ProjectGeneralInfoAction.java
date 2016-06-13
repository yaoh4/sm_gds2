package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.services.SearchProjectService;

/**
 * This class is responsible for saving Project General Information.
 * @author tembharend
 */
@SuppressWarnings("serial")
public class ProjectGeneralInfoAction extends BaseAction {
	
	private static final Logger logger = LogManager.getLogger(ProjectGeneralInfoAction.class);
	
	private Long projectId;
	
	private Project project;
	
	@Autowired
	private SearchProjectService searchProjectService;
	
	/**
	 * Retrieves Project from DB.
	 * @return forward string
	 */
	public String execute() throws Exception {      
		project = searchProjectService.findProjectById(projectId);
        return SUCCESS;
	}
	
	/**
	 * Saves Project General Information.
	 * @return forward string
	 */
	public String save() throws Exception {      
        
        return SUCCESS;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
}

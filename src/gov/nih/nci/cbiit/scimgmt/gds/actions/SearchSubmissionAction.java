package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import gov.nih.nci.cbiit.scimgmt.gds.services.SearchProjectService;

/**
 * This class is responsible for searching Projects.
 * @author tembharend
 */
@SuppressWarnings("serial")
public class SearchSubmissionAction extends BaseAction {

	private static final Logger logger = LogManager.getLogger(SearchSubmissionAction.class);

	@Autowired
	private SearchProjectService searchProjectService;

	/**
	 * Search Project.
	 * @return forward string
	 */
	public String execute() throws Exception {      

		return SUCCESS;
	}

	/**
	 * Get all project ids.
	 * @return List
	 */
	public List<Long> getAllProjectIds(){
		return searchProjectService.getAllProjectIds();
	}
}

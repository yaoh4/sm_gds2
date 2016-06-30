package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import gov.nih.nci.cbiit.scimgmt.gds.model.SubmissionSearchCriteria;
import gov.nih.nci.cbiit.scimgmt.gds.services.SearchProjectService;

/**
 * This class is responsible for searching Projects.
 * @author tembharend, dinhys
 */
@SuppressWarnings("serial")
public class SearchSubmissionAction extends BaseAction {

	private static final Logger logger = LogManager.getLogger(SearchSubmissionAction.class);

	@Autowired
	private SearchProjectService searchProjectService;

	private SubmissionSearchCriteria criteria;
	
	/**
	 * Navigate to Search Project.
	 * @return forward string
	 */
	public String execute() throws Exception {      

		return SUCCESS;
	}

	/** 
	 * Search Project.
	 * @return forward string
	 */
	public String search() throws Exception {      

		return SUCCESS;
	}
	
	/** 
	 * Validate Search Project.
	 * @return forward string
	 */
	public String validateSearch() throws Exception {      

		if(criteria.isBlank()) {
			addActionError("Please provide search criteria");
		}
		return SUCCESS;
	}
	
	/**
	 * Get all project ids.
	 * @return List
	 */
	public List<Long> getAllProjectIds(){
		return searchProjectService.getAllProjectIds();
	}

	public SubmissionSearchCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(SubmissionSearchCriteria criteria) {
		this.criteria = criteria;
	}
}

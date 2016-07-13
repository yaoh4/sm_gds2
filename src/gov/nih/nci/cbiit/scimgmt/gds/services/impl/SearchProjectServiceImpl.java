package gov.nih.nci.cbiit.scimgmt.gds.services.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.nih.nci.cbiit.scimgmt.gds.dao.ProjectSearchDao;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.model.SubmissionSearchCriteria;
import gov.nih.nci.cbiit.scimgmt.gds.model.SubmissionSearchResult;
import gov.nih.nci.cbiit.scimgmt.gds.services.SearchProjectService;

/**
 * Class to support Project searches from the database.
 */
@Component
public class SearchProjectServiceImpl implements SearchProjectService {
	
	private static final Logger logger = LogManager.getLogger(SearchProjectServiceImpl.class);
	
	@Autowired
	private ProjectSearchDao projectSearchDAO;
	
	/**
	 * This method returns all Project Ids
	 * @return List
	 */
	public List<Long> getAllProjectIds(){
		return projectSearchDAO.getAllProjectIds();
	}
	
	/**
	 * Search Project Submission based on Criteria.
	 * @param criteria
	 * @return List<Project>
	 */
	public SubmissionSearchResult search(SubmissionSearchCriteria criteria) {
		
		logger.debug("search");
		
		return projectSearchDAO.search(criteria);
	}

	/**
	 * Retrieve Sub-projects based on parent project ID.
	 * @param parentProjectId
	 * @return List<Project>
	 */
	public List<Project> getSubprojects(Long parentProjectId) {
		logger.debug("getSubprojects");
		return projectSearchDAO.getSubprojects(parentProjectId);
		
	}
	
}

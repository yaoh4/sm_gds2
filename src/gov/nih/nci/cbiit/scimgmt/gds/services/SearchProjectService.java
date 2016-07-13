package gov.nih.nci.cbiit.scimgmt.gds.services;

import java.util.List;

import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.model.SubmissionSearchCriteria;
import gov.nih.nci.cbiit.scimgmt.gds.model.SubmissionSearchResult;

public interface SearchProjectService {

	/**
	 * This method returns all Project Ids.
	 * @return List
	 */
	public List<Long> getAllProjectIds();
	
	/**
	 * Search Project Submission based on Criteria.
	 * @param criteria
	 * @return SubmissionSearchResult
	 */
	public SubmissionSearchResult search(SubmissionSearchCriteria criteria);

	/**
	 * Retrieve Sub-projects based on parent project ID.
	 * @param parentProjectId
	 * @return List<Project>
	 */
	public List<Project> getSubprojects(Long parentProjectId);
	
}

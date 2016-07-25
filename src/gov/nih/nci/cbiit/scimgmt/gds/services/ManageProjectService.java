package gov.nih.nci.cbiit.scimgmt.gds.services;

import java.util.List;

import gov.nih.nci.cbiit.scimgmt.gds.domain.GdsGrantsContracts;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectsVw;

public interface ManageProjectService {

	/**
	 * Inserts or Updates the Project
	 * 
	 * @param project
	 * @return saved Project
	 */
	public Project saveOrUpdate(Project project);

	/**
	 * Deletes the Project given an ID
	 * 
	 * @param projectId
	 */
	public void delete(Long projectId);

	/**
	 * Retrieve Project given an ID
	 * 
	 * @param projectId
	 * @return Project
	 */
	public Project findById(Long projectId);

	/**
	 * This method retrieves Intramural / Grant / Contract List
	 * @return
	 */
	public List<GdsGrantsContracts> getGrantOrContractList(String grantContractNum);
	
	/**
	 * This method returns grantContract for given applId
	 * @param applId
	 * @return
	 */
	public GdsGrantsContracts getGrantOrContract(Long applId);
		
	/**
	 * This method retrieves list of already linked submissions for a given grant.
	 * 
	 * @param grantContractNum
	 * @return
	 */
	public List<ProjectsVw> getPrevLinkedSubmissionsForGrant(String grantContractNum);
}

package gov.nih.nci.cbiit.scimgmt.gds.services;

import java.util.List;

import gov.nih.nci.cbiit.scimgmt.gds.domain.GdsGrantsContracts;
import gov.nih.nci.cbiit.scimgmt.gds.domain.InstitutionalCertification;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectsVw;
import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;

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
	 * Retrieve RepositoryStatus given an ID
	 * 
	 * @param repoId
	 * @return RepositoryStatus
	 */
	public RepositoryStatus findRepositoryById(Long repoId);
	
	/**
	 * Retrieve IC given an ID
	 * 
	 * @param icId
	 * @return InstitutionCertification
	 */
	public InstitutionalCertification findIcById(Long icId);
	
	
	
	
	/**
	 * Inserts or Updates the IC
	 * 
	 * @param IC
	 * @return saved IC
	 */
	public InstitutionalCertification saveOrUpdateIc(InstitutionalCertification ic);

	/**
	 * Deletes the IC given an ID and parent project
	 * 
	 * @param icId
	 * @param project
	 */
	public boolean deleteIc(Long icId, Project project);


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
	public List<ProjectsVw> getPrevLinkedSubmissionsForGrant(String grantContractNum, String projectId);

	/**
	 * Retrieve Sub-projects based on parent project ID.
	 * @param parentProjectId
	 * @return List<Project>
	 */
	public List<Project> getSubprojects(Long parentProjectId);
	
	/**
	 * Retrieve versions based on project group ID.
	 * @param projectGroupId
	 * @return List<Project>
	 */
	public List<Project> getVersions(Long projectGroupId);
}

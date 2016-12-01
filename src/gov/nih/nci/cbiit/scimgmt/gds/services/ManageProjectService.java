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
	 * Deletes the sub-projects with Parent Id
	 * 
	 */
	public void deleteSubProjects(Long parentId);

	/**
	 * Retrieve Project given an ID
	 * 
	 * @param projectId
	 * @return Project
	 */
	public Project findById(Long projectId);
	
	
	/**
	 * Retrieve ProjectsVw given an ID
	 * 
	 * @param projectId
	 * @return ProjectsVw
	 */
	public ProjectsVw findProjectsVwById(Long projectId);
	
	
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
	public List<GdsGrantsContracts> getGrantOrContractList(String grantContractNum,String applClassCode);
	
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
	 * This method retrieves list of program/branch for the doc.
	 * 
	 * @param doc
	 * @return
	 */
	public List<String> getSubOrgList(String doc);

	/**
	 * Retrieve Sub-projects based on parent project ID.
	 * @param parentProjectId
	 * @return List<Project>
	 */
	public List<Project> getSubprojects(Long parentProjectId);
	
	/**
	 * Retrieve Sub-project views based on parent project ID.
	 * @param parentProjectId
	 * @return List<ProjectsVw>
	 */
	public List<ProjectsVw> getSubprojectVws(Long parentProjectId);
	
	/**
	 * Retrieve versions based on project group ID.
	 * @param projectGroupId
	 * @return List<Project>
	 */
	public List<Project> getVersions(Long projectGroupId);
}

package gov.nih.nci.cbiit.scimgmt.gds.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.dao.DocumentsDao;
import gov.nih.nci.cbiit.scimgmt.gds.dao.InstitutionalCertificationsDao;
import gov.nih.nci.cbiit.scimgmt.gds.dao.ProjectsDao;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.GdsGrantsContracts;
import gov.nih.nci.cbiit.scimgmt.gds.domain.InstitutionalCertification;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Organization;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectsIcMapping;
import gov.nih.nci.cbiit.scimgmt.gds.services.ManageProjectService;
import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectsVw;
import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;

/**
 * Class to support management of Projects (save, update and delete etc...)
 */
@Component
public class ManageProjectServiceImpl implements ManageProjectService {
	private static final Logger logger = LogManager.getLogger(LookupServiceImpl.class);

	@Autowired
	private ProjectsDao projectsDao;
	
	@Autowired
	private DocumentsDao documentsDao;
	
	@Autowired
	InstitutionalCertificationsDao icCertsDao;
	
	/**
	 * Inserts or Updates the Project
	 * 
	 * @param project
	 * @return saved Project
	 */
	public Project saveOrUpdate(Project project) {
		
		//If this is a new project, check if this is a new  version of
		//of an existing project
		if(project.getId() == null && project.getProjectGroupId() != null) {
			//Reset the latest version flag on the current version to 'N'.
			Project currentLatestVersion = getCurrentLatestVersion(project.getProjectGroupId());
			currentLatestVersion.setLatestVersionFlag(ApplicationConstants.FLAG_NO);
			projectsDao.merge(currentLatestVersion);
		}
		
		return projectsDao.merge(project);
	}

	
	/**
	 * Deletes Sub-projects with parent Id
	 * 
	 */
	public void deleteSubProjects(Long parentId) {
		
		for(Project subproject: getSubprojects(parentId)) {
			List<Document>  docs = documentsDao.findByProjectId(subproject.getId());
			for(Document doc : docs) {
				documentsDao.delete(doc);
			}
			projectsDao.delete(subproject);
		}
	}
	/**
	 * Deletes the Project given an ID
	 * 
	 * @param projectId
	 */
	public void delete(Long projectId) {
		
		// First delete the documents
		List<Document>  docs = documentsDao.findByProjectId(projectId);
		for(Document doc : docs) {
			documentsDao.delete(doc);
		}
		
		Project project = findById(projectId);
		Long projectGroupId = project.getProjectGroupId();
		String subProjectFlag=project.getSubprojectFlag();
		List<InstitutionalCertification> certs=project.getInstitutionalCertifications();
		
		//If this is a parent project, delete the subprojects
		for(Project subproject: getSubprojects(projectId)) {
			docs = documentsDao.findByProjectId(subproject.getId());
			for(Document doc : docs) {
				documentsDao.delete(doc);
			}
			projectsDao.delete(subproject);
		}
		
		//Then delete the project
		projectsDao.delete(project);
		
		//If this is a parent project, then delete the orphaned IC
		if(subProjectFlag.equalsIgnoreCase("N")){
			for(InstitutionalCertification ic: certs) {
				icCertsDao.delete(ic);
			}
		}
		
		//Set the previous version to be the latest
		List<Project> projects = getVersions(projectGroupId);
		if(!CollectionUtils.isEmpty(projects)) {
			Project currentLatestVersion = projects.get(0);
			currentLatestVersion.setLatestVersionFlag(ApplicationConstants.FLAG_YES);
			projectsDao.merge(currentLatestVersion);
		}
		
		return;
	}
	
	
	
	/**
	 * Retrieve Project given an ID
	 * 
	 * @param projectId
	 * @return Project
	 */
	public Project findById(Long projectId) {
		return projectsDao.findById(projectId);
	}
	
	
	/**
	 * Retrieve ProjectsVw given an ID
	 * 
	 * @param projectId
	 * @return ProjectsVw
	 */
	public ProjectsVw findProjectsVwById(Long projectId) {
		return projectsDao.findProjectsVwById(projectId);
	}
	
	/**
	 * Retrieve IC given an ID
	 * 
	 * @param icId
	 * @return IC
	 */
	public InstitutionalCertification findIcById(Long icId) {
		return icCertsDao.findById(icId);
	}
	
	
	/**
	 * Retrieve RepositoryStatus given an ID
	 * 
	 * @param repoId
	 * @return RepositoryStatus
	 */
	public RepositoryStatus findRepositoryById(Long repoId) {
		return projectsDao.findRepositoryById(repoId);
	}
	
	/**
	 * Inserts or Updates the IC
	 * 
	 * @param ic
	 * @return saved ic
	 */
	public InstitutionalCertification saveOrUpdateIc(InstitutionalCertification ic) {
		InstitutionalCertification result =  icCertsDao.merge(ic);
		
		//saveOrUpdate(project);		
		return result;
	}
	
	
	/**
	 * Deletes an IC from DB by icId
	 * 
	 * @param icId
	 * @return boolean
	 */
	public boolean deleteIc(Long icId, Project project) {
		
		InstitutionalCertification ic = icCertsDao.findById(icId);
		if(ic == null)
			return false;
		
		// First, delete the files for this certId
		List<Document>	icFileDocs = documentsDao.findByIcId(icId, project.getId());
		for(Document doc: icFileDocs) {
			documentsDao.delete(doc);		
		}
		
		//Then remove the IC mapping from all the associated projects
		List<Project> projects = ic.getProjects();
		for(Project proj: projects) {
			proj.getInstitutionalCertifications().remove(ic);
			saveOrUpdate(proj);
		}
		
		//Now delete the IC
		icCertsDao.delete(ic);
		
		return true;
	}
	
	
	/**
	 * This method retrieves Intramural / Grant / Contract List
	 * @return
	 */
	public List<GdsGrantsContracts> getGrantOrContractList(String grantContractNum,String applClassCode){
		return projectsDao.getGrantOrContractList(grantContractNum,applClassCode);
	}
	
	/**
	 * This method returns grantContract for given applId
	 * @param applId
	 * @return
	 */
	public GdsGrantsContracts getGrantOrContract(Long applId){
		GdsGrantsContracts grantContract = projectsDao.getGrantOrContract(applId);
		if(grantContract == null){
			//If grant contract doesn't exist in DB, then create new one.
			grantContract = new GdsGrantsContracts();
		}
		return grantContract;
	}
	
	/**
	 * This method retrieves list of already linked submissions for a given grant.
	 * 
	 * @param grantContractNum
	 * @return
	 */
	public List<ProjectsVw> getPrevLinkedSubmissionsForGrant(String grantContractNum, String projectId) {
		return projectsDao.getPrevLinkedSubmissionsForGrant(grantContractNum,projectId);
	}
	
	/**
	 * This method retrieves list of program/branch for the given doc.
	 * 
	 * @param doc
	 * @return
	 */
	public List<String> getSubOrgList(String doc) {
		Organization organization = projectsDao.getOrganizationByDoc(doc);
		return projectsDao.getSubOrgList(organization.getNihsac());
	}

	
	/**
	 * Retrieve Sub-projects based on parent project ID.
	 * @param parentProjectId
	 * @return List<Project>
	 */
	public List<ProjectsVw> getSubprojectVws(Long parentProjectId) {
		logger.debug("getSubprojects");
		return projectsDao.getSubprojectVws(parentProjectId);
	}
	
	
	/**
	 * Retrieve Sub-projects based on parent project ID.
	 * @param parentProjectId
	 * @return List<Project>
	 */
	public List<Project> getSubprojects(Long parentProjectId) {
		logger.debug("getSubprojects");
		return projectsDao.getSubprojects(parentProjectId);
	}
	
	/**
	 * Retrieve project versions based on project group ID.
	 * @param projectGroupId
	 * @return List<Project>
	 */
	public List<Project> getVersions(Long projectGroupId) {
		logger.debug("getVersions");
		return projectsDao.getVersions(projectGroupId);
	}
	
	
	/**
	 * Retrieve the latest version of the project within
	 * the given project group ID.
	 * @param projectGroupId
	 * @return Project
	 */
	public Project getCurrentLatestVersion(Long projectGroupId) {
		return projectsDao.getCurrentLatestVersion(projectGroupId);
	}
	
}

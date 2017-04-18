package gov.nih.nci.cbiit.scimgmt.gds.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import gov.nih.nci.cbiit.scimgmt.gds.services.ManageProjectService;
import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectsVw;
import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Study;

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
		
		//If this is a new project, check if this is to be latest version of
		//of an existing project or subproject
		if(project.getId() == null && project.getProjectGroupId() != null
				&& ApplicationConstants.FLAG_YES.equals(project.getLatestVersionFlag())) {
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
		logger.info("Deleting all subprojects from project "  + parentId);
		for(Project subproject: getSubprojects(parentId)) {
			List<Document>  docs = documentsDao.findByProjectId(subproject.getId());
			for(Document doc : docs) {
				documentsDao.delete(doc);
			}
			projectsDao.delete(subproject);
		}
		logger.info("Completed deletion of all subprojects from project "  + parentId);
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
		Long parentProjectId = project.getParentProjectId();
		String subProjectFlag=project.getSubprojectFlag();
		List<InstitutionalCertification> certs=project.getInstitutionalCertifications();
		
		//If this is a parent project, delete the subprojects
		deleteSubProjects(projectId);
		
		//Then delete the project
		projectsDao.delete(project);
		
		//If this is a parent project, then delete the orphaned IC
		if(subProjectFlag.equalsIgnoreCase("N")){
			for(InstitutionalCertification ic: certs) {
				icCertsDao.delete(ic);
			}
		}
		
		//Set the previous version to be the latest
		List<Project> projects = getVersions(projectGroupId, parentProjectId);
		if(!CollectionUtils.isEmpty(projects)) {
			Project currentLatestVersion = projects.get(0);
			currentLatestVersion.setLatestVersionFlag(ApplicationConstants.FLAG_YES);
			for(Project subproject: getSubprojects(currentLatestVersion.getId())) {
				List<Project> subprojectVersions = getVersions(subproject.getProjectGroupId(), subproject.getParentProjectId() );
				Project latestSubProjectVersion = subprojectVersions.get(0);
				if(!ApplicationConstants.FLAG_YES.equals(latestSubProjectVersion.getLatestVersionFlag())) {
					latestSubProjectVersion.setLatestVersionFlag(ApplicationConstants.FLAG_YES);
					projectsDao.merge(latestSubProjectVersion);
				}
			}
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
	 * saves study
	 * 
	 * returns saved study
	 */
	public Study saveStudy( Study study) {
		Study result = icCertsDao.mergeStudy(study);
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
		
		// Remove the ic mapping from all the associated studies
		// and remove the DULs from associated studies
		for(Study stud: ic.getStudies()) {
			stud.getInstitutionalCertifications().clear();
			stud.getStudiesDulSets().clear();
			stud.setDulVerificationId(null);
			stud.setComments(null);
			saveStudy(stud);
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
	 * deletes the study
	 * 
	 * return boolean
	 */
	public boolean deleteStudy(Long studyId, Project project) {
		
		Study study = icCertsDao.findStudyById(studyId);
		icCertsDao.delete(study);
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
	 * Retrieve Sub-projects based on parent project ID. Used
	 * for populating the version table in submission details.
	 * @param parentProjectId
	 * @return List<Project>
	 */
	public List<ProjectsVw> getSubprojectsVw(Long parentProjectId) {
		
		Map<Long, ProjectsVw> projectMap = new HashMap<Long, ProjectsVw>();
		logger.debug("getSubprojectsVw");
		List<ProjectsVw> subprojects = projectsDao.getSubprojectsVw(parentProjectId);
		
		for(ProjectsVw subproject: subprojects) {
			//Only get the latest version. We cannot use latestVersionFlag in the query
			//because we could be getting the subprojects for an older parent
			//version (as in the case of the submissions details page version table)
			//in which case the latestVersionFlag will be 'N' for all these subprojects.
			if(!projectMap.containsKey(subproject.getProjectGroupId())) {
				projectMap.put(subproject.getProjectGroupId(), subproject);
			}
		}
		
		return new ArrayList<ProjectsVw>(projectMap.values());
	}
	
	
	/**
	 * Retrieve All Sub-projects based on parent project ID.
	 * @param parentProjectId
	 * @return List<Project>
	 */
	public List<Project> getSubprojects(Long parentProjectId) {
		return  projectsDao.getSubprojects(parentProjectId, false);
	}
	
	/**
	 * Retrieve Sub-projects based on parent project ID.
	 * @param parentProjectId
	 * @return List<Project>
	 */
	public List<Project> getSubprojects(Long parentProjectId, boolean latestVersionOnly) {
		logger.debug("getSubprojects");
		return  projectsDao.getSubprojects(parentProjectId, latestVersionOnly);
	}
	
	/**
	 * Retrieve project versions based on project group ID.
	 * @param projectGroupId
	 * @param parentProjectId
	 * @return List<Project>
	 */
	public List<Project> getVersions(Long projectGroupId, Long parentProjectId) {
		logger.debug("getVersions");
		return projectsDao.getVersions(projectGroupId, parentProjectId);
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

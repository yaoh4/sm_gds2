package gov.nih.nci.cbiit.scimgmt.gds.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import gov.nih.nci.cbiit.scimgmt.gds.dao.DocumentsDao;
import gov.nih.nci.cbiit.scimgmt.gds.dao.InstitutionalCertificationsDao;
import gov.nih.nci.cbiit.scimgmt.gds.dao.ProjectsDao;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.GdsGrantsContracts;
import gov.nih.nci.cbiit.scimgmt.gds.domain.InstitutionalCertification;
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
		return projectsDao.merge(project);
	}

	/**
	 * Deletes the Project given an ID
	 * 
	 * @param projectId
	 */
	public void delete(Long projectId) {
		// Delete the documents first
		List<Document>  docs = documentsDao.findByProjectId(projectId);
		for(Document doc : docs) {
			documentsDao.delete(doc);
		}
		
		Project project = findById(projectId);
		String subProjectFlag=project.getSubprojectFlag();
		List<InstitutionalCertification> certs=project.getInstitutionalCertifications();
		projectsDao.delete(project);
		if(subProjectFlag.equalsIgnoreCase("N")){
			for(InstitutionalCertification ic: certs) {
				icCertsDao.delete(ic);
			}
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
	public boolean deleteIc(Long icId) {
		
		InstitutionalCertification ic = icCertsDao.findById(icId);
		if(ic == null)
			return false;
		
		icCertsDao.delete(ic);
		
		return true;
	}
	
	
	/**
	 * This method retrieves Intramural / Grant / Contract List
	 * @return
	 */
	public List<GdsGrantsContracts> getGrantOrContractList(String grantContractNum){
		return projectsDao.getGrantOrContractList(grantContractNum);
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
	 * Retrieve Sub-projects based on parent project ID.
	 * @param parentProjectId
	 * @return List<Project>
	 */
	public List<Project> getSubprojects(Long parentProjectId) {
		logger.debug("getSubprojects");
		return projectsDao.getSubprojects(parentProjectId);
		
	}
	
}

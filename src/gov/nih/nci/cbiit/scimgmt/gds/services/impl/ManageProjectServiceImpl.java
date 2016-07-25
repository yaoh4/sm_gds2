package gov.nih.nci.cbiit.scimgmt.gds.services.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.nih.nci.cbiit.scimgmt.gds.dao.DocumentsDao;
import gov.nih.nci.cbiit.scimgmt.gds.dao.ProjectsDao;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.GdsGrantsContracts;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.services.ManageProjectService;
import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectsVw;

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
		projectsDao.delete(project);
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
		return projectsDao.getGrantOrContract(applId);
	}
	
	/**
	 * This method retrieves list of already linked submissions for a given grant.
	 * 
	 * @param grantContractNum
	 * @return
	 */
	public List<ProjectsVw> getPrevLinkedSubmissionsForGrant(String grantContractNum){
		return projectsDao.getPrevLinkedSubmissionsForGrant(grantContractNum);
	}
}

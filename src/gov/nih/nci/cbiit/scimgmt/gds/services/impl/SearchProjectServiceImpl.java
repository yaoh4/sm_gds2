package gov.nih.nci.cbiit.scimgmt.gds.services.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.nih.nci.cbiit.scimgmt.gds.dao.ProjectsDao;
import gov.nih.nci.cbiit.scimgmt.gds.domain.GdsGrantsContracts;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.services.SearchProjectService;

/**
 * Class to support Project searches from the database.
 */
@Component
public class SearchProjectServiceImpl implements SearchProjectService {
	
	private static final Logger logger = LogManager.getLogger(SearchProjectServiceImpl.class);
	
	@Autowired
	private ProjectsDao projectsDAO;
	
	/**
	 * This method returns all Project Ids
	 * @return List
	 */
	public List<Long> getAllProjectIds(){
		return projectsDAO.getAllProjectIds();
	}
	
	/**
	 * This method retrieves Project from DB based on projectId.
	 * @param projectId
	 * @return Project
	 */
	public Project findProjectById(Long projectId){
		 return projectsDAO.findById(projectId);
	}
	
	/**
	 * This method retrieves Intramural / Grant / Contract List
	 * @return
	 */
	public List<GdsGrantsContracts> getGrantOrContractList(String grantContractNum){
		return projectsDAO.getGrantOrContractList(grantContractNum);
	}
	
}

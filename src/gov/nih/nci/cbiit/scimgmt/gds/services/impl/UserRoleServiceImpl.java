/**
 * 
 */
package gov.nih.nci.cbiit.scimgmt.gds.services.impl;

import gov.nih.nci.cbiit.scimgmt.gds.dao.UserRoleDao;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PersonRole;
import gov.nih.nci.cbiit.scimgmt.gds.domain.UserRole;
import gov.nih.nci.cbiit.scimgmt.gds.model.RoleSearchCriteria;
import gov.nih.nci.cbiit.scimgmt.gds.services.UserRoleService;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Service Implementation for UserService.
 * @author tembharend
 */
@Component
public class UserRoleServiceImpl implements UserRoleService {

	private static final Logger logger = LogManager.getLogger(UserRoleServiceImpl.class);

	@Autowired
	private UserRoleDao userRoleDao;

	/**
	 * This method retrieves Ned Person from DB for given userId. 
	 */
	public NedPerson findNedPersonByUserId(String userId) throws Exception{
		logger.debug("findByAdUserId('" + userId + "')");
		return userRoleDao.findNedPersonByUserId(userId);
	}
	
	
	/**
	 * This method retrieves UserRole from DB for given userId. 
	 */
	public UserRole findUserRoleByUserId(String userId) {
		logger.debug("findUserRoleByUserId('" + userId + "')");
		return userRoleDao.findUserRoleByUserId(userId);
	}
	
	
	/**
	 * This method retrieves PersonRole from DB for given userId. 
	 */
	public PersonRole findPersonRoleByUserId(String userId) {
		logger.debug("findPersonRoleByUserId('" + userId + "')");
		return userRoleDao.findPersonRole(userId);
	}
	
	/**
	 * This method retrieves List of PersonRole from DB for given roleId. 
	 */
	public List<UserRole> searchUserRole(RoleSearchCriteria searchCriteria) {
		logger.debug("searchUserRole('" + searchCriteria + "')");
		return userRoleDao.searchUserRole(searchCriteria);
	}
	
	/**
	 * This method retrieves List of PersonRole from DB for given roleId. 
	 */
	public List<NedPerson> searchNedPerson(RoleSearchCriteria searchCriteria) {
		logger.debug("searchNedPerson('" + searchCriteria + "')");
		return userRoleDao.searchNedPerson(searchCriteria);
	}
	
	
	/**
	 * Inserts or Updates the PersonRole
	 * 
	 * @param personRole
	 * @return saved PersonRole
	 */
	public PersonRole saveOrUpdatePersonRole(PersonRole personRole) {
		PersonRole result =  userRoleDao.merge(personRole);
				
		return result;
	}
	
	
	/**
	 * Deletes a PersonRole from the DB 
	 * 
	 * @param networkId
	 * @return boolean
	 */
	public boolean deletePersonRole(String networkId) {
		
		PersonRole personRole = userRoleDao.findPersonRole(networkId);
		if(personRole == null)
			return false;
		
		userRoleDao.delete(personRole);
		
		return true;
	}
	

}

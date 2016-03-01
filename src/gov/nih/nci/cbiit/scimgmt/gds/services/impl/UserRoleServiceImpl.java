/**
 * 
 */
package gov.nih.nci.cbiit.scimgmt.gds.services.impl;

import gov.nih.nci.cbiit.scimgmt.gds.dao.UserRoleDao;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.services.UserRoleService;


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
}

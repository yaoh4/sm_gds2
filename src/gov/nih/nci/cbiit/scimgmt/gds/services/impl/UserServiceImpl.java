/**
 * 
 */
package gov.nih.nci.cbiit.scimgmt.gds.services.impl;

import gov.nih.nci.cbiit.scimgmt.gds.dao.UserRoleDao;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.services.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

	private static final Logger log = LogManager.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRoleDao userRoleDao;

	/* (non-Javadoc)
	 * @see gov.nih.nci.cbiit.scimgmt.oar.service.PersonService#findNedPersonByUserId(java.lang.String)
	 */
	@Override
	public NedPerson findNedPersonByUserId(String userName) throws Exception{
		log.debug("findByAdUserId('" + userName + "')");
		return userRoleDao.findByAdUserId(userName);
	}

	/**
	 * @return the userRoleDao
	 */
	public UserRoleDao getUserRoleDao() {
		return userRoleDao;
	}

	/**
	 * @param userRoleDao the userRoleDao to set
	 */
	public void setUserRoleDao(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

}

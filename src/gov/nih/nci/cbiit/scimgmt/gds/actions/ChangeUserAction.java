package gov.nih.nci.cbiit.scimgmt.gds.actions;


import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.services.UserRoleService;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class is responsible for changing the user only for Test purposes.
 * @author tembharend
 */
@SuppressWarnings("serial")
public class ChangeUserAction extends BaseAction {

	static Logger logger = LogManager.getLogger(ChangeUserAction.class);

	private String user;    

	@Autowired
	private UserRoleService userRoleService;

	/**
	 * Change user functionality support only for NON production environment
	 */
	public String execute() throws Exception {

		String forward = SUCCESS;

		if (loggedOnUser != null && StringUtils.isNotBlank(user) && !ApplicationConstants.PRODUCTION.equalsIgnoreCase(getEnvironment())) {
			
			String loggedOnUserEmail = loggedOnUser.getEmail();
			NedPerson nedPerson =  userRoleService.findNedPersonByUserId(user);
			nedPerson.setEmail(loggedOnUserEmail);
			BeanUtils.copyProperties(nedPerson, loggedOnUser);				
		}
		
		return forward;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}
}

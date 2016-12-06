package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.opensymphony.xwork2.ActionContext;

import org.apache.commons.lang.StringUtils;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PersonRole;
import gov.nih.nci.cbiit.scimgmt.gds.domain.UserRole;
import gov.nih.nci.cbiit.scimgmt.gds.model.RoleSearchCriteria;
import gov.nih.nci.cbiit.scimgmt.gds.services.MailService;
import gov.nih.nci.cbiit.scimgmt.gds.services.UserRoleService;

/**
 * @author menons2
 *
 */
@SuppressWarnings("serial")
public class AdminAction extends BaseAction {
	
	@Autowired
	protected UserRoleService userRoleService;
	
	@Autowired
	protected MailService mailService;
	
	private RoleSearchCriteria criteria = new RoleSearchCriteria();
	
	//private List<PersonRole> personRoles = new ArrayList<PersonRole>();
	private List<UserRole> userRoles = new ArrayList<UserRole>();
	
	private List<NedPerson> nedPersons = new ArrayList<NedPerson>();
	
	private String userId;
	
	private UserRole selectedUserRole;
	
	private String userRoleCode;
	
	private String gdsUserOnly;
	
	static Logger logger = LogManager.getLogger(AdminAction.class);
	
	/**
	 * Entry point to the admin tab. Invoked when the user clicks
	 * the Admin tab or Search sub-tab on the admin tab.
	 * 
	 * @return forward string
	 */
	public String execute() throws Exception {
     
        return SUCCESS;
	}
	
	
	public String searchNedPersons() {
		//Perform search
		if(!isSearchCriteriaValid(criteria)) {
			addActionError(getText("error.admin.criteria"));
			return INPUT;
		}
		nedPersons = userRoleService.searchNedPerson(getCriteria());
		if(CollectionUtils.isEmpty(nedPersons)) {
			logger.debug("No results found for given search criteria in searchNedPerson");
		} else {
			//Save the criteria in session for retrieving later
			session.put("roleSearchCriteria", getCriteria());
		}
		
		return SUCCESS;
	}
	
	
	public String searchGdsUsers() {
		
		if(!isSearchCriteriaValid(getCriteria())) {
			addActionError(getText("error.admin.criteria"));
			return INPUT;
		}
		
		//Perform search
		userRoles = userRoleService.searchUserRole(getCriteria());
		if(CollectionUtils.isEmpty(userRoles)) {
			logger.debug("No results found for given search criteria in searchGdsUsers");
		} else {
			//Save the criteria in session for retrieving later
			session.put("roleSearchCriteria", getCriteria());
		}
		return SUCCESS;
	}

	
	public String deleteGdsUser() throws Exception {
		
		logger.debug("deleteGdsUser()");

		userRoleService.deletePersonRole(getUserId());
		
		if(criteria.getGdsUsersOnly() == true || StringUtils.isNotBlank(getCriteria().getRoleCode())) {
			return searchGdsUsers();
		} else {
			return searchNedPersons();
		}	
	}
	
	public String selectGdsUser() throws Exception {
		
		logger.debug("selectGdsUser()");

		selectedUserRole = userRoleService.findUserRoleByUserId(getUserId());
			
		return SUCCESS;
	}
	
	
	public String saveGdsUser() {
		
		String gdsRoleCode = getUserRoleCode();
		String networkId  = getUserId();
		boolean changed = true;
		
		PersonRole personRole = userRoleService.findPersonRoleByUserId(networkId);
		if(personRole == null) {
			personRole = new PersonRole();
			personRole.setNihNetworkId(networkId);
			personRole.setActiveFlag(ApplicationConstants.FLAG_YES);
		} else {
			if (StringUtils.equals(personRole.getRole().getCode(), gdsRoleCode))
				changed = false;
		}
		personRole.setRole(lookupService.getLookupByCode(ApplicationConstants.GDS_ROLE_LIST, gdsRoleCode));
	
		personRole = userRoleService.saveOrUpdatePersonRole(personRole);
		
		if(changed) {
			try {
				mailService.sendRoleAddedToUser(personRole);
			} catch (Exception e) {
				logger.error("Action taken by " + loggedOnUser.getAdUserId());
				logger.error("Email failed to send to notify user: " + personRole.getNihNetworkId() + " of added role, " + personRole.getRole().getCode() + "." + e.getMessage());
			}
		}
		if(criteria.getGdsUsersOnly() == true || StringUtils.isNotBlank(getCriteria().getRoleCode())) {
			return searchGdsUsers();
		} else {
			return searchNedPersons();
		}
	}
	
	
	private boolean isSearchCriteriaValid(RoleSearchCriteria searchCriteria) {
		return !StringUtils.isBlank(searchCriteria.getLastName())
			|| !StringUtils.isBlank(searchCriteria.getRoleCode());
	}

	
	/**
	 * @return the searchCriteria
	 */
	public RoleSearchCriteria getCriteria() {
		return criteria;
	}

	
	/**
	 * @param criteria the criteria to set
	 */
	public void setCriteria(RoleSearchCriteria criteria) {
		this.criteria = criteria;
	}


	/**
	 * @return the userRoles
	 */
	public List<UserRole> getUserRoles() {
		return userRoles;
	}


	/**
	 * @param userRoles the userRoles to set
	 */
	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	
	/**
	 * @return the nedPersons
	 */
	public List<NedPerson> getNedPersons() {
		return nedPersons;
	}


	/**
	 * @param nedPersons the nedPersons to set
	 */
	public void setNedPersons(List<NedPerson> nedPersons) {
		this.nedPersons = nedPersons;
	}


	/**
	 * @return the userRoleService
	 */
	public UserRoleService getUserRoleService() {
		return userRoleService;
	}


	/**
	 * @param userRoleService the userRoleService to set
	 */
	public void setUserRoleService(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}


	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}


	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}


	/**
	 * @return the selectedUserRole
	 */
	public UserRole getSelectedUserRole() {
		return selectedUserRole;
	}


	/**
	 * @param selectedUserRole the selectedUserRole to set
	 */
	public void setSelectedUserRole(UserRole selectedUserRole) {
		this.selectedUserRole = selectedUserRole;
	}


	/**
	 * @return the userRoleCode
	 */
	public String getUserRoleCode() {
		return userRoleCode;
	}


	/**
	 * @param userRoleCode the userRoleCode to set
	 */
	public void setUserRoleCode(String userRoleCode) {
		this.userRoleCode = userRoleCode;
	}


	/**
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}


	/**
	 * @param logger the logger to set
	 */
	public static void setLogger(Logger logger) {
		AdminAction.logger = logger;
	}

}

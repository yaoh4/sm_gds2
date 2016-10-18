package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.commons.lang.StringUtils;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PersonRole;
import gov.nih.nci.cbiit.scimgmt.gds.model.RoleSearchCriteria;
import gov.nih.nci.cbiit.scimgmt.gds.services.UserRoleService;

/**
 * @author menons2
 *
 */
@SuppressWarnings("serial")
public class AdminAction extends BaseAction {
	
	@Autowired
	protected UserRoleService userRoleService;
	
	private RoleSearchCriteria searchCriteria = new RoleSearchCriteria();
	
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
	
	public String edit() {
		
		
		return SUCCESS;
	}
	
	
	
	
	public String searchNedPersons() {
		//Perform search
		RoleSearchCriteria searchCriteria = getSearchCriteria();
		if(!isSearchCriteriaValid(searchCriteria)) {
			addActionError("Please enter at least one of last name or doc as search criteria");
			return INPUT;
		}
		List<NedPerson> persons = userRoleService.searchNedPerson(getSearchCriteria());
		if(persons == null || persons.isEmpty()) {
			logger.debug("No results found for given search criteria in searchNedPerson");
		}
		
		return SUCCESS;
	}
	
	
	public String searchGdsUsers() {
		
		RoleSearchCriteria searchCriteria = getSearchCriteria();
		if(!isSearchCriteriaValid(searchCriteria)) {
			addActionError("Please enter at least one of last name, role or doc as search criteria");
			return INPUT;
		}
		
		//Perform search
		List<PersonRole> personRoles = userRoleService.searchPersonRole(getSearchCriteria());
		if(personRoles == null || personRoles.isEmpty()) {
			logger.debug("No results found for given search criteria in searchGdsUsers");
		}
		return SUCCESS;
	}


	private boolean isSearchCriteriaValid(RoleSearchCriteria searchCriteria) {
		return !StringUtils.isBlank(searchCriteria.getLastName())
			|| !StringUtils.isBlank(searchCriteria.getDoc())
			|| searchCriteria.getRoleId() != null;
	}

	/**
	 * @return the searchCriteria
	 */
	public RoleSearchCriteria getSearchCriteria() {
		return searchCriteria;
	}


	/**
	 * @param searchCriteria the searchCriteria to set
	 */
	public void setSearchCriteria(RoleSearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

}

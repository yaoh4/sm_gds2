package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.util.ArrayList;
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
	
	private RoleSearchCriteria criteria = new RoleSearchCriteria();
	
	private List<PersonRole> personRoles = new ArrayList<PersonRole>();
	
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
			addActionError("Please enter at least one of last name or doc as search criteria");
			return INPUT;
		}
		List<NedPerson> persons = userRoleService.searchNedPerson(getCriteria());
		if(persons == null || persons.isEmpty()) {
			logger.debug("No results found for given search criteria in searchNedPerson");
		}
		
		return SUCCESS;
	}
	
	
	public String searchGdsUsers() {
		
		if(!isSearchCriteriaValid(getCriteria())) {
			addActionError("Please enter at least one of last name, role or doc as search criteria");
			return INPUT;
		}
		
		//Perform search
		personRoles = userRoleService.searchPersonRole(getCriteria());
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
	public RoleSearchCriteria getCriteria() {
		return criteria;
	}


	/**
	 * @param searchCriteria the searchCriteria to set
	 */
	public void setSearchCriteria(RoleSearchCriteria criteria) {
		this.criteria = criteria;
	}

	/**
	 * @return the personRoles
	 */
	public List<PersonRole> getPersonRoles() {
		return personRoles;
	}

	/**
	 * @param personRoles the personRoles to set
	 */
	public void setPersonRoles(List<PersonRole> personRoles) {
		this.personRoles = personRoles;
	}

}

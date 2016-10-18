package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

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
	 * search on the Admin tab.
	 * 
	 * @return forward string
	 */
	public String execute() throws Exception {
     
        return SUCCESS;
	}
	
	
	public String search() {
		
		//Perform search
		List<PersonRole> personRoles = userRoleService.searchPersonRole(getSearchCriteria());
		
		return SUCCESS;
	}
	
	
	public String edit() {
		
		//Perform search
		List<NedPerson> persons = userRoleService.searchNedPerson(getSearchCriteria());
		return SUCCESS;
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

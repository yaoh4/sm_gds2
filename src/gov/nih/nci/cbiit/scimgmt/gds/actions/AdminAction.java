package gov.nih.nci.cbiit.scimgmt.gds.actions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author menons2
 *
 */
@SuppressWarnings("serial")
public class AdminAction extends BaseAction {
	
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
		
		//Get the search criteria
		//RoleSearchcriteria criteria = getRoleSearchCriteria();
		
		return SUCCESS;
	}
	
	
	public String edit() {
		
		return SUCCESS;
	}

}

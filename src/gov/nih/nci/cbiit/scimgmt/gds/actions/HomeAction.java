package gov.nih.nci.cbiit.scimgmt.gds.actions;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author menons2
 *
 */
@SuppressWarnings("serial")
public class HomeAction extends BaseAction {
	
	static Logger logger = LogManager.getLogger(BaseAction.class);
	
	/**
	 * Entry point to the application. Invoked when the user logs in.
	 * 
	 * @return forward string
	 */
	public String execute() throws Exception {
       
        
        return ApplicationConstants.SUCCESS;
	}

}

package gov.nih.nci.cbiit.scimgmt.gds.actions;

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
	 * Entry point. Invoked when user logs into the application.
	 * 
	 * @return forward string
	 */
	public String execute() throws Exception {
        String forward = SUCCESS;
        
        return forward;
	}

}

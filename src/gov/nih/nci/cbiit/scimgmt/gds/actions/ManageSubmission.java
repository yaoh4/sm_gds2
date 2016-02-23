package gov.nih.nci.cbiit.scimgmt.gds.actions;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author tembharend
 *
 */
@SuppressWarnings("serial")
public class ManageSubmission extends BaseAction {
	
	static Logger logger = LogManager.getLogger(ManageSubmission.class);
	
	/**
	 * Manages Submission creation, updates and deletion.
	 * 
	 * @return forward string
	 */
	public String execute() throws Exception {
       
        
        return ApplicationConstants.SUCCESS;
	}

}

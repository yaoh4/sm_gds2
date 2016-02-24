package gov.nih.nci.cbiit.scimgmt.gds.actions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is responsible for searching submissions.
 * @author tembharend
 */
@SuppressWarnings("serial")
public class SearchSubmissions extends BaseAction {
	
	static Logger logger = LogManager.getLogger(ManageSubmission.class);
	
	/**
	 * Search Submissions.
	 * 
	 * @return forward string
	 */
	public String execute() throws Exception {
       
        
        return SUCCESS;
	}

}

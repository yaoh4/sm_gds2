package gov.nih.nci.cbiit.scimgmt.gds.actions;



import gov.nih.nci.cbiit.scimgmt.gds.domain.AppPropertiesT;
import gov.nih.nci.cbiit.scimgmt.gds.services.LookupService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * GDS System Administration Utility
 */
public class SysAdminAction extends BaseAction {

   
    /**
     * SYS Admin Action 
     * See Constants below
     */
    private String task;
	
    static Logger logger = LogManager.getLogger(BaseAction.class);
    
    @Autowired
	LookupService lookupService;
    
    private static final String ADMIN_TASK_RELOAD_PROPERTIES="RELOAD_PROPERTIES";
    private static final String ADMIN_TASK_REFRESH_LISTS="REFRESH_LISTS";
 
   // URL examples
   // http://localhost/gds/SysAdmin.action?task=RELOAD_PROPERTIES
   // http://localhost/gds/SysAdmin.action?task=REFRESH_LISTS 
   
    
    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() throws Exception {
        
        String  log="";
        log="System Administration Task  "+getTask()+" Initiated by "+loggedOnUser.getAdUserId();
        logger.info(log);
                
        if (loggedOnUser == null){
        	String error = "Invalid Access";
        	throw new Exception(error);
        }
        
		verifyUser();
        
        if (ADMIN_TASK_RELOAD_PROPERTIES.equals(task)){
            logger.info("Task: Reload GDS properties Initiated by :"+loggedOnUser.getAdUserId());
            reloadProperties();
            logger.info("Task: Reload GDS properties Completed");

        }
        
        if (ADMIN_TASK_REFRESH_LISTS.equals(task)){
            logger.info("Task: Refresh GDS Lists Initiated by :"+loggedOnUser.getAdUserId());
            refreshLists();
            logger.info("Task: Refresh GDS Lists Completed");

        }
        
        return SUCCESS;       
    }
    
  
    public void setTask(String task) {
        this.task = task;
    }

    public String getTask() {
        return task;
    }
    
    /**
     * Calls property init
     */
    private void reloadProperties(){
    	logger.info("Initiating Reload properties...");
    	for (AppPropertiesT a : lookupService.loadPropertiesList()) {
    		gdsProperties.setProperty(a.getId().getPropKey(), a.getPropValue());
		}
        logger.info("Reload properties completed");
    }
    
    /**
     * Calls initializer bean reinit
     */
    private void refreshLists(){
    	logger.info("Initiating Refresh lists...");
    	lookupService.loadLookupLists();
        logger.info("Refresh lists completed");
    }
    
    /**
     * Only Sys Admin users can execute SysAdmin Actions.
     * @throws Exception
     */
    private void  verifyUser()throws Exception{
        logger.info("Authenticating the User "+loggedOnUser.getAdUserId()+" for Sys Administration Task");
        String  adminUsers= gdsProperties.getProperty("sys.admin");
        String[] sysAdminUsers  = adminUsers.split(",");
        boolean validAdminUser =  false;
        for (int i=0;i <sysAdminUsers.length;i++){
            if (loggedOnUser.getAdUserId().equalsIgnoreCase(sysAdminUsers[i])){
                validAdminUser =  true;
            }
        }
        if (!validAdminUser){
            String error =loggedOnUser.getAdUserId() + " is NOT a valid user to perform System Administration Task";
            logger.error(error);
            throw new Exception(error);
        }
    }   

}

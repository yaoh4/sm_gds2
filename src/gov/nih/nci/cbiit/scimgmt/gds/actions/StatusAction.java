package gov.nih.nci.cbiit.scimgmt.gds.actions;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import gov.nih.nci.cbiit.scimgmt.gds.services.LookupService;


@SuppressWarnings("serial")
public class StatusAction extends BaseAction {

    public static final String status = 
        "The GDS Application is up and running.";
    
    @Autowired
	protected LookupService lookupService;
    
    private String appStatus;

    public String execute() throws Exception {
        try {
        	lookupService.loadPropertiesList();
            setAppStatus(status);
        } catch (Exception e) {
            logger.fatal("CANNOT RETRIEVE PROPERTIES !!!!");
            String statusErr = "CANNOT RETRIEVE PROPERTIES: " + e.getMessage();
            setAppStatus(statusErr);
            ServletActionContext.getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, statusErr);
            return SUCCESS;
        }
        return SUCCESS;
    }

   
	/**
     * @return the appStatus
     */
    public String getAppStatus() {
        return appStatus;
    }

    /**
     * @param appStatus the appStatus to set
     */
    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }  

}

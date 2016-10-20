/**
 * 
 */
package gov.nih.nci.cbiit.scimgmt.gds.actions;


import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsProperties;

import java.io.InputStream;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author menons2
 *
 */
@SuppressWarnings("serial")
public class BaseAction extends ActionSupport implements SessionAware {

	static Logger logger = LogManager.getLogger(BaseAction.class);
		
	@SuppressWarnings("unused")
	private Map<String, Object> session;
	
	@Autowired
	protected NedPerson loggedOnUser;	
	
	@Autowired 
	protected GdsProperties gdsProperties;	
	
	/*Id of the selected project or subproject*/
	private String projectId;
	
	protected InputStream inputStream;
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}	

	/**
	 * @return the loggedOnUser
	 */
	public NedPerson getLoggedOnUser() {
		return loggedOnUser;
	}

	/**
	 * @param loggedOnUser the loggedOnUser to set
	 */
	public void setLoggedOnUser(NedPerson loggedOnUser) {
		this.loggedOnUser = loggedOnUser;
	}
	
	/**
	 * Gets the Help URL
	 * 
	 * @return
	 */
	public String getHelpUrl() {
		return gdsProperties.getProperty(ApplicationConstants.GDS_HELP_DOCUMENT_URL);
	}
	
	/**
	 * Gets the Report URL.
	 * 
	 * @return
	 */
	public String getReportUrl() {
		return ""; 
	}
	
	/**
	 * Gets the GDS contact e-mail.
	 * 
	 * @return
	 */
	public String getGdsContactEmail() {
		return gdsProperties.getProperty(ApplicationConstants.CONTACT_EMAIL);
	}
	
	/**
	 * Gets the environment.
	 * 
	 * @return the environment
	 */
	public String getEnvironment() {		
		return gdsProperties.getProperty(ApplicationConstants.ENVIRONMENT);
	}
	
	
	/**
	 * Gets the environment.
	 * 
	 * @return the environment
	 */
	public String getVersion() {		
		return gdsProperties.getProperty(ApplicationConstants.VERSION);
	}
	
	/**
	 * Gets the GDS Error e=mail.
	 * 
	 * @return
	 */
	public String getGdsErrorEmail() {
		return gdsProperties.getProperty(ApplicationConstants.ERROR_EMAIL);
	}
	
	
	/**
	 * Get business policy email
	 */
	public String getBusinessPolicyEmail() {
		return gdsProperties.getProperty(ApplicationConstants.BUSINESS_POLICY_EMAIL);
	}
	
	
	public String getTechnicalIssuesEmail() {
		return gdsProperties.getProperty(ApplicationConstants.TECHNICAL_ISSUES_EMAIL);
	}
    
	public String getBusinessPolicyDisplay() {
		return gdsProperties.getProperty(ApplicationConstants.BUSINESS_POLICY_DISPLAY);
	}
	
	public String getTechnicalIssuesDisplay() {
	    return gdsProperties.getProperty(ApplicationConstants.TECHNICAL_ISSUES_DISPLAY);
	}
	
	/**
	 * Gets the NotAuthorized Error Message.
	 * 
	 * @return
	 */
	public String getNotAuthorizedErrorMessage() {
		return gdsProperties.getProperty("error.notAuthorized");
	}

	/**
	 * @return the projectId
	 */
	public String getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}	
	
	public InputStream getInputStream() {
		return inputStream;
	}


	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	/**
	 * Checks if is Read only user
	 * 
	 * @return true, if is Read only user
	 */
	public boolean isReadOnlyUser() {
		if(loggedOnUser.getPersonRole() == null)
			return false;
		return (loggedOnUser.getPersonRole().getRole() == null? false: loggedOnUser.getPersonRole().getRole().getId().equals(ApplicationConstants.ROLE_READ_ONLY_USER_ID));
	}
	
}

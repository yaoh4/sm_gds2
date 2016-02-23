/**
 * 
 */
package gov.nih.nci.cbiit.scimgmt.gds.actions;


import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.services.MailService;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author menons2
 *
 */
@SuppressWarnings("serial")
public class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, SessionAware {

	static Logger logger = LogManager.getLogger(BaseAction.class);
	
	@SuppressWarnings("unused")
	private HttpServletRequest request;
	
	@SuppressWarnings("unused")
	private HttpServletResponse response;
	
	@SuppressWarnings("unused")
	private Map<String, Object> session;
	
	@Autowired
	protected NedPerson loggedOnUser;
	
	@Autowired
	protected MailService mailService;	
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
		
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		
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
		return ""; // Properties.getProperty("GDS_HELP_DOCUMENT_URL");
	}
	
	/**
	 * Gets the Help URL
	 * 
	 * @return
	 */
	public String getReportUrl() {
		return ""; 
	}
	
	/**
	 * Gets the OAR General mailbox
	 * 
	 * @return
	 */
	public String getGdsContactEmail() {
		return ""; //Properties.getProperty("CONTACT_EMAIL");
	}
	
	/**
	 * Gets the environment.
	 * 
	 * @return the environment
	 */
	public String getEnvironment() {		
		return "local"; //Properties().getProperty("gds.environment");
	}

}

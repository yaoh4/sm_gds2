/**
 * 
 */
package gov.nih.nci.cbiit.scimgmt.gds.actions;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author menons2
 *
 */
@SuppressWarnings("serial")
public class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, SessionAware {

	static Logger logger = LogManager.getLogger(BaseAction.class);
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Map<String, Object> session;
	
	
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

}

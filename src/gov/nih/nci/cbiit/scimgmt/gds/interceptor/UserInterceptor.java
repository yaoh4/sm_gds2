package gov.nih.nci.cbiit.scimgmt.gds.interceptor;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.services.UserRoleService;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.StrutsStatics;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @author tembharend
 *
 */
@SuppressWarnings("serial")
public class UserInterceptor extends AbstractInterceptor implements StrutsStatics  {

	private static final Logger log = LogManager.getLogger(UserInterceptor.class);

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private NedPerson loggedOnUser;	
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		final ActionContext context = invocation.getInvocationContext();
		final HttpServletRequest request = (HttpServletRequest) context.get(HTTP_REQUEST);		

		if (StringUtils.isBlank(loggedOnUser.getAdUserId())) {

			NedPerson nedPerson = null;

			//get the remoteUser from the SM_USER header (SiteMinder) or Authorization header.
			String remoteUser = request.getHeader("SM_USER");
			log.info("User login from Siteminder SM_USER = " + remoteUser);			

			if (StringUtils.isBlank(remoteUser)) {

				log.info("Remote user from SM_USER is null; trying Authorization header");
				String authUser = request.getHeader("Authorization");

				if (StringUtils.isNotBlank(authUser)) {

					authUser = new String(Base64.decodeBase64(authUser.substring(6)));
					remoteUser = authUser.substring(0, authUser.indexOf(":"));
					log.info("User login from Auth Header: " + remoteUser);
				}
			}

			if (StringUtils.isNotBlank(remoteUser)) {

				nedPerson = userRoleService.findNedPersonByUserId(remoteUser);

				if (nedPerson == null) {
					log.error("NedPerson could not be found for userId:  " + remoteUser);
					return ApplicationConstants.NOT_AUTHORIZED;
				}

				if (!hasValidRole(nedPerson)) {
					log.error("Insufficient privileges for user " + remoteUser);
					return ApplicationConstants.NOT_AUTHORIZED;
				}
				BeanUtils.copyProperties(nedPerson, loggedOnUser);					
			}
			else{
				 throw new Exception("Site Minder did not pass the SM User"); 
			}
		}
		return invocation.invoke();
				
	}

	/**
	 * Validate the roles of the loggedOnUser.
	 * @param user
	 * @return true, if successful
	 */
	private boolean hasValidRole(final NedPerson user) {
		return true;
	}	
}

package gov.nih.nci.cbiit.scimgmt.gds.interceptor;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.domain.UserRole;
import gov.nih.nci.cbiit.scimgmt.gds.services.LookupService;
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

	private static final Logger logger = LogManager.getLogger(UserInterceptor.class);

	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private LookupService lookupService;

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
			logger.info("User login from Siteminder SM_USER = " + remoteUser);			

			if (StringUtils.isBlank(remoteUser)) {

				logger.info("Remote user from SM_USER is null; trying Authorization header");
				String authUser = request.getHeader("Authorization");

				if (StringUtils.isNotBlank(authUser)) {

					authUser = new String(Base64.decodeBase64(authUser.substring(6)));
					remoteUser = authUser.substring(0, authUser.indexOf(":"));
					logger.info("User login from Auth Header: " + remoteUser);
				}
			}

			if (StringUtils.isNotBlank(remoteUser)) {

				nedPerson = userRoleService.findNedPersonByUserId(remoteUser);

				if (nedPerson == null) {
					logger.error("NedPerson could not be found for userId:  " + remoteUser);
					return ApplicationConstants.NOT_AUTHORIZED;
				} else {
					UserRole userRole = nedPerson.getUserRole();
					if(userRole != null) {
						String roleCode = userRole.getGdsRoleCode();
						Lookup gdsRole = lookupService.getLookupByCode(ApplicationConstants.ROLE_TYPE, roleCode);
						if(gdsRole != null)
							logger.info("GDS role for user " + remoteUser + " is " + gdsRole.getDescription());
					}
				}

				//When we do have user roles in the application, in UserInterceptor, 
				//bypass hasValidRole check for Sysadmin in case sys.admin user doesn't have access in production
				if (!hasValidRole(nedPerson)) {
					logger.error("Insufficient privileges for user " + remoteUser);
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
		if(user.getUserRole() != null)
			return true;
		else
			return false;
	}	
}

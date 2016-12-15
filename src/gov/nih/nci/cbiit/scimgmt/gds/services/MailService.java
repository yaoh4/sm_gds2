package gov.nih.nci.cbiit.scimgmt.gds.services;

import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PersonRole;

public interface MailService {

	/**
	 * Send error message.
	 * 
	 * @param exceptionStack
	 *            the exception stack
	 * @param userErrorMessage
	 *            the user error message
	 * @param loggedOnUser
	 *            the logged on user
	 */
	public void sendErrorMessage(String exceptionStack, String userErrorMessage, NedPerson loggedOnUser);

	/**
	 * Send one time and summary emails to GPA for Extramural Submissions
	 * @throws Exception 
	 */
	public void sendExtramuralEmail() throws Exception;

	/**
	 * Send one time and summary emails to GPA for Intramural Submissions
	 */
	public void sendIntramuralEmail() throws Exception;

	/**
	 * Send notification to user when a role is added.
	 */
	public void sendRoleAddedToUser(PersonRole personRole) throws Exception;
}

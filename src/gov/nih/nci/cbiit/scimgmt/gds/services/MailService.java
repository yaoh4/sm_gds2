package gov.nih.nci.cbiit.scimgmt.gds.services;

import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;

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
	 * Send weekly emails to GPA for Extramural Submissions
	 */
	public void sendWeeklyExtramuralEmail();

	/**
	 * Send weekly emails to GPA for Intramural Submissions
	 */
	public void sendWeeklyIntramuralEmail();

}

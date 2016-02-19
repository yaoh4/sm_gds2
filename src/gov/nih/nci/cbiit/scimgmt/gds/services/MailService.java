package gov.nih.nci.cbiit.scimgmt.gds.services;

import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;

public interface MailService {

	public static final String TO = "to";
	public static final String ATTACHMENTS = "attachments";
	public static final String CC = "cc";
	public static final String BCC = "bcc";
	public static final String FROM = "from";
	static final String LOGGED_ON_USER = "loggedOnUser";
	static final String MESSAGE = "message";
	static final String EXCEPTION_STACK = "exceptionStack";
	static final String FROM_DISPLAY = "fromDisplay";

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

}

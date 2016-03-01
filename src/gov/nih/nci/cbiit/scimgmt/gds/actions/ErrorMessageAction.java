package  gov.nih.nci.cbiit.scimgmt.gds.actions;

import gov.nih.nci.cbiit.scimgmt.gds.services.MailService;

import java.text.DateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class is responsible for sending Error Email to the the application support.
 * @author tembharend
 */
@SuppressWarnings("serial")
public class ErrorMessageAction extends BaseAction {
	
	private static final Logger logger = LogManager.getLogger(ErrorMessageAction.class);
	
	private String exceptionStack;
	private String message;
	
	@Autowired
	protected MailService mailService;	
	
	/**
	 * Sends error e-mails.
	 */
	public String execute() throws Exception {
		logger.error("===================================> Error Report <===================================");
		logger.error("=====> Unexpected error reported by: " + getLoggedOnUser().getFullName() + " at " + DateFormat.getInstance().format(new Date()));
		logger.error("=====> Exception stack: " + exceptionStack);
		logger.error("=====> Reported message: " + message);
		logger.error("===================================> End Error Report <===================================");

		mailService.sendErrorMessage(exceptionStack, message, getLoggedOnUser());
		return SUCCESS;
	}

	/**
	 * @return the exceptionStack
	 */
	public String getExceptionStack() {
		return exceptionStack;
	}

	/**
	 * @param exceptionStack the exceptionStack to set
	 */
	public void setExceptionStack(String exceptionStack) {
		this.exceptionStack = exceptionStack;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}

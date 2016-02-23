package  gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.text.DateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is responsible for sending Error Email to the the application support.
 */
@SuppressWarnings("serial")
public class ErrorMessageAction extends BaseAction {
	
	private static final Logger log = LogManager.getLogger(ErrorMessageAction.class);
	
	private String exceptionStack;
	private String message;
	
	/**
	 * Sends error e-mails.
	 */
	public String execute() throws Exception {
		log.error("===================================> Error Report <===================================");
		log.error("=====> Unexpected error reported by: " + getLoggedOnUser().getFullName() + " at " + DateFormat.getInstance().format(new Date()));
		log.error("=====> Exception stack: " + exceptionStack);
		log.error("=====> Reported message: " + message);
		log.error("===================================> End Error Report <===================================");

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

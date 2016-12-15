package gov.nih.nci.cbiit.scimgmt.gds.util;

import gov.nih.nci.cbiit.scimgmt.gds.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class AutomatedSendReminderMailTask.
 */
@Component
public class AutomatedSendEmailTask {

	@Autowired
	protected MailService mailService;
	@Autowired
	private GdsProperties gdsProperties;
	
	static final Logger logger = LogManager.getLogger(AutomatedSendEmailTask.class.getName());

	/**
	 * Send emails to GPA for Extramural Submissions
	 */
	public void sendExtramuralEmail() {
		if (gdsProperties.getProperty("email.extramural.sendflag", "Y").equalsIgnoreCase("N")) {
			logger.info("=====> Extramural Reminder emails to GPA is turned off. Property Name: email.extramural.sendflag");
			return;
		}
		logger.info("=====> Sending Extramural Reminder emails to GPA");
		try {
			//Send email			
			mailService.sendExtramuralEmail();
		} catch (Exception e) {
			logger.error("=====> Exception occurred while Sending Extramural Reminder emails to GPA", e);
		}
	}
	
	/**
	 * Send emails to GPA for Intramural Submissions
	 */
	public void sendIntramuralEmail() {
		if (gdsProperties.getProperty("email.intramural.sendflag", "Y").equalsIgnoreCase("N")) {
			logger.info("=====> Intramural Reminder emails to GPA is turned off. Property Name: email.intramural.sendflag");
			return;
		}
		logger.info("=====> Sending Intramural Reminder emails to GPA");
		try {
			//Send email			
			mailService.sendIntramuralEmail();
		} catch (Exception e) {
			logger.error("=====> Exception occurred while Sending Intramural Reminder emails to GPA", e);
		}
	}
	
}

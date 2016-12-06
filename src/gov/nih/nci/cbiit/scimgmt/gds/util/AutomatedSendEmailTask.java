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
	 * Send weekly emails to GPA for Extramural Submissions
	 */
	public void sendWeeklyExtramuralEmail() {
		if (gdsProperties.getProperty("email.weekly.extramural.sendflag", "Y").equalsIgnoreCase("N")) {
			logger.info("=====> Weekly Extramural Reminder emails to GPA is turned off. Property Name: email.weekly.extramural.sendflag");
			return;
		}
		logger.info("=====> Sending Weekly Extramural Reminder emails to GPA");
		try {
			//Send email			
			mailService.sendWeeklyExtramuralEmail();
		} catch (Exception e) {
			logger.error("=====> Exception occurred while Sending Weekly Extramural Reminder emails to GPA", e);
		}
	}
	
	/**
	 * Send weekly emails to GPA for Intramural Submissions
	 */
	public void sendWeeklyIntramuralEmail() {
		if (gdsProperties.getProperty("email.weekly.intramural.sendflag", "Y").equalsIgnoreCase("N")) {
			logger.info("=====> Weekly Intramural Reminder emails to GPA is turned off. Property Name: email.weekly.intramural.sendflag");
			return;
		}
		logger.info("=====> Sending Weekly Intramural Reminder emails to GPA");
		try {
			//Send email			
			mailService.sendWeeklyIntramuralEmail();
		} catch (Exception e) {
			logger.error("=====> Exception occurred while Sending Weekly Intramural Reminder emails to GPA", e);
		}
	}
	
}

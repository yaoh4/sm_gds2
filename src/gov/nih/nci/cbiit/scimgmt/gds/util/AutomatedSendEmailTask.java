package gov.nih.nci.cbiit.scimgmt.gds.util;

import gov.nih.nci.cbiit.scimgmt.gds.domain.UserRole;
import gov.nih.nci.cbiit.scimgmt.gds.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
	public void sendExtramuralSummaryEmail() {
		if (gdsProperties.getProperty("email.extramural.sendflag", "Y").equalsIgnoreCase("N")) {
			logger.info("=====> Extramural Reminder emails to GPA is turned off. Property Name: email.extramural.sendflag");
			return;
		}
		logger.info("=====> Sending Extramural Reminder emails to GPA");
		try {
			//Send email			
			mailService.sendExtramuralSummaryEmail();
		} catch (Exception e) {
			logger.error("=====> Exception occurred while Sending Extramural Reminder emails to GPA", e);
		}
		logger.info("=====> Finished Sending Extramural Reminder emails to GPA");
	}
	
	public void sendExtramuralOneTimeEmails() {
		if (gdsProperties.getProperty("email.extramural.sendflag", "Y").equalsIgnoreCase("N")) {
			logger.info("=====> Extramural Reminder emails to PD/Creator is turned off. Property Name: email.extramural.sendflag");
			return;
		}
		logger.info("=====> Sending Extramural Reminder emails to PD/Creator");
		try {
			
			List<UserRole> gpas = mailService.retrieveGpas();
			
			// For each submission, send an email to the creator and PD of the submission if not sent yet.
			mailService.sendExtramuralEmailByTemplate(mailService.getExtramuralPastSubmissionDate(), "EXT_PAST_SUB", gpas);
			
			mailService.sendExtramuralEmailByTemplate(mailService.getExtramuralBsiInProgress(), "EXT_BSI_IN_PROG", gpas);
			
			mailService.sendExtramuralEmailByTemplate(mailService.getExtramuralGdsIcInProgress(), "EXT_IC_IN_PROG", gpas);
			
			mailService.sendExtramuralEmailByTemplate(mailService.getExtramuralBudgetEndDateComing(), "EXT_BSI_BUD_END_45", gpas);
			
			mailService.sendExtramuralEmailByTemplate(mailService.getExtramuralPastProjectEndDate(), "EXT_PAST_PROJ_END", gpas);
			
			mailService.sendExtramuralEmailByTemplate(mailService.getExtramuralProjectEndDateComing(30), "EXT_PROJ_END_30", gpas);
			
			mailService.sendExtramuralEmailByTemplate(mailService.getExtramuralProjectEndDateComing(60), "EXT_PROJ_END_60", gpas);
			
			mailService.sendExtramuralEmailByTemplate(mailService.getExtramuralProjectEndDateComing(90), "EXT_PROJ_END_90", gpas);
			
		} catch (Exception e) {
			logger.error("=====> Exception occurred while Sending Extramural Reminder emails to PD/Creator", e);
		}
		logger.info("=====> Finished Sending Extramural Reminder emails to PD/Creator");
	}
	/**
	 * Send emails to GPA for Intramural Submissions
	 */
	public void sendIntramuralSummaryEmail() {
		if (gdsProperties.getProperty("email.intramural.sendflag", "Y").equalsIgnoreCase("N")) {
			logger.info("=====> Intramural Reminder emails to GPA is turned off. Property Name: email.intramural.sendflag");
			return;
		}
		logger.info("=====> Sending Intramural Reminder emails to GPA");
		try {
			//Send email			
			mailService.sendIntramuralSummaryEmail();
		} catch (Exception e) {
			logger.error("=====> Exception occurred while Sending Intramural Reminder emails to GPA", e);
		}
		logger.info("=====> Finished Sending Intramural Reminder emails to GPA");
	}
	
}

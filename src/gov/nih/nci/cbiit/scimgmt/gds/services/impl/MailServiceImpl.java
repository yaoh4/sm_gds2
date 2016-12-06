package gov.nih.nci.cbiit.scimgmt.gds.services.impl;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.dao.MailTemplateDao;
import gov.nih.nci.cbiit.scimgmt.gds.dao.NotificationsDao;
import gov.nih.nci.cbiit.scimgmt.gds.domain.MailTemplate;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectsVw;
import gov.nih.nci.cbiit.scimgmt.gds.services.MailService;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsProperties;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailServiceImpl implements MailService {
	static final Logger logger = LogManager.getLogger(MailServiceImpl.class.getName());
	static final String TO = "to";
	static final String ATTACHMENTS = "attachments";
	static final String CC = "cc";
	static final String BCC = "bcc";
	static final String FROM = "from";
	static final String LOGGED_ON_USER = "loggedOnUser";
	static final String MESSAGE = "message";
	static final String EXCEPTION_STACK = "exceptionStack";
	static final String FROM_DISPLAY = "fromDisplay";
	
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private MailTemplateDao mailTemplateDao;
	@Autowired
	private VelocityEngine velocityEngine;
	@Autowired
	private NedPerson loggedOnUser;
	@Autowired
	private GdsProperties gdsProperties;
	@Autowired
	private NotificationsDao notificationsDao;
	
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
	@Override
	public void sendErrorMessage(final String exceptionStack, String message, final NedPerson loggedOnUser) {
		logger.info("Sending error message");
		final Map<String, Object> params = new HashMap<String, Object>();
		String[] to = null;
		final String errorReportingEmail = gdsProperties.getProperty(ApplicationConstants.ERROR_EMAIL);
		final String from = gdsProperties.getProperty(ApplicationConstants.EMAIL_FROM);
		final String fromDisplay = gdsProperties.getProperty(ApplicationConstants.EMAIL_FROM_DISPLAY);

		logger.info("errorReportingEmail: " + errorReportingEmail);
		if (StringUtils.isNotBlank(errorReportingEmail)) {
			to = parse(errorReportingEmail);
		}

		if (message == null || message.isEmpty()) {
			message = "Exception Report";
		}
		params.put(LOGGED_ON_USER, loggedOnUser);
		params.put(MESSAGE, message);
		params.put(EXCEPTION_STACK, exceptionStack);
		params.put(TO, to);
		params.put(FROM, from);
		params.put(FROM_DISPLAY, fromDisplay);

		final String env = gdsProperties.getProperty(ApplicationConstants.ENVIRONMENT);

		if (!env.toLowerCase().startsWith("prod") || to != null && to.length > 0) {
			send("ERROR_REPORT", params);
		} else {
			logger.error("Unable to send error message: no TO: addresses found");
		}
	}
	
	/**
	 * Gets the mail template given a short identifier
	 * 
	 * @param shortIdentifier
	 * @return
	 */
	private MailTemplate findByShortIdentifier(final String shortIdentifier) {
		return mailTemplateDao.findByShortIdentifier(shortIdentifier);
	}

	/**
	 * Parses the source string and returns an array of strings.
	 * 
	 * Separator character is a comma.
	 * 
	 * @param source
	 *            the source
	 * @return the string[]
	 */
	private String[] parse(final String source) {
		if (StringUtils.isBlank(source)) {
			return null;
		}
		return source.split(",");
	}

	/**
	 * Very simple mail sender method using Velocity templates pulled from a
	 * database.
	 * The message template is retrieved from the database using the short
	 * identifier.
	 * The subject and body are evaluated by the Velocity Engine, and all
	 * parameter substitutions are performed.
	 * The Map of parameters provided should include all the values expected by
	 * the subject and body, but no error checking is done to confirm this.
	 * 
	 * @param identifier
	 * @param params
	 */
	private void send(final String identifier, final Map<String, Object> params) {

		final MailTemplate template = findByShortIdentifier(identifier);

		if (template != null) {
			if (!template.getActiveFlag()) {
				logger.info("Sending mail is turned off for template: " + identifier);
				return;
			}
			final VelocityContext vc = new VelocityContext(params);
			final String[] to = (String[]) vc.get(TO);
			final String from = (String) vc.get(FROM);
			final String fromDisplay = (String) vc.get(FROM_DISPLAY);
			final Map<String, File> attachments = (Map<String, File>) params.get(ATTACHMENTS);

			if (to != null) {
				final String[] cc = (String[]) vc.get(CC);
				final String[] bcc = (String[]) vc.get(BCC);

				final StringWriter body = new StringWriter();
				final StringWriter subjectWriter = new StringWriter();

				try {
					velocityEngine.evaluate(vc, body, identifier, template.getBody());
					velocityEngine.evaluate(vc, subjectWriter, identifier, template.getSubject());
					logger.info("evaluating email template: " + body.toString());
					logger.info("sending message....." + identifier + " with params..... " + params);

					final MimeMessageHelper helper = new MimeMessageHelper(mailSender.createMimeMessage(), true,
							"UTF-8");
					String subject = subjectWriter.toString();

					final String env = gdsProperties.getProperty(ApplicationConstants.ENVIRONMENT);

					if (env.toLowerCase().startsWith("prod")) {
						helper.setTo(to);

						if (cc != null) {
							helper.setCc(cc);
						}
						if (bcc != null) {
							helper.setBcc(bcc);
						}
					} else {
						subject = "[" + env.toUpperCase() + "] " + subject;
						subject += " {TO: " + StringUtils.join(to, ',') + "} {CC: " + StringUtils.join(cc, ',') + "}";
						final String[] overrideAddrs = {loggedOnUser.getEmail()};
						helper.setTo(overrideAddrs);
					}

					helper.setText(body.toString(), true);
					helper.setSubject(subject);

					if (StringUtils.isNotBlank(fromDisplay)) {
						helper.setFrom(from, fromDisplay);
					} else {
						helper.setFrom(from);
					}

					/* handle attachments with a MimeMessageHelper */
					if (attachments != null) {
						for (final Map.Entry<String, File> e : attachments.entrySet()) {
							helper.addAttachment(e.getKey(), e.getValue());
						}
					}

					logger.info("invoking mailSender");
					mailSender.send(helper.getMimeMessage());
					logger.info("done invoking mailSender");

				} catch (final VelocityException e) {
					logger.error("VelocityException", e);
				} catch (final IOException e) {
					logger.error("IOException", e);
				} catch (final MessagingException e) {
					logger.error("MessagingException", e);
				}
			} else {
				logger.error("required parameter 'to' not found");
			}
		} else {
			logger.error("No message with identifier '" + identifier + "' found");
		}
	}

	/**
	 * Send weekly emails to GPA for Extramural Submissions
	 */
	@Override
	public void sendWeeklyExtramuralEmail() {
		List<ProjectsVw> result = notificationsDao.getExtramuralPastSubmissionDate();
		result = notificationsDao.getExtramuralBsiInProgress();
		result = notificationsDao.getExtramuralGdsIcInProgress();
	}

	/**
	 * Send weekly emails to GPA for Intramural Submissions
	 */
	@Override
	public void sendWeeklyIntramuralEmail() {
		List<ProjectsVw> result = notificationsDao.getIntramuralPastSubmissionDate();
		result = notificationsDao.getIntramuralBsiInProgress();
		result = notificationsDao.getIntramuralGdsIcInProgress();
	}


}

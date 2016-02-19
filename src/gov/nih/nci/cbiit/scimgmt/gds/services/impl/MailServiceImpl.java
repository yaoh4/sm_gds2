package gov.nih.nci.cbiit.scimgmt.gds.services.impl;

import gov.nih.nci.cbiit.scimgmt.gds.dao.MailTemplateDao;
import gov.nih.nci.cbiit.scimgmt.gds.domain.MailTemplate;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.services.MailService;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
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
	static final Logger log = LogManager.getLogger(MailServiceImpl.class.getName());
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private MailTemplateDao mailTemplateDao;
	@Autowired
	private VelocityEngine velocityEngine;
	@Autowired
	private NedPerson loggedOnUser;

	@SuppressWarnings("unused")
	private String applicationUrl;

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
	 * <p>
	 * Very simple mail sender method using Velocity templates pulled from a
	 * database.
	 * </p>
	 * <p>
	 * The message template is retrieved from the database using the short
	 * identifier.
	 * </p>
	 * <p>
	 * The subject and body are evaluated by the Velocity Engine, and all
	 * parameter substitutions are performed.
	 * </p>
	 * <p>
	 * The Map of parameters provided should include all the values expected by
	 * the subject and body, but no error checking is done to confirm this.
	 * </p>
	 * <p>
	 * No errors are thrown if the send fails, velocity evaluation fails, or the
	 * requested template is not found, but it will be logged.
	 * </p>
	 * <p>
	 * If no 'to' parameter is provided the send will log an error message and
	 * exit.
	 * </p>
	 * <p>
	 * Potential error conditions:
	 * </p>
	 * 
	 * <ul>
	 * <li>The specified template isn't found.</li>
	 * <li>The 'to' parameter is not provided.</li>
	 * <li>Velocity evaluation fails.</li>
	 * <li>Mail sending fails.</li>
	 * <li>Attachment can't be attached.</li>
	 * </ul>
	 * <p>
	 * Parameters (not including those required by the template):
	 * </p>
	 * <ul>
	 * <li>to - a String[] of addresses to send the message to {required}</li>
	 * <li>cc, bcc - String[] of addresses for the cc and bcc fields,
	 * respectively {optional}</li>
	 * <li>attachments - a Map<String, File> containing names and Files of all
	 * attachments</li>
	 * </ul>
	 * 
	 * @param identifier
	 *            the identifier
	 * @param params
	 *            the params
	 */
	private void send(final String identifier, final Map<String, Object> params) {

		final MailTemplate template = findByShortIdentifier(identifier);

		if (template != null) {
			if (!template.getActive()) {
				log.info("Sending mail is turned off for template: " + identifier);
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
					log.info("evaluating email template: " + body.toString());
					log.info("sending message....." + identifier + " with params..... " + params);

					final MimeMessageHelper helper = new MimeMessageHelper(mailSender.createMimeMessage(), true,
							"UTF-8");
					String subject = subjectWriter.toString();

					final String env = "dev";//Properties.getProperty("app.environment", "dev");

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
						final String[] overrideAddrs = StringUtils
								.split("yuri.dinh@nih.gov"/*Properties.getProperty("mail.override.addresses")*/);
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

					log.info("invoking mailSender");
					mailSender.send(helper.getMimeMessage());
					log.info("done invoking mailSender");

				} catch (final VelocityException e) {
					log.error("VelocityException", e);
				} catch (final IOException e) {
					log.error("IOException", e);
				} catch (final MessagingException e) {
					log.error("MessagingException", e);
				}
			} else {
				log.error("required parameter 'to' not found");
			}
		} else {
			log.error("No message with identifier '" + identifier + "' found");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.nih.nci.cbiit.scimgmt.telework.service.MailService#sendErrorMessage
	 * (java.lang.String, java.lang.String,
	 * gov.nih.nci.cbiit.scimgmt.telework.domain.NedPerson)
	 */
	@Override
	public void sendErrorMessage(final String exceptionStack, String message, final NedPerson loggedOnUser) {
		log.info("Sending error message");
		final Map<String, Object> params = new HashMap<String, Object>();
		String[] to = null;
		final String errorReportingEmail = "yuri.dinh@nih.gov";//Properties.getProperty("mail.error.addresses", null);
		final String from = "yuri.dinh@nih.gov";//Properties.getProperty("email.from", null);
		final String fromDisplay = "yuri.dinh@nih.gov";//Properties.getProperty("email.from.display", null);

		log.info("errorReportingEmail: " + errorReportingEmail);
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

		if (to != null && to.length > 0) {
			send("ERROR_REPORT", params);
		} else {
			log.error("Unable to send error message: no TO: addresses found");
		}
	}
}

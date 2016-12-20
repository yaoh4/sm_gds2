package gov.nih.nci.cbiit.scimgmt.gds.services.impl;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.dao.MailTemplateDao;
import gov.nih.nci.cbiit.scimgmt.gds.dao.NotificationsDao;
import gov.nih.nci.cbiit.scimgmt.gds.dao.PropertyListDao;
import gov.nih.nci.cbiit.scimgmt.gds.dao.UserRoleDao;
import gov.nih.nci.cbiit.scimgmt.gds.domain.EmailNotification;
import gov.nih.nci.cbiit.scimgmt.gds.domain.MailTemplate;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Organization;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PersonRole;
import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectsVw;
import gov.nih.nci.cbiit.scimgmt.gds.domain.UserRole;
import gov.nih.nci.cbiit.scimgmt.gds.model.RoleSearchCriteria;
import gov.nih.nci.cbiit.scimgmt.gds.services.MailService;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsProperties;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.CollectionUtils;

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
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private PropertyListDao propertyListDAO;
	
	private Properties emailTemplates;
	
	private String pastSubmissionDateFlag;
	private String bsiInProgressFlag;
	private String gdsIcInProgressFlag;
	private String budgetEndDateComingFlag;
	private String pastProjectEndDateFlag;
	private String projectEndDateComingFlag;
	
	private List<ProjectsVw> pastSubmissionDateResult = new ArrayList<ProjectsVw>();
	private List<ProjectsVw> bsiInProgressResult = new ArrayList<ProjectsVw>();
	private List<ProjectsVw> gdsIcInProgressResult = new ArrayList<ProjectsVw>();

	private List<ProjectsVw> pastSubmissionDateResultAll = new ArrayList<ProjectsVw>();
	private List<ProjectsVw> bsiInProgressResultAll = new ArrayList<ProjectsVw>();
	private List<ProjectsVw> gdsIcInProgressResultAll = new ArrayList<ProjectsVw>();
	
	private List<ProjectsVw> budgetEndDateComingResult = new ArrayList<ProjectsVw>();
	private List<ProjectsVw> pastProjectEndDateResult = new ArrayList<ProjectsVw>();
	private List<ProjectsVw> projectEndDateComingResult = new ArrayList<ProjectsVw>();

	private List<ProjectsVw> budgetEndDateComingResultAll = new ArrayList<ProjectsVw>();
	private List<ProjectsVw> pastProjectEndDateResultAll = new ArrayList<ProjectsVw>();
	private List<ProjectsVw> projectEndDateComingResultAll = new ArrayList<ProjectsVw>();

	Map<String,List<String>> orgMapEmail = new HashMap<String, List<String>>();
	Map<String,List<String>> orgMapName = new HashMap<String, List<String>>();
	Map<String,String> orgMapAcronym = new HashMap<String, String>();
	Map<String,String> orgMapFullName = new HashMap<String, String>();
	
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
	 * Send notification to user when a role is added.
	 * @throws Exception 
	 */
	@Override
	public void sendRoleAddedToUser(PersonRole personRole) throws Exception {

		logger.info("Sending notification to user when a role is added");
		NedPerson user = userRoleDao.findNedPersonByUserId(personRole.getNihNetworkId());
		String newRole = personRole.getRole().getDescription();
		
		final Map<String, Object> params = new HashMap<String, Object>();
		
		setCommonParams(params);
		String[] to = {user.getEmail()};

		params.put(LOGGED_ON_USER, loggedOnUser);
		params.put(TO, to);
		params.put("user", user);
		params.put("newRole", newRole);

		send("ROLE_ADDED", params);
	}

	/**
	 * Send summary emails to GPA for Extramural Submissions
	 * @throws Exception 
	 */
	@Override
	public void sendExtramuralSummaryEmail() throws Exception {
		
		final Map<String, Object> params = new HashMap<String, Object>();
		
		setCommonParams(params);
		params.put("system", "true");

		sendSummaryEmail(params, "EXT_SUMMARY");

	}
	
	/**
	 * Send one time emails to PD and creator for Extramural Submissions
	 * @throws Exception 
	 */
	@Override
	public void sendExtramuralEmailByTemplate(List<ProjectsVw> list, String template, List<UserRole> gpas) throws Exception {
		
		final Map<String, Object> params = new HashMap<String, Object>();
		
		setCommonParams(params);
		params.put("system", "true");
		
		sendExtramuralPdCreatorEmail(params, list, template, gpas);

	}

	/**
	 * Send summary emails to GPA for Intramural Submissions
	 */
	@Override
	public void sendIntramuralSummaryEmail() throws Exception {
		
		final Map<String, Object> params = new HashMap<String, Object>();
		
		setCommonParams(params);
		params.put("system", "true");
		
		sendSummaryEmail(params, "INT_SUMMARY");
		
	}
	
	/**
	 * Retrieve a lit of submissions where anticipated submission date is in the past
	 */
	public List<ProjectsVw> getExtramuralPastSubmissionDate() {
		return notificationsDao.getExtramuralPastSubmissionDate();
	}
	
	/**
	 * Retrieve a lit of submissions where BSI is in progress
	 */
	public List<ProjectsVw> getExtramuralBsiInProgress() {
		return notificationsDao.getExtramuralBsiInProgress();
	}
	
	/**
	 * Retrieve a lit of submissions where GDS and IC is in progress
	 */
	public List<ProjectsVw> getExtramuralGdsIcInProgress() {
		return notificationsDao.getExtramuralGdsIcInProgress();
	}
	
	/**
	 * Retrieve a lit of submissions where the budget end is in 45 days
	 */
	public List<ProjectsVw> getExtramuralBudgetEndDateComing() {
		return notificationsDao.getExtramuralBudgetEndDateComing();
	}
	
	/**
	 * Retrieve a lit of submissions where project end date is in the past
	 */
	public List<ProjectsVw> getExtramuralPastProjectEndDate() {
		return notificationsDao.getExtramuralPastProjectEndDate();
	}
	
	/**
	 * Retrieve a lit of submissions where project end date is in x days
	 */
	public List<ProjectsVw> getExtramuralProjectEndDateComing(Integer days) {
		return notificationsDao.getExtramuralProjectEndDateComing(days);
	}
	
	/**
	 * Retrieve a list of GPAs in the system.
	 */
	public List<UserRole> retrieveGpas() {
		RoleSearchCriteria gpaCriteria = new RoleSearchCriteria();
		gpaCriteria.setGdsUsersOnly(true);
		gpaCriteria.setRoleCode(ApplicationConstants.ROLE_GPA_CODE);
		gpaCriteria.setDoc("%");
		return userRoleDao.searchUserRole(gpaCriteria);
	}

	/**
	 * Sends Extramural One time emails to PD and Creator of the submission based on template
	 * 
	 * @param params
	 * @param submissionAll
	 * @param template
	 * @param gpas
	 * @throws Exception
	 */
	private void sendExtramuralPdCreatorEmail(Map<String, Object> params, List<ProjectsVw> submissionAll, String template, List<UserRole> gpas) throws Exception {
		
		MailTemplate mailTemplate = findByShortIdentifier(template);
		
		for(ProjectsVw submission: submissionAll) {
			// Check to see if this has already been sent, if so, continue
			List<EmailNotification> logs = notificationsDao.findLogByTemplateAndProjectId(mailTemplate.getId(), submission.getId());
			if(!CollectionUtils.isEmpty(logs)) {
				continue;
			}
			
			NedPerson nedPerson = userRoleDao.findNedPersonByUserId(submission.getCreatedBy());
			String pdName = (StringUtils.isEmpty(submission.getExtPdEmailAddress())? null: submission.getExtPdFirstName() + " " + submission.getExtPdLastName());
			String pdEmail = (StringUtils.isEmpty(submission.getExtPdEmailAddress())? null: submission.getExtPdEmailAddress());
			String creatorName = (nedPerson == null || isGpa(submission.getCreatedBy(), gpas)? null: nedPerson.getFullName());
			String creatorEmail = (nedPerson == null || isGpa(submission.getCreatedBy(), gpas)? null: nedPerson.getEmail());
			// If email can not be sent, continue
			if(pdName == null && creatorName == null) {
				logger.error("Email template: " + mailTemplate.getId() + " for submission ID: " + submission.getId() + " could not be sent to PD nor creator.");
				continue;
			}
			
			ArrayList<String> emails = new ArrayList<String>();
			if(pdEmail != null)
				emails.add(pdEmail);
			if(creatorEmail != null)
				emails.add(creatorEmail);
			String[] to = emails.toArray(new String[emails.size()]);
			params.put(TO, to);
			params.put("dear", (pdName == null? creatorName: (creatorName == null? pdName: pdName + ", " + creatorName)));
			params.put("submission", submission);
			
			send(template, params);
			
			//Insert record into EMAIL_NOTIFICATIONS_T
			insertEmailLogByProjectId(mailTemplate.getId(), submission.getId(), to);
		}
	}
	
	/**
	 * Utility method to determine whether the user is GPA
	 * 
	 * @param userId
	 * @param gpas
	 * @return
	 */
	private boolean isGpa(String userId, List<UserRole> gpas) {
		for(UserRole gpa: gpas) {
			if(StringUtils.equalsIgnoreCase(gpa.getNihNetworkId(), userId))
				return true;
		}
		return false;
	}

	/**
	 * Sends Summary email for Extramural/Intramural Submissions.
	 * 
	 * @param params
	 * @param template
	 * @throws Exception
	 */
	private synchronized void sendSummaryEmail(Map<String, Object> params, String template) throws Exception {

		MailTemplate mailTemplate = findByShortIdentifier(template);
		// 1. Loop through all GPAs and get their email address and hash them using their DOC
		populateGpaOrgMap();

		if(StringUtils.equals(template, "EXT_SUMMARY"))
			retrieveExtramuralResults();
		else
			retrieveIntramuralResults();
		// 2. For each DOC, send the summary email if not sent yet. Filter out submission for that DOC only
		for (Map.Entry<String, List<String>> entry : orgMapEmail.entrySet()) {

			String[] to = entry.getValue().toArray(new String[entry.getValue().size()]);
			String dear = constructDearForDoc(entry.getKey());

			populateSummaryResultForDoc(entry.getKey());
			
			if (StringUtils.equals(pastSubmissionDateFlag, "Y") || StringUtils.equals(bsiInProgressFlag, "Y")
					|| StringUtils.equals(gdsIcInProgressFlag, "Y")) {

				// Check to see if this has already been sent, if so, continue
				List<EmailNotification> logs = notificationsDao.findLogByTemplateAndDoc(template, mailTemplate.getId(), entry.getKey());
				if(!CollectionUtils.isEmpty(logs)) {
					continue;
				}
				
				setSummaryParams(params, to, dear, entry.getKey());
	
				send(template, params);
				
				//Insert record into EMAIL_NOTIFICATIONS_T
				insertEmailLogByDoc(mailTemplate.getId(), entry.getKey(), to);
			}

		}

		// 3. These are the Submissions which doesn't have GPA for that DOC.
		if(!pastSubmissionDateResultAll.isEmpty() || !bsiInProgressResultAll.isEmpty()
			|| !gdsIcInProgressResultAll.isEmpty()) {
				
			List<EmailNotification> logs = notificationsDao.findLogByTemplateAndDoc(template, mailTemplate.getId(), "All");
			if(!CollectionUtils.isEmpty(logs)) {
			
				String[] to = { "test@test.com" };
				setSummaryParams(params, to, "To Whom It May Concern", "All");

				send(template, params);
				
				//Insert record into EMAIL_NOTIFICATIONS_T
				insertEmailLogByDoc(mailTemplate.getId(), "All", to);
			}
		}
	}
	
	/**
	 * Retrieve Intramural submissions which goes into the summary email
	 */
	private void retrieveIntramuralResults() {
		// Get the Submissions for Extramural Notifications
		pastSubmissionDateResultAll = notificationsDao.getIntramuralAll();
		bsiInProgressResultAll.clear();
		gdsIcInProgressResultAll.clear();
	}

	/**
	 * Retrieve Extramural submissions which goes into the summary email
	 */
	private void retrieveExtramuralResults() {
		// Get the Submissions for Extramural Notifications
		pastSubmissionDateResultAll = getExtramuralPastSubmissionDate();
		bsiInProgressResultAll = getExtramuralBsiInProgress();
		gdsIcInProgressResultAll = getExtramuralGdsIcInProgress();
		budgetEndDateComingResultAll = getExtramuralBudgetEndDateComing();
		pastProjectEndDateResultAll = getExtramuralPastProjectEndDate();
		projectEndDateComingResultAll = getExtramuralProjectEndDateComing(90);
	}
	
	/**
	 * Populate the GPAs based on their DOC
	 * 
	 * @throws Exception
	 */
	private void populateGpaOrgMap() throws Exception {
		orgMapEmail.clear();
		orgMapName.clear();
		orgMapAcronym.clear();
		orgMapFullName.clear();
		
		List<Organization> docListFromDb = propertyListDAO.getDocList(ApplicationConstants.DOC_LIST.toUpperCase());
		for(Organization org: docListFromDb) {
			orgMapAcronym.put(new String(org.getNihorgpath()), new String(org.getNihouacronym()));
			orgMapFullName.put(new String(org.getNihorgpath()), new String(WordUtils.capitalizeFully(org.getNihouname())));
		}
		
		for (UserRole gpa: retrieveGpas()) {
					
			NedPerson nedPerson = userRoleDao.findNedPersonByUserId(gpa.getNihNetworkId());

			String doc = GdsSubmissionActionHelper.getLoggedonUsersDOC(docListFromDb, nedPerson.getNihsac());
			if(!orgMapEmail.containsKey(doc)) {
				orgMapEmail.put(doc,new ArrayList<String>());
				orgMapName.put(doc,new ArrayList<String>());
			}
			orgMapEmail.get(doc).add(nedPerson.getEmail());
			orgMapName.get(doc).add(nedPerson.getFullName());
		}
	}
	
	/**
	 * Utility method to construct who to address the email to
	 * 
	 * @param doc
	 * @return
	 */
	private String constructDearForDoc(String doc) {
		String dear = StringUtils.join(orgMapName.get(doc), ", ");
		if (orgMapName.get(doc).size() >= 2) {
			StringBuffer sb = new StringBuffer(dear);
	        sb.replace(dear.lastIndexOf(", "), dear.lastIndexOf(", ") + 1, ", and ");
	        dear = sb.toString();
		}
		return dear;
	}

	/**
	 * Utility method to filter out the submissions based on doc
	 * 
	 * @param doc
	 */
	private void populateSummaryResultForDoc(String doc) {
		
		ProjectsVw submission = null;
		pastSubmissionDateResult.clear();
		for(Iterator<ProjectsVw> i= pastSubmissionDateResultAll.iterator(); i.hasNext();) {
			submission = i.next();
			if(StringUtils.equals(submission.getDocAbbreviation(), doc)) {
				pastSubmissionDateResult.add(submission);
				i.remove();
			}
		}
		
		bsiInProgressResult.clear();
		for(Iterator<ProjectsVw> i= bsiInProgressResultAll.iterator(); i.hasNext();) {
			submission = i.next();
			if(StringUtils.equals(submission.getDocAbbreviation(), doc)) {
				bsiInProgressResult.add(submission);
				i.remove();
			}
		}
		
		gdsIcInProgressResult.clear();
		for(Iterator<ProjectsVw> i= gdsIcInProgressResultAll.iterator(); i.hasNext();) {
			submission = i.next();
			if(StringUtils.equals(submission.getDocAbbreviation(), doc)) {
				gdsIcInProgressResult.add(submission);
				i.remove();
			}
		}
		
		budgetEndDateComingResult.clear();
		for(Iterator<ProjectsVw> i= budgetEndDateComingResultAll.iterator(); i.hasNext();) {
			submission = i.next();
			if(StringUtils.equals(submission.getDocAbbreviation(), doc)) {
				budgetEndDateComingResult.add(submission);
				i.remove();
			}
		}
	
		pastProjectEndDateResult.clear();
		for(Iterator<ProjectsVw> i= pastProjectEndDateResultAll.iterator(); i.hasNext();) {
			submission = i.next();
			if(StringUtils.equals(submission.getDocAbbreviation(), doc)) {
				pastProjectEndDateResult.add(submission);
				i.remove();
			}
		}
		
		projectEndDateComingResult.clear();
		for(Iterator<ProjectsVw> i= projectEndDateComingResultAll.iterator(); i.hasNext();) {
			submission = i.next();
			if(StringUtils.equals(submission.getDocAbbreviation(), doc)) {
				projectEndDateComingResult.add(submission);
				i.remove();
			}
		}
		pastSubmissionDateFlag = (pastSubmissionDateResult.isEmpty()? "N": "Y");
		bsiInProgressFlag = (bsiInProgressResult.isEmpty()? "N": "Y");
		gdsIcInProgressFlag = (gdsIcInProgressResult.isEmpty()? "N": "Y");
		budgetEndDateComingFlag = (budgetEndDateComingResult.isEmpty()? "N": "Y");
		pastProjectEndDateFlag = (pastProjectEndDateResult.isEmpty()? "N": "Y");
		projectEndDateComingFlag = (projectEndDateComingResult.isEmpty()? "N": "Y");
	}

	/**
	 * Log that the email has been sent out in DB for this template and project id
	 * 
	 * @param templateId
	 * @param projectId
	 * @param to
	 */
	private void insertEmailLogByProjectId(Long templateId, Long projectId, String[] to) {

		EmailNotification record = new EmailNotification();
		record.setMailTemplateId(templateId);
		record.setProjectId(projectId);
		record.setSentDate(new Date());
		record.setToList(StringUtils.join(to, ","));
		notificationsDao.logEmail(record);
		
	}
	
	/**
	 * Log that the email has been sent out in DB for this template and doc
	 * 
	 * @param templateId
	 * @param doc
	 * @param to
	 */
	private void insertEmailLogByDoc(Long templateId, String doc, String[] to) {
		
		EmailNotification record = new EmailNotification();
		record.setMailTemplateId(templateId);
		record.setDocAbbreviation(doc);
		record.setSentDate(new Date());
		record.setToList(StringUtils.join(to, ","));
		notificationsDao.logEmail(record);
		
	}
	
	/**
	 * Sets the common parameters used for all emails.
	 * 
	 * @param params
	 */
	private void setCommonParams(Map<String, Object> params) {
		final String from = gdsProperties.getProperty(ApplicationConstants.EMAIL_FROM);
		final String fromDisplay = gdsProperties.getProperty(ApplicationConstants.EMAIL_FROM_DISPLAY);
		final String url = gdsProperties.getProperty(ApplicationConstants.GDS_APPLICATION_URL);

		params.put(FROM, from);
		params.put(FROM_DISPLAY, fromDisplay);
		params.put("url", url);
	}

	/**
	 * Sets the parameters used for summary emails.
	 * 
	 * @param params
	 */
	private void setSummaryParams(Map<String, Object> params, String[] to, String dear, String doc) {
		params.put(TO, to);
		params.put("dear", dear);
		params.put("doc", doc);
		params.put("docAcronym", orgMapAcronym.get(doc));
		params.put("docFullName", orgMapFullName.get(doc));
		
		params.put("pastSubmissionDateResult", pastSubmissionDateResult);
		params.put("bsiInProgressResult", bsiInProgressResult);
		params.put("gdsIcInProgressResult", gdsIcInProgressResult);
		params.put("pastSubmissionDateFlag", pastSubmissionDateFlag);
		params.put("bsiInProgressFlag", bsiInProgressFlag);
		params.put("gdsIcInProgressFlag", gdsIcInProgressFlag);
		
		params.put("budgetEndDateComingResult", budgetEndDateComingResult);
		params.put("pastProjectEndDateResult", pastProjectEndDateResult);
		params.put("projectEndDateComingResult", projectEndDateComingResult);
		params.put("budgetEndDateComingFlag", budgetEndDateComingFlag);
		params.put("pastProjectEndDateFlag", pastProjectEndDateFlag);
		params.put("projectEndDateComingFlag", projectEndDateComingFlag);

		if(StringUtils.equals(doc, "All")) {
			params.put("doc", "DOCs which does not have GPA");
			
			params.put("pastSubmissionDateResult", pastSubmissionDateResultAll);
			params.put("bsiInProgressResult", bsiInProgressResultAll);
			params.put("gdsIcInProgressResult", gdsIcInProgressResultAll);
			params.put("pastSubmissionDateFlag", (pastSubmissionDateResultAll.isEmpty()? "N": "Y"));
			params.put("bsiInProgressFlag", (bsiInProgressResultAll.isEmpty()? "N": "Y"));
			params.put("gdsIcInProgressFlag", (gdsIcInProgressResultAll.isEmpty()? "N": "Y"));
			
			params.put("budgetEndDateComingResult", budgetEndDateComingResultAll);
			params.put("pastProjectEndDateResult", pastProjectEndDateResultAll);
			params.put("projectEndDateComingResult", projectEndDateComingResultAll);
			params.put("budgetEndDateComingFlag", (budgetEndDateComingResultAll.isEmpty()? "N": "Y"));
			params.put("pastProjectEndDateFlag", (pastProjectEndDateResultAll.isEmpty()? "N": "Y"));
			params.put("projectEndDateComingFlag", (projectEndDateComingResultAll.isEmpty()? "N": "Y"));
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
		return source.split("[;,]");
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

		MailTemplate template = findByShortIdentifier(identifier);
		
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
					if(emailTemplates.getProperty(identifier) != null) {
						Template t = velocityEngine.getTemplate(emailTemplates.getProperty(identifier));
					    t.merge(vc, body);
					} else {
						velocityEngine.evaluate(vc, body, identifier, template.getBody());
					}
					velocityEngine.evaluate(vc, subjectWriter, identifier, template.getSubject());
					logger.trace("evaluating email template: " + body.toString());
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
						
						if(params.get("system") != null && params.get("system").equals("true")) {
							final String[] overrideAddrs = parse(gdsProperties.getProperty("email.override.address"));
							helper.setTo(overrideAddrs);
						} else {
							final String[] overrideAddrs = {loggedOnUser.getEmail()};
							helper.setTo(overrideAddrs);
						}
						
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
				} catch (Exception e) {
					logger.error("Exception", e);
				}
			} else {
				logger.error("required parameter 'to' not found");
			}
		} else {
			logger.error("No message with identifier '" + identifier + "' found");
		}
	}

	public Properties getEmailTemplates() {
		return emailTemplates;
	}

	public void setEmailTemplates(Properties emailTemplates) {
		this.emailTemplates = emailTemplates;
	}
	
}

package gov.nih.nci.cbiit.scimgmt.gds.services;

import java.util.List;

import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PersonRole;
import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectsVw;
import gov.nih.nci.cbiit.scimgmt.gds.domain.UserRole;

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
	 * Send notification to user when a role is added.
	 */
	public void sendRoleAddedToUser(PersonRole personRole) throws Exception;
	
	/**
	 * Send summary emails to GPA for Extramural Submissions
	 * @throws Exception 
	 */
	public void sendExtramuralSummaryEmail() throws Exception;
	
	/**
	 * Send one time emails to Creator and PD for Extramural Submissions
	 * @throws Exception 
	 */
	public void sendExtramuralEmailByTemplate(List<ProjectsVw> list, String template, List<UserRole> gpas) throws Exception;

	/**
	 * Send one time and summary emails to GPA for Intramural Submissions
	 */
	public void sendIntramuralSummaryEmail() throws Exception;

	
	/**
	 * Retrieve a lit of submissions where anticipated submission date is in the past
	 */
	public List<ProjectsVw> getExtramuralPastSubmissionDate();
	
	/**
	 * Retrieve a lit of submissions where BSI is in progress
	 */
	public List<ProjectsVw> getExtramuralBsiInProgress();
	
	/**
	 * Retrieve a lit of submissions where GDS and IC is in progress
	 */
	public List<ProjectsVw> getExtramuralGdsIcInProgress();
	
	/**
	 * Retrieve a lit of submissions where the budget end is in 45 days
	 */
	public List<ProjectsVw> getExtramuralBudgetEndDateComing();
	
	/**
	 * Retrieve a lit of submissions where project end date is in the past
	 */
	public List<ProjectsVw> getExtramuralPastProjectEndDate();
	
	/**
	 * Retrieve a lit of submissions where project end date is in x days
	 */
	public List<ProjectsVw> getExtramuralProjectEndDateComing(Integer days);
	
	/**
	 * Retrieve a list of GPAs in the system.
	 */
	public List<UserRole> retrieveGpas();
}

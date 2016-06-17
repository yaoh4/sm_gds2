package gov.nih.nci.cbiit.scimgmt.gds.constants;

/**
 * @author menons2
 *
 */
public interface ApplicationConstants {

	/* Application property constants */
	public static final String GDS_HELP_DOCUMENT_URL = "gds.help.document.url";
	public static final String CONTACT_EMAIL = "contact.email";
	public static final String ERROR_EMAIL = "error.email";
	public static final String EMAIL_FROM = "email.from";
	public static final String EMAIL_FROM_DISPLAY = "email.from.display";
	public static final String ENVIRONMENT = "environment";
	public static final String PRODUCTION = "Production";
		
	/* Action forwards */
	public static final String NOT_AUTHORIZED = "notAuthorized";	
	
	/*List discriminators*/
	 public static final String PREGISTRATION_STATUS_LIST = "registration_status";
	 public static final String PROJECT_SUBMISSION_STATUS_LIST = "project_sub_status";
	 public static final String STUDY_RELEASED_LIST = "study_released";
	 
	 public static final String DOC_LIST = "docList";
	 
	 /* Numeric constants */
	 public static final Long PLAN_QUESTION_ANSWER_REPOSITORY_ID = 20L;
	 public static final int  COMMENTS_MAX_ALLOWED_SIZE = 2000;
	     
}

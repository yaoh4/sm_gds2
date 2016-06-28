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
	 public static final String REGISTRATION_STATUS_LIST = "registration_status";
	 public static final String PROJECT_SUBMISSION_STATUS_LIST = "project_sub_status";
	 public static final String STUDY_RELEASED_LIST = "study_released";
	 public static final String IC_APPROVED_BY_GPA_LIST = "IC_APPROVED_BY_GPA";
	 public static final String IC_PROV_OR_FINAL_LIST = "IC_PROV_OR_FINAL";
	 public static final String IC_FOR_FUTURE_USE_LIST = "IC_FOR_FUTURE_USE";
	 public static final String IC_DUL_VERIFIED_LIST = "IC_DUL_VERIFIED";
	 public static final String PROJECT_SUBMISSION_REASON_LIST = "project_sub_reason";
	 public static final String PROJECT_TYPE_LIST = "project_type";
	 
	 /* Type discriminators */ 
	 public static final String DOC_TYPE = "DOC_TYPE";
	 public static final String PAGE_TYPE = "PAGE";
	 public static final String PAGE_STATUS_TYPE = "PAGE_STATUS";
	 
	 public static final String DOC_LIST = "docList";
	 
	 /* Numeric constants */
	 public static final Long PLAN_QUESTION_ANSWER_REPOSITORY_ID = 20L;
	 public static final int  COMMENTS_MAX_ALLOWED_SIZE = 2000;
	 public static final int  NED_PERSON_NIH_SAC_MIN_SIZE = 4;
	 public static final int  GRANT_CONTRACT_NUM_MIN_SIZE = 6;
	 
	 
	     
}

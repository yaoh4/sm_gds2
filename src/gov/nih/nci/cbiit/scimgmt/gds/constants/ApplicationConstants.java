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
	public static final String BUSINESS_POLICY_EMAIL = "business.policy.email";
	public static final String TECHNICAL_ISSUES_EMAIL = "technical.issues.email";
	public static final String BUSINESS_POLICY_DISPLAY="business.policy.display";
	public static final String TECHNICAL_ISSUES_DISPLAY="technical.issues.display";
	public static final String EMAIL_FROM = "email.from";
	public static final String EMAIL_FROM_DISPLAY = "email.from.display";
	public static final String ENVIRONMENT = "ENVIRONMENT";
	public static final String PRODUCTION = "Production";
	public static final String VERSION = "version";
	
	/* Action forwards */
	public static final String NOT_AUTHORIZED = "notAuthorized";	
	public static final String LINK_TO_PARENT_PAGE = "linkToParentProject";	
	
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
	 public static final String SEARCH_SUBMISSION_FROM = "search_from";
	 
	 /* Type discriminators */ 
	 public static final String DOC_TYPE = "DOC_TYPE";
	 public static final String PAGE_TYPE = "PAGE";
	 public static final String PAGE_STATUS_TYPE = "PAGE_STATUS";
	 
	 public static final String DOC_LIST = "docList";
	 public static final String PD_LIST = "pdList";
	 
	 /* Numeric constants */
	 public static final Long PLAN_QUESTION_ANSWER_REPOSITORY_ID = 20L;
	 public static final Long PLAN_QUESTION_ANSWER_REPOSITORY_DBGAP_ID = 21L;
	 public static final Long PLAN_QUESTION_ANSWER_REPOSITORY_OTHER_ID = 25L;
	 public static final int  COMMENTS_MAX_ALLOWED_SIZE = 2000;
	 public static final int  NED_PERSON_NIH_SAC_MIN_SIZE = 4;
	 public static final int  GRANT_CONTRACT_NUM_MIN_SIZE = 6;
	 public static final Long PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_ID = 1L;
	 public static final Long PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_YES_ID = 2L;
	 public static final Long PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_NO_ID = 3L;
	 public static final Long PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_ID = 4L;
	 public static final Long PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_YES_ID = 5L;
	 public static final Long PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_NO_ID = 6L;
	 public static final Long PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_PENDING_ID = 7L;
	 public static final Long PLAN_QUESTION_ANSWER_DATA_SUBMITTED_ID = 8L;
	 public static final Long PLAN_QUESTION_ANSWER_DATA_SUBMITTED_YES_ID = 9L;
	 public static final Long PLAN_QUESTION_ANSWER_DATA_SUBMITTED_NO_ID = 10L;
	 public static final Long PLAN_QUESTION_ANSWER_SPECIMEN_ID = 11L;
	 public static final Long PLAN_QUESTION_ANSWER_SPECIMEN_HUMAN_ID = 12L;
	 public static final Long PLAN_QUESTION_ANSWER_SPECIMEN_NONHUMAN_ID = 13L;
	 public static final Long PLAN_QUESTION_ANSWER_DATA_TYPE_ID = 14L;
	 public static final Long PLAN_QUESTION_ANSWER_ACCESS_ID = 17L;
	 public static final Long PLAN_QUESTION_ANSWER_ACCESS_CONTROLLED_ID = 18L;
	 public static final Long PLAN_QUESTION_ANSWER_ACCESS_UNRESTRICTED_ID = 19L;
	 public static final Long PLAN_QUESTION_ANSWER_DBGAP_ID = 21L;
	 public static final Long PLAN_QUESTION_ANSWER_GPA_REVIEWED_ID = 26L;
	 public static final Long PLAN_QUESTION_ANSWER_GPA_REVIEWED_YES_ID = 27L;
	 public static final Long PLAN_QUESTION_ANSWER_GPA_REVIEWED_NO_ID = 28L;
	 public static final Long PLAN_QUESTION_ANSWER_UPLOAD_OPTION_ID = 29L;
	 public static final Long PLAN_QUESTION_ANSWER_UPLOAD_OPTION_FILE_ID = 30L;
	 public static final Long SUBMISSION_REASON_GDSPOLICY = 26L;
	 public static final Long SUBMISSION_REASON_GWASPOLICY = 27L;
	 public static final Long SUBMISSION_REASON_NIHFUND = 28L;
	 public static final Long SUBMISSION_REASON_NONNIHFUND = 29L;
	 public static final Long IC_STUDY_DUL_CHECKLIST_HEALTH_MEDICAL_BIOMEDICAL_ID = 6L;
	 public static final Long IC_STUDY_DUL_CHECKLIST_DISEASE_SPECIFIC_ID = 13L;
	 public static final Long IC_STUDY_DUL_CHECKLIST_OTHER_ID = 21L;
	 public static final Long PROJECT_SUBMISSION_STATUS_INPROGRESS_ID = 12L;
	 public static final Long PROJECT_SUBMISSION_STATUS_NOTSTARTED_ID = 13L;	
	 public static final Long PROJECT_SUBMISSION_STATUS_COMPLETED_ID = 14L;
	 public static final Long REGISTRATION_STATUS_INPROGRESS_ID = 8L;
	 public static final Long REGISTRATION_STATUS_NOTSTARTED_ID = 9L;
	 public static final Long REGISTRATION_STATUS_COMPLETED_ID = 10L;
	 public static final Long PROJECT_STUDY_RELEASED_YES_ID = 16L;
	 public static final Long PROJECT_STUDY_RELEASED_NO_ID = 17L;
	 public static final Long SEARCH_MY_PROJECT_SUBMISSIONS = 40L;
	 public static final Long SEARCH_SUBMISSION_FROM_MYDOC = 41L;
	 public static final Long SEARCH_SUBMISSION_FROM_ALL = 42L;
	 public static final Long IC_DUL_VERIFIED_YES_ID = 35L;
			 
	 public static final String DISPLAY_NAME_YES = "Yes";
	 public static final String DISPLAY_NAME_NO = "No";
	 
	 //public static final Long NO_ID  = 19L;
	 
	 /* Data Constants for flags*/
	 public static final String FLAG_YES = "Y";
	 public static final String FLAG_NO = "N";
	 
	 /*Constants for page codes*/
	 //public static final String PAGE_CODE_ICLIST = "ICLIST";
	 public static final String PAGE_CODE_IC = "IC";
	 public static final String PAGE_CODE_GDSPLAN = "GDSPLAN";
	 public static final String PAGE_CODE_BSI = "BSI";
	 public static final String PAGE_CODE_REPOSITORY = "REPOSITORY";
	 public static final String PAGE_CODE_SUBMISISON_DETAILS = "SUBDETAILS";
	 
	 
	 /*Constants for page status codes*/
	 public static final String PAGE_STATUS_CODE_NOT_STARTED = "NOTSTARTED";
	 public static final String PAGE_STATUS_CODE_IN_PROGRESS = "INPROGRESS";
	 public static final String PAGE_STATUS_CODE_COMPLETED = "COMPLETED";	 
	 
	 /* String constants */
	 public static final String NOT_STARTED = "NOTSTARTED"; 
	 public static final String NOT_APPLICABLE = "NA"; 
	 public static final String NO = "NO"; 	
	 public static final String OTHER = "Other"; 
	 public static final String DOC_TYPE_EXCEPMEMO = "EXCEPMEMO";
	 public static final String DOC_TYPE_GDSPLAN = "GDSPLAN";
	 public static final String DOC_TYPE_IC = "IC";
	 public static final String DOC_TYPE_BSI = "BSI";
	 public static final String PAGE_TYPE_GDSPLAN = "GDSPLAN";
	 public static final String PAGE_TYPE_IC = "IC";
	 public static final String PAGE_TYPE_BSI = "BSI";
	 public static final String PAGE_TYPE_STATUS = "REPOSITORY";
	 
	 public static final Long IC_PARENT_DUL_ID_GENERAL_RESEARCH_USE = 1L;
	 public static final Long IC_PARENT_DUL_ID_HEALTH_MEDICAL_BIOMEDICAL = 6L;
	 public static final Long IC_PARENT_DUL_ID_DISEASE_SPECIFIC = 13L;
	 public static final Long IC_PARENT_DUL_ID_OTHER = 21L;
	 
	 public static final Long IC_PROV_FINAL_ID_PROV = 22L;
	 public static final Long IC_PROV_FINAL_ID_FINAL = 23L;  
	 
	 public static final Long IC_GPA_APPROVED_YES_ID = 18L;
	 public static final Long IC_GPA_APPROVED_NO_ID = 19L;
	
	 
	 //Type of grant or contract
	 public static final String APPL_CLASS_CODE_EXTRAMURAL = "G";
	 public static final String APPL_CLASS_CODE_INTRAMURAL = "M";
	 public static final String APPL_CLASS_CODE_CONTRACT = "C";
	 
	 //Submission Type constants.	
	 public static final String SUBMISSION_TYPE_NEW_SUBPROJECT = "43";
	 public static final String SUBMISSION_TYPE_NEW_VERSION_PROJECT = "44";
	 public static final String SUBMISSION_TYPE_NEW_VERSION_SUBPROJECT = "45";
	 
	 //Action forward constants
	 public static final String EMPTY = "empty";
	 public static final String SHOW_IC = "showIc";
	 public static final String SHOW_BSI = "showBsi";
	 
}

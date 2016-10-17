/**
 * 
 */
package gov.nih.nci.cbiit.scimgmt.gds.util;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.xml.sax.SAXException;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.InstitutionalCertification;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;
import gov.nih.nci.cbiit.scimgmt.gds.services.FileUploadService;

/**
 * @author menons2
 *
 */
public class GdsPageStatusUtil {
	
	private static final Logger logger = LogManager.getLogger(GdsPageStatusUtil.class);
	
	@Autowired
	protected FileUploadService fileUploadService;
	
	@Autowired
	protected NedPerson loggedOnUser;
	
	private static GdsPageStatusUtil instance;
   	private static boolean loaded = false;
	
   	
   	public void init() throws IOException, SAXException {
		if (!loaded) {
			instance = this;
			loaded = true;
		}
		BeanUtilsBean.getInstance().getConvertUtils().register(false, true, -1);
	}
   	
   	
   	/**
	 * Gets the singleton
	 * 
	 * @return GdsPageStatusUtil singleton
	 */
	public static GdsPageStatusUtil getInstance() {
		return instance;
	}
	
	/**
	 * Returns the status of the GDSPlan page if present.
	 * Else returns null
	 * 
	 * @param project
	 * @return String The status if present
	 */
	public String computeGdsPlanStatus(Project project) {
		
		if(ApplicationConstants.SUBMISSION_REASON_NONNIHFUND.equals(project.getSubmissionReasonId())
				|| project.getParentProjectId() != null) {
			//If submission reason is non-NIH funded OR this is 
			//a sub-project, then there is no GDS Plan.
			return null;
		}
		
		//No data has been entered
		if(StringUtils.isBlank(project.getPlanComments()) && 
			CollectionUtils.isEmpty(project.getPlanAnswerSelections())) {
			return ApplicationConstants.PAGE_STATUS_CODE_NOT_STARTED;
		}
		
		Long submissionReasonId = project.getSubmissionReasonId();
		List<Document> exceptionMemo = 
			fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_EXCEPMEMO, project.getId());
			
		List<Document> gdsPlan = 
				fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_GDSPLAN, project.getId());
				
		if(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY.equals(submissionReasonId)
				 || ApplicationConstants.SUBMISSION_REASON_GWASPOLICY.equals(submissionReasonId)) {
		
			//Data sharing exception request not indicated, OR Data sharing exception requested  
			//but not yet approved OR data sharing exception approved but memo not loaded
			if(CollectionUtils.isEmpty(project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_ID))
				|| 
				(project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_YES_ID) != null
					&& (project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_PENDING_ID) != null
						|| CollectionUtils.isEmpty(project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_ID))))
				||
				(project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_YES_ID) != null
					&& (CollectionUtils.isEmpty(exceptionMemo) || CollectionUtils.isEmpty(project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_ID)))
						)){
				return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
			}
			
			//Data Sharing Plan not loaded or not reviewed
			if(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY.equals(submissionReasonId)
					&& 
					(project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_NO_ID) != null
					  || project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_NO_ID) != null
					  || project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_YES_ID) != null 
					)) {
				if(CollectionUtils.isEmpty(gdsPlan) || project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_GPA_REVIEWED_YES_ID) == null) {
					return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
				}
			}
		}		
		
		//Exception not requested, or requested but not approved, or requested
		//and approved but still data needs to be submitted
		if(ApplicationConstants.SUBMISSION_REASON_NIHFUND.equals(submissionReasonId)
				 || ApplicationConstants.SUBMISSION_REASON_NONNIHFUND.equals(submissionReasonId)
				 || (project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_NO_ID) != null
			|| 	project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_NO_ID) != null
			||	project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_YES_ID) != null)) {
					
			if(CollectionUtils.isEmpty(project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_ID))
					|| CollectionUtils.isEmpty(project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_TYPE_ID)) 
					|| CollectionUtils.isEmpty(project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_ACCESS_ID)))  {
				
				return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;			
			}
		}		
		
		return ApplicationConstants.PAGE_STATUS_CODE_COMPLETED;
	}
	
	
	/**
	 * Returns the status of the IC List page if present.
	 * Else returns null.
	 * 
	 * @param project
	 * @return String The status if present
	 */
	public String computeIcListStatus(Project project) {
		
		List<InstitutionalCertification> icList = project.getInstitutionalCertifications();
		
		//If user selects "Non-human" only,
		//OR if the answer to "Will there be any data submitted?" is No.
		//there is no IC
		if((project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_HUMAN_ID) == null &&
			 project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_NONHUMAN_ID) != null)
			||
			(project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_NO_ID) != null)) {
			return null;
		}
		
		// If user selects ONLY the "Other" repository in the "What repository will the data be submitted to?" question GDS plan page, 
		// there is no IC
		Set<PlanAnswerSelection> repoSet = project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID);
		if(!CollectionUtils.isEmpty(repoSet) && repoSet.size() == 1) {
			PlanAnswerSelection repo = repoSet.iterator().next();
			if(repo.getPlanQuestionsAnswer().getId().longValue() == ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_OTHER_ID.longValue()) {
				return null;
			}
		}
		
		if(CollectionUtils.isEmpty(icList)) {
			return ApplicationConstants.PAGE_STATUS_CODE_NOT_STARTED;
		}
			
		if(!ApplicationConstants.FLAG_YES.equalsIgnoreCase(project.getCertificationCompleteFlag())) { 
			return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
		}
		
		//There is at least one IC and IC certification flag says done. So proceed to
		//check if the ICs are all ok.
		for(InstitutionalCertification ic: icList) {
			if(ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS.equals(ic.getStatus())) {
				return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
			}
		}
			
		return ApplicationConstants.PAGE_STATUS_CODE_COMPLETED;
	}
	
	
	/**
	 * Returns the status of the BSI Study Info page if present.
	 * Else returns null.
	 * 
	 * @param project
	 * @return String The status if present.
	 */
	public String computeBsiStudyInfoStatus(Project project) {
		
		//If the answer to "Will there be any data submitted?" is No, 
		//there is no BSI
		if(project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_NO_ID) != null) {
			return null;
		}
		
		List<Document> docs = 
				fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_BSI, project.getId());
		
		if(project.getBsiReviewedFlag() == null
				&& StringUtils.isBlank(project.getBsiComments()) 
				&& CollectionUtils.isEmpty(docs)) {
			//If no data has been entered
			return ApplicationConstants.PAGE_STATUS_CODE_NOT_STARTED;
		} else {
			//If GPA has not reviewed or GPA has reviewed but no document has been uploaded
			if(ApplicationConstants.FLAG_NO.equalsIgnoreCase(project.getBsiReviewedFlag())
					|| (ApplicationConstants.FLAG_YES.equalsIgnoreCase(project.getBsiReviewedFlag())
							&& CollectionUtils.isEmpty(docs)) ) {
				return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
			}
		}
		
		return ApplicationConstants.PAGE_STATUS_CODE_COMPLETED;
	}

	/**
	 * Returns the status of the Repository Status page if present.
	 * Else returns null.
	 * 
	 * @param project
	 * @return String The status if present.
	 */
	public String computeRepositoryStatus(Project project) {
		
		// If there are no repositories selected, don't show the repository page.
		if (project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID).isEmpty()) {
			return null;
		}
		
		
		for(PlanAnswerSelection selection: project.getPlanAnswerSelections()) {
			for(RepositoryStatus repositoryStatus : selection.getRepositoryStatuses()){
				if(repositoryStatus.getProject().getId() == project.getId())
					project.getRepositoryStatuses().add(repositoryStatus);
			}		
		}
		
		String status = ApplicationConstants.PAGE_STATUS_CODE_NOT_STARTED;
		
		List<RepositoryStatus> repositoryStatuses = project.getRepositoryStatuses();
		for(RepositoryStatus repoStatus: repositoryStatuses) {
			
			Lookup registrationStatus = repoStatus.getLookupTByRegistrationStatusId();
			Lookup submissionStatus = repoStatus.getLookupTBySubmissionStatusId();
			Lookup studyReleased = repoStatus.getLookupTByStudyReleasedId();
			
			if(ApplicationConstants.REGISTRATION_STATUS_NOTSTARTED_ID.equals(registrationStatus.getId())) {
				//No need to check this repository further, since the submission status
				//and study released fields will be disabled in this case
				if(ApplicationConstants.PAGE_STATUS_CODE_COMPLETED.equals(status)) {
					//If previous repository is in complete status, then we are now
					//in in-progress state
					return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
				}
				continue;
			}
			
			//If we get here, then the page status is either in progress or completed.
			//Check in progress first
			
			//Registration Status In Progress, OR Submission Status Not Started or In Progress  
			//or Not Applicable, OR Study Released is No.
			if(ApplicationConstants.REGISTRATION_STATUS_INPROGRESS_ID.equals(registrationStatus.getId())
			||	(ApplicationConstants.PROJECT_SUBMISSION_STATUS_NOTSTARTED_ID.equals(submissionStatus.getId())
				|| ApplicationConstants.PROJECT_SUBMISSION_STATUS_INPROGRESS_ID.equals(submissionStatus.getId())) 
			|| (!ApplicationConstants.PROJECT_SUBMISSION_STATUS_NOTAPPLICABLE_ID.equals(submissionStatus.getId())
					&& ApplicationConstants.PROJECT_STUDY_RELEASED_NO_ID.equals(studyReleased.getId()))) {
				return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
			}
			
			//Neither not started, nor in in-progress status.Hence, completed
			status = ApplicationConstants.PAGE_STATUS_CODE_COMPLETED;
			
		};
		
		if(project.getAnticipatedSubmissionDate() != null &&
				ApplicationConstants.PAGE_STATUS_CODE_NOT_STARTED.equals(status)) {
			return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
		}
		
		return status;
	}
	
	
	

}

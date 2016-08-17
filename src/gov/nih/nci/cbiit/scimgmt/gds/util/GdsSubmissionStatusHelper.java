/**
 * 
 */
package gov.nih.nci.cbiit.scimgmt.gds.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.xml.sax.SAXException;


import gov.nih.nci.cbiit.scimgmt.gds.actions.SearchSubmissionAction;
import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.InstitutionalCertification;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PageStatus;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Study;
import gov.nih.nci.cbiit.scimgmt.gds.model.MissingData;
import gov.nih.nci.cbiit.scimgmt.gds.services.FileUploadService;
import gov.nih.nci.cbiit.scimgmt.gds.services.LookupService;
import gov.nih.nci.cbiit.scimgmt.gds.services.ManageProjectService;

/**
 * @author menons2
 *
 */
public class GdsSubmissionStatusHelper {

	private static final Logger logger = LogManager.getLogger(GdsSubmissionStatusHelper.class);
	
	@Autowired
	protected LookupService lookupService;
	
	@Autowired
	protected ManageProjectService manageProjectService;
	
	@Autowired
	protected FileUploadService fileUploadService;
	
	@Autowired
	protected NedPerson loggedOnUser;
	
	private static GdsSubmissionStatusHelper instance;
   	private static boolean loaded = false;
	
   	
   	public void init() throws IOException, SAXException {
		if (!loaded) {
			instance = this;
			loaded = true;
		}
		BeanUtilsBean.getInstance().getConvertUtils().register(false, true, -1);
	}
   	
   	
   	/**
	 * Gets the single instance of GdsSubmissionStatusHelper.
	 * 
	 * @return single instance of GdsSubmissionStatusHelper
	 */
	public static GdsSubmissionStatusHelper getInstance() {
		return instance;
	}
	
	
	public List<PageStatus> initPageStatuses(Project project) {
		List<PageStatus> pageStatuses = new ArrayList<PageStatus>();
		
		List<String> pageCodes = Arrays.asList(ApplicationConstants.PAGE_CODE_IC, 
											   ApplicationConstants.PAGE_CODE_GDSPLAN, 
											   ApplicationConstants.PAGE_CODE_BSI,
											   ApplicationConstants.PAGE_CODE_REPOSITORY);
		for(String pageCode: pageCodes) {
			PageStatus pageStatus = new PageStatus(
			lookupService.getLookupByCode(ApplicationConstants.PAGE_STATUS_TYPE, ApplicationConstants.PAGE_STATUS_CODE_NOT_STARTED),
			lookupService.getLookupByCode(ApplicationConstants.PAGE_TYPE, pageCode),
			project, loggedOnUser.getFullNameLF(), new Date());
			pageStatuses.add(pageStatus);
		}
		
		return pageStatuses;
	}
	
	
	public PageStatus getPageStatus(String pageCode, Project project) {
		
		String pageStatusStr = null;
		switch(pageCode) {
		case ApplicationConstants.PAGE_CODE_IC:		
			pageStatusStr =  getIcPageStatus(project);
			break;
		case ApplicationConstants.PAGE_CODE_GDSPLAN:
			pageStatusStr =  getGdsPageStatus(project);
			break;
		case ApplicationConstants.PAGE_CODE_BSI:
			pageStatusStr =  getBsiPageStatus(project);
			break;
		case ApplicationConstants.PAGE_CODE_REPOSITORY:
			pageStatusStr = getRepositoryPageStatus(project);
			break;
		default:
			String errorMsg = "Incorrect page code (" + pageCode + ") provided";
			logger.error(errorMsg);
			throw new RuntimeException(errorMsg);
		}
		
		PageStatus pageStatus = new PageStatus(
			lookupService.getLookupByCode(ApplicationConstants.PAGE_STATUS_TYPE, pageStatusStr),
			lookupService.getLookupByCode(ApplicationConstants.PAGE_TYPE, pageCode),
			project, loggedOnUser.getFullNameLF(), new Date());		
		return pageStatus;
	}
	
	
	private String getIcPageStatus(Project project) {
		
		String status = ApplicationConstants.PAGE_STATUS_CODE_COMPLETED;
		
		List<InstitutionalCertification> icList = manageProjectService.findIcsByProject(project);
		if(CollectionUtils.isEmpty(icList)) {
			return ApplicationConstants.PAGE_STATUS_CODE_NOT_STARTED;
		}
			
		if(!ApplicationConstants.FLAG_YES.equalsIgnoreCase(project.getCertificationCompleteFlag())) { 
			return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
		}
		
		//There is at least one IC and IC certification flag says done. So proceed to
		//check if the ICs are all ok.
		for(InstitutionalCertification ic: icList) {
			if(!ApplicationConstants.YES_ID.equals(ic.getGpaApprovalCode())) {
				return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
			}
			List<Study> studies = ic.getStudies();
			for(Study study: studies) {
				if(!ApplicationConstants.YES_ID.equals(study.getDulVerificationId())) {
					return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
				}
			}
		}
		
		return status;
	}
	
	private String getGdsPageStatus(Project project) {
		String status = ApplicationConstants.PAGE_STATUS_CODE_COMPLETED;
		
		//No data has been entered
		if(!StringUtils.hasText(project.getPlanComments()) && 
			CollectionUtils.isEmpty(project.getPlanAnswerSelections())) {
			return ApplicationConstants.PAGE_STATUS_CODE_NOT_STARTED;
		}
		
		List<Document> exceptionMemo = 
			fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_EXCEPMEMO, project.getId());
			
		List<Document> gdsPlan = 
				fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_GDSPLAN, project.getId());
				
		
		//Data sharing exception request not indicated, OR Data sharing exception requested  
		//but not approved OR data sharing exception approved but memo not loaded
		if(CollectionUtils.isEmpty(project.getPlanAnswerSelections())
		|| 
		(project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_YES_ID) != null
		&& project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_YES_ID) == null) 
		||
		(project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_YES_ID) != null
		&& CollectionUtils.isEmpty(exceptionMemo))){
			return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
		}
		
		//Data sharing plan not reviewed
		if(project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_GPA_REVIEWED_YES_ID) == null) {
			return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
		}
		
		//GDS Plan required by GDS policy but no plan loaded
		if(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY.equals(project.getSubmissionReasonId()) 
				&& CollectionUtils.isEmpty(gdsPlan)) {
			return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
		}
		
		return status;
	}
	
	
	private String getBsiPageStatus(Project project) {
		String status = ApplicationConstants.PAGE_STATUS_CODE_COMPLETED;
		
		//Check if document has been loaded
		List<Document> docs = 
				fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_BSI, project.getId());
		
		if(!ApplicationConstants.FLAG_YES.equals(project.getBsiReviewedFlag()) 
				|| CollectionUtils.isEmpty(docs)) {
			return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
		}
		
		return status;
	}
	
	
	private String getRepositoryPageStatus(Project project) {
		String status = ApplicationConstants.PAGE_STATUS_CODE_NOT_STARTED;
		
		
		List<RepositoryStatus> repositoryStatuses = project.getRepositoryStatuses();
		for(RepositoryStatus repoStatus: repositoryStatuses) {
			
			Lookup submissionStatus = repoStatus.getLookupTBySubmissionStatusId();
			Lookup registrationStatus = repoStatus.getLookupTBySubmissionStatusId();
			Lookup studyReleased = repoStatus.getLookupTByStudyReleasedId();
			
			if(ApplicationConstants.REGISTRATION_STATUS_NOTSTARTED_ID.equals(registrationStatus.getId())) {
				//No need to check further, since the submission status and
				//study released fields will be disabled in this case
				if(ApplicationConstants.PAGE_STATUS_CODE_COMPLETED.equals(status)) {
					//If previous repository is in complete status, then we are now
					//in in-progress state
					return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
				}
				continue;
			}
			
			//If we get here, then the page status is either in progress or completed.
			//Check in progress first
			
			//Submission status is not started or in progress, OR Registration 
			//status is not started or in progress, OR study released is No.
			if(ApplicationConstants.REGISTRATION_STATUS_INPROGRESS_ID.equals(registrationStatus.getId())
			||	(ApplicationConstants.PROJECT_SUBMISSION_STATUS_NOTSTARTED_ID.equals(submissionStatus.getId())
				|| ApplicationConstants.PROJECT_SUBMISSION_STATUS_INPROGRESS_ID.equals(submissionStatus.getId())) 
			|| ApplicationConstants.PROJECT_STUDY_RELEASED_NO_ID.equals(studyReleased.getId())) {
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
	
	
	public List<MissingData> computeMissingDataReport(Project project, String pageCode) {
		//Generate array of MissingDataFields - project id, page id, displayText level orderNum
		if(ApplicationConstants.PAGE_CODE_IC.equals(pageCode)) {
			return computeMissingIcData(project);
		} else if(ApplicationConstants.PAGE_CODE_BSI.equals(pageCode)) {
			return computeMissingBsiData(project);
		} else if(ApplicationConstants.PAGE_CODE_REPOSITORY.equals(pageCode)) {
			return computeMissingRepositoryStatusData(project);
		}
		return null;
	}
	
	
	public List<MissingData> computeMissingIcData(Project project) {
		ArrayList<MissingData> missingDataList = new ArrayList<MissingData>();
		
		List<InstitutionalCertification> icList = manageProjectService.findIcsByProject(project);
		
		if(!ApplicationConstants.FLAG_YES.equals(project.getCertificationCompleteFlag()) ||
				CollectionUtils.isEmpty(icList)) {
			String displayText = "Add all the required Institutional Certifications";
			MissingData missingData = new MissingData(displayText);
			missingDataList.add(missingData);
		}
		
		//Get the file list
		HashMap<Long, Document> docMap = new HashMap<Long, Document>();
		List<Document> docs = 
			fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_IC, project.getId());
		if(docs != null && !docs.isEmpty()) {
			for(Document doc: docs) {
				if(doc.getInstitutionalCertificationId() != null) {
					docMap.put(doc.getInstitutionalCertificationId(), doc);
				}			
			}
		}
		
		//There is at least one IC. So proceed to check if the ICs are all ok.
		MissingData missingData = new MissingData("The following ICs have incomplete data:");
		
		for(InstitutionalCertification ic: icList) {
			MissingData missingIcData = new MissingData();
			Document document = docMap.get(ic.getId());
			
			if(document != null) {				
				missingIcData.setDisplayText(document.getFileName());
			} else {
				missingIcData.setDisplayText("No file uploaded for IC");
				continue;
			}
			
			//Check GPA Approval Code
			if(!ApplicationConstants.YES_ID.equals(ic.getGpaApprovalCode())) {
				String text = "GPA approval code must be 'Yes'";
				missingIcData.addChild(new MissingData(text));	
			}
			
			//Loop through all the studies in the IC
			List<Study> studies = ic.getStudies();			
			for(Study study: studies) {
				String studyText = "Study Name: " + study.getStudyName();
				MissingData missingStudyData = new MissingData(studyText);
				if(!ApplicationConstants.YES_ID.equals(study.getDulVerificationId())) {
					String dulVerifiedText = "Data User Limitations Verified must be 'Yes'";
					missingStudyData.addChild(new MissingData(dulVerifiedText));					
				}
				//Other checks, if and when added will come here
				
				if(missingStudyData.getChildList().size() > 0) {
					//Add the study to the missing data list if 
					//there is at least one piece of missing data
					missingIcData.addChild(missingStudyData);
				}
			}
			
			if(missingIcData.getChildList().size() > 0) {
				missingData.addChild(missingIcData);
			}
		}
		
		if(missingData.getChildList().size() > 0) {
			missingDataList.add(missingData);
		}
		
		return missingDataList;
	}
	
	public List<MissingData> computeMissingBsiData(Project project) {
		ArrayList<MissingData> missingDataList = new ArrayList<MissingData>();
		
		if(!ApplicationConstants.FLAG_YES.equals(project.getBsiReviewedFlag())) {
			String displayText = "BSI Reviewed flag must be 'Yes'.";
			MissingData missingData = new MissingData(displayText);
			missingDataList.add(missingData);
		}
		
		List<Document> docs = 
			fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_BSI, project.getId());
		if(CollectionUtils.isEmpty(docs)) {
			String displayText = "The completed Basic Study Information Form must be uploaded.";
			MissingData missingData = new MissingData(displayText);
			missingDataList.add(missingData);
		}
		
		return missingDataList;
	}
	
	public List<MissingData> computeMissingRepositoryStatusData(Project project) {
		ArrayList<MissingData> missingDataList = new ArrayList<MissingData>();
		
		List<RepositoryStatus> repositoryStatuses = project.getRepositoryStatuses();
		MissingData missingData = new MissingData("The following repository statuses need to be updated:");
		
		for(RepositoryStatus repoStatus: repositoryStatuses) {
			
			MissingData missingRepoData = new MissingData(repoStatus.getPlanAnswerSelectionTByRepositoryId().getPlanQuestionsAnswer().getDisplayText());
			Lookup submissionStatus = repoStatus.getLookupTBySubmissionStatusId();
			Lookup registrationStatus = repoStatus.getLookupTBySubmissionStatusId();
			Lookup studyReleased = repoStatus.getLookupTByStudyReleasedId();
			
			if(!ApplicationConstants.PROJECT_SUBMISSION_STATUS_COMPLETED_ID.equals(submissionStatus.getId())) {
				missingRepoData.addChild(new MissingData("Submission Status must have a value of 'Completed'"));
			}
			if(!ApplicationConstants.REGISTRATION_STATUS_COMPLETED_ID.equals(registrationStatus.getId())) {
				missingRepoData.addChild(new MissingData("Registration Status must have a value of 'Completed'"));
			}
			if(!ApplicationConstants.PROJECT_STUDY_RELEASED_YES_ID.equals(studyReleased.getId())) {
				missingRepoData.addChild(new MissingData("Study Released must have a value of 'Yes'"));
			}
			
			if(missingRepoData.getChildList().size() > 0) {
				missingData.addChild(missingRepoData);
			}
		}
		
		if(missingData.getChildList().size() > 0) {
			missingDataList.add(missingData);
		}
		
		return missingDataList;
	}
	
	public List<MissingData> computeMissingGdsPlanData(Project project) {
		ArrayList<MissingData> missingDataList = new ArrayList<MissingData>();
		
		
		return missingDataList;
	}
		
}

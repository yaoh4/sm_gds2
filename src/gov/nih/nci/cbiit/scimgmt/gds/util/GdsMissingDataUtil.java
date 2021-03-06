/**
 * 
 */
package gov.nih.nci.cbiit.scimgmt.gds.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtilsBean;
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
import gov.nih.nci.cbiit.scimgmt.gds.domain.Study;
import gov.nih.nci.cbiit.scimgmt.gds.model.MissingData;
import gov.nih.nci.cbiit.scimgmt.gds.services.FileUploadService;
import gov.nih.nci.cbiit.scimgmt.gds.services.LookupService;
import gov.nih.nci.cbiit.scimgmt.gds.services.ManageProjectService;

/**
 * @author menons2
 *
 */
public class GdsMissingDataUtil {

	private static final Logger logger = LogManager.getLogger(GdsMissingDataUtil.class);
	
	@Autowired
	protected LookupService lookupService;
	
	@Autowired
	protected ManageProjectService manageProjectService;
	
	@Autowired
	protected FileUploadService fileUploadService;
	
	@Autowired
	protected NedPerson loggedOnUser;
	
	private static GdsMissingDataUtil instance;
   	private static boolean loaded = false;
	
   	
   	public void init() throws IOException, SAXException {
		if (!loaded) {
			instance = this;
			loaded = true;
		}
		BeanUtilsBean.getInstance().getConvertUtils().register(false, true, -1);
	}
   	
   	
   	/**
	 * Gets the single instance of GdsMissingDataUtil
	 * 
	 * @return single instance of GdsMissingDataUtil
	 */
	public static GdsMissingDataUtil getInstance() {
		return instance;
	}
	
	
	public List<MissingData> getMissingGdsPlanData(Project project) {
		
		ArrayList<MissingData> missingDataList = new ArrayList<MissingData>();
				
		List<Document> exceptionMemos = 
			fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_EXCEPMEMO, project.getId());
			
		List<Document> gdsPlans = 
			fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_GDSPLAN, project.getId());
			
		Long submissionReasonId = project.getSubmissionReasonId();
		
		if(ApplicationConstants.SUBMISSION_REASON_NONNIHFUND.equals(submissionReasonId)
			|| ApplicationConstants.FLAG_YES.equals(project.getSubprojectFlag())) {
			//Return empty list
			return missingDataList;
		}
		  
			
		if(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY.equals(submissionReasonId)
			 || ApplicationConstants.SUBMISSION_REASON_GWASPOLICY.equals(submissionReasonId)) {
					
			//Not indicated whether there is a data sharing exception requested for this project
			if(CollectionUtils.isEmpty(project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_ID))) {
				MissingData missingData = new MissingData("The question 'Is there a data sharing exception requested for this project ?' has not been answered.");
				missingDataList.add(missingData);
			}
				
			//Not indicated if the data sharing exception requested was approved
			if(project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_YES_ID) != null
				&& ( CollectionUtils.isEmpty(project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_ID)))) {
				MissingData missingData = new MissingData("The question 'Was this exception approved ?' has not been answered.");
				missingDataList.add(missingData);
			}
			
			if(project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_YES_ID) != null
					&& (project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_PENDING_ID) != null)) {
					MissingData missingData = new MissingData("Approval of the Exception is pending.");
					missingDataList.add(missingData);
				}
				
			//Data sharing exception approved but Exception Memo not uploaded
			if(project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_YES_ID) != null) {
				
			
				if(CollectionUtils.isEmpty(exceptionMemos)) {
					MissingData missingData = new MissingData("The Exception Memo has not been uploaded.");
					missingDataList.add(missingData);
				}
			}
				if(project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_YES_ID) != null || project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_PENDING_ID) != null) {
				if(CollectionUtils.isEmpty(project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_ID))) {
					MissingData missingData = new MissingData("The question 'Will there be any data submitted ?' has not been answered.");
					missingDataList.add(missingData);
				}
			}
				
			if(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY.equals(submissionReasonId)
					&& 
					(project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_NO_ID) != null
					  || project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_NO_ID) != null
					  || project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_YES_ID) != null 
					)) {
				
				if(CollectionUtils.isEmpty(gdsPlans)) {
					MissingData missingData = new MissingData("The Data Sharing Plan has not been uploaded.");
					missingDataList.add(missingData);
				}
			
				//GPA has not reviewed the data sharing plan
				if(project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_GPA_REVIEWED_YES_ID) == null) {
					MissingData missingData = new MissingData("GPA review of the Data Sharing Plan is pending.");
					missingDataList.add(missingData);
				}
			}
		}		
		

		//If Exception not requested, or requested but not approved, or requested and approved but still
		//data needs to be submitted
		if(ApplicationConstants.SUBMISSION_REASON_NIHFUND.equals(submissionReasonId)
			|| (project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_NO_ID) != null
			|| 	project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_NO_ID) != null
			||	project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_YES_ID) != null)) {
			
			if(CollectionUtils.isEmpty(project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_ID))) {
				MissingData missingData = new MissingData("The question 'What specimen type does the data submission pertain to ?' has not been answered.");
				missingDataList.add(missingData);
			}
			
			if(CollectionUtils.isEmpty(project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_TYPE_ID))) {
				MissingData missingData = new MissingData("The question 'What type of data will be submitted ?' has not been answered.");
				missingDataList.add(missingData);
			}
			
			if(CollectionUtils.isEmpty(project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_ACCESS_ID))) {
				MissingData missingData = new MissingData("The question 'What type of access is the data to be made available through ?' has not been answered.");
				missingDataList.add(missingData);
			}
			
			if(CollectionUtils.isEmpty(project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID))){
				MissingData missingData = new MissingData("The question 'What repository will the data be submitted to ?' has not been answered.");
				missingDataList.add(missingData);
			}
		}
			
		return missingDataList;
	}
	
	
	public List<MissingData> getMissingIcListData(Project project) {
		
		List<MissingData> missingDataList = new ArrayList<MissingData>();
		
		//If user selects "Non-human" only,
		//OR if the answer to "Will there be any data submitted?" is No.
		//there is no IC, so return empty list
		if((project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_HUMAN_ID) == null &&
					 project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_NONHUMAN_ID) != null)
			|| (project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_NO_ID) != null)) {
			return missingDataList;
		}
		
		if(ApplicationConstants.FLAG_YES.equals(project.getSubprojectFlag())) {
			Project parentProject=manageProjectService.findById(project.getParentProjectId());
			if((parentProject.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_HUMAN_ID) == null &&
					parentProject.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_NONHUMAN_ID) != null)) {
					return null;
				}
		}
				
		// If user selects ONLY the "Other" repository in the "What repository will the data be submitted to?" 
		//question GDS plan page, there is no IC, so return empty list
		Set<PlanAnswerSelection> repoSet = project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID);
		if(!CollectionUtils.isEmpty(repoSet) && repoSet.size() == 1) {
			PlanAnswerSelection repo = repoSet.iterator().next();
			if(repo.getPlanQuestionsAnswer().getId().longValue() == ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_OTHER_ID.longValue()) {
				return missingDataList;
			}
		}
			
		List<InstitutionalCertification> icList = project.getInstitutionalCertifications();
		List<Study> studies = project.getStudies();
		
		
		//There are no studies
		if(CollectionUtils.isEmpty(studies)) {
			String displayText="";
			if(ApplicationConstants.FLAG_YES.equalsIgnoreCase(project.getSubprojectFlag()) && CollectionUtils.isEmpty(icList)) {
				displayText="At least one Institutional Certification must be selected";
			} else if(ApplicationConstants.FLAG_NO.equalsIgnoreCase(project.getSubprojectFlag())) {
				//TBD - Confirm with Catherine
				displayText="At least one Study and Institutional Certification must be present";
			}
			if(!displayText.isEmpty()) {
				MissingData missingDataIc = new MissingData(displayText);
				missingDataList.add(missingDataIc);
			}
		}
		
		//Check if there are studies without IC - at project level only
		if(ApplicationConstants.FLAG_NO.equalsIgnoreCase(project.getSubprojectFlag())) {
			MissingData missingDataStudy = new MissingData("The following Studies are not yet associated with an Institutional Certification:");
			for(Study study: studies) {
				if(CollectionUtils.isEmpty(study.getInstitutionalCertifications())) {			
					missingDataStudy.addChild(new MissingData(study.getStudyName()));
				}
			} 
			if(!CollectionUtils.isEmpty(missingDataStudy.getChildList())) {
			missingDataList.add(missingDataStudy);
			}
		}
			
		
		//Check each Ic status - at project level only
		if(ApplicationConstants.FLAG_NO.equals(project.getSubprojectFlag())) {
			
			//Get the file list first
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
			
			//Check if the ICs are all ok.
			MissingData missingData = new MissingData("The following ICs have incomplete data:");
			for(InstitutionalCertification ic: icList) {
				Document document = docMap.get(ic.getId());
				MissingData missingIcData = computeMissingIcData(ic, document);
									
				if(missingIcData.getChildList().size() > 0) {
					missingIcData.setDisplayText(document.getFileName());
					missingData.addChild(missingIcData);
				}
			}
		
		
			if(missingData.getChildList().size() > 0) {
				missingDataList.add(missingData);
			} 
		}
		
		return missingDataList;
	}
	
	
	public List<MissingData> getMissingIcData(Project project, Long instCertId) {
		
		List<MissingData> missingDataList = new ArrayList<MissingData>();		
		InstitutionalCertification ic = manageProjectService.findIcById(instCertId);
			
		//Get the file list
		Document document = null;
		Project docParent = project;
		Long parentProjectId = project.getParentProjectId();
		if(parentProjectId != null) {
			docParent =  manageProjectService.findById(parentProjectId);
		}
		List<Document> docs = 
			fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_IC, docParent.getId());
		if(docs != null && !docs.isEmpty()) {
			for(Document doc: docs) {
				Long docId = doc.getInstitutionalCertificationId();
				if(docId != null && docId.equals(instCertId)) {
					document = doc;
				}			
			}
		}
		
		MissingData missingIcData = computeMissingIcData(ic, document);	
		if(missingIcData.getChildList().size() > 0) {
				missingIcData.setDisplayText("The following data is incomplete");
				missingDataList.add(missingIcData);
		}		
			
		return missingDataList;
	}
	
	
	
	public List<MissingData> getMissingBsiData(Project project) {
		
		ArrayList<MissingData> missingDataList = new ArrayList<MissingData>();
		
		if(!ApplicationConstants.FLAG_YES.equals(project.getSubprojectFlag())) {
		  if(project.getSubmissionReasonId().equals(ApplicationConstants.SUBMISSION_REASON_NONNIHFUND) && CollectionUtils.isEmpty(project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID))){
		    	String displayText = "The question 'What repository will the data be submitted to ?' has not been answered.";
				MissingData missingData = new MissingData(displayText);
				missingDataList.add(missingData);
		    }
		}
		if(!ApplicationConstants.BSI_NA.equals(project.getBsiReviewedId())){
		if(!ApplicationConstants.BSI_YES.equals(project.getBsiReviewedId())) {
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
		}
	
		return missingDataList;
	}
	
	
	public List<MissingData> getMissingRepositoryListData(Project project) {
		
		List<MissingData> missingDataList = new ArrayList<MissingData>();
			
		MissingData missingData = new MissingData("The following repository statuses need to be updated:");
		for(PlanAnswerSelection selection: project.getPlanAnswerSelections()) {
			for(RepositoryStatus repoStatus: selection.getRepositoryStatuses()) {
				if(project.getId().longValue() == repoStatus.getProject().getId().longValue()) {
					
				MissingData missingRepoData = computeMissingRepositoryData(project, repoStatus);
				if(missingRepoData.getChildList().size() > 0) {
					missingData.addChild(missingRepoData);
				}
				}
			}
		}
	
	if(ApplicationConstants.FLAG_NO.equals(project.getSubprojectFlag())) {
		if(project.getRepoCount() == 0 && ApplicationConstants.SUBMISSION_REASON_NONNIHFUND.equals(project.getSubmissionReasonId())) {
			MissingData repositories =  new MissingData("To track the Submission Status of the repositories for this submission, please select the applicable repositories on the Basic Study Information page");
			missingDataList.add(repositories);
		}
		else if(project.getRepoCount() == 0 && !ApplicationConstants.SUBMISSION_REASON_NONNIHFUND.equals(project.getSubmissionReasonId())) {
			MissingData repositories =  new MissingData("To track the Submission Status of the repositories for this submission, please select the applicable repositories on the Genomic Data Sharing page");
			missingDataList.add(repositories);
		}
		}
		else {
			Project parentProject=manageProjectService.findById(project.getParentProjectId());
			if(parentProject.getRepoCount() == 0) {
				MissingData repositories =  new MissingData("Select the applicable repositories on the Parent Project to track the Submission Status of the repositories for this submission.");
				missingDataList.add(repositories);
			}
			else if(parentProject.getRepoCount() != 0 && project.getRepoCount() == 0) {
				MissingData repositories =  new MissingData("At least one repository must be selected.");
				missingDataList.add(repositories);
			}
				
		}
		
		if(missingData.getChildList().size() > 0) {
			missingDataList.add(missingData);
		}
		
		return missingDataList;
	}	
	
	
	public List<MissingData> getMissingRepositoryData(Project project, Long repoStatusId) {
		
		List<MissingData> missingDataList = new ArrayList<MissingData>();
		RepositoryStatus repoStatus = manageProjectService.findRepositoryById(repoStatusId);	
		MissingData missingRepoData = computeMissingRepositoryData(project, repoStatus);	
		if(missingRepoData.getChildList().size() > 0) {
				missingRepoData.setDisplayText("The following data is incomplete");
				missingDataList.add(missingRepoData);
		}	
			
		return missingDataList;
	}	
	

	private MissingData computeMissingRepositoryData(Project project, RepositoryStatus repoStatus) {
		MissingData missingRepoData = new MissingData(repoStatus.getPlanAnswerSelectionTByRepositoryId().getPlanQuestionsAnswer().getDisplayText());
		Lookup submissionStatus = repoStatus.getLookupTBySubmissionStatusId();
		Lookup registrationStatus = repoStatus.getLookupTByRegistrationStatusId();
		Lookup studyReleased = repoStatus.getLookupTByStudyReleasedId();
		
		if(!ApplicationConstants.REGISTRATION_STATUS_COMPLETED_ID.equals(registrationStatus.getId())
				&& !ApplicationConstants.REGISTRATION_STATUS_NOTAPPLICABLE_ID.equals(registrationStatus.getId())) {
			missingRepoData.addChild(new MissingData("Registration Status must have a value of 'Completed' or 'Not Applicable'."));
		}
		if(GdsSubmissionActionHelper.willThereBeAnyDataSubmittedInGdsPlan(project)) {
			if(!ApplicationConstants.PROJECT_SUBMISSION_STATUS_COMPLETED_ID.equals(submissionStatus.getId())
				&& !ApplicationConstants.PROJECT_SUBMISSION_STATUS_NOTAPPLICABLE_ID.equals(submissionStatus.getId())) {
				missingRepoData.addChild(new MissingData("Submission Status must have a value of 'Completed' or 'Not Applicable'."));
			}
		}
		if(!ApplicationConstants.PROJECT_STUDY_RELEASED_YES_ID.equals(studyReleased.getId())) {
				missingRepoData.addChild(new MissingData("Study Released must have a value of 'Yes'."));
		}
		
		
		return missingRepoData;
	}
	
	
	public MissingData computeMissingIcData(InstitutionalCertification ic, Document doc) {
		
		MissingData missingIcData = new MissingData();
		
		if(doc == null) {				
			String text = "No file uploaded for IC.";
			missingIcData.addChild(new MissingData(text));
			return missingIcData;
		}
		
		//Check GPA Approval Code
		if(!ApplicationConstants.IC_GPA_APPROVED_YES_ID.equals(ic.getGpaApprovalCode())) {
			String text = "GPA approval code must be 'Approved'.";
			missingIcData.addChild(new MissingData(text));	
		}
		
		if(ApplicationConstants.IC_PROV_FINAL_ID_PROV.equals(ic.getProvisionalFinalCode())) {
			String text = "Institutional Certification must be marked as Final";
			missingIcData.addChild(new MissingData(text));	
		}
		//Loop through all the studies in the IC
		List<Study> studies = ic.getStudies();			
		for(Study study: studies) {
			String studyText = "Study Name: " + study.getStudyName();
			MissingData missingStudyData = new MissingData(studyText);
			if(!ApplicationConstants.IC_DUL_VERIFIED_YES_ID.equals(study.getDulVerificationId())
				&& !ApplicationConstants.IC_DUL_VERIFIED_NOT_APPLICABLE_ID.equals(study.getDulVerificationId())) {
				String dulVerifiedText = "Data Use Limitation(s) Verified must be 'Yes' or 'Not Applicable'.";
				missingStudyData.addChild(new MissingData(dulVerifiedText));					
			}
			//Other checks, if and when added will come here
			
			if(missingStudyData.getChildList().size() > 0) {
				//Add the study to the missing data list if 
				//there is at least one piece of missing data
				missingIcData.addChild(missingStudyData);
			}
		}
			
		return missingIcData;
	}
	
	
		
}

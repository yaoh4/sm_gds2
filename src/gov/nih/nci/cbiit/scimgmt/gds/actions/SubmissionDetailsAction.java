package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.InstitutionalCertification;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PageStatus;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectsVw;
import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;
import gov.nih.nci.cbiit.scimgmt.gds.model.ExportRow;
import gov.nih.nci.cbiit.scimgmt.gds.model.MissingData;
import gov.nih.nci.cbiit.scimgmt.gds.model.Submission;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsMissingDataUtil;

/**
 * @author menons2
 *
 */
@SuppressWarnings("serial")
public class SubmissionDetailsAction extends ManageSubmission {
	
	private List<Document> bsiFile;
	private List<Document> exceptionMemo;
	private List<Document> gdsPlanFile;

	/**
	 * Opens Grants Contracts Search page.
	 * 
	 * @return forward string
	 */
	public String execute() throws Exception {
		
		Project project = retrieveSelectedProject();
		for(PlanAnswerSelection selection: project.getPlanAnswerSelections()) {
			for(RepositoryStatus repositoryStatus : selection.getRepositoryStatuses()){
				project.getRepositoryStatuses().add(repositoryStatus);
			}		
		}
			
		//Load ICs
		List<InstitutionalCertification> certs  = project.getInstitutionalCertifications();
		project.setInstitutionalCertifications(certs);
		HashMap<Long, InstitutionalCertification> map = new HashMap<Long, InstitutionalCertification>();
		
		Long projectId = project.getId();
		if(project.getParentProjectId() != null) {
			projectId = project.getParentProjectId();
		}
		if(certs != null && !certs.isEmpty()) {
			List<Document> docs = 
					fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_IC, projectId);
				    
			for(InstitutionalCertification cert: certs) {
				for(Document doc: docs) {
					if(doc.getInstitutionalCertificationId() != null && 
							doc.getInstitutionalCertificationId().equals(cert.getId())) {
						cert.addDocument(doc);
						break;
					}
				}		
			}
		}
		
		setProject(project);
		
		//Load general info
		loadGrantInfo();
		
		//Load BSI file(s)
		setBsiFile(fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_BSI, new Long(getProjectId())));
		
		//Load Exceptions memo
		setExceptionMemo(fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_EXCEPMEMO, new Long(getProjectId())));
		
		setGdsPlanFile(fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_GDSPLAN, new Long(getProjectId())));
		
		return SUCCESS;
	}
	
	/**
	 * This method returns answers for the questions on gds plan page.
	 * @param project
	 * @param questionId
	 * @return
	 */
	public String getAnswerForQuestionInGdsPlan(Long questionId){

		logger.debug("Getting answer for the question on gds plan page for questionId :"+questionId);
		StringBuffer answer = new StringBuffer();
		for(PlanAnswerSelection planAnswerSelection : getProject().getPlanAnswerSelections()){			
			if( questionId == planAnswerSelection.getPlanQuestionsAnswer().getQuestionId()){
				if(StringUtils.isNotBlank(answer)){
					answer.append("; ");
				}
				answer.append(planAnswerSelection.getPlanQuestionsAnswer().getDisplayText());			
			}			
		}
		return answer.toString();
	}
	
	/**
	 * @return the bsiFile
	 */
	public List<Document> getBsiFile() {
		return bsiFile;
	}
	/**
	 * @param bsiFile the bsiFile to set
	 */
	public void setBsiFile(List<Document> bsiFile) {
		this.bsiFile = bsiFile;
	}
	
	//Get project Submission Reason
	public String getProjectSubmissionReason() {		
		return lookupService.getLookupById(ApplicationConstants.PROJECT_SUBMISSION_REASON_LIST, getProject().getSubmissionReasonId()).getDescription();
	}	
	
	/**
	 * @return the exceptionMemo
	 */
	public List<Document> getExceptionMemo() {
		return exceptionMemo;
	}
	
	/**
	 * @param bsiFile the exceptionMemo to set
	 */
	public void setExceptionMemo(List<Document> exceptionMemo) {
		this.exceptionMemo = exceptionMemo;
	}
	
	/**
	 * @return the gdsPlanFile
	 */
	public List<Document> getGdsPlanFile() {
		return gdsPlanFile;
	}
	
	/**
	 * @param bsiFile the gdsPlanFile to set
	 */
	public void setGdsPlanFile(List<Document> gdsPlanFile) {
		this.gdsPlanFile = gdsPlanFile;
	}
	
	
	public String getPageStatusCode() {
		
		List<PageStatus> statuses = retrieveSelectedProject().getPageStatuses();
		if(!CollectionUtils.isEmpty(statuses)) {
			for(PageStatus status: statuses) {
				if(status.getStatus().getCode().equals(
					ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS)) {
					return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
				}
			}
		} else {
			return ApplicationConstants.PAGE_STATUS_CODE_NOT_STARTED;
		}		
		return ApplicationConstants.PAGE_STATUS_CODE_COMPLETED;
	}
	
	
	public String getIcStatusCode(Long icId) {
		List<InstitutionalCertification> certs = retrieveSelectedProject().getInstitutionalCertifications();
		for(InstitutionalCertification ic: certs) {
			if(ic.getId().equals(icId)) {
				return ic.getStatus();
			}
		}		
		return null;
	}
	
	
	public String getExceptionMemoStatusCode() {
		
		Project project = retrieveSelectedProject();
		List<Document> exceptionMemos = 
				fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_EXCEPMEMO, project.getId());
				
		//Not indicated whether there a data sharing exception has been requested, or exception not requested, or requested but not approved
		if(CollectionUtils.isEmpty(project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_ID)) 
				|| project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_NO_ID) != null
				|| project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_NO_ID) != null) {
			return ApplicationConstants.PAGE_STATUS_CODE_NOT_STARTED; 
		} else {
			//Data sharing exception has been approved and the file has been uploaded
			if (project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_YES_ID) != null
				&& !CollectionUtils.isEmpty(exceptionMemos)) {
			return ApplicationConstants.PAGE_STATUS_CODE_COMPLETED;
				
			}	
		}
		return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
	}
	
	
	
	public String getRepositoryStatusCode(Long repoId) {

		List<RepositoryStatus> statuses = retrieveSelectedProject().getRepositoryStatuses();
		for(RepositoryStatus repoStatus: statuses) {
			if(repoStatus.getId().equals(repoId)) {
				return repoStatus.getStatus();
			}
		}
		
		return null;
	}
	
	
	
	public String getMissingProjectData() {
		
		Project project = retrieveSelectedProject();
		
		GdsMissingDataUtil missingDataUtil = GdsMissingDataUtil.getInstance();		
		
		setupMissingDataList(ApplicationConstants.PAGE_CODE_GDSPLAN, 
				missingDataUtil.getMissingGdsPlanData(project));
		setupMissingDataList(ApplicationConstants.PAGE_CODE_IC, 
				missingDataUtil.getMissingIcListData(project));
		setupMissingDataList(ApplicationConstants.PAGE_CODE_BSI, 
				missingDataUtil.getMissingBsiData(project));
		setupMissingDataList(ApplicationConstants.PAGE_CODE_REPOSITORY, 
				missingDataUtil.getMissingRepositoryListData(project));
		
		return SUCCESS;
	}
	
	
	private void setupMissingDataList(String pageCode, List<MissingData> list) {
		if(!CollectionUtils.isEmpty(list)) {
			String displayText = lookupService.getLookupByCode(ApplicationConstants.PAGE_TYPE, 
					pageCode).getDisplayName();
			MissingData missingData = new MissingData(displayText);
			missingData.addChildren(list);
			missingDataList.add(missingData);
		}	
	}
	
}

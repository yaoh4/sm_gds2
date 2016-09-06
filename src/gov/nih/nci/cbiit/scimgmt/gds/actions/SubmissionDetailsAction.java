package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.util.ArrayList;
import java.util.Collections;
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
import gov.nih.nci.cbiit.scimgmt.gds.util.RepositoryStatusComparator;

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
	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		
		Project project = retrieveSelectedProject();
		for(PlanAnswerSelection selection: project.getPlanAnswerSelections()) {
			for(RepositoryStatus repositoryStatus : selection.getRepositoryStatuses()){
				if(repositoryStatus.getProject().getId() == project.getId())
					project.getRepositoryStatuses().add(repositoryStatus);
				Collections.sort(project.getRepositoryStatuses(),new RepositoryStatusComparator());
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
		
		return super.getProjectStatusCode(retrieveSelectedProject());
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
		Long submissionReasonId = project.getSubmissionReasonId();
		
		if(ApplicationConstants.SUBMISSION_REASON_NIHFUND.equals(submissionReasonId)
				 || ApplicationConstants.SUBMISSION_REASON_NONNIHFUND.equals(submissionReasonId)
				 || project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_NO_ID) != null
				 || project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_NO_ID) != null)
		{
			//Not applicable
			return null;
		}
		
		//Exception requested but approval pending, or approved but document not loaded not approved
		if(project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_PENDING_ID) != null
			|| (project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_YES_ID) != null 
				&& CollectionUtils.isEmpty(exceptionMemos))) {
			return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS; 
		} 
		
		//Data sharing exception has been approved and the file has been uploaded
		if (project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_YES_ID) != null
				&& !CollectionUtils.isEmpty(exceptionMemos)) {
			return ApplicationConstants.PAGE_STATUS_CODE_COMPLETED;	
		}
		
		return ApplicationConstants.PAGE_STATUS_CODE_NOT_STARTED;
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
		
		
		setPage(lookupService.getLookupByCode(ApplicationConstants.PAGE_TYPE, ApplicationConstants.PAGE_CODE_SUBMISISON_DETAILS));
		
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

package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.InstitutionalCertification;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;
import gov.nih.nci.cbiit.scimgmt.gds.model.MissingData;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsMissingDataUtil;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper;
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
	
	private List<Project> versions;

	/**
	 * Opens Grants Contracts Search page.
	 * 
	 * @return forward string
	 */
	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		
		setupProjectData();
		setEditFlag(ApplicationConstants.FLAG_YES);
		
		return SUCCESS;
	}
	
	public String viewOnly() {
		
		setupProjectData();
		setEditFlag(ApplicationConstants.FLAG_NO);
		
		return SUCCESS;
	}
	
	private void setupProjectData() {
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
		
		Long projectId = project.getId();
		if(project.getParentProjectId() != null) {
			projectId = project.getParentProjectId();
			project.setParent(retrieveParentProject(project));
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
		setBsiFile(fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_BSI, Long.valueOf(getProjectId())));
		
		//Load Exceptions memo
		setExceptionMemo(fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_EXCEPMEMO, Long.valueOf(getProjectId())));
		
		setGdsPlanFile(fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_GDSPLAN, Long.valueOf(getProjectId())));
		
		setVersions(retrieveVersions(project));
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
			if( questionId != null && questionId.longValue() == planAnswerSelection.getPlanQuestionsAnswer().getQuestionId().longValue()){
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
	
	
	/**
	 * @return the versions
	 */
	public List<Project> getVersions() {
		return versions;
	}

	/**
	 * @param versions the versions to set
	 */
	public void setVersions(List<Project> versions) {
		this.versions = versions;
	}

	public String getPageStatusCode() {
		return super.getProjectStatusCode(getProject().getId());
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
		setProject(project);
		
		GdsMissingDataUtil missingDataUtil = GdsMissingDataUtil.getInstance();		
		
		setupMissingDataList(ApplicationConstants.PAGE_CODE_GDSPLAN, 
				missingDataUtil.getMissingGdsPlanData(project));
		
		
		if(GdsSubmissionActionHelper.willThereBeAnyDataSubmittedInGdsPlan(getProject())) {
			setupMissingDataList(ApplicationConstants.PAGE_CODE_IC, 
				missingDataUtil.getMissingIcListData(project));
			setupMissingDataList(ApplicationConstants.PAGE_CODE_BSI, 
				missingDataUtil.getMissingBsiData(project));
		}
		
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

package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.InstitutionalCertification;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectsVw;
import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;
import gov.nih.nci.cbiit.scimgmt.gds.model.ExportRow;
import gov.nih.nci.cbiit.scimgmt.gds.model.Submission;

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
					answer.append(";");
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
	
}

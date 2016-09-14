package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.GdsGrantsContracts;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PageStatus;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.model.MissingData;
import gov.nih.nci.cbiit.scimgmt.gds.services.FileUploadService;
import gov.nih.nci.cbiit.scimgmt.gds.services.LookupService;
import gov.nih.nci.cbiit.scimgmt.gds.services.ManageProjectService;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper;

/**
 * Manages Submission creation, updates and deletion.
 * 
 * @author tembharend
 *
 */
@SuppressWarnings("serial")
public class ManageSubmission extends BaseAction {
	
	static Logger logger = LogManager.getLogger(ManageSubmission.class);
	
	@Autowired
	protected ManageProjectService manageProjectService;
	
	@Autowired 
	protected FileUploadService fileUploadService;	
	
	@Autowired
	protected LookupService lookupService;
	
	private Project project;
	
	private Long docId;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	private Lookup page;
	
	protected List<MissingData> missingDataList = new ArrayList<MissingData>();
	
	
	/**
	 * Execute method, for now used for navigation
	 * 
	 * @return forward string
	 */
	public String execute() throws Exception {
		logger.debug("execute");
        
        return SUCCESS;
	}
	

	/**
	 * @return the selectedProject
	 */
	public Project getProject() {
		return project;
	}


	/**
	 * @param selectedProject the selectedProject to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}
	
	
	/**
	 * Retrieve the project based on the projectId indicated in the request
	 * 
	 * @return
	 */
	public Project retrieveSelectedProject() {
	
		String projectId  = getProjectId();
		if(StringUtils.isNotBlank(projectId)) {
			return manageProjectService.findById(Long.valueOf(projectId));
		} 
		
		return null;
	}
	
	
	public Project retrieveParentProject() {
		
		if(project == null) {
			String projectId  = getProjectId();
			if(StringUtils.isNotBlank(projectId)) {
				project =  manageProjectService.findById(Long.valueOf(projectId));
			} 
		}
		
		return retrieveParentProject(project);
	}
	
	
	public Project retrieveParentProject(Project project) {
		
		Long parentProjectId = project.getParentProjectId();
		if(parentProjectId != null) {
			Project parentProject =  manageProjectService.findById(Long.valueOf(parentProjectId));
			loadGrantInfo(parentProject);
			return parentProject;
		} 
		return null;
	}
	
	public List<Project> getSubprojects() {
		
		Long projectId = project.getId();
		if(projectId != null) {
			List<Project> subprojects =  manageProjectService.getSubprojects(projectId);
			for(Project subproject: subprojects) {
				loadGrantInfo(subproject);
			}
			return subprojects;
		} 
		return new ArrayList<Project>();
	}
	
	public List<Project> retrieveVersions(Project project) {
		
		Long projectGroupId = project.getProjectGroupId();
		if(projectGroupId != null) {
			List<Project> versions =  manageProjectService.getVersions(projectGroupId);
			return versions;
		} 
		return new ArrayList<Project>();
	}
	
	/**
	 * Save the project
	 */
	public Project saveProject(Project project, String pageCode) {
		
		//Temporary hard coding project property. 
		project.setVersionNum(1l);
		project.setLatestVersionFlag("Y");
		if(project.getParentProjectId() == null){
			project.setSubprojectFlag("N");
			if(GdsSubmissionActionHelper.isEligibleForSubproject(project))
				project.setSubprojectEligibleFlag("Y");
			else
				project.setSubprojectEligibleFlag("N");
		}
		else{
			project.setSubprojectFlag("Y");
			project.setSubprojectEligibleFlag("N");
		}
		
		//Set the page status on the project
		if(pageCode != null) {
			//We are not in the General Info page, so the project has
			//already been saved, hence add a status only for this page
			String statusCode = computePageStatus(project);
			PageStatus pageStatus = new PageStatus(
				lookupService.getLookupByCode(ApplicationConstants.PAGE_STATUS_TYPE, statusCode),
				lookupService.getLookupByCode(ApplicationConstants.PAGE_TYPE, pageCode),
				project, loggedOnUser.getFullNameLF(), new Date());	
			project.addUpdatePageStatus(pageStatus);
		} else  {
			//We are in the General Info page. Check if this is a new submission
			if (project.getId() == null) {
				project.setPageStatuses(initPageStatuses(project));
			}
		}
		
		//Set the exception memo status
		project.setDataSharingExcepStatus(
			lookupService.getLookupByCode(ApplicationConstants.PAGE_STATUS_TYPE, getExceptionMemoStatusCode(project)));
		
		return manageProjectService.saveOrUpdate(project);
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
	
	
	public String getProjectStatusCode(Project project) {
		
		List<PageStatus> statuses = project.getPageStatuses();
		if(CollectionUtils.isEmpty(statuses)) {
			return ApplicationConstants.PAGE_STATUS_CODE_NOT_STARTED;
		}
		
		for(PageStatus status: statuses) {
			String statusCode = status.getStatus().getCode();
			if(statusCode.equals(ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS) 
				|| statusCode.equals(ApplicationConstants.PAGE_STATUS_CODE_NOT_STARTED)) {
				return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
			} 			
		} 
		
		return ApplicationConstants.PAGE_STATUS_CODE_COMPLETED;
	}
	
	public String getExceptionMemoStatusCode() {
		return getExceptionMemoStatusCode(retrieveSelectedProject());
	}
	
	public String getExceptionMemoStatusCode(Project project) {
		
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
	
	/**
	 * Delete a file using document id
	 */
	public String deleteFile() {
		logger.debug("deleteFile()");
		try {
			if (docId == null) {
				inputStream = new ByteArrayInputStream(
						getText("error.doc.id").getBytes("UTF-8"));

				return SUCCESS;
			}
			fileUploadService.deleteFile(docId);
			inputStream = new ByteArrayInputStream(getText("document.delete.success").getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * Retrieve a file using document id
	 */
	public String downloadFile() {
		logger.debug("downloadFile()");
		try {
			if (docId == null) {
				inputStream = new ByteArrayInputStream(
						getText("error.doc.id").getBytes("UTF-8"));
				return SUCCESS;
			}
			HttpServletResponse response = ServletActionContext.getResponse();
			Document doc = fileUploadService.retrieveFile(docId);
			if (doc == null) {
				inputStream = new ByteArrayInputStream(getText("error.doc.notFound").getBytes("UTF-8"));
				return SUCCESS;
			}

			if(StringUtils.isBlank(doc.getFileName())) {
				response.setHeader("Content-Disposition", "inline;filename=\"" + doc.getDocTitle() + "\"");
			}
			else {
				response.setHeader("Content-Disposition", "inline;filename=\"" + doc.getFileName() + "\"");
			}
			
			OutputStream out = response.getOutputStream();
			response.setContentType(doc.getContentType());
			out.write(doc.getDoc());
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public boolean showPage(String page) {
		return showPage(page, project);
	}
	
	/**
	 * Determine whether a page should be shown
	 * @param page
	 * @return
	 */
	public boolean showPage(String page, Project project) {
		logger.debug("showPage()");
		
		boolean show = true;
		
		//Do not show this page for a sub-project it this is the GDS
		//Plan or if the page is not being shown by the parent.
		Project parent = retrieveParentProject(project);
		if(parent != null) {
			if(page.equalsIgnoreCase(ApplicationConstants.PAGE_TYPE_GDSPLAN)
					|| !showPage(page, parent)) {
				return false;
			}
		}
		
		//If submission reason is non-NIH fund, do not show IC and GDS Plan
		if(ApplicationConstants.SUBMISSION_REASON_NONNIHFUND.equals(project.getSubmissionReasonId())) {
			if(page.equalsIgnoreCase(ApplicationConstants.PAGE_TYPE_IC) ||
				page.equalsIgnoreCase(ApplicationConstants.PAGE_TYPE_GDSPLAN)) {
				return false;
			}
		}
		
		// If user selects "Non-human" only, the system will NOT display the "Institutional Certifications"
		if(project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_HUMAN_ID) == null &&
				project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_NONHUMAN_ID) != null) {
			if(page.equalsIgnoreCase(ApplicationConstants.PAGE_TYPE_IC)) {
				show = false;
			}
		}
		
		// If user selects ONLY the "Other" repository in the "What repository will the data be submitted to?" question GDS plan page, 
		// the "Institutional Certification" page will not be displayed.
		Set<PlanAnswerSelection> repoSet = project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID);
		boolean otherRepoOnly = true;
		boolean otherRepoExist = false;
		for(PlanAnswerSelection repo: repoSet) {
			if(repo.getPlanQuestionsAnswer().getId().longValue() == ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_OTHER_ID.longValue())
				otherRepoExist = true;
			if(repo.getPlanQuestionsAnswer().getId().longValue() != ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_OTHER_ID.longValue())
				otherRepoOnly = false;
		}
		if(otherRepoExist && otherRepoOnly) {
			if(page.equalsIgnoreCase(ApplicationConstants.PAGE_TYPE_IC)) {
				show = false;
			}
		}
		
		// If the answer to "Will there be any data submitted?" is No.
		// Don't show IC, BSI.
		if (project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_NO_ID) != null) {
			if(page.equalsIgnoreCase(ApplicationConstants.PAGE_TYPE_IC) || page.equalsIgnoreCase(ApplicationConstants.PAGE_TYPE_BSI)) {
				show = false;
			}
		}
		else if (project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID).isEmpty()) {
			// If there are no repository selected, don't show the repository page.
			if(page.equalsIgnoreCase(ApplicationConstants.PAGE_TYPE_STATUS)) {
				show = false;
			}
		}
		
		return show;
	}
	
	/**
	 * Get docId
	 * 
	 * @return
	 */
	public Long getDocId() {
		return docId;
	}

	/**
	 * Set docId
	 * 
	 * @param docId
	 */
	public void setDocId(Long docId) {
		this.docId = docId;
	}

	
	/**
	 * Get Lookup object by list name and code
	 * 
	 * @param id
	 * @return
	 */
	public  String getLookupDisplayNamebyId(Long id) {
		List<Lookup> list = (List<Lookup>) lookupService.getAllLookupLists();
		for(Lookup entry: list) {
			if (entry.getId().equals(id))
				return entry.getDisplayName();
		}
		return null;
	}
	
	
	public String getDisplayNameByFlag(String flag) {
		if(ApplicationConstants.FLAG_YES.equals(flag)) {
			 return ApplicationConstants.DISPLAY_NAME_YES;
		} else if(ApplicationConstants.FLAG_NO.equals(flag)) {
			return ApplicationConstants.DISPLAY_NAME_NO;
		} else {
			return flag;
		}
	}
	
	
	/**
	 * Validate Upload File
	 */
	protected boolean validateUploadFile(File file, String contentType) {

		String errorMessage = "";
		
		try {
			if (file == null) {
				errorMessage = getText("error.doc.required");

			} else if (file.length() == 0) {
				errorMessage = getText("error.doc.empty");

			} else if (file.length() > 5000000) {
				errorMessage = getText("error.doc.size");

			} else if (!"application/pdf".equals(contentType)
					&& !"application/msword".equals(contentType)
					&& !"application/vnd.openxmlformats-officedocument.wordprocessingml.document"
							.equals(contentType)) {
				errorMessage = getText("error.doc.format");

			}
			if(StringUtils.isNotBlank(errorMessage)) {
				inputStream = new ByteArrayInputStream(errorMessage.getBytes("UTF-8"));
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public void loadGrantInfo() {
		loadGrantInfo(getProject());
	}
	/**
	 * This method retrieves grant information data from grantsContractsw if project has grant/contract tied to it.
	 */
	public void loadGrantInfo(Project project){
			
			logger.debug("Retreiving Project grant information data from grantsContractsw for grantContract with applId: "+project.getApplId());
			GdsGrantsContracts grantContract = manageProjectService.getGrantOrContract(project.getApplId());
			if(StringUtils.equals(project.getDataLinkFlag(), "Y")){
			if(grantContract != null){
				project.setProjectTitle(grantContract.getProjectTitle());
				project.setPiFirstName(grantContract.getPiFirstName());
				project.setPiLastName(grantContract.getPiLastName());
				project.setPiInstitution(grantContract.getPiInstitution());
				project.setPiEmailAddress(grantContract.getPiEmailAddress());
				project.setPdFirstName(grantContract.getPdFirstName());
				project.setPdLastName(grantContract.getPdLastName());
				project.setProjectStartDate(grantContract.getProjectPeriodStartDate());
				project.setProjectEndDate(grantContract.getProjectPeriodEndDate());
				project.setApplClassCode(grantContract.getApplClassCode());
				project.setCayCode(grantContract.getCayCode());
			}
		}
		else {
			if(grantContract != null) 
				project.setCayCode(grantContract.getCayCode());
			
		}
	}
	
	//Get project start date
	public String getProjectStartDate() {		
		return dateFormat.format(getProject().getProjectStartDate());
	}

	//Get project end date
	public String getProjectEndDate() {
		return dateFormat.format(getProject().getProjectEndDate());
	}
	
	//Invoked to display status on individual pages
	public String getPageStatusCode(String pageCode) {
		return getPageStatus(pageCode).getStatus().getCode();
	}
	
	public PageStatus getPageStatus(String pageCode) {
		PageStatus pageStatus = 
			getProject().getPageStatus(pageCode);
		if(pageStatus == null) {
			return new PageStatus(
					lookupService.getLookupByCode(ApplicationConstants.PAGE_STATUS_TYPE, ApplicationConstants.PAGE_STATUS_CODE_NOT_STARTED),
					lookupService.getLookupByCode(ApplicationConstants.PAGE_TYPE, pageCode),
					project, null, null);
		}
		return pageStatus;
	}
	
	
	public List<MissingData> getMissingDataList() {
		return missingDataList;
	}
	
	
	public void setMissingDataList(List<MissingData> missingDataList) {
		this.missingDataList = missingDataList;
	}
	
	/**
	 * @return the page
	 */
	public Lookup getPage() {
		return page;
	}


	/**
	 * @param page the page to set
	 */
	public void setPage(Lookup page) {
		this.page = page;
	}
	
	protected String computePageStatus(Project project) {
		//Override
		return null;
	}
	
}

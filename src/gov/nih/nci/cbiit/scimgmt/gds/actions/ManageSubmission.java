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
			return parentProject;
		} 
		return null;
	}
	
	public List<Project> retrieveSubprojects(Project project) {
		
		Long projectId = project.getId();
		if(projectId != null) {
			List<Project> subproject =  manageProjectService.getSubprojects(projectId);
			return subproject;
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
	
	/**
	 * This method retrieves grant information data from grantsContractsw if project has grant/contract tied to it.
	 */
	public void loadGrantInfo(){
		
		Project project = getProject();		
		if(StringUtils.equals(project.getDataLinkFlag(), "Y")){
			
			logger.debug("Retreiving Project grant information data from grantsContractsw for grantContract with applId: "+project.getApplId());
			GdsGrantsContracts grantContract = manageProjectService.getGrantOrContract(project.getApplId());
			if(grantContract != null){
				getProject().setProjectTitle(grantContract.getProjectTitle());
				getProject().setPiFirstName(grantContract.getPiFirstName());
				getProject().setPiLastName(grantContract.getPiLastName());
				getProject().setPiInstitution(grantContract.getPiInstitution());
				getProject().setPiEmailAddress(grantContract.getPiEmailAddress());
				getProject().setPdFirstName(grantContract.getPdFirstName());
				getProject().setPdLastName(grantContract.getPdLastName());
				getProject().setProjectStartDate(grantContract.getProjectPeriodStartDate());
				getProject().setProjectEndDate(grantContract.getProjectPeriodEndDate());
				getProject().setApplClassCode(grantContract.getApplClassCode());
			}
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

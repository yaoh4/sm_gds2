package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.constants.PlanQuestionList;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.GdsGrantsContracts;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PageStatus;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanQuestionsAnswer;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectGrantContract;
import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectsVw;
import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Study;
import gov.nih.nci.cbiit.scimgmt.gds.model.MissingData;
import gov.nih.nci.cbiit.scimgmt.gds.services.FileUploadService;
import gov.nih.nci.cbiit.scimgmt.gds.services.ManageProjectService;
import gov.nih.nci.cbiit.scimgmt.gds.services.SearchProjectService;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsPageStatusUtil;
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
	protected SearchProjectService searchProjectService;	
	
	
	private Project project;
	
	private Long docId;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	private Lookup page;
	
	private String editFlag = ApplicationConstants.FLAG_NO;
	
	protected List<MissingData> missingDataList = new ArrayList<MissingData>();
	
	private Map<Long, List<String>> answerMap = new HashMap<Long, List<String>>();
	
	private Map<Long, List<String>> otherTextMap = new HashMap<Long, List<String>>();
	
	protected Set<Long> newSet = new HashSet<Long>();
	
	protected Set<Long> oldSet = new HashSet<Long>();
	
	protected Set<Long> otherSet = new HashSet<Long>();
	
	private ProjectGrantContract extramuralGrant = new ProjectGrantContract(ApplicationConstants.GRANT_CONTRACT_TYPE_EXTRAMURAL,ApplicationConstants.FLAG_YES);
	private ProjectGrantContract intramuralGrant = new ProjectGrantContract(ApplicationConstants.GRANT_CONTRACT_TYPE_INTRAMURAL, ApplicationConstants.FLAG_YES);
	private List<ProjectGrantContract> associatedSecondaryGrants =new ArrayList<ProjectGrantContract>(); 
	
	protected List<Study> studiesForSelection = new ArrayList<Study>();


	


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
	
	
	public ProjectsVw getProjectsVw() {
		return manageProjectService.findProjectsVwById(getProject().getId());
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
			Project parentProject =  manageProjectService.findById(parentProjectId);
			//loadGrantInfo(parentProject);
			return parentProject;
		} 
		return null;
	}
	
	public List<ProjectsVw> getSubprojects() {
		
		Long projectId = project.getId();
		if(projectId != null) {
			List<ProjectsVw> subprojects = manageProjectService.getSubprojectsVw(projectId);
			return subprojects;
		} 
		return new ArrayList<ProjectsVw>();
	}
	
	public List<Project> retrieveVersions(Project project) {
		
		Long projectGroupId = project.getProjectGroupId();
		Long parentProjectId = project.getParentProjectId();
		if(projectGroupId != null) {
			List<Project> versions =  manageProjectService.getVersions(projectGroupId, parentProjectId);
			if (versions != null) {
				Project np = null;
				for (final Iterator<Project> i = versions.iterator(); i.hasNext();) {
					np = i.next();
					if (project.getId().equals(np.getId())) {
						i.remove();
					}
				}
			}
			return versions;
		} 
		return new ArrayList<Project>();
	}
	
	/**
	 * Save the project
	 */
	public Project saveProject(Project project, String page) {
		return saveProject(project, page, true);
	}
	
	/**
	 * Save the project
	 */
	public Project saveProject(Project project, String page, boolean saveSubprojects) {
		
		// Set subproject flag and subproject eligibility 
		if(project.getParentProjectId() == null){
			project.setSubprojectFlag(ApplicationConstants.FLAG_NO);
			if(GdsSubmissionActionHelper.isEligibleForSubproject(project))
				project.setSubprojectEligibleFlag(ApplicationConstants.FLAG_YES);
			else
				project.setSubprojectEligibleFlag(ApplicationConstants.FLAG_NO);
		}
		else{
			project.setSubprojectFlag(ApplicationConstants.FLAG_YES);
			project.setSubprojectEligibleFlag(ApplicationConstants.FLAG_NO);
		}
		
		//Set the exception memo status
		project.setDataSharingExcepStatus(
			getLookupByCode(ApplicationConstants.PAGE_STATUS_TYPE, getExceptionMemoStatusCode(project)));
		
		project.setPageStatuses(computePageStatuses(project, page));
		
		// Set version eligibility
		project.setVersionEligibleFlag(
				GdsSubmissionActionHelper.isProjectEligibleForVersion(project));
		
		project= manageProjectService.saveOrUpdate(project);
		
		if(saveSubprojects) {
			//We save subprojects also if we feel that the status could have changed
			List<Project> subprojects = manageProjectService.getSubprojects(project.getId(), true);
			for(Project subproject: subprojects) {
				subproject.setPageStatuses(computePageStatuses(subproject, page));
				manageProjectService.saveOrUpdate(subproject);
			}
		}
		
		return project;
	}

	
	private List<PageStatus> computePageStatuses(Project project, String modifiedPageCode) {
		
		List<PageStatus> pageStatuses = new ArrayList<PageStatus>();
		
		//GDS Plan page status
		String status = GdsPageStatusUtil.getInstance().computeGdsPlanStatus(project);
		PageStatus pageStatus = updatePageStatus(project, ApplicationConstants.PAGE_CODE_GDSPLAN, status,	
				ApplicationConstants.PAGE_CODE_GDSPLAN.equals(modifiedPageCode) ? true : false);
		if(pageStatus != null) {
			pageStatuses.add(pageStatus);
		}
		
		//IC List status
		status = GdsPageStatusUtil.getInstance().computeIcListStatus(project);
		pageStatus = updatePageStatus(project, ApplicationConstants.PAGE_CODE_IC, status,	
				ApplicationConstants.PAGE_CODE_IC.equals(modifiedPageCode) ? true : false);
		if(pageStatus != null) {
			pageStatuses.add(pageStatus);
		}
		
		//BSI Study Info status
		status = GdsPageStatusUtil.getInstance().computeBsiStudyInfoStatus(project);
		pageStatus = updatePageStatus(project, ApplicationConstants.PAGE_CODE_BSI, status,	
				ApplicationConstants.PAGE_CODE_BSI.equals(modifiedPageCode) ? true : false);
		if(pageStatus != null) {
			pageStatuses.add(pageStatus);
		}
		
		//Repository status
		status = GdsPageStatusUtil.getInstance().computeRepositoryStatus(project);
		pageStatus = updatePageStatus(project, ApplicationConstants.PAGE_CODE_REPOSITORY, status,	
				ApplicationConstants.PAGE_CODE_REPOSITORY.equals(modifiedPageCode) ? true : false);
		if(pageStatus != null) {
			pageStatuses.add(pageStatus);
		}
		
		return pageStatuses;
	}
	
	
	private PageStatus updatePageStatus(Project project, String pageCode, 
			String status, boolean userUpdated) {
		
		if(status != null) {
			//Get the existing pageStatus
			PageStatus pageStatus = getPageStatus(project, pageCode);
		
			//Update the status
			pageStatus.setStatus(getLookupByCode(ApplicationConstants.PAGE_STATUS_TYPE, status));
			if(userUpdated) {
				pageStatus.setLastChangedBy(loggedOnUser.getAdUserId());
				pageStatus.setLastChangedDate(new Date());
			}
			return pageStatus;
		}
		
		return null;
	}
	
	public String getProjectStatusCode(Long Id) {
		
		ProjectsVw projectsVw = manageProjectService.findProjectsVwById(Id);
		return projectsVw.getProjectStatusCode();
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
			if(page.equalsIgnoreCase(ApplicationConstants.PAGE_TYPE_GDSPLAN)) {
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
		
		//If the answer to "Will there be any data submitted?" is No.
		// Don't show IC, BSI.
		if (project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_NO_ID) != null) {
			if(page.equalsIgnoreCase(ApplicationConstants.PAGE_TYPE_IC) || page.equalsIgnoreCase(ApplicationConstants.PAGE_TYPE_BSI)) {
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
	 * Validate Upload File
	 */
	protected boolean validateUploadFile(File file, String contentType) {

		String errorMessage = "";
		
		try {
			if (file == null) {
				errorMessage = getText("error.doc.required");

			} else if (file.length() == 0) {
				errorMessage = getText("error.doc.empty");

			} else if (file.length() > 15728640) {
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
			
		ProjectGrantContract extramuralGrantContract = project.getPrimaryGrant(ApplicationConstants.GRANT_CONTRACT_TYPE_EXTRAMURAL);
		if(extramuralGrantContract != null) {
			if(extramuralGrantContract.getApplId() != null && 
				ApplicationConstants.FLAG_YES.equals(extramuralGrantContract.getDataLinkFlag()) && !ApplicationConstants.SUBMISSION_REASON_NONNIHFUND.equals(getProject().getSubmissionReasonId())) {
			
				GdsGrantsContracts grantContract = manageProjectService.getGrantOrContract(extramuralGrantContract.getApplId());
				if(grantContract != null){
					extramuralGrantContract.setGrantContractNum(grantContract.getSegGrantContractNum());
					extramuralGrantContract.setProjectTitle(grantContract.getSegProjectTitle());
					extramuralGrantContract.setPiFirstName(grantContract.getSegPiFirstName());
					extramuralGrantContract.setPiLastName(grantContract.getSegPiLastName());
					extramuralGrantContract.setPiInstitution(grantContract.getSegPiInstitution());
					extramuralGrantContract.setPiEmailAddress(grantContract.getSegPiEmailAddress());
					extramuralGrantContract.setPdFirstName(grantContract.getSegPdFirstName());
					extramuralGrantContract.setPdLastName(grantContract.getSegPdLastName());
					extramuralGrantContract.setProjectStartDate(grantContract.getSegProjectPeriodStartDate());
					extramuralGrantContract.setProjectEndDate(grantContract.getSegProjectPeriodEndDate());
					extramuralGrantContract.setApplClassCode(grantContract.getSegApplClassCode());
					extramuralGrantContract.setCayCode(grantContract.getSegCayCode());
				}
			}
			setExtramuralGrant(extramuralGrantContract);
		} else {
			setExtramuralGrant(new ProjectGrantContract(ApplicationConstants.GRANT_CONTRACT_TYPE_EXTRAMURAL,ApplicationConstants.FLAG_YES));
		}
		
		
		ProjectGrantContract intramuralGrantContract = project.getPrimaryGrant(ApplicationConstants.GRANT_CONTRACT_TYPE_INTRAMURAL);
		if(intramuralGrantContract != null) {
			setIntramuralGrant(intramuralGrantContract);
		} else {
			setIntramuralGrant(new ProjectGrantContract(ApplicationConstants.GRANT_CONTRACT_TYPE_INTRAMURAL, ApplicationConstants.FLAG_YES));
		}
		
		List<ProjectGrantContract> associatedGrants = project.getAssociatedGrants();
		if(associatedGrants != null) {
		setAssociatedSecondaryGrants(associatedGrants);
		}
	}
	
	public GdsGrantsContracts getPiInfo(Long applId) {
		logger.debug("get applId here:" + applId);
	    if(applId != null) {
		    GdsGrantsContracts grantContract = manageProjectService.getGrantOrContract(applId);
			return grantContract;
		 }
	    return null;
	}
	
	//Get project start date
	public String getProjectStartDate() {		
		return dateFormat.format(extramuralGrant.getProjectStartDate());
	}

	//Get project end date
	public String getProjectEndDate() {
		return dateFormat.format(extramuralGrant.getProjectEndDate());
	}
	
	//Invoked to display status on individual pages
	public String getPageStatusCode(String pageCode) {
		return getPageStatus(pageCode).getStatus().getCode();
	}
	
	
	public PageStatus getPageStatus(String pageCode) {
		return getPageStatus(getProject(), pageCode);
	}
	
	private PageStatus getPageStatus(Project project, String pageCode) {
		PageStatus pageStatus = 
			project.getPageStatus(pageCode);
		if(pageStatus == null) {
			return new PageStatus(
					getLookupByCode(ApplicationConstants.PAGE_STATUS_TYPE, ApplicationConstants.PAGE_STATUS_CODE_NOT_STARTED),
					getLookupByCode(ApplicationConstants.PAGE_TYPE, pageCode),
					project, null, new Date());
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
	
	
	/**
	 * @return the editFlag
	 */
	public String getEditFlag() {
		return editFlag;
	}


	/**
	 * @param editFlag the editFlag to set
	 */
	public void setEditFlag(String editFlag) {
		this.editFlag = editFlag;
	}


	public Map<Long, List<String>> getAnswers() {
		return answerMap;
	}

	public void setAnswers(Map<Long, List<String>> answers) {
		this.answerMap = answers;
	}
	
	public Map<Long, List<String>> getOtherText() {
		return otherTextMap;
	}

	public void setOtherText(Map<Long, List<String>> otherText) {
		this.otherTextMap = otherText;
	}
	
	/**
	 * @return the intramuralGrant
	 */
	public ProjectGrantContract getIntramuralGrant() {
		return intramuralGrant;
	}


	/**
	 * @param intramuralGrant the intramuralGrant to set
	 */
	public void setIntramuralGrant(ProjectGrantContract intramuralGrant) {
		this.intramuralGrant = intramuralGrant;
	}
     
	public List<ProjectGrantContract> getAssociatedSecondaryGrants() {
		return associatedSecondaryGrants;
	}


	public void setAssociatedSecondaryGrants(List<ProjectGrantContract> associatedSecondaryGrants) {
		this.associatedSecondaryGrants = associatedSecondaryGrants;
	}


	/**
	 * @return the extramuralGrant
	 */
	public ProjectGrantContract getExtramuralGrant() {
		return extramuralGrant;
	}


	/**
	 * @param extramuralGrant the extramuralGrant to set
	 */
	public void setExtramuralGrant(ProjectGrantContract extramuralGrant) {
		this.extramuralGrant = extramuralGrant;
	}


	/**
	 * Return true if answer should be pre-selected 
	 * based on saved data for checkboxes
	 */
	public boolean getSelected(Long qId, String aId) {
		
		List<String> list = answerMap.get(qId);
		if(list != null && list.contains(aId))
			return true;
		return false;
	}
	
	public PlanQuestionsAnswer getQuestionById(Long qid) {
		return PlanQuestionList.getQuestionById(qid);
	}
	
	
	public List<PlanQuestionsAnswer> getAnswerListByQuestionId(Long qid) {
		return PlanQuestionList.getAnswerListByQuestionId(qid);
	}

	
	/**
	 * This method converts the PlanAnswerSelection objects to answers map
	 * @throws Exception 
	 */
	protected void populateAnswersMap() {

		List<PlanAnswerSelection> savedList = new LinkedList<PlanAnswerSelection>(
				getProject().getPlanAnswerSelections());

		class PlanAnswerSelectionComparator implements Comparator<PlanAnswerSelection> {

			public int compare(PlanAnswerSelection e1, PlanAnswerSelection e2) {
				return e1.getPlanQuestionsAnswer().getQuestionId()
						.compareTo(e2.getPlanQuestionsAnswer().getQuestionId());
			}

		}

		Collections.sort(savedList, new PlanAnswerSelectionComparator());
 
		answerMap.clear();
		Long prevId = null;
		Long otherId = null;
		List<String> ansList = new ArrayList<String>();
		List<String> otherList = new ArrayList<String>();
		for (PlanAnswerSelection e: savedList) {
			Long qId = e.getPlanQuestionsAnswer().getQuestionId();
			Long aId = e.getPlanQuestionsAnswer().getId();
			if(prevId != null && prevId.longValue() != qId.longValue()) {
				answerMap.put(prevId, ansList);
				if(otherId !=  null) {
					Collections.sort(otherList, String.CASE_INSENSITIVE_ORDER);
					otherTextMap.put(otherId, otherList);
					otherList = new ArrayList<String>();
					otherId = null;
				}
				ansList = new ArrayList<String>();
			}
			prevId = qId;
			ansList.add(aId.toString());
			if(StringUtils.isNotBlank(e.getOtherText())) {
				otherId = aId;
				otherList.add(e.getOtherText());
			}
		}
		if(!ansList.isEmpty()) {
			answerMap.put(prevId, ansList);
			if(otherId !=  null) {
				Collections.sort(otherList, String.CASE_INSENSITIVE_ORDER);
				otherTextMap.put(otherId, otherList);
			}
		}

	}
	
	
	/**
	 * This method converts the user answers to PlanAnswerSelection objects
	 * @throws Exception 
	 */
	protected void populatePlanAnswerSelection() throws Exception{
				
		for(Long id: oldSet) {
			for (Iterator<PlanAnswerSelection> planAnswerSelectionIterator = getProject().getPlanAnswerSelections().iterator(); planAnswerSelectionIterator.hasNext();) {
				PlanAnswerSelection savedOther = planAnswerSelectionIterator.next();
				if(savedOther.getPlanQuestionsAnswer().getId().longValue() == id.longValue()) {
					planAnswerSelectionIterator.remove();
				}
			}
		}
		
		for(Long id: otherSet) {
			for (Iterator<PlanAnswerSelection> planAnswerSelectionIterator = getProject().getPlanAnswerSelections().iterator(); planAnswerSelectionIterator.hasNext();) {
				PlanAnswerSelection savedOther = planAnswerSelectionIterator.next();
				if(savedOther.getPlanQuestionsAnswer().getId().longValue() == id.longValue() &&
						StringUtils.isNotBlank(savedOther.getOtherText())) {
					boolean found = false;
					for(String otherText: otherTextMap.get(id)) {
						if(StringUtils.equals(savedOther.getOtherText(), otherText)) {
							found = true;
							break;
						}
					}
					if(!found) {
						planAnswerSelectionIterator.remove();
					}
				}
			}
		}
		
		PlanAnswerSelection newObject = null;
		for (Long id : newSet) {
			PlanQuestionsAnswer planQuestionsAnswer = PlanQuestionList.getAnswerByAnswerId(id);
			if(planQuestionsAnswer.getDisplayText().equalsIgnoreCase(ApplicationConstants.OTHER) && otherTextMap != null && !otherTextMap.isEmpty()) {
				for(String otherText: otherTextMap.get(id)) {
					newObject = getProject().getPlanAnswerSelectionByAnswerIdAndText(id, otherText);
					if(newObject == null) {
						newObject = new PlanAnswerSelection();
						newObject.setCreatedBy(loggedOnUser.getAdUserId());
						newObject.setOtherText(otherText);
						newObject.setPlanQuestionsAnswer(planQuestionsAnswer);
						newObject.addProject(getProject());
						getProject().getPlanAnswerSelections().add(newObject);
					}
				}
			} else {
				newObject = getProject().getPlanAnswerSelectionByAnswerId(id);
				if(newObject == null) {
					newObject = new PlanAnswerSelection();
					newObject.setCreatedBy(loggedOnUser.getAdUserId());
					newObject.setPlanQuestionsAnswer(planQuestionsAnswer);
					newObject.addProject(getProject());
					getProject().getPlanAnswerSelections().add(newObject);
				}
			}
		}

	}
	
	
	/**
	 * Construct new and old set of answers to be used for warning and
	 * Plan Answer Selection object removal/creation
	 */
	protected void populateSelectedRemovedSets(boolean warn) {
		Set<Long> origSet = new HashSet<Long>();
		newSet.clear();
		oldSet.clear();
		otherSet.clear();
		
		for (Entry<Long, List<String>> e : getAnswers().entrySet()) {
			for(String entry: e.getValue()) {
				newSet.add(Long.valueOf(entry));
				List<String> otherList = getOtherText().get(Long.valueOf(entry));
				if(otherList != null && !otherList.isEmpty()) {
					otherSet.add(Long.valueOf(entry));
				}
			}
		}
		for (PlanAnswerSelection e: getProject().getPlanAnswerSelections()) {
			origSet.add(e.getPlanQuestionsAnswer().getId());
		}
		
		oldSet.addAll(origSet);
		if(!warn) {
			oldSet.removeAll(newSet); // deleted set
		
			newSet.removeAll(origSet); // added set
		}
		newSet.addAll(otherSet);
		
	}
	
	
	/**
	 * This method sets up Repository Statuses.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	protected void setupRepositoryStatuses(Project project) {
		
		logger.debug("Setting up Repository statuses.");
		for(PlanAnswerSelection selection: project.getPlanAnswerSelections()) {
			if( ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID.equals(selection.getPlanQuestionsAnswer().getQuestionId())) {
				
				if(selection.getRepositoryStatuses().isEmpty()) {
					//Add a new repository status if this is a new selection by
					//the user or this is a subproject. 
					RepositoryStatus repoStatus = createRepositoryStatus(selection);
					repoStatus.setProject(project);
				} else {
					RepositoryStatus repoStatus = selection.getRepositoryStatuses().iterator().next();		
					if(getProject().getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_NO_ID) != null) {
						//If no data is to be submitted, then set the submission status to NA and study released to No
						repoStatus.setLookupTBySubmissionStatusId(
							getLookupByCode(ApplicationConstants.PROJECT_SUBMISSION_STATUS_LIST, ApplicationConstants.NOT_APPLICABLE));
						repoStatus.setLookupTByStudyReleasedId(
							getLookupByCode(ApplicationConstants.STUDY_RELEASED_LIST, ApplicationConstants.NO));
						repoStatus.setAccessionNumber("");
					} else {
						//If 'Will there be any data submitted' is 'Yes', then if registration status is not started,
						//then restore the submission status to Not Started, else it may stay in
						//the NA state if 'Will there be any data submitted' was set to No earlier.
						if(repoStatus.getLookupTByRegistrationStatusId().getCode().equals(ApplicationConstants.NOT_STARTED)) {							
							repoStatus.setLookupTBySubmissionStatusId(
								getLookupByCode(ApplicationConstants.PROJECT_SUBMISSION_STATUS_LIST, ApplicationConstants.NOT_STARTED));	
						}
					}
				}
			}
		}
	}
	
	protected RepositoryStatus createRepositoryStatus(PlanAnswerSelection selection) {
		
		RepositoryStatus repoStatus = new RepositoryStatus();
		repoStatus.setLookupTByRegistrationStatusId(
			getLookupByCode(ApplicationConstants.REGISTRATION_STATUS_LIST, ApplicationConstants.NOT_STARTED));
		repoStatus.setLookupTBySubmissionStatusId(
			getLookupByCode(ApplicationConstants.PROJECT_SUBMISSION_STATUS_LIST, ApplicationConstants.NOT_STARTED));
		repoStatus.setLookupTByStudyReleasedId(
			getLookupByCode(ApplicationConstants.STUDY_RELEASED_LIST, ApplicationConstants.NO));
		repoStatus.setCreatedBy(loggedOnUser.getAdUserId());
		repoStatus.setCreatedDate(new Date());
		repoStatus.setPlanAnswerSelectionTByRepositoryId(selection);
		selection.getRepositoryStatuses().add(repoStatus);
	
		return repoStatus;
	}
	
	protected String computePageStatus(Project project) {
		//Override
		return null;
	}
	
	public List<Study> getStudiesForSelection() {
		return studiesForSelection;
	}

	public void setStudiesForSelection(List<Study> studiesForSelection) {
		this.studiesForSelection = studiesForSelection;
	}
	
	/**
	 * Filter out the list of studies that are not tied to any IC from project.studies
	 * 
	 * @return
	 */
	protected List<Study> retrieveStudies() {

		List<Study> studies = new ArrayList<Study>();
		
		for(Study study: getProject().getStudies()) {
			if (study.getInstitutionalCertification() == null) {
				studies.add(study);
			}
		}
		
		return studies;
	}

}

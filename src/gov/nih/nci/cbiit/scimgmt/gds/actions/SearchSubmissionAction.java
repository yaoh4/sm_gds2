package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.GdsPd;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Organization;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectsVw;
import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;
import gov.nih.nci.cbiit.scimgmt.gds.model.ExportRow;
import gov.nih.nci.cbiit.scimgmt.gds.model.Submission;
import gov.nih.nci.cbiit.scimgmt.gds.model.SubmissionSearchCriteria;
import gov.nih.nci.cbiit.scimgmt.gds.model.SubmissionSearchResult;
import gov.nih.nci.cbiit.scimgmt.gds.services.LookupService;
import gov.nih.nci.cbiit.scimgmt.gds.services.ManageProjectService;
import gov.nih.nci.cbiit.scimgmt.gds.services.SearchProjectService;
import gov.nih.nci.cbiit.scimgmt.gds.util.DropDownOption;
import gov.nih.nci.cbiit.scimgmt.gds.util.ExcelExportProc;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper;
import gov.nih.nci.cbiit.scimgmt.gds.util.RepositoryStatusComparator;

/**
 * This class is responsible for searching Projects.
 * @author dinhys
 */
@SuppressWarnings("serial")
public class SearchSubmissionAction extends BaseAction implements ServletRequestAware {

	private static final Logger logger = LogManager.getLogger(SearchSubmissionAction.class);

	private HttpServletRequest request;
	
	@Autowired
	private SearchProjectService searchProjectService;
	
	@Autowired
	private ManageProjectService manageProjectService; // for delete project

	@Autowired
	private LookupService lookupService;
	
	private SubmissionSearchCriteria criteria = new SubmissionSearchCriteria();
	
	private List<DropDownOption> pdList = new ArrayList<DropDownOption>();	

	private List<DropDownOption> submissionFromList = new ArrayList<DropDownOption>();	
	
	private SubmissionSearchResult jsonResult = new SubmissionSearchResult();
	
    private int draw; // draw counter needed for server side processing data table
    
    private int start; // first record indicator, index of first record to retrieve for pagination
    
    private int length; // Number of records the table can display
    
    private List<RepositoryStatus> repoList = new ArrayList<RepositoryStatus>();
    
    private List<ProjectsVw> subprojectList = new ArrayList<ProjectsVw>();
      
	/**
	 * Navigate to Search Project.
	 * @return forward string
	 */
	public String execute() throws Exception {      

		logger.debug("execute");
		// Populate "Submission from" and "PD" lists
		setUpLists();
		
		return SUCCESS;
	}

	/** 
	 * Search Project.
	 * @return forward string
	 */
	public String search() throws Exception {      

		logger.debug("search");
		
		logger.debug(criteria.toString());
		
		populateCriteria();
		
        jsonResult = searchProjectService.search(criteria);
        if(jsonResult == null) {
        	jsonResult = new SubmissionSearchResult();
        	jsonResult.setData(new ArrayList<Submission>());
        }
		jsonResult.setDraw(getDraw());
		
		logger.debug("total records = " + jsonResult.getRecordsTotal());
		
		logger.debug("end - search");
		
		return SUCCESS;
		
	}
	
	private void populateCriteria() {

		if(criteria.getSubmissionFromId() == ApplicationConstants.SEARCH_MY_PROJECT_SUBMISSIONS) {
			criteria.setPdFirstName(loggedOnUser.getFirstName());
			criteria.setPdLastName(loggedOnUser.getLastName());
		}
		if(criteria.getSubmissionFromId() == ApplicationConstants.SEARCH_SUBMISSION_FROM_MYDOC) {
			List<Organization> docListFromDb = (List<Organization>) lookupService.getDocList(ApplicationConstants.DOC_LIST.toUpperCase());	
			criteria.setDoc(GdsSubmissionActionHelper.getLoggedonUsersDOC(docListFromDb,loggedOnUser.getNihsac()));	
		}
		if(StringUtils.isNotBlank(criteria.getPdFirstAndLastName())) {
			//Parse Last, First names
			criteria.setPdLastName(StringUtils.substringBefore(criteria.getPdFirstAndLastName(), ","));
			criteria.setPdFirstName(StringUtils.substringBefore(StringUtils.substringAfter(criteria.getPdFirstAndLastName(), ", "), " "));
		}

		// Provide pagination information
		criteria.setSortBy(getSortByProperty(request));
		criteria.setSortDir(getSortByDirection(request));
		criteria.setStart(start);
		criteria.setLength(length);
        
        logger.debug("Fetching " + length + " record(s) from index " + start);
        logger.debug("Sort by: " + criteria.getSortBy());
        logger.debug("Sort direction: " + criteria.getSortDir());
        
	}

	/** 
	 * Validate Search Project.
	 * @return forward string
	 */
	public void validateSearch() throws Exception {      

		logger.debug("validateSearch");
		
		if(criteria == null || criteria.isBlank()) {
			jsonResult.setError(getText("error.search.criteria.required"));
			addActionError(getText("error.search.criteria.required"));
		}
		
		if(StringUtils.isNotBlank(criteria.getGrantContractNum()) && criteria.getGrantContractNum().length() < ApplicationConstants.GRANT_CONTRACT_NUM_MIN_SIZE){
			jsonResult.setError(getText("grantContractNum.min.size.error"));
			addActionError(getText("grantContractNum.min.size.error")); 
		}

		logger.debug("end - validateSearch");
	}
	
	/** 
	 * Export to excel.
	 * @return null
	 */
	public String export() throws Exception {      

		logger.debug("export");
		
		HttpServletResponse response = ServletActionContext.getResponse();
		
		length = -1; // Fetch all data
		populateCriteria();

        jsonResult = searchProjectService.search(criteria);
        
		ExcelExportProc proc = new ExcelExportProc();
		List<ExportRow> rows = prepareExportData();
		proc.setData(rows);
		proc.doExportExcel(request, response);
		
		logger.debug("end - export");
		
		return null;
		
	}
	
	private List<ExportRow> prepareExportData() {
	       // Prepare rows of the export data
	     	List<ExportRow> rows = new ArrayList<>();
	     		
			// Prepare headers
	     	ExportRow exportRow = new ExportRow();
			List<String> header = new ArrayList<String>();
			header.add("Project ID");
			header.add("Subproject Count");
			header.add("Intramural/Grant/Contract");
			header.add("Project Title");
			header.add("Principle Investigator Name");
			header.add("Principle Investigator Email");
			header.add("GDS Plan");
			header.add("Data Sharing Exception");
			header.add("IC");
			header.add("BSI");
			header.add("Repository Count");
			exportRow.setRow(header);
			exportRow.setHeader(true);
			rows.add(exportRow);
			
			// Prepare data
			for(Submission submission : jsonResult.getData()) {
				exportRow = new ExportRow();
				List<String> row = new ArrayList<>();
				row.add(submission.getId().toString());
				row.add((submission.getSubprojectCount() == null? "" : submission.getSubprojectCount().toString()));
				row.add(submission.getGrantContractNum());
				row.add(submission.getProjectTitle());
				row.add((submission.getPiLastName() == null ? "" : submission.getPiLastName() + ", " + submission.getPiFirstName()));
				row.add(submission.getPiEmailAddress());
				row.add(submission.getGdsPlanPageStatus());
				row.add(submission.getDataSharingException());
				row.add(submission.getIcPageStatus());
				row.add(submission.getBsiPageStatus());
				row.add((submission.getRepoCount() == null ? "" : submission.getRepoCount().toString()));
				exportRow.setRow(row);
				rows.add(exportRow);
				
				// Add groups - Repo and Subprojects
				setProjectId(submission.getId().toString());
				
				if (submission.getRepoCount() > 0) {
					getRepoInfo();

					exportRow = new ExportRow();
					row = new ArrayList<>();
					row.add("");
					row.add("Repositories");
					row.add("Registration Status");
					row.add("Submission Status");
					row.add("Study Released Status");
					row.add("Accession Number");
					exportRow.setHeader(true);
					exportRow.setGroup(true);
					exportRow.setRow(row);
					rows.add(exportRow);

					for (RepositoryStatus repo : repoList) {
						exportRow = new ExportRow();
						row = new ArrayList<>();
						exportRow.setGroup(true);
						row.add("");
						row.add(repo.getPlanAnswerSelectionTByRepositoryId().getPlanQuestionsAnswer().getDisplayText());
						row.add(repo.getLookupTByRegistrationStatusId().getDisplayName());
						row.add(repo.getLookupTByDataSubmissionStatusId().getDisplayName());
						row.add(repo.getLookupTByStudyReleasedId().getDisplayName());
						row.add(repo.getAccessionNumber());
						exportRow.setRow(row);
						rows.add(exportRow);
					}
				}

				if (submission.getSubprojectCount() > 0) {
					getSubprojects();

					exportRow = new ExportRow();
					row = new ArrayList<>();
					row.add("");
					row.add("Sub-Project ID");
					row.add("Sub-project Title");
					row.add("Principle Investigator Name");
					row.add("Principle Investigator Email");
					row.add("GDS Plan");
					row.add("Data Sharing Exception");
					row.add("IC");
					row.add("BSI");
					exportRow.setHeader(true);
					exportRow.setGroup(true);
					exportRow.setRow(row);
					rows.add(exportRow);
					
					for (ProjectsVw subProject : subprojectList) {
						exportRow = new ExportRow();
						row = new ArrayList<>();
						exportRow.setGroup(true);
						row.add("");
						row.add(subProject.getId().toString());
						row.add(subProject.getProjectTitle());
						row.add((subProject.getPiLastName() == null ? "" : subProject.getPiLastName() + ", " + subProject.getPiFirstName()));
						row.add(subProject.getPiEmailAddress());
						row.add(subProject.getGdsPlanPageStatus());
						row.add("");
						row.add(subProject.getIcPageStatus());
						row.add(subProject.getBsiPageStatus());
						exportRow.setRow(row);
						rows.add(exportRow);
						
						if (!subProject.getRepositoryStatuses().isEmpty()) {
							exportRow = new ExportRow();
							row = new ArrayList<>();
							row.add("");
							row.add("Repositories");
							row.add("Registration Status");
							row.add("Submission Status");
							row.add("Study Released Status");
							row.add("Accession Number");
							exportRow.setHeader(true);
							exportRow.setGroup(true);
							exportRow.setRow(row);
							rows.add(exportRow);
							
							for (RepositoryStatus repo : subProject.getRepositoryStatuses()) {
								exportRow = new ExportRow();
								row = new ArrayList<>();
								exportRow.setGroup(true);
								row.add("");
								row.add(repo.getPlanAnswerSelectionTByRepositoryId().getPlanQuestionsAnswer()
										.getDisplayText());
								row.add(repo.getLookupTByRegistrationStatusId().getDisplayName());
								row.add(repo.getLookupTByDataSubmissionStatusId().getDisplayName());
								row.add(repo.getLookupTByStudyReleasedId().getDisplayName());
								row.add(repo.getAccessionNumber());
								exportRow.setRow(row);
								rows.add(exportRow);
							}
						}
					}
				}

			}
			return rows;
	}
	
	/**
	 * Get all project ids.
	 * @return List
	 */
	public List<Long> getAllProjectIds(){
		return searchProjectService.getAllProjectIds();
	}

	
	/**
	 * Delete Project
	 * @throws Exception 
	 */
	public String deleteProject() throws Exception {
		logger.debug("deleteProject()");

		manageProjectService.delete(Long.valueOf(getProjectId()));
		addActionMessage(getText("project.delete.success"));
		
		setUpLists();
		
		return SUCCESS;
	}
	
	/**
	 * Retrieve the project based on the projectId in order to get the repository information
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getRepoInfo() {
	
		String projectId  = getProjectId();
		if(StringUtils.isNotBlank(projectId)) {
			Project project = manageProjectService.findById(Long.valueOf(projectId));
			for(PlanAnswerSelection selection: project.getPlanAnswerSelection()) {
				for(RepositoryStatus repositoryStatus : selection.getRepositoryStatuses()){
					project.getRepositoryStatuses().add(repositoryStatus);
				}		
			}
			Collections.sort(project.getRepositoryStatuses(),new RepositoryStatusComparator());
			setRepoList(project.getRepositoryStatuses());
		}
		
		return SUCCESS;
	}
	
	/**
	 * Retrieve the subprojects based on the parent projectId and also get the repository information for the subproject
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getSubprojects() {
	
		String parentProjectId  = getProjectId();
		if(StringUtils.isNotBlank(parentProjectId)) {
			subprojectList = searchProjectService.getSubprojects(Long.valueOf(parentProjectId));
			for(ProjectsVw subproject: subprojectList) {
				Collections.sort(subproject.getRepositoryStatuses(),new RepositoryStatusComparator());
				setRepoList(subproject.getRepositoryStatuses());
			}
		}
		
		return SUCCESS;
	}
	
	/** 
	 * Validate Delete Project
	 * @return forward string
	 */
	public void validateDeleteProject() throws Exception {      

		logger.debug("validateDeleteProject");
		
		if(StringUtils.isBlank(getProjectId())) {
			addActionError("Error deleting submission");
		}

		logger.debug("end - validateDeleteProject");
	}
	
	/**
	 * This method sets up the "Submission from" and "PD" lists for Search
	 */
	private void setUpLists(){
		
		logger.debug("Setting up page lists.");
		List<GdsPd> pdListDb = lookupService.getPdList(ApplicationConstants.PD_LIST);
		if(pdList.isEmpty()){
			DropDownOption option = new DropDownOption();
			option.setOptionKey("");
			option.setOptionValue("Select Program Director");
			pdList.add(option);
			
			for(GdsPd pd: pdListDb) {
				option = new DropDownOption();
				option.setOptionKey(pd.getNpnId().toString());
				option.setOptionValue(pd.getPdFullNameDescrip());
				pdList.add(option);
			}
		}
		
		if(submissionFromList.isEmpty()){			
			submissionFromList = GdsSubmissionActionHelper.getLookupDropDownList(ApplicationConstants.SEARCH_SUBMISSION_FROM.toUpperCase());	
		}		

	}
	
    public String getSortByProperty(HttpServletRequest request) {
        String sSortProperty = null;
        String sSortIndex = request.getParameter("order[0][column]");
        if (sSortIndex != null && !sSortIndex.isEmpty()) {
            sSortProperty = request.getParameter("columns[" + sSortIndex + "][data]");
        }
        return sSortProperty;
    }

    public String getSortByDirection(HttpServletRequest request) {
        return request.getParameter("order[0][dir]");
    }
	
	public SubmissionSearchCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(SubmissionSearchCriteria criteria) {
		this.criteria = criteria;
	}

	public List<DropDownOption> getSubmissionFromList() {
		return submissionFromList;
	}

	public void setSubmissionFromList(List<DropDownOption> submissionFromList) {
		this.submissionFromList = submissionFromList;
	}

	public List<DropDownOption> getPdList() {
		return pdList;
	}

	public void setPdList(List<DropDownOption> pdList) {
		this.pdList = pdList;
	}

	public SubmissionSearchResult getJsonResult() {
		return jsonResult;
	}

	public void setJsonResult(SubmissionSearchResult jsonResult) {
		this.jsonResult = jsonResult;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	@Override
	public void setServletRequest(final HttpServletRequest request) {
		this.request = request;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public List<RepositoryStatus> getRepoList() {
		return repoList;
	}

	public void setRepoList(List<RepositoryStatus> repoList) {
		this.repoList = repoList;
	}

	public List<ProjectsVw> getSubprojectList() {
		return subprojectList;
	}

	public void setSubprojectList(List<ProjectsVw> subprojectList) {
		this.subprojectList = subprojectList;
	}
}

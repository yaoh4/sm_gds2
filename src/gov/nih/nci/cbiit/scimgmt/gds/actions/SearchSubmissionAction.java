package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Organization;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;
import gov.nih.nci.cbiit.scimgmt.gds.model.Submission;
import gov.nih.nci.cbiit.scimgmt.gds.model.SubmissionSearchCriteria;
import gov.nih.nci.cbiit.scimgmt.gds.model.SubmissionSearchResult;
import gov.nih.nci.cbiit.scimgmt.gds.services.LookupService;
import gov.nih.nci.cbiit.scimgmt.gds.services.ManageProjectService;
import gov.nih.nci.cbiit.scimgmt.gds.services.SearchProjectService;
import gov.nih.nci.cbiit.scimgmt.gds.util.DropDownOption;
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
    
    private List<Project> subprojectList = new ArrayList<Project>();
    
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
		
		if(criteria.getSubmissionFromId().equals(ApplicationConstants.SEARCH_MY_PROJECT_SUBMISSIONS)) {
			criteria.setPdFirstName(loggedOnUser.getFirstName());
			criteria.setPdLastName(loggedOnUser.getLastName());
		}
		if(criteria.getSubmissionFromId().equals(ApplicationConstants.SEARCH_SUBMISSION_FROM_MYDOC)) {
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

		logger.debug("end - validateSearch");
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
			for(Project subproject: subprojectList) {
				for(PlanAnswerSelection selection: subproject.getPlanAnswerSelection()) {
					for(RepositoryStatus repositoryStatus : selection.getRepositoryStatuses()){
						subproject.getRepositoryStatuses().add(repositoryStatus);
					}		
				}
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
		if(pdList.isEmpty()){
			DropDownOption option = new DropDownOption();
			option.setOptionKey("");
			option.setOptionValue("Select Program Director");
			pdList.add(option);
			option = new DropDownOption();
			
			option.setOptionKey("Abrams, Natalie Davidovna (0N)");
			option.setOptionValue("Abrams, Natalie Davidovna (0N)");
			pdList.add(option);
			option = new DropDownOption();
			option.setOptionKey("Adjei, Brenda A (F8)");
			option.setOptionValue("Adjei, Brenda A (F8)");
			pdList.add(option);
			option = new DropDownOption();
			option.setOptionKey("Agarwal, Rajeev K. (2A)");
			option.setOptionValue("Agarwal, Rajeev K. (2A)");
			pdList.add(option);
			option = new DropDownOption();
			option.setOptionKey("Agelli, Maria (9U)");
			option.setOptionValue("Agelli, Maria (9U)");
			pdList.add(option);
			option = new DropDownOption();
			option.setOptionKey("Agrawal, Lokesh (0S)");
			option.setOptionValue("Agrawal, Lokesh (0S)");
			pdList.add(option);
			option = new DropDownOption();
			option.setOptionKey("Aguila, H. Nelson (6U)");
			option.setOptionValue("Aguila, H. Nelson (6U)");
			pdList.add(option);
			option = new DropDownOption();
			option.setOptionKey("Agurs-Collins, Tanya (H1)");
			option.setOptionValue("Agurs-Collins, Tanya (H1)");
			pdList.add(option);
			option = new DropDownOption();
			option.setOptionKey("Ahmed, Mansoor M (X9)");
			option.setOptionValue("Ahmed, Mansoor M (X9)");
			pdList.add(option);
			option = new DropDownOption();
			option.setOptionKey("Alexander, Mark (O9)");
			option.setOptionValue("Alexander, Mark (O9)");
			pdList.add(option);
			option = new DropDownOption();
			option.setOptionKey("Alley, Michael C (X3)");
			option.setOptionValue("Alley, Michael C (X3)");
			pdList.add(option);
			option = new DropDownOption();
			option.setOptionKey("Arnold, Julia T (U7)");
			option.setOptionValue("Arnold, Julia T (U7)");
			pdList.add(option);
			option = new DropDownOption();
			option.setOptionKey("Arya, Suresh (2Q)");
			option.setOptionValue("Arya, Suresh (2Q)");
			pdList.add(option);
			option = new DropDownOption();
			option.setOptionKey("Atienza, Audie A (A2)");
			option.setOptionValue("Atienza, Audie A (A2)");
			pdList.add(option);
			option = new DropDownOption();
			option.setOptionKey("Augustson, Erik (T3)");
			option.setOptionValue("Augustson, Erik (T3)");
			pdList.add(option);
			option = new DropDownOption();
			option.setOptionKey("Ault, Grace S (7K)");
			option.setOptionValue("Ault, Grace S (7K)");
			pdList.add(option);
			option = new DropDownOption();
			option.setOptionKey("Belin, Precilla L (8I)");
			option.setOptionValue("Belin, Precilla L (8I)");
			pdList.add(option);
			option = new DropDownOption();
			option.setOptionKey("Bernhard, Eric J (J8)");
			option.setOptionValue("Bernhard, Eric J (J8)");
			pdList.add(option);
			option = new DropDownOption();
			option.setOptionKey("Berrigan, David (6B)");
			option.setOptionValue("Berrigan, David (6B)");
			pdList.add(option);
			option = new DropDownOption();
			option.setOptionKey("Bhatia, Kishor (3N)");
			option.setOptionValue("Bhatia, Kishor (3N)");
			pdList.add(option);
			option = new DropDownOption();
			option.setOptionKey("Blake, Kelly D (K5)");
			option.setOptionValue("Blake, Kelly D (K5)");
			pdList.add(option);
			option = new DropDownOption();
			option.setOptionKey("Bloch, Michele (6M)");
			option.setOptionValue("Bloch, Michele (6M)");
			pdList.add(option);
			option = new DropDownOption();
			option.setOptionKey("Boja, Emily L (T5)");
			option.setOptionValue("Boja, Emily L (T5)");
			pdList.add(option);
			option = new DropDownOption();
			option.setOptionKey("Breen, Nancy (60)");
			option.setOptionValue("Breen, Nancy (60)");
			pdList.add(option);
			option = new DropDownOption();
			option.setOptionKey("Breslau, Erica S. (9W)");
			option.setOptionValue("Breslau, Erica S. (9W)");
			pdList.add(option);
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

	public List<Project> getSubprojectList() {
		return subprojectList;
	}

	public void setSubprojectList(List<Project> subprojectList) {
		this.subprojectList = subprojectList;
	}
}

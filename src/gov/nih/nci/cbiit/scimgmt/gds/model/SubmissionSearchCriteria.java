package gov.nih.nci.cbiit.scimgmt.gds.model;

import org.apache.commons.lang3.StringUtils;


public class SubmissionSearchCriteria {

	private Long submissionFromId;
	private Long pdNpnId;
	private String pdLastName;
	private String pdFirstName;
	private String pdFirstAndLastName;
	private String grantContractNum;
	private String projectTitle;
	private String accessionNumber;
	private String piFirstOrLastName;
	private String doc;
	// Pagination information
	private int start;
    private int length;
    private String sortBy;
    private String sortDir;
    private String parentSearch;
    private String selectedTypeOfProject;
    private Long submissionReasonId;
    private boolean excludeCompleted = false;
    
	public SubmissionSearchCriteria(){}

	public SubmissionSearchCriteria(Long submissionFromId, Long pdNpnId, 
			String grantContractNum, String projectTitle, String accessionNumber, 
			String piFirstOrLastName, String parentSearch, boolean excludeCompleted) {
		this.submissionFromId = submissionFromId;
		this.pdNpnId = pdNpnId;
		this.grantContractNum = grantContractNum;
		this.projectTitle = projectTitle;
		this.accessionNumber = accessionNumber;
		this.piFirstOrLastName = piFirstOrLastName;
		this.parentSearch = parentSearch;
		this.excludeCompleted = excludeCompleted;
	}

	@Override
	public String toString() {
		return "SubmissionSearchCriteria [submissionFromId=" + submissionFromId + ", pdNpnId=" + pdNpnId
				+ ", pdLastName=" + pdLastName + ", pdFirstName=" + pdFirstName + ", pdFirstAndLastName="
				+ pdFirstAndLastName + ", grantContractNum=" + grantContractNum + ", projectTitle=" + projectTitle
				+ ", accessionNumber=" + accessionNumber + ", piFirstOrLastName=" + piFirstOrLastName + ", doc=" + doc
				+ ", start=" + start + ", length=" + length + ", sortBy=" + sortBy + ", sortDir=" + sortDir 
				+ ", parentSearch=" + parentSearch + ", excludeCompleted=" + excludeCompleted + "]";
	}
	
	/**
	 * Checks if no search criteria is provided
	 * 
	 * @return true, if is blank
	 */
	public boolean isBlank() {
		return (submissionFromId == null
				/*|| submissionFromId.longValue() == ApplicationConstants.SEARCH_SUBMISSION_FROM_ALL.longValue()*/)
				&& StringUtils.isBlank(pdFirstAndLastName) && StringUtils.isBlank(grantContractNum) && StringUtils.isBlank(projectTitle)
				&& StringUtils.isBlank(accessionNumber) && StringUtils.isBlank(piFirstOrLastName) && submissionReasonId == null;
	}
	
	public Long getSubmissionFromId() {
		return submissionFromId;
	}

	public void setSubmissionFromId(Long submissionFromId) {
		this.submissionFromId = submissionFromId;
	}

	public Long getPdNpnId() {
		return pdNpnId;
	}

	public void setPdNpnId(Long pdNpnId) {
		this.pdNpnId = pdNpnId;
	}

	public String getGrantContractNum() {
		return grantContractNum;
	}

	public void setGrantContractNum(String grantContractNum) {
		this.grantContractNum = grantContractNum;
	}

	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	public String getAccessionNumber() {
		return accessionNumber;
	}

	public void setAccessionNumber(String accessionNumber) {
		this.accessionNumber = accessionNumber;
	}

	public String getPiFirstOrLastName() {
		return piFirstOrLastName;
	}

	public void setPiFirstOrLastName(String piFirstOrLastName) {
		this.piFirstOrLastName = piFirstOrLastName;
	}

	public String getDoc() {
		return doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	public String getPdFirstName() {
		return pdFirstName;
	}

	public void setPdFirstName(String pdFirstName) {
		this.pdFirstName = pdFirstName;
	}

	public String getPdLastName() {
		return pdLastName;
	}

	public void setPdLastName(String pdLastName) {
		this.pdLastName = pdLastName;
	}

	public String getPdFirstAndLastName() {
		return pdFirstAndLastName;
	}

	public void setPdFirstAndLastName(String pdFirstAndLastName) {
		this.pdFirstAndLastName = pdFirstAndLastName;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSortDir() {
		return sortDir;
	}

	public void setSortDir(String sortDir) {
		this.sortDir = sortDir;
	}

	public String getParentSearch() {
		return parentSearch;
	}

	public void setParentSearch(String parentSearch) {
		this.parentSearch = parentSearch;
	}

	public String getSelectedTypeOfProject() {
		return selectedTypeOfProject;
	}

	public void setSelectedTypeOfProject(String selectedTypeOfProject) {
		this.selectedTypeOfProject = selectedTypeOfProject;
	}

	public Long getSubmissionReasonId() {
		return submissionReasonId;
	}

	public void setSubmissionReasonId(Long submissionReasonId) {
		this.submissionReasonId = submissionReasonId;
	}

	public boolean isExcludeCompleted() {
		return excludeCompleted;
	}

	public void setExcludeCompleted(boolean excludeCompleted) {
		this.excludeCompleted = excludeCompleted;
	}

}

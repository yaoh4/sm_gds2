package gov.nih.nci.cbiit.scimgmt.gds.model;

import org.apache.commons.lang3.StringUtils;


public class SubmissionSearchCriteria {

	private Long submissionFromId;
	private Long pdNpnId;
	private String pdLastName;
	private String pdFirstName;
	private String pdFirstAndLastName;
	private String applicationNum;
	private String projectTitle;
	private String accessionNumber;
	private String piFirstOrLastName;
	private String doc;
	// Pagination information
	private int start;
    private int length;
    private String sortBy;
    private String sortDir;
    
	public SubmissionSearchCriteria(){}

	public SubmissionSearchCriteria(Long submissionFromId, Long pdNpnId, 
			String applicationNum, String projectTitle, String accessionNumber, String piFirstOrLastName) {
		this.submissionFromId = submissionFromId;
		this.pdNpnId = pdNpnId;
		this.applicationNum = applicationNum;
		this.projectTitle = projectTitle;
		this.accessionNumber = accessionNumber;
		this.piFirstOrLastName = piFirstOrLastName;
	}

	/**
	 * Checks if no search criteria is provided
	 * 
	 * @return true, if is blank
	 */
	public boolean isBlank() {
		return (submissionFromId == null
				/*|| submissionFromId.longValue() == ApplicationConstants.SEARCH_SUBMISSION_FROM_ALL.longValue()*/)
				&& StringUtils.isBlank(pdFirstAndLastName) && StringUtils.isBlank(applicationNum) && StringUtils.isBlank(projectTitle)
				&& StringUtils.isBlank(accessionNumber) && StringUtils.isBlank(piFirstOrLastName);
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

	public String getApplicationNum() {
		return applicationNum;
	}

	public void setApplicationNum(String applicationNum) {
		this.applicationNum = applicationNum;
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

	@Override
	public String toString() {
		return "SubmissionSearchCriteria [submissionFromId=" + submissionFromId + ", pdFirstAndLastName="
				+ pdFirstAndLastName + ", applicationNum=" + applicationNum + ", projectTitle=" + projectTitle
				+ ", accessionNumber=" + accessionNumber + ", piFirstOrLastName=" + piFirstOrLastName + "]";
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

}

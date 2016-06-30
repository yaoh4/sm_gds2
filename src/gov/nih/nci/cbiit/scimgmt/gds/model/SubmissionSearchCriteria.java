package gov.nih.nci.cbiit.scimgmt.gds.model;

import org.apache.commons.lang3.StringUtils;


public class SubmissionSearchCriteria {

	private Long submissionFromId;
	private Long pdNpnId;
	private String applicationNum;
	private String projectTitle;
	private String accessionNumber;
	private String piFirstOrLastName;

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
		return submissionFromId == null && pdNpnId == null && StringUtils.isBlank(applicationNum)
				&& StringUtils.isBlank(projectTitle) && StringUtils.isBlank(accessionNumber)
				&& StringUtils.isBlank(piFirstOrLastName);
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

}

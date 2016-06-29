package gov.nih.nci.cbiit.scimgmt.gds.model;


public class SubmissionSearchCriteria {

	private Long submissionFromId;
	private Long pdNpnId;
	private Long intraExtraContractId;
	private String applicationNum;
	private String projectTitle;
	private String accessionNumber;
	private String piFirstOrLastName;

	public SubmissionSearchCriteria(){}

	public SubmissionSearchCriteria(Long submissionFromId, Long pdNpnId, Long intraExtraContractId,
			String applicationNum, String projectTitle, String accessionNumber, String piFirstOrLastName) {
		this.submissionFromId = submissionFromId;
		this.pdNpnId = pdNpnId;
		this.intraExtraContractId = intraExtraContractId;
		this.applicationNum = applicationNum;
		this.projectTitle = projectTitle;
		this.accessionNumber = accessionNumber;
		this.piFirstOrLastName = piFirstOrLastName;
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

	public Long getIntraExtraContractId() {
		return intraExtraContractId;
	}

	public void setIntraExtraContractId(Long intraExtraContractId) {
		this.intraExtraContractId = intraExtraContractId;
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

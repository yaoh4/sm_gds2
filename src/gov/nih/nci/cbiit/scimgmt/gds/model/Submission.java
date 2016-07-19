package gov.nih.nci.cbiit.scimgmt.gds.model;

import java.util.Date;


/**
 * Submission object which is used for data table result display
 */

public class Submission implements java.io.Serializable {

	private Long id;
	private String projectIdentifierNum;
	private String projectTitle;
	private String docAbbreviation;
	private String programBranch;
	private String grantContractNum;
	private String piInstitution;
	private String piEmailAddress;
	private Date projectStartDate;
	private Date projectEndDate;
	private Date sciRevApprovalRcvdDate;
	private String parentAccessionNum;
	private String comments;
	private String bsiComments;
	private String planComments;
	private String bsiReviewedFlag;
	private Long versionNum;
	private String createdBy;
	private String lastChangedBy;
	private String subprojectFlag;
	private Long parentProjectId;
	private String latestVersionFlag;
	private Long projectGroupId;
	private Long subprojectGroupId;
	private Long submissionReasonId;
	private String certificationCompleteFlag;
	private String piFirstName;
	private String piLastName;
	private String pocFirstName;
	private String pocLastName;
	private String pocEmailAddress;
	private String pdFirstName;
	private String pdLastName;
	private Long applId;
	private Date anticipatedSubmissionDate;
	private String gdsPlanPageStatus;
	private String dataSharingException;
	private String icPageStatus;
	private String bsiPageStatus;
	private Long repoCount;
	private Long subprojectCount;

	public Submission() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectIdentifierNum() {
		return this.projectIdentifierNum;
	}

	public void setProjectIdentifierNum(String projectIdentifierNum) {
		this.projectIdentifierNum = projectIdentifierNum;
	}

	public String getProjectTitle() {
		return this.projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	public String getDocAbbreviation() {
		return this.docAbbreviation;
	}

	public void setDocAbbreviation(String docAbbreviation) {
		this.docAbbreviation = docAbbreviation;
	}

	public String getProgramBranch() {
		return this.programBranch;
	}

	public void setProgramBranch(String programBranch) {
		this.programBranch = programBranch;
	}

	public String getGrantContractNum() {
		return this.grantContractNum;
	}

	public void setGrantContractNum(String grantContractNum) {
		this.grantContractNum = grantContractNum;
	}

	public String getPiInstitution() {
		return this.piInstitution;
	}

	public void setPiInstitution(String piInstitution) {
		this.piInstitution = piInstitution;
	}
	
	public String getPiEmailAddress() {
		return this.piEmailAddress;
	}

	public void setPiEmailAddress(String piEmailAddress) {
		this.piEmailAddress = piEmailAddress;
	}

	public Date getProjectStartDate() {
		return this.projectStartDate;
	}

	public void setProjectStartDate(Date projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	public Date getProjectEndDate() {
		return this.projectEndDate;
	}

	public void setProjectEndDate(Date projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	public Date getSciRevApprovalRcvdDate() {
		return this.sciRevApprovalRcvdDate;
	}

	public void setSciRevApprovalRcvdDate(Date sciRevApprovalRcvdDate) {
		this.sciRevApprovalRcvdDate = sciRevApprovalRcvdDate;
	}

	public String getParentAccessionNum() {
		return this.parentAccessionNum;
	}

	public void setParentAccessionNum(String parentAccessionNum) {
		this.parentAccessionNum = parentAccessionNum;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getPlanComments() {
		return this.planComments;
	}

	public void setPlanComments(String planComments) {
		this.planComments = planComments;
	}

	public String getBsiComments() {
		return this.bsiComments;
	}

	public void setBsiComments(String bsiComments) {
		this.bsiComments = bsiComments;
	}

	public String getBsiReviewedFlag() {
		return this.bsiReviewedFlag;
	}

	public void setBsiReviewedFlag(String bsiReviewedFlag) {
		this.bsiReviewedFlag = bsiReviewedFlag;
	}

	public Long getVersionNum() {
		return this.versionNum;
	}

	public void setVersionNum(Long versionNum) {
		this.versionNum = versionNum;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastChangedBy() {
		return this.lastChangedBy;
	}

	public void setLastChangedBy(String lastChangedBy) {
		this.lastChangedBy = lastChangedBy;
	}

	public String getSubprojectFlag() {
		return this.subprojectFlag;
	}

	public void setSubprojectFlag(String subprojectFlag) {
		this.subprojectFlag = subprojectFlag;
	}

	public Long getParentProjectId() {
		return this.parentProjectId;
	}

	public void setParentProjectId(Long parentProjectId) {
		this.parentProjectId = parentProjectId;
	}

	public String getLatestVersionFlag() {
		return this.latestVersionFlag;
	}

	public void setLatestVersionFlag(String latestVersionFlag) {
		this.latestVersionFlag = latestVersionFlag;
	}

	public Long getProjectGroupId() {
		return this.projectGroupId;
	}

	public void setProjectGroupId(Long projectGroupId) {
		this.projectGroupId = projectGroupId;
	}

	public Long getSubprojectGroupId() {
		return this.subprojectGroupId;
	}

	public void setSubprojectGroupId(Long subprojectGroupId) {
		this.subprojectGroupId = subprojectGroupId;
	}

	public Long getSubmissionReasonId() {
		return this.submissionReasonId;
	}

	public void setSubmissionReasonId(Long submissionReasonId) {
		this.submissionReasonId = submissionReasonId;
	}

	public String getCertificationCompleteFlag() {
		return this.certificationCompleteFlag;
	}

	public void setCertificationCompleteFlag(String certificationCompleteFlag) {
		this.certificationCompleteFlag = certificationCompleteFlag;
	}

	public String getPiFirstName() {
		return this.piFirstName;
	}

	public void setPiFirstName(String piFirstName) {
		this.piFirstName = piFirstName;
	}

	public String getPiLastName() {
		return this.piLastName;
	}

	public void setPiLastName(String piLastName) {
		this.piLastName = piLastName;
	}

	public String getPocFirstName() {
		return this.pocFirstName;
	}

	public void setPocFirstName(String pocFirstName) {
		this.pocFirstName = pocFirstName;
	}

	public String getPocLastName() {
		return this.pocLastName;
	}

	public void setPocLastName(String pocLastName) {
		this.pocLastName = pocLastName;
	}

	public String getPocEmailAddress() {
		return pocEmailAddress;
	}

	public void setPocEmailAddress(String pocEmailAddress) {
		this.pocEmailAddress = pocEmailAddress;
	}

	public String getPdFirstName() {
		return this.pdFirstName;
	}

	public void setPdFirstName(String pdFirstName) {
		this.pdFirstName = pdFirstName;
	}

	public String getPdLastName() {
		return this.pdLastName;
	}

	public void setPdLastName(String pdLastName) {
		this.pdLastName = pdLastName;
	}
	
	public Long getApplId() {
		return applId;
	}

	public void setApplId(Long applId) {
		this.applId = applId;
	}

	public Date getAnticipatedSubmissionDate() {
		return anticipatedSubmissionDate;
	}

	public void setAnticipatedSubmissionDate(Date anticipatedSubmissionDate) {
		this.anticipatedSubmissionDate = anticipatedSubmissionDate;
	}

	public String getGdsPlanPageStatus() {
		return gdsPlanPageStatus;
	}

	public void setGdsPlanPageStatus(String gdsPlanPageStatus) {
		this.gdsPlanPageStatus = gdsPlanPageStatus;
	}

	public String getDataSharingException() {
		return dataSharingException;
	}

	public void setDataSharingException(String dataSharingException) {
		this.dataSharingException = dataSharingException;
	}

	public String getBsiPageStatus() {
		return bsiPageStatus;
	}

	public void setBsiPageStatus(String bsiPageStatus) {
		this.bsiPageStatus = bsiPageStatus;
	}

	public String getIcPageStatus() {
		return icPageStatus;
	}

	public void setIcPageStatus(String icPageStatus) {
		this.icPageStatus = icPageStatus;
	}

	public Long getRepoCount() {
		return repoCount;
	}

	public void setRepoCount(Long repoCount) {
		this.repoCount = repoCount;
	}

	public Long getSubprojectCount() {
		return subprojectCount;
	}

	public void setSubprojectCount(Long subprojectCount) {
		this.subprojectCount = subprojectCount;
	}
}

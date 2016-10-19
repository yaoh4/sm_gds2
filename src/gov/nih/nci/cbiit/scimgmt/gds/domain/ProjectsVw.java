package gov.nih.nci.cbiit.scimgmt.gds.domain;
// Generated Jul 19, 2016 12:32:23 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Formula;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;

/**
 * ProjectsVw generated by hbm2java
 */
@Entity
@Table(name = "PROJECTS_VW")
public class ProjectsVw implements java.io.Serializable {

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
	private Long bsiReviewedId;
	private Long versionNum;
	private String subprojectFlag;
	private ProjectsVw parentProject;
	private String latestVersionFlag;
	private Long projectGroupId;
	private Long subprojectGroupId;
	private Long submissionReasonId;
	private String icCompleteFlag;
	private String piFirstName;
	private String piLastName;
	private String pocFirstName;
	private String pocLastName;
	private Long pdNpnId;
	private String pdFirstName;
	private String pdLastName;
	private String pocEmailAddress;
	private String planComments;
	private Long applId;
	private String bsiComments;
	private Date anticipatedSubmissionDate;
	private String projectSubmissionTitle;
	private String dataLinkFlag;
	private String gdsPlanPageStatusCode;
	private String icPageStatusCode;
	private String bsiPageStatusCode;
	private String dataSharingExcepStatusCode;
	private String repositoryPageStatusCode;
	private String subprojectEligibleFlag;
	private String projectStatusCode;
	
	private Long subprojectCount;
	private Long repoCount;
	private boolean expandRepository = false;
	
	private List<RepositoryStatus> repositoryStatuses = new ArrayList<RepositoryStatus>(0);
	private List<ProjectsVw> subprojects = new ArrayList<ProjectsVw>();
	
	public ProjectsVw() {
	}

	public ProjectsVw(Long id, String projectIdentifierNum, String projectTitle, String docAbbreviation,
			String programBranch, String grantContractNum, String piInstitution, String piEmailAddress,
			Date projectStartDate, Date projectEndDate, Date sciRevApprovalRcvdDate, String parentAccessionNum,
			String comments, Long bsiReviewedId, Long versionNum, String subprojectFlag,
			ProjectsVw parentProject, String latestVersionFlag, Long projectGroupId, Long subprojectGroupId,
			Long submissionReasonId, String icCompleteFlag, String piFirstName, String piLastName,
			String pocFirstName, String pocLastName, Long pdNpnId, String pdFirstName, String pdLastName,
			String pocEmailAddress, String planComments, Long applId, String bsiComments,
			Date anticipatedSubmissionDate, String projectSubmissionTitle, String dataLinkFlag,
			String gdsPlanPageStatusCode, String icPageStatusCode, String bsiPageStatusCode,
			String dataSharingExcepStatusCode, String repositoryPageStatusCode, String subprojectEligibleFlag) {
		this.id = id;
		this.projectIdentifierNum = projectIdentifierNum;
		this.projectTitle = projectTitle;
		this.docAbbreviation = docAbbreviation;
		this.programBranch = programBranch;
		this.grantContractNum = grantContractNum;
		this.piInstitution = piInstitution;
		this.piEmailAddress = piEmailAddress;
		this.projectStartDate = projectStartDate;
		this.projectEndDate = projectEndDate;
		this.sciRevApprovalRcvdDate = sciRevApprovalRcvdDate;
		this.parentAccessionNum = parentAccessionNum;
		this.comments = comments;
		this.bsiReviewedId = bsiReviewedId;
		this.versionNum = versionNum;
		this.subprojectFlag = subprojectFlag;
		this.parentProject = parentProject;
		this.latestVersionFlag = latestVersionFlag;
		this.projectGroupId = projectGroupId;
		this.subprojectGroupId = subprojectGroupId;
		this.submissionReasonId = submissionReasonId;
		this.icCompleteFlag = icCompleteFlag;
		this.piFirstName = piFirstName;
		this.piLastName = piLastName;
		this.pocFirstName = pocFirstName;
		this.pocLastName = pocLastName;
		this.pdNpnId = pdNpnId;
		this.pdFirstName = pdFirstName;
		this.pdLastName = pdLastName;
		this.pocEmailAddress = pocEmailAddress;
		this.planComments = planComments;
		this.applId = applId;
		this.bsiComments = bsiComments;
		this.anticipatedSubmissionDate = anticipatedSubmissionDate;
		this.projectSubmissionTitle = projectSubmissionTitle;
		this.dataLinkFlag = dataLinkFlag;
		this.gdsPlanPageStatusCode = gdsPlanPageStatusCode;
		this.icPageStatusCode = icPageStatusCode;
		this.bsiPageStatusCode = bsiPageStatusCode;
		this.dataSharingExcepStatusCode = dataSharingExcepStatusCode;
		this.repositoryPageStatusCode = repositoryPageStatusCode;
		this.subprojectEligibleFlag = subprojectEligibleFlag;
	}

	@Id
	@Column(name = "ID", nullable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "PROJECT_IDENTIFIER_NUM", length = 120)
	public String getProjectIdentifierNum() {
		return this.projectIdentifierNum;
	}

	public void setProjectIdentifierNum(String projectIdentifierNum) {
		this.projectIdentifierNum = projectIdentifierNum;
	}

	@Column(name = "PROJECT_TITLE", length = 800)
	public String getProjectTitle() {
		return this.projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	@Column(name = "DOC_ABBREVIATION", length = 120)
	public String getDocAbbreviation() {
		return this.docAbbreviation;
	}

	public void setDocAbbreviation(String docAbbreviation) {
		this.docAbbreviation = docAbbreviation;
	}

	@Column(name = "PROGRAM_BRANCH", length = 120)
	public String getProgramBranch() {
		return this.programBranch;
	}

	public void setProgramBranch(String programBranch) {
		this.programBranch = programBranch;
	}

	@Column(name = "GRANT_CONTRACT_NUM", length = 120)
	public String getGrantContractNum() {
		return this.grantContractNum;
	}

	public void setGrantContractNum(String grantContractNum) {
		this.grantContractNum = grantContractNum;
	}

	@Column(name = "PI_INSTITUTION", length = 720)
	public String getPiInstitution() {
		return this.piInstitution;
	}

	public void setPiInstitution(String piInstitution) {
		this.piInstitution = piInstitution;
	}

	@Column(name = "PI_EMAIL_ADDRESS", length = 320)
	public String getPiEmailAddress() {
		return this.piEmailAddress;
	}

	public void setPiEmailAddress(String piEmailAddress) {
		this.piEmailAddress = piEmailAddress;
	}

	@Column(name = "PROJECT_START_DATE", length = 7)
	public Date getProjectStartDate() {
		return this.projectStartDate;
	}

	public void setProjectStartDate(Date projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	@Column(name = "PROJECT_END_DATE", length = 7)
	public Date getProjectEndDate() {
		return this.projectEndDate;
	}

	public void setProjectEndDate(Date projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	@Column(name = "SCI_REV_APPROVAL_RCVD_DATE", length = 7)
	public Date getSciRevApprovalRcvdDate() {
		return this.sciRevApprovalRcvdDate;
	}

	public void setSciRevApprovalRcvdDate(Date sciRevApprovalRcvdDate) {
		this.sciRevApprovalRcvdDate = sciRevApprovalRcvdDate;
	}

	@Column(name = "PARENT_ACCESSION_NUM", length = 120)
	public String getParentAccessionNum() {
		return this.parentAccessionNum;
	}

	public void setParentAccessionNum(String parentAccessionNum) {
		this.parentAccessionNum = parentAccessionNum;
	}

	@Column(name = "COMMENTS", length = 4000)
	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Column(name = "BSI_REVIEWED_ID", precision = 10, scale = 0)
	public Long getBsiReviewedId() {
		return this.bsiReviewedId;
	}

	public void setBsiReviewedId(Long bsiReviewedId) {
		this.bsiReviewedId = bsiReviewedId;
	}

	@Column(name = "VERSION_NUM", nullable = false, precision = 10, scale = 0)
	public Long getVersionNum() {
		return this.versionNum;
	}

	public void setVersionNum(Long versionNum) {
		this.versionNum = versionNum;
	}

	@Column(name = "SUBPROJECT_FLAG", nullable = false, length = 4)
	public String getSubprojectFlag() {
		return this.subprojectFlag;
	}

	public void setSubprojectFlag(String subprojectFlag) {
		this.subprojectFlag = subprojectFlag;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_PROJECT_ID", nullable = false)
	public ProjectsVw getParentProject() {
		return this.parentProject;
	}    
	
	public void setParentProject(ProjectsVw parentProject) {
		this.parentProject = parentProject;
	}

	@Column(name = "LATEST_VERSION_FLAG", length = 4)
	public String getLatestVersionFlag() {
		return this.latestVersionFlag;
	}

	public void setLatestVersionFlag(String latestVersionFlag) {
		this.latestVersionFlag = latestVersionFlag;
	}

	@Column(name = "PROJECT_GROUP_ID", nullable = false, precision = 10, scale = 0)
	public Long getProjectGroupId() {
		return this.projectGroupId;
	}

	public void setProjectGroupId(Long projectGroupId) {
		this.projectGroupId = projectGroupId;
	}

	@Column(name = "SUBPROJECT_GROUP_ID", precision = 10, scale = 0)
	public Long getSubprojectGroupId() {
		return this.subprojectGroupId;
	}

	public void setSubprojectGroupId(Long subprojectGroupId) {
		this.subprojectGroupId = subprojectGroupId;
	}

	@Column(name = "SUBMISSION_REASON_ID", nullable = false, precision = 10, scale = 0)
	public Long getSubmissionReasonId() {
		return this.submissionReasonId;
	}

	public void setSubmissionReasonId(Long submissionReasonId) {
		this.submissionReasonId = submissionReasonId;
	}

	@Column(name = "IC_COMPLETE_FLAG", length = 4)
	public String getIcCompleteFlag() {
		return this.icCompleteFlag;
	}

	public void setIcCompleteFlag(String icCompleteFlag) {
		this.icCompleteFlag = icCompleteFlag;
	}

	@Column(name = "PI_FIRST_NAME", length = 120)
	public String getPiFirstName() {
		return this.piFirstName;
	}

	public void setPiFirstName(String piFirstName) {
		this.piFirstName = piFirstName;
	}

	@Column(name = "PI_LAST_NAME", length = 120)
	public String getPiLastName() {
		return this.piLastName;
	}

	public void setPiLastName(String piLastName) {
		this.piLastName = piLastName;
	}

	@Column(name = "POC_FIRST_NAME", length = 120)
	public String getPocFirstName() {
		return this.pocFirstName;
	}

	public void setPocFirstName(String pocFirstName) {
		this.pocFirstName = pocFirstName;
	}

	@Column(name = "POC_LAST_NAME", length = 120)
	public String getPocLastName() {
		return this.pocLastName;
	}

	public void setPocLastName(String pocLastName) {
		this.pocLastName = pocLastName;
	}

	@Column(name = "PD_NPN_ID", precision = 22, scale = 0)
	public Long getPdNpnId() {
		return this.pdNpnId;
	}

	public void setPdNpnId(Long pdNpnId) {
		this.pdNpnId = pdNpnId;
	}

	@Column(name = "PD_FIRST_NAME", length = 120)
	public String getPdFirstName() {
		return this.pdFirstName;
	}

	public void setPdFirstName(String pdFirstName) {
		this.pdFirstName = pdFirstName;
	}

	@Column(name = "PD_LAST_NAME", length = 120)
	public String getPdLastName() {
		return this.pdLastName;
	}

	public void setPdLastName(String pdLastName) {
		this.pdLastName = pdLastName;
	}

	@Column(name = "POC_EMAIL_ADDRESS", length = 320)
	public String getPocEmailAddress() {
		return this.pocEmailAddress;
	}

	public void setPocEmailAddress(String pocEmailAddress) {
		this.pocEmailAddress = pocEmailAddress;
	}

	@Column(name = "PLAN_COMMENTS", length = 4000)
	public String getPlanComments() {
		return this.planComments;
	}

	public void setPlanComments(String planComments) {
		this.planComments = planComments;
	}

	@Column(name = "APPL_ID", precision = 10, scale = 0)
	public Long getApplId() {
		return this.applId;
	}

	public void setApplId(Long applId) {
		this.applId = applId;
	}

	@Column(name = "BSI_COMMENTS", length = 4000)
	public String getBsiComments() {
		return this.bsiComments;
	}

	public void setBsiComments(String bsiComments) {
		this.bsiComments = bsiComments;
	}

	@Column(name = "ANTICIPATED_SUBMISSION_DATE", length = 7)
	public Date getAnticipatedSubmissionDate() {
		return this.anticipatedSubmissionDate;
	}

	public void setAnticipatedSubmissionDate(Date anticipatedSubmissionDate) {
		this.anticipatedSubmissionDate = anticipatedSubmissionDate;
	}

	@Column(name = "PROJECT_SUBMISSION_TITLE", nullable = false, length = 400)
	public String getProjectSubmissionTitle() {
		return this.projectSubmissionTitle;
	}

	public void setProjectSubmissionTitle(String projectSubmissionTitle) {
		this.projectSubmissionTitle = projectSubmissionTitle;
	}

	@Column(name = "DATA_LINK_FLAG", nullable = false, length = 4)
	public String getDataLinkFlag() {
		return this.dataLinkFlag;
	}

	public void setDataLinkFlag(String dataLinkFlag) {
		this.dataLinkFlag = dataLinkFlag;
	}

	@Column(name = "GDS_PLAN_PAGE_STATUS_CODE", length = 400)
	public String getgdsPlanPageStatusCode() {
		return this.gdsPlanPageStatusCode;
	}

	public void setGdsPlanPageStatusCode(String gdsPlanPageStatusCode) {
		this.gdsPlanPageStatusCode = gdsPlanPageStatusCode;
	}

	@Column(name = "IC_PAGE_STATUS_CODE", length = 400)
	public String getIcPageStatusCode() {
		return this.icPageStatusCode;
	}

	public void setIcPageStatusCode(String icPageStatusCode) {
		this.icPageStatusCode = icPageStatusCode;
	}

	@Column(name = "BSI_PAGE_STATUS_CODE", length = 400)
	public String getBsiPageStatusCode() {
		return this.bsiPageStatusCode;
	}

	public void setBsiPageStatusCode(String bsiPageStatusCode) {
		this.bsiPageStatusCode = bsiPageStatusCode;
	}

	@Column(name = "DATA_SHARING_EXCEP_STATUS_CODE", length = 400)
	public String getDataSharingExcepStatusCode() {
		return this.dataSharingExcepStatusCode;
	}

	public void setDataSharingExcepStatusCode(String dataSharingExcepStatusCode) {
		this.dataSharingExcepStatusCode = dataSharingExcepStatusCode;
	}

	@Column(name = "REPOSITORY_PAGE_STATUS_CODE", length = 400)
	public String getRepositoryPageStatusCode() {
		return repositoryPageStatusCode;
	}

	public void setRepositoryPageStatusCode(String repositoryPageStatusCode) {
		this.repositoryPageStatusCode = repositoryPageStatusCode;
	}

	@Column(name = "SUBPROJECT_ELIGIBLE_FLAG", length = 4)
	public String getSubprojectEligibleFlag() {
		return this.subprojectEligibleFlag;
	}

	public void setSubprojectEligibleFlag(String subprojectEligibleFlag) {
		this.subprojectEligibleFlag = subprojectEligibleFlag;
	}
	
	@Formula(value="(SELECT count(*) FROM projects_t p WHERE p.parent_project_id = id AND p.latest_version_flag = 'Y')")
    public Long getSubprojectCount() {
		return subprojectCount;
	}
	
	public void setSubprojectCount(Long subprojectCount) {
		this.subprojectCount = subprojectCount;
	}
	
	@Formula(value="(SELECT count(*) FROM repository_statuses_t r WHERE r.project_id = id)")
    public Long getRepoCount() {
		return repoCount;
	}
	
	public void setRepoCount(Long repoCount) {
		this.repoCount = repoCount;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
	public List<RepositoryStatus> getRepositoryStatuses() {
		return this.repositoryStatuses;
	}

	public void setRepositoryStatuses(List<RepositoryStatus> repositoryStatuses) {
		this.repositoryStatuses = repositoryStatuses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy="parentProject")
	public List<ProjectsVw> getSubprojects() {
		return subprojects;
	}

	public void setSubprojects(List<ProjectsVw> subprojects) {
		this.subprojects = subprojects;
	}
	
	/**
	 * This method returns Project status.
	 * @return String
	 */
	@Formula(value = "case when (SUBPROJECT_FLAG='Y' or GDS_PLAN_PAGE_STATUS_CODE='COMPLETED') and IC_PAGE_STATUS_CODE='COMPLETED' and BSI_PAGE_STATUS_CODE='COMPLETED' and REPOSITORY_PAGE_STATUS_CODE ='COMPLETED' then 'COMPLETED' else 'INPROGRESS' end ")
	public String getProjectStatusCode(){
		return projectStatusCode;
	}
	
	public void setProjectStatusCode(String projectStatusCode){
		this.projectStatusCode = projectStatusCode;
	}

	@Transient
	public boolean isExpandRepository() {
		return expandRepository;
	}

	public void setExpandRepository(boolean expandRepository) {
		this.expandRepository = expandRepository;
	}
	
	/**
	 * This method is for displaying the pi full name and hyper link for the email.
	 * @return
	 */
	@Transient
	public String getPiFullName(){
		String lastName = piLastName;
		String firstName = piFirstName;
		String fullName = "";
		if(lastName != null && lastName.length() > 0){
			fullName = fullName + lastName;
		}
		if(lastName != null && lastName.length() > 0 && firstName != null && firstName.length() > 0){
			fullName = fullName + ", ";
		}
		if(firstName != null && firstName.length() > 0){
			fullName = fullName + firstName;
		}
		String email = piEmailAddress;
		if(StringUtils.isBlank(fullName)){
			return "";
		}else if(email == null || fullName.trim().length() < 1 || email.trim().length() < 1 ){
			return fullName;
		}
		else{
			return "<a href='mailto:" + email + "'>" + fullName + "</a>";
		}
	}
}

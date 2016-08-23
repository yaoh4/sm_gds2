package gov.nih.nci.cbiit.scimgmt.gds.domain;
// Generated Mar 28, 2016 10:25:57 AM by Hibernate Tools 4.0.0

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Formula;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;


/**
 * Project generated by hbm2java
 */
@Entity
@Table(name = "PROJECTS_T")
public class Project implements java.io.Serializable {

	private Long id;
	private String projectIdentifierNum;
	private String projectTitle;
	private String docAbbreviation;
	private String programBranch;
	private String applicationNum;
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
	private String submissionTitle;
	private String dataLinkFlag;
	private String subprojectEligibleFlag;
	private Date createdDate;
	private Date lastChangedDate;
	private List<PageStatus> pageStatuses = new ArrayList();
	private Set<Document> documents = new HashSet(0);
	private Set<PlanAnswerSelection> planAnswerSelections = new HashSet(0);
	private List<RepositoryStatus> repositoryStatuses = new ArrayList<RepositoryStatus>(0);
	private List<InstitutionalCertification> institutionalCertifications = new ArrayList<InstitutionalCertification>();
	
	
	private Long subprojectCount;
	private Long repoCount;
	private String applClassCode;
	
	public Project() {
	}

	public Project(Long id, String projectIdentifierNum, Long versionNum,
		 String createdBy) {
		this.id = id;
		this.projectIdentifierNum = projectIdentifierNum;
		this.versionNum = versionNum;
		this.createdBy = createdBy;
	}


	@Override
	public String toString() {
		return "Project [id=" + id + ", projectIdentifierNum=" + projectIdentifierNum + ", projectTitle="
				+ projectTitle + ", docAbbreviation=" + docAbbreviation + ", programBranch=" + programBranch
				+ ", applicationNum=" + applicationNum + ", piInstitution="
				+ piInstitution + ", piEmailAddress=" + piEmailAddress + ", projectStartDate=" + projectStartDate
				+ ", projectEndDate=" + projectEndDate + ", sciRevApprovalRcvdDate=" + sciRevApprovalRcvdDate
				+ ", parentAccessionNum=" + parentAccessionNum + ", comments=" + comments + ", bsiReviewedFlag="
				+ bsiReviewedFlag + ", versionNum=" + versionNum + ", createdBy="
				+ createdBy + ", lastChangedBy=" + lastChangedBy
				+ ", subprojectFlag=" + subprojectFlag + ", parentProjectId=" + parentProjectId
				+ ", latestVersionFlag=" + latestVersionFlag + ", projectGroupId=" + projectGroupId
				+ ", subprojectGroupId=" + subprojectGroupId + ", submissionReasonId=" + submissionReasonId
				+ ", certificationCompleteFlag=" + certificationCompleteFlag + ", piFirstName=" + piFirstName
				+ ", piLastName=" + piLastName + ", pocFirstName=" + pocFirstName + ", pocLastName=" + pocLastName
				+ ", pdFirstName=" + pdFirstName + ", pdLastName=" + pdLastName + ", pageStatuses="
				+ pageStatuses + ", documents=" + documents + ", planAnswerSelections=" + planAnswerSelections
				+ ", repositoryStatuses=" + repositoryStatuses + ", institutionalCertifications="
				+ institutionalCertifications +  ", applId=" + applId +  ", submissionTitle=" + submissionTitle +  ", dataLinkFlag=" + dataLinkFlag +"]";
	}


	public Project(Long id, String projectIdentifierNum, String projectTitle, String docAbbreviation,
			String programBranch, String applicationNum, String piInstitution,
			String piEmailAddress, Date projectStartDate, Date projectEndDate, Date sciRevApprovalRcvdDate,
			String parentAccessionNum, String comments, String bsiReviewedFlag, Long versionNum,
			String createdBy, String lastChangedBy, String subprojectFlag,
			Long parentProjectId, String latestVersionFlag, Long projectGroupId, Long subprojectGroupId,
			Long submissionReasonId, String certificationCompleteFlag, String piFirstName, String piLastName,
			String pocFirstName, String pocLastName, String pdFirstName, String pdLastName, List pageStatuses, Set documents, String subprojectEligibleFlag, 
			Set planAnswerSelections, List repositoryStatuses, List institutionalCertifications,Long applId, String submissionTitle, String dataLinkFlag) {
		this.id = id;
		this.projectIdentifierNum = projectIdentifierNum;
		this.projectTitle = projectTitle;
		this.docAbbreviation = docAbbreviation;
		this.programBranch = programBranch;
		this.applicationNum = applicationNum;
		this.piInstitution = piInstitution;
		this.piEmailAddress = piEmailAddress;
		this.projectStartDate = projectStartDate;
		this.projectEndDate = projectEndDate;
		this.sciRevApprovalRcvdDate = sciRevApprovalRcvdDate;
		this.parentAccessionNum = parentAccessionNum;
		this.comments = comments;
		this.bsiReviewedFlag = bsiReviewedFlag;
		this.versionNum = versionNum;
		this.createdBy = createdBy;
		this.lastChangedBy = lastChangedBy;
		this.subprojectFlag = subprojectFlag;
		this.parentProjectId = parentProjectId;
		this.latestVersionFlag = latestVersionFlag;
		this.projectGroupId = projectGroupId;
		this.subprojectGroupId = subprojectGroupId;
		this.submissionReasonId = submissionReasonId;
		this.certificationCompleteFlag = certificationCompleteFlag;
		this.piFirstName = piFirstName;
		this.piLastName = piLastName;
		this.pocFirstName = pocFirstName;
		this.pocLastName = pocLastName;
		this.pdFirstName = pdFirstName;
		this.pdLastName = pdLastName;
		this.pageStatuses = pageStatuses;
		this.documents = documents;
		this.planAnswerSelections = planAnswerSelections;
		this.repositoryStatuses = repositoryStatuses;
		this.institutionalCertifications = institutionalCertifications;
		this.applId = applId;
		this.submissionTitle = submissionTitle;
		this.dataLinkFlag = dataLinkFlag;
		this.subprojectEligibleFlag = subprojectEligibleFlag;
		
	}

	@Id
	@SequenceGenerator(name="project_seq_gen", sequenceName="PROJ_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq_gen")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
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

	@Column(name = "PROJECT_TITLE", length = 400)
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
	public String getApplicationNum() {
		return this.applicationNum;
	}

	public void setApplicationNum(String applicationNum) {
		this.applicationNum = applicationNum;
	}

	@Column(name = "PI_INSTITUTION", length = 480)
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PROJECT_START_DATE", length = 7)
	public Date getProjectStartDate() {
		return this.projectStartDate;
	}

	public void setProjectStartDate(Date projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PROJECT_END_DATE", length = 7)
	public Date getProjectEndDate() {
		return this.projectEndDate;
	}

	public void setProjectEndDate(Date projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	
	@Temporal(TemporalType.TIMESTAMP)
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

	@Column(name = "PLAN_COMMENTS", length = 4000)
	public String getPlanComments() {
		return this.planComments;
	}

	public void setPlanComments(String planComments) {
		this.planComments = planComments;
	}
	
	@Column(name = "BSI_COMMENTS", length = 4000)
	public String getBsiComments() {
		return this.bsiComments;
	}

	public void setBsiComments(String bsiComments) {
		this.bsiComments = bsiComments;
	}
	
	@Column(name = "BSI_REVIEWED_FLAG", length = 4)
	public String getBsiReviewedFlag() {
		return this.bsiReviewedFlag;
	}

	public void setBsiReviewedFlag(String bsiReviewedFlag) {
		this.bsiReviewedFlag = bsiReviewedFlag;
	}

	@Column(name = "VERSION_NUM", nullable = false, precision = 10, scale = 0)
	public Long getVersionNum() {
		return this.versionNum;
	}

	public void setVersionNum(Long versionNum) {
		this.versionNum = versionNum;
	}

	@Column(name = "CREATED_BY", nullable = false, length = 120)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "LAST_CHANGED_BY", length = 120)
	public String getLastChangedBy() {
		return this.lastChangedBy;
	}

	public void setLastChangedBy(String lastChangedBy) {
		this.lastChangedBy = lastChangedBy;
	}
	
	@Transient
	public String getUpdatedBy() {
		if(lastChangedBy != null) {
			return lastChangedBy;
		}
		
		return createdBy;
	}
	
	@Column(name = "SUBPROJECT_FLAG", length = 4)
	public String getSubprojectFlag() {
		return this.subprojectFlag;
	}

	public void setSubprojectFlag(String subprojectFlag) {
		this.subprojectFlag = subprojectFlag;
	}

	@Column(name = "PARENT_PROJECT_ID", precision = 10, scale = 0)
	public Long getParentProjectId() {
		return this.parentProjectId;
	}

	public void setParentProjectId(Long parentProjectId) {
		this.parentProjectId = parentProjectId;
	}

	@Column(name = "LATEST_VERSION_FLAG", length = 4)
	public String getLatestVersionFlag() {
		return this.latestVersionFlag;
	}

	public void setLatestVersionFlag(String latestVersionFlag) {
		this.latestVersionFlag = latestVersionFlag;
	}

	
	@Column(name = "PROJECT_GROUP_ID", precision = 10, scale = 0)
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

	@Column(name = "IC_COMPLETE_FLAG", length = 1)
	public String getCertificationCompleteFlag() {
		return this.certificationCompleteFlag;
	}

	public void setCertificationCompleteFlag(String certificationCompleteFlag) {
		this.certificationCompleteFlag = certificationCompleteFlag;
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

	@Column(name = "POC_EMAIL_ADDRESS", length = 320)
	public String getPocEmailAddress() {
		return pocEmailAddress;
	}

	public void setPocEmailAddress(String pocEmailAddress) {
		this.pocEmailAddress = pocEmailAddress;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", length = 7)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_CHANGED_DATE", length = 7)
	public Date getLastChangedDate() {
		return lastChangedDate;
	}

	public void setLastChangedDate(Date lastChangedDate) {
		this.lastChangedDate = lastChangedDate;
	}
	
	@Transient
	public Date getUpdatedDate() {
		if(lastChangedDate != null) {
			return lastChangedDate;
		}
		
		return createdDate;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project", orphanRemoval=true)
	@Cascade({CascadeType.ALL})
	@OrderBy("createdDate DESC")
	public List<PageStatus> getPageStatuses() {
		return this.pageStatuses;
	}

	public void setPageStatuses(List<PageStatus> pageStatuses) {
		this.pageStatuses = pageStatuses;
	}
	
	public void addUpdatePageStatus(PageStatus pageStatus) {
		if(!pageStatuses.isEmpty()) {
			Iterator<PageStatus> pageIterator = pageStatuses.iterator();
			while(pageIterator.hasNext()) {
				PageStatus status = pageIterator.next();
				if(status.getPage().getId().equals(pageStatus.getPage().getId())) {
					pageIterator.remove();
					break;
				}
			}
		}
		this.pageStatuses.add(pageStatus);
	}
	
	public PageStatus getPageStatus(String pageCode) {
		Iterator<PageStatus> statuses = getPageStatuses().iterator();
		while(statuses.hasNext()) {
		    PageStatus pageStatus = statuses.next();
			if(pageStatus.getPage().getCode().equals(pageCode)) {
				return pageStatus;
			}
		}
		
		return null;
	}

	@Transient 
	public Set<Document> getDocuments() {
		return this.documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}

	@Column(name = "APPL_ID", length = 10)
	public Long getApplId() {
		return applId;
	}

	public void setApplId(Long applId) {
		this.applId = applId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ANTICIPATED_SUBMISSION_DATE", length = 7)
	public Date getAnticipatedSubmissionDate() {
		return anticipatedSubmissionDate;
	}

	public void setAnticipatedSubmissionDate(Date anticipatedSubmissionDate) {
		this.anticipatedSubmissionDate = anticipatedSubmissionDate;
	}
	
	@Column(name = "PROJECT_SUBMISSION_TITLE", length = 100)
	public String getSubmissionTitle() {
		return submissionTitle;
	}

	public void setSubmissionTitle(String submissionTitle) {
		this.submissionTitle = submissionTitle;
	}

	@Column(name = "DATA_LINK_FLAG", length = 1)
	public String getDataLinkFlag() {
		return dataLinkFlag;
	}

	public void setDataLinkFlag(String dataLinkFlag) {
		this.dataLinkFlag = dataLinkFlag;
	}

	@Column(name = "SUBPROJECT_ELIGIBLE_FLAG", length = 4)
	public String getSubprojectEligibleFlag() {
		return this.subprojectEligibleFlag;
	}

	public void setSubprojectEligibleFlag(String subprojectEligibleFlag) {
		this.subprojectEligibleFlag = subprojectEligibleFlag;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project", orphanRemoval=true)
	@Cascade({CascadeType.ALL})
	public Set<PlanAnswerSelection> getPlanAnswerSelections() {
		return this.planAnswerSelections;
	}

	public void setPlanAnswerSelections(Set<PlanAnswerSelection> planAnswerSelections) {
		this.planAnswerSelections = planAnswerSelections;
	}
	
	@Transient
	public PlanAnswerSelection getPlanAnswerSelectionById(Long id) {
		for(PlanAnswerSelection sel: getPlanAnswerSelections()) {
			if(sel.getId().longValue() == id.longValue())
				return sel;
		}
		return null;
	}
	
	@Transient
	public PlanAnswerSelection getPlanAnswerSelectionByAnswerId(Long id) {
		for(PlanAnswerSelection sel: getPlanAnswerSelections()) {
			if(sel.getPlanQuestionsAnswer().getId().longValue() == id.longValue())
				return sel;
		}
		return null;
	}
	
	@Transient
	public PlanAnswerSelection getPlanAnswerSelectionByAnswerIdAndText(Long id, String other) {
		for(PlanAnswerSelection sel: getPlanAnswerSelections()) {
			if(StringUtils.isEmpty(other) && sel.getPlanQuestionsAnswer().getId().longValue() == id.longValue()) {
				return sel;
			}
			if(StringUtils.isNotEmpty(other) && sel.getPlanQuestionsAnswer().getId().longValue() == id.longValue()
					&& StringUtils.equals(sel.getOtherText(), other)) {
				return sel;
			}
		}
		return null;
	}
	
	@Transient
	public Set<PlanAnswerSelection> getPlanAnswerSelectionByQuestionId(Long id) {
		Set<PlanAnswerSelection> set = new HashSet<PlanAnswerSelection>();
		for(PlanAnswerSelection sel: getPlanAnswerSelections()) {
			if(sel.getPlanQuestionsAnswer().getQuestionId().longValue() == id.longValue())
				set.add(sel);
		}
		return set;
	}
	
	@Transient
	public List<RepositoryStatus> getRepositoryStatuses() {
		return this.repositoryStatuses;
	}

	public void setRepositoryStatuses(List<RepositoryStatus> repositoryStatuses) {
		this.repositoryStatuses = repositoryStatuses;
	}

	@ManyToMany(mappedBy="projects") 
	public List<InstitutionalCertification> getInstitutionalCertifications() {
		return this.institutionalCertifications;
	}

	public void setInstitutionalCertifications(List<InstitutionalCertification> institutionalCertifications) {
		this.institutionalCertifications = institutionalCertifications;
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

	@Transient 
	public String getApplClassCode() {
		return applClassCode;
	}

	public void setApplClassCode(String applClassCode) {
		this.applClassCode = applClassCode;
	}
}

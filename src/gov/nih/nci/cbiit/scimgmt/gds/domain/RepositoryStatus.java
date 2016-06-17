package gov.nih.nci.cbiit.scimgmt.gds.domain;
// Generated Mar 4, 2016 12:46:29 PM by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * RepositoryStatus generated by hbm2java
 */
@Entity
@Table(name = "REPOSITORY_STATUSES_T")
public class RepositoryStatus implements java.io.Serializable {

	private Long id;
	private PlanQuestionsAnswer planQuestionAnswerTByRepositoryId;
	private Lookup lookupTByDataSubmissionStatusId;
	private Lookup lookupTByRegistrationStatusId;
	private Lookup lookupTByStudyReleasedId;
	private Project project;
	private Date anticipatedSubmissionDate;
	private String accessionNumber;
	private String comments;
	private Date createdDate;
	private String createdBy;
	private Date lastChangedDate;
	private String lastChangedBy;

	public RepositoryStatus() {
	}

	public RepositoryStatus(Long id, PlanQuestionsAnswer planQuestionAnswerTByRepositoryId, Lookup lookupTByDataSubmissionStatusId,
			Lookup lookupTByRegistrationStatusId, Lookup lookupTByStudyReleasedId, Project project,
			Date createdDate, String createdBy) {
		this.id = id;
		this.planQuestionAnswerTByRepositoryId = planQuestionAnswerTByRepositoryId;
		this.lookupTByDataSubmissionStatusId = lookupTByDataSubmissionStatusId;
		this.lookupTByRegistrationStatusId = lookupTByRegistrationStatusId;
		this.lookupTByStudyReleasedId = lookupTByStudyReleasedId;
		this.project = project;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
	}

	public RepositoryStatus(Long id, PlanQuestionsAnswer planQuestionAnswerTByRepositoryId, Lookup lookupTByDataSubmissionStatusId,
			Lookup lookupTByRegistrationStatusId, Lookup lookupTByStudyReleasedId, Project project,
			Date anticipatedSubmissionDate, String accessionNumber, String comments, Date createdDate,
			String createdBy, Date lastChangedDate, String lastChangedBy) {
		this.id = id;
		this.planQuestionAnswerTByRepositoryId = planQuestionAnswerTByRepositoryId;
		this.lookupTByDataSubmissionStatusId = lookupTByDataSubmissionStatusId;
		this.lookupTByRegistrationStatusId = lookupTByRegistrationStatusId;
		this.lookupTByStudyReleasedId = lookupTByStudyReleasedId;
		this.project = project;
		this.anticipatedSubmissionDate = anticipatedSubmissionDate;
		this.accessionNumber = accessionNumber;
		this.comments = comments;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.lastChangedDate = lastChangedDate;
		this.lastChangedBy = lastChangedBy;
	}

	@Id

	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REPOSITORY_ID", nullable = false)
	public PlanQuestionsAnswer getPlanQuestionAnswerTByRepositoryId() {
		return this.planQuestionAnswerTByRepositoryId;
	}

	public void setPlanQuestionAnswerTByRepositoryId(PlanQuestionsAnswer planQuestionAnswerTByRepositoryId) {
		this.planQuestionAnswerTByRepositoryId = planQuestionAnswerTByRepositoryId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DATA_SUBMISSION_STATUS_ID", nullable = false)
	public Lookup getLookupTByDataSubmissionStatusId() {
		return this.lookupTByDataSubmissionStatusId;
	}

	public void setLookupTByDataSubmissionStatusId(Lookup lookupTByDataSubmissionStatusId) {
		this.lookupTByDataSubmissionStatusId = lookupTByDataSubmissionStatusId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REGISTRATION_STATUS_ID", nullable = false)
	public Lookup getLookupTByRegistrationStatusId() {
		return this.lookupTByRegistrationStatusId;
	}

	public void setLookupTByRegistrationStatusId(Lookup lookupTByRegistrationStatusId) {
		this.lookupTByRegistrationStatusId = lookupTByRegistrationStatusId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STUDY_RELEASED_ID", nullable = false)
	public Lookup getLookupTByStudyReleasedId() {
		return this.lookupTByStudyReleasedId;
	}

	public void setLookupTByStudyReleasedId(Lookup lookupTByStudyReleasedId) {
		this.lookupTByStudyReleasedId = lookupTByStudyReleasedId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID", nullable = false)
	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ANTICIPATED_SUBMISSION_DATE", length = 7)
	public Date getAnticipatedSubmissionDate() {
		return this.anticipatedSubmissionDate;
	}

	public void setAnticipatedSubmissionDate(Date anticipatedSubmissionDate) {
		this.anticipatedSubmissionDate = anticipatedSubmissionDate;
	}

	@Column(name = "ACCESSION_NUMBER", length = 120)
	public String getAccessionNumber() {
		return this.accessionNumber;
	}

	public void setAccessionNumber(String accessionNumber) {
		this.accessionNumber = accessionNumber;
	}

	@Column(name = "COMMENTS", length = 4000)
	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false, length = 7)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "CREATED_BY", nullable = false, length = 120)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_CHANGED_DATE", length = 7)
	public Date getLastChangedDate() {
		return this.lastChangedDate;
	}

	public void setLastChangedDate(Date lastChangedDate) {
		this.lastChangedDate = lastChangedDate;
	}

	@Column(name = "LAST_CHANGED_BY", length = 120)
	public String getLastChangedBy() {
		return this.lastChangedBy;
	}

	public void setLastChangedBy(String lastChangedBy) {
		this.lastChangedBy = lastChangedBy;
	}

}

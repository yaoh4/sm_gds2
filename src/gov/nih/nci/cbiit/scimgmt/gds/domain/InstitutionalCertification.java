package gov.nih.nci.cbiit.scimgmt.gds.domain;
// Generated Mar 4, 2016 12:46:29 PM by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * InstitutionalCertification generated by hbm2java
 */
@Entity
@Table(name = "INSTITUTIONAL_CERTIFICATIONS_T")
public class InstitutionalCertification implements java.io.Serializable {

	private Long id;
	private Project project;
	private String gpaApprovalCode;
	private String provisionalFinalCode;
	private String futureProjectUseFlag;
	private String comments;
	private Date createdDate;
	private String createdBy;
	private Date lastChangedDate;
	private String lastChangedBy;
	private Set<Document> documents = new HashSet(0);
	private Set<Study> studies = new HashSet(0);

	public InstitutionalCertification() {
	}

	public InstitutionalCertification(Long id, Project project, Date createdDate, String createdBy) {
		this.id = id;
		this.project = project;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
	}

	public InstitutionalCertification(Long id, Project project, String gpaApprovalCode,
			String provisionalFinalCode, String futureProjectUseFlag, String comments, Date createdDate,
			String createdBy, Date lastChangedDate, String lastChangedBy, Set documents, Set studies) {
		this.id = id;
		this.project = project;
		this.gpaApprovalCode = gpaApprovalCode;
		this.provisionalFinalCode = provisionalFinalCode;
		this.futureProjectUseFlag = futureProjectUseFlag;
		this.comments = comments;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.lastChangedDate = lastChangedDate;
		this.lastChangedBy = lastChangedBy;
		this.documents = documents;
		this.studies = studies;
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
	@JoinColumn(name = "PROJECT_ID", nullable = false)
	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Column(name = "GPA_APPROVAL_CODE", length = 8)
	public String getGpaApprovalCode() {
		return this.gpaApprovalCode;
	}

	public void setGpaApprovalCode(String gpaApprovalCode) {
		this.gpaApprovalCode = gpaApprovalCode;
	}

	@Column(name = "PROVISIONAL_FINAL_CODE", length = 4)
	public String getProvisionalFinalCode() {
		return this.provisionalFinalCode;
	}

	public void setProvisionalFinalCode(String provisionalFinalCode) {
		this.provisionalFinalCode = provisionalFinalCode;
	}

	@Column(name = "FUTURE_PROJECT_USE_FLAG", length = 4)
	public String getFutureProjectUseFlag() {
		return this.futureProjectUseFlag;
	}

	public void setFutureProjectUseFlag(String futureProjectUseFlag) {
		this.futureProjectUseFlag = futureProjectUseFlag;
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

	@Transient
	public Set<Document> getDocuments() {
		return this.documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "institutionalCertification")
	public Set<Study> getStudies() {
		return this.studies;
	}

	public void setStudies(Set<Study> studies) {
		this.studies = studies;
	}

}

package gov.nih.nci.cbiit.scimgmt.gds.domain;
// Generated Jun 14, 2016 9:48:11 PM by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Document generated by hbm2java
 */
@Entity
@Table(name = "DOCUMENTS_T")
public class Document implements java.io.Serializable {

	private Long id;
	private Long institutionalCertificationId;
	private Long projectId;
	private Lookup docType;
	private String fileName;
	private Date uploadedDate;
	private Long versionNum;
	private String activeFlag;
	private Date createdDate;
	private String createdBy;
	private Date lastChangedDate;
	private String lastChangedBy;
	private byte[] doc;
	private String contentType;
	private String docTitle;
	private String uploadedBy;

	public Document() {
	}

	public Document(Long id, Long projectId, Lookup docType, Date uploadedDate, Long versionNum,
			String activeFlag, Date createdDate, String createdBy, byte[] doc, String contentType) {
		this.id = id;
		this.projectId = projectId;
		this.docType = docType;
		this.uploadedDate = uploadedDate;
		this.versionNum = versionNum;
		this.activeFlag = activeFlag;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.doc = doc;
		this.contentType = contentType;
	}

	public Document(Long id, Long projectId, Lookup docType,
			Long institutionalCertificationId, String fileName, Date uploadedDate,
			Long versionNum, String activeFlag, Date createdDate, String createdBy, Date lastChangedDate,
			String lastChangedBy, byte[] doc, String contentType, String docTitle, String uploadedBy) {
		this.id = id;
		this.institutionalCertificationId = institutionalCertificationId;
		this.projectId = projectId;
		this.docType = docType;
		this.fileName = fileName;
		this.uploadedDate = uploadedDate;
		this.versionNum = versionNum;
		this.activeFlag = activeFlag;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.lastChangedDate = lastChangedDate;
		this.lastChangedBy = lastChangedBy;
		this.doc = doc;
		this.contentType = contentType;
		this.docTitle = docTitle;
		this.uploadedBy = uploadedBy;
	}

	@Id
	@SequenceGenerator(name="doc_seq_gen", sequenceName="DOC_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doc_seq_gen")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@Column(name = "PROJECT_ID", nullable = false)
	public Long getProjectId() {
		return this.projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	@Column(name = "CERTIFICATION_ID")
	public Long getInstitutionalCertificationId() {
		return this.institutionalCertificationId;
	}

	public void setInstitutionalCertificationId(Long institutionalCertificationId) {
		this.institutionalCertificationId = institutionalCertificationId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOC_TYPE_ID", nullable = false)
	public Lookup getDocType() {
		return this.docType;
	}

	public void setDocType(Lookup docType) {
		this.docType = docType;
	}

	@Column(name = "FILE_NAME", length = 120)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPLOADED_DATE", nullable = false, length = 7)
	public Date getUploadedDate() {
		return this.uploadedDate;
	}

	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
	}

	@Column(name = "VERSION_NUM", nullable = false, precision = 10, scale = 0)
	public Long getVersionNum() {
		return this.versionNum;
	}

	public void setVersionNum(Long versionNum) {
		this.versionNum = versionNum;
	}

	@Column(name = "ACTIVE_FLAG", nullable = false, length = 4)
	public String getActiveFlag() {
		return this.activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
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

	@Column(name = "DOC", nullable = false)
	public byte[] getDoc() {
		return this.doc;
	}

	public void setDoc(byte[] doc) {
		this.doc = doc;
	}

	@Column(name = "CONTENT_TYPE", nullable = false, length = 40)
	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Column(name = "DOC_TITLE", length = 200)
	public String getDocTitle() {
		return this.docTitle;
	}

	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}


	@Column(name = "UPLOADED_BY", length = 80)
	public String getUploadedBy() {
		return this.uploadedBy;
	}
	
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	
}

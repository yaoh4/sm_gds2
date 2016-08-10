package gov.nih.nci.cbiit.scimgmt.gds.domain;
// Generated Mar 4, 2016 12:46:29 PM by Hibernate Tools 4.0.0

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
 * StatusHistory generated by hbm2java
 */
@Entity
@Table(name = "PAGE_STATUSES_T")
public class PageStatus implements java.io.Serializable {

	private Long id;
	private Lookup status;
	private Lookup page;
	private Project project;
	private String createdBy;
	private String lastChangedBy;

	public PageStatus() {
	}

	public PageStatus(Long id, Lookup status, Lookup page, Project project) {
		this.id = id;
		this.status = status;
		this.page = page;
		this.project = project;
		
	}

	public PageStatus(Long id, Lookup status, Lookup page, Project project, Date createdDate, String createdBy,
			Date lastChangedDate, String lastChangedBy) {
		this.id = id;
		this.status = status;
		this.page = page;
		this.project = project;
		this.createdBy = createdBy;
		this.lastChangedBy = lastChangedBy;
	}


	@Id
	@SequenceGenerator(name="pst_seq_gen", sequenceName="PST_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pst_seq_gen")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATUS_ID", nullable = false)
	public Lookup getStatus() {
		return this.status;
	}

	public void setStatus(Lookup status) {
		this.status = status;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAGE_ID", nullable = false)
	public Lookup getPage() {
		return this.page;
	}

	public void setPage(Lookup page) {
		this.page = page;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID", nullable = false)
	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}


	@Column(name = "CREATED_BY", length = 120)
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

}

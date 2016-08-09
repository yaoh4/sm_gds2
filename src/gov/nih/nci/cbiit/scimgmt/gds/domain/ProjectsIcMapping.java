package gov.nih.nci.cbiit.scimgmt.gds.domain;


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



@Entity
@Table(name = "PROJECTS_IC_MAPPING_T")
public class ProjectsIcMapping implements java.io.Serializable {

	private Long id;
	private Project project;
	private String createdBy;
	private String lastChangedBy;
	private InstitutionalCertification institutionalCertification;
	
	public ProjectsIcMapping() {
		
	}
	
	public ProjectsIcMapping(Project project, InstitutionalCertification institutionalCertification) {
		this.project = project;
		this.institutionalCertification = institutionalCertification;
	}

	public ProjectsIcMapping(Project project, String createdBy,
			String lastChangedBy, InstitutionalCertification institutionalCertification) {
		
		
		this.createdBy = createdBy;
		this.lastChangedBy = lastChangedBy;
		this.project = project;
		this.institutionalCertification = institutionalCertification;
	}

	@Id
	@SequenceGenerator(name="projice_seq_gen", sequenceName="PROJICE_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "projice_seq_gen")
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CERTIFICATION_ID", nullable = false)
	public InstitutionalCertification getInstitutionalCertification() {
		return institutionalCertification;
	}

	
	public void setInstitutionalCertification(InstitutionalCertification institutionalCertification) {
		this.institutionalCertification = institutionalCertification;
	}

}

package gov.nih.nci.cbiit.scimgmt.gds.domain;
// Generated Mar 28, 2016 10:25:57 AM by Hibernate Tools 4.0.0

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * Study generated by hbm2java
 */
@Entity
@Table(name = "STUDIES_T")
public class Study implements java.io.Serializable {

	private Long id;
	private InstitutionalCertification institutionalCertification;
	private Project project;
	private String studyName;
	private String institution;
	private Long dulVerificationId;
	private String comments;
	private String createdBy;
	private String lastChangedBy;
	private List<StudiesDulSet> studiesDulSets = new ArrayList<StudiesDulSet>();
	private String displayId = null;

	public Study() {
	}

	public Study(Long id, InstitutionalCertification institutionalCertification, String studyName,
			String createdBy) {
		this.id = id;
		this.institutionalCertification = institutionalCertification;
		this.studyName = studyName;
		this.createdBy = createdBy;
	}

	public Study(Long id, InstitutionalCertification institutionalCertification, String studyName,
			String institution, Long dulVerificationId, String createdBy,
			String lastChangedBy, List<StudiesDulSet> studiesDulSets) {
		this.id = id;
		this.institutionalCertification = institutionalCertification;
		this.studyName = studyName;
		this.institution = institution;
		this.dulVerificationId = dulVerificationId;
		this.createdBy = createdBy;
		this.lastChangedBy = lastChangedBy;
		this.studiesDulSets = studiesDulSets;
	}

	@Id
	@SequenceGenerator(name="stu_seq_gen", sequenceName="STU_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stu_seq_gen")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "CERTIFICATION_ID")
	public InstitutionalCertification getInstitutionalCertification() {
		return this.institutionalCertification;
	}

	public void setInstitutionalCertification(InstitutionalCertification institutionalCertification) {
		this.institutionalCertification = institutionalCertification;
	}
	
	@ManyToOne
	@JoinColumn(name = "PROJECT_ID")
	public Project getProject() {
		return project;
	}


	public void setProject(Project project) {
		this.project = project;
	}
	
	@Column(name = "STUDY_NAME", nullable = false, length = 400)
	public String getStudyName() {
		return this.studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	@Column(name = "INSTITUTION", length = 480)
	public String getInstitution() {
		return this.institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	@Column(name = "DUL_VERIFICATION_ID", precision = 10, scale = 0)
	public Long getDulVerificationId() {
		return this.dulVerificationId;
	}

	public void setDulVerificationId(Long dulVerificationId) {
		this.dulVerificationId = dulVerificationId;
	}

	@Column(name = "COMMENTS", length = 4000)
	public String getComments() {
		return comments;
	}

	
	public void setComments(String comments) {
		this.comments = comments;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "study", orphanRemoval=true)
	public List<StudiesDulSet> getStudiesDulSets() {
		return this.studiesDulSets;
	}

	public void setStudiesDulSets(List<StudiesDulSet> studiesDulSets) {
		this.studiesDulSets = studiesDulSets;
	}
	
	public void addStudiesDulSet(StudiesDulSet studiesDulSet) {
		this.studiesDulSets.add(studiesDulSet);
	}

	@Transient
	public String getDisplayId() {
		return displayId;
	}

	public void setDisplayId(String displayId) {
		this.displayId = displayId;
	}

}

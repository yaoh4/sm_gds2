package gov.nih.nci.cbiit.scimgmt.gds.domain;
// Generated Mar 4, 2016 12:46:29 PM by Hibernate Tools 4.0.0

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.util.CollectionUtils;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;

/**
 * InstitutionalCertification generated by hbm2java
 */
@Entity
@Table(name = "INSTITUTIONAL_CERTIFICATIONS_T")
public class InstitutionalCertification implements java.io.Serializable {

	private Long id;
	private Long gpaApprovalCode;
	private Long provisionalFinalCode;
	private Long futureProjectUseCode;
	private String comments;
	private String createdBy;
	private String lastChangedBy;
	private List<Document> documents = new ArrayList<Document>();
	private List<Study> studies = new ArrayList<Study>();
	//private List<ProjectsIcMapping> projectsIcMappings = new ArrayList<ProjectsIcMapping>();
	private List<Project> projects = new ArrayList();
	
	public InstitutionalCertification() {
	}

	public InstitutionalCertification(Long id,  String createdBy) {
		this.id = id;
		this.createdBy = createdBy;
	}

	public InstitutionalCertification(Long id, Project project, Long gpaApprovalCode,
			Long provisionalFinalCode, Long futureProjectUseCode, String comments,
			String createdBy, String lastChangedBy, List documents, List studies) {
		this.id = id;
		this.gpaApprovalCode = gpaApprovalCode;
		this.provisionalFinalCode = provisionalFinalCode;
		this.futureProjectUseCode = futureProjectUseCode;
		this.comments = comments;
		this.createdBy = createdBy;
		this.lastChangedBy = lastChangedBy;
		this.documents = documents;
		this.studies = studies;
	}

	@Id
	@SequenceGenerator(name="ice_seq_gen", sequenceName="ICE_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ice_seq_gen")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@Column(name = "GPA_APPROVAL_CODE", length = 8)
	public Long getGpaApprovalCode() {
		return this.gpaApprovalCode;
	}

	public void setGpaApprovalCode(Long gpaApprovalCode) {
		this.gpaApprovalCode = gpaApprovalCode;
	}

	@Column(name = "PROVISIONAL_FINAL_CODE", length = 4)
	public Long getProvisionalFinalCode() {
		return this.provisionalFinalCode;
	}

	public void setProvisionalFinalCode(Long provisionalFinalCode) {
		this.provisionalFinalCode = provisionalFinalCode;
	}

	@Column(name = "FUTURE_PROJECT_USE_CODE", length = 4)
	public Long getFutureProjectUseCode() {
		return this.futureProjectUseCode;
	}

	public void setFutureProjectUseCode(Long futureProjectUseCode) {
		this.futureProjectUseCode = futureProjectUseCode;
	}

	@Column(name = "COMMENTS", length = 4000)
	public String getComments() {
		return this.comments;
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

	@Transient
	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
	
	public void addDocument(Document document) {
		this.documents.add(document);
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "institutionalCertification", orphanRemoval=true)
	public List<Study> getStudies() {
		return this.studies;
	}

	public void setStudies(List<Study> studies) {
		this.studies = studies;
	}
	
	
	public void addStudy(Study study) {
		studies.add(study);
	}

	@ManyToMany(mappedBy="institutionalCertifications")
	public List<Project> getProjects() {
		return projects;
	}

	
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public void addProject(Project project) {
		this.projects.add(project);
	}
	
	public void removeProject(Project project) {
		Iterator<Project> projects = this.getProjects().iterator();
		while(projects.hasNext()) {
			Project curProject = projects.next();
			if(curProject.getId().equals(project.getId())) {
				projects.remove();
				break;
			}
		}
	}
	
	@Transient
	public String getStatus() {
		String status = ApplicationConstants.PAGE_STATUS_CODE_COMPLETED;
		
		if(!ApplicationConstants.IC_PROV_FINAL_ID_FINAL.equals(getProvisionalFinalCode())) {
			return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
		}
					
		if(!ApplicationConstants.IC_GPA_APPROVED_YES_ID.equals(getGpaApprovalCode())) {
			return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
		}
		List<Study> studies = getStudies();
		for(Study study: studies) {
			if(!ApplicationConstants.IC_DUL_VERIFIED_YES_ID.equals(study.getDulVerificationId())
				&& !ApplicationConstants.IC_DUL_VERIFIED_NOT_APPLICABLE_ID.equals(study.getDulVerificationId())) {
				return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
			}
		}		
		return status;
	}

}

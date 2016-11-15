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
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;

/**
 * RepositoryStatus generated by hbm2java
 */
@Entity
@Table(name = "REPOSITORY_STATUSES_T")
public class RepositoryStatus implements java.io.Serializable {
	
	private Long id;
	private PlanAnswerSelection planAnswerSelectionTByRepositoryId;
	private Lookup lookupTBySubmissionStatusId;
	private Lookup lookupTByRegistrationStatusId;
	private Lookup lookupTByStudyReleasedId;
	private Project project;
	private String accessionNumber;
	private String comments;
	private String createdBy;
	private String lastChangedBy;
	private Date createdDate;
	private Date lastChangedDate;
	private boolean selected = false;
	
	private NedPerson createdByPerson;
	private NedPerson lastChangedByPerson;

	public RepositoryStatus() {
	}

	public RepositoryStatus(Long id, PlanAnswerSelection planAnswerSelectionTByRepositoryId, Lookup lookupTBySubmissionStatusId,
			Lookup lookupTByRegistrationStatusId, Lookup lookupTByStudyReleasedId, Project project,
			 String createdBy) {
		this.id = id;
		this.planAnswerSelectionTByRepositoryId = planAnswerSelectionTByRepositoryId;
		this.lookupTBySubmissionStatusId = lookupTBySubmissionStatusId;
		this.lookupTByRegistrationStatusId = lookupTByRegistrationStatusId;
		this.lookupTByStudyReleasedId = lookupTByStudyReleasedId;
		this.project = project;
		this.createdBy = createdBy;
	}

	public RepositoryStatus(Long id, PlanAnswerSelection planAnswerSelectionTByRepositoryId, Lookup lookupTBySubmissionStatusId,
			Lookup lookupTByRegistrationStatusId, Lookup lookupTByStudyReleasedId, Project project,
			String accessionNumber, String comments, String createdBy, String lastChangedBy) {
		this.id = id;
		this.planAnswerSelectionTByRepositoryId = planAnswerSelectionTByRepositoryId;
		this.lookupTBySubmissionStatusId = lookupTBySubmissionStatusId;
		this.lookupTByRegistrationStatusId = lookupTByRegistrationStatusId;
		this.lookupTByStudyReleasedId = lookupTByStudyReleasedId;
		this.project = project;
		this.accessionNumber = accessionNumber;
		this.comments = comments;
		this.createdBy = createdBy;
		this.lastChangedBy = lastChangedBy;
	}

	@Id
	@SequenceGenerator(name="rst_seq_gen", sequenceName="RST_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rst_seq_gen")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REPOSITORY_ID", nullable = false)
	public PlanAnswerSelection getPlanAnswerSelectionTByRepositoryId() {
		return this.planAnswerSelectionTByRepositoryId;
	}

	public void setPlanAnswerSelectionTByRepositoryId(PlanAnswerSelection planAnswerSelectionTByRepositoryId) {
		this.planAnswerSelectionTByRepositoryId = planAnswerSelectionTByRepositoryId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DATA_SUBMISSION_STATUS_ID", nullable = false)
	public Lookup getLookupTBySubmissionStatusId() {
		return this.lookupTBySubmissionStatusId;
	}

	public void setLookupTBySubmissionStatusId(Lookup lookupTBySubmissionStatusId) {
		this.lookupTBySubmissionStatusId = lookupTBySubmissionStatusId;
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
	@JoinColumn(name = "CREATED_BY", nullable=true, insertable=false, updatable=false)
	public NedPerson getCreatedByPerson() {
		return this.createdByPerson;
	}
	
	public void setCreatedByPerson(NedPerson person) {
		this.createdByPerson = person;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LAST_CHANGED_BY", nullable=true, insertable=false, updatable=false)
	public NedPerson getLastChangedByPerson() {
		return this.lastChangedByPerson;
	}
	
	public void setLastChangedByPerson(NedPerson person) {
		this.lastChangedByPerson = person;
	}
	
	
	@Transient
	public String getUpdatedBy() {
		//If lastChangedBy present return that info
		if(lastChangedBy != null) {
			try {
				return getLastChangedByPerson().getFullName();
			} catch (Exception e) {
				//Person may be left, so return the stored user id
				return lastChangedBy;
			}
		}
		
		//Else get createdBy info
		try {
			return getCreatedByPerson().getFullName();
		} catch (Exception e) {
		
		return createdBy;
		}
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
	
	@Transient
	public String getStatus() {
		Long registrationStatusId = lookupTByRegistrationStatusId.getId();
		Long submissionStatusId = lookupTBySubmissionStatusId.getId();
		Long studyReleaseId = lookupTByStudyReleasedId.getId();
		
		if(ApplicationConstants.REGISTRATION_STATUS_NOTSTARTED_ID.equals(registrationStatusId)) {
			//No need to check this repository further, since the submission status
			//and study released fields will be disabled in this case
			return ApplicationConstants.PAGE_STATUS_CODE_NOT_STARTED;
		}
		
		if(ApplicationConstants.REGISTRATION_STATUS_COMPLETED_ID.equals(registrationStatusId)
			&& ( (ApplicationConstants.PROJECT_SUBMISSION_STATUS_COMPLETED_ID.equals(submissionStatusId)
			      && ApplicationConstants.PROJECT_STUDY_RELEASED_YES_ID.equals(studyReleaseId))
			     || ApplicationConstants.PROJECT_SUBMISSION_STATUS_NOTAPPLICABLE_ID.equals(submissionStatusId)
			   ) 
		  ) {			
			return ApplicationConstants.PAGE_STATUS_CODE_COMPLETED;
		}
			
		return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
	}

	@Transient
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}

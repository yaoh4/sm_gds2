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
@Table(name = "IC_STUDIES_MAPPING_T")
public class IcStudiesMapping implements java.io.Serializable {

	private Long id;
	private InstitutionalCertification institutionalCertification;
	private Study study;

	public IcStudiesMapping() {
		
	}
	
	public IcStudiesMapping(InstitutionalCertification institutionalCertification, Study study) {
		this.institutionalCertification = institutionalCertification;
		this.study = study;
	}

	@Id
	@SequenceGenerator(name="icestu_seq_gen", sequenceName="ICESTU_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "icestu_seq_gen")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CERTIFICATION_ID", nullable = false)
	public InstitutionalCertification getInstitutionalCertification() {
		return institutionalCertification;
	}
	
	public void setInstitutionalCertification(InstitutionalCertification institutionalCertification) {
		this.institutionalCertification = institutionalCertification;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STUDY_ID", nullable = false)
	public Study getStudy() {
		return this.study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}

}

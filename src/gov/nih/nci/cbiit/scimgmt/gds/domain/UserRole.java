package gov.nih.nci.cbiit.scimgmt.gds.domain;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@SuppressWarnings("serial")
@Entity
@Table(name = "USER_ROLES_VW")
public class UserRole implements java.io.Serializable {

	private String nihNetworkId;
	private Lookup gdsRole;
	private String pdFlag;
	private String activeFlag;
	private String createdByFullName;
	private String createdBy;
	private Date createdDate;
	
	//private NedPerson nedPerson;
	
	public UserRole() {
	}

	public UserRole(String nihNetworkId, Lookup gdsRole, String pdFlag, String activeFlag, String createdByFullName, String createdBy, Date createdDate) {
		this.nihNetworkId = nihNetworkId;
		this.gdsRole = gdsRole;
		this.pdFlag = pdFlag;
		this.activeFlag = activeFlag;
		this.createdByFullName = createdByFullName;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	
	@Id
	@Column(name = "NIH_NETWORK_ID", length = 30)
	public String getNihNetworkId() {
		return this.nihNetworkId;
	}

	public void setNihNetworkId(String nihNetworkId) {
		this.nihNetworkId = nihNetworkId;
	}
	
	
	@Column(name="GDS_ROLE_CODE", length = 30)
	public Lookup getGdsRole() {
		return gdsRole;
	}

	public void setGdsRole(Lookup gdsRole) {
		this.gdsRole = gdsRole;
	}

	@Column(name="PD_FLAG", length = 4)
	public String getPdFlag() {
		return pdFlag;
	}
	
	public void setPdFlag(String pdFlag) {
		this.pdFlag = pdFlag;
	}

	@Column(name="ACTIVE_FlAG", length = 4)
	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	@Column(name = "CREATED_BY_FULL_NAME", length = 120)
	public String getCreatedByFullName() {
		return createdByFullName;
	}

	public void setCreatedByFullName(String createdByFullName) {
		this.createdByFullName = createdByFullName;
	}
	
	@Column(name = "CREATED_BY", length = 120)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", length = 7)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	/*@OneToOne(mappedBy="userRole", optional = false)
	public NedPerson getNedPerson() {
		return nedPerson;
	}

	public void setNedPerson(NedPerson nedPerson) {
		this.nedPerson = nedPerson;
	}*/
}

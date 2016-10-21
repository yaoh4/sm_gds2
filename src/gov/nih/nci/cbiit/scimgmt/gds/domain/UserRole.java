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
	private String gdsRoleCode;
	private String pdFlag;
	private String activeFlag;
	private String createdByFullName;
	private String createdBy;
	private Date createdDate;
	private String lastChangedByFullName;
	private String lastChangedBy; 
	private Date lastChangedDate;
	
	//private NedPerson nedPerson;
	
	public UserRole() {
	}

	public UserRole(String nihNetworkId, String gdsRoleCode, String pdFlag, String activeFlag, String createdByFullName, String createdBy, Date createdDate, String lastChangedByFullName, String lastChangedBy , Date lastChangedDate ) {
		this.nihNetworkId = nihNetworkId;
		this.gdsRoleCode = gdsRoleCode;
		this.pdFlag = pdFlag;
		this.activeFlag = activeFlag;
		this.createdByFullName = createdByFullName;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.lastChangedByFullName = lastChangedByFullName;
		this.lastChangedBy = lastChangedBy;
		this.lastChangedDate = lastChangedDate;
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
	public String getGdsRoleCode() {
		return gdsRoleCode;
	}

	public void setGdsRoleCode(String gdsRoleCode) {
		this.gdsRoleCode = gdsRoleCode;
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
	
	@Column(name="LAST_CHANGED_BY_FULL_NAME", length = 120)
	public String getLastChangedByFullName() {
		return lastChangedByFullName;
	}

	public void setLastChangedByFullName(String lastChangedByFullName) {
		this.lastChangedByFullName = lastChangedByFullName;
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
  
	@Column(name = "LAST_CHANGED_BY", length = 120)
	public String getLastChangedBy() {
		return this.lastChangedBy;
	}

	public void setLastChangedBy(String lastChangedBy) {
		this.lastChangedBy = lastChangedBy;
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
	public String getFullName(){
		if(lastChangedByFullName != null) {
			return lastChangedByFullName;
		}
		
		return createdByFullName;
	}
	
}

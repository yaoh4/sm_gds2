package gov.nih.nci.cbiit.scimgmt.gds.domain;


import java.text.DateFormat;
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
@Table(name = "PERSON_ROLES_T")
public class PersonRole implements java.io.Serializable {

	private String nihNetworkId;
	private Lookup role;
	//private Long roleId;
	private String createdBy;
	private String lastChangedBy;
	private Date createdDate;
	private Date lastChangedDate;
	
	//private NedPerson nedPerson;

	public PersonRole() {
	}

	public PersonRole(String nihNetworkId, Lookup role, Date createdDate) {
		this.nihNetworkId = nihNetworkId;
		this.role = role;
		this.createdDate = createdDate;
	}

	
	@Id
	@Column(name = "NIH_NETWORK_ID", nullable = false, length = 30)
	public String getNihNetworkId() {
		return this.nihNetworkId;
	}

	public void setNihNetworkId(String nihNetworkId) {
		this.nihNetworkId = nihNetworkId;
	}
	
	
	/*@Column(name="ROLE_ID", nullable = false, length = 30)
	public Long getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}*/
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID", nullable = false)
	public Lookup getRole() {
		return this.role;
	}

	public void setRole(Lookup role) {
		this.role = role;
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
	public String getUpdatedDate() {
		if(lastChangedDate != null) {
			return DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(lastChangedDate);
		}	
		return DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(createdDate);
	}
	
	
	@Transient
	public String getUpdatedBy() {
		if(lastChangedBy != null) {
			return lastChangedBy;
		}
		
		return createdBy;
	}
	
}

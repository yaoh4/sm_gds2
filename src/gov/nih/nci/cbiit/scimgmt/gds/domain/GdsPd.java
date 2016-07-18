package gov.nih.nci.cbiit.scimgmt.gds.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * GdsPdVw
 */
@Entity
@Table(name = "GDS_PD_VW")
public class GdsPd {
	
	private Long npnId;
	private String firstName;
	private String miName;
	private String lastName;
	private String pdFullNameDescrip;
	private String pdCode;


	public GdsPd(){
		
	}
	
	public GdsPd(Long npnId, String firstName, String miName, String lastName, String pdFullNameDescrip,
			String pdCode) {
		this.npnId = npnId;
		this.firstName = firstName;
		this.miName = miName;
		this.lastName = lastName;
		this.pdFullNameDescrip = pdFullNameDescrip;
		this.pdCode = pdCode;
	}

	@Override
	public String toString() {
		return "GdsPd [npnId=" + npnId + ", firstName=" + firstName + ", miName=" + miName + ", lastName=" + lastName
				+ ", pdFullNameDescrip=" + pdFullNameDescrip + ", pdCode=" + pdCode + "]";
	}

	@Id
	@Column(name = "NPN_ID", length = 10)
	public Long getNpnId() {
		return npnId;
	}

	public void setNpnId(Long npnId) {
		this.npnId = npnId;
	}
	
	@Column(name = "PD_CODE", length = 3)
	public String getPdCode() {
		return pdCode;
	}
	
	public void setPdCode(String pdCode) {
		this.pdCode = pdCode;
	}
	
	@Column(name = "PD_FULL_NAME_DESCRIP", length = 2)
	public String getPdFullNameDescrip() {
		return pdFullNameDescrip;
	}
	
	public void setPdFullNameDescrip(String pdFullNameDescrip) {
		this.pdFullNameDescrip = pdFullNameDescrip;
	}
	
	@Column(name = "FIRST_NAME", length = 30)
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name = "LAST_NAME", length = 30)
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Column(name = "MI_NAME", length = 30)
	public String getMiName() {
		return miName;
	}
	
	public void setMiName(String miName) {
		this.miName = miName;
	}
	
}

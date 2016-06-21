package gov.nih.nci.cbiit.scimgmt.gds.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * GdsGrantsContracts generated by hbm2java
 */
@Entity
@Table(name = "GDS_GRANTS_CONTRACTS_VW")
public class GdsGrantsContracts {
	
	private String grantContractNum;
	private String applTypeCode;
	private String activityCode;
	private String adminPhsOrgCode;
	private Integer serialNum;
	private Integer supportYear;
	private String suffixCode;
	private String projectTitle;
	private String piFirstName;
	private String piLastName;
	private String piEmailAddress;
	private String piInstitution;
	private String pdFirstName;
	private String pdLastName;
	private Date projectPeriodStartDate;
	private Date projectPeriodEndDate;
	
	public GdsGrantsContracts(){
		
	}
	
	public GdsGrantsContracts(String grantContractNum, String applTypeCode, String activityCode, String adminPhsOrgCode,
			Integer serialNum, Integer supportYear, String suffixCode, String projectTitle, String piFirstName,
			String piLastName, String piEmailAddress, String piInstitution, String pdFirstName, String pdLastName,
			Date projectPeriodStartDate, Date projectPeriodEndDate) {
		super();
		this.grantContractNum = grantContractNum;
		this.applTypeCode = applTypeCode;
		this.activityCode = activityCode;
		this.adminPhsOrgCode = adminPhsOrgCode;
		this.serialNum = serialNum;
		this.supportYear = supportYear;
		this.suffixCode = suffixCode;
		this.projectTitle = projectTitle;
		this.piFirstName = piFirstName;
		this.piLastName = piLastName;
		this.piEmailAddress = piEmailAddress;
		this.piInstitution = piInstitution;
		this.pdFirstName = pdFirstName;
		this.pdLastName = pdLastName;
		this.projectPeriodStartDate = projectPeriodStartDate;
		this.projectPeriodEndDate = projectPeriodEndDate;
	}
	
	@Override
	public String toString() {
		return "GdsGrantsContracts [grantContractNum=" + grantContractNum + ", applTypeCode=" + applTypeCode
				+ ", activityCode=" + activityCode + ", adminPhsOrgCode=" + adminPhsOrgCode + ", serialNum=" + serialNum
				+ ", supportYear=" + supportYear + ", suffixCode=" + suffixCode + ", projectTitle=" + projectTitle
				+ ", piFirstName=" + piFirstName + ", piLastName=" + piLastName + ", piEmailAddress=" + piEmailAddress
				+ ", piInstitution=" + piInstitution + ", pdFirstName=" + pdFirstName + ", pdLastName=" + pdLastName
				+ ", projectPeriodStartDate=" + projectPeriodStartDate + ", projectPeriodEndDate="
				+ projectPeriodEndDate + "]";
	}
	
	@Id
	@Column(name = "GRANT_CONTRACT_NUM", length = 19)
	public String getGrantContractNum() {
		return grantContractNum;
	}
	
	public void setGrantContractNum(String grantContractNum) {
		this.grantContractNum = grantContractNum;
	}
	
	@Column(name = "APPL_TYPE_CODE", length = 1)
	public String getApplTypeCode() {
		return applTypeCode;
	}
	
	public void setApplTypeCode(String applTypeCode) {
		this.applTypeCode = applTypeCode;
	}
	
	@Column(name = "ACTIVITY_CODE", length = 3)
	public String getActivityCode() {
		return activityCode;
	}
	
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	
	@Column(name = "ADMIN_PHS_ORG_CODE", length = 2)
	public String getAdminPhsOrgCode() {
		return adminPhsOrgCode;
	}
	
	public void setAdminPhsOrgCode(String adminPhsOrgCode) {
		this.adminPhsOrgCode = adminPhsOrgCode;
	}
	
	@Column(name = "SERIAL_NUM", length = 6)
	public Integer getSerialNum() {
		return serialNum;
	}
	
	public void setSerialNum(Integer serialNum) {
		this.serialNum = serialNum;
	}
	
	@Column(name = "SUPPORT_YEAR", length = 2)
	public Integer getSupportYear() {
		return supportYear;
	}
	
	public void setSupportYear(Integer supportYear) {
		this.supportYear = supportYear;
	}
	
	@Column(name = "SUFFIX_CODE", length = 4)
	public String getSuffixCode() {
		return suffixCode;
	}
	
	public void setSuffixCode(String suffixCode) {
		this.suffixCode = suffixCode;
	}
	
	@Column(name = "PROJECT_TITLE", length = 200)
	public String getProjectTitle() {
		return projectTitle;
	}
	
	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}
	
	@Column(name = "PI_FIRST_NAME", length = 30)
	public String getPiFirstName() {
		return piFirstName;
	}
	
	public void setPiFirstName(String piFirstName) {
		this.piFirstName = piFirstName;
	}
	
	@Column(name = "PI_LAST_NAME", length = 30)
	public String getPiLastName() {
		return piLastName;
	}
	
	public void setPiLastName(String piLastName) {
		this.piLastName = piLastName;
	}
	
	@Column(name = "PI_EMAIL_ADDRESS", length = 80)
	public String getPiEmailAddress() {
		return piEmailAddress;
	}
	
	public void setPiEmailAddress(String piEmailAddress) {
		this.piEmailAddress = piEmailAddress;
	}
	
	@Column(name = "PI_INSTITUTION", length = 120)
	public String getPiInstitution() {
		return piInstitution;
	}
	
	public void setPiInstitution(String piInstitution) {
		this.piInstitution = piInstitution;
	}
	
	@Column(name = "PD_FIRST_NAME", length = 30)
	public String getPdFirstName() {
		return pdFirstName;
	}
	
	public void setPdFirstName(String pdFirstName) {
		this.pdFirstName = pdFirstName;
	}
	
	@Column(name = "PD_LAST_NAME", length = 30)
	public String getPdLastName() {
		return pdLastName;
	}
	
	public void setPdLastName(String pdLastName) {
		this.pdLastName = pdLastName;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PROJECT_PERIOD_START_DATE", length = 7)
	public Date getProjectPeriodStartDate() {
		return projectPeriodStartDate;
	}
	
	public void setProjectPeriodStartDate(Date projectPeriodStartDate) {
		this.projectPeriodStartDate = projectPeriodStartDate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PROJECT_PERIOD_END_DATE", length = 7)
	public Date getProjectPeriodEndDate() {
		return projectPeriodEndDate;
	}
	
	public void setProjectPeriodEndDate(Date projectPeriodEndDate) {
		this.projectPeriodEndDate = projectPeriodEndDate;
	}
}

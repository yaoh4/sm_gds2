package gov.nih.nci.cbiit.scimgmt.gds.model;


/**
 * Submission object which is used for data table result display
 */

public class Submission implements java.io.Serializable {

	private Long id;
	private String docAbbreviation;
	private String parentAccessionNum;
	private Long versionNum;
	private String subprojectFlag;
	private String latestVersionFlag;
	private Long projectGroupId;
	private Long submissionReasonId;
	private String projectSubmissionTitle;
	private String gdsPlanPageStatusCode;
	private String dataSharingExcepStatusCode;
	private String icPageStatusCode;
	private String bsiPageStatusCode;
	private String repositoryPageStatusCode;
	private String subprojectEligibleFlag;
	private String projectStatusCode;
	
	private String extGrantContractNum;
	private String extPiInstitution;
	private String extPiEmailAddress;
	private String extPiFirstName;
	private String extPiLastName;
	private Long extPdNpnId;
	private String extPdFirstName;
	private String extPdLastName;
	
	private String intGrantContractNum;
	private String intPiInstitution;
	private String intPiEmailAddress;
	private String intPiFirstName;
	private String intPiLastName;
	private Long intPdNpnId;
	private String intPdFirstName;
	private String intPdLastName;
	
	private Long repoCount;
	private Long subprojectCount;
	private boolean expandSubproject = false;
	private boolean expandRepository = false;
	
	public Submission() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDocAbbreviation() {
		return this.docAbbreviation;
	}

	public void setDocAbbreviation(String docAbbreviation) {
		this.docAbbreviation = docAbbreviation;
	}

	public String getParentAccessionNum() {
		return this.parentAccessionNum;
	}

	public void setParentAccessionNum(String parentAccessionNum) {
		this.parentAccessionNum = parentAccessionNum;
	}

	public Long getVersionNum() {
		return this.versionNum;
	}

	public void setVersionNum(Long versionNum) {
		this.versionNum = versionNum;
	}

	public String getSubprojectFlag() {
		return this.subprojectFlag;
	}

	public void setSubprojectFlag(String subprojectFlag) {
		this.subprojectFlag = subprojectFlag;
	}

	public String getLatestVersionFlag() {
		return this.latestVersionFlag;
	}

	public void setLatestVersionFlag(String latestVersionFlag) {
		this.latestVersionFlag = latestVersionFlag;
	}

	public Long getProjectGroupId() {
		return this.projectGroupId;
	}

	public void setProjectGroupId(Long projectGroupId) {
		this.projectGroupId = projectGroupId;
	}

	public Long getSubmissionReasonId() {
		return this.submissionReasonId;
	}

	public void setSubmissionReasonId(Long submissionReasonId) {
		this.submissionReasonId = submissionReasonId;
	}

	public String getProjectSubmissionTitle() {
		return projectSubmissionTitle;
	}

	public void setProjectSubmissionTitle(String projectSubmissionTitle) {
		this.projectSubmissionTitle = projectSubmissionTitle;
	}

	public String getGdsPlanPageStatusCode() {
		return gdsPlanPageStatusCode;
	}

	public void setGdsPlanPageStatusCode(String gdsPlanPageStatusCode) {
		this.gdsPlanPageStatusCode = gdsPlanPageStatusCode;
	}

	public String getDataSharingExcepStatusCode() {
		return dataSharingExcepStatusCode;
	}

	public void setDataSharingExcepStatusCode(String dataSharingExcepStatusCode) {
		this.dataSharingExcepStatusCode = dataSharingExcepStatusCode;
	}

	public String getBsiPageStatusCode() {
		return bsiPageStatusCode;
	}

	public void setBsiPageStatusCode(String bsiPageStatusCode) {
		this.bsiPageStatusCode = bsiPageStatusCode;
	}

	public String getIcPageStatusCode() {
		return icPageStatusCode;
	}

	public void setIcPageStatusCode(String icPageStatusCode) {
		this.icPageStatusCode = icPageStatusCode;
	}

	public String getRepositoryPageStatusCode() {
		return repositoryPageStatusCode;
	}

	public void setRepositoryPageStatusCode(String repositoryPageStatusCode) {
		this.repositoryPageStatusCode = repositoryPageStatusCode;
	}

	public Long getRepoCount() {
		return repoCount;
	}

	public void setRepoCount(Long repoCount) {
		this.repoCount = repoCount;
	}

	public Long getSubprojectCount() {
		return subprojectCount;
	}

	public void setSubprojectCount(Long subprojectCount) {
		this.subprojectCount = subprojectCount;
	}

	public boolean isExpandSubproject() {
		return expandSubproject;
	}

	public void setExpandSubproject(boolean expandSubproject) {
		this.expandSubproject = expandSubproject;
	}

	public boolean isExpandRepository() {
		return expandRepository;
	}

	public void setExpandRepository(boolean expandRepository) {
		this.expandRepository = expandRepository;
	}
	
	public String getSubprojectEligibleFlag() {
		return this.subprojectEligibleFlag;
	}

	public void setSubprojectEligibleFlag(String subprojectEligibleFlag) {
		this.subprojectEligibleFlag = subprojectEligibleFlag;
	}
	
	public String getProjectStatusCode(){
		return projectStatusCode;
	}
	
	public void setProjectStatusCode(String projectStatusCode){
		this.projectStatusCode = projectStatusCode;
	}

	public String getExtGrantContractNum() {
		return extGrantContractNum;
	}

	public void setExtGrantContractNum(String extGrantContractNum) {
		this.extGrantContractNum = extGrantContractNum;
	}

	public String getExtPiInstitution() {
		return extPiInstitution;
	}

	public void setExtPiInstitution(String extPiInstitution) {
		this.extPiInstitution = extPiInstitution;
	}

	public String getExtPiEmailAddress() {
		return extPiEmailAddress;
	}

	public void setExtPiEmailAddress(String extPiEmailAddress) {
		this.extPiEmailAddress = extPiEmailAddress;
	}

	public String getExtPiFirstName() {
		return extPiFirstName;
	}

	public void setExtPiFirstName(String extPiFirstName) {
		this.extPiFirstName = extPiFirstName;
	}

	public String getExtPiLastName() {
		return extPiLastName;
	}

	public void setExtPiLastName(String extPiLastName) {
		this.extPiLastName = extPiLastName;
	}

	public Long getExtPdNpnId() {
		return extPdNpnId;
	}

	public void setExtPdNpnId(Long extPdNpnId) {
		this.extPdNpnId = extPdNpnId;
	}

	public String getExtPdFirstName() {
		return extPdFirstName;
	}

	public void setExtPdFirstName(String extPdFirstName) {
		this.extPdFirstName = extPdFirstName;
	}

	public String getExtPdLastName() {
		return extPdLastName;
	}

	public void setExtPdLastName(String extPdLastName) {
		this.extPdLastName = extPdLastName;
	}

	public String getIntGrantContractNum() {
		return intGrantContractNum;
	}

	public void setIntGrantContractNum(String intGrantContractNum) {
		this.intGrantContractNum = intGrantContractNum;
	}

	public String getIntPiInstitution() {
		return intPiInstitution;
	}

	public void setIntPiInstitution(String intPiInstitution) {
		this.intPiInstitution = intPiInstitution;
	}

	public String getIntPiEmailAddress() {
		return intPiEmailAddress;
	}

	public void setIntPiEmailAddress(String intPiEmailAddress) {
		this.intPiEmailAddress = intPiEmailAddress;
	}

	public String getIntPiFirstName() {
		return intPiFirstName;
	}

	public void setIntPiFirstName(String intPiFirstName) {
		this.intPiFirstName = intPiFirstName;
	}

	public String getIntPiLastName() {
		return intPiLastName;
	}

	public void setIntPiLastName(String intPiLastName) {
		this.intPiLastName = intPiLastName;
	}

	public Long getIntPdNpnId() {
		return intPdNpnId;
	}

	public void setIntPdNpnId(Long intPdNpnId) {
		this.intPdNpnId = intPdNpnId;
	}

	public String getIntPdFirstName() {
		return intPdFirstName;
	}

	public void setIntPdFirstName(String intPdFirstName) {
		this.intPdFirstName = intPdFirstName;
	}

	public String getIntPdLastName() {
		return intPdLastName;
	}

	public void setIntPdLastName(String intPdLastName) {
		this.intPdLastName = intPdLastName;
	}
}

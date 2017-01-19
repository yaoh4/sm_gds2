package gov.nih.nci.cbiit.scimgmt.gds.model;


public class RoleSearchCriteria {

	
	private String lastName;
	private String firstName;
	private String roleCode;
	private String doc;
	private boolean gdsUsersOnly;
    
	public RoleSearchCriteria(){}

	public RoleSearchCriteria(String lastName, String firstName, 
			String roleCode, String doc) {
		this.lastName = firstName;
		this.firstName = firstName;
		this.roleCode = roleCode;
		this.doc = doc;
	}

	@Override
	public String toString() {
		return "RoleSearchCriteria [firstName=" + firstName + ", lastName=" 
				+ lastName + ", roleId=" + roleCode + ", doc=" + doc + "]";
	}
	
	/**
	 * Checks if no search criteria is provided
	 * 
	 * @return true, if is blank
	 */
	/*public boolean isBlank() {
		return (StringUtils.isBlank(roleCode) && doc == null
				&& StringUtils.isBlank(firstName) && StringUtils.isBlank(lastName));
	}*/

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the roleId
	 */
	public String getRoleCode() {
		return roleCode;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	/**
	 * @return the doc
	 */
	public String getDoc() {
		return doc;
	}

	/**
	 * @param doc the doc to set
	 */
	public void setDoc(String doc) {
		this.doc = doc;
	}

	/**
	 * @return the gdsUserOnly
	 */
	public boolean getGdsUsersOnly() {
		return gdsUsersOnly;
	}

	/**
	 * @param gdsUsersOnly the gdsUserOnly to set
	 */
	public void setGdsUsersOnly(boolean gdsUsersOnly) {
		this.gdsUsersOnly = gdsUsersOnly;
	}

}

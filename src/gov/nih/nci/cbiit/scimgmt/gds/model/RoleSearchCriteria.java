package gov.nih.nci.cbiit.scimgmt.gds.model;

import org.apache.commons.lang3.StringUtils;


public class RoleSearchCriteria {

	
	private String lastName;
	private String firstName;
	private Long roleId;
	private String doc;
    
	public RoleSearchCriteria(){}

	public RoleSearchCriteria(String lastName, String firstName, 
			Long roleId, String doc) {
		this.lastName = firstName;
		this.firstName = firstName;
		this.roleId = roleId;
		this.doc = doc;
	}

	@Override
	public String toString() {
		return "RoleSearchCriteria [firstName=" + firstName + ", lastName=" 
				+ lastName + ", roleId=" + roleId + ", doc=" + doc + "]";
	}
	
	/**
	 * Checks if no search criteria is provided
	 * 
	 * @return true, if is blank
	 */
	public boolean isBlank() {
		return (roleId == null && doc == null
				&& StringUtils.isBlank(firstName) && StringUtils.isBlank(lastName));
	}

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
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
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

}

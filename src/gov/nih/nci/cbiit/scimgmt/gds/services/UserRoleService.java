package gov.nih.nci.cbiit.scimgmt.gds.services;

import java.util.List;

import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PersonRole;
import gov.nih.nci.cbiit.scimgmt.gds.domain.UserRole;
import gov.nih.nci.cbiit.scimgmt.gds.model.RoleSearchCriteria;


/**
 * Service for user roles.
 * @author tembharend
 */
public interface UserRoleService {

	/**
	 * Find NedPerson by user id.
	 * 
	 * @param userId
	 * @return NedPerson
	 * @throws Exception 
	 */
	public abstract NedPerson findNedPersonByUserId(final String userId) throws Exception;

	
	/**
	 * Find UserRole by user id.
	 * 
	 * @param userId
	 * @return UserRole
	 * @throws Exception 
	 */
	public UserRole findUserRoleByUserId(final String userId);
	
	
	/**
	 * Find PersonRole by userId. 
	 * @return PersonRole
	 */
	public PersonRole findPersonRoleByUserId(String userId);
	
	/**
	 * This method retrieves list of UserRoles matching the 
	 * given criteria. 
	 * 
	 * @param searchCriteria
	 * @return criteria
	 */
	public abstract List<UserRole> searchUserRole(RoleSearchCriteria searchCriteria); 

	
	/**
	 * This method retrieves List of NedPersons matching the 
	 * given criteria. 
	 * 
	 * @param searchCriteria
	 * @return criteria
	 * 
	 */
	public abstract List<NedPerson> searchNedPerson(RoleSearchCriteria searchCriteria);
	
	
	/**
	 * Inserts or Updates the PersonRole
	 * 
	 * @param personRole
	 * @return saved PersonRole
	 */
	public PersonRole saveOrUpdatePersonRole(PersonRole personRole);
	
	
	/**
	 * Deletes a PersonRole from the DB 
	 * 
	 * @param networkId
	 * @return boolean
	 */
	public boolean deletePersonRole(String networkId);
}
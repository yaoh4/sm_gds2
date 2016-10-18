package gov.nih.nci.cbiit.scimgmt.gds.services;

import java.util.List;

import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PersonRole;
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
	 * Find PersonRole by user id.
	 * 
	 * @param userId
	 * @return PersonRole
	 * @throws Exception 
	 */
	public PersonRole findPersonRoleByUserId(final String userId);
	
	
	/**
	 * This method retrieves list of PersonRoles matching the 
	 * given criteria. 
	 * 
	 * @param searchCriteria
	 * @return criteria
	 */
	public abstract List<PersonRole> searchPersonRole(RoleSearchCriteria searchCriteria); 

	
	/**
	 * This method retrieves List of NedPersons matching the 
	 * given criteria. 
	 * 
	 * @param searchCriteria
	 * @return criteria
	 * 
	 */
	public abstract List<NedPerson> searchNedPerson(RoleSearchCriteria searchCriteria); 
}
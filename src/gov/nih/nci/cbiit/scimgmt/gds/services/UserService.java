package gov.nih.nci.cbiit.scimgmt.gds.services;

import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;

public interface UserService {

	/**
	 * Find NedPerson by user id.
	 * 
	 * @param userId
	 * @return NedPerson
	 * @throws Exception 
	 */
	public abstract NedPerson findNedPersonByUserId(final String userId) throws Exception;

}
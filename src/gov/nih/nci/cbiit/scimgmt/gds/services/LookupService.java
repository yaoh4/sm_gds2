package gov.nih.nci.cbiit.scimgmt.gds.services;


import gov.nih.nci.cbiit.scimgmt.gds.domain.LookupT;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PropertiesT;

import java.util.List;

/**
 * @author menons2
 *
 */
public interface LookupService {
	
	/**
	 * Retrieves the lookup list from the cache if present,
	 * else retrieves from the database
	 * @param listName
	 * @return
	 */
	public List<LookupT> getLookupList (String listName);
	
	
	/**
	 * Update the given list in the cache.
	 * @param listname
	 */
	public List<LookupT> updateLookupList(String listName, List<LookupT> lookupList);
	
	
	/**
	 * Loads the lookup lists from the DB and stores in
	 * cache. Invoked during application initialization.
	 */
	public void loadLookupLists();
	
	
	/**
	 * Retrieves the properties from DB. Invoked during
	 * application initialization
	 */
	public List<PropertiesT> loadPropertiesList();
}

package gov.nih.nci.cbiit.scimgmt.gds.services;

import java.util.List;

/**
 * @author menons2
 *
 */
public interface LookupService {
	
	/**
	 * Retrieves the list from the cache if present,
	 * else retrieves from the database
	 * @param listName
	 * @return
	 */
	public List<? extends Object> getList (String listName);
	
	
	/**
	 * Clear the given list from the cache.
	 * @param listname
	 */
	public void clearList(String listname);
	
}

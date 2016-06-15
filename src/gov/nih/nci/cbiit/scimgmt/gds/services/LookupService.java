package gov.nih.nci.cbiit.scimgmt.gds.services;


import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanQuestionsAnswer;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Property;

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
	public List<Lookup> getLookupList (String listName);
	
	
	/**
	 * Update the given list in the cache.
	 * @param listname
	 */
	public List<Lookup> updateLookupList(String listName, List<Lookup> lookupList);
	
	
	/**
	 * Loads the lookup lists from the DB and stores in
	 * cache. Invoked during application initialization.
	 */
	public List<Lookup> getAllLookupLists();
	
	
	/**
	 * Retrieves the properties from DB. Invoked during
	 * application initialization
	 */
	public List<Property> loadPropertiesList();
	
	/**
	 * Loads the static GDS Plan questions and answers from the DB.
	 * Invoked during application initialization.
	 */
	public List<PlanQuestionsAnswer> getAllPlanQuestionsAnswers();
	
	/**
	 * Get Lookup object by list name and code
	 * 
	 * @param listName
	 * @param code
	 * @return
	 */
	public Lookup getLookupByCode(String listName, String code);
	
}

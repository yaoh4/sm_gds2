package gov.nih.nci.cbiit.scimgmt.gds.services;


import gov.nih.nci.cbiit.scimgmt.gds.domain.DulChecklist;
import gov.nih.nci.cbiit.scimgmt.gds.domain.GdsPd;
import gov.nih.nci.cbiit.scimgmt.gds.domain.HelpText;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Organization;
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
	 *  Get docList.  
	 *  Retrieve from the cache if present, else from the DB
	 */
	public List<?> getDocList(String docList);	
	
	/**
	 * Update the given docList in the cache.
	 * @param docList
	 * @param lookupList
	 * @return
	 */
	public List<Organization> updateDocList(String docList, List<Organization> updatedDocList);
	
	
	/**
	 * Fetch the static DUL display text from DUL_CHECKLIST_T to store 
	 * in cache. Invoked during application initialization.
	 */
	public List<DulChecklist> getDulChecklists(String dulChecklistKey);
	 
	 
	 /**
	  * Update the dulChecklists in the cache.
	  * @param dulChecklists
	  * @return
	  */
	 public List<DulChecklist> updateDulChecklists(String dulChecklistKey, List<DulChecklist> dulChecklists);
	
	 /**
	  * Get pdList. Retrieve from the cache if present, else from the DB
	  */
	 public List<GdsPd> getPdList(String pdListKey);
	 
	 
	 /**
	  * Load helplist from DB and store in cache
	  */
	 public void loadHelpList(String helpListKey);
	 
	 
	 /**
	  * Get helpList. Retrieve from the cache if present, else from the DB
	  */
	 public List<HelpText> getHelpList(String helpListKey);
	 
}

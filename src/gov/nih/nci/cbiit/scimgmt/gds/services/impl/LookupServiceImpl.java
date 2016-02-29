package gov.nih.nci.cbiit.scimgmt.gds.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

import gov.nih.nci.cbiit.scimgmt.gds.dao.PropertyListDao;
import gov.nih.nci.cbiit.scimgmt.gds.domain.LookupT;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PropertiesT;
import gov.nih.nci.cbiit.scimgmt.gds.services.LookupService;

/**
 * Class to support retrieval and caching of lookup data from the database.
 * 
 * @author menons2
 *
 */
@Component
@EnableCaching
@CacheConfig(cacheNames = "lookupLists")
public class LookupServiceImpl implements LookupService {
	
	
	private static final Logger logger = LogManager.getLogger(LookupServiceImpl.class);
	
	@Autowired
	private PropertyListDao propertyListDAO;
	
	
	/**
	 * Get lookup list for a given list name. Retrieve 
	 * from the cache if present, else from the DB
	 */
	@Cacheable(key = "#listName")
	public List<LookupT> getLookupList(String listName) {
	  	
		logger.info("Loading Lookup list from DB");
		return propertyListDAO.searchLookup(listName);
	  	
	}
	
	
	
	/**
	 * Loads the lookup lists from the DB and stores in
	 * cache. Invoked during application initialization.
	 */
	public void loadLookupLists() {
		
		String listName = "";
		String prevListName = "";
		List<LookupT> lookupList = new ArrayList();
		
		logger.info("Loading lookup data from LOOKUP_T");
		List<LookupT> allLookups = propertyListDAO.getAllLookupLists();
		
		for(LookupT appLookupT: allLookups) {
			listName = appLookupT.getDisplayName();
			if(!prevListName.isEmpty() && !prevListName.equalsIgnoreCase(listName)) {
				
				//Put this list in the cache
				updateLookupList(listName, lookupList);
				
				prevListName = listName;
				
				//Setup the next list
				lookupList = new ArrayList();
				
			}
			lookupList.add(appLookupT);
			
		}
		if(!lookupList.isEmpty()) {
			updateLookupList(listName, lookupList);
		}
	}


	/**
	 * Update the given lookup list in the cache.
	 * @param listName
	 * @param lookupList
	 * @return
	 */
	@CachePut(key="#listName")
	public List<LookupT> updateLookupList(String listName, List<LookupT> lookupList) {
		return lookupList;
	}
	
	
	/**
	 * Retrieves the properties from DB. Invoked during
	 * application initialization and for reloading
	 * from sysAdmin action.
	 */
	public List<PropertiesT> loadPropertiesList() {
		
		logger.info("Loading Properties list from DB");
		return propertyListDAO.getPropertiesList();
	}

}

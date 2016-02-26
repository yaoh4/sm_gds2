package gov.nih.nci.cbiit.scimgmt.gds.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

import gov.nih.nci.cbiit.scimgmt.gds.dao.PropertyListDao;
import gov.nih.nci.cbiit.scimgmt.gds.domain.AppLookupT;
import gov.nih.nci.cbiit.scimgmt.gds.domain.AppPropertiesT;
import gov.nih.nci.cbiit.scimgmt.gds.services.LookupService;

/**
 * Class to support retrieval and caching of lookup data from the database.
 * 
 * @author menons2
 *
 */
@Component
@EnableCaching
public class LookupServiceImpl implements LookupService {
	
	
	private static final Logger logger = LogManager.getLogger(LookupServiceImpl.class);
	
	@Autowired
	private PropertyListDao propertyListDAO;

	
	/**
	 * Get lookup list for a given discriminator. Retrieve 
	 * from the cache if present, else from the DB
	 */
	@Cacheable(cacheNames="lookupLists", key = "#listName")
	public List<AppLookupT> getLookupList(String listName) {
	  	
		logger.info("Loading Lookup list from DB");
		return propertyListDAO.searchLookup(listName);
	  	
	}
	
	
	
	/**
	 * Loads the lookup lists from the DB and stores in
	 * cache. Invoked during application initialization.
	 */
	public void loadLookupLists() {
		
		String prevDiscriminator = "";
		List<AppLookupT> lookupList = null;
		List<AppLookupT> allLookups = propertyListDAO.getAllLookupLists();
		
		for(AppLookupT appLookupT: allLookups) {
			String discriminator = appLookupT.getDiscriminator();
			if(!prevDiscriminator.equalsIgnoreCase(discriminator)) {
				
				//Put this list in the cache
				updateLookupList(discriminator, lookupList);
				
				prevDiscriminator = discriminator;
				
				//Setup the next list
				lookupList = new ArrayList();
				
			}
			lookupList.add(appLookupT);
			
		}
	}


	/**
	 * Update the given lookup list in the cache.
	 * @param listName
	 * @param lookupList
	 * @return
	 */
	@CachePut(cacheNames="lookupLists", key="#listName")
	public List<AppLookupT> updateLookupList(String listName, List<AppLookupT> lookupList) {
		return lookupList;
	}
	
	
	/**
	 * Retrieves the properties from DB. Invoked during
	 * application initialization and for reloading
	 * from sysAdmin action.
	 */
	public List<AppPropertiesT> loadPropertiesList() {
		
		logger.info("Loading Properties list from DB");
		return propertyListDAO.getPropertiesList();
	}

}

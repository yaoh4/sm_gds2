package gov.nih.nci.cbiit.scimgmt.gds.services.impl;

import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

import gov.nih.nci.cbiit.scimgmt.gds.dao.PropertyListDao;
import gov.nih.nci.cbiit.scimgmt.gds.domain.DulChecklist;
import gov.nih.nci.cbiit.scimgmt.gds.domain.GdsPd;
import gov.nih.nci.cbiit.scimgmt.gds.domain.HelpText;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Organization;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanQuestionsAnswer;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Property;
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
	public List<Lookup> getLookupList(String listName) {
	  	
		logger.info("Loading Lookup list from DB for listName " + listName);
		return propertyListDAO.searchLookup(listName);
	  	
	}
	
	
	
	/**
	 * Fetch entire lookup lists from the DB and the caller stores in
	 * cache. Invoked only during application initialization and
	 * for reloading from sysAdmin action.
	 */
	@CacheEvict(cacheNames="lookupLists", allEntries=true)
	public List<Lookup> getAllLookupLists() {
		
		logger.info("Loading all lookup data from DB");
		List<Lookup> allLookups = propertyListDAO.getAllLookupLists();
		return allLookups;
	}


	/**
	 * Update the given lookup list in the cache.
	 * @param listName
	 * @param lookupList
	 * @return
	 */
	@CachePut(key="#listName")
	public List<Lookup> updateLookupList(String listName, List<Lookup> lookupList) {
		return lookupList;
	}
	
	
	/**
	 * Retrieves the properties from DB. Invoked only 
	 * during application initialization and for 
	 * reloading from sysAdmin action.
	 */
	public List<Property> loadPropertiesList() {
		
		logger.info("Loading Properties list from DB");
		return propertyListDAO.getPropertiesList();
	}

	/**
	 * Fetch entire lookup lists from the DB and the caller stores in
	 * cache. Invoked only during application initialization
	 * and for reloading from sysAdmin action.
	 */
	public List<PlanQuestionsAnswer> getAllPlanQuestionsAnswers() {
		
		logger.info("Loading all PlanQuestionsAnswers from DB");
		List<PlanQuestionsAnswer> allPlanQuestionsAnswers = propertyListDAO.getAllPlanQuestionsAnswers();
		return allPlanQuestionsAnswers;
	}
	
	
	/**
	 *  Get docList.  
	 *  Retrieve from the cache if present, else from the DB
	 */
	@Cacheable(key = "#docList")
	public List<Organization> getDocList(String docList) {
	  	
		logger.info("Loading docList from DB");
		return propertyListDAO.getDocList(docList);	  	
	}
	
	/**
	 * Update the given docList in the cache.
	 * @param docList
	 * @param lookupList
	 * @return
	 */
	@CachePut(key="#docList")
	public List<Organization> updateDocList(String docList, List<Organization> updatedDocList) {
		return updatedDocList;
	}
	
	/**
	 * Fetch the static DUL display text from DUL_CHECKLIST_T to store 
	 * in cache. Invoked only during application initialization.
	 */
	@Cacheable(key = "#dulChecklistKey")
	public List<DulChecklist> getDulChecklists(String dulChecklistKey) {
		
		logger.info("Loading static DUL display text from DB");
		List<DulChecklist> dulChecklists = propertyListDAO.getAllDulChecklists();
		return dulChecklists;
	}
	 
	 
	 /**
	  * Update the dulChecklists in the cache.
	  * @param dulChecklists
	  * @return
	  */
	@CachePut(key = "#dulChecklistKey")
	 public List<DulChecklist> updateDulChecklists(String dulChecklistKey, List<DulChecklist> dulChecklists) {
		 return dulChecklists;
	 }
	
	
	/**
	 *  Get pdList.  
	 *  Retrieve from the cache if present, else from the DB
	 */
	@Cacheable(key = "#pdListKey")
	public List<GdsPd> getPdList(String pdListKey) {
	  	
		logger.info("Loading pdList from DB");
		List<GdsPd> pdList = propertyListDAO.getPdList();
		for(GdsPd pd: pdList) {
			pd.setPdFullNameDescrip(WordUtils.capitalizeFully(pd.getPdFullNameDescrip()));
			pd.setFirstName(WordUtils.capitalizeFully(pd.getFirstName()));
			pd.setLastName(WordUtils.capitalizeFully(pd.getLastName()));
		}
		return updatePdList(pdListKey, pdList);	  	
	}
	
	/**
	 * Update the given pdList in the cache.
	 * @param pdList
	 * @return
	 */
	@CachePut(key="#pdListKey")
	public List<GdsPd> updatePdList(String pdListKey, List<GdsPd> pdList) {
		return pdList;
	}
	
	
	
	/**
	 *  Retrieve help list from the DB and store in cache
	 *  Invoked only from sysAdmin if required to refresh
	 */
	public void loadHelpList(String helpListKey) {
	  	
		logger.info("Loading helpList from DB");
		List<HelpText> helpList = propertyListDAO.getHelpList();
		updateHelpList(helpListKey, helpList);	  	
	}
	
	/**
	 *  Get pdList.  
	 *  Retrieve from the cache if present, else from the DB
	 */
	@Cacheable(key = "#helpListKey")
	public List<HelpText> getHelpList(String helpListKey) {
	  	
		logger.info("Loading helpList from DB");
		return propertyListDAO.getHelpList();	  	
	}
	
	/**
	 * Update the given helpList in the cache.
	 * @param helpList
	 * @return
	 */
	@CachePut(key="#helpListKey")
	public List<HelpText> updateHelpList(String helpListKey, List<HelpText> helpList) {
		return helpList;
	}
	
	
	
}

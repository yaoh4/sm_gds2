package gov.nih.nci.cbiit.scimgmt.gds.services.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;

import gov.nih.nci.cbiit.scimgmt.gds.dao.PropertyListDAO;
import gov.nih.nci.cbiit.scimgmt.gds.services.LookupService;

/**
 * Class to support retrieval and caching of lookup data from the database.
 * 
 * @author menons2
 *
 */
@EnableCaching
public class LookupServiceImpl implements LookupService {
	
	
	private static final Logger logger = LogManager.getLogger(LookupServiceImpl.class);
	
	@Autowired
	private PropertyListDAO propertyListDAO;

	
	@Cacheable(key = "#listName")
	public List< ? extends Object> getList(String listName) {
	  	
	  	return search(listName);
	  	
	}
	
	
	@CacheEvict
	public void clearList(String listName) {
		
		//Do nothing, invocation of this method will cause
		//the cache to be cleared.
	}
	
	
	/**
	 * Retrieve the lookup list from DB
	 * 
	 * @param listName
	 * @return
	 */
	private List<? extends Object> search(String listName) {
		
		return propertyListDAO.retrieve(listName);
	}


}

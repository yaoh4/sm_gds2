package gov.nih.nci.cbiit.scimgmt.gds.util;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Property;
import gov.nih.nci.cbiit.scimgmt.gds.services.LookupService;



/**
 * Loads the properties into memory from the file system. 
 * @author menons2
 *
 */

@SuppressWarnings("serial")
@Component
@Scope("singleton")
public class GdsProperties extends Properties {
	
	private static final Logger logger = LogManager.getLogger(GdsProperties.class);
	
	@Autowired
	LookupService lookupService;

	@EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
		//Load properties from files
		try {
			this.load(this.getClass().getResourceAsStream("/messages.properties"));

			String confDirLocation = System.getProperty("conf.dir");
			logger.info("=====> conf.dir=" + confDirLocation);
			this.load(new FileInputStream(confDirLocation + "/gds/application.properties"));
			this.load(new FileInputStream(confDirLocation + "/gds/gds.properties"));

			//Override with properties from DB when present
			//TBD - Is it required to do a null check ?
			for (Property a : lookupService.loadPropertiesList()) {
				setProperty(a.getPropKey(), a.getPropValue());
			}

			//Load lookup data from DB
			loadLookupLists();
			logger.info("Completed loading GdsProperties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads the lookup lists from the DB and stores in
	 * cache. Invoked during application initialization.
	 */
	public void loadLookupLists() {
		
		String listName = "";
		String prevListName = "";
		List<Lookup> lookupList = new ArrayList();
		
		logger.info("Loading lookup data from LOOKUP_T");
		List<Lookup> allLookups = lookupService.getAllLookupLists();
		
		for(Lookup lookup: allLookups) {
			listName = lookup.getDiscriminator();
			if(!prevListName.isEmpty() && !prevListName.equalsIgnoreCase(listName)) {
				
				//Put this list in the cache
				lookupService.updateLookupList(prevListName, lookupList);
				
				prevListName = listName;
				
				//Setup the next list
				lookupList = new ArrayList();
				
			}
			prevListName = lookup.getDiscriminator();
			lookupList.add(lookup);
		}
		if(!lookupList.isEmpty()) {
			lookupService.updateLookupList(listName, lookupList);
		}
		
		lookupService.loadHelpList(ApplicationConstants.HELP_LIST);
	}
}

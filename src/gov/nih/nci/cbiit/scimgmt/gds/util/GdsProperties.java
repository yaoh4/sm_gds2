package gov.nih.nci.cbiit.scimgmt.gds.util;


import gov.nih.nci.cbiit.scimgmt.gds.domain.AppPropertiesT;
import gov.nih.nci.cbiit.scimgmt.gds.services.LookupService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;



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
	

	@PostConstruct
	public void init() throws FileNotFoundException, IOException {
		
		//Load properties from files
		this.load(this.getClass().getResourceAsStream("/application.properties"));
		this.load(this.getClass().getResourceAsStream("/messages.properties"));		
					
		String confDirLocation = System.getProperty("conf.dir");
		logger.info("=====> conf.dir=" + confDirLocation);			
		this.load(new FileInputStream(confDirLocation + "/gds/gds.properties"));
			
		
		//Override with properties from DB when present
		//TBD - Is it required to do a null check ?
		for (AppPropertiesT a : lookupService.loadPropertiesList()) {
			setProperty(a.getId().getPropKey(), a.getPropValue());
		}
		
		//Load lookup data from DB
		lookupService.loadLookupLists();
		
		logger.info("Completed loading GdsProperties");
	}
	
}

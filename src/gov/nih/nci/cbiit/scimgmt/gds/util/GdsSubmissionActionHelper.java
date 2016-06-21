package gov.nih.nci.cbiit.scimgmt.gds.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.xml.sax.SAXException;

import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.services.LookupService;


public class GdsSubmissionActionHelper {
	
	private static final Logger logger = LogManager.getLogger(GdsSubmissionActionHelper.class);
	
	@Autowired
	protected LookupService lookupService;
	
	private static GdsSubmissionActionHelper instance;
   	private static boolean loaded = false;
	
   	
   	public void init() throws IOException, SAXException {
		if (!loaded) {
			instance = this;
			loaded = true;
		}
		BeanUtilsBean.getInstance().getConvertUtils().register(false, true, -1);
	}
   	
   	
   	/**
	 * Gets the single instance of PrfAppUtil.
	 * 
	 * @return single instance of PrfAppUtil
	 */
	public static GdsSubmissionActionHelper getInstance() {
		return instance;
	}
	
	
	/**
	 * This method populates StatusDropDownLists from lookUpList
	 * @param dropDownList
	 * @param lookupList
	 */
	public static void populateStatusDropDownLists(List<DropDownOption> dropDownList, List<Lookup> lookupList){
		
		for(Lookup lookup : lookupList ){
			DropDownOption option = new DropDownOption(lookup.getId().toString(), lookup.getDescription());
			dropDownList.add(option);
		}
	}
	
	/**
	 * This method populates DocDropDownList from Object list
	 * @param dropDownList
	 * @param lookupList
	 */
	public static void populateDocDropDownList(List<DropDownOption> dropDownList, List<Object> docList){
		
		for(Object lookup : docList ){
			DropDownOption option = new DropDownOption();
			//DropDownOption option = new DropDownOption(lookup.getId().toString(), lookup.getDescription());
			dropDownList.add(option);
		}
	}
	
	
	/**
	 * Get the Lookup list for the given list name and prepare
	 * the dropDown list.
	 * 
	 * @param listName
	 * @return
	 */
	public static List<DropDownOption> getLookupDropDownList(String listName) {
		List<DropDownOption> dropDownList = new ArrayList<DropDownOption>();
		List<Lookup> lookupList = getInstance().lookupService.getLookupList(listName);
		if(!CollectionUtils.isEmpty(lookupList)) {
			for(Lookup lookup: lookupList) {
				DropDownOption option = new DropDownOption(lookup.getId().toString(), lookup.getDisplayName());
				dropDownList.add(option);
			}	
		}
		
		return dropDownList;
		
	}

}

package gov.nih.nci.cbiit.scimgmt.gds.util;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;

public class GdsSubmissionActionHelper {
	
	private static final Logger logger = LogManager.getLogger(GdsSubmissionActionHelper.class);
	
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

}

package gov.nih.nci.cbiit.scimgmt.gds.util;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DropDownOption implements Serializable{
	private String optionKey;
	private String optionValue;
	
	public DropDownOption(){}
	
	public DropDownOption(String key, String value){
		this.optionKey = key;
		this.optionValue = value;
	}
	
	public String getOptionKey() {
		return optionKey;
	}
	public void setOptionKey(String optionKey) {
		this.optionKey = optionKey;
	}
	public String getOptionValue() {
		return optionValue;
	}
	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}
	
	
}

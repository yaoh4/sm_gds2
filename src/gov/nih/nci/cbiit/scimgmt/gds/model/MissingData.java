package gov.nih.nci.cbiit.scimgmt.gds.model;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;

public class MissingData {

	
	private String data;
	private String displayText;
	private Lookup page;
	
	private List<MissingData> childList = new ArrayList<MissingData>();
	
	public MissingData() {
		
	}
	
	public MissingData(String displayText) {
		this.displayText = displayText;
	}
	
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	/**
	 * @return the displayText
	 */
	public String getDisplayText() {
		return displayText;
	}
	/**
	 * @param displayText the displayText to set
	 */
	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}
	
	
	/**
	 * @return the page
	 */
	public Lookup getPage() {
		return page;
	}
	/**
	 * @param page the page to set
	 */
	public void setPage(Lookup page) {
		this.page = page;
	}
	
	
	public List<MissingData> getChildList() {
		return childList;
	}
	
	public void setChildList(List<MissingData> childList) {
		this.childList = childList;
	}
	
	public void addChild(MissingData missingData) {
		this.childList.add(missingData);
	}
	
	public void addChildren(List<MissingData> missingDataList) {
		this.childList.addAll(missingDataList);
	}
	
}

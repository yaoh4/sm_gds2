package gov.nih.nci.cbiit.scimgmt.gds.model;

import java.util.List;

public class MissingData {

	private String data;
	private List<MissingData> childList;
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public List<MissingData> getChildList() {
		return childList;
	}
	public void setChildList(List<MissingData> childList) {
		this.childList = childList;
	}

}

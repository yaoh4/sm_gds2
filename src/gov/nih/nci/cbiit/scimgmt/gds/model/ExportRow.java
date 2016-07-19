package gov.nih.nci.cbiit.scimgmt.gds.model;

import java.util.List;

public class ExportRow {

	private List<String> row;
	
	private boolean header = false;
	
	private boolean group = false;
	
	public List<String> getRow() {
		return row;
	}
	public void setRow(List<String> row) {
		this.row = row;
	}
	public boolean isHeader() {
		return header;
	}
	public void setHeader(boolean header) {
		this.header = header;
	}
	public boolean isGroup() {
		return group;
	}
	public void setGroup(boolean group) {
		this.group = group;
	}

}

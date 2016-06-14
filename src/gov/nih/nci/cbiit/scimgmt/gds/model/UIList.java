package gov.nih.nci.cbiit.scimgmt.gds.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class UIList {
	private List<UIElement> list = new ArrayList<UIElement>();
	private String style;

	public List<UIElement> getList() {
		return list;
	}

	public void setList(List<UIElement> list) {
		this.list = list;
	}
	
	public String getParameters() {
		final StringBuffer sb = new StringBuffer("");
		for(UIElement e: list) {
			if(sb.length() > 0) {
				sb.append(",");
			}
			sb.append("'" + e.getValue() + "','" + e.getElementId() + "','" + e.getOperation() + "'");
		}
		return sb.toString();
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
}

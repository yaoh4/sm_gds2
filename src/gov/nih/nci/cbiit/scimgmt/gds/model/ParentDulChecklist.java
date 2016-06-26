/**
 * 
 */
package gov.nih.nci.cbiit.scimgmt.gds.model;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.cbiit.scimgmt.gds.domain.DulChecklist;

/**
 * @author menons2
 *
 */
public class ParentDulChecklist extends DulChecklist {
	
	private List<DulChecklist> dulChecklists = new ArrayList<DulChecklist>();

	
	/**
	 * Constructor
	 * @param dulChecklist
	 */
	public ParentDulChecklist(DulChecklist dulChecklist) {
		this.setId(dulChecklist.getId());
		this.setDisplayText(dulChecklist.getDisplayText());
		this.setDisplayOrderNum(dulChecklist.getDisplayOrderNum());
	}
	
	/**
	 * @return the dulChecklists
	 */
	public List<DulChecklist> getDulChecklists() {
		return dulChecklists;
	}

	/**
	 * @param dulChecklists the dulChecklists to set
	 */
	public void setDulChecklists(List<DulChecklist> dulChecklists) {
		this.dulChecklists = dulChecklists;
	}
	
	public void addDulChecklist(DulChecklist dulChecklist) {
		this.dulChecklists.add(dulChecklist);
	}
	
}

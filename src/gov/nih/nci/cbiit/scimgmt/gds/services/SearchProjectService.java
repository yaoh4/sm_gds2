package gov.nih.nci.cbiit.scimgmt.gds.services;

import java.util.List;

import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;

public interface SearchProjectService {

	public List<Long> getAllProjectIds();
	public Project findProjectById(Long projectId);
	public List<Object> getIntramuralGrantOrContractList();

}

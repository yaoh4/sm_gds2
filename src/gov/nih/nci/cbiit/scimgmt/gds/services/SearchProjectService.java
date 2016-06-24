package gov.nih.nci.cbiit.scimgmt.gds.services;

import java.util.List;

import gov.nih.nci.cbiit.scimgmt.gds.domain.GdsGrantsContracts;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;

public interface SearchProjectService {

	public List<Long> getAllProjectIds();
	public Project findProjectById(Long projectId);
	public List<GdsGrantsContracts> getGrantOrContractList(String grantContractNum);
	public GdsGrantsContracts getGrantOrContract(Long applId);

}

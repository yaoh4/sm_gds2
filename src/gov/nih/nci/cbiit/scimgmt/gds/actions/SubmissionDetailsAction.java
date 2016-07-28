package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.util.HashMap;
import java.util.List;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.InstitutionalCertification;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;

/**
 * @author menons2
 *
 */
@SuppressWarnings("serial")
public class SubmissionDetailsAction extends ManageSubmission {
	
	private List<Document> bsiFile;
	/**
	 * Opens Grants Contracts Search page.
	 * 
	 * @return forward string
	 */
	public String execute() throws Exception {
		
		Project project = retrieveSelectedProject();
		
		//Load ICs
		List<InstitutionalCertification> certs  = project.getInstitutionalCertifications();
		HashMap<Long, InstitutionalCertification> map = new HashMap<Long, InstitutionalCertification>();
		
		if(certs != null && !certs.isEmpty()) {
			List<Document> docs = 
					fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_IC, retrieveSelectedProject().getId());
				    
			for(InstitutionalCertification cert: certs) {
				for(Document doc: docs) {
					if(doc.getInstitutionalCertificationId() != null && 
							doc.getInstitutionalCertificationId().equals(cert.getId())) {
						cert.addDocument(doc);
						break;
					}
				}		
			}
		}
		  
		setProject(project);
		
		//Load general info
		loadGrantInfo();
		
		//Load BSI file(s)
		setBsiFile(fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_BSI, new Long(getProjectId())));
		
		return SUCCESS;
	}
	/**
	 * @return the bsiFile
	 */
	public List<Document> getBsiFile() {
		return bsiFile;
	}
	/**
	 * @param bsiFile the bsiFile to set
	 */
	public void setBsiFile(List<Document> bsiFile) {
		this.bsiFile = bsiFile;
	}
	
}

package gov.nih.nci.cbiit.scimgmt.gds.actions;


import java.util.List;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.InstitutionalCertification;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Study;
import org.springframework.util.CollectionUtils;


/**
 * @author gantam2
 *
 */
@SuppressWarnings("serial")
public class SubmissionStudyListAction extends ManageSubmission  {
	
	
	public String execute() throws Exception {
		return getStudiesList();
	}

	 public String getStudiesList() {
		 
		 Project storedProject = retrieveSelectedProject();
		 Long displayProjectId = storedProject.getId();
		 List<Study> studies = storedProject.getStudies();
			
			if(ApplicationConstants.FLAG_YES.equalsIgnoreCase(storedProject.getSubprojectFlag())) {
				displayProjectId = storedProject.getParentProjectId();
				studies = retrieveParentProject().getStudies();
				
			}
			storedProject.setStudies(studies);
			
			List<Document> docs = fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_IC, displayProjectId);
				
				if(docs != null && !docs.isEmpty()) {
					for(Study stu : studies) {
						if(!CollectionUtils.isEmpty(stu.getInstitutionalCertifications())) {
					       for(InstitutionalCertification ic: stu.getInstitutionalCertifications()) {
						      for(Document doc: docs) {
							      if(doc.getInstitutionalCertificationId() != null && 
									  doc.getInstitutionalCertificationId().equals(ic.getId()))
								      ic.addDocument(doc);								
						      }	
					       }
				        }
				    }
				}
				
			setProject(storedProject);
			setProjectId(storedProject.getId().toString());
		 
		 return SUCCESS;
	 }
}

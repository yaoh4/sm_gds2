/**
 * 
 */
package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.DulChecklistSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.InstitutionalCertification;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.StudiesDulSet;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Study;

import org.springframework.util.CollectionUtils;

/**
 * @author menons2
 *
 */
public class IcSubmissionAction extends ManageSubmission {

	static Logger logger = LogManager.getLogger(IcSubmissionAction.class);
	
	
	private InstitutionalCertification instCertification;
	
	private String instCertId;
	
	/**
	 * Retrieves all data associated with the specified IC and redirects the user to the
	 * Edit/Add IC page. If no IC is present, then a new one is created. Invoked from:
	 * 1. The Edit link on the Track Institutional Status (IC List) page.
	 * 2. The Add New Institutional Certificate link on the Track Institutional Status page
	 * 3. The Save and Next button on the GDS Plan page if no IC is present in the 
	 *    Submission. (else user will be directed to the Track IC Status (IC List) page.
	 * 4. The ICs tab, if no IC is present in the Submission. (if an IC is present, then 
	 *  user will be directed to the Track IC Status (IC List) page.
	 * 
	 * @return forward string Takes the user back to the submissionIc.jsp page.
	 */
	public String execute() throws Exception {
						
		logger.debug("execute");
		
		InstitutionalCertification instCertification = retrieveIC();
		if(instCertification != null) {
					
			Long docTypeId = lookupService.getLookupByCode(ApplicationConstants.DOC_TYPE, "IC").getId();
			List<Document> docs = fileUploadService.retrieveFileByDocType(docTypeId, instCertification.getProject().getId());
			if(docs != null && !docs.isEmpty()) {
				instCertification.setDocuments(new HashSet(docs));
			}
			setInstCertification(instCertification);
		} else {
			setInstCertification(new InstitutionalCertification());
		}
        
        return SUCCESS;
	}
	
		
	
	/**
	 * Retrieve the project based on the projectId indicated in the request
	 * 
	 * @return
	 */
	private InstitutionalCertification retrieveIC() {
	
		String instCertId  = getInstCertId();
		if(instCertId != null) {
			Project project = retrieveSelectedProject();
			Set<InstitutionalCertification> certs = project.getInstitutionalCertifications();
			if(CollectionUtils.isEmpty(certs)) {
				for(InstitutionalCertification cert: certs) {
					if(cert.getId().toString().equals(instCertId)) {
						return cert;
					}
				}
			}
		} 
		
		return null;
	}
	
	
	public void validateSaveIc() {
		InstitutionalCertification instCert = getInstCertification();
		
		Set<Study> icSet = instCert.getStudies();
		//Map used to keep track of duplicate DulSets
		HashMap<String, Integer> validationMap = new HashMap<String, Integer>();
		int studyIndex = 0;
		//validate the DULs in each Study
		for(Study study: icSet) {
			studyIndex++;
			int dulSetIndex = 0;
			Set<StudiesDulSet> studiesDulSets = study.getStudiesDulSets();
			for(StudiesDulSet dulSet: studiesDulSets) {
				dulSetIndex++;
				Set<DulChecklistSelection> dulChecklistSelections = dulSet.getDulChecklistSelections();
				String dulSelection = "";
				//Loop through all the selections in a set and create a unique String
				//value to represent the set based on the selections. This value will
				//be the same for sets that have identical selections
				for(DulChecklistSelection dulChecklistSelection: dulChecklistSelections) {
					dulSelection = dulSelection  + dulChecklistSelection.getDulChecklist().getId();
				}
				if(validationMap.containsKey(dulSelection)) {
					Integer dupDulSetIndex = validationMap.get(dulSelection);
					this.addActionError("Duplicate DULs in rows " + 
							dulSetIndex + " and " + dupDulSetIndex + " of Study " + studyIndex);
				} else {
					validationMap.put(dulSelection, Integer.valueOf(dulSetIndex));
				}
			}
		}
		
		if(hasActionErrors()) {
			setInstCertification(instCert);
		}
	}
	
	
	/**
	 * Saves the IC. Invoked from:
	 * 1. 'Save Institutional Certification' button on the  Add/Edit Institutional Certification
	 * 	page.
	 *   (IC List) page.
	 * 2.  
	 * @return
	 */
	public String saveIc() {
		Project project = retrieveSelectedProject();
		boolean matchFound = false;
		
		InstitutionalCertification instCert = getInstCertification();
		if(instCert.getId() != null) {
			//We need to update an existing IC in the project
			for(InstitutionalCertification cert: project.getInstitutionalCertifications()) {
				if(cert.getId().equals(instCert.getId())) {
					project.getInstitutionalCertifications().remove(cert);
					project.getInstitutionalCertifications().add(instCert);
					matchFound = true;
					break;
				}
			}
			if(!matchFound) {
				//TBD - Fatal error ??
			}
		} else {
			//The institutional certification is not in the DB, hence add it
			project.getInstitutionalCertifications().add(instCert);
		}
		saveProject(project);
		
		return SUCCESS;
	}
	
	
	
	/**
	 * Invoked for the Track IC Status page. Invoked from
	 * 1. ICs tab (if at least one IC is present in the submission (else, user will
	 * be directed to the Add IC page)
	 * 2. The Save and Next button on the Genomic Data Sharing Plan if at least one
	 *   IC is present in the Submission (else user will be directed to the Add IC page).
	 * 3. Save Study button on the Add IC Certification page.
	 * @return
	 */
	public String getIcList() {
		setProject(retrieveSelectedProject());
		
		return SUCCESS;
	}
	
	
	/**
	 * Saves the certificationComplete flag. Invoked from:
	 * 'Save' or 'Save and Next' button on the Track Institutional Certification
	 *  Status page.
	 * 
	 * @return
	 */
	public String saveICCompletion() {
		
		String certComplete = getProject().getCertificationCompleteFlag();
		
		Project project = retrieveSelectedProject();
		
		project.setCertificationCompleteFlag(certComplete);
		
		setProject(saveProject(project));		
		
		return SUCCESS;
	}
	
	
	/**
	 * Deletes an IC. Invoked when the delete button is clicked on the Track
	 * Submission Status (IC List) page
	 * 
	 * @return
	 */
	public String deleteIc() {
		Project project = retrieveSelectedProject();
		if(!CollectionUtils.isEmpty(project.getInstitutionalCertifications())) {
			for(InstitutionalCertification cert: project.getInstitutionalCertifications()) {
				if(cert.getId().equals(Long.valueOf(getInstCertId()))) {
					project.getInstitutionalCertifications().remove(cert);
					break;
				}
			}
		} else {
			//fatal error - how can we delete an IC if the project has noen
		}
		
		setProject(saveProject(project));

		
		return SUCCESS;
	}
	
	
	/**
	 * @return the instCertification
	 */
	public InstitutionalCertification getInstCertification() {
		return instCertification;
	}

	/**
	 * @param instCertification the instCertification to set
	 */
	public void setInstCertification(InstitutionalCertification instCertification) {
		this.instCertification = instCertification;
	}

	/**
	 * @return the instCertId
	 */
	public String getInstCertId() {
		return instCertId;
	}

	/**
	 * @param instCertId the instCertId to set
	 */
	public void setInstCertId(String instCertId) {
		this.instCertId = instCertId;
	}

}

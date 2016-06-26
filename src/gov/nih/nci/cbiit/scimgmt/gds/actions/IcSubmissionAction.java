/**
 * 
 */
package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.DulChecklist;
import gov.nih.nci.cbiit.scimgmt.gds.domain.DulChecklistSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.InstitutionalCertification;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.StudiesDulSet;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Study;
import gov.nih.nci.cbiit.scimgmt.gds.model.ParentDulChecklist;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper;

import org.springframework.util.CollectionUtils;

/**
 * @author menons2
 *
 */
public class IcSubmissionAction extends ManageSubmission {

	static Logger logger = LogManager.getLogger(IcSubmissionAction.class);
	
	
	private InstitutionalCertification instCertification;
	
	private String instCertId;
	
	private List<ParentDulChecklist> parentDulChecklists = new ArrayList<ParentDulChecklist>();
	
	
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
		
		//setProjectId("1");
		
		InstitutionalCertification instCert = retrieveIC();
		if(instCert != null) {
			Long docTypeId = lookupService.getLookupByCode(ApplicationConstants.DOC_TYPE, "IC").getId();
			List<Document> docs = fileUploadService.retrieveFileByDocType(docTypeId.toString(), instCertification.getProject().getId());
			if(docs != null && !docs.isEmpty()) {
				instCertification.setDocument(docs.get(0));
			}
		} else {
			instCert = new InstitutionalCertification();
			Study study = new Study();
			StudiesDulSet studiesDulSet = new StudiesDulSet();
			studiesDulSet.setStudy(study);
			study.addStudiesDulSet(studiesDulSet);
			study.setInstitutionalCertification(instCert);
			instCert.addStudy(study);
		}
        
		setInstCertification(instCert);
		prepareDisplay();
        return SUCCESS;
	}
	
	
	private void prepareDisplay() {
		setParentDulChecklists(GdsSubmissionActionHelper.getDulChecklistsSets());
				
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
		
		int studyIndex = -1;
		//validate the DULs in each Study
		for(Study study: instCert.getStudies()) {
			
			studyIndex++;
			int dulSetIndex = -1;
			
			//Map used to keep track of duplicate DulSets in a study
			HashMap<String, Integer> validationMap = new HashMap<String, Integer>();
			
			for(StudiesDulSet dulSet: study.getStudiesDulSets()) {
				dulSetIndex++;
				
				String [] parentDulId = ServletActionContext.getRequest().getParameterValues("parentDul-" + studyIndex + "-" + dulSetIndex);
				if(parentDulId == null) {
					//Error, no DUL selection made for study at index x, dulSet at index y
					this.addActionError("No DUL selection made for study at index " + studyIndex + " and DulSet at index " + dulSetIndex);
				} else {
					String [] selectedDuls = ServletActionContext.getRequest().getParameterValues("dul-" + studyIndex + "-" + dulSetIndex + "-" + parentDulId[0]);
					if(selectedDuls == null) {
						this.addActionError("No DUL selection made for study at index " + studyIndex + " and DulSet at index " + dulSetIndex);
					} else {
						
						//Represents the duls selected in a dulSet
						List<DulChecklistSelection> dulChecklistSelections = new ArrayList<DulChecklistSelection>();
						
						String dulSelections = "";
						for(int i = 0; i < selectedDuls.length; i++) {										
							
							dulSelections = dulSelections + selectedDuls[i];
							
							//for each selectedDul, create a dulChecklistSelection
							DulChecklistSelection dulChecklistSelection = new DulChecklistSelection();
							
							//get the dulChecklist with the id and attach it to this dulCheckListSelection.
							DulChecklist dulChecklist = GdsSubmissionActionHelper.getDulChecklist(Long.parseLong(selectedDuls[i]));
							dulChecklistSelection.setDulChecklist(dulChecklist);
							dulChecklistSelections.add(dulChecklistSelection);
						}
						
						//Check if this dulSet is already present
						if(validationMap.containsKey(dulSelections)) {
							Integer duplicateDulSetIndex = validationMap.get(dulSelections);
							this.addActionError("Duplicate DULs found in DulSets at index " + duplicateDulSetIndex + " and " + dulSetIndex);
						} else {
							validationMap.put(dulSelections, new Integer(dulSetIndex));
							dulSet.setDulChecklistSelections(dulChecklistSelections);
						}
					}		
				}
				//All selections in a dulSet retrieved
			}
			//All dulSets in a study scanned
		}
		//All studies in an ic scanned
		
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
	 * Add an empty study to the selected IC
	 */
	public String addStudy() {
		//Get the  currently selected IC
		InstitutionalCertification instCert = getInstCertification();
		
		Study study = new Study();
		study.setInstitutionalCertification(instCert);
		instCertification.addStudy(study);
		setInstCertification(instCertification);
		
		return SUCCESS;
	}
	
	/**
	 * Add an empty Dul to the selected Study
	 */
	public String addDul() {
		
		InstitutionalCertification instCert = getInstCertification();
		String selectedStudyIndex = ServletActionContext.getRequest().getParameter("studyIndex");
		List<Study> studies = instCert.getStudies();
		Study study = studies.get(Integer.parseInt(selectedStudyIndex));
		
		StudiesDulSet studiesDulSet = new StudiesDulSet();
		studiesDulSet.setStudy(study);
		study.addStudiesDulSet(studiesDulSet);
		instCert.setStudies(studies);
		setInstCertification(instCert);
		
		return SUCCESS;
	}
	
	/**
	 * Remove the specified study from the IC
	 * @return
	 */
	public String removeStudy() {
		
		InstitutionalCertification instCert = getInstCertification();
		String selectedStudyIndex = ServletActionContext.getRequest().getParameter("studyIndex");
		List<Study> studies = instCert.getStudies();
		
		studies.remove(selectedStudyIndex);
		instCert.setStudies(studies);
		setInstCertification(instCert);
		
		return SUCCESS;
	}
	
	/**
	 * Remove the specified DUL from a selected study on the IC
	 * @return
	 */
	public String removeDul() {
		
		InstitutionalCertification instCert = getInstCertification();
		String parentStudyIndex = ServletActionContext.getRequest().getParameter("studyIndex");
		String selectedDulIndex = ServletActionContext.getRequest().getParameter("dulIndex");
		List<Study> studies = instCert.getStudies();
		
		studies.get(Integer.parseInt(parentStudyIndex)).getStudiesDulSets().remove(selectedDulIndex);
		instCert.setStudies(studies);
		setInstCertification(instCert);
		
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


	/**
	 * @return the parentDulChecklists
	 */
	public List<ParentDulChecklist> getParentDulChecklists() {
		return parentDulChecklists;
	}


	/**
	 * @param parentDulChecklists the parentDulChecklists to set
	 */
	public void setParentDulChecklists(List<ParentDulChecklist> parentDulChecklists) {
		this.parentDulChecklists = parentDulChecklists;
	}

}

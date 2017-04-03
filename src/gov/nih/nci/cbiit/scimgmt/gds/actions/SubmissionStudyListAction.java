package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.DulChecklist;
import gov.nih.nci.cbiit.scimgmt.gds.domain.DulChecklistSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.InstitutionalCertification;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PageStatus;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.StudiesDulSet;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Study;
import gov.nih.nci.cbiit.scimgmt.gds.model.ParentDulChecklist;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsMissingDataUtil;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper;

import org.springframework.util.CollectionUtils;

public class SubmissionStudyListAction extends ManageSubmission  {
	
	 

	public String execute() throws Exception {
		return getStudiesList();
	}

	 public String getStudiesList(){
		 Project storedProject = retrieveSelectedProject();
		 Long displayProjectId = storedProject.getId();
			List<Study> studies = storedProject.getStudies();
			
			if(ApplicationConstants.FLAG_YES.equalsIgnoreCase(storedProject.getSubprojectFlag())) {
				displayProjectId = storedProject.getParentProjectId();
				studies = retrieveParentProject().getStudies();
				
			}
			storedProject.setStudies(studies);
			
			List<Document> docs = 
					fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_IC, displayProjectId);
				
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

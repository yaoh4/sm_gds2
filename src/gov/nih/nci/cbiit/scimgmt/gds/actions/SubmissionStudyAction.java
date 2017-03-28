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

public class SubmissionStudyAction  extends ManageSubmission {
	
	
	private String studyname;
	
	private String studyId;
	
	private Study study;

	private String studyInstitution;
	
	
	public String execute() throws Exception {
		setProject(retrieveSelectedProject());
		Study study = null;
		if(studyId != null) {
			study = retrieveStudy(Long.valueOf(studyId));
		}
		
		if(study == null) {
			study = new Study();
		}
        
		setStudy(study);
        return SUCCESS;
	}
	/**
	 * Retrieve the project based on the projectId indicated in the request
	 * 
	 * @return
	 */
	private Study retrieveStudy(Long studyId) {
	
		if(studyId != null) {
			
			Project project = getProject();
			if(project == null) {
				project = retrieveSelectedProject();
			}
			List<Study> studies = project.getStudies();
			if(!CollectionUtils.isEmpty(studies)) {
				for(Study study: studies) {
					if(study.getId().equals(studyId)) {
						return study;
					}
				}
			}
		} 
		
		return null;
	}
	
	public void validateSaveStudy() {
		this.clearActionErrors();
		Study study = getStudy();
		
		if(StringUtils.isEmpty(study.getStudyName())) {
			addActionError(getText("error.studyName.required"));
		}
		
		if(hasActionErrors()) {
			setProject(retrieveSelectedProject());
			setStudy(study);
		}
	}
	public String saveStudy(){
		
		Project project = retrieveSelectedProject();
		Study study = getStudy();
		Long storedStudyId = study.getId();
		if(storedStudyId != null) {
			study.setLastChangedBy(loggedOnUser.getAdUserId());
		} else {
			study.setCreatedBy(loggedOnUser.getAdUserId());
		}

		study.setProject(project);
		//Save the study
		study = manageProjectService.saveStudy(study);
				
				if(storedStudyId == null) {
					//This is a new study, so add it to the project
					project.getStudies().add(study);
					project = super.saveProject(project, ApplicationConstants.PAGE_CODE_IC,false);
				} 
				else {
					project = super.saveProject(project, ApplicationConstants.PAGE_CODE_IC);
				}
		setProject(project);
		setProjectId(project.getId().toString());

	 return SUCCESS;
		
	}
	
	public String saveAndAddStudy() {
		saveStudy();
		return SUCCESS;
	}
	
	public String deleteStudy() {

		Long studyId = Long.valueOf(getStudyId());
		Project project = retrieveSelectedProject();		
		manageProjectService.deleteStudy(studyId, project);
		setProject(retrieveSelectedProject());	
        List<Study> studies = retrieveSelectedProject().getStudies();
		if(CollectionUtils.isEmpty(studies)) {
			//We don't save subprojects here because we have to do that anyways in the
			//next step since the certification complete flag has to be changed.
			super.saveProject(getProject(), ApplicationConstants.PAGE_CODE_IC, true);
		}
			
		setProject(retrieveSelectedProject());
		return SUCCESS;
		
	}
	
	
	public Study getStudy() {
		return study;
      }


    public void setStudy(Study study) {
	   this.study = study;
     }

    public String getStudyId() {
	   return studyId;
    }


    public void setStudyId(String studyId) {
	  this.studyId = studyId;
    }


    public String getStudyname() {
	  return studyname;
    }


    public void setStudyname(String studyname) {
	   this.studyname = studyname;
    }


    public String getStudyInstitution() {
	 return studyInstitution;
   }


   public void setStudyInstitution(String studyInstitution) {
	 this.studyInstitution = studyInstitution;
   }

}

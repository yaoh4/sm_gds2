package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Study;
import org.springframework.util.CollectionUtils;


/**
 * @author gantam2
 *
 */
@SuppressWarnings("serial")
public class SubmissionStudyAction  extends ManageSubmission {
	
	private static final Logger logger = LogManager.getLogger(SubmissionStudyAction.class);	
	private static SubmissionStudyAction instance;
	
	private String studyname;
	
	private String studyId;
	
	private Study study;

	private String studyInstitution;

	public static SubmissionStudyAction getInstance() {
		return instance;
	}
	
	
	/**
	 * Navigate to Studies List.
	 * @return forward string
	 */
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
	 * @return Study
	 */
	public Study retrieveStudy(Long studyId) {
		logger.info("Retreives the study based on study Id.");
		
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
	
	
	/**
	 * validates save study
	 * 
	 */
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
	
	/**
	 * saves the study
	 * @return forward string
	 */
	public String saveStudy() {
		logger.info("Saving the study.");
		
		Project project = retrieveSelectedProject();
		if(getStudy().getId() != null) {
			if(!CollectionUtils.isEmpty(getStudy().getInstitutionalCertifications())) {
			    Study study = project.getStudyById(Long.valueOf(getStudy().getId()));
				study.setStudyName(getStudy().getStudyName());
				study.setInstitution(getStudy().getInstitution());
				study.setLastChangedBy(loggedOnUser.getAdUserId());
				study.setProject(project);
			} else {
			    Study study = getStudy();
			    study.setLastChangedBy(loggedOnUser.getAdUserId());
			    study.setProject(project);
			    //Save the study
			    study = manageProjectService.saveStudy(study);
			  }
			project = super.saveProject(project, ApplicationConstants.PAGE_CODE_IC);
		    
		} else {
			    Study study = getStudy();
			    study.setCreatedBy(loggedOnUser.getAdUserId());
			    study.setProject(project);
			    //Save the study
			    study = manageProjectService.saveStudy(study);
			    project.getStudies().add(study);
			    project = super.saveProject(project, ApplicationConstants.PAGE_CODE_IC,false);
		  }
		
		setProject(project);
		setProjectId(project.getId().toString());
	    return SUCCESS;
		
	}
	
	/**
	 * validates save and Add another  study
	 * 
	 */
	public void validateSaveAndAddStudy() {
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
	
	/**
	 * saves and add another study
	 * 
	 * @return forward string
	 */
	public String saveAndAddStudy() {
		logger.info("Saving the study and returns to the Add study.");
		saveStudy();
		getStudy().setStudyName(null);
		getStudy().setInstitution(null);
		addActionMessage(getText("project.save.success"));
		return SUCCESS;
	}
	
	/**
	 * deletes the study not associated iwth IC
	 * 
	 * @return forward string
	 */
	public String deleteStudy() {
		logger.info("Deleting the study.");

		Long studyId = Long.valueOf(getStudyId());
		Project project = retrieveSelectedProject();		
		manageProjectService.deleteStudy(studyId, project);
		setProject(retrieveSelectedProject());	
        List<Study> studies = retrieveSelectedProject().getStudies();
		if(CollectionUtils.isEmpty(studies)) {
			super.saveProject(getProject(), ApplicationConstants.PAGE_CODE_IC, true);
		}
			
		setProject(retrieveSelectedProject());
		return SUCCESS;
		
	}
	
	/**
	 * gets the study
	 * 
	 * @return study
	 */
	public Study getStudy() {
	   return study;
    }
    
	/**
	 * sets the study
	 * 
	 * @param study
	 */
    public void setStudy(Study study) {
	   this.study = study;
    }
    
    /**
     * gets the study id
     * 
     * @return study id
     */
    public String getStudyId() {
	   return studyId;
    }

    /**
     * sets the study ID
     * 
     * @param studyId
     */
    public void setStudyId(String studyId) {
	   this.studyId = studyId;
    }

    /**
     * gets the study name
     * 
     * @return study name
     */
    public String getStudyname() {
	   return studyname;
    }

    /**
     * sets the study name
     * 
     * @param studyname
     */
    public void setStudyname(String studyname) {
	   this.studyname = studyname;
    }

    /**
     * gets the study Institution
     * 
     * @return study institution
     */
    public String getStudyInstitution() {
	   return studyInstitution;
    }

    /**
     * sets the study institution
     * 
     * @param studyInstitution
     */
    public void setStudyInstitution(String studyInstitution) {
	   this.studyInstitution = studyInstitution;
    }

}

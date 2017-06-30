package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	private String studyId;
	
	private Study study;

	private List<Study> listStudies = new ArrayList<Study>();

	
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
		listStudies = retrieveSelectedProject().getStudies();
		Collections.sort(listStudies,StuNameComparator);
        return SUCCESS;
	}
	
	 public static Comparator<Study> StuNameComparator = new Comparator<Study>() {

			public int compare(Study s1, Study s2) {
			   String StudentName1 = s1.getStudyName().toUpperCase();
			   String StudentName2 = s2.getStudyName().toUpperCase();
			   return StudentName1.compareTo(StudentName2);
		    }
	 };

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
			listStudies = retrieveSelectedProject().getStudies();
			Collections.sort(listStudies,StuNameComparator);
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
			listStudies = retrieveSelectedProject().getStudies();
			Collections.sort(listStudies,StuNameComparator);
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
		listStudies = retrieveSelectedProject().getStudies();
		Set<Study> hs = new HashSet<>();
		hs.addAll(listStudies);
		listStudies.clear();
		listStudies.addAll(hs);
		Collections.sort(listStudies,StuNameComparator);
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
     * 
     * @return list of studies
     */
    public List<Study> getListStudies() {
		return listStudies;
	}

    /**
     * 
     * @param listStudies
     */
	public void setListStudies(List<Study> listStudies) {
		this.listStudies = listStudies;
	}

}

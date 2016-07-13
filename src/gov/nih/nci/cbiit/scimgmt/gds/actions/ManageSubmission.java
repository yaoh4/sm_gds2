package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.services.FileUploadService;
import gov.nih.nci.cbiit.scimgmt.gds.services.LookupService;
import gov.nih.nci.cbiit.scimgmt.gds.services.ManageProjectService;

/**
 * Manages Submission creation, updates and deletion.
 * 
 * @author tembharend
 *
 */
@SuppressWarnings("serial")
public class ManageSubmission extends BaseAction {
	
	static Logger logger = LogManager.getLogger(ManageSubmission.class);
	
	@Autowired
	protected ManageProjectService manageProjectService;
	
	@Autowired 
	protected FileUploadService fileUploadService;	
	
	@Autowired
	protected LookupService lookupService;
	
	private Project project;
	
	private Long docId;

	protected InputStream inputStream;
	
	/**
	 * Execute method, for now used for navigation
	 * 
	 * @return forward string
	 */
	public String execute() throws Exception {
		logger.debug("execute");
        
        return SUCCESS;
	}
	

	/**
	 * @return the selectedProject
	 */
	public Project getProject() {
		return project;
	}


	/**
	 * @param selectedProject the selectedProject to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}
	
	
	/**
	 * Retrieve the project based on the projectId indicated in the request
	 * 
	 * @return
	 */
	public Project retrieveSelectedProject() {
	
		String projectId  = getProjectId();
		if(StringUtils.isNotBlank(projectId)) {
			return manageProjectService.findById(Long.valueOf(projectId));
		} 
		
		return null;
	}
	
	
	/**
	 * Save the project
	 */
	public Project saveProject(Project project) {
		
		//Temporary hard coding project property. 
		project.setVersionNum(1l);
		project.setSubprojectFlag("N");
		return manageProjectService.saveOrUpdate(project);
		
	}

	
	/**
	 * Delete a file using document id
	 */
	public String deleteFile() {
		logger.debug("deleteFile()");
		try {
			if (docId == null) {
				inputStream = new ByteArrayInputStream(
						getText("error.doc.id").getBytes("UTF-8"));

				return SUCCESS;
			}
			fileUploadService.deleteFile(docId);
			inputStream = new ByteArrayInputStream(getText("document.delete.success").getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * Retrieve a file using document id
	 */
	public String downloadFile() {
		logger.debug("downloadFile()");
		try {
			if (docId == null) {
				inputStream = new ByteArrayInputStream(
						getText("error.doc.id").getBytes("UTF-8"));
				return SUCCESS;
			}
			HttpServletResponse response = ServletActionContext.getResponse();
			Document doc = fileUploadService.retrieveFile(docId);
			if (doc == null) {
				inputStream = new ByteArrayInputStream(getText("error.doc.notFound").getBytes("UTF-8"));
				return SUCCESS;
			}

			if(StringUtils.isBlank(doc.getFileName())) {
				response.setHeader("Content-Disposition", "inline;filename=\"" + doc.getDocTitle() + "\"");
			}
			else {
				response.setHeader("Content-Disposition", "inline;filename=\"" + doc.getFileName() + "\"");
			}
			
			OutputStream out = response.getOutputStream();
			response.setContentType(doc.getContentType());
			out.write(doc.getDoc());
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Determine whether a page should be shown
	 * @param page
	 * @return
	 */
	public boolean showPage(String page) {
		logger.debug("showPage()");
		
		boolean show = true;
		
		// If user selects "Non-human" only, the system will NOT display the "Institutional Certifications"
		if(project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_HUMAN_ID) == null &&
				project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_NONHUMAN_ID) != null) {
			if(page.equalsIgnoreCase(ApplicationConstants.PAGE_TYPE_IC)) {
				show = false;
			}
		}
		
		// If user selects ONLY the "Other" repository in the "What repository will the data be submitted to?" question GDS plan page, 
		// the "Institutional Certification" page will not be displayed.
		Set<PlanAnswerSelection> repoSet = project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID);
		boolean otherRepoOnly = true;
		boolean otherRepoExist = false;
		for(PlanAnswerSelection repo: repoSet) {
			if(repo.getPlanQuestionsAnswer().getId().longValue() == ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_OTHER_ID.longValue())
				otherRepoExist = true;
			if(repo.getPlanQuestionsAnswer().getId().longValue() != ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_OTHER_ID.longValue())
				otherRepoOnly = false;
		}
		if(otherRepoExist && otherRepoOnly) {
			if(page.equalsIgnoreCase(ApplicationConstants.PAGE_TYPE_IC)) {
				show = false;
			}
		}
		
		// If the answer to "Will there be any data submitted?" is No.
		// Don't show IC, BSI.
		if (project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_NO_ID) != null) {
			if(page.equalsIgnoreCase(ApplicationConstants.PAGE_TYPE_IC) || page.equalsIgnoreCase(ApplicationConstants.PAGE_TYPE_BSI)) {
				show = false;
			}
		}
		else if (project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID).isEmpty()) {
			// If there are no repository selected, don't show the repository page.
			if(page.equalsIgnoreCase(ApplicationConstants.PAGE_TYPE_STATUS)) {
				show = false;
			}
		}
		
		return show;
	}
	
	/**
	 * Get docId
	 * 
	 * @return
	 */
	public Long getDocId() {
		return docId;
	}

	/**
	 * Set docId
	 * 
	 * @param docId
	 */
	public void setDocId(Long docId) {
		this.docId = docId;
	}


	public InputStream getInputStream() {
		return inputStream;
	}


	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	
	/**
	 * Get Lookup object by list name and code
	 * 
	 * @param id
	 * @return
	 */
	public  String getLookupDisplayNamebyId(Long id) {
		List<Lookup> list = (List<Lookup>) lookupService.getAllLookupLists();
		for(Lookup entry: list) {
			if (entry.getId().equals(id))
				return entry.getDisplayName();
		}
		return null;
	}
	
	/**
	 * Validate Upload File
	 */
	protected boolean validateUploadFile(File file, String contentType) {

		String errorMessage = "";
		
		try {
			if (file == null) {
				errorMessage = getText("error.doc.required");

			} else if (file.length() == 0) {
				errorMessage = getText("error.doc.empty");

			} else if (file.length() > 5000000) {
				errorMessage = getText("error.doc.size");

			} else if (!"application/pdf".equals(contentType)
					&& !"application/msword".equals(contentType)
					&& !"application/vnd.openxmlformats-officedocument.wordprocessingml.document"
							.equals(contentType)) {
				errorMessage = getText("error.doc.format");

			}
			if(StringUtils.isNotBlank(errorMessage)) {
				inputStream = new ByteArrayInputStream(errorMessage.getBytes("UTF-8"));
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}

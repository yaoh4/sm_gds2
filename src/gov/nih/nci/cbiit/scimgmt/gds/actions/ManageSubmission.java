package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
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
						"Document Id needs to be provided for delete".getBytes("UTF-8"));

				return SUCCESS;
			}
			fileUploadService.deleteFile(docId);
			inputStream = new ByteArrayInputStream("Document have been deleted.".getBytes("UTF-8"));
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
						"Document Id needs to be provided for download".getBytes("UTF-8"));
				return SUCCESS;
			}
			HttpServletResponse response = ServletActionContext.getResponse();
			Document doc = fileUploadService.retrieveFile(docId);
			if (doc == null) {
				inputStream = new ByteArrayInputStream("Document could not be found".getBytes("UTF-8"));
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
		if(project.getPlanAnswerSelectionByAnswerId(new Long(12)) == null &&
				project.getPlanAnswerSelectionByAnswerId(new Long(13)) != null) {
			if(page.equalsIgnoreCase("ic")) {
				show = false;
			}
		}
		
		// If the answer to "Will there be any data submitted?" is No.
		// Don't show IC, BSI, Repository page.
		if (project.getPlanAnswerSelectionByAnswerId(new Long(10)) != null) {
			if(page.equalsIgnoreCase("ic") || page.equalsIgnoreCase("bsi") || page.equalsIgnoreCase("repository")) {
				show = false;
			}
		}
		
		// If there are no repository selected, don't show the repository page.
		if (project.getPlanAnswerSelectionByQuestionId(new Long(20)).isEmpty()) {
			if(page.equalsIgnoreCase("repository")) {
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
	
}

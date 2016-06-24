package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.io.IOException;
import java.io.OutputStream;

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
	public void deleteFile() {
		logger.debug("deleteFile()");
		if(docId == null) {
			return;
		}
		fileUploadService.deleteFile(docId);
		return;
	}
	
	/**
	 * Retrieve a file using document id
	 */
	public void downloadFile() {
		logger.debug("downloadFile()");
		if(docId == null) {
			return;
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		Document doc = fileUploadService.retrieveFile(docId);
		if (doc == null)
			return;
		try {
            response.setHeader("Content-Disposition", "inline;filename=\"" +doc.getDocTitle()+ "\"");
            OutputStream out = response.getOutputStream();
            response.setContentType(doc.getContentType());
            out.write(doc.getDoc());
            out.flush();
            out.close();
         
        } catch (IOException e) {
            e.printStackTrace();
        }
		return;
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
	
}

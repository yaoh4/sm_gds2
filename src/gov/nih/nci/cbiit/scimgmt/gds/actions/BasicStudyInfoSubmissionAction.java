package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;

/**
 * Basic Study Information Page Action Class
 * 
 * @author dinhys
 *
 */
@SuppressWarnings("serial")
public class BasicStudyInfoSubmissionAction extends ManageSubmission {
	
	static Logger logger = LogManager.getLogger(BasicStudyInfoSubmissionAction.class);
	
	private File bsi;

	private String bsiFileName;

	private String bsiContentType;

	private List<Document> bsiFile;

	private String comments;

	private String bsiReviewedFlag;

	/**
	 * Execute method for Basic Study Info.  
	 * Invoked from GDS Plan page Next button or IC page Next button or
	 * Submission Status page Next button or Navigation tab for Basic Study Info or 
	 * Submission Details page edit.
	 * 
	 * The following will be performed.
	 * 1. Retrieve project from database based on project id.
	 * 3. Navigate to Basic Study Info page.
	 * 
	 * @return forward string
	 */
	public String execute() throws Exception {

		logger.debug("execute");
        
		if(StringUtils.isEmpty(getProjectId())) {
			throw new Exception();
		}
		setProject(retrieveSelectedProject());
		
		setUpPageData();
		
        return SUCCESS;
	}

	
	/**
	 * Save Basic Study Info 
	 * Invoked from Basic Study Info page Save button.
	 * 
	 * The following will be performed.
	 * 1. Saves user-selected answers for "Has the GPA reviewed the Basic Study Information?".
	 * 2. Navigate back to Basic Study Info page.
	 * 
	 * @return forward string
	 */
	public String save() throws Exception {
		
		logger.debug("Save Basic Study");
		
		setProject(retrieveSelectedProject());
		
		// Save user answer and comments
		getProject().setBsiReviewedFlag(bsiReviewedFlag);
		getProject().setBsiComments(comments); 

		super.saveProject(getProject());
		
		setProject(retrieveSelectedProject());
		
		setUpPageData();
		
		addActionMessage(getText("project.save.success"));
		
        return SUCCESS;
	}
	
	/**
	 * Validate Save Basic Study Info
	 */
	public void validateSave() {
		
		logger.debug("Validate save Basic Study Info");
		
		//Comments cannot be greater than 2000 characters.
		if (!StringUtils.isEmpty(comments)) {
			if (comments.length() > ApplicationConstants.COMMENTS_MAX_ALLOWED_SIZE) {
				this.addActionError(getText("error.comments.size.exceeded"));
			}
		}

		// Answer is required.
		if(StringUtils.isEmpty(bsiReviewedFlag)) {
			this.addActionError(getText("bsiReviewedFlag.required"));
		}
		
		// File is required.
		bsiFile = fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_BSI, Long.valueOf(getProjectId()));
		if(bsiFile == null || bsiFile.isEmpty()) {
			this.addActionError(getText("error.doc.required"));
		}
		
		if(hasErrors())
			setProject(retrieveSelectedProject());
		
	}

	/**
	 * Save Basic Study Info and Navigate to Submission Details
	 * Invoked from Basic Study Info Save & Next button.
	 * 
	 * @return forward string
	 */
	public String saveAndNext() throws Exception {
		
		save();
		
		if(showPage(ApplicationConstants.PAGE_TYPE_STATUS))
			return ApplicationConstants.PAGE_TYPE_STATUS.toLowerCase();
		
        return SUCCESS;
	}
	
	/**
	 * Validate Save & Next Basic Study Info 
	 */
	public void validateSaveAndNext() {
		
		validateSave();
	}
	
	/**
	 * Upload Basic Study Info Document
	 * 
	 * @return forward string
	 */
	public String uploadBasicStudyInfo() {
		logger.info("uploadBasicStudyInfo()");
		Document doc = null;
		
		if (!validateUploadFile(bsi, bsiContentType))
			return INPUT;
		
		try {
			doc = fileUploadService.storeFile(new Long(getProjectId()), ApplicationConstants.DOC_TYPE_BSI, bsi, bsiFileName);
			bsiFile = fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_BSI, new Long(getProjectId()));
			
		} catch (Exception e) {
			try {
				inputStream = new ByteArrayInputStream(getText("error.doc.upload").getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				return INPUT;
			}
			return INPUT;
		}
				
		logger.info("===> docId: " + doc.getId());
		logger.info("===> fileName: " + doc.getFileName());
		logger.info("===> docTitle: " + doc.getDocTitle());
		logger.info("===> uploadDate: " + doc.getUploadedDate());

		return SUCCESS;
	}
	
	/**
	 * Delete Basic Study Info Document
	 * 
	 * @return forward string
	 */
	public String deleteBsiFile() {
		logger.info("deleteBsiFile()");
		
		try {
			if (getDocId() == null) {
				inputStream = new ByteArrayInputStream(
						getText("error.doc.id").getBytes("UTF-8"));

				return INPUT;
			}
			fileUploadService.deleteFile(getDocId());
			bsiFile = fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_BSI, new Long(getProjectId()));
			
		} catch (UnsupportedEncodingException e) {
			try {
				inputStream = new ByteArrayInputStream(getText("error.doc.delete").getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				return INPUT;
			}
			return INPUT;
		}

		return SUCCESS;
	}

	
	/**
	 * This method sets up all data for Basic Study Info page.
	 * @throws Exception 
	 */
	private void setUpPageData() throws Exception{

		bsiFile = fileUploadService.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_BSI, getProject().getId());
		
		bsiReviewedFlag = getProject().getBsiReviewedFlag();
		
		// Set comments
		comments = getProject().getBsiComments();

	}


	public File getBsi() {
		return bsi;
	}


	public void setBsi(File bsi) {
		this.bsi = bsi;
	}


	public String getBsiFileName() {
		return bsiFileName;
	}


	public void setBsiFileName(String bsiFileName) {
		this.bsiFileName = bsiFileName;
	}


	public String getBsiContentType() {
		return bsiContentType;
	}


	public void setBsiContentType(String bsiContentType) {
		this.bsiContentType = bsiContentType;
	}


	public List<Document> getBsiFile() {
		return bsiFile;
	}


	public void setBsiFile(List<Document> bsiFile) {
		this.bsiFile = bsiFile;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}


	public String getBsiReviewedFlag() {
		return bsiReviewedFlag;
	}


	public void setBsiReviewedFlag(String bsiReviewedFlag) {
		this.bsiReviewedFlag = bsiReviewedFlag;
	}

}

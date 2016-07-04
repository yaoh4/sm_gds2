/**
 * 
 */
package gov.nih.nci.cbiit.scimgmt.gds.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.dao.DocumentsDao;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.services.FileUploadService;
import gov.nih.nci.cbiit.scimgmt.gds.services.LookupService;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * File Upload Service - Store file, text to DB, retrieve file, text from DB
 * File version management if necessary.
 * 
 * @author dinhys
 *
 */
@Component
public class FileUploadServiceImpl implements FileUploadService {

	private static final Logger logger = LogManager.getLogger(FileUploadServiceImpl.class);

	@Autowired
	private DocumentsDao documentsDao;
	@Autowired
	private LookupService lookupService;
	@Autowired
	protected NedPerson loggedOnUser;
	
	
	/**
	 * Stores user selected file in DB
	 * 
	 * @param projectId
	 * @param docType
	 * @param file
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public Document storeFile(Long projectId, String docType, File file, String fileName) throws Exception {
		
		return storeFile(projectId, docType, file, fileName, null);
	}

	/**
	 * Stores user selected file in DB
	 * 
	 * @param projectId
	 * @param docType
	 * @param file
	 * @param fileName
	 * @param certId
	 * @return
	 * @throws Exception
	 */
	public Document storeFile(Long projectId, String docType, File file, String fileName, Long certId) throws Exception {
		
		Document doc = createDocument(projectId, docType, fileName, certId);
		
		//Perform tasks related to version control including removal of old doc if not supported.
		doc = perfromVersionControl(doc);
		
		// Set the binary file
		byte[] data = new byte[(int) file.length()];
		FileInputStream fileInputStream = new FileInputStream(file);
		fileInputStream.read(data);
		fileInputStream.close();
		doc.setDoc(data);
		
		// Save the file to DB
		Document savedDoc = documentsDao.saveOrUpdate(doc);
		return savedDoc;
	}

	/**
	 * Stores user input text as a row in DB
	 * 
	 * @param documents
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public Document storeFile(Long projectId, String docType, String text) throws Exception {
		
		Document doc = createDocument(projectId, docType, "", null);
		
		//Perform tasks related to version control including removal of old doc if not supported.
		doc = perfromVersionControl(doc);
				
		// Set the user entered text
		byte[] data = text.getBytes();
		doc.setDoc(data);
		
		// Save the text to DB
		Document savedDoc = documentsDao.saveOrUpdate(doc);
		return savedDoc;
	}

	/**
	 * Deletes a file from DB by docId
	 * 
	 * @param docId
	 * @return boolean
	 */
	public boolean deleteFile(Long docId) {
		
		Document doc = documentsDao.findById(docId);
		if(doc == null)
			return false;
		documentsDao.delete(doc);
		return true;
	}

	/**
	 * Retrieve a list of files from DB by ProjectId and DocTypeId
	 * 
	 * @param docTypeId
	 * @param projectId
	 * 
	 * @return document
	 */
	public List<Document> retrieveFileByDocType(String docType, Long projectId) {
		
		List<Document> docs = documentsDao.findByDocType(docType, projectId);
		return docs;
	}

	/**
	 * Retrieve the file from DB by docId
	 * 
	 * @param docId
	 * @return document
	 */
	public Document retrieveFile(Long docId) {
		
		Document doc = documentsDao.findById(docId);
		if (doc != null) {
			doc.setContentType(getMimeType(doc.getContentType()));
		}
		return doc;
	}

	/**
	 * Update a document row with Institutional Certification Id
	 * 
	 * @param docId
	 * @param certId
	 * @return
	 */
	public boolean updateCertId(Long docId, Long certId) {
		
		Document doc = documentsDao.findById(docId);
		doc.setInstitutionalCertificationId(certId);
		documentsDao.saveOrUpdate(doc);
		return true;
	}

	/**
	 * Create a new Document object to store in DB.
	 * 
	 * @param projectId
	 * @param docType
	 * @param fileName
	 * @param certId
	 * @return
	 */
	private Document createDocument(Long projectId, String docType, String fileName, Long certId) {
		
		Document doc = new Document();
		doc.setProjectId(projectId);
		doc.setActiveFlag("Y");
		doc.setFileName(fileName);
		doc.setContentType(getFileExtension(fileName));
		doc.setUploadedDate(new Date());
		doc.setCreatedBy(loggedOnUser.getAdUserId().toUpperCase());
		doc.setUploadedBy(loggedOnUser.getFullName());
		if(certId != null) {
			doc.setInstitutionalCertificationId(certId);
		}
		
		// Get doc type object from lookup
		doc.setDocType(lookupService.getLookupByCode("DOC_TYPE", docType));

		return doc;
	}
	

	/**
	 * Perform maintenance task for version control
	 * 
	 * 1. If version is not supported, old file will be replaced.
	 * 2. If this is an institutional certificate and old record with no certId is kept, replace.
	 * 3. If version is supported, get the latest version and increment the version and doc title
	 * 
	 * @param doc
	 * @return
	 */
	private Document perfromVersionControl(Document doc) {
		
		// Retrieve the latest document for this docType if any
		List<Document> oldDocs = documentsDao.findByDocType(doc.getDocType().getCode(), doc.getProjectId());
		
		// Preset the version and doc Title to version 1, updated later if necessary
		doc.setVersionNum(1L);
		doc.setDocTitle(doc.getDocType().getDisplayName() + " Version 1");
		
		// There are no files stored previously so create version 1
		if(oldDocs == null || oldDocs.isEmpty()) {
			return doc;
		}
		
		// If docType is IC, then we want to create a new file unless there is an unsaved file
		Document unsavedFile = null;
		if(doc.getDocType().getCode().equals("IC")) {
			for(Document d : oldDocs) {
				if (d.getInstitutionalCertificationId() == null) {
					unsavedFile = d;
					break;
				}
			}
			if(unsavedFile != null) {
				Long id = unsavedFile.getId();
				BeanUtils.copyProperties(doc, unsavedFile);
				unsavedFile.setId(id);
				return unsavedFile;
			}
			return doc;
		}
		
		// Check if version is supported for this docType
		if (isVersionControlled(doc.getDocType().getCode())) {
			// Increment the version and store it as a new version
			Long newVersion = oldDocs.get(0).getVersionNum() + 1;
			doc.setVersionNum(newVersion);
			doc.setDocTitle(doc.getDocType().getDisplayName() + " Version " + newVersion.toString());
		} else {
			// Version is not supported, retrieve the last doc if any for replacement
			Long id = oldDocs.get(0).getId();
			BeanUtils.copyProperties(doc, oldDocs.get(0));
			oldDocs.get(0).setId(id);
			return oldDocs.get(0);
		}

		return doc;
	}
	
	/**
	 * Returns true if docType supports versions
	 * 
	 * @param code
	 * @return
	 */
	private boolean isVersionControlled(String docType) {
		switch (docType) {
		case ApplicationConstants.DOC_TYPE_EXCEPMEMO:
			return false;
		case ApplicationConstants.DOC_TYPE_GDSPLAN:
			return true;
		case ApplicationConstants.DOC_TYPE_IC:
			return false;
		case ApplicationConstants.DOC_TYPE_BSI:
			return true;
		}
		return false;
	}

	/**
	 * Get the file extension for the content type
	 * 
	 * @param doc
	 * @return
	 */
	private String getFileExtension(String fileName) {
		
		if (StringUtils.isEmpty(fileName)) {
			return "html";
		}

		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
	
	/**
	 * Get the mimeType for display
	 * 
	 * @param doc
	 * @return
	 */
	private String getMimeType(String fileExtention) {
		
		String mimeType = "";

		if (fileExtention.equalsIgnoreCase("pdf")) {
			mimeType = "application/pdf";
		} else if (fileExtention.equalsIgnoreCase("doc")) {
			mimeType = "application/msword";
		} else if (fileExtention.equalsIgnoreCase("docx")) {
			mimeType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		} else if (fileExtention.equalsIgnoreCase("xls")) {
			mimeType = "application/vnd.ms-excel";
		} else if (fileExtention.equalsIgnoreCase("xlsx")) {
			mimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		} else if (fileExtention.equalsIgnoreCase("rtf")) {
			mimeType = "text/rtf";
		} else if (fileExtention.equalsIgnoreCase("txt")) {
			mimeType = "text/plain";
		}  else if (fileExtention.equalsIgnoreCase("html")) {
			mimeType = "text/html";
		} else if (fileExtention.equalsIgnoreCase("ppt")) {
			mimeType = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
		}
		return mimeType;
	}

}

package gov.nih.nci.cbiit.scimgmt.gds.services;

import java.io.File;
import java.util.List;

import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;

public interface FileUploadService {

	
	/**
	 * Stores user selected file in DB
	 * 
	 * @param projectId
	 * @param docType
	 * @param file
	 * @param filename
	 * @param certId
	 * @return
	 * @throws Exception 
	 */
	public Document storeFile(Long projectId, String docType, File file, String fileName, Long certId) throws Exception;
	
	
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
	public Document storeFile(Long projectId, String docType, File file, String fileName) throws Exception;

	/**
	 * Stores user input text as a row in DB
	 * 
	 * @param documents
	 * @param text
	 * @return
	 * @throws Exception 
	 */
	public Document storeFile(Long projectId, String docType, String text) throws Exception;

	
	/**
	 * Deletes a file from DB by docId
	 * 
	 * @param docId
	 * @return boolean
	 */	
	public boolean deleteFile(Long docId);
	
	/**
	 * Retrieve a list of files from DB by ProjectId
	 * 
	 * @param projectId
	 * 
	 * @return document
	 */
	public List<Document> retrieveFileByProjectId(Long projectId);
	
	/**
	 * Retrieve a list of files from DB by icId and projectId
	 * 
	 * @param icId
	 * @param projectId
	 * 
	 * @return document
	 */
	public List<Document> retrieveFileByIcId(Long icId, Long projectId);
	
	/**
	 * Retrieve a list of files from DB by ProjectId and DocTypeId
	 * 
	 * @param docTypeId
	 * @param projectId
	 * 
	 * @return document
	 */
	public List<Document> retrieveFileByDocType(String docType, Long projectId);

	/**
	 * Retrieve the file from DB by docId
	 * 
	 * @param docId
	 * @return document
	 */
	public Document retrieveFile(Long docId);
	
	
	/**
	 * Update a document row with Institutional Certification Id
	 * 
	 * @param docId
	 * @param certId
	 * @return
	 */
	public boolean updateCertId(Long docId, Long certId);
}

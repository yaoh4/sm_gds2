package gov.nih.nci.cbiit.scimgmt.gds.dao;
// Generated Jun 14, 2016 9:48:12 PM by Hibernate Tools  4.0.0

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;

/**
 * DAO for domain model class Document.
 * 
 * @see gov.nih.nci.cbiit.scimgmt.gds.domain.Document
 * @author dinhys
 */
@Component
public class DocumentsDao {

	private static final Logger logger = LogManager.getLogger(DocumentsDao.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	protected NedPerson loggedOnUser;

	/**
	 * Deletes the document
	 * 
	 * @param persistentInstance
	 */
	public void delete(Document persistentInstance) {
		Long id = persistentInstance.getId();
		logger.info("Deleting Document instance " + id);
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			logger.info("Delete successful for document " + id);
			logger.info("Deletion performed by user: " + loggedOnUser.getAdUserId() + "/" + loggedOnUser.getFullName());									
		} catch (RuntimeException re) {
			logger.error("delete failed for document " + id, re);
			logger.error("user ID: " + loggedOnUser.getAdUserId() + "/" + loggedOnUser.getFullName());			
			throw re;
		}
	}

	/**
	 * Gets the document by docId
	 * 
	 * @param docId
	 * @return
	 */
	public Document findById(Long docId) {
		logger.debug("getting Document instance with docId: " + docId);
		try {
			final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Document.class);
			criteria.add(Restrictions.eq("id", docId));
			Document doc = (Document) criteria.uniqueResult();
			return doc;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	
	public List<Document> findByIcId(Long icId, Long projectId) {
		logger.debug("getting IC Document instance with icId: " + icId + " projectId: " + projectId);
		try {
			final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Document.class);
			criteria.createAlias("docType", "docType");
			criteria.add(Restrictions.eq("docType.code", ApplicationConstants.DOC_TYPE_IC));
			criteria.add(Restrictions.eq("projectId", projectId));
			if(icId != null) {
				criteria.add(Restrictions.eq("institutionalCertificationId", icId));
			}
			criteria.add(Restrictions.eq("activeFlag", "Y"));
			criteria.addOrder(Order.desc("uploadedDate"));
			List<Document> docs = (List<Document>) criteria.list();
			return docs;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	/**
	 * Retrieves a list of Document objects by projectId and DocType
	 * 
	 * @param docTypeId
	 * @param projectId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Document> findByDocType(String docType, Long projectId) {
		logger.debug("getting Document instance with docType: " + docType + " projectId: " + projectId);
		try {
			final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Document.class);
			criteria.createAlias("docType", "docType");
			criteria.add(Restrictions.eq("docType.code", docType));
			criteria.add(Restrictions.eq("projectId", projectId));
			criteria.add(Restrictions.eq("activeFlag", "Y"));
			criteria.addOrder(Order.desc("versionNum"));
			List<Document> docs = (List<Document>) criteria.list();
			return docs;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * Retrieves a list of Document objects by projectId
	 * 
	 * @param docTypeId
	 * @param projectId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Document> findByProjectId(Long projectId) {
		logger.debug("getting Document instance with projectId: " + projectId);
		try {
			final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Document.class);
			criteria.add(Restrictions.eq("projectId", projectId));
			List<Document> docs = (List<Document>) criteria.list();
			return docs;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	/**
	 * Save or updates the document object.
	 * 
	 * @param transientInstance
	 */
	public Document saveOrUpdate(Document transientInstance) {
		Long id = transientInstance.getId();
		logger.info("Save or Update Document instance " + id);
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(transientInstance);
			logger.info("Save successful");
			logger.info("Document saved by user: " + loggedOnUser.getAdUserId() + "/" + loggedOnUser.getFullName());
			return transientInstance;
		} catch (RuntimeException re) {
			logger.error("save failed for document " + id, re);
			logger.error("user ID: " + loggedOnUser.getAdUserId() + "/" + loggedOnUser.getFullName());			
			throw re;
		}
	}

}

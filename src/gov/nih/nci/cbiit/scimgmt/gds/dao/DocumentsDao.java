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

import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;

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

	/**
	 * Deletes the document
	 * 
	 * @param persistentInstance
	 */
	public void delete(Document persistentInstance) {
		logger.debug("deleting Document instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			logger.debug("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
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

	/**
	 * Retrieves a list of Document objects by projectId and DocType
	 * 
	 * @param docTypeId
	 * @param projectId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Document> findByDocType(Long docTypeId, Long projectId) {
		logger.debug("getting Document instance with docTypeId: " + docTypeId + " projectId: " + projectId);
		try {
			final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Document.class);
			criteria.createAlias("docType", "docType");
			criteria.add(Restrictions.eq("docType.id", docTypeId));
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
	 * Save or updates the document object.
	 * 
	 * @param transientInstance
	 */
	public Document saveOrUpdate(Document transientInstance) {
		logger.debug("Save or Update Document instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(transientInstance);
			logger.debug("save successful");
			return transientInstance;
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

}

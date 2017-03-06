package gov.nih.nci.cbiit.scimgmt.gds.dao;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.nih.nci.cbiit.scimgmt.gds.domain.InstitutionalCertification;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectsIcMapping;


/**
 * DAO for domain model class InstitutionalCertification.
 * 
 * @see gov.nih.nci.cbiit.scimgmt.gds.domain.InstitutionalCertification
 * @author menons2
 */
@Component
public class InstitutionalCertificationsDao {

	private static final Logger logger = LogManager.getLogger(InstitutionalCertificationsDao.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Autowired
	protected NedPerson loggedOnUser;

	/**
	 * Deletes the InstitutionalCertification
	 * 
	 * @param persistentInstance
	 */
	public void delete(InstitutionalCertification persistentInstance) {
		Long id = persistentInstance.getId();
		logger.info("Deleting IC instance " + id);
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			logger.info("Delete successful for IC " + id);
			logger.info("Deletion performed by user: " + loggedOnUser.getAdUserId() + "/" + loggedOnUser.getFullName());						
		} catch (RuntimeException re) {
			logger.error("delete failed for IC " + id, re);
			logger.error("user ID: " + loggedOnUser.getAdUserId() + "/" + loggedOnUser.getFullName());
			throw re;
		}
	}
	
	
	/**
	 * Delete the ProjectsIcMapping
	 */
	public void delete(ProjectsIcMapping persistentInstance) {
		Long id = persistentInstance.getId();
		logger.info("Deleting ProjectsIcMapping instance " + id);
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			logger.info("Delete successful for ProjectIcMapping " + id);
			logger.info("Deletion performed by user: " + loggedOnUser.getAdUserId() + "/" + loggedOnUser.getFullName());									
		} catch (RuntimeException re) {
			logger.error("delete failed for ProjectsIcMapping " + id, re);
			logger.error("user ID: " + loggedOnUser.getAdUserId() + "/" + loggedOnUser.getFullName());			
			throw re;
		}
	}
	
	/**
	 * Gets the IC by icId
	 * 
	 * @param icId
	 * @return
	 */
	public List<ProjectsIcMapping> findProjectIcMappingsByIcId(Long icId) {
		logger.debug("getting ProjectsIcMappings with icId: " + icId);
		try {
			final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ProjectsIcMapping.class);
			criteria.add(Restrictions.eq("institutionalCertification.id", icId));
			 List<ProjectsIcMapping> mappings = (List<ProjectsIcMapping>) criteria.list();
			return mappings;
		} catch (RuntimeException re) {
			logger.error("Unable to find IC by id " + icId, re);
			throw re;
		}
	}

	
	/**
	 * Gets the IC by icId
	 * 
	 * @param icId
	 * @return
	 */
	public InstitutionalCertification findById(Long icId) {
		logger.debug("Getting IC instance with id: " + icId);
		try {
			final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(InstitutionalCertification.class);
			criteria.add(Restrictions.eq("id", icId));
			 InstitutionalCertification ic = (InstitutionalCertification) criteria.uniqueResult();
			return ic;
		} catch (RuntimeException re) {
			logger.error("Unable to find IC by id " + icId, re);
			throw re;
		}
	}
	
	public InstitutionalCertification merge(InstitutionalCertification detachedInstance) {
		Long id = detachedInstance.getId();
		logger.info("Merging IC instance " + id);
		try {
			if(id != null){
				//Already saved submission				
				sessionFactory.getCurrentSession().evict(sessionFactory.getCurrentSession().get(InstitutionalCertification.class, id));
				detachedInstance.setLastChangedBy(loggedOnUser.getAdUserId());				
			}
			else{
				//New submission
				detachedInstance.setCreatedBy(loggedOnUser.getAdUserId());				
			}
			InstitutionalCertification result = (InstitutionalCertification) sessionFactory.getCurrentSession().merge(detachedInstance);
			logger.info("Merge successful for IC "  + result.getId());
			logger.info("Merge performed by user: " + loggedOnUser.getAdUserId() + "/" + loggedOnUser.getFullName());			
			
			return result;
		} catch (RuntimeException re) {
			logger.error("Merge failed for IC " + id, re);
			logger.error("user ID: " + loggedOnUser.getAdUserId() + "/" + loggedOnUser.getFullName());			
			throw re;
		}
	}

}

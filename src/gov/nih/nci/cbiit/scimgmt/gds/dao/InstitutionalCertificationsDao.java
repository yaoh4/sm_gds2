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
		logger.debug("deleting IC instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			logger.debug("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}
	
	
	/**
	 * Delete the ProjectsIcMapping
	 */
	public void delete(ProjectsIcMapping persistentInstance) {
		logger.debug("deleting ProjectsIcMapping instance " + persistentInstance.getId());
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			logger.debug("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
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
		logger.debug("getting IC instance with icId: " + icId);
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
		logger.debug("merging IC instance");
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
			logger.debug("merge successful for IC "  + id);
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed for IC " + id, re);
			throw re;
		}
	}

}

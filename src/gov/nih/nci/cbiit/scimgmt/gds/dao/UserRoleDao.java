package gov.nih.nci.cbiit.scimgmt.gds.dao;

import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.InstitutionalCertification;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PersonRole;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.model.RoleSearchCriteria;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DAO for user roles.
 * @author tembharend
 */
@Component
public class UserRoleDao {

	private static final Logger logger = LogManager.getLogger(UserRoleDao.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	protected NedPerson loggedOnUser;

	/**
	 * Find Ned person by user id. 
	 * @param userId
	 * @return NedPerson
	 */
	public NedPerson findNedPersonByUserId(final String userId) throws Exception{
		logger.info("finding NedPerson by userId: '" + userId + "'");
		Session sess = null;

		try {
			sess = sessionFactory.getCurrentSession();
			final Criteria criteria = sess.createCriteria(NedPerson.class);
			criteria.add(Restrictions.ilike("adUserId", userId, MatchMode.EXACT));
			return (NedPerson) criteria.uniqueResult();
			
		} catch (Throwable re) {
			logger.error("Exception occurred while finding NedPerson by userId: "+userId, re);
			throw re;
		}
	}
	
	public PersonRole findPersonRoleByUserId(final String userId) {
		logger.info("finding PersonRole by userId: '" + userId + "'");
		Session sess = null;	

		try {
			sess = sessionFactory.getCurrentSession();
			final Criteria criteria = sess.createCriteria(PersonRole.class);
			criteria.add(Restrictions.ilike("nihNetworkId", userId, MatchMode.EXACT));
			return (PersonRole) criteria.uniqueResult();
			
			
		} catch (Throwable re) {
			logger.error("Exception occurred while finding PersonRole by userId: "+userId, re);
			throw re;
		}
	}
	

	public List<PersonRole> searchPersonRole(RoleSearchCriteria searchCriteria) {
		Session sess = null;

		try {
			sess = sessionFactory.getCurrentSession();
			Criteria criteria = sess.createCriteria(PersonRole.class);
			criteria = setupRoleSearchCriteria(criteria, searchCriteria);
			
			List<PersonRole> roles =  criteria.list();
			return roles;
			
		} catch (Throwable re) {
			logger.error("Exception occurred during searchPersonRole " + re);
			throw re;
		}
	}
	
	
	private Criteria setupRoleSearchCriteria(Criteria criteria, RoleSearchCriteria searchCriteria) {
		
		criteria.createAlias("nedPerson", "nedPerson");
		if(StringUtils.isNotBlank(StringUtils.trim(searchCriteria.getFirstName()))) {
			criteria.add(Restrictions.ilike("nedPerson.firstName", searchCriteria.getFirstName().trim(), MatchMode.EXACT));
		}
		if (StringUtils.isNotBlank(StringUtils.trim(searchCriteria.getLastName()))) {
			criteria.add(Restrictions.ilike("nedPerson.lastName", searchCriteria.getLastName().trim(), MatchMode.EXACT));			
		}
		if(searchCriteria.getRoleId() != null) {
			criteria.add(Restrictions.eq("role.id", searchCriteria.getRoleId()));
		}
		if(StringUtils.isNotBlank(searchCriteria.getDoc())) {
			criteria.add(Restrictions.ilike("nedPerson.nihsac", searchCriteria.getDoc(), MatchMode.START));
		}
		return criteria;
	}
	
	
	public List<NedPerson> searchNedPerson(RoleSearchCriteria searchCriteria) {
		Session sess = null;

		try {
			sess = sessionFactory.getCurrentSession();
			Criteria criteria = sess.createCriteria(NedPerson.class);
			criteria = setupPersonSearchCriteria(criteria, searchCriteria);
			
			List<NedPerson> roles =  criteria.list();
			return roles;
			
		} catch (Throwable re) {
			logger.error("Exception occurred during searchNedPerson ", re);
			throw re;
		}
	}
	
	
	private Criteria setupPersonSearchCriteria(Criteria criteria, RoleSearchCriteria searchCriteria) {
		
		if(StringUtils.isNotBlank(StringUtils.trim(searchCriteria.getFirstName()))) {
			criteria.add(Restrictions.ilike("firstName", searchCriteria.getFirstName().trim(), MatchMode.EXACT));
		}
		if (StringUtils.isNotBlank(StringUtils.trim(searchCriteria.getLastName()))) {
			criteria.add(Restrictions.ilike("lastName", searchCriteria.getLastName().trim(), MatchMode.EXACT));			
		}
		if(StringUtils.isNotBlank(searchCriteria.getDoc())) {
			criteria.add(Restrictions.ilike("nihsac", searchCriteria.getDoc(), MatchMode.START));
		}
		return criteria;
	}
	
	
	public PersonRole merge(PersonRole detachedInstance) {
		String networkId = detachedInstance.getNihNetworkId();
		logger.debug("merging PersonRole instance");
		try {
			if(networkId != null){
				//Already saved role				
				sessionFactory.getCurrentSession().evict(sessionFactory.getCurrentSession().get(PersonRole.class, networkId));
				detachedInstance.setLastChangedBy(loggedOnUser.getAdUserId());				
			}
			else{
				//New submission
				detachedInstance.setCreatedBy(loggedOnUser.getAdUserId());				
			}
			PersonRole result = (PersonRole) sessionFactory.getCurrentSession().merge(detachedInstance);
			logger.debug("merge successful for PersonRole with networkId "  + networkId);
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed for PersonRole  " + networkId, re);
			throw re;
		}
	}
	
	
	/**
	 * Deletes the personRole
	 * 
	 * @param persistentInstance
	 */
	public void delete(PersonRole persistentInstance) {
		logger.debug("deleting PersonRole instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			logger.debug("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}
	
	
	/**
	 * Gets the PersonRole by nihNetworkId
	 * 
	 * @param networkId
	 * @return
	 */
	public PersonRole findByNetworkId(String networkId) {
		logger.debug("getting PersonRole instance with networkId: " + networkId);
		try {
			final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PersonRole.class);
			criteria.add(Restrictions.eq("nihNetworkId", networkId));
			 PersonRole personRole = (PersonRole) criteria.uniqueResult();
			return personRole;
		} catch (RuntimeException re) {
			logger.error("Unable to find PersonRole by nihNetworkId " + networkId, re);
			throw re;
		}
	}
	
}

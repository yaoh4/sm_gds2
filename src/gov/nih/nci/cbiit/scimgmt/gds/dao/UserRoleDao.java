package gov.nih.nci.cbiit.scimgmt.gds.dao;

import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PersonRole;
import gov.nih.nci.cbiit.scimgmt.gds.domain.UserRole;
import gov.nih.nci.cbiit.scimgmt.gds.model.RoleSearchCriteria;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
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
			criteria.add(Restrictions.eq("adUserId", userId));
			return (NedPerson) criteria.uniqueResult();
			
		} catch (Throwable re) {
			logger.error("Exception occurred while finding NedPerson by userId: "+userId, re);
			throw re;
		}
	}
	
	public UserRole findUserRoleByUserId(final String userId) {
		logger.info("finding UserRole by userId: '" + userId + "'");
		Session sess = null;	

		try {
			sess = sessionFactory.getCurrentSession();
			final Criteria criteria = sess.createCriteria(UserRole.class);
			criteria.add(Restrictions.eq("nihNetworkId", userId));
			return (UserRole) criteria.uniqueResult();
			
			
		} catch (Throwable re) {
			logger.error("Exception occurred while finding UserRole by userId: "+userId, re);
			throw re;
		}
	}
	
	
	/**
	 * Gets the PersonRole by nihNetworkId
	 * 
	 * @param networkId
	 * @return
	 */
	public PersonRole findPersonRole(String networkId) {
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
	

	public List<UserRole> searchUserRole(RoleSearchCriteria searchCriteria) {
		Session sess = null;

		try {
			sess = sessionFactory.getCurrentSession();
			Criteria criteria = sess.createCriteria(UserRole.class);
			criteria = setupRoleSearchCriteria(criteria, searchCriteria);
			
			List<UserRole> roles =  criteria.list();
			return roles;
			
		} catch (Throwable re) {
			logger.error("Exception occurred during searchUserRole " + re);
			throw re;
		}
	}
	
	
	private Criteria setupRoleSearchCriteria(Criteria criteria, RoleSearchCriteria searchCriteria) {
		
		criteria.createAlias("nedPerson", "nedPerson");
		
		if(StringUtils.isNotBlank(StringUtils.trim(searchCriteria.getFirstName()))) {
			Disjunction disjunction = Restrictions.disjunction();
			disjunction.add(Restrictions.eq("nedPerson.firstName", searchCriteria.getFirstName().trim()).ignoreCase());
			disjunction.add(Restrictions.eq("nedPerson.preferredName", searchCriteria.getFirstName().trim()).ignoreCase());
			criteria.add(disjunction);
		}
		if (StringUtils.isNotBlank(StringUtils.trim(searchCriteria.getLastName()))) {
			Disjunction disjunction = Restrictions.disjunction();
			disjunction.add(Restrictions.eq("nedPerson.lastName", searchCriteria.getLastName().trim()).ignoreCase());
			disjunction.add(Restrictions.eq("nedPerson.nihcommonsn", searchCriteria.getLastName().trim()).ignoreCase());			
			criteria.add(disjunction);
		}
		
		if(StringUtils.isNotBlank(searchCriteria.getRoleCode())) {
			criteria.add(Restrictions.eq("gdsRoleCode", searchCriteria.getRoleCode()));
		}
		if(StringUtils.isNotBlank(searchCriteria.getDoc())) {
			criteria.add(Restrictions.like("nedPerson.nihsac", searchCriteria.getDoc(), MatchMode.START));
		} else {
			Conjunction conjunction = Restrictions.conjunction();
			conjunction.add(Restrictions.eq("nedPerson.nihorgacronym", loggedOnUser.getNihorgacronym()).ignoreCase());
			conjunction.add(Restrictions.not(Restrictions.in("nedPerson.nihsac", new String[]{"HNC17Y", "HNC1R", "HNC1-5", "HNC17"})));
			criteria.add(conjunction);		
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
			Disjunction disjunction = Restrictions.disjunction();
			disjunction.add(Restrictions.eq("firstName", searchCriteria.getFirstName().trim()).ignoreCase());
			disjunction.add(Restrictions.eq("preferredName", searchCriteria.getFirstName().trim()).ignoreCase());
			criteria.add(disjunction);
		}
		if (StringUtils.isNotBlank(StringUtils.trim(searchCriteria.getLastName()))) {
			Disjunction disjunction = Restrictions.disjunction();
			disjunction.add(Restrictions.eq("lastName", searchCriteria.getLastName().trim()).ignoreCase());
			disjunction.add(Restrictions.eq("nihcommonsn", searchCriteria.getLastName().trim()).ignoreCase());			
			criteria.add(disjunction);
		}
		
		if(StringUtils.isNotBlank(searchCriteria.getDoc())) {
			criteria.add(Restrictions.like("nihsac", searchCriteria.getDoc(), MatchMode.START));
		} else {
			Conjunction conjunction = Restrictions.conjunction();
			conjunction.add(Restrictions.eq("nihorgacronym", loggedOnUser.getNihorgacronym()));
			conjunction.add(Restrictions.not(Restrictions.in("nihsac", new String[]{"HNC17Y", "HNC1R", "HNC1-5", "HNC17"})));
			criteria.add(conjunction);		
		}
		return criteria;
	}
	
	
	public PersonRole merge(PersonRole detachedInstance) {
		String networkId = detachedInstance.getNihNetworkId();
		logger.debug("merging PersonRole instance");
		try {
			if(detachedInstance.getCreatedBy() != null){
				//Already saved GDS role				
				sessionFactory.getCurrentSession().evict(sessionFactory.getCurrentSession().get(PersonRole.class, networkId));
				detachedInstance.setLastChangedBy(loggedOnUser.getAdUserId());
				detachedInstance.setLastChangedDate(new Date());
			}
			else{
				//New GDS role
				detachedInstance.setCreatedBy(loggedOnUser.getAdUserId());	
				detachedInstance.setCreatedDate(new Date());
			}
			PersonRole result = (PersonRole) sessionFactory.getCurrentSession().merge(detachedInstance);
			logger.debug("merge successful for PersonRole with networkId "  + networkId);
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed for PersonRole with networkId " + networkId, re);
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
	
	
}

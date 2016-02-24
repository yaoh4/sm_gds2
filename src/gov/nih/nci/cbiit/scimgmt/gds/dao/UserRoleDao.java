package gov.nih.nci.cbiit.scimgmt.gds.dao;

import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;

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
}

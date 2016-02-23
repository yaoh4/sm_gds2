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
 * The Class NedPersonDao.
 */
@Component
public class UserRoleDao {

	private static final Logger log = LogManager.getLogger(UserRoleDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Find by ad user id.
	 * 
	 * @param userId
	 * @return NedPerson
	 */
	public NedPerson findByAdUserId(final String userId) throws Exception{
		log.info("finding NedPerson by userId: '" + userId + "'");
		Session sess = null;

		try {
			sess = sessionFactory.getCurrentSession();
			final Criteria criteria = sess.createCriteria(NedPerson.class);
			criteria.add(Restrictions.ilike("adUserId", userId, MatchMode.EXACT));
			criteria.setMaxResults(1);

			log.info("findByAdUserId search criteria: " + criteria);

			return (NedPerson) criteria.uniqueResult();
		} catch (Throwable re) {
			log.error("Exception occurred while finding NedPerson by userId: "+userId, re);
			throw re;
		}
	}
}

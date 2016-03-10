package gov.nih.nci.cbiit.scimgmt.gds.dao;

import gov.nih.nci.cbiit.scimgmt.gds.domain.MailTemplate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MailTemplateDao {
	static final Logger logger = LogManager.getLogger(MailTemplateDao.class.getName());
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Find by id.
	 * 
	 * @param id
	 *            the id
	 * @return the mail template
	 */
	public MailTemplate findById(final Long id) {
		logger.info("getting MailTemplate instance with id: " + id);
		try {
			final MailTemplate instance = sessionFactory.getCurrentSession().get(MailTemplate.class, id);
			if (instance == null) {
				logger.info("get successful, no instance found");
			} else {
				logger.info("get successful, instance found");
			}
			return instance;
		} catch (final RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	/**
	 * Find by short identifier.
	 * 
	 * @param shortIdentifier
	 *            the short identifier
	 * @return the mail template
	 */
	public MailTemplate findByShortIdentifier(final String shortIdentifier) {
		logger.info("finding MailTemplate instance by shortIdentifier '" + shortIdentifier + "'");
		try {
			final Criteria crit = sessionFactory.getCurrentSession().createCriteria(MailTemplate.class);
			crit.add(Restrictions.eq("shortIdentifier", shortIdentifier));

			final MailTemplate result = (MailTemplate) crit.uniqueResult();

			return result;
		} catch (final RuntimeException re) {
			logger.error("find by example failed", re);
			throw re;
		}
	}

}

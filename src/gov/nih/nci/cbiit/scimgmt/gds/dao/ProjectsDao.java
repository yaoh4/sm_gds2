package gov.nih.nci.cbiit.scimgmt.gds.dao;
// Generated Mar 7, 2016 1:12:03 PM by Hibernate Tools 4.0.0

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectsT;

/**
 * Dao object for domain model class ProjectsT.
 * @see gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectsT
 * @author Hibernate Tools
 */
@Component
public class ProjectsDao {

	private static final Log logger = LogFactory.getLog(ProjectsDao.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			logger.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(ProjectsT transientInstance) {
		logger.debug("persisting ProjectsT instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void delete(ProjectsT persistentInstance) {
		logger.debug("deleting ProjectsT instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			logger.debug("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	public ProjectsT merge(ProjectsT detachedInstance) {
		logger.debug("merging ProjectsT instance");
		try {
			ProjectsT result = (ProjectsT) sessionFactory.getCurrentSession().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public ProjectsT findById(Long id) {
		logger.debug("getting ProjectsT instance with id: " + id);
		try {
			ProjectsT instance = (ProjectsT) sessionFactory.getCurrentSession()
					.get("gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectsT", id);
			if (instance == null) {
				logger.debug("get successful, no instance found");
			} else {
				logger.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
}

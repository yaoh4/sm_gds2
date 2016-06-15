package gov.nih.nci.cbiit.scimgmt.gds.dao;
// Generated Mar 7, 2016 1:12:03 PM by Hibernate Tools 4.0.0

import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;

/**
 * Dao object for domain model class Project.
 * @see gov.nih.nci.cbiit.scimgmt.gds.domain.Project
 * @author Hibernate Tools
 */
@Component
public class ProjectsDao {

	private static final Logger logger = LogManager.getLogger(ProjectsDao.class);

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

	public void persist(Project transientInstance) {
		logger.debug("persisting Project instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	public void delete(Project persistentInstance) {
		logger.debug("deleting Project instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			logger.debug("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	public Project merge(Project detachedInstance) {
		Long id = detachedInstance.getId();
		logger.debug("merging Project instance");
		try {
			sessionFactory.getCurrentSession().evict(sessionFactory.getCurrentSession().get(Project.class, id));
			Project result = (Project) sessionFactory.getCurrentSession().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public Project findById(Long id) {
		logger.debug("getting Project instance with id: " + id);
		try {
			Project instance = (Project) sessionFactory.getCurrentSession()
					.get(Project.class, id);
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
	
	/**
	 * This method returns all Project Ids
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<Long> getAllProjectIds(){
		Session session = null;
		List<Long> allProjectIds = new ArrayList<Long>();
		try{
			session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Project.class, "project");
			criteria.setProjection( Projections.projectionList().add( Projections.property("project.id"), "project.id"));
			allProjectIds = criteria.list();
			
		} catch (RuntimeException re) {
			logger.error("get all project ids failed", re);
			throw re;
		}
		return allProjectIds;
	}
}

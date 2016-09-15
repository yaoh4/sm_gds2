package gov.nih.nci.cbiit.scimgmt.gds.dao;
// Generated Mar 7, 2016 1:12:03 PM by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.nih.nci.cbiit.scimgmt.gds.domain.GdsGrantsContracts;
import gov.nih.nci.cbiit.scimgmt.gds.domain.InstitutionalCertification;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectsVw;
import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;


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
	
	@Autowired
	protected NedPerson loggedOnUser;	
	
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
			if(id != null){
				//Already saved submission				
				sessionFactory.getCurrentSession().evict(sessionFactory.getCurrentSession().get(Project.class, id));
				detachedInstance.setLastChangedBy(loggedOnUser.getFullNameLF());				
			}
			else{
				//New submission
				detachedInstance.setCreatedBy(loggedOnUser.getFullNameLF());	
				detachedInstance.setCreatedDate(new Date());
			}
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
				Hibernate.initialize(instance.getPlanAnswerSelections());
				Hibernate.initialize(instance.getRepositoryStatuses());				
				Hibernate.initialize(instance.getPageStatuses());
				logger.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	
	/**
	 * Gets the IC by icId
	 * 
	 * @param icId
	 * @return
	 */
	public RepositoryStatus findRepositoryById(Long repoId) {
		logger.debug("getting RepositoryStatus instance with repoId: " + repoId);
		try {
			final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RepositoryStatus.class);
			criteria.add(Restrictions.eq("id", repoId));
			 RepositoryStatus repoStatus = (RepositoryStatus) criteria.uniqueResult();
			return repoStatus;
		} catch (RuntimeException re) {
			logger.error("Unable to find IC by id " + repoId, re);
			throw re;
		}
	}
	
	/**
	 * This method retrieves Grant / Contract List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<GdsGrantsContracts> getGrantOrContractList(String grantContractNum){

		logger.info("Retrieving  Grant / Contract List from DB for grantContractNum: "+grantContractNum);
		try {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GdsGrantsContracts.class);	
			criteria.add(Restrictions.ilike("lookupGrantContractNum", grantContractNum,MatchMode.ANYWHERE));
			List<GdsGrantsContracts> grantsListlist = criteria.list();
			
			//If multiple records exist then always pick the latest grant.
			if(grantsListlist.size() > 1){	
				criteria.add(Restrictions.eqProperty("lookupGrantContractNum","grantContractNum"));
				grantsListlist = criteria.list();
			}
			
			return grantsListlist;

		}catch (RuntimeException re) {
			logger.error("Retrieving  Grant / Contract List failed", re);
			throw re;
		}
	}
	
	/**
	 * This method returns grantContract for given applId
	 * @param applId
	 * @return
	 */
	public GdsGrantsContracts getGrantOrContract(Long applId){

		logger.info("Retrieving  Grant / Contract from DB for applId: "+applId);
		try {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GdsGrantsContracts.class);	
			criteria.add(Restrictions.eq("applId", applId));
			GdsGrantsContracts grantContract = (GdsGrantsContracts) criteria.uniqueResult();
			return grantContract;

		}catch (RuntimeException re) {
			logger.error("Retrieving  Grant / Contract failed", re);
			throw re;
		}
	}
	
	
	
	/**
	 * This method retrieves list of already linked submissions for a given grant.
	 * 
	 * @param grantContractNum
	 * @return
	 */
	public List<ProjectsVw> getPrevLinkedSubmissionsForGrant(String grantContractNum, String projectId){
		
		logger.info("Retrieving already linked submissions for grantContractNum: "+grantContractNum);
		try {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ProjectsVw.class);	
			criteria.add(Restrictions.ilike("grantContractNum", grantContractNum,MatchMode.ANYWHERE));
			if(!projectId.isEmpty()){
			criteria.add(Restrictions.ne("id", Long.valueOf(projectId)));
			criteria.add(Restrictions.or(
					   Restrictions.ne("parentProject.id",Long.valueOf(projectId)),
					   Restrictions.isNull("parentProject.id")));
			}
			List<ProjectsVw> grantsListlist = criteria.list();
			return grantsListlist;

		}catch (RuntimeException re) {
			logger.error("Error occurred while retrieving already linked submissions for grantContractNum: "+grantContractNum, re);
			throw re;
		}
	}
	
	/**
	 * Retrieve Sub-projects based on parent project ID.
	 * @param parentProjectId
	 * @return List<Project>
	 */
	@SuppressWarnings("unchecked")
	public List<Project> getSubprojects(Long parentProjectId) {
		
		List<Project> list =  new ArrayList<Project>();
		
		try {
			Criteria criteria = null;
			criteria = sessionFactory.getCurrentSession().createCriteria(Project.class);
			criteria.add(Restrictions.eq("subprojectFlag", "Y"));
			criteria.add(Restrictions.eq("parentProjectId", parentProjectId));
			list =  (List<Project>) criteria.list();
			return list;
			
		} catch (Throwable e) {
			logger.error("Error while searching for subproject submission ", e);
			logger.error("user ID: " + loggedOnUser.getAdUserId() + "/" + loggedOnUser.getFullName());
			logger.error("Pass-in parameters: Parent ProjectId - " + parentProjectId);
			logger.error("Outgoing parameters: Subproject List - " + list);
			
			throw e;
		}
	}
	
	
	/**
	 * Retrieve Sub-projects based on parent project ID.
	 * @param parentProjectId
	 * @return List<Project>
	 */
	@SuppressWarnings("unchecked")
	public List<Project> getVersions(Long projectGroupId) {
		
		List<Project> list =  new ArrayList<Project>();
		
		try {
			Criteria criteria = null;
			criteria = sessionFactory.getCurrentSession().createCriteria(Project.class);
			criteria.add(Restrictions.eq("projectGroupId", projectGroupId));
			list =  (List<Project>) criteria.list();
			return list;
			
		} catch (Throwable e) {
			logger.error("Error while searching for project versions ", e);
			logger.error("user ID: " + loggedOnUser.getAdUserId() + "/" + loggedOnUser.getFullName());
			logger.error("Pass-in parameters: projectGroupId - " + projectGroupId);
			logger.error("Outgoing parameters: Version List - " + list);
			
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getSubOrgList(String sacCode) {
		
		List<String> result = null;
		Session session = sessionFactory.getCurrentSession();
		try {		
			final String hql = "select ltrim(sys_connect_by_path(nihOuAcronym,'/'),'/')"
				+ " from ned_orgunit orgunit where orgunit.inactive_date is null"
				+ " start with orgunit.nihsac = ?"
				+ " connect by prior orgunit.nihsac = orgunit.nihparentsac";
			
			SQLQuery query = session.createSQLQuery(hql);
			query.setString(0, sacCode);

			result = query.list();

		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return result;
	}
}

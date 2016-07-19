package gov.nih.nci.cbiit.scimgmt.gds.dao;
// Generated Mar 7, 2016 1:12:03 PM by Hibernate Tools 4.0.0

import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PageStatus;
import gov.nih.nci.cbiit.scimgmt.gds.model.Submission;
import gov.nih.nci.cbiit.scimgmt.gds.model.SubmissionSearchCriteria;
import gov.nih.nci.cbiit.scimgmt.gds.model.SubmissionSearchResult;

/**
 * Dao object for Searching Project, Subprojects.
 * @see gov.nih.nci.cbiit.scimgmt.gds.domain.Project
 * @author Hibernate Tools
 */
@Component
public class ProjectSearchDao {

	private static final Logger logger = LogManager.getLogger(ProjectSearchDao.class);

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
	
	/**
	 * Search Project Submission based on Criteria.
	 * @param searchCriteria
	 * @return List<Project>
	 */
	@SuppressWarnings("unchecked")
	public SubmissionSearchResult search(SubmissionSearchCriteria searchCriteria) {
		logger.debug("searching for project submission : " + searchCriteria);
		List<Project> list = null;
		try {
	  
			Criteria criteria = null;
			criteria = sessionFactory.getCurrentSession().createCriteria(Project.class, "project");
			int totalRecords = 0;
			
			// Sort order
			criteria = addSortOrder(criteria, searchCriteria);
			
			// Add user specific search criteria
			addSearchCriteria(criteria, searchCriteria);
			
			// Add pagination and retrieve data
			if (searchCriteria.getLength() == -1) {
				list = criteria.list();
			} else {
				list =  (List<Project>) criteria.setFirstResult(searchCriteria.getStart())
						.setMaxResults(searchCriteria.getLength())
						.list();
				totalRecords = getTotalResultCount(criteria);
			}
			
			// Convert list to submission and set total records
			if(list != null && !list.isEmpty()) {
				SubmissionSearchResult result = new SubmissionSearchResult();
				List<Submission> submissions = new ArrayList<Submission>();
				for(Project p: list) {
					Submission s = new Submission();
					BeanUtils.copyProperties(p, s);
					for(PageStatus status: p.getPageStatuses()) {
						s.setGdsPlanStatus(status.getStatus().getDisplayName());
						s.setBsiStatus(status.getStatus().getDisplayName());
						s.setIcStatus(status.getStatus().getDisplayName());
						s.setDataSharingException(status.getStatus().getDisplayName());
					}
					submissions.add(s);
				}
				result.setData(submissions);
				if(searchCriteria.getLength() == -1) {
					result.setRecordsFiltered(submissions.size());
					result.setRecordsTotal(submissions.size());
				} else {
					result.setRecordsFiltered(totalRecords);
					result.setRecordsTotal(totalRecords);
				}
				return result;
			}
			
			return null;

		} catch (Throwable e) {
			logger.error("Error while searching for project submission ", e);
			logger.error("user ID: " + loggedOnUser.getAdUserId() + "/" + loggedOnUser.getFullName());
			logger.error("Pass-in parameters: searchCriteria - " + searchCriteria);
			logger.error("Outgoing parameters: Project List - " + list);
			
			throw e;
		}
	}
	
	/**
	 * Gets the total result count.
	 * 
	 * @param criteria
	 *            the criteria
	 * @return the total result count
	 */
	private int getTotalResultCount(Criteria criteria) {

		criteria.setMaxResults(0);
		criteria.setFirstResult(0);
		criteria.setProjection(Projections.rowCount());
		Long rowCount = (Long) criteria.uniqueResult();
		return rowCount.intValue();

	}
	
	/**
	 * Retrieve Sub-projects based on parent project ID.
	 * @param parentProjectId
	 * @return List<Project>
	 */
	@SuppressWarnings("unchecked")
	public List<Project> getSubprojects(Long parentProjectId) {
		
		List<Project> list = null;
		
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

	/**
	 * Adding user specific search criteria
	 * 
	 * @param criteria
	 * @param searchCriteria
	 * @return
	 */
	private Criteria addSearchCriteria(Criteria criteria, SubmissionSearchCriteria searchCriteria) {
		logger.debug("adding search criteria for project submission search: " + searchCriteria);

		criteria.add(Restrictions.ne("subprojectFlag", "Y"));
		
		// My DOC
		if (!StringUtils.isBlank(StringUtils.trim(searchCriteria.getDoc()))) {
			criteria.add(Restrictions.eq("docAbbreviation", searchCriteria.getDoc()));
		}
		
		// Program Director or My Project Submissions
		if (!StringUtils.isBlank(StringUtils.trim(searchCriteria.getPdLastName()))) {
			criteria.add(Restrictions.ilike("pdLastName", searchCriteria.getPdLastName().trim(), MatchMode.EXACT));
		}
		if (!StringUtils.isBlank(StringUtils.trim(searchCriteria.getPdFirstName()))) {
			criteria.add(Restrictions.ilike("pdFirstName", searchCriteria.getPdFirstName().trim(), MatchMode.EXACT));
		}
		
		// Project/Subproject Title partial search
		if (!StringUtils.isBlank(StringUtils.trim(searchCriteria.getProjectTitle()))) {
			criteria.add(Restrictions.ilike("projectTitle", searchCriteria.getProjectTitle().trim(), MatchMode.ANYWHERE));
		}
		
		// Principal Investigator first or last name partial search
		if (!StringUtils.isBlank(StringUtils.trim(searchCriteria.getPiFirstOrLastName()))) {
			Disjunction dc = Restrictions.disjunction();
			dc.add(Restrictions.ilike("piFirstName", searchCriteria.getPiFirstOrLastName().trim(), MatchMode.ANYWHERE));
			dc.add(Restrictions.ilike("piLastName", searchCriteria.getPiFirstOrLastName().trim(), MatchMode.ANYWHERE));
			criteria.add(dc);
		}
		
		// Intramural(Z01)/Grant/Contract #
		if (!StringUtils.isBlank(StringUtils.trim(searchCriteria.getApplicationNum()))) {
			criteria.add(Restrictions.ilike("applicationNum", searchCriteria.getApplicationNum().trim(), MatchMode.ANYWHERE));
		}

		// Accession Number
		if (!StringUtils.isBlank(StringUtils.trim(searchCriteria.getAccessionNumber()))) {
			criteria.createCriteria("project.planAnswerSelection" , "planAnswerSelection");
			criteria.createCriteria("planAnswerSelection.repositoryStatuses" , "repositoryStatuses");
			criteria.add(Restrictions.eq("repositoryStatuses.accessionNumber", searchCriteria.getAccessionNumber().trim()));
		}

		return criteria;
	}
	
	/**
	 * Add Sort Order
	 * 
	 * @param criteria
	 * @return
	 */
	private Criteria addSortOrder(Criteria criteria, SubmissionSearchCriteria searchCriteria) {

		if(StringUtils.isNotBlank(searchCriteria.getSortBy())) {
			if(StringUtils.equalsIgnoreCase(searchCriteria.getSortDir(), "asc"))
				criteria.addOrder(Order.asc(searchCriteria.getSortBy()));
			else
				criteria.addOrder(Order.desc(searchCriteria.getSortBy()));
		}	
		
		return criteria;
	}
}

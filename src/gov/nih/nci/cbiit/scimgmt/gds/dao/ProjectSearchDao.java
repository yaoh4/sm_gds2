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
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectsVw;
import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;
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
		List<ProjectsVw> list = null;
		try {
	  
			Criteria criteria = null;
			criteria = sessionFactory.getCurrentSession().createCriteria(ProjectsVw.class, "project");
			int totalRecords = 0;
			
			// Sort order
			criteria = addSortOrder(criteria, searchCriteria);
			
			// Add user specific search criteria
			addSearchCriteria(criteria, searchCriteria);
			criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
			
			// Add pagination and retrieve data
			if (searchCriteria.getLength() == -1) {
				list = criteria.list();
			} else {
				list =  (List<ProjectsVw>) criteria.setFirstResult(searchCriteria.getStart())
						.setMaxResults(searchCriteria.getLength())
						.list();
				totalRecords = getTotalResultCount(criteria, searchCriteria);
			}
			
			// Retrieve subproject matches
			List<Long> projectIdsContainingMatchingSubproject = new ArrayList<Long>();
			if(StringUtils.isBlank(searchCriteria.getSelectedTypeOfProject()) &&
					(searchCriteria.getPdNpnId() != null ||
					!StringUtils.isBlank(StringUtils.trim(searchCriteria.getProjectTitle())) || 
					!StringUtils.isBlank(StringUtils.trim(searchCriteria.getPiFirstOrLastName())) ||
					!StringUtils.isBlank(StringUtils.trim(searchCriteria.getGrantContractNum())))) {
				criteria = sessionFactory.getCurrentSession().createCriteria(ProjectsVw.class, "project");
				addContainingMatchingSubprojectCriteria(criteria, searchCriteria);
				criteria.setProjection(Property.forName("id"));
				criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
				projectIdsContainingMatchingSubproject = (List<Long>)criteria.list();
			}
			
			// Convert list to submission and set total records
			if(list != null && !list.isEmpty()) {
				SubmissionSearchResult result = new SubmissionSearchResult();
				List<Submission> submissions = new ArrayList<Submission>();
				for(ProjectsVw p: list) {
					Submission s = new Submission();
					BeanUtils.copyProperties(p, s);
					if(projectIdsContainingMatchingSubproject.contains(s.getId()))
						s.setExpandSubproject(true);
					if(StringUtils.isNotBlank(searchCriteria.getAccessionNumber())) {
						for(RepositoryStatus r: p.getRepositoryStatuses()) {
							if(StringUtils.containsIgnoreCase(r.getAccessionNumber(), searchCriteria.getAccessionNumber())) {
								s.setExpandRepository(true);
								break;
							}
						}
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
	private int getTotalResultCount(Criteria criteria, SubmissionSearchCriteria searchCriteria) {

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
	public List<ProjectsVw> getSubprojects(Long parentProjectId) {
		
		List<ProjectsVw> list = null;
		
		try {
			Criteria criteria = null;
			criteria = sessionFactory.getCurrentSession().createCriteria(ProjectsVw.class);
			criteria.add(Restrictions.eq("subprojectFlag", "Y"));
			criteria.add(Restrictions.eq("parentProject.id", parentProjectId));
			list =  (List<ProjectsVw>) criteria.list();
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

		boolean accessionNumberSearch = StringUtils.isNotBlank(StringUtils.trim(searchCriteria.getAccessionNumber()));
		
		//If search criteria has accession number, we need a detached parent criteria
		DetachedCriteria parentDetachedCriteria = DetachedCriteria.forClass(ProjectsVw.class,"project");
		Conjunction parentCriteria = Restrictions.conjunction();

		//Need to search for parent project latest and sub project latest
		DetachedCriteria subprojectCriteria = DetachedCriteria.forClass(ProjectsVw.class,"subproject");
		
		parentCriteria.add(Restrictions.ne("subprojectFlag", "Y"));
		parentCriteria.add(Restrictions.eq("latestVersionFlag", "Y"));
		parentDetachedCriteria.add(Restrictions.ne("subprojectFlag", "Y"));
		parentDetachedCriteria.add(Restrictions.eq("latestVersionFlag", "Y"));

		subprojectCriteria.add(Restrictions.eq("subprojectFlag", "Y"));
		subprojectCriteria.add(Restrictions.eq("latestVersionFlag", "Y"));
		
		if(StringUtils.equals(searchCriteria.getParentSearch(), "Y")) {
			parentCriteria.add(Restrictions.eq("subprojectEligibleFlag", "Y"));
			parentDetachedCriteria.add(Restrictions.eq("subprojectEligibleFlag", "Y"));
		}
		if(StringUtils.equals(searchCriteria.getSelectedTypeOfProject(), ApplicationConstants.SUBMISSION_TYPE_NEW_VERSION_PROJECT)) {
			parentCriteria.add(Restrictions.eq("projectStatusCode", "COMPLETED"));
			parentDetachedCriteria.add(Restrictions.eq("projectStatusCode", "COMPLETED"));
		}
		if(StringUtils.equals(searchCriteria.getSelectedTypeOfProject(), ApplicationConstants.SUBMISSION_TYPE_NEW_VERSION_SUBPROJECT)) {
			subprojectCriteria.add(Restrictions.eq("projectStatusCode", "COMPLETED"));
		}

		// My DOC
		if (!StringUtils.isBlank(StringUtils.trim(searchCriteria.getDoc()))) {
			parentCriteria.add(Restrictions.eq("docAbbreviation", searchCriteria.getDoc()));
			parentDetachedCriteria.add(Restrictions.eq("docAbbreviation", searchCriteria.getDoc()));
			subprojectCriteria.add(Restrictions.eq("docAbbreviation", searchCriteria.getDoc()));
		}
		
		// Program Director or My Submissions
		if(searchCriteria.getPdNpnId() != null) {
			// PD search
			Disjunction dc = Restrictions.disjunction();
			dc.add(Restrictions.eq("extPdNpnId", searchCriteria.getPdNpnId()));
			dc.add(Restrictions.eq("intPdNpnId", searchCriteria.getPdNpnId()));
			parentCriteria.add(dc);
			parentDetachedCriteria.add(dc);
			subprojectCriteria.add(dc);
		} else {
			if (!StringUtils.isBlank(StringUtils.trim(searchCriteria.getPdLastName()))) {
				Disjunction dc = Restrictions.disjunction();
				dc.add(Restrictions.ilike("extPdLastName", searchCriteria.getPdLastName().trim(), MatchMode.EXACT));
				dc.add(Restrictions.ilike("intPdLastName", searchCriteria.getPdLastName().trim(), MatchMode.EXACT));
				parentCriteria.add(dc);
				parentDetachedCriteria.add(dc);
				subprojectCriteria.add(dc);
			}
			if (!StringUtils.isBlank(StringUtils.trim(searchCriteria.getPdFirstName()))) {
				Disjunction dc = Restrictions.disjunction();
				dc.add(Restrictions.ilike("extPdFirstName", searchCriteria.getPdFirstName().trim(), MatchMode.EXACT));
				dc.add(Restrictions.ilike("intPdFirstName", searchCriteria.getPdFirstName().trim(), MatchMode.EXACT));
				parentCriteria.add(dc);
				parentDetachedCriteria.add(dc);
				subprojectCriteria.add(dc);
			}
		}
		
		// My Created Submissions
		if(searchCriteria.getSubmissionFromId() == ApplicationConstants.SEARCH_MY_CREATED_SUBMISSIONS) {
			parentCriteria.add(Restrictions.ilike("createdBy", loggedOnUser.getAdUserId(), MatchMode.ANYWHERE));
			parentDetachedCriteria.add(Restrictions.ilike("createdBy", loggedOnUser.getAdUserId(), MatchMode.ANYWHERE));
			subprojectCriteria.add(Restrictions.ilike("createdBy", loggedOnUser.getAdUserId(), MatchMode.ANYWHERE));
		}
		
		// Project/Subproject Title partial search
		if (!StringUtils.isBlank(StringUtils.trim(searchCriteria.getProjectTitle()))) {
			parentCriteria.add(Restrictions.ilike("projectSubmissionTitle", searchCriteria.getProjectTitle().trim(), MatchMode.ANYWHERE));
			parentDetachedCriteria.add(Restrictions.ilike("projectSubmissionTitle", searchCriteria.getProjectTitle().trim(), MatchMode.ANYWHERE));
			subprojectCriteria.add(Restrictions.ilike("projectSubmissionTitle", searchCriteria.getProjectTitle().trim(), MatchMode.ANYWHERE));
		}
		
		// Principal Investigator first or last name partial search
		if (!StringUtils.isBlank(StringUtils.trim(searchCriteria.getPiFirstOrLastName()))) {
			Disjunction dc = Restrictions.disjunction();
			dc.add(Restrictions.ilike("extPiFirstName", searchCriteria.getPiFirstOrLastName().trim(), MatchMode.ANYWHERE));
			dc.add(Restrictions.ilike("extPiLastName", searchCriteria.getPiFirstOrLastName().trim(), MatchMode.ANYWHERE));
			dc.add(Restrictions.ilike("intPiFirstName", searchCriteria.getPiFirstOrLastName().trim(), MatchMode.ANYWHERE));
			dc.add(Restrictions.ilike("intPiLastName", searchCriteria.getPiFirstOrLastName().trim(), MatchMode.ANYWHERE));
			parentCriteria.add(dc);
			parentDetachedCriteria.add(dc);
			subprojectCriteria.add(dc);
		}
		
		// Intramural(Z01)/Grant/Contract #
		if (!StringUtils.isBlank(StringUtils.trim(searchCriteria.getGrantContractNum()))) {
			Disjunction dc = Restrictions.disjunction();
			dc.add(Restrictions.ilike("extGrantContractNum", searchCriteria.getGrantContractNum().trim(), MatchMode.ANYWHERE));
			dc.add(Restrictions.ilike("intGrantContractNum", searchCriteria.getGrantContractNum().trim(), MatchMode.ANYWHERE));
			parentCriteria.add(dc);
			parentDetachedCriteria.add(dc);
			subprojectCriteria.add(dc);
		}

		// Accession Number
		if (!StringUtils.isBlank(StringUtils.trim(searchCriteria.getAccessionNumber()))) {
			parentDetachedCriteria.createAlias("project.repositoryStatuses" , "repositoryStatuses");
			parentDetachedCriteria.add(Restrictions.eq("repositoryStatuses.accessionNumber", searchCriteria.getAccessionNumber().trim()));
			subprojectCriteria.createCriteria("subproject.repositoryStatuses" , "repositoryStatuses");
			subprojectCriteria.add(Restrictions.eq("repositoryStatuses.accessionNumber", searchCriteria.getAccessionNumber().trim()));
		}
		
		// Reason for Submission
		if (searchCriteria.getSubmissionReasonId() != null) {
			parentCriteria.add(Restrictions.eq("submissionReasonId", searchCriteria.getSubmissionReasonId()));
			parentDetachedCriteria.add(Restrictions.eq("submissionReasonId", searchCriteria.getSubmissionReasonId()));
			subprojectCriteria.add(Restrictions.eq("submissionReasonId", searchCriteria.getSubmissionReasonId()));
		}
		
		// excludeCompleted
		if (searchCriteria.isExcludeCompleted()) {
			parentCriteria.add(Restrictions.ne("projectStatusCode", ApplicationConstants.PAGE_STATUS_CODE_COMPLETED));
			parentDetachedCriteria.add(Restrictions.ne("projectStatusCode", ApplicationConstants.PAGE_STATUS_CODE_COMPLETED));
			subprojectCriteria.add(Restrictions.ne("projectStatusCode", ApplicationConstants.PAGE_STATUS_CODE_COMPLETED));
		}
				
		Disjunction dc = Restrictions.disjunction();
		if(!StringUtils.equals(searchCriteria.getSelectedTypeOfProject(), ApplicationConstants.SUBMISSION_TYPE_NEW_VERSION_SUBPROJECT)
			&& !accessionNumberSearch) {
			dc.add(parentCriteria);
		}
		else if(!StringUtils.equals(searchCriteria.getSelectedTypeOfProject(), ApplicationConstants.SUBMISSION_TYPE_NEW_VERSION_SUBPROJECT)) {
			dc.add(Subqueries.propertyIn("id", parentDetachedCriteria.setProjection(Projections.property("project.id"))));
		}
		if(StringUtils.isBlank(searchCriteria.getSelectedTypeOfProject()) ||
				StringUtils.equals(searchCriteria.getSelectedTypeOfProject(), ApplicationConstants.SUBMISSION_TYPE_NEW_VERSION_SUBPROJECT)) {
			dc.add(Subqueries.propertyIn("id", subprojectCriteria.setProjection(Projections.property("subproject.parentProject.id"))));
		}
		criteria.add(dc);

		return criteria;
	}
	
	/**
	 * Adding user specific search criteria to retrieve subproject only
	 * 
	 * @param criteria
	 * @param searchCriteria
	 * @return
	 */
	private Criteria addContainingMatchingSubprojectCriteria(Criteria criteria, SubmissionSearchCriteria searchCriteria) {
		logger.debug("adding search criteria for project submission search for projects containing matching subprojects: " + searchCriteria);

		//Need to search for parent project latest and sub project latest
		DetachedCriteria subprojectCriteria = DetachedCriteria.forClass(ProjectsVw.class,"subproject");
		
		subprojectCriteria.add(Restrictions.eq("subprojectFlag", "Y"));
		
		if(StringUtils.equals(searchCriteria.getParentSearch(), "Y")) {
			subprojectCriteria.add(Restrictions.eq("subprojectEligibleFlag", "Y"));
		}

		// My DOC
		if (!StringUtils.isBlank(StringUtils.trim(searchCriteria.getDoc()))) {
			subprojectCriteria.add(Restrictions.eq("docAbbreviation", searchCriteria.getDoc()));
		}
		
		// Program Director or My Project Submissions
		if(searchCriteria.getPdNpnId() != null) {
			// PD search
			Disjunction dc = Restrictions.disjunction();
			dc.add(Restrictions.eq("extPdNpnId", searchCriteria.getPdNpnId()));
			dc.add(Restrictions.eq("intPdNpnId", searchCriteria.getPdNpnId()));
			Conjunction c = Restrictions.conjunction();
			if (!StringUtils.isBlank(StringUtils.trim(searchCriteria.getPdLastName()))) {
				Disjunction d = Restrictions.disjunction();
				d.add(Restrictions.ilike("extPdLastName", searchCriteria.getPdLastName().trim(), MatchMode.EXACT));
				d.add(Restrictions.ilike("intPdLastName", searchCriteria.getPdLastName().trim(), MatchMode.EXACT));
				c.add(d);
			}
			if (!StringUtils.isBlank(StringUtils.trim(searchCriteria.getPdFirstName()))) {
				Disjunction d = Restrictions.disjunction();
				d.add(Restrictions.ilike("extPdFirstName", searchCriteria.getPdFirstName().trim(), MatchMode.EXACT));
				d.add(Restrictions.ilike("intPdFirstName", searchCriteria.getPdFirstName().trim(), MatchMode.EXACT));
				c.add(d);
			}
			dc.add(c);
			subprojectCriteria.add(dc);
		} else {
			if (!StringUtils.isBlank(StringUtils.trim(searchCriteria.getPdLastName()))) {
				Disjunction dc = Restrictions.disjunction();
				dc.add(Restrictions.ilike("extPdLastName", searchCriteria.getPdLastName().trim(), MatchMode.EXACT));
				dc.add(Restrictions.ilike("intPdLastName", searchCriteria.getPdLastName().trim(), MatchMode.EXACT));
				subprojectCriteria.add(dc);
			}
			if (!StringUtils.isBlank(StringUtils.trim(searchCriteria.getPdFirstName()))) {
				Disjunction dc = Restrictions.disjunction();
				dc.add(Restrictions.ilike("extPdFirstName", searchCriteria.getPdFirstName().trim(), MatchMode.EXACT));
				dc.add(Restrictions.ilike("intPdFirstName", searchCriteria.getPdFirstName().trim(), MatchMode.EXACT));
				subprojectCriteria.add(dc);
			}
		}
		
		// Project/Subproject Title partial search
		if (!StringUtils.isBlank(StringUtils.trim(searchCriteria.getProjectTitle()))) {
			subprojectCriteria.add(Restrictions.ilike("projectSubmissionTitle", searchCriteria.getProjectTitle().trim(), MatchMode.ANYWHERE));
		}
		
		// Principal Investigator first or last name partial search
		if (!StringUtils.isBlank(StringUtils.trim(searchCriteria.getPiFirstOrLastName()))) {
			Disjunction dc = Restrictions.disjunction();
			dc.add(Restrictions.ilike("extPiFirstName", searchCriteria.getPiFirstOrLastName().trim(), MatchMode.ANYWHERE));
			dc.add(Restrictions.ilike("extPiLastName", searchCriteria.getPiFirstOrLastName().trim(), MatchMode.ANYWHERE));
			dc.add(Restrictions.ilike("intPiFirstName", searchCriteria.getPiFirstOrLastName().trim(), MatchMode.ANYWHERE));
			dc.add(Restrictions.ilike("intPiLastName", searchCriteria.getPiFirstOrLastName().trim(), MatchMode.ANYWHERE));
			subprojectCriteria.add(dc);
		}
		
		// Intramural(Z01)/Grant/Contract #
		if (!StringUtils.isBlank(StringUtils.trim(searchCriteria.getGrantContractNum()))) {
			Disjunction dc = Restrictions.disjunction();
			dc.add(Restrictions.ilike("extGrantContractNum", searchCriteria.getGrantContractNum().trim(), MatchMode.ANYWHERE));
			dc.add(Restrictions.ilike("intGrantContractNum", searchCriteria.getGrantContractNum().trim(), MatchMode.ANYWHERE));
			subprojectCriteria.add(dc);
		}

		// Accession Number
		if (!StringUtils.isBlank(StringUtils.trim(searchCriteria.getAccessionNumber()))) {
			subprojectCriteria.createCriteria("subproject.repositoryStatuses" , "repositoryStatuses");
			subprojectCriteria.add(Restrictions.eq("repositoryStatuses.accessionNumber", searchCriteria.getAccessionNumber().trim()));
		}
		
		// excludeCompleted
		if (searchCriteria.isExcludeCompleted()) {
			subprojectCriteria.add(Restrictions.ne("projectStatusCode", ApplicationConstants.PAGE_STATUS_CODE_COMPLETED));
		}
				
		criteria.add(Subqueries.propertyIn("id", subprojectCriteria.setProjection(Projections.property("subproject.parentProject.id"))));

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

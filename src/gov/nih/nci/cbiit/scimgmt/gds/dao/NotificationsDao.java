/**
 * 
 */
package gov.nih.nci.cbiit.scimgmt.gds.dao;

import gov.nih.nci.cbiit.scimgmt.gds.domain.EmailNotification;
import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectsVw;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.plexus.util.StringUtils;


/**
 * @author dinhys
 * 
 */
@Component
public class NotificationsDao {

		
		private static final Logger logger = LogManager.getLogger(NotificationsDao.class);

		//Used for Extramural
		private static final String QUERY_PAST_PROJECT_END_DATE = "select v " 
			+ "from ProjectsVw v, ProjectGrantContract g "
			+ "where v.projectEndDate < trunc(sysdate) "
			+ "and exists (select 1 from PageStatus s where s.project.id = v.id and s.status.id in (46,47))"
			+ "and g.project.id=v.id and g.primaryGrantContractFlag='Y' and v.latestVersionFlag='Y'";
		
		//Used for Extramural
		private static final String QUERY_PROJECT_END_DATE_DUE_IN_X_DAYS = "select v " 
			+ "from ProjectsVw v, ProjectGrantContract g "
			+ "where v.projectEndDate - trunc(sysdate) <= :days and v.projectEndDate > trunc(sysdate) "
			+ "and exists (select 1 from PageStatus s where s.project.id = v.id and s.status.id in (46,47))"
			+ "and g.project.id=v.id and g.primaryGrantContractFlag='Y' and v.latestVersionFlag='Y' ";
		
		//Used for Extramural and Intramural
		private static final String QUERY_PAST_SUBMISSION_DATE = "select v " 
			+ "from ProjectsVw v, PageStatus s, ProjectGrantContract g "
			+ "where s.project.id = v.id and v.anticipatedSubmissionDate < trunc(sysdate) "
			+ "and g.project.id=v.id and g.primaryGrantContractFlag='Y' and v.latestVersionFlag='Y' "
			+ "and s.page.id=50 and s.status.id in (46,47)";

		//Used for Intramural
		private static final String QUERY_BSI_INPROGRESS_DUE_IN_A_WEEK = "select v " 
			+ "from ProjectsVw v, PageStatus s, ProjectGrantContract g "
			+ "where s.project.id = v.id and s.page.id=49 and v.latestVersionFlag='Y' "
			+ "and s.status.id in (46,47) and v.anticipatedSubmissionDate - trunc(sysdate) <= 7 "
			+ "and g.project.id=v.id and v.anticipatedSubmissionDate > trunc(sysdate) and g.primaryGrantContractFlag='Y' ";
		
		//Used for Extramural
		private static final String QUERY_BSI_INPROGRESS_DUE_IN_A_MONTH = "select v " 
				+ "from ProjectsVw v, PageStatus s, ProjectGrantContract g "
				+ "where s.project.id = v.id and s.page.id=49 and v.latestVersionFlag='Y' "
				+ "and s.status.id in (46,47) and v.anticipatedSubmissionDate - trunc(sysdate) <= 30 "
				+ "and g.project.id=v.id and v.anticipatedSubmissionDate > trunc(sysdate) and g.primaryGrantContractFlag='Y' ";
	
		//Used for Extramural
		private static final String QUERY_GDS_IC_INPROGRESS_FOR_90_DAYS_SINCE_PROJECT_START_DATE = "select v " 
			+ "from ProjectsVw v, PageStatus gds, PageStatus ic, ProjectGrantContract g "
			+ "where gds.project.id = v.id and ic.project.id = v.id and v.latestVersionFlag='Y' "
			+ "and gds.page.id=1 and ic.page.id=2 and (gds.status.id in (46,47) or ic.status.id in (46,47)) "
			+ "and g.project.id=v.id and sysdate - v.projectStartDate >= 90 and g.primaryGrantContractFlag='Y' ";
		
		//Used for Intramural
		private static final String QUERY_GDS_IC_INPROGRESS_FOR_90_DAYS_SINCE_CREATED_DATE = "select v " 
			+ "from ProjectsVw v, PageStatus gds, PageStatus ic, ProjectGrantContract g "
			+ "where gds.project.id = v.id and ic.project.id = v.id and v.latestVersionFlag='Y' "
			+ "and gds.page.id=1 and ic.page.id=2 and (gds.status.id in (46,47) or ic.status.id in (46,47)) "
			+ "and g.project.id=v.id and sysdate - v.createdDate >= 90 and g.primaryGrantContractFlag='Y' ";
		
		//Used for Extramural
		private static final String QUERY_BSI_INPROGRESS_BUDGET_END_IN_45_DAYS = "select v " 
				+ "from ProjectsVw v, PageStatus s, ProjectGrantContract g, GdsGrantsContracts mv "
				+ "where s.project.id = v.id "
				+ "and mv.applId=g.applId and mv.lookupGrantContractNum=g.grantContractNum "
				+ "and s.page.id=49 and s.status.id in (46,47) "
				+ "and mv.budgetEndDate - trunc(sysdate) <= 45 and mv.budgetEndDate > trunc(sysdate) "
				+ "and g.project.id=v.id and g.primaryGrantContractFlag='Y' and v.latestVersionFlag='Y' ";
		
		@Autowired
		private SessionFactory sessionFactory;
		
		/**
		 * Extramural Notifications
		 * 
		 * i. Anticipated Submission Date is in the past
		 *    and the Project Submission Status is Not Started or In Process
		 * 
		 *    Projects and Subprojects are retrieved.
		 * @return
		 */
		public List<ProjectsVw> getExtramuralPastSubmissionDate() {
			
			logger.info("Retrieving all Extramural Submissions where Anticipated Submission Date is in the past" +
						" and the Project Submission Status is Not Started or In Process.");
			String hql  = QUERY_PAST_SUBMISSION_DATE + "and g.grantContractType = 'Extramural'";
			List<ProjectsVw> submissions = sessionFactory.getCurrentSession().createQuery(hql).list();
					
			return submissions;
			
		}
		
		/**
		 * Extramural Notifications
		 * 
		 * ii. Basic Study Information is In Progress
		 *     and Anticipated Submission Date is one week from today.
		 * 
		 *     Projects and Subprojects are retrieved.
		 * @return
		 */
		public List<ProjectsVw> getExtramuralBsiInProgress() {
			
			logger.info("Retrieving all Extramural Submissions where Basic Study Information is In Progress" +
						" and Anticipated Submission Date is one week from today.");
			String hql  = QUERY_BSI_INPROGRESS_DUE_IN_A_MONTH + "and g.grantContractType = 'Extramural'";
			List<ProjectsVw> submissions = sessionFactory.getCurrentSession().createQuery(hql).list();
					
			return submissions;
			
		}
		
		/**
		 * Extramural Notifications
		 * 
		 * iii. BSI is In Progress status and
		 *      the budget end date associated to the latest funded extramural grant is 45 days from today.
		 * 
		 *      Projects and Subprojects are retrieved.
		 * @return
		 */
		public List<ProjectsVw> getExtramuralBudgetEndDateComing() {
			
			logger.info("Retrieving all Extramural Submissions where BSI is In Progress status" + 
						" and the budget end date associated to the latest funded extramural grant is 45 days from today.");
			String hql  = QUERY_BSI_INPROGRESS_BUDGET_END_IN_45_DAYS + "and g.grantContractType = 'Extramural'";
			List<ProjectsVw> submissions = sessionFactory.getCurrentSession().createQuery(hql).list();
					
			return submissions;
			
		}
		
		/**
		 * Extramural Notifications
		 * 
		 * iv. GDS Plan or IC is In Progress status and
		 *      the Project Start Date is past 3 months (90 days) as of today.
		 * 
		 *      Projects and Subprojects are retrieved.
		 * @return
		 */
		public List<ProjectsVw> getExtramuralGdsIcInProgress() {
			
			logger.info("Retrieving all Extramural Submissions where GDS Plan or IC is In Progress status" + 
						" the Project Start Date is past 3 months (90 days) as of today.");
			String hql  = QUERY_GDS_IC_INPROGRESS_FOR_90_DAYS_SINCE_PROJECT_START_DATE + "and g.grantContractType = 'Extramural'";
			List<ProjectsVw> submissions = sessionFactory.getCurrentSession().createQuery(hql).list();
					
			return submissions;
			
		}
		
		/**
		 * Extramural Notifications
		 * 
		 * v.	The Grant Project End Date is in the past and 
		 * 		at least one milestone is not in "Completed" status.
		 * 
		 *      Projects and Subprojects are retrieved.
		 * @return
		 */
		public List<ProjectsVw> getExtramuralPastProjectEndDate() {
			
			logger.info("Retrieving all Extramural Submissions where the Grant Project End Date is in the past" + 
						" and at least one milestone is not in Completed status");
			String hql  = QUERY_PAST_PROJECT_END_DATE + "and g.grantContractType = 'Extramural'";
			List<ProjectsVw> submissions = sessionFactory.getCurrentSession().createQuery(hql).list();
					
			return submissions;
			
		}
		
		/**
		 * Extramural Notifications
		 * 
		 * vi.	The Grant Project End Date is within 3 months from today 
		 * 		and at least one milestone is not in "Completed" status.
		 * 
		 *      Projects and Subprojects are retrieved.
		 * @return
		 */
		public List<ProjectsVw> getExtramuralProjectEndDateComing(Integer days) {
			
			logger.info("Retrieving all Extramural Submissions where the Grant Project End Date is within "+ days +" days from today" + 
						" and at least one milestone is not in Completed status");
			String hql  = QUERY_PROJECT_END_DATE_DUE_IN_X_DAYS + "and g.grantContractType = 'Extramural'";
			List<ProjectsVw> submissions = sessionFactory.getCurrentSession().createQuery(hql).setString("days", days.toString()).list();
					
			return submissions;
			
		}
		
		/**
		 * Intramural Notifications
		 * 
		 * i. GDS Plan or IC is In Progress for 3 months since the Project Submission has been created.
		 * 
		 * Projects and Subprojects are retrieved.
		 * @return
		 */
		public List<ProjectsVw> getIntramuralGdsIcInProgress() {
			
			logger.info("Retrieving all Intramural Submissions where GDS Plan or IC is In Progress" +
						" for 3 months since the Project Submission has been created.");
			String hql  = QUERY_GDS_IC_INPROGRESS_FOR_90_DAYS_SINCE_CREATED_DATE + "and g.grantContractType = 'Intramural'";
			List<ProjectsVw> submissions = sessionFactory.getCurrentSession().createQuery(hql).list();
					
			return submissions;
			
		}
		
		/**
		 * Intramural Notifications
		 * 
		 * ii. Anticipated Submission Date is in the past
		 * and the Project Submission Status is Not Started or In Process
		 * 
		 * Projects and Subprojects are retrieved.
		 * @return
		 */
		public List<ProjectsVw> getIntramuralPastSubmissionDate() {
			
			logger.info("Retrieving all Intramural Submissions where Anticipated Submission Date is in the past" +
						" and the Project Submission Status is Not Started or In Process.");
			String hql  = QUERY_PAST_SUBMISSION_DATE + "and g.grantContractType = 'Intramural'";
			List<ProjectsVw> submissions = sessionFactory.getCurrentSession().createQuery(hql).list();
					
			return submissions;
			
		}
		
		/**
		 * Intramural Notifications
		 * 
		 * iii. Basic Study Information is In Progress
		 *     and Anticipated Submission Date is one week from today.
		 * 
		 *     Projects and Subprojects are retrieved.
		 * @return
		 */
		public List<ProjectsVw> getIntramuralBsiInProgress() {
			
			logger.info("Retrieving all Intramural Submissions where Basic Study Information is In Progress" +
						" and Anticipated Submission Date is one week from today.");
			String hql  = QUERY_BSI_INPROGRESS_DUE_IN_A_WEEK + "and g.grantContractType = 'Intramural'";
			List<ProjectsVw> submissions = sessionFactory.getCurrentSession().createQuery(hql).list();
					
			return submissions;
			
		}
		
		/**
		 * Intramural Notifications
		 * 
		 * i. GDS Plan or IC is In Progress for 3 months since the Project Submission has been created.
		 * 
		 * Projects and Subprojects are retrieved.
		 * @return
		 */
		public List<ProjectsVw> getIntramuralAll() {
			
			logger.info("getIntramuralAll");
			List<ProjectsVw> submissionsAll = getIntramuralGdsIcInProgress();
			Set<Long> uniqueIds = new HashSet<Long>();
			for(ProjectsVw submission: submissionsAll) {
				uniqueIds.add(submission.getId());
			}
			List<ProjectsVw> pastSubmissions = getIntramuralPastSubmissionDate();
			for(ProjectsVw submission: pastSubmissions) {
				if(uniqueIds.contains(submission.getId()))
					continue;
				submissionsAll.add(submission);
				uniqueIds.add(submission.getId());
			}
			List<ProjectsVw> bsiInProgressSubmissions = getIntramuralBsiInProgress();
			for(ProjectsVw submission: bsiInProgressSubmissions) {
				if(uniqueIds.contains(submission.getId()))
					continue;
				submissionsAll.add(submission);
				uniqueIds.add(submission.getId());
			}
			return submissionsAll;
			
		}
		
		/**
		 * Save or updates the emailNotification object.
		 * 
		 * @param transientInstance
		 */
		public EmailNotification logEmail(EmailNotification transientInstance) {
			logger.debug("Save or Update EmailNotification instance");
			try {
				Session session = sessionFactory.getCurrentSession();
				Transaction tx = session.beginTransaction();  
				session.saveOrUpdate(transientInstance);
				tx.commit();
				session.flush();
				logger.debug("save successful");
				return transientInstance;
			} catch (RuntimeException re) {
				logger.error("save failed", re);
				throw re;
			}
		}
		
		public List<EmailNotification> findLogByTemplateAndProjectId(Long templateId, Long projectId) {
			logger.debug("getting EmailNotification instance with template id: " + templateId + " project id: " + projectId);
			try {
				final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(EmailNotification.class);
				criteria.add(Restrictions.eq("mailTemplateId", templateId));
				criteria.add(Restrictions.eq("projectId", projectId));
				List<EmailNotification> logs = (List<EmailNotification>) criteria.list();
				return logs;
			} catch (RuntimeException re) {
				logger.error("get failed", re);
				throw re;
			}
		}
		
		public List<EmailNotification> findLogByTemplateAndDoc(String template, Long templateId, String doc) {
			logger.debug("getting EmailNotification instance with template id: " + templateId + " doc: " + doc);
			try {
				final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(EmailNotification.class);
				criteria.add(Restrictions.eq("mailTemplateId", templateId));
				criteria.add(Restrictions.eq("docAbbreviation", doc));
				if(StringUtils.equals(template, "INT_SUMMARY")) {
					Calendar c = Calendar.getInstance();
				    c.set(Calendar.DAY_OF_MONTH, 1);
					criteria.add(Restrictions.ge("sentDate", c.getTime()));
				}
				if(StringUtils.equals(template, "EXT_SUMMARY")) {
					Calendar c = Calendar.getInstance();
					// Set the calendar to sunday of the current week
					c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
					c.set(Calendar.HOUR_OF_DAY, 0);
					// Get sunday one weeks prior to the current sunday
					c.add(Calendar.DATE, -7);
					criteria.add(Restrictions.ge("sentDate", c.getTime()));
				}
				List<EmailNotification> logs = (List<EmailNotification>) criteria.list();
				return logs;
			} catch (RuntimeException re) {
				logger.error("get failed", re);
				throw re;
			}
		}

}

/**
 * 
 */
package gov.nih.nci.cbiit.scimgmt.gds.dao;

import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectsVw;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @author dinhys
 * 
 */
@Component
public class NotificationsDao {

		
		private static final Logger logger = LogManager.getLogger(NotificationsDao.class);

		private static final String QUERY_PAST_SUBMISSION_DATE = "select v " 
			+ "from Project p, ProjectsVw v, ProjectGrantContract g "
			+ "where p.id=v.id and p.anticipatedSubmissionDate < trunc(sysdate) and g.project.id=p.id ";

		private static final String QUERY_BSI_INPROGRESS_DUE_IN_A_WEEK = "select v " 
			+ "from Project p, ProjectsVw v, PageStatus s, ProjectGrantContract g "
			+ "where v.id=p.id and s.project.id = p.id and s.page.id=49 "
			+ "and s.status.id = 47 and p.anticipatedSubmissionDate - trunc(sysdate) < 7 "
			+ "and g.project.id=p.id and p.anticipatedSubmissionDate > trunc(sysdate) ";
		
		private static final String QUERY_GDS_IC_INPROGRESS_FOR_X_DAYS = "select v " 
			+ "from ProjectsVw v, Project p, PageStatus gds, PageStatus ic, ProjectGrantContract g "
			+ "where v.id=p.id and gds.project.id = p.id and ic.project.id = p.id "
			+ "and gds.page.id=1 and ic.page.id=2 and (gds.status.id=47 or ic.status.id=47) "
			+ "and g.project.id=p.id and sysdate - p.createdDate > 60 ";
		
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
			String hql  = QUERY_BSI_INPROGRESS_DUE_IN_A_WEEK + "and g.grantContractType = 'Extramural'";
			List<ProjectsVw> submissions = sessionFactory.getCurrentSession().createQuery(hql).list();
					
			return submissions;
			
		}
		
		/**
		 * Extramural Notifications
		 * 
		 * iii. GDS Plan or IC is In Progress status and
		 *      the budget end date associated to the latest funded extramural grant is 45 days from today.
		 * 
		 *      Projects and Subprojects are retrieved.
		 * @return
		 */
		public List<ProjectsVw> getExtramuralGdsIcInProgress() {
			
			logger.info("Retrieving all Extramural Submissions where GDS Plan or IC is In Progress status" + 
						" and the budget end date associated to the latest funded extramural grant is 45 days from today.");
			String hql  = QUERY_GDS_IC_INPROGRESS_FOR_X_DAYS + "and g.grantContractType = 'Extramural'";
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
		public List<ProjectsVw> getIntramuralGdsIcInProgress() {
			
			logger.info("Retrieving all Intramural Submissions where GDS Plan or IC is In Progress" +
						" for 3 months since the Project Submission has been created.");
			String hql  = QUERY_GDS_IC_INPROGRESS_FOR_X_DAYS + "and g.grantContractType = 'Intramural'";
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

}

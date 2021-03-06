/**
 * 
 */
package gov.nih.nci.cbiit.scimgmt.gds.dao;

import gov.nih.nci.cbiit.scimgmt.gds.domain.DulChecklist;
import gov.nih.nci.cbiit.scimgmt.gds.domain.GdsPd;
import gov.nih.nci.cbiit.scimgmt.gds.domain.HelpText;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Organization;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanQuestionsAnswer;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Property;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.NullPrecedence;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @author menons2
 * 
 */
@Component
public class PropertyListDao {

		
		private static final Logger logger = LogManager.getLogger(PropertyListDao.class);
		
		@Autowired
		private SessionFactory sessionFactory;
		
		public List<Lookup> getAllLookupLists() {
			
			logger.info("Retrieving all lookup lists from LOOKUP_T");
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Lookup.class);
			criteria.addOrder(Order.asc("discriminator"));
			criteria.addOrder(Order.asc("orderNum"));
			List<Lookup> lookups = criteria.list();
					
			return lookups;
			
		}
		
		/**
		 * Gets the lookup lists for this application. Invoked
		 * during application initialization.
		 * @return
		 */
		public List<Property> getPropertiesList() {
			
			logger.info("Retrieving all properties from PROPERTIES_T");
			
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Property.class);
			List<Property> properties = criteria.list();
					
			return properties;
		}
		
		
		/**
		 * Retrieve the lookup list for a given list name.
		 * 
		 * @param listName
		 * @return
		 */
		public List<Lookup> searchLookup(String listName) {
			
			List<Lookup> lookups = null;
			
			logger.info("Retrieving lookup list from LOOKUP_T for listName " + listName);
			
			try {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Lookup.class);
			criteria.add(Restrictions.ilike("discriminator", listName, MatchMode.EXACT));
			criteria.addOrder(Order.asc("orderNum"));
			lookups = criteria.list();
			} catch (Throwable e) {
				logger.error("Error retrieving lookup list for listName " + listName, e);
				throw e;
			}
					
			return lookups;
		}

		/**
		 * Get static data to use for GDS Plan
		 * 
		 * @return
		 */
		public List<PlanQuestionsAnswer> getAllPlanQuestionsAnswers() {
			
			logger.info("Retrieving all plan questions answers from PLAN_QUESTIONS_ANSWERS_T");
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PlanQuestionsAnswer.class);
			criteria.add(Restrictions.eq("activeFlag", true));
			criteria.addOrder(Order.asc("id"));
			criteria.addOrder(Order.asc("questionId").nulls(NullPrecedence.FIRST));
			criteria.addOrder(Order.asc("displayOrderNum"));
		
			return criteria.list();
			
		}
		
		/**
		 * Get DOC list
		 * 
		 * @return
		 */
		public List<Organization> getDocList(String docList) {
			
			logger.info("Retrieving DOC list from DOCS_VW");
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Organization.class);
			List<Organization> orgList = criteria.list();	
			return orgList;
		}
		
		
		/**
		 * Get static DUL display text for IC Submission
		 * @return
		 */
		public List<DulChecklist> getAllDulChecklists() {
			logger.info("Retrieving all DulChecklists from DUL_CHECKLIST_T");
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DulChecklist.class);
			criteria.addOrder(Order.asc("parentDulId").nulls(NullPrecedence.FIRST));
			criteria.addOrder(Order.asc("displayOrderNum"));
			
			return criteria.list();
		}
		
		/**
		 * Get GdsPd list
		 * 
		 * @return
		 */
		public List<GdsPd> getPdList() {
			
			logger.info("Retrieving PD list from GDS_PD_VW");
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GdsPd.class);
			criteria.addOrder(Order.asc("pdFullNameDescrip").ignoreCase());
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			return criteria.list();	
		}
		
		
		
		public List<HelpText> getHelpList() {
			logger.info("Retrieving Help strings from MESSAGES_T");
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HelpText.class);
			return criteria.list();
		}
}

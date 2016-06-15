/**
 * 
 */
package gov.nih.nci.cbiit.scimgmt.gds.dao;

import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
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
			
			logger.info("Retrieving all lookup lists from DB");
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Lookup.class);
			criteria.addOrder(Order.asc("discriminator"));
			List<Lookup> lookups = criteria.list();
					
			return lookups;
			
		}
		
		/**
		 * Gets the lookup lists for this application. Invoked
		 * during application initialization.
		 * @return
		 */
		public List<Property> getPropertiesList() {
			
			logger.info("Retrieving properties list from DB ");
			
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
			
			logger.info("Retrieving lookup list from DB for listName " + listName);
			
			try {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Lookup.class);
			criteria.add(Restrictions.ilike("discriminator", listName, MatchMode.EXACT));
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
			
			logger.info("Retrieving all plan questions answers from DB");
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PlanQuestionsAnswer.class);
			criteria.add(Restrictions.eq("activeFlag", true));
			criteria.addOrder(Order.asc("id"));
			criteria.addOrder(Order.asc("questionId").nulls(NullPrecedence.FIRST));
			criteria.addOrder(Order.asc("displayOrderNum"));
		
			return criteria.list();
			
		}
}

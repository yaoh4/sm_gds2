/**
 * 
 */
package gov.nih.nci.cbiit.scimgmt.gds.dao;

import gov.nih.nci.cbiit.scimgmt.gds.domain.LookupT;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PropertiesT;

import java.util.List;

import org.hibernate.Criteria;
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
		
		public List<LookupT> getAllLookupLists() {
			
			logger.info("Retrieving all lookup lists from DB");
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LookupT.class);
			criteria.addOrder(Order.asc("displayName"));
			List<LookupT> lookups = criteria.list();
					
			return lookups;
			
		}
		
		/**
		 * Gets the lookup lists for this application. Invoked
		 * during application initialization.
		 * @return
		 */
		public List<PropertiesT> getPropertiesList() {
			
			logger.info("Retrieving properties list from DB ");
			
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PropertiesT.class);
			List<PropertiesT> properties = criteria.list();
					
			return properties;
		}
		
		
		/**
		 * Retrieve the lookup list for a given list name.
		 * 
		 * @param listName
		 * @return
		 */
		public List<LookupT> searchLookup(String listName) {
			
			List<LookupT> lookups = null;
			
			logger.info("Retrieving lookup list from DB for listName " + listName);
			
			try {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LookupT.class);
			criteria.add(Restrictions.ilike("displayName", listName, MatchMode.EXACT));
			lookups = criteria.list();
			} catch (Throwable e) {
				logger.error("Error retrieving lookup list for listName " + listName, e);
				throw e;
			}
					
			return lookups;
		}

}

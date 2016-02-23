/**
 * 
 */
package gov.nih.nci.cbiit.scimgmt.gds.dao;

import gov.nih.nci.cbiit.scimgmt.gds.domain.AppLookupT;
import gov.nih.nci.cbiit.scimgmt.gds.domain.AppPropertiesT;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
		
		public List<AppLookupT> getAllLookupLists() {
			
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(AppLookupT.class);
			criteria.add(Restrictions.eq("applicationName", "GDS"));
			criteria.addOrder(Order.asc("discriminator"));
			List<AppLookupT> lookups = criteria.list();
					
			return lookups;
			
		}
		
		/**
		 * Gets the lookup lists for this application. Invoked
		 * during application initialization.
		 * @return
		 */
		public List<AppPropertiesT> getPropertiesList() {
			
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(AppPropertiesT.class);
			criteria.add(Restrictions.eq("appName", "GDS"));
			List<AppPropertiesT> properties = criteria.list();
					
			return properties;
		}
		
		
		/**
		 * Retrieve the lookup list for a given discriminator (list name).
		 * 
		 * @param listName
		 * @return
		 */
		public List<AppLookupT> searchLookup(String listName) {
			
			Session session = null;
			List<AppLookupT> lookups = null;
			
			try {
			session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(AppLookupT.class);
			criteria.add(Restrictions.eq("applicationName", "GDS"));
			criteria.add(Restrictions.eq("discriminator", listName));
			lookups = criteria.list();
			} catch (Throwable e) {
				logger.error("Error retrieving lookup list for discriminator " + listName, e);
				throw e;
			}
					
			return lookups;
		}
		
		
		/**
		 * Retrieve the value of the given property key. 
		 * 
		 * @param key
		 * @return
		 */
		public String searchProperty(String key) {
			Session session = null;
			String value = null;
			
			try {
				session = sessionFactory.getCurrentSession();
				Criteria criteria = session.createCriteria(AppLookupT.class);
				criteria.add(Restrictions.eq("id.appName", "GDS"));
				criteria.add(Restrictions.eq("id.propKey", key));
				AppPropertiesT properties = (AppPropertiesT) criteria.uniqueResult();
				value = properties.getPropValue();
			} catch (Throwable e) {
				logger.error("Error retrieving lookup list for key " + key, e);
				throw e;
			}
			
			return value;
		}

}

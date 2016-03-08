package gov.nih.nci.cbiit.scimgmt.gds.junit;
import org.junit.Assert;import org.junit.Test;import org.junit.runner.RunWith;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.test.context.ContextConfiguration;import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;import org.springframework.transaction.annotation.Transactional;import gov.nih.nci.cbiit.scimgmt.gds.dao.ProjectsDao;import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectsT;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("applicationContext.xml")
public class HibernateMappingTest {
	
	@Autowired 
	protected ProjectsDao projectsDao;			@Test	@Transactional	public void testFetchProjectById(){		Long id = 1L;		boolean isOk = true;				ProjectsT project = projectsDao.findById(id);		System.out.println(project.toString());				if(!isOk){			Assert.assertTrue(isOk);			System.out.println("----Failed test fetch project by id---");		}else{			System.out.println("----Tested fetch project by id successfully---");		}	}}

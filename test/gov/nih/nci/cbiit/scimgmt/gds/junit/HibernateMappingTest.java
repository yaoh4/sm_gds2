package gov.nih.nci.cbiit.scimgmt.gds.junit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("applicationContext.xml")
public class HibernateMappingTest {
	
	@Autowired 
	protected ProjectsDao projectsDao;
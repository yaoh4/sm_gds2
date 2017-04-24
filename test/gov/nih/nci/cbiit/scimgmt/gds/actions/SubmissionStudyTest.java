package gov.nih.nci.cbiit.scimgmt.gds.actions;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Study;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"../applicationContext.xml"})
public class SubmissionStudyTest {
	
	static Logger logger = LogManager.getLogger(SubmissionStudyAction.class);
	Project project;

	@Test
	@Transactional
	public void testRetrieveStudy() {
		
		SubmissionStudyAction submissionStudy = SubmissionStudyAction.getInstance();
		Project project = new Project();
		project.setId(1L);
		
		//Inserting studies
	    List<Study> studies = new ArrayList<Study>();
		Study study1 = new Study();
		study1.setId(2L);
		studies.add(study1);
		Study study2 = new Study();
		study2.setId(3L);
		studies.add(study2);
		
		//set Studies to project
		project.setStudies(studies);
		
		System.out.println("study is here is " + project.getStudyById(2L).getId());
		//Study stu = submissionStudy.retrieveStudy(Long.valueOf(2L));
		Assert.assertEquals(project.getStudyById(2L).getId(), Long.valueOf(2L));
	}
}

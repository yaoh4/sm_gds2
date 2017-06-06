/**
 * 
 */
package gov.nih.nci.cbiit.scimgmt.gds.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanQuestionsAnswer;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.services.FileUploadService;
import gov.nih.nci.cbiit.scimgmt.gds.services.ManageProjectService;

/**
 * @author menons2
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"../applicationContext.xml"})
public class GdsPageStatusUtilTest {
	
	private static final Logger logger = LogManager.getLogger(GdsPageStatusUtil.class);
	
	@Autowired
	protected FileUploadService fileUploadService;
	
	@Autowired
	protected ManageProjectService manageProjectService;
	
	@Autowired
	protected NedPerson loggedOnUser;
	
	private static GdsPageStatusUtil instance;
   	private static boolean loaded = false;
	
   	
	@Test
	@Transactional
	public void testComputeGdsPlanStatus() {
		System.out.println("Starting junit for testComputeGdsPlanStatus");
		GdsPageStatusUtil gdsPageStatusUtil = GdsPageStatusUtil.getInstance();
		
		//Initial setup
		Project project = new Project();
		project.setId(2L);
		
		//Test conditions for NULL status
		
		//If submission reason is non-NIH funded OR this is 
		//a sub-project, then there is no GDS Plan.
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NONNIHFUND);
		Assert.assertNull("GDSPlanStatus should be null when submission reason is Non NIH Fund", gdsPageStatusUtil.computeGdsPlanStatus(project));
		
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NIHFUND);
		setAsSubproject(project);
		Assert.assertNull("GDSPlan Status should be null for subproject", gdsPageStatusUtil.computeGdsPlanStatus(project));
		
		//Test conditions for NOT_STARTED status
		
		//Not a subproject, and no data entered, so status should denote not started
		project.setParent(null);
		project.setParentProjectId(null);
		Assert.assertEquals("GDSPlan Status should be Not Started when there is no data", ApplicationConstants.PAGE_STATUS_CODE_NOT_STARTED, gdsPageStatusUtil.computeGdsPlanStatus(project));
		
	}
	
	@Test
	@Transactional
	public void testComputeIcListStatus() {
		System.out.println("Starting junit for testComputeIcListStatus");
		GdsPageStatusUtil gdsPageStatusUtil = GdsPageStatusUtil.getInstance();
		
		//Initial setup
		Project project = new Project();
		project.setId(2L);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NIHFUND);
		
		//Test conditions for NULL status (No IC Page)
		PlanQuestionsAnswer answer = new PlanQuestionsAnswer();
		answer.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_NONHUMAN_ID);
		PlanAnswerSelection selection = new PlanAnswerSelection(1L, answer, "JUnit");
		project.addPlanAnswerSelection(selection);
		Assert.assertNull("ICList Status should be null if Non Human option is selected", gdsPageStatusUtil.computeIcListStatus(project));
		
		project.removePlanAnswerSelection(selection.getId());
		answer.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_NO_ID);
		selection.setPlanQuestionsAnswer(answer);
		project.addPlanAnswerSelection(selection);
		Assert.assertNull("ICList Status should be null if f the answer to 'Will there be any data submitted' is 'No'", gdsPageStatusUtil.computeIcListStatus(project));
		
		setAsSubproject(project);
		project.removePlanAnswerSelection(answer.getId());
		answer.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_NONHUMAN_ID);
		selection.setPlanQuestionsAnswer(answer);
		project.getParent().addPlanAnswerSelection(selection);
		Assert.assertNull("ICList Status should be null for subprojects whose parent has Non Human option selected", gdsPageStatusUtil.computeIcListStatus(project));
		
	}
	
	@Test
	@Transactional
	public void computeBsiStudyInfoStatusTest() {
		System.out.println("Starting junit for testComputeIcListStatus");
		GdsPageStatusUtil gdsPageStatusUtil = GdsPageStatusUtil.getInstance();
		
		//Initial setup
		Project project = new Project();
		project.setId(2L);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NIHFUND);
		
		//Test conditions for Null status  - NO BSI
		PlanQuestionsAnswer answer = new PlanQuestionsAnswer();
		answer.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_NO_ID);
		PlanAnswerSelection selection = new PlanAnswerSelection(1L, answer, "JUnit");
		project.addPlanAnswerSelection(selection);
		Assert.assertNull("BSIStudyInfo Status should be null If the answer to 'Will there be any data submitted' is 'No'", gdsPageStatusUtil.computeBsiStudyInfoStatus(project));
		
	}
	
	@Test
	@Transactional
	public void computeRepositoryStatusTest() {
		
		System.out.println("Starting junit for computeRepositoryStatusTest");
		GdsPageStatusUtil gdsPageStatusUtil = GdsPageStatusUtil.getInstance();
		
		//Initial setup
		Project project = new Project();
		project.setId(2L);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NIHFUND);
		
		// when no repositories are selected, the status is NOT STARTED 
		Assert.assertEquals(ApplicationConstants.NOT_STARTED, gdsPageStatusUtil.computeRepositoryStatus(project));	
	}
	
	private void setAsSubproject(Project project) {
		Project parent = new Project();
		parent.setId(1L);
		project.setParent(parent);
		project.setParentProjectId(parent.getId());
		project.setSubprojectFlag(ApplicationConstants.FLAG_YES);
	}
	

}

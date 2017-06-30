/**
 * 
 */
package gov.nih.nci.cbiit.scimgmt.gds.util;

import static org.mockito.Matchers.any;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanQuestionsAnswer;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;
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
		project.setPlanComments(null);
		gdsPageStatusUtil.computeGdsPlanStatus(project);
		Assert.assertEquals("GDSPlan Status should be Not Started when there is no data", ApplicationConstants.PAGE_STATUS_CODE_NOT_STARTED, gdsPageStatusUtil.computeGdsPlanStatus(project));
		// submission reason id is gwas policy
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GWASPOLICY);
		gdsPageStatusUtil.computeGdsPlanStatus(project);
		
		PlanQuestionsAnswer ans = new PlanQuestionsAnswer();
		ans.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_ID);
		ans.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_ID);
		PlanAnswerSelection sele = new PlanAnswerSelection(2L, ans, "JUnit");
		project.addPlanAnswerSelection(sele);
		gdsPageStatusUtil.computeGdsPlanStatus(project);
		
		PlanQuestionsAnswer answer = new PlanQuestionsAnswer();
		answer.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_YES_ID);
		answer.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_YES_ID);
		PlanAnswerSelection selection = new PlanAnswerSelection(2L, answer, "JUnit");
		project.addPlanAnswerSelection(selection);

		PlanQuestionsAnswer answer1 = new PlanQuestionsAnswer();
		answer1.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_PENDING_ID);
		answer1.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_PENDING_ID);
		PlanAnswerSelection selection1 = new PlanAnswerSelection(3L, answer1, "JUnit");
		project.addPlanAnswerSelection(selection1);
		gdsPageStatusUtil.computeGdsPlanStatus(project);

		project.removePlanAnswerSelection(selection.getId());
		project.removePlanAnswerSelection(selection1.getId());
		PlanQuestionsAnswer answer2 = new PlanQuestionsAnswer();
		answer2.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_YES_ID);
		answer2.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_YES_ID);
		PlanAnswerSelection selection2 = new PlanAnswerSelection(2L, answer2, "JUnit");
		project.addPlanAnswerSelection(selection2);
		gdsPageStatusUtil.computeGdsPlanStatus(project);

		
		// submission reason id is gds policy
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
		project.removePlanAnswerSelection(selection2.getId());
		project.removePlanAnswerSelection(sele.getId());
		
		PlanQuestionsAnswer answer3 = new PlanQuestionsAnswer();
		answer3.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_NO_ID);
		answer3.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_NO_ID);
		PlanAnswerSelection selection3 = new PlanAnswerSelection(2L, answer3, "JUnit");
		project.addPlanAnswerSelection(selection3);
		gdsPageStatusUtil.computeGdsPlanStatus(project);
		
		project.removePlanAnswerSelection(selection3.getId());
		PlanQuestionsAnswer answer4 = new PlanQuestionsAnswer();
		answer4.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_NO_ID);
		answer4.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_NO_ID);
		PlanAnswerSelection selection4 = new PlanAnswerSelection(2L, answer4, "JUnit");
		project.addPlanAnswerSelection(selection4);
		gdsPageStatusUtil.computeGdsPlanStatus(project);
		
		project.removePlanAnswerSelection(selection4.getId());
		PlanQuestionsAnswer answer5 = new PlanQuestionsAnswer();
		answer5.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_YES_ID);
		answer5.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_YES_ID);
		PlanAnswerSelection selection5 = new PlanAnswerSelection(2L, answer5, "JUnit");
		project.addPlanAnswerSelection(selection5);
		gdsPageStatusUtil.computeGdsPlanStatus(project);
   
		//If the submission is a sub-project, GDS plan and missing data report do not exist
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NIHFUND);
		project.removePlanAnswerSelection(selection5.getId());
		gdsPageStatusUtil.computeGdsPlanStatus(project);
		PlanQuestionsAnswer answer6 = new PlanQuestionsAnswer();
		answer6.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_NO_ID);
		answer6.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_NO_ID);
		PlanAnswerSelection selection6 = new PlanAnswerSelection(2L, answer6, "JUnit");
		project.addPlanAnswerSelection(selection6);
		gdsPageStatusUtil.computeGdsPlanStatus(project);
		
		project.removePlanAnswerSelection(selection6.getId());
		PlanQuestionsAnswer answer7 = new PlanQuestionsAnswer();
		answer7.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_NO_ID);
		answer7.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_NO_ID);
		PlanAnswerSelection selection7 = new PlanAnswerSelection(2L, answer7, "JUnit");
		project.addPlanAnswerSelection(selection7);
		gdsPageStatusUtil.computeGdsPlanStatus(project);
		
		project.removePlanAnswerSelection(selection7.getId());
		PlanQuestionsAnswer answer8 = new PlanQuestionsAnswer();
		answer8.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_YES_ID);
		answer8.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_YES_ID);
		PlanAnswerSelection selection8 = new PlanAnswerSelection(2L, answer8, "JUnit");
		project.addPlanAnswerSelection(selection8);
		gdsPageStatusUtil.computeGdsPlanStatus(project);
		
		PlanQuestionsAnswer answer9 = new PlanQuestionsAnswer();
		answer9.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_ID);
		answer9.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_ID);
		PlanAnswerSelection selection9 = new PlanAnswerSelection(2L, answer9, "JUnit");
		project.addPlanAnswerSelection(selection9);
		gdsPageStatusUtil.computeGdsPlanStatus(project);
		
		PlanQuestionsAnswer answer10 = new PlanQuestionsAnswer();
		answer10.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_TYPE_ID);
		answer10.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_TYPE_ID);
		PlanAnswerSelection selection10 = new PlanAnswerSelection(2L, answer10, "JUnit");
		project.addPlanAnswerSelection(selection10);
		gdsPageStatusUtil.computeGdsPlanStatus(project);
		
		PlanQuestionsAnswer answer0 = new PlanQuestionsAnswer();
		answer0.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_ACCESS_ID);
		answer0.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_ACCESS_ID);
		PlanAnswerSelection selection0 = new PlanAnswerSelection(2L, answer0, "JUnit");
		project.addPlanAnswerSelection(selection0);
		gdsPageStatusUtil.computeGdsPlanStatus(project);
		
		setAsSubproject(project);
		gdsPageStatusUtil.computeGdsPlanStatus(project);
		Assert.assertNull("GDSPlan Status should be null for subproject", gdsPageStatusUtil.computeGdsPlanStatus(project));
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
		ManageProjectService manageProject = Mockito.mock(ManageProjectService.class);
		Mockito.when(manageProject.findById(1L)).thenReturn(project.getParent());
		Mockito.when(manageProject.getSubprojects(1L, true)).thenReturn(new ArrayList<Project>());
		Mockito.when(manageProject.saveOrUpdate(any(Project.class))).thenReturn(project);
		gdsPageStatusUtil.manageProjectService = manageProject;
		answer.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_NONHUMAN_ID);
		answer.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_NONHUMAN_ID);
		selection.setPlanQuestionsAnswer(answer);
		project.getParent().setSubprojectFlag(ApplicationConstants.FLAG_NO);
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
		
		// bsi commnets blank
		project.removePlanAnswerSelection(selection.getId());
		project.setBsiReviewedId(ApplicationConstants.BSI_NA);
		gdsPageStatusUtil.computeBsiStudyInfoStatus(project);
		
		// all empty fields
		project.setBsiReviewedId(null);
		project.setBsiComments(null);
		project.setSubprojectFlag(ApplicationConstants.FLAG_NO);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NONNIHFUND);
		gdsPageStatusUtil.computeBsiStudyInfoStatus(project);
		System.out.println("bsi status" +gdsPageStatusUtil.computeBsiStudyInfoStatus(project));
		
		// repository exists for non nih funded
		PlanQuestionsAnswer answer1 = new PlanQuestionsAnswer();
		answer1.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID);
		answer1.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID);
		PlanAnswerSelection selection1 = new PlanAnswerSelection(1L, answer1, "JUnit");
		project.addPlanAnswerSelection(selection1);
		gdsPageStatusUtil.computeBsiStudyInfoStatus(project);
		
	    //If GPA has not reviewed or GPA has reviewed but no document has been uploaded
		project.setBsiComments("yes");
		gdsPageStatusUtil.computeBsiStudyInfoStatus(project);
		Assert.assertEquals(ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS, gdsPageStatusUtil.computeBsiStudyInfoStatus(project));
		
		// 
		project.removePlanAnswerSelection(selection1.getId());
		gdsPageStatusUtil.computeBsiStudyInfoStatus(project);
		Assert.assertEquals(ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS, gdsPageStatusUtil.computeBsiStudyInfoStatus(project));
		
		project.setBsiReviewedId(ApplicationConstants.BSI_NO);
		gdsPageStatusUtil.computeBsiStudyInfoStatus(project);
		Assert.assertEquals(ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS, gdsPageStatusUtil.computeBsiStudyInfoStatus(project));
		
		project.setBsiReviewedId(ApplicationConstants.BSI_YES);
		gdsPageStatusUtil.computeBsiStudyInfoStatus(project);
		Assert.assertEquals(ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS, gdsPageStatusUtil.computeBsiStudyInfoStatus(project));
		
		project.setBsiReviewedId(null);
		gdsPageStatusUtil.computeBsiStudyInfoStatus(project);
		Assert.assertEquals(ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS, gdsPageStatusUtil.computeBsiStudyInfoStatus(project));
		
		FileUploadService fileUploadServiceMock2 = Mockito.mock(FileUploadService.class);
		List<Document> docs = new ArrayList<Document>();
		Document d = new Document();
		d.setId(3L);
		docs.add(d);
		Mockito.when(fileUploadServiceMock2.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_BSI, 2L)).thenReturn(docs);
		gdsPageStatusUtil.fileUploadService = fileUploadServiceMock2;
		gdsPageStatusUtil.computeBsiStudyInfoStatus(project);
		Assert.assertEquals(ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS, gdsPageStatusUtil.computeBsiStudyInfoStatus(project));
		
		project.setBsiReviewedId(ApplicationConstants.BSI_YES);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
		gdsPageStatusUtil.computeBsiStudyInfoStatus(project);
		Assert.assertEquals(ApplicationConstants.PAGE_STATUS_CODE_COMPLETED, gdsPageStatusUtil.computeBsiStudyInfoStatus(project));
		
		project.setBsiReviewedId(ApplicationConstants.BSI_NA);
		gdsPageStatusUtil.computeBsiStudyInfoStatus(project);
		Assert.assertEquals(ApplicationConstants.PAGE_STATUS_CODE_COMPLETED, gdsPageStatusUtil.computeBsiStudyInfoStatus(project));
	}
	
	@Test
	@Transactional
	public void computeRepositoryStatusTest() throws ParseException {
		
		System.out.println("Starting junit for computeRepositoryStatusTest");
		GdsPageStatusUtil gdsPageStatusUtil = GdsPageStatusUtil.getInstance();
		
		//Initial setup
		Project project = new Project();
		project.setId(2L);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NIHFUND);
		
		// when no repositories are selected, the status is NOT STARTED 
		Assert.assertEquals(ApplicationConstants.PAGE_STATUS_CODE_NOT_STARTED, gdsPageStatusUtil.computeRepositoryStatus(project));	
		
		// Setting repositories
		RepositoryStatus rep = new RepositoryStatus(); 
		rep.setId(3L);

		PlanQuestionsAnswer answer = new PlanQuestionsAnswer();
		answer.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_DBGAP_ID);
		answer.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID);
		answer.setDisplayText("Database of Genotypes and Phenotypes (dbGaP)");
		PlanAnswerSelection selection = new PlanAnswerSelection(1L, answer, "JUnit");
		project.addPlanAnswerSelection(selection);
		
		List<RepositoryStatus> repositoryStatuses = new ArrayList<RepositoryStatus>(0);
		Set<RepositoryStatus> repositoryStatuses1 = new HashSet(0);
		rep.setProject(project);
		Lookup lookup = new Lookup();
		lookup.setId(17L);
		lookup.setCode("NO");
		Lookup lookup1 = new Lookup();
		lookup1.setId(12L);
		lookup1.setCode("INPROGRESS");
		Lookup lookup2 = new Lookup();
		lookup2.setId(8L);
		lookup2.setCode("INPROGRESS");
		Lookup lookup3 = new Lookup();
		lookup3.setId(16L);
		lookup3.setCode("YES");
		Lookup lookup4 = new Lookup();
		lookup4.setId(9L);
		lookup4.setCode("NOTSTARTED");
		
		rep.setLookupTByStudyReleasedId(lookup);
		rep.setLookupTByRegistrationStatusId(lookup2);
		rep.setLookupTBySubmissionStatusId(lookup1);
		rep.setPlanAnswerSelectionTByRepositoryId(selection);

		repositoryStatuses.add(rep);
		repositoryStatuses1.add(rep);
		selection.setRepositoryStatuses(repositoryStatuses1);
		project.setRepositoryStatuses(repositoryStatuses);
		
		gdsPageStatusUtil.computeRepositoryStatus(project);
		
		// study id is yes
		RepositoryStatus rep0 = new RepositoryStatus(); 
		rep0.setId(4L);
		rep0.setProject(project);
		rep0.setLookupTByStudyReleasedId(lookup3);
		rep0.setLookupTByRegistrationStatusId(lookup2);
		rep0.setLookupTBySubmissionStatusId(lookup1);
		rep0.setPlanAnswerSelectionTByRepositoryId(selection);
		
		repositoryStatuses.clear();
		repositoryStatuses1.clear();
		repositoryStatuses.add(rep0);
		repositoryStatuses1.add(rep0);
		selection.setRepositoryStatuses(repositoryStatuses1);
		project.setRepositoryStatuses(repositoryStatuses);
		gdsPageStatusUtil.computeRepositoryStatus(project);
		System.out.println("status repo" +gdsPageStatusUtil.computeRepositoryStatus(project));
		
		// study id is yes
		RepositoryStatus rep1 = new RepositoryStatus(); 
		rep1.setId(4L);
		rep1.setProject(project);
		rep1.setLookupTByStudyReleasedId(lookup);
		rep1.setLookupTByRegistrationStatusId(lookup4);
		rep1.setLookupTBySubmissionStatusId(lookup1);
		rep1.setPlanAnswerSelectionTByRepositoryId(selection);
		
		repositoryStatuses.clear();
		repositoryStatuses1.clear();
		repositoryStatuses.add(rep1);
		repositoryStatuses1.add(rep1);
		selection.setRepositoryStatuses(repositoryStatuses1);
		project.setRepositoryStatuses(repositoryStatuses);
		
		gdsPageStatusUtil.computeRepositoryStatus(project);
		System.out.println("status repo111" +gdsPageStatusUtil.computeRepositoryStatus(project));
		
		java.util.Date date=new java.util.Date();  
		project.setAnticipatedSubmissionDate(date);
		gdsPageStatusUtil.computeRepositoryStatus(project);
		System.out.println("status repodate" +gdsPageStatusUtil.computeRepositoryStatus(project));

	}
	
	private void setAsSubproject(Project project) {
		Project parent = new Project();
		parent.setId(1L);
		project.setParent(parent);
		project.setParentProjectId(parent.getId());
		project.setSubprojectFlag(ApplicationConstants.FLAG_YES);
	}
	

}

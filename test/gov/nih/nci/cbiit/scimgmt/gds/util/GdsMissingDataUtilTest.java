package gov.nih.nci.cbiit.scimgmt.gds.util;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.HashMap;
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

import org.springframework.util.CollectionUtils;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.InstitutionalCertification;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanQuestionsAnswer;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Study;
import gov.nih.nci.cbiit.scimgmt.gds.model.MissingData;
import gov.nih.nci.cbiit.scimgmt.gds.services.FileUploadService;
import gov.nih.nci.cbiit.scimgmt.gds.services.LookupService;
import gov.nih.nci.cbiit.scimgmt.gds.services.ManageProjectService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"../applicationContext.xml"})
public class GdsMissingDataUtilTest {
	
	private static final Logger logger = LogManager.getLogger(GdsMissingDataUtil.class);
	
	@Autowired
	protected LookupService lookupService;
	
	@Autowired
	protected ManageProjectService manageProjectService;
	
	@Autowired
	protected FileUploadService fileUploadService;
	
	@Autowired
	protected NedPerson loggedOnUser;
	
	private static GdsMissingDataUtil instance;
   	private static boolean loaded = false;
   	
   	@Test
	@Transactional
	public void getMissingGdsPlanDataTest(){
   		
   		System.out.println("Starting junit for getMissingGdsPlanDataTest");
		GdsMissingDataUtil gdsMissingDataUtil = GdsMissingDataUtil.getInstance();
		
		//Initial setup
		Project project = new Project();
		project.setId(2L);
		ArrayList<MissingData> missingDataList = new ArrayList<MissingData>();
		
		//Test conditions for Empty Missing Data Report
		
		//If submission reason is non-NIH funded, GDS plan and missing data report do not exist
		
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NONNIHFUND);
		Assert.assertEquals(missingDataList, gdsMissingDataUtil.getMissingGdsPlanData(project));
		
		
		// Test Scenario when submission Reason id is GDS Policy and Not indicated whether there is a data sharing exception requested for this project
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
		Assert.assertEquals("The question 'Is there a data sharing exception requested for this project ?' has not been answered.", gdsMissingDataUtil.getMissingGdsPlanData(project).get(0).getDisplayText());
		PlanQuestionsAnswer ans = new PlanQuestionsAnswer();
		ans.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_NO_ID);
		ans.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_NO_ID);
		PlanAnswerSelection selec = new PlanAnswerSelection(2L, ans, "JUnit");
		project.addPlanAnswerSelection(selec);
		Assert.assertEquals("The Data Sharing Plan has not been uploaded.", gdsMissingDataUtil.getMissingGdsPlanData(project).get(1).getDisplayText());
		
		project.removePlanAnswerSelection(selec.getId());
		PlanQuestionsAnswer ans1 = new PlanQuestionsAnswer();
		ans1.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_NO_ID);
		ans1.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_NO_ID);
		PlanAnswerSelection selec1 = new PlanAnswerSelection(2L, ans1, "JUnit");
		project.addPlanAnswerSelection(selec1);
		Assert.assertEquals("The Data Sharing Plan has not been uploaded.", gdsMissingDataUtil.getMissingGdsPlanData(project).get(1).getDisplayText());
		
		project.removePlanAnswerSelection(selec1.getId());
		PlanQuestionsAnswer ans2 = new PlanQuestionsAnswer();
		ans2.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_YES_ID);
		ans2.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_YES_ID);
		PlanAnswerSelection selec2 = new PlanAnswerSelection(2L, ans2, "JUnit");
		project.addPlanAnswerSelection(selec2);
		Assert.assertEquals("The Data Sharing Plan has not been uploaded.", gdsMissingDataUtil.getMissingGdsPlanData(project).get(1).getDisplayText());
		
		Assert.assertEquals("GPA review of the Data Sharing Plan is pending.", gdsMissingDataUtil.getMissingGdsPlanData(project).get(2).getDisplayText());
		
		
		
		// submission reason id is gwas policy
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GWASPOLICY);
		Assert.assertEquals("The question 'Is there a data sharing exception requested for this project ?' has not been answered.", gdsMissingDataUtil.getMissingGdsPlanData(project).get(0).getDisplayText());
		
		PlanQuestionsAnswer answer = new PlanQuestionsAnswer();
		answer.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_YES_ID);
		answer.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_YES_ID);
		PlanAnswerSelection selection = new PlanAnswerSelection(2L, answer, "JUnit");
		project.addPlanAnswerSelection(selection);
		Assert.assertEquals("The question 'Was this exception approved ?' has not been answered.", gdsMissingDataUtil.getMissingGdsPlanData(project).get(1).getDisplayText());
		
		
		PlanQuestionsAnswer answer1 = new PlanQuestionsAnswer();
		answer1.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_PENDING_ID);
		answer1.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_PENDING_ID);
		PlanAnswerSelection selection1 = new PlanAnswerSelection(2L, answer1, "JUnit");
		project.addPlanAnswerSelection(selection1);
		Assert.assertEquals("Approval of the Exception is pending.", gdsMissingDataUtil.getMissingGdsPlanData(project).get(2).getDisplayText());
		
		
		PlanQuestionsAnswer answer2 = new PlanQuestionsAnswer();
		answer2.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_YES_ID);
		answer2.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_YES_ID);
		PlanAnswerSelection selection2 = new PlanAnswerSelection(2L, answer2, "JUnit");
		project.addPlanAnswerSelection(selection2);
		Assert.assertEquals("The Exception Memo has not been uploaded.", gdsMissingDataUtil.getMissingGdsPlanData(project).get(3).getDisplayText());
		
		
		PlanQuestionsAnswer answer3 = new PlanQuestionsAnswer();
		answer3.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_PENDING_ID);
		answer3.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_PENDING_ID);
		PlanAnswerSelection selection3 = new PlanAnswerSelection(2L, answer3, "JUnit");
		project.addPlanAnswerSelection(selection3);
		Assert.assertEquals("The question 'Will there be any data submitted ?' has not been answered.", gdsMissingDataUtil.getMissingGdsPlanData(project).get(4).getDisplayText());
		
		
	    //If the submission is a sub-project, GDS plan and missing data report do not exist
	  	project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NIHFUND);
	  	setAsSubproject(project);
	  	Assert.assertEquals(missingDataList, gdsMissingDataUtil.getMissingGdsPlanData(project));

   	}
   	
   	
   	@Test
	@Transactional
	public void getMissingIcListDataTest() {
   		
   		System.out.println("Starting junit for getMissingIcListDataTest");
		GdsMissingDataUtil gdsMissingDataUtil = GdsMissingDataUtil.getInstance();
		
		//Initial setup
		Project project = new Project();
		project.setId(2L);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NIHFUND);
		ArrayList<MissingData> missingDataList = new ArrayList<MissingData>();
   		
		// When the user selects non-human only, there is no Ic page and so no missing data report
		PlanQuestionsAnswer answer = new PlanQuestionsAnswer();
		answer.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_NONHUMAN_ID);
		PlanAnswerSelection selection = new PlanAnswerSelection(1L, answer, "JUnit");
		project.addPlanAnswerSelection(selection);
		Assert.assertEquals(missingDataList, gdsMissingDataUtil.getMissingIcListData(project));
		
		// when Data Submitted is "NO", there is no Ic
		project.removePlanAnswerSelection(selection.getId());
		answer.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_NO_ID);
		selection.setPlanQuestionsAnswer(answer);
		project.addPlanAnswerSelection(selection);
		Assert.assertEquals(missingDataList, gdsMissingDataUtil.getMissingIcListData(project));
		

		// If user selects ONLY the "Other" repository in the "What repository will the data be submitted to?" 
        //question GDS plan page, there is no IC, so return empty list
		project.removePlanAnswerSelection(selection.getId());
		answer.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_OTHER_ID);
		answer.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID);
		selection.setPlanQuestionsAnswer(answer);
		project.addPlanAnswerSelection(selection);
		//System.out.println("ic missing data123" +gdsMissingDataUtil.getMissingIcListData(project).get(0).getDisplayText());
		//Assert.assertEquals(missingDataList, gdsMissingDataUtil.getMissingIcListData(project));
		Assert.assertEquals(missingDataList, gdsMissingDataUtil.getMissingIcListData(project));
		Assert.assertFalse(CollectionUtils.isEmpty(project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID)));
		Assert.assertEquals(project.getPlanAnswerSelectionByQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID).size(), 1);
		System.out.println("plan answers el" +project.getPlanAnswerSelectionById(1L));
		Assert.assertEquals(project.getPlanAnswerSelectionById(1L).getId(),Long.valueOf(1L));
		
		// List of Ic`s and Studies
		List<InstitutionalCertification> ics = new ArrayList<InstitutionalCertification>();
		List<Study> studies = new ArrayList<Study>();
		
		Assert.assertTrue(CollectionUtils.isEmpty(ics));
		Assert.assertTrue(CollectionUtils.isEmpty(studies));
	    
		ManageProjectService manageProject = Mockito.mock(ManageProjectService.class);
		Mockito.when(manageProject.findById(1L)).thenReturn(project);
		Mockito.when(manageProject.deleteStudy(1L, project)).thenReturn(true);
		Mockito.when(manageProject.getSubprojects(1L, true)).thenReturn(new ArrayList<Project>());
		Mockito.when(manageProject.saveOrUpdate(any(Project.class))).thenReturn(project);
		gdsMissingDataUtil.manageProjectService = manageProject;
		

	    Study stu = new Study();
	    stu.setId(1L);
	    stu.setStudyName("study1");
	    stu.setInstitution("ins1");
	    stu.setCreatedBy("Mounica Ganta");
	    stu.setProject(project);
	    Mockito.when(manageProject.saveStudy(stu)).thenReturn(stu);
	    Mockito.when(manageProject.saveOrUpdate(project)).thenReturn(project);
	    gdsMissingDataUtil.getMissingIcListData(project);
	    
	    //docs
	    HashMap<Long, Document> docMap = new HashMap<Long, Document>();
		List<Document> docs = new ArrayList<Document> ();
		Document doc1 = new Document();
		doc1.setId(2L);
		doc1.setInstitutionalCertificationId(1L);
		doc1.setProjectId(2L);
		docs.add(doc1);
	    FileUploadService fileUploadServiceMock1 = Mockito.mock(FileUploadService.class);
		Mockito.when(fileUploadServiceMock1.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_IC, 2L)).thenReturn(docs);
		gdsMissingDataUtil.fileUploadService = fileUploadServiceMock1;
	    
	    
		// Empty missing Data, return null
		setAsSubproject(project);
		ManageProjectService manageProjectServiceMock = Mockito.mock(ManageProjectService.class);
		Mockito.when(manageProjectServiceMock.findById(project.getParentProjectId())).thenReturn(project);
		gdsMissingDataUtil.manageProjectService = manageProjectServiceMock;
		project.removePlanAnswerSelection(selection.getId());
		PlanQuestionsAnswer answer3 = new PlanQuestionsAnswer();
		answer3.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_NONHUMAN_ID);
		answer3.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_NONHUMAN_ID);
		PlanAnswerSelection selection3 = new PlanAnswerSelection(24L, answer3, "JUnit");
		project.getParent().addPlanAnswerSelection(selection3);
		gdsMissingDataUtil.getMissingIcListData(project);
		System.out.println("ic missing data" +gdsMissingDataUtil.getMissingIcListData(project).get(0).getDisplayText());
   	}
   	
   	
   	@Test
	@Transactional
	public void getMissingBsiDataTest() {
   		
   		System.out.println("Starting junit for getMissingBsiDataTest");
   		GdsMissingDataUtil gdsMissingDataUtil = GdsMissingDataUtil.getInstance();
		
		//Initial setup
		Project project = new Project();
		project.setId(2L);
		
		//If the BSI reviewed flag is "no"
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NIHFUND);
		project.setBsiReviewedId(ApplicationConstants.BSI_NO);
		String text = "BSI Reviewed flag must be 'Yes'.";
		Assert.assertEquals(text,gdsMissingDataUtil.getMissingBsiData(project).get(0).getDisplayText());
		
		// If submission reason id is non-nih funded and bsi reviewed flag is "NA"
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NONNIHFUND);
		project.setBsiReviewedId(ApplicationConstants.BSI_NA);
		text = "The question 'What repository will the data be submitted to ?' has not been answered.";
		Assert.assertEquals(text,gdsMissingDataUtil.getMissingBsiData(project).get(0).getDisplayText());
   	}
   	
   	@Test
	@Transactional
	public void getMissingRepositoryListDataTest() {
   		
   		System.out.println("Starting junit for getMissingRepositoryListDataTest");
   		GdsMissingDataUtil gdsMissingDataUtil = GdsMissingDataUtil.getInstance();
		
		//Initial setup
		Project project = new Project();
		project.setId(2L);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
		project.setSubprojectFlag(ApplicationConstants.FLAG_NO);
		
		// when submission reason is non-nih funded and no repositories are selected for a parent project
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NONNIHFUND);
		project.setRepoCount(Long.valueOf(0));
		String data = "To track the Submission Status of the repositories for this submission, please select the applicable repositories on the Basic Study Information page";
		Assert.assertEquals(data,gdsMissingDataUtil.getMissingRepositoryListData(project).get(0).getDisplayText());
		
		// when submission reason id is not non-nih funded and no repositories are selected
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NIHFUND);
		data = "To track the Submission Status of the repositories for this submission, please select the applicable repositories on the Genomic Data Sharing page";
		Assert.assertEquals(data,gdsMissingDataUtil.getMissingRepositoryListData(project).get(0).getDisplayText());
		
		// For a sub-project with no repositories selected on parent
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NIHFUND);
		project.setRepoCount(Long.valueOf(0));
		Project subProject=new Project();
		subProject.setId(3L);
		subProject.setParent(project);
		subProject.setParentProjectId(project.getId());
		subProject.setSubprojectFlag(ApplicationConstants.FLAG_YES);
		ManageProjectService manageProjectServiceMock = Mockito.mock(ManageProjectService.class);
		Mockito.when(manageProjectServiceMock.findById(2L)).thenReturn(project);
		Mockito.when(manageProjectServiceMock.saveOrUpdate(any(Project.class))).thenReturn(project);
		gdsMissingDataUtil.manageProjectService = manageProjectServiceMock;
		data = "Select the applicable repositories on the Parent Project to track the Submission Status of the repositories for this submission.";
		Assert.assertEquals(data,gdsMissingDataUtil.getMissingRepositoryListData(subProject).get(0).getDisplayText());
		
		project.setRepoCount(Long.valueOf(1L));
		subProject.setRepoCount(Long.valueOf(0));
		Assert.assertEquals("At least one repository must be selected.",gdsMissingDataUtil.getMissingRepositoryListData(subProject).get(0).getDisplayText());
		Assert.assertEquals(project.getRepoCount(), Long.valueOf(1L));
		
		
		//
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
		
		rep.setLookupTByStudyReleasedId(lookup);
		rep.setLookupTByRegistrationStatusId(lookup2);
		rep.setLookupTBySubmissionStatusId(lookup1);
		rep.setPlanAnswerSelectionTByRepositoryId(selection);

		repositoryStatuses.add(rep);
		repositoryStatuses1.add(rep);
		selection.setRepositoryStatuses(repositoryStatuses1);
		project.setRepositoryStatuses(repositoryStatuses);
		
		gdsMissingDataUtil.getMissingRepositoryListData(project);
   	}
   	
   	@Test
   	@Transactional
   	public void testGetMissingrepositoryData() {
   		
   		GdsMissingDataUtil gdsMissingDataUtil = GdsMissingDataUtil.getInstance();
		
		//Initial setup
		Project project = new Project();
		project.setId(2L);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
		project.setSubprojectFlag(ApplicationConstants.FLAG_NO);
		
		RepositoryStatus rep = new RepositoryStatus(); 
		rep.setId(3L);
		ManageProjectService manageProjectServiceMock = Mockito.mock(ManageProjectService.class);
		Mockito.when(manageProjectServiceMock.findRepositoryById(3L)).thenReturn(rep);
		Mockito.when(manageProjectServiceMock.saveOrUpdate(any(Project.class))).thenReturn(project);
		gdsMissingDataUtil.manageProjectService = manageProjectServiceMock;
		
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
		
		rep.setLookupTByStudyReleasedId(lookup);
		rep.setLookupTByRegistrationStatusId(lookup2);
		rep.setLookupTBySubmissionStatusId(lookup1);
		rep.setPlanAnswerSelectionTByRepositoryId(selection);

		repositoryStatuses.add(rep);
		repositoryStatuses1.add(rep);
		selection.setRepositoryStatuses(repositoryStatuses1);
		project.setRepositoryStatuses(repositoryStatuses);
		
		gdsMissingDataUtil.getMissingRepositoryData(project, Long.valueOf(3L));
   	}
   	
   	@Test
   	@Transactional
   	public void testGetMissingIcData() {
   		System.out.println("Starting Junit test caes for getMissingICData");
   		GdsMissingDataUtil gdsMissingDataUtil = GdsMissingDataUtil.getInstance();
   		
   		Project project = new Project();
		project.setId(2L);
		project.setParentProjectId(null);
		project.setSubprojectFlag(ApplicationConstants.FLAG_NO);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NIHFUND);
		InstitutionalCertification ic = new InstitutionalCertification();
		List<MissingData> missingDataList = new ArrayList<MissingData>(); 
		ManageProjectService manageProjectServiceMock = Mockito.mock(ManageProjectService.class);
		Mockito.when(manageProjectServiceMock.findById(2L)).thenReturn(project);
		Mockito.when(manageProjectServiceMock.saveOrUpdate(any(Project.class))).thenReturn(project);
		Mockito.when(manageProjectServiceMock.findIcById(1L)).thenReturn(ic);
		gdsMissingDataUtil.manageProjectService = manageProjectServiceMock;
		
		List<Document> docs = new ArrayList<Document> ();
		Document doc = new Document();
		doc.setId(2L);
		doc.setInstitutionalCertificationId(3L);
		docs.add(doc);
		FileUploadService fileUploadServiceMock = Mockito.mock(FileUploadService.class);
		Mockito.when(fileUploadServiceMock.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_IC, 1L)).thenReturn(docs);
		gdsMissingDataUtil.fileUploadService = fileUploadServiceMock;
		gdsMissingDataUtil.getMissingIcData(project, Long.valueOf(3L));
		
		//for a subproject
		
		setAsSubproject(project);
		Mockito.when(manageProjectServiceMock.findById(1L)).thenReturn(project);
   	}

   	private void setAsSubproject(Project project) {
		Project parent = new Project();
		parent.setId(1L);
		project.setParent(parent);
		project.setParentProjectId(parent.getId());
		project.setSubprojectFlag(ApplicationConstants.FLAG_YES);
	}
  
}

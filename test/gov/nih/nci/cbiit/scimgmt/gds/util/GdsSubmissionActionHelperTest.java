package gov.nih.nci.cbiit.scimgmt.gds.util;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.HashSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.DulChecklist;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Organization;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanQuestionsAnswer;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectGrantContract;
import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;
import gov.nih.nci.cbiit.scimgmt.gds.model.ParentDulChecklist;
import gov.nih.nci.cbiit.scimgmt.gds.services.LookupService;

public class GdsSubmissionActionHelperTest {
	
private static final Logger logger = LogManager.getLogger(GdsSubmissionActionHelperTest.class);
	
	@Autowired
	protected LookupService lookupService;
   	
   	GdsSubmissionActionHelper gdsSubmissionHelper = new GdsSubmissionActionHelper();
	
	@Test
	@Transactional
	public void isProjectEligibleForVersionTest() {
		
		System.out.println("Starting junit for isProjectEligibleForVersionTest");
		//Initial setup
		Project project = new Project();
		project.setSubprojectFlag(ApplicationConstants.FLAG_NO);
		project.setId(1L);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
		
	   //add repositories
		PlanQuestionsAnswer answer = new PlanQuestionsAnswer();
		answer.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID);
		answer.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID);
		PlanAnswerSelection selection = new PlanAnswerSelection(1L, answer, "JUnit");
		project.addPlanAnswerSelection(selection);
		System.out.println("ansgewr plan:" +selection.getPlanQuestionsAnswer().getQuestionId());
		Assert.assertEquals(Long.valueOf(20L), selection.getPlanQuestionsAnswer().getQuestionId());
		
		List<RepositoryStatus> repositoryStatuses = new ArrayList<RepositoryStatus>(0);
		 Set<RepositoryStatus> repositoryStatuses1 = new HashSet(0);
		RepositoryStatus rep = new RepositoryStatus();
		rep.setId(2L);
		rep.setProject(project);
		Lookup lookup = new Lookup();
		lookup.setId(16L);
		lookup.setCode("YES");
		rep.setLookupTByStudyReleasedId(lookup);
		repositoryStatuses.add(rep);
		repositoryStatuses1.add(rep);
		selection.setRepositoryStatuses(repositoryStatuses1);
		project.setRepositoryStatuses(repositoryStatuses);
		System.out.println("anser here " +GdsSubmissionActionHelper.isProjectEligibleForVersion(project));
		System.out.println("rep " +repositoryStatuses.size());
		Assert.assertEquals(ApplicationConstants.FLAG_YES, GdsSubmissionActionHelper.isProjectEligibleForVersion(project));
		Assert.assertEquals(rep.getProject().getId(),Long.valueOf(1L));
		Assert.assertEquals(project.getId(),Long.valueOf(1L));
		Assert.assertEquals(Long.valueOf(16L), rep.getLookupTByStudyReleasedId().getId());
		
		//scenario 2 
		repositoryStatuses.clear();
		repositoryStatuses1.clear();
		Assert.assertEquals(ApplicationConstants.FLAG_NO, GdsSubmissionActionHelper.isProjectEligibleForVersion(project));
		
		//scenario 3
		repositoryStatuses.clear();
		repositoryStatuses1.clear();
		RepositoryStatus repo = new RepositoryStatus();
		repo.setId(3L);
		repo.setProject(project);
		Lookup lookUp = new Lookup();
		lookUp.setId(17L);
		lookUp.setCode("No");
		repo.setLookupTByStudyReleasedId(lookUp);
		repositoryStatuses.add(repo);
		repositoryStatuses1.add(repo);
		selection.setRepositoryStatuses(repositoryStatuses1);
		project.setRepositoryStatuses(repositoryStatuses);
		Assert.assertEquals(ApplicationConstants.FLAG_NO, GdsSubmissionActionHelper.isProjectEligibleForVersion(project));
		
	}
  
	@Test
	@Transactional
	public void isEligibleForSubprojectTest() {
		
		System.out.println("Starting junit for isEligibleForSubprojectTest");
		
		//Initial setup
		Project project = new Project();
		project.setId(2L);	
		
		// when plan answer is PLAN_QUESTION_ANSWER_DATA_SUBMITTED_YES_ID and submission reason id is SUBMISSION_REASON_GDSPOLICY
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
		PlanQuestionsAnswer answer = new PlanQuestionsAnswer();
		answer.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_YES_ID);
		PlanAnswerSelection selection = new PlanAnswerSelection(1L, answer, "JUnit");
		project.addPlanAnswerSelection(selection);
		Assert.assertEquals(true, GdsSubmissionActionHelper.isEligibleForSubproject(project));
		
		
		// when plan answer is  PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_NO_ID and submission reason id is SUBMISSION_REASON_GWASPOLICY
	    project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GWASPOLICY);
	    project.removePlanAnswerSelection(selection.getId());
	    answer.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_NO_ID);
		selection.setPlanQuestionsAnswer(answer);
		project.addPlanAnswerSelection(selection);
		Assert.assertEquals(true, GdsSubmissionActionHelper.isEligibleForSubproject(project));
		
		// When plan answers are PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_YES_ID and PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_NO_ID
		project.removePlanAnswerSelection(selection.getId());
		answer.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_YES_ID);
		selection.setPlanQuestionsAnswer(answer);
		project.addPlanAnswerSelection(selection);
		
		PlanQuestionsAnswer answer1 = new PlanQuestionsAnswer();
		answer1.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_NO_ID);
		PlanAnswerSelection selection1 = new PlanAnswerSelection(1L, answer1, "JUnit");
		project.addPlanAnswerSelection(selection1);
		Assert.assertEquals(true, GdsSubmissionActionHelper.isEligibleForSubproject(project));
		
		// submission reason is nih funded
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NIHFUND);
		Assert.assertEquals(true,GdsSubmissionActionHelper.isEligibleForSubproject(project));
		
		//submission reason is non-nih funded
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NONNIHFUND);
		Assert.assertEquals(true,GdsSubmissionActionHelper.isEligibleForSubproject(project));
	
	}
	
	@Test
	@Transactional
	public void willThereBeAnyDataSubmittedInGdsPlanTest() {
		
        System.out.println("Starting junit for willThereBeAnyDataSubmittedInGdsPlanTest");
		
		//Initial setup
		Project project = new Project();
		project.setId(2L);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
		
	   // when plan answer is PLAN_QUESTION_ANSWER_DATA_SUBMITTED_NO_ID 
				
		PlanQuestionsAnswer answer = new PlanQuestionsAnswer();
		answer.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_NO_ID);
		PlanAnswerSelection selection = new PlanAnswerSelection(1L, answer, "JUnit");
		project.addPlanAnswerSelection(selection);
		Assert.assertEquals(false, GdsSubmissionActionHelper.willThereBeAnyDataSubmittedInGdsPlan(project));
		
		//scenario 2
		project.removePlanAnswerSelection(selection.getId());
		PlanQuestionsAnswer answer1 = new PlanQuestionsAnswer();
		answer1.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_NO_ID);
		PlanAnswerSelection selection1 = new PlanAnswerSelection(1L, answer1, "JUnit");
		project.addPlanAnswerSelection(selection1);
		Assert.assertEquals(true, GdsSubmissionActionHelper.willThereBeAnyDataSubmittedInGdsPlan(project));
		
	}
	
	@Test
	@Transactional
	public void isSubmissionUpdatedTest() {
		
		 System.out.println("Starting junit for isSubmissionUpdatedTest");
			
			//Initial setup
			Project project = new Project();
			project.setId(2L);
			
			//submission reason id is changed from non nih funded to gds policy
			project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NONNIHFUND);
			Project updatedProject = new Project();
			updatedProject.setId(3L);
			updatedProject.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
			Assert.assertNotNull(updatedProject.getSubmissionReasonId());
			Assert.assertNotNull(project.getSubmissionReasonId());
			
			Assert.assertEquals(true, GdsSubmissionActionHelper.isSubmissionUpdated(project, updatedProject));
			Assert.assertEquals(project.getSubmissionReasonId(), Long.valueOf(ApplicationConstants.SUBMISSION_REASON_NONNIHFUND));
			Assert.assertEquals(updatedProject.getSubmissionReasonId(), Long.valueOf(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY));
			
			//scenario 2 
			project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NONNIHFUND);
			updatedProject.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GWASPOLICY);
			Assert.assertEquals(true, GdsSubmissionActionHelper.isSubmissionUpdated(project, updatedProject));
			Assert.assertEquals(project.getSubmissionReasonId(), Long.valueOf(ApplicationConstants.SUBMISSION_REASON_NONNIHFUND));
			Assert.assertEquals(updatedProject.getSubmissionReasonId(), Long.valueOf(ApplicationConstants.SUBMISSION_REASON_GWASPOLICY));
			
			//scenario 3
			project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NONNIHFUND);
			updatedProject.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NIHFUND);
			Assert.assertEquals(true, GdsSubmissionActionHelper.isSubmissionUpdated(project, updatedProject));
			Assert.assertEquals(project.getSubmissionReasonId(), Long.valueOf(ApplicationConstants.SUBMISSION_REASON_NONNIHFUND));
			Assert.assertEquals(updatedProject.getSubmissionReasonId(), Long.valueOf(ApplicationConstants.SUBMISSION_REASON_NIHFUND));
			
			//scenario 4
			project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NIHFUND);
			updatedProject.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NIHFUND);
			Assert.assertEquals(false, GdsSubmissionActionHelper.isSubmissionUpdated(project, updatedProject));
			Assert.assertEquals(project.getSubmissionReasonId(), Long.valueOf(ApplicationConstants.SUBMISSION_REASON_NIHFUND));
			Assert.assertEquals(updatedProject.getSubmissionReasonId(), Long.valueOf(ApplicationConstants.SUBMISSION_REASON_NIHFUND));
			
			//scenario 5
			project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
			updatedProject.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NONNIHFUND);
			Assert.assertEquals(true, GdsSubmissionActionHelper.isSubmissionUpdated(project, updatedProject));
			Assert.assertEquals(project.getSubmissionReasonId(), Long.valueOf(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY));
			Assert.assertEquals(updatedProject.getSubmissionReasonId(), Long.valueOf(ApplicationConstants.SUBMISSION_REASON_NONNIHFUND));
	
			
			//scenario 6
			project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GWASPOLICY);
			updatedProject.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NONNIHFUND);
			Assert.assertEquals(true, GdsSubmissionActionHelper.isSubmissionUpdated(project, updatedProject));
			Assert.assertEquals(project.getSubmissionReasonId(), Long.valueOf(ApplicationConstants.SUBMISSION_REASON_GWASPOLICY));
			Assert.assertEquals(updatedProject.getSubmissionReasonId(), Long.valueOf(ApplicationConstants.SUBMISSION_REASON_NONNIHFUND));
			
			//scenario 7
			project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NIHFUND);
			updatedProject.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NONNIHFUND);
			Assert.assertEquals(true, GdsSubmissionActionHelper.isSubmissionUpdated(project, updatedProject));
			Assert.assertEquals(project.getSubmissionReasonId(), Long.valueOf(ApplicationConstants.SUBMISSION_REASON_NIHFUND));
			Assert.assertEquals(updatedProject.getSubmissionReasonId(), Long.valueOf(ApplicationConstants.SUBMISSION_REASON_NONNIHFUND));
	}
	
	@Test
	@Transactional
	public void testGetLookupDropDownList() {
		List<Lookup> lookupList = new ArrayList<Lookup>();
		Lookup lookup = new Lookup();
		lookupList.add(lookup);
		LookupService lookupServiceMock = Mockito.mock(LookupService.class);
		Mockito.when(lookupServiceMock.getLookupList(ApplicationConstants.BSI_REVIEWED.toUpperCase()))
				.thenReturn(lookupList);
		gdsSubmissionHelper.lookupService = lookupServiceMock;
		
		List<DropDownOption> dropdownList = new ArrayList<DropDownOption>();
		DropDownOption dropdown = new DropDownOption("55","Yes");
		DropDownOption dropdown1 = new DropDownOption("56","No");
		DropDownOption dropdown2 = new DropDownOption("57","Not Applicable");
		dropdownList.add(dropdown);
		dropdownList.add(dropdown1);
		dropdownList.add(dropdown2);
		List<DropDownOption> listDropDown = GdsSubmissionActionHelper.getLookupDropDownList(ApplicationConstants.BSI_REVIEWED.toUpperCase());
		Assert.assertEquals(dropdownList.size(), listDropDown.size());
	}
	
	@Test
	@Transactional
	public void testGetDocDropDownList() {
		List<Organization> orgList = new ArrayList<Organization>();
		Organization org = new Organization();
		orgList.add(org);
		LookupService lookupServiceMock = Mockito.mock(LookupService.class);
		@SuppressWarnings("unchecked")
		List<Organization> list = (List<Organization>)lookupServiceMock.getDocList(ApplicationConstants.DOC_LIST);
		Mockito.when(list).thenReturn(orgList);
		gdsSubmissionHelper.lookupService = lookupServiceMock;
		
		List<DropDownOption> dropdownList = new ArrayList<DropDownOption>();
		DropDownOption dropdown = new DropDownOption("HNC7","CCR");
		DropDownOption dropdown1 = new DropDownOption("HNCC","DCB");
		DropDownOption dropdown2 = new DropDownOption("HNCD","DCCPS");
		dropdownList.add(dropdown);
		dropdownList.add(dropdown1);
		dropdownList.add(dropdown2);
		List<DropDownOption> listDropDown = GdsSubmissionActionHelper.getDocDropDownList();
		String sacid = listDropDown.get(0).getOptionKey();
		Assert.assertEquals(sacid, dropdown.getOptionKey());
		Assert.assertEquals(listDropDown.get(0).getOptionValue(), dropdown.getOptionValue());
	}
	
	@Test
	@Transactional
	public void testPopulateDocDropDownList() {
		List<DropDownOption> dropdownList = new ArrayList<DropDownOption>();
		
		List<Organization> orgList = new ArrayList<Organization>();
		Organization org1 = new Organization();
		org1.setNihorgpath("CCR");
		org1.setNihsac("HNC7");
		org1.setNihouacronym("CCR");
		org1.setNihouname("CENTER FOR CANCER RESEARCH");
		orgList.add(org1);
		Organization org2 = new Organization();
		org2.setNihorgpath("DCB");
		org2.setNihsac("HNCC");
		org2.setNihouacronym("DCB");
		org2.setNihouname("DIVISION OF CANCER BIOLOGY");
		orgList.add(org2);
		GdsSubmissionActionHelper.populateDocDropDownList(dropdownList, orgList);
		Assert.assertEquals(dropdownList.get(0).getOptionKey(), "CCR");
		Assert.assertEquals(dropdownList.get(0).getOptionValue(), "CCR");
	}
	
	@Test
	@Transactional
	public void testPopulateProgDropDownList() {
		
		List<DropDownOption> dropdownList = new ArrayList<DropDownOption>();
		List<String> progList = new ArrayList<String>();
		progList.add("option1");
		progList.add("option2");
		progList.add("option3");
		dropdownList = GdsSubmissionActionHelper.populateProgDropDownList(dropdownList, progList);
		//Assert.assertEquals(dropdownList, actual);
		
	}
	
	@Test
	@Transactional
	public void testRemovelookUpFromDropDownList() {
		
		List<DropDownOption> dropdownList = new ArrayList<DropDownOption>();
		DropDownOption dropdown = new DropDownOption("55","Yes");
		DropDownOption dropdown1 = new DropDownOption("56","No");
		DropDownOption dropdown2 = new DropDownOption("57","Not Applicable");
		dropdownList.add(dropdown);
		dropdownList.add(dropdown1);
		dropdownList.add(dropdown2);
		dropdownList = GdsSubmissionActionHelper.removeLookupFromDropDownList(dropdownList, Long.valueOf(55L));
		Assert.assertEquals(dropdownList.size(), 2);
	}
	
	@Test
	@Transactional
	public void testGetLookUpDropDownCodeList() {
		
		List<Lookup> lookupList = new ArrayList<Lookup>();
		Lookup lookup = new Lookup();
		lookupList.add(lookup);
		LookupService lookupServiceMock = Mockito.mock(LookupService.class);
		Mockito.when(lookupServiceMock.getLookupList(ApplicationConstants.BSI_REVIEWED.toUpperCase()))
				.thenReturn(lookupList);
		gdsSubmissionHelper.lookupService = lookupServiceMock;
		
		List<DropDownOption> dropdownList = new ArrayList<DropDownOption>();
		DropDownOption dropdown = new DropDownOption("YES","Yes");
		DropDownOption dropdown1 = new DropDownOption("NO","No");
		DropDownOption dropdown2 = new DropDownOption("NA","Not Applicable");
		dropdownList.add(dropdown);
		dropdownList.add(dropdown1);
		dropdownList.add(dropdown2);
		List<DropDownOption> listDropDown = GdsSubmissionActionHelper.getLookupDropDownCodeList(ApplicationConstants.BSI_REVIEWED.toUpperCase());
		Assert.assertEquals(dropdownList.size(), listDropDown.size());
	}
	
	@Test
	@Transactional
	public void testGetDulCheckList() {
		List<DulChecklist> checklist = new ArrayList<DulChecklist>();
		DulChecklist dul = new DulChecklist();
		checklist.add(dul);
		LookupService lookupServiceMock = Mockito.mock(LookupService.class);
		Mockito.when(lookupServiceMock.getDulChecklists("allDuls"))
		.thenReturn(checklist);
         gdsSubmissionHelper.lookupService = lookupServiceMock;
         DulChecklist retrievedUl = GdsSubmissionActionHelper.getDulChecklist(Long.valueOf(1L));
		 System.out.println("dul id " + retrievedUl.getId());
		 Assert.assertEquals(retrievedUl.getId(), Long.valueOf(1));
		 Assert.assertEquals(null, GdsSubmissionActionHelper.getDulChecklist(null));
	}
	
	@Test
	@Transactional
	public void testGetLoggedOnOsersDOC() {
		List<Organization> orgList = new ArrayList<Organization>();
		Organization org1 = new Organization();
		org1.setNihorgpath("CCR");
		org1.setNihsac("HNC7");
		org1.setNihouacronym("CCR");
		org1.setNihouname("CENTER FOR CANCER RESEARCH");
		orgList.add(org1);
		Organization org2 = new Organization();
		org2.setNihorgpath("DCB");
		org2.setNihsac("HNCC");
		org2.setNihouacronym("DCB");
		org2.setNihouname("DIVISION OF CANCER BIOLOGY");
		orgList.add(org2);
		Assert.assertEquals(ApplicationConstants.NED_PERSON_NIH_SAC_MIN_SIZE, org1.getNihsac().length());
		String selectedDoc = GdsSubmissionActionHelper.getLoggedonUsersDOC(orgList, "HNC7");
		Assert.assertEquals(selectedDoc, org1.getNihorgpath());
		String empty = GdsSubmissionActionHelper.getLoggedonUsersDOC(orgList, "HNC7dwqd");
		Assert.assertEquals(empty, org1.getNihorgpath());
		System.out.println("empty string" + empty);
	}
	
	@Test
	@Transactional
	public void testPopolateProjectProperties() {
		  System.out.println("Starting junit for populateProject properties");
			
			//Initial setup
			Project transientProject = new Project();
			transientProject.setId(2L);
			transientProject.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NONNIHFUND);
			transientProject.setSubmissionTitle("Test Submission");
			transientProject.setDocAbbreviation("OD CBIIT");
			transientProject.setProgramBranch("CBIIT/ESIB");
			transientProject.setComments("general info page comments");
			Project persistentProject = new Project();
			persistentProject.setId(3L);
			persistentProject.setSubprojectFlag(ApplicationConstants.FLAG_NO);
			
			ProjectGrantContract extramuralGrant = new ProjectGrantContract("Extramural",ApplicationConstants.FLAG_YES);
			extramuralGrant.setId(34L);
			extramuralGrant.setDataLinkFlag(ApplicationConstants.FLAG_YES);
			transientProject.setPrimaryGrant("Extramural", extramuralGrant);
			transientProject.addProjectGrantContract(extramuralGrant);
			
			ProjectGrantContract intramuralGrant = new ProjectGrantContract("Intramural",ApplicationConstants.FLAG_YES);
			intramuralGrant.setId(35L);
			transientProject.setPrimaryGrant("Intramural", intramuralGrant);
			transientProject.addProjectGrantContract(intramuralGrant);
			
			List<ProjectGrantContract> assocGrants = new ArrayList<ProjectGrantContract>();
			ProjectGrantContract associatedGrant = new ProjectGrantContract("",ApplicationConstants.FLAG_NO);
			associatedGrant.setId(36L);
			assocGrants.add(associatedGrant);
			transientProject.setAssociatedGrants(assocGrants);
			
			persistentProject = GdsSubmissionActionHelper.popoulateProjectProperties(transientProject, persistentProject);
			Assert.assertEquals(persistentProject.getSubmissionTitle(), transientProject.getSubmissionTitle());
			Assert.assertEquals(persistentProject.getComments(), transientProject.getComments());
			Assert.assertEquals(persistentProject.getSubmissionReasonId(), transientProject.getSubmissionReasonId());
			Assert.assertEquals(persistentProject.getDocAbbreviation(), transientProject.getDocAbbreviation());
			Assert.assertEquals(persistentProject.getProgramBranch(), transientProject.getProgramBranch());
			Assert.assertEquals(persistentProject.getAssociatedGrants(), transientProject.getAssociatedGrants());
			Assert.assertEquals(persistentProject.getPrimaryGrant("Extramural"), transientProject.getPrimaryGrant("Extramural"));
			Assert.assertEquals(persistentProject.getPrimaryGrant("Intramural"), transientProject.getPrimaryGrant("Intramural"));	
	}
	
	@Test
	@Transactional
	public void testGetDulChecklistsSets() {
		
		Project project = new Project();
		project.setId(2L);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NONNIHFUND);
		
		System.out.println("Starting Junit test cases for getDulChecklistsSets");
		List<DulChecklist> checklist = new ArrayList<DulChecklist>();
		DulChecklist dul = new DulChecklist();
		checklist.add(dul);
		LookupService lookupServiceMock = Mockito.mock(LookupService.class);
		Mockito.when(lookupServiceMock.getDulChecklists("allDuls"))
		.thenReturn(checklist);
         gdsSubmissionHelper.lookupService = lookupServiceMock;
		
         // setting plan question answers
        PlanQuestionsAnswer answer = new PlanQuestionsAnswer();
 		answer.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_HUMAN_ID);
 		PlanAnswerSelection selection = new PlanAnswerSelection(1L, answer, "JUnit");
 		project.addPlanAnswerSelection(selection);
 		
 		 PlanQuestionsAnswer answer1 = new PlanQuestionsAnswer();
  		answer1.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_ACCESS_UNRESTRICTED_ID);
  		PlanAnswerSelection selection1 = new PlanAnswerSelection(3L, answer1, "JUnit");
  		project.addPlanAnswerSelection(selection1);
  		List<ParentDulChecklist> dulList = new ArrayList<ParentDulChecklist>();
  		dulList = GdsSubmissionActionHelper.getDulChecklistsSets(project);
  		Assert.assertEquals(project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_HUMAN_ID).getId(), Long.valueOf(1L));
  		Assert.assertEquals(project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_ACCESS_UNRESTRICTED_ID).getId(), Long.valueOf(3L));
	}
}

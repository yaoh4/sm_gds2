package gov.nih.nci.cbiit.scimgmt.gds.actions;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import com.opensymphony.xwork2.ActionContext;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.InstitutionalCertification;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PageStatus;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanQuestionsAnswer;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Study;
import gov.nih.nci.cbiit.scimgmt.gds.services.FileUploadService;
import gov.nih.nci.cbiit.scimgmt.gds.services.LookupService;
import gov.nih.nci.cbiit.scimgmt.gds.services.ManageProjectService;


@RunWith(MockitoJUnitRunner.class)
public class SubmissionStudyTest {
	
	static Logger logger = LogManager.getLogger(SubmissionStudyTest.class);
	Project project;
	SubmissionStudyAction submissionStudy = new SubmissionStudyAction() {

		private static final long serialVersionUID = 1L;

			@Override
			public String getText(String textName) {
				return "dummy";
			}

			@Override
			public String getText(String key, String[] args) {
				return "dummy";

			}
		};

	@Test
	public void testRetrieveStudy() {
	
		Project project = new Project();
		project.setId(1L);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
		submissionStudy.setProjectId("1");
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
		mockServices(submissionStudy, project);
		//submissionStudy.setListStudies(studies);
		System.out.println("study is here is " + project.getStudyById(2L).getId());
		Assert.assertEquals(project.getStudyById(2L).getId(), Long.valueOf(2L));
		Assert.assertEquals(project.getStudyById(4L), null);
		
		Assert.assertEquals(submissionStudy.retrieveStudy(Long.valueOf(2L)), study1);
	}
	
	@Test
	public void testDeleteStudy() {
       System.out.println("Starting junit for deleteStudy");
		
		Project project = new Project();
		project.setId(1L);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
		
	
		submissionStudy.setProjectId("1");
		submissionStudy.setStudyId("1");
		
		mockServices(submissionStudy, project);
		
		Assert.assertEquals(submissionStudy.deleteStudy(), "success");
	}
	
	@Test
	public void testValidateSaveStudy() {
       System.out.println("Starting junit for validateSaveStudy");
		
		Project project = new Project();
		project.setId(1L);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
	
		submissionStudy.setProjectId("1");
		//Validate Study save
		Study stu = new Study();
		stu.setId(1L);
	    stu.setStudyName("Study1");
	    stu.setInstitution("Institution1");
	    submissionStudy.setStudy(stu);
	    submissionStudy.setProject(project);

		mockServices(submissionStudy, project);
		submissionStudy.validateSaveStudy();
		Assert.assertEquals(project.getStudies(), submissionStudy.getListStudies());
		Assert.assertEquals(submissionStudy.getStudy().getStudyName(), "Study1");
		Assert.assertEquals(submissionStudy.getStudy().getInstitution(), "Institution1");
	    Assert.assertEquals(submissionStudy.getStudy(), stu);
	    Assert.assertEquals(0, submissionStudy.getActionErrors().size());
	    
	    // validation with no study name
	    
	    Study study1 = new Study();
	    study1.setStudyName(null);
	    study1.setInstitution(null);
	    submissionStudy.setStudy(study1);
	    submissionStudy.setProject(project);
	    mockServices(submissionStudy, project);
	    submissionStudy.validateSaveStudy();
	    System.out.println("error size" + submissionStudy.getActionErrors().size());
	    Assert.assertEquals(1, submissionStudy.getActionErrors().size());
	    

		
	}
	
	@Test
	public void testValidateSaveAndAddStudy() {
       System.out.println("Starting junit for validateSaveStudy");
		
		Project project = new Project();
		project.setId(1L);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
	
		submissionStudy.setProjectId("1");
		//Validate Study save
		Study stu = new Study();
		stu.setId(1L);
	    stu.setStudyName("Study1");
	    stu.setInstitution("Institution1");
	    submissionStudy.setStudy(stu);
		
		mockServices(submissionStudy, project);
		submissionStudy.validateSaveAndAddStudy();
		Assert.assertEquals(project.getStudies(), submissionStudy.getListStudies());
		Assert.assertEquals(submissionStudy.getStudy().getStudyName(), "Study1");
		Assert.assertEquals(submissionStudy.getStudy().getInstitution(), "Institution1");
	    Assert.assertEquals(submissionStudy.getStudy(), stu);
	    Assert.assertEquals(0, submissionStudy.getActionErrors().size());
	    
        // validation with no study name
	    
	    Study study3 = new Study();
	    study3.setId(3L);
	    study3.setStudyName(null);
	    study3.setInstitution(null);
	    submissionStudy.setStudy(study3);
	    submissionStudy.setProject(project);
	    mockServices(submissionStudy, project);
	    submissionStudy.validateSaveAndAddStudy();
	    System.out.println("error size11" + submissionStudy.getActionErrors().size());
	    Assert.assertEquals(1, submissionStudy.getActionErrors().size());
		
	}
	
	@Test
	public void testSaveStudy() {
       System.out.println("Starting junit for saveStudy");
		
       submissionStudy.setProjectId("1");
				
       submissionStudy.validateSaveStudy();
		
		Project project = new Project();
		project.setSubprojectFlag(ApplicationConstants.FLAG_NO);
		project.setId(1L);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
		project.setPlanAnswerSelections(new HashSet<PlanAnswerSelection>());
		submissionStudy.setProject(project);
		Study study4 = new Study();
	    study4.setId(4L);
	    study4.setStudyName("Study1");
	    study4.setInstitution("Ins2");
	    InstitutionalCertification ic = new InstitutionalCertification();
		ic.setId(1L);
		study4.addInstitutionalCertification(ic);
	    project.getStudies().add(study4);
	    submissionStudy.setStudy(study4);
	    
		mockServices(submissionStudy, project);
		Assert.assertEquals(submissionStudy.saveStudy(), "success");
		//submissionStudy.saveStudy();
		Study stu1 = submissionStudy.getStudy();
		System.out.println("stduy id is " + stu1.getId());
		
		List<PageStatus> pageStatuses = new ArrayList<PageStatus>();
		PageStatus ps = new PageStatus();
		Lookup page = new Lookup();
		page.setCode("IC");
		ps.setPage(page);
		Lookup s = new Lookup();
		s.setCode(ApplicationConstants.PAGE_STATUS_CODE_NOT_STARTED);
		ps.setStatus(s);
		pageStatuses.add(ps);
		project.setPageStatuses(pageStatuses);
		project.setParentProjectId(1L);
		project.setSubprojectFlag(ApplicationConstants.FLAG_YES);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
		project.setPlanAnswerSelections(new HashSet<PlanAnswerSelection>());
		PlanAnswerSelection pas = new PlanAnswerSelection();
		PlanQuestionsAnswer pqa = new PlanQuestionsAnswer();
		pqa.setQuestionId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_ID);
		pqa.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_NONHUMAN_ID);
		pas.setPlanQuestionsAnswer(pqa);
		project.addPlanAnswerSelection(pas);
		
		Study study  = new Study();
		study.setId(1L);
		project.getStudies().add(study);
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		ServletActionContext servletActionContext = Mockito.mock(ServletActionContext.class);
		ActionContext actionContext = Mockito.mock(ActionContext.class);
		ServletActionContext.setContext(actionContext);
		Mockito.when(servletActionContext.getRequest()).thenReturn(request);
		
		submissionStudy.saveStudy();		
        
	}
	
	@Test
	public void testSaveAndAddStudy() {
		System.out.println("Starting junit for saveAndAddStudy");
		
	      submissionStudy.setProjectId("1");
					
	      submissionStudy.validateSaveAndAddStudy();
			
		  Project project = new Project();
		  project.setSubprojectFlag(ApplicationConstants.FLAG_NO);
		  project.setId(1L);
		  project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
		  project.setPlanAnswerSelections(new HashSet<PlanAnswerSelection>());
		  submissionStudy.setProject(project);
			
		  mockServices(submissionStudy, project);
		 // submissionStudy.saveAndAddStudy();
		  Assert.assertEquals(submissionStudy.saveAndAddStudy(), "success");
	}
	
	@Test
	public void testExecute() throws Exception {
		System.out.println("Starting junit for execute");
		
		Project project = new Project();
		project.setId(1L);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
		
		submissionStudy.setProjectId("1");
		submissionStudy.setStudyId("1");
		Study study = submissionStudy.retrieveStudy(Long.valueOf(1L));
		List<Study> studiesList = new ArrayList<Study>();
		studiesList = project.getStudies();
		mockServices(submissionStudy, project);
		Assert.assertEquals(submissionStudy.execute(), "success");
		Assert.assertEquals(studiesList, submissionStudy.getListStudies());
		Assert.assertEquals(study, submissionStudy.retrieveStudy(Long.valueOf(1L)));
	}
	
	@Test
	public void testSetListStudies() {
       System.out.println("Starting junit for execute");
		
		Project project = new Project();
		project.setId(1L);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
		submissionStudy.setProjectId("1");
		List<Study> listStudies = new ArrayList<Study>();
		Study stu = new Study();
		stu.setId(1L);
		stu.setStudyName("study1");
		listStudies.add(stu);
		submissionStudy.setListStudies(listStudies);
	}
	
    private void mockServices(SubmissionStudyAction submissionStudy, Project project) {
		
    	 submissionStudy.loggedOnUser = new NedPerson();
    	 submissionStudy.loggedOnUser.setAdUserId("TEST");
		
		ManageProjectService manageProjectServiceMock = Mockito.mock(ManageProjectService.class);
		Mockito.when(manageProjectServiceMock.findById(1L)).thenReturn(project);
		Mockito.when(manageProjectServiceMock.deleteStudy(1L, project)).thenReturn(true);
		Mockito.when(manageProjectServiceMock.getSubprojects(1L, true)).thenReturn(new ArrayList<Project>());
		Mockito.when(manageProjectServiceMock.saveOrUpdate(any(Project.class))).thenReturn(project);
		submissionStudy.manageProjectService = manageProjectServiceMock;
		
		FileUploadService fileUploadServiceMock = Mockito.mock(FileUploadService.class);
		Mockito.when(fileUploadServiceMock.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_IC, null)).thenReturn(null);
		submissionStudy.fileUploadService = fileUploadServiceMock;
		
		List<Lookup> lookupList = new ArrayList<Lookup>();
		Lookup lookup = new Lookup();
		lookup.setCode("IC");
		lookupList.add(lookup);
		LookupService lookupServiceMock = Mockito.mock(LookupService.class);
		Mockito.when(lookupServiceMock.getLookupList(any(String.class)))
				.thenReturn(lookupList);
		submissionStudy.lookupService = lookupServiceMock;
	}
}

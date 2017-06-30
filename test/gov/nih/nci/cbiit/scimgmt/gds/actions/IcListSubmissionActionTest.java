package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import static org.mockito.Matchers.any;
import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.InstitutionalCertification;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Study;
import gov.nih.nci.cbiit.scimgmt.gds.services.FileUploadService;
import gov.nih.nci.cbiit.scimgmt.gds.services.LookupService;
import gov.nih.nci.cbiit.scimgmt.gds.services.ManageProjectService;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PageStatus;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanQuestionsAnswer;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"../applicationContext.xml"})
public class IcListSubmissionActionTest {
	
  static Logger logger = LogManager.getLogger(IcListSubmissionActionTest.class);
	
  IcListSubmissionAction icListSubmissionAction = new IcListSubmissionAction() {

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
	@Transactional
	public void testExecute() {
		System.out.println("Starting junit for execute");
		
		// Test getIcList() private method
		Project project = new Project();
		project.setId(1L);
		project.setSubprojectFlag("N");
		icListSubmissionAction.setProjectId("1");
		mockServices(icListSubmissionAction, project);
		Assert.assertEquals(icListSubmissionAction.execute(), "empty");
		
		// Test getIcList() private method except document retrieval
		// TODO test getter setters for Objects, Project, InstitutionalCertification, Study
		List<InstitutionalCertification> icList = new ArrayList<InstitutionalCertification>();
		InstitutionalCertification ic = new InstitutionalCertification();
		Study stu = new Study();
		stu.addInstitutionalCertification(ic);
		List<Study> studies = new ArrayList<Study>();
		studies.add(stu);
		ic.setStudies(studies);
		ic.addStudy(stu);
		ic.setId(1L);
		icList.add(ic);
		project.setInstitutionalCertifications(icList);
		Assert.assertEquals(icListSubmissionAction.execute(), "success");
		
		// Test getIcList() private method for subprojects
		project.setSubprojectFlag("Y");
		project.setParentProjectId(1L);
		project.setStudies(studies);
		icListSubmissionAction.setProject(project);
		Assert.assertEquals(icListSubmissionAction.execute(), "success");
		
		// Test getIcList() private method for document retrieval
		FileUploadService fileUploadServiceMock2 = Mockito.mock(FileUploadService.class);
		List<Document> docs = new ArrayList<Document>();
		// TODO test getter setters for Objects, Document
		Document d = new Document();
		Document d2 = new Document();
		Document d3 = new Document();
		d.setInstitutionalCertificationId(1L);
		d3.setInstitutionalCertificationId(2L);
		docs.add(d);
		docs.add(d2);
		docs.add(d3);
		Mockito.when(fileUploadServiceMock2.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_IC, 1L)).thenReturn(docs);
		icListSubmissionAction.fileUploadService = fileUploadServiceMock2;
		Assert.assertEquals(icListSubmissionAction.execute(), "success");
		
		
		//Test retrieval of IC
		icListSubmissionAction.setInstCertification(ic);
		Assert.assertEquals(icListSubmissionAction.getInstCertification(), ic);
		icListSubmissionAction.setIcIds("1");
		Assert.assertEquals(icListSubmissionAction.getIcIds(), "1");
	}

	@Test
	@Transactional
	public void testDeleteIc() {
		System.out.println("Starting junit for deleteIc");
		
		Project project = new Project();
		project.setId(1L);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
		
		
		icListSubmissionAction.setProjectId("1");
		icListSubmissionAction.setInstCertId("1");
		
		mockServices(icListSubmissionAction, project);
		
		Assert.assertEquals(icListSubmissionAction.deleteIc(), "success");
		
	}
	
	@Test
	@Transactional
	public void testValidateIcListSubmission() {
		System.out.println("Starting junit for validateIcListSubmission");
		
		icListSubmissionAction.setProjectId("1");
				
		icListSubmissionAction.validateIcListSubmission();
		
		Project project = new Project();
		project.setSubprojectFlag(ApplicationConstants.FLAG_YES);
		project.setId(1L);
		project.setParentProjectId(1L);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
		icListSubmissionAction.setProject(project);
		icListSubmissionAction.setCertFlag(ApplicationConstants.FLAG_YES);
		icListSubmissionAction.setIfIcSelected(false);
		icListSubmissionAction.setAdditionalComments(
				"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		icListSubmissionAction.setIcComments(
				"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

		//Validate IC Complete status
		List<InstitutionalCertification> icList = new ArrayList<InstitutionalCertification>();
		InstitutionalCertification ic = new InstitutionalCertification();
		ic.setId(1L);
		icList.add(ic);
		project.setInstitutionalCertifications(icList);
				
		mockServices(icListSubmissionAction, project);
		icListSubmissionAction.validateIcListSubmission();
		Assert.assertEquals(icListSubmissionAction.getCertFlag(), ApplicationConstants.FLAG_YES);
		Assert.assertEquals(icListSubmissionAction.isIfIcSelected(), false);
		icListSubmissionAction.setAdditionalComments("test");
		icListSubmissionAction.setIcComments("test");
		Assert.assertEquals(icListSubmissionAction.getAdditionalComments(),"test");
		Assert.assertEquals(icListSubmissionAction.getIcComments(),"test");
	
	}
	
	@Test
	@Transactional
	public void testValidateSave() {
		System.out.println("Starting junit for validateSave");
		
		icListSubmissionAction.setProjectId("1");
				
		icListSubmissionAction.validateIcListSubmission();
		
		Project project = new Project();
		project.setSubprojectFlag(ApplicationConstants.FLAG_YES);
		project.setId(1L);
		project.setParentProjectId(1L);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
		icListSubmissionAction.setProject(project);
		icListSubmissionAction.setCertFlag(ApplicationConstants.FLAG_YES);
		icListSubmissionAction.setIfIcSelected(false);
		icListSubmissionAction.setIcComments(
				"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

		mockServices(icListSubmissionAction, project);
		icListSubmissionAction.validateSave();
		icListSubmissionAction.validateSaveAndNext();
	}
	
	@Test
	@Transactional
	public void testSave() {
		System.out.println("Starting junit for save");
		
		icListSubmissionAction.setProjectId("1");
				
		icListSubmissionAction.validateIcListSubmission();
		
		Project project = new Project();
		project.setSubprojectFlag(ApplicationConstants.FLAG_NO);
		project.setId(1L);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
		project.setPlanAnswerSelections(new HashSet<PlanAnswerSelection>());
		icListSubmissionAction.setProject(project);
		icListSubmissionAction.setCertFlag(ApplicationConstants.FLAG_YES);
		icListSubmissionAction.setIfIcSelected(false);
		
		mockServices(icListSubmissionAction, project);
		icListSubmissionAction.save();
		
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
		List<InstitutionalCertification> icList = new ArrayList<InstitutionalCertification>();
		InstitutionalCertification ic = new InstitutionalCertification();
		ic.setId(1L);
		icList.add(ic);
		project.setInstitutionalCertifications(icList);
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		ServletActionContext servletActionContext = Mockito.mock(ServletActionContext.class);
		ActionContext actionContext = Mockito.mock(ActionContext.class);
		ServletActionContext.setContext(actionContext);
		Mockito.when(servletActionContext.getRequest()).thenReturn(request);
		
		icListSubmissionAction.saveAndNext();
		
	}
	
	@Test
	@Transactional
	public void testGetMissingIcListData() {
		System.out.println("Starting junit for getMissingIcListData");
		
		icListSubmissionAction.setProjectId("1");
				
		icListSubmissionAction.validateIcListSubmission();
		
		Project project = new Project();
		project.setSubprojectFlag(ApplicationConstants.FLAG_NO);
		project.setId(1L);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
		project.setPlanAnswerSelections(new HashSet<PlanAnswerSelection>());
		icListSubmissionAction.setProject(project);
		icListSubmissionAction.setCertFlag(ApplicationConstants.FLAG_YES);
		icListSubmissionAction.setIfIcSelected(false);
		
		mockServices(icListSubmissionAction, project);
		icListSubmissionAction.getMissingIcListData();
	}
	
	@Test
	@Transactional
	public void testGetPageStatusCode() {
		System.out.println("Starting junit for getPageStatusCode");
		
		icListSubmissionAction.setProjectId("1");
				
		icListSubmissionAction.validateIcListSubmission();
		
		Project project = new Project();
		project.setSubprojectFlag(ApplicationConstants.FLAG_NO);
		project.setId(1L);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
		project.setPlanAnswerSelections(new HashSet<PlanAnswerSelection>());
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
		icListSubmissionAction.setProject(project);
		icListSubmissionAction.setCertFlag(ApplicationConstants.FLAG_YES);
		icListSubmissionAction.setIfIcSelected(false);
		
		mockServices(icListSubmissionAction, project);
		icListSubmissionAction.getPageStatusCode();
	}
	
	private void mockServices(IcListSubmissionAction icListSubmissionAction, Project project) {
		
		icListSubmissionAction.loggedOnUser = new NedPerson();
		icListSubmissionAction.loggedOnUser.setAdUserId("TEST");
		
		ManageProjectService manageProjectServiceMock = Mockito.mock(ManageProjectService.class);
		Mockito.when(manageProjectServiceMock.findById(1L)).thenReturn(project);
		Mockito.when(manageProjectServiceMock.deleteIc(1L, project)).thenReturn(true);
		Mockito.when(manageProjectServiceMock.getSubprojects(1L, true)).thenReturn(new ArrayList<Project>());
		Mockito.when(manageProjectServiceMock.saveOrUpdate(any(Project.class))).thenReturn(project);
		icListSubmissionAction.manageProjectService = manageProjectServiceMock;
		
		FileUploadService fileUploadServiceMock = Mockito.mock(FileUploadService.class);
		Mockito.when(fileUploadServiceMock.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_IC, null)).thenReturn(null);
		icListSubmissionAction.fileUploadService = fileUploadServiceMock;
		
		List<Lookup> lookupList = new ArrayList<Lookup>();
		Lookup lookup = new Lookup();
		lookup.setCode("IC");
		lookupList.add(lookup);
		LookupService lookupServiceMock = Mockito.mock(LookupService.class);
		Mockito.when(lookupServiceMock.getLookupList(any(String.class)))
				.thenReturn(lookupList);
		icListSubmissionAction.lookupService = lookupServiceMock;
	}

}

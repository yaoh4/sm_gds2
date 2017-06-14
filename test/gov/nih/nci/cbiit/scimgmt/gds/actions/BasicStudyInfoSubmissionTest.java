package gov.nih.nci.cbiit.scimgmt.gds.actions;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.domain.NedPerson;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.services.FileUploadService;
import gov.nih.nci.cbiit.scimgmt.gds.services.LookupService;
import gov.nih.nci.cbiit.scimgmt.gds.services.ManageProjectService;
import gov.nih.nci.cbiit.scimgmt.gds.util.DropDownOption;
import junit.framework.Assert;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"../applicationContext.xml"})
public class BasicStudyInfoSubmissionTest {
	
  static Logger logger = LogManager.getLogger(BasicStudyInfoSubmissionTest.class);

	Project project;
	
	BasicStudyInfoSubmissionAction basicStudyInfo = new BasicStudyInfoSubmissionAction() {

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
	
 
	@Autowired
	LookupService lookupService;
	
	@Test
	@Transactional
	public void computePageStatusTest() {
		
      System.out.println("Starting junit for computePageStatusTest");
		//Initial setup
      	List<Document> docs = new ArrayList<Document> ();
      	Project project = new Project();
		project.setId(2L);
		project.setSubprojectFlag(ApplicationConstants.FLAG_NO);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NIHFUND);
		
		//bsi reviewed flag is yes and no file is uploaded
		FileUploadService fileUploadServiceMock1 = Mockito.mock(FileUploadService.class);
		Mockito.when(fileUploadServiceMock1.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_BSI, null)).thenReturn(docs);
		BasicStudyInfoSubmissionAction basicStudyInfoSubmission1 = new BasicStudyInfoSubmissionAction();
		basicStudyInfoSubmission1.setBsiReviewedId(ApplicationConstants.BSI_YES);
		basicStudyInfoSubmission1.setComments("bsi comments");
		basicStudyInfoSubmission1.fileUploadService = fileUploadServiceMock1;
		
		String projStat = basicStudyInfoSubmission1.computePageStatus(project);
		Assert.assertEquals(ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS, projStat);
		Assert.assertEquals(basicStudyInfoSubmission1.getComments(), "bsi comments");
		Assert.assertEquals(basicStudyInfoSubmission1.getBsiReviewedId(), Long.valueOf(55L));
		
		//bsi reviewed is no and file is uploaded
		project.setBsiReviewedId(ApplicationConstants.BSI_NO);
		FileUploadService fileUploadServiceMock = Mockito.mock(FileUploadService.class);
		Mockito.when(fileUploadServiceMock.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_BSI, 1L)).thenReturn(docs);
		BasicStudyInfoSubmissionAction basicStudyInfoSubmission = new BasicStudyInfoSubmissionAction();
		basicStudyInfoSubmission.fileUploadService = fileUploadServiceMock;
		
		String projectStatus = basicStudyInfoSubmission.computePageStatus(project);
		Assert.assertEquals(ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS, projectStatus);
		Assert.assertEquals(basicStudyInfoSubmission.getBsiFile(), null);
		
		// when bsi reviewed id is not applicable
		project.setBsiReviewedId(ApplicationConstants.BSI_NA);
		String status = basicStudyInfoSubmission.computePageStatus(project);
		Assert.assertEquals(ApplicationConstants.PAGE_STATUS_CODE_COMPLETED, status);
		
	}

	@Test
	@Transactional
	public void testuploadBasicStudyInfo() throws Exception{
		System.out.println("Starting junit for upload basic study info");
		List<Document> docs = new ArrayList<Document> ();
		Document doc = new Document();
      	Project project = new Project();
		project.setId(1L);
		project.setSubprojectFlag(ApplicationConstants.FLAG_NO);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NIHFUND);
		project.setBsiReviewedId(ApplicationConstants.BSI_NO);
		basicStudyInfo.setProject(project);
		String filename ="test.doc";
	    //File content = new File("test" + (new Random().nextInt()) + ".doc");
		File content = new File("test.doc");
		String con = "this is anew file";
		FileOutputStream fop = new FileOutputStream(content);
	    content.createNewFile();
	    byte[] contentInBytes = con.getBytes();
		fop.write(contentInBytes);
		fop.flush();
		fop.close();
		
		FileUploadService fileUploadServiceMock = Mockito.mock(FileUploadService.class);
		Mockito.when(fileUploadServiceMock.storeFile(1L, ApplicationConstants.DOC_TYPE_BSI, content, filename)).thenReturn(doc);
		Mockito.when(fileUploadServiceMock.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_BSI, 1L)).thenReturn(docs);
		//BasicStudyInfoSubmissionAction basicStudyInfoSubmission = new BasicStudyInfoSubmissionAction();
		basicStudyInfo.fileUploadService = fileUploadServiceMock;
		ManageSubmission manageSubmission = Mockito.mock(ManageSubmission.class);
		Mockito.when(manageSubmission.validateUploadFile(content, "application/msword")).thenReturn(true);
		mockServices(basicStudyInfo, project);
		basicStudyInfo.uploadBasicStudyInfo();
	}
	
	@Test
	@Transactional
	public void testDeleteBsiFile() {
		System.out.println("Starting Junit for delete bsi file");
		List<Document> docs = new ArrayList<Document> ();
		Document doc = new Document();
      	Project project = new Project();
		project.setId(1L);
		project.setSubprojectFlag(ApplicationConstants.FLAG_NO);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NIHFUND);
		project.setBsiReviewedId(ApplicationConstants.BSI_NO);
		FileUploadService fileUploadServiceMock = Mockito.mock(FileUploadService.class);
		Mockito.when(fileUploadServiceMock.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_BSI, 1L)).thenReturn(docs);
		BasicStudyInfoSubmissionAction basicStudyInfoSubmission = new BasicStudyInfoSubmissionAction();
		basicStudyInfoSubmission.fileUploadService = fileUploadServiceMock;
		basicStudyInfoSubmission.setProject(project);
		mockServices(basicStudyInfoSubmission, project);
		basicStudyInfoSubmission.setDocId(null);
	}
	
	@Test
	@Transactional
	public void testValidateSave() throws Exception {
	        System.out.println("Starting junit for validateSave");
			
			Project project = new Project();
			project.setId(1L);
			project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
			basicStudyInfo.setProjectId("1");
			basicStudyInfo.setComments("bsi");
			basicStudyInfo.setProject(project);
			mockServices(basicStudyInfo, project);
			basicStudyInfo.validateSave();
		    System.out.println("error size" + basicStudyInfo.getActionErrors().size());
		    Assert.assertEquals(0, basicStudyInfo.getActionErrors().size());
		    
		    // set bsi comments grater than 2000 characters
		    project.setBsiComments("experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.");
		    basicStudyInfo.setComments("experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.experiment.As explained above, A GEO Series record is an original submitter-supplied record that summarizes an experiment.");
		    basicStudyInfo.validateSave();
		    System.out.println("error size here is" + basicStudyInfo.getActionErrors().size());
		    Assert.assertEquals(1, basicStudyInfo.getActionErrors().size());
		    basicStudyInfo.validateSaveAndNext();	  
	}
	
	@Test
	@Transactional
	public void testSave() throws Exception {
		    System.out.println("Starting junit for save");
		
		    basicStudyInfo.setProjectId("1");
			
			Project project = new Project();
			project.setSubprojectFlag(ApplicationConstants.FLAG_NO);
			project.setId(1L);
			project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
			project.setBsiComments("bsi comments");
			project.setBsiReviewedId(57L);
			basicStudyInfo.setProject(project);
			mockServices(basicStudyInfo, project);
			basicStudyInfo.save();
		
	}
	
	@Test
	@Transactional
	public void testExecute() throws Exception {
		   System.out.println("Starting junit for execute");
			
		    basicStudyInfo.setProjectId("1");
			
			Project project = new Project();
			project.setSubprojectFlag(ApplicationConstants.FLAG_NO);
			project.setId(1L);
			project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
			project.setBsiComments("bsi comments");
			project.setBsiReviewedId(57L);
			basicStudyInfo.setProject(project);
			mockServices(basicStudyInfo, project);
			basicStudyInfo.execute();
			List<DropDownOption> dropdownList = new ArrayList<DropDownOption>();
			DropDownOption dropdown = new DropDownOption();
			dropdown.setOptionKey("55");
			dropdown.setOptionValue("Yes");
			DropDownOption dropdown1 = new DropDownOption();
			dropdown1.setOptionKey("56");
			dropdown1.setOptionValue("No");
			DropDownOption dropdown2 = new DropDownOption();
			dropdown2.setOptionKey("57");
			dropdown2.setOptionValue("Not Applicable");
			dropdownList.add(dropdown);
			dropdownList.add(dropdown1);
			dropdownList.add(dropdown2);
			basicStudyInfo.setBsiOptions(dropdownList);
			Assert.assertEquals(dropdownList, basicStudyInfo.getBsiOptions());
	}
	
	@Test
	@Transactional
	public void testSaveAndNext() throws Exception {
		   System.out.println("Starting junit for save and next");
			
		    basicStudyInfo.setProjectId("1");
			
			Project project = new Project();
			project.setSubprojectFlag(ApplicationConstants.FLAG_NO);
			project.setId(1L);
			project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
			project.setBsiComments("bsi comments");
			project.setBsiReviewedId(57L);
			basicStudyInfo.setProject(project);
			mockServices(basicStudyInfo, project);
			basicStudyInfo.saveAndNext();
	}
	
	@Test
	@Transactional
	public void testMissingBsiData() {
		System.out.println("Starting junit missing bsi data");
		

		basicStudyInfo.setProjectId("1");
				
		Project project = new Project();
		project.setSubprojectFlag(ApplicationConstants.FLAG_NO);
		project.setId(1L);
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
		project.setPlanAnswerSelections(new HashSet<PlanAnswerSelection>());
		project.setBsiComments("bsi comments");
		project.setBsiReviewedId(57L);
		basicStudyInfo.setProject(project);
		
		mockServices(basicStudyInfo, project);
		basicStudyInfo.getMissingBsiData();
	}
	
    private void mockServices(BasicStudyInfoSubmissionAction basicStudyInfo, Project project) {
		
    	basicStudyInfo.loggedOnUser = new NedPerson();
    	basicStudyInfo.loggedOnUser.setAdUserId("TEST");
		
		ManageProjectService manageProjectServiceMock = Mockito.mock(ManageProjectService.class);
		Mockito.when(manageProjectServiceMock.findById(1L)).thenReturn(project);
		Mockito.when(manageProjectServiceMock.deleteStudy(1L, project)).thenReturn(true);
		Mockito.when(manageProjectServiceMock.getSubprojects(1L, true)).thenReturn(new ArrayList<Project>());
		Mockito.when(manageProjectServiceMock.saveOrUpdate(any(Project.class))).thenReturn(project);
		basicStudyInfo.manageProjectService = manageProjectServiceMock;
		
		FileUploadService fileUploadServiceMock = Mockito.mock(FileUploadService.class);
		Mockito.when(fileUploadServiceMock.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_BSI, null)).thenReturn(null);
		basicStudyInfo.fileUploadService = fileUploadServiceMock;
		
		List<Lookup> lookupList = new ArrayList<Lookup>();
		Lookup lookup = new Lookup();
		lookup.setCode("BSI");
		lookupList.add(lookup);
		LookupService lookupServiceMock = Mockito.mock(LookupService.class);
		Mockito.when(lookupServiceMock.getLookupList(any(String.class)))
				.thenReturn(lookupList);
		basicStudyInfo.lookupService = lookupServiceMock;
		
		
		List<DropDownOption> dropdownList = new ArrayList<DropDownOption>();
		DropDownOption dropdown = new DropDownOption();
		dropdown.setOptionKey("55");
		dropdown.setOptionValue("Yes");
		DropDownOption dropdown1 = new DropDownOption();
		dropdown1.setOptionKey("56");
		dropdown1.setOptionValue("No");
		DropDownOption dropdown2 = new DropDownOption();
		dropdown2.setOptionKey("57");
		dropdown2.setOptionValue("Not Applicable");
		dropdownList.add(dropdown);
		dropdownList.add(dropdown1);
		dropdownList.add(dropdown2);
		basicStudyInfo.setBsiOptions(dropdownList);
	}
}

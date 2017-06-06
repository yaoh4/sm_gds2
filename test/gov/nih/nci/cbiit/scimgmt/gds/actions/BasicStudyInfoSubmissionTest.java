package gov.nih.nci.cbiit.scimgmt.gds.actions;

import java.util.ArrayList;
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

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Document;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.services.FileUploadService;
import gov.nih.nci.cbiit.scimgmt.gds.services.LookupService;
import junit.framework.Assert;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"../applicationContext.xml"})
public class BasicStudyInfoSubmissionTest {
	
  static Logger logger = LogManager.getLogger(BasicStudyInfoSubmissionTest.class);
	
    Project project;
	
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
		project.setBsiReviewedId(ApplicationConstants.BSI_NO);
		
		FileUploadService fileUploadServiceMock = Mockito.mock(FileUploadService.class);
		Mockito.when(fileUploadServiceMock.retrieveFileByDocType(ApplicationConstants.DOC_TYPE_BSI, 1L)).thenReturn(docs);
		BasicStudyInfoSubmissionAction basicStudyInfoSubmission = new BasicStudyInfoSubmissionAction();
		basicStudyInfoSubmission.fileUploadService = fileUploadServiceMock;
		
		String projectStatus = basicStudyInfoSubmission.computePageStatus(project);
		Assert.assertEquals(ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS, projectStatus);
		
	}

}

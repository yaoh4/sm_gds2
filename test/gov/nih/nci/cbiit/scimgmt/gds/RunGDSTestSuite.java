package gov.nih.nci.cbiit.scimgmt.gds;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import gov.nih.nci.cbiit.scimgmt.gds.actions.BasicStudyInfoSubmissionTest;
import gov.nih.nci.cbiit.scimgmt.gds.actions.IcListSubmissionActionTest;
import gov.nih.nci.cbiit.scimgmt.gds.actions.ManageSubmissionTest;
import gov.nih.nci.cbiit.scimgmt.gds.actions.SubmissionStudyTest;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsMissingDataUtilTest;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsPageStatusUtilTest;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelperTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "applicationContext.xml" })
public class RunGDSTestSuite {
	static Logger logger = LogManager.getLogger(RunGDSTestSuite.class);

	@Test
	@Transactional
	public void callTestCases() throws Exception {

		//call test cases in BaseActionTest
		ManageSubmissionTest manageSubmissionTest = new ManageSubmissionTest();
		manageSubmissionTest.testGetProjectStatusCode();
	
		SubmissionStudyTest submissionStudyTest = new SubmissionStudyTest();
		submissionStudyTest.testRetrieveStudy();
		submissionStudyTest.testDeleteStudy();
		submissionStudyTest.testValidateSaveStudy();
		submissionStudyTest.testSaveStudy();
		submissionStudyTest.testValidateSaveAndAddStudy();
		submissionStudyTest.testSaveAndAddStudy();
		submissionStudyTest.testExecute();
		submissionStudyTest.testSetListStudies();
		
		BasicStudyInfoSubmissionTest basicStudyInfoSubmissionTest = new BasicStudyInfoSubmissionTest();
		basicStudyInfoSubmissionTest.computePageStatusTest();
		basicStudyInfoSubmissionTest.testSave();
		basicStudyInfoSubmissionTest.testValidateSave();
		basicStudyInfoSubmissionTest.testExecute();
		basicStudyInfoSubmissionTest.testSaveAndNext();
		basicStudyInfoSubmissionTest.testMissingBsiData();
		basicStudyInfoSubmissionTest.testuploadBasicStudyInfo();
		
		
		GdsPageStatusUtilTest gdsPageStatusUtilTest = new GdsPageStatusUtilTest();
		gdsPageStatusUtilTest.computeBsiStudyInfoStatusTest();
		gdsPageStatusUtilTest.computeRepositoryStatusTest();
		gdsPageStatusUtilTest.testComputeGdsPlanStatus();
		gdsPageStatusUtilTest.testComputeIcListStatus();

		GdsMissingDataUtilTest gdsMissingDataUtilTest = new GdsMissingDataUtilTest();
		gdsMissingDataUtilTest.getMissingBsiDataTest();
		gdsMissingDataUtilTest.getMissingIcListDataTest();
		gdsMissingDataUtilTest.getMissingGdsPlanDataTest();
		gdsMissingDataUtilTest.getMissingRepositoryListDataTest();

		GdsSubmissionActionHelperTest gdsSubmissionActionHelperTest = new GdsSubmissionActionHelperTest();
		gdsSubmissionActionHelperTest.isEligibleForSubprojectTest();
		gdsSubmissionActionHelperTest.isProjectEligibleForVersionTest();
		gdsSubmissionActionHelperTest.isSubmissionUpdatedTest();

		IcListSubmissionActionTest icListSubmissionActionTest = new IcListSubmissionActionTest();
		icListSubmissionActionTest.testExecute();
		icListSubmissionActionTest.testDeleteIc();
		icListSubmissionActionTest.testValidateIcListSubmission();
		icListSubmissionActionTest.testValidateSave();
		icListSubmissionActionTest.testSave();
		icListSubmissionActionTest.testGetMissingIcListData();
		icListSubmissionActionTest.testGetPageStatusCode();
	}
	

}

package gov.nih.nci.cbiit.scimgmt.gds;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import gov.nih.nci.cbiit.scimgmt.gds.actions.BasicStudyInfoSubmissionTest;
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
		
		BasicStudyInfoSubmissionTest basicStudyInfoSubmissionTest = new BasicStudyInfoSubmissionTest();
		basicStudyInfoSubmissionTest.computePageStatusTest();
		
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

	}

}

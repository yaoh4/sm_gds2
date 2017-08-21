package gov.nih.nci.cbiit.scimgmt.gds;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import gov.nih.nci.cbiit.scimgmt.gds.actions.BasicStudyInfoSubmissionTest;
import gov.nih.nci.cbiit.scimgmt.gds.actions.IcListSubmissionActionTest;
import gov.nih.nci.cbiit.scimgmt.gds.actions.ManageSubmissionTest;
import gov.nih.nci.cbiit.scimgmt.gds.actions.SubmissionStudyTest;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsMissingDataUtilTest;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsPageStatusUtilTest;
import gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelperTest;

@RunWith(Suite.class)
@SuiteClasses({ ManageSubmissionTest.class, SubmissionStudyTest.class, BasicStudyInfoSubmissionTest.class,
		GdsPageStatusUtilTest.class, GdsMissingDataUtilTest.class, GdsSubmissionActionHelperTest.class,
		IcListSubmissionActionTest.class })
public class RunGDSTestSuite {
	static Logger logger = LogManager.getLogger(RunGDSTestSuite.class);

	@Test
	public void callTestCases() throws Exception {

	}
	

}

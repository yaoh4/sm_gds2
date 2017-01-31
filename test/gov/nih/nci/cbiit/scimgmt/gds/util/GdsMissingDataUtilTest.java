package gov.nih.nci.cbiit.scimgmt.gds.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.xml.sax.SAXException;

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
	public void testGetMissingGdsPlanData(){
   		
   		System.out.println("Starting junit for testGetMissingGdsPlanData");
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
		String data = "The question 'Is there a data sharing exception requested for this project ?' has not been answered.";
		Assert.assertEquals(data, gdsMissingDataUtil.getMissingGdsPlanData(project).get(0).getDisplayText());
		
		
		// Test Scenario when submission reason id is GDS Policy and Not indicated if the data sharing exception requested was approved
		/*project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GWASPOLICY);
		PlanQuestionsAnswer answer = new PlanQuestionsAnswer();
		answer.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_YES_ID);
		PlanAnswerSelection selection = new PlanAnswerSelection(9L, answer, "JUnit");
		project.addPlanAnswerSelection(selection);
	    String text="The question 'Was this exception approved ?' has not been answered.";
	    Assert.assertEquals(text,gdsMissingDataUtil.getMissingGdsPlanData(project).get(0).getDisplayText());*/
		
	    //If the submission is a sub-project, GDS plan and missing data report do not exist
	  		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NIHFUND);
	  		setAsSubproject(project);
	  		Assert.assertEquals(missingDataList, gdsMissingDataUtil.getMissingGdsPlanData(project));

   	}
   	
   	
   	@Test
	@Transactional
	public void testGetMissingIcListData() {
   		
   		System.out.println("Starting junit for testGetMissingIcListData");
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
		
		// Empty missing Data, return null
		setAsSubproject(project);
		project.removePlanAnswerSelection(answer.getId());
		answer.setId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_NONHUMAN_ID);
		selection.setPlanQuestionsAnswer(answer);
		project.getParent().addPlanAnswerSelection(selection);
		Assert.assertEquals(missingDataList, gdsMissingDataUtil.getMissingIcListData(project));
   	}
   	
   	
   	@Test
	@Transactional
	public void testGetMissingBsiData() {
   		
   		System.out.println("Starting junit for testGetMissingBsiData");
   		GdsMissingDataUtil gdsMissingDataUtil = GdsMissingDataUtil.getInstance();
		
		//Initial setup
		Project project = new Project();
		project.setId(2L);
		
		//If the BSI reviewed flag is "no"
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NIHFUND);
		project.setBsiReviewedId(ApplicationConstants.BSI_NO);
		String text="BSI Reviewed flag must be 'Yes'.";
		Assert.assertEquals(text,gdsMissingDataUtil.getMissingBsiData(project).get(0).getDisplayText());
		
		// If submission reason id is non-nih funded and bsi reviewed flag is "NA"
		project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NONNIHFUND);
		project.setBsiReviewedId(ApplicationConstants.BSI_NA);
		String data="The question 'What repository will the data be submitted to ?' has not been answered.";
		Assert.assertEquals(data,gdsMissingDataUtil.getMissingBsiData(project).get(0).getDisplayText());
   	}
   	
   	
   	private void setAsSubproject(Project project) {
		Project parent = new Project();
		parent.setId(2L);
		project.setParent(parent);
		project.setParentProjectId(parent.getId());
		project.setSubprojectFlag(ApplicationConstants.FLAG_YES);
	}

}

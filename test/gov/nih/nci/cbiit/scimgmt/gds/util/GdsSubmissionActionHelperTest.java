package gov.nih.nci.cbiit.scimgmt.gds.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.xml.sax.SAXException;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.DulChecklist;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Organization;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanQuestionsAnswer;
import gov.nih.nci.cbiit.scimgmt.gds.model.ParentDulChecklist;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectGrantContract;
import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;
import gov.nih.nci.cbiit.scimgmt.gds.services.LookupService;

public class GdsSubmissionActionHelperTest {
	
private static final Logger logger = LogManager.getLogger(GdsSubmissionActionHelper.class);
	
	@Autowired
	protected LookupService lookupService;
	
	private static GdsSubmissionActionHelper instance;
   	private static boolean loaded = false;
	
	@Test
	@Transactional
	public void isProjectEligibleForVersionTest() {
		
		System.out.println("Starting junit for isProjectEligibleForVersionTest");
		GdsSubmissionActionHelper gdsSubmissionActionHelper = GdsSubmissionActionHelper.getInstance();
		
		//Initial setup
		Project project = new Project();
		project.setId(2L);	
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
		
	}
	
	@Test
	@Transactional
	public void isSubmissionUpdatedTest() {
		
		 System.out.println("Starting junit for isSubmissionUpdatedTest");
			
			//Initial setup
			Project project = new Project();
			project.setId(2L);
			
			//submission reason id is changed from nih funded to gds policy
			project.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_NIHFUND);
			Project updatedProject = new Project();
			updatedProject.setId(3L);
			updatedProject.setSubmissionReasonId(ApplicationConstants.SUBMISSION_REASON_GDSPOLICY);
			Assert.assertEquals(true, GdsSubmissionActionHelper.isSubmissionUpdated(project, updatedProject));
		
	}
}

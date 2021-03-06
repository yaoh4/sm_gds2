package gov.nih.nci.cbiit.scimgmt.gds.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.xml.sax.SAXException;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.DulChecklist;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Organization;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PlanAnswerSelection;
import gov.nih.nci.cbiit.scimgmt.gds.model.ParentDulChecklist;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.ProjectGrantContract;
import gov.nih.nci.cbiit.scimgmt.gds.domain.RepositoryStatus;
import gov.nih.nci.cbiit.scimgmt.gds.services.LookupService;


public class GdsSubmissionActionHelper {
	
	private static final Logger logger = LogManager.getLogger(GdsSubmissionActionHelper.class);
	
	@Autowired
	protected LookupService lookupService;
	
	private static GdsSubmissionActionHelper instance;
   	private static boolean loaded = false;
	
   	
   	public void init() throws IOException, SAXException {
		if (!loaded) {
			instance = this;
			loaded = true;
		}
		BeanUtilsBean.getInstance().getConvertUtils().register(false, true, -1);
	}
   	
   	
   	/**
	 * Gets the single instance of GdsSubmissionActionHelper.
	 * 
	 * @return single instance of GdsSubmissionActionHelper
	 */
	public static GdsSubmissionActionHelper getInstance() {
		return instance;
	}
		
	/**
	 * This method populates DocDropDownList from Object list
	 * @param dropDownList
	 * @param docList
	 */
	public static void populateDocDropDownList(List<DropDownOption> dropDownList, List<Organization> docList){
		
		for(Organization org : docList ){
			DropDownOption option = new DropDownOption(org.getNihorgpath(), org.getNihorgpath());
			dropDownList.add(option);
		}
	}
	
	public static List<DropDownOption> populateProgDropDownList(List<DropDownOption> dropDownList, List<String> progList){
		DropDownOption option = new DropDownOption();
		option.setOptionKey("");
		option.setOptionValue("");
		dropDownList.add(option);
		for(String prog : progList ) {
		    option = new DropDownOption(prog,prog);
			dropDownList.add(option);
		}
		
		return dropDownList;

		
	}
	
	
	/**
	 * Get the Lookup list for the given list name and prepare
	 * the dropDown list.
	 * 
	 * @param listName
	 * @return
	 */
	public static List<DropDownOption> getDocDropDownList() {
		List<DropDownOption> dropDownList = new ArrayList<DropDownOption>();
		List<Organization> orgList = (List<Organization>)getInstance().lookupService.getDocList(ApplicationConstants.DOC_LIST);
		if(!CollectionUtils.isEmpty(orgList)) {
			for(Organization org: orgList) {
				DropDownOption option = new DropDownOption(org.getNihsac(), org.getNihorgpath());
				dropDownList.add(option);
			}	
		}
		
		return dropDownList;
		
	}
	
	
	/**
	 * Get the Lookup list for the given list name and prepare
	 * the dropDown list with the lookup ID as optionKey.
	 * 
	 * @param listName
	 * @return
	 */
	public static List<DropDownOption> getLookupDropDownList(String listName) {
		List<DropDownOption> dropDownList = new ArrayList<DropDownOption>();
		List<Lookup> lookupList = getInstance().lookupService.getLookupList(listName);
		if(!CollectionUtils.isEmpty(lookupList)) {
			for(Lookup lookup: lookupList) {
				DropDownOption option = new DropDownOption(lookup.getId().toString(), lookup.getDisplayName());
				dropDownList.add(option);
			}	
		}
		
		return dropDownList;
		
	}
	
	/**
	 * Remove Lookup element based on Id from
	 * the dropDown list.
	 * 
	 * @param list
	 * @param id
	 * @return
	 */
	public static List<DropDownOption> removeLookupFromDropDownList(List<DropDownOption> dropDownList, Long id) {
		
		for(Iterator<DropDownOption> i= dropDownList.iterator(); i.hasNext();) {
			DropDownOption option = i.next();
			if(StringUtils.equals(option.getOptionKey(), id.toString())) {
				i.remove();
				break;
			}
		}
		
		return dropDownList;
		
	}
	
	
	/**
	 * Get the Lookup list for the given list name and prepare
	 * the dropDown list with lookup Code as optionKey.
	 * 
	 * @param listName
	 * @return
	 */
	public static List<DropDownOption> getLookupDropDownCodeList(String listName) {
		List<DropDownOption> dropDownList = new ArrayList<DropDownOption>();
		List<Lookup> lookupList = getInstance().lookupService.getLookupList(listName);
		if(!CollectionUtils.isEmpty(lookupList)) {
			for(Lookup lookup: lookupList) {
				DropDownOption option = new DropDownOption(lookup.getCode().toString(), lookup.getDisplayName());
				dropDownList.add(option);
			}	
		}
		
		return dropDownList;
		
	}
	
	/**
	 * Retrieve list of parent dulChecklists to display text for
	 * radio buttons representing dul sets.
	 * @return
	 */
	public static List<ParentDulChecklist> getDulChecklistsSets(Project project) {
		List<DulChecklist> allDulChecklists = getInstance().lookupService.getDulChecklists("allDuls");
		
		Map<Long, ParentDulChecklist> dulChecklistMap = new TreeMap<Long, ParentDulChecklist>();
		boolean humanAndUnrestricted = 
			project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_NONHUMAN_ID) == null
			&& project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_SPECIMEN_HUMAN_ID) != null
			&& project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_ACCESS_CONTROLLED_ID) == null
			&& project.getPlanAnswerSelectionByAnswerId(ApplicationConstants.PLAN_QUESTION_ANSWER_ACCESS_UNRESTRICTED_ID) != null;
				
		for(DulChecklist dulChecklist: allDulChecklists) {
			if(humanAndUnrestricted && 
				(!ApplicationConstants.IC_PARENT_DUL_ID_GENERAL_RESEARCH_USE.equals(dulChecklist.getId()) &&
				!ApplicationConstants.IC_PARENT_DUL_ID_GENERAL_RESEARCH_USE.equals(dulChecklist.getParentDulId()))) {
				continue;
			}
			Long parentDulId = dulChecklist.getParentDulId();
			if(parentDulId == null) {
				ParentDulChecklist parentDulChecklist = new ParentDulChecklist(dulChecklist);
				dulChecklistMap.put(dulChecklist.getId(), parentDulChecklist);
			} else {
				( (ParentDulChecklist)dulChecklistMap.get(parentDulId)).addDulChecklist(dulChecklist);
			}
		}
		
		return new ArrayList<ParentDulChecklist>(dulChecklistMap.values());
	}
	
	
	
	public static DulChecklist getDulChecklist(Long id) {
		
		logger.info("Retrieving dulChecklist for id " + id);
		if(id != null) {
			List<DulChecklist> allDulChecklists = getInstance().lookupService.getDulChecklists("allDuls");
			for(DulChecklist dulChecklist: allDulChecklists) {
				if(id.equals(dulChecklist.getId())) {
					return dulChecklist;
				}
			}
		}
		
		return null;
	}	
	
	/**
	 * This method returns LoggedOn user's DOC.
	 * @param docListFromDb
	 * @param userNihSac
	 * @return
	 */
	public static String getLoggedonUsersDOC(List<Organization> docListFromDb, String userNihSac){
		String preSelectedDOC = "";
		do{
			for(Organization org: docListFromDb){
				if(userNihSac.equalsIgnoreCase(org.getNihsac())){
					preSelectedDOC = org.getNihorgpath();
					break;
				}			
			}

			if(StringUtils.isBlank(preSelectedDOC)){
				userNihSac = userNihSac.substring(0, userNihSac.length()-1);
			}

		} while(userNihSac.length() >= ApplicationConstants.NED_PERSON_NIH_SAC_MIN_SIZE && StringUtils.isBlank(preSelectedDOC));

		logger.debug("Logged on User's DOC is: "+preSelectedDOC);
		return preSelectedDOC;
	}
	
	/**
	 * This method copies properties from UI project to DB project object.
	 * @param transientProject
	 * @param persistentProject
	 * @return Project
	 */
	public static Project popoulateProjectProperties(Project transientProject, Project persistentProject){	
		
		logger.debug("Copying transient project properties to persistent project properties.");
		persistentProject.setSubmissionTitle(transientProject.getSubmissionTitle());
		if(!persistentProject.getSubprojectFlag().equalsIgnoreCase(ApplicationConstants.FLAG_YES)) {
			persistentProject.setSubmissionReasonId(transientProject.getSubmissionReasonId());
			persistentProject.setDocAbbreviation(transientProject.getDocAbbreviation());
			persistentProject.setProgramBranch(transientProject.getProgramBranch());
		}
		persistentProject.setComments(transientProject.getComments());
		
		//Set PI, PD, Title and Dates properties for extramural grant when 
		//grant is not linked 
		ProjectGrantContract extramuralGrant = transientProject.getPrimaryGrant(ApplicationConstants.GRANT_CONTRACT_TYPE_EXTRAMURAL);
		if(extramuralGrant != null) {
			//If project is being saved with the linked data, then wipe out old PI, PD, Title and Dates properties.
			if(StringUtils.equals(extramuralGrant.getDataLinkFlag(), "Y")) {			
				logger.debug("Grant/Contract is tied to the already saved project which was manually entered. Nullify old PI, PD, Title and Dates properties.");
				extramuralGrant.setProjectTitle(null);
				extramuralGrant.setPiFirstName(null);
				extramuralGrant.setPiLastName(null);
				extramuralGrant.setPiEmailAddress(null);
				extramuralGrant.setPiInstitution(null);		
				extramuralGrant.setPdFirstName(null);
				extramuralGrant.setPdLastName(null);
				extramuralGrant.setProjectStartDate(null);
				extramuralGrant.setProjectEndDate(null);			
			}
			persistentProject.setPrimaryGrant(ApplicationConstants.GRANT_CONTRACT_TYPE_EXTRAMURAL, extramuralGrant);	
		}
		
		ProjectGrantContract intramuralGrant = transientProject.getPrimaryGrant(ApplicationConstants.GRANT_CONTRACT_TYPE_INTRAMURAL);
		if(intramuralGrant != null) {
			persistentProject.setPrimaryGrant(ApplicationConstants.GRANT_CONTRACT_TYPE_INTRAMURAL, intramuralGrant);	
		}

		List<ProjectGrantContract> associatedGrants=transientProject.getAssociatedGrants();
		if(associatedGrants !=null) {
			persistentProject.setAssociatedGrants(associatedGrants);
		}
		return persistentProject;
	}
	
	/**
	 * This method checks if answer to 
	 * Why is the project being submitted? is changed from:
	 * Required by GDS Policy or Required by GWAS Policy to "Optional Submission NIH Funded" or "Optional Submission non-NIH Funded".
	 * @param transientProject
	 * @param persistentProject
	 * @return
	 */
	public static boolean isSubmissionUpdated(Project transientProject, Project persistentProject){
		//For subprojects there is no submission id
		if(transientProject.getSubmissionReasonId() != null) {
			if((transientProject.getSubmissionReasonId().longValue() == ApplicationConstants.SUBMISSION_REASON_NONNIHFUND.longValue())
				&& (persistentProject.getSubmissionReasonId().longValue() == ApplicationConstants.SUBMISSION_REASON_GDSPOLICY.longValue()
				|| persistentProject.getSubmissionReasonId().longValue() == ApplicationConstants.SUBMISSION_REASON_GWASPOLICY.longValue() 
				||persistentProject.getSubmissionReasonId().longValue() == ApplicationConstants.SUBMISSION_REASON_NIHFUND.longValue()))
				return true;	
			else if((persistentProject.getSubmissionReasonId().longValue() == ApplicationConstants.SUBMISSION_REASON_NONNIHFUND.longValue())
					&& (transientProject.getSubmissionReasonId().longValue() == ApplicationConstants.SUBMISSION_REASON_GDSPOLICY.longValue()
					|| transientProject.getSubmissionReasonId().longValue() == ApplicationConstants.SUBMISSION_REASON_GWASPOLICY.longValue() 
					||transientProject.getSubmissionReasonId().longValue() == ApplicationConstants.SUBMISSION_REASON_NIHFUND.longValue()))
				return true;
		} 
		
		return false;
	}
	
	/**
	 * This method checks if any repositories were selected on the GDS plan page.
	 * @return
	 */
	public static boolean willThereBeAnyDataSubmittedInGdsPlan(Project project){

		logger.debug("Checking if any repositories were selected on the GDS plan page.");
		
		for(PlanAnswerSelection planAnswerSelection : project.getPlanAnswerSelections()){
			if( ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_NO_ID.longValue() == planAnswerSelection.getPlanQuestionsAnswer().getId().longValue()){	
				return false;			
			}
		}
		return true;
	}
	
	/*
	 * This method checks if a sub-project can be created off of this Project
	 * Condition:
	 * A project submission exists in the system and:
	 *	  - If Submission is marked as required by GDS or GWAS and answer to question 
	 *		"Is there a data sharing exception requested for this project?" is No,
	 *	 OR
	 *	 - If Submission is marked as required by GDS or GWAS and 
	 *		answer to "Will there be any data submitted?" is Yes,
	 *	 OR
	 *	 - If submission is marked as Optional Submission NIH Funded or Non-NIH Funded.
	 */
	public static boolean isEligibleForSubproject(Project project){
		logger.debug("Checking if subproject can be created off of this project.");
		
		boolean dataSubmittedYesFlag = false;
		boolean exceptionRequestedNoFlag = false;
		boolean exceptionRequestedYesFlag = false;
		boolean exceptionApprovedNoFlag = false;
		for(PlanAnswerSelection planAnswerSelection : project.getPlanAnswerSelections()){
			if( ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_YES_ID.longValue() == planAnswerSelection.getPlanQuestionsAnswer().getId().longValue()){	
				dataSubmittedYesFlag = true;
			}
			if( ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_NO_ID.longValue() == planAnswerSelection.getPlanQuestionsAnswer().getId().longValue()){	
				exceptionRequestedNoFlag = true;
			}
			if( ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_YES_ID.longValue() == planAnswerSelection.getPlanQuestionsAnswer().getId().longValue()){	
				exceptionRequestedYesFlag = true;
			}
			if( ApplicationConstants.PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_NO_ID.longValue() == planAnswerSelection.getPlanQuestionsAnswer().getId().longValue()){	
				exceptionApprovedNoFlag = true;
			}
		}
		
		if((project.getSubmissionReasonId().longValue() == ApplicationConstants.SUBMISSION_REASON_GDSPOLICY.longValue() || 
				project.getSubmissionReasonId().longValue() == ApplicationConstants.SUBMISSION_REASON_GWASPOLICY.longValue()) &&
				exceptionRequestedNoFlag) {
			return true;
		}
		
		if((project.getSubmissionReasonId().longValue() == ApplicationConstants.SUBMISSION_REASON_GDSPOLICY.longValue() || 
				project.getSubmissionReasonId().longValue() == ApplicationConstants.SUBMISSION_REASON_GWASPOLICY.longValue()) &&
				exceptionRequestedYesFlag && exceptionApprovedNoFlag) {
			return true;
		}
		
		if((project.getSubmissionReasonId().longValue() == ApplicationConstants.SUBMISSION_REASON_GDSPOLICY.longValue() || 
				project.getSubmissionReasonId().longValue() == ApplicationConstants.SUBMISSION_REASON_GWASPOLICY.longValue()) &&
				dataSubmittedYesFlag) {
			return true;
		}
		
		if(project.getSubmissionReasonId().longValue() == ApplicationConstants.SUBMISSION_REASON_NIHFUND.longValue() || 
				project.getSubmissionReasonId().longValue() == ApplicationConstants.SUBMISSION_REASON_NONNIHFUND.longValue()) {
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * This method checks if a version can be created off of this project.
	 * Condition: If 'Study Released' is set to 'Yes' for all the 
	 * selected repositories.
	 * 
	 * @param project
	 * @return
	 */
	public static String isProjectEligibleForVersion(Project project) {
		
	Set<RepositoryStatus> repositoryStatuses = new HashSet(0);
	 if(project.getId() != null) {
		  for(PlanAnswerSelection answer: project.getPlanAnswerSelections()) {
			if(ApplicationConstants.PLAN_QUESTION_ANSWER_REPOSITORY_ID.equals(answer.getPlanQuestionsAnswer().getQuestionId())) {
				if(!CollectionUtils.isEmpty(answer.getRepositoryStatuses())) {
				    for(RepositoryStatus rep : answer.getRepositoryStatuses()) {
					   if(rep.getProject().getId().equals(project.getId().longValue())) {
						repositoryStatuses.add(rep);
					   }
				    }
				}
			}
		 }
	 }
		
		if(repositoryStatuses.size() > 0) {
			for(RepositoryStatus repoStatus: repositoryStatuses) {
					if(!ApplicationConstants.PROJECT_STUDY_RELEASED_YES_ID.equals(
							repoStatus.getLookupTByStudyReleasedId().getId())) {
						return ApplicationConstants.FLAG_NO;
					}
			}
		} else {
			return ApplicationConstants.FLAG_NO;
		}
		
		return ApplicationConstants.FLAG_YES;
	}
	
}

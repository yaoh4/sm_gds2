package gov.nih.nci.cbiit.scimgmt.gds.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
	
	
	/**
	 * Get the Lookup list for the given list name and prepare
	 * the dropDown list.
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
	 * Retrieve list of parent dulChecklists to display text for
	 * radio buttons representing dul sets.
	 * @return
	 */
	public static List<ParentDulChecklist> getDulChecklistsSets() {
		List<DulChecklist> allDulChecklists = getInstance().lookupService.getDulChecklists("allDuls");
		
		Map<Long, ParentDulChecklist> dulChecklistMap = new TreeMap<Long, ParentDulChecklist>();
		
		for(DulChecklist dulChecklist: allDulChecklists) {
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

		} while(userNihSac.length() > ApplicationConstants.NED_PERSON_NIH_SAC_MIN_SIZE && StringUtils.isBlank(preSelectedDOC));

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
		persistentProject.setSubmissionReasonId(transientProject.getSubmissionReasonId());
		persistentProject.setDocAbbreviation(transientProject.getDocAbbreviation());
		persistentProject.setProgramBranch(transientProject.getProgramBranch());
		persistentProject.setPocFirstName(transientProject.getPocFirstName());
		persistentProject.setPocLastName(transientProject.getPocLastName());
		persistentProject.setPocEmailAddress(transientProject.getPocEmailAddress());
		persistentProject.setApplicationNum(transientProject.getApplicationNum());
		persistentProject.setComments(transientProject.getComments());
		
		//Set PI, PD, Title and Dates properties when grant is not tied to this project.
		if(persistentProject.getApplId() == null && transientProject.getApplId() == null){	
			
			logger.debug("Grant/Contract is not tied to this project. This is a manual entry.");
			persistentProject.setProjectTitle(transientProject.getProjectTitle());
			persistentProject.setPiFirstName(transientProject.getPiFirstName());
			persistentProject.setPiLastName(transientProject.getPiLastName());
			persistentProject.setPiEmailAddress(transientProject.getPiEmailAddress());
			persistentProject.setPiInstitution(transientProject.getPiInstitution());		
			persistentProject.setPdFirstName(transientProject.getPdFirstName());
			persistentProject.setPdLastName(transientProject.getPdLastName());
			persistentProject.setProjectStartDate(transientProject.getProjectStartDate());
			persistentProject.setProjectEndDate(transientProject.getProjectEndDate());
		}
		//If a grant is tied to the already saved project which was manually entered then wipe out old PI, PD, Title and Dates properties.
		else if(persistentProject.getApplId() == null && transientProject.getApplId() != null){
			
			logger.debug("Grant/Contract is tied to the already saved project which was manually entered. Nullify old PI, PD, Title and Dates properties.");
			persistentProject.setApplId(transientProject.getApplId());
			persistentProject.setProjectTitle(null);
			persistentProject.setPiFirstName(null);
			persistentProject.setPiLastName(null);
			persistentProject.setPiEmailAddress(null);
			persistentProject.setPiInstitution(null);		
			persistentProject.setPdFirstName(null);
			persistentProject.setPdLastName(null);
			persistentProject.setProjectStartDate(null);
			persistentProject.setProjectEndDate(null);
			
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

		if((transientProject.getSubmissionReasonId() == ApplicationConstants.SUBMISSION_REASON_NIHFUND  
				|| transientProject.getSubmissionReasonId() == ApplicationConstants.SUBMISSION_REASON_NONNIHFUND)
				&& (persistentProject.getSubmissionReasonId() == ApplicationConstants.SUBMISSION_REASON_GDSPOLICY
				|| persistentProject.getSubmissionReasonId() == ApplicationConstants.SUBMISSION_REASON_GWASPOLICY))
			return true;
		else
			return false;		
	}
	
	/**
	 * This method checks if any repositories were selected on the GDS plan page.
	 * @return
	 */
	public static boolean willThereBeAnyDataSubmittedInGdsPlan(Project project){

		logger.debug("Checking if any repositories were selected on the GDS plan page.");
		
		for(PlanAnswerSelection planAnswerSelection : project.getPlanAnswerSelection()){
			if( ApplicationConstants.PLAN_QUESTION_ANSWER_DATA_SUBMITTED_NO_ID == planAnswerSelection.getPlanQuestionsAnswer().getId()){	
				return false;			
			}
		}
		return true;
	}
}

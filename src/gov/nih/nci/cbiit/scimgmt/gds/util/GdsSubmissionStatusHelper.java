/**
 * 
 */
package gov.nih.nci.cbiit.scimgmt.gds.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.xml.sax.SAXException;

import gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants;
import gov.nih.nci.cbiit.scimgmt.gds.domain.InstitutionalCertification;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Lookup;
import gov.nih.nci.cbiit.scimgmt.gds.domain.PageStatus;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Project;
import gov.nih.nci.cbiit.scimgmt.gds.domain.Study;
import gov.nih.nci.cbiit.scimgmt.gds.services.LookupService;
import gov.nih.nci.cbiit.scimgmt.gds.services.ManageProjectService;

/**
 * @author menons2
 *
 */
public class GdsSubmissionStatusHelper {

	@Autowired
	protected LookupService lookupService;
	
	@Autowired
	protected ManageProjectService manageProjectService;
	
	private static GdsSubmissionStatusHelper instance;
   	private static boolean loaded = false;
	
   	
   	public void init() throws IOException, SAXException {
		if (!loaded) {
			instance = this;
			loaded = true;
		}
		BeanUtilsBean.getInstance().getConvertUtils().register(false, true, -1);
	}
   	
   	
   	/**
	 * Gets the single instance of GdsSubmissionStatusHelper.
	 * 
	 * @return single instance of GdsSubmissionStatusHelper
	 */
	public static GdsSubmissionStatusHelper getInstance() {
		return instance;
	}
	
	public String getPageStatus(String pageCode, Project project) {
		if(ApplicationConstants.PAGE_CODE_IC.equals(pageCode)) {
			return getIcPageStatus(project);
		}
		
		return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
	}
	
	
	private String getIcPageStatus(Project project) {
		
		String status = ApplicationConstants.PAGE_STATUS_CODE_COMPLETED;
		
		List<InstitutionalCertification> icList = manageProjectService.findIcsByProject(project);
		if(CollectionUtils.isEmpty(icList)) {
			return ApplicationConstants.PAGE_STATUS_CODE_NOT_STARTED;
		}
			
		if(!ApplicationConstants.FLAG_YES.equalsIgnoreCase(project.getCertificationCompleteFlag())) { 
			return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
		}
		
		//There is at least one IC and IC certification flag says done. So proceed to
		//check if the ICs are all ok.
		for(InstitutionalCertification ic: icList) {
			if(!ApplicationConstants.YES_ID.equals(ic.getGpaApprovalCode())) {
				return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
			}
			List<Study> studies = ic.getStudies();
			for(Study study: studies) {
				if(!ApplicationConstants.YES_ID.equals(study.getDulVerificationId())) {
					return ApplicationConstants.PAGE_STATUS_CODE_IN_PROGRESS;
				}
			}
		}
		
		return status;
	}
	
	
	public void computeMissingDataReport(Project project, Long pageId) {
		//Generate array of MissingDataFields - project id, page id, displayText level orderNum
		
	}
	
	
	public List getPageStatuses(Project project) {
		List<PageStatus> pageStatuses = new ArrayList<PageStatus>();
		
		List<String> pageCodes = Arrays.asList(ApplicationConstants.PAGE_CODE_IC);
		for(String pageCode: pageCodes) {
			String statusCode = getPageStatus(pageCode, project);
			Lookup status = 
					lookupService.getLookupByCode(ApplicationConstants.PAGE_STATUS_TYPE, statusCode);
			Lookup page = 
					lookupService.getLookupByCode(ApplicationConstants.PAGE_TYPE, pageCode);
			PageStatus pageStatus = new PageStatus();
			pageStatus.setStatus(status);
			pageStatus.setPage(page);
			pageStatus.setProject(project);
			pageStatuses.add(pageStatus);
		}
		
		return pageStatuses;
		
	}
}

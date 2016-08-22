<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<div class="panel panel-default" id="searchGrant" style="margin-top: 20px;">
        <div class="panel-heading"><span class="clickable panel-collapsed"><i class="fa fa-plus-square fa-lg" aria-hidden="true"></i></span>
          <div class="pheader" style="display:inline;"><h5>General Information</h5></div>
          <div style="display:inline; float: right;">
           <a href="/gds/manage/navigateToGeneralInfo.action?projectId=${project.id}">
            <i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="edit" title="edit"></i>
           </a>
          </div>
        </div> <!--end panel header-->
        
        <div class="panel-body" style="display:none;">
       
        <s:if test="%{pageStatusCode.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PAGE_STATUS_CODE_NOT_STARTED)}"> 
           No data entered.
         </s:if>
         <s:else>

                  <table width="100%" border="0" cellpadding="3">
    <tr>
      <td width="30%" style="white-space: nowrap"><strong>Project Submission Title:</strong></td>
      <td colspan="4">${project.submissionTitle}</td>
    </tr>
    <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td style="white-space: style="white-space: nowrap""><strong>Reason for being submitted:</strong></td>
      <td colspan="4"><s:property value="%{projectSubmissionReason}" /></td>
    </tr>
    <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td style="white-space: nowrap"><strong>Division/Office/Center:</strong></td>
      <td colspan="4">${project.docAbbreviation}</td>
    </tr>
    <tr>
      <td style="white-space: nowrap"><strong>Program Branch:</strong></td>
      <td colspan="4">${project.programBranch}</td>
    </tr>
    <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td style="white-space: nowrap"><strong>Intramural (Z01)/Grant/Contract #:</strong></td>
      <td colspan="4">${project.applicationNum}</td>
    </tr>
    <tr>
      <td style="white-space: nowrap"><strong>Intramural/Grant/Contract Project Title:</strong></td>
      <td colspan="4">${project.projectTitle}</td>
    </tr>
    <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td style="white-space: nowrap"><strong>Principal Investigator:</strong></td>
      <td style="white-space: nowrap" colspan="4">${project.piFirstName} ${project.piLastName}</td>
       </tr>
       <tr>
      <td ><strong>Email:</strong></td>
      <td width="67%"><s:a href="mailto:%{project.piEmailAddress}?">${project.piEmailAddress}</s:a></td>
    </tr>
    <tr>
      <td style="white-space: nowrap"><strong>Institution:</strong></td>
      <td colspan="4">${project.piInstitution}</td>
    </tr>
    <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>
      

    <tr>
      <s:if test="project.pocFirstName != null && project.pocLastName != null">
      <td style="white-space: nowrap"><strong>Primary Contact: </strong></td>
      <td colspan="4">${project.pocFirstName} ${project.pocLastName} 
          </s:if>
       
    </tr>
    <tr>
      <s:if test="project.pocEmailAddress != null">      
      <td style="white-space: nowrap"><strong>Email:</strong></td>
      <td colspan="4"><s:a href="mailto:%{project.pocEmailAddress}?">${project.pocEmailAddress}</s:a></td>
      </s:if>
    </tr>
    <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td style="white-space: nowrap"><strong>Program Director:</strong></td>
      <td colspan="4">${project.pdFirstName} ${project.pdLastName}</td>
    </tr>
    <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td style="white-space: nowrap"><strong>Project Start Date:</strong></td>
      <td colspan="4"><s:property value="%{projectStartDate}" /></td>
    </tr>
    <tr>
      <td style="white-space: nowrap"><strong>Project End Date: </strong></td>
      <td colspan="4"><s:property value="%{projectEndDate}" /></td>
    </tr>
    <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>
    <tr>    <s:if test="project.comments != null">
      <td style="white-space: nowrap"><strong>Comments:</strong></td>
      <td colspan="4">${project.comments}</td>
       </s:if>
    </tr>
  </table>
		
         </s:else>
        </div><!--end panel body-->
      </div><!--end panel-->
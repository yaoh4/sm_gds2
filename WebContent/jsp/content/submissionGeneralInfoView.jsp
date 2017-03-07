<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


  <div class="panel panel-default"  style="margin-top: 20px;">
      <div class="panel-heading"><span class="clickable panel-collapsed"><i class="fa fa-plus-square fa-lg" aria-hidden="true"></i></span>
        <div class="pheader" style="display:inline;"><h5>General Information</h5></div>
        <s:if test="%{!isReadOnlyUser() && editFlag.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@FLAG_YES) && project.latestVersionFlag.equals(\"Y\")}">
          <div style="display:inline; float: right;">
           <a href="/gds/manage/navigateToGeneralInfo.action?projectId=${project.id}">
            <i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="edit" title="edit"></i>
           </a>
          </div>
        </s:if>
          
      </div> <!--end panel header-->
        
      <div class="panel-body" style="display:none;">
       
        <s:if test="%{pageStatusCode.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PAGE_STATUS_CODE_NOT_STARTED)}"> 
           No data entered.
         </s:if>
         <s:else>
		<s:hidden id="grantSelection"  value="%{project.grantSelection}"/>
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
      <s:hidden id="answer" value="%{projectSubmissionReason}" />
      <td colspan="4"><s:property value="%{projectSubmissionReason}" /></td>
    </tr>
    <tr class="display">
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>
    <tr class="display">
      <td style="white-space: nowrap"><strong>NCI Division/Office/Center:</strong></td>
     <!-- <s:hidden id="projAbbr" value="%{project.applClassCode}"/> -->
      <td colspan="4">${project.docAbbreviation}</td>
    </tr>
   <tr class="display">
      <td style="white-space: nowrap"><strong>Branch/Program/Laboratory:</strong></td>
      <td colspan="4">${project.programBranch}</td>
    </tr>
    <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>
    
    
    <!-- ----EXTRAMURAL FIELDS    -->
    <s:if test = "extramuralGrant.id != null">
    <tr>
    <td colspan="6">
        <div class="panel  panel panel-default">
        
      <div class="panel-heading">EXTRAMURAL</div>
      <div class="panel-body">
   
   <table style="width: 90%;">
    <tr class="display">
      <td style="white-space: nowrap; width: 33%;">
        <s:if test="%{extramuralGrant.applClassCode.equals(\"G\")}">
      	  <strong>Grant #:</strong>
      	</s:if>
      	<s:else>
      	  <strong>Contract #:</strong>
      	</s:else>
      </td>
      <td>${extramuralGrant.grantContractNum}</td>
    </tr>
    <tr class="conditionalDisplay">
    <s:if test="extramuralGrant.cayCode != null">   
      <td style="white-space: nowrap"><strong>Cancer Activity:</strong></td>
      <td>${extramuralGrant.cayCode}</td>
      </s:if>
    </tr>
   <tr class="display">
      <td style="white-space: nowrap">
        <s:if test="%{extramuralGrant.applClassCode.equals(\"G\")}">
      	  <strong>Grant Project Title:</strong>
      	</s:if>
      	<s:else>
      	  <strong>Contract Project Title:</strong>
      	</s:else>
      </td>
      <td>${extramuralGrant.projectTitle}</td>
    </tr>
    <tr class="display">
      <td style="white-space: nowrap">&nbsp;</td>
      <td >&nbsp;</td>
    </tr>
    <tr>
      <td style="white-space: nowrap"><strong>Principal Investigator:</strong></td>
      <td style="white-space: nowrap">${extramuralGrant.piFirstName} ${extramuralGrant.piLastName}</td>
       </tr>
       <tr>
      <td ><strong>Email:</strong></td>
      <td width="67%"><s:a href="mailto:%{extramuralGrant.piEmailAddress}?">${extramuralGrant.piEmailAddress}</s:a></td>
    </tr>
     <tr>    
      <td style="white-space: nowrap"><strong>Institution:</strong></td>
      <td style="white-space: nowrap">${extramuralGrant.piInstitution}</td>
    </tr>
    <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <s:if test="extramuralGrant.pocFirstName != null && extramuralGrant.pocLastName != null">
      <td style="white-space: nowrap"><strong>Secondary Contact: </strong></td>
      <td>${extramuralGrant.pocFirstName} ${extramuralGrant.pocLastName} 
      </s:if>
    </tr>
    <tr>
      <s:if test="extramuralGrant.pocEmailAddress != null">      
      <td style="white-space: nowrap"><strong>Email:</strong></td>
      <td><s:a href="mailto:%{extramuralGrant.pocEmailAddress}?">${extramuralGrant.pocEmailAddress}</s:a></td>
      </s:if>
    </tr>
    <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr class="conditionalDisplay">
      <td style="white-space: nowrap"><strong>Program Director:</strong></td>
      <td>${extramuralGrant.pdFirstName} ${extramuralGrant.pdLastName}</td>
    </tr>
    <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
   <tr class="conditionalDisplay">
      <td style="white-space: nowrap"><strong>Project Start Date:</strong></td>
      <td><s:property value="%{projectStartDate}" /></td>
    </tr>
   <tr class="conditionalDisplay">
      <td style="white-space: nowrap"><strong>Project End Date: </strong></td>
      <td><s:property value="%{projectEndDate}" /></td>
    </tr>
    <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    </table>
    </div><!--end panel body-->
    </div><!--end panel-->
    </td> 
    </tr><!--end tr-->
    </s:if>
    
    <!-- -INTRAMURAL FIELDS -->
       <s:if test = "intramuralGrant.id != null">
    <tr>
    <td colspan="6">
        <div class="panel  panel panel-default">
        
      <div class="panel-heading">INTRAMURAL</div>
      <div class="panel-body">
   
   <table style="width: 90%;">
    <tr class="display">
      <td style="white-space: nowrap; width: 33%">
        <s:if test="%{intramuralGrant.applClassCode.equals(\"M\")}">
      	  <strong>Intramural #:</strong>
      	</s:if>
      	<s:else>
      	  <strong>Contract #:</strong>
      	</s:else>
      </td>
      <td >${intramuralGrant.grantContractNum}</td>
    </tr>
   <tr class="display">
      <td style="white-space: nowrap">
        <s:if test="%{intramuralGrant.applClassCode.equals(\"M\")}">
      	  <strong>Intramural Project Title:</strong>
      	</s:if>
      	<s:else>
      	  <strong>Contract Project Title:</strong>
      	</s:else>
      </td>
      <td >${intramuralGrant.projectTitle}</td>
    </tr>
    <tr class="display">
      <td style="white-space: nowrap">&nbsp;</td>
      <td >&nbsp;</td>
    </tr>
    <tr>
      <td style="white-space: nowrap"><strong>Principal Investigator:</strong></td>
      <td style="white-space: nowrap" >${intramuralGrant.piFirstName} ${intramuralGrant.piLastName}</td>
       </tr>
       <tr>
      <td ><strong>Email:</strong></td>
      <td width="67%"><s:a href="mailto:%{intramuralGrant.piEmailAddress}?">${intramuralGrant.piEmailAddress}</s:a></td>
    </tr>
    <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td >&nbsp;</td>
    </tr>
      
    <tr>
      <s:if test="intramuralGrant.pocFirstName != null && intramuralGrant.pocLastName != null">
      <td style="white-space: nowrap"><strong>Secondary Contact: </strong></td>
      <td>${intramuralGrant.pocFirstName} ${intramuralGrant.pocLastName} 
          </s:if>
       
    </tr>
    <tr>
      <s:if test="intramuralGrant.pocEmailAddress != null">      
      <td style="white-space: nowrap"><strong>Email:</strong></td>
      <td><s:a href="mailto:%{intramuralGrant.pocEmailAddress}?">${intramuralGrant.pocEmailAddress}</s:a></td>
      </s:if>
    </tr>

    </table>
    </div><!--end panel body-->
    </div><!--end panel-->
    </td> 
    </tr><!--end tr-->
</s:if>



    <s:if test="%{associatedSecondaryGrants.size > 0}">
    <tr>
      <td colspan="6">  
<div class="well">
  <table style="width: 90%;">
    <tr style="background-color: #f5f5f5;">
  <td style="white-space: nowrap">
          <strong>Additional Grants:</strong></td>
        <td id="additionalGrantsVal" style="width: 67%">
       <s:iterator value="associatedSecondaryGrants" var="r" status="stat">
          ${r.grantContractNum}&nbsp;
       </s:iterator>
     </td>
     </tr>
     </table>
       </div>
       </td>
      </tr>
      </s:if>
      <s:if test="%{project.comments != null}">
      <tr>
        <td style="white-space: nowrap"><strong>Comments:</strong></td>
      </tr>
      <tr>
        <td colspan="4"><textarea style="width: 100%; border: 0px solid #000000; overflow-y: scroll; resize: none;" readonly="readonly">${project.comments}</textarea></td>
      </tr>
      </s:if>
    <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td >&nbsp;</td>
    </tr>
 
    
  </table>
         </s:else>
        </div><!--end panel body-->
      </div><!--end panel-->
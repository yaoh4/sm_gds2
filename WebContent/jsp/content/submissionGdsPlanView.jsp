<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<div class="panel panel-default" id="searchGrant" style="margin-top: 20px;">
  <div class="panel-heading">
    <span class="clickable panel-collapsed">
      <i class="fa fa-plus-square fa-lg" aria-hidden="true"></i>
    </span>
    <div class="pheader" style="display:inline;"><h5>Genomic Data Sharing Plan</h5></div>
      <s:if test="%{!isReadOnlyUser() && editFlag.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@FLAG_YES) && project.latestVersionFlag.equals(\"Y\")}">
      <div style="display:inline; float: right;">
        <a href="/gds/manage/editGdsPlan.action?projectId=${project.id}">
          <i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="edit" title="edit"></i>
        </a>
      </div>
    </s:if> 
  </div> <!--end panel header-->
        
  <div class="panel-body" style="display:none;">
  <s:if test="%{getPageStatus('GDSPLAN').status.code.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PAGE_STATUS_CODE_NOT_STARTED)}">
      No data entered.
  </s:if>
  <s:else>

    <table width="100%" border="0" cellpadding="3">

    <s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_ID) != ''}">
      <tr>  
		<td width="30%" style="white-space: nowrap"><strong>Data sharing exception requested for this project?</strong></td>
        <td style="padding-left: 20px;"><s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_ID)}" /></td>
      </tr>
    </s:if>
		 
     <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr> 
     
     
	<s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_ID) != ''}">
      <tr>
         <td><strong>Exception approved?</strong></td> 
         <td style="padding-left: 20px;"><s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_ID)}" /></td>
	  </tr>
	  <tr>
        <td style="white-space: nowrap">&nbsp;</td>
        <td colspan="4">&nbsp;</td>
      </tr>
	</s:if>

 
	<s:if test="%{exceptionMemo[0] != null}">
	  <tr>
        <td colspan="2"><strong>Uploaded Exception Memo:</strong><br/>
          <table style="width: 95%;" cellpadding="0px" cellspacing="0" class="table table-bordered table-striped">
            <tbody>
              <tr class="modalTheader">
                  <th class="tableHeader" align="center" width="10%">File Name</th>
                  <th class="tableHeader" align="center" width="10%">Date</th>
                  <th class="tableHeader" align="center" width="10%">Uploaded By</th>
              </tr>
              <tr>
                <td>
                  <s:a href="javascript:openDocument(%{exceptionMemo[0].id})">
                    <s:property	value="%{exceptionMemo[0].fileName}" />
                  </s:a>
                </td>
                <td style="white-space: nowrap">
                  <s:date name="%{exceptionMemo[0].uploadedDate}" format="MMM dd yyyy hh:mm:ss a" />
                </td>
                <td><s:property value="%{exceptionMemo[0].uploadedBy}" />
                </td>
              </tr>
            </tbody>
          </table>
        </td>
      </tr>
      <tr>
        <td style="white-space: nowrap">&nbsp;</td>
        <td colspan="4">&nbsp;</td>
      </tr>
	</s:if>
   
   
	<s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_SUBMITTED_ID) != ''}">
	  <tr>
		<td><strong>Will there be any data submitted?</strong></td> 
        <td style="padding-left: 20px;"><s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_SUBMITTED_ID)}" />
        </td>
      </tr>
      <tr>
        <td style="white-space: nowrap">&nbsp;</td>
        <td colspan="4">&nbsp;</td>
      </tr>
	</s:if>
		  
     
	<s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_SPECIMEN_ID) != ''}">
	  <tr>	
	    <td width="30%" style="white-space: nowrap;"><strong>Types of specimens the data submission pertain to:</strpmg></td>
        <td style="padding-left: 20px;"> <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_SPECIMEN_ID)}" /></td>
	  </tr>
	  <tr>
        <td style="white-space: nowrap">&nbsp;</td>
        <td colspan="4">&nbsp;</td>
      </tr>
	</s:if>
    
    
	<s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_TYPE_ID) != ''}">
	  <tr>
	    <td><strong>Type of data that will be submitted:</strong></td>
        <td style="padding-left: 20px;"> <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_TYPE_ID)}" /></td>
      </tr>
      <tr>
        <td style="white-space: nowrap">&nbsp;</td>
        <td colspan="4">&nbsp;</td>
      </tr>
    </s:if>

    
	<s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_ACCESS_ID) != ''}">
	  <tr>	   
	   <td><strong>Type of access the data will be made available through:</strong></td> 
       <td style="padding-left: 20px;"><s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_ACCESS_ID)}" /> &nbsp;&nbsp;&nbsp; </td>
	  </tr>
	  <tr>
        <td style="white-space: nowrap">&nbsp;</td>
        <td colspan="4">&nbsp;</td>
      </tr>
	</s:if>

    
	<s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_REPOSITORY_ID) != ''}">  	
	  <tr>
	    <td><strong> Repository(ies) the data will be submitted to:</strong></td>
        <td style="padding-left: 20px;"> <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_REPOSITORY_ID)}" /></td>
	  </tr>
      <tr>
        <td style="white-space: nowrap">&nbsp;</td>
        <td colspan="4">&nbsp;</td>
      </tr>	  
	</s:if>
    
	
	<s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_GPA_REVIEWED_ID) != ''}">  	
      <tr>
        <td><strong>Has the GPA reviewed the Data Sharing Plan?</strong></td>
        <td style="padding-left: 20px;"> <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_GPA_REVIEWED_ID)}" /></td>
      </tr>
      <tr>
        <td style="white-space: nowrap">&nbsp;</td>
        <td colspan="4">&nbsp;</td>
      </tr>
    </s:if>
        
    
	<s:if test="%{gdsPlanFile[0] != null}">
	  <tr>
		<td colspan="2">
          <strong>Uploaded Data Sharing Plan:</strong><br/>
          <table style="width: 95%;  margin-top: 10px;" cellpadding="0px" cellspacing="0" class="table table-bordered table-striped">
            <tbody>
              <tr class="modalTheader">
                  <th class="tableHeader" align="center" width="10%">Document Title</th>
                  <th class="tableHeader" align="center" width="10%">File Name</th>
                  <th class="tableHeader" align="center" width="10%">Date</th>
                  <th class="tableHeader" align="center" width="10%">Uploaded By</th>
              </tr>
              <tr>
                <td>
                  <s:if test="%{gdsPlanFile[0].fileName == null || gdsPlanFile[0].fileName == ''}">
					<s:a href="javascript:openDocument(%{gdsPlanFile[0].id})">
					<s:property value="%{gdsPlanFile[0].docTitle}" /></s:a>
				  </s:if>
				  <s:else>
					<s:property value="%{gdsPlanFile[0].docTitle}" />
				  </s:else>
				</td>
                <td>
                  <s:if test="%{gdsPlanFile[0].fileName != null && gdsPlanFile[0].fileName != ''}">
					<s:a href="javascript:openDocument(%{gdsPlanFile[0].id})">
					  <s:property value="%{gdsPlanFile[0].fileName}" />
					</s:a>
			      </s:if>
				</td>
                <td style="white-space: nowrap">
                  	<s:date	name="%{gdsPlanFile[0].uploadedDate}" format="MMM dd yyyy hh:mm:ss a" />
                </td>
                <td>
                  <s:property value="%{gdsPlanFile[0].uploadedBy}" />
                </td>
              </tr>
            </tbody>
          </table>
        </td>
      </tr>
      <tr>
        <td style="white-space: nowrap">&nbsp;</td>
        <td colspan="4">&nbsp;</td>
      </tr>
    </s:if>
   </table>
   <s:if test="%{project.planComments != null}">
	  <tr>
	    <td><strong>Comments:</strong></td>
	    </tr>
	    <tr>
        <td colspan="4"><textarea style="width: 100%; border: 0px solid #000000; overflow-y: scroll; resize: none;" readonly="readonly">${project.planComments}</textarea></td>
      </tr>
      <tr>
        <td style="white-space: nowrap">&nbsp;</td>
        <td colspan="4">&nbsp;</td>
      </tr>
    </s:if>
 </s:else>
</div><!--end panel body-->
</div><!--end panel-->

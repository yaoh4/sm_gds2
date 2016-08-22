<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<div class="panel panel-default" id="searchGrant" style="margin-top: 20px;">
        <div class="panel-heading">
          <span class="clickable panel-collapsed">
            <i class="fa fa-plus-square fa-lg" aria-hidden="true"></i>
          </span>
          <div class="pheader" style="display:inline;"><h5>Genomic Data Sharing Plan</h5></div>
          <div style="display:inline; float: right;">
            <a href="/gds/manage/editGdsPlan.action?projectId=${project.id}">
              <i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="edit" title="edit"></i>
            </a>
          </div>
        </div> <!--end panel header-->
        
        <div class="panel-body" style="display:none;">
         <s:if test="%{project.planAnswerSelection.size == 0}">
           No data entered.
         </s:if>
         <s:else>

         <table width="100%" border="0" cellpadding="3">

            <tr>
          <s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_ID) != ''}">
          
		<td width="25%" style="white-space: nowrap"><strong>Data sharing exception requested for this project?</strong></td>
    <td><s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_ID)}" /></td>
    </s:if>
    </tr>
		 
     <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr> 
     
      <tr>

		   <s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_ID) != ''}">
             <td><strong>Exception approved?</strong></td> 
             <td><s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_ID)}" /></td>
		  </s:if>
          </tr>

<tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>


          <tr>
			<s:if test="%{exceptionMemo[0] != null}">
            <td colspan="2"><strong>Uploaded Exception Memo:</strong></br>
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
			</s:if>
    </tr>
   
<tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>

    <tr>
			<s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_SUBMITTED_ID) != ''}">
				<td><strong>Will there be any data submitted?</strong></td> 
        <td><s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_SUBMITTED_ID)}" />
        </td>
			</s:if>
		  </tr>
     

<tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>

      <tr>
		  <s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_SPECIMEN_ID) != ''}">
			<td><strong>Types of specimens the data submission pertain to:</strpmg></td>
      <td> <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_SPECIMEN_ID)}" /></td>
		  </s:if>
    </tr>

<tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>


    <tr>
		  <s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_TYPE_ID) != ''}">
			<td><strong>Type of data that will be submitted:</strong></td>
      <td> <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_TYPE_ID)}" /></td>
          </s:if>
        </tr>

<tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>


        <tr>
		  <s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_ACCESS_ID) != ''}">
		   <td><strong>Type of access the data will be made available through:</strong></td> 
       <td><s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_ACCESS_ID)}" /> &nbsp;&nbsp;&nbsp; </td>
		  </s:if>
    </tr>


    <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>

    <tr>
		  <s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_REPOSITORY_ID) != ''}">  	
			 <td><strong> Repository(ies) the data will be submitted to:</strong></td>
       <td> <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_REPOSITORY_ID)}" /></td>
		  </s:if>
    </tr>

<tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>


    <tr>
		  <s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_GPA_REVIEWED_ID) != ''}">  	
            <td><strong>Has the GPA reviewed the Data Sharing Plan?</strong></td>
            <td> <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_GPA_REVIEWED_ID)}" /></td>
          </s:if>
        </tr>


<tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>


        <tr>
		  <s:if test="%{gdsPlanFile[0] != null}">
		  <td colspan="2">
            <strong>Uploaded Data Sharing Plan:</strong><br/>
              <table style="width: 95%;  margin-top: 10px;" cellpadding="0px" cellspacing="0" class="table table-bordered table-striped">
                <tbody><tr class="modalTheader">
                  <th class="tableHeader" align="center" width="10%">Documnent Title</th>
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
                  <td><s:property value="%{gdsPlanFile[0].uploadedBy}" /></td>
                </tr>
              </tbody>
            </table>
          </td>
		  </s:if>
    </tr>


    <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>

    <tr>
		   <s:if test="project.planComments != null">
			<td><strong>Comments:</strong></td>
      <td> ${project.planComments}</td>
		   </s:if>
     </tr>
   </table>
         </s:else>
        </div><!--end panel body-->
      </div><!--end panel-->

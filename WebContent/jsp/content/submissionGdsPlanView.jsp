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
          <s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_ID) != ''}">
			<p><span class="reportLabel">Data sharing exception requested for this project?</span>  <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_ID)}" /></p>
		  </s:if>
		   <s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_ID) != ''}">
             <p><span class="reportLabel">Exception approved?</span> <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_ID)}" /></p>
		  </s:if>
          <p>
			<s:if test="%{exceptionMemo[0] != null}">
            <span class="reportLabel">Uploaded Exception Memo:</span></br>
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
			</s:if>
			<s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_SUBMITTED_ID) != ''}">
				<span class="reportLabel">Will there be any data submitted?</span> <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_SUBMITTED_ID)}" />
			</s:if>
		  </p>
		  <s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_SPECIMEN_ID) != ''}">
			<p><span class="reportLabel">Types of specimens the data submission pertain to:</span> <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_SPECIMEN_ID)}" /></p>
		  </s:if>
		  <s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_TYPE_ID) != ''}">
			<p><span class="reportLabel">Type of data that will be submitted:</span> <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_TYPE_ID)}" /></p>
          </s:if>
		  <s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_ACCESS_ID) != ''}">
		   <p><span class="reportLabel">Type of access the data will be made available through:</span> <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_ACCESS_ID)}" /> &nbsp;&nbsp;&nbsp; </p>
		  </s:if>
		  <s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_REPOSITORY_ID) != ''}">  	
			 <p><span class="reportLabel"> Repository(ies) the data will be submitted to:</span> <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_REPOSITORY_ID)}" /></p>
		  </s:if>
		  <s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_GPA_REVIEWED_ID) != ''}">  	
            <p><span class="reportLabel">Has the GPA reviewed the Data Sharing Plan?</span> <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_GPA_REVIEWED_ID)}" /></p>
          </s:if>
		  <s:if test="%{gdsPlanFile[0] != null}">
		  <p>
            <span class="reportLabel">Uploaded Data Sharing Plan:</span><br/>
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
          </p>
		  </s:if>
		   <s:if test="project.planComments != null">
			<p><span class="reportLabel">Comments:</span> ${project.planComments}</p>
		   </s:if>
         </s:else>
        </div><!--end panel body-->
      </div><!--end panel-->

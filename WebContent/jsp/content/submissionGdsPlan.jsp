<%@ taglib uri="/struts-tags" prefix="s"%>


<s:form id="gds-form" cssClass="dirty-check" action="saveGdsPlan" namespace="manage" method="post"
			enctype="multipart/form-data" data-toggle="validator" role="form">
	<s:hidden name="projectId" value="%{project.id}"/>
	<!-- Page navbar -->
	<div class="pageNav">
		<s:submit action="saveGdsPlan" value=" Save " class="saved btn btn-default"/>
		<s:submit action="saveGdsPlanAndNext" value=" Save & Next " class="btn btn-project-primary"/>
	</div>

	<!-- Begin Panel -->
	<div class="col-md-12">
		<div class="panel  project-panel-primary">
			<div class="panel-heading">
				<div class="pheader">
					<h4>Genomic Data Sharing Plan</h4>
				</div>
				<%-- <div class="statusWrapper">
					<div class="status">
						<a href="#" class="statusLink">Generate Missing Data Report</a>
						&nbsp; &nbsp;
					</div>
					<div class="statusIcon">
						<a href="images/legend.gif" class="preview" title="Legend"><img
							src="images/inprogress.png" alt="In Progress" /></a>
					</div>
				</div> --%>
			</div>

			<div class="panel-body">
				<div style="float: right;" class="question">
					<a href="http://www.cancer.gov/grants-training/grants-management/nci-policies/genomic-data/submission/nci-dsp.pdf"
						target="_blank">Genomic Data Sharing Plan Template&nbsp;<i
						class="fa fa-external-link" aria-hidden="true"></i></a>
				</div>

				<p>&nbsp;</p>

				<%-- Is there a data sharing exception requested for this project? --%>
				<div id="1" style="${map['1'].style}" class="qSpacing">
					<p class="question">
						<s:property value="%{getQuestionById(1).getDisplayText()}" /> [
						<a href="http://www.cancer.gov/grants-training/grants-management/nci-policies/genomic-data#exceptions"
							target="_blank">View Exception Process&nbsp;<i
							class="fa fa-external-link" aria-hidden="true"></i></a> ]
					</p>
					<s:set name="params" value="%{map[1].parameters}" />
					<s:radio id="%{#answer.id}" list="%{getAnswerListByQuestionId(1)}"
						listKey="id" listValue="displayText" name="answers[1]"
						onClick="applyUiRule(this,${params})"
						template="radiomap-div.ftl" />
				</div>

				<%-- Was this exception approved? --%>
				<div id="4" style="${map['4'].style}" class="qSpacing">
					<p class="question"><s:property
							value="%{getQuestionById(4).getDisplayText()}" /></p>
					<s:set name="params" value="%{map[4].parameters}" />
					<s:radio id="%{#answer.id}" list="%{getAnswerListByQuestionId(4)}"
						listKey="id" listValue="displayText" name="answers[4]"
						onClick="applyUiRule(this,${params})"
						template="radiomap-div.ftl" />
				</div>

				<div id="exceptionMemoDiv" style="${map['exceptionMemoDiv'].style}" 
					class="qSpacing" style="margin-left: 30px;">
						<s:include value="/jsp/content/submissionGdsPlanMemo.jsp"/>
				</div>

				<%-- Will there be any data submitted? --%>
				<div id="8" style="${map['8'].style}" class="qSpacing">
					<p class="question"><s:property
							value="%{getQuestionById(8).getDisplayText()}" /></p>
					<s:set name="params" value="%{map[8].parameters}" />
					<s:radio id="%{#answer.id}" list="%{getAnswerListByQuestionId(8)}"
						listKey="id" listValue="displayText" name="answers[8]"
						onClick="applyUiRule(this,${params})"
						template="radiomap-div.ftl" />
				</div>

				<%-- What specimen type does the data submission pertain to? --%>
				<div id="11" style="${map['11'].style}" class="qSpacing">
					<p class="question"><s:property
							value="%{getQuestionById(11).getDisplayText()}" /></p>
					<s:iterator value="%{getAnswerListByQuestionId(11)}" var="ans"
						status="stat">
						<s:set name="params" value="%{map[#ans.id].parameters}" />
						<div class="checkbox">
							<s:checkbox id="%{#ans.id}" name="answers[11]" 
								value="%{getSelected(11, #ans.id)}" fieldValue="%{#ans.id}"
								onClick="applyUiRule(this,${params})" /> <s:label
								for="%{#ans.id}" value="%{#ans.displayText}"/>
						</div>
					</s:iterator>
				</div>

				<%-- What type of data will be submitted? --%>
				<div id="14" style="${map['14'].style}" class="qSpacing">
					<p class="question"><s:property
							value="%{getQuestionById(14).getDisplayText()}" /></p>
					<s:iterator value="%{getAnswerListByQuestionId(14)}" var="ans"
						status="stat">
						<s:set name="params" value="%{map[#ans.id].parameters}" />
						<div class="checkbox">
							<s:checkbox id="%{#ans.id}" name="answers[14]" 
								value="%{getSelected(14, #ans.id)}" fieldValue="%{#ans.id}"
								onClick="applyUiRule(this,${params})" /> <s:label
								for="%{#ans.id}" value="%{#ans.displayText}" />
						</div>
					</s:iterator>
				</div>

				<%-- What type of access is the data to be made available through? --%>
				<div id="17" style="${map['17'].style}" class="qSpacing">
					<p class="question"><s:property
							value="%{getQuestionById(17).getDisplayText()}" /></p>
					<s:iterator value="%{getAnswerListByQuestionId(17)}" var="ans"
						status="stat">
						<s:set name="params" value="%{map[#ans.id].parameters}" />
						<div class="checkbox">
							<s:checkbox id="%{#ans.id}" name="answers[17]" 
								value="%{getSelected(17, #ans.id)}" fieldValue="%{#ans.id}"
								onClick="applyUiRule(this,${params})" /> <s:label
								for="%{#ans.id}" value="%{#ans.displayText}" />
						</div>
					</s:iterator>
				</div>

				<%-- What repository will the data be submitted to? --%>
				<div id="20" style="${map['20'].style}" class="qSpacing">
					<p class="question"><s:property
							value="%{getQuestionById(20).getDisplayText()}" /></p>

					<s:iterator value="%{getAnswerListByQuestionId(20)}" var="ans"
						status="stat">
						<s:set name="params" value="%{map[#ans.id].parameters}" />
						<div class="checkbox">
							<s:checkbox id="%{#ans.id}" name="answers[20]" 
								value="%{getSelected(20, #ans.id)}" fieldValue="%{#ans.id}"
								onClick="applyUiRule(this,${params})" /> <s:label
								for="%{#ans.id}" value="%{#ans.displayText}" />
							<div id="controlledText" class="noteText" style="color:#686868;padding-left:20px;">
									<s:property value="%{#ans.additionalText}" />
							</div> 
							<s:if test="%{#ans.displayText=='Other'}">
								<div id="addRepo" style="display: none">
									<!--Repo hidden field-->				
									<s:if test="%{otherText[#ans.id].size > 0}">
										<s:iterator value="%{otherText[#ans.id]}" var="other" status="otherStat">
											<s:div class="otherWrapper" style="margin-bottom: 15px; margin-top: 15px;">
												<s:textfield id="field_%{#otherStat.index}" name="otherText[%{#ans.id}]" value="%{#other}"
													class="other" placeholder="Name of Repository" />
												<s:if test="#otherStat.index != 0">
													<span class="fa fa-trash removeclass delete" title="Delete" aria-hidden="true" alt="delete icon" style="font-size: 18px; padding-right: 3px;"></span>
												</s:if>
											</s:div>
										</s:iterator>
									</s:if>
									<s:else>
										<s:div class="otherWrapper" style="margin-bottom: 15px; margin-top: 15px;">
											<s:textfield id="field_0" name="otherText[%{#ans.id}]"
												class="other" placeholder="Name of Repository" />
										</s:div>
									</s:else>
									<div style="margin-left: 75px; margin-top: 15px;">
										<input id="addfield" type="button" class="btn btn-default" value="Add Another Repository" />
									</div>
								</div>
							</s:if>
						</div>
					</s:iterator>

				</div>
				<!--end add Repo hidden field-->

				<%-- Has the GPA reviewed the Data Sharing Plan? --%>
				<s:if test="%{requiredByGdsPolicy == true}">
					<div id="26" style="${map['26'].style}" class="qSpacing">
						<p class="question"><s:property
							value="%{getQuestionById(26).getDisplayText()}" /></p>
						<s:set name="params" value="%{map[26].parameters}" />
					<s:radio id="%{#answer.id}" list="%{getAnswerListByQuestionId(26)}"
						listKey="id" listValue="displayText" name="answers[26]"
						onClick="applyUiRule(this,${params})" 
						template="radiomap-div.ftl" />
					</div>
				</s:if>

				<%-- How would you like to submit the Data Sharing Plan? --%>
				<div id="29" style="${map['29'].style}" class="qSpacing">
					<p class="question"><s:property
						value="%{getQuestionById(29).getDisplayText()}" />
						<s:if test="%{requiredByGdsPolicy == false}">
							(Optional)
						</s:if></p>
					<s:set name="params" value="%{map[29].parameters}" />
				<s:radio id="%{#answer.id}" list="%{getAnswerListByQuestionId(29)}"
					listKey="id" listValue="displayText" name="answers[29]"
					onClick="applyUiRule(this,${params})"
					template="radiomap-div.ftl" />
				</div>
					
				<div id="textEditorDiv" style="${map['textEditorDiv'].style}">
					<textarea name="dataSharingPlanEditorText" id="editor1" rows="10"
						cols="80"></textarea>
					<div class="loadFileHistory">
						<s:include value="/jsp/content/submissionGdsPlanFile.jsp" />
					</div>
				</div>

				<!--BEGIN HIDDEN Field for Uploader-->
				<div class="qSpacing" style="margin-left: 30px; ${map['dataSharingPlanDiv'].style}"
					id="dataSharingPlanDiv">
					<p class="question">Upload Data Sharing Plan: [to be uploaded by GPA]</p>
					<s:file name="dataSharingPlan" id="dataSharingPlan" />
					<label for="dataSharingPlan" style="width: auto; display: none;">Upload
						Data Sharing Plan</label> 
					<div style="margin-left: 75px; margin-top: 15px;">
						<input type="button"
							name="dataSharingPlanUpload" value="Upload Data Sharing Plan File"
							class="saved btn btn-default" id="dataSharingPlanUpload">
					</div>
					<div class="loadFileHistory">
						<s:include value="/jsp/content/submissionGdsPlanFile.jsp" />
					</div>
				</div>

				<div>
					<p class="question">Comments:</p>
					<s:textarea class="col-md-10" rows="3" name="comments"></s:textarea>
				</div>
			</div>
			<!--end panel body-->
		</div>
	</div>
	

	<!--SAVE & NEXT BUTTONS-->
	<div class="pageNav">
		<s:submit action="saveGdsPlan" value=" Save " class="saved btn btn-default"/>
		<s:submit action="saveGdsPlanAndNext" value=" Save & Next " class="btn btn-project-primary"/>
	</div>
</s:form>

<!-- Modal -->
<div id="fileModal" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="fileModalLabel">
  <div class="modal-dialog">
   <div class="modal-content">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3 id="fileModalLabel">File Upload Message</h3>
    </div>
    <div id="fileModalId" class="modal-body">
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
    </div>
  </div>
 </div>
</div>	

<script type="text/javascript"
	src="<s:url value="/scripts/ckeditor/ckeditor.js" />"></script>
<script type="text/javascript"
	src="<s:url value="/controllers/dataSharing.js" />"></script>
<script type="text/javascript"
	src="<s:url value="/controllers/gds.js" />"></script>
<script type="text/javascript"
	src="<s:url value="/scripts/UiRule.js" />"></script>


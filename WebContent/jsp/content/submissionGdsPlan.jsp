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
					<%-- <div class="checkbox">
						<label style="display: inherit"> <input type="checkbox"
							name="repository" value="bGaP" id="dGaP"> Database of
							Genotypes and Phenotypes (dbGaP)
							<div id="controlledText" class="noteText" style="display: none">All
								controlled access studies must be registered in dbGaP,
								regardless of whether an exception was granted, or where the
								data is submitted.</div>
						</label>
					</div>--%>

					<s:iterator value="%{getAnswerListByQuestionId(20)}" var="ans"
						status="stat">
						<s:set name="params" value="%{map[#ans.id].parameters}" />
						<div class="checkbox">
							<s:checkbox id="%{#ans.id}" name="answers[20]" 
								value="%{getSelected(20, #ans.id)}" fieldValue="%{#ans.id}"
								onClick="applyUiRule(this,${params})" /> <s:label
								for="%{#ans.id}" value="%{#ans.displayText}" /> 
							<s:if test="%{#ans.displayText=='Other'}">
								<label for="otherRepository" style="width: auto; display: none;">Specify</label>
								<s:textfield id="otherRepository"
									name="otherText[%{#ans.id}]"
									placeholder="Specify" />
							</s:if>
						</div>
					</s:iterator>

					<%-- <div class="checkbox">
					<label> <input type="checkbox" name="repository"
						value="other" id="other"> Other
					</label>
					<div id="addRepo" style="display: none">
						<!--Repo hidden field-->
						<div id="InputsWrapper" class="otherWrapper">
							<input name="otherRepository" value="" type="text" class="other"
								type="text" placeholder="Name of Repository"></input><br />
						</div>

						<div style="margin-left: 75px; margin-top: 15px;">
							<button id="addfield" class="btn btn-default">Add
								Another Repository</button>
						</div>

					</div>--%>
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
						Data Sharing Plan</label> <input type="button"
						name="dataSharingPlanUpload" value="Upload Data Sharing Plan File"
						class="saved btn btn-default" id="dataSharingPlanUpload">
					<div class="loadFileHistory">
						<s:include value="/jsp/content/submissionGdsPlanFile.jsp" />
					</div>
				</div>

				<div>
					<p class="question">Comments:</p>
					<textarea class="col-md-10" rows="3"></textarea>
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
	src="<s:url value="/controllers/gdsPlan.js" />"></script>
<script type="text/javascript"
	src="<s:url value="/scripts/UiRule.js" />"></script>


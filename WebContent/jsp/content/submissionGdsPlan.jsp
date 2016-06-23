<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<s:form id="gds-form" cssClass="dirty-check" action="saveGdsPlan" namespace="manage" method="post"
			enctype="multipart/form-data">
	<s:hidden name="projectId" value="%{project.id}"/>
	<div class="content">
		<div class="inside">
			<fieldset>
				<%-- 1. Is there a data sharing exception requested for this project? --%>
				<div id="1" style="${map['1'].style}">
					<b>1. <s:property
							value="%{getQuestionById(1).getDisplayText()}" /></b>
					<s:set name="params" value="%{map[1].parameters}" />
					<s:radio id="%{#answer.id}" list="%{getAnswerListByQuestionId(1)}"
						listKey="id" listValue="displayText" name="answers[1]"
						onClick="applyUiRule(this,${params})"
						style="text-align: left; width: auto;" />
				</div>
				<%-- 2. Was this exception approved? --%>
				<div id="4" style="${map['4'].style}">
					<b>2. <s:property
							value="%{getQuestionById(4).getDisplayText()}" /></b>
					<s:set name="params" value="%{map[4].parameters}" />
					<s:radio id="%{#answer.id}" list="%{getAnswerListByQuestionId(4)}"
						listKey="id" listValue="displayText" name="answers[4]"
						onClick="applyUiRule(this,${params})"
						style="text-align: left; width: auto;" />
				</div>
				<div id="exceptionMemoDiv"
					style="${map['exceptionMemoDiv'].style}">
					<s:file name="exceptionMemo" id="exceptionMemo" />
					<label for="exceptionMemo" style="width: auto; display: none;">Upload
						Exception Memo</label> <input type="button" name="exceptionMemoUpload"
						value="Upload Exception Memo File" class="saved btn btn-default"
						id="exceptionMemoUpload"/>
					<div>
						<p> View uploaded <s:a href="downloadFile?docId=1">Exception Memo</s:a> in new window </p>
					</div>
				</div>
				<%-- 3. Will there be any data submitted? --%>
				<div id="8" style="${map['8'].style}">
					<b>3. <s:property
							value="%{getQuestionById(8).getDisplayText()}" /></b>
					<s:set name="params" value="%{map[8].parameters}" />
					<s:radio id="%{#answer.id}" list="%{getAnswerListByQuestionId(8)}"
						listKey="id" listValue="displayText" name="answers[8]"
						onClick="applyUiRule(this,${params})"
						style="text-align: left; width: auto;" />
				</div>
				<%-- 4. What specimen type does the data submission pertain to? --%>
				<div id="11" style="${map['11'].style}">
					<b>4. <s:property
							value="%{getQuestionById(11).getDisplayText()}" /></b>
					<s:iterator value="%{getAnswerListByQuestionId(11)}" var="ans"
						status="stat">
						<s:set name="params" value="%{map[#ans.id].parameters}" />
						<li><s:checkbox id="%{#ans.id}" name="answers[11]" 
								value="%{getSelected(11, #ans.id)}" fieldValue="%{#ans.id}"
								onClick="applyUiRule(this,${params})" /> <s:label
								for="%{#ans.id}" value="%{#ans.displayText}"
								style="text-align: left; width: auto;" /></li>
					</s:iterator>
				</div>
				<%-- 5. What type of data will be submitted? --%>
				<div id="14" style="${map['14'].style}">
					<b>5. <s:property
							value="%{getQuestionById(14).getDisplayText()}" /></b>
					<s:iterator value="%{getAnswerListByQuestionId(14)}" var="ans"
						status="stat">
						<s:set name="params" value="%{map[#ans.id].parameters}" />
						<li><s:checkbox id="%{#ans.id}" name="answers[14]" 
								value="%{getSelected(14, #ans.id)}" fieldValue="%{#ans.id}"
								onClick="applyUiRule(this,${params})" /> <s:label
								for="%{#ans.id}" value="%{#ans.displayText}"
								style="text-align: left; width: auto;" /></li>
					</s:iterator>
				</div>
				<%-- 6. What type of access is the data to be made available through? --%>
				<div id="17" style="${map['17'].style}">
					<b>6. <s:property
							value="%{getQuestionById(17).getDisplayText()}" /></b>
					<s:iterator value="%{getAnswerListByQuestionId(17)}" var="ans"
						status="stat">
						<s:set name="params" value="%{map[#ans.id].parameters}" />
						<li><s:checkbox id="%{#ans.id}" name="answers[17]" 
								value="%{getSelected(17, #ans.id)}" fieldValue="%{#ans.id}"
								onClick="applyUiRule(this,${params})" /> <s:label
								for="%{#ans.id}" value="%{#ans.displayText}"
								style="text-align: left; width: auto;" /></li>
					</s:iterator>
				</div>
				<%-- 7. What repository will the data be submitted to? --%>
				<div id="20" style="${map['20'].style}">
					<b>7. <s:property
							value="%{getQuestionById(20).getDisplayText()}" /></b>
					<s:iterator value="%{getAnswerListByQuestionId(20)}" var="ans"
						status="stat">
						<s:set name="params" value="%{map[#ans.id].parameters}" />
						<li><s:checkbox id="%{#ans.id}" name="answers[20]" 
								value="%{getSelected(20, #ans.id)}" fieldValue="%{#ans.id}"
								onClick="applyUiRule(this,${params})" /> <s:label
								for="%{#ans.id}" value="%{#ans.displayText}"
								style="text-align: left; width: auto;" /> 
							<s:if test="%{#ans.displayText=='Other'}">
								<label for="otherRepository" style="width: auto; display: none;">Specify</label>
								<s:textfield id="otherRepository"
									name="otherText[%{#ans.id}]"
									placeholder="Specify" />
							</s:if>
						</li>
					</s:iterator>
				</div>
				<%-- 8. Has the GPA reviewed the Data Sharing Plan? --%>
				<s:if test="%{requiredByGdsPolicy == true}">
				<div id="26" style="${map['26'].style}">
					<b>8. <s:property
							value="%{getQuestionById(26).getDisplayText()}" /></b>
					<s:set name="params" value="%{map[26].parameters}" />
					<s:radio id="%{#answer.id}" list="%{getAnswerListByQuestionId(26)}"
						listKey="id" listValue="displayText" name="answers[26]"
						onClick="applyUiRule(this,${params})"
						style="text-align: left; width: auto;" />
				</div>
				</s:if>
				<%-- 9. How would you like to submit the Data Sharing Plan? --%>
				<div id="29" style="${map['29'].style}">
					<b>9. <s:property
							value="%{getQuestionById(29).getDisplayText()}" />
						<s:if test="%{requiredByGdsPolicy == false}">
							(Optional)
						</s:if>
					</b>
					<s:set name="params" value="%{map[29].parameters}" />
					<s:radio id="%{#answer.id}" list="%{getAnswerListByQuestionId(29)}"
						listKey="id" listValue="displayText" name="answers[29]"
						onClick="applyUiRule(this,${params})"
						style="text-align: left; width: auto;" />
				</div>
				<div id="dataSharingPlanDiv"
					style="${map['dataSharingPlanDiv'].style}">
					<s:file name="dataSharingPlan" id="dataSharingPlan" />
					<label for="dataSharingPlan" style="width: auto; display: none;">Upload
						Data Sharing Plan</label> <input type="button"
						name="dataSharingPlanUpload" value="Upload Data Sharing Plan File"
						class="saved btn btn-default" id="dataSharingPlanUpload">
					<div>
						<p> View uploaded <s:a href="downloadFile?docId=1">Data Sharing Plan</s:a> in new window </p>
					</div>
				</div>
				<div id="textEditorDiv" style="${map['textEditorDiv'].style}">
					<textarea name="dataSharingPlanEditorText" id="editor1" rows="10"
						cols="80"></textarea>
        			<div>
						<p> View uploaded <s:a href="downloadFile?docId=1">Data Sharing Plan</s:a> in new window </p>
					</div>
				</div>
			</fieldset>
		</div>
	</div>
	
	<s:submit action="saveGdsPlan" value="Save" />
</s:form>
	
<br /><br /><br /><br />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript" src="<s:url value="/scripts/ckeditor/ckeditor.js" />"></script>
<script type="text/javascript" src="<s:url value="/controllers/gdsPlan.js" />"></script>
<script type="text/javascript" src="<s:url value="/scripts/UiRule.js" />"></script>


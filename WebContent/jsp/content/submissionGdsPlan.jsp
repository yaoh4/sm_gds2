<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<script type="text/javascript" src="<s:url value="/scripts/UiRule.js" />"></script>
<script type="text/javascript" src="<s:url value="/scripts/ckeditor/ckeditor.js" />"></script>

<s:form id="gds-form" cssClass="dirty-check" action="saveGdsPlan" namespace="manage" method="post"
			enctype="multipart/form-data">
	<div class="content">
		<div class="inside">
			<fieldset>
				<%-- 1. Is there a data sharing exception requested for this project? --%>
				<div id="1" style="${map['1'].style}">
					<b>1. <s:property
							value="%{getQuestionById(1).getDisplayText()}" /></b>
					<s:set name="params" value="%{map[1].parameters}" />
					<s:radio id="%{#answer.id}" list="%{getAnswerListByQuestionId(1)}"
						listKey="id" listValue="displayText" name="q1"
						onChange="applyUiRule(this,${params})"
						style="text-align: left; width: auto;" />
				</div>
				<%-- 2. Was this exception approved? --%>
				<div id="4" style="${map['4'].style}">
					<b>2. <s:property
							value="%{getQuestionById(4).getDisplayText()}" /></b>
					<s:set name="params" value="%{map[4].parameters}" />
					<s:radio id="%{#answer.id}" list="%{getAnswerListByQuestionId(4)}"
						listKey="id" listValue="displayText" name="q2"
						onChange="applyUiRule(this,${params})"
						style="text-align: left; width: auto;" />
				</div>
				<%-- 3. Will there be any data submitted? --%>
				<div id="8" style="${map['8'].style}">
					<b>3. <s:property
							value="%{getQuestionById(8).getDisplayText()}" /></b>
					<s:set name="params" value="%{map[8].parameters}" />
					<s:radio id="%{#answer.id}" list="%{getAnswerListByQuestionId(8)}"
						listKey="id" listValue="displayText" name="q3"
						onClick="applyUiRule(this,${params})"
						style="text-align: left; width: auto;" />
				</div>
				<%-- 4. What specimen type does the data submission pertain to? --%>
				<div id="11" style="${map['11'].style}">
					<b>4. <s:property
							value="%{getQuestionById(11).getDisplayText()}" /></b>
					<s:iterator value="%{getAnswerListByQuestionId(11)}" var="answer"
						status="stat">
						<s:set name="params" value="%{map[#answer.id].parameters}" />
						<li><s:checkbox id="%{#answer.id}" name="q4"
								onChange="applyUiRule(this,${params})" /> <s:label
								for="%{#answer.id}" value="%{#answer.displayText}"
								style="text-align: left; width: auto;" /></li>
					</s:iterator>
				</div>
				<%-- 5. What type of data will be submitted? --%>
				<div id="14" style="${map['14'].style}">
					<b>5. <s:property
							value="%{getQuestionById(14).getDisplayText()}" /></b>
					<s:iterator value="%{getAnswerListByQuestionId(14)}" var="answer"
						status="stat">
						<s:set name="params" value="%{map[#answer.id].parameters}" />
						<li><s:checkbox id="%{#answer.id}" name="q5"
								onChange="applyUiRule(this,${params})" /> <s:label
								for="%{#answer.id}" value="%{#answer.displayText}"
								style="text-align: left; width: auto;" /></li>
					</s:iterator>
				</div>
				<%-- 6. What type of access is the data to be made available through? --%>
				<div id="17" style="${map['17'].style}">
					<b>6. <s:property
							value="%{getQuestionById(17).getDisplayText()}" /></b>
					<s:iterator value="%{getAnswerListByQuestionId(17)}" var="answer"
						status="stat">
						<s:set name="params" value="%{map[#answer.id].parameters}" />
						<li><s:checkbox id="%{#answer.id}" name="q6"
								onChange="applyUiRule(this,${params})" /> <s:label
								for="%{#answer.id}" value="%{#answer.displayText}"
								style="text-align: left; width: auto;" /></li>
					</s:iterator>
				</div>
				<%-- 7. What repository will the data be submitted to? --%>
				<div id="20" style="${map['20'].style}">
					<b>7. <s:property
							value="%{getQuestionById(20).getDisplayText()}" /></b>
					<s:iterator value="%{getAnswerListByQuestionId(20)}" var="answer"
						status="stat">
						<s:set name="params" value="%{map[#answer.id].parameters}" />
						<li><s:checkbox id="%{#answer.id}" name="q7"
								onChange="applyUiRule(this,${params})" /> <s:label
								for="%{#answer.id}" value="%{#answer.displayText}"
								style="text-align: left; width: auto;" /></li>
					</s:iterator>
				</div>
				<%-- 8. Has the GPA reviewed the Data Sharing Plan? --%>
				<div id="26" style="${map['26'].style}">
					<b>8. <s:property
							value="%{getQuestionById(26).getDisplayText()}" /></b>
					<s:set name="params" value="%{map[26].parameters}" />
					<s:radio id="%{#answer.id}" list="%{getAnswerListByQuestionId(26)}"
						listKey="id" listValue="displayText" name="q8"
						onChange="applyUiRule(this,${params})"
						style="text-align: left; width: auto;" />
				</div>
				<%-- 9. How would you like to submit the Data Sharing Plan? --%>
				<div id="29" style="${map['29'].style}">
					<b>9. <s:property
							value="%{getQuestionById(29).getDisplayText()}" /></b>
					<s:set name="params" value="%{map[29].parameters}" />
					<s:radio id="%{#answer.id}" list="%{getAnswerListByQuestionId(29)}"
						listKey="id" listValue="displayText" name="q9"
						onChange="applyUiRule(this,${params})"
						style="text-align: left; width: auto;" />
				</div>
			</fieldset>
		</div>
	</div>
	<div id="exceptionMemo" style="margin: 15px; display: block; clear: both;">
		<s:file name="gdsFile" id="exceptionMemo" />
		<label for="exceptionMemo" style="width: auto; display: none;">Upload</label>
		<s:submit action="uploadFile" value="Upload File" />
	</div>
	<div id="textEditor" style="display: block; clear: both;">
		<textarea name="editorText" id="editor1" rows="10" cols="80"></textarea>
		<script>
        	// Replace the <textarea id="editor1"> with a CKEditor
            // instance, using default configuration.
            CKEDITOR.replace( 'editor1' );
        </script>
	</div>
</s:form>
	<div>
		<p>
			... or download <s:a href="downloadFile?docId=1">File</s:a> for viewing
		</p>
	</div>
	<div>
		<p>
			... or visit <a href="http://www.google.com/">Google</a> or close the
			window without saving!
		</p>
	</div>
<br /><br /><br /><br />
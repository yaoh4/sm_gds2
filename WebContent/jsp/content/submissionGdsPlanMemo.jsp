<%@ taglib uri="/struts-tags" prefix="s"%>

<p class="question">Upload Exception Memo: [to be uploaded by GPA]</p>

<s:file name="exceptionMemo" id="exceptionMemo" />
<label for="exceptionMemo" style="width: auto; display: none;">Upload
	Exception Memo</label> 
	<div style="margin-left: 75px; margin-top: 15px;">
		<input type="button" name="exceptionMemoUpload"
		value="Upload Exception Memo File" class="saved btn btn-primary"
		id="exceptionMemoUpload"/>
	</div>
						
<s:if test="%{excepMemoFile.size > 0}">
	<p></p>
	<table style="width: 60%;" cellpadding="0px" cellspacing="0"
		class="table table-bordered table-striped"
		style="margin-left: 10px;">
		<tr class="modalTheader">
			<th class="tableHeader" align="center" >File Name</th>
			<th class="tableHeader" align="center" >Date</th>
			<th class="tableHeader" align="center" >Uploaded By</th>
		</tr>
		<tr>
			<td><s:a href="javascript:openDocument(%{excepMemoFile[0].id})"><s:property
				value="%{excepMemoFile[0].fileName}" /></s:a></td>
			<td style="white-space: nowrap">
				<s:date name="%{excepMemoFile[0].uploadedDate}" format="MMM dd yyyy hh:mm:ss a" /></td>
			<td><s:property
				value="%{excepMemoFile[0].uploadedBy}" /></td>
		</tr>
	</table>
</s:if>
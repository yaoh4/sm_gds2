<%@ taglib uri="/struts-tags" prefix="s"%>
<div style="width:600px;">
	<div style="width:58%; float:left; margin-left:15px;">
		<p class="question">Upload Exception Memo: [to be uploaded by GPA]</p>
		<label for="exceptionMemo" style="width: auto; display: none;">Upload
		Exception Memo</label></div>
		<div style="margin-bottom: 20px; float:left; width:30%;">
			<div class="input-group" style="width: 94px;">
				<label class="input-group-btn"> <span class="btn btn-default">
					Choose File
					<input type="file" name="exceptionMemo" value="" id="exceptionMemo" style="display: none;">
				</span>
			</label>
		</div>
	</div>
	<div style="clear:both;"></div>
	
</div>

<s:if test="%{excepMemoFile.size > 0}">
<div class="qSpacing">
<p></p>
<div class="qSpacing" style="margin-top: 10px; margin-left: 15px;">
	<table style="width: 884px;" cellpadding="0px" cellspacing="0" class="table table-bordered table-striped">
		<tr>
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
</div>
</div>
</s:if>
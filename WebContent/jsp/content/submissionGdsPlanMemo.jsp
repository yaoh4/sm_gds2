<%@ taglib uri="/struts-tags" prefix="s"%>

<p class="question">Upload Exception Memo: [to be uploaded by GPA]</p>

<label for="exceptionMemo" style="width: auto; display: none;">Upload
	Exception Memo</label> 
	<p class="uploadBox">File Uploader</p>
	<div class="col-lg-6">
		<div class="input-group">
			<s:file class="form-control" placeholder="Choose File to Upload" name="exceptionMemo" id="exceptionMemo" />
		</div>
	</div>
	<input type="button" name="exceptionMemoUpload"
		value="Upload File" class="saved btn btn-primary upload"
		id="exceptionMemoUpload"/>
	
						
<s:if test="%{excepMemoFile.size > 0}">
	<p></p>
	<div class="qSpacing" style="margin-top: 10px; margin-left: 15px;">
	<table style="width: 95%;" cellpadding="0px" cellspacing="0" class="table table-bordered table-striped">
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
	</div>
</s:if>
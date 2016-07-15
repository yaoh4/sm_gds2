<%@ taglib uri="/struts-tags" prefix="s"%>

<p class="question">
	<i class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>
	Upload Institutional Certification
</p>
<!--BEGIN Uploader-->
<s:hidden name="docId" value="%{doc.id}" />

<div class="col-lg-6">
	<p class="uploadBox">
		File Uploader</span>
	</p>
	<div class="input-group">
		<input type="text" class="form-control" placeholder="Choose File to Upload" readonly><label
			class="input-group-btn"> <span class="btn btn-default">
				Choose File
			<s:file style="display: none;" name="ic" id="ic" />
		</span>
		</label>
	</div>
</div>
<input type="button" name="icUpload"
		value="Upload File" class="saved btn btn-primary upload"
		id="icUpload"/>

<div id="loadIcFileHistory">
	<s:if test="%{icFileDocs.size > 0}">
		<p></p>
		<table style="width: 80%;" cellpadding="0px" cellspacing="0"
			class="table table-bordered table-striped" style="margin-left: 10px;">
			<tr class="modalTheader">
				<th class="tableHeader" align="center" width="10%">File Name</th>
				<th class="tableHeader" align="center" width="10%">Date</th>
				<th class="tableHeader" align="center" width="10%">Uploaded By</th>
			</tr>
			<tr>
				<td><s:a href="javascript:openDocument(%{icFileDocs[0].id})">
						<s:property value="%{icFileDocs[0].fileName}" />
					</s:a></td>
				<td style="white-space: nowrap"><s:date
						name="%{icFileDocs[0].uploadedDate}"
						format="MMM dd yyyy hh:mm:ss a" /></td>
				<td><s:property value="%{icFileDocs[0].uploadedBy}" /></td>
			</tr>
		</table>
	</s:if>
</div>

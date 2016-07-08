<%@ taglib uri="/struts-tags" prefix="s"%>

<p class="question">
	<i class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>
	Upload Institutional Certification
</p>
<!--BEGIN Uploader-->
<s:hidden name="docId" value="%{doc.id}" />
<s:file name="ic" id="ic" cssStyle="margin-top: 2px;margin-left: 14px;" />
<label for="icDoc" style="width: auto; display: none;">Upload
	Institutional Certification</label> 
<div style="margin-left: 75px; margin-top: 15px;">
	<input type="button" name="icUpload" value="Upload File"
		class="saved btn btn-primary" id="icUpload">
</div>
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

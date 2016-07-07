<%@ taglib uri="/struts-tags" prefix="s"%>

<p></p>

<s:if test="%{icFileDocs.size > 0}">
	<table style="width: 80%;" cellpadding="0px" cellspacing="0"
		class="table table-bordered table-striped" style="margin-left: 10px;">
		<tr class="modalTheader">
			<th class="tableHeader" align="center" width="10%">File Name</th>
			<th class="tableHeader" align="center" width="10%">Date</th>
			<th class="tableHeader" align="center" width="10%">Uploaded By</th>
		</tr>
		<tr>
			<td>
				<s:a href="javascript:openDocument(%{icFileDocs[0].id})">
				<s:property value="%{icFileDocs[0].fileName}" /></s:a>
			</td>
			<td style="white-space: nowrap">
				<s:date name="%{icFileDocs[0].uploadedDate}"
					format="MMM dd yyyy hh:mm:ss a" />
			</td>
			<td>
				<s:property value="%{icFileDocs[0].uploadedBy}" />
			</td>
		</tr>
	</table>
</s:if>

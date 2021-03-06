<%@ taglib uri="/struts-tags" prefix="s"%>

<p></p>

<s:if test="%{gdsPlanFile.size > 0}">
	<table style="width: 869px;" cellpadding="0px" cellspacing="0"
		class="table table-bordered table-striped" style="margin-left: 15px;">
		<tr>
			<th class="tableHeader" align="center" width="10%">Document Title</th>
			<th class="tableHeader" align="center" width="15%">File Name</th>
			<th class="tableHeader" align="center" width="5%">Date</th>
			<th class="tableHeader" align="center" width="10%">Uploaded By</th>
			<th class="tableHeader" align="center" width="5%">Action</th>
		</tr>
		<tr>
			<td><s:property value="%{gdsPlanFile[0].docTitle}" /></td>
			<td><s:if test="%{gdsPlanFile[0].fileName != null && gdsPlanFile[0].fileName != ''}">
					<s:a href="javascript:openDocument(%{gdsPlanFile[0].id})">
					<s:property value="%{gdsPlanFile[0].fileName}" /></s:a>
				</s:if>
				<s:else>
					<s:a href="javascript:openDocument(%{gdsPlanFile[0].id})">
					<i class="fa fa-file-text" aria-hidden="true" alt="view" title="view"></i></s:a>
				</s:else></td>
			<td style="white-space: nowrap"><s:date
					name="%{gdsPlanFile[0].uploadedDate}"
					format="MMM dd yyyy hh:mm:ss a" /></td>
			<td><s:property value="%{gdsPlanFile[0].uploadedBy}" /></td>
			<td><s:a href="javascript:removeDocument(%{gdsPlanFile[0].id}, %{gdsPlanFile[0].projectId})">
				<i class="fa fa-trash fa-lg" aria-hidden="true" title="Delete"
				alt="Delete" style="margin-bottom: 5px;"></i></s:a></td>
		</tr>
	</table>
</s:if>
<s:else>
<p>&nbsp;</p>
<p>&nbsp;</p>
</s:else>

<s:if test="%{gdsPlanFile.size > 1}">
	<div class="qSpacing">
		<p class="question">
			<a href="javascript:void"
				class="history"><i class="expand fa fa-plus-square" aria-hidden="true"></i></a>&nbsp;History of Uploaded Documents
		</p>
		<div class="uploadedHistory" style="display: none; margin-left: 0px;">
			<table style="width: 869px;" cellpadding="0px" cellspacing="0"
				class="table table-bordered table-striped"
				style="margin-left: 10px;">
				<tr>
					<th class="tableHeader" align="center" width="10%">Document Title</th>
					<th class="tableHeader" align="center" width="10%">File Name</th>
					<th class="tableHeader" align="center" width="10%">Date</th>
					<th class="tableHeader" align="center" width="10%">Uploaded By</th>
					<th class="tableHeader" align="center" width="5%">Action</th>
				</tr>
				<s:iterator value="%{gdsPlanFile}" var="file" status="stat">

					<s:if test="#stat.index != 0">
						<tr>
							<td><s:property value="%{#file.docTitle}" /></td>
							<td><s:if test="%{#file.fileName != null && #file.fileName != ''}">
									<s:a href="javascript:openDocument(%{#file.id})">
									<s:property value="%{#file.fileName}" /></s:a>
								</s:if>
								<s:else>
									<s:a href="javascript:openDocument(%{#file.id})">
									<i class="fa fa-file-text" aria-hidden="true" alt="view" title="view"></i></s:a>
								</s:else></td>
							<td style="white-space: nowrap"><s:date
								name="%{#file.uploadedDate}"
								format="MMM dd yyyy hh:mm:ss a" /></td>
							<td><s:property value="%{#file.uploadedBy}" /></td>
							<td><s:a href="javascript:removeDocument(%{#file.id}, %{#file.projectId})">
								<i class="fa fa-trash fa-lg" aria-hidden="true"
								alt="Delete" title="Delete"></i></s:a></td>
						</tr>
					</s:if>
				</s:iterator>
			</table>
		</div>
	</div>
</s:if>

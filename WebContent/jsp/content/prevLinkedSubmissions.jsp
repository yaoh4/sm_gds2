<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta charset="utf-8">

<body class="popPage">
	<s:if test="%{prevLinkedSubmissions.size > 0}">
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="alert alert-warning">
					<button type="button" class="close" aria-hidden="true">&times;</button>
					<i class="fa fa-exclamation-triangle" aria-hidden="true"></i>&nbsp;The
					following submission(s) has been linked to the same
					Intramural/Grant/Contract #
				</div>
				
				<table style="width: 100%;" cellpadding="0px" cellspacing="0"
					class="table table-bordered table-striped"
					style="margin-left: 10px;">
					<tr class="modalTheader">
						<th class="tableHeader" align="center" width="10%" scope="col"
							style="white-space: nowrap">Project/Sub-project ID</th>
						<th class="tableHeader" width="50%" scope="col">Project Title</th>
						<th class="tableHeader" widht="25%" scope="col">Principal
							Investigator</th>
						<th class="tableHeader" widht="25%" scope="col">Action</th>
					</tr>

					<s:iterator value="prevLinkedSubmissions" var="prevSubmission"
						status="stat">
						<s:if test="#stat.index /2 == 0">
							<tr class="tableContent">
						</s:if>
						<s:else>
							<tr class="tableContentOdd">
						</s:else>
						<td align="center"><s:property
								value="%{#prevSubmission.id}" /></td>
						<td class="paddingT" nowrap><s:property
								value="%{#prevSubmission.projectTitle}" /></td>
						<td class="paddingT"><s:a
								href="mailto:%{#prevSubmission.piEmailAddress}?">
								<s:property value="%{#prevSubmission.piLastName}" /> , <s:property
									value="%{#prevSubmission.piFirstName}" />
							</s:a></td>
						<td class="paddingT"><a href="#">View</a></td>
						</tr>
					</s:iterator>
				</table>
			</div>
		</div>
	</s:if>
</body>
</html>
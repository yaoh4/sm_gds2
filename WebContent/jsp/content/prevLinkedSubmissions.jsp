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
					<i class="fa fa-exclamation-triangle fa-lg" aria-hidden="true"></i>&nbsp;The
					following submission(s) have been linked to the same
					Grant/Intramural/Contract #
				</div>
				
				<table id="prevLinkedSubmissionsTable" style="width: 100%;" cellpadding="0px" cellspacing="0"
					class="table table-bordered table-striped"
					style="margin-left: 10px;">
					<tr class="modalTheader">
						<th class="tableHeader" align="center" width="10%" scope="col"
							style="white-space: nowrap">Project/Sub-project ID</th>
						<th class="tableHeader" width="50%" scope="col">Project/Sub-project Submission Title</th>
						<th class="tableHeader" width="25%" scope="col">Principal
							Investigator</th>
						<th class="tableHeader" width="5%" scope="col">Status</th>
						<th class="tableHeader" width="20%" scope="col">Action</th>
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
								value="%{#prevSubmission.id}" />
						<td class="paddingT" nowrap><s:property
								value="%{#prevSubmission.projectTitle}" /></td>
						<td class="paddingT"><s:a
								href="mailto:%{#prevSubmission.piEmailAddress}?">
								<s:property value="%{#prevSubmission.piLastName}" /> , <s:property
									value="%{#prevSubmission.piFirstName}" />
							</s:a></td>
							
						<td class="paddingT"> 
							<s:if test="%{#prevSubmission.projectStatusCode.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PAGE_STATUS_CODE_IN_PROGRESS)}">
								<img src="../images/inprogress.png" alt="In Progress" title="In Progress" width="18px" height="18px"/>
							</s:if> <s:elseif
								test="%{#prevSubmission.projectStatusCode.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PAGE_STATUS_CODE_COMPLETED)}">
								<img src="../images/complete.png" alt="Completed" title="Completed" width="18px" height="18px"/>
							</s:elseif> <s:else>
								<img src="../images/pending.png" alt="Not Started" title="Not Started" width="18px" height="18px"/>
							</s:else>
						</td>	
							
						<td class="paddingT"> <a href="#" onclick="openDetailsReport(${prevSubmission.id})"> 
						<s:hidden  id="prevSubId" value="%{#prevSubmission.id}"/>
						<i class="fa fa-file-text fa-lg" aria-hidden="true" alt="View" title="View"></i></a> &nbsp;&nbsp;&nbsp;
					 <a href="javascript: void(0)" id="confEdit" onclick="confirmEdit(this)">
						<i class="fa fa-pencil" aria-hidden="true" alt="Edit" title="Edit"></i></a>
						</td>
						</tr>
					</s:iterator>
				</table>
			</div>
		</div>
	</s:if>
	<s:else>
		<span>No existing submission(s) were found for the selected Grant/Intramural/Contract #</span>
	</s:else>
</body>
</html>
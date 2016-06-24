<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta charset="utf-8">
<title>Select Intramural (Z01)/Grant/Contract #</title>

<!-- Bootstrap -->
<link href="<s:url value="/stylesheets/bootstrap.min.css" />"
	rel="stylesheet" type="text/css" media="screen" />
<link href="<s:url value="/stylesheets/non-responsive.css" />"
	rel="stylesheet" type="text/css" media="screen" />
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.1/css/bootstrap-datepicker.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.1/css/font-awesome.min.css">
<link
	href='https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,600,600italic,700,700italic,900,900italic,400italic'
	rel='stylesheet' type='text/css'>
<link href="<s:url value="/stylesheets/custom.css" />" rel="stylesheet"
	type="text/css" media="screen" />
<body class="popPage">
	<s:form name="searchGrantsContractsForm"
		action="searchGrantsContractsAction" cssClass="form-horizontal">
		<div style="width: 750px; margin: 20px 20px 20px 20px;">
			<div class="panel panel-default" id="searchGrant">
				<div class="panel-heading">
					<div class="pheader">
						<h5>Search for Intramural (Z01)/Grant/Contract #</h5>
					</div>
				</div>
				<div>
				<s:if test="%{!actionErrors.isEmpty}">
  					 <p style="color:red"><strong>Errors:  <s:actionerror cssStyle="color:red"/> </strong> </p>					
				</s:if>
				</div>
				<!--end panel header-->
				<div class="panel-body">					
						<p>
							<strong>Please enter your search criteria using the
								following format:</strong>
						</p>
						<div>
							<div class="searchLabel">Extramural Grant:</div>
							<div class="searchFormat">(enter MECH-ICD-Serial#)</div>
						</div>
						<div>
							<div class="searchLabel">Intramural Grant:</div>
							<div class="searchFormat">(enter full Z01 or ZIA#)</div>
						</div>
						<div>
							<div class="searchLabel" align="right">Contract:</div>
							<div class="searchFormat">(enter full contract#)</div>
						</div>

						<div class="form-group"></div>
						<div class="form-group">
							<label for="Intramural (Z01)/Grant/Contract #"
								class="col-sm-4 control-label">Intramural
								(Z01)/Grant/Contract #</label>

							<div class="col-sm-6">
								<s:textfield name="grantContractNum" cssClass="form-control"
									id="grantSearch"
									placeholder="Use Correct Format from Examples Above"
									value="%{grantContractNum}" />
							</div>
						</div>
						<div class="col-sm-offset-4 col-sm-10 row">
							<s:submit value="Search"
								action="search/searchGrantsContractsAction"
								cssClass="btn btn-default" />
							<s:submit value="Reset"
								action="search/openSearchGrantsContracts"
								cssClass="btn btn-default" />
						</div>

						<p>&nbsp;</p>
						<!--Begin Search Results-->
						<table style="width: 100%;" cellpadding="0px" cellspacing="0"
							class="table table-bordered table-striped"
							style="margin-left: 10px;">
							<tr class="modalTheader">
								<th class="tableHeader" align="center" width="10%">Select</th>
								<th class="tableHeader" width="25%">Grant Number</th>
								<th class="tableHeader" width="50%">Project Title</th>
								<th class="tableHeader" width="25%">Principal Investigator</th>
							</tr>
							<s:iterator value="grantOrContractList" var="grantsContracts"
								status="stat">

								<s:if test="#stat.index /2 == 0">
									<tr class="tableContent">
								</s:if>
								<s:else>
									<tr class="tableContentOdd">
								</s:else>
								<td align="center"><s:radio theme="simple" list="#{top:''}"
										name="selectedGrantContract" /></td>
								<td class="paddingT" nowrap><s:property
										value="%{#grantsContracts.grantContractNum}" /></td>
								<td class="paddingT"><s:property
										value="%{#grantsContracts.projectTitle}" /></td>
								<td class="paddingT"><s:property
										value="%{#grantsContracts.piLastName}" /> , <s:property
										value="%{#grantsContracts.piFirstName}" /></td>
								</tr>
							</s:iterator>
						</table>

						<div class="alert alert-warning" style="display: none;">
							<button type="button" class="close" aria-hidden="true">&times;</button>
							<i class="fa fa-exclamation-triangle" aria-hidden="true"></i>&nbsp;Your
							selection might update some of the previously entered data in the
							General Information Page.
						</div>
						<div style="float: right; display: inline;">
							<s:submit id="btnConfirm" value="Select"
								onclick="populateGrantsContractsData()"
								cssClass="btn btn-project-primary" disabled="true"/>
						</div>
				</div>				
			</div>
			<!--end panel body-->
		</div>
		<!--end panel-->
		</div>
		<!--end container-->
		
		
		<script>
		function populateGrantsContractsData(){
			var grantContract = jQuery("input:radio[name=selectedGrantContract]:checked").val();
			var json = jQuery.parseJSON(grantContract);	
				
			if (json.grantContractNum !== "undefined") {
				window.opener.$("#grantsContractNum").val(json.grantContractNum);
				window.opener.$("#grantsContractNum").prop('readOnly', true);
			}
			
			if (json.projectTitle !== "undefined") {
				window.opener.$("#projectTitle").val(json.projectTitle);
				window.opener.$("#projectTitle").prop('readOnly', true);
			}
			
			if (json.piFirstName !== "undefined") {
					window.opener.$("#fnPI").val(json.piFirstName);
					window.opener.$("#fnPI").prop('readOnly', true);
			}
			
			if (json.piLastName !== "undefined") {
				window.opener.$("#lnPI").val(json.piLastName);
				window.opener.$("#lnPI").prop('readOnly', true);
			}
			
			if (json.piEmailAddress !== "undefined") {
				window.opener.$("#piEmail").val(json.piEmailAddress);
				window.opener.$("#piEmail").prop('readOnly', true);
			}
			
			
			if (json.piInstitution !== "undefined") {
				window.opener.$("#PIInstitute").val(json.piInstitution);
				window.opener.$("#PIInstitute").prop('readOnly', true);
			}
			
			if (json.pdFirstName !== "undefined") {
				window.opener.$("#fnPD").val(json.pdFirstName);
				window.opener.$("#fnPD").prop('readOnly', true);	
			}
				
			if (json.pdLastName !== "undefined") {
				window.opener.$("#lnPD").val(json.pdLastName);
				window.opener.$("#lnPD").prop('readOnly', true);
			}
			
			if (json.projectPeriodStartDate !== "undefined") {
				window.opener.$("#projectStartDate").val(json.projectPeriodStartDate);
				window.opener.$("#projectStartDate").prop('readOnly', true);
			}
			
			if (json.projectPeriodEndDate !== "undefined") {
				window.opener.$("#projectEndDate").val(json.projectPeriodEndDate);
				window.opener.$("#projectEndDate").prop('readOnly', true);
			}
			
			window.close();
			
		}
		</script>
		<script type="text/javascript"
			src="<s:url value="/scripts/tooltipjquery.js" />"></script>
		<script type="text/javascript"
			src="<s:url value="/scripts/bootstrap.min.js" />"></script>
		<script
			src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
		<script
			src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>

		<script>
			$(document).ready(function() {
				$(':radio').click(function() {
					$('.alert').show();
					$("#btnConfirm").prop('disabled', false);
				})
			});
			
		</script>
	</s:form>
</body>
</html>
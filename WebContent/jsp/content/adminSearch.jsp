<html lang="en" xmlns="http://www.w3.org/1999/xhtml"><head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta charset="utf-8">
	<title>GDS Tracking System</title>
	<script src="../../scripts/jquery-1.12.3.min.js"></script>
	<script src="../../scripts/bootstrap-3.3.6.min.js"></script>
	<script src="../../scripts/bootbox-4.4.0.min.js"></script>
	<script src="../../scripts/bootstrap-datepicker-1.6.1.min.js"></script>
	<script src="../../scripts/datatables-1.10.12.min.js"></script>
	<script src="../../scripts/jquery.validate-1.15.0.min.js"></script>
	<script src="../../scripts/jquery.are-you-sure-1.9.0.js"></script>
	<script src="../../scripts/theme.js"></script>
	<script src="../../controllers/gds.js"></script>
	<link href="../../stylesheets/bootstrap-3.3.6.min.css" rel="stylesheet" type="text/css" media="screen">
	<!-- <link href="../../stylesheets/non-responsive.css" rel="stylesheet" type="text/css" media="screen" /> -->
	<link href="../../stylesheets/bootstrap-datepicker-1.6.1.min.css" rel="stylesheet" type="text/css" media="screen">
	<link href="../../stylesheets/datatables-1.10.12.min.css" rel="stylesheet" type="text/css" media="screen">
	<link href="../../stylesheets/font-awesome-4.6.3.min.css" rel="stylesheet" type="text/css" media="screen">
	<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,600,600italic,700,700italic,900,900italic,400italic" rel="stylesheet" type="text/css">
	<link href="../../stylesheets/custom.css" rel="stylesheet" type="text/css" media="screen">
	<link href="../../stylesheets/datatable.css" rel="stylesheet" type="text/css" media="screen">
	<link href="../../stylesheets/styles.css" rel="stylesheet" type="text/css" media="screen">
	<script src="../../struts/utils.js" type="text/javascript"></script>
</head>
<body>
	<!-- Header -->
	
	<div id="messages" class="container"></div>
	<!-- Content start -->
	<div class="container">
		
		<!--Begin Form -->
		<form id="general_form" name="general_form" action="viewProject.action" method="post" class="dirty-check" data-toggle="validator" role="form">
			<!-- Page navbar -->
			
			
			<div id="searchGrantsContracts" style="">
				
				
				
				
				<!-- Begin Panel -->
				<div class="col-md-12">
					<div class="panel project-panel-primary" id="searchGrant">
						
						<div class="panel-heading">
							<div class="pheader">
								<h4>Manage GDS User Accounts</h4>
							</div>
						</div>
						<!--end panel header-->
						<div class="panel-body">
							<h4>Search Criteria</h4>
							<div class="form-group row">
								<div class="checkbox"><label><input type="checkbox" value="" style="margin-left: -40px;">&nbsp;&nbsp;Only users with GDS Roles</label>
							</div>
							<div class="col-xs-5">
								<label for="First Name">First Name:</label>
								<input type="text" name="criteria.grantContractNum" maxlength="30" value="" id="grantNumber" class="form-control">
							</div>
							<div class="col-xs-5">
								<label for="Last Name">Last Name:</label>
								<input type="text" name="criteria.grantContractNum" maxlength="30" value="" id="grantNumber" class="form-control">
							</div>
							
							
							
							<div class="col-xs-5">
								<label for="NCI Division/Office/Center">NCI Division/Office/Center:</label>
								<select name="" id="DOC" class="c-select form-control">
									<option value="CCR">CCR</option>
									<option value="DCB">DCB</option>
									<option value="DCCPS">DCCPS</option>
									<option value="DCEG">DCEG</option>
									<option value="DCP">DCP</option>
									<option value="DCTD">DCTD</option>
									<option value="DEA">DEA</option>
									<option value="OD">OD</option>
									<option value="OD CBIIT" selected="selected">OD CBIIT</option>
									<option value="OD CCG">OD CCG</option>
									<option value="OD CCT">OD CCT</option>
									<option value="OD CGH">OD CGH</option>
									<option value="OD CRCHD">OD CRCHD</option>
									<option value="OD CSSI">OD CSSI</option>
									<option value="OD OCC">OD OCC</option>
									<option value="OD OCPL">OD OCPL</option>
									<option value="OD OHAM">OD OHAM</option>
									<option value="OD OSO">OD OSO</option>
									<option value="OD OSPA">OD OSPA</option>
									<option value="OD SBIR">OD SBIR</option>
								</select>
							</div>
							
							<div class="col-xs-5">
								<label for="User Role">User Role:</label>
								<select name="criteria.pdNpnId" id="directorSelect" class="c-select form-control">
									<option value="">Select User Role</option>
									<option value="">None</option>
									<option value="">GPA</option>
									<option value="">GPA Admin</option>
									<option value="">GDS User - Edit</option>
									<option value="">GDS User - Read Only</option>
									
									
								</select>
							</div>
							
							
							
							
							
							
							
							<div class="searchFormat col-xs-10" style="float:right; margin-top: 10px; padding-left: 70px;">
								<button type="button" class="btn btn-primary has-spinner" id="searchGrants" onclick="searchGrantsData()"><i class="fa fa-spinner fa-spin"></i> Search</button>
								<button type="button" class="btn btn-default" id="reset" onclick="resetData()">Reset</button>
								<p>&nbsp;</p>
							</div>
						</div>
						
						<!--Begin Search Results-->
						<div id="searchResults" style="margin-left: 10px;">
							<h4>&nbsp;</h4>
							<h4>Search Results</h4><br/>&nbsp;
							<table style="width: 95%;" cellpadding="0px" cellspacing="0" class="table table-bordered table-striped">
								<tbody><tr class="modalTheader">
									
									<th width="20%"  align="left" style="vertical-align:bottom;" scope="col">User Name</th>
									<th width="20%"  align="left"   style="whitespace: nowrap; vertical-align:bottom;" scope="col">Email Address</th>
									
									<th width="8%"  align="left" style="vertical-align:bottom;" scope="col">NCI DOC</th>
									<th width="20%"  align="left" style="vertical-align:bottom;" scope="col">Institutes/Centers</th>
                                    
                                    <th width="12%"  align="left" style="vertical-align:bottom;" scope="col">Current Role(s)</th>
                                    <th width="20%"  align="left" style="vertical-align:bottom;" scope="col">Created/Updated by:</th>
									
									<th width="5%"  align="left" style="vertical-align:bottom;" scope="col">Actions</th>
								</tr>
								
								<tr>
									<td>Catherine Fishman</td>
									<td><a href="mailto:Fishmanc@mail.nih.gov">Fishmanc@mail.nih.gov</a></td>
									<td>CBIIT </td>
									<td>NCI - National Cancer Institute</td>
									<td>PD/GPA Admin</td>
                                    <td>Catherine Fishman on 10/18/2016</td>
                                    
									<td><div style="white-space: nowrap; font-size: 14px;"><a data-toggle="modal" href="#myModal"><i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="Edit" title="Edit"></i></a>&nbsp;&nbsp;&nbsp;<a onclick="deleteSubmission(29)" href="javascript: void(0)"><i class="fa fa-trash fa-lg" aria-hidden="true" alt="Delete" title="Delete"></i></a></div></td>
								</tr>
								<tr>
									<td>John Fishman</td>
									<td><a href="mailto:Fishmanc@mail.nih.gov">JFishman2@mail.nih.gov</a></td>
									<td>DCEG</td>
									<td>NCI - National Cancer Institute</td>
									<td>None</td>
                                    <td>&nbsp;</td>
                                    
									<td><div style="white-space: nowrap; font-size: 14px;"><a data-toggle="modal" href="#myModal"><i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="Edit" title="Edit"></i></a>&nbsp;&nbsp;&nbsp;<a onclick="deleteSubmission(29)" href="javascript: void(0)"><i class="fa fa-trash fa-lg" aria-hidden="true" alt="Delete" title="Delete"></i></a></div></td>
								</tr>
								<tr>
									<td>Laura Fist</td>
									<td><a href="mailto:Fishmanc@mail.nih.gov">FistL@mail.nih.gov</a></td>
									<td>CCR</td>
									<td>NCI - National Cancer Institute</td>
									<td>None</td>
                                    <td>&nbsp;</td>
                                    
									<td><div style="white-space: nowrap; font-size: 14px;"><a data-toggle="modal" href="#myModal"><i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="Edit" title="Edit"></i></a>&nbsp;&nbsp;&nbsp;<a onclick="deleteSubmission(29)" href="javascript: void(0)"><i class="fa fa-trash fa-lg" aria-hidden="true" alt="Delete" title="Delete"></i></a></div></td>
								</tr>
								<tr>
									<td>Laura Fist</td>
									<td><a href="mailto:Fishmanc@mail.nih.gov">FistL@mail.nih.gov</a></td>
									<td>CCR </td>
									<td>NCI - National Cancer Institute</td>
									<td>GDS User - Edit</td>
                                    <td>Catherine Fishman on 09/10/2016</td>
									<td><div style="white-space: nowrap; font-size: 14px;"><a href="../manage/navigateToSubmissionDetail.action?projectId=29"><i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="Edit" title="Edit"></i></a>&nbsp;&nbsp;&nbsp;<a onclick="deleteSubmission(29)" href="javascript: void(0)"><i class="fa fa-trash fa-lg" aria-hidden="true" alt="Delete" title="Delete"></i></a></div></td>
								</tr>
							</form>
							
							
							
							
							
						</tbody></table>
						
						</div>	<!--end search results-->
						
						</div> <!--end panel body-->
						</div> <!--end panel-->
						
						
						
						</div> <!--  end Panel  -->
						
					</div>
					
					
					
					
					
					
				</div>

				<!-- /container -->
			</div>
			<p>&nbsp;</p>
			<!-- end Content -->
			<!-- Modal for Roles -->
			<div id="myModal" class="modal fade" role="dialog">
				<div class="modal-dialog modal-lg">
					<!-- Modal content-->
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Edit/Add User Role</h4>
						</div>
						<div class="modal-body">
							
							<p>&nbsp;Only one selection per user can be made.</p>
							<table style="width: 95%;" cellpadding="0px" cellspacing="0" class="table table-bordered table-striped">
								<tbody><tr class="modalTheader">
									<th class="tableHeader" scope="col" colspan="4">Select for:</th>
									<td colspan="4" align="left" bgcolor="#FFFFFF" style="vertical-align:bottom; border-color: #FFF; " scope="col">&nbsp;</td>
								</tr>
								<tr>
									<td width="6%" align="center" style="vertical-align:bottom; font-size: 11px;">GPA Admin</td>
									<td width="4%" align="center" style="vertical-align:bottom; font-size: 11px;">GPA</td>
									<td width="5%" align="center" style="vertical-align:bottom; font-size: 11px;">GDS User Edit</td>
									<td width="5%" align="center" style="vertical-align:bottom; font-size: 11px;">GDS User Read Only</td>
									<th width="28%" align="left" style="vertical-align:bottom; background-color: #14819b; color: #FFF; " scope="col">User Name</th>
									<th width="31%" align="left" style="whitespace: nowrap; vertical-align:bottom; background-color: #14819b; color: #FFF;" scope="col">Email Address</th>
									<th width="8%" align="left" style="vertical-align:bottom; background-color: #14819b; color: #FFF;" scope="col">DOC</th>
									<th width="13%" align="left" style="vertical-align:bottom; background-color: #14819b; color: #FFF;" scope="col">Active PD Role?</th>
								</tr>
								<tr>
									<td align="center" valign="middle"><form name="form1" method="post" action="">
										<input type="radio" name="userRole" id="GPA Admin" value="GPA Admin"></form></td>
										<td align="center" valign="middle"><input type="radio" name="userRole" id="GPA" value="GPA"></td>
										<td align="center" valign="middle"><input type="radio" name="userRole" id="GDS User Edit" value="GDS User Edit"></td>
										<td align="center" valign="middle"><input type="radio" name="userRole" id="GDS User Read Only" value="GDS User Read Only"></td>
										<td>Catherine Fishman</td>
										<td><a href="mailto:Fishmanc@mail.nih.gov">Fishmanc@mail.nih.gov</a></td>
										<td>DEA</td>
										<td>Yes</td>
									</tr>
									
									
									
									
									
									
									
								</tbody></table>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">Save</button>
								<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
							</div>
						</div>
					</div>
				</div>
				
			</body></html>
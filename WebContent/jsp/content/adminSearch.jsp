<%@ taglib uri="/struts-tags" prefix="s"%>
	
	
		<!--Begin Form -->
		<form id="admin_form" name="admin_form" action="searchGdsUsers.action" method="post" data-toggle="validator" role="form">
			<!-- Page navbar -->
			
			
			<div id="adminSearch" style="">
				
				
				
				
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
								<input type="text" name="criteria.firstName" maxlength="30" value="" id="firstName" class="form-control">
							</div>
							<div class="col-xs-5">
								<label for="Last Name">Last Name:</label>
								<input type="text" name="criteria.lastName" maxlength="30" value="" id="lastName" class="form-control">
							</div>
							
							
							
							<div class="col-xs-5">
								<label for="NCI Division/Office/Center">NCI Division/Office/Center:</label>
								<select name="" id="DOC" class="c-select form-control">
									<option value="CCR">All of NCI</option>
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
								<label for="User Role">GDS User Role:</label>
								<select name="criteria.roleId" id="role" class="c-select form-control">
									<option value="">Select User Role</option>
								
									<option>GPA</option>
								
									<option>GDS User - Edit</option>
									<option>GDS User - Read Only</option>
									
									
								</select>
							</div>
							
							
							
							
							
							
							
							<div class="searchFormat col-xs-10" style="float:right; margin-top: 10px; padding-left: 70px;">
								<button type="button" class="btn btn-primary has-spinner" id="search" onclick="searchGdsUsers()"><i class="fa fa-spinner fa-spin"></i> Search</button>
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
								
                                    
                                    <th width="12%"  align="left" style="vertical-align:bottom;" scope="col">Current Role(s)</th>
                                    <th width="20%"  align="left" style="vertical-align:bottom;" scope="col">Created/Updated by:</th>
									
									<th width="5%"  align="left" style="vertical-align:bottom;" scope="col">Actions</th>
								</tr>
								
								
								<s:if test="%{personRoles.size > 0}">
				  			<s:iterator value="personRoles" var="personRole" status="stat">
				    			<s:if test="#stat.index /2 == 0">
					  			<tr class="tableContent">
								</s:if>
								<s:else>
					  			<tr class="tableContentOdd">
								</s:else>					    	
																
								<td>${personRole.nedPerson.fullName}</td>
									<td><a href="mailto:${personRole.nedPerson.email}">${personRole.nedPerson.email}</a></td>
									<td>CBIIT </td>
							
									<td>PD/GPA</td>
                                    <td>${personRole.updatedBy} on ${personRole.updatedDate}</td>
                                    
									<td><div style="white-space: nowrap; font-size: 14px;"><a data-toggle="modal" href="#myModal"><i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="Edit" title="Edit"></i></a>&nbsp;&nbsp;&nbsp;<a onclick="deleteSubmission(29)" href="javascript: void(0)"><i class="fa fa-trash fa-lg" aria-hidden="true" alt="Delete" title="Delete"></i></a></div></td>
								
								 
									</tr>
								
								</s:iterator>
								</s:if>
								
								<s:else>
				  					<tr class="tableContent">
				    					<td colspan="4">Nothing found to display.</td>
				  					</tr>
								</s:else>
								
							
						</tbody></table>
						
						</div>	<!--end search results-->
						
						</div> <!--end panel body-->
						</div> <!--end panel-->
						
						
						
						</div> <!--  end Panel  -->
						
					</div>
					
					
					
					
					
					</form>
				
			
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
									<th class="tableHeader" scope="col" colspan="3">Select for:</th>
									<td colspan="4" align="left" bgcolor="#FFFFFF" style="vertical-align:bottom; border-color: #FFF; " scope="col">&nbsp;</td>
								</tr>
								<tr>
									
									<td width="4%" align="center" style="vertical-align:bottom; font-size: 11px;">GPA</td>
									<td width="5%" align="center" style="vertical-align:bottom; font-size: 11px;">GDS User Edit</td>
									<td width="5%" align="center" style="vertical-align:bottom; font-size: 11px;">GDS User Read Only</td>
									<th width="28%" align="left" style="vertical-align:bottom; background-color: #14819b; color: #FFF; " scope="col">User Name</th>
									<th width="31%" align="left" style="whitespace: nowrap; vertical-align:bottom; background-color: #14819b; color: #FFF;" scope="col">Email Address</th>
									<th width="8%" align="left" style="vertical-align:bottom; background-color: #14819b; color: #FFF;" scope="col">DOC</th>
									<th width="13%" align="left" style="vertical-align:bottom; background-color: #14819b; color: #FFF;" scope="col">Active PD Role?</th>
								</tr>
								<tr>
									
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
				
			

<script type="text/javascript" src="<s:url value="/controllers/gds.js"/>"></script>
<script type="text/javascript" src="<s:url value="/controllers/admin.js"/>"></script>
			
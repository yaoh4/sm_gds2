<%@ taglib uri="/struts-tags" prefix="s"%>

<!-- Modal for Sub-Projects -->
<div class="modal-dialog modal-lg">

	<!-- Modal content-->
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4 class="modal-title">
				Sub-Projects for Project ID:
				<s:property value="projectId" />
			</h4>
		</div>
		<div class="modal-body">
			<table style="width: 900px;" cellpadding="0px" cellspacing="0"
				class="table table-bordered">
				<thead>
					<tr class="modalTheader">
						<th width="8%" scope="col">Sub-Project ID</th>
						<th width="16%" scope="col">Sub-project Title</th>
						<th width="11%" scope="col">Principle Investigator</th>
						<th width="7%" scope="col">GDS Plan</th>
						<th width="7%" scope="col">Data<br /> Sharing<br />Exception</th>
						<th width="7%" scope="col">IC</th>
						<th width="7%" scope="col">BSI</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="subprojectList" var="s" status="stat">
						<tr>
							<td style="white-space: nowrap"><s:a
									href="../manage/navigateToGeneralInfo.action?projectId=%{#s.id}">
									<strong><s:property value="#s.id" /></strong>
								</s:a></td>
							<td><s:property value="#s.projectTitle" /></td>
							<td style="white-space: nowrap"><s:property escape="false"
									value="#s.piFullName" /></td>
							<td>
								<div class="searchProgess">
									<img src="../images/complete.png" alt="Complete" width="18px"
										height="18px" title="Complete" />
								</div>
							</td>
							<td>
								<div class="searchProgess">
									<img src="../images/inprogress.png"
										alt="In Progress" title="In Progress" width="18px" height="18px" />
								</div>
							</td>
							<td>
								<div class="searchProgess">
									<img src="../images/complete.png" alt="Complete" width="18px"
										height="18px" title="Complete"/>
								</div>
							</td>
							<td>
								<div class="searchProgess">
									<img src="../images/inprogress.png"
										alt="In Progress" title="In Progress" width="18px" height="18px" />
								</div>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
		<p>&nbsp;</p>

		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		</div>
	</div>
</div>
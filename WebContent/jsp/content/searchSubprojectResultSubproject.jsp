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
		<div class="subproject-table">
		<div class="subproject-div">
			<span> <a class="subproject-control shown"
				style="font-size: 12px; margin-left: 70px;"> <i
					class="expand fa fa-minus-square" aria-hidden="true"></i>
					&nbsp;Sub-Projects
			</a>
			</span>
			<table style="width: 90%; margin-left: 40px; margin-top: 5px;"
				class="table  table-bordered subtable">
				<thead>
					<tr class="modalTheader">
						<th class="dt-center" width="7%" scope="col" style="background: #e6e6e6;border-bottom-color:#e6e6e6;color:#000 ">Select</th>
						<th width="16%" scope="col" style="background: #e6e6e6;border-bottom-color:#e6e6e6;color:#000 ">Sub-project Submission Title</th>
						<th width="11%" scope="col" style="background: #e6e6e6;border-bottom-color:#e6e6e6;color:#000 ">Grant/<br />Intramural/Contract #</th>
						<th width="11%" scope="col" style="background: #e6e6e6;border-bottom-color:#e6e6e6;color:#000 ">Principal Investigator</th>
						<th width="7%" scope="col" style="background: #e6e6e6;border-bottom-color:#e6e6e6;color:#000 ">IC</th>
						<th width="7%" scope="col" style="background: #e6e6e6;border-bottom-color:#e6e6e6;color:#000 ">BSI</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="subprojectList" var="s" status="stat">
						<s:set name="subprojectId" value="#s.id" />
						<tr>
							<td class="dt-center">
								<s:if test="%{#s.projectStatusCode == 'COMPLETED'}">
									<input type="radio" name="projectId" value="${subprojectId}"/>
								</s:if>
							</td>
							<td><s:a href="../manage/navigateToSubmissionDetail.action?projectId=%{#s.id}">
									<s:property value="#s.projectSubmissionTitle" />
								</s:a></td>
							<td><s:property value="#s.grantContractNum" /></td>
							<td style="white-space: nowrap"><s:property escape="false"
									value="#s.piFullName" /></td>
							<td>
								<div class="searchProgess">
									<s:if
										test="%{#s.icPageStatusCode == 'INPROGRESS'}">
										<img src="../images/inprogress.png" alt="In Progress" width="18px"
											height="18px" title="In Progress"/>
									</s:if>
									<s:elseif
										test="%{#s.icPageStatusCode == 'COMPLETED'}">
										<img src="../images/complete.png" alt="Complete" width="18px"
											height="18px" title="Complete" />
									</s:elseif>
									<s:elseif
										test="%{#s.icPageStatusCode == 'NOTSTARTED'}">
										<img src="../images/pending.png" alt="Pending" width="18px"
											height="18px" title="Pending">
									</s:elseif>
									<s:else>
										<div style="text-align: center;">N/A</div>
									</s:else>
								</div>
							</td>
							<td>
								<div class="searchProgess">
									<s:if
										test="%{#s.bsiPageStatusCode == 'INPROGRESS'}">
										<img src="../images/inprogress.png" alt="In Progress" width="18px"
											height="18px" title="In Progress"/>
									</s:if>
									<s:elseif
										test="%{#s.bsiPageStatusCode == 'COMPLETED'}">
										<img src="../images/complete.png" alt="Complete" width="18px"
											height="18px" title="Complete" />
									</s:elseif>
									<s:elseif
										test="%{#s.bsiPageStatusCode == 'NOTSTARTED'}">
										<img src="../images/pending.png" alt="Pending" width="18px"
											height="18px" title="Pending">
									</s:elseif>
									<s:else>
										<div style="text-align: center;">N/A</div>
									</s:else>
								</div>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
		</div>
		<p>&nbsp;</p>

		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		</div>
	</div>
</div>
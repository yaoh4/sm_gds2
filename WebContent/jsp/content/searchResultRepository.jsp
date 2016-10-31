<%@ taglib uri="/struts-tags" prefix="s"%>

<!-- Modal for Repositories -->

<div class="modal-dialog modal-lg">

	<!-- Modal content-->
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4 class="modal-title">Repository Data Submissions</h4>
		</div>
		<div class="repository-table">
		<div class="repository-div">
			<span> <a class="repository-control shown"
				style="font-size: 12px; margin-left: 25px;"> <i
					class="expand fa fa-minus-square" aria-hidden="true"></i>
					&nbsp;Project Submission Status
			</a>
			</span>
			<table style="width: 90%; margin-left: 40px; margin-top: 5px;"
				class="table  table-bordered subtable">
				<tbody>
					<tr style="background: #e6e6e6;">
						<th>Repositories</th>
						<th>Registration Status</th>
						<th>Submission Status</th>
						<th>Study Released Status</th>
						<th>Accession Number</th>
					</tr>

					<s:iterator value="repoList" var="r" status="stat">
						<tr>
							<td>
								<s:if test="%{#r.planAnswerSelectionTByRepositoryId.otherText != null}">
									<s:property value="#r.planAnswerSelectionTByRepositoryId.planQuestionsAnswer.displayText" />
										 - <s:property value="#r.planAnswerSelectionTByRepositoryId.otherText" />
								</s:if>
								<s:else>
									<s:property value="#r.planAnswerSelectionTByRepositoryId.planQuestionsAnswer.displayText" />
								</s:else></td>
							<td>
								<div class="searchProgess">
									<s:if
										test="%{#r.lookupTByRegistrationStatusId.displayName == 'In Progress'}">
										<img src="../images/inprogress.png" alt="In Progress" width="18px"
											height="18px" title="In Progress"/>
									</s:if>
									<s:elseif
										test="%{#r.lookupTByRegistrationStatusId.displayName == 'Completed'}">
										<img src="../images/complete.png" alt="Complete" width="18px"
											height="18px" title="Complete" />
									</s:elseif>
									<s:elseif
										test="%{#r.lookupTByRegistrationStatusId.displayName == 'Not Started'}">
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
										test="%{#r.lookupTBySubmissionStatusId.displayName == 'In Progress'}">
										<img src="../images/inprogress.png" alt="In Progress" width="18px"
											height="18px"  title="In Progress"/>
									</s:if>
									<s:elseif
										test="%{#r.lookupTBySubmissionStatusId.displayName == 'Completed'}">
										<img src="../images/complete.png" alt="Complete" width="18px"
											height="18px"  title="Complete"/>
									</s:elseif>
									<s:elseif
										test="%{#r.lookupTBySubmissionStatusId.displayName == 'Not Started'}">
										<img src="../images/pending.png" alt="Pending" width="18px"
											height="18px" title="Pending">
									</s:elseif>
									<s:else>
										<div style="text-align: center;">N/A</div>
									</s:else>
								</div>
							</td>
							<td><s:property
									value="#r.lookupTByStudyReleasedId.displayName" /></td>
							<td><s:property value="#r.accessionNumber" /></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		</div>
	</div>

</div>

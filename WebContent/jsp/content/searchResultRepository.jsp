<%@ taglib uri="/struts-tags" prefix="s"%>

<!-- Modal for Repositories -->

<div class="modal-dialog modal-lg">

	<!-- Modal content-->
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4 class="modal-title">Repository Data Submissions</h4>
		</div>
		<div class="modal-body">
			<table width="100%" border="1" cellpadding="3"
				class="table  table-bordered">
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
									<s:else>
										<img src="../images/pending.png" alt="Pending" width="18px"
											height="18px" title="Pending">
									</s:else>
								</div>
							</td>
							<td>
								<div class="searchProgess">
									<s:if
										test="%{#r.lookupTByDataSubmissionStatusId.displayName == 'In Progress'}">
										<img src="../images/inprogress.png" alt="In Progress" width="18px"
											height="18px"  title="In Progress"/>
									</s:if>
									<s:elseif
										test="%{#r.lookupTByDataSubmissionStatusId.displayName == 'Completed'}">
										<img src="../images/complete.png" alt="Complete" width="18px"
											height="18px"  title="Complete"/>
									</s:elseif>
									<s:else>
										<img src="../images/pending.png" alt="Pending" width="18px"
											height="18px" title="Pending">
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
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		</div>
	</div>

</div>

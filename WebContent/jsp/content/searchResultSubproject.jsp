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
						<th width="8%" scope="col">Sub-project ID</th>
						<th width="16%" scope="col">Sub-project Title</th>
						<th width="11%" scope="col">Principle Investigator</th>
						<th width="7%" scope="col">GDS Plan</th>
						<th width="7%" scope="col">Data<br /> Sharing<br />Exception</th>
						<th width="7%" scope="col">IC</th>
						<th width="7%" scope="col">BSI</th>
						<th width="10%" scope="col">Actions</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="subprojectList" var="s" status="stat">
						<tr>
							<s:if test="%{#s.repositoryStatuses.size > 0}">
								<s:set name="rowSpan" value="2"/>
							</s:if>
							<s:else>
								<s:set name="rowSpan" value="1"/>
							</s:else>
							<td rowspan="${rowSpan}" style="white-space: nowrap"><s:a
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

							
							<td rowspan="${rowSpan}">
								<table cellpadding="1px" class="iconTable">
									<tr>
										<td>
											<div style="white-space: nowrap;">
												<!--icon div-->
												&nbsp;&nbsp;&nbsp;
												<s:a
													href="../manage/navigateToGeneralInfo.action?projectId=%{#s.id}">
													<i class="fa fa-pencil-square fa-lg" aria-hidden="true"
														alt="Edit" title="Edit"></i>
												</s:a>
												&nbsp;&nbsp;&nbsp;

												<s:a onclick="deleteSubmission(%{#s.id})" href="javascript: void(0)"
													class="editor_remove">
													<i class="fa fa-trash fa-lg" aria-hidden="true"
														alt="Delete" title="Delete"></i>
												</s:a>
												&nbsp;&nbsp;&nbsp; <a href="javascript: void(0)"><i class="fa fa-clone fa-lg" aria-hidden="true" title="Add New Version" alt="Add New Version"></i></a>
											</div>

										</td>
									</tr>
								</table>
							</td>
						</tr>

						<s:if test="%{#s.repositoryStatuses.size > 0}">
						<tr>
							<td colspan="6">
								<!--Repository Table --> <span> <a class="repoExpand"
									style="font-size: 12px; font-weight: bold;"> <i
										class="expand fa fa-plus-square" aria-hidden="true"></i>
										&nbsp;Sub-Project Repository Submission Status
								</a>
							</span>
								<div style="display: none">
									<table width="100%" border="1" cellpadding="3"
										class="table  table-bordered repoModalTable">
										<tbody>
											<tr style="background: #e6e6e6;">
												<th>Repositories</th>
												<th>Registration Status</th>
												<th>Submission Status</th>
												<th>Study Released Status</th>
												<th>Accession Number</th>
											</tr>

											<s:iterator value="%{#s.repositoryStatuses}" var="r"
												status="stat2">
												<tr>
													<td><s:if test="%{#r.planAnswerSelectionTByRepositoryId.otherText != null}">
															<s:property value="#r.planAnswerSelectionTByRepositoryId.planQuestionsAnswer.displayText" />
															 - <s:property value="#r.planAnswerSelectionTByRepositoryId.otherText" />
														</s:if> 
														<s:else>
															<s:property value="#r.planAnswerSelectionTByRepositoryId.planQuestionsAnswer.displayText" />
														</s:else>
													</td>
													<td>
														<div class="searchProgess">
															<s:if
																test="%{#r.lookupTByRegistrationStatusId.displayName == 'In Progress'}">
																<img src="../images/inprogress.png" alt="In Progress"
																	title="In Progress" width="18px" height="18px" />
															</s:if>
															<s:elseif
																test="%{#r.lookupTByRegistrationStatusId.displayName == 'Completed'}">
																<img src="../images/complete.png" alt="Complete"
																	title="Complete" width="18px" height="18px" />
															</s:elseif>
															<s:else>
																<img src="../images/pending.png" alt="Pending"
																	title="Pending" width="18px" height="18px">
															</s:else>
														</div>
													</td>
													<td>
														<div class="searchProgess">
															<s:if
																test="%{#r.lookupTBySubmissionStatusId.displayName == 'In Progress'}">
																<img src="../images/inprogress.png" alt="In Progress" 
																	title="In Progress" width="18px" height="18px" />
															</s:if>
															<s:elseif
																test="%{#r.lookupTBySubmissionStatusId.displayName == 'Completed'}">
																<img src="../images/complete.png" alt="Complete"
																	title="Complete" width="18px" height="18px" />
															</s:elseif>
															<s:else>
																<img src="../images/pending.png" alt="Pending"
																	title="Pending" width="18px" height="18px">
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
							</td>
						</tr>
						</s:if>
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
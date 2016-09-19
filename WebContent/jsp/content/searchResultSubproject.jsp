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
				style="font-size: 12px; font-weight: bold; margin-left: 25px;"> <i
					class="expand fa fa-minus-square" aria-hidden="true"></i>
					&nbsp;Sub-Projects
			</a>
			</span>
			<table style="width: 80%; margin-left: 100px;" cellpadding="0px" cellspacing="0"
				class="table table-bordered ">
				<thead>
					<tr class="modalTheader" >
						<th width="16%" scope="col" style="background: #e6e6e6;border-bottom-color:#e6e6e6;color:#000 ">Sub-project Submission Title</th>
						<th width="11%" scope="col" style="background: #e6e6e6;border-bottom-color:#e6e6e6;color:#000 ">Grant/<br />Intramural/Contract #</th>
						<th width="11%" scope="col" style="background: #e6e6e6;border-bottom-color:#e6e6e6;color:#000 ">Principal Investigator</th>
						<th width="7%" scope="col" style="background: #e6e6e6;border-bottom-color:#e6e6e6;color:#000 ">IC</th>
						<th width="7%" scope="col" style="background: #e6e6e6;border-bottom-color:#e6e6e6;color:#000 ">BSI</th>
						<th width="7%" scope="col" style="background: #e6e6e6;border-bottom-color:#e6e6e6;color:#000 ">Submission<br/> Status</th>
						<th width="10%" scope="col" style="background: #e6e6e6;border-bottom-color:#e6e6e6;color:#000 ">Actions</th>
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
							<td><s:a href="../manage/navigateToSubmissionDetail.action?projectId=%{#s.id}">
								<s:property value="#s.projectSubmissionTitle" /></s:a></td>
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
								</div>
							</td>
							<td>
								<div class="searchProgess">
									<s:if
										test="%{#s.repositoryPageStatusCode == 'INPROGRESS'}">
										<img src="../images/inprogress.png" alt="In Progress" width="18px"
											height="18px" title="In Progress"/>
									</s:if>
									<s:elseif
										test="%{#s.repositoryPageStatusCode == 'COMPLETED'}">
										<img src="../images/complete.png" alt="Complete" width="18px"
											height="18px" title="Complete" />
									</s:elseif>
									<s:elseif
										test="%{#s.repositoryPageStatusCode == 'NOTSTARTED'}">
										<img src="../images/pending.png" alt="Pending" width="18px"
											height="18px" title="Pending">
									</s:elseif>
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
													href="../manage/navigateToSubmissionDetail.action?projectId=%{#s.id}">
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
									style="font-size: 12px; font-weight: bold; margin-left: 25px;"> 
									<s:if test="%{#s.expandRepository}">
										<i class="expand fa fa-minus-square" aria-hidden="true"></i>
									</s:if>
									<s:else>
										<i class="expand fa fa-plus-square" aria-hidden="true"></i>
									</s:else>
										&nbsp;Sub-Project Submission Status
								</a>
							</span>
								<s:if test="%{#s.expandRepository}">
									<div style="display: block; margin-left: 25px;">
								</s:if>
								<s:else>
									<div style="display: none; margin-left: 25px;">
								</s:else>
								
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
		</div>
		<p>&nbsp;</p>

		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		</div>
	</div>
</div>
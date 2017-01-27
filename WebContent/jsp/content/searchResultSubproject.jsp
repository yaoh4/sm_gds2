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
				style="font-size: 12px; margin-left: 25px;"> <i
					class="expand fa fa-minus-square" aria-hidden="true"></i>
					&nbsp;Sub-Projects
			</a>
			</span>
			<table style="width: 90%; margin-left: 40px; margin-top: 5px;"
				class="table  table-bordered subtable">
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
								<s:property value="#s.projectSubmissionTitle" /> (v<s:property value="#s.versionNum" />)</s:a></td>
							<td>
								<s:if test="%{#s.extGrantContractNum == '' || #s.extGrantContractNum == null}">
									<s:property value="#s.intGrantContractNum" />
								</s:if>
								<s:elseif test="%{#s.intGrantContractNum == '' || #s.intGrantContractNum == null}}">
									<s:property value="#s.extGrantContractNum" />
								</s:elseif>
								<s:else>
									<s:property value="#s.extGrantContractNum" /><br>
									<s:property value="#s.intGrantContractNum" />
								</s:else>
							</td>
							<td style="white-space: nowrap">
								<s:if test="%{#s.extPiFullName == '' || #s.extPiFullName == null}">
									<s:property escape="false" value="#s.intPiFullName" />
								</s:if>
								<s:elseif test="%{#s.intPiFullName == '' || #s.intPiFullName == null}">
									<s:property escape="false" value="#s.extPiFullName" />
								</s:elseif>
								<s:else>
									<s:property escape="false" value="#s.extPiFullName" /><br>
									<s:property escape="false" value="#s.intPiFullName" />
								</s:else>
							</td>
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
									<s:else>
										<div style="text-align: center;">N/A</div>
									</s:else>
								</div>
							</td>

							
							<td rowspan="${rowSpan}">
								<table cellpadding="1px" class="iconTable">
									<tr>
										<td>
											<div style="white-space: nowrap;">
											  <s:if test="isReadOnlyUser()">
											  	<!--icon div-->
												&nbsp;&nbsp;&nbsp;
												<s:a
													href="../manage/navigateToSubmissionDetail.action?projectId=%{#s.id}">
													<i class="fa fa-file-text fa-lg" aria-hidden="true"
														alt="View" title="View"></i>
												</s:a>
											  </s:if>
											  <s:else>
												<!--icon div-->
												&nbsp;&nbsp;&nbsp;
												<s:a
													href="../manage/navigateToSubmissionDetail.action?projectId=%{#s.id}">
													<i class="fa fa-pencil-square fa-lg" aria-hidden="true"
														alt="Edit" title="Edit"></i>
												</s:a>
												&nbsp;&nbsp;&nbsp;

											  	<s:if test="isGPA()">
												<s:a onclick="deleteSubmission(%{#s.id})" href="javascript: void(0)"
													class="editor_remove">
													<i class="fa fa-trash fa-lg" aria-hidden="true"
														alt="Delete" title="Delete"></i>
												</s:a>
												&nbsp;&nbsp;&nbsp; 
												</s:if>
												
												<s:if test="%{#s.newVersionEligibleFlag.equalsIgnoreCase(\'Y\')}">
													<s:a href="../manage/createNewSubprojectVersion.action?projectId=%{#s.id}"><i class="fa fa-clone fa-lg" aria-hidden="true" title="Add New Version" alt="Add New Version"></i>
													</s:a>
												</s:if>
											  </s:else>
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
									style="font-size: 12px; margin-left: 25px;"> 
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
															<s:elseif
																test="%{#r.lookupTByRegistrationStatusId.displayName == 'Not Started'}">
																<img src="../images/pending.png" alt="Pending"
																	title="Pending" width="18px" height="18px">
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
																<img src="../images/inprogress.png" alt="In Progress" 
																	title="In Progress" width="18px" height="18px" />
															</s:if>
															<s:elseif
																test="%{#r.lookupTBySubmissionStatusId.displayName == 'Completed'}">
																<img src="../images/complete.png" alt="Complete"
																	title="Complete" width="18px" height="18px" />
															</s:elseif>
															<s:elseif
																test="%{#r.lookupTBySubmissionStatusId.displayName == 'Not Started'}">
																<img src="../images/pending.png" alt="Pending"
																	title="Pending" width="18px" height="18px">
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
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
						<th width="10%" scope="col">Actions</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="subprojectList" var="s" status="stat">
						<tr>
							<td rowspan="2" style="white-space: nowrap"><s:a
									href="../manage/navigateToGeneralInfo.action?projectId=%{#s.id}">
									<strong><s:property value="#s.id" /></strong>
								</s:a></td>
							<td><s:property value="#s.projectTitle" /></td>
							<td style="white-space: nowrap"><s:property escape="false"
									value="#s.piFullName" /></td>
							<td>
								<div class="searchProgess">
									<a href="../images/legend.gif" class="preview" title="Complete">
										<img src="../images/complete.png" alt="Complete" width="18px"
										height="18px" />
									</a>
								</div>
							</td>
							<td>
								<div class="searchProgess">
									<a href="../images/legend.gif" class="preview"
										title="In Progress"> <img src="../images/inprogress.png"
										alt="In Progress" width="18px" height="18px" />
									</a>
								</div>
							</td>
							<td>
								<div class="searchProgess">
									<a href="../images/legend.gif" class="preview" title="Complete">
										<img src="../images/complete.png" alt="Complete" width="18px"
										height="18px" />
									</a>
								</div>
							</td>
							<td>
								<div class="searchProgess">
									<a href="../images/legend.gif" class="preview"
										title="In Progress"> <img src="../images/inprogress.png"
										alt="In Progress" width="18px" height="18px" />
									</a>
								</div>
							</td>


							<td rowspan="2">
								<table cellpadding="1px" class="iconTable">
									<tr>
										<td>
											<div style="white-space: nowrap;">
												<!--icon div-->
												&nbsp;&nbsp;&nbsp;
												<s:a
													href="../manage/navigateToGeneralInfo.action?projectId=%{#s.id}">
													<i class="fa fa-pencil-square fa-lg" aria-hidden="true"
														alt="edit" title="edit"></i>
												</s:a>
												&nbsp;&nbsp;&nbsp;

												<s:a href="deleteProject.action?projectId=%{#s.id}"
													class="editor_remove">
													<i class="fa fa-trash fa-lg" aria-hidden="true"
														alt="delete" title="delete"></i>
												</s:a>
												&nbsp;&nbsp;&nbsp; <a href="#" class="eclick"><img
													src="../images/ellipsis.png" height="17px" width="16x"
													alt="open ellipsis"></a>	
											</div> <!--icon div-->
											
											<div class="ellipsis">
												<div class="dropbottom">
													<div class="dropmid"
														style="white-space: nowrap; height: auto;">
														<a href="newProjectVersion.htm">Add New Version</a><br />
													</div>
												</div>
											</div>

										</td>
									</tr>
								</table>
							</td>
						</tr>

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
													<td><s:property
															value="#r.planAnswerSelectionTByRepositoryId.planQuestionsAnswer.displayText" /></td>
													<td>
														<div class="searchProgess">
															<s:if
																test="%{#r.lookupTByRegistrationStatusId.displayName == 'In Progress'}">
																<img src="../images/inprogress.png"
																	data-toggle="tooltip" data-content="In progress"
																	alt="In Progress" width="18px" height="18px" />
															</s:if>
															<s:elseif
																test="%{#r.lookupTByRegistrationStatusId.displayName == 'Completed'}">
																<img src="../images/complete.png" alt="Complete"
																	width="18px" height="18px" />
															</s:elseif>
															<s:else>
																<img src="../images/pending.png" alt="Pending"
																	width="18px" height="18px">
															</s:else>
														</div>
													</td>
													<td>
														<div class="searchProgess">
															<s:if
																test="%{#r.lookupTByDataSubmissionStatusId.displayName == 'In Progress'}">
																<img src="../images/inprogress.png"
																	data-toggle="tooltip" data-content="In progress"
																	alt="In Progress" width="18px" height="18px" />
															</s:if>
															<s:elseif
																test="%{#r.lookupTByDataSubmissionStatusId.displayName == 'Completed'}">
																<img src="../images/complete.png" alt="Complete"
																	width="18px" height="18px" />
															</s:elseif>
															<s:else>
																<img src="../images/pending.png" alt="Pending"
																	width="18px" height="18px">
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
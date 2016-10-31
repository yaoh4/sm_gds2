<%@ taglib uri="/struts-tags" prefix="s"%>

<div id="messages" class="col-md-12" style="display: none">
	<div class="alert alert-danger">
		<h3>
			<i class="fa fa-exclamation-triangle" aria-hidden="true"></i>&nbsp;Error Status
		</h3>
		<ul class="errorMessage">
			<li><span>Please search and select a Submission.</span></li>
		</ul>
	</div>
</div>

<s:form action="search" namespace="/search" id="search-form">
	<s:hidden id="parentSearch" name="criteria.parentSearch" value="Y"/>
	<s:hidden id="selectedTypeOfProject" name="criteria.selectedTypeOfProject" value="%{criteria.selectedTypeOfProject}"/>
	<s:hidden id="selectedProject"/>
	
	<div class="pageNav">
		<!-- Page navbar -->
		<s:submit type="button" class="btn btn-project-primary submitButton">
			Save &amp; Next &nbsp;&nbsp;<i class="fa fa-caret-right"
				style="color: #ffffff;"></i>
		</s:submit>
	</div>

	<!-- Begin Panel -->
	<div class="col-md-12">
		<div class="panel  project-panel-primary">
			<div class="panel-heading">
				<div class="pheader">
					<s:if test="%{criteria.selectedTypeOfProject.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@SUBMISSION_TYPE_NEW_SUBPROJECT)}">
						<h4>Link Sub-project to Parent Project&nbsp;</h4>
					</s:if>
					<s:elseif test="%{selectedTypeOfProject.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@SUBMISSION_TYPE_NEW_VERSION_PROJECT)}">
						<h4>Find Existing Project to Create New Version&nbsp;</h4>
					</s:elseif>
					<s:else>
						<h4>Find Existing Sub-Project to Create New Version&nbsp;</h4>
					</s:else>
				</div>
			</div>
			<s:if test="%{criteria.selectedTypeOfProject.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@SUBMISSION_TYPE_NEW_SUBPROJECT)}">
				<p style="font-size: 14px; margin-left: 15px; margin-top: 5px;">You
					have selected to add a new Sub-project. You must first link your
					Sub-project with a Parent Project. Use the search criteria below to
					find the correct Parent Project. Link your Sub-project by selecting
					the Parent Project in the Search Results and then click "Save and
					Next" to continue.</p>
			</s:if>
			<s:elseif test="%{criteria.selectedTypeOfProject.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@SUBMISSION_TYPE_NEW_VERSION_PROJECT)}">
				<p style="font-size: 14px; margin-left: 15px; margin-top: 5px;">You
					have selected to create a new version of an existing Project. You must first find
					a Project to create a new version. Use the search criteria below to
					find the correct existing Project. Create a new version of a project by selecting
					the Project in the Search Results and then click "Save and
					Next" to continue.</p>
			</s:elseif>
			<s:else>
				<p style="font-size: 14px; margin-left: 15px; margin-top: 5px;">You
					have selected to create a new version of an existing Sub-project. You must first find
					a Sub-project to create a new version. Use the search criteria below to
					find the correct existing Sub-project. Create a new version of a Sub-project by selecting
					the Sub-project in the Search Results and then click "Save and
					Next" to continue.</p>
			</s:else>
			
			<s:hidden id="directorName" name="criteria.pdFirstAndLastName" />
			<div class="panel panel-default mainContent" style="width: 80%;">
				<div class="panel-body">
					<s:if test="%{criteria.selectedTypeOfProject.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@SUBMISSION_TYPE_NEW_SUBPROJECT)}">
						<h3 style="margin-top: 0px;">Parent Project Search Criteria</h3>
					</s:if>
					<s:elseif test="%{criteria.selectedTypeOfProject.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@SUBMISSION_TYPE_NEW_VERSION_PROJECT)}">
						<h3 style="margin-top: 0px;">New Version of an Existing Project Search Criteria</h3>
					</s:elseif>
					<s:else>
						<h3 style="margin-top: 0px;">New Version of an Existing Sub-Project Search Criteria</h3>
					</s:else>
					
					<div class="form-group row">
						<div class="col-xs-4">
							<label for="selectFrom">Submission from:</label>
							<s:select id="selectFrom" cssClass="c-select form-control"
								name="criteria.submissionFromId" list="submissionFromList"
								listKey="optionKey" listValue="optionValue" />
						</div>

						<div class="col-xs-4">
							<s:if test="%{criteria.selectedTypeOfProject.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@SUBMISSION_TYPE_NEW_VERSION_SUBPROJECT)}">
								<label for="projectTitle">Subproject Submission Title:</label>
							</s:if>
							<s:else>
								<label for="projectTitle">Project Submission Title:</label>
							</s:else>
							<s:textfield name="criteria.projectTitle" class="form-control"
								id="projectTitle" maxLength="100"/>
						</div>

						<div class="col-xs-4">
							<label for="grantNumber">Grant/Intramural/Contract #:</label>
							<s:textfield name="criteria.grantContractNum"
								class="form-control" id="grantNumber" maxLength="30"/>
						</div>

					</div>
					<!--end form group-->


					<div class="form-group row">
						<div class="col-xs-4">
							<label for="submissionReasonId">Reason for Submission:</label>
							<s:select id="submissionReasonId"
								name="criteria.submissionReasonId"
								value="criteria.submissionReasonId"
								cssClass="c-select form-control" list="submissionReasonList"
								listKey="optionKey" listValue="optionValue" emptyOption="true"/>
						</div>

						<div class="col-xs-4">
							<label for="investigator">Principal
								Investigator</label>
							<s:textfield name="criteria.piFirstOrLastName"
								class="form-control" id="investigator" maxLength="120"/>
						</div>

						<div class="col-xs-4">
							<label for="directorSelect">Program Director:</label>
							<s:select id="directorSelect" name="criteria.pdNpnId"
								value="criteria.pdNpnId" cssClass="c-select form-control"
								list="pdList" listKey="optionKey" listValue="optionValue" />
						</div>

					</div>
					<!--end form group-->
					<div class="searchButton">
						<button type="button" class="btn btn-primary has-spinner" id="search-btn"><i class="fa fa-spinner fa-spin"></i> Search</button>
						<s:submit action="navigateToParentSearch" value=" Clear " class="btn btn-default"/>
					</div>

				</div>
				<!--end panel body-->
			</div>
			<!--end panel-->
			<s:if test="%{criteria.selectedTypeOfProject.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@SUBMISSION_TYPE_NEW_VERSION_SUBPROJECT)}">
				<s:include value="/jsp/content/searchSubprojectResult.jsp" />
			</s:if>
			<s:else>
				<s:include value="/jsp/content/searchParentResult.jsp" />
			</s:else>
					
			

		</div>
	</div>

	<div class="pageNav">
		<!-- Page navbar -->
		<s:submit type="button" class="btn btn-project-primary submitButton">
			Save &amp; Next &nbsp;&nbsp;<i class="fa fa-caret-right"
				style="color: #ffffff;"></i>
		</s:submit>
	</div>
</s:form>

<%@ taglib uri="/struts-tags" prefix="s"%>

<s:form action="search" namespace="/search" id="search-form">

	<div class="pageNav">
		<!-- Page navbar -->
		<s:submit type="button" onclick="createNewSubProject()" class="btn btn-project-primary">
			Save &amp; Next &nbsp;&nbsp;<i class="fa fa-caret-right"
				style="color: #ffffff;"></i>
		</s:submit>
	</div>

	<!-- Begin Panel -->
	<div class="col-md-12">
		<div class="panel  project-panel-primary">
			<div class="panel-heading">
				<div class="pheader">
					<h4>Link Sub-project to Parent Project&nbsp;</h4>
				</div>
			</div>
			<p style="font-size: 14px; margin-left: 15px; margin-top: 5px;">You
				have selected to add a new Sub-project. You must first link your
				Sub-project with a Parent Project. Use the search criteria below to
				find the correct Parent Project. Link your Sub-project by selecting
				the Parent Project in the Search Results and then click "Save and
				Next" to continue.</p>

			<s:hidden id="directorName" name="criteria.pdFirstAndLastName" />
			<div class="panel panel-default mainContent" style="width: 80%;">
				<div class="panel-body">
					<h3 style="margin-top: 0px;">Parent Project Search Criteria</h3>
					<div class="form-group row">
						<div class="col-xs-4">
							<label for="Submission from">Submission from:</label>
							<s:select id="selectFrom" cssClass="c-select form-control"
								name="criteria.submissionFromId" list="submissionFromList"
								listKey="optionKey" listValue="optionValue" />
						</div>

						<div class="col-xs-4">
							<label for="Program Director">Program Director:</label>
							<s:select id="directorSelect" name="criteria.pdNpnId"
								value="criteria.pdNpnId" cssClass="c-select form-control"
								list="pdList" listKey="optionKey" listValue="optionValue" />
						</div>

						<div class="col-xs-4">
							<label for="Intramural(Z01)/Grant/Contract #:">Intramural(Z01)/Grant/Contract
								#:</label>
							<s:textfield name="criteria.grantContractNum"
								class="form-control" id="grantNumber" />
						</div>

					</div>
					<!--end form group-->


					<div class="form-group row">
						<div class="col-xs-4">
							<label for="Project/Subproject Title">Project/Subproject
								Title:</label>
							<s:textfield name="criteria.projectTitle" class="form-control"
								id="projectTitle" />
						</div>


						<div class="col-xs-4">
							<label for="Principal Investigator">Principal
								Investigator</label>
							<s:textfield name="criteria.piFirstOrLastName"
								class="form-control" id="investigator" />
						</div>

						<div class="col-xs-4">
							<label for="Accession Number">Accession Number</label>
							<s:textfield name="criteria.accessionNumber" class="form-control"
								id="accessionNumber" />
						</div>

					</div>
					<!--end form group-->
					<div class="searchButton">
						<button type="button" class="btn btn-primary has-spinner" id="search-btn"><i class="fa fa-spinner fa-spin"></i> Search</button>
					</div>

				</div>
				<!--end panel body-->
			</div>
			<!--end panel-->
			<s:include value="/jsp/content/searchParentResult.jsp" />

		</div>
	</div>

	<div class="pageNav">
		<!-- Page navbar -->
		<s:submit type="button" onclick="createNewSubProject()" class="btn btn-project-primary">
			Save &amp; Next &nbsp;&nbsp;<i class="fa fa-caret-right"
				style="color: #ffffff;"></i>
		</s:submit>
	</div>
</s:form>

<script type="text/javascript"
	src="<s:url value="/controllers/searchParent.js" />"></script>
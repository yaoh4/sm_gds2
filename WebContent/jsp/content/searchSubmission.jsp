<%@ taglib uri="/struts-tags" prefix="s"%>

<s:form action="search" namespace="/search" id="search-form">
	<s:hidden id="directorName" name="criteria.pdFirstAndLastName"/>
	<div class="panel panel-default mainContent" style="width: 80%;">
		<div class="panel-body">
			<h3 style="margin-top: 0px;">Search Criteria</h3>
			<div class="form-group row">
				<div class="col-xs-4">
					<label for="Submission from">Submission from:</label> 
					<s:select id="selectFrom" cssClass="c-select form-control" name="criteria.submissionFromId" list="submissionFromList" listKey="optionKey" listValue="optionValue" />
				</div>

				<div class="col-xs-4">
					<label for="Program Director">Program Director:</label>
					<s:select id="directorSelect" name="criteria.pdNpnId"
						value="criteria.pdNpnId" cssClass="c-select form-control" 
						list="pdList" listKey="optionKey" listValue="optionValue" />
				</div>

				<div class="col-xs-4">
					<label for="Intramural(Z01)/Grant/Contract #:">Intramural(Z01)/Grant/Contract #:</label> 
					<s:textfield  name="criteria.applicationNum" class="form-control" id="grantNumber" />
				</div>

			</div>
			<!--end form group-->


			<div class="form-group row">
				<div class="col-xs-4">
					<label for="Project/Subproject Title">Project/Subproject
						Title:</label> 
					<s:textfield  name="criteria.projectTitle" class="form-control" id="projectTitle" />
				</div>


				<div class="col-xs-4">
					<label for="Principal Investigator">Principal Investigator</label>
					<s:textfield  name="criteria.piFirstOrLastName" class="form-control" id="investigator" />
				</div>

				<div class="col-xs-4">
					<label for="Accession Number">Accession Number</label> 
					<s:textfield  name="criteria.accessionNumber" class="form-control" id="accessionNumber" />
				</div>

			</div>
			<!--end form group-->
			<div class="searchButton">
				<input type="button" id="search-btn" value=" Search " class="btn btn-project-primary"/>
			</div>

		</div>
		<!--end panel body-->
	</div>
	<!--end panel-->
	<s:include value="/jsp/content/searchResult.jsp"/>
</s:form>

<script type="text/javascript"
	src="<s:url value="/controllers/search.js" />"></script>
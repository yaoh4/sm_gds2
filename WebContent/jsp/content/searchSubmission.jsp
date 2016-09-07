<%@ taglib uri="/struts-tags" prefix="s"%>

<s:form action="search" namespace="/search" id="search-form">
	<s:hidden id="directorName" name="criteria.pdFirstAndLastName"/>
	<div class="panel project-panel-primary">

		<div class="panel-heading">
		    <div class="pheader">
			  <h4>Search Criteria</h4>
			</div>
		  </div>

		  <!--end panel header-->
		<div class="panel-body">

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
					<label for="Intramural(Z01)/Grant/Contract #:">Grant/Intramural/Contract # :</label> 
					<s:textfield  name="criteria.grantContractNum" class="form-control" id="grantNumber" maxLength="30"/>
				</div>

			</div>
			<!--end form group-->


			<div class="form-group row">
				<div class="col-xs-4">
					<label for="Project/Subproject Title">Project/Subproject
						Title:</label> 
					<s:textfield  name="criteria.projectTitle" class="form-control" id="projectTitle" maxLength="100"/>
				</div>


				<div class="col-xs-4">
					<label for="Principal Investigator">Principal Investigator</label>
					<s:textfield  name="criteria.piFirstOrLastName" class="form-control" id="investigator" maxLength="120"/>
				</div>

				<div class="col-xs-4">
					<label for="Accession Number">Accession Number</label> 
					<s:textfield  name="criteria.accessionNumber" class="form-control" id="accessionNumber" maxLength="30"/>
				</div>

			</div>
			<!--end form group-->
			<div class="searchButton">
				<button type="button" class="btn btn-primary has-spinner" id="search-btn"><i class="fa fa-spinner fa-spin"></i> Search</button>
			</div>

		
	<s:include value="/jsp/content/searchResult.jsp"/>
</s:form>

</div>
		<!--end panel body-->
	</div>
	<!--end panel-->

<script type="text/javascript"
	src="<s:url value="/controllers/search.js" />"></script>
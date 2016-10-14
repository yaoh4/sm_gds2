<%@ taglib uri="/struts-tags" prefix="s"%>

<div id="searchResult" style="display: none">
	<br />
	<h3>Search Results</h3>
	<br />
	<table id="parentTable" style="table-layout: fixed;" class="table table-striped table-bordered" >
        <thead>
        <tr>
			<th>Project ID</th>
			<th>Project Submission Title</th>
			<th>Grant/<br />Intramural/Contract #</th>
			<th>Principal Investigator</th>
			<th>PI First Name</th>
			<th>PI Email</th>
			<th width="8%">Genomic DSP</th>
			<th width="8%">GDSP Excep.</th>
			<th width="8%">IC</th>
			<th width="8%">BSI</th>
			<th>Subproject Count</th>
		</tr>
        </thead>
    </table>
	
	<div id="existingSubProjects" class="modal fade" role="dialog">
		<s:include value="/jsp/content/searchSubprojectResultSubproject.jsp"/>
	</div>
	
</div>

<script type="text/javascript"
	src="<s:url value="/controllers/searchSubproject.js" />"></script>
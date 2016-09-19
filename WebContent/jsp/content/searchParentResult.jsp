<%@ taglib uri="/struts-tags" prefix="s"%>

<div id="searchResult" style="display: none">
	<br />
	<h3>Search Results</h3>
	<br />
	<table id="parentTable" style="table-layout: fixed;" class="table table-striped table-bordered" >
        <thead>
        <tr>
        	<th align="center">Select</th>
			<th>Project ID</th>
			<th>Project Submission Title</th>
			<th>Grant/<br />Intramural/Contract #</th>
			<th>Principal Investigator</th>
			<th>PI First Name</th>
			<th>PI Email</th>
			<th>Genomic DSP</th>
			<th>GDSP Excep.</th>
			<th>IC</th>
			<th>BSI</th>
			<th>Subproject Count</th>
		</tr>
        </thead>
    </table>
	
	<div id="existingSubProjects" class="modal fade" role="dialog">
		<s:include value="/jsp/content/searchParentResultSubproject.jsp"/>
	</div>
	
</div>
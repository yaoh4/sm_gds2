<%@ taglib uri="/struts-tags" prefix="s"%>

<div id="searchResult" style="display: none">
	<br />
	<h3>Search Results</h3>
	<br />
	<table id="submissionTable" style="table-layout: fixed;" class="table table-striped table-bordered" >
        <thead>
        <tr>
			<th>Project ID</th>
			<th>Project<br/> Submission Title</th>
			<th>Grant/<br/>Intramural/Contract #</th>
			<th>Principal Investigator</th>
			<th>PI First Name</th>
			<th>PI Email</th>
			<th width="8%">Genomic<br/> DSP</th>
			<th width="8%">GDSP<br/> Excep.</th>
			<th width="6%">IC</th>
			<th width="8%">BSI</th>
			<th>Repository Count</th>
			<th>Sub-project Count</th>
			<th>Expand Subproject</th>
			<th>Expand Repository</th>
			<th>Subproject Eligible Flag</th>
			<th>Project Status Code</th>
			<th>New Version Eligible Flag</th>
			<th>Grant/<br/>Intramural/Contract # 2</th>
			<th>Principal Investigator 2</th>
			<th>PI First Name 2</th>
			<th>PI Email 2</th>
			<th>Version</th>
			<th width="10%">Submission<br/> Status</th>
			<th width="12%" style="whitespace:nowrap;">Actions</th>
		</tr>
        </thead>
    </table>

	<div id="repoModal" class="modal fade" role="dialog">
		<s:include value="/jsp/content/searchResultRepository.jsp"/>
	</div>
	
	<div id="existingSubProjects" class="modal fade" role="dialog">
		<s:include value="/jsp/content/searchResultSubproject.jsp"/>
	</div>
	
</div>
<%@ taglib uri="/struts-tags" prefix="s"%>

<div id="searchResult" style="display: none">
	<br />
	<h3>Search Results</h3>
	<br />

	<table id="submissionTable" style="table-layout: fixed;" class="table table-striped table-bordered" >
       	<section class="">
  <div class="container1"> <thead>
        <tr>
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
			<th>Repository Count</th>
			<th>Sub-project Count</th>
			<th>Expand Subproject</th>
			<th>Expand Repository</th>
			<th>Subproject Eligible Flag</th>
			<th>Project Status Code</th>
			<th>Submission<br/> Status</th>
			<th style="whitespace:nowrap;">Actions</th>
		</tr>
		</div>
</section>
        </thead>
    </table>


	<div id="repoModal" class="modal fade" role="dialog">
		<s:include value="/jsp/content/searchResultRepository.jsp"/>
	</div>
	
	<div id="existingSubProjects" class="modal fade" role="dialog">
		<s:include value="/jsp/content/searchResultSubproject.jsp"/>
	</div>
	
</div>
<%@ taglib uri="/struts-tags" prefix="s"%>

<div id="searchResult" style="display: none">
	<br />
	<h3>Search Results</h3>
	<br />
	<table id="submissionTable" width="100%" cellpadding="0px" cellspacing="0" class="table table-striped table-bordered responsive" >
        <thead>
        <tr>
			<th width="11%" scope="col" style="whitespace:nowrap;">Project ID</th>
			<th width="5%"  scope="col"  >Intramural/<br />Grant/Contract</th>
			<th width="20%" scope="col">Project Title</th>
			<th width="11%" scope="col">Principle Investigator</th>
			<th class="never">PI First Name</th>
			<th class="never">PI Email</th>
			<th width="6%"  scope="col">GDS Plan</th>
			<th width="6%"  scope="col">Data<br /> Sharing<br /> Exception</th>
			<th width="6%"  scope="col">IC</th>
			<th width="6%"  scope="col">BSI</th>
			<th class="never">Repository Count</th>
			<th class="never">Subproject Count</th>
			<th style="whitespace:nowrap;" width="6%" scope="col">Submission<br/> Status</th>
			<th width="11%"  scope="col" style="whitespace:nowrap;">Actions</th>
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
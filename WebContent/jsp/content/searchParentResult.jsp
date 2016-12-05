<%@ taglib uri="/struts-tags" prefix="s"%>

<div id="searchResult" style="display: none">
	<br />
	<div style="margin-left: 15px"><h3>Search Results</h3></div>
	<br />
	<table id="parentTable" style="table-layout: fixed;" class="table table-striped table-bordered" >
        <thead>
        <tr>
        	<th width="8%" align="center">Select</th>
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
			<th>Grant/<br/>Intramural/Contract # 2</th>
			<th>Principal Investigator 2</th>
			<th>PI First Name 2</th>
			<th>PI Email 2</th>
		</tr>
        </thead>
    </table>
	
	<div id="existingSubProjects" class="modal fade" role="dialog">
		<s:include value="/jsp/content/searchParentResultSubproject.jsp"/>
	</div>
	
</div>

<script type="text/javascript"
	src="<s:url value="/controllers/searchParent.js" />"></script>
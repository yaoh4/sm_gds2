<%@ taglib uri="/struts-tags" prefix="s"%>

<div id="searchResult" style="display: none">
	<br />
	<h3>Search Results</h3>
	<br />
	<table id="parentTable" width="100%" cellpadding="0px" cellspacing="0" class="table table-striped table-bordered responsive" >
        <thead>
        <tr>
        	<th width="5%"  scope="col" align="center">Select</th>
			<th style="width: 153px;"  scope="col">Project ID</th>
			<th width="5%"  scope="col">Grant/<br />Intramural/Contract #</th>
			<th width="16%" scope="col">Project Title</th>
			<th width="11%" scope="col">Principle Investigator</th>
			<th class="never">PI First Name</th>
			<th class="never">PI Email</th>
			<th width="9%"  scope="col">GDS Plan</th>
			<th width="9%"  scope="col">Data<br /> Sharing<br /> Exception</th>
			<th width="9%"  scope="col">IC</th>
			<th width="9%"  scope="col">BSI</th>
			<th class="never">Subproject Count</th>
		</tr>
        </thead>
    </table>
	
	<div id="existingSubProjects" class="modal fade" role="dialog">
		<s:include value="/jsp/content/searchParentResultSubproject.jsp"/>
	</div>
	
</div>
<%@ taglib uri="/struts-tags" prefix="s"%>

<s:if test="true">
	<br />
	<h3>Search Results</h3>
	<br />
	<table id="submissionTable" width="100%" cellpadding="0px" cellspacing="0" class="table table-striped table-bordered responsive" >
        <thead>
        <tr>
			<th width="8%"  scope="col">Project ID</th>
			<th width="5%"  scope="col">Intramural/<br />Grant/Contract</th>
			<th width="16%" scope="col">Project Title</th>
			<th width="11%" scope="col">Principle Investigator</th>
			<th class="never">PI First Name</th>
			<th class="never">PI Email</th>
			<th width="9%"  scope="col">GDS Plan</th>
			<th width="9%"  scope="col">Data<br /> Sharing<br /> Exception</th>
			<th width="9%"  scope="col">IC</th>
			<th width="9%"  scope="col">BSI</th>
			<th width="9%"  scope="col">Repositories</th>
			<th width="5%"  scope="col">Actions</th>
		</tr>
        </thead>
    </table>

	<div id="repoModal" class="modal fade" role="dialog">
		<s:include value="/jsp/content/searchResultRepository.jsp"/>
	</div>
	
	<s:include value="/jsp/content/searchResultSubproject.jsp"/>
	
</s:if>
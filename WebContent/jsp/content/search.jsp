<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<s:form action="viewProject" name="viewProjectForm">
<div class="content">
	<div class="inside">
		<fieldset>
			<h2>Search Criteria</h2>
			<span class="search"><label for="number">Project Id:</label>			
				<s:select cssClass="searchInput" name="projectId" list="%{getAllProjectIds()}" id="projectId"  emptyOption="true"/>				
			</span>
			<br/>
			 <s:submit name="viewProject" action="viewProject" cssClass="searchButton" value="View"/>
			
			<br/>
		</fieldset>
	</div>
</div>
<br/><br/><br/><br/>
</s:form>
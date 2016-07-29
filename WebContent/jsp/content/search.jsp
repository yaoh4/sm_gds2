<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<s:form name="viewProjectForm">
	
		<fieldset>
			<h2>Project Search</h2>
			<span class="search"><label for="number">Project Id:</label>			
				<s:select cssClass="searchInput" name="projectId" list="%{getAllProjectIds()}" id="projectId"  emptyOption="true"/>				
			</span>
			<br/>
			 <s:submit name="viewProject" action="manage/navigateToGeneralInfo" cssClass="searchButton" value="View"/>
			 <s:submit name="viewProject" action="search/deleteProject" cssClass="searchButton" value="Delete"/>
			<br/>
		</fieldset>
	
<br/><br/><br/><br/>
</s:form>
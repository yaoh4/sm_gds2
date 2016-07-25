<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<s:form action="navigateToGeneralInfo" namespace="/manage" name="viewProjectForm">
	
		<fieldset>
			<h2>Project Search</h2>
			<span class="search"><label for="number">Project Id:</label>			
				<s:select cssClass="searchInput" name="projectId" list="%{getAllProjectIds()}" id="projectId"  emptyOption="true"/>				
			</span>
			<br/>
			 <s:submit name="viewProject" action="navigateToGeneralInfo" cssClass="searchButton" value="View"/>
			
			<br/>
		</fieldset>
	
<br/><br/><br/><br/>
</s:form>
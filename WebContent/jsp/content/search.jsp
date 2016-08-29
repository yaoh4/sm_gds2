<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<s:form id="searchForm" action="navigateToGeneralInfo" namespace="/manage" name="viewProjectForm">
	
		<fieldset>
			<h2>Project Search</h2>
			<span class="search"><label for="number">Project Id:</label>			
				<s:select cssClass="searchInput" name="projectId" list="%{getAllProjectIds()}" id="projectId"  emptyOption="true"/>				
			</span>
			<br/>
			 <s:submit id="viewProject" action="navigateToGeneralInfo" cssClass="searchButton" value="View"/>
			 <s:submit id="deleteProject" onclick="submitFormForDeletion()" cssClass="searchButton" value="Delete"/>
			<br/>
		</fieldset>
	
<br/><br/><br/><br/>
</s:form>

<script type="text/javascript">
     function submitFormForDeletion()
     { 
         var myForm = document.getElementById("searchForm");
         myForm.action="../search/deleteProject";
         myForm.submit();
     }
</script>
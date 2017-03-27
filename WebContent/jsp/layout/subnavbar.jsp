<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<tiles:useAttribute id="navtab" name="navtab" />
<tiles:useAttribute id="subnavtab" name="subnavtab" />

<!--Sub Navigation -->
<div class="navbar navbar-default2" >  
<div id="secNav"  class="row">
<div class="container">
	<div class="navbar-collapse main-nav subnav">
		<ul class="nav navbar-nav ">
			<s:if test="%{'newSubmission' eq #attr['navtab'] && project.id != null}">
			<s:hidden id="latestVersionFlag" value="%{project.latestVersionFlag}"/>
			  <s:if test="!isReadOnlyUser() && project.latestVersionFlag.equals(\"Y\")">
				<%-- We always show the general info if its a saved project --%>
				<s:if test="%{'generalInfo' eq #attr['subnavtab']}">
					<li class="active"><a href="javascript: void(0)">General Info.</a></li>
				</s:if>
				<s:else>
					<s:url namespace="/manage" var="generalInfoUrl" action="navigateToGeneralInfo">
						<s:param name="projectId" value="%{projectId}" />
					</s:url>
					<li><s:a href="%{generalInfoUrl}">General Info.</s:a></li>
				</s:else>
				<li class="divider-vertical"></li>
				
				<s:if test="%{showPage('gdsPlan')}">
				  <s:if test="%{'gdsPlan' eq #attr['subnavtab']}">
					<li class="active"><a href="javascript: void(0)">Genomic Data Sharing Plan</a></li>
				  </s:if>
				  <s:else>
					<s:url namespace="/manage" var="gdsPlanUrl" action="editGdsPlan">
						<s:param name="projectId" value="%{projectId}" />
					</s:url>
					<li><s:a href="%{gdsPlanUrl}">Genomic Data Sharing Plan</s:a></li>
				  </s:else>
				  <li class="divider-vertical"></li>
				</s:if>
				
				<%-- Show IC tab --%>
				<s:if test="%{showPage('ic')}">
					<s:if test="%{'ic' eq #attr['subnavtab']}">
						<li class="active"><a href="javascript: void(0)">Institutional Certification(s)</a></li>
					</s:if>
					<s:else>
						<s:url namespace="/manage" var="icUrl" action="navigateToIcMain">
							<s:param name="projectId" value="%{projectId}" />
						</s:url>
						<li><s:a href="%{icUrl}">Institutional Certification(s)</s:a></li>
					</s:else>
					<li class="divider-vertical"></li>
				</s:if>
				
				<s:if test="%{showPage('bsi')}">
					<s:if test="%{'bsi' eq #attr['subnavtab']}">
						<li class="active"><a href="javascript: void(0)">Basic Study Info.</a></li>
					</s:if>
					<s:else>
						<s:url namespace="/manage" var="bsiUrl" action="navigateToBasicStudyInfo">
							<s:param name="projectId" value="%{projectId}" />
						</s:url>
						<li><s:a href="%{bsiUrl}">Basic Study Info.</s:a></li>
					</s:else>
					<li class="divider-vertical"></li>
				</s:if>
				
				<%-- Show repository tab --%>
				<s:if test="%{showPage('repository')}">
					<s:if test="%{'repository' eq #attr['subnavtab']}">
						<li class="active"><a href="javascript: void(0)">Submission Status</a></li>
					</s:if>
					<s:else>
						<s:url namespace="/manage" var="submissionStatusUrl" action="navigateToRepositoryStatus">
							<s:param name="projectId" value="%{projectId}" />
						</s:url>
						<li><s:a href="%{submissionStatusUrl}">Submission Status</s:a></li>
					</s:else>
					<li class="divider-vertical"></li>
				</s:if>
			  </s:if>	
				<%-- We always show the detail page if its a saved project --%>
				<s:if test="%{'detail' eq #attr['subnavtab']}">
					<li class="active"><a href="javascript: void(0)">Submission Details</a></li>
				</s:if>
				<s:else>
					<s:url namespace="/manage" var="submissionDetailUrl" action="navigateToSubmissionDetail">
						<s:param name="projectId" value="%{projectId}" />
					</s:url>
					<li><s:a href="%{submissionDetailUrl}">Submission Details</s:a></li>
				</s:else>
				<li class="divider-vertical"></li>
							 
			</s:if>
			
			<!-- -------Admin tab -->
			<s:if test="%{'admin' eq #attr['navtab']}">
				<s:if test="%{'searchAdmin' eq #attr['subnavtab']}">
					<li class="active"><a href="javascript: void(0)">Manage User Accounts</a></li>
				</s:if>
				<s:else>
					<s:url namespace="/admin" var="searchAdminUrl" action="navigateToAdminSearch"></s:url>
					<li><s:a href="%{searchAdminUrl}">Manage User Accounts</s:a></li>
				</s:else>
			 </s:if>
		</ul>
	</div>
</div>
</div>
<s:if test="%{'newSubmission' eq #attr['navtab'] && project.id != null}">
	<s:if test="%{savedCriteria != null}">
		<div class="container"><div style="margin-top: 15px;" class="col-md-12">
			<a href="../search/returnToSearch.action">Return to Find Submissions</a>
		</div></div>
	</s:if>
</s:if>
</div>
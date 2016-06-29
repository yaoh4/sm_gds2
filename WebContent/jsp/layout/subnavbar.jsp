<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<tiles:useAttribute id="navtab" name="navtab" />
<tiles:useAttribute id="subnavtab" name="subnavtab" />

<!--Sub Navigation -->
<div class="container">
	<div class="navbar-collapse main-nav subnav">
		<ul class="nav navbar-nav ">
			<s:if test="%{'newSubmission' eq #attr['navtab'] && project.id != null}">
			
				<%-- We always show the general info if its a saved project --%>
				<s:if test="%{'generalInfo' eq #attr['subnavtab']}">
					<li class="active"><s:a href="javascript: void(0)">General Info.</s:a></li>
				</s:if>
				<s:else>
					<s:url namespace="/manage" var="generalInfoUrl" action="navigateToGeneralInfo">
						<s:param name="projectId" value="%{projectId}" />
					</s:url>
					<li><s:a href="%{generalInfoUrl}">General Info.</s:a></li>
				</s:else>
				<li class="divider-vertical"></li>
				
				<%-- We always show the gds plan page if its a saved project --%>
				<s:if test="%{'gdsPlan' eq #attr['subnavtab']}">
					<li class="active"><s:a href="javascript: void(0)">Genomic Data Sharing Plan</s:a></li>
				</s:if>
				<s:else>
					<s:url namespace="/manage" var="gdsPlanUrl" action="editGdsPlan">
						<s:param name="projectId" value="%{projectId}" />
					</s:url>
					<li><s:a href="%{gdsPlanUrl}">Genomic Data Sharing Plan</s:a></li>
				</s:else>
				<li class="divider-vertical"></li>
				
				<%-- Show IC tab --%>
				<s:if test="%{showPage('ic')}">
					<s:if test="%{'ic' eq #attr['subnavtab']}">
						<li class="active"><s:a href="javascript: void(0)">Institutional Certification(s)</s:a></li>
					</s:if>
					<s:else>
						<s:url namespace="/manage" var="icUrl" action="editIc">
							<s:param name="projectId" value="%{projectId}" />
						</s:url>
						<li><s:a href="%{icUrl}">Institutional Certification(s)</s:a></li>
					</s:else>
					<li class="divider-vertical"></li>
				</s:if>
				
				<s:if test="%{showPage('bsi')}">
					<s:if test="%{'bsi' eq #attr['subnavtab']}">
						<li class="active"><s:a href="javascript: void(0)">Basic Study Info.</s:a></li>
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
						<li class="active"><s:a href="javascript: void(0)">Submission Status</s:a></li>
					</s:if>
					<s:else>
						<s:url namespace="/manage" var="submissionStatusUrl" action="navigateToRepositoryStatus">
							<s:param name="projectId" value="%{projectId}" />
						</s:url>
						<li><s:a href="%{submissionStatusUrl}">Submission Status</s:a></li>
					</s:else>
					<li class="divider-vertical"></li>
				</s:if>
				
				<%-- We always show the detail page if its a saved project --%>
				<!-- Commenting out for this Sprint
				<s:if test="%{'detail' eq #attr['subnavtab']}">
					<li class="active"><s:a href="javascript: void(0)">Submission Details</s:a></li>
				</s:if>
				<s:else>
					<s:url namespace="/manage" var="submissionStatusUrl" action="navigateToSubmissionDetail">
						<s:param name="projectId" value="%{projectId}" />
					</s:url>
					<li><s:a href="%{submissionStatusUrl}">Submission Details</s:a></li>
				</s:else>
				<li class="divider-vertical"></li>
				 -->
			</s:if>
		</ul>
	</div>
</div>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<tiles:useAttribute id="navtab" name="navtab" />

<!--Main Navigation -->

<div id="primNav" class="row">
	<div  class="container">
		<nav>
			<div id="mainNav" class="navbar-header" style="display:block;">
				<ul class="nav navbar-nav navbar-main">
				<s:if test="!isReadOnlyUser()">
				<s:if test="%{'newSubmission' eq #attr['navtab']}">
				<s:if test="%{project == null || project.id == null}">
					<li class="active"><s:a href="javascript: void(0)">
							Create New Submission
						</s:a></li>
				</s:if>
				<s:else>
					<li><s:a href="/gds/manage/newSubmission.action">
						Create New Submission
					</s:a></li>
				</s:else>
				</s:if>
				<s:else>
					<li><s:a href="/gds/manage/newSubmission.action">
							Create New Submission
						</s:a></li>
				</s:else>	
				</s:if>	
				<s:if test="%{'search' eq #attr['navtab']}">
					<li class="active"><s:a href="javascript: void(0)">
							Find Submissions
						</s:a></li>
				</s:if>
				<s:else>
					<li><s:a href="/gds/search/home.action">
							Find Submissions
						</s:a></li>
				</s:else>
				<s:if test="isGPA()">
				<s:if test="%{'admin' eq #attr['navtab']}">
					<li class="active"><s:a href="javascript: void(0)">
							Administration
						</s:a></li>
				</s:if>
				<s:else>
					<li><s:a href="/gds/admin/navigateToAdminSearch.action">
							Administration
						</s:a></li>
				</s:else> 
				</s:if>
				</ul>
			</div>
		</nav>
	</div>
</div>
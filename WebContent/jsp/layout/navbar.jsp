<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<tiles:useAttribute id="navtab" name="navtab" />

<!--Main Navigation -->

<div id="navbar" class="navbar-header navbar-default"></div>
<div id="mainNav" class="navbar" style="display: block;">
	<ul class="nav navbar-nav navbar-main">
		<s:if test="%{'home' eq #attr['navtab']}">
			<li><s:a href="javascript: void(0)" cssClass="selected">
					Home
				</s:a></li>
		</s:if>
		<s:else>
			<li><s:a href="/gds/search/home.action">
					Home
				</s:a></li>
		</s:else>
		<s:if test="%{'newSubmission' eq #attr['navtab']}">
			<li><s:a href="javascript: void(0)" cssClass="selected">
					Create New Submission
				</s:a></li>
		</s:if>
		<s:else>
			<li><s:a href="/gds/manage/newSubmission.action">
					Create New Submission
				</s:a></li>
		</s:else>		
		<s:if test="%{'search' eq #attr['navtab']}">
			<li><s:a href="javascript: void(0)" cssClass="selected">
					Search
				</s:a></li>
		</s:if>
		<s:else>
			<li><s:a href="/gds/search/home.action">
					Search
				</s:a></li>
		</s:else>
	</ul>
</div>
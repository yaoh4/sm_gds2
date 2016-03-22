<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<tiles:useAttribute id="navtab" name="navtab" />

<div id="navbar">
	<ul>
		<s:if test="%{'home' eq #attr['navtab']}">
			<li><s:a href="javascript: void(0)" cssClass="selected">
					<span>Search</span>
				</s:a></li>
		</s:if>
		<s:else>
			<li><s:a href="/gds/search/home.action">
					<span>Search</span>
				</s:a></li>
		</s:else>
		<s:if test="%{'newSubmission' eq #attr['navtab']}">
			<li><s:a href="javascript: void(0)" cssClass="selected">
					<span>New Submission</span>
				</s:a></li>
		</s:if>
		<s:else>
			<li><s:a href="/gds/manage/newSubmission.action">
					<span>New Submission</span>
				</s:a></li>
		</s:else>		
	</ul>
</div>
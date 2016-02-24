<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:url namespace="/" id="home" action="home" />
<s:url namespace="/" id="newSubmission" action="addNewSubmission" />

<div id="navbar">
	<ul>
		<s:if test="%{'home' eq #attr['navtab']}">
			<li><s:a href="%{home}" cssClass="selected">
					<span>Search</span>
				</s:a></li>
		</s:if>
		<s:else>
			<li><s:a href="%{home}">
					<span>Search</span>
				</s:a></li>
		</s:else>
		<s:if test="%{'newSubmission' eq #attr['navtab']}">
			<li><s:a href="%{newSubmission}" cssClass="selected">
					<span>New Submission</span>
				</s:a></li>
		</s:if>
		<s:else>
			<li><s:a href="%{newSubmission}">
					<span>New Submission</span>
				</s:a></li>
		</s:else>		
	</ul>
</div>
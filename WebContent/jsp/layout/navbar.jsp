<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:url namespace="/" id="gdsHome" action="GdsHome" />
<s:url namespace="/" id="newSubmission" action="AddNewSubmission" />

<div id="navbar">
	<ul>
		<s:if test="%{'home' eq #attr['navtab']}">
			<li><s:a href="%{gdsHome}" cssClass="selected">
					<span>Search</span>
				</s:a></li>
		</s:if>
		<s:else>
			<li><s:a href="%{gdsHome}">
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
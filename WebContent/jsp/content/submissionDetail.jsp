<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<div class="content">
	<div class="inside">
		<fieldset>
			<b>Submission Details</b>
			<s:include value="/jsp/content/submissionGeneralInfoView.jsp"/>
			<s:include value="/jsp/content/submissionGdsPlanView.jsp"/>
			<s:include value="/jsp/content/submissionIcListView.jsp"/>
			<s:include value="/jsp/content/submissionBasicStudyInfoView.jsp"/>
		</fieldset>
	</div>
</div>

<br /><br /><br /><br />
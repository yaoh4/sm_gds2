<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>




<s:form id="submission-details_form"  namespace="manage"
    enctype="multipart/form-data" action="submissionDetails" method="post" role="form">  

			<s:include value="/jsp/content/submissionGeneralInfoView.jsp"/>
			<s:include value="/jsp/content/submissionGdsPlanView.jsp"/>
			<s:include value="/jsp/content/submissionIcListView.jsp"/>
			<s:include value="/jsp/content/submissionBasicStudyInfoView.jsp"/>
			<s:include value="/jsp/content/submissionStatusView.jsp"/>

</s:form>		


<script src="<s:url value="/controllers/gds.js" />"></script>
<script src="<s:url value="/controllers/grantSearch.js" />"></script>
        
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<!-- Begin Panel -->
	<div class="col-md-12">
		<div class="panel  project-panel-primary">
			<div class="panel-heading">
				<div class="pheader">
					<h4>Basic Study Information</h4>
				</div>
				</div>

			<div class="panel-body">

<s:form id="submission-details_form"  namespace="manage"
    enctype="multipart/form-data" action="submissionDetails" method="post" role="form">  

			<s:include value="/jsp/content/submissionGeneralInfoView.jsp"/>
			<s:include value="/jsp/content/submissionGdsPlanView.jsp"/>
			<s:include value="/jsp/content/submissionIcListView.jsp"/>
			<s:include value="/jsp/content/submissionBasicStudyInfoView.jsp"/>
			<s:include value="/jsp/content/submissionStatusView.jsp"/>

</s:form>		
</div> <!--end panel body-->
</div> <!--end panel -->

<script src="<s:url value="/controllers/gds.js" />"></script>
<script src="<s:url value="/controllers/grantSearch.js" />"></script>
        
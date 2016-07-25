<%@ taglib uri="/struts-tags" prefix="s"%>
<s:if test="%{!actionErrors.isEmpty or !fieldErrors.isEmpty}">
	<div class="container">
		<div class="col-md-12">
			<div class="alert alert-danger">
			 	<h3><i class="fa fa-exclamation-triangle" aria-hidden="true"></i>&nbsp;Error Status</h3>
				<s:actionerror />
				<s:fielderror />
			</div>
		</div>
	</div>
</s:if>

<s:if test="%{ !actionMessages.isEmpty }">
	<div class="container">
		<div class="col-md-12">
			<div class="alert alert-success">
				<h3><i class="fa fa-check-square" aria-hidden="true"></i>&nbsp;Success Status</h3>
				<s:actionmessage escape="false" />
			</div>
		</div>
	</div>
</s:if>

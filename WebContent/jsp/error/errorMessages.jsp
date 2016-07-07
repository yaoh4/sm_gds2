<%@ taglib uri="/struts-tags" prefix="s"%>
<s:if test="%{!actionErrors.isEmpty or !fieldErrors.isEmpty}">
	<div class="container">
		<div class="container">
			<div class="col-md-12">
				<div class="alert alert-danger">
					<s:actionerror />
					<s:fielderror />
				</div>
			</div>
		</div>
	</div>

</s:if>
<s:if test="%{ !actionMessages.isEmpty }">
	<div class="container">
		<div class="container">
			<div class="col-md-12">
				<div class="alert alert-success">
					<s:actionmessage escape="false" />
				</div>
			</div>
		</div>
	</div>

</s:if>

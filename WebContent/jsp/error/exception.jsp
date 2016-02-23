<%@ page isErrorPage="true"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<div class="content">
	<div class="inside">
		<s:form>
			<fieldset>
				<legend>Application Error Notification</legend>
				<p>
					<strong> An unexpected error has occurred in the GDS Tracking System. Please provide the details of the action you took when the
						error occurred. </strong>
				</p>
				<br />
				<fieldset>
					<legend>Error Description</legend>
					<s:actionerror />
					<p>
						<strong><s:property value="%{exception.message}" /></strong>
						<s:hidden name="exceptionStack" value="%{exceptionStack}" />
					</p>
				</fieldset>
				<fieldset>
					<p>Notify Application Support of the error by clicking on the
						&quot;Send Details&quot; button below</p>
					<br />
					<s:textarea cols="90" rows="10" name="message"
						cssStyle="width: 665px;" />
					<br /> <br />
					<s:submit value="Send Details" action="SendErrorMessage" />
				</fieldset>
				<br>
				<s:if
					test="!(environment.startsWith('Prod') || environment.startsWith('prod'))">
					<fieldset>
						<legend>Technical Details</legend>
						<p>
							<s:property value="%{exceptionStack}" />
						</p>
					</fieldset>
				</s:if>
			</fieldset>
		</s:form>
	</div>
</div>
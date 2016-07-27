<%@ taglib uri="/struts-tags" prefix="s"%>


	<!--Begin Form -->
	<s:form id="createNewSubmission" name="createNewSubmissionForm"
		action="createNewSubmission.action" method="post"
		data-toggle="validator" role="form">
		<!-- Page navbar -->

		<div class="pageNav">
			<s:submit type="button" 
				action="manage/saveAndNextSubmissionType" cssClass="btn btn-project-primary">
				Next &nbsp;&nbsp;<i class="fa fa-caret-right" style="color:#ffffff;"></i></s:submit>
		</div>


		<!-- Begin Panel -->
		<div class="col-md-12">
			<div class="panel  project-panel-primary">
				<div class="panel-heading">
					<div class="pheader">
						<h4>Submission Type&nbsp;</h4>
					</div>
				</div>
				<p style="font-size: 12px; margin-left: 15px; margin-top: 5px;">
					Note: <i class="fa fa-asterisk" aria-hidden="true"></i>&nbsp;Asterisk
					indicates a required field
				</p>

				<div class="panel-body">
					<div>
						<p class="question"><i class="fa fa-asterisk" aria-hidden="true"></i>&nbsp; What would you like to create?</p>
						<s:radio id="projectTypesId"
								name="selectedTypeOfProject"
								list="projectTypes" template="radiomap-div.ftl"
								listKey="optionKey" listValue="optionValue"/>						
					</div>
					<p>&nbsp;</p>
				</div>
			</div>
			<!--end panel body-->
		</div>
		<!--end panel-->

		<!--SAVE & NEXT BUTTONS-->
		<div class="pageNav">
			<s:submit type="button" 
				action="manage/saveAndNextSubmissionType" cssClass="btn btn-project-primary">
				Next &nbsp;&nbsp;<i class="fa fa-caret-right" style="color:#ffffff;"></i></s:submit>
		</div>

	</s:form>


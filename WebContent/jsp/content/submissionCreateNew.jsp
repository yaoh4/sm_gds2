<%@ taglib uri="/struts-tags" prefix="s"%>

<div class="container">
	<!--Begin Form -->
	<s:form id="createNewSubmission" name="createNewSubmissionForm"
		action="createNewSubmission.action" method="post"
		data-toggle="validator" role="form">
		<!-- Page navbar -->

		<div class="pageNav">
			<s:submit value="Next" action="manage/navigateToGeneralInfo"
				cssClass="btn btn-project-primary" />
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
						<div style="color: #686868;" class="radio form-group has-feedback">
							<s:radio id="projectTypesId"
								name="selectedTypeOfProject"
								list="projectTypes" template="radiomap-br.ftl"
								listKey="optionKey" listValue="optionValue" value="34"/>
						</div>
					</div>
					<p>&nbsp;</p>
				</div>
			</div>
			<!--end panel body-->
		</div>
		<!--end panel-->

		<!--SAVE & NEXT BUTTONS-->
		<div class="pageNav">
			<s:submit value="Next" action="manage/navigateToGeneralInfo"
				cssClass="btn btn-project-primary" />
		</div>

	</s:form>

</div>

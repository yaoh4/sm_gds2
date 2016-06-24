<%@ taglib uri="/struts-tags" prefix="s"%>

<div class="container">
	<!--Begin Form -->
	<s:form id="general_form" name="general_form" action="viewProject.action" method="post" data-toggle="validator" role="form">
		<!-- Page navbar -->
		<s:hidden name="projectId" value="%{project.id}"/>
		<div class="pageNav">
			<s:submit value=" Save " action="manage/saveGeneralInfo"
				id="general_saved" cssClass="saved btn btn-default" />

			<s:submit value="Save & Next"
				action="manage/saveGeneralInfoAndNext" id="general_saved_next"
				cssClass="btn btn-project-primary" />
		</div>


		<!-- Begin Panel -->
		<div class="col-md-12">
			<div class="panel  project-panel-primary">
				<div class="panel-heading">
					<div class="pheader">
						<h4>General Information&nbsp;</h4>
					</div>
				</div>
				<p style="font-size: 12px; margin-left: 15px; margin-top: 5px;">
					Note: <i class="fa fa-asterisk" aria-hidden="true"></i>&nbsp;Asterisk
					indicates a required field
				</p>

				<div class="panel-body">

					<div>
						<p class="question"><i class="fa fa-asterisk" aria-hidden="true"></i>&nbsp;Why is the project being submitted?</p>
						<div style="color: #686868;" class="radio form-group has-feedback">
							<s:radio id="submissionReasonId" name="project.submissionReasonId" list="projectSubmissionReasons" template="radiomap-br.ftl"
								listKey="optionKey" listValue="optionValue" />
						</div>
					</div>
					<p>&nbsp;</p>

					<div class="form-group row has-feedback">
						<div class="col-xs-5">
							<label for="Division/Office/Center"><i
								class="fa fa-asterisk" aria-hidden="true"></i>&nbsp;
								Division/Office/Center</label> 
								<s:select id="DOC" cssClass="c-select form-control" name="project.docAbbreviation" list="docList" listKey="optionKey" listValue="optionValue"/>								
						</div>
					</div>
					
					<div class="form-group row has-feedback">
						<div class="col-xs-5">
							<label for="Program Branch"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Program
								Branch</label> 
								<s:textfield name="project.programBranch" cssClass="form-control" id="programBranch" placeholder="Enter Full Branch Name" value="%{project.programBranch}"/>
						</div>
					</div>

					<div class="form-group row">
						<div class="col-xs-5">
							<label for="Intramural (Z01)/Grant/Contract #">Intramural
								(Z01)/Grant/Contract #</label>
							<div class="input-group2">
							
							<s:textfield name="project.applicationNum" cssClass="form-control" disabled="disabled" id="grantsContractNum" placeholder="Click on Search Icon to Find # " value="%{project.applicationNum}"/>
							<span	class="input-group-btn"><a href="openSearchGrantsContracts.action"
									class="js-newWindow"
									data-popup="width=800,height=800,scrollbars=yes">
										<button class="btn btn-default" type="button">
											<i class="fa fa-search" aria-hidden="true"></i>
										</button>
								</a></span>
							</div>
						</div>
					</div>

					<div class="form-group row has-feedback">
						<div class="col-xs-10">
							<label for="Project Title"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Project
								Title</label> 
								<s:textfield name="project.projectTitle" cssClass="form-control" id="projectTitle" placeholder="" value="%{project.projectTitle}"/>
						</div>
					</div>

					<div class="row">
						<div class="form-group col-xs-5 has-feedback">
							<label for="First Name of Principal Investigator"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>First
								Name of Principal Investigator</label> 
								<s:textfield name="project.piFirstName" cssClass="form-control" id="fnPI" placeholder="" value="%{project.piFirstName}"/>
						</div>
						<div class="form-group col-xs-5 has-feedback">
							<label for="Last Name of Principal Investigator"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Last
								Name of Principal Investigator</label>
								<s:textfield name="project.piLastName" cssClass="form-control" id="lnPI" placeholder="" value="%{project.piLastName}"/>								
						</div>
					</div>

					<div class="form-group row has-feedback">
						<div class="col-xs-5 ">
							<label for="Email of Principal Investigator"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Email
								of Principal Investigator</label>
								<s:textfield name="project.piEmailAddress" cssClass="form-control" id="piEmail" placeholder="Enter Vaild Email Address"
								data-error="Email address is invalid" value="%{project.piEmailAddress}"/>								
						</div>
						<div class="help-block with-errors" style="margin-left: 15px"></div>
					</div>

					<div class="form-group row has-feedback">
						<div class="col-xs-5 ">
							<label for="Institution of Principal Investigator"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Institution
								of Principal Investigator</label>
								<s:textfield name="project.piInstitution" cssClass="form-control" id="PIInstitute" placeholder="" value="%{project.piInstitution}"/>								
						</div>
					</div>

					<div class="row">
						<div class="form-group col-xs-5 has-feedback">
							<label for="First Name of Principal Investigator">First
								Name of Primary Contact</label> 
								<s:textfield name="project.pocFirstName" cssClass="form-control" id="fnPC" placeholder="Required if No Principal Investigator" value="%{project.pocFirstName}"/>								
						</div>
						<div class="form-group col-xs-5 has-feedback">
							<label for="Last Name of Primary Contact">Last Name of
								Primary Contact</label> 
								<s:textfield name="project.pocLastName" cssClass="form-control" id="lnPC" placeholder="Required if No Principal Investigator" value="%{project.pocLastName}"/>								
						</div>
					</div>

					<div class="form-group row has-feedback">
						<div class="col-xs-5">
							<label for="Email of Principal Investigator">Email of
								Primary Contact</label>
						<s:textfield name="project.pocEmailAddress" cssClass="form-control" id="PCemail" placeholder="Enter Vaild Email Address" data-error="Email address is invalid" value="%{project.pocEmailAddress}"/>								
						</div>
						<div class="help-block with-errors" style="margin-left: 15px"></div>
					</div>


					<div class="row pdirector ">
						<div class="form-group col-xs-5 has-feedback">
							<label for="First Name of Program Director"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp; </i>First
								Name of Program Director</label>
								<s:textfield name="project.pdFirstName" cssClass="form-control" id="fnPD" placeholder="" value="%{project.pdFirstName}"/>								
						</div>
						<div class="form-group col-xs-5 has-feedback">
							<label for="Last Name of Program Director"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp; </i>Last
								Name of Program Director</label>
								<s:textfield name="project.pdLastName" cssClass="form-control" id="lnPD" placeholder="" value="%{project.pdLastName}"/>								
						</div>
					</div>
				</div>

				<div class="row pdates">
					<div id="pStartDate"
						class="form-group col-xs-2 projectDates has-feedback">
						<label for="Project Start Date"><i
							class="fa fa-asterisk eAsterisk" aria-hidden="true">&nbsp;</i>Project
							Start Date</label>
						<div class="input-group date">
						
						<s:textfield name="project.projectStartDate" cssClass="form-control" id="projectStartDate" value="%{project.projectStartDate}"/>	
						<span
								class="input-group-addon"><i
								class="glyphicon glyphicon-th"></i></span>

						</div>
					</div>

					<div id="pEndDate"
						class="form-group col-xs-2 projectDates has-feedback">
						<label for="Project End Date"><i
							class="fa fa-asterisk eAsterisk" aria-hidden="true">&nbsp;</i>Project
							End Date</label>
						<div class="input-group date">
						<s:textfield name="project.projectEndDate" cssClass="form-control" id="projectEndDate" value="%{project.projectEndDate}"/>	
							<span
								class="input-group-addon"><i
								class="glyphicon glyphicon-th"></i></span>

						</div>
					</div>
				</div>
			</div>
			<!--end panel body-->
		</div>
		<!--end panel-->



		<!--SAVE & NEXT BUTTONS-->
		<div class="pageNav">
			<s:submit value=" Save " namespace="/manage" action="saveGeneralInfo"
				id="general_saved" cssClass="saved btn btn-default" />

			<s:submit value="Save & Next" namespace="/manage"
				action="saveGeneralInfoAndNext" id="general_saved_next"
				cssClass="btn btn-project-primary" />
		</div>

	</s:form>

</div>
<!-- /container -->


<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>  
<script type="text/javascript" src="<s:url value="/controllers/general.js"/>"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.1/js/bootstrap-datepicker.js"></script>
<script type="text/javascript">
     $(function () {
          $('#repositoryDate .input-group.date').datepicker({
          orientation: "bottom auto",
          todayHighlight: true
         });
     });
</script> 

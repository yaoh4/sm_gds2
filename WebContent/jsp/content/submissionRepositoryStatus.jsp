<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<div class="container">
	<div class="pageNav">	
			<s:submit value=" Save " action="manage/saveRepositoryStatus" cssClass="saved btn btn-default" />								
			<s:submit value=" « Previous  " action="manage/navigateToBasicStudyInfo" cssClass="btn btn-default" />
			<s:submit value=" Save & Next »  " action="manage/saveRepositoryStatusAndNext" cssClass="btn btn-project-primary" />
	</div>
	
	<s:form name="submission_status_form" id="submission_status_form" action="navigateToRepositoryStatus" cssClass="form-horizontal">
		<!-- Begin Panel -->
		<div class="col-md-12">
			<div class="panel  project-panel-primary">
				<div class="panel-heading">
					<div class="pheader">
						<h4>Submission Status&nbsp;</h4>
					</div>
				</div>
				<div class="panel-body">
					<p>
						You have indicated the following [<strong><s:property value="%{project.repositoryStatuses.size}"/></strong>] data
						repositories for your project. Please complete the submission
						details for each repository.
					</p>
					<br />
					<s:iterator value="project.repositoryStatuses" var="repositoryStatus" status="stat">
					<!-- Begin Panel for each Repository -->
					<s:div class="panel panel-default" id="%{'repositoryStatus_' + #stat.index}">
						<div class="panel-heading">
							<div class="pheader">
								<h5><s:property value="%{#repositoryStatus.planQuestionAnswerTByRepositoryId.displayText}"/></h5>
							</div>

						</div>
						<div class="panel-body">						
							<div class="form-group row">
								<div class="col-xs-2">
									<br /> <s:label for="%{'regStatus_' + #stat.index}">Registration
										Status</s:label> 
										<s:select id="%{'regStatus_' + #stat.index}" name="project.repositoryStatuses[%{#stat.index}].lookupTByRegistrationStatusId.id"
												value="%{#repositoryStatus.lookupTByRegistrationStatusId.id}" cssClass="c-select form-control"  list="registrationStatusList" listKey="optionKey" listValue="optionValue"/>											
								</div>

								<div class="col-xs-2">
									<s:label for="%{'projStatus_' + #stat.index}">Project<br />
										Submission Status
									</s:label> 
									<s:select id="%{'projStatus_' + #stat.index}" name="project.repositoryStatuses[%{#stat.index}].lookupTByDataSubmissionStatusId.id"
												value="%{#repositoryStatus.lookupTByDataSubmissionStatusId.id}" cssClass="c-select form-control" disabled="true" list="projectSubmissionStatusList" listKey="optionKey" listValue="optionValue"/>											
								</div>

								<div id="repositoryDate" class="col-xs-2">
									<s:label for="%{'anticpated_submission_date_' + #stat.index}">Anticipated<br />
										Submission Date
									</s:label>
									<div class="input-group date">
										<s:textfield id="%{'anticpated_submission_date_' + #stat.index}" name="project.repositoryStatuses[%{#stat.index}].anticipatedSubmissionDate" value="%{#repositoryStatus.anticipatedSubmissionDate}" cssClass="form-control" />	
										<span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
								</div>

								<div class="col-xs-2">
									<br /> <s:label for="%{'studyRel_' + #stat.index}">Study Released?</s:label>
									<s:select id="%{'studyRel_' + #stat.index}" name="project.repositoryStatuses[%{#stat.index}].lookupTByStudyReleasedId.id"
												value="%{#repositoryStatus.lookupTByStudyReleasedId.id}"  cssClass="c-select form-control" disabled="true" list="studyReleasedList" listKey="optionKey" listValue="optionValue"/>											
								</div>

								<div class="col-xs-3">
									<br /> <s:label for="%{'accessionNumber_' + #stat.index}">Accession Number</s:label>
									<s:textfield id="%{'accessionNumber_' + #stat.index}" name="project.repositoryStatuses[%{#stat.index}].accessionNumber" value="%{#repositoryStatus.accessionNumber}" cssClass="form-control" maxLength="30"/>										
								</div>

							</div>

							<div class="form-group row">
								<div class="col-xs-12">
									<s:label for="%{'repositoryComments_' + #stat.index}">Comments (2000 Characters):</s:label>
									<s:textarea id="%{'repositoryComments_' + #stat.index}" name="project.repositoryStatuses[%{#stat.index}].accessionNumber" value="%{#repositoryStatus.accessionNumber}" cssClass="form-control input" rows="3"></s:textarea>
								</div>
							</div>					
						</div>						
					</s:div><!--end listed repository -->
					</s:iterator>
				</div>
			</div>
		</div>
		<!--end main panel-->
		
		<!--SAVE & NEXT BUTTONS-->
		<div class="pageNav">		
			<s:submit value=" Save " action="manage/saveRepositoryStatus" cssClass="saved btn btn-default" />								
			<s:submit value=" « Previous  " action="manage/navigateToBasicStudyInfo" cssClass="btn btn-default" />
			<s:submit value=" Save & Next »  " action="manage/saveRepositoryStatusAndNext" cssClass="btn btn-project-primary" />
		</div>
	</s:form>
</div>
<!-- /container -->
<script type="text/javascript" src="<s:url value="/controllers/submissionStatus.js" />"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.1/js/bootstrap-datepicker.js"></script> 
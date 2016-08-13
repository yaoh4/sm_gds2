<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<s:form name="submission_status_form" id="submission_status_form" action="navigateToRepositoryStatus" cssClass="form-horizontal dirty-check">
	<div class="pageNav">	
			<s:submit value=" Save " action="manage/saveRepositoryStatus" cssClass="saved btn btn-default" />								
			<s:submit type="button" action="manage/saveRepositoryStatusAndNext" cssClass="btn btn-project-primary" >
			Save &amp; Next &nbsp;&nbsp;<i class="fa fa-caret-right" style="color:#ffffff;"></i></s:submit>
	</div>	
		<s:hidden name="projectId" value="%{project.id}"/>
		<s:hidden id ="isDbGap" name="isDbGap" value="%{isDbGap}"/>	
		
		<s:set name="isAnticipatedSubDateDisabled" value="%{isAnticipatedSubDateDisabled()}" />
		<!-- Begin Panel -->
		<div class="col-md-12">
			<div class="panel  project-panel-primary">
				<div class="panel-heading">
					<div class="pheader">
						<h4>Submission Status&nbsp;</h4>
						 <s:include value="/jsp/content/pageStatus.jsp"/>
					</div>
				</div>
				<div class="panel-body">
					<p>
						You have indicated the following [<strong><s:property value="%{project.repositoryStatuses.size}"/></strong>] data
						repositories for your project. Please complete the submission
						details for each repository.
					</p>
					<br />
					<div id="repositoryDate" style="width:200px;">
						<s:label for="anticpated_submission_date" value="Anticipated Submission Date" />
						<div class="input-group date"> <s:textfield id="%{'anticpated_submission_date'}" name="project.anticipatedSubmissionDate"	value="%{anticipatedSubmissionDate}" disabled="isAnticipatedSubDateDisabled"
								cssClass="form-control" />
							<span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
						</div>
					</div>
					<br />
					<s:iterator value="project.repositoryStatuses" var="repositoryStatus" status="stat">
					<!-- Begin Panel for each Repository -->
					<s:div class="panel panel-default" id="%{'repositoryStatus_' + #stat.index}">
						<div class="panel-heading">
							<div class="pheader">
								<h5>
								<s:if test="%{#repositoryStatus.planAnswerSelectionTByRepositoryId.otherText != null}" >
									<s:property value="%{#repositoryStatus.planAnswerSelectionTByRepositoryId.planQuestionsAnswer.displayText}"/> - <s:property value="%{#repositoryStatus.planAnswerSelectionTByRepositoryId.otherText}"/>
								 </s:if>
								 <s:else>
								 	<s:property value="%{#repositoryStatus.planAnswerSelectionTByRepositoryId.planQuestionsAnswer.displayText}"/>
								 </s:else>
								</h5>					
							</div>

						</div> 
						<s:hidden name="project.repositoryStatuses[%{#stat.index}].planAnswerSelectionTByRepositoryId.id" value="%{#repositoryStatus.planAnswerSelectionTByRepositoryId.id}"/>
						<div class="panel-body">		 				
							<div class="form-group row">
								<div class="col-xs-2">
									<br /> <s:label for="%{'regStatus_' + #stat.index}" value="Registration Status" /> 
									<s:select id="%{'regStatus_' + #stat.index}" name="project.repositoryStatuses[%{#stat.index}].lookupTByRegistrationStatusId.id" onChange="enableDisableSubmissionStatus(this.id)" 
												value="%{#repositoryStatus.lookupTByRegistrationStatusId.id}" cssClass="c-select form-control"  list="registrationStatusList" listKey="optionKey" listValue="optionValue"/>											
								</div>

								<div class="col-xs-2">
									<s:label for="%{'projStatus_' + #stat.index}" value="Project Submission Status" /> 
									<s:select id="%{'projStatus_' + #stat.index}" name="project.repositoryStatuses[%{#stat.index}].lookupTBySubmissionStatusId.id" onChange="enableDisableStudyReleased(this.id)" 
												value="%{#repositoryStatus.lookupTBySubmissionStatusId.id}" cssClass="c-select form-control" disabled="true" list="projectSubmissionStatusList" listKey="optionKey" listValue="optionValue"/>											
								</div>							

								<div class="col-xs-2">
									<br /> <s:label for="%{'studyRel_' + #stat.index}" value="Study Released?" />
									<s:select id="%{'studyRel_' + #stat.index}" name="project.repositoryStatuses[%{#stat.index}].lookupTByStudyReleasedId.id" 
												value="%{#repositoryStatus.lookupTByStudyReleasedId.id}"  cssClass="c-select form-control" disabled="true" list="studyReleasedList" listKey="optionKey" listValue="optionValue"/>											
								</div>

								<div class="col-xs-3">
									<br /> <s:label for="%{'accessionNumber_' + #stat.index}" value="Accession Number" />
									<s:textfield id="%{'accessionNumber_' + #stat.index}" name="project.repositoryStatuses[%{#stat.index}].accessionNumber" value="%{#repositoryStatus.accessionNumber}" cssClass="form-control" maxLength="30"/>										
								</div>

							</div>

							<div class="form-group row">
								<div class="col-xs-12">
									<s:label for="%{'repositoryComments_' + #stat.index}" value="Comments (2000 Characters):" />
									<s:textarea id="%{'repositoryComments_' + #stat.index}" name="project.repositoryStatuses[%{#stat.index}].comments" value="%{#repositoryStatus.comments}" cssClass="form-control input" maxlength="2000" rows="3"></s:textarea>
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
			<s:submit type="button" action="manage/saveRepositoryStatusAndNext" cssClass="btn btn-project-primary" >
			Save &amp; Next &nbsp;&nbsp;<i class="fa fa-caret-right" style="color:#ffffff;"></i></s:submit>
		</div>
	</s:form>

<script type="text/javascript" src="<s:url value="/controllers/submissionStatus.js" />"></script>
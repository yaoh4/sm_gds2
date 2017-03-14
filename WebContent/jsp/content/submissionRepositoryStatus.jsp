<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<s:form name="submission_status_form" id="submission_status_form" action="navigateToRepositoryStatus" cssClass="form-horizontal dirty-check">
	<div class="pageNav">	
			<s:submit value=" Save " action="manage/saveRepositoryStatus" cssClass="saved btn btn-default" />								
			<s:submit type="button" action="manage/saveRepositoryStatusAndNext" cssClass="btn btn-project-primary" >
			Save &amp; Next &nbsp;&nbsp;<i class="fa fa-caret-right" style="color:#ffffff;"></i></s:submit>
	</div>	
		<s:hidden name="projectId" value="%{project.id}"/>
		<s:hidden name="project.subprojectFlag" id="subprojectFlag" value="%{project.subprojectFlag}"/>
		<s:hidden id ="isDbGap" name="isDbGap" value="%{isDbGap}"/>	
		<s:hidden id="dataSubmitted" name="dataSubmitted" value="%{dataSubmitted}"/>
		
		<s:set name="isAnticipatedSubDateDisabled" value="%{isAnticipatedSubDateDisabled()}" />
		<!-- Begin Panel -->
		<div class="col-md-12">
			<div class="panel  project-panel-primary">
				<div class="panel-heading">
					<div class="pheader">
						<h4>Submission Status&nbsp;</h4>
						<div class="statusWrapper">
						  <s:if test="%{!pageStatusCode.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PAGE_STATUS_CODE_COMPLETED)}">         		       	      
						    <div class="status">
						      <a href="#" onclick="openMissingDataReport(${project.id}, '/gds/manage/viewMissingRepositoryListData.action?')" class="statusLink">Generate Missing Data Report</a> &nbsp; &nbsp;
						    </div>
						  </s:if>
              			  <s:include value="/jsp/content/pageStatus.jsp"/>           	
            			</div>				
					</div>
				</div>
				<div class="panel-body">
				<s:if test="%{project.subprojectFlag.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@FLAG_YES)}">
					<div>
                 	 <span>You will be able to add/remove repositories only at the parent project level. Changes will then be reflected in this sub-project.</span>
                 	 <br/>
                </div> 
                </s:if>
                <s:if test="%{project.repositoryStatuses.size == 0 && project.subprojectFlag.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@FLAG_NO)}">
                  <s:if test="%{project.submissionReasonId.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@SUBMISSION_REASON_NONNIHFUND)}">
                    <span><strong>To track the Submission Status of the repositories for this submission, please select the applicable repositories on the Basic Study Information  page</strong></span>
                  </s:if>
                  <s:else>
                    <span><strong>To track the Submission Status of the repositories for this submission, please select the applicable repositories on the Genomic Data Sharing Plan page</strong></span>
                  </s:else>
                </s:if>
                <s:else>
                  <s:if test="%{project.subprojectFlag.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@FLAG_NO)}">
					<p>
						You have indicated the following [<strong><s:property value="%{project.repositoryStatuses.size}"/></strong>] data
						repositories for your project. Please complete the submission
						details for each repository.
					</p>
				  </s:if>
					<br />
					<div id="repositoryDate" style="width:205px;">
						<s:label for="anticpated_submission_date" value="Anticipated Submission Date" />
						&nbsp; <a href="#" class="hoverOver" data-toggle="tooltip" data-placement="right"  data-html="true"
						 style="font-size: 12px;"><s:hidden id="SUB_STATUS_ANTICIPATED_KEY" value="%{getHelpText('SUB_STATUS_ANTICIPATED_KEY')}"/> <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a>
						<div class="input-group date"> <s:textfield id="%{'anticpated_submission_date'}" name="project.anticipatedSubmissionDate"	value="%{anticipatedSubmissionDate}" disabled="isAnticipatedSubDateDisabled"
								cssClass="form-control" />
							<span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
						</div>
					</div>
					<br />
					<s:if test="%{project.repositoryStatuses.size > 0 && project.subprojectFlag.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@FLAG_YES)}">
				  	<p>
						<b>Select the repositories that apply to the sub-project submission.</b>
					</p>
					<br/>
				  </s:if>
					<s:iterator value="project.repositoryStatuses" var="repositoryStatus" status="stat">
					<!-- Begin Panel for each Repository -->
					<s:div class="panel panel-default repoCount" id="%{'repositoryStatus_' + #stat.index}">
						<div class="panel-heading">
							<div class="pheader">
								<s:if test="%{project.subprojectFlag.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@FLAG_YES)}">
									<s:checkbox cssClass="repoSelect" value="%{#repositoryStatus.selected}" name="project.repositoryStatuses[%{#stat.index}].selected" cssStyle="margin-right: 5px;" id="%{'selected_' + #stat.index}" />
								</s:if>
								<h5 class="adjustText">
								<s:if test="%{#repositoryStatus.planAnswerSelectionTByRepositoryId.otherText != null}" >
									<s:property value="%{#repositoryStatus.planAnswerSelectionTByRepositoryId.planQuestionsAnswer.displayText}"/> - <s:property value="%{#repositoryStatus.planAnswerSelectionTByRepositoryId.otherText}"/>
								 </s:if>
								 <s:else>
								 	<s:property value="%{#repositoryStatus.planAnswerSelectionTByRepositoryId.planQuestionsAnswer.displayText}"/>
								 </s:else>
								</h5>
								&nbsp; &nbsp;  <a href="#" class="hoverOver" data-toggle="tooltip" data-placement="right"  data-html="true"
						 style="font-size: 12px;"><s:hidden id="SUB_STATUS_REPOSITORY_KEY" value="%{getHelpText('SUB_STATUS_REPOSITORY_KEY')}"/> <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a>					
							</div>

						</div> 
						<s:hidden name="project.repositoryStatuses[%{#stat.index}].planAnswerSelectionTByRepositoryId.id" value="%{#repositoryStatus.planAnswerSelectionTByRepositoryId.id}"/>
						<s:hidden name="project.repositoryStatuses[%{#stat.index}].id" value="%{#repositoryStatus.id}"/>		
						<div class="panel-body">		 				
							<div class="form-group row">
								<div class="col-xs-2">
									<br /> <s:label for="%{'regStatus_' + #stat.index}" value="Registration Status" /> 
									&nbsp; &nbsp;  <a href="#" class="hoverOver" data-toggle="tooltip" data-placement="right"  data-html="true"
						 style="font-size: 12px;"><s:hidden id="SUB-STATUS_REGISTRATION_KEY" value="%{getHelpText('SUB-STATUS_REGISTRATION_KEY')}"/> <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a>
									<s:select id="%{'regStatus_' + #stat.index}" name="project.repositoryStatuses[%{#stat.index}].lookupTByRegistrationStatusId.id" onChange="processRegistrationStatusSelected(this.id)" 
												value="%{#repositoryStatus.lookupTByRegistrationStatusId.id}" cssClass="c-select form-control"  list="registrationStatusList" listKey="optionKey" listValue="optionValue"/>											
								</div>

								<div class="col-xs-3">
									<br/> <s:label for="%{'projStatus_' + #stat.index}" value="Data Submission Status" /> 
                                     &nbsp; &nbsp;  <a href="#" class="hoverOver" data-toggle="tooltip" data-placement="right"  data-html="true"
						 style="font-size: 12px;"><s:hidden id="SUB_STATUS_SUBMISSION_KEY" value="%{getHelpText('SUB_STATUS_SUBMISSION_KEY')}"/> <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a>
									

									<s:select id="%{'projStatus_' + #stat.index}" name="project.repositoryStatuses[%{#stat.index}].lookupTBySubmissionStatusId.id" onChange="processSubmissionStatusSelected(this.id)" 
												value="%{#repositoryStatus.lookupTBySubmissionStatusId.id}" cssClass="c-select form-control" disabled="true" list="projectSubmissionStatusList" listKey="optionKey" listValue="optionValue"/>											
								</div>							

								<div class="col-xs-2">
									<br /> <s:label for="%{'studyRel_' + #stat.index}" value="Study Released?" />
									&nbsp; &nbsp;  <a href="#" class="hoverOver" data-toggle="tooltip" data-placement="right"  data-html="true"
						 style="font-size: 12px;"><s:hidden id="SUB-STATUS_STUDY_KEY" value="%{getHelpText('SUB-STATUS_STUDY_KEY')}"/> <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a>
									<s:select id="%{'studyRel_' + #stat.index}" name="project.repositoryStatuses[%{#stat.index}].lookupTByStudyReleasedId.id" 
												value="%{#repositoryStatus.lookupTByStudyReleasedId.id}"  cssClass="c-select form-control" disabled="true" list="studyReleasedList" listKey="optionKey" listValue="optionValue"/>											
								</div>

								<div class="col-xs-3">
									<br /> <s:label for="%{'accessionNumber_' + #stat.index}" value="Accession Number" />
									&nbsp; &nbsp;  <a href="#" class="hoverOver" data-toggle="tooltip" data-placement="right"  data-html="true"
						 style="font-size: 12px;"><s:hidden id="SUB_STATUS_ACCESSION_KEY" value="%{getHelpText('SUB_STATUS_ACCESSION_KEY')}"/> <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a>
									<s:textfield id="%{'accessionNumber_' + #stat.index}" name="project.repositoryStatuses[%{#stat.index}].accessionNumber" value="%{#repositoryStatus.accessionNumber}" cssClass="form-control" maxLength="30"/>										
								</div>

							</div>

							<div class="form-group row">
								<div class="col-xs-12">
									<s:label for="%{'repositoryComments_' + #stat.index}" value="Comments (2000 Characters):" />
									<s:textarea id="%{'repositoryComments_' + #stat.index}" name="project.repositoryStatuses[%{#stat.index}].comments" onkeyup="countChar(this)" value="%{#repositoryStatus.comments}" cssClass="form-control input commentsClass" maxlength="2000" style="overflow-y: scroll;" rows="3"></s:textarea>
								     <s:div id="%{'countRepo_' + #stat.index}" style="text-align: right; font-style: italic;">
				                      <span style="color: #990000;">2000</span> Character limits
			                          </s:div>
								</div>
							</div>					
						</div>						
					</s:div><!--end listed repository -->
					</s:iterator>
				  </s:else>
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

<script type="text/javascript" src="<s:url value="/controllers/gds.js" />"></script>
<script type="text/javascript" src="<s:url value="/controllers/submissionStatus.js" />"></script>
<script type="text/javascript">
$(function($){
	$('[data-toggle="tooltip"]').tooltip({
	    container : 'body'
	  });
});
</script>
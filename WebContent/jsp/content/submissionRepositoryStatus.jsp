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
				<div style="display:none" id="showMessage">
                  <span>You will be able to add/remove repositories only at the parent project level. Changes will then be reflected in this sub-project.</span>
                  <br/><br/>
                </div> 
                <s:if test="%{project.repositoryStatuses.size == 0 && project.subprojectFlag.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@FLAG_NO)}">
                  <s:if test="%{project.submissionReasonId.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@SUBMISSION_REASON_NONNIHFUND)}">
                    <span><strong>To track the Submission Status of the repositories for this submission, please select the applicable repositories on the Basic Study Information  page</strong></span>
                  </s:if>
                  <s:else>
                    <span><strong>To track the Submission Status of the repositories for this submission, please select the applicable repositories on the Genomic Data Sharing Plan page</strong></span>
                  </s:else>
                </s:if>
                <s:else>
					<p>
						You have indicated the following [<strong><s:property value="%{project.repositoryStatuses.size}"/></strong>] data
						repositories for your project. Please complete the submission
						details for each repository.
					</p>
					<br />
					<div id="repositoryDate" style="width:200px;">
						<s:label for="anticpated_submission_date" value="Anticipated Submission Date" />
						&nbsp; &nbsp; <a href="#" id="popover" style="font-size: 12px;">
                         <i class="helpfile fa fa-question-circle fa-1x"
							aria-hidden="true"></i></a>
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
								&nbsp; &nbsp; <a href="#" id="popover" style="font-size: 12px;">
                             <i class="helpfile fa fa-question-circle fa-1x"
							aria-hidden="true"></i></a>					
							</div>

						</div> 
						<s:hidden name="project.repositoryStatuses[%{#stat.index}].planAnswerSelectionTByRepositoryId.id" value="%{#repositoryStatus.planAnswerSelectionTByRepositoryId.id}"/>
						<s:hidden name="project.repositoryStatuses[%{#stat.index}].id" value="%{#repositoryStatus.id}"/>		
						<div class="panel-body">		 				
							<div class="form-group row">
								<div class="col-xs-2">
									<br /> <s:label for="%{'regStatus_' + #stat.index}" value="Registration Status" /> 
									&nbsp; &nbsp; <a href="#" id="popover" style="font-size: 12px;">
                                   <i class="helpfile fa fa-question-circle fa-1x"
							       aria-hidden="true"></i></a>
									<s:select id="%{'regStatus_' + #stat.index}" name="project.repositoryStatuses[%{#stat.index}].lookupTByRegistrationStatusId.id" onChange="processRegistrationStatusSelected(this.id)" 
												value="%{#repositoryStatus.lookupTByRegistrationStatusId.id}" cssClass="c-select form-control"  list="registrationStatusList" listKey="optionKey" listValue="optionValue"/>											
								</div>

								<div class="col-xs-3">
									<br/> <s:label for="%{'projStatus_' + #stat.index}" value="Data Submission Status" /> 
                                     &nbsp; &nbsp; <a href="#" id="popover" style="font-size: 12px;">
                                  <i class="helpfile fa fa-question-circle fa-1x"
							        aria-hidden="true"></i></a>
									

									<s:select id="%{'projStatus_' + #stat.index}" name="project.repositoryStatuses[%{#stat.index}].lookupTBySubmissionStatusId.id" onChange="processSubmissionStatusSelected(this.id)" 
												value="%{#repositoryStatus.lookupTBySubmissionStatusId.id}" cssClass="c-select form-control" disabled="true" list="projectSubmissionStatusList" listKey="optionKey" listValue="optionValue"/>											
								</div>							

								<div class="col-xs-2">
									<br /> <s:label for="%{'studyRel_' + #stat.index}" value="Study Released?" />
									&nbsp; &nbsp; <a href="#" id="popover" style="font-size: 12px;">
                                  <i class="helpfile fa fa-question-circle fa-1x"
							         aria-hidden="true"></i></a>
									<s:select id="%{'studyRel_' + #stat.index}" name="project.repositoryStatuses[%{#stat.index}].lookupTByStudyReleasedId.id" 
												value="%{#repositoryStatus.lookupTByStudyReleasedId.id}"  cssClass="c-select form-control" disabled="true" list="studyReleasedList" listKey="optionKey" listValue="optionValue"/>											
								</div>

								<div class="col-xs-3">
									<br /> <s:label for="%{'accessionNumber_' + #stat.index}" value="Accession Number" />
									&nbsp; &nbsp; <a href="#" id="popover" style="font-size: 12px;">
                                  <i class="helpfile fa fa-question-circle fa-1x"
							       aria-hidden="true"></i></a>
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

<script type="text/javascript" src="<s:url value="/controllers/submissionStatus.js" />"></script>
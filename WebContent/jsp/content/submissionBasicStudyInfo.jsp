<%@ taglib uri="/struts-tags" prefix="s"%>

<s:form id="basic-study-form" cssClass="dirty-check"
	action="saveBasicStudyInfo" namespace="manage" method="post"
	enctype="multipart/form-data" data-toggle="validator" role="form">
	<s:hidden name="projectId" value="%{project.id}" />
	<!-- Page navbar -->
	<div class="pageNav">
		<s:submit action="saveBasicStudyInfo" value=" Save "
			class="saved btn btn-default" />
		<s:submit type="button" action="saveBasicStudyInfoAndNext"
			class="btn btn-project-primary">
			Save &amp; Next &nbsp;&nbsp;<i class="fa fa-caret-right" style="color:#ffffff;"></i></s:submit>
	</div>


	<!-- Begin Panel -->
	<div class="col-md-12">
		<div class="panel  project-panel-primary">
			<div class="panel-heading">
				<div class="pheader">
					<h4>Basic Study Information</h4>
					<div class="statusWrapper">
					  <s:if test="%{!pageStatusCode.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PAGE_STATUS_CODE_COMPLETED)}">         		          		      
    		  		    <div class="status">
    		  		      <a href="#" onclick="openMissingDataReport(${project.id}, '/gds/manage/viewMissingBsiData.action?')" class="statusLink">Generate Missing Data Report</a> &nbsp; &nbsp;
    		  		    </div>
    		  		  </s:if>
              		  <s:include value="/jsp/content/pageStatus.jsp"/>           	
            	    </div>					 
				</div>
				
			</div>

			<div class="panel-body">

				<div style="float: right;" class="question">
					<a
						href="http://www.cancer.gov/grants-training/grants-management/nci-policies/genomic-data/submission/basic-study-information.pdf"
						target="_blank">NCI Basic Study Information Form&nbsp;<i
						class="fa fa-external-link" aria-hidden="true"></i></a>
				</div>
			<!--<p style="font-size: 12px; margin-top: 5px;">
					Note: <i class="fa fa-asterisk" aria-hidden="true"></i>&nbsp;Asterisk
					indicates a required field
				</p>-->
				<p>&nbsp;</p>

				<div class="qSpacing">
				
				<!-- What repository will the data be submitted to? -->
				<s:if test="%{project.submissionReasonId.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@SUBMISSION_REASON_NONNIHFUND)
						&& project.subprojectFlag.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@FLAG_NO)}">				
				<div id="20" style="${map['20'].style}" class="qSpacing">
					
					<p class="question"><s:property
							value="%{getQuestionById(20).getDisplayText()}" /> &nbsp; &nbsp; <a href="#" id="popover" style="font-size: 12px;">
                         <i class="helpfile fa fa-question-circle fa-1x"
							aria-hidden="true"></i></a></p>
                          
					<s:iterator value="%{getAnswerListByQuestionId(20)}" var="ans"
						status="stat">
						<s:set name="params" value="%{map[#ans.id].parameters}" />
						<div class="checkbox">
							<s:checkbox id="%{#ans.id}" name="answers[20]" 
								value="%{getSelected(20, #ans.id)}" fieldValue="%{#ans.id}"
								onClick="applyUiRule(this,${params})" /> <s:label
								for="%{#ans.id}" value="%{#ans.displayText}" />
							<div id="controlledText" class="noteText" style="color:#686868;padding-left:20px;">
									<s:property value="%{#ans.additionalText}" />
							</div> 
							<s:if test="%{#ans.displayText=='Other'}">
								<div id="addRepo" style="display: none">
									<!--Repo hidden field-->				
									<s:if test="%{otherText[#ans.id].size > 0}">
										<s:iterator value="%{otherText[#ans.id]}" var="other" status="otherStat">
											<s:div class="otherWrapper" style="margin-bottom: 15px; margin-top: 15px;">
												<s:textfield id="field_%{#otherStat.index}" name="otherText[%{#ans.id}]" value="%{#other}" maxlength="200"
													class="other" placeholder="Name of Repository" />
												<s:if test="%{otherText[#ans.id].size > 1}">
													<i class="fa fa-trash fa-lg delete removeclass" title="Delete" aria-hidden="true" alt="Delete" style="font-size: 18px; padding-right: 3px; margin-left: 10px; cursor:pointer"></i>
												</s:if>
											</s:div>
										</s:iterator>
									</s:if>
									<s:else>
										<s:div class="otherWrapper" style="margin-bottom: 15px; margin-top: 15px;">
											<s:textfield id="field_0" name="otherText[%{#ans.id}]" maxlength="200"
												class="other" placeholder="Name of Repository" />
										</s:div>
									</s:else>
									<div style="margin-left: 75px; margin-top: 15px;">
										<input id="addfield" type="button" class="btn btn-default" value="Add Another Repository" />
									</div>
								</div>
							</s:if>
						</div>
					</s:iterator>

				</div>
				</s:if>
				<!--end add Repo hidden field-->
				<br/>
				

					<p class="question" style="display: inline;">
						<!--<i class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>-->
						Has the GPA reviewed the Basic Study Information?&nbsp; &nbsp; <a href="#" class="pop" data-container="body" data-toggle="popover" data-placement="right" data-content="GPA must review the descriptive information about the project for completeness. 
	                    This data is used to register the study in dbGaP. A &lt;a href=&quot;https://www.cancer.gov/grants-training/grants-management/nci-policies/genomic-data/submission/basic-study-information.pdf &quot;target=&quot;_blank &quot;&gt;template &lt;/a&gt; has been provided for the Investigator to fill-out." data-html="true" style="font-size: 12px;">
                            <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a></p>
	
					<s:radio id="reviewed" list="bsiOptions"
						name="bsiReviewedId" value="bsiReviewedId"
						template="radiomap-div.ftl" listKey="optionKey" listValue="optionValue" />
					<p>&nbsp;</p>


					<!--  File Upload -->
					<div style="width:600px;">
					<div style="margin-left: 15px; float:left; width: 38%"><p id="2" class="question">
					<!-- <i class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>-->
						Upload Basic Study Information:
					</p></div>
					<!--BEGIN Uploader-->
					<div class="qSpacing" id="bsiDiv" style="margin-bottom: 30px; float: left; width: 30%;">
						<div>
							<div class="input-group" style="width: 94px;">
								<label
									class="input-group-btn"> <span class="btn btn-default">
										Choose File <s:file style="display: none;"
											name="bsi" id="bsi" />
								</span>
								</label>
							</div>
						</div>	

						</div>			
						
						<div class="loadFileHistory" style="clear:both; width: 884px;">
							<s:include value="/jsp/content/submissionBasicStudyInfoFile.jsp" />
						</div>
					</div>

					<div>
						<p class="question">Comments (2000 Characters):</p>
						<s:textarea class="col-md-12 form-control input" rows="3" maxlength="2000" id="bsiComments" name="comments"></s:textarea>
						<div id="charNum5" style="text-align: right; font-style: italic;">
				          <span style="color: #990000;">2000</span> Character limits
			          </div>
					</div>
				</div>
			</div>
			<!--  panel body -->
		</div>
		<!--  Panel -->
	</div>
    <s:hidden name="ie_Upload"/>

	<!--SAVE & NEXT BUTTONS-->
	<div class="pageNav">
		<s:submit action="saveBasicStudyInfo" value=" Save "
			class="saved btn btn-default" />
		<s:submit type="button" action="saveBasicStudyInfoAndNext"
			class="btn btn-project-primary" >
			Save &amp; Next &nbsp;&nbsp;<i class="fa fa-caret-right" style="color:#ffffff;"></i></s:submit>
	</div>
</s:form>

<!-- Modal -->
<div id="fileModal" class="modal fade bs-example-modal-sm" tabindex="-1"
	role="dialog" aria-labelledby="fileModalLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3 id="fileModalLabel">File Upload Message</h3>
			</div>
			<div id="fileModalId" class="modal-body"></div>
			<div class="modal-footer">
				<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="<s:url value="/controllers/gds.js" />"></script>
<script type="text/javascript" src="<s:url value="/controllers/basicStudy.js" />"></script>
<script type="text/javascript">
$(function($){
	$('[data-toggle="tooltip"]').tooltip({
	    container : 'body'
	  });
});
</script>

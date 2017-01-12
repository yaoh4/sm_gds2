<%@ taglib uri="/struts-tags" prefix="s"%>


<s:form id="gds-form" cssClass="dirty-check" action="saveGdsPlan" namespace="manage" method="post"
			enctype="multipart/form-data" data-toggle="validator" role="form">
	<s:hidden name="projectId" value="%{project.id}"/>
	<s:hidden name="project.subprojectFlag" id="subprojectFlag" value="%{project.subprojectFlag}"/>
	<!-- Page navbar -->
	<div class="pageNav">
		<s:submit action="saveGdsPlan" onclick="enableAllCheckbox();return warnGdsPlan()" value=" Save " class="saved btn btn-default"/>
		<s:submit type="button" action="saveGdsPlanAndNext" onclick="enableAllCheckbox();return warnGdsPlanNext()" class="btn btn-project-primary">
			 Save &amp; Next &nbsp;&nbsp;<i class="fa fa-caret-right" style="color:#ffffff;"></i></s:submit>
	</div>

	<!-- Begin Panel -->
	<div class="col-md-12">
		<div class="panel  project-panel-primary">
			<div class="panel-heading">
				<div class="pheader">
					<h4>Genomic Data Sharing Plan</h4>
					<div class="statusWrapper">
					  <s:if test="%{!pageStatusCode.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PAGE_STATUS_CODE_COMPLETED)}">         		         		      
    		  		    <div class="status">    		  		  
    		  		      <a href="#" onclick="openMissingDataReport(${project.id}, '/gds/manage/viewMissingGdsPlanData.action?')" class="statusLink">Generate Missing Data Report</a> &nbsp; &nbsp;
    		  		    </div>
    		  		  </s:if>
              		  <s:include value="/jsp/content/pageStatus.jsp"/>           	
            		</div>
				</div>
			</div>

			<div class="panel-body">
				<div style="float: right;" class="question">
					<a href="http://www.cancer.gov/grants-training/grants-management/nci-policies/genomic-data/submission/nci-dsp.pdf"
						target="_blank">Genomic Data Sharing Plan Template&nbsp;<i
						class="fa fa-external-link" aria-hidden="true"></i></a>
				</div>

				<p>&nbsp;</p>

				<%-- Is there a data sharing exception requested for this project? --%>
				<div id="1" style="${map['1'].style}" class="qSpacing">
					<p class="question">
						<s:property value="%{getQuestionById(1).getDisplayText()}" /> [
						<a href="http://www.cancer.gov/grants-training/grants-management/nci-policies/genomic-data/about-policy#exceptions"
							target="_blank">View Exception Process&nbsp;<i
							class="fa fa-external-link" aria-hidden="true"></i></a> ]
							&nbsp; &nbsp; <a href="#" class="hoverOver" data-toggle="tooltip" data-placement="right"  data-html="true"
						 style="font-size: 12px;"><s:hidden id="GDS_EXCEPTION_KEY" value="%{getHelpText('GDS_EXCEPTION_KEY')}"/> <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a>
					</p>
					<s:set name="params" value="%{map[1].parameters}" />
					<s:radio id="%{#answer.id}" list="%{getAnswerListByQuestionId(1)}"
						listKey="id" listValue="displayText" name="answers[1]"
						onClick="applyUiRule(this,${params})"
						template="radiomap-div.ftl" />
				</div>

				<%-- Was this exception approved? --%>
				<div id="4" style="${map['4'].style}" class="qSpacing">
					<p class="question"><s:property
							value="%{getQuestionById(4).getDisplayText()}" />
							&nbsp; &nbsp; <a href="#" class="hoverOver" data-toggle="tooltip" data-placement="right"  data-html="true"
						 style="font-size: 12px;"><s:hidden id="GDS_APPROVED_EXCEPTION_KEY" value="%{getHelpText('GDS_APPROVED_EXCEPTION_KEY')}"/> <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a></p>
					<s:set name="params" value="%{map[4].parameters}" />
					<s:radio id="%{#answer.id}" list="%{getAnswerListByQuestionId(4)}"
						listKey="id" listValue="displayText" name="answers[4]"
						onClick="applyUiRule(this,${params})"
						template="radiomap-div.ftl" />
				</div>

				<div id="exceptionMemoDiv" style="${map['exceptionMemoDiv'].style}" 
					class="qSpacing" style="margin-left: 30px;">
						<s:include value="/jsp/content/submissionGdsPlanMemo.jsp"/>
				</div>

				<%-- Will there be any data submitted? --%>
				<div id="8" style="${map['8'].style}" class="qSpacing">
					<p class="question"><s:property
							value="%{getQuestionById(8).getDisplayText()}" />
							&nbsp; &nbsp; <a href="#" class="hoverOver" data-toggle="tooltip" data-placement="right"  data-html="true"
						 style="font-size: 12px;"><s:hidden id="GDS_DATA_SUBMITTED_KEY" value="%{getHelpText('GDS_DATA_SUBMITTED_KEY')}"/> <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a></p>
					<s:set name="params" value="%{map[8].parameters}" />
					<s:radio id="%{#answer.id}" list="%{getAnswerListByQuestionId(8)}"
						listKey="id" listValue="displayText" name="answers[8]"
						onClick="applyUiRule(this,${params})"
						template="radiomap-div.ftl" />
				</div>

				<%-- What specimen type does the data submission pertain to? --%>
				<div id="11" style="${map['11'].style}" class="qSpacing">
					<p class="question"><s:property
							value="%{getQuestionById(11).getDisplayText()}" />
							&nbsp; &nbsp;  <a href="#" class="hoverOver" data-toggle="tooltip" data-placement="right"  data-html="true"
						 style="font-size: 12px;"><s:hidden id="GDS_SPECIMEN_TYPE_KEY" value="%{getHelpText('GDS_SPECIMEN_TYPE_KEY')}"/> <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a></p>
					<s:iterator value="%{getAnswerListByQuestionId(11)}" var="ans"
						status="stat">
						<s:set name="params" value="%{map[#ans.id].parameters}" />
						<div class="checkbox">
							<s:checkbox id="%{#ans.id}" name="answers[11]" 
								value="%{getSelected(11, #ans.id)}" fieldValue="%{#ans.id}"
								onClick="applyUiRule(this,${params})" /> <s:label
								for="%{#ans.id}" value="%{#ans.displayText}"/>
						</div>
					</s:iterator>
				</div>

				<%-- What type of data will be submitted? --%>
				<div id="14" style="${map['14'].style}" class="qSpacing">
					<p class="question"><s:property
							value="%{getQuestionById(14).getDisplayText()}" />
							&nbsp; &nbsp; <a href="#" class="hoverOver" data-toggle="tooltip" data-placement="right"  data-html="true"
						 style="font-size: 12px;"><s:hidden id="GDS_DATA_TYPE_SUBMITTED_KEY" value="%{getHelpText('GDS_DATA_TYPE_SUBMITTED_KEY')}"/> <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a></p>
					<s:iterator value="%{getAnswerListByQuestionId(14)}" var="ans"
						status="stat">
						<s:set name="params" value="%{map[#ans.id].parameters}" />
						<div class="checkbox">
							<s:checkbox id="%{#ans.id}" name="answers[14]" 
								value="%{getSelected(14, #ans.id)}" fieldValue="%{#ans.id}"
								onClick="applyUiRule(this,${params})" /> <s:label
								for="%{#ans.id}" value="%{#ans.displayText}" />
						</div>
					</s:iterator>
				</div>

				<%-- What type of access is the data to be made available through? --%>
				<div id="17" style="${map['17'].style}" class="qSpacing">
					<p class="question"><s:property
							value="%{getQuestionById(17).getDisplayText()}" />
							&nbsp; &nbsp; <a href="#" class="hoverOver" data-toggle="tooltip"  data-placement="right"  data-html="true"
						 style="font-size: 12px;"><s:hidden id="GDS_ACCESS_TYPE_KEY" value="%{getHelpText('GDS_ACCESS_TYPE_KEY')}"/> <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a></p>
					<s:iterator value="%{getAnswerListByQuestionId(17)}" var="ans"
						status="stat">
						<s:set name="params" value="%{map[#ans.id].parameters}" />
						<div class="checkbox">
							<s:checkbox id="%{#ans.id}" name="answers[17]" 
								value="%{getSelected(17, #ans.id)}" fieldValue="%{#ans.id}"
								onClick="applyUiRule(this,${params})" /> <s:label
								for="%{#ans.id}" value="%{#ans.displayText}" />
						</div>
					</s:iterator>
				</div>

				<%-- What repository will the data be submitted to? --%>
				<div id="20" style="${map['20'].style}" class="qSpacing">
					<p class="question"><s:property
							value="%{getQuestionById(20).getDisplayText()}" />
							&nbsp; &nbsp; <a href="#" class="pop" data-container="body" data-toggle="popover" data-placement="right" data-content="&lt;b&gt;dbGaP :&lt;/b&gt;&lt;a href=&quot; http://www.ncbi.nlm.nih.gov/gap&quot;target=&quot;_blank &quot;&gt; http://www.ncbi.nlm.nih.gov/gap&lt;/a&gt; &lt;br&gt;
                            &lt;b&gt;SRA :&lt;/b&gt;&lt;a href=&quot;  http://www.ncbi.nlm.nih.gov/sra&quot;target=&quot;_blank &quot; &gt; http://www.ncbi.nlm.nih.gov/sra &lt;/a&gt; &lt;br&gt;
                            &lt;b&gt;GDC :&lt;/b&gt;&lt;a href=&quot;  https://gdc.nci.nih.gov/&quot;target=&quot;_blank &quot; &gt; https://gdc.nci.nih.gov/&lt;/a&gt;  &lt;br&gt;
                            &lt;b&gt;GEO :&lt;/b&gt;&lt;a href=&quot;http://www.ncbi.nlm.nih.gov/geo/&quot;target=&quot;_blank &quot; &gt; http://www.ncbi.nlm.nih.gov/geo/ &lt;/a&gt;  &lt;br&gt;
                            &lt;b&gt;Other:&lt;/b&gt; If you are planning on submitting data to another repository, please be sure to discuss with your Genomic Program Administrator (GPA) to make sure it is appropriate." data-html="true" style="font-size: 12px;">
                            <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a></p>

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
									<s:if test="%{otherText[#ans.id].size lt 10}">
										<div id="anotherButton" style="margin-left: 75px; margin-top: 15px;">
											<input id="addfield" type="button" class="btn btn-default" value="Add Another Repository" />
										</div>
									</s:if>
									<s:else>
										<div id="anotherButton" style="display: none; margin-left: 75px; margin-top: 15px;">
											<input id="addfield" type="button" class="btn btn-default" value="Add Another Repository" />
										</div>
									</s:else>
								</div>
							</s:if>
						</div>
					</s:iterator>

				</div>
				<!--end add Repo hidden field-->

				<%-- Has the GPA reviewed the Data Sharing Plan? --%>
				<s:if test="%{requiredByGdsPolicy == true}">
					<div id="26" style="${map['26'].style}" class="qSpacing">
						<p class="question"><s:property
							value="%{getQuestionById(26).getDisplayText()}" />
							&nbsp; &nbsp; <a href="#" class="pop" data-container="body" data-toggle="popover" data-placement="right" data-content="Prior to the start of GDS policy-covered research, all investigators must develop and have in place an approved data sharing plan (DSP).
	                        A &lt;a href=&quot;https://www.cancer.gov/grants-training/grants-management/nci-policies/genomic-data/submission/nci-dsp.pdf &quot;target=&quot;_blank &quot;&gt;template &lt;/a&gt;
	                        has been provided for your use. It is not required that you use this template, but the DSP should adhere to the 
	                        &lt;a href=&quot;https://gds.nih.gov/pdf/NIH_guidance_developing_GDS_plans.pdf &quot;target=&quot;_blank &quot;&gt;NIH Guidance for Investigators in Developing Data Sharing Plans. &lt;/a&gt; " data-html="true" style="font-size: 12px;">
                            <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a></p>
						<s:set name="params" value="%{map[26].parameters}" />
					<s:radio id="%{#answer.id}" list="%{getAnswerListByQuestionId(26)}"
						listKey="id" listValue="displayText" name="answers[26]"
						onClick="applyUiRule(this,${params})" 
						template="radiomap-div.ftl" />
					</div>
				</s:if>

				<%-- How would you like to submit the Data Sharing Plan? --%>
				<div id="29" style="${map['29'].style}" class="qSpacing">
					<p class="question"><s:property
						value="%{getQuestionById(29).getDisplayText()}" />
						<s:if test="%{requiredByGdsPolicy == false}">
							(Optional)
						</s:if>
						&nbsp; &nbsp; <a href="#" class="pop" data-container="body" data-toggle="popover" data-placement="right" data-content="A &lt;a href=&quot;https://www.cancer.gov/grants-training/grants-management/nci-policies/genomic-data/submission/nci-dsp.pdf &quot;target=&quot;_blank &quot;&gt;template &lt;/a&gt;
	                    has been provided for your use. It is not required that you use this template, but the DSP should adhere to the 
	                    &lt;a href=&quot; https://gds.nih.gov/pdf/NIH_guidance_developing_GDS_plans.pdf &quot;target=&quot;_blank &quot;&gt; NIH Guidance for Investigators in Developing Data Sharing Plans.&lt;/a&gt;" data-html="true" style="font-size: 12px;">
                            <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a>
					</p>
					<s:set name="params" value="%{map[29].parameters}" />
				<s:radio id="%{#answer.id}" list="%{getAnswerListByQuestionId(29)}"
					listKey="id" listValue="displayText" name="answers[29]"
					onClick="applyUiRule(this,${params})"
					template="radiomap-div.ftl" />
				</div>
					
				<div id="textEditorDiv" style="${map['textEditorDiv'].style}">
					<textarea name="dataSharingPlanEditorText" id="editor1" rows="10"
						cols="80"></textarea>
					<div class="loadFileHistory">
						<s:include value="/jsp/content/submissionGdsPlanFile.jsp" />
					</div>
				</div>

				<!--BEGIN HIDDEN Field for Uploader-->
				<div class="qSpacing" style="margin-left: 30px; ${map['dataSharingPlanDiv'].style}"
					id="dataSharingPlanDiv">
					<p class="question" style="width:33.5%; float:left;">Upload Data Sharing Plan: [to be uploaded by GPA]</p>

				
						<div class="input-group" style="width: 94px; float: left; margin-bottom: 20px;"><label
								class="input-group-btn"> <span class="btn btn-default">
									Choose File <s:file style="display: none;" name="dataSharingPlan"
										id="dataSharingPlan" />
							</span>
							</label>
						</div>
					
										
					<div class="loadFileHistory">
						<s:include value="/jsp/content/submissionGdsPlanFile.jsp" />
					</div>
				</div>
				
				<div>
					<p class="question">Comments (2000 Characters):</p>
					<s:textarea class="col-md-12 form-control input" rows="3" maxlength="2000" id="gdsPlanComments" name="comments"></s:textarea>
					<div id="charNum4" style="text-align: right; font-style: italic;">
				       <span style="color: #990000;">2000</span> Character limits
			        </div>
				</div>
			</div>
			
			<!--end panel body-->
		</div>
	</div>
	

	<!--SAVE & NEXT BUTTONS-->
	<div class="pageNav">
		<s:submit action="saveGdsPlan" onclick="enableAllCheckbox();return warnGdsPlan()" value=" Save " class="saved btn btn-default"/>
		<s:submit type="button" action="saveGdsPlanAndNext" onclick="enableAllCheckbox();return warnGdsPlanNext()" class="btn btn-project-primary">
			Save &amp; Next &nbsp;&nbsp;<i class="fa fa-caret-right" style="color:#ffffff;"></i></s:submit>
	</div>
</s:form>

<script type="text/javascript"
	src="<s:url value="/scripts/ckeditor/ckeditor.js" />"></script>
<script type="text/javascript"
	src="<s:url value="/controllers/UiRule.js" />"></script>
<script type="text/javascript"
	src="<s:url value="/controllers/dataSharing.js" />"></script>
<script type="text/javascript">
$(function($){
	$('[data-toggle="tooltip"]').tooltip({
	    container : 'body'
	  });
});
</script>

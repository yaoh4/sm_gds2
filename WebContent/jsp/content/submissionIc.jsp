<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<div id="submissionIcSection">
    <!--Begin Form -->
    <s:form id="institutional_form" name="instititional_form" cssClass="dirty-check" namespace="manage"
    enctype="multipart/form-data" data-toggle="validator" action="saveIc" method="post" role="form">  
    <s:hidden name="projectId" value="%{project.id}"/>
    <s:hidden name="instCertification.id" value="%{instCertification.id}"/>
	<s:hidden name="instCertification.createdBy" value="%{instCertification.createdBy}"/>
	<s:hidden name="doc.id" value="%{doc.id}" />
      
      <!-- Page navbar -->
      <div class="pageNav">
        <s:submit action="saveIc" value=" Save " onclick="enableStudy()" class="saved btn btn-project-primary" />
       <s:submit type="button" action="saveIcAndGotoDashboard" onclick="enableStudy()" class="saved btn btn-project-primary">
      Save &amp; Go to Dashboard &nbsp;&nbsp;<i class="fa fa-caret-right" style="color:#ffffff;"></i>
    </s:submit>
      </div>


 	  <!-- Begin Panel -->
      <div class="col-md-12">
        <div class="panel  project-panel-primary">
          
          <div class="panel-heading">
            <div class="pheader"><h4>Add Institutional Certification(s)</h4></div>
            <s:if test="%{instCertification.id != null}">
              <div class="statusWrapper">
    		    <s:if test="%{!pageStatusCode.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PAGE_STATUS_CODE_COMPLETED)}">         		       
    		      <div class="status">
    		        <a href="#" onclick="openMissingDataReport(${project.id}, '/gds/manage/viewMissingIcData.action?instCertId=${instCertification.id}&')" class="statusLink">Generate Missing Data Report</a> &nbsp; &nbsp;   		    
    		      </div>
    		    </s:if>
                <s:include value="/jsp/content/pageStatus.jsp"/>           	
              </div>
            </s:if>
          </div>

		
          <div class="panel-body">
		
            <div style="float: right;" class="question"><a href="https://gds.nih.gov/Institutional_Certifications.html" target="_blank">Institutional Certifications&nbsp;<i class="fa fa-external-link" aria-hidden="true"></i></a></div>
            <p style="font-size: 12px;  margin-top: 5px;">Note: <i class="fa fa-asterisk" aria-hidden="true"></i>&nbsp;Asterisk indicates a required field   </p>
            <p>&nbsp;</p>
            
            <div class="qSpacing">
			<!--  File Upload -->
			<div class="qSpacing" style="margin-left: 30px;" id="icDiv" style="${map['icDiv'].style}">
				<s:include value="/jsp/content/submissionIcFile.jsp" />
			</div>
		
			  <div class="form-group row">
			   <div class="col-xs-3">
                  <label for="Provisional or Final?">Provisional or Final? </label>
                  &nbsp; &nbsp; <a href="#" class="hoverOver" data-toggle="tooltip" data-placement="right"  data-html="true"
						 style="font-size: 12px;"><s:hidden id="IC_PROV_FINAL_KEY" value="%{getHelpText('IC_PROV_FINAL_KEY')}"/> <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a>
                  <s:select name="instCertification.provisionalFinalCode"
                    value="instCertification.provisionalFinalCode"
                    class="c-select form-control"
                    list="%{@gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper@getLookupDropDownList(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@IC_PROV_OR_FINAL_LIST)}"
                    listKey="optionKey" listValue="optionValue" id="finalprov"
                    emptyOption="true"/>
                </div>
                
                <div class="col-xs-3">
                  <label for="Approved by GPA">Approved by GPA</label>
                  &nbsp; &nbsp; <a href="#" class="hoverOver" data-toggle="tooltip" data-placement="right"  data-html="true"
						 style="font-size: 12px;"><s:hidden id="IC_APPROVED_BY_GPA_KEY" value="%{getHelpText('IC_APPROVED_BY_GPA_KEY')}"/> <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a>
                  <s:select name="instCertification.gpaApprovalCode"
                  	value="instCertification.gpaApprovalCode"
                    class="c-select form-control"
                    list="%{@gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper@getLookupDropDownList(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@IC_APPROVED_BY_GPA_LIST)}"
                    listKey="optionKey" listValue="optionValue" id="gpa"
                    emptyOption="true"/>
                 
                </div>
             
                <div id="memo" class="col-xs-3 fixWidth">
                   <label for="Project Submission Status">IC Memo for Use in Future Projects?</label>
                  &nbsp; <a href="#" class="hoverOver" data-toggle="tooltip" data-placement="right"  data-html="true"
						 style="font-size: 12px;"><s:hidden id="IC_MEMO_KEY" value="%{getHelpText('IC_MEMO_KEY')}"/> <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a>
                  <s:select name="instCertification.futureProjectUseCode"
                    value="instCertification.futureProjectUseCode"
                    class="c-select form-control"
                    list="%{@gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper@getLookupDropDownList(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@IC_FOR_FUTURE_USE_LIST)}"
                    listKey="optionKey" listValue="optionValue"
                    emptyOption="true"/>
                </div>
              </div>
	
			  <!--  IC comments -->
	 		  <div class="form-group row  col-xs-12">
                <label for="comment">Comments (2000 Characters):</label><br/>
                <s:textarea class="col-md-12 form-control commentsClass" style="overflow-y: scroll;" rows="3" maxlength="2000" id="instCertComments" value="%{instCertification.comments}" name="instCertification.comments" ></s:textarea>
                <div id="charNum6" style="text-align: right; font-style: italic;">
				   <span style="color: #990000;">2000</span> Character limits
			    </div>
			  </div>
        <p>&nbsp;</p>
	
			   <!--Begin STUDY SECTION-->
			
              <div style="display: block;" class="form-group row col-xs-12" id="sections">
								
                <div id="entry" class="cloneStudyInput">
					  								
                  <s:iterator status="studiesStat" var="study" value="instCertification.studies">
                    <s:set name="studiesIdx" value="#studiesStat.index" />
					  					    
                    <div id="studySection${studiesIdx}" class="studySections">
					  	
                      <s:hidden name="instCertification.studies[%{#studiesStat.index}].displayId" id="studyDisplayId-%{#studiesIdx}" value="%{#studiesIdx}"/>
					  	
					  <!--  This is read and passed back to prevent the createdBy field from being overwritten -->
					  <s:hidden name="instCertification.studies[%{#studiesStat.index}].createdBy"/>
					     
					  <!--  This is read and passed back to so the existing ids are updated and not replaced -->
					  <s:hidden name="instCertification.studies[%{#studiesStat.index}].id" class="studySelectedStudyId" id="studyId-%{#studiesIdx}"/> 
				  		
					  <div class="panel-group" id="accordion-${studiesIdx}">
				        <div class="panel panel-default">
					  	
					    <!--  STUDY SECTION HEADER  -->
					  
                        <div class="studyHeadingPanel panel-heading header">
                          <h4 class="panel-title ">
                            <a class="studyHeading" data-toggle="collapse" data-target="#collapse-${studiesIdx}">
                             <i class="fa fa-minus-square fa-lg" aria-expanded="true"></i>&nbsp;
                              Study
                            </a>
                            <s:if test="%{instCertification.studies.size > 1}"> 
                              <a href="#" onclick="deleteStudy(${studiesIdx})" class="deleteIcon" style="float: right;">
                            	<i class="fa fa-trash fa-lg" title="delete" alt="delete" aria-hidden="true"></i>
                              </a>
                            </s:if> 
                                               
                           </h4>
                        </div>	                      		
												
						<!--  STUDY SECTION BODY -->
									
                        <div id="collapse-${studiesIdx}" class="content panel-collapse collapse in">
                          <div class="panel-body">
                          				
                            <div class="form-group row" style="display: block;">
                              <div class="col-xs-10">
                                <label class="label_sn" for="Study Name">
                                  <i class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>
                                  Study Name &nbsp; &nbsp; <a href="#" class="hoverOver" data-toggle="tooltip" data-placement="right"  data-html="true"
						 style="font-size: 12px;"><s:hidden id="IC_STUDY_KEY" value="%{getHelpText('IC_STUDY_KEY')}"/> <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a>
                                </label>
                                <div class="input-group">
                                  <input type="text" class="form-control input_sn" placeholder="Full Name of Study" 
                              	    id="studyName-${studiesIdx}"  maxlength="150"
                              		name="instCertification.studies[<s:property value='#studiesStat.index'/>].studyName" 
                              		value="${study.studyName}" disabled />
                              	  <div class="input-group-btn">
                                    <a href="#">
                                    <button onClick="openStudy(this, 'single')" class="btn btn-default" type="button" title="Edit" style=" margin-left: -2px; height: 34px;">
                                      <i class="fa fa-pencil" aria-hidden="true"></i>
                                    </button></a> 
                				  </div>
                				</div>
                              </div>
                            </div>
                              	
                            <div class="form-group row" style="display: block;">				
                              <div class="col-xs-12">
                                <label class="label_in" for="Provisional or Final?">Institution(s) &nbsp; &nbsp; <a href="#" class="hoverOver" data-toggle="tooltip" data-placement="right"  data-html="true"
						 style="font-size: 12px;"><s:hidden id="IC_INSTITUTION_KEY" value="%{getHelpText('IC_INSTITUTION_KEY')}"/> <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a></label>
                                <div class="input_fields_wrap">
                                  <input type="text" class="form-control input_in" placeholder="Full Name of Institution"
                              		id="institution-${studiesIdx}" maxlength="150"
									name="instCertification.studies[<s:property value='#studiesStat.index'/>].institution"
									value="${study.institution}" disabled />
								</div>
                              
                              </div>
                            </div>
                            
                              <div class="DULv col-xs-3">
                                <label for="Data Use Limitation(s) Verified?" class="label_dulV">Data Use Limitation(s) Verified?
                                &nbsp; &nbsp; <a href="#" class="hoverOver" data-toggle="tooltip" data-placement="right"  data-html="true"
						 style="font-size: 12px;"><s:hidden id="IC_DUL_KEY" value="%{getHelpText('IC_DUL_KEY')}"/> <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a>
                                </label>
                                <s:select name="instCertification.studies[%{#studiesStat.index}].dulVerificationId"
                        			value="instCertification.studies[#studiesStat.index].dulVerificationId"
                        			class="c-select form-control"
                        			list="%{@gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper@getLookupDropDownList(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@IC_DUL_VERIFIED_LIST)}"
                        			listKey="optionKey" listValue="optionValue" id="dulVerificationId-%{#studiesIdx}"
                       				emptyOption="true"/>
                              </div>
		
                            <div class="form-group row  col-xs-12" style="display: block;" >
                              <label for="study comments" class="label_stCom">Comments (2000 Characters):</label><br/>
                              <s:textarea id="comments-%{#studiesStat.index}" class="col-md-12 form-control input_stCom commentsClass"  
                              		value="%{#study.comments}" maxlength="2000"
									name="instCertification.studies[%{#studiesIdx}].comments" 
									 style="overflow-y: scroll;" rows="3" onkeyup="countChar(this)"></s:textarea>
									<div id="count-${studiesIdx}" style="text-align: right; font-style: italic;">
				                    <span style="color: #990000;">2000</span> Character limits
			                		</div>
                            </div> <!--end row-->
                            <p>&nbsp;</p>
                                                 
                            <div class="DULinfo"> 
                              <div class="form-group row col-xs-12">
                                <p><span class="question">
                                  <h4>Data Use Limitation(s)</h4></span><br/>
                                   You may add up to 10 DULs
                                </p>
                              </div>
                            </div>

							<s:include value="/jsp/content/manageDuls.jsp"/>																
																	
                          </div> <!-- End Panel body -->
			            </div> <!-- End Collapse 1 -->
			             </div> <!--  End study panel -->
                 		 </div> <!--  End panel group -->
			            </div>  <!--  studySection div -->
			            
                      </s:iterator>
                      
                      </div>  <!--  cloneStudyInput -->

	
              <div>
              	<input type="button" id="btnAdd" value="Select Additional Studies" onClick="openStudy(this, 'multiple')" class="btn btn-default">
              </div>	

			  								
              </div> <!--  End Study Section -->	
              </div> <!--  end qSpacing> -->
				
          </div> <!--  panel body -->
        </div> <!--  Panel -->
      </div>  
      <!-- Adding dummy hidden field as a workaround for IE bug that corrupts data from the last input field -->
		<s:hidden name="ie_Upload"/>
	 <!--SAVE & NEXT BUTTONS-->
      <div class="pageNav">
        <s:submit action="saveIc" value=" Save " onclick="enableStudy()" class="saved btn btn-project-primary"/>
        <s:submit type="button" action="saveIcAndGotoDashboard" onclick="enableStudy()" class="saved btn btn-project-primary">
      Save &amp; Go to Dashboard &nbsp;&nbsp;<i class="fa fa-caret-right" style="color:#ffffff;"></i>
    </s:submit>
	  </div>
        
    </s:form>
</div>
<div id="reselectStudySection" style="display: none;">
	<s:include value="/jsp/content/submissionStudySelect.jsp" />
</div>
<s:include value="/jsp/content/dulSetTemplate.jsp"/>
<script type="text/javascript"
	src="<s:url value="/controllers/gds.js" />"></script>
<script type="text/javascript"
	src="<s:url value="/controllers/institutional.js" />"></script>
	<script type="text/javascript">
$(function($){
	$('[data-toggle="tooltip"]').tooltip({
	    container : 'body'
	  });
});
</script>

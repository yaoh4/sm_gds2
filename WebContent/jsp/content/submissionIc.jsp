<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

    
    <!--Begin Form -->
    <s:form id="institutional_form" name="instititional_form" cssClass="dirty-check" namespace="manage"
    enctype="multipart/form-data" data-toggle="validator" action="saveIc" method="post" role="form">  
    <s:hidden name="projectId" value="%{project.id}"/>
    <s:hidden name="instCertification.id" value="%{instCertification.id}"/>
	<s:hidden name="instCertification.createdBy" value="%{instCertification.createdBy}"/>
	<s:hidden name="doc.id" value="%{doc.id}" />
      
      <!-- Page navbar -->
      <div class="pageNav">
        <s:if test="%{instCertification.id != null}">
      		<s:submit action="listIc" value=" Cancel " class="saved btn btn-default"/>	 
        </s:if>
        <s:submit action="saveIc" value=" Save Institutional Certification " class="saved btn btn-project-primary"/>
      </div>


 	  <!-- Begin Panel -->
      <div class="col-md-12">
        <div class="panel  project-panel-primary">
          
          <div class="panel-heading">
            <div class="pheader"><h4>Add Institutional Certification(s)</h4></div>
      <!--    <div class="statusWrapper">
              <div class="status"><a href="#" class="statusLink">Generate Missing Data Report</a> &nbsp; &nbsp;</div>
              <div class="statusIcon">
                <a href="#" class="tooltip">
                  <img src="<s:url value="/images/inprogress.png"/>" alt="In Progress" />
                  <span>
                    <img class="callout" src="<s:url value="/images/callout_black.gif" />" />
                    <strong>Legend:</strong><br />
                    <img src="<s:url value="/images/legend.gif" />" />
                    
                  </span>
                </a>
              </div>
            </div> -->
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
                  <label for="Approved by GPA">Approved by GPA</label>
                  <s:select name="instCertification.gpaApprovalCode"
                  	value="instCertification.gpaApprovalCode"
                    class="c-select form-control"
                    list="%{@gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper@getLookupDropDownList(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@IC_APPROVED_BY_GPA_LIST)}"
                    listKey="optionKey" listValue="optionValue" id="gpa"
                    emptyOption="true"/>
                 
                </div>
                <div class="col-xs-3">
                  <label for="Provisional or Final?">Provisional or Final?</label>
                  <s:select name="instCertification.provisionalFinalCode"
                    value="instCertification.provisionalFinalCode"
                    class="c-select form-control"
                    list="%{@gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper@getLookupDropDownList(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@IC_PROV_OR_FINAL_LIST)}"
                    listKey="optionKey" listValue="optionValue" id="finalprov"
                    emptyOption="true"/>
                </div>
                  
                <div id="memo" class="col-xs-3">
                  <label for="Project Submission Status">IC Memo for Use in Future Projects?</label>
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
                <s:textarea class="col-md-12 form-control" rows="3" value="%{instCertification.comments}" name="instCertification.comments" ></s:textarea>
			  </div>
        <p>&nbsp;</p>
	
			  <!--Begin STUDY SECTION-->
			
              <div class="form-group row col-xs-12" id="sections">
								
                <div id="entry" class="cloneStudyInput">
					  								
                  <s:iterator status="studiesStat" var="study" value="instCertification.studies">
                    <s:set name="studiesIdx" value="#studiesStat.index" />
					  					    
                    <div id="studySection${studiesIdx}" class="studySections">
					  	
                      <s:hidden name="instCertification.studies[%{#studiesStat.index}].displayId" id="studyDisplayId-%{#studiesIdx}" value="%{#studiesIdx}"/>
					  	
					  <!--  This is read and passed back to prevent the createdBy field from being overwritten -->
					  <s:hidden name="instCertification.studies[%{#studiesStat.index}].createdBy"/>
					     
					  <!--  This is read and passed back to so the existing ids are updated and not replaced -->
					  <s:hidden name="instCertification.studies[%{#studiesStat.index}].id" id="studyId-%{#studiesIdx}"/> 
				  		
					  <div class="panel-group" id="accordion">
				        <div class="panel panel-default">
					  	
					    <!--  STUDY SECTION HEADER  -->
					  
                        <div class="studyHeadingPanel panel-heading header">
                          <h4 class="panel-title ">
                            <a class="studyHeading" href="#collapseOne">
                            <s:if test="%{instCertification.studies.size > 1}"> 
                              <a href="#" onclick="deleteStudy(${studiesIdx})" class="deleteIcon" style="float: right;">
                            	<i class="fa fa-trash fa-lg" title="delete" alt="delete" aria-hidden="true"></i>
                              </a>
                            </s:if> 
                              <i class="fa fa-minus-square fa-lg" aria-hidden="true" alt="delete" title="delete"></i>&nbsp;
                              Study
                            </a>
                           </h4>
                        </div>	                      		
												
						<!--  STUDY SECTION BODY -->
									
                        <div id="collapseOne" class="content">
                          <div class="panel-body">
                          				
                            <div class="form-group row">
                              <div class="col-xs-3">
                                <label class="label_sn" for="Study Name">Study Name</label>
                                <input type="text" class="form-control input_sn" placeholder="Full Name of Study" 
                              	    id="studyName-${studiesIdx}"  
                              		name="instCertification.studies[<s:property value='#studiesStat.index'/>].studyName" 
                              		value="${study.studyName}"/>	
                              </div>
                              					
                              <div class="col-xs-3">
                                <label class="label_in" for="Provisional or Final?">Institution</label>
                                <input type="text" class="form-control input_in" placeholder="Full Name Institution"
                              		id="institution-${studiesIdx}"
									name="instCertification.studies[<s:property value='#studiesStat.index'/>].institution"
									value="${study.institution}"/>
                              
                              </div>
                              <div class="DULv col-xs-3">
                                <label for="Data Use Limitation(s) Verified?" class="label_dulV">Data Use Limitation(s) Verified?</label>
                                <s:select name="instCertification.studies[%{#studiesStat.index}].dulVerificationId"
                        			value="instCertification.studies[#studiesStat.index].dulVerificationId"
                        			class="c-select form-control"
                        			list="%{@gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper@getLookupDropDownList(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@IC_DUL_VERIFIED_LIST)}"
                        			listKey="optionKey" listValue="optionValue" id="dulVerificationId%-{#studiesIdx}"
                       				emptyOption="true"/>
                              </div>
                            </div> <!--end row-->			
                            <div class="form-group row  col-xs-12" >
                              <label for="study comments" class="label_stCom">Comments (2000 Characters):</label><br/>
                              <s:textarea id="comments-%{#studiesStat.index}" class="col-md-12 form-control input_stCom"  
                              		value="%{#study.comments}"
									name="instCertification.studies[%{#studiesIdx}].comments" 
									 rows="3"></s:textarea>
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
                      <input type="button" id="btnAdd" value="Add Another Study" onClick="addStudy()"class="btn btn-default">
                </div>	
													
              </div> <!--  End Study Section -->	
            </div> <!--  end qSpacing> -->
				
          </div> <!--  panel body -->
        </div> <!--  Panel -->
      </div>  
		
	  <!--SAVE & NEXT BUTTONS-->
      <div class="pageNav">
        <s:if test="%{instCertification.id != null}">
      		<s:submit action="listIc" value=" Cancel " class="saved btn btn-project-primary"/>	 
        </s:if>
        <s:submit action="saveIc" value=" Save Institutional Certification " class="saved btn btn-default"/>
	  </div>
        
    </s:form>

<s:include value="/jsp/content/dulSetTemplate.jsp"/>
<script type="text/javascript"
	src="<s:url value="/controllers/institutional.js" />"></script>

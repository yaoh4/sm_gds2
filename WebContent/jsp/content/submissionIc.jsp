<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<div class="content">
    
    <!--Begin Form -->
    <form id="institutional_form" name="instititional_form" action="/post" method="post">
      
      <!-- Page navbar -->
      <div class="pageNav">
        <!--<div><img src="images/legend.gif" alt="legend for progress icons" width="206px"></div>-->
        <input name="" type="button" value=" Save " class="saved btn btn-default">
        
        <input name="" type="button" value=" Save &amp; Next &raquo;  " class="btn btn-project-primary" onclick="window.location.href='dataSharing.htm'">
      </div>


 	  <!-- Begin Panel -->
      <div class="col-md-12">
        <div class="panel  project-panel-primary">
          
          <div class="panel-heading">
            <div class="pheader"><h4>Add Institutional Certification(s)</h4></div>
            <div class="statusWrapper">
              <div class="status"><a href="#" class="statusLink">Generate Missing Data Report</a> &nbsp; &nbsp;</div>
              <div class="statusIcon">
                <a href="#" class="tooltip">
                  <img src="images/inprogress.png" alt="In Progress" />
                  <span>
                    <img class="callout" src="images/callout_black.gif" />
                    <strong>Legend:</strong><br />
                    <img src="images/legend.gif" />
                    
                  </span>
                </a>
              </div>
            </div>
          </div>

          <s:hidden name="projectId" value="%{#request.projectId}" />	
		
          <div class="panel-body">
		
            <div style="float: right;" class="question"><a href="https://gds.nih.gov/Institutional_Certifications.html" target="_blank">Institutional Certifications&nbsp;<i class="fa fa-external-link" aria-hidden="true"></i></a></div>
            <p style="font-size: 12px;  margin-top: 5px;">Note: <i class="fa fa-asterisk" aria-hidden="true"></i>&nbsp;Asterisk indicates a required field   </p>
            <p>&nbsp;</p>
            
            <div class="qSpacing">
            
              <!--  File Upload --> 					
              <p class="question"><i class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>File Uploader Will go here</p>
              <div class="radio">	
			  </div>
		
			  <div class="form-group row">
                <div class="col-xs-3">
                  <label for="Approved by GPA">Approved by GPA</label>
                  <s:select name="instCertification.gpaApprovalCode"
                  	value="instCertification.gpaApprovalCode"
                    class="c-select form-control"
                    list="%{@gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper@getLookupDropDownList(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@IC_APPROVED_BY_GPA_LIST)}"
                    listKey="optionKey" listValue="optionValue" id="GPA"
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
                  
                <div class="col-xs-3">
                  <label for="Project Submission Status">IC Memo for Use in Future Projects?</label>
                  <s:select name="instCertification.futureProjectUseFlag"
                    value="instCertification.futureProjectUseFlag"
                    class="c-select form-control"
                    list="%{@gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper@getLookupDropDownList(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@IC_FOR_FUTURE_USE_LIST)}"
                    listKey="optionKey" listValue="optionValue" id="Study"
                    emptyOption="true"/>
                </div>
              </div>
	
			  <!--  IC comments -->
	 		  <div class="form-group row  col-xs-12">
                <label for="comment">Comments:</label><br/>
                <textarea class="col-md-12 form-control" rows="3" name="comments" ></textarea>
			  </div>
	
			  <!--Begin STUDY SECTION-->
			
              <div class="form-group row col-xs-12" id="sections">
			    <div id="entry1" class="clonedInput">
                  <div class="panel-group" id="accordion">
				    <div class="panel panel-default">	
																
                      <s:iterator status="studiesStat" var="study" value="instCertification.studies">
					    <s:set name="studiesIdx" value="#studiesStat.index" />
					  		
					    <!--  STUDY SECTION HEADER  -->
					  
                        <div class="panel-heading header">
                          <h4 class="panel-title ">
                            <a class="study" href="#collapseOne" id="entry1_study" name="entry1_study">
                              <i class="fa fa-minus-square" aria-hidden="true"></i>&nbsp;
                              Study #1
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
                              	    id="studyName%{#studiesStat.index}"  
                              		name="instCertification.studies[<s:property value='#studiesStat.index'/>].studyName" 
                              		value="${study.studyName}"/>	
                              </div>
                              					
                              <div class="col-xs-3">
                                <label class="label_in" for="Provisional or Final?">Institution</label>
                                <input type="text" class="form-control input_in" placeholder="Full Name Institution"
                              		id="institution%{#studiesStat.index}"
									name="instCertification.studies[<s:property value='#studiesStat.index'/>].institution"
									value="${study.institution}"/>
                              
                              </div>
                              <div class="col-xs-3" id="DULv">
                                <label for="Data Use Limitation(s) Verified?" class="label_dulV">Data Use Limitation(s) Verified?</label>
                                <s:select name="instCertification.studies[<s:property value='#studiesStat.index'/>].dulVerificationId"
                        			value="study.dulVerificationId"
                        			class="mn" style="width: 120px;"
                        			list="%{@gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper@getLookupDropDownList(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@IC_DUL_VERIFIED_LIST)}"
                        			listKey="optionKey" listValue="optionValue" id="dulVerified"
                       				emptyOption="true"/>
                              </div>
                            </div> <!--end row-->			
                            <div class="form-group row  col-xs-12" >
                              <label for="study comments" class="label_stCom">Comments:</label><br/>
                              <s:textarea id="comments%{#studiesStat.index}" class="col-md-12 form-control input_stCom"  
                              		value="%{#study.comments}"
									name="instCertification.studies[<s:property value='#studiesStat.index'/>].comments" 
									 rows="3"></s:textarea>
                            </div> <!--end row-->
                            <p>&nbsp;</p>
                                			
                            <div class="form-group row col-xs-12" id="DULinfo">
                              <p><span class="question">
                                <H4>Data Use Limitation(s)</h4></span><br/>
                                 You may add up to 10 DULs
                              </p>
                            </div>
														
							<s:include value="/jsp/content/manageDuls.jsp"/>																
																	
                          </div> <!-- End Panel body -->
			            </div> <!-- End Collapse 1 -->
                      </s:iterator>
										
                    </div> <!--  End study panel -->
                  </div> <!--  End panel group -->
                </div> <!--  End cloned input-->	
							
								
                <div id="addDelButtons">
                      <input type="button" id="btnAdd" value="Add Another Study" class="btn btn-default">
                </div>	
													
              </div> <!--  End Study Section -->	
            </div> <!--  end qSpacing> -->
				
          </div> <!--  panel body -->
        </div> <!--  Panel -->
      </div>  
		
	  <!--SAVE & NEXT BUTTONS-->
      <div class="pageNav">
          <input name="" type="button" value=" Save Institutional Certification "  id="institutionalCert_saved" class="btn btn-project-primary" onclick="window.location.href='dataSharingDashboard.htm'">
      </div>
        
    </form>
	
</div>


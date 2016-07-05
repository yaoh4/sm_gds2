<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<div class="content">
	
<!--Begin Form -->
    <form id="ic_dashboard_form" name="ic_dashboard_form" namespace = "manage" method="post"
      enctype="multipart/form-data" action="listIc"  role="form">
      <div class="pageNav">
          <s:submit action="saveIcList" value=" Save " class="saved btn btn-project-primary"/>
          <s:submit action="saveIcListAndNext" value=" Save and Next " class="saved btn btn-project-primary"/>	
      </div>
      
      <s:hidden name="projectId" id="projectId" value="%{project.id}"/>
      <s:hidden name="project.createdBy" value="%{project.createdBy}"/>
      
      
      <!-- Begin Panel -->
      <div class="col-md-12">
        <div class="panel  project-panel-primary">
          
          <div class="panel-heading">
            <div class="pheader"><h4>Institutional Certification Status</h4></div>
            <div class="statusWrapper">
              <div class="status"><a href="#" class="statusLink">Generate Missing Data Report</a> &nbsp; &nbsp;</div>
              <div class="statusIcon"> 
                <a href="#" class="tooltip"><img src="images/inprogress.png" alt="In Progress" />
                  <span>
                    <img class="callout" src="images/callout_black.gif" />
                    <strong>Legend:</strong><br />  
                    <img src="images/legend.gif" /> 
                  </span>
                </a>
              </div>
            </div>
          </div><!--end header-->
          
          
          <div class="panel-body">
           
            <div style="float: right;" class="question">
              <a href="https://gds.nih.gov/Institutional_Certifications.html" target="_blank">Institutional Certifications&nbsp;
                <i class="fa fa-external-link" aria-hidden="true"></i>
              </a>
            </div><br/>
          
            <p class="question" style="display:inline;">Have you received and reviewed all Institutional Certifications?&nbsp; &nbsp; &nbsp;
              <div style="display:none;" id="addICBtn">
                <s:submit action="editIc" id="addIC" value=" Add Another Institutional Certification " class="saved btn btn-project-primary"/>
              </div>
            </p>
                
            <div class="radio form-group">
              <label><input type="radio"  name="project.certificationCompleteFlag" value="Y" id="reviewedYes">Yes</label>
            </div>
            <div class="radio form-group">
              <label><input type="radio"  name="project.certificationCompleteFlag" value="N" id="reviewedNo">No</label>
            </div>
          
            <p>&nbsp;</p>

            <table style="width: 90%;" cellpadding="0px" cellspacing="0" class="table table-bordered table-striped">
              <tr class="modalTheader">
                <th class="tableHeader" align="center" width="60%">Institutional Certification Document</th>
                <th class="tableHeader" align="center" width="30%">Date Uploaded</th>
                <th class="tableHeader" align="center" width="10%">Actions</th>
              </tr>
                    
              <s:iterator status="icStat" var="ic" value="project.institutionalCertifications">
                <s:set name="icIdx" value="#icStat.index" />
                
                <!--  FILE DISPLAY AND ICONS ROW -->    
                <tr  data-id="${ic.id}">
                  <td style="white-space: nowrap">
                    <i class="fa fa-file-word-o" aria-hidden="true"></i>
                    <s:a href="javascript:openDocument(%{#ic.documents[0].id})">
                      <s:property value="%{#ic.documents[0].fileName}" />
                     </s:a>
                  </td>
                
                  <td style="white-space: nowrap"> 
                    <s:date name="%{#ic.documents[0].uploadedDate}" format="MMM dd yyyy hh:mm:ss a" />
                  </td>
                      
                  <td style="white-space: nowrap">
                    <a href="#" class="icDetails" id="icDetails${ic.id}">
                      <i class="fa fa-eye fa-lg" aria-hidden="true" alt="view" title="view"></i>
                    </a>&nbsp;&nbsp;&nbsp;
                    <a href="/gds/manage/editIc.action?instCertId=${ic.id}&projectId=${project.id}">
                      <i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="edit" title="edit"></i>&nbsp;
                    </a>&nbsp;&nbsp;&nbsp;
                    <a href="#" class="btnDelete">
                      <i class="fa fa-trash fa-lg" aria-hidden="true" alt="delete" title="delete"></i>
                    </a>
                  </td>
                </tr>
                        
                             
                <!--Begin view details-->
                <tr>
			      <td colspan="3">
                    <div id="contentDivImg${ic.id}" style="display: none">
                      <table width="100%" class="tBorder2" cellspacing="3">
                        <tr>
                          <td><span class="question">Approved by GPA: </span>${ic.gpaApprovalCode}</td>
						  <td><span class="question">Provisional or Final? </span>${ic.provisionalFinalCode}</td>
						  <td><span class="question">Study for use in Future Projects? </span>${ic.futureProjectUseFlag}</td>
                        </tr>
                        <tr>
						  <td colspan="6">&nbsp;</td>
                        </tr>
                        <tr>
                          <td colspan="6" class="question">Comments:</td>
                        </tr>
                        <tr>
                          <td colspan="6">
                            <textarea name="ic.comments" id="comments2" style="width: 882px; padding: 4px; opacity: 0.7;"
			                  rows="4" disabled class="normalText">${ic.comments}
						    </textarea>
						  </td>
			            </tr>
                        <tr>
                          <td colspan="3" align="left" valign="top">&nbsp;</td>
                          <td colspan="3">&nbsp;</td>
                        </tr>
                        <tr>
                          <td colspan="6">
                            <div id="container">                    
                              <s:iterator status="studiesStat" var="study" value="project.institutionalCertifications[#icStat.index].studies">
                                <s:set name="studyIdx" value="#studiesStat.index" />
                                <table width="100%">
                                  <tr>
                                    <td valign="top" class="question" style="padding-top: 7px;">${studyIdx+1})</td>
                                    <td>
                                      <table class="table-bordered" width="100%" class="study">
                                        <tr>
                                          <td>
                                            <table width="100%" cellspacing="5">
                                              <tr>
                                                <td><span class="question">Study Name: </span>${study.studyName}</td>
					                            <td align="left" valign="top">&nbsp;</td>
					                            <td><span class="question">Institution: </span>${study.institution}</td>
                                                <td><span class="question">Data Use Limitation(s) Verified? </span>${study.dulVerificationId}</td>
                                              </tr>
                                              <tr>
                                                <td colspan="6" class="question">Comments:</td>
                                              </tr>
                                              <tr>
                                                <td colspan="6">
                                                  <textarea name="comments" id="comments2" style="width: 98%; padding: 4px; opacity: 0.7;"
                                                    rows="4" disabled >${study.comments}
                                                  </textarea>
                                                </td>
                                              </tr>
                                              <tr>
                                                <td colspan="4" align="left" valign="top">&nbsp;</td>
                                              </tr>
                                              <tr>
                                                <td colspan="4" align="left" valign="top" class="question" valign="bottom">Data Use Limitation(s)</td>
                                              </tr>       
                                              
                                              <tr>
                                                <s:iterator status="dulSetStat" var="studiesDulSet" value="project.institutionalCertifications[#icStat.index].studies[#studiesStat.index].studiesDulSets">                                                         
                                                  <s:set name="dulSetIdx" value="#dulSetStat.index" />
                                                  <td colspan="1" class="row">${dulSetStat.index + 1}. ${studiesDulSet.dulChecklistSelections[0].dulChecklist.parentDulId}
                                                    <ul class="spacing">
                                                    <s:iterator status="dulStat" var="dul" value="project.institutionalCertifications[#icStat.index].studies[#studiesStat.index].studiesDulSets[#dulSetStat.index].dulChecklistSelections">
                                                      <li class="bullet" style="display: list-item">${dul.dulChecklist.displayText}</li>
                                                     </s:iterator>    
                                                     </ul>
                                                  </td>
                                                </s:iterator> 
                                              </tr>     
                                              
                                                                                                                              
                                            </table>
                                          </td>
                                        </tr>
                                      </table> <!-- study class -->
                                      </td>
                                    </tr>
                                </table> <!--study end-->
                               <p>&nbsp;</p>
                              </s:iterator> <!-- for studies -->                      
                             
                            </div> <!-- for container -->
                          
                            <p>&nbsp;</p>				        
                          </td> <!-- for colspan 6-->
					    </tr>
                      </table> <!-- for class tBorder2 -->
				    </div> <!-- for contentDivImg -->
                  </td> <!-- for colspan 3 -->
                </tr>  <!--end view H view details-->
                         
                
                
                
                
                
                    
                
              </s:iterator> <!-- ics -->
                      
            </table>
          </div> <!--end panel body-->
        </div> <!--end panel-->
      </div> 
              
              <!--SAVE & NEXT BUTTONS-->
        <div class="pageNav">
          <s:submit action="saveIcList" value=" Save " class="saved btn btn-project-primary"/>
          <s:submit action="saveIcListAndNext" value=" Save and Next " class="saved btn btn-project-primary"/>	  
        </div>
              
            


 <!-- start: Delete Coupon Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                 <i class="fa fa-exclamation-triangle fa-3x" aria-hidden="true" title="warning sign"></i>&nbsp;&nbsp;
                 <h3 class="modal-title" id="myModalLabel">Are You Sure You Want to Delete?</h3>

            </div>
            <div class="modal-body">
                 <h5>Deleting the IC will remove all data from the project and any sub-projects, including all data on associated studies and DULs.</h5>
            </div>
            <!--/modal-body-collapse -->
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" id="btnDelteYes" href="#">Delete</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
            </div>
            <!--/modal-footer-collapse -->
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

    </form>
</div><!--end column formatting div-->



<script type="text/javascript"
	src="<s:url value="/controllers/institutional_dashboard.js" />"></script>


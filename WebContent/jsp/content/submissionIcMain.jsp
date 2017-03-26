<%@ page import="gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper" %>

<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<!--Begin Form -->
    <s:form id="ic_dashboard_form" name="ic_dashboard_form" namespace="manage" method="post"
       action="listIc"  role="form">
      
    
      <!-- Begin Panel -->
      <div class="col-md-12">
        <div class="panel  project-panel-primary">
          
          <div class="panel-heading">
            <div class="pheader"><h4>Institutional Certification Status</h4></div>
            <div class="statusWrapper">
              <s:if test="%{!pageStatusCode.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PAGE_STATUS_CODE_COMPLETED)}">         		           		      
    		    <div class="status">
    		      <a href="#" onclick="openMissingDataReport(${project.id}, '/gds/manage/viewMissingIcListData.action?')" class="statusLink">Generate Missing Data Report</a> &nbsp; &nbsp;
    		    </div>
    		  </s:if>
              <s:include value="/jsp/content/pageStatus.jsp"/>           	
            </div>
             
          </div><!--end header-->   
          
          <div class="panel-body">
           
           <div style="display:inline" id="addICBtn">
                <button type="button" value=" Add Study " class="saved btn btn-project-primary" onclick="location.href = 'studydemo.htm';">Add Study(ies)</button>
              </div>
             <div style="display: inline;" id="addICBtn">
                <button type="button" id="addIC" name="action:editIc" value=" Add Institutional Certification " class="saved btn btn-project-primary" onclick="location.href = 'ICdemo.htm';">Add Institutional Certification</button>

           </div>
       
      <br>
      
      
      <br/>&nbsp; 

                 <div class="container" style="width: 100%; padding-left: 0px;">
  <ul class="nav nav-tabs">
    <li class="active"><a data-toggle="tab" href="#studies">Studies</a></li>
    <li><a data-toggle="tab"  href="#IC">Institutional Certifications</a></li>
   
  </ul>

       <div class="tab-content">
      <div id="studies" class="tab-pane fade in active" >

                       <table style="width: 100%; font-size: 14px" cellpadding="0px" cellspacing="0" class="table table-bordered table-striped">
              <tbody><tr class="modalTheader">
               <!--  Show this column header only for subproject -->
                <th  class="tableHeader"  align="center" width="25%">Study Name</th>                      
                <th class="tableHeader" align="center" width="25%">Institution</th>
                <th>Received</th>
                <th>DULs</th>
                
                <th>Document</th>
                <th align="center">Comments</th>         
                <th id="actionColumn" class="tableHeader" style="" align="center" width="1%">Actions</th>
              </tr> 
              
              <tr>
              <td >PLCO</td>
              <td >NCI</td> 
              <td >Yes</td>
               
              <td><table class="table table-striped table-bordered DUL">
                <th>Type</th>
                <th>Appendix</th>
                <tr><td>Disease-specific Cancer (MDS)</td>
              
              <td><div style="position: relative;"><a href="#" class="hvrlink" target="_blank">View</a><div class="details-pane">
              <h3 class="title">Appendix: Disease-specific Cancer (MDS)</h3>
              <p class="desc">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque purus lorem, sagittis vitae lacus id, dictum malesuada magna. Donec a sem et arcu imperdiet ullamcorper vitae ut est. Sed sapien eros, pulvinar fringilla ex eu, vehicula pellentesque velit. Mauris auctor enim vel tincidunt dictum. Maecenas elementum tempus velit vel facilisis. </p>
            </div><!-- @end .details-pane --></div></td> 
          </tr>

          <tr><td>General Research Use (IRB, PUB)</td>
              
              <td>None</td> 
          </tr>
        </table>
      </td>

              <td  style="text-align: center;"><a href="#" onclick="openMissingDataReport(415, '/gds/manage/viewMissingIcData.action?instCertId=432&amp;')"><i class="fa fa-file-text fa-lg" aria-hidden="true" alt="view" title="view"></i></a> </td> 
              <td style="text-align: center;"><div style="position: relative;"><a href="#" class="hvrlink" target="_blank">View</a><div class="details-pane">
              <h4 class="title">Comments</h4>
              <p class="desc">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque purus lorem, sagittis vitae lacus id, dictum malesuada magna. Donec a sem et arcu imperdiet ullamcorper vitae ut est. Sed sapien eros, pulvinar fringilla ex eu, vehicula pellentesque velit. Mauris auctor enim vel tincidunt dictum. Maecenas elementum tempus velit vel facilisis. </p>
            </div><!-- @end .details-pane --></div></td> 
              <td width="2%"  class="editDeleteBtns" style="white-space: nowrap;">
                    
                    <!--  Do not show edit and delete for sub-project -->
                      <a class="btnEdit" href="editstudydemo.htm">
                        <i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="edit" title="Edit"></i>&nbsp;
                      </a>&nbsp;&nbsp;&nbsp;
                                       
                  </td>
                </tr>
<!--Row 2 -->
                <tr>
              <td >PATBC</td>
              <td >NCI</td> 
              <td >No</td>
               
              <td>None</td> 
          

              <td  style="text-align: center;">N/A</td> 
              <td style="text-align: center;">None</td> 
              <td width="2%"  class="editDeleteBtns" style="white-space: nowrap;">
                    
                    <!--  Do not show edit and delete for sub-project -->
                      <a class="btnEdit" href="/gds/manage/editIc.action?instCertId=311&amp;projectId=415">
                        <i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="edit" title="Edit"></i>&nbsp;
                      </a>&nbsp;&nbsp;&nbsp;
                      <a class="btnDelete" href="#">
                        <i class="fa fa-trash fa-lg" aria-hidden="true" alt="delete" title="Delete"></i>
                      </a>                   
                  </td>
                </tr>
 <!--Row 3 -->              
                           <tr>
              <td >NHS</td>
              <td >Harvard</td> 
              <td >Yes</td>
               
              <td><table class="table table-striped table-bordered DUL">
                <th>Type</th>
                <th>Appendix</th>
                <tr><td>General Research Use (NPU, IRB, PUB)</td>
              
              <td><div style="position: relative;"><a href="#" class="hvrlink" target="_blank">View</a><div class="details-pane">
              <h3 class="title">Appendix: General Research Use (NPU, IRB, PUB)</h3>
              <p class="desc">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque purus lorem, sagittis vitae lacus id, dictum malesuada magna. Donec a sem et arcu imperdiet ullamcorper vitae ut est. Sed sapien eros, pulvinar fringilla ex eu, vehicula pellentesque velit. Mauris auctor enim vel tincidunt dictum. Maecenas elementum tempus velit vel facilisis. </p>
            </div><!-- @end .details-pane --></div></td> 
          </tr>
          <tr><td>General Research Use (IRB, PUB)</td>
              
              <td>None</td> 
          </tr>
          <tr><td>Health/Medical/Biomedical</td>
              
              <td>None</td> 
          </tr>
          <tr><td>Health/Medical/Biomedical (IRB, PUB)</td>
              
              <td><div style="position: relative;"><a href="#" class="hvrlink" target="_blank">View</a><div class="details-pane">
              <h3 class="title">Appendix: Health/Medical/Biomedical (IRB, PUB)</h3>
              <p class="desc">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque purus lorem, sagittis vitae lacus id, dictum malesuada magna. Donec a sem et arcu imperdiet ullamcorper vitae ut est. Sed sapien eros, pulvinar fringilla ex eu, vehicula pellentesque velit. Mauris auctor enim vel tincidunt dictum. Maecenas elementum tempus velit vel facilisis. </p>
            </div><!-- @end .details-pane --></div></td> 
          </tr>
          <tr><td>Disease-specific Cancer (XYZ)</td>
              
              <td><div style="position: relative;"><a href="#" class="hvrlink" target="_blank">View</a><div class="details-pane">
              <h3 class="title">Appendix: Disease-specific Cancer (XYZ)</h3>
              <p class="desc">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque purus lorem, sagittis vitae lacus id, dictum malesuada magna. Donec a sem et arcu imperdiet ullamcorper vitae ut est. Sed sapien eros, pulvinar fringilla ex eu, vehicula pellentesque velit. Mauris auctor enim vel tincidunt dictum. Maecenas elementum tempus velit vel facilisis. </p>
            </div><!-- @end .details-pane --></div></td> 
          </tr>
          <tr><td>Disease-specific Cancer (ABC)</td>
              
              <td><div style="position: relative;"><a href="#" class="hvrlink" target="_blank">View</a><div class="details-pane">
              <h3 class="title">Appendix: Disease-specific Cancer (ABC)</h3>
              <p class="desc">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque purus lorem, sagittis vitae lacus id, dictum malesuada magna. Donec a sem et arcu imperdiet ullamcorper vitae ut est. Sed sapien eros, pulvinar fringilla ex eu, vehicula pellentesque velit. Mauris auctor enim vel tincidunt dictum. Maecenas elementum tempus velit vel facilisis. </p>
            </div><!-- @end .details-pane --></div></td> 
          </tr>
          <tr><td>Other</td>
              
              <td><div style="position: relative;"><a href="#" class="hvrlink" target="_blank">View</a><div class="details-pane">
              <h3 class="title">Other Explaination</h3>
              <p class="desc">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque purus lorem, sagittis vitae lacus id, dictum malesuada magna. Donec a sem et arcu imperdiet ullamcorper vitae ut est. Sed sapien eros, pulvinar fringilla ex eu, vehicula pellentesque velit. Mauris auctor enim vel tincidunt dictum. Maecenas elementum tempus velit vel facilisis. </p>
            </div><!-- @end .details-pane --></div></td> 
          </tr>
        </table>
      </td>

              <td  style="text-align: center;"><a href="#" onclick="openMissingDataReport(415, '/gds/manage/viewMissingIcData.action?instCertId=432&amp;')"><i class="fa fa-file-text fa-lg" aria-hidden="true" alt="view" title="view"></i></a></td> 
              <td style="text-align: center;"><div style="position: relative;"><a href="#" class="hvrlink" target="_blank">View</a><div class="details-pane">
              <h4 class="title">Comments</h4>
              <p class="desc">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque purus lorem, sagittis vitae lacus id, dictum malesuada magna. Donec a sem et arcu imperdiet ullamcorper vitae ut est. Sed sapien eros, pulvinar fringilla ex eu, vehicula pellentesque velit. Mauris auctor enim vel tincidunt dictum. Maecenas elementum tempus velit vel facilisis. </p>
            </div><!-- @end .details-pane --></div></td> 
              <td width="2%"  class="editDeleteBtns" style="white-space: nowrap;">
                    
                    <!--  Do not show edit and delete for sub-project -->
                      <a class="btnEdit" href="/gds/manage/editIc.action?instCertId=311&amp;projectId=415">
                        <i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="edit" title="Edit"></i>&nbsp;
                      </a>&nbsp;&nbsp;&nbsp;
                                        
                  </td>
                </tr>
               
<!--Row 4 -->
                <tr>
              <td >Study with a long name: Lorem ipsum dolor sit amet, consectetur adipiscing elit imperdiet ullamcorper vitae ut est</td>
              <td >John Hopkins University</td> 
              <td >Yes</td>
               
              <td>None</td> 
          

              <td  style="text-align: center;"><a href="#" onclick="openMissingDataReport(415, '/gds/manage/viewMissingIcData.action?instCertId=432&amp;')"><i class="fa fa-file-text fa-lg" aria-hidden="true" alt="view" title="view"></i></a></td> 
              <td style="text-align: center;"><div style="position: relative;"><a href="#" class="hvrlink" target="_blank">View</a><div class="details-pane">
              <h4 class="title">Comments</h4>
              <p class="desc">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque purus lorem, sagittis vitae lacus id, dictum malesuada magna. Donec a sem et arcu imperdiet ullamcorper vitae ut est. Sed sapien eros, pulvinar fringilla ex eu, vehicula pellentesque velit. Mauris auctor enim vel tincidunt dictum. Maecenas elementum tempus velit vel facilisis. </p>
            </div><!-- @end .details-pane --></div></td> 
              <td width="2%"  class="editDeleteBtns" style="white-space: nowrap;">
                    
                    <!--  Do not show edit and delete for sub-project -->
                      <a class="btnEdit" href="/gds/manage/editIc.action?instCertId=311&amp;projectId=415">
                        <i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="edit" title="Edit"></i>&nbsp;
                      </a>&nbsp;&nbsp;&nbsp;
                                        
                  </td>
                </tr>


              
                </table>

            </div>


    <div id="IC" class="tab-pane fade in">
     
                 <!-- Begin IC Table  -->  
                <table style="width: 100%; font-size: 14px" cellpadding="0px" cellspacing="0" class="table table-bordered">
              <tbody><tr class="modalTheader">
               <!--  Show this column header only for subproject -->
                <th id="subprojectColumn" class="tableHeader" style="display:none;" align="center" width="10%">Select 
                 &nbsp; <a href="#" id="popover" style="font-size: 12px;">
                 <i class="helpfileSubProject fa fa-question-circle fa-1x" aria-hidden="true"></i></a>
                </th>                      
                <th class="tableHeader" align="center" width="40%">Institutional Certification Document</th>
                <th class="tableHeader projectColumn" align="center" width="10%">Status</th>
                <th class="tableHeader projectColumn" align="center" width="10%">Missing Data</th>
                <th class="tableHeader" align="center" width="20%">Date Uploaded</th>
                <th class="tableHeader" align="center" width="20%">Uploaded By</th>                 
                <th id="actionColumn" class="tableHeader" style="" align="center" width="10%">Actions</th>
              </tr>
               
              <input type="hidden" name="" value="5" id="icListSize">     
              
               <tr data-id="433">
                 
                 <!--  Show this column only for subproject -->
                    <td class="subprojectSelect" style="white-space: nowrap;display:none;">                 
                    <input class="icSelect" type="checkbox" name="ic-selected" id="ic433" value="433">  
                        <input type="hidden" name="ifIcSelected" value="false" id="selectIcs">     
                    </td>

                
                  <td style="white-space: nowrap">
                    <a href="#" class="icDetails" id="icDetails433">
                      <i class="expand fa fa-lg fa-plus-square" id="433expand" aria-hidden="true" alt="Details" title="Details"></i>
                    </a>&nbsp;&nbsp;&nbsp;<a href="javascript:openDocument(947)">GDS notifications templates (1).docx</a>
                  </td>
                  
                    
                <td class="projectColumn" style="white-space: nowrap">
                <input type="hidden" name="" value="INPROGRESS" id="icReg0">              
                <div id="icDiv0" class="searchProgess"><img src="../images/inprogress.png" alt="In Progress" width="18px" height="18px" title="In Progress"></div>
                  </td>
                  
                  <td class="projectColumn" style="white-space: nowrap">
                   
                   <a href="#" onclick="openMissingDataReport(415, '/gds/manage/viewMissingIcData.action?instCertId=433&amp;')"><i class="fa fa-file-text fa-lg" aria-hidden="true" alt="view" title="view"></i></a> &nbsp; &nbsp;
                  
                  </td>
                
                  <td style="white-space: nowrap"> 
                    Dec 22 2016 11:21:49 AM
                  </td>
                  
                  <td style="white-space: nowrap"> 
                     Yuri Dinh
                  </td>
                      
                  <td class="editDeleteBtns" style="white-space: nowrap;">
                    
                    <!--  Do not show edit and delete for sub-project -->
                      <a class="btnEdit" href="/gds/manage/editIc.action?instCertId=433&amp;projectId=415">
                        <i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="edit" title="Edit"></i>&nbsp;
                      </a>&nbsp;&nbsp;&nbsp;
                                       
                  </td>
                </tr>
                    
                <!--Begin view details-->
               <tr class="remove433">
            <td colspan="6">
                    <div id="contentDivImg433" style="display: none;">
                      <table width="100%" class="tBorder2" cellspacing="3">
                        <tbody><tr>
                          <td><span class="question">Provisional or Final? </span></td>
                          <td><span class="question">Approved by GPA: </span></td>
              <td><span class="question">Study for use in Future Projects? </span></td>
                        </tr>
                        
                        
                  
                        <tr>
                          <td colspan="3" align="left" valign="top">&nbsp;</td>
                          <td colspan="3">&nbsp;</td>
                        </tr>
                        
                        <tr>
                          <td colspan="6">           
                              
                                
                                <table width="100%">
                                  <tbody><tr>
                                    <td valign="top" class="question" style="width: 35px;"><p class="number">1</p></td>
                                    <td>
                                      <table class="table table-bordered" width="100%">
                                        <tbody><tr>
                                          <td>
                                            <table width="100%" cellspacing="5">
                                              <tbody><tr>
                                                <td><span class="question">Study Name: </span>Adding one more IC again</td>
                                   <!--    <td align="left" valign="top">&nbsp;</td> -->
                                      <td><span class="question">Institution: </span></td>
                                                <td><span class="question">Data Use Limitation(s) Verified? </span></td>
                                              </tr>
                                                                                    
                                              
                                              
                                              
                                              
                                              <tr><td colspan="4" align="left" valign="top">&nbsp;</td></tr>
                                              <tr>
                                                <td colspan="4" align="left" valign="top" class="question">Data Use Limitation(s)</td>
                                              </tr>       
                                              
                                              <tr>
                                                <td colspan="4">
                                                  <table class="table table-striped">
                                                    
                                                      
                                                      <tbody><tr>
                                                        <td>
                                                          <span class="question">
                                                            1. 
                                                            
                                                          </span>
                                                          
                                                          <br>
                                                          
                                                        </td>
                                                      </tr>
                                                     
                                                  </tbody></table>
                                                </td>
                                              </tr>
                                               <!-- check for DULs present-->   
                                                                                                                                                                            
                                            </tbody></table>
                                          </td>
                                        </tr>
                                      </tbody></table> <!-- study class -->
                                    </td>
                                  </tr>
                                </tbody></table> <!--study end-->
                             <!--    <p>&nbsp;</p> -->
                               <!-- for studies -->                               
                          </td> <!-- for colspan 6-->
              </tr>
                      </tbody></table> <!-- for class tBorder2 -->
            </div> <!-- for contentDivImg -->
                  </td> <!-- for colspan 3 -->
                </tr>  <!--end view H view details-->
                         
                
                
                
                
                
                    
                
              
               <tr data-id="432">
                 
                 <!--  Show this column only for subproject -->
                    <td class="subprojectSelect" style="white-space: nowrap;display:none;">                 
                    <input class="icSelect" type="checkbox" name="ic-selected" id="ic432" value="432">  
                        <input type="hidden" name="ifIcSelected" value="false" id="selectIcs">     
                    </td>

                
                  <td style="white-space: nowrap">
                    <a href="#" class="icDetails" id="icDetails432">
                      <i class="expand fa fa-plus-square fa-lg" id="432expand" aria-hidden="true" alt="Details" title="Details"></i>
                    </a>&nbsp;&nbsp;&nbsp;<a href="javascript:openDocument(696)">repository record disappears upon unchecking and saving.docx</a>
                  </td>
                  
                    
                <td class="projectColumn" style="white-space: nowrap">
                <input type="hidden" name="" value="INPROGRESS" id="icReg1">              
                <div id="icDiv1" class="searchProgess"><img src="../images/inprogress.png" alt="In Progress" width="18px" height="18px" title="In Progress"></div>
                  </td>
                  
                  <td class="projectColumn" style="white-space: nowrap">
                   
                   <a href="#" onclick="openMissingDataReport(415, '/gds/manage/viewMissingIcData.action?instCertId=432&amp;')"><i class="fa fa-file-text fa-lg" aria-hidden="true" alt="view" title="view"></i></a> &nbsp; &nbsp;
                  
                  </td>
                
                  <td style="white-space: nowrap"> 
                    Dec 22 2016 11:19:11 AM
                  </td>
                  
                  <td style="white-space: nowrap"> 
                     Yuri Dinh
                  </td>
                      
                  <td class="editDeleteBtns" style="white-space: nowrap;">
                    
                    <!--  Do not show edit and delete for sub-project -->
                      <a class="btnEdit" href="/gds/manage/editIc.action?instCertId=432&amp;projectId=415">
                        <i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="edit" title="Edit"></i>&nbsp;
                      </a>&nbsp;&nbsp;&nbsp;
                      <a class="btnDelete" href="#">
                        <i class="fa fa-trash fa-lg" aria-hidden="true" alt="delete" title="Delete"></i>
                      </a>                   
                  </td>
                </tr>
                    
                <!--Begin view details-->
               <tr class="remove432">
            <td colspan="6">
                    <div id="contentDivImg432" style="display: none">
                      <table width="100%" class="tBorder2" cellspacing="3">
                        <tbody><tr>
                          <td><span class="question">Provisional or Final? </span></td>
                          <td><span class="question">Approved by GPA: </span></td>
              <td><span class="question">Study for use in Future Projects? </span></td>
                        </tr>
                        
                        
                  
                        <tr>
                          <td colspan="3" align="left" valign="top">&nbsp;</td>
                          <td colspan="3">&nbsp;</td>
                        </tr>
                        
                        <tr>
                          <td colspan="6">           
                              
                                
                                <table width="100%">
                                  <tbody><tr>
                                    <td valign="top" class="question" style="width: 35px;"><p class="number">1</p></td>
                                    <td>
                                      <table class="table table-bordered" width="100%">
                                        <tbody><tr>
                                          <td>
                                            <table width="100%" cellspacing="5">
                                              <tbody><tr>
                                                <td><span class="question">Study Name: </span>Adding one more IC</td>
                                   <!--    <td align="left" valign="top">&nbsp;</td> -->
                                      <td><span class="question">Institution: </span></td>
                                                <td><span class="question">Data Use Limitation(s) Verified? </span></td>
                                              </tr>
                                                                                    
                                              
                                              
                                              
                                              
                                              <tr><td colspan="4" align="left" valign="top">&nbsp;</td></tr>
                                              <tr>
                                                <td colspan="4" align="left" valign="top" class="question">Data Use Limitation(s)</td>
                                              </tr>       
                                              
                                              <tr>
                                                <td colspan="4">
                                                  <table class="table table-striped">
                                                    
                                                      
                                                      <tbody><tr>
                                                        <td>
                                                          <span class="question">
                                                            1. Health/Medical/Biomedical
                                                            
                                                          </span>
                                                          
                                                            :&nbsp;
                                                          
                                                          
                                                            <!-- Dont show the parent DUL in the bullet list -->
                                                            
                                                          
                                                            <!-- Dont show the parent DUL in the bullet list -->
                                                            
                                                              IRB approval required
                                                              
                                                            
                                                          <br>
                                                          
                                                        </td>
                                                      </tr>
                                                     
                                                  </tbody></table>
                                                </td>
                                              </tr>
                                               <!-- check for DULs present-->   
                                                                                                                                                                            
                                            </tbody></table>
                                          </td>
                                        </tr>
                                      </tbody></table> <!-- study class -->
                                    </td>
                                  </tr>
                                </tbody></table> <!--study end-->
                             <!--    <p>&nbsp;</p> -->
                               <!-- for studies -->                               
                          </td> <!-- for colspan 6-->
              </tr>
                      </tbody></table> <!-- for class tBorder2 -->
            </div> <!-- for contentDivImg -->
                  </td> <!-- for colspan 3 -->
                </tr>  <!--end view H view details-->
                         
                
                
                
                
                
                    
                
              
               <tr data-id="313">
                 
                 <!--  Show this column only for subproject -->
                    <td class="subprojectSelect" style="white-space: nowrap;display:none;">                 
                    <input class="icSelect" type="checkbox" name="ic-selected" id="ic313" value="313">  
                        <input type="hidden" name="ifIcSelected" value="false" id="selectIcs">     
                    </td>

                
                  <td style="white-space: nowrap">
                    <a href="#" class="icDetails" id="icDetails313">
                      <i class="expand fa fa-plus-square fa-lg" id="313expand" aria-hidden="true" alt="Details" title="Details"></i>
                    </a>&nbsp;&nbsp;&nbsp;<a href="javascript:openDocument(695)">GDS notifications templates_CC.docx</a>
                  </td>
                  
                    
                <td class="projectColumn" style="white-space: nowrap">
                <input type="hidden" name="" value="INPROGRESS" id="icReg2">              
                <div id="icDiv2" class="searchProgess"><img src="../images/inprogress.png" alt="In Progress" width="18px" height="18px" title="In Progress"></div>
                  </td>
                  
                  <td class="projectColumn" style="white-space: nowrap">
                   
                   <a href="#" onclick="openMissingDataReport(415, '/gds/manage/viewMissingIcData.action?instCertId=313&amp;')"><i class="fa fa-file-text fa-lg" aria-hidden="true" alt="view" title="view"></i></a> &nbsp; &nbsp;
                  
                  </td>
                
                  <td style="white-space: nowrap"> 
                    Dec 09 2016 07:55:14 AM
                  </td>
                  
                  <td style="white-space: nowrap"> 
                     Catherine Fishman
                  </td>
                      
                  <td class="editDeleteBtns" style="white-space: nowrap;">
                    
                    <!--  Do not show edit and delete for sub-project -->
                      <a class="btnEdit" href="/gds/manage/editIc.action?instCertId=313&amp;projectId=415">
                        <i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="edit" title="Edit"></i>&nbsp;
                      </a>&nbsp;&nbsp;&nbsp;
                      <a class="btnDelete" href="#">
                        <i class="fa fa-trash fa-lg" aria-hidden="true" alt="delete" title="Delete"></i>
                      </a>                   
                  </td>
                </tr>
                    
                <!--Begin view details-->
               <tr class="remove313">
            <td colspan="6">
                    <div id="contentDivImg313" style="display: none">
                      <table width="100%" class="tBorder2" cellspacing="3">
                        <tbody><tr>
                          <td><span class="question">Provisional or Final? </span>Final</td>
                          <td><span class="question">Approved by GPA: </span>Yes</td>
              <td><span class="question">Study for use in Future Projects? </span></td>
                        </tr>
                        
                        
                  
                        <tr>
                          <td colspan="3" align="left" valign="top">&nbsp;</td>
                          <td colspan="3">&nbsp;</td>
                        </tr>
                        
                        <tr>
                          <td colspan="6">           
                              
                                
                                <table width="100%">
                                  <tbody><tr>
                                    <td valign="top" class="question" style="width: 35px;"><p class="number">1</p></td>
                                    <td>
                                      <table class="table table-bordered" width="100%">
                                        <tbody><tr>
                                          <td>
                                            <table width="100%" cellspacing="5">
                                              <tbody><tr>
                                                <td><span class="question">Study Name: </span>Maryland Study </td>
                                   <!--    <td align="left" valign="top">&nbsp;</td> -->
                                      <td><span class="question">Institution: </span>UMD</td>
                                                <td><span class="question">Data Use Limitation(s) Verified? </span></td>
                                              </tr>
                                                                                    
                                              
                                              
                                              
                                              
                                              <tr><td colspan="4" align="left" valign="top">&nbsp;</td></tr>
                                              <tr>
                                                <td colspan="4" align="left" valign="top" class="question">Data Use Limitation(s)</td>
                                              </tr>       
                                              
                                              <tr>
                                                <td colspan="4">
                                                  <table class="table table-striped">
                                                    
                                                      
                                                      <tbody><tr>
                                                        <td>
                                                          <span class="question">
                                                            1. Health/Medical/Biomedical
                                                            
                                                          </span>
                                                          
                                                          
                                                            <!-- Dont show the parent DUL in the bullet list -->
                                                            
                                                          <br>
                                                          
                                                            &nbsp;&nbsp;&nbsp; DUL Appendix : jgjfgjkdjlgjdlkjdgkjkjsdgklj
                                                          
                                                        </td>
                                                      </tr>
                                                    
                                                      
                                                      <tr>
                                                        <td>
                                                          <span class="question">
                                                            2. Disease-specific (list specific)
                                                            
                                                              - Dissease
                                                            
                                                          </span>
                                                          
                                                          
                                                            <!-- Dont show the parent DUL in the bullet list -->
                                                            
                                                          <br>
                                                          
                                                            &nbsp;&nbsp;&nbsp; DUL Appendix : jgjkdfsjgfjdskljgkldsj
                                                          
                                                        </td>
                                                      </tr>
                                                    
                                                      
                                                      <tr>
                                                        <td>
                                                          <span class="question">
                                                            3. General Research Use
                                                            
                                                          </span>
                                                          
                                                            :&nbsp;
                                                          
                                                          
                                                            <!-- Dont show the parent DUL in the bullet list -->
                                                            
                                                          
                                                            <!-- Dont show the parent DUL in the bullet list -->
                                                            
                                                              IRB approval required
                                                              
                                                            
                                                          <br>
                                                          
                                                        </td>
                                                      </tr>
                                                    
                                                      
                                                      <tr>
                                                        <td>
                                                          <span class="question">
                                                            4. Other
                                                            
                                                              - guiodsuoguodisugoiduiougioudso
                                                            
                                                          </span>
                                                          
                                                          
                                                            <!-- Dont show the parent DUL in the bullet list -->
                                                            
                                                          <br>
                                                          
                                                        </td>
                                                      </tr>
                                                     
                                                  </tbody></table>
                                                </td>
                                              </tr>
                                               <!-- check for DULs present-->   
                                                                                                                                                                            
                                            </tbody></table>
                                          </td>
                                        </tr>
                                      </tbody></table> <!-- study class -->
                                    </td>
                                  </tr>
                                </tbody></table> <!--study end-->
                             <!--    <p>&nbsp;</p> -->
                              
                                
                                <table width="100%">
                                  <tbody><tr>
                                    <td valign="top" class="question" style="width: 35px;"><p class="number">2</p></td>
                                    <td>
                                      <table class="table table-bordered" width="100%">
                                        <tbody><tr>
                                          <td>
                                            <table width="100%" cellspacing="5">
                                              <tbody><tr>
                                                <td><span class="question">Study Name: </span>Washington Study</td>
                                   <!--    <td align="left" valign="top">&nbsp;</td> -->
                                      <td><span class="question">Institution: </span>Wash University</td>
                                                <td><span class="question">Data Use Limitation(s) Verified? </span>Yes</td>
                                              </tr>
                                                                                    
                                              
                                              
                                              
                                              
                                              <tr><td colspan="4" align="left" valign="top">&nbsp;</td></tr>
                                              <tr>
                                                <td colspan="4" align="left" valign="top" class="question">Data Use Limitation(s)</td>
                                              </tr>       
                                              
                                              <tr>
                                                <td colspan="4">
                                                  <table class="table table-striped">
                                                    
                                                      
                                                      <tbody><tr>
                                                        <td>
                                                          <span class="question">
                                                            1. General Research Use
                                                            
                                                          </span>
                                                          
                                                          
                                                            <!-- Dont show the parent DUL in the bullet list -->
                                                            
                                                          <br>
                                                          
                                                        </td>
                                                      </tr>
                                                     
                                                  </tbody></table>
                                                </td>
                                              </tr>
                                               <!-- check for DULs present-->   
                                                                                                                                                                            
                                            </tbody></table>
                                          </td>
                                        </tr>
                                      </tbody></table> <!-- study class -->
                                    </td>
                                  </tr>
                                </tbody></table> <!--study end-->
                             <!--    <p>&nbsp;</p> -->
                               <!-- for studies -->                               
                          </td> <!-- for colspan 6-->
              </tr>
                      </tbody></table> <!-- for class tBorder2 -->
            </div> <!-- for contentDivImg -->
                  </td> <!-- for colspan 3 -->
                </tr>  <!--end view H view details-->
                         
                
                
                
                
                
                    
                
              
               <tr data-id="311">
                 
                 <!--  Show this column only for subproject -->
                    <td class="subprojectSelect" style="white-space: nowrap;display:none;">                 
                    <input class="icSelect" type="checkbox" name="ic-selected" id="ic311" value="311">  
                        <input type="hidden" name="ifIcSelected" value="false" id="selectIcs">     
                    </td>

                
                  <td style="white-space: nowrap">
                    <a href="#" class="icDetails" id="icDetails311">
                      <i class="expand fa fa-plus-square fa-lg" id="311expand" aria-hidden="true" alt="Details" title="Details"></i>
                    </a>&nbsp;&nbsp;&nbsp;<a href="javascript:openDocument(589)">GDS mini UAT items with LOE.docx</a>
                  </td>
                  
                    
                <td class="projectColumn" style="white-space: nowrap">
                <input type="hidden" name="" value="INPROGRESS" id="icReg3">              
                <div id="icDiv3" class="searchProgess"><img src="../images/inprogress.png" alt="In Progress" width="18px" height="18px" title="In Progress"></div>
                  </td>
                  
                  <td class="projectColumn" style="white-space: nowrap">
                   
                   <a href="#" onclick="openMissingDataReport(415, '/gds/manage/viewMissingIcData.action?instCertId=311&amp;')"><i class="fa fa-file-text fa-lg" aria-hidden="true" alt="view" title="view"></i></a> &nbsp; &nbsp;
                  
                  </td>
                
                  <td style="white-space: nowrap"> 
                    Dec 07 2016 03:48:15 PM
                  </td>
                  
                  <td style="white-space: nowrap"> 
                     Catherine Fishman
                  </td>
                      
                  <td class="editDeleteBtns" style="white-space: nowrap;">
                    
                    <!--  Do not show edit and delete for sub-project -->
                      <a class="btnEdit" href="/gds/manage/editIc.action?instCertId=311&amp;projectId=415">
                        <i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="edit" title="Edit"></i>&nbsp;
                      </a>&nbsp;&nbsp;&nbsp;
                      <a class="btnDelete" href="#">
                        <i class="fa fa-trash fa-lg" aria-hidden="true" alt="delete" title="Delete"></i>
                      </a>                   
                  </td>
                </tr>
                    
                <!--Begin view details-->
               <tr class="remove311">
            <td colspan="6">
                    <div id="contentDivImg311" style="display: none">
                      <table width="100%" class="tBorder2" cellspacing="3">
                        <tbody><tr>
                          <td><span class="question">Provisional or Final? </span></td>
                          <td><span class="question">Approved by GPA: </span></td>
              <td><span class="question">Study for use in Future Projects? </span></td>
                        </tr>
                        
                        
                  
                        <tr>
                          <td colspan="3" align="left" valign="top">&nbsp;</td>
                          <td colspan="3">&nbsp;</td>
                        </tr>
                        
                        <tr>
                          <td colspan="6">           
                              
                                
                                <table width="100%">
                                  <tbody><tr>
                                    <td valign="top" class="question" style="width: 35px;"><p class="number">1</p></td>
                                    <td>
                                      <table class="table table-bordered" width="100%">
                                        <tbody><tr>
                                          <td>
                                            <table width="100%" cellspacing="5">
                                              <tbody><tr>
                                                <td><span class="question">Study Name: </span>Study 1</td>
                                   <!--    <td align="left" valign="top">&nbsp;</td> -->
                                      <td><span class="question">Institution: </span>Institution</td>
                                                <td><span class="question">Data Use Limitation(s) Verified? </span></td>
                                              </tr>
                                                                                    
                                              
                                              
                                              
                                              
                                              <tr><td colspan="4" align="left" valign="top">&nbsp;</td></tr>
                                              <tr>
                                                <td colspan="4" align="left" valign="top" class="question">Data Use Limitation(s)</td>
                                              </tr>       
                                              
                                              <tr>
                                                <td colspan="4">
                                                  <table class="table table-striped">
                                                    
                                                      
                                                      <tbody><tr>
                                                        <td>
                                                          <span class="question">
                                                            1. 
                                                            
                                                          </span>
                                                          
                                                          <br>
                                                          
                                                        </td>
                                                      </tr>
                                                     
                                                  </tbody></table>
                                                </td>
                                              </tr>
                                               <!-- check for DULs present-->   
                                                                                                                                                                            
                                            </tbody></table>
                                          </td>
                                        </tr>
                                      </tbody></table> <!-- study class -->
                                    </td>
                                  </tr>
                                </tbody></table> <!--study end-->
                             <!--    <p>&nbsp;</p> -->
                               <!-- for studies -->                               
                          </td> <!-- for colspan 6-->
              </tr>
                      </tbody></table> <!-- for class tBorder2 -->
            </div> <!-- for contentDivImg -->
                  </td> <!-- for colspan 3 -->
                </tr>  <!--end view H view details-->
                         
                
                
                
                
                
                    
                
              
               <tr data-id="247">
                 
                 <!--  Show this column only for subproject -->
                    <td class="subprojectSelect" style="white-space: nowrap;display:none;">                 
                    <input class="icSelect" type="checkbox" name="ic-selected" id="ic247" value="247">  
                        <input type="hidden" name="ifIcSelected" value="false" id="selectIcs">     
                    </td>

                
                  <td style="white-space: nowrap">
                    <a href="#" class="icDetails" id="icDetails247">
                      <i class="expand fa fa-plus-square fa-lg" id="247expand" aria-hidden="true" alt="Details" title="Details"></i>
                    </a>&nbsp;&nbsp;&nbsp;<a href="javascript:openDocument(553)">Search Results Parse Error on Change Page Size from 5 to 25 When Results Set Empty..docx</a>
                  </td>
                  
                    
                <td class="projectColumn" style="white-space: nowrap">
                <input type="hidden" name="" value="COMPLETED" id="icReg4">             
                <div id="icDiv4" class="searchProgess"><img src="../images/complete.png" alt="Completed" width="18px" height="18px" title="Completed"></div>
                  </td>
                  
                  <td class="projectColumn" style="white-space: nowrap">
                   
                  </td>
                
                  <td style="white-space: nowrap"> 
                    Oct 19 2016 01:42:49 PM
                  </td>
                  
                  <td style="white-space: nowrap"> 
                     Yuri Dinh
                  </td>
                      
                  <td class="editDeleteBtns" style="white-space: nowrap;">
                    
                    <!--  Do not show edit and delete for sub-project -->
                      <a class="btnEdit" href="/gds/manage/editIc.action?instCertId=247&amp;projectId=415">
                        <i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="edit" title="Edit"></i>&nbsp;
                      </a>&nbsp;&nbsp;&nbsp;
                      <a class="btnDelete" href="#">
                        <i class="fa fa-trash fa-lg" aria-hidden="true" alt="delete" title="Delete"></i>
                      </a>                   
                  </td>
                </tr>
                    
                <!--Begin view details-->
               <tr class="remove247">
            <td colspan="6">
                    <div id="contentDivImg247" style="display: none">
                      <table width="100%" class="tBorder2" cellspacing="3">
                        <tbody><tr>
                          <td><span class="question">Provisional or Final? </span></td>
                          <td><span class="question">Approved by GPA: </span>Yes</td>
              <td><span class="question">Study for use in Future Projects? </span></td>
                        </tr>
                        
                        
                  
                        <tr>
                          <td colspan="3" align="left" valign="top">&nbsp;</td>
                          <td colspan="3">&nbsp;</td>
                        </tr>
                        
                        <tr>
                          <td colspan="6">           
                              
                                
                                <table width="100%">
                                  <tbody><tr>
                                    <td valign="top" class="question" style="width: 35px;"><p class="number">1</p></td>
                                    <td>
                                      <table class="table table-bordered" width="100%">
                                        <tbody><tr>
                                          <td>
                                            <table width="100%" cellspacing="5">
                                              <tbody><tr>
                                                <td><span class="question">Study Name: </span>TEst</td>
                                   <!--    <td align="left" valign="top">&nbsp;</td> -->
                                      <td><span class="question">Institution: </span></td>
                                                <td><span class="question">Data Use Limitation(s) Verified? </span>Yes</td>
                                              </tr>
                                                                                    
                                              
                                              
                                              
                                              
                                              <tr><td colspan="4" align="left" valign="top">&nbsp;</td></tr>
                                              <tr>
                                                <td colspan="4" align="left" valign="top" class="question">Data Use Limitation(s)</td>
                                              </tr>       
                                              
                                              <tr>
                                                <td colspan="4">
                                                  <table class="table table-striped">
                                                    
                                                      
                                                      <tbody><tr>
                                                        <td>
                                                          <span class="question">
                                                            1. Disease-specific (list specific)
                                                            
                                                              - asdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdf
                                                            
                                                          </span>
                                                          
                                                            :&nbsp;
                                                          
                                                          
                                                            <!-- Dont show the parent DUL in the bullet list -->
                                                            
                                                          
                                                            <!-- Dont show the parent DUL in the bullet list -->
                                                            
                                                              Collaboration required
                                                              
                                                            
                                                          <br>
                                                          
                                                            &nbsp;&nbsp;&nbsp; DUL Appendix : asdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddd
                                                          
                                                        </td>
                                                      </tr>
                                                    
                                                      
                                                      <tr>
                                                        <td>
                                                          <span class="question">
                                                            2. Disease-specific (list specific)
                                                            
                                                              - asdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdf
                                                            
                                                          </span>
                                                          
                                                          
                                                            <!-- Dont show the parent DUL in the bullet list -->
                                                            
                                                          <br>
                                                          
                                                            &nbsp;&nbsp;&nbsp; DUL Appendix : asdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddd
                                                          
                                                        </td>
                                                      </tr>
                                                     
                                                  </tbody></table>
                                                </td>
                                              </tr>
                                               <!-- check for DULs present-->   
                                                                                                                                                                            
                                            </tbody></table>
                                          </td>
                                        </tr>
                                      </tbody></table> <!-- study class -->
                                    </td>
                                  </tr>
                                </tbody></table> <!--study end-->
                             <!--    <p>&nbsp;</p> -->
                               <!-- for studies -->                               
                          </td> <!-- for colspan 6-->
              </tr>
                      </tbody></table> <!-- for class tBorder2 -->
            </div> <!-- for contentDivImg -->
                  </td> <!-- for colspan 3 -->
                </tr>  <!--end view H view details-->
                         
                
                
                
                
                
                    
                
            
                      
            </tbody></table></div>
               <!-- END IC TABLE-->

              

          </div>
          </div><!--end tabs container-->
          <div style="display: block; margin-left: 0px; margin-right: 15px;" class="form-group ">
                <label for="comment">Comments (2000 Characters):</label><br>
                <textarea name="instCertification.comments" cols="" rows="3" id="instCertComments" class="col-md-12 form-control" maxlength="2000"></textarea>
                <div id="charNum6" style="text-align: right; font-style: italic;">2000 characters left</div>
        </div>
          

      
            <div>
				<p class="question">Studies awaiting ICs (2000 Characters):</p>
				<s:textarea class="form-control input_other commentsClass" style="overflow-y: scroll;" rows="3" maxlength="2000" id="icComments" name="icComments" placeholder="List Studies awaiting Institutional Certifications to be received"></s:textarea>
			    <div id="charNum" style="text-align: right; font-style: italic;">
				<span style="color: #990000;">2000</span> Character limits
			   </div>
			</div>
			
			 <div>
				<p class="question">Additional Comments (2000 Characters):</p>
				<s:textarea class="form-control input_other commentsClass" style="overflow-y: scroll;" rows="3" maxlength="2000" id="additionalComments" name="additionalComments" placeholder=""></s:textarea>
			    <div id="charNum2" style="text-align: right; font-style: italic;">
				<span style="color: #990000;">2000</span> Character limits
			    </div>
			</div>
			<br>
            
          </div> <!--end panel body-->
        </div> <!--end panel-->
      </div> 
      
      
              
  <!-- end Content -->

 
              
              <!--SAVE & NEXT BUTTONS-->
        <div class="pageNav">
          <s:submit action="saveIcList" value=" Save " class="saved btn btn-default"/>
          <s:submit type="button" action="saveIcListAndNext" class="saved btn btn-project-primary">
          Save &amp; Next &nbsp;&nbsp;<i class="fa fa-caret-right" style="color:#ffffff;"></i></s:submit>	  
        </div>
              
            

    </s:form>

<script type="text/javascript" src="<s:url value="/controllers/gds.js" />"></script>
<script type="text/javascript" src="<s:url value="/controllers/institutional_dashboard.js" />"></script> 
<script type="text/javascript">
$(function($){
	$('[data-toggle="tooltip"]').tooltip({
	    container : 'body'
	  });
});
</script> 


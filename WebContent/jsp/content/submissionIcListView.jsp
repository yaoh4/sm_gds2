<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>



<div class="panel panel-default" id="searchGrant" style="margin-top: 20px;">
        <div class="panel-heading">
          <span class="clickable panel-collapsed"><i class="fa fa-plus-square fa-lg" aria-hidden="true"></i>
          <div class="pheader" style="display:inline;"><h5>Institutional Certification(s)</h5></div>
        </div> <!--end panel header-->
        <div class="panel-body" style="display:none;">
         <s:if test="%{project.institutionalCertifications.size == 0}">
          No data entered.
         </s:if>
         <s:else>
          <p><span class="reportLabel">All Institutional Certifications recieved?</span>  ${project.certificationCompleteFlag}</p>
          <table style="width: 100%;" cellpadding="0px" cellspacing="0" class="table table-bordered">
            <tbody>
              <tr class="modalTheader">
                <th class="tableHeader" align="center" width="60%">Institutional Certification Document</th>
                <th class="tableHeader" align="center" width="30%">Date Uploaded</th>
                <th class="tableHeader" align="center" width="10%">Actions</th>
              </tr>
                    
              <s:iterator status="icStat" var="ic" value="project.institutionalCertifications">
                <s:set name="icIdx" value="#icStat.index" />
                
                <!--  FILE DISPLAY AND ICONS ROW -->    
                <tr data-id="${ic.id}">
                  <td style="white-space: nowrap">
                    <s:a href="javascript:openDocument(%{#ic.documents[0].id})"><s:property value="%{#ic.documents[0].fileName}" /></s:a>
                  </td>
                
                  <td style="white-space: nowrap"> 
                    <s:date name="%{#ic.documents[0].uploadedDate}" format="MMM dd yyyy hh:mm:ss a" />
                  </td>
                      
                  <td style="white-space: nowrap">
                    <a href="#" class="icDetails" id="icDetails${ic.id}">
                      <i class="expand fa fa-lg fa-plus-square" id="${ic.id}expand" aria-hidden="true" alt="view" title="view"></i></a>
                  </td>
                </tr>
                        
                             
              <!--Begin view details-->
             
                <tr>
                  <td colspan="3">
                    <div id="contentDivImg${ic.id}" style="display: none;">  
                      <table width="100%" class="tBorder2" cellspacing="3">
                        <tbody>
                          <tr>
                            <s:if test="%{#ic.gpaApprovalCode != null}">
                              <td><span class="question">Approved by GPA: </span><s:property value="%{getLookupDisplayNamebyId(#ic.gpaApprovalCode)}"/></td>
                            </s:if>
                             <s:if test="%{#ic.provisionalFinalCode != null}">
                              <td><span class="question">Provisional or Final? </span></span><s:property value="%{getLookupDisplayNamebyId(#ic.provisionalFinalCode)}"/></td>
                            </s:if>
                             <s:if test="%{#ic.futureProjectUseCode != null}">
                              <td><span class="question">Study for use in Future Projects? </span><s:property value="%{getLookupDisplayNamebyId(#ic.futureProjectUseCode)}"/></td>
                             </s:if>
                          </tr>
                       
                          <s:if test="%{#ic.comments != null}">
                            <tr><td colspan="6">&nbsp;</td></tr>
                            <tr><td colspan="6" class="question">Comments:</td></tr>
                            <tr><td colspan="6">${ic.comments}</td></tr>
			              </s:if>
                       
                          <tr>
                            <td colspan="3" align="left" valign="top">&nbsp;</td>
                            <td colspan="3">&nbsp;</td>
                          </tr>
                        
                          <tr>
                            <td colspan="6">           
                              <s:iterator status="studiesStat" var="study" value="project.institutionalCertifications[#icStat.index].studies">
                                <s:set name="studyIdx" value="#studiesStat.index" />
                                <table width="100%">
                                  <tbody>
                                    <tr>
                                      <td valign="top" class="question" style="width: 35px;"><p class="number">${studyIdx+1}</p></td>
                                      <td>
                                        <table class="table table-bordered" width="100%">
                                          <tbody>
                                            <tr>
                                              <td>
                                               <table width="100%" cellspacing="5">
                                                 <tbody>
                                                   <tr>
                                                     <td><span class="question">Study Name: </span>${study.studyName}</td>
                                                     <s:if test="%{#study.institution != null}">
                                                       <td><span class="question">Institution: </span>${study.institution}</td>
                                                     </s:if>
                                                      <s:if test="%{study.dulverificationId != null}">
                                                       <td><span class="question">Data Use Limitation(s) Verified? </span><s:property value="%{getLookupDisplayNamebyId(#study.dulVerificationId)}"/></td>
                                                     </s:if>
                                                   </tr>
                                                    
                                                   <s:if test="%{#study.comments != null}">                                
                                                     <tr><td colspan="4" align="left" valign="top">&nbsp;</td></tr>
                                                     <tr><td colspan="6" class="question">Comments:</td></tr>
                                                     <tr><td colspan="6">${study.comments}</td></tr>
                                                   </s:if>       
                                                             
                                                   <s:if test="%{project.institutionalCertifications[#icStat.index].studies[#studiesStat.index].studiesDulSets.size > 0}">
                                                                         
                                                     <tr><td colspan="4" align="left" valign="top">&nbsp;</td></tr>
                                                     <tr>
                                                       <td colspan="4" align="left" valign="top" class="question">Data Use Limitation(s)</td>
                                                     </tr>       
                                              
                                                     <tr>
                                                       <td colspan="4">
                                                         <table class="table table-striped">
                                                          <s:iterator status="dulSetStat" var="studiesDulSet" value="project.institutionalCertifications[#icStat.index].studies[#studiesStat.index].studiesDulSets">
                                                            <s:set name="dulSetIdx" value="#dulSetStat.index" />                                                  
                                                            <tr>
                                                              <td>
                                                                <span class="question">                                                   
                                                                  ${dulSetStat.index + 1}. ${studiesDulSet.parentDulChecklist.displayText}
                                                                  <s:if test="%{#studiesDulSet.additionalText != null}">
                                                                         - ${studiesDulSet.additionalText}
                                                                  </s:if>
                                                                </span>
                                                                <s:if test="%{#studiesDulSet.dulChecklistSelections.size > 0 && 
                                                          		  (#studiesDulSet.dulChecklistSelections.size != 1 || 
                                                          		  #studiesDulSet.dulChecklistSelections[0].dulChecklist.parentDulId != null)}">
                                                                  :&nbsp
                                                                </s:if>                                                         
                                                                <s:iterator status="dulStat" var="dul" value="%{#studiesDulSet.dulChecklistSelections}">
                                                                  <!-- Dont show the parent DUL in the bullet list -->
                                                                  <s:if test="%{#dul.dulChecklist.parentDulId != null}">
                                                                    ${dul.dulChecklist.displayText}
                                                                    <s:if test="%{#dulStat.index < (#studiesDulSet.dulChecklistSelections.size - 1)}">
                                                                           ;
                                                                    </s:if>
                                                                  </s:if>
                                                                </s:iterator>                                                              
                                                              </td>
                                                            </tr>                                                                                                                                                                            
                                                          </s:iterator> <!--  studiesDulSets -->
                                                        </table>
                                                      </td>
                                                    </tr>
                                                  </s:if><!-- check for DULs present-->                                                                                                                                                                               
                                                </tbody>
                                              </table> <!-- study table -->
                                            </td>
                                          </tr>
                                        </tbody>
                                      </table> <!-- study class -->
                                    </td>
                                  </tr>
                                </tbody></table> <!--study end-->
                              </s:iterator> <!-- for studies -->                               
                            </td> <!-- for colspan 6-->
                          </tr>
                        </tbody>
                      </table> <!-- for class tBorder2 -->
                     
                    </div> <!-- for contentDivImg -->
                  </td> <!-- for colspan 3 -->
                </tr>  <!--end view H view details-->  
              </s:iterator><!-- ics -->              
            </tbody>
          </table>
         </s:else>
        </div><!--end panel body-->
      </div><!--end panel-->

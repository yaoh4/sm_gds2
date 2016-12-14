<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>



<div class="panel panel-default" id="searchGrant" style="margin-top: 20px;">
      <div class="panel-heading">
        <span class="clickable panel-collapsed">
            <i class="fa fa-plus-square fa-lg" aria-hidden="true"></i>
        </span>
        <div class="pheader" style="display:inline;"><h5>Institutional Certification(s)</h5></div>
        
          <s:if test="%{!isReadOnlyUser() && editFlag.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@FLAG_YES) && project.latestVersionFlag.equals(\"Y\")}">
          <div style="display:inline; float: right;">
            <a href="/gds/manage/listIc.action?projectId=${project.id}">
              <i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="edit" title="edit"></i>
            </a>
          </div>
        </s:if>  
          
        </div> <!--end panel header-->
        <div class="panel-body" style="display:none;">
       <s:if test="%{getPageStatus('IC').status.code.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PAGE_STATUS_CODE_NOT_STARTED)}">
          No data entered.
        </s:if>
         <s:else>
          <table width="100%" border="0" cellpadding="3">
            <tr>
              <td width="30%" style="white-space: nowrap"><strong>All Institutional Certifications received?</strong></td>
              <td style="padding-left: 20px;"><s:property value="%{getDisplayNameByFlag(project.certificationCompleteFlag)}"/></td>
            </tr>
          </table>
          <p>&nbsp;</p>
          <table width="100%" cellpadding="0px" cellspacing="0" style="table-layout:fixed;" class="table table-bordered">
            <tbody>
              <tr class="modalTheader">
                <th class="tableHeader" align="center" width="40%">Institutional Certification Document</th>
                <th class="tableHeader projectColumn" align="center" width=10%>Status</th>
                <th class="tableHeader projectColumn" align="center" width="10%">Missing Data</th>
                <th class="tableHeader" align="center" width="20%">Date Uploaded</th>
               <th class="tableHeader" align="center" width="20%">Uploaded By</th>
              </tr>
                    
              <s:iterator status="icStat" var="ic" value="project.institutionalCertifications">
              <div class="icCount">
                <s:set name="icIdx" value="#icStat.index" />
                
                <!--  FILE DISPLAY AND ICONS ROW -->    
                <tr data-id="${ic.id}">
                  <td style="style="word-wrap:break-word;">
                    <a href="#" class="icDetails" id="icDetails${ic.id}">
                      <i class="expand fa fa-lg fa-plus-square" id="${ic.id}expand" aria-hidden="true" alt="view" title="view"></i></a>&nbsp;
                    <s:a href="javascript:openDocument(%{#ic.documents[0].id})"><s:property value="%{#ic.documents[0].fileName}" /></s:a>
                  </td>
                  
                <td class="projectColumn" style="white-space: nowrap">
                <s:hidden id="icReg%{#icStat.index}" value="%{getIcStatusCode(#ic.id)}"/>            	
              	<div id="icDiv${icStat.index}" class="searchProgress">
        		  <img src="../images/inprogress.png" alt="In Progress" width="18px" height="18px" title="In Progress"/>
        	  	</div>
                  </td>
                  
                  <td class="projectColumn" style="white-space: nowrap">
                   <s:if test="%{!getIcStatusCode(#ic.id).equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PAGE_STATUS_CODE_COMPLETED)}">
                   <a href="#" onclick="openMissingDataReport(${project.id}, '/gds/manage/viewMissingIcData.action?instCertId=${ic.id}&')"><i class="fa fa-file-text fa-lg" aria-hidden="true"></i></a> &nbsp; &nbsp;
                  </s:if>
                  </td>
                  
                  <td style="white-space: nowrap"> 
                    <s:date name="%{#ic.documents[0].uploadedDate}" format="MMM dd yyyy hh:mm:ss a" />
                  </td>
                      
                  <td style="white-space: nowrap"> 
                     <s:property value="%{#ic.documents[0].uploadedBy}" />
                  </td>
                </tr>
                        
                   </div>          
              <!--Begin view details-->
             
                <tr>
                 <s:if test="%{project.subprojectFlag.equals(\"N\")}">
                  <td colspan="5">
                  </s:if>
                  <s:else>
                   <td colspan="3">
                  </s:else>
                    <div id="contentDivImg${ic.id}" style="display: none;">  
                      <table width="100%" class="tBorder2" cellspacing="3">
                        <tbody>
                          <tr>
                          <s:if test="%{#ic.provisionalFinalCode != null}">
                              <td><span class="question">Provisional or Final? </span></span><s:property value="%{getLookupDisplayNamebyId(#ic.provisionalFinalCode)}"/></td>
                            </s:if>
                            <s:if test="%{#ic.gpaApprovalCode != null}">
                              <td><span class="question">Approved by GPA: </span><s:property value="%{getLookupDisplayNamebyId(#ic.gpaApprovalCode)}"/></td>
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

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
            <a href="/gds/manage/navigateToIcMain.action?projectId=${project.id}">
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
          <!-- Studies Table -->
          <s:if test="%{project.studies.size > 0}">
        <p class="question">
      <a href="javascript:void"
        class="studiesTab"><i class="expandStudies fa fa-plus-square" aria-hidden="true"></i></a>&nbsp;&nbsp;Studies</p>
             <div class="studiesTable" style="display: none;"> 
          <table style="width: 100%; font-size: 14px; table-layout:fixed;" cellpadding="0px" cellspacing="0" class="table table-bordered table-striped">
              <tbody><tr class="modalTheader">
                <th  class="tableHeader"  align="center" width="25%">Study Name</th>                      
                <th class="tableHeader" align="center" width="25%">Institution</th>
                <th>Received</th>
                <th>Document</th>
                <th>Approved by GPA</th>
                <th align="center">Comments</th>
              </tr> 
              
              <s:iterator status="studiesStat" var="study" value="project.studies">
              <div class="studyDetailsDiv">
              <s:set name="studyIdx" value="%{#study.id}" />
               <tr  data-id="${study.id}">
              <td style="word-wrap:break-word;"> 
              <s:if test="%{#study.studiesDulSets.size > 0}">
              <a href="#" class="studyDetails" id="studyDetails${study.id}">
              <i class="expand fa fa-plus-square fa-lg" id="${study.id}expand" aria-hidden="true" alt="Details" title="Details"></i></a>&nbsp;&nbsp;&nbsp;
              </s:if> 
              <s:property value="%{#study.studyName}" />
              </td>
              <td style="word-wrap:break-word;"> <s:property value="%{#study.institution}" /></td> 
              <td>
              <s:if test="%{#study.institutionalCertifications[0].id != null}">
              Yes
              </s:if>
              <s:else>
              No
              </s:else>
              </td>
              <td  style="text-align: center;">
              <s:if test="%{#study.institutionalCertifications[0].id != null}">
              <s:a href="javascript:openDocument(%{#study.institutionalCertifications[0].documents[0].id})">
                     <i class="fa fa-file-text fa-lg" aria-hidden="true" alt="view" title="view"></i>
                     </s:a>
              </s:if>
              <s:else>
               None
              </s:else>
              </td>                    
               <td>
               <s:if test="%{#study.institutionalCertifications[0].gpaApprovalCode != null}">
               <s:property value="%{getLookupDisplayNamebyId(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@IC_APPROVED_BY_GPA_LIST, #study.institutionalCertifications[0].gpaApprovalCode)}"/>
               </s:if>
               <s:else>
                N/A
               </s:else>
                </td>
               <td style="text-align: center;">
                <s:if test="%{#study.comments == null && #study.institutionalCertifications[0].comments == null}">
                None
                </s:if>
                <s:elseif test="%{#study.comments != null || #study.institutionalCertifications[0].comments != null}">
              <div style="position: relative;"><a href="#" class="hvrlink" target="_blank">View</a><div style="word-wrap:break-word;" class="details-pane">
              <s:if test="%{#study.comments != null}">
              <h3 class="title">Study Comments:</h3>
              <p class="desc"><s:property value="%{#study.comments}" /></p>
              </s:if>
              <s:if test="%{#study.institutionalCertifications[0].comments != null}">
              <h3 class="title">IC Comments:</h3>
              <p class="desc"><s:property value="%{#study.institutionalCertifications[0].comments}" /></p>
              </s:if>
              </div></div>      
                </s:elseif>
              <s:else>
              None
              </s:else>
              </td>
              </tr>
              </div>
                <!--Begin view details-->
		   <tr class="removeStudy${study.id}">
			<td colspan="6">
            <div id="dulContent${study.id}" style="display: none">
					<table width="50%" class="tableStudy table table-bordered table-striped" style="table-layout:fixed; align:center" cellspacing="3">
                       <!--   <tr class="modalTheader">-->
                       <tr>
                          <th width="20%" align="center">Type</th>
                          <th width="10%" align="center">Appendix</th>
						  <th width="10%" align="center">DUL Verified?</th>
                        </tr>
                        <s:iterator status="dulSetStat" var="studiesDulSet" value="project.studies[#studiesStat.index].studiesDulSets"> 
                         <tr>
                          <td> 
                           <span>
                            ${studiesDulSet.parentDulChecklist.displayText}
                                                            <s:if test="%{#studiesDulSet.additionalText != null}">
                                                              - ${studiesDulSet.additionalText}
                                                            </s:if>
                                                          </span>
                                                          <s:if test="%{#studiesDulSet.dulChecklistSelections.size > 0 && 
                                                          			(#studiesDulSet.dulChecklistSelections.size != 1 || 
                                                          			#studiesDulSet.dulChecklistSelections[0].dulChecklist.parentDulId != null)}">
                                                            (
                                                          <s:iterator status="dulStat" var="dul" value="%{#studiesDulSet.dulChecklistSelections}">
                                                            <!-- Dont show the parent DUL in the bullet list -->
                                                            <s:if test="%{#dul.dulChecklist.parentDulId != null}">
                                                              ${dul.dulChecklist.abbreviation}
                                                              <s:if test="%{#dulStat.index < (#studiesDulSet.dulChecklistSelections.size - 1)}">
                                                                ;
                                                              </s:if>
                                                            </s:if>
                                                          </s:iterator> 
                                                          )
                                                          </s:if>      
                          </td>
                          <td>
                          <s:if test="%{#studiesDulSet.comments != null}">
                          <div style="position: relative;"><a href="#" class="hvrlink" target="_blank">View</a><div style="word-wrap:break-word;" class="details-pane">
                           <h3 class="title">DUL Appendix:</h3>
                     <p class="desc"><s:property value="%{#studiesDulSet.comments}"/></p>
                     </div></div>
                     </s:if>
                     <s:else>
                     None
                     </s:else>
                          </td>
						   <td>
						  <s:if test="%{#study.dulVerificationId != null}">
						  <s:property value="%{getLookupDisplayNamebyId(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@IC_DUL_VERIFIED_LIST, #study.dulVerificationId)}"/>
						  </s:if>
						  <s:else>
						  None
						  </s:else>
						  </td>
                        </tr>
                       </s:iterator>
					</table> <!-- for class tBorder2 -->
				</div> <!-- for contentDivImg -->
			</td> <!-- for colspan 3 -->
		</tr>  <!--end view H view details-->
                
                  </s:iterator>           
                </table>
                </div>
                </s:if>
          <!-- End Studies Table -->
                    <p>&nbsp;</p>
                    <p class="question">
      <a href="javascript:void"
        class="icTab"><i class="expandIc fa fa-plus-square" aria-hidden="true"></i></a>&nbsp;&nbsp;Institutional Certification</p>
         <div class="icTable" style="display: none;">
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
                   <a href="#" onclick="openMissingDataReport(${project.id}, '/gds/manage/viewMissingIcData.action?instCertId=${ic.id}&')"><i class="fa fa-file-text fa-lg" aria-hidden="true" alt="view" title="view"></i></a> &nbsp; &nbsp;
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
                              <td><span class="question">Provisional or Final? </span></span><s:property value="%{getLookupDisplayNamebyId(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@IC_PROV_OR_FINAL_LIST, #ic.provisionalFinalCode)}"/></td>
                            </s:if>
                            <s:if test="%{#ic.gpaApprovalCode != null}">
                              <td><span class="question">Approved by GPA: </span><s:property value="%{getLookupDisplayNamebyId(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@IC_APPROVED_BY_GPA_LIST, #ic.gpaApprovalCode)}"/></td>
                            </s:if>
                             <s:if test="%{#ic.futureProjectUseCode != null}">
                              <td><span class="question">Study for use in Future Projects? </span><s:property value="%{getLookupDisplayNamebyId(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@IC_FOR_FUTURE_USE_LIST, #ic.futureProjectUseCode)}"/></td>
                             </s:if>
                          </tr>
                       
                          <s:if test="%{#ic.comments != null}">
                            <tr><td colspan="6">&nbsp;</td></tr>
                            <tr><td colspan="6" class="question">Comments:</td></tr>
                            <tr><td colspan="6"><textarea class="commentsClass" style="width: 100%; border: 0px solid #000000; overflow-y: scroll; resize: none;" readonly="readonly">${ic.comments}</textarea></td></tr>
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
                                               <table style="table-layout:fixed;" width="100%" cellspacing="5">
                                                 <tbody>
                                                   <tr>
                                                     <td width="35%" style="word-wrap:break-word;"><span class="question">Study Name: </span>${study.studyName}</td>
                                                     <td>&nbsp;</td>
                                                     <s:if test="%{#study.institution != null}">
                                                       <td width="35%" style="word-wrap:break-word;"><span class="question">Institution(s): </span>${study.institution}</td>
                                                     </s:if>
                                                     <td>&nbsp;</td>
                                                      <s:if test="%{getLookupDisplayNamebyId(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@IC_DUL_VERIFIED_LIST, #study.dulVerificationId) != null}">
                                                       <td width="20%"><span class="question">DUL(s) Verified? </span><s:property value="%{getLookupDisplayNamebyId(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@IC_DUL_VERIFIED_LIST, #study.dulVerificationId)}"/></td>
                                                     </s:if>
                                                   </tr>
                                                    <s:if test="%{#study.comments != null}">
                                                       <tr><td colspan="4" align="left" valign="top">&nbsp;</td></tr>
                                                       <tr><td colspan="6" class="question">Comments:</td></tr>
                                                       <tr><td colspan="6"><textarea class="commentsClass" style="width: 100%; border: 0px solid #000000; overflow-y: scroll; resize: none;" readonly="readonly">${study.comments}</textarea></td></tr>
                                                    </s:if>
                                                        
                                                             
                                                   <s:if test="%{project.institutionalCertifications[#icStat.index].studies[#studiesStat.index].studiesDulSets.size > 0}">
                                                                         
                                                     <tr><td colspan="4" align="left" valign="top">&nbsp;</td></tr>
                                                     <tr>
                                                       <td colspan="4" align="left" valign="top" class="question">Data Use Limitation(s)</td>
                                                     </tr>       
                                              
                                                     <tr>
                                                       <td colspan="6">
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
                                                                  :&nbsp;
                                                                </s:if>                                                         
                                                                <s:iterator status="dulStat" var="dul" value="%{#studiesDulSet.dulChecklistSelections}">
                                                                  <!-- Dont show the parent DUL in the bullet list -->
                                                                  <s:if test="%{#dul.dulChecklist.parentDulId != null}">
                                                                    ${dul.dulChecklist.displayText}
                                                                    <s:if test="%{#dulStat.index < (#studiesDulSet.dulChecklistSelections.size - 1)}">
                                                                           ;
                                                                    </s:if>
                                                                  </s:if>
                                                                </s:iterator><br>
                                                                <s:if test="%{#studiesDulSet.comments != null}">
                                                                 DUL Appendix:
                                                                 <textarea class="commentsClass" style="width: 100%; border: 0px solid #000000; overflow-y: scroll; resize: none;" readonly="readonly">${studiesDulSet.comments}</textarea>
                                                                 </s:if>                                                         
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
          </div>
          <!-- Comments table -->
          <table width="100%" border="0" cellpadding="3">
            <s:if test="%{project.additionalIcComments != null}">
            <tr>
               <td style="white-space: nowrap">&nbsp;</td>
               <td colspan="4">&nbsp;</td>
            </tr>
            <tr>
            <tr>
              <td colspan="4" class="question">Additional Comments:</td>
            </tr>       
            <tr>
              <td colspan="4"><textarea class="commentsClass" style="width: 100%; border: 0px solid #000000; overflow-y: scroll; resize: none;" readonly="readonly">${project.additionalIcComments}</textarea></td>
            </tr>
            </s:if>
          </table>
         </s:else>
        </div><!--end panel body-->
      </div><!--end panel-->

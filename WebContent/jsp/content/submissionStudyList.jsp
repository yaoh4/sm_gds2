 <%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<s:if test="%{project.studies.size == 0}">
<p>&nbsp;</p>
<p> There are currently no studies added.</p>
<br/>
</s:if>
<s:else>
       <table style="width: 100%; font-size: 14px; table-layout:fixed;" cellpadding="0px" cellspacing="0" class="table table-bordered table-striped">
              <tbody><tr class="modalTheader">
                <th  class="tableHeader"  align="center" width="25%">Study Name</th>                      
                <th class="tableHeader" align="center" width="25%">Institution</th>
                <th width="10%">Received</th>
                <th width="10%">Document</th>
                <th width="10%">Approved by GPA</th>
                <th align="center" width="10%">Comments</th>         
                <th id="actionColumnStudy" class="tableHeader" style="display:none;"align="center" width="10%">Actions</th>
              </tr> 
              
              <s:hidden id="studyListSize" value="%{project.studies.size}"/>  
              <s:iterator status="studiesStat" var="study" value="project.studies">
              <div class="studyDetailsDiv">
              <s:set name="studyIdx" value="%{#study.id}" />
               <tr  data-id="${study.id}">
              <td style="word-wrap:break-word;" > 
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
              <td width="2%"  class="editDeleteBtns" style="white-space: nowrap; display:none;">
                    <!--  Do not show edit and delete for sub-project -->
                      <a class="btnEdit" href="/gds/manage/editStudy.action?studyId=${study.id}&projectId=${project.id}">
                       <s:hidden name ="studyid" id="studyid" value="%{#study.id}"/>
                        <i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="edit" title="Edit"></i>&nbsp;
                  </a> &nbsp;&nbsp;&nbsp;
                  <s:hidden id="val" value="%{#study.institutionalCertifications[0].id}"/>
                  <s:if test="%{#study.institutionalCertifications[0].id != null}">
                   </s:if>  
                   <s:else>
                      <a class="btnDelete" href="#" >
                        <i class="fa fa-trash fa-lg" aria-hidden="true" alt="delete" title="Delete"></i>
                  </a>
                   </s:else>       
                  </td>
                </tr>
                </div>
                <!--Begin view details-->
		<tr class="removeStudy${study.id}">
			<td colspan="7">
            <div id="dulContent${study.id}" style="display: none">
					<table width="50%" class="tableStudy table table-bordered table-striped" style="table-layout:fixed; align:center" cellspacing="3">
                       <!--   <tr class="modalTheader">-->
                       <tr>
                          <th width="20%" align="center">DUL Type</th>
                          <th width="10%" align="center">DUL Appendix</th>
						  <th width="10%" align="center">DUL Verified?</th>
                        </tr>
                        <s:iterator status="dulSetStat" var="studiesDulSet" value="project.studies[#studiesStat.index].studiesDulSets"> 
                         <tr>
                          <td> 
                          <s:if test="%{#studiesDulSet.parentDulChecklist.displayText != null}">
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
                          </s:if>    
                          <s:else>
                          None
                          </s:else> 
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
                </s:else>
                
                 <!-- start: Delete Coupon Modal -->
      <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	       <div class="modal-dialog">
              <div class="modal-content">
                 <div class="modal-header">
                   <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                     <i class="fa fa-exclamation-triangle fa-3x" aria-hidden="true" title="warning sign"></i>&nbsp;&nbsp;
                       <h3 class="modal-title" id="myModalLabel">Are You Sure You Want to Delete?</h3>

                 </div>
            <!--/modal-body-collapse -->
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" id="btnDelteYesStudy">Delete</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
            </div>
            <!--/modal-footer-collapse -->
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
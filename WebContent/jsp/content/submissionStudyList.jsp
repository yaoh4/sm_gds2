 <%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

       <table style="width: 100%; font-size: 14px" cellpadding="0px" cellspacing="0" class="table table-bordered table-striped">
              <tbody><tr class="modalTheader">
                <th  class="tableHeader"  align="center" width="25%">Study Name</th>                      
                <th class="tableHeader" align="center" width="25%">Institution</th>
                <th>Received</th>
                <th>Document</th>
                <th>Approved by GPA</th>
                <th align="center">Comments</th>         
                <th id="actionColumnStudy" class="tableHeader" style="display:none;"align="center" width="1%">Actions</th>
              </tr> 
              
              <s:iterator status="studies" var="study" value="project.studies">
              <div class="studyDetailsDiv">
              <s:set name="studyIdx" value="%{#study.id}" />
               <tr  data-id="${study.id}">
              <td> <a href="#" class="studyDetails" id="studyDetails${study.id}">
                      <i class="expand fa fa-plus-square fa-lg" id="${study.id}expand" aria-hidden="true" alt="Details" title="Details"></i> </a>&nbsp;&nbsp;&nbsp;
                      <s:property value="%{#study.studyName}" /></td>
              <td > <s:property value="%{#study.institution}" /></td> 
              <td >Yes</td>
              <td  style="text-align: center;"><a href="#" onclick="openMissingDataReport(415, '/gds/manage/viewMissingIcData.action?instCertId=432&amp;')"><i class="fa fa-file-text fa-lg" aria-hidden="true" alt="view" title="view"></i></a> </td>                    
              <td> N/A </td>
              <td style="text-align: center;"><div style="position: relative;"><a href="#" class="hvrlink" target="_blank">View</a><div class="details-pane">
              <h4 class="title">Comments</h4>
              <p class="desc">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque purus lorem, sagittis vitae lacus id, dictum malesuada magna.</p>
            </div><!-- @end .details-pane --></div></td> 
              <td width="2%"  class="editDeleteBtns" style="white-space: nowrap;">
                    <!--  Do not show edit and delete for sub-project -->
                      <a class="btnEdit" href="/gds/manage/editStudy.action?studyId=${study.id}&projectId=${project.id}">
                       <s:hidden name ="studyid" id="studyid" value="%{#study.id}"/>
                        <i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="edit" title="Edit"></i>&nbsp;
                  </a> &nbsp;&nbsp;&nbsp;
                  <s:hidden id="val" value="%{#study.institutionalCertification.id}"/>
                  <s:if test="%{#study.institutionalCertification.id != null}">
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
					<table width="50%" class="tableStudy table-bordered table-striped" style="table-layout:fixed; align:center" cellspacing="3">
                        <tr class="modalTheader">
                          <th width="20%" align="center">Type</th>
                          <th width="20%" align="center">Appendix</th>
						  <th width="10%" align="center">DUL Verified?</th>
                        </tr>
                        <s:iterator status="duls" var="dul" value="project.studies">
                         <tr>
                          <td style="text-align: center;">na</td>
                          <td style="text-align: center;">na</td>
						  <td style="text-align: center;">na</td>
                        </tr>
                        </s:iterator>    
					</table> <!-- for class tBorder2 -->
				</div> <!-- for contentDivImg -->
			</td> <!-- for colspan 3 -->
		</tr>  <!--end view H view details-->
                
                  </s:iterator>           
                </table>
                
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
                 <h5>Deleting the Study.</h5>
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
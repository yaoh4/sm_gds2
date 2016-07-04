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
      
      <s:hidden name="projectId" value="%{project.id}"/>
      <s:hidden name="project.createdBy" value="%{project.createdBy}"/>
      
      
      <!-- Begin Panel -->
      <div class="col-md-12">
        <div class="panel  project-panel-primary">
          <div class="panel-heading">
            <div class="pheader"><h4>Institutional Certification Status</h4></div>
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
                    <tr  data-id="${icIdx}">
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

                        <a href="#icDetails" data-toggle="modal">
                        	<i class="fa fa-eye fa-lg" aria-hidden="true" alt="view" title="view"></i>
                        </a>&nbsp;&nbsp;&nbsp;
                        <a href="/gds/manage/editIc.action?instCertId=${ic.id}&projectId=${project.id}">
                        	<i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="edit" title="edit"></i>&nbsp;
                        </a>&nbsp;&nbsp;&nbsp;
                        <a href="#" class="btnDelete">
                        	<i class="fa fa-trash fa-lg" aria-hidden="true" alt="delete" title="delete"></i>
                        </a></td>
                      </tr>
				</s:iterator>
                      
                    </table>
                  </div>
              </div> <!--end panel body-->
              </div> <!--end panel-->
              
              <!--SAVE & NEXT BUTTONS-->
        <div class="pageNav">
          <s:submit action="saveIcList" value=" Save " class="saved btn btn-project-primary"/>
          <s:submit action="saveIcListAndNext" value=" Save and Next " class="saved btn btn-project-primary"/>	  
        </div>
              
            </form>
            </div><!--end column formatting div-->


<script type="text/javascript"
	src="<s:url value="/controllers/institutional_dashboard.js" />"></script>


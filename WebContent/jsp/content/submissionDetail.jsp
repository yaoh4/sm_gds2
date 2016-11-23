<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


  <div class="col-md-12">
 
	<div class="panel  project-panel-primary">
  			
  	  <div class="panel-heading">
		<div class="pheader">
		  <h4>Submission Details</h4>
		  <div class="statusWrapper">
		    <s:if test="%{!pageStatusCode.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PAGE_STATUS_CODE_COMPLETED)}">         		           		      
    	  	  <div class="status">    	  	
    	  	    <a href="#" onclick="openMissingDataReport(${project.id}, '/gds/manage/viewMissingProjectData.action?')" class="statusLink">Generate Missing Data Report</a> &nbsp; &nbsp;
    	  	  </div>
    	  	</s:if>
           	<s:include value="/jsp/content/pageStatus.jsp"/>           	
          </div>
		</div>
	  </div>
         
      <div class="panel-body">

	

        <table width="85%" style="table-layout:fixed;" class="table table-bordered">
          <caption style="display: none;">Status History</caption>
          <thead>
            <tr class="active">
              <th colspan="2" class="sortable" style="width: 45%;" abbr="Year">Milestones
               &nbsp; &nbsp; <a href="#" id="popover" style="font-size: 12px;">
              <i class="helpfile fa fa-question-circle fa-1x"  aria-hidden="true"></i></a></th>
              <th width="25%" class="sortable" style="width: 10%;" abbr="Org">Current Status</th>
              <th width="29%" class="sortable" style="width: 20%;" abbr="Role">Last Action Date</th>
              <th width="29%" class="sortable" style="width: 10%;" abbr="Role">Missing Data</th>
              <th width="25%" class="sortable" style="width: 35%; white-space:nowrap;" abbr="Person">Person</th>
            </tr>
          </thead>
          <tbody>
            <tr class="odd">
              <td colspan="2">
              <s:if test="!isReadOnlyUser()">
              	<a href="/gds/manage/navigateToGeneralInfo.action?projectId=${project.id}">Project Created</a>
              </s:if><s:else>Project Created</s:else></td>
              <td><div class="searchProgess">
        <img src="../images/complete.png" alt="Complete" width="18px" height="18px" title="Completed"/></div></td>
              <td><s:date name="%{project.createdDate}" format="MM/dd/yyyy"/> </td>
              <td align="center"></td>
              <td>${project.createdByPerson.fullName}</td>
            </tr>
		  <s:if test="%{showPage('GDSPLAN')}">
            <tr >
              <td colspan="2">
              <s:if test="!isReadOnlyUser()"><a href="/gds/manage/editGdsPlan.action?projectId=${project.id}">Genomic DSP</a>
              </s:if><s:else>Genomic DSP</s:else></td>
      
              <td>
               <s:hidden id="gdsPlan" value="%{getPageStatus('GDSPLAN').status.code}"/>
              	<div id="gdsPlanDiv" class="searchProgess">
        		  <img src="../images/inprogress.png" alt="In Progress" width="18px" height="18px" title="In Progress"/>
      		    </div>
      		  </td>
              <td><s:date name="%{getPageStatus('GDSPLAN').updatedDate}" format="MM/dd/yyyy"/> </td>
              <td align="center">
               <s:if test="%{!getPageStatus('GDSPLAN').status.code.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PAGE_STATUS_CODE_COMPLETED)}">
              <a href="#" onclick="openMissingDataReport(${project.id}, '/gds/manage/viewMissingGdsPlanData.action?')">
              <i class="fa fa-file-text fa-lg" aria-hidden="true"></i>
              </a>
              </s:if>
              </td>
              <td><s:property value="%{getPageStatus('GDSPLAN').updatedBy}"/></td>  
          </tr>
          <s:if test="%{exceptionMemoStatusCode neq null}">            
            <tr class="odd">
              <td width="5%" style="border-right: 0px;">&nbsp;</td>
              <td width="26%" style="border-left: 0px;">Data Sharing Exception</td>
              <td>
                <s:hidden id="exceptionMemo" value="%{project.dataSharingExcepStatus.code}"/>
                <div id="exceptionMemoDiv" class="searchProgess">
        		  <img src="../images/inprogress.png" alt="In Progress" width="18px" height="18px" title="In Progress"/>
      			</div>
      		  </td>
              <td></td>
              <td></td>
              <td></td>
            </tr>
          </s:if>
		</s:if>
		  
		  <s:if test="%{showPage('IC')}">
            <tr >
              <td colspan="2">
              <s:if test="!isReadOnlyUser()"><a href="/gds/manage/listIc.action?projectId=${project.id}">Institutional Certification</a>
              </s:if><s:else>Institutional Certification</s:else></td>
              <td>
               <s:hidden id="ic" value="%{getPageStatus('IC').status.code}"/>
              	<div id="icDiv" class="searchProgess">               
        		  <img src="../images/inprogress.png" alt="In Progress" width="18px" height="18px" title="In Progress"/>
      		    </div>
      		  </td>
              <td><s:date name="%{getPageStatus('IC').updatedDate}" format="MM/dd/yyyy"/></td>
                <td align="center">
                 <s:if test="%{!getPageStatus('IC').status.code.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PAGE_STATUS_CODE_COMPLETED)}">
                <a href="#" onclick="openMissingDataReport(${project.id}, '/gds/manage/viewMissingIcListData.action?')"><i class="fa fa-file-text fa-lg" aria-hidden="true"></i></a>
                </s:if>
                </td>
              <td><s:property value="%{getPageStatus('IC').updatedBy}"/></td>
            </tr>
          </s:if>
          <s:if test="%{showPage('BSI')}">
            <tr class="odd">
              <td colspan="2">
              <s:if test="!isReadOnlyUser()"><a href="/gds/manage/navigateToBasicStudyInfo.action?projectId=${project.id}">Basic Study Information</a>
              </s:if><s:else>Basic Study Information</s:else></td>
              <td>
              <s:hidden id="bsi" value="%{getPageStatus('BSI').status.code}"/>
              	<div id="bsiDiv" class="searchProgess">
        		  <img src="../images/inprogress.png" alt="In Progress" width="18px" height="18px" title="In Progress"/>
      			</div>
      		  </td>
              <td><s:date name="%{getPageStatus('BSI').updatedDate}" format="MM/dd/yyyy"/></td>
              <td align="center">
              <s:if test="%{!getPageStatus('BSI').status.code.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PAGE_STATUS_CODE_COMPLETED)}">
              <a href="#" onclick="openMissingDataReport(${project.id}, '/gds/manage/viewMissingBsiData.action?')"><i class="fa fa-file-text fa-lg" aria-hidden="true"></i></a>
              </s:if>
              </td>
              <td><s:property value="%{getPageStatus('BSI').updatedBy}"/></td>
            </tr>
          </s:if>
        <s:iterator status="repStat" var="repStatus" value="project.repositoryStatuses">
          <div class="repoItem">      
            <tr class="repoRow">
              <td colspan="3" style="word-wrap:break-word;">
              <s:if test="!isReadOnlyUser()"><a href="/gds/manage/navigateToRepositoryStatus.action?projectId=${project.id}">Submission Status for Repository: 
               <s:if test="%{#repStatus.planAnswerSelectionTByRepositoryId.otherText != null}">
                  <s:property value="#repStatus.planAnswerSelectionTByRepositoryId.planQuestionsAnswer.displayText" />
                     - <s:property value="#repStatus.planAnswerSelectionTByRepositoryId.otherText" />
               </s:if>
               <s:else>
                  <s:property value="#repStatus.planAnswerSelectionTByRepositoryId.planQuestionsAnswer.displayText" />
               </s:else></a>
              </s:if><s:else>Submission Status for Repository: 
               <s:if test="%{#repStatus.planAnswerSelectionTByRepositoryId.otherText != null}">
                  <s:property value="#repStatus.planAnswerSelectionTByRepositoryId.planQuestionsAnswer.displayText" />
                     - <s:property value="#repStatus.planAnswerSelectionTByRepositoryId.otherText" />
               </s:if>
               <s:else>
                  <s:property value="#repStatus.planAnswerSelectionTByRepositoryId.planQuestionsAnswer.displayText" />
               </s:else>
			  </s:else></td>
             <td><s:date name="%{#repStatus.updatedDate}" format="MM/dd/yyyy"/></td>
             <td align="center">
             <s:hidden value="%{getRepositoryStatusCode(#repStatus.id)}"/> 
              <s:if test="%{!getRepositoryStatusCode(#repStatus.id).equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PAGE_STATUS_CODE_COMPLETED)}">
             <a href="#" onclick="openMissingDataReport(${project.id}, '/gds/manage/viewMissingRepositoryData.action?repoStatusId=${repStatus.id}&')"><i class="fa fa-file-text fa-lg" aria-hidden="true"></i></a>
             </s:if>
              </td>
              <td><s:property value="%{#repStatus.updatedBy}"/></td>
            </tr>
            
            <tr class="odd">
              <td width="5%" style="border-right: 0px;">&nbsp;</td>
              <td width="26%" style="border-left: 0px;">Study Registration</td>
              <td>
                <s:hidden id="repoReg%{#repStat.index}" value="%{#repStatus.lookupTByRegistrationStatusId.code}"/>
              	<div id="repoRegDiv${repStat.index}" class="searchProgess">
        			<img src="../images/complete.png" alt="Complete" width="18px" height="18px" title="Completed"/>
      			</div>
      		  </td>
              <td></td>
              <td></td>
              <td></td>
            </tr> 
            
            <tr class="odd">
              <td width="5%" style="border-right: 0px;">&nbsp;</td>
              <td width="26%" style="border-left: 0px;">Data Submission Status</td>
              <td>
               <s:hidden id="repoSub%{#repStat.index}" value="%{#repStatus.lookupTBySubmissionStatusId.code}"/>            	
              	<div id="repoSubDiv${repStat.index}" class="searchProgess">
        		  <img src="../images/inprogress.png" alt="In Progress" width="18px" height="18px" title="In Progress" />
        	  	</div>
        	  </td>
              <td></td>
              <td></td>
              <td></td>
            </tr>   
      
            <tr class="odd">
              <td width="5%" style="border-right: 0px;">&nbsp;</td>
              <td width="26%" style="border-left: 0px;">Study Released</td>
              <td style="text-align: center;"><s:property value="#repStatus.lookupTByStudyReleasedId.displayName" /></td>
              <td></td>
              <td></td>
              <td></td>
            </tr>   
          </div>                 
        </s:iterator>            
              
        </tbody>
      </table>
      
      <!--Link to sub projects-->
  <s:if test="%{subprojects.size > 0}">
    <div class="qSpacing">
    <p class="question">
      <a href="javascript:void"
        class="subproject"><i class="expandS fa fa-plus-square" aria-hidden="true"></i></a>&nbsp;View Sub-projects
    &nbsp; &nbsp; <a href="#" id="popover" style="font-size: 12px;">
    <i class="helpfile fa fa-question-circle fa-1x"  aria-hidden="true"></i></a></p>
    <div class="relatedSubs" style="display: none;">
      <table style="width: 90%;" cellpadding="0px" cellspacing="0"
        class="table table-bordered table-striped"
        style="margin-left: 10px;">
        <tr>
          <th class="tableHeader" width="50%" scope="col">Sub-project Submission Title</th>
            <th class="tableHeader" width="30%" scope="col">Principal
              Investigator</th>
            <th class="tableHeader" width="5%" scope="col">Submission Status</th>
            <th class="tableHeader" width="15%" scope="col">Action</th>
        </tr>
           <s:iterator status="stat" var="subproject" value="subprojects">
            <tr>
              <td>${subproject.projectSubmissionTitle}</td>
              
              <td>${subproject.extPiFullName}<br>${subProject.intPiFullName}</td>
              
              <td style="white-space: nowrap;" align="center">
              <s:if test="%{getProjectStatusCode(#subproject.id).equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PAGE_STATUS_CODE_IN_PROGRESS)}">
                <img src="../images/inprogress.png" alt="In Progress" title="In Progress" width="18px" height="18px"/>
              </s:if> <s:elseif test="%{getProjectStatusCode(#subproject.id).equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PAGE_STATUS_CODE_COMPLETED)}">
                <img src="../images/complete.png" alt="Completed" title="Completed" width="18px" height="18px"/>
              </s:elseif> <s:else>
                <img src="../images/pending.png" alt="Not Started" title="Not Started" width="18px" height="18px"/>
              </s:else></td>

              <td><s:if test="isReadOnlyUser()"><a href="/gds/manage/navigateToSubmissionDetail.action?projectId=${subproject.id}"> 
            <s:hidden  id="prevSubId" value="%{subproject.id}"/>
            <i class="fa fa-file-text fa-lg" aria-hidden="true" alt="View" title="View"></i></a></s:if> &nbsp;&nbsp;&nbsp;
            <s:if test="!isReadOnlyUser()"><a href="/gds/manage/navigateToSubmissionDetail.action?projectId=${subproject.id}">
            <i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="Edit" title="Edit"></i></a></s:if></td>
              
            </tr>
        </s:iterator>
      </table>
    </div>
  </div>
</s:if>
<!--  End link to sub projects -->
          
  <!--Link to Parent Project-->
  <s:if test="%{project.subprojectFlag.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@FLAG_YES)}">                                
        <div class="qSpacing">
    <p class="question">
      <a href="javascript:void"
        class="project"><i class="expand fa fa-plus-square" aria-hidden="true"></i></a>&nbsp;View Parent Project
    &nbsp; &nbsp; <a href="#" id="popover" style="font-size: 12px;">
    <i class="helpfile fa fa-question-circle fa-1x"  aria-hidden="true"></i></a></p>
    <div class="related" style="display: none;">
      <table style="width: 90%;" cellpadding="0px" cellspacing="0"
        class="table table-bordered table-striped"
        style="margin-left: 10px;">
        <tr>
          <th class="tableHeader" width="50%" scope="col">Parent Submission Title</th>
            <th class="tableHeader" width="25%" scope="col">Principal
              Investigator</th>
            <th class="tableHeader" width="5%" scope="col">Submission Status</th>
            <th class="tableHeader" width="20%" scope="col">Action</th>
        </tr>
     
            <tr>
              <td>${projectsVw.parentProject.projectSubmissionTitle}</td>
              
              <td>${projectsVw.parentProject.extPiFullName}<br>${projectsVw.parentProject.intPiFullName}</td>
              
              <td style="white-space: nowrap">
               <s:if test="%{getProjectStatusCode(project.parent.id).equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PAGE_STATUS_CODE_IN_PROGRESS)}">
                <img src="../images/inprogress.png" alt="In Progress" title="In Progress" width="18px" height="18px"/>
              </s:if> <s:elseif
                test="%{getProjectStatusCode(project.parent.id).equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PAGE_STATUS_CODE_COMPLETED)}">
                <img src="../images/complete.png" alt="Completed" title="Completed" width="18px" height="18px"/>
              </s:elseif> <s:else>
                <img src="../images/pending.png" alt="Not Started" title="Not Started" width="18px" height="18px"/>
              </s:else></td>

              <td><s:if test="isReadOnlyUser()"><a href="/gds/manage/navigateToSubmissionDetail.action?projectId=${project.parent.id}"> 
            <s:hidden  id="prevSubId" value="%{project.parent.id}"/>
            <i class="fa fa-file-text fa-lg" aria-hidden="true" alt="View" title="View"></i></a></s:if> &nbsp;&nbsp;&nbsp;
            <s:if test="!isReadOnlyUser()"><a href="/gds/manage/navigateToSubmissionDetail.action?projectId=${project.parent.id}">
            <i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="Edit" title="Edit"></i></a></s:if></td>
              
            </tr>
        
        
      </table>
    </div>
  </div>
  </s:if>
<!-- End link to parent project -->
    </div>
  </div>

<s:form id="submission-details_form"  namespace="manage"
    enctype="multipart/form-data" action="submissionDetails" method="post" role="form">  

		  <s:include value="/jsp/content/submissionGeneralInfoView.jsp"/>
		  <s:if test="%{showPage('GDSPLAN')}">
		    <s:include value="/jsp/content/submissionGdsPlanView.jsp"/>
		  </s:if>
          <s:if test="%{showPage('IC')}">		  
		    <s:include value="/jsp/content/submissionIcListView.jsp"/>
		  </s:if>
		  <s:if test="%{showPage('BSI')}">
			<s:include value="/jsp/content/submissionBasicStudyInfoView.jsp"/>
		  </s:if>
		  <s:if test="%{showPage('REPOSITORY')}">
	      <s:include value="/jsp/content/submissionStatusView.jsp"/>
		 </s:if>

</s:form>		

</div>
<script src="<s:url value="/controllers/gds.js" />"></script>
<script src="<s:url value="/controllers/submissionDetail.js" />"></script>       
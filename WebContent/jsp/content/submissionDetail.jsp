<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


  <div style="float:right; display:block;"><a href="#"><i class="fa fa-print fa-lg" aria-hidden="true"></i>&nbsp;Generate missing data report (PDF)</a></div><br/><br/>
        <h4>Submission Details</h4><div style="display:inline; float: right;"><img alt="legend for progress icons" src="../images/legend-search.gif"></div>
          <br/>
          <table width="85%" class="table table-bordered">
          <caption style="display: none;">Status History</caption>
          <thead>
            <tr class="active">
              <th colspan="2" class="sortable" style="width: 45%;" abbr="Year">Milestones</th>
              <th width="25%" class="sortable" style="width: 10%;" abbr="Org">Current Status</th>
              <th width="29%" class="sortable" style="width: 20%;" abbr="Role">Last Action Date</th>
              <th width="29%" class="sortable" style="width: 10%;" abbr="Role">Missing Data</th>
              <th width="25%" class="sortable" style="width: 35%; white-space:nowrap;" abbr="Person">Person</th>
            </tr>
          </thead>
          <tbody>
            <tr class="odd">
              <td colspan="2"><a href="#">Project Created</a></td>
              <td><div class="searchProgess">
        <img src="../images/complete.png" alt="Complete" width="18px" height="18px" title="Completed"/></div></td>
              <td><s:date name="%{project.updatedDate}" format="MM/dd/yyyy"/> </td>
              <td align="center"></td>
              <td><a href="#">Jones, Mary</a></td>
            </tr>
		  <s:if test="%{showPage('GDSPLAN')}">
            <tr class="info">
              <td colspan="2"><a href="#">GDS Plan</a></td>
      
              <td>
               <s:hidden id="gdsPlan" value="%{getPageStatusCode('GDSPLAN')}"/>
              	<div id="gdsPlanDiv" class="searchProgess">
        		  <img src="../images/inprogress.png" alt="In Progress" width="18px" height="18px" title="In Progress"/>
      		    </div>
      		  </td>
              <td><s:date name="%{getPageStatus('GDSPLAN').updatedDate}" format="MM/dd/yyyy"/> </td>
              <td align="center"><a href="errorReport2.htm"
    onclick="return !window.open(this.href, 'Google', 'width=800,height=500')"
    target="_blank">View</a></td>
              <td><a href="#">Jones, Mary</a></td>
          
          <s:if test="%{project.getPlanAnswerSelectionByAnswerId(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_YES_ID) != null}">
            </tr>
            <tr class="odd">
              <td width="5%" style="border-right: 0px;">&nbsp;</td>
              <td width="26%" style="border-left: 0px;">Data Sharing Exception</td>
              <td><div class="searchProgess">
        <img src="../images/inprogress.png" alt="In Progress" width="18px" height="18px" title="In Progress"/>
      </div></td>
              <td></td>
              <td align="center"></td>
              <td><a href="#">Jones, Mary</a></td>
            </tr>
            </s:if>
		  </s:if>
		  
		  <s:if test="%{showPage('IC')}">
            <tr class="info">
              <td colspan="2"><a href="#">Institutional Certification</a></td>
              <td>
               <s:hidden id="ic" value="%{getPageStatusCode('IC')}"/>
              	<div id="icDiv" class="searchProgess">               
        		  <img src="../images/inprogress.png" alt="In Progress" width="18px" height="18px" title="In Progress"/>
      		    </div>
      		  </td>
              <td><s:date name="%{getPageStatus('IC').updatedDate}" format="MM/dd/yyyy"/></td>
                <td align="center"><a href="errorReport3.htm"
    onclick="return !window.open(this.href, 'Google', 'width=800,height=500')"
    target="_blank">View</a></td>
              <td><a href="#">Jones, Mary</a></td>
            </tr>
          </s:if>
          <s:if test="%{showPage('BSI')}">
            <tr class="odd">
              <td colspan="2"><a href="#">Basic Study Information</a></td>
              <td>
              <s:hidden id="bsi" value="%{getPageStatusCode('BSI')}"/>
              	<div id="bsiDiv" class="searchProgess">
        		  <img src="../images/inprogress.png" alt="In Progress" width="18px" height="18px" title="In Progress"/>
      			</div>
      		  </td>
              <td><s:date name="%{getPageStatus('GDSPLAN').updatedDate}" format="MM/dd/yyyy"/></td>
              <td align="center"><a href="#">View</a></td>
              <td><a href="#">Jones, Mary</a></td>
            </tr>
          </s:if>
        <s:iterator status="repStat" var="repStatus" value="project.repositoryStatuses">
          <div class="repoItem">      
            <tr class="info">
              <td colspan="4"><a href="#">Submission Status for Repository: ${repStatus.planAnswerSelectionTByRepositoryId.planQuestionsAnswer.displayText}</a></td>
             <td align="center"><a href="#">View</a></td>
              <td></td>
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
              <td>03/11/2016 2:41 PM</td>
              <td></td>
              <td><a href="#">Jones, Mary</a></td>
            </tr> 
            
            <tr class="odd">
              <td width="5%" style="border-right: 0px;">&nbsp;</td>
              <td width="26%" style="border-left: 0px;">Project Submission</td>
              <td>
               <s:hidden id="repoSub%{#repStat.index}" value="%{#repStatus.lookupTBySubmissionStatusId.code}"/>            	
              	<div id="repoSubDiv${repStat.index}" class="searchProgess">
        		  <img src="../images/inprogress.png" alt="In Progress" width="18px" height="18px" title="In Progress" />
        	  	</div>
        	  </td>
              <td>03/11/2016 2:41 PM</td>
              <td align="center"></td>
              <td><a href="#">Jones, Mary</a></td>
            </tr>   
      
            <tr class="odd">
              <td width="5%" style="border-right: 0px;">&nbsp;</td>
              <td width="26%" style="border-left: 0px;">Study Released</td>
              <td style="text-align: center;">${repStatus.lookupTByStudyReleasedId.displayName}</td>
              <td>03/11/2016 2:41 PM</td>
              <td></td>
              <td><a href="#">Jones, Mary</a></td>
            </tr>   
          </div>                 
        </s:iterator>            
              
        </tbody>
      </table>
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
	      <s:include value="/jsp/content/submissionStatusView.jsp"/>
		 

</s:form>		


<script src="<s:url value="/controllers/gds.js" />"></script>
<script src="<s:url value="/controllers/submissionDetail.js" />"></script>       
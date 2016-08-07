<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<div class="panel panel-default" id="searchGrant" style="margin-top: 20px;">
        <div class="panel-heading"><span class="clickable panel-collapsed"><i class="fa fa-plus-square fa-lg" aria-hidden="true"></i></span>
          <div class="pheader" style="display:inline;"><h5>General Information</h5></div>
          <div style="display:inline; float: right;">
           <a href="/gds/manage/navigateToGeneralInfo.action?projectId=${project.id}">
            <i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="edit" title="edit"></i>
           </a>
          </div>
        </div> <!--end panel header-->
        
        <div class="panel-body" style="display:none;">
         <s:if test="%{project.applicationNum == null}">
           No data entered.
         </s:if>
         <s:else>
          <p><span class="reportLabel">Project Submission Title:</span>  ${project.submissionTitle}</p>
          <p><span class="reportLabel">Reason for being submitted:</span> <s:property value="%{projectSubmissionReason}" /> </p>
          <p>
            <span class="reportLabel">Division/Office/Center:</span> ${project.docAbbreviation}</br>
            <span class="reportLabel">Program Branch:</span> ${project.programBranch}
          </p>
          <p>
            <span class="reportLabel"> Intramural (Z01)/Grant/Contract #:</span> ${project.applicationNum}</br>
            <span class="reportLabel">Intramural/Grant/Contract Project Title:</span> ${project.projectTitle}
          </p>
          <p>
            <span class="reportLabel">Principal Investigator:</span> ${project.piFirstName} ${project.piLastName} &nbsp;&nbsp;&nbsp; <span class="reportLabel">Email:</span> <s:a href="mailto:%{project.piEmailAddress}?">${project.piEmailAddress}</s:a></br>
            <span class="reportLabel"> Institution:</span> ${project.piInstitution}
          </p>
          <p>
			 <s:if test="project.pocFirstName != null && project.pocLastName != null">
				<span class="reportLabel">Primary Contact:</span> ${project.pocFirstName} ${project.pocLastName} &nbsp;&nbsp;&nbsp;
			</s:if>
			<s:if test="project.pocEmailAddress != null">
			    <span class="reportLabel">Email:</span> <s:a href="mailto:%{project.pocEmailAddress}?">${project.pocEmailAddress}</s:a>
			</s:if>
		  </p>
          <p><span class="reportLabel">Program Director:</span> ${project.pdFirstName} ${project.pdLastName}</p>       
          <p><span class="reportLabel">Project Start Date:</span> <s:property value="%{projectStartDate}" /> &nbsp;&nbsp;&nbsp; <span class="reportLabel">Project End Date:</span> <s:property value="%{projectEndDate}" /></p>        
          <s:if test="project.comments != null">
			<p><span class="reportLabel">Comments</span> ${project.comments}</p>
		  </s:if>
         </s:else>
        </div><!--end panel body-->
      </div><!--end panel-->
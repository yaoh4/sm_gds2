<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta charset="utf-8">
    <title>Submission Details Report</title>
    
<script src="<s:url value="/scripts/jquery-1.12.3.min.js" />"></script>
<script src="<s:url value="/scripts/bootstrap-3.3.6.min.js" />"></script>
<script src="<s:url value="/scripts/jquery.validate-1.15.0.min.js" />"></script>



<link href="<s:url value="/stylesheets/bootstrap-3.3.6.min.css" />" rel="stylesheet" type="text/css" media="screen" />
<link href="<s:url value="/stylesheets/datatables-1.10.12.min.css" />" rel="stylesheet" type="text/css" media="screen" />
<link href="<s:url value="/stylesheets/font-awesome-4.6.3.min.css" />" rel="stylesheet" type="text/css" media="screen" />
<link href='https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,600,600italic,700,700italic,900,900italic,400italic' rel='stylesheet' type='text/css'>
<link href="<s:url value="/stylesheets/custom.css" />" rel="stylesheet" type="text/css" media="screen" />
<link href="<s:url value="/stylesheets/styles.css" />" rel="stylesheet" type="text/css" media="screen">

<s:head />

</head>

<body class="popPage">

  <div style="width: 750px; margin: 20px 20px 20px 20px;">

    <!--   <body class="noSubNav"> -->
    <!-- header-->
    <div class="logo-header"> </div>

    <!-- Fixed navbar -->

    <div id="wrap">
      <div class="navbar  navbar-fixed-top" style="min-width: 750px;">
        <div class="row">
          <div class="container">
            <div style="margin-left: 740px; margin-top: 10px; margin-bottom: 0px;"><a href="" id="close"><i class="fa fa-times-circle fa-2x" aria-hidden="true"></i></a></div>
            <div id="logo" class="logoImage" style="margin-left: 2px;">
              <img src="../images/nci-logo-full.svg" width="450px;"  alt="National Cancer Institute" style="display:inline;">
              <div class="GDS"><h3 style="padding: 0px; margin:0px;">Genomic Data Sharing Tracking System</h3></div>
            </div>
          </div>
          
          <!--Main Navigation -->
          <div id="primNav" class="row">
            <div  class="container" >
              <nav></nav>
            </div>
          </div>
   
          <div class="pageHeader" id="pageHeader">
            <div class="titleWrapper container">
              <h3>Project Submission Details Report: ${project.submissionTitle}</h3>
            </div>
          </div>
        </div>      
      </div>

      <!----------------------------------------General Info Section------------------------------------------ -->
      
      <div class="panel panel-default" id="searchGrant" style="margin-top: 200px;">
        <div class="panel-heading"><span class="clickable panel-collapsed"><i class="fa fa-plus-square fa-lg" aria-hidden="true"></i></span>
          <div class="pheader" style="display:inline;"><h5>General Information</h5></div>
        </div> <!--end panel header-->
        <div class="panel-body" style="display:none;">
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
          <p><span class="reportLabel">Primary Contact:</span> ${project.pocFirstName} ${project.pocLastName} &nbsp;&nbsp;&nbsp; <span class="reportLabel">Email:</span> <s:a href="mailto:%{project.pocEmailAddress}?">${project.pocEmailAddress}</s:a></p>
          <p><span class="reportLabel">Program Director:</span> ${project.pdFirstName} ${project.pdLastName}</p>       
          <p><span class="reportLabel">Project Start Date:</span> <s:property value="%{projectStartDate}" /> &nbsp;&nbsp;&nbsp; <span class="reportLabel">Project End Date:</span> <s:property value="%{projectEndDate}" /></p>        
          <p><span class="reportLabel">Comments</span> ${project.comments}</p>
        </div><!--end panel body-->
      </div><!--end panel-->


      <!-- -----------------------------------GDS Plan Info Section------------------------------------------ -->
      
      <div class="panel panel-default" id="searchGrant" style="margin-top: 20px;">
        <div class="panel-heading"><span class="clickable panel-collapsed"><i class="fa fa-plus-square fa-lg" aria-hidden="true"></i>
          <div class="pheader" style="display:inline;"><h5>Genomic Data Sharing Plan</h5></div>
        </div> <!--end panel header-->
        <div class="panel-body" style="display:none;">
          <p><span class="reportLabel">Data sharing exception requested for this project?</span>  <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_ID)}" /></p>
          <p><span class="reportLabel">Exception approved?</span> <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_ID)}" /></p>
          <p>
            <span class="reportLabel">Uploaded Exception Memo:</span></br>
            <table style="width: 95%;" cellpadding="0px" cellspacing="0" class="table table-bordered table-striped">
              <tbody>
                <tr class="modalTheader">
                  <th class="tableHeader" align="center" width="10%">File Name</th>
                  <th class="tableHeader" align="center" width="10%">Date</th>
                  <th class="tableHeader" align="center" width="10%">Uploaded By</th>
                </tr>
                <tr>
                  <td>
                  	<s:a href="javascript:openDocument(%{exceptionMemo[0].id})">
                  		<s:property	value="%{exceptionMemo[0].fileName}" />
                  	</s:a>
                  </td>
                  <td style="white-space: nowrap">
                  	<s:date name="%{exceptionMemo[0].uploadedDate}" format="MMM dd yyyy hh:mm:ss a" />
                  </td>
                  <td><s:property value="%{exceptionMemo[0].uploadedBy}" /></td>
                  </td>
                </tr>
              </tbody>
            </table>
            <span class="reportLabel">Will there be any data submitted?</span> <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_SUBMITTED_ID)}" />
          </p>
          <p><span class="reportLabel">Types of specimens the data submission pertain to:</span> <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_SPECIMEN_ID)}" /></p>
          <p><span class="reportLabel">Type of data that will be submitted:</span> <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_TYPE_ID)}" /></p>
          <p><span class="reportLabel">Type of access the data will be made available through:</span> <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_ACCESS_ID)}" /> &nbsp;&nbsp;&nbsp; </p>
          <p><span class="reportLabel"> Repository(ies) the data will be submitted to:</span> <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_REPOSITORY_ID)}" /></p>
          <p><span class="reportLabel">Has the GPA reviewed the Data Sharing Plan?</span> <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_GPA_REVIEWED_ID)}" /></p>
          <p>
            <span class="reportLabel">Uploaded Data Sharing Plan:</span><br/>
              <table style="width: 95%;  margin-top: 10px;" cellpadding="0px" cellspacing="0" class="table table-bordered table-striped">
                <tbody><tr class="modalTheader">
                  <th class="tableHeader" align="center" width="10%">Documnent Title</th>
                  <th class="tableHeader" align="center" width="10%">File Name</th>
                  <th class="tableHeader" align="center" width="10%">Date</th>
                  <th class="tableHeader" align="center" width="10%">Uploaded By</th>
                </tr>
                <tr>
                  <td>
                  	<s:if test="%{gdsPlanFile[0].fileName == null || gdsPlanFile[0].fileName == ''}">
						<s:a href="javascript:openDocument(%{gdsPlanFile[0].id})">
						<s:property value="%{gdsPlanFile[0].docTitle}" /></s:a>
					</s:if>
					<s:else>
						<s:property value="%{gdsPlanFile[0].docTitle}" />
					</s:else>
				  </td>
                  <td>
                  	<s:if test="%{gdsPlanFile[0].fileName != null && gdsPlanFile[0].fileName != ''}">
						<s:a href="javascript:openDocument(%{gdsPlanFile[0].id})">
							<s:property value="%{gdsPlanFile[0].fileName}" />
						</s:a>
					</s:if>
				  </td>
                  <td style="white-space: nowrap">
                  	<s:date	name="%{gdsPlanFile[0].uploadedDate}" format="MMM dd yyyy hh:mm:ss a" />
                   </td>
                  <td><s:property value="%{gdsPlanFile[0].uploadedBy}" /></td>
                </tr>
              </tbody>
            </table>
          </p>
          <p><span class="reportLabel">Comments:</span> ${project.planComments}</p>
        </div><!--end panel body-->
      </div><!--end panel-->

      <!--------------------------------Institutional Certifications---------------------------------------- -->

      <div class="panel panel-default" id="searchGrant" style="margin-top: 20px;">
        <div class="panel-heading">
          <span class="clickable panel-collapsed"><i class="fa fa-plus-square fa-lg" aria-hidden="true"></i>
          <div class="pheader" style="display:inline;"><h5>Institutional Certification(s)</h5></div>
        </div> <!--end panel header-->
        <div class="panel-body" style="display:none;">
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
                            <td><span class="question">Approved by GPA: </span><s:property value="%{getLookupDisplayNamebyId(#ic.gpaApprovalCode)}"/></td>
                            <td><span class="question">Provisional or Final? </span></span><s:property value="%{getLookupDisplayNamebyId(#ic.provisionalFinalCode)}"/></td>
                            <td><span class="question">Study for use in Future Projects? </span><s:property value="%{getLookupDisplayNamebyId(#ic.futureProjectUseCode)}"/></td>
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
                                                     <td><span class="question">Institution: </span>${study.institution}</td>
                                                     <td><span class="question">Data Use Limitation(s) Verified? </span><s:property value="%{getLookupDisplayNamebyId(#study.dulVerificationId)}"/></td>
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
                                                             <tbody>
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
                                                            </tbody>
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
        </div><!--end panel body-->
      </div><!--end panel-->

    
    <!-- --------------------------------Basic Info Section--------------------------------------- -->

      <div class="panel panel-default" id="searchGrant" style="margin-top: 20px;">
        <div class="panel-heading"><span class="clickable panel-collapsed"><i class="fa fa-plus-square fa-lg" aria-hidden="true"></i>
          <div class="pheader" style="display:inline;"><h5>Basic Study Information</h5></div>
        </div> <!--end panel header-->
        <div class="panel-body" style="display:none;">
          <p><span class="reportLabel">Has the GPA reviewed the Basic Study Information?</span> ${project.bsiReviewedFlag}</p>  
          <p><span class="reportLabel">Uploaded Basic Study Infomation Form:</span></br>
            <table style="width: 95%;" cellpadding="0px" cellspacing="0" class="table table-bordered table-striped">
              <tbody>
                <tr class="modalTheader">
                  <th class="tableHeader" align="center" width="10%">File Name</th>
                  <th class="tableHeader" align="center" width="10%">Date</th>
                  <th class="tableHeader" align="center" width="10%">Uploaded By</th>
                </tr>
                <tr>
                  <td><i class="fa fa-file-word-o" aria-hidden="true"></i> &nbsp;<s:a href="#" data-original-title="" title=""><s:property value="%{bsiFile[0].fileName}" /></s:a></td>
                  <td style="white-space: nowrap"><s:date name="%{bsiFile[0].uploadedDate}" format="MMM dd yyyy hh:mm:ss a" /></td>
                  <td><a href="mailto: jonesm@mail.nih.gov" data-original-title="" title=""><s:property value="%{bsiFile[0].uploadedBy}" /></a>
                  </td>
                </tr>
              </tbody>
            </table>  
          <p><span class="reportLabel">Comments:</span>${project.bsiComments}</p>
        </div><!--end panel body-->
      </div><!--end panel-->

    </div><!--end container-->
              
    <script src="<s:url value="/controllers/gds.js" />"></script>
    <script src="<s:url value="/controllers/grantSearch.js" />"></script>        	 
  </body>
</html>
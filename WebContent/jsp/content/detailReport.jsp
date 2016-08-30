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
<link href="<s:url value="https://fonts.googleapis.com/css?family=Source+Sans+Pro:500,600,600italic,700,700italic,900,900italic,500italic" />" rel="stylesheet" type="text/css">
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
            <div style="margin-left: 750px; margin-top: 10px; margin-bottom: 0px;"><a href="" id="close"><i class="fa fa-times-circle fa-2x" aria-hidden="true"></i></a></div>
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
         <s:if test="%{project.applicationNum == null}">
           No data entered.
         </s:if>
         <s:else>
           <table width="100%" border="0" cellpadding="3">
    <tr>
      <td width="50%" style="white-space: nowrap"><strong>Project Submission Title:</strong></td>
      <td colspan="4">${project.submissionTitle}</td>
    </tr>
    <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td style="white-space: style="white-space: nowrap""><strong>Reason for being submitted:</strong></td>
      <td colspan="4"><s:property value="%{projectSubmissionReason}" /></td>
    </tr>
    <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td style="white-space: nowrap"><strong>Division/Office/Center:</strong></td>
      <td colspan="4">${project.docAbbreviation}</td>
    </tr>
    <tr>
      <td style="white-space: nowrap"><strong>Program Branch:</strong></td>
      <td colspan="4">${project.programBranch}</td>
    </tr>
    <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td style="white-space: nowrap"><strong>Grant/Intramural/Contract #:</strong></td>
      <td colspan="4">${project.applicationNum}</td>
    </tr>
    <tr>
      <td style="white-space: nowrap"><strong>Grant/Intramural/Contract Project Title:</strong></td>
      <td colspan="4">${project.projectTitle}</td>
    </tr>
    <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td style="white-space: nowrap"><strong>Principal Investigator:</strong></td>
      <td style="white-space: nowrap" colspan="4">${project.piFirstName} ${project.piLastName}</td>
       </tr>
       <tr>
      <td ><strong>Email:</strong></td>
      <td width="67%"><s:a href="mailto:%{project.piEmailAddress}?">${project.piEmailAddress}</s:a></td>
    </tr>
    <tr>
      <td style="white-space: nowrap"><strong>Institution:</strong></td>
      <td colspan="4">${project.piInstitution}</td>
    </tr>
    <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>
      

    <tr>
      <s:if test="project.pocFirstName != null && project.pocLastName != null">
      <td style="white-space: nowrap"><strong>Primary Contact: </strong></td>
      <td colspan="4">${project.pocFirstName} ${project.pocLastName} 
          </s:if>
       
    </tr>
    <tr>
      <s:if test="project.pocEmailAddress != null">      
      <td style="white-space: nowrap"><strong>Email:</strong></td>
      <td colspan="4"><s:a href="mailto:%{project.pocEmailAddress}?">${project.pocEmailAddress}</s:a></td>
      </s:if>
    </tr>
    <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td style="white-space: nowrap"><strong>Program Director:</strong></td>
      <td colspan="4">${project.pdFirstName} ${project.pdLastName}</td>
    </tr>
    <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td style="white-space: nowrap"><strong>Project Start Date:</strong></td>
      <td colspan="4"><s:property value="%{projectStartDate}" /></td>
    </tr>
    <tr>
      <td style="white-space: nowrap"><strong>Project End Date: </strong></td>
      <td colspan="4"><s:property value="%{projectEndDate}" /></td>
    </tr>
    <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="4">&nbsp;</td>
    </tr>
  </table>
         </s:else>
        </div><!--end panel body-->
      </div><!--end panel-->


      <!-- -----------------------------------GDS Plan Info Section------------------------------------------ -->
      
      <div class="panel panel-default" id="searchGrant" style="margin-top: 20px;">
        <div class="panel-heading"><span class="clickable panel-collapsed"><i class="fa fa-plus-square fa-lg" aria-hidden="true"></i>
          <div class="pheader" style="display:inline;"><h5>Genomic Data Sharing Plan</h5></div>
        </div> <!--end panel header-->
        
        <div class="panel-body" style="display:none;">
         <s:if test="%{project.planAnswerSelections.size == 0}">
           No data entered.
         </s:if>
         <s:else>
          <table width="100%" border="0" cellpadding="3">
            <s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_ID) != ''}">
              <tr>
    		    <td width="50%" style="white-space: nowrap"><strong>Data sharing exception requested for this project?</strong></td>
    			<td><s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_SHARING_EXCEPTION_ID)}" /></td>
    		  </tr>
    		  <tr>
      			<td style="white-space: nowrap">&nbsp;</td>
      			<td colspan="4">&nbsp;</td>
    		  </tr> 
    	    </s:if>
    
     
       		<s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_ID) != ''}">
              <tr>   
                <td><strong>Exception approved?</strong></td> 
                <td><s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_EXCEPTION_APPROVED_ID)}" /></td>
      		  </tr>
      		  <tr>
      		    <td style="white-space: nowrap">&nbsp;</td>
      		    <td colspan="4">&nbsp;</td>
    		  </tr>  
      		</s:if>
     

            <s:if test="%{exceptionMemo[0] != null}">
        	  <tr>    
            	<td colspan="2"><strong>Uploaded Exception Memo:</strong><br/>
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
                            <s:property value="%{exceptionMemo[0].fileName}" />
                          </s:a>
                        </td>
                  		<td style="white-space: nowrap">
                    	  <s:date name="%{exceptionMemo[0].uploadedDate}" format="MMM dd yyyy hh:mm:ss a" />
                  		</td>
                  		<td><s:property value="%{exceptionMemo[0].uploadedBy}" />
                  		</td>
                	  </tr>
              		</tbody>
            	  </table>
          		</td>
          	  </tr>
          	  <tr>
      			<td style="white-space: nowrap">&nbsp;</td>
      			<td colspan="4">&nbsp;</td>
    		  </tr>
      		</s:if>
      		

      		<s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_SUBMITTED_ID) != ''}">
        	  <tr>
          	    <td><strong>Will there be any data submitted?</strong></td> 
          		<td><s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_SUBMITTED_ID)}" />
          		</td>
        	  </tr>
        	  <tr>
                <td style="white-space: nowrap">&nbsp;</td>
          	    <td colspan="4">&nbsp;</td>
    		  </tr>
      	    </s:if>
    
     

     
            <s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_SPECIMEN_ID) != ''}">
       	      <tr>
      		    <td><strong>Types of specimens the data submission pertain to:</strpmg></td>
      		    <td> <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_SPECIMEN_ID)}" /></td>
      	      </tr>
      	      <tr>
        	    <td style="white-space: nowrap">&nbsp;</td>
        	    <td colspan="4">&nbsp;</td>
      	      </tr>
        	</s:if>
         		
            <s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_TYPE_ID) != ''}">
      		  <tr>
      		    <td><strong>Type of data that will be submitted:</strong></td>
      		    <td> <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_DATA_TYPE_ID)}" /></td>
              </tr>
              <tr>
      		    <td style="white-space: nowrap">&nbsp;</td>
      		    <td colspan="4">&nbsp;</td>
    		  </tr>
            </s:if>

      
      		<s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_ACCESS_ID) != ''}">
       		  <tr>
       			<td><strong>Type of access the data will be made available through:</strong></td> 
       			<td><s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_ACCESS_ID)}" /> &nbsp;&nbsp;&nbsp; </td>
      		  </tr>
        	  <tr>
      			<td style="white-space: nowrap">&nbsp;</td>
      			<td colspan="4">&nbsp;</td>
    		  </tr>
      		</s:if>
   

      		<s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_REPOSITORY_ID) != ''}">    
       		  <tr>
       			<td style="text-align: top;"><strong> Repository(ies) the data will be submitted to:</strong></td>
       			<td> <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_REPOSITORY_ID)}" /></td>
      		  </tr>
      		  <tr>
      			<td style="white-space: nowrap">&nbsp;</td>
      			<td colspan="4">&nbsp;</td>
    		  </tr>    
      		</s:if>
    
   
        	<s:if test="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_GPA_REVIEWED_ID) != ''}">    
          	  <tr>  
            	<td><strong>Has the GPA reviewed the Data Sharing Plan?</strong></td>
            	<td> <s:property value="%{getAnswerForQuestionInGdsPlan(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PLAN_QUESTION_ANSWER_GPA_REVIEWED_ID)}" /></td>
          	  </tr>
          	  <tr>
      			<td style="white-space: nowrap">&nbsp;</td>
      			<td colspan="4">&nbsp;</td>
    	  	  </tr>     
        	</s:if>
        

       
      		<s:if test="%{gdsPlanFile[0] != null}">
      		  <tr>
      			<td colspan="2">
            	  <strong>Uploaded Data Sharing Plan:</strong><br/>
              		<table style="width: 95%;  margin-top: 10px;" cellpadding="0px" cellspacing="0" class="table table-bordered table-striped">
                	  <tbody>
                	    <tr class="modalTheader">
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
                    	  <s:date name="%{gdsPlanFile[0].uploadedDate}" format="MMM dd yyyy hh:mm:ss a" />
                   		</td>
                  		<td><s:property value="%{gdsPlanFile[0].uploadedBy}" /></td>
                	  </tr>
              	    </tbody>
            	  </table>
          	    </td>
              </tr>
              <tr>
      		    <td style="white-space: nowrap">&nbsp;</td>
      		    <td colspan="4">&nbsp;</td>
    		  </tr>
            </s:if>
    
   		  </table>
        </s:else>
      </div><!--end panel body-->
    </div><!--end panel-->

      <!--------------------------------Institutional Certifications---------------------------------------- -->

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
          <table width="100%" border="0" cellpadding="3"><tr><td width="50%" style="white-space: nowrap"><strong>All Institutional Certifications received?</strong><td><s:property value="%{getDisplayNameByFlag(project.certificationCompleteFlag)}"/></td></tr></table>
          <p>&nbsp;</p>
          <table style="width: 100%;" cellpadding="0px" cellspacing="0" class="table table-bordered">
            <tbody>
              <tr class="modalTheader">
                <th class="tableHeader" align="center" width="23%">Institutional Certification Document</th>
                <th class="tableHeader" align="center" width="19%">Status</th>
                <th class="tableHeader" align="center" width="19%">Missing Data</th>
                <th class="tableHeader" align="center" width="19%">Date Uploaded</th>
               
              </tr>
                    
              <s:iterator status="icStat" var="ic" value="project.institutionalCertifications">
              <div class="icCount">
                <s:set name="icIdx" value="#icStat.index" />
                
                <!--  FILE DISPLAY AND ICONS ROW -->    
                <tr data-id="${ic.id}">
                  <td style="white-space: nowrap">
                    <a href="#" class="icDetails" id="icDetails${ic.id}">
                      <i class="expand fa fa-lg fa-plus-square" id="${ic.id}expand" aria-hidden="true" alt="view" title="view"></i></a>&nbsp;
                    <s:a href="javascript:openDocument(%{#ic.documents[0].id})"><s:property value="%{#ic.documents[0].fileName}" /></s:a>
                  </td>
                  
                <td style="white-space: nowrap">
                <s:hidden id="icReg%{#icStat.index}" value="%{getIcStatusCode(#ic.id)}"/>             
                <div id="icDiv${icStat.index}" class="searchProgess">
              <img src="../images/inprogress.png" alt="In Progress" width="18px" height="18px" title="In Progress" />
              </div>
                  </td>
                  
                  <td style="white-space: nowrap">
                   <s:if test="%{!getIcStatusCode(#ic.id).equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PAGE_STATUS_CODE_COMPLETED)}">
                   <a href="#" onclick="openMissingDataReport(${project.id}, '/gds/manage/viewMissingIcData.action?instCertId=${ic.id}&')"><i class="fa fa-file-text fa-lg" aria-hidden="true"></i></a> &nbsp; &nbsp;
                  </s:if>
                  </td>
                  
                  <td style="white-space: nowrap"> 
                    <s:date name="%{#ic.documents[0].uploadedDate}" format="MMM dd yyyy hh:mm:ss a" />
                  </td>
                      
                  
                </tr>
                        
                   </div>          
              <!--Begin view details-->
             
                <tr>
                  <td colspan="4">
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
                                      <td valign="top" class="question" style="width: 50px;"><p class="number">${studyIdx+1}</p></td>
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

    
    <!-- --------------------------------Basic Info Section--------------------------------------- -->

      <div class="panel panel-default" id="searchGrant" style="margin-top: 20px;">
        <div class="panel-heading"><span class="clickable panel-collapsed"><i class="fa fa-plus-square fa-lg" aria-hidden="true"></i>
          <div class="pheader" style="display:inline;"><h5>Basic Study Information</h5></div>
        </div> <!--end panel header-->
        <div class="panel-body" style="display:none;">
        <s:if test="%{project.bsiReviewedFlag == null}">
          No data entered.
        </s:if>
        <s:else>
          <s:if test="%{project.bsiReviewedFlag != null}">  
          <table width="100%" border="0" cellpadding="3" ><tr><td width="50%" style="white-space: nowrap"><strong>Has the GPA reviewed the Basic Study Information?</strong><td><s:property value="%{getDisplayNameByFlag(project.bsiReviewedFlag)}"/></td></tr></table>  
          </s:if>
          <p>&nbsp;</p>
          <s:if test="%{bsiFile[0] != null}">
          <p><strong>Uploaded Basic Study Infomation Form:</strong><br>
            <table style="width: 95%;" cellpadding="0px" cellspacing="0" class="table table-bordered table-striped">
              <tbody>
                <tr class="modalTheader">
                  <th class="tableHeader" align="center" width="10%">File Name</th>
                  <th class="tableHeader" align="center" width="10%">Date</th>
                  <th class="tableHeader" align="center" width="10%">Uploaded By</th>
                </tr>
                <tr>
                  <td>
                     <s:a href="javascript:openDocument(%{bsiFile[0].id})">
                      <s:property value="%{bsiFile[0].fileName}" />
                    </s:a>
                  </td>
                  <td style="white-space: nowrap"><s:date name="%{bsiFile[0].uploadedDate}" format="MMM dd yyyy hh:mm:ss a" /></td>
                  <td><a href="mailto: jonesm@mail.nih.gov" data-original-title="" title=""><s:property value="%{bsiFile[0].uploadedBy}" /></a>
                  </td>
                </tr>
              </tbody>
            </table>
            </s:if>
         </s:else>
        </div><!--end panel body-->
      </div><!--end panel-->


    <!-- --------------------------------Submission Status--------------------------------------- -->

      <div class="panel panel-default" id="searchGrant" style="margin-top: 20px;">
        <div class="panel-heading"><span class="clickable panel-collapsed"><i class="fa fa-plus-square fa-lg" aria-hidden="true"></i>
          <div class="pheader" style="display:inline;"><h5>Submission Status</h5></div>
        </div> <!--end panel header-->
        <div class="panel-body" style="display:none;">
        <s:if test="%{project.bsiReviewedFlag == null}">
          No data entered.
        </s:if>
        <s:else>
         <table width="100%" border="0" cellpadding="3"><tr><td width="50%" style="white-space: nowrap"><strong>Number of Data Repositories indicated:</strong></td><td><s:property value="%{project.repositoryStatuses.size}"/></td></tr>
          <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="2">&nbsp;</td>
    </tr> 
          <tr><td><strong>Anticipated Submission Date:</strong></td>
            <td><s:date name="%{project.anticipatedSubmissionDate}" format="MM/dd/yyyy"/>  </td> </tr></table> 
            <p>&nbsp;</p>
          <table width="100%" border="1" cellpadding="3"
        class="table  table-bordered">
        <tbody>
          <tr style="background: #e6e6e6;">
            <th>Repositories</th>
            <th>Registration Status</th>
            <th>Submission Status</th>
            <th>Study Released Status</th>
            <th>Accession Number</th>
          </tr>

          <s:iterator value="project.repositoryStatuses" var="r" status="stat">
            <tr>
              <td>
                <s:if test="%{#r.planAnswerSelectionTByRepositoryId.otherText != null}">
                  <s:property value="#r.planAnswerSelectionTByRepositoryId.planQuestionsAnswer.displayText" />
                     - <s:property value="#r.planAnswerSelectionTByRepositoryId.otherText" />
                </s:if>
                <s:else>
                  <s:property value="#r.planAnswerSelectionTByRepositoryId.planQuestionsAnswer.displayText" />
                </s:else></td>
              <td>
                <div class="searchProgess">
                  <s:if
                    test="%{#r.lookupTByRegistrationStatusId.displayName == 'In Progress'}">
                    <img src="../images/inprogress.png" alt="In Progress" width="18px"
                      height="18px" title="In Progress"/>
                  </s:if>
                  <s:elseif
                    test="%{#r.lookupTByRegistrationStatusId.displayName == 'Completed'}">
                    <img src="../images/complete.png" alt="Complete" width="18px"
                      height="18px" title="Complete" />
                  </s:elseif>
                  <s:else>
                    <img src="../images/pending.png" alt="Pending" width="18px"
                      height="18px" title="Pending">
                  </s:else>
                </div>
              </td>
              <td>
                <div class="searchProgess">
                  <s:if
                    test="%{#r.lookupTBySubmissionStatusId.displayName == 'In Progress'}">
                    <img src="../images/inprogress.png" alt="In Progress" width="18px"
                      height="18px"  title="In Progress"/>
                  </s:if>
                  <s:elseif
                    test="%{#r.lookupTBySubmissionStatusId.displayName == 'Completed'}">
                    <img src="../images/complete.png" alt="Complete" width="18px"
                      height="18px"  title="Complete"/>
                  </s:elseif>
                  <s:else>
                    <img src="../images/pending.png" alt="Pending" width="18px"
                      height="18px" title="Pending">
                  </s:else>
                </div>
              </td>
              <td><s:property
                  value="#r.lookupTByStudyReleasedId.displayName" /></td>
              <td><s:property value="#r.accessionNumber" /></td>
            </tr>
          </s:iterator>
        </tbody>
      </table>
          
         </s:else>
        </div><!--end panel body-->
      </div><!--end panel-->


    </div><!--end container-->
              
    <script src="<s:url value="/controllers/gds.js" />"></script>
    <script src="<s:url value="/controllers/grantSearch.js" />"></script>        	 
  </body>
</html>
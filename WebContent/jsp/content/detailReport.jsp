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

  <div style="margin: 20px 20px 20px 20px;">

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

	  <div style="margin-top: 200px;"></div>
      <!----------------------------------------General Info Section------------------------------------------ -->
      
      <s:include value="/jsp/content/submissionGeneralInfoView.jsp"/>

      <!-- -----------------------------------GDS Plan Info Section------------------------------------------ -->
      
      <s:if test="%{showPage('GDSPLAN', project)}">
	  	<s:include value="/jsp/content/submissionGdsPlanView.jsp"/>
	  </s:if>

      <!--------------------------------Institutional Certifications---------------------------------------- -->

 	  <s:if test="%{showPage('IC', project)}">		  
	  	<s:include value="/jsp/content/submissionIcListView.jsp"/>
	  </s:if>
      
      <!-- --------------------------------Basic Info Section--------------------------------------- -->

      <s:if test="%{showPage('BSI', project)}">
		<s:include value="/jsp/content/submissionBasicStudyInfoView.jsp"/>
	  </s:if>
      
    <!-- --------------------------------Submission Status--------------------------------------- -->

	<s:if test="%{showPage('REPOSITORY', project)}">
	  <s:include value="/jsp/content/submissionStatusView.jsp"/>
	</s:if>
      
    </div><!--end container-->
              
    <script src="<s:url value="/controllers/gds.js" />"></script>
    <script src="<s:url value="/controllers/grantSearch.js" />"></script>        	 
  </body>
</html>
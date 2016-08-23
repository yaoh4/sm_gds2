<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta charset="utf-8">
    <title>Missing Data Report</title>
    
<script src="<s:url value="/scripts/jquery-1.12.3.min.js" />"></script>
<script src="<s:url value="/scripts/bootstrap-3.3.6.min.js" />"></script>
<script src="<s:url value="/scripts/jquery.validate-1.15.0.min.js" />"></script>



<link href="<s:url value="/stylesheets/bootstrap-3.3.6.min.css" />" rel="stylesheet" type="text/css" media="screen" />
<link href="<s:url value="/stylesheets/datatables-1.10.12.min.css" />" rel="stylesheet" type="text/css" media="screen" />
<link href="<s:url value="/stylesheets/font-awesome-4.6.3.min.css" />" rel="stylesheet" type="text/css" media="screen" />
<link href='https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,600,600italic,700,700italic,900,900italic,400italic' rel='stylesheet' type='text/css'>

<link href="<s:url value="/stylesheets/styles.css" />" rel="stylesheet" type="text/css" media="screen">

<s:head />

</head>

<body>
 <br/>
<div class="panel panel-danger" id="missing data" style="width: 95%; margin: auto;">          
      
      <div class="panel-heading">
        <div class="pheader"><span style="font-size: 18px; font-weight: bold;">Missing/Incomplete Data Report</span></div>
      </div>
        <div class="panel-body">
    <div align="right">
      <a href="#" onclick="window.print()">
        <i class="fa fa-print" aria-hidden="true"></i>&nbsp;Print Report
      </a>
    </div>
    <br/>
    <table border="0" cellpadding="3"><tr><td style="verticle-align: top;"><i class="fa fa-exclamation-triangle fa-lg" aria-hidden="true" style="color: orange;"></i></td><td><strong>&nbsp;In order for the ${page.displayName} to be moved to the "Completed" status, the following data needs to be provided or updated:</strong></td></tr></table>
 <p>&nbsp;</p>
 

  	<s:iterator status="missingLevel0Stat" var="missingLevel0Data" value="missingDataList">	  
  	  <p>${missingLevel0Stat.index + 1}.&nbsp;&nbsp;${missingLevel0Data.displayText}</p>
  	  <s:if test="%{#missingLevel0Data.childList.size > 0}">
  	    <ol style="list-style-type: square;"> 	    
  	    <s:iterator status="missingLevel1Stat" var="missingLevel1Data" value="#missingLevel0Data.childList">
  	    <li>${missingLevel1Data.displayText}</li>
  	    <s:if test="%{#missingLevel1Data.childList.size > 0}">
  	        <ul class="indent" style="list-style-type: disc">
  	          <s:iterator status="missingLevel2Stat" var="missingLevel2Data" value="#missingLevel1Data.childList">
  		        <li>${missingLevel2Data.displayText}</li>
  		        <s:if test="%{#missingLevel2Data.childList.size > 0}">
  		          <ol style="list-style-type: circle;">
  		            <s:iterator status="missingLevel3Stat" var="missingLevel3Data" value="#missingLevel2Data.childList">		     
  		              <li class="indent2">${missingLevel3Data.displayText}</li>
  		            </s:iterator>
  		          </ol>
  	            </s:if>
  	          </s:iterator> <!-- level2 iteration end -->
  	        </ul>
  	      </s:if>
  	      <br/>
        </s:iterator><!--  level1 iteration end -->
        </ol>
      </s:if>
    </s:iterator> <!--  level0 iteration end -->	
  </div> <!--  end panel body -->
</div> <!--  end panel -->
</body>
</html>
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

<script type="text/javascript" src="scripts/main.js"></script>
  <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<link href="stylesheets/table.css" rel="stylesheet" type="text/css" media="screen">
<link href="stylesheets/styles.css" rel="stylesheet" type="text/css" media="screen">
<link href="stylesheets/displaytag.css" rel="stylesheet" type="text/css" media="screen">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
</head>

<body>
<div class="modal-content2">
  <div class="modal-header">
    <p align="left" class="modal-alert">Missing/Incomplete Data Report</p>
    <p class="modal-warning">In order for the ${missingData.page.displayName} to be moved to the "Completed" status, the following data needs to be provided or updated:</p>
  </div>
  <div class="modal-body" id="template">
  	<div align="right"><img src="images/print.png" width="15px" height="15px">&nbsp;<a href="#">print report</a></div>
  	<s:iterator status="missingLevel0Stat" var="missingLevel0Data" value="missingDataList">	  
  	  <p>${missingLevel0Stat.index + 1}.&nbsp;&nbsp;${missingLevel0Data.displayText}</p>
  	  <s:if test="%{#missingLevel0Data.childList.size > 0}">
  	    <s:iterator status="missingLevel1Stat" var="missingLevel1Data" value="#missingLevel0Data.childList">
  	    <div class="indent"><p>${missingLevel1Stat.index + 1}.&nbsp;&nbsp;${missingLevel1Data.displayText}</p></div>
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
        </s:iterator><!--  level1 iteration end -->
      </s:if>
    </s:iterator> <!--  level0 iteration end -->	
  </div> <!--  end modal body -->
</div> <!--  modal-content2 -->
</body>
</html>
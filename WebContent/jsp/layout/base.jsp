<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>


<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />  
<meta charset="utf-8" />
<title><tiles:insertAttribute name="title" /></title>
<script type="text/javascript" src="<s:url value="/scripts/jquery.js" />"></script>
<link href="<s:url value="/stylesheets/styles.css" />" rel="stylesheet" type="text/css" media="screen"/>
<s:head />

</head>

<body>
	<div id="center">
		<!-- Header -->
		<s:include value="../layout/header.jsp" />
		<!-- end Header -->
				
		<s:if test="hasErrors() || hasActionMessages()">
			<s:include value="../error/errorMessages.jsp" />
		</s:if>

		<!-- Content start -->
		<tiles:insertAttribute name="body" />
		<!-- end Content -->

		<!-- Footer -->
		<tiles:insertAttribute name="footer" />
		<!-- end Footer -->
	</div>
	
</body>
</html>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>


<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta charset="utf-8" />
<title><tiles:insertAttribute name="title" /></title>
<script src="<s:url value="/scripts/jquery-1.12.3.min.js" />"></script>
<script src="<s:url value="/scripts/bootstrap-3.3.6.min.js" />"></script>
<script src="<s:url value="/scripts/bootbox-4.4.0.min.js" />"></script>
<script src="<s:url value="/scripts/bootstrap-datepicker-1.6.1.min.js" />"></script>
<script src="<s:url value="/scripts/datatables-1.10.12.min.js" />"></script>
<script src="<s:url value="/scripts/jquery.validate-1.15.0.min.js" />"></script>
<script src="<s:url value="/scripts/jquery.are-you-sure-1.9.0.js" />"></script>
<script src="<s:url value="/scripts/theme.js" />"></script>

<script src="<s:url value="/controllers/gds.js" />"></script>

<link href="<s:url value="/stylesheets/bootstrap-3.3.6.min.css" />" rel="stylesheet" type="text/css" media="screen" />
<!-- <link href="<s:url value="/stylesheets/non-responsive.css" />" rel="stylesheet" type="text/css" media="screen" /> -->
<link href="<s:url value="/stylesheets/bootstrap-datepicker-1.6.1.min.css" />" rel="stylesheet" type="text/css" media="screen" />
<link href="<s:url value="/stylesheets/datatables-1.10.12.min.css" />" rel="stylesheet" type="text/css" media="screen" />
<link href="<s:url value="/stylesheets/font-awesome-4.6.3.min.css" />" rel="stylesheet" type="text/css" media="screen" />
<link href='https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,600,600italic,700,700italic,900,900italic,400italic' rel='stylesheet' type='text/css'>
<link href="<s:url value="/stylesheets/custom.css" />" rel="stylesheet" type="text/css" media="screen" />
<link href="<s:url value="/stylesheets/datatable.css" />" rel="stylesheet" type="text/css" media="screen" />
<link href="<s:url value="/stylesheets/styles.css" />" rel="stylesheet" type="text/css" media="screen">

<s:head />

</head>

<body>
	<!-- Header -->
	<s:include value="../layout/header.jsp" />
	<!-- end Header -->

	<div id="messages" class="container">
		<s:if test="hasErrors() || hasActionMessages()">
			<s:include value="../error/errorMessages.jsp" />
		</s:if>
	</div>

	<!-- Content start -->
	<div class="container">
		<tiles:insertAttribute name="body" />
	</div>
	<!-- end Content -->

	<!-- Footer -->
	<tiles:insertAttribute name="footer" />
	<!-- end Footer -->

</body>
</html>

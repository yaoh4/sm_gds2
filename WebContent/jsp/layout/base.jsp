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
<script type="text/javascript" src="<s:url value="/scripts/tooltipjquery.js" />"></script>
<script type="text/javascript" src="<s:url value="/scripts/theme.js" />"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.1/js/bootstrap-datepicker.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.15.0/jquery.validate.min.js" type="text/javascript"></script>
<link href="<s:url value="/stylesheets/bootstrap.min.css" />" rel="stylesheet" type="text/css" media="screen" />
<link href="<s:url value="/stylesheets/non-responsive.css" />" rel="stylesheet" type="text/css" media="screen" />
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.1/css/bootstrap-datepicker.css" rel="stylesheet">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.1/css/font-awesome.min.css">
<link href='https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,600,600italic,700,700italic,900,900italic,400italic' rel='stylesheet' type='text/css'>
<link href="<s:url value="/stylesheets/custom.css" />" rel="stylesheet" type="text/css" media="screen" />
<s:head />

</head>

<body>
	<!-- Header -->
	<s:include value="../layout/header.jsp" />
	<!-- end Header -->

	<s:if test="hasErrors() || hasActionMessages()">
		<s:include value="../error/errorMessages.jsp" />
	</s:if>

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

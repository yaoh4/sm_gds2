<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div id="headerimg">

	<div id="cross_nav">
		
		<!-- User Name -->
		<ul id="name">			
			<s:if test="loggedOnUser.lastName != null">
				<li class="last">Name: <strong>
					<s:property	value="loggedOnUser.fullName" /></strong>
				</li>
			</s:if>				
		</ul>
		<ul id="helpReportUrl">
			<li>
				<a href="#" onclick="window.open('<s:property value="%{helpUrl}"/>')">Help</a>
			</li>
			<li class="last">
				<a  href="#" onclick="window.open('<s:property value="%{reportUrl}"/>')">Report</a>
			</li>
		</ul>
	</div>

	<h1 id="naa_header">GDS Tracking System</h1>

	<s:include value="/jsp/layout/navbar.jsp" />
</div>


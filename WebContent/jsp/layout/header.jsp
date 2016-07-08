<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<tiles:useAttribute id="navtab" name="navtab" />
<tiles:useAttribute id="subnavtab" name="subnavtab" />

<div class="logo-header"> </div>

<!-- Fixed navbar -->

<div id="wrap"> <%-- This will be closed after the footer --%>
	<div class="navbar  navbar-fixed-top">
		<div class="row">
			<div class="container">
				<div id="logo" class="logoImage">
					<img src="<s:url value="/images/nci-logo-full.svg" />" width="450px;"  alt="National Cancer Institute">
					<div id="loginName"
						style="float: right; padding-top: 15px; padding-bottom: 15px; margin-right: 15px;"
						class="nav navbar-nav">
						Welcome:
						<div id="loginID" class="login">
							<s:if test="loggedOnUser.lastName != null">
								<s:property value="loggedOnUser.fullName" />
							</s:if>
						</div>
					</div>
					<div class="GDS"><h3 style="padding: 0px; margin:0px; display:inline;">Genomic Data Sharing Tracking System</h3></div>
				</div>
			</div>
			<s:include value="/jsp/layout/navbar.jsp" />

		
		 

    		<!--Page Header -->
    		<s:if test="%{'newSubmission' eq #attr['navtab']}">
    			<s:if test="%{project == null || project.id == null}">
    				<div class="pageHeader" id="pageHeader"><div class="titleWrapper container"><h3>Create New Submission</h3></div></div>
    			</s:if>
    			<s:else>
      				<div class="pageHeader" id="pageHeader"><div class="titleWrapper container"><h3>Track Project Submission</h3>
      				<div id="verisonNumber" style="display:inline-block;">&nbsp;&nbsp;&nbsp;Version 
					<s:property value="%{project.versionNum}" /> (ID: <s:property value="%{project.id}" />)</div>
      				</div></div>
      				<s:include value="/jsp/layout/subnavbar.jsp" />
				</s:else>
      		</s:if>
      		<s:else>
      			<div class="pageHeader" id="pageHeader"><div class="titleWrapper container"><h3>Project Search</h3></div></div>
      		</s:else>
			
		</div>
	</div>


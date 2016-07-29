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
				    <div
				  	    style="float: right; padding-top: 15px; padding-bottom: 15px;"
					    >
					    <div class="nav navbar-nav" style="display:inline-block;">
					  	    Welcome:
						    <div id="loginID" class="login">
							    <s:if test="loggedOnUser.lastName != null">
								    <s:property value="loggedOnUser.fullName" />
							    </s:if>
						    </div>
					    </div>  
					    <div style="display:inline-block; padding-right: 10px;">
					        <img src="<s:url value="/images/version-divider.gif" />" alt="">
					        &nbsp;&nbsp;&nbsp;Env.: <span class="question" style="font-size: 14px;">${environment}</span>
					    </div>
					    <div style="display:inline-block; margin-right: 15px;">
					        <img src="<s:url value="/images/version-divider.gif" />" alt="">
					        &nbsp;&nbsp;&nbsp;Version: <span class="question" style="font-size: 14px;">${version}</span>
					    </div>
				    </div>
				   <div id="popup_nav" style="clear:right;">
				    <ul>
				      <li>
				        <a href="#">Send Comments <img src="<s:url value="/images/arrow_down.png" />" alt=""></a>
		                <ul>
	               			<li><a href="mailto:${businessPolicyEmail}?subject=GDS" >Business Policy Questions</a></li>
	               			<li><a href="mailto:${technicalIssuesEmail}?subject=GDS" >Technical Issues</a></li>												
	               		</ul>
	               	  </li>
	               	</ul>
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
      				<div style="display:inline-block;">&nbsp;(&nbsp;<s:property value="%{project.submissionTitle}"/>&nbsp;)</div>
      				</div></div>
      				<s:include value="/jsp/layout/subnavbar.jsp" />
				</s:else>
      		</s:if>
      		<s:else>
      			<div class="pageHeader" id="pageHeader"><div class="titleWrapper container"><h3>Project Search</h3></div></div>
      		</s:else>
			
		</div>
	</div>


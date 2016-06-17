<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<tiles:useAttribute id="navtab" name="navtab" />
<tiles:useAttribute id="subnavtab" name="subnavtab" />

<div class="logo-header"> </div>

<!-- Fixed navbar -->

<div id="wrap">
	<div class="navbar  navbar-fixed-top">
		<div class="container">
			<div id="logo" class="logoImage">
				<img src="<s:url value="/images/GDSlogo.png" />" width="250px;"
					alt="National Cancer Institute Genomic Data Sharing Tracking System Logo">

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
			</div>
			<s:include value="/jsp/layout/navbar.jsp" />
		</div>
		
		<div class="navbar navbar-default2 navbar-fixed-top" >   

    		<!--Page Header -->
    		<s:if test="%{'newSubmission' eq #attr['navtab']}">
      			<div class="pageHeader" id="pageHeader"><div class="titleWrapper container"><h3>Track Project Submission</h3><div id="verisonNumber" style="display:inline-block;">&nbsp;&nbsp;&nbsp;Version 4 (ID: 123876)</div></div></div>
      		</s:if>
      		<s:else>
      			<div class="pageHeader" id="pageHeader"><div class="titleWrapper container"><h3>&nbsp;</h3></div></div>
      		</s:else>
      		
			<s:include value="/jsp/layout/subnavbar.jsp" />
			
		</div>
		
	</div>

</div>
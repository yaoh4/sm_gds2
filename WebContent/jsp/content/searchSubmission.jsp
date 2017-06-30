<%@ taglib uri="/struts-tags" prefix="s"%>
<body onload="performSearch();"></body>
<s:form action="search" namespace="/search" id="search-form">
	<s:hidden id="directorName" name="criteria.pdFirstAndLastName"/>
	<s:hidden id="readonly" value="%{isReadOnlyUser()}"/>
	<s:hidden id="gpa" value="%{isGPA()}"/>
	<s:hidden id="isReturnToSearch" value="%{returnToSearch}"/>
	<div class="panel project-panel-primary">

		<div class="panel-heading">
		    <div class="pheader">
			  <h4>Search Criteria</h4>
			</div>
		  </div>

		  <!--end panel header-->
		<div class="panel-body">

			<div class="form-group row">
				<div class="col-xs-4">
					<label for="selectFrom">Submission from:</label> &nbsp; <a href="#" class="pop" data-container="body" data-toggle="popover" data-placement="right" data-content="&lt;ul class=&quot;noIndent&quot; style=&quot;padding-left:3px;&quot;&gt; &lt;li&gt;&lt;b&gt;All: &lt;/b&gt;Available to user with GPA role. The system will retrieve all NCI submissions. &lt;/li&gt;
						            &lt;li&gt;&lt;b&gt;My Submissions: &lt;/b&gt;Available to user with Program Director role. The system will retrieve all submissions where the logged in user is the Program Director. Default value for logged in user with PD role. &lt;/li&gt;
									&lt;li&gt;&lt;b&gt;My Created Submissions: &lt;/b&gt; Available to all roles other than Read Only user. The system will retrieve all submissions where the logged in user is the creator. Default value for logged in user with Edit role. &lt;/li&gt;
									&lt;li&gt;&lt;b&gt;Submissions from my DOC: &lt;/b&gt;Available to all roles.The system will retrieve all submissions for the DOC of the logged in user. Default value for logged in user with GPA role and Read Only role. &lt;/li&gt;&lt;/ul&gt;" data-html="true" style="font-size: 12px;">
                                    <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a>
					<s:select id="selectFrom" cssClass="c-select form-control" name="criteria.submissionFromId" list="submissionFromList" listKey="optionKey" listValue="optionValue" />
				</div>

				<div class="col-xs-4">
					<label for="projectTitle">Project Submission Title:</label> 
					<s:textfield  name="criteria.projectTitle" class="form-control" id="projectTitle" maxLength="100"/>
				</div>

				<div class="col-xs-4">
					<label for="grantNumber">Grant/Intramural/Contract # :</label> 
					<s:textfield  name="criteria.grantContractNum" class="form-control" id="grantNumber" maxLength="30"/>
				</div>

			</div>
			<!--end form group-->


			<div class="form-group row">
			
				<div class="col-xs-4">
					<label for="submissionReasonId">Reason for Submission:</label>
					<s:select id="submissionReasonId" name="criteria.submissionReasonId"
						value="criteria.submissionReasonId" cssClass="c-select form-control" 
						list="submissionReasonList" listKey="optionKey" listValue="optionValue" emptyOption="true"/>
				</div>


				<div class="col-xs-4">
					<label for="investigator">Principal Investigator</label>
					<s:textfield  name="criteria.piFirstOrLastName" class="form-control" id="investigator" maxLength="120"/>
				</div>

				<div class="col-xs-4">
					<label for="directorSelect">Program Director:</label>
					<s:select id="directorSelect" name="criteria.pdNpnId"
						value="criteria.pdNpnId" cssClass="c-select form-control" 
						list="pdList" listKey="optionKey" listValue="optionValue" />
				</div>

			</div>

			<div class="form-group row">
				<div class="col-xs-4">
					<s:checkbox id="exclude" name="criteria.excludeCompleted" /> 
					<label for="exclude" style="font-weight: 600;">Exclude
						Submissions in Completed Status &nbsp;</label>
				</div>
			</div>

			<!--end form group-->
			<div class="searchButton">
				<button type="button" class="btn btn-primary has-spinner" id="search-btn"><i class="fa fa-spinner fa-spin"></i> Search</button>
				<s:submit action="navigateToSearch" value=" Clear " class="btn btn-default"/>
			</div>

		
	<s:include value="/jsp/content/searchResult.jsp"/>
</s:form>

</div>
		<!--end panel body-->
	</div>
	<!--end panel-->

<script type="text/javascript"
	src="<s:url value="/controllers/search.js" />"></script>
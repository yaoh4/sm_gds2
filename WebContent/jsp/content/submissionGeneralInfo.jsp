<%@ taglib uri="/struts-tags" prefix="s"%>


	<!--Begin Form -->
	<s:form id="general_form" name="general_form" cssClass="dirty-check" action="viewProject.action" method="post" data-toggle="validator" role="form" >
		<!-- Page navbar -->
		<s:hidden name="projectId" id="projectId" value="%{project.id}"/>
		<s:hidden name="applId" id="applId" value="%{project.applId}"/>
		<s:hidden id="applClassCode" name="project.applClassCode" value="%{project.applClassCode}"/>
		<s:hidden name="project.parentProjectId" id="parentId" value="%{project.parentProjectId}"/>
		<s:hidden name="project.projectGroupId" value="%{project.projectGroupId}"/>
	
	  <div id="searchGrantsContracts"  style="display:none;">
	    <s:include value="/jsp/content/searchGrantsContracts.jsp"/>
	  </div>
		
	  <div id="generalInfoSection">
		<div class="pageNav">
			<s:submit value=" Save " action="manage/saveGeneralInfo" onclick="return warnGeneralInfo()"
				id="general_saved" cssClass="saved btn btn-default" />

			<s:submit type="button" 
				action="manage/saveGeneralInfoAndNext" id="general_saved_next" onclick="return warnGeneralInfoNext()"
				cssClass="btn btn-project-primary">
				Save &amp; Next &nbsp;&nbsp;<i class="fa fa-caret-right" style="color:#ffffff;"></i></s:submit>
		</div>

		<s:if test="project.applId==null">
			<s:set name="isNotEditable" value="false" />
		</s:if>
		<s:else>
			<s:set name="isNotEditable" value="true" />
		</s:else>

		<!-- Begin Panel -->
		<div class="col-md-12">
			<div class="panel  project-panel-primary">
				<div class="panel-heading">
					<div class="pheader">
						<h4>General Information&nbsp;</h4>
					</div>
				</div>
				<p style="font-size: 12px; margin-left: 15px; margin-top: 5px;">
					Note: <i class="fa fa-asterisk" aria-hidden="true"></i>&nbsp;Asterisk
					indicates a required field
				</p>

				<div class="panel-body">
                <div class="form-group row has-feedback">
						<div class="col-xs-10">
							<p class="question">Research Type:</p>
							 <s:radio  name="grantSelection" class="grantSelection" list="#{'G':'Extramural','M':'Intramural','C':'Contract'}" template="radiomap-div.ftl" value="%{project.applClassCode}"/>
								
						</div>
					</div>
					<div class="form-group row has-feedback">
						<div class="col-xs-10">
							<label for="submissionTitle"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Project Submission Title</label> 
								<s:textfield name="project.submissionTitle" cssClass="form-control" id="submissionTitle" placeholder="" value="%{project.submissionTitle}" maxLength="100"/>
						</div>
					</div>
					
					<div class="form-group row has-feedback">
						<div class="col-xs-10">
						<p class="question"> <br/> <i class="fa fa-asterisk" aria-hidden="true"></i>&nbsp;Why is the project being submitted?</p>	
							<s:radio id="submissionReasonId" class="submissionReasonSelect" name="project.submissionReasonId" list="projectSubmissionReasons" template="radiomap-div.ftl"
								listKey="optionKey" listValue="optionValue" />
								
						</div>
					</div>

					<div class="form-group row has-feedback">
					<div id="DivisionOffice">
						<div class="col-xs-6">
							<label for="NCI Division/Office/Center under which the submission is being created"><i
								class="fa fa-asterisk" aria-hidden="true"></i>NCI Division/Office/Center under which the submission is being created</label> 
								<s:if test="project.parentProjectId==null">
								<s:select id="DOC" cssClass="c-select form-control" name="project.docAbbreviation" list="docList" listKey="optionKey" listValue="optionValue"  value="%{preSelectedDOC}"/>								
						</s:if>
						<s:else>
						<s:select id="DOC" cssClass="c-select form-control" name="project.docAbbreviation" list="docList" listKey="optionKey" listValue="optionValue" contenteditable="true" readonly="true"  value="%{preSelectedDOC}"/>
						</s:else>
						</div>
						</div>
					</div>
					
					<div class="form-group row has-feedback">
					<div id="pBranch">
						<div class="col-xs-6">
							<label for="Program Branch"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Branch/Program/Laboratory</label> 
								<s:if test="project.parentProjectId==null">
								<s:select id="programBranch" cssClass="c-select form-control" name="project.programBranch" list="progList" listKey="optionKey" listValue="optionValue"  value="%{project.programBranch}"/>	
				
						</s:if>
						<s:else>
						<s:select id="programBranch" cssClass="c-select form-control" name="project.programBranch" list="progList" listKey="optionKey" listValue="optionValue"  contenteditable="true" readonly="true" value="%{project.programBranch}"/>	
							
						</s:else>
						</div>
						</div>
					</div>
                      
                      <div id="grantDiv">
					<div class="form-group row">
					<div class="col-xs-5">
							<label for="Grant #" id="grantLabel">Intramural/Grant/Contract  #</label>
									

				
								<div class="input-group ">
								  <s:textfield name="project.applicationNum"  maxlength="30" class="form-control" cssclass="form-control" readonly="true" id="grantsContractNum" placeholder="Click on Edit Icon" value="%{project.applicationNum}"/>
								  <div class="input-group-btn">
                                    <a href="#" onclick="openGrantsContractsSearchPage()">
																		<button class="btn btn-default" type="button" title="Edit" style=" margin-left: -2px;">
																			<i class="fa fa-pencil" aria-hidden="true"></i>
																		</button></a> 
								</div>
								</div>
								</div>
					
							<div class="col-xs-5" style="margin-left:-15px">
							<label>&nbsp;</label>
							  <div class="position: relative; display: table; border-collapse: separate;">
														
							<s:hidden name="project.dataLinkFlag" id="dataLinkFlag" value="%{project.dataLinkFlag}">
							<div class="btn-group" id="linkButton">
															
							<a href="javascript: void(0)" class="btn btn-default" type="button" id="link" style="background-color: #d4d4d4; margin-right: -2px;" title="Data is Linked" onclick="linkUnlinkGrants(this)">
							<i class="fa fa-link" aria-hidden="true" alt="Linked" title="Data is Linked"></i></button></a>					
							<a href="javascript: void(0)" id="unlink" class="btn btn-default" title="Link" type="button" onclick="linkUnlinkGrants(this)" title="Data is Unlinked"><i class="fa fa-chain-broken" aria-hidden="true" alt="Unlinked" title="Data is Unlinked"></i></a>
														  
							</div>
	 
							</s:hidden>
						</div>
						</div>
					</div>

					</div>
					
					<div id="canAct">
					<div class="form-group row has-feedback">
                       <div class="col-xs-5">
                           <label for="Cancer Activity"><i class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Cancer Activity</label>
                           <s:textfield name="cancerActivity" cssClass="form-control unlink-group"  id="cancerActivity" value="%{project.cayCode}" placeholder=""  readonly="true">
                           </s:textfield>
                           </div>
                           </div>
                    </div>
                        
                        <div id="title">   
					<div class="form-group row has-feedback">
						<div class="col-xs-10">
							<label for="Project Title" id="projectTitleLabel">Intramural/Grant/Contract Project Title</label> 
							<s:textfield name="project.projectTitle" cssClass="form-control unlink-group" id="projectTitle" placeholder="" value="%{project.projectTitle}"  maxLength="100"/>
						</div>
					</div>
					</div>

					<div class="row">
						<div class="form-group col-xs-5 has-feedback">
							<label for="First Name of Principal Investigator"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>First
								Name of Principal Investigator</label> 
								<s:textfield name="project.piFirstName" cssClass="form-control unlink-group" id="fnPI" placeholder="" value="%{project.piFirstName}"  maxLength="30"/>
						</div>
						<div class="form-group col-xs-5 has-feedback">
							<label for="Last Name of Principal Investigator"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Last
								Name of Principal Investigator</label>
								<s:textfield name="project.piLastName" cssClass="form-control unlink-group" id="lnPI" placeholder="" value="%{project.piLastName}"  maxLength="30"/>								
						</div>
					</div>

					<div class="form-group row has-feedback">
						<div class="col-xs-6">
							<label for="Email of Principal Investigator"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Email
								of Principal Investigator</label>
								<s:textfield name="project.piEmailAddress" cssClass="form-control unlink-group" id="piEmail" placeholder="Enter Vaild Email Address"
								data-error="Email address is invalid" value="%{project.piEmailAddress}"  maxLength="80"/>								
						</div>
						<div class="help-block with-errors" style="margin-left: 15px"></div>
					</div>
                    
                    <div id="piInstution">
					<div class="form-group row has-feedback">
						<div class="col-xs-10">
							<label for="Institution of Principal Investigator"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Institution
								of Principal Investigator</label>
								<s:textfield name="project.piInstitution" cssClass="form-control unlink-group" id="PIInstitute" placeholder="" value="%{project.piInstitution}"  maxLength="120"/>								
						</div>
					</div>
                    </div>
                    
					<div class="row">
						<div class="form-group col-xs-5 has-feedback">
							<label for="First Name of Principal Investigator">First
								Name of Primary Contact</label> 
								<s:textfield name="project.pocFirstName" cssClass="form-control" id="fnPC" placeholder="Required if No Principal Investigator" value="%{project.pocFirstName}" maxLength="30"/>								
						</div>
						<div class="form-group col-xs-5 has-feedback">
							<label for="Last Name of Primary Contact">Last Name of
								Primary Contact</label> 
								<s:textfield name="project.pocLastName" cssClass="form-control" id="lnPC" placeholder="Required if No Principal Investigator" value="%{project.pocLastName}" maxLength="30"/>								
						</div>
					</div>

					<div class="form-group row has-feedback">
						<div class="col-xs-6">
							<label for="Email of Principal Investigator">Email of
								Primary Contact</label>
						<s:textfield name="project.pocEmailAddress" cssClass="form-control" id="PCemail" placeholder="Enter Vaild Email Address" data-error="Email address is invalid" value="%{project.pocEmailAddress}" maxLength="80"/>								
						</div>
						<div class="help-block with-errors" style="margin-left: 15px"></div>
					</div>
					
					<div id="pdName" class="row pdirector ">
						<div class="form-group col-xs-5 has-feedback">
							<label for="First Name of Program Director"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp; </i>First
								Name of Program Director</label>
								<s:textfield name="project.pdFirstName" cssClass="form-control unlink-group" id="fnPD" placeholder="" value="%{project.pdFirstName}"  maxLength="30"/>								
						</div>
						<div class="form-group col-xs-5 has-feedback">
							<label for="Last Name of Program Director"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp; </i>Last
								Name of Program Director</label>
								<s:textfield name="project.pdLastName" cssClass="form-control unlink-group" id="lnPD" placeholder="" value="%{project.pdLastName}"  maxLength="30"/>								
						</div>
					</div>
				</div>

				<div class="row pdates">
					<div id="pStartDate"
						class="form-group col-xs-2 projectDates has-feedback">
						<label for="Project Start Date"><i
							class="fa fa-asterisk eAsterisk" aria-hidden="true">&nbsp;</i>Project
							Start Date</label>
						<div class="input-group date">
						
						<s:textfield name="project.projectStartDate" cssClass="form-control unlink-group" id="projectStartDate" value="%{projectStartDate}" />	
						<span
								class="input-group-addon"><i
								class="glyphicon glyphicon-th"></i></span>

						</div>
					</div>

					<div id="pEndDate"
						class="form-group col-xs-2 projectDates has-feedback">
						<label for="Project End Date"><i
							class="fa fa-asterisk eAsterisk" aria-hidden="true">&nbsp;</i>Project
							End Date</label>
						<div class="input-group date">
						<s:textfield name="project.projectEndDate" cssClass="form-control unlink-group" id="projectEndDate" value="%{projectEndDate}" />	
							<span
								class="input-group-addon"><i
								class="glyphicon glyphicon-th"></i></span>

						</div>
					</div>

					<div class="form-group row  col-xs-12" style="padding-left: 30px;">

						<label for="general info comments" class="label_stCom">Comments (2000 Characters):</label><br />
						<s:textarea cssClass="col-md-12 form-control input " rows="3" maxlength="2000"
							name="project.comments" id="gComments"></s:textarea>
					</div>


		<script type="text/javascript" src="<s:url value="/controllers/grantSearch.js"/>"></script>		</div>
			</div>
			<!--end panel body-->
		</div>
		<!--end panel-->



		<!--SAVE & NEXT BUTTONS-->
		<div class="pageNav">
			<s:submit value=" Save " namespace="/manage" action="saveGeneralInfo" onclick="return warnGeneralInfo()"
				id="general_saved" cssClass="saved btn btn-default" />

			<s:submit type="button" namespace="/manage"
				action="saveGeneralInfoAndNext" id="general_saved_next" onclick="return warnGeneralInfoNext()"
				cssClass="btn btn-project-primary" >
				Save &amp; Next &nbsp;&nbsp;<i class="fa fa-caret-right" style="color:#ffffff;"></i></s:submit>
		</div>

	  </div> <!-- generalInfoPage end -->
	</s:form>

<!-- /container -->

<script type="text/javascript" src="<s:url value="/controllers/gds.js"/>"></script>
<script type="text/javascript" src="<s:url value="/controllers/grantSearch.js"/>"></script>
<script type="text/javascript" src="<s:url value="/controllers/general.js"/>"></script>
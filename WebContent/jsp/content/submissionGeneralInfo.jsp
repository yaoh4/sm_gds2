<%@ taglib uri="/struts-tags" prefix="s"%>


	<!--Begin Form -->
	<s:form id="general_form" name="general_form" cssClass="dirty-check" action="viewProject.action" method="post" data-toggle="validator" role="form" >
		<!-- Page navbar -->
		<s:hidden name="projectId" value="%{project.id}"/>
		<s:hidden name="applId" id="applId" value="%{project.applId}"/>
	
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
							<label for="submissionTitle"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Project or Sub-project Submission Title</label> 
								<s:textfield name="project.submissionTitle" cssClass="form-control" id="submissionTitle" placeholder="" value="%{project.submissionTitle}" maxLength="100"/>
						</div>
					</div>
					
					<div class="form-group row has-feedback">
						<div class="col-xs-10">
						<p class="question"> <br/> <i class="fa fa-asterisk" aria-hidden="true"></i>&nbsp;Why is the project being submitted?</p>						
							<s:radio id="submissionReasonId" name="project.submissionReasonId" list="projectSubmissionReasons" template="radiomap-div.ftl"
								listKey="optionKey" listValue="optionValue" />
						
						</div>
					</div>

					<div class="form-group row has-feedback">
						<div class="col-xs-5">
							<label for="Division/Office/Center"><i
								class="fa fa-asterisk" aria-hidden="true"></i>&nbsp;
								Division/Office/Center</label> 
								<s:select id="DOC" cssClass="c-select form-control" name="project.docAbbreviation" list="docList" listKey="optionKey" listValue="optionValue" value="%{preSelectedDOC}"/>								
						</div>
					</div>
					
					<div class="form-group row has-feedback">
						<div class="col-xs-5">
							<label for="Program Branch"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Program
								Branch</label> 
								<s:textfield name="project.programBranch" cssClass="form-control" id="programBranch" placeholder="Enter Full Branch Name" value="%{project.programBranch}" maxLength="30"/>
						</div>
					</div>

					<div class="form-group row">
						<div class="col-xs-5">
							<label for="Intramural (Z01)/Grant/Contract #"><i class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Intramural (Z01)/Grant/Contract #</label>
							<div class="input-group2">
							
							<s:textfield name="project.applicationNum" cssClass="form-control" readOnly="true" id="grantsContractNum" placeholder="Click on Edit Icon" value="%{project.applicationNum}"/>
							<span class="input-group-btn"><a href="#" onclick="openGrantsContractsSearchPage()">
										<button class="btn btn-default" type="button" title="edit">
											<i class="fa fa-pencil" aria-hidden="true"></i>
										</button>
							</a></span>
							
							<s:hidden name="project.dataLinkFlag" id="dataLinkFlag" value="%{project.dataLinkFlag}"/>
							
							<span class="input-group-btn">						
							  <a href="javascript: void(0)" id="link" style="display:none;" onclick="linkUnlinkGrants(this)">
							    <button class="btn btn-default" type="button" title="Unlinked. Click to Link">
								  <i class="fa fa-chain-broken" aria-hidden="true" alt="unlink" title="unlink"></i>
								</button>
							  </a>
							  <a href="javascript: void(0)" id="unlink" style="display:none;" onclick="linkUnlinkGrants(this)">
								<button class="btn btn-default" type="button" title="Linked. Click to Unlink">
								  <i class="fa fa-link" aria-hidden="true" alt="link" title="link"></i>
								</button>
							  </a>
							</span>
	 
								 
							</div>
						</div>
					</div>

					<div class="form-group row has-feedback">
						<div class="col-xs-10">
							<label for="Project Title"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Intramural/Grant/Contract Project Title</label> 
								<s:textfield name="project.projectTitle" cssClass="form-control unlink-group" id="projectTitle" placeholder="" value="%{project.projectTitle}" disabled="isNotEditable" maxLength="100"/>
						</div>
					</div>

					<div class="row">
						<div class="form-group col-xs-5 has-feedback">
							<label for="First Name of Principal Investigator"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>First
								Name of Principal Investigator</label> 
								<s:textfield name="project.piFirstName" cssClass="form-control unlink-group" id="fnPI" placeholder="" value="%{project.piFirstName}" disabled="isNotEditable" maxLength="30"/>
						</div>
						<div class="form-group col-xs-5 has-feedback">
							<label for="Last Name of Principal Investigator"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Last
								Name of Principal Investigator</label>
								<s:textfield name="project.piLastName" cssClass="form-control unlink-group" id="lnPI" placeholder="" value="%{project.piLastName}" disabled="isNotEditable" maxLength="30"/>								
						</div>
					</div>

					<div class="form-group row has-feedback">
						<div class="col-xs-5 ">
							<label for="Email of Principal Investigator"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Email
								of Principal Investigator</label>
								<s:textfield name="project.piEmailAddress" cssClass="form-control unlink-group" id="piEmail" placeholder="Enter Vaild Email Address"
								data-error="Email address is invalid" value="%{project.piEmailAddress}" disabled="isNotEditable" maxLength="80"/>								
						</div>
						<div class="help-block with-errors" style="margin-left: 15px"></div>
					</div>

					<div class="form-group row has-feedback">
						<div class="col-xs-5 ">
							<label for="Institution of Principal Investigator"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Institution
								of Principal Investigator</label>
								<s:textfield name="project.piInstitution" cssClass="form-control unlink-group" id="PIInstitute" placeholder="" value="%{project.piInstitution}" disabled="isNotEditable" maxLength="120"/>								
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
						<div class="col-xs-5">
							<label for="Email of Principal Investigator">Email of
								Primary Contact</label>
						<s:textfield name="project.pocEmailAddress" cssClass="form-control" id="PCemail" placeholder="Enter Vaild Email Address" data-error="Email address is invalid" value="%{project.pocEmailAddress}" maxLength="80"/>								
						</div>
						<div class="help-block with-errors" style="margin-left: 15px"></div>
					</div>


					<div class="row pdirector ">
						<div class="form-group col-xs-5 has-feedback">
							<label for="First Name of Program Director"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp; </i>First
								Name of Program Director</label>
								<s:textfield name="project.pdFirstName" cssClass="form-control unlink-group" id="fnPD" placeholder="" value="%{project.pdFirstName}" disabled="isNotEditable" maxLength="30"/>								
						</div>
						<div class="form-group col-xs-5 has-feedback">
							<label for="Last Name of Program Director"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp; </i>Last
								Name of Program Director</label>
								<s:textfield name="project.pdLastName" cssClass="form-control unlink-group" id="lnPD" placeholder="" value="%{project.pdLastName}" disabled="isNotEditable" maxLength="30"/>								
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
						
						<s:textfield name="project.projectStartDate" cssClass="form-control unlink-group" id="projectStartDate" value="%{projectStartDate}" disabled="isNotEditable"/>	
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
						<s:textfield name="project.projectEndDate" cssClass="form-control unlink-group" id="projectEndDate" value="%{projectEndDate}" disabled="isNotEditable"/>	
							<span
								class="input-group-addon"><i
								class="glyphicon glyphicon-th"></i></span>

						</div>
					</div>

					<div class="form-group row  col-xs-12" style="padding-left: 30px;">

						<label for="general info comments" class="label_stCom">Comments (2000 Characters):</label><br />
						<s:textarea cssClass="col-md-12 form-control input " rows="3"
							name="project.comments" id="gComments"></s:textarea>
					</div>


				</div>
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


<script type="text/javascript" src="<s:url value="/controllers/general.js"/>"></script>
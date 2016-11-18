<%@ taglib uri="/struts-tags" prefix="s"%>


	<!--Begin Form -->
	<s:form id="general_form" name="general_form" cssClass="dirty-check" action="viewProject.action" method="post" data-toggle="validator" role="form" >
		<!-- Page navbar -->
		<s:hidden name="projectId" id="projectId" value="%{project.id}"/>
		<s:hidden name="project.parentProjectId" id="parentId" value="%{project.parentProjectId}"/>
		<s:hidden name="project.projectGroupId" value="%{project.projectGroupId}"/>
		<s:hidden name="searchType" id="searchType" value="%{searchType}"/>
		<s:hidden id="grantContractIdPrefix"/>
	
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

		<s:if test="extramuralGrant.applId==null">
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
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Project Submission Title</label> 
								<s:textfield name="project.submissionTitle" cssClass="form-control" id="submissionTitle" placeholder="" value="%{project.submissionTitle}" maxLength="100"/>
						</div>
					</div>

					<div class="form-group row has-feedback" id="submissionReason">
						<div class="col-xs-10">
						<p class="question"> <br/> <i class="fa fa-asterisk" aria-hidden="true"></i>&nbsp;Why is the project being submitted?</p>	
							<s:radio id="submissionReasonId" class="submissionReasonSelect" name="project.submissionReasonId" list="projectSubmissionReasons" template="radiomap-div.ftl"
								listKey="optionKey" listValue="optionValue" />
								
						</div>
					</div>


                <div class="form-group row has-feedback" id="researchType">
						<div class="col-xs-10">
							<p class="question">Research Type:</p>
							 <s:radio  name="grantSelection" class="grantSelection" list="#{'Extramural':'Extramural','Intramural':'Intramural','Both':'Both'}" template="radiomap-div.ftl" value="%{grantSelection}"/>
								
						</div>
					</div>
					
					
					

					<div class="form-group row has-feedback">
					<div id="DivisionOffice" style="display:none;">
						<div class="col-xs-6">
							<label for="NCI Division/Office/Center under which the submission is being created"><i
								class="fa fa-asterisk" aria-hidden="true"></i>NCI Division/Office/Center under which the submission is being created</label> 
								<s:if test="project.parentProjectId==null">
								<s:select id="DOC" cssClass="c-select form-control" name="project.docAbbreviation" list="docList" listKey="optionKey" listValue="optionValue"  value="%{preSelectedDOC}"/>								
						</s:if>
						<s:else>
						<s:select id="DOC" cssClass="c-select form-control" name="project.docAbbreviation" list="docList" listKey="optionKey" listValue="optionValue" disabled="true"  value="%{preSelectedDOC}"/>
						</s:else>
						</div>
						</div>
					</div>
					
					<div class="form-group row has-feedback" >
					<div id="pBranch" style="display:none;">
						<div class="col-xs-6">
							<label for="Program Branch"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Branch/Program/Laboratory</label> 
								<s:if test="project.parentProjectId==null">
								<s:select id="programBranch" cssClass="c-select form-control" name="project.programBranch" list="progList" listKey="optionKey" listValue="optionValue"  value="%{project.programBranch}"/>	
				
						</s:if>
						<s:else>
						<s:select id="programBranch" cssClass="c-select form-control" name="project.programBranch" list="progList" listKey="optionKey" listValue="optionValue"  disabled="true"  value="%{project.programBranch}"/>	
							
						</s:else>
						</div>
						</div>
					</div>
                   
 
					<!--START EXTRAMURAL GRANT BOX -->
					<div class="panel panel-default muralBox" id="extramuralDiv" style="display:none;">
  					<div class="panel-heading" style="font-weight: bold;">EXTRAMURAL</div>
  					<div class="panel-body">
                      <div id="extramural_grantDiv" style="display:none;">
					<div class="row">
					<div class="col-xs-5" id="exGrantSearch">
							<label for="Grant #">Grant# or Contract#</label>									

								<s:hidden name="extramuralGrant.grantContractType"  value="%{extramuralGrant.grantContractType}"/>
								<s:hidden name="extramuralGrant.primaryGrantContractFlag"  value="%{extramuralGrant.primaryGrantContractFlag}"/>
								<s:hidden name="extramuralGrant.createdBy"  value="%{extramuralGrant.createdBy}"/>
								
								<div class="input-group ">
								  <s:textfield name="extrmuralGrant.grantContractNum"  maxlength="271" class="form-control" cssclass="form-control" readonly="true" id="extramural_grantsContractNum" placeholder="Click on Edit Icon" value="%{extramuralGrant.grantContractNum}"/>
								  <div class="input-group-btn">
                                    <a href="#" onclick="openGrantsContractsSearchPage('extramural', 'extramural')">
																		<button class="btn btn-default" type="button" title="Edit" style=" margin-left: -2px;">
																			<i class="fa fa-pencil" aria-hidden="true"></i>
																		</button></a> 
								</div>
								</div>
								</div>
					
							<div class="col-xs-5" style="margin-left:-15px">
							<label>&nbsp;</label>
							  <div class="position: relative; display: table; border-collapse: separate;">
														
							<s:hidden name="extramuralGrant.dataLinkFlag" id="dataLinkFlag" value="%{extramuralGrant.dataLinkFlag}">
							<s:if test="isGPA()">
							<div class="btn-group" id="linkButton">
															
							<a href="javascript: void(0)" class="btn btn-default" type="button" id="link" style="background-color: #d4d4d4; margin-right: -2px;" title="Data is Linked" onclick="linkUnlinkGrants(this)">
							<i class="fa fa-link" aria-hidden="true" alt="Linked" title="Data is Linked"></i></button></a>					
							<a href="javascript: void(0)" id="unlink" class="btn btn-default" type="button" onclick="linkUnlinkGrants(this)" title="Data is Unlinked"><i class="fa fa-chain-broken" aria-hidden="true" alt="Unlinked" title="Data is Unlinked"></i></a>
														  
							</div>
	 						</s:if>
							</s:hidden>
						</div>
						</div>
					</div>

					</div>
					
					<div id="canAct">
					<div class="row has-feedback">
                       <div class="col-xs-5">
                           <label for="Cancer Activity"><i class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Cancer Activity</label>
                           <s:textfield name="cancerActivity" cssClass="form-control unlink-group"  id="cancerActivity" value="%{project.cayCode}" placeholder=""  readonly="true">
                           </s:textfield>
                           </div>
                           </div>
                    </div>
                        
                        <div id="title">   
					<div class="row has-feedback">
						<div class="col-xs-10">
							<label for="Project Title" id="projectTitleLabel">Extramural or Contract Project Title</label> 
							<s:textfield name="extramuralGrant.projectTitle" cssClass="form-control unlink-group" id="extramural_projectTitle" placeholder="" value="%{project.projectTitle}"  maxLength="100"/>
						</div>
					</div>
					</div>

					<div class="row">
						<div class="col-xs-5 has-feedback">
							<label for="First Name of Principal Investigator"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>First
								Name of Principal Investigator</label> 
								<s:textfield name="extramuralGrant.piFirstName" cssClass="form-control unlink-group" id="extramural_fnPI" placeholder="" value="%{project.piFirstName}"  maxLength="30"/>
						</div>
						<div class="col-xs-5 has-feedback">
							<label for="Last Name of Principal Investigator"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Last
								Name of Principal Investigator</label>
								<s:textfield name="extramuralGrant.piLastName" cssClass="form-control unlink-group" id="extramural_lnPI" placeholder="" value="%{project.piLastName}"  maxLength="30"/>								
						</div>
					</div>

					<div class="row has-feedback">
						<div class="col-xs-6">
							<label for="Email of Principal Investigator"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Email
								of Principal Investigator</label>
								<s:textfield name="extramuralGrant.piEmailAddress" cssClass="form-control unlink-group" id="extramural_piEmail" placeholder="Enter Vaild Email Address"
								data-error="Email address is invalid" value="%{extramuralGrant.piEmailAddress}"  maxLength="80"/>								
						</div>
						<div class="help-block with-errors" style="margin-left: 15px"></div>
					</div>
                    
                    <div id="piInstution">
					<div class="row has-feedback">
						<div class="col-xs-10">
							<label for="Institution of Principal Investigator"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Institution
								of Principal Investigator</label>
								<s:textfield name="extramuralGrant.piInstitution" cssClass="form-control unlink-group" id="extramural_PIInstitute" placeholder="" value="%{extramuralGrant.piInstitution}"  maxLength="120"/>								
						</div>
					</div>
                    </div>
                    
					<div class="row">
						<div class="col-xs-5 has-feedback">
							<label for="First Name of Primary Contact">First
								Name of Primary Contact</label> 
								<s:textfield name="extramuralGrant.pocFirstName" cssClass="form-control" id="fnPC" placeholder="Required if No Principal Investigator" value="%{extramuralGrant.pocFirstName}" maxLength="30"/>								
						</div>
						<div class="col-xs-5 has-feedback">
							<label for="Last Name of Primary Contact">Last Name of
								Primary Contact</label> 
								<s:textfield name="extramuralGrant.pocLastName" cssClass="form-control" id="lnPC" placeholder="Required if No Principal Investigator" value="%{extramuralGrant.pocLastName}" maxLength="30"/>								
						</div>
					</div>

					<div class="row has-feedback">
						<div class="col-xs-6">
							<label for="Email of Principal Investigator">Email of
								Primary Contact</label>
						<s:textfield name="extramuralGrant.pocEmailAddress" cssClass="form-control" id="PCemail" placeholder="Enter Vaild Email Address" data-error="Email address is invalid" value="%{extramuralGrant.pocEmailAddress}" maxLength="80"/>								
						</div>
						<div class="help-block with-errors" style="margin-left: 15px"></div>
					</div>
					
					<div id="pdName" class="row pdirector ">
						<div class="col-xs-5 has-feedback">
							<label for="First Name of Program Director"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp; </i>First
								Name of Program Director</label>
								<s:textfield name="extramuralGrant.pdFirstName" cssClass="form-control unlink-group" id="fnPD" placeholder="" value="%{extramuralGrant.pdFirstName}"  maxLength="30"/>								
						</div>
						<div class="col-xs-5 has-feedback">
							<label for="Last Name of Program Director"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp; </i>Last
								Name of Program Director</label>
								<s:textfield name="extramuralGrant.pdLastName" cssClass="form-control unlink-group" id="lnPD" placeholder="" value="%{extramuralGrant.pdLastName}"  maxLength="30"/>								
						</div>
					</div>
				

				<div class="row pdates">
					<div id="pStartDate"
						class="col-xs-2 projectDates has-feedback">
						<label for="Project Start Date"><i
							class="fa fa-asterisk eAsterisk" aria-hidden="true">&nbsp;</i>Project
							Start Date</label>
						<div class="input-group date">
						
						<s:textfield name="extramuralGrant.projectStartDate" cssClass="form-control unlink-group" id="projectStartDate" value="%{extramuralGrant.projectStartDate}" />	
						<span
								class="input-group-addon"><i
								class="glyphicon glyphicon-th"></i></span>

						</div>
					</div>

					<div id="pEndDate"
						class="col-xs-2 projectDates has-feedback">
						<label for="Project End Date"><i
							class="fa fa-asterisk eAsterisk" aria-hidden="true">&nbsp;</i>Project
							End Date</label>
						<div class="input-group date">
						<s:textfield name="extramuralGrant.projectEndDate" cssClass="form-control unlink-group" id="projectEndDate" value="%{extramuralGrant.projectEndDate}" />	
							<span
								class="input-group-addon"><i
								class="glyphicon glyphicon-th"></i></span>

						</div>
					</div>
					</div>

				</div> <!--end panel body-->
				</div> <!--end panel -->


					<!--START INTRAMURAL GRANT BOX -->
					 <div class="panel panel-default muralBox" id="intramuralDiv" style="display:none;">
  <div class="panel-heading" style="font-weight: bold;">INTRAMURAL</div>
  <div class="panel-body">


					 <div id="intramural_grantDiv">
					<div class="row">
					<div class="col-xs-5">
							<label for="Grant #" id="grantLabel">Intramural# or Contract#</label>									

								<s:hidden name="intramuralGrant.grantContractType"  value="%{intramuralGrant.grantContractType}"/>
								<s:hidden name="intramuralGrant.primaryGrantContractFlag"  value="%{intramuralGrant.primaryGrantContractFlag}"/>
								<s:hidden name="intramuralGrant.createdBy"  value="%{intramuralGrant.createdBy}"/>
								
								<div class="input-group ">
								  <s:textfield name="intramuralGrant.grantContractNum"  maxlength="271" class="form-control" cssclass="form-control" readonly="true" id="intramural_grantsContractNum" placeholder="Click on Edit Icon" value="%{intramuralGrant.grantContractNum}"/>
								  <div class="input-group-btn">
                                    <a href="#" onclick="openGrantsContractsSearchPage('intramural', 'intramural')">
																		<button class="btn btn-default" type="button" title="Edit" style=" margin-left: -2px;">
																			<i class="fa fa-pencil" aria-hidden="true"></i>
																		</button></a> 
								</div>
								</div>
								</div>
					
							<div class="col-xs-5" style="margin-left:-15px">
							<label>&nbsp;</label>
							  <div class="position: relative; display: table; border-collapse: separate;">
														
							<s:hidden name="intramuralGrant.dataLinkFlag" id="dataLinkFlag" value="%{intramuralGrant.dataLinkFlag}">
							<s:if test="isGPA()">
							<div class="btn-group" id="linkButton">
															
							<a href="javascript: void(0)" class="btn btn-default" type="button" id="link" style="background-color: #d4d4d4; margin-right: -2px;" title="Data is Linked" onclick="linkUnlinkGrants(this)">
							<i class="fa fa-link" aria-hidden="true" alt="Linked" title="Data is Linked"></i></button></a>					
							<a href="javascript: void(0)" id="unlink" class="btn btn-default" type="button" onclick="linkUnlinkGrants(this)" title="Data is Unlinked"><i class="fa fa-chain-broken" aria-hidden="true" alt="Unlinked" title="Data is Unlinked"></i></a>
														  
							</div>
	 						</s:if>
							</s:hidden>
						</div>
						</div>
					</div>

					</div>
					
		
                        
                        <div id="title">   
					<div class="row has-feedback">
						<div class="col-xs-10">
							<label for="Project Title" id="projectTitleLabel">Intramural or Contract Project Title</label> 
							<s:textfield name="intramuralGrant.projectTitle" cssClass="form-control unlink-group" id="projectTitle" placeholder="" value="%{project.projectTitle}"  maxLength="100"/>
						</div>
					</div>
					</div>

					<div class="row">
						<div class="col-xs-5 has-feedback">
							<label for="First Name of Principal Investigator"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>First
								Name of Principal Investigator</label> 
								<s:textfield name="intramuralGrant.piFirstName" cssClass="form-control unlink-group" id="intramural_fnPI" placeholder="" value="%{project.piFirstName}"  maxLength="30"/>
						</div>
						<div class="form-group col-xs-5 has-feedback">
							<label for="Last Name of Principal Investigator"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Last
								Name of Principal Investigator</label>
								<s:textfield name="intramuralGrant.piLastName" cssClass="form-control unlink-group" id="intramural_lnPI" placeholder="" value="%{project.piLastName}"  maxLength="30"/>								
						</div>
					</div>

					<div class="row has-feedback">
						<div class="col-xs-6">
							<label for="Email of Principal Investigator"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Email
								of Principal Investigator</label>
								<s:textfield name="intramuralGrant.piEmailAddress" cssClass="form-control unlink-group" id="intramural_piEmail" placeholder="Enter Vaild Email Address"
								data-error="Email address is invalid" value="%{intramuralGrant.piEmailAddress}"  maxLength="80"/>								
						</div>
						<div class="help-block with-errors" style="margin-left: 15px"></div>
					</div>
                    
                    
                    
					<div class="row">
						<div class="col-xs-5 has-feedback">
							<label for="First Name of Primary Contact">First
								Name of Primary Contact</label> 
								<s:textfield name="intramuralGrant.pocFirstName" cssClass="form-control" id="fnPC" placeholder="Required if No Principal Investigator" value="%{intramuralGrant.pocFirstName}" maxLength="30"/>								
						</div>
						<div class="form-group col-xs-5 has-feedback">
							<label for="Last Name of Primary Contact">Last Name of
								Primary Contact</label> 
								<s:textfield name="intramuralGrant.pocLastName" cssClass="form-control" id="lnPC" placeholder="Required if No Principal Investigator" value="%{intramuralGrant.pocLastName}" maxLength="30"/>								
						</div>
					</div>

					<div class="row has-feedback">
						<div class="col-xs-6">
							<label for="Email of Principal Investigator">Email of
								Primary Contact</label>
						<s:textfield name="intramuralGrant.pocEmailAddress" cssClass="form-control" id="PCemail" placeholder="Enter Vaild Email Address" data-error="Email address is invalid" value="%{intramuralGrant.pocEmailAddress}" maxLength="80"/>								
						</div>
						<div class="help-block with-errors" style="margin-left: 15px"></div>
					</div>
					
					<div id="pdName" class="row pdirector ">
						<div class="col-xs-5 has-feedback">
							<label for="First Name of Program Director"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp; </i>First
								Name of Program Director</label>
								<s:textfield name="intramuralGrant.pdFirstName" cssClass="form-control unlink-group" id="fnPD" placeholder="" value="%{intramuralGrant.pdFirstName}"  maxLength="30"/>								
						</div>
						<div class="col-xs-5 has-feedback">
							<label for="Last Name of Program Director"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp; </i>Last
								Name of Program Director</label>
								<s:textfield name="intramuralGrant.pdLastName" cssClass="form-control unlink-group" id="lnPD" placeholder="" value="%{intramuralGrant.pdLastName}"  maxLength="30"/>								
						</div>
					</div>
			

				

				</div> <!--end panel body-->
				
				</div> <!--end panel -->


				<div class="form-group row">
					<div class="col-xs-12">
										
							<label for="Additional_Grants"><i
								class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Are there any additional Grants or Contracts associated with the submission?</label>
								 <s:radio  name="grantsAdditional" class="grants" list="#{'Y':'Yes','N':'No'}" template="radiomap-div.ftl"/>

					         <div id="addGrant" style="display: none" class="col-xs-5"> 
					            <s:if test="%{associatedSecondaryGrants.size > 0}">
										<s:iterator value="%{associatedSecondaryGrants}" var="otherGrants" status="stat">
								<s:div class="input-group otherWrapper1">
								  <s:textfield name="associatedSecondaryGrants.grantContractNum" id="grants_%{#stat.index}_grantsContractNum" maxlength="271" class="form-control other" cssclass="form-control"  placeholder="Click on Search Icon" value="%{#otherGrants.grantContractNum}"/>
								
								  <div class="input-group-btn" style="vertical-align: top;">
                                    <s:a href="#" id="grants_%{#stat.index}_div" onclick="openGrantsContractsSearchPage('all','grants_%{#stat.index}')">
									<button class="btn btn-default" type="button"  title="Edit" style=" margin-left: -2px;">
									<i class="fa fa-pencil" aria-hidden="true"></i>
																		</button></s:a> 
								</div>
								<s:if test="%{associatedSecondaryGrants.size > 1}">
													<i class="fa fa-trash fa-lg delete removeclass" title="Delete" aria-hidden="true" alt="Delete" style="font-size: 18px; padding-right: 3px; margin-left: 10px; vertical-align: 75%; cursor:pointer"></i>
												</s:if>
								</s:div>
										</s:iterator>
									</s:if>					  
					             <s:else>
									<!--Repo hidden field-->
								<s:div class="input-group otherWrapper1">
								  <s:textfield name="associatedSecondaryGrants.grantContractNum" id="grants_0_grantsContractNum" maxlength="271" class="form-control other" cssclass="form-control"  placeholder="Click on Edit Icon"/>
								  <div class="input-group-btn" style="vertical-align: top;">
                                    <s:a href="#" id="grants_0_div" onclick="openGrantsContractsSearchPage('all','grants_0')">
																		<button class="btn btn-default" type="button"  title="Edit" style=" margin-left: -2px;">
																			<i class="fa fa-pencil"  aria-hidden="true"></i>
																		</button></s:a> 
								</div>
								</s:div>
								</s:else>
									<div id="anotherButtons" style="margin-left: 75px; margin-top: 15px;">
										<input id="grantButton" type="button" class="btn btn-default" value="Add Another Grant" />
									</div>
								</div>
								
								</div>
								<div class="col-xs-5" style="margin-left:-15px">
							<label>&nbsp;</label>
							  <div class="position: relative; display: table; border-collapse: separate;">
						</div>
						</div>
						</div>
						

					<div class="form-group row  col-xs-12" style="padding-left: 30px;">

						<label for="general info comments" class="label_stCom">Comments (2000 Characters):</label><br />
						<s:textarea cssClass="col-md-12 form-control input " rows="3" maxlength="2000"
							name="project.comments" id="gComments"></s:textarea>
					</div>
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
<%@ taglib uri="/struts-tags" prefix="s"%>

<s:form id="basic-study-form" cssClass="dirty-check"
	action="saveBasicStudyInfo" namespace="manage" method="post"
	enctype="multipart/form-data" data-toggle="validator" role="form">
	<s:hidden name="projectId" value="%{project.id}" />
	<!-- Page navbar -->
	<div class="pageNav">
		<s:submit action="saveBasicStudyInfo" value=" Save "
			class="saved btn btn-default" />
		<s:submit type="button" action="saveBasicStudyInfoAndNext"
			class="btn btn-project-primary">
			Save &amp; Next &nbsp;&nbsp;<i class="fa fa-caret-right" style="color:#ffffff;"></i></s:submit>
	</div>


	<!-- Begin Panel -->
	<div class="col-md-12">
		<div class="panel  project-panel-primary">
			<div class="panel-heading">
				<div class="pheader">
					<h4>Basic Study Information</h4>
					 <s:include value="/jsp/content/pageStatus.jsp"/>
				</div>
				<!-- <div class="statusWrapper">
					<div class="status">
						<a href="#" class="statusLink">Generate Missing Data Report</a>
						&nbsp; &nbsp;
					</div>
					<div class="statusIcon">
						<a href="#" class="tooltip"> <img src="images/inprogress.png"
							alt="In Progress" /> <span> <img class="callout"
								src="images/callout_black.gif" /> <strong>Legend:</strong><br />
								<img src="images/legend.gif" />

						</span>
						</a>
					</div>
				</div> -->
			</div>

			<div class="panel-body">

				<div style="float: right;" class="question">
					<a
						href="http://www.cancer.gov/grants-training/grants-management/nci-policies/genomic-data/submission/basic-study-information.pdf"
						target="_blank">NCI Basic Study Information Form&nbsp;<i
						class="fa fa-external-link" aria-hidden="true"></i></a>
				</div>
				<p style="font-size: 12px; margin-top: 5px;">
					Note: <i class="fa fa-asterisk" aria-hidden="true"></i>&nbsp;Asterisk
					indicates a required field
				</p>
				<p>&nbsp;</p>

				<div class="qSpacing">

					<p class="question" style="display: inline;">
						<i class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Has the GPA
						reviewed the Basic Study Information?&nbsp; &nbsp; &nbsp;</p>

					<s:radio id="reviewed" list="#{'Y':'Yes','N':'No'}"
						name="bsiReviewedFlag" value="bsiReviewedFlag"
						template="radiomap-div.ftl" />

					<p>&nbsp;</p>


					<!--  File Upload -->
					<p class="question">
						<i class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>Upload File(s):
					</p>
					<!--BEGIN Uploader-->
					<div class="qSpacing" id="bsiDiv">
						<div class="col-lg-6">
							<div class="input-group">
								<input type="text" class="form-control"
									placeholder="Choose File to Upload" readonly><label
									class="input-group-btn"> <span class="btn btn-default">
										Choose File <s:file style="display: none;"
											name="bsi" id="bsi" />
								</span>
								</label>
							</div>
						</div>
						<button type="button" name="bsiUpload"
							class="saved btn btn-primary has-spinner upload"
							id="bsiUpload"><i class="fa fa-spinner fa-spin"></i> Upload File</button>				
						
						<div class="loadFileHistory">
							<s:include value="/jsp/content/submissionBasicStudyInfoFile.jsp" />
						</div>
					</div>

					<div>
						<p class="question">Comments (2000 Characters):</p>
						<s:textarea class="col-md-10" rows="3" maxlength="2000" name="comments"></s:textarea>
					</div>
				</div>
			</div>
			<!--  panel body -->
		</div>
		<!--  Panel -->
	</div>

	<!--SAVE & NEXT BUTTONS-->
	<div class="pageNav">
		<s:submit action="saveBasicStudyInfo" value=" Save "
			class="saved btn btn-default" />
		<s:submit type="button" action="saveBasicStudyInfoAndNext"
			class="btn btn-project-primary" >
			Save &amp; Next &nbsp;&nbsp;<i class="fa fa-caret-right" style="color:#ffffff;"></i></s:submit>
	</div>
</s:form>

<!-- Modal -->
<div id="fileModal" class="modal fade bs-example-modal-sm" tabindex="-1"
	role="dialog" aria-labelledby="fileModalLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3 id="fileModalLabel">File Upload Message</h3>
			</div>
			<div id="fileModalId" class="modal-body"></div>
			<div class="modal-footer">
				<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript"
	src="<s:url value="/controllers/basicStudy.js" />"></script>
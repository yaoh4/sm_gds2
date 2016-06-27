<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<div class="content">
	
	<div class="inside">
		<fieldset>
			<b>Add Institutional Certification</b>
		</fieldset>
	</div>
	
	<form>
	
		<s:hidden name="projectId" value="%{#request.projectId}" />		

		<p class="privacy" style="margin-left: 10px; font-size: 10px;">
				Note: <em>*</em> Asterisk indicates a required field
		</p>

		<fieldset class="lbl_block" style="display: block; clear: both;">
			<legend>
				<em>*</em>Add Institutional Certification
			</legend>

			<div id="parent">
				<div>
					<!--BEGIN MAIN TABLE -->
					<table width="910px" cellpadding="0px" class="outsideT">
						<tr>
							<td colspan="2">
								<table width="100%" cellspacing="5">
									
									<!--  File Upload --> 
									<tr>
										<td colspan="2" align="left" valign="bottom"
												class="subLabel"><em>*</em>File Upload <span
												class="normal">(2MB maximum file size, Word or PDF
													only)</span></td>
										<td colspan="4" align="left" valign="bottom"
												class="subLabel">&nbsp;</td>

									</tr>
									<tr height="50px" valign="middle">
										<td colspan="6" align="left">
											<div style="display: inline" id="upLoad">

												<input type="file" id="myFile" class="upload"
													onchange="myUpload()"> 
												<label id="myFile-label"
														for="myFile">Upload File
												</label>
												<div id="demo" class="demo" style="display: inline">
												</div>
											</div>
										</td>
									</tr>

									<!--  IC statuses -->
									<tr>
										<td valign="bottom" width="177" class="subLabel">Approved
															by GPA</td>
										<td valign="bottom" width="190" class="subLabel">Provisional
															or Final?</td>
										<td colspan="2" valign="bottom" class="subLabel">Study
															for use in Future Projects?</td>
										<td valign="bottom" width="132" class="subLabel">&nbsp;</td>
									</tr>

									<tr>
										<td>
											<s:select name="instCertification.gpaApprovalCode"
                        						value="instCertification.gpaApprovalCode"
                        							class="mn" style="width: 120px;"
                        							list="%{@gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper@getLookupDropDownList(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@IC_APPROVED_BY_GPA_LIST)}"
                        							listKey="optionKey" listValue="optionValue" id="GPA"
                       								emptyOption="true"/>													
														
													<!-- 	<select name="submissionStatus3" id="GPA"
															class="mn" style="width: 120px;">
																<option value="0" selected></option>
																<option value="1">Yes</option>
																<option value="">No</option>
																<option value="">Partially</option>
																<option value="">Not Applicable</option></select> -->
														</td>
														<td>
											<s:select name="instCertification.provisionalFinalCode"
                        							value="instCertification.provisionalFinalCode"
                        							class="mn" style="width: 120px;"
                        							list="%{@gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper@getLookupDropDownList(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@IC_PROV_OR_FINAL_LIST)}"
                        							listKey="optionKey" listValue="optionValue" id="finalprov"
                       								emptyOption="true"/>	
                       										
														<!-- <select name="submissionStatus4" id="finalProv"
															class="mn" style="width: 120px;" onChange="prov()">
																<option value="0" selected></option>
																<option value="provisional" id="provisional">Provisional</option>
																<option value="final">Final</option></select> -->
														</td>
														
														<td colspan="2">
											<s:select name="instCertification.futureProjectUseFlag"
                        							value="instCertification.futureProjectUseFlag"
                        							class="mn" style="width: 120px;"
                        							list="%{@gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper@getLookupDropDownList(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@IC_FOR_FUTURE_USE_LIST)}"
                        							listKey="optionKey" listValue="optionValue" id="Study"
                       								emptyOption="true"/>	
                       										
													<!-- 	<select name="submissionStatus"
															id="Study" class="mn" style="width: 120px;">
																<option value="0" selected></option>
																<option value="1">Yes</option>
																<option value="">No</option></select> -->
																
										</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td colspan="3" align="left" valign="bottom">&nbsp;</td>
										<td colspan="3" align="left" valign="bottom">&nbsp;</td>
									</tr>

									<!-- IC Comments -->
									<tr>
										<td colspan="3" align="left" valign="top"><span
												class="subLabel">Comments</span></td>
										<td colspan="3">&nbsp;</td>
									</tr>
									<tr>
										<td colspan="6" align="left" valign="top"><textarea
												name="comments" id="comments2" style="width: 100%"
											rows="6"> </textarea></td>
									</tr>
									
									<tr>
										<td colspan="6">&nbsp;</td>
									</tr>
									
									<tr>
										<td colspan="6">
											<!--Begin STUDY TABLE-->
															
											<s:iterator status="studiesStat" var="study" value="instCertification.studies">
												<s:set name="studiesIdx" value="#studiesStat.index" />
												<div id="studyTable">
													<div id="container">
														<table width="100%" class="study">
															<tr>
																<td>
																	<table width="100%" cellspacing="5">
																		<tr>
																			<td colspan="6" align="right">
																				<p>
																					<a href="#">[X] Remove Study</a>
																				</p>
																				<tr>
																					<td class="subLabel">Study Name</td>
																					<td colspan="1" align="left" valign="top">&nbsp;</td>
																					<td colspan="3" class="subLabel">Institution</td>
																					<td>
																						<div id="verified">
																							<span class="subLabel">Data Use
																								Limitation(s) Verified?
																							</span>
																						</div>
																					</td>
																				</tr>
              																	<tr>										
																					<td colspan="1" align="left" valign="top"><input
																							type="text" style="width: 90%" align="left"
																							id="studyName%{#studiesStat.index}"
																							name="instCertification.studies[<s:property value='#studiesStat.index'/>].studyName"
																							value="${study.studyName}">																							
																					</td>
																					<td></td>
																					<td colspan="3" align="left" valign="top"><input
																							type="text" style="width: 90%" align="left"
																							id="institution%{#studiesStat.index}"
																							name="instCertification.studies[<s:property value='#studiesStat.index'/>].institution"
																							value="${study.institution}">
																					</td>
																					<td colspan="2">
																						<div id="verifiedList">
																							<s:select name="instCertification.studies[<s:property value='#studiesStat.index'/>].dulVerificationId"
                        																		value="study.dulVerificationId"
                        																		class="mn" style="width: 120px;"
                        																		list="%{@gov.nih.nci.cbiit.scimgmt.gds.util.GdsSubmissionActionHelper@getLookupDropDownList(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@IC_DUL_VERIFIED_LIST)}"
                        																		listKey="optionKey" listValue="optionValue" id="dulVerified"
                       																			emptyOption="true"/>	
                       										
																								<!-- <select name="submissionStatus2"
																									id="Verification" class="mn"
																									style="width: 120px;">
																									<option value="0" selected></option>
																									<option value="1">Yes</option>
																									<option value="">No</option>
																									<option value="">Partially</option>
																									<option value="">Not Applicable</option>
																								</select> -->
																								
																								
																						</div>
																					</td>
																				</tr>
																				<tr>
																					<td colspan="3" align="left" valign="top"><span
																							class="subLabel">Comments</span></td>
																					<td colspan="3">&nbsp;</td>
																				</tr>
																				<tr>
																					<td colspan="6" align="left" valign="top">
																								<s:textarea id="comments%{#studiesStat.index}" style="width: 100%" value="%{#study.comments}"
																								name="instCertification.studies[<s:property value='#studiesStat.index'/>].comments" placeholder="Enter Your Comments" rows="5"></s:textarea>
																								<!--  <textarea name="comments" id="comments2" style="width: 100%" rows="5"> </textarea> -->														
																					</td>
																				</tr>																							
																				<tr>
																					<td colspan="3" align="left" valign="top">&nbsp;</td>
																					<td colspan="3">&nbsp;</td>
																				</tr>
																		<tr>
																					<td colspan="6" align="left" valign="top">
																						<div id="DULs">
																							<p class="subLabel">Data Use Limitation</p>
																							<p>You may add up to 10 DULs</p>
																						</div>

																						<s:include value="/jsp/content/manageDuls.jsp"/>
																						
																					</td>
																		</tr>
																	</table>
																</td>
															</tr>
														</table>

														<p>&nbsp;</p>
													</div>
												</div> 
											</s:iterator>
															
											<input type="button" id="cloneStudy"
												value="Add Another Study" onClick="addTable()" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>

				</div>
			</div>

		</fieldset>
					<br />
					<div style="display: block; clear: both;">
						<s:submit id="prev_button" value="Save Institutional Certification" action="saveIc" />
						<!-- 	<input id="prev_button" name="button"
								value=" Save Institutional Certification "
								style="font-weight: bold; padding: 3px;" type="button"
								onclick="location.href='3Submission_IC_page3dashboard.htm'"> -->

					</div>
					<br />
	</form>
	
	
</div>

<br /><br /><br /><br />
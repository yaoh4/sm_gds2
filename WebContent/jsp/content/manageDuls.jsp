<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<div id="dulSections">
																							 
	<s:iterator status="dulSetStat" var="studiesDulSet" value="instCertification.studies[#studiesStat.index].studiesDulSets">
		<s:set name="dulSetIdx" value="#dulSetStat.index" />
		<s:hidden name="instCertification.studies[%{#studiesStat.index}].studiesDulSets[%{#dulSetStat.index}].id"/>
		<div class="dulSection">
			<fieldset class="DULfield">
				<legend class="DUL">Data Use Limitation Type </legend>
				<table width="100%" cellspacing="0" valign="top">
					<tr>
						<td>																																	
							<ul class="checkbox" style="padding-left: 10px; padding-top: 7px;">	
							
						  		<s:iterator status="parentDulStat" var="parentDul" value="parentDulChecklists">
									<s:set name="parentIdx" value="#parentDulStat.index"/>
									<li>
										<!-- <input type="radio" name="parentDul" id="parentDul%{#parentDul.id}" value="${parentDul.id}" checked>
											&nbsp;&nbsp;${parentDul.displayText} -->
										<input type="radio" name="parentDul-<s:property value='#studiesStat.index'/>-<s:property value='#dulSetStat.index'/>" 
											id="parentDul%{#parentDul.id}" value="${parentDul.id}" checked>
											&nbsp;&nbsp;${parentDul.displayText}
										 </input>
									</li>
								
									<div id="General" class="indent" style="display: block;">
										<p>(select any that apply)</p>																																											
										<ul class="indent">
											<s:iterator status="dulStat" var="dul" value="parentDulChecklists[#parentIdx].dulChecklists">
											<li>
												<input type="checkbox" 
													name="dul-<s:property value='#studiesStat.index'/>-<s:property value='#dulSetStat.index'/>-<s:property value='#parentDul.id'/>" 
													id="dul%{#dul.parentId}-%{#dul.id}" value="${dul.id}">
													&nbsp;&nbsp;${dul.displayText}
												</input>
											</li>
											</s:iterator>	
										</ul>
									</div>			
						 		</s:iterator>																							
		
							</ul> <!-- checkbox class -->																																																						
						</td>
						<td valign="top">
							<div align="right">
								<a href="#" class='removeDul'>[X] Remove DUL</a>
							</div>
						</td>
					</tr>
				</table>

			</fieldset>
		</div> <!-- section -->
	</s:iterator>
																							 
</div>
<div id="add1">
	<p>
		<a href="#" class='addsection' style="margin-left: 8px;">[+] Add DUL</a>
	</p>
</div>

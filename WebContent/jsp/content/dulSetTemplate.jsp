<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

																					   
	<div id="dulTypeTemplate0-0" class="dulTypesTemplate" style="display:none;">
		  
	  <s:hidden name="instCertification.studies[0].studiesDulSets[0].displayId" id="dulSetDisplayId0-0"/>
	  <s:hidden name="instCertification.studies[0].studiesDulSets[0].createdBy" id="dulSetCreatedBy0-0" value=""/>
	  <s:hidden name="instCertification.studies[0].studiesDulSets[0].id" id="dulSetId0-0" value=""/>
		
	  <div class="panel-group">
        <div class="panel panel-default">
		
		  <!-- DUL PANEL HEADER -->
		  <div class="panel-heading">
            <h5 class="panel-title heading-reference" id="entry_dulSet_0-0" name="reference">
              <a href="#" onclick="deleteDulSet(0,0)" class="deleteIcon" style="float: right;">
              <i class="fa fa-trash fa-lg" aria-hidden="true" alt="delete" title="delete"></i></a>            
              Data Use Limitation Type
            </h5>
          </div>
                                      
		
		  <!--  DUL PANEL BODY -->
		  <div>
            <div class="panel-body">
              <div class="entrylist">
    
                <s:iterator status="parentDulStat" var="parentDul" value="parentDulChecklists">
				  <s:set name="parentIdx" value="#parentDulStat.index"/>
                
                  <div class="radio">
				    <label>
                      <input type="radio" class="parentDulSet" onClick="displayDuls(this)"
                      		name="parentDul-0-0" 
							id="parentDul0-0-${parentDul.id}" value="${parentDul.id}">
                    	&nbsp;&nbsp;${parentDul.displayText}    
                    </label> &nbsp; &nbsp; <a href="#" class="pop" data-container="body" data-toggle="popover" data-placement="right" data-html="true" style="font-size: 12px;">
                         <s:hidden id="%{#parentDul.id}" value="%{getHelpText(#parentDul.id)}"/>  <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a> 	  
                  </div>				
				  <div id="dulSet0-0-${parentDul.id}" class="dulSetDiv indent info" style="display:none;">
				  
				    <s:if test="%{#parentDul.id == 13}"> 
                      <div>
                      	<input type="text" class="form-control input_other" 
                      	  id="otherAddText0-0-${parentDul.id}"
                      	  name="otherAddText-0-0-${parentDul.id}" 
                      	  placeholder="Please List Specific Disease" size="100" maxlength="200" onkeyup="countChar(this)" >
                        <div id="textlength0-0-${parentDul.id}" style="text-align: right; font-style: italic;">
				           <span style="color: #990000;">2000</span> Character limits
			            </div>
			                </div>                
                    </s:if>
                    <s:if test="%{#parentDul.id == 21}">
                      <div>
                      	<input type="text" class="form-control input_other" 
                      	  id="otherAddText0-0-${parentDul.id}"
                      	  name="otherAddText-0-0-${parentDul.id}" 
                      	  placeholder="Please Be Specific" maxlength="2000" onkeyup="countChar(this)">
                           <div id="textlength0-0-${parentDul.id}" style="text-align: right; font-style: italic;">
				           <span style="color: #990000;">2000</span> Character limits
			               </div>
			          </div>
                    </s:if>
                       <s:if test="%{#parentDul.id != null && #parentDul.id != 21 }">
                      <div>
                      <label for="dulAppendix">DUL Appendix</label> 
                      	<textarea class="form-control input_other" id="comments0-0-${parentDul.id}"
                      	  name="comments-0-0-${parentDul.id}"  maxlength="2000"
                      	  rows="2" onkeyup="countChar(this)"></textarea>
                           <div id="charlength0-0-${parentDul.id}" style="text-align: right; font-style: italic;">
				           <span style="color: #990000;">2000</span> Character limits
			                </div>
			           </div>
                    </s:if>
                    <s:if test="%{parentDulChecklists[#parentIdx].dulChecklists.size > 0}">
                       <label for="checkboxitem">(select any that apply)</label>                 
                    </s:if>
                    <div class="indent">
					  <s:iterator status="dulStat" var="dul" value="parentDulChecklists[#parentIdx].dulChecklists">
					
					    <div class="checkbox">
                          <label class="checkboxitem-0" for="entry1_dul1_generalAdd_checkboxitem-0">
		                    <input class="dulSet" type="checkbox" 
	                        	name="dul-0-0-<s:property value='#parentDul.id'/>" 
					  			id="dul0-0-${dul.id}" value="${dul.id}">
					  			&nbsp;&nbsp;${dul.displayText} 
					  	  </label>
					  	  
					  	  
						</div>			
						
					  </s:iterator>			
					</div>	
						
				  </div>					
				</s:iterator>																							
	
              </div> <!-- DUL Entry list -->
            </div>  <!--  DUL Panel body -->
		  </div>
		</div>  <!--  End Panel -->
	  </div>	<!--  END Panel group -->
	</div>   <!-- End dulType -->


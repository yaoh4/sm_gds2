<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<s:hidden id="dulIds" name="dulIds"/>

<div class="form-group row col-xs-12" id="DULpanel">
  
	<div class="cloneDULInput" id="cloneDULInput-${studiesIdx}">		
																					 
        <s:iterator status="dulSetStat" var="studiesDulSet" value="instCertification.studies[#studiesStat.index].studiesDulSets">
		  <s:set name="dulSetIdx" value="#dulSetStat.index" />
		   
		  <div id="dulType${studiesIdx}-${dulSetIdx}" class="dulTypes">
		  
		   <s:hidden name="instCertification.studies[%{#studiesStat.index}].studiesDulSets[%{#dulSetStat.index}].displayId" id="dulSetDisplayId%{#studiesIdx}-%{#dulSetIdx}" value="%{#dulSetIdx}"/>
		   
		   <s:hidden name="instCertification.studies[%{#studiesStat.index}].studiesDulSets[%{#dulSetStat.index}].createdBy" id="dulSetCreatedBy%{#studiesIdx}-%{#dulSetIdx}"/>
		   <s:hidden name="instCertification.studies[%{#studiesStat.index}].studiesDulSets[%{#dulSetStat.index}].id" id="dulSetId%{#studiesIdx}-%{#dulSetIdx}"/>
		
		<div class="panel-group">
         <div class="panel panel-default">
		
		  <!-- DUL PANEL HEADER -->
		  <div class="panel-heading">
            <h5 class="panel-title heading-reference">
              <a href="#" onclick="deleteDulSet(${studiesIdx},${dulSetIdx})" class="deleteIcon" style="float: right;">
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
                      		name="parentDul-<s:property value='#studiesStat.index'/>-<s:property value='#dulSetStat.index'/>" 
							id="parentDul${studiesIdx}-${dulSetIdx}-${parentDul.id}" value="${parentDul.id}">
                    	&nbsp;&nbsp;${parentDul.displayText}    
                    </label> &nbsp; &nbsp; <a href="#" class="pop" data-container="body" data-toggle="popover" data-placement="right" data-html="true" style="font-size: 12px;">
                         <s:hidden id="%{#parentDul.id}" value="%{getHelpText(#parentDul.id)}"/>  <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a>  	  
                  </div>
				  <div id="dulSet${studiesIdx}-${dulSetIdx}-${parentDul.id}" class="dulSetDiv indent info" style="display:none;">
				  
				    <s:if test="%{#parentDul.id == 13}"> 
                      <div>
                      	<input type="text" class="form-control input_other" size="100" maxlength="100"
                      	  id="otherAddText${studiesIdx}-${dulSetIdx}-${parentDul.id}"
                      	  name="otherAddText-${studiesIdx}-${dulSetIdx}-${parentDul.id}" 
                      	  placeholder="Please List Specific Disease"> 
			            </div>                 
                    </s:if>
                    <s:if test="%{#parentDul.id == 21}">
                      <div>
                      	<textarea class="form-control input_other"  maxlength="2000"
                      	  rows="3" id="otherAddText${studiesIdx}-${dulSetIdx}-${parentDul.id}"
                      	  name="otherAddText-${studiesIdx}-${dulSetIdx}-${parentDul.id}" 
                      	  placeholder="Please Be Specific" onkeyup="countChar(this)"></textarea>
                            <div id="textlength${studiesIdx}-${dulSetIdx}-${parentDul.id}" style="text-align: right; font-style: italic;">
				           <span>2000</span> Character limits
			                </div>
			           </div>
                    </s:if>
                    <s:if test="%{#parentDul.id != null && #parentDul.id != 21 }">
                      <div>
                      <label for="dulAppendix">DUL Appendix</label> 
                      	<textarea class="form-control input_other commentsClass" id="comments${studiesIdx}-${dulSetIdx}-${parentDul.id}"
                      	  name="comments-${studiesIdx}-${dulSetIdx}-${parentDul.id}" maxlength="2000"
                      	 style="overflow-y: scroll;" rows="2" placeholder="For DUL language in addition to the standard language (e.g. details for collaborative agreement, further restrictions not covered by standard language)" onkeyup="countChar(this)"></textarea>
                         <div id="charlength${studiesIdx}-${dulSetIdx}-${parentDul.id}" style="text-align: right; font-style: italic;">
				           <span>2000</span> Character limits
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
	                        	name="dul-<s:property value='#studiesStat.index'/>-<s:property 
	                  			value='#dulSetStat.index'/>-<s:property value='#parentDul.id'/>" 
					  			id="dul${studiesIdx}-${dulSetIdx}-${dul.id}" value="${dul.id}">
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
	    </s:iterator>
</div> <!-- cloned input-->
 		   
 		   
  <div>
    <input type="button"  id="btnAddDUL-${studiesIdx}"  onclick="addDulSet(this)" class="addDulSetButton btn btn-default" value="Add Another DUL">
  </div> 																	 
  
</div> <!-- END DULPanel -->


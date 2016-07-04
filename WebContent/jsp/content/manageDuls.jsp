<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<s:hidden id="dulIds" name="dulIds"/>

<div class="form-group row col-xs-12" id="DULpanel">
<div id="entry1_dul1" class="DULclonedInput">
  
		<div class="cloneDULInput" id="cloneDULInput${studiesIdx}">		
																					 
        <s:iterator status="dulSetStat" var="studiesDulSet" value="instCertification.studies[#studiesStat.index].studiesDulSets">
		  <s:set name="dulSetIdx" value="#dulSetStat.index" />
		  
		  <s:hidden name="instCertification.studies[%{#studiesStat.index}].studiesDulSets[%{#dulSetStat.index}].createdBy"/>
		  
		  <div id="dulType${studiesIdx}-${dulSetIdx}">
		   
		   <s:hidden name="instCertification.studies[%{#studiesStat.index}].studiesDulSets[%{#dulSetStat.index}].id" id="dulSetId%{studiesIdx}-%{dulSetIdx}"/>
		
		<div class="panel-group">
         <div class="panel panel-default">
		
		  <!-- DUL PANEL HEADER -->
		  <div class="panel-heading">
            <h5 class="panel-title heading-reference" id="entry_dulSet_${studiesIdx}_${dulSetIdx}" name="reference">
             <s:if test="%{#dulSetIdx > 0}">
             	<a href="#" onclick="deleteDulSet(${studiesIdx}, ${dulSetIdx})" class="deleteIcon" style="float: right;">
                <i class="fa fa-trash" aria-hidden="true"></i></a>
              </s:if>   
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
                      <input type="radio" class="parentDulSet" 
                      		name="parentDul-<s:property value='#studiesStat.index'/>-<s:property value='#dulSetStat.index'/>" 
							id="parentDul${studiesIdx}-${dulSetIdx}-${parentDul.id}" value="${parentDul.id}">
                    	&nbsp;&nbsp;${parentDul.displayText}    
                    </label>  	  
                  </div>				
				  <div id="dulSet${studiesIdx}-${dulSetIdx}-${parentDul.id}" class="dulSetDiv indent info" style="display:none;">
				  
				    <s:if test="%{#parentDul.id == 13}"> 
                      <span>
                      	<input type="text" class="form-control input_other" 
                      	  id="otherAddText${studiesIdx}-${dulSetIdx}-${parentDul.id}"
                      	  name="otherAddText-${studiesIdx}-${dulSetIdx}-${parentDul.id}" 
                      	  placeholder="Please List Specific Disease" >
                      </span>  
                                          
                    </s:if>
                    <s:if test="%{#parentDul.id == 21}">
                      <span>
                      	<input type="text" class="form-control input_other" 
                      	  id="parentAddText${studiesIdx}-${dulSetIdx}-${parentDul.id}"
                      	  name="parentAddText-${studiesIdx}-${dulSetIdx}-${parentDul.id}" 
                      	  placeholder="Please Be Specific" >
                      </span>
                    </s:if>
                    
                    <label for="checkboxitem">(select any that apply)</label>                 
                    
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
 		   
  </div>  <!-- End DULClonedInput -->
  
  <div id="addDulSetButton_${studiesIdx}">
    <input type="button" id="btnAddDUL" class="btn btn-default" value="Add DUL">
  </div> 																	 
  
</div> <!-- END DULPanel -->


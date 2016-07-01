<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<s:hidden id="dulIds" name="dulIds"/>

<div class="form-group row col-xs-12" id="DULpanel">
<div id="entry1_dul1" class="DULclonedInput">
  
		<div class="cloneDULInput" id="cloneDULInput${studiesIdx}">		
																					 
        <s:iterator status="dulSetStat" var="studiesDulSet" value="instCertification.studies[#studiesStat.index].studiesDulSets">
		  <s:set name="dulSetIdx" value="#dulSetStat.index" />
		  <s:hidden name="instCertification.studies[%{#studiesStat.index}].studiesDulSets[%{#dulSetStat.index}].id"/>
		
		<div id="dulType${studiesIdx}-${dulSetIdx}">
		
		<div class="panel-group">
         <div class="panel panel-default">
		
		  <!-- DUL PANEL HEADER -->
		  <div class="panel-heading">
            <h5 class="panel-title heading-reference" id="entry1_ID1reference" name="reference">   
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
				  <div id="dulSet${studiesIdx}-${dulSetIdx}-${parentDul.id}" class="dulSetDiv" style="display:none;">
                    
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
  
  <div id="addDelButtons2">
    <input type="button" id="btnAddDUL" onClick="addDulSet('<s:property value='#studiesIdx'/>')" class="btn btn-default" value="Add DUL">
  </div> 
  
 <!-- <div id="addDelButtons2">
    <input type="button" id="btnAddDUL" class="btn btn-default" value="Add DUL">
  </div> -->																							 
  
</div> <!-- END DULPanel -->


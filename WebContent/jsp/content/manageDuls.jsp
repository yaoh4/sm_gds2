<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<div class="form-group row col-xs-12" id="DULpanel">
  <div id="entry1_dul1" class="DULclonedInput">
    <div class="panel-group">
      <div class="panel panel-default">

	    <s:hidden id="dulIds" name="dulIds"/>
																							 
        <s:iterator status="dulSetStat" var="studiesDulSet" value="instCertification.studies[#studiesStat.index].studiesDulSets">
		  <s:set name="dulSetIdx" value="#dulSetStat.index" />
		  <s:hidden name="instCertification.studies[%{#studiesStat.index}].studiesDulSets[%{#dulSetStat.index}].id"/>
		
		
		  <!-- DUL PANEL HEADER -->
		  <div class="panel-heading">
            <h4 class="panel-title heading-reference" id="entry1_ID1reference" name="reference">   
              DUL #1
            </h4>
          </div>
                                      
		
		  <!--  DUL PANEL BODY -->
		  <div>
            <div class="panel-body">
              <div class="entrylist">
                <label class="label_radio" for="entry1_dul1_radioitem">Data Use Limitation Type</label>
                
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
	    </s:iterator>

      
      </div> <!--  END Panel -->
    </div> <!--  END Panel group -->
  </div> <!--  END DUL Cloned Input -->
  
  <div id="addDelButtons2">
	      <input type="button" id="btnAddDUL" class="btn btn-default" value="Add DUL">
  </div>																							 
  
</div> <!-- END DULPanel -->

<script type="text/javascript"
	src="<s:url value="/controllers/icDetails.js" />"></script>
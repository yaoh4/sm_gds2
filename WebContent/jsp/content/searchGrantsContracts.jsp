<%@ taglib uri="/struts-tags" prefix="s"%>

    
      <div class="pageNav"> <!-- Page navbar -->
        <button type="button" value="Cancel" class="btn btn-default" onclick="cancel()">Cancel</button>
        <button type="button" value="" class="btn btn-project-primary" onclick="populateGrantsContractsData()"> Next &nbsp;&nbsp;<i class="fa fa-caret-right" style="color:#ffffff;"></i></button> 
      </div>
      
      <!-- Begin Panel -->
      <div class="col-md-12">           
        <div class="panel project-panel-primary" id="searchGrant">					
		  
		  <div class="panel-heading">
		    <div class="pheader">
			  <h4>Search for Grant/Intramural/Contract #</h4>
			</div>
		  </div>

		  <!--end panel header-->
		  <div class="panel-body">					
		    <p><strong>Please enter your search criteria using the following format:</strong></p>
			<div>
			  <div class="searchLabel">Extramural Grant:</div>
			  <div class="searchFormat">(enter MECH-ICD-Serial#)</div>
			</div>
			<div>
			  <div class="searchLabel">Intramural Grant:</div>
			  <div class="searchFormat">(enter full Z01 or ZIA#)</div>
			</div>
			<div>
			  <div class="searchLabel" align="right">Contract:</div>
			  <div class="searchFormat">(enter full contract#)</div>
			</div>

			<!--  Search grants -->
			<div class="searchLabel">
              <label for="Intramural (Z01)/Grant/Contract #">Grant/Intramural/Contract #</label>
            </div>
            <div class="searchFormat">
              <input type="text" class="form-control" id="grantSearch" size="30" maxlength="30" placeholder="Use Correct Format from Examples Above"
                    	name="grantContractNum" value="${grantContractNum}">
            </div> 
            <div class="searchFormat" style="float:right;">
              <button type="button" class="btn btn-primary has-spinner" id="searchGrants" onclick="searchGrantsData()"><i class="fa fa-spinner fa-spin"></i> Search</button> 
              <button type="button" class="btn btn-default" id="reset" onclick="resetData()">Reset</button> 
              <p>&nbsp;</p>
            </div>
							
			<!--Begin Search Results-->
            <div id="searchResults">
              <table style="width: 100%;" cellpadding="0px" cellspacing="0"
                    class="table table-bordered table-striped" style="margin-left: 10px;">
                <tr class="modalTheader">
                  <th class="tableHeader" align="center" style="width:5%" scope="col">Select</th>
                  <th class="tableHeader" style="width:25%" scope="col">Grant/Intramural/Contract #</th>
                  <th class="tableHeader" style="width:50%" scope="col">Grant/Intramural/Contract Project Title</th>
                  <th class="tableHeader" style="width:20%" scope="col">Principal Investigator</th>
                </tr>
				
					
				<s:if test="%{grantOrContractList.size > 0}">
				  <s:iterator value="grantOrContractList" var="grantsContracts" status="stat">
				    <s:if test="#stat.index /2 == 0">
					  <tr class="tableContent">
					</s:if>
					<s:else>
					  <tr class="tableContentOdd">
					</s:else>
					    <td align="center" style="width:5%"><input name="selectedGrantContract" onclick="showPrevLinkedSubmissions()" type="radio" value='${grantsContracts}'/></td>
				  		<td class="paddingT" style="width:25%" nowrap><s:property value="%{#grantsContracts.grantContractNum}" /></td>
						<td class="paddingT" style="width:50%"><s:property value="%{#grantsContracts.projectTitle}" /></td>
						<td class="paddingT" style="width:20%">
						  <s:a href="mailto:%{#grantsContracts.piEmailAddress}?">
						    <s:property	value="%{#grantsContracts.piLastName}" /> , <s:property	value="%{#grantsContracts.piFirstName}" />
						  </s:a>		
						</td>
					  </tr>
				  </s:iterator>
				</s:if>
				<s:else>
				  <tr class="tableContent">
				    <td colspan="4">Nothing found to display.</td>
				  </tr>
				</s:else>
			  </table>
			  <div id="prevLinkedSubmissions" tabindex="-1" style="display: none;"></div>
			</div>	<!--end search results-->
		  </div> <!--end panel body-->
		</div> <!--end panel-->
		
		<div class="pageNav"><!-- Page navbar -->
          <button type="button" value="Cancel" class="btn btn-default" onclick="cancel()">Cancel</button>
          <button type="button" value="" class="btn btn-project-primary" onclick="populateGrantsContractsData()"> Next &nbsp;&nbsp;<i class="fa fa-caret-right" style="color:#ffffff;"></i></button> 
        </div>
           
     </div> <!--  end Panel  -->
	
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<style>
h3 {
  font-size: 2rem;
  font-weight: 700; 
  display: inline-block;
  margin-top: 0px;
}
table.dataTable td {
    font-size: 14px;
}
.searchHelp {
  font-size: 10px;
  
}
.table>caption+thead>tr:first-child>td, .table>caption+thead>tr:first-child>th, .table>colgroup+thead>tr:first-child>td, .table>colgroup+thead>tr:first-child>th, .table>thead:first-child>tr:first-child>td, .table>thead:first-child>tr:first-child>th {
    border-top: 0;
    padding-top: 10px;
    padding-bottom: 10px;
    padding-left: 5px;
    padding-right: 5px;
    vertical-align: middle;
}
table.dataTable tbody tr td {
    border-top: 1px solid #DDD;
}
table.dataTable tbody th, table.dataTable tbody td {
    padding: 8px 10px;
}
</style>

<s:if test="newIC">
<script>
$(function() {
	initializeStudyTable();
	$("#select_study_form").parent().before('<div id="messages" class="container"><div class="container">'+
		'<div class="col-md-12">'+
			'<div class="alert alert-danger">'+
			 	'<h3><i class="fa fa-exclamation-triangle" aria-hidden="true"></i>&nbsp;Error Status</h3>'+
					'<ul class="errorMessage">'+
            '<li><span>Select a Study</span></li></ul>'+
			'</div>'+
		'</div>'+
	'</div>'+
	'</div>');
});
</script>
</s:if>
<!--Begin Form -->
<s:hidden id="studyIndex"/>
<s:hidden id="selectType"/>
<s:form action="editIc" namespace="/manage" id="select_study_form" >
  <s:hidden name="projectId" value="%{project.id}"/>
  <!--Cancel & Next Buttons-->
  <div class="pageNav">
    <s:if test="newIC">
    	<s:submit action="navigateToIcMain" value=" Cancel " class="saved btn btn-default"/>
    </s:if>
    <s:else>
    	<input onclick="cancelStudy()" value=" Cancel " class="saved btn btn-default" type="button">
    </s:else>
    <s:if test="!studiesForSelection.isEmpty">
      <s:if test="newIC">
    	<s:submit value=" Next " class="saved btn btn-project-primary"/>  
      </s:if>
      <s:else>
    	<input onclick="selectStudy()" value=" Next " class="saved btn btn-project-primary" type="button">
      </s:else>
    </s:if>
  </div>
  
  <!-- Begin Panel -->
  <div class="col-md-12">
    <div class="panel  project-panel-primary">
          
      <div class="panel-heading">
        <div class="pheader"><h4>Select from Existing Studies Awaiting Institutional Certification</h4></div>  
      </div><!--end header-->
      
      <div class="panel-body">
       <s:if test="studiesForSelection.isEmpty">
      	<div class="alert alert-warning" role="alert" style="margin-left: 15px;">
        <p><strong>Please Note:</strong> No studies have been entered that can be associated with this Institutional Certification.  Studies must be entered first and then associated with this Institutioanl Certifcation. Select the "Save and Add Study" button to begin adding studies.  After studies have been entered return to this Institutional Certification and associate the studies with it.</p>
        </div>
       </s:if>
      
       <s:else>
        <div class="well">
           <h3>Selected Studies</h3>
           <div id="message">Select Studies from table below.</div>
           <div id="log"></div>
        </div>
        
        <table id="studySelectTable" class="table table-striped table-bordered" cellspacing="3" width="100%" style="font-size: 16px;">
        <thead>
            <tr>
              <th class="no-sort sorting_disabled" style="width: 55px; text-align: center;" id="no-sorting">Select</th>
                <th>Study Name</th>
                <th>Institutions</th>
                
            </tr>
        </thead>
        
        <tbody>
          <s:iterator value="studiesForSelection" var="s" status="stat">
        	<tr>
              <td style="text-align: center;">
              	<div class="checkboxSelect" style="display:block">
              		<input class="checkboxSelected" type="checkbox" name="studyIds" value="<s:property value="#s.id" />">
              	</div>
              	<div class="radioSelect" style="display:none">
              		<input class="radioSelected" type="radio" name="studyIds" value="<s:property value="#s.id" />">
              	</div>
              </td>
              <td><s:property value="#s.studyName" /></td>
              <td><s:property value="#s.institution" /></td>   
            </tr>
          </s:iterator>
        </tbody>
        </table>
       </s:else>
      </div><!--end panel body-->
    </div>
  </div>
  
  <!--Cancel & Next Buttons-->
  <div class="pageNav">
    <s:if test="newIC">
    	<s:submit action="navigateToIcMain" value=" Cancel " class="saved btn btn-default"/>
    </s:if>
    <s:else>
    	<input onclick="cancelStudy()" value=" Cancel " class="saved btn btn-default" type="button">
    </s:else>
    <s:if test="!studiesForSelection.isEmpty">
      <s:if test="newIC">
    	<s:submit value=" Next " class="saved btn btn-project-primary"/>  
      </s:if>
      <s:else>
    	<input onclick="selectStudy()" value=" Next " class="saved btn btn-project-primary" type="button">
      </s:else>
    </s:if>
  </div>
</s:form>

<script type="text/javascript" src="<s:url value="/controllers/institutional.js" />"></script>
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

<!--Begin Form -->
<s:form action="editIc" namespace="/manage" id="select_study_form" cssClass="dirty-check" >
  <s:hidden name="projectId" value="%{project.id}"/>
  <!--Cancel & Next Buttons-->
  <div class="pageNav">
    <s:submit action="navigateToIcMain" value=" Cancel " class="saved btn btn-default"/>
    <s:submit value=" Next " class="saved btn btn-project-primary"/>  
  </div>
  
  <!-- Begin Panel -->
  <div class="col-md-12">
    <div class="panel  project-panel-primary">
          
      <div class="panel-heading">
        <div class="pheader"><h4>Select from Existing Studies Awaiting Institutional Certification</h4></div>  
      </div><!--end header-->
      
      <div class="panel-body">
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
              <td style="text-align: center;"><input type="checkbox" name="studyIds" value="<s:property value="#s.id" />"></td>
              <td><s:property value="#s.studyName" /></td>
              <td><s:property value="#s.institution" /></td>   
            </tr>
          </s:iterator>
        </tbody>
        </table>
      </div><!--end panel body-->
    </div>
  </div>
  
  <!--Cancel & Next Buttons-->
  <div class="pageNav">
    <s:submit action="navigateToIcMain" value=" Cancel " class="saved btn btn-default"/>
    <s:submit value=" Next " class="saved btn btn-project-primary"/>  
  </div>
</s:form>

<script type="text/javascript" src="<s:url value="/controllers/institutional.js" />"></script>
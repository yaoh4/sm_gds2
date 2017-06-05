<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
 <!--Begin Form -->
    <s:form id="study_form" name="study_form" cssClass="dirty-check" namespace="manage"
    enctype="multipart/form-data" data-toggle="validator" action="saveStudy" method="post" role="form">  
    
    <s:hidden name="projectId" value="%{project.id}"/>
    <s:hidden name="study.id" value="%{study.id}"/>
    <s:hidden name="study.createdBy" value="%{study.createdBy}"/>
    <s:if test="%{study.institutionalCertifications[0].id != null}">
    <s:hidden name="study.institutionalCertifications[0].id" value="%{study.institutionalCertifications[0].id}"/>
    <s:hidden name="study.institutionalCertifications[0].documents[0].id" value="%{study.institutionalCertifications[0].documents[0].id}"/>
    <s:hidden name="study.institutionalCertifications[0].createdBy" value="%{study.institutionalCertifications[0].createdBy}"/>
    <s:hidden name="study.dulVerificationId" value="%{study.dulVerificationId}"/>
    <s:hidden name="study.comments" value="%{study.comments}"/>

</s:if>
    
  <div id="messages" class="container">
    
  </div>

  <!-- Content start -->
  <div class="container">
    
         <div class="pageNav">
          <s:submit action="navigateToIcMain" value=" Cancel " class="saved btn btn-default"/>	
           <s:if test="%{study.id != null}">
           </s:if>
           <s:else>
          <s:submit action="saveAndAddStudy" value="Save & Add Another Study " class="saved btn btn-default add_field_button"/>
          </s:else>
           <s:submit type="button" action="saveStudy" class="saved btn btn-project-primary">Save Study &nbsp;&nbsp;<i class="fa fa-caret-right" style="color:#ffffff;"></i>
            </s:submit>
        </div>   
      
      <!-- Begin Panel -->
      <div class="col-md-12">

        <div class="panel  project-panel-primary">
          
          <div class="panel-heading">
           <s:if test="%{study.id != null}">
          <div class="pheader"><h4>Edit Study</h4></div>
          </s:if>
          <s:else>
            <div class="pheader"><h4>Add Study</h4></div>
             </s:else>
          </div><!--end header-->  

          <div class="panel-body" >
           <div class="panel panel-default">
                   
              <!--  STUDY SECTION HEADER  -->
            
                        <div class="studyHeadingPanel panel-heading header">
                          <h4 class="panel-title ">Study</h4>
                          <p style="font-size:11px;">For multi-site studies in which each site is providing their own certification, please enter a separate entry for each site/institution (using the same study name).</p>
                        </div>  
                                   
            <!--  STUDY SECTION BODY -->            
          <div class="panel-body" >
                                  
            <div style="display: block;" class="form-group row">
                              <div class="col-xs-12">
                                <label class="label_sn" for="Study Name">
                                  <i class="fa fa-asterisk asterisk" aria-hidden="true">&nbsp;</i>
                                  Study Name &nbsp; &nbsp; <a href="#" class="pop" data-container="body" data-toggle="popover" data-placement="right" data-content="Name of the individual study or protocol under which institutional IRB approval was granted (e.g. The Nurses Health Study)" data-html="true" style="font-size: 12px;">
                            <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a>
                                </label>
                               <div class="input-group col-xs-12">
                           <s:textfield name="study.studyName" style="width: 78.7%" class="form-control2" placeholder="Enter Study Name" id="studyname" value="%{study.studyName}" maxlength="150">
                           </s:textfield>
                </div>
                              </div>

                            </div>
                               <div style="display: block;" class="form-group row">         
                       <div class="col-xs-12">
                                <label class="label_in" for="Institution">Institution &nbsp; &nbsp; <a href="#" class="pop" data-container="body" data-toggle="popover" data-placement="right" data-content="Institution that has purview over the study or protocol and under which the IRB review occurred (e.g. Johns Hopkins School of Public Health)" data-html="true" style="font-size: 12px;">
                            <i class="fa fa-question-circle fa-1x" aria-hidden="true"></i></a></label>
                                        <div class="col-xs-12">
                                      <s:textfield name="study.institution" style="margin-left: -15px; width: 81%" class="form-control2" placeholder="Full Name of Institution" id="studyInsitution" value="%{study.institution}" maxlength="150">
                           </s:textfield>
                                      </div> 
                              </div>
                            </div>

                  </div> <!-- end panel body --> 
                  </div> <!--end panel-->           
          </div> <!--end panel body-->

          <div class="input_fields_wrap">
          </div>
        </div> <!--end panel-->
        </div>
    
              
              <!--SAVE & NEXT BUTTONS-->
        <div class="pageNav">
          <s:submit action="navigateToIcMain" value=" Cancel " class="saved btn btn-default"/>	
          <s:if test="%{study.id != null}">
           </s:if>
           <s:else>
          <s:submit action="saveAndAddStudy" value="Save & Add Another Study " class="saved btn btn-default add_field_button"/>
          </s:else>
           <s:submit type="button" action="saveStudy" class="saved btn btn-project-primary">Save Study &nbsp;&nbsp;<i class="fa fa-caret-right" style="color:#ffffff;"></i>
            </s:submit>

        </div>       
  </div> 
  </s:form>
  
     <!-- Existing studies List --> 
                  <!-- Studies Table -->
          <s:if test="%{listStudies.size > 0}">
          <div class="col-md-10">
        <p class="question">
      <a href="javascript:void"
        class="studiesTabl"><i class="expandStudies fa fa-plus-square" aria-hidden="true"></i></a>&nbsp;&nbsp;Studies (<s:property value="project.studies.size" />)</p>
             <div class="studiesTables" style="display: none;"> 
          <table style="width: 100%; font-size: 14px; table-layout:fixed;" cellpadding="0px" cellspacing="0" class="table table-bordered table-striped">
              <tbody><tr class="modalTheader">
                <th  class="tableHeader"  align="center" width="25%">Study Name</th>                      
                <th class="tableHeader" align="center" width="25%">Institution</th>
                <th>Received</th>
              </tr> 
              
              <s:iterator status="studiesStat" var="stu" value="listStudies">
              <div class="studyDetailsDiv">
              <s:set name="studyIdx" value="%{#stu.id}" />
               <tr data-id="${stu.id}">
              <td style="word-wrap:break-word;"> 
              <s:property value="%{#stu.studyName}" />
              </td>
              <td style="word-wrap:break-word;"> <s:property value="%{#stu.institution}" /></td> 
              <td>
              <s:if test="%{#stu.institutionalCertifications[0].id != null}">
              Yes
              </s:if>
              <s:else>
              No
              </s:else>
              </td>
              </tr>
              </div>
                  </s:iterator>           
                </table>
                </div>
                </div>
                </s:if> 
  
  <!-- end Content -->
  <link href="<s:url value="/stylesheets/demo.css" />" rel="stylesheet" type="text/css" media="screen">
 <script type="text/javascript" src="<s:url value="/controllers/institutional_dashboard.js" />"></script> 
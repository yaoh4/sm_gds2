<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<div class="panel panel-default" id="searchGrant" style="margin-top: 20px;">
        <div class="panel-heading">
          <span class="clickable panel-collapsed">
            <i class="fa fa-plus-square fa-lg" aria-hidden="true"></i>
          </span>
          <div class="pheader" style="display:inline;"><h5>Basic Study Information</h5></div>
          <div style="display:inline; float: right;">
            <a href="/gds/manage/navigateToBasicStudyInfo.action?projectId=${project.id}">
              <i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="edit" title="edit"></i>
            </a>
          </div>
        </div> <!--end panel header-->
        <div class="panel-body" style="display:none;">
       <s:if test="%{getPageStatus('BSI').status.code.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PAGE_STATUS_CODE_NOT_STARTED)}">
          No data entered.
        </s:if>
        <s:else>
        <s:if test="%{project.bsiReviewedFlag != null}">  
          <p><span class="reportLabel">Has the GPA reviewed the Basic Study Information?</span> ${project.bsiReviewedFlag}</p>  
          </s:if>
          <s:if test="%{bsiFile[0] != null}">
          <p><span class="reportLabel">Uploaded Basic Study Infomation Form:</span><br>
            <table style="width: 95%;" cellpadding="0px" cellspacing="0" class="table table-bordered table-striped">
              <tbody>
                <tr class="modalTheader">
                  <th class="tableHeader" align="center" width="10%">File Name</th>
                  <th class="tableHeader" align="center" width="10%">Date</th>
                  <th class="tableHeader" align="center" width="10%">Uploaded By</th>
                </tr>
                <tr>
                  <td>
                     <s:a href="javascript:openDocument(%{bsiFile[0].id})">
                      <s:property value="%{bsiFile[0].fileName}" />
                    </s:a>
                  </td>
                  <td style="white-space: nowrap"><s:date name="%{bsiFile[0].uploadedDate}" format="MMM dd yyyy hh:mm:ss a" /></td>
                  <td><a href="mailto: jonesm@mail.nih.gov" data-original-title="" title=""><s:property value="%{bsiFile[0].uploadedBy}" /></a>
                  </td>
                </tr>
              </tbody>
            </table>
            </s:if>
          <s:if test="%{project.bsiComments != null}">  
            <p><span class="reportLabel">Comments:</span>${project.bsiComments}</p>
          </s:if>
         </s:else>
        </div><!--end panel body-->
      </div><!--end panel-->

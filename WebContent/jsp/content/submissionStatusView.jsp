<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>



  <div class="panel panel-default" id="searchGrant" style="margin-top: 20px;">
        <div class="panel-heading">
          <span class="clickable panel-collapsed">
            <i class="fa fa-plus-square fa-lg" aria-hidden="true"></i>
          </span>
          <div class="pheader" style="display:inline;"><h5>Submission Status</h5></div>
          <div style="display:inline; float: right;">
            <a href="/gds/manage/navigateToRepositoryStatus.action?projectId=${project.id}">
              <i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="edit" title="edit"></i>
            </a>
          </div>
        </div> <!--end panel header-->
          <div class="panel-body" style="display:none;">
        <s:if test="%{getPageStatus('REPOSITORY').status.code.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PAGE_STATUS_CODE_NOT_STARTED)}">
          No data entered.
        </s:if>
        <s:else>
         <table width="100%" border="0" cellpadding="3"><tr><td width="30%" style="white-space: nowrap"><strong>Number of Data Repositories indicated:</strong></td><td><s:property value="%{project.repositoryStatuses.size}"/></td></tr>
          <tr>
      <td style="white-space: nowrap">&nbsp;</td>
      <td colspan="2">&nbsp;</td>
    </tr> 
          <tr><td><strong>Anticipated Submission Date:</strong></td>
            <td><s:date name="%{project.anticipatedSubmissionDate}" format="MM/dd/yyyy"/>  </td> </tr></table> 
            <p>&nbsp;</p>
          <table width="100%" border="1" cellpadding="3"
        class="table  table-bordered">
        <tbody>
          <tr style="background: #e6e6e6;">
            <th>Repositories</th>
            <th>Registration Status</th>
            <th>Submission Status</th>
            <th>Study Released Status</th>
            <th>Accession Number</th>
          </tr>

          <s:iterator value="project.repositoryStatuses" var="r" status="stat">
            <tr>
              <td>
                <s:if test="%{#r.planAnswerSelectionTByRepositoryId.otherText != null}">
                  <s:property value="#r.planAnswerSelectionTByRepositoryId.planQuestionsAnswer.displayText" />
                     - <s:property value="#r.planAnswerSelectionTByRepositoryId.otherText" />
                </s:if>
                <s:else>
                  <s:property value="#r.planAnswerSelectionTByRepositoryId.planQuestionsAnswer.displayText" />
                </s:else></td>
              <td>
                <div class="searchProgess">
                  <s:if
                    test="%{#r.lookupTByRegistrationStatusId.displayName == 'In Progress'}">
                    <img src="../images/inprogress.png" alt="In Progress" width="18px"
                      height="18px" title="In Progress"/>
                  </s:if>
                  <s:elseif
                    test="%{#r.lookupTByRegistrationStatusId.displayName == 'Completed'}">
                    <img src="../images/complete.png" alt="Complete" width="18px"
                      height="18px" title="Complete" />
                  </s:elseif>
                  <s:else>
                    <img src="../images/pending.png" alt="Pending" width="18px"
                      height="18px" title="Pending">
                  </s:else>
                </div>
              </td>
              <td>
                <div class="searchProgess">
                  <s:if
                    test="%{#r.lookupTBySubmissionStatusId.displayName == 'In Progress'}">
                    <img src="../images/inprogress.png" alt="In Progress" width="18px"
                      height="18px"  title="In Progress"/>
                  </s:if>
                  <s:elseif
                    test="%{#r.lookupTBySubmissionStatusId.displayName == 'Completed'}">
                    <img src="../images/complete.png" alt="Complete" width="18px"
                      height="18px"  title="Complete"/>
                  </s:elseif>
                  <s:else>
                    <img src="../images/pending.png" alt="Pending" width="18px"
                      height="18px" title="Pending">
                  </s:else>
                </div>
              </td>
              <td><s:property
                  value="#r.lookupTByStudyReleasedId.displayName" /></td>
              <td><s:property value="#r.accessionNumber" /></td>
            </tr>
          </s:iterator>
        </tbody>
      </table>
         </s:else>
        </div><!--end panel body-->
      </div><!--end panel-->

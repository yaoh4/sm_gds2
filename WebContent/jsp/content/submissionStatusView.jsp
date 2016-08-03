<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>



<div class="panel panel-default" id="searchGrant" style="margin-top: 20px;">
        <div class="panel-heading">
          <span class="clickable panel-collapsed"><i class="fa fa-plus-square fa-lg" aria-hidden="true"></i>
          <div class="pheader" style="display:inline;"><h5>Submission Status</h5></div><div style="display:inline; float: right;"><i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="edit" title="edit"></i></div>
        </div> <!--end panel header-->
          <div class="panel-body" style="display:none;">
        <s:if test="%{project.bsiReviewedFlag == null}">
          No data entered.
        </s:if>
        <s:else>
         <p><span class="reportLabel">[<strong><s:property value="%{project.repositoryStatuses.size}"/></strong>] Data Repositories have been indicated for this project.</span> 05/22/2020</p>
          <p><span class="reportLabel">Anticipated Submission Date:</span> 05/22/2020</p>  
          
          <table width="100%" border="1" cellpadding="3"
        class="table  table-bordered">
        <tbody>
          <tr style="background: #e6e6e6;">
            <th>Repositories</th>
            <th>Registration Status</th>
            <th>Submission Status</th>
            <th>Study Released Status</th>
            <th>Accession Number</th>
            <th>Comments</th>
          </tr>

          <s:iterator value="repoList" var="r" status="stat">
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
                    test="%{#r.lookupTByDataSubmissionStatusId.displayName == 'In Progress'}">
                    <img src="../images/inprogress.png" alt="In Progress" width="18px"
                      height="18px"  title="In Progress"/>
                  </s:if>
                  <s:elseif
                    test="%{#r.lookupTByDataSubmissionStatusId.displayName == 'Completed'}">
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

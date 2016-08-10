<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

  <div class="statusWrapper">
    <div class="status"><a href="#" class="statusLink">Generate Missing Data Report</a> &nbsp; &nbsp;</div>
    <div class="statusIcon"> 
      <a href="#" class="tooltip">
        <s:if test="%{pageStatusCode.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PAGE_STATUS_CODE_NOT_STARTED)}">	
          <img src="../images/newV.png" alt="Not Started" />
        </s:if>
        <s:elseif test="%{pageStatusCode.equals(@gov.nih.nci.cbiit.scimgmt.gds.constants.ApplicationConstants@PAGE_STATUS_CODE_IN_PROGRESS)}">
          <img src="../images/inprogress.png" alt="In Progress" />
        </s:elseif>
        <s:else>
          <img src="../images/complete.png" alt="Completed" />
        </s:else>
        <span>
          <img class="callout" src="../images/callout_black.gif" />
          <strong>Legend:</strong><br />  
          <img src="../images/legend.gif" /> 
        </span>
      </a>
    </div>
  </div> 

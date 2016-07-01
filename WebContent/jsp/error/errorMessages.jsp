<%@ taglib uri="/struts-tags" prefix="s"%>
<s:if test="%{!actionErrors.isEmpty or !fieldErrors.isEmpty}">
   <fieldset class="gdsErrorMessage">
      <legend class="gdsErrorMessageLegend">Errors</legend>
	  <div class="alert alert-warning" style="margin-left:-120px; margin-right: 5px;">
      <s:actionerror/>
      <s:fielderror/>
	  </div>
   </fieldset>
</s:if>
<s:if test="%{ !actionMessages.isEmpty && !disableMessages }">
    <fieldset class="gdsErrorMessage">
       <legend class="gdsErrorMessageLegend">Messages</legend>
      <div style="margin-left:-120px; margin-right: 5px;">
	  <s:actionmessage escape="false"/>
	  </div>
   </fieldset>  
</s:if>

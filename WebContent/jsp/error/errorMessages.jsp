<%@ taglib uri="/struts-tags" prefix="s"%>
<s:if test="%{!actionErrors.isEmpty or !fieldErrors.isEmpty}">
   <fieldset class="lbl_block">
      <legend>Errors</legend>
      <s:actionerror theme="jquery" />
      <s:fielderror theme="jquery" />
   </fieldset>
</s:if>
<s:if test="%{ !actionMessages.isEmpty && !disableMessages }">
   <span class="print"> <fieldset class="lbl_block">
      <legend>Messages</legend>
      <s:actionmessage escape="false" theme="jquery" />
   </fieldset>
   </span>
</s:if>

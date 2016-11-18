<%@ taglib uri="/struts-tags" prefix="s"%>




<p class="question" style="width:32%; float:left; margin-left:30px;">Upload Exception Memo: [to be uploaded by GPA]
&nbsp;<a href="#" id="popover" style="font-size: 12px;">
                         <i class="helpfile fa fa-question-circle fa-1x"
							aria-hidden="true"></i></a> &nbsp;
<label for="exceptionMemo" style="width: auto; display: none;">Upload
	Exception Memo</label>
</p>

	<div class="input-group" style="width: 94px; float: left; margin-bottom: 20px;" >
		<label
			class="input-group-btn"> <span class="btn btn-default">
				Choose File
			<s:file style="display: none;" name="exceptionMemo" id="exceptionMemo" />
		</span>
		</label>
	</div>
	<div style="clear:both;"></div>


						
<s:if test="%{excepMemoFile.size > 0}">
	<p></p>
	<div class="qSpacing" style="margin-top: 10px; margin-left: 30px;">
	<table style="width: 869px;" cellpadding="0px" cellspacing="0" class="table table-bordered table-striped">
		<tr>
			<th class="tableHeader" align="center" >File Name</th>
			<th class="tableHeader" align="center" >Date</th>
			<th class="tableHeader" align="center" >Uploaded By</th>
		</tr>
		<tr>
			<td><s:a href="javascript:openDocument(%{excepMemoFile[0].id})"><s:property
				value="%{excepMemoFile[0].fileName}" /></s:a></td>
			<td style="white-space: nowrap">
				<s:date name="%{excepMemoFile[0].uploadedDate}" format="MMM dd yyyy hh:mm:ss a" /></td>
			<td><s:property
				value="%{excepMemoFile[0].uploadedBy}" /></td>
		</tr>
	</table>
	</div>
</s:if>
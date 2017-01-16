// common javascript function for file download and delete

function openDocument(id)
{
  var url = "/gds/manage/downloadFile.action?docId=" + id;
  var winName = "document";
  var features = "menubar=yes,scrollbars=yes,resizable=yes,width=850,height=700";

  var newWin = window.open(url, winName ,features);
}

function openFileModal(result){
	$('#fileModalId').html(result);
	$('#fileModal').modal('show');
}

function openMissingDataReport(id, action) {
	var url = action + "projectId=" + id;
	var winName = "Missing Data Report";
	var features = "menubar=yes,scrollbars=yes,resizable=yes,width=800,height=800";
	var newWin = window.open(url, winName, features);
}

function openDetailsReport(id) {
	var url = "/gds/manage/viewSubmissionDetails.action?projectId=" + id;
	var winName = "Submission Details Report";
	var features = "menubar=yes,scrollbars=yes,resizable=yes,width=800,height=800";
	var newWin = window.open(url, winName, features);
}

$("a.hoverOver").hover(function(){
	var value=$(this).children().first().val();
	$(this).attr('data-original-title', value);
});


$(document).ready(function() {
	
	var icItemsSize=$(".icCountList").length;
	for(var size=0; size < icItemsSize; size++) {
		setStatusIcon("icReg" + size, "icDiv" + size);
	}
	
});

function setStatusIcon(elem, elemDiv) {
	$("#" + elemDiv).empty();
	var elemVal = $("#" + elem).val();
	if($("#" + elem).val() == 'COMPLETED') {
		$("#" + elemDiv).prepend('<img src="../images/complete.png" alt="Completed" width="18px" height="18px" title="Completed"/>');
	} else if($("#" + elem).val() == 'INPROGRESS') {
		$("#" + elemDiv).prepend('<img src="../images/inprogress.png" alt="In Progress" width="18px" height="18px" title="In Progress"/>');
	} else {
		$("#" + elemDiv).prepend('<img src="../images/pending.png" alt="Not Started" width="18px" height="18px" title="Not Started"/>');
	}
}

function showCharCount(elem, countDisplayDiv) {
	var max = 50;
	//A newline is actually 2 characters but val function returns only one
	//char. Hence replace all occurrences of a Carriage Return not followed 
	//by a New Line, and all New Lines not followed by a Carriage Return, 
	//with a Carriage Return - Line Feed pair
	var len = $(elem).val().replace(/\r(?!\n)|\n(?!\r)/g, "\r\n").length;
	
	if (len >= max) {
		$(countDisplayDiv).text(' you have reached the limit');
	} else {
		var char = max - len;
		$(countDisplayDiv).text(char + ' characters left');
	}
}
// common javascript function for file download and delete

function openDocument(id)
{
  var url = "/gds/manage/downloadFile.action?docId=" + id;
  var winName = "document";
  var features = "menubar=yes,scrollbars=yes,resizable=yes,width=850,height=700";

  var newWin = window.open(url, winName ,features);
}

function removeDocument(docId, projectId)
{
	var result = "";
	ans = confirm("Are you sure you want to delete this file?");
	if (ans) {
	
		$.ajax({
			url: "deleteGdsFile.action",
			type: "post",
			data: {docId: docId, projectId: projectId},
			async:   false,
			success: function(msg){
				result = $.trim(msg);
			}, 
			error: function(){}		
		});
		if(result.startsWith("<p")) {
			$('div.loadFileHistory').html(result);
		}
		else {
			openFileModal(result);
		}
	}
}

function openFileModal(result){
	$('#fileModalId').html(result);
	$('#fileModal').modal('show');
}


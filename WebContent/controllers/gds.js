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

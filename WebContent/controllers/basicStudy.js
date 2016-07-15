//for basicStudy.htm page --

$(document).ready(function () {

//Show and hide file history
$('body').on('click', 'a.history', function() {
    $(".uploadedHistory").slideToggle('500');
    $("i.expand.fa").toggleClass('fa-plus-square fa-minus-square');
});

// Data sharing plan file upload Ajax
$("#basic-study-form").on('click', '#bsiUpload', function () {

	var result = "";
	var $form, fd;
    $form = $("#basic-study-form");
    fd = new FormData($form[0]);
    
	$.ajax({
	  	url: 'uploadBasicStudyInfo.action',
	  	type: 'post',
	  	processData: false,
	    contentType: false,
	    data: fd,
	  	async:   false,
	  	success: function(msg){
			result = $.trim(msg);
		}, 
		error: function(){}	
	});
	if(result.indexOf("<p") == 0) {
		$('div.loadFileHistory').html(result);
		$("#bsiUpload").prev('div').children('.input-group').find(':text').val('')
	}
	else {
		bootbox.alert(result, function() {
  			return true;
		});
	}
});

});

function removeDocument(docId, projectId)
{
	var result = "";
	bootbox.confirm("Are you sure you want to delete this file?", function(ans) {
		  if (ans) {
			  $.ajax({
					url: "deleteBsiFile.action",
					type: "post",
					data: {docId: docId, projectId: projectId},
					async:   false,
					success: function(msg){
						result = $.trim(msg);
					}, 
					error: function(){}		
				});
				if(result.indexOf("<p") == 0) {
					$('div.loadFileHistory').html(result);
				}
				else {
					openFileModal(result);
				}
		  }
	}); 
}
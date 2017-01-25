//for basicStudy.htm page --

$(document).ready(function () {

//Show and hide file history
$('body').on('click', 'a.history', function() {
    $(".uploadedHistory").slideToggle('500');
    $("i.expand.fa").toggleClass('fa-plus-square fa-minus-square');
});

//set the correct length for text areas
showCharCount('#bsiComments', '#charNum5');

//Function that shows Other box
$('#25').change(function () {
    if( $(this).is(':checked')) {
        $("#addRepo").show();
    } else {
    	$(".otherWrapper").first().children("i").remove();
    	$(".other").val('');
    	$(".otherWrapper").not(".otherWrapper:first").remove();
        $("#addRepo").hide();
    }
}); 
$('#25').change();




//Function that controls the Adding of Additional Repositories
$("#basic-study-form").on('click', '#addfield', function () {
	
	maxInputs = 10;
	fieldCount = $(".otherWrapper").length;

	if (fieldCount < maxInputs) // max input box allowed
	{
		// If its the second row, add a trash bin next to the first row.
		if(fieldCount == 1) {
			$(".otherWrapper").first().append('<i class="fa fa-trash fa-lg delete removeclass" title="Delete" aria-hidden="true" alt="Delete icon" style="font-size: 18px; padding-right: 3px; margin-left: 10px; cursor:pointer">'
					+ '</i></div>');
		}
		
		// add input box
		$(".otherWrapper").last().after('<div class="otherWrapper" style="margin-bottom: 15px; margin-top: 15px;">'
			+ '<input type="text" maxlength="200" class="other" name="otherText[25]" id="field_'
			+ fieldCount
			+ '" placeholder="Name of Repository" />'
			+ '<i class="fa fa-trash fa-lg delete removeclass" title="Delete"  aria-hidden="true" alt="Delete icon" style="font-size: 18px; padding-right: 3px; margin-left: 13px; cursor:pointer">'
			+ '</i></div>');
		
	}
});


//Function that controls the Deleting of Additional Repositories
$("#basic-study-form").on('click', '.removeclass', function () {//user click on remove text
	$(this).parent('div').remove(); //remove text box
	
	fieldCount = $(".otherWrapper").length;
	// If this was the second row, remove the trash bin from the first row.
	if(fieldCount == 1) {
		$(".otherWrapper").first().children("i").remove();
	}
	
}); 


// Data sharing plan file upload Ajax
$("#basic-study-form").on('change', '#bsi', function () {

	$("#messages").empty();
	
	var result = "";
	var $form, fd;
    $form = $("#basic-study-form");
    fd = new FormData($form[0]);
    $('button.has-spinner').toggleClass('active');
    if($("#bsi").val() != '') {
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
	$('button.has-spinner').toggleClass('active');
	if(result.indexOf("<p") == 0) {
		$('div.loadFileHistory').html(result);
		$("#bsiUpload").prev('div').children('.input-group').find(':text').val('')
		$("#bsi").val('');
	}
	else {
		bootbox.alert(result, function() {
  			return true;
		});
	}
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

$(".helpfile").click(function() {
	
	var url = "/documentation/application/Project_and_Sub-project_BSI_help.pdf";
	var winName = "Submission BSI Help File";
	var features = "menubar=yes,scrollbars=yes,resizable=yes,width=800,height=800";
	var newWin = window.open(url, winName, features);
});

//comments kep up function
$('#bsiComments').keyup(function() {
	//set the correct length for text areas
	showCharCount(this, '#charNum5');
});

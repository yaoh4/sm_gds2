//for dataSharing.htm page --

$(document).ready(function () {

//Show and hide file history
$('body').on('click', 'a.history', function() {
    $(".uploadedHistory").slideToggle('500');
    $("i.expand.fa").toggleClass('fa-plus-square fa-minus-square');
});


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
$("#gds-form").on('click', '#addfield', function () {
	
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
$("#gds-form").on('click', '.removeclass', function () {//user click on remove text
	$(this).parent('div').remove(); //remove text box
	
	fieldCount = $(".otherWrapper").length;
	// If this was the second row, remove the trash bin from the first row.
	if(fieldCount == 1) {
		$(".otherWrapper").first().children("i").remove();
	}
	
}); 

// If "Is there a data sharing exception requested for this project?" is changed to No,
// Make sure to hide the "Will there be any data submitted?" question.
$("#3").change(function () {
	applyUiRule(this,'3','8','hide');
});

// If "Copy/paste into a text box" is not selected, 
// Make sure to hide the text editor.
$('body').click( function(e) {
	if (!$("#31").is(":checked")) {
		$("#textEditorDiv").hide();
	} else if ($("#31").is(":checked")) {
		CKEDITOR.replace( 'editor1' );
	}
});

$('body').click();

// If user selects Non-human ONLY to "What specimen type does the data submission pertain to?", 
// the system will select and grey out the "Unrestricted" option 
// and the "Controlled" checkbox will be non-selectable in "What type of access is the data to be made available through?"
// Also for the same condition, the system will grey out the “Database of Genotypes and Phenotypes (dbGaP)” repository checkbox in question #7
$("#12").change(function () {
	humanNonhuman(false);
});
$("#13").change(function () {
	humanNonhuman(false);
});
humanNonhuman(true);

// If user selects "Controlled" access in "What type of access is the data to be made available through?", 
// the system will select and grey out the "Database of Genotypes and Phenotypes (dbGaP)" repository checkbox 
// in "What repository will the data be submitted to?"
$("#18").change(function () {
	controlledUnrestricted(false);
});
$("#19").change(function () {
	controlledUnrestricted(false);
});
controlledUnrestricted(true);

// Data sharing plan file upload Ajax
$("#gds-form").on('click', '#dataSharingPlanUpload', function () {

	var result = "";
	var $form, fd;
    $form = $("#gds-form");
    fd = new FormData($form[0]);
    $('button.has-spinner').toggleClass('active');
	$.ajax({
	  	url: 'uploadDataSharingPlan.action',
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
		$("#dataSharingPlanUpload").prev('div').children('.input-group').find(':text').val('')
		$("#dataSharingPlan").val('');
	}
	else {
		bootbox.alert(result, function() {
  			return true;
		});
	}
});

// Exception memo file upload Ajax
$("#gds-form").on('click', '#exceptionMemoUpload', function () {
	
	var result = "";
	var $form, fd;
    $form = $("#gds-form");
    fd = new FormData($form[0]);
    $('button.has-spinner').toggleClass('active');
	$.ajax({
	  	url: 'uploadExceptionMemo.action',
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
		$('div#exceptionMemoDiv').html(result);
		$("#exceptionMemo").val('');
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
					url: "deleteGdsFile.action",
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

function enableAllCheckbox() {
	$("#gds-form :checkbox").prop('disabled', false);
}

function warnGdsPlan(element) {

	var result = "";
	var $form, fd;
	$form = $("#gds-form");
	fd = new FormData($form[0]);

	$.ajax({
		url : 'warnGdsPlan.action',
		type : 'post',
		processData : false,
		contentType : false,
		data : fd,
		async : false,
		success : function(msg) {
			result = $.trim(msg);
		},
		error : function() {
		}
	});
	
	if (result == "") {
		return true;
	}
	bootbox.confirm(result, function(ans) {
		if (ans) {
			$('#gds-form').submit();
			return true;
		} else {
			return true;
		}
	});
	return false;
}

function warnGdsPlanNext(element) {

	var result = "";
	var $form, fd;
	$form = $("#gds-form");
	fd = new FormData($form[0]);

	$.ajax({
		url : 'warnGdsPlan.action',
		type : 'post',
		processData : false,
		contentType : false,
		data : fd,
		async : false,
		success : function(msg) {
			result = $.trim(msg);
		},
		error : function() {
		}
	});
	
	if (result == "") {
		return true;
	}
	bootbox.confirm(result, function(ans) {
		if (ans) {
			$('#gds-form').attr('action', "saveGdsPlanAndNext").submit();
			return true;
		} else {
			return true;
		}
	});
	return false;
}

//If user selects Non-human ONLY to "What specimen type does the data submission pertain to?", 
//the system will select and grey out the "Unrestricted" option 
//and the "Controlled" checkbox will be non-selectable in "What type of access is the data to be made available through?"
//Also for the same condition, the system will grey out the “Database of Genotypes and Phenotypes (dbGaP)” repository checkbox in question #7
function humanNonhuman(load) {
	if (!$("#12").is(":checked") && $("#13").is(":checked")) {
		// Non-human only, select and disable "Unrestricted", disable "Controlled" and "dbGaP"
		$("#18").prop('checked', false ); // De-select Controlled
		$("#18").prop('disabled', true); // Controlled
		$("#19").prop('checked', true ); // Select Unrestricted
		$("#19").prop('disabled', true); // Unrestricted
		$("#21").prop('checked', false ); // De-select dbGaP
		$("#21").prop('disabled', true); // dbGaP
	} else { // They are both checked or None are checked, so enable all
		$("#18").prop('disabled', false); // Controlled
		if(!load)
			$("#19").prop('checked', false ); // De-select Unrestricted
		$("#19").prop('disabled', false); // Unrestricted
		$("#21").prop('disabled', false); // dbGaP
	}
}

//If user selects "Controlled" access in "What type of access is the data to be made available through?", 
//the system will select and grey out the "Database of Genotypes and Phenotypes (dbGaP)" repository checkbox 
//in "What repository will the data be submitted to?"
function controlledUnrestricted(load) {
	if (!$("#18").is(":checked") && $("#19").is(":checked")) {
		// Unrestricted ONLY is checked.
		$("#21").prop('checked', false ); // De-select dbGaP
		$("#21").prop('disabled', true); // dbGaP
	} else if ($("#18").is(":checked")) {
		// Controlled is checked.
		$("#21").prop('checked', true ); // Select dbGaP
		$("#21").prop('disabled', true); // Disable dbGaP
	} else { // Control is de-selected and no unrestricted is selected
		if(!load)
			$("#21").prop('checked', false ); // De-select dbGaP
		$("#21").prop('disabled', false); // dbGaP
	}
}


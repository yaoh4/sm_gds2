//for general.htm page -- 

$(document).ready(function () {

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
		}
	});
	
	$('body').click();
	
	// If user selects Non-human ONLY to "What specimen type does the data submission pertain to?", 
	// the system will select and grey out the "Unrestricted" option 
	// and the "Controlled" checkbox will be non-selectable in "What type of access is the data to be made available through?"
	// Also for the same condition, the system will grey out the “Database of Genotypes and Phenotypes (dbGaP)” repository checkbox in question #7
	$("#12").change(function () {
		if (!$("#12").is(":checked") && $("#13").is(":checked")) {
			// Non-human only, select and disable "Unrestricted", disable "Controlled" and "dbGaP"
			$("#18").prop('disabled', true); // Controlled
			$("#19").prop('checked', true ); // Select Unrestricted
			$("#19").prop('disabled', true); // Unrestricted
			$("#21").prop('disabled', true); // dbGaP
		} else { // They are both checked or None are checked, so enable all
			$("#18").prop('disabled', false); // Controlled
			$("#19").prop('disabled', false); // Unrestricted
			$("#21").prop('disabled', false); // dbGaP
		}
	});
	$("#13").change(function () {
		if (!$("#12").is(":checked") && $("#13").is(":checked")) {
			// Non-human only, select and disable "Unrestricted", disable "Controlled" and "dbGaP"
			$("#18").prop('disabled', true); // Controlled
			$("#19").prop('checked', true ); // Select Unrestricted
			$("#19").prop('disabled', true); // Unrestricted
			$("#21").prop('disabled', true); // dbGaP
		} else { // They are both checked or None are checked, so enable all
			$("#18").prop('disabled', false); // Controlled
			$("#19").prop('disabled', false); // Unrestricted
			$("#21").prop('disabled', false); // dbGaP
		}
	});
	$("#12").change();
	
	// If user selects "Controlled" access in "What type of access is the data to be made available through?", 
	// the system will select and grey out the "Database of Genotypes and Phenotypes (dbGaP)" repository checkbox 
	// in "What repository will the data be submitted to?"
	$("#18").change(function () {
		if ($("#18").is(":checked")) {
			$("#21").prop('checked', true ); // Select dbGaP
			$("#21").prop('disabled', true); // Disable dbGaP
		} else { // Deselected so Enable dbGaP
			$("#21").prop('disabled', false); // dbGaP
		}
	});
	$("#18").change();
	
	//The system will pre-select Database of Genotypes and Phenotypes (dbGaP), 
	// if user checked NCI Genomic Data Commons (GDC)
	$("#23").change(function () {
		if ($("#23").is(":checked")) {
			$("#21").prop('checked', true ); // Select dbGaP
		}
	});
	
	// Data sharing plan file upload Ajax
	$("#gds-form").on('click', '#dataSharingPlanUpload', function () {

		var result = "";
		var $form, fd;
	    $form = $("#gds-form");
	    fd = new FormData($form[0]);
	    
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
		if(result.startsWith("<p")) {
			$('div.loadFileHistory').html(result);
		}
		else {
			openFileModal(result);
		}
	});
	
	// Exception memo file upload Ajax
	$("#gds-form").on('click', '#exceptionMemoUpload', function () {
		
		var result = "";
		var $form, fd;
	    $form = $("#gds-form");
	    fd = new FormData($form[0]);
	    
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
		if(result.startsWith("<p")) {
			$('div#exceptionMemoDiv').html(result);
		}
		else {
			openFileModal(result);
		}
	});

});

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


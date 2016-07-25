//for general.htm page -- 

$(function () {
	//date picker for Project Start
	$('#pStartDate .input-group.date').datepicker({
          orientation: "bottom auto",
          todayHighlight: true
          });
});

//date picker for Project End    
$(function () {
	$('#pEndDate .input-group.date').datepicker({
          orientation: "bottom auto",
          todayHighlight: true
          });
});



//Edit button
function openGrantsContractsSearchPage() {
	$("#messages").empty();
	var parent = $(".tableContent").parent();
	$(".tableContent").remove();
	$(".tableContentOdd").remove();
	parent.append('<tr class="tableContent"><td colspan="4">Nothing found to display.</td></tr>');
	$("#generalInfoSection").hide();
	$("#searchGrantsContracts").show();
	
}


//Search button
$("#searchGrants").click(function() {
	$("#messages").empty();
	if($('#grantSearch').val().length == 0) {
		var errorMsg = "Please enter Intramural (Z01)/Grant/Contract #.";
		$("#messages").prepend('<div class="container"><div class="col-md-12"><div class="alert alert-danger"><h3><i class="fa fa-exclamation-triangle" aria-hidden="true"></i>&nbsp;Error Status</h3><ul class="errorMessage"><li><span>' + errorMsg + '</span></li></ul></div></div></div>');
		window.scrollTo(0,0);
	} else {	
		$('#general_form').attr('action', "searchGrantsContractsAction.action").submit();
	}
	
});


//Confirm if user really wants to clear grants/contracts data.
function clearGrantsContracts(){	
	var result = "This will clear all the autopopulated grants/contracts data.<br /> Do you wish to continue?";
	bootbox.confirm(result, function(ans) {
		if (ans) {
			clearData();
			return true;
		} else {
			return true;
		}
	});
}


//Cancel button
$(".cancel").click(function() {	
	$('#grantSearch').val('');
	$("#messages").empty();
	if($("#grantsContractNum").val().length > 0) {
		//$('#general_form').attr('action', "navigateToGeneralInfo.action").submit();
		$("#searchGrantsContracts").hide();
		$("#generalInfoSection").show();
	} else {
		$('#general_form').attr('action', "newSubmission.action").submit();
	}

});


//Next button
function populateGrantsContractsData(){
	
	//$('#grantSearch').val('');
	$("#messages").empty();
	var grantContract = $("input[name=selectedGrantContract]:checked").val();
	
	if(grantContract == undefined) {
		var errorMsg = "Please select Intramural (Z01)/Grant/Contract #.";
		$("#messages").prepend('<div class="container"><div class="col-md-12"><div class="alert alert-danger"><h3><i class="fa fa-exclamation-triangle" aria-hidden="true"></i>&nbsp;Error Status</h3><ul class="errorMessage"><li><span>' + errorMsg + '</span></li></ul></div></div></div>');
		window.scrollTo(0,0);
		return;
	}
	
	var json = jQuery.parseJSON(grantContract);	
		
	if (json.grantContractNum !== "undefined") {
		$("#grantsContractNum").val(json.grantContractNum);
		$("#grantsContractNum").prop('readOnly', true);
	}
	
	if (json.projectTitle !== "undefined") {
		$("#projectTitle").val(json.projectTitle);
		$("#projectTitle").prop('disabled', true);
	}
	
	if (json.piFirstName !== "undefined") {
		$("#fnPI").val(json.piFirstName);
		$("#fnPI").prop('disabled', true);
	}
	
	if (json.piLastName !== "undefined") {
		$("#lnPI").val(json.piLastName);
		$("#lnPI").prop('disabled', true);
	}
	
	if (json.piEmailAddress !== "undefined") {
		$("#piEmail").val(json.piEmailAddress);
		$("#piEmail").prop('disabled', true);
	}
	
	
	if (json.piInstitution !== "undefined") {
		$("#PIInstitute").val(json.piInstitution);
		$("#PIInstitute").prop('disabled', true);
	}
	
	if (json.pdFirstName !== "undefined") {
		$("#fnPD").val(json.pdFirstName);
		$("#fnPD").prop('disabled', true);	
	}
		
	if (json.pdLastName !== "undefined") {
		$("#lnPD").val(json.pdLastName);
		$("#lnPD").prop('disabled', true);
	}
	
	if (json.projectPeriodStartDate !== "undefined") {
		$("#projectStartDate").val(json.projectPeriodStartDate);
		$("#projectStartDate").prop('disabled', true);
	}
	
	if (json.projectPeriodEndDate !== "undefined") {
		$("#projectEndDate").val(json.projectPeriodEndDate);
		$("#projectEndDate").prop('disabled', true);
	}
	
	if (json.applId !== "undefined") {
		$("#applId").val(json.applId);			
	}
	
	//window.opener.$('#clearGrantsContractsId').prop('disabled', false);	
	$('#grantSearch').val('');
	$("#searchGrantsContracts").hide();
	$("#generalInfoSection").show();
	//$('#general_form').attr('action', "navigateToGeneralInfo.action").submit();
}


function linkUnlinkGrants(elem) {
	if($(elem).attr("id") == 'link') {
		$("#dataLinkFlag").val('Y');
		// Re-populate the data from DB.
		refreshGrantsContractsData();
		$("#link").hide();
		$("#unlink").show();
		$(".unlink-group").prop('disabled', true);
	} else {
		var result = "Unlinking will remove the auto-refresh of the Intramural/Grant/Contract data from the data source that was used to populate it.<br /> Do you wish to continue?";
		bootbox.confirm(result, function(ans) {
			if (ans) {
				$("#dataLinkFlag").val('N');
				$("#unlink").hide();
				$("#link").show();
				$(".unlink-group").prop('disabled', false);
				return true;
			} else {
				return true;
			}
		});
	}
}




//Clear grants/contracts data.
function clearData(){
	$("#grantsContractNum").val("");
	$("#projectTitle").val("");
	$("#projectTitle").prop('disabled', false);
	$("#fnPI").val("");
	$("#fnPI").prop('disabled', false);
	$("#lnPI").val("");
	$("#lnPI").prop('disabled', false);
	$("#piEmail").val("");
	$("#piEmail").prop('disabled', false);
	$("#PIInstitute").val("");
	$("#PIInstitute").prop('disabled', false);
	$("#fnPD").val("");
	$("#fnPD").prop('disabled', false);	
	$("#lnPD").val("");
	$("#lnPD").prop('disabled', false);
	$("#projectStartDate").val("");
	$("#projectStartDate").prop('disabled', false);
	$("#projectEndDate").val("");
	$("#projectEndDate").prop('disabled', false);
	$("#applId").val("");	
	$('#clearGrantsContractsId').prop('disabled', true);		
}

//Warns user when user clicks save.
function warnGeneralInfo(element) {

	var result = "";
	var $form, fd;
	$form = $("#general_form");
	fd = new FormData($form[0]);

	$.ajax({
		url : 'warnGeneralInfo.action',
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
			$('#general_form').attr('action', "saveGeneralInfo").submit();
			return true;
		} else {
			return true;
		}
	});
	return false;
}

//Warns user when user clicks save and next.
function warnGeneralInfoNext(element) {

	var result = "";
	var $form, fd;
	$form = $("#general_form");
	fd = new FormData($form[0]);

	$.ajax({
		url : 'warnGeneralInfo.action',
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
			$('#general_form').attr('action', "saveGeneralInfoAndNext").submit();
			return true;
		} else {
			return true;
		}
	});
	return false;
}

function refreshGrantsContractsData(){
	var applId = $("#applId").val();
		
	$.ajax({
	  	url: 'getGrantOrContractByApplId.action',
	  	data: {applId: applId},
	  	type: 'post',
	  	async:   false,
	  	success: function(json){
	  		if (json.grantContractNum !== "undefined") {
	  			$("#grantsContractNum").val(json.grantContractNum);
	  		}
	  		if (json.projectTitle !== "undefined") {
	  			$("#projectTitle").val(json.projectTitle);
	  		}
	  		if (json.piFirstName !== "undefined") {
	  			$("#fnPI").val(json.piFirstName);
	  		}
	  		if (json.piLastName !== "undefined") {
	  			$("#lnPI").val(json.piLastName);
	  		}
	  		if (json.piEmailAddress !== "undefined") {
	  			$("#piEmail").val(json.piEmailAddress);
	  		}
	  		if (json.piInstitution !== "undefined") {
	  			$("#PIInstitute").val(json.piInstitution);
	  		}
	  		if (json.pdFirstName !== "undefined") {
	  			$("#fnPD").val(json.pdFirstName);
	  		}	
	  		if (json.pdLastName !== "undefined") {
	  			$("#lnPD").val(json.pdLastName);
	  		}
	  		if (json.projectPeriodStartDate !== "undefined") {
	  			$("#projectStartDate").val(json.projectPeriodStartDate);
	  		}
	  		if (json.projectPeriodEndDate !== "undefined") {
	  			$("#projectEndDate").val(json.projectPeriodEndDate);
	  		}	
	  		if (json.applId !== "undefined") {
	  			$("#applId").val(json.applId);			
	  		}
		}, 
		error: function(){}	
	});
}

 $(function () { 
	if($('#grantsContractNum').val() != ""){ 
		$('#clearGrantsContractsId').prop('disabled', false);	
	}   
	else{
		$('#clearGrantsContractsId').prop('disabled', true);	
	} 
	
	
	if ($("#dataLinkFlag").attr("value") == 'Y') {
		$("#unlink").show();
		$(".unlink-group").prop('disabled', true);
		
	} else {
		$("#link").show();
		$(".unlink-group").prop('disabled', false);
	}
});
 
 $(document).ready(function() {
		//$(':radio').click(function() {
			//if(window.opener.$("#projectTitle").val() != "" || window.opener.$("#fnPI").val() != ""
			//	|| window.opener.$("#lnPI").val() != "" || window.opener.$("#piEmail").val() != ""
			//	|| window.opener.$("#PIInstitute").val() != "" || window.opener.$("#fnPD").val() != ""
			//	|| window.opener.$("#lnPD").val() != "" || window.opener.$("#projectStartDate").val() != ""
			//	|| window.opener.$("#projectEndDate").val() != ""){ 
			//	$('.alert').show();
			//}
			//$("#btnConfirm").prop('disabled', false);
		//})
		
		if($("#grantsContractNum").val().length == 0 ||
				$("#grantSearch").val().length != 0) {
			//The project has no grant number specified, or a
			//grant search request was made
			$("#generalInfoSection").hide();
			$("#searchGrantsContracts").show();
		} else {
			$("#searchGrantsContracts").hide();
			$("#generalInfoSection").show();
		}	
 });

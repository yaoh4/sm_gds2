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

//Open grants/Contracts search page:
function openGrantsContractsSearchPage() {
	var url = "/gds/manage/openSearchGrantsContracts.action";
	var winName = "Intramural (Z01)/Grant/Contract # Search";
	var features = "menubar=yes,scrollbars=yes,resizable=yes,width=800,height=800";
	var newWin = window.open(url, winName, features);
}

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
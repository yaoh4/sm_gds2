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

$(function(){
	var parentId=$("#parentId").val();
	if(parentId){
		$("input[type=radio]").attr('disabled', true);
	}
});


//Search/Edit button
function openGrantsContractsSearchPage() {
	
	$("#messages").empty();
	$('#grantSearch').val('');
	var parent = $(".tableContent").parent();
	$(".tableContent").remove();
	$(".tableContentOdd").remove();
	parent.append('<tr class="tableContent"><td colspan="4">Nothing found to display.</td></tr>');
	
	$("#prevLinkedSubmissions").hide();
	$("#generalInfoSection").hide();
	$("#searchGrantsContracts").show();
	$("#grantSearch").focus();	
	//If user hits Enter key : 
	$("#general_form").keydown(function( event ) {
		if ( event.which == 13) {				
			//Prevent default submit
			event.preventDefault();						
			//Hit Search
			$( "#searchGrants" ).click();					
		}
	});		
	
}

$( document ).ready(function() {
	if($("#grantsContractNum").val()=='') {
		  $("#grantDiv i").removeClass("fa fa-pencil").addClass("fa fa-search");
		  $("#grantsContractNum").attr("placeholder", "Click on Search Icon");
		  $("#canAct").hide();
	}
	
});



function linkUnlinkGrants(elem) {
	if($(elem).attr("id") == 'link') {
		var result = "Linking will restore the auto-refresh of the Grant/Intramural/Contract data to the data source and delete any edits you migh have made.<br /> Do you wish to continue?";
		bootbox.confirm(result, function(ans) {
			if (ans) {
				$("#dataLinkFlag").val('Y');
				// Re-populate the data from DB.
				refreshGrantsContractsData();
				$("#link").css("background-color", "#d4d4d4");
				$("#unlink").css("background-color", "#FFF");
				$('#link').addClass('disabled');
				$("#unlink").removeClass('disabled');
				$(".unlink-group").prop('disabled', true);
				return true;
			} else {
				return true;
			}
		});
	} else {
		var result = "Unlinking will remove the auto-refresh of the Intramural/Grant/Contract data from the data source that was used to populate it.<br /> Do you wish to continue?";
		bootbox.confirm(result, function(ans) {
			if (ans) {
				//getting the cancerActivity code
				$("#dataLinkFlag").val('N');
				$("#unlink").css("background-color", "#d4d4d4");
				$("#link").css("background-color", "#FFF");
				$("#unlink").addClass('disabled');
				$("#link").removeClass('disabled');
				$(".unlink-group").prop('disabled', false);
				refreshCancerActivityCode();
				return true;
			} else {
				return true;
			}
		});
	}
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

function refreshCancerActivityCode(){
	var applId = $("#applId").val();
	$.ajax({
	  	url: 'getGrantOrContractByApplId.action',
	  	data: {applId: applId},
	  	type: 'post',
	  	async:   false,
	  	success: function(json){
	  		if (json.cayCode !== "undefined") {
	  			$("#cancerActivity").val(json.cayCode);
	  			$("#cancerActivity").prop('readOnly', true);
	  		}
		}, 
		error: function(){}	
	});
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
	  			var d = new Date(json.projectPeriodStartDate);
	  			$("#projectStartDate").val(d.getMonth()+1 +'/'+ d.getDate() +'/'+ d.getFullYear());
	  		}
	  		if (json.projectPeriodEndDate !== "undefined") {
	  			var d = new Date(json.projectPeriodEndDate);
	  			$("#projectEndDate").val(d.getMonth()+1 +'/'+ d.getDate() +'/'+ d.getFullYear());
	  		}	
	  		if (json.cayCode !== "undefined") {
	  			$("#cancerActivity").val(json.cayCode);
	  			$("#cancerActivity").prop('readOnly', true);
	  		}
	  		if (json.applId !== "undefined") {
	  			$("#applId").val(json.applId);			
	  		}
		}, 
		error: function(){}	
	});
}



 $(function () { 
	
	if ($("#dataLinkFlag").attr("value") == 'Y') {
		$("#link").css("background-color", "#d4d4d4");
		$("#unlink").css("background-color", "#FFF");
		$('#link').addClass('disabled');
		$("#unlink").removeClass('disabled');
		$(".unlink-group").prop('disabled', true);
		
	} else {
		$("#unlink").css("background-color", "#d4d4d4");
		$("#link").css("background-color", "#FFF");
		$("#unlink").addClass('disabled');
		$("#link").removeClass('disabled');
		$(".unlink-group").prop('disabled', false);
	}

	
	
 });
 
 $(function () {
	 var docName=$('#DOC').find('option:selected').text();
	 var projAnswer=$('input[type="radio"]:checked').val();
	//if ($("#applClassCode").attr("value") == 'M') {  
	 if(projAnswer == 29){
		   hideGrantFields();
	 }
	 else if(docName == "DCEG" || docName == "CCR" ){
		 $("#DivisionOffice").show();
			$("#pBranch").show();
			$("#grantDiv").show();
			$("#title").show();
		$("#canAct").hide();
		$("#pdName").hide();
		$("#pStartDate").hide();
		$("#pEndDate").hide();			
	}
	 else{
		showGrantFields();
	 }
	
});
 
 $('.radio').on('change', function () {
	 var projAnswer=$('input[type="radio"]:checked').val();
	 var docName=$('#DOC').find('option:selected').text();
	 if(projAnswer == 29){
		    hideGrantFields();
			$("#grantsContractNum").val('');
			$("#applId").val('');
	 }
	 else if(docName == "DCEG" || docName == "CCR"){
		 $("#DivisionOffice").show();
			$("#pBranch").show();
			$("#grantDiv").show();
			$("#title").show();
		 $("#canAct").hide();
			$("#pdName").hide();
			$("#pStartDate").hide();
			$("#pEndDate").hide();	 
	 }
	 else{
		 showGrantFields();
	 }
 });
 
 $('#DOC').on('change', function () {
	   var optionSelected = $("option:selected", this);
	   var projAnswer=$('input[type="radio"]:checked').val();
	   var valueSelected = this.value;
	   if(valueSelected == 'DCEG' || valueSelected == 'CCR'){
		   $("#DivisionOffice").show();
			$("#pBranch").show();
			$("#grantDiv").show();
			$("#title").show();
		   $("#canAct").hide();
		   $("#pdName").hide();
			$("#pStartDate").hide();
			$("#pEndDate").hide();
	   }
	   else if(projAnswer == 29){
		   hideGrantFields();
	   }
	   else{
		  showGrantFields();
	   }
	   $.ajax({
		  	url: 'getProgBranchList.action',
		  	data : "valueSelected="+valueSelected,
			dataType : "json",
			async: true,
			success : function(result){
				var select=$('#programBranch');
				select.find('option').remove();		 
				$.each(result, function (i,item) {
                    $('#programBranch').append($('<option></option>').val(item.optionKey).text(item.optionValue));                         
                });
			}, 
			error: function(){}	
		});
	   
	});
 
 
 function  hideGrantFields() {
	 $("#DivisionOffice").hide();
		$("#pBranch").hide();
		$("#grantDiv").hide();
		$("#title").hide();
	    $("#canAct").hide();
		$("#pdName").hide();
		$("#pStartDate").hide();
		$("#pEndDate").hide();
 }
 
 function  showGrantFields() {
	 $("#DivisionOffice").show();
		$("#pBranch").show();
		$("#grantDiv").show();
		$("#title").show();
		if($("#grantsContractNum").val() == '') {
			$("#canAct").hide();
		} else {
			$("#canAct").show();
		}
		$("#pdName").show();
		$("#pStartDate").show();
		$("#pEndDate").show();
 }
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

//setting up default questions/grant boxes
$(document).ready(function() {
	$("#intramuralDiv").hide();
	$("#nonfundedLabel").hide();
	$("#researchType").show();
	$("#DivisionOffice").show();
	$("#DpBranch").show();
	$("#extramuralDiv").show();
	$("#extramuralHeading").show();
	$("#extramural_grantDiv").show();

});

//Research Type 

$("input[name='grantSelection']").click(function () {
    $('#extramuralDiv').css('display', ($(this).val() === 'Extramural') ? 'block':'none');
     $('#intramuralDiv').css('display', ($(this).val() === 'Intramural') ? 'block':'none');
      $('#extramuralDiv, #intramuralDiv').css('display', ($(this).val() === 'Both') ? 'block':'display');
});
// Non-funded NIH Grants

$("input[name='project.submissionReasonId']").click(function () {
    $('#extramuralDiv, #nonfundedLabel').css('display', ($(this).val() === '29') ? 'block':'none');
     $('#researchType, #extramuralHeading').css('display', ($(this).val() === '29') ? 'none':'none'); 
     $('#researchType, #extramuralHeading').css('display', ($(this).val() !== '29') ? 'block':'none'); 
});

//Search/Edit button
function openGrantsContractsSearchPage(searchType, grantContractIdPrefix) {
	
	$("#searchType").val(searchType);
	$("#grantContractIdPrefix").val(grantContractIdPrefix);
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
	var code= $("input[type='radio'].grantSelection:checked").val();
	var code= $("input[type='radio'].grants:checked").val();
	if(code == 1){
		 $("#addGrant").show();
		 var fieldCount = $(".otherWrapper1").length;
			for(var j = 0; j < fieldCount; j++) {
				var num=$("#grants_" + j + "_grantsContractNum").val();
			if(num ==''){
				$("#grants_"+ j + "_icon").removeClass("fa fa-pencil").addClass("fa fa-search");
				  $("#grants_" + j + "_button").attr("title", "Search");
				  $("#grants_" + j + "_grantsContractNum").attr("placeholder", "Click on Search Icon");
			}
			}
	}
	if(code == 'Extramural' || code == 'Both') {
		if($("#extramural_grantsContractNum").val()=='') {
			$("#extramural_grantDiv i").removeClass("fa fa-pencil").addClass("fa fa-search");
			$("#extramural_grantDiv button").attr("title", "Search");
			$("#extramural_grantsContractNum").attr("placeholder", "Click on Search Icon");
			$("#canAct").hide();
			$("#linkButton").hide();
		} else {
			$("#linkButton").show();
		}
	}
	
	if(code == 'Intramural' || code == 'Both') {
		if($("#intramural_grantsContractNum").val()=='') {
			$("#intramural_grantDiv i").removeClass("fa fa-pencil").addClass("fa fa-search");
			$("#intramural_grantDiv button").attr("title", "Search");
			$("#intramural_grantsContractNum").attr("placeholder", "Click on Search Icon");
			$("#canAct").hide();
			$("#linkButton").hide();
		} else {
			$("#linkButton").show();
		}
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



/* $(function () { 
	
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
 });*/
 
 
 $(function () {
	 //var docName=$('#DOC').find('option:selected').text();
	 var projAnswer= $("input[type='radio'].submissionReasonSelect:checked").val();
	 var code= $("input[type='radio'].grantSelection:checked").val();
	 if(projAnswer == 29){
		 hideGrantFields();
	 }
	 else if(code == 'M' ) {
		 //If selection is 'Intramural', hide pd first and last
		 //name, start and end date, and cancer activity field. 
		 //Make all other fields including the grant number field
		 //editable, and hide link/unlink button.
		 setupIntramuralFields();
	}
	 else{
		 //Show all fields. Make grant number field non-editable.
		 //If grant number is empty: do not show the link-unlink
		 //button, and cancer activity field, make all the grant 
		 //specific fields including grant number non-editable. 
		 //If grant number is not empty: If linked, make all grant
		 //specific fields un-editable. Else make them editable.
		showGrantFields();
	 }
});
 
 $('.grantSelection').on('change', function () {
	 var code= $("input[type='radio'].grantSelection:checked").val();
	 var projAnswer= $("input[type='radio'].submissionReasonSelect:checked").val();
	 
	 if($("#grantsContractNum").val()!='') {
	   var result = "Changing the Research type will clear the Extramural/Intramural/Contract#.<br /> Do you wish to continue?";
		bootbox.confirm(result, function(ans) {
			if (ans) {
				//Clear the grant number and grant specific fields. Remove
				//the cancer activity field. Remove link-unlink button				
				$("#grantsContractNum").val('');
				$(".unlink-group").val('');
				$("#grantDiv i").removeClass("fa fa-pencil").addClass("fa fa-search");
				$("#grantDiv button").attr("title", "Search");
		  		$("#grantsContractNum").attr("placeholder", "Click on Search Icon");
		  		$("#canAct").hide();
		  		
		  		//Setup the new applClassCode
		  		$("#applClassCode").val(code);
		  		
		  		//Perform remaining actions depending on
		  		//whether the new code is intramural
		  		//or others.
		  		if(projAnswer == 29){
		  			$(".unlink-group").prop('disabled', false);
				    hideGrantFields();
					$("#grantsContractNum").val('');
					$("#applId").val('');
					$("#dataLinkFlag").val('N');
		  		}
		  		else if(code == 'M' ) {
		  			setupIntramuralFields();
		  		} else {
		  			showGrantFields();
		  		}
			} else {
				var currentCode = $("#applClassCode").val();
				$("#general_form_grantSelection" + currentCode).prop("checked", true);
				return true;
			}
		});
	 } else {
	 
		 $("#applClassCode").val(code);
		 if(projAnswer == 29){
			 $(".unlink-group").prop('disabled', false);
			    hideGrantFields();
				$("#grantsContractNum").val('');
				$("#applId").val('');
				$("#dataLinkFlag").val('N');
		 }
		 else if(code == 'M' ) {
		 //If selection is 'Intramural', hide pd first and last
		 //name, start and end date, and cancer activity field. 
		 //Make all other fields including the grant number field
		 //editable, and hide link/unlink button.
		 setupIntramuralFields();
		} else {
		//Show all fields. Make the grant number field 
		 //non-editable. Since grant number is empty, do not
		 //show the link unlink button, and cancer activity
		 //field, amd make all the grant specific fields
		 //non-editable and blank.
		 showGrantFields();
		}
	 }
 });
 
 $('.submissionReasonSelect').on('change', function () {
	 var projAnswer= $("input[type='radio'].submissionReasonSelect:checked").val();
	 //var docName=$('#DOC').find('option:selected').text();
	 var code= $("input[type='radio'].grantSelection:checked").val();
	 if(projAnswer == 29){
		    $(".unlink-group").prop('disabled', false);
		    hideGrantFields();
			$("#grantsContractNum").val('');
			$("#applId").val('');
			$("#dataLinkFlag").val('N');
	 }
	 else if(code == 'M' ) {
		 //If selection is 'Intramural', hide pd first and last
		 //name, start and end date, and cancer activity field. 
		 //Make all other fields including the grant number field
		 //editable, and hide link/unlink button.
		 setupIntramuralFields();		 			
	 } else {
		//Show all fields. Make grant number field non-editable.
		 //If grant number is empty: do not show the link-unlink
		 //button and cancer activity field, and make all the  
		 //grant specific fields including grant number non-editable  
		 //and blank. If grant number is not empty: If linked, 
		 //make all grant specific fields un-editable. Else make 
		 //them editable.
		 showGrantFields();
	 }
 });
 
 $('#DOC').on('change', function () {
	   //var optionSelected = $("option:selected", this);
	   var valueSelected = this.value;	 
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
 
//If selection is 'Intramural', hide pd first and last
 //name, start and end date, and cancer activity field. 
 //Make all other fields including the grant number field
 //editable, and hide link/unlink button.
 function setupIntramuralFields() {
	 
	 	var projAnswer= $("input[type='radio'].submissionReasonSelect:checked").val();
	 
	 	$("#DivisionOffice").show();
		$("#pBranch").show();
		$("#grantLabel").html('Intramural #');
		$("#projectTitleLabel").html('Intramural Project Title');
		$("#grantDiv").show();
		$("#title").show();
	    $("#canAct").hide();
	    $("#piInstution").hide();
		$("#pdName").hide();
		$("#pStartDate").hide();
		$("#pEndDate").hide();
		$("#grantsContractNum").removeAttr("readonly");
		$(".unlink-group").prop('disabled', false);
		$("#linkButton").hide();
		$("#dataLinkFlag").val('N');
		
		 if(projAnswer == 29){
			 hideGrantFields();
		 }
 }

//Show all fields. Make grant number field non-editable.
 //If grant number is empty: do not show the link-unlink
 //button and cancer activity field, and make all the  
 //grant specific fields including grant number non-editable  
 //and blank. If grant number is not empty: If linked, 
 //make all grant specific fields un-editable. Else make 
 //them editable.
 function  showGrantFields() {
	    $("#DivisionOffice").show();
		$("#pBranch").show();
		if($("#applClassCode").val() == 'G') {
			$("#grantLabel").html('Grant #');
			$("#projectTitleLabel").html('Grant Project Title');
		} else {
			$("#grantLabel").html('Contract #');
			$("#projectTitleLabel").html('Contract Project Title');
		}
		$("#grantDiv").show();
		$("#grantsContractNum").attr('readonly', "true");
		$("#title").show();
		if($("#grantsContractNum").val() == '') {
			$("#linkButton").hide();
			$("#canAct").hide();
			$(".unlink-group").val('');
			$(".unlink-group").prop('disabled', true);
			
			//Since grant is empty, ensure search icon is showing
			$("#grantDiv i").removeClass("fa fa-pencil").addClass("fa fa-search");
			$("#grantDiv button").attr("title", "Search");
	  		$("#grantsContractNum").attr("placeholder", "Click on Search Icon");
		} else {
			$("#canAct").show();
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
		}
		$("#piInstution").show();
		$("#pdName").show();
		$("#pStartDate").show();
		$("#pEndDate").show();
 }
 
 $("#general_form").on('click', '#grantButton', function () {
		maxInputs = 10;
		fieldCount = $(".otherWrapper1").length;

		if (fieldCount < maxInputs) // max input box allowed
		{
			// If its the second row, add a trash bin next to the first row.
			if(fieldCount == 1) {
				
				$(".otherWrapper1").first().append('<i class="fa fa-trash fa-lg delete removeclass" title="Delete" aria-hidden="true" alt="Delete icon" style="font-size: 18px; padding-right: 3px; margin-left: 10px; cursor:pointer">'
						+ '</i></div>');
			}		
			
			// add input box
			$(".otherWrapper1").last().after('<div class="input-group otherWrapper1 ">'
			  + '<input type="text" name="associatedSecondaryGrants.grantContractNum" maxlength="271" class="form-control other" cssclass="form-control" id="grants_'
				+ fieldCount
				+'_grantsContractNum" placeholder="Click on Edit Icon"/>'
			 + '<div class="input-group-btn">'
	         + '<a href="#" onclick="openGrantsContractsSearchPage(\'all\', \'grants_'
	         + fieldCount
	        +'\')">'     
			 + '<button class="btn btn-default"  type="button" title="Edit" id="grants_'
			 + fieldCount
			 +'_button" style=" margin-left: -2px;">'
			+ '<i class="fa fa-pencil"  id="grants_'
			+ fieldCount
			+'_icon" aria-hidden="true"></i>'
			+ '</button></a></div>'
			+ '<i class="fa fa-trash fa-lg delete removeclass" title="Delete"  aria-hidden="true" alt="Delete icon" style="font-size: 18px; padding-right: 3px; margin-left: 13px; cursor:pointer">'
			+ '</i></div>');
			
			
			//if grantfield is empty
					//for(var j = 0; j < fieldCount; j++) {
						var num=$("#grants_" + fieldCount + "_grantsContractNum").val();
					if(num ==''){
						$("#grants_"+ fieldCount + "_icon").removeClass("fa fa-pencil").addClass("fa fa-search");
						  $("#grants_" + fieldCount + "_button").attr("title", "Search");
						  $("#grants_" + fieldCount + "_grantsContractNum").attr("placeholder", "Click on Search Icon");
					}
					//}
					
			// If max is reached, then remove the add button
			if ((fieldCount + 1) == maxInputs) {
				$("#anotherButtons").hide();
			}
		}
	});

	$("#general_form").on('click', '.removeclass', function () {//user click on remove text
		$(this).parent('div').remove(); //remove text box
		
		fieldCount = $(".otherWrapper1").length;
		// If this was the second row, remove the trash bin from the first row.
		if(fieldCount == 1) {
			$(".otherWrapper1").first().children("i").remove();
		}
		$("#anotherButtons").show();
	}); 
	
	$('.grants').on('change', function () {
		var code= $("input[type='radio'].grants:checked").val();
		if(code == 1){
			 $("#addGrant").show();
			 var fieldCount = $(".otherWrapper1").length;
				for(var j = 0; j < fieldCount; j++) {
					var num=$("#grants_" + j + "_grantsContractNum").val();
				if(num ==''){
					$("#grants_"+ j + "_icon").removeClass("fa fa-pencil").addClass("fa fa-search");
					  $("#grants_" + j + "_button").attr("title", "Search");
					  $("#grants_" + j + "_grantsContractNum").attr("placeholder", "Click on Search Icon");
				}
				}
		}
		else{
			$(".otherWrapper1").first().children("i").remove();
	    	$(".other").val('');
	    	$(".otherWrapper1").not(".otherWrapper1:first").remove();
	        $("#addGrant").hide();
		}
	});
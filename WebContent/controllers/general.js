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
		if($("#parentGrantSelection").val() == 'Both') {
			$(".grantSelection").attr('disabled', false);
		} else if($("#parentGrantSelection").val() == 'Extramural') {
			$("#general_form_grantSelectionExtramural").attr('disabled', false);
			$("#general_form_grantSelectionBoth").attr('disabled', false);
		} else if($("#parentGrantSelection").val() == 'Intramural') {
			$("#general_form_grantSelectionIntramural").attr('disabled', false);
			$("#general_form_grantSelectionBoth").attr('disabled', false);
		}
		$("input[type=radio].grants").attr('disabled', false);
	}
	 $("#extramural_grantsContractNum").blur(function() {
	        $('input:hidden[name="linkedGrantContractNum"]').val( $(this).val() );
	    });
	 $("#cancerActivity").blur(function() {
	        $('input:hidden[name="linkedCayCode"]').val( $(this).val() );
	    });
	 $("#extramural_projectTitle").blur(function() {
	        $('input:hidden[name="linkedProjectTitle"]').val( $(this).val() );
	    });
	 $("#pStartDate").blur(function() {
	        $('input:hidden[name="linkedProjectStartDate"]').val( $("#projectStartDate").val() );
	    });
	 $("#pEndDate").blur(function() {
	        $('input:hidden[name="linkedProjectEndDate"]').val( $("#projectEndDate").val() );
	    });
	
});

//setting up default questions/grant boxes
$(document).ready(function() {
	var projAnswer= $("input[type='radio'].submissionReasonSelect:checked").val();
	var code= $("input[type='radio'].grantSelection:checked").val();
	$("#researchType").val(code);
	
	if(projAnswer === '29') {
		$("#extramuralDiv").show();
		$(".extConditionalDisplay").hide();
		$(".nonNihFunded").prop('disabled', false);
	} else {
		$(".genConditionalDisplay").show();
		if(code == 'Extramural' || code === 'Both') {
			$("#extramuralDiv").show();
		}
		if(code == 'Intramural' || code === 'Both') {
			$("#intramuralDiv").show();
		}
		prepareGrantNumField(code);
		if($("#dataLinkFlag").val() === 'Y') {
			setLinkedDisplay();
		} else if($("#dataLinkFlag").val() == 'N') {
			setUnlinkedDisplay();
		}
	}
	
	//set the correct length for text areas
	showCharCount('#gComments', '#charNum3');	
});

//comments kep up function
$('#gComments').keyup(function() {
	showCharCount(this, '#charNum3');
});

//Research Type 

$("input[name='grantSelection']").click(function () {
	
	var result = "Changing the Research type will clear the Extramural/Intramural/Contract#.<br /> Do you wish to continue?";
	var code= $(this).val(); 
	var submissionId = $("input[type='radio'].submissionReasonSelect:checked").val();
	
	if(submissionId === '29') {
		$(".nonNihFunded").prop('disabled', false);
	}  else if($("#dataLinkFlag").val() === 'N') {
		$(".nonNihFunded").prop('disabled', false);
		setUnlinkedDisplay();
	} else if($("#dataLinkFlag").val() === 'Y'){
		$(".nonNihFunded").prop('disabled', true);
		setLinkedDisplay();
	}
	else {
		$(".nonNihFunded").prop('disabled', false);
		setUnlinkedDisplay();
	}
	//bootbox.confirm(result, function(ans) {
	//	if (ans) {
			$("#researchType").val(code);
			//$('.genConditionalDisplay').css('display', 'block');
			//$('#extramuralDiv').css('display', (code === 'Extramural' || code === 'Both') ? 'block':'none');
		    // $('#intramuralDiv').css('display', (code === 'Intramural' || code === 'Both') ? 'block':'none');
			$('.genConditionalDisplay').css('display', (submissionId != '29') ? 'block':'none');
		    $('#extramuralDiv').css('display', ( submissionId === '29' 
				|| (submissionId !== '29' && (code === 'Extramural' || code === 'Both'))) ? 'block':'none');
		    $('.extConditionalDisplay').css('display', (submissionId !== '29' && (code == 'Extramural' || code === 'Both')) ? 'block':'none');  
		    $('#intramuralDiv').css('display',  (submissionId !== '29' && (code === 'Intramural' || code === 'Both')) ? 'block':'none'); 
		
		    prepareGrantNumField(code);
		    
	/*	} else {
			//Restore previous value
			var currentCode = $("#researchType").val();
			$("#general_form_grantSelection" + currentCode).prop("checked", true);
			return true;
		};
	});*/
});

// Submission reason

$("input[name='project.submissionReasonId']").click(function () {
	var code= $("input[type='radio'].grantSelection:checked").val();
		
	if($(this).val() === '29') {
		$(".nonNihFunded").prop('disabled', false);
		code = 'Extramural';
		$("#researchType").val(code);
		$("#general_form_grantSelectionExtramural").prop("checked", true);
	} else if($("#dataLinkFlag").val() === 'N') {
		$(".nonNihFunded").prop('disabled', false);
		setUnlinkedDisplay();
	} else if($("#dataLinkFlag").val() === 'Y'){
		$(".nonNihFunded").prop('disabled', true);
		setLinkedDisplay();
	}
	else {
		$(".nonNihFunded").prop('disabled', false);
		setUnlinkedDisplay();
	}
	
	$('.genConditionalDisplay').css('display', ($(this).val() != '29') ? 'block':'none');
	$('#extramuralDiv').css('display', ( $(this).val() === '29' 
			|| ($(this).val() !== '29' && (code === 'Extramural' || code === 'Both'))) ? 'block':'none');
	$('.extConditionalDisplay').css('display', ($(this).val() !== '29' && (code == 'Extramural' || code === 'Both')) ? 'block':'none');  
	$('#intramuralDiv').css('display',  ($(this).val() !== '29' && (code === 'Intramural' || code === 'Both')) ? 'block':'none'); 
	prepareGrantNumField(code);
	
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
	var grantNumber = $("#" + grantContractIdPrefix + "_grantsContractNum").val();
	if(grantNumber !== null && $.trim(grantNumber) != '') {
		$("#grantSearch").val(grantNumber);
		$( "#searchGrants" ).click();	
	} else {
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
	
}

//Additional grants

$( document ).ready(function() {
	var code= $("input[type='radio'].grantSelection:checked").val();
	var grants= $("input[type='radio'].grants:checked").val();
	if(grants == 'Y'){
		 $("#addGrant").show();
		 var fieldCount = $(".otherWrapper1").length;
			for(var j = 0; j < fieldCount; j++) {
				var num=$("#grants_" + j + "_grantsContractNum").val();
			if(num ==''){
				$("#grants_" + j + "_grantsContractNum").attr("placeholder", "Click on Search Icon");
				$("#grants_" + j +"_div").find("i").removeClass("fa fa-pencil").addClass("fa fa-search");
				$("#grants_" + j +"_div").find("button").attr("title", "Search");

			}
			}
	}
});

//Toggle search/edit icon, show/hide link/unlink buttons, 
//and enable/disable grantsContractNum field depending on 
//whether grantsContractNum is empty or not
function prepareGrantNumField(code) {
	if(code == 'Extramural' || code == 'Both') {
		if($("#extramural_grantsContractNum").val()=='') {
			$("#extramural_grantDiv i").removeClass("fa fa-pencil").addClass("fa fa-search");
			$("#extramural_grantDiv button").attr("title", "Search");
			$("#extramural_grantsContractNum").attr("placeholder", "Click on Search Icon");
			$("#linkButton").hide();
			
		} else {
			$("#extramural_grantDiv i").removeClass("fa fa-search").addClass("fa fa-pencil");
			$("#extramural_grantDiv button").attr("title", "Edit");
			$("#extramural_grantsContractNum").attr("placeholder", "Click on Edit Icon");
			
			$("#linkButton").show();
		}
	}
	
	if(code == 'Intramural' || code == 'Both') {
		if($("#intramural_grantsContractNum").val()=='') {
			$("#intramural_grantDiv i").removeClass("fa fa-pencil").addClass("fa fa-search");
			$("#intramural_grantDiv button").attr("title", "Search");
			$("#intramural_grantsContractNum").attr("placeholder", "Click on Search Icon");
		} else {
			$("#intramural_grantDiv i").removeClass("fa fa-search").addClass("fa fa-pencil");
			$("#intramural_grantDiv button").attr("title", "Edit");
			$("#intramural_grantsContractNum").attr("placeholder", "Click on Edit Icon");
		}
	}
}	
	

function linkUnlinkGrants(elem) {
	if($(elem).attr("id") == 'link') {
		var result = "Linking will restore the auto-refresh of the Grant/Intramural/Contract data to the data source and delete any edits you migh have made.<br /> Do you wish to continue?";
		bootbox.confirm(result, function(ans) {
			if (ans) {
				$("#dataLinkFlag").val('Y');
				setLinkedDisplay();
				
				// Re-populate the data from DB.
				refreshGrantsContractsData();
			} 
			return true;
		});
	} else {
		var result = "Unlinking will remove the auto-refresh of the Intramural/Grant/Contract data from the data source that was used to populate it.<br /> Do you wish to continue?";
		bootbox.confirm(result, function(ans) {
			if (ans) {
				//getting the cancerActivity code
				$("#dataLinkFlag").val('N');
				$("#extramural_grantsContractNum").attr("readonly", false);
				setUnlinkedDisplay();
			}
			return true;
		});
	}
}

function setLinkedDisplay() {
	$("#link").css("background-color", "#d4d4d4");
	$("#unlink").css("background-color", "#FFF");
	$('#link').addClass('disabled');
	$("#unlink").removeClass('disabled');
	$(".unlink-group").prop('disabled', true);
	if($("#linkButton").is(":visible"))  {
		$(".disabled-group").each(function(){
			if($(this).val() != "") {
			$(this).prop('disabled', true);	
			} else {
				$(this).prop('disabled', false);
			}
		});
	}
}


function setUnlinkedDisplay() {
	$("#unlink").css("background-color", "#d4d4d4");
	$("#link").css("background-color", "#FFF");
	$("#unlink").addClass('disabled');
	$("#link").removeClass('disabled');
	$(".unlink-group").prop('disabled', false);
	if($("#linkButton").is(":visible"))  {
		$(".disabled-group").each(function(){
			if($(this).val() != "") {
			$(this).prop('disabled', true);	
			} else {
				$(this).prop('disabled', false);
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
	
	var parentId=$("#parentId").val();
	if (result == "") {
		if(parentId){
			$("input[type=radio]").attr('disabled', false);
			$('#DOC').attr('disabled', false);
			$('#programBranch').attr('disabled', false);
		}
		return true;
	}
	bootbox.confirm(result, function(ans) {
		if (ans) {
			if(parentId){
				$("input[type=radio]").attr('disabled', false);
				$('#DOC').attr('disabled', false);
				$('#programBranch').attr('disabled', false);
			}
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
	
	var parentId=$("#parentId").val();
	if (result == "") {
		if(parentId){
			$("input[type=radio]").attr('disabled', false);
			$('#DOC').attr('disabled', false);
			$('#programBranch').attr('disabled', false);
		}
		return true;
	}
	bootbox.confirm(result, function(ans) {
		if (ans) {
			if(parentId){
				$("input[type=radio]").attr('disabled', false);
				$('#DOC').attr('disabled', false);
				$('#programBranch').attr('disabled', false);
			}
			$('#general_form').attr('action', "saveGeneralInfoAndNext").submit();
			return true;
		} else {
			return true;
		}
	});
	return false;
}

/**
 * Invoked when the user clicks the icon on the
 * General Info page.
 */
function refreshGrantsContractsData(){
	
    $("#messages").empty();
    var errorResult = false;
    var errorMsg = "";
    
	if($("#extramural_grantsContractNum").val().length == 0) {
		
		errorResult = true;
		errorMsg = "Please enter the Grant# or Contract#.";
		
	} else if($("#extramural_grantsContractNum").val().length < 6) {
		
		errorResult = true;
		errorMsg = "Please enter a minimum of 6 characters for the Grant# or Contract#.";
	} else {
		
		var grantContractNum = $("#extramural_grantsContractNum").val();

		$.ajax({
	  	url: 'getGrantByGrantNum.action',
	  	data: {grantContractNum: grantContractNum},
	  	type: 'post',
	  	async:   false,
	  	success: function(json){
	  		
	  		if(json == null) {
	  			
	  			errorResult = true;
	  			errorMsg = "No data found to link to the given Grant# or Contract#.";
	  		
	  		} else if(json == "multiple") {
	  			
	  			errorResult = true;
				errorMsg = "Multiple matches found for the given Grant# or Contract#. Please click the Edit button to search and select the desired grant.";
		
	  		} else {
	  		
	  			if (json.grantContractNum !== "undefined") {
	  				$("#extramural_grantsContractNum").val(json.grantContractNum);	
	  			}
	  			if (json.projectTitle !== "undefined") {
	  				$("#extramural_projectTitle").val(json.projectTitle);
	  			}
	  			if (json.piFirstName !== "undefined") {
	  				$("#extramural_fnPI").val(json.piFirstName);
	  			}
	  			if (json.piLastName !== "undefined") {
	  				$("#extramural_lnPI").val(json.piLastName);
	  			}
	  			if (json.piEmailAddress !== "undefined") {
	  				$("#extramural_piEmail").val(json.piEmailAddress);
	  			}
	  			if (json.piInstitution !== "undefined") {
	  				$("#extramural_PIInstitute").val(json.piInstitution);
	  			}
	  			if (json.pdFirstName !== "undefined") {
	  				$("#fnPD").val(json.pdFirstName);
	  			}	
	  			if (json.pdLastName !== "undefined") {
	  				$("#lnPD").val(json.pdLastName);
	  			}
	  			if (json.projectPeriodStartDate !== "undefined" && json.projectPeriodStartDate != null && json.projectPeriodStartDate != "null") {
	  				var d = new Date(json.projectPeriodStartDate);
	  			$("#projectStartDate").val(d.getMonth()+1 +'/'+ d.getDate() +'/'+ d.getFullYear());
	  			} else {
	  				$("#projectStartDate").val("");
	  			}
	  			if (json.projectPeriodEndDate !== "undefined" && json.projectPeriodEndDate != null && json.projectPeriodEndDate != "null") {
	  				var d = new Date(json.projectPeriodEndDate);
	  				$("#projectEndDate").val(d.getMonth()+1 +'/'+ d.getDate() +'/'+ d.getFullYear());
	  			} else {
		  			$("#projectEndDate").val("");
	  			}
	  			if (json.cayCode !== "undefined") {
	  				$("#cancerActivity").val(json.cayCode);
	  			}
	  			if (json.applId !== "undefined") {
	  				$("#extramural_applId").val(json.applId);			
	  			}
	  		}
		}, 
		error: function(){}
			
		});
	}
	
	if(errorResult == true) {
		//Restore unlink status since we have an error
		$("#dataLinkFlag").val('N');
		$("#extramural_grantsContractNum").attr("readonly", false);
		setUnlinkedDisplay();
		$("#messages").prepend('<div class="container"><div class="col-md-12"><div class="alert alert-danger"><h3><i class="fa fa-exclamation-triangle fa-lg" aria-hidden="true"></i>&nbsp;Error Status</h3><ul class="errorMessage"><li><span>' + errorMsg + '</span></li></ul></div></div></div>');
		window.scrollTo(0,0);
	}
}
 
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
 
 
 
 $("#general_form").on('click', '#grantButton', function () {
		maxInputs = 10;
		fieldCount = $(".otherWrapper1").length;

		if (fieldCount < maxInputs) // max input box allowed
		{
			// If its the second row, add a trash bin next to the first row.
			if(fieldCount == 1) {
				
				$(".otherWrapper1").first().append('<i class="fa fa-trash fa-lg delete removeclass" title="Delete" aria-hidden="true" alt="Delete icon" style="font-size: 18px; padding-right: 3px; margin-left: 10px; vertical-align: 75%; cursor:pointer">'
						+ '</i></div>');
			}		
			
			// add input box
			$(".otherWrapper1").last().after('<div class="input-group otherWrapper1' 		
				    +'">'
				  + '<input type="text" name="associatedSecondaryGrants.grantContractNum" maxlength="271" class="form-control other2" cssclass="form-control" id="grants_'
					+ fieldCount
					+'_grantsContractNum" placeholder="Click on Edit Icon"/>'
				 + '<div class="input-group-btn grantAlign">'
		         + '<a href="#" id="grants_'
		         + fieldCount
		         +'_div" onclick="openGrantsContractsSearchPage(\'all\', \'grants_'
		         + fieldCount
		        +'\')">'     
				 + '<button class="btn btn-default"'
				+'type="button" title="Edit"' 
				 +' style=" margin-left: -2px;">'
				+ '<i class="fa fa-pencil" '
				+' aria-hidden="true"></i>'
				+ '</button></a></div>'
				+ '<i class="fa fa-trash fa-lg delete removeclass" title="Delete"  aria-hidden="true" alt="Delete icon" style="font-size: 18px; padding-right: 3px; margin-left: 13px; vertical-align: 75%; cursor:pointer">'
				+ '</i></div>');
			
			
			//if grantfield is empty
					//for(var j = 0; j < fieldCount; j++) {
						var num=$("#grants_" + fieldCount + "_grantsContractNum").val();
					if(num ==''){
						$("#grants_" + fieldCount + "_grantsContractNum").attr("placeholder", "Click on Search Icon");
						$("#grants_" + fieldCount +"_div").find("i").removeClass("fa fa-pencil").addClass("fa fa-search");
						$("#grants_" + fieldCount +"_div").find("button").attr("title", "Search");
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
		if(code == 'Y'){
			 $("#addGrant").show();
			 var fieldCount = $(".otherWrapper1").length;
				for(var j = 0; j < fieldCount; j++) {
					var num=$("#grants_" + j + "_grantsContractNum").val();
				if(num ==''){
					 $("#grants_" + j + "_grantsContractNum").attr("placeholder", "Click on Search Icon");
					 $("#grants_" + j +"_div").find("i").removeClass("fa fa-pencil").addClass("fa fa-search");
					 $("#grants_" + j +"_div").find("button").attr("title", "Search");
				}
				}
		}
		else{
			$(".otherWrapper1").first().children("i").remove();
	    	$(".other2").val('');
	    	$(".otherWrapper1").not(".otherWrapper1:first").remove();
	        $("#addGrant").hide();
		}
	});
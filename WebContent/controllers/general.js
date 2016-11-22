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
		}
	}
});

//setting up default questions/grant boxes
$(document).ready(function() {
	var projAnswer= $("input[type='radio'].submissionReasonSelect:checked").val();
	var code= $("input[type='radio'].grantSelection:checked").val();
	$("#researchType").val(code);
	
	if(projAnswer === '29') {
		$("#extramuralDiv").show();
		$(".extConditionalDisplay").hide();
	} else {
		$(".genConditionalDisplay").show();
		if(code == 'Extramural' || code === 'Both') {
			$("#extramuralDiv").show();
		}
		if(code == 'Intramural' || code === 'Both') {
			$("#intramuralDiv").show();
		}
	}
	
	prepareGrantNumField(code);
	if($("#dataLinkFlag").val() === 'Y') {
		setLinkedDisplay();
	} else if($("#dataLinkFlag").val() == 'N') {
		setUnlinkedDisplay();
	}

});

//Research Type 

$("input[name='grantSelection']").click(function () {
	
	var result = "Changing the Research type will clear the Extramural/Intramural/Contract#.<br /> Do you wish to continue?";
	var code= $(this).val(); 
	
	//bootbox.confirm(result, function(ans) {
	//	if (ans) {
			$("#researchType").val(code);
			
			$('.genConditionalDisplay').css('display', 'block');
			$('#extramuralDiv').css('display', (code === 'Extramural' || code === 'Both') ? 'block':'none');
		    $('#intramuralDiv').css('display', (code === 'Intramural' || code === 'Both') ? 'block':'none');
		    
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
		
	$('.genConditionalDisplay').css('display', ($(this).val() != '29') ? 'block':'none');
	$('#extramuralDiv').css('display', ( $(this).val() === '29' 
			|| ($(this).val() !== '29' && (code === 'Extramural' || code === 'Both'))) ? 'block':'none');
	$('.extConditionalDisplay').css('display', ($(this).val() !== '29' && (code == 'Extramural' || code === 'Both')) ? 'block':'none');  
	$('#intramuralDiv').css('display',  ($(this).val() !== '29' && (code === 'Intramural' || code === 'Both')) ? 'block':'none'); 
     
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

//Additional grants

$( document ).ready(function() {
	var code= $("input[type='radio'].grantSelection:checked").val();
	var grants= $("input[type='radio'].grants:checked").val();
	 if($("#projectId").val()) {
		  var $nonempty = $('.other').filter(function() {
		    return this.value != ''
		  });

		  if ($nonempty.length != 0) {
			  var value = 'Y';
			  $("input[name=grantsAdditional][value=" + value + "]").attr('checked', 'checked');
			 
		  }
		  else {
			  var value = 'N';
			  $("input[name=grantsAdditional][value=" + value + "]").attr('checked', 'checked');
		  }
	  }
	
	if(grants == 'Y'){
		 $("#addGrant").show();
		 var fieldCount = $(".otherWrapper1").length;
			for(var j = 0; j < fieldCount; j++) {
				var num=$("#grants_" + j + "_grantsContractNum").val();
			if(num ==''){
				$("#grants_" + j + "_grantsContractNum").attr("placeholder", "Click on Search Icon")
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
			$("#extramural_grantsContractNum").prop('readOnly', false);
			
		} else {
			$("#extramural_grantDiv i").removeClass("fa fa-search").addClass("fa fa-pencil");
			$("#extramural_grantDiv button").attr("title", "Edit");
			$("#extramural_grantsContractNum").attr("placeholder", "Click on Edit Icon");
			
			$("#linkButton").show();
			$("#extramural_grantsContractNum").prop('readOnly', true);
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
				// Re-populate the data from DB.
				refreshGrantsContractsData();
				setLinkedDisplay();
			} 
			return true;
		});
	} else {
		var result = "Unlinking will remove the auto-refresh of the Intramural/Grant/Contract data from the data source that was used to populate it.<br /> Do you wish to continue?";
		bootbox.confirm(result, function(ans) {
			if (ans) {
				//getting the cancerActivity code
				$("#dataLinkFlag").val('N');
				setUnlinkedDisplay();
				refreshCancerActivityCode();
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
}


function setUnlinkedDisplay() {
	$("#unlink").css("background-color", "#d4d4d4");
	$("#link").css("background-color", "#FFF");
	$("#unlink").addClass('disabled');
	$("#link").removeClass('disabled');
	$(".unlink-group").prop('disabled', false);
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
				
				$(".otherWrapper1").first().append('<i class="fa fa-trash fa-lg delete removeclass" title="Delete" aria-hidden="true" alt="Delete icon" style="font-size: 18px; padding-right: 3px; margin-left: 10px; cursor:pointer">'
						+ '</i></div>');
			}		
			
			// add input box
			$(".otherWrapper1").last().after('<div class="input-group otherWrapper1' 		
				    +'">'
				  + '<input type="text" name="associatedSecondaryGrants.grantContractNum" maxlength="271" class="form-control other" cssclass="form-control" id="grants_'
					+ fieldCount
					+'_grantsContractNum" placeholder="Click on Edit Icon"/>'
				 + '<div class="input-group-btn"'
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
				+ '<i class="fa fa-trash fa-lg delete removeclass" title="Delete"  aria-hidden="true" alt="Delete icon" style="font-size: 18px; padding-right: 3px; margin-left: 13px; cursor:pointer">'
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
	    	$(".other").val('');
	    	$(".otherWrapper1").not(".otherWrapper1:first").remove();
	        $("#addGrant").hide();
		}
	});
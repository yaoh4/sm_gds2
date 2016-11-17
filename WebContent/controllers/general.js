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

//setting up default questions/grant boxes




//Optional Submission – Non-NIH Funded
$("#general_form").on('click', '#submissionReason', function () {

if ($("#submissionReasonId29").prop("checked", true)) {
 $("#intramuralDiv").hide();
	$("#nonfundedLabel").show();
	$("#extramuralHeading").hide();
	$("#researchType").hide();
	$("#DivisionOffice").hide();
	$("#DpBranch").hide();
	$("#extramuralDiv").show();
	$("#extramuralHeading").hide();
	
}

else {
	$("#intramuralDiv").hide();
	$("#nonfundedLabel").hide();
	$("#researchType").show();
	$("#DivisionOffice").show();
	$("#DpBranch").show();
	$("#extramuralDiv").show();
	$("#extramuralHeading").show();
	$("#extramural_grantDiv").show();
	
	

}
	
});

//Research Type 
$("#general_form").on('click', '#researchType', function () {

if $("#general_form_grantSelectionBoth").prop("checked", true) {
$("#intramuralDiv").show();
	$("#nonfundedLabel").hide();
	$("#extramuralDiv").show();
	$("#extramuralHeading").show();
	$("#nonfundedLabel").hide();
}

if $("#general_form_grantSelectionIntramural").prop("checked", true) {
$("#intramuralDiv").show();
	$("#nonfundedLabel").hide();
	$("#extramuralDiv").hide();
	$("#extramuralHeading").hide();
	$("#nonfundedLabel").hide();
}

else {
	$("#intramuralDiv").hide();
	$("#nonfundedLabel").hide();
	$("#extramuralDiv").show();
	$("#extramuralHeading").show();
	$("#nonfundedLabel").hide();

}
	
});





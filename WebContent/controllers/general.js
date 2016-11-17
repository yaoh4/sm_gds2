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

$( document ).ready(function() {
	$("#intramuralDiv").hide();
	$("#nonfundedLabel").hide();
	$("#researchType").show();
	$("#DivisionOffice").show();
	$("#DpBranch").show();
	$("#extramuralDiv").show();
	$("#extramuralHeading").show();
	$("#extramural_grantDiv").show();

});


//Optional Submission – Non-NIH Funded
$("#general_form").on('click', '#submissionReason', function () {

if $("#submissionReasonId29").prop("checked", true) {
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




//Add additional Grants Function
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
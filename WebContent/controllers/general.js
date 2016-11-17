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


//Research Type 
$(function () {
        $("input[name='grantSelection']").click(function () {

if ($("#general_form_grantSelectionBoth").prop("checked", true)) {
    $("#intramuralDiv").show();
	$("#nonfundedLabel").hide();
	$("#extramuralDiv").show();
	$("#extramuralHeading").show();
	$("#nonfundedLabel").hide();
}

else if ($("#general_form_grantSelectionIntramural").prop("checked", true)) {
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

});        

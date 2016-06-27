//for manageDuls.jsp page -- 

$(document).ready(function () {
	
	//var myDulIdArray = "input[id$=" + dulIds + "]";
	//var dulIdArray = $(myDulIdArray).val();
	var dulIdArray = JSON.parse($("#dulIds").val());
	
	//Set all selected duls to checked
	jQuery.each(dulIdArray, function(index, value) {
		$("#" + value).prop('checked', true);
		if(value.startsWith("parentDul")) {
			
			//Show the checkbox set for this parent
			var divValue = value.replace("parentDul", "dulSet");
			$("#" + divValue).show();
		}
	});
		
});


$(".parentDulSet").change(function() {
	
	//First hide dul selections (checkboxes) from all parents (radio buttons) 
	$(".dulSetDiv").hide();
	//Uncheck all the selections
	$(".dulSet").prop('checked', false);
	
	//Then show dul selections only from the parent that was checked
	if($(this).is(":checked")) {
		var dulSetIds = $(this).attr('id').replace("parentDul", "dulSet");
		$("#" + dulSetIds).show();
	}
	
	
});
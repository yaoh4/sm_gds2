  
//for institutional.htm page


$(document).ready(function () {
	
	//var myDulIdArray = "input[id$=" + dulIds + "]";
	//var dulIdArray = $(myDulIdArray).val();
	var dulIdArray = JSON.parse($("#dulIds").val());
	
	//Set all selected duls to checked
	jQuery.each(dulIdArray, function(index, value) {
		
		//if value contains otherAddText, parse out the text
		//and set the value on the indicated text box
		if(value.includes("otherAddText")) {
			//The length of the text is available at the beginning of the value string
			//Format is <length of text><otherAddText-x-x-x><text>
			var textsize = value.substr(0, value.indexOf("otherAddText"));
			var elemId = value.substr(textsize.length, value.length - textsize - textsize.length);
			var text = value.substr(value.length - textsize);
			$("#" + elemId).val(text);
		} else {
			//this represents value of a checkbox or parent radio
			$("#" + value).prop('checked', true);
			if(value.startsWith("parentDul")) {
			
				//Show the checkbox set for this parent
				var divValue = value.replace("parentDul", "dulSet");
				$("#" + divValue).show();
			}
		}
		
		
	});
	
	//Do not show DULs and DUL verified flag if provisional
	value  = $("#finalprov option:selected").text();
	  if ( value == 'Provisional') { 
        $("#DULv, #DULinfo, #DULpanel").hide();
      }
      else
      {
        $("#DULv, #DULinfo, #DULpanel").show();
      }
	  

	  //Do not show any sections if no file uploaded.
	  if( !$.trim( $("#loadIcFileHistory").html() ).length ) {
		  $(".form-group").hide();
	  } else {
		  $(".form-group").show();
	  } 
		
});

$(".parentDulSet").change(function() {
	
	var dulTypeDivId = $(this).attr("name").replace("parentDul-", "dulType");
	
	//First hide dul selections (checkboxes) from all parents (radio buttons) 
	$("#" + dulTypeDivId).find(".dulSetDiv").hide();
	//Uncheck all the selections
	$("#" + dulTypeDivId).find(".dulSet").prop('checked', false);
	$("#" + dulTypeDivId).find(".input_other").val('');
	
	//Show the text fields associated with the parent if any
	
	
	//Then show dul selections only from the checkboxes of the parent that was checked
	if($(this).is(":checked")) {
		var dulSetIds = $(this).attr('id').replace("parentDul", "dulSet");
		$("#" + dulSetIds).show();
		//Show the additional text associated with the parent radio if present
		//var parentOtherId = $(this).attr('id').replace("parentDul", "otherAdd");
		//$("#" + parentOtherId).show();
	}
	
	
});

$("#btnAddDUL").click(function() {
	//Get the index of the parent study
	var studiesIdx = $(this).parent().attr("id").replace("addDulSetButton_", "");
	addDulSet(studiesIdx);
});

function addDulSet(studiesIdx)  {

	//Clone the ist existing DUL
	var newDulTypeDiv = $( "#dulType0-0" ).clone(true);
	var newDulTypeIndex = $("#studySection" + studiesIdx).find(".entrylist").length;
	
	 // right now you can only add 10 DULs. change '10' below to the max number of times the form can be duplicated
    if (newDulTypeIndex == 10) {
    	$('#btnAddDUL').attr('disabled', true).prop('value', "You've reached the limit");
    	return;
	};

	//Set the correct ids and names
	newDulTypeDiv.attr("id", "dulType" + studiesIdx + "-" + newDulTypeIndex);
	
	newDulTypeDiv.find("#dulSetId0-0").attr("id", "dulSetId" + studiesIdx + "-" + newDulTypeIndex);
	var dulSetIdElemName = "instCertification.studies[" + studiesIdx + "].studiesDulSets[" + newDulTypeIndex + "].id";
	newDulTypeDiv.find("#dulSetId" + studiesIdx + "-" + newDulTypeIndex).attr("name", dulSetIdElemName);
	newDulTypeDiv.find("#dulSetId" + studiesIdx + "-" + newDulTypeIndex).attr("value", "");
	
	var parentDulSetArray = newDulTypeDiv.find(".parentDulSet");
	jQuery.each(parentDulSetArray, function(index, val) {
		var parentElemName = "parentDul" + "-" + studiesIdx + "-" + newDulTypeIndex;
		var parentElemId = $(this).attr('id').replace("parentDul0-0", "parentDul" + studiesIdx + "-" + newDulTypeIndex);
		$(this).attr({id: parentElemId, name: parentElemName});
	});
	
	var dulSetDivArray = newDulTypeDiv.find(".dulSetDiv");
	jQuery.each(dulSetDivArray, function(index, val) {
		var dulDivElemId = $(this).attr('id').replace("dulSet0-0", "dulSet" + studiesIdx + "-" + newDulTypeIndex);
		$(this).attr("id", dulDivElemId);
	});
	
	var dulSetArray = newDulTypeDiv.find(".dulSet");
	jQuery.each(dulSetArray, function(index, val) {
		var dulElemId = $(this).attr('id').replace("dul0-0", "dul" + studiesIdx + "-" + newDulTypeIndex);
		var dulElemName = $(this).attr('name').replace("dul-0-0", "dul" + "-" + studiesIdx + "-" + newDulTypeIndex);
		$(this).attr({id: dulElemId, name: dulElemName});
	});
	
	
	//Replace ID of the parent radio button additional text for 'disease specific' and 'other' option
	var newParentAddTextId = "#parentAddText" + studiesIdx + "-" + newDulTypeIndex + "-13";
	var newParentAddTextName = "#parentAddText-" + studiesIdx + "-" + newDulTypeIndex + "-13";
	newDulTypeDiv.find("#parentAddText0-0-13").attr("id", newParentAddTextId);
	newDulTypeDiv.find(newParentAddTextId).attr("name", newParentAddTextName);
	
	newParentAddTextId = "parentAddText" + studiesIdx + "-" + newDulTypeIndex + "-21";
	newParentAddTextName = "parentAddText-" + studiesIdx + "-" + newDulTypeIndex + "-21";
	newDulTypeDiv.find("#parentAddText0-0-21").attr("id", newParentAddTextId);
	newDulTypeDiv.find(newParentAddTextId).attr("name", newParentAddTextName);
	
	newDulTypeDiv.find("#entry_dulSet_0_0").attr("id", "entry_dulSet_" + studiesIdx + "_" + newDulTypeIndex);
	
	
	if(newDulTypeIndex > 0) {
		newDulTypeDiv.find("#entry_dulSet_" + studiesIdx + "_" + newDulTypeIndex).append('<a href="#" onclick="deleteDulSet(' + studiesIdx + ',' + newDulTypeIndex + ')" class="deleteIcon" style="float: right;"><i class="fa fa-trash" aria-hidden="true"></i></a>');
	}
	//Hide the checkboxes
	newDulTypeDiv.find(".dulSetDiv").hide();
	
	//Uncheck all the selections
	newDulTypeDiv.find(".parentDulSet").prop('checked', false);
	newDulTypeDiv.find(".dulSet").prop('checked', false);
	
	//Append the new DUL Type
	newDulTypeDiv.appendTo("#cloneDULInput" + studiesIdx);

	
	//$.getJSON("addDul.action", {
	//}, function(response) {
		//alert(response);
	//});		
};


function deleteDulSet(studiesIdx, dulSetIdx) {
	$("#dulType" + studiesIdx + "-" + dulSetIdx).remove();
};


function addStudy() {
	
	var newStudySectionDiv = $( "#studySection0" ).clone(true).val('');
	var newStudySectionIndex = $(".studyList").length;
	
	//Set the correct ids and names
	newStudySectionDiv.attr("id", "studySection" + newStudySectionIndex);
	
	newStudySectionDiv.find("#studyId0").attr("id", "studyId" + newStudySectionIndex);
	newStudySectionDiv.find("#studyId" + newStudySectionIndex).attr("name", "instCertification.studies[" + newStudySectionIndex + "].id");
	newStudySectionDiv.find("#studyId" + newStudySectionIndex).removeAttr("value");
		
	newStudySectionDiv.find("#studyName0").attr("id", "studyName" + newStudySectionIndex);
	newStudySectionDiv.find("#studyName" + newStudySectionIndex).attr("name", "instCertification.studies[" + newStudySectionIndex + "].studyName");
	newStudySectionDiv.find("#studyName" + newStudySectionIndex).removeAttr("value");
	
	newStudySectionDiv.find("#institution0").attr("id", "institution" + newStudySectionIndex);
	newStudySectionDiv.find("#institution" + newStudySectionIndex).attr("name", "instCertification.studies[" + newStudySectionIndex + "].institution");
	newStudySectionDiv.find("#institution" + newStudySectionIndex).removeAttr("value");
	
	newStudySectionDiv.find("#dulVerificationId0").attr("id", "dulVerificationId" + newStudySectionIndex);
	newStudySectionDiv.find("#dulVerificationId" + newStudySectionIndex).attr("name", "instCertification.studies[" + newStudySectionIndex + "].dulVerificationId");
	newStudySectionDiv.find("#dulVerificationId" + newStudySectionIndex).val(-1);
	
	newStudySectionDiv.find("#comments0").attr("id", "comments" + newStudySectionIndex);
	newStudySectionDiv.find("#comments" + newStudySectionIndex).attr("name", "instCertification.studies[" + newStudySectionIndex + "].comments");
	newStudySectionDiv.find("#comments" + newStudySectionIndex).val("").removeAttr("value");
	
	newStudySectionDiv.find("#entry_study_0").attr("id", "entry_study_" + newStudySectionIndex);
	
	newStudySectionDiv.find("#cloneDULInput0").attr("id", "cloneDULInput" + newStudySectionIndex);
	
	newStudySectionDiv.find("#addDulSetButton_0").attr("id", "addDulSetButton_" + newStudySectionIndex);
	
	newStudySectionDiv.find("#entry_study_" + newStudySectionIndex).append('<a href="#" onclick="deleteStudy(' + newStudySectionIndex + ')" class="deleteIcon" style="float: right;"><i class="fa fa-trash" aria-hidden="true"></i></a>');
	
	
	//Empty the class cloneDULInput that contains the DUL Types cloned
	newStudySectionDiv.find("#cloneDULInput" + newStudySectionIndex).empty();
	
	//Append the new Study to cloneStudyInput
	newStudySectionDiv.appendTo(".cloneStudyInput");
	
	//Add a new DUL Type to cloneDULInput
	addDulSet(newStudySectionIndex);
	
	//newStudySectionDiv.html('<i class="fa fa-minus-square" aria-hidden="true"></i>&nbsp;Study<a href="javascript:void" class="deleteIcon" style="float: right;"><i class="fa fa-trash" aria-hidden="true"></i></a>');
    
	//newStudySectionDiv.find(".content").show();
	//$("#entry_study_" + newStudySectionIndex).append('<a href="#" onclick="deleteStudy(${studiesIdx})" class="deleteIcon" style="float: right;"><i class="fa fa-trash" aria-hidden="true"></i></a>');
	
	//alert($("#entry_study_" + newStudySectionIndex).html());
	
	//$.getJSON("addStudy.action", {
	//}, function(response) {
	//		alert(response);
	//});	
};



function deleteStudy(studiesIdx) {
	$("#studySection" + studiesIdx).remove();
};



$(document).ready(function () {

	// IC file upload Ajax
	$("#institutional_form").on('click', '#icUpload', function () {

		var result = "";
		var $form, fd;
	    $form = $("#institutional_form");
	    fd = new FormData($form[0]);
	    
		$.ajax({
		  	url: 'uploadInstCertification.action',
		  	type: 'post',
		  	processData: false,
		    contentType: false,
		    data: fd,
		  	async:   false,
		  	success: function(msg){
				result = $.trim(msg);
			}, 
			error: function(){}	
		});
		if(result.startsWith("<p")) {
			$('div#icDiv').html(result);
			$(".form-group").show();
		}
		else {
			bootbox.alert(result, function() {
	  			return true;
			});
		}
	});

	});

	function removeDocument(docId, projectId)
	{
		var result = "";
		bootbox.confirm("Are you sure you want to delete this file?", function(ans) {
			  if (ans) {
				  $.ajax({
						url: "deleteInstCertificationFile.action",
						type: "post",
						data: {docId: docId, projectId: projectId},
						async:   false,
						success: function(msg){
							result = $.trim(msg);
						}, 
						error: function(){}		
					});
					if(result.startsWith("<p")) {
						$('div.loadFileHistory').html(result);
					}
					else {
						openFileModal(result);
					}
			  }
		}); 
	}



  $(function() { 
var emptyStudy = ""

//toggle for Provisional/Final Dropdown box

    $('#finalprov').on('change', function() {
    	value  = $("#finalprov option:selected").text();
      if ( value == 'Provisional') { 
    	  $("#DULv, #DULinfo, #DULpanel").hide();
    	  
    		//remove all dulTypes except one
    		$(".dulTypes").not("#dulType0-0").remove();
    		
    		//Hide dul selections (checkboxes) from all parents (radio buttons) 
    		$(".dulSetDiv").hide();
    		
    		//Uncheck all the selections
    		$(".dulSet").prop('checked', false);
    		$(".input_other").val('');
    		$(".DULvSelect").val(-1);
      }
      else
      {
        $("#DULv, #DULinfo, #DULpanel").show();
      }
    });
  });

$( document ).ready(function() {
  emptyStudy = $("#entry1").clone();
});



//funtion for accordion study panels 



$(document).on('click', '.header', function () {

    $(this).next(".content").slideToggle(400);
    $(this).find('.fa').toggleClass('fa-plus-square fa-minus-square');
    $(this).show();
    $("#collapseOne").focus();

});


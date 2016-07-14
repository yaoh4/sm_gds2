  
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
			if(value.indexOf("parentDul") == 0) {
			
				//Show the checkbox set for this parent
				var divValue = value.replace("parentDul", "dulSet");
				$("#" + divValue).show();
			}
		}
		
		
	});
	
	
	//TBD - Needs to be fixed
	//Do not show DULs and DUL verified flag if provisional
	value  = $("#finalprov1 option:selected").text();
	  if ( value == 'Provisional') { 
		//Hide DUL verification dropdown
    	  $(".DULvSelect").hide();
    	  
    	  //Hide the Add DUL message
    	  $(".DULinfo").hide();
    	  
    	  //Hide the DUL container
    	  //$(".cloneDULinput").hide();
    	  
    	  //Hide addDul buttons from all studies
  	      $(".addDulSetButton").hide();
    	  
    	  //remove all dulTypes except the first one
    	  //$(".dulTypes").not("#dulType0-0").remove();
    	    
		 	    
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
	}
	
	
});

$("#btnAddDUL").click(function() {
	//Get the index of the parent study
	var studiesIdx = $(this).parent().attr("id").replace("addDulSetButton_", "");
	addDulSet(studiesIdx);
});

function addDulSet(studiesIdx)  {

	
	var dulItems = $("#studySection" + studiesIdx).find(".dulTypes").length;
	
	 // Right now you can only add 10 DULs. change '10' below to the max number of DULSets that
	//can be attached to a study
    if (dulItems == 10) {
    	$('#btnAddDUL').attr('disabled', true).prop('value', "You've reached the limit");
    	return;
	};

	//Get the first available dulSet to select for cloning. It need not
	//be the first index because deletions may have occurred from top 
	//because we do not know which ones may have been deleted
	cloneStudySectionIndex = 0;
	cloneDulTypeIndex = 0;
	for(var studySectionIndex=0; studySectionIndex < 500; studySectionIndex++) {
		for(var dulTypeIndex=0; dulTypeIndex < 10; dulTypeIndex++) {
			if($("#dulType" + studySectionIndex + "-" + dulTypeIndex).length > 0) {
				cloneDulTypeIndex = dulTypeIndex;
				cloneStudySectionIndex = studySectionIndex;
				break;
			}
		}
	}
	
	//Get the first available slot to use for the new dulSet. It need not be 
	//last index + 1 because deletions may have occurred from the top or middle
	newDulTypeIndex = 0;
	for(var dulTypeIndex=0; dulTypeIndex < 10; dulTypeIndex++) {
		if($("#dulType" + studiesIdx + "-" + dulTypeIndex).length == 0) {
			newDulTypeIndex = dulTypeIndex;
			break;
		}
	}
	//Perform the cloning
	var newDulTypeDiv = $( "#dulType" + cloneStudySectionIndex + "-" + cloneDulTypeIndex).clone(true);
	
	//Set the correct ids and names
	newDulTypeDiv.attr("id", "dulType" + studiesIdx + "-" + newDulTypeIndex);
	
	newDulTypeDiv.find("#dulSetId" + cloneStudySectionIndex + "-" + cloneDulTypeIndex).attr("id", "dulSetId" + studiesIdx + "-" + newDulTypeIndex);
	var dulSetIdElemName = "instCertification.studies[" + studiesIdx + "].studiesDulSets[" + newDulTypeIndex + "].id";
	newDulTypeDiv.find("#dulSetId" + studiesIdx + "-" + newDulTypeIndex).attr("name", dulSetIdElemName);
	newDulTypeDiv.find("#dulSetId" + studiesIdx + "-" + newDulTypeIndex).attr("value", "");
	
	//Set correct id and name for created by
	newDulTypeDiv.find("#dulSetCreatedBy" + cloneStudySectionIndex + "-" + cloneDulTypeIndex).attr("id", "dulSetCreatedBy" + studiesIdx + "-" + newDulTypeIndex);
	var dulSetCreatedByElemName = "instCertification.studies[" + studiesIdx + "].studiesDulSets[" + newDulTypeIndex + "].createdBy";
	newDulTypeDiv.find("#dulSetCreatedBy" + studiesIdx + "-" + newDulTypeIndex).attr("name", dulSetCreatedByElemName);
	newDulTypeDiv.find("#dulSetCreatedBy" + studiesIdx + "-" + newDulTypeIndex).attr("value", "");
	
	//Set correct id and name for parent radio button
	var parentDulSetArray = newDulTypeDiv.find(".parentDulSet");
	jQuery.each(parentDulSetArray, function(index, val) {
		var parentElemName = "parentDul" + "-" + studiesIdx + "-" + newDulTypeIndex;
		var parentElemId = $(this).attr('id').replace("parentDul" + cloneStudySectionIndex + "-" + cloneDulTypeIndex, "parentDul" + studiesIdx + "-" + newDulTypeIndex);
		$(this).attr({id: parentElemId, name: parentElemName});
	});
	
	var dulSetDivArray = newDulTypeDiv.find(".dulSetDiv");
	jQuery.each(dulSetDivArray, function(index, val) {
		var dulDivElemId = $(this).attr('id').replace("dulSet" + cloneStudySectionIndex + "-" + cloneDulTypeIndex, "dulSet" + studiesIdx + "-" + newDulTypeIndex);
		$(this).attr("id", dulDivElemId);
	});
	
	//Set correct id and name for dul checkboxes
	var dulSetArray = newDulTypeDiv.find(".dulSet");
	jQuery.each(dulSetArray, function(index, val) {
		var dulElemId = $(this).attr('id').replace("dul" + cloneStudySectionIndex + "-" + cloneDulTypeIndex, "dul" + studiesIdx + "-" + newDulTypeIndex);
		var dulElemName = $(this).attr('name').replace("dul" + cloneStudySectionIndex + "-" + cloneDulTypeIndex, "dul" + "-" + studiesIdx + "-" + newDulTypeIndex);
		$(this).attr({id: dulElemId, name: dulElemName});
	});
	
	
	//Replace ID of the parent radio button additional text for 'disease specific' and 'other' option
	var newParentAddTextId = "otherAddText" + studiesIdx + "-" + newDulTypeIndex + "-13";
	var newParentAddTextName = "otherAddText-" + studiesIdx + "-" + newDulTypeIndex + "-13";
	newDulTypeDiv.find("#otherAddText" + cloneStudySectionIndex + "-" + cloneDulTypeIndex + "-13").attr("id", newParentAddTextId);
	newDulTypeDiv.find("#" + newParentAddTextId).attr("name", newParentAddTextName);
	
	newParentAddTextId = "otherAddText" + studiesIdx + "-" + newDulTypeIndex + "-21";
	newParentAddTextName = "otherAddText-" + studiesIdx + "-" + newDulTypeIndex + "-21";
	newDulTypeDiv.find("#otherAddText" + cloneStudySectionIndex + "-" + cloneDulTypeIndex + "-21").attr("id", newParentAddTextId);
	newDulTypeDiv.find("#" + newParentAddTextId).attr("name", newParentAddTextName);
	
	newDulTypeDiv.find("#entry_dulSet_" + studiesIdx + "_" + cloneDulTypeIndex).attr("id", "entry_dulSet_" + studiesIdx + "_" + newDulTypeIndex);
	
	
	//Hide the checkboxes
	newDulTypeDiv.find(".dulSetDiv").hide();
	
	//Uncheck all the selections
	newDulTypeDiv.find(".parentDulSet").prop('checked', false);
	newDulTypeDiv.find(".dulSet").prop('checked', false);
	
	//Append the new DUL Type
	newDulTypeDiv.appendTo("#cloneDULInput" + studiesIdx);
	
	//If number of DULSets for this study is greater than 1, add trash can to all DULs
	//Else remove trash can from the lone one.
	var numItems = $("#cloneDULInput" + studiesIdx).find('.dulTypes').length;
	var dulSetArray = $("#cloneDULInput" + studiesIdx).find('.dulTypes');
	if(numItems > 1) {
		jQuery.each(dulSetArray, function(index, val) {
			if($(this).find(".deleteIcon").length == 0) {
				//Get the dulIndex of this element. 
				var elemIndex = $(this).attr("id").slice($(this).attr("id").lastIndexOf("-") + 1);
				$(this).find(".dulHeading").prepend('<a href="#" onclick="deleteDulSet(' + studiesIdx + ',' + elemIndex + ')" class="deleteIcon" style="float: right;"><i class="fa fa-trash" aria-hidden="true"></i></a>');
			}
		});
	} else {
		dulSetArray.find(".deleteIcon").remove();
	}
	
};


function deleteDulSet(studiesIdx, dulSetIdx) {
	$("#dulType" + studiesIdx + "-" + dulSetIdx).remove();
	
	var numItems = $("#studySection" + studiesIdx).find('.dulTypes').length;
	if(numItems == 1) {
		//Use dulTypes class and not id to locate the element because we dont know
		//which is the DULSet that is left. 
		$("#studySection" + studiesIdx).find(".dulTypes").find(".deleteIcon").remove();
	}
	
};



function addStudy() {
	
	
	var studyItems = $(".studySections").length;
	
	
	 // Right now you can  add 500 studies. change '500' below to the max number of studies
	//that are permitted
    if (studyItems == 500) {
    	$('#btnAddDUL').attr('disabled', true).prop('value', "You've reached the limit");
    	return;
	};

	//Get the first available study to select for cloning. It need not
	//be the first index because deletions may have occurred from top 
	//because we do not know which ones may have been deleted
	cloneDulTypeIndex = 0;
	for(var studySectionIndex=0; studySectionIndex < 500; i++) {
		if($("#studySection" + studySectionIndex).length > 0) {
			cloneStudySectionIndex = studySectionIndex;
			break;
		}
	}
	
	//Get the first available slot to use for the new study. It need not be 
	//last index + 1 because deletions may have occurred from the top or middle
	newDulTypeIndex = 0;
	for(var studySectionIndex=0; studySectionIndex < 10; studySectionIndex++) {
		if($("#studySection" + studySectionIndex).length == 0) {
			newStudySectionIndex = studySectionIndex;
			break;
		}
	}
	
	//Perform the cloning
	var newStudySectionDiv = $( "#studySection" + cloneStudySectionIndex).clone(true).val('');
	
	//Set the correct ids and names
	newStudySectionDiv.attr("id", "studySection" + newStudySectionIndex);
	
	newStudySectionDiv.find("#studyId" + cloneStudySectionIndex).attr("id", "studyId" + newStudySectionIndex);
	newStudySectionDiv.find("#studyId" + newStudySectionIndex).attr("name", "instCertification.studies[" + newStudySectionIndex + "].id");
	newStudySectionDiv.find("#studyId" + newStudySectionIndex).removeAttr("value");
		
	newStudySectionDiv.find("#studyName" + cloneStudySectionIndex).attr("id", "studyName" + newStudySectionIndex);
	newStudySectionDiv.find("#studyName" + newStudySectionIndex).attr("name", "instCertification.studies[" + newStudySectionIndex + "].studyName");
	newStudySectionDiv.find("#studyName" + newStudySectionIndex).removeAttr("value");
	
	newStudySectionDiv.find("#institution" + cloneStudySectionIndex).attr("id", "institution" + newStudySectionIndex);
	newStudySectionDiv.find("#institution" + newStudySectionIndex).attr("name", "instCertification.studies[" + newStudySectionIndex + "].institution");
	newStudySectionDiv.find("#institution" + newStudySectionIndex).removeAttr("value");
	
	newStudySectionDiv.find("#dulVerificationId" + cloneStudySectionIndex).attr("id", "dulVerificationId" + newStudySectionIndex);
	newStudySectionDiv.find("#dulVerificationId" + newStudySectionIndex).attr("name", "instCertification.studies[" + newStudySectionIndex + "].dulVerificationId");
	newStudySectionDiv.find("#dulVerificationId" + newStudySectionIndex).val(-1);
	
	newStudySectionDiv.find("#comments" + cloneStudySectionIndex).attr("id", "comments" + newStudySectionIndex);
	newStudySectionDiv.find("#comments" + newStudySectionIndex).attr("name", "instCertification.studies[" + newStudySectionIndex + "].comments");
	newStudySectionDiv.find("#comments" + newStudySectionIndex).val("").removeAttr("value");
	
	newStudySectionDiv.find("#entry_study_" + cloneStudySectionIndex).attr("id", "entry_study_" + newStudySectionIndex);
	
	newStudySectionDiv.find("#cloneDULInput" + cloneStudySectionIndex).attr("id", "cloneDULInput" + newStudySectionIndex);
	
	newStudySectionDiv.find("#addDulSetButton_" + cloneStudySectionIndex).attr("id", "addDulSetButton_" + newStudySectionIndex);
	
	//Empty the class cloneDULInput that contains the DUL Types that also got cloned
	newStudySectionDiv.find("#cloneDULInput" + newStudySectionIndex).empty();
	
	//Append the new Study to cloneStudyInput
	newStudySectionDiv.appendTo(".cloneStudyInput");
	
	
	//Add the trash icon to all studies if there is more than one study, 
	//else remove trash can from the lone one
	var numItems = $(".studySections").length;
	var studySetArray = $(".studySections");
	if(numItems > 1) {		
		jQuery.each(studySetArray, function(index, val) {
			if($(this).find(".studyHeading").find(".deleteIcon").length == 0) {
				var elemIndex = $(this).attr("id").replace("studySection", "");
				$(this).find(".studyHeading").prepend('<a href="#" onclick="deleteStudy(' + elemIndex + ')" class="deleteIcon" style="float: right;"><i class="fa fa-trash" aria-hidden="true"></i></a>');
			}
		});
	} else {
		studySetArray.find(".studyHeading").find(".deleteIcon").remove();
	}
		
	//Add a new DUL Type to this study
	addDulSet(newStudySectionIndex);	
};



function deleteStudy(studiesIdx) {
	$("#studySection" + studiesIdx).remove();
	var numItems = $(".studySections").length;
	if(numItems == 1) {
		$(".studySections").find(".studyHeading").find(".deleteIcon").remove();
	}
};

//TBD - needs to be fixed
$(function() { 
	var emptyStudy = ""

	//toggle for Provisional/Final Dropdown box
		//User clicked provisional or final
		//When they click provisonal remove all the DUL Sets, hide all DUL info and all buttons
		//When they click final add one DUL set to every study, show all DULL info and add button

	    $('#finalprov1').on('change', function() {
	    	value  = $("#finalprov option:selected").text();
	      if ( value == 'Provisional') { 
	    	  
	    	  //Hide DUL verification dropdown
	    	  $(".DULvSelect").hide();
	    	  $(".DULvSelect").val(-1);
	    	  
	    	  //Hide the Add DUL message
	    	  $(".DULinfo").hide();
	    	  
	    	  //Hide the DUL container
	    	  $(".cloneDULinput").hide();
	    	  
	    	//Hide addDul buttons from all studies
	  	    $(".addDulSetButton").hide();
	    	  
	    	   //remove all dulTypes
	    		$(".dulTypes").not("#dulType0-0").remove();
	    		
	    		//Hide dul selections (checkboxes) from all parents (radio buttons) 
	    		//$(".dulSetDiv").hide();
	    		
	    		//Uncheck all the selections
	    		//$(".dulSet").prop('checked', false);
	    		//$(".input_other").val('');
	    		//$(".DULvSelect").val(-1);
	      }
	      else
	      {
	        //$("#DULv, #DULinfo, #DULpanel").show();
	        
	        //Show the DUL verification
	        $(".DULvSelect").show();
	        
	        //Show the add DUL message
	        $(".DULinfo").show();
	        
	        //Show the DUL container with one set
	       // $("#dulType0-0").show();
	        //$("#cloneDULInput0").show();
	        
	        //Show the add buttons
	        $(".addDulSetButton").show();
	      }
	    });
	  });

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
		if(result.indexOf("<p") == 0) {
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


  
//for institutional.htm page

$(document).ready(function () {
		$(".checkboxSelected").on("change", function(){
			$("#message").attr("style", "display:none");
		    var studies = [];
		    $('.checkboxSelected:checked').each(function(){        
		        var studyTypes = $(this).parent().parent().next().text();
		        studies.push(studyTypes);
		    });
		    $("#log").html(studies.join("<br/> "));


		});
});

$(document).ready(function () {
	

	//var myDulIdArray = "input[id$=" + dulIds + "]";
	//var dulIdArray = $(myDulIdArray).val();
	var dulIdArray = JSON.parse($("#dulIds").val());
	
	//Set all selected duls to checked
	jQuery.each(dulIdArray, function(index, value) {
		
		//if value contains otherAddText, parse out the text
		//and set the value on the indicated text box
		//if(value.includes("comments")) {
		if(value.indexOf("comments") > -1) {
			var textsize = value.substr(0, value.indexOf("comments"));
			var elemId = value.substr(textsize.length, value.length - textsize - textsize.length);
			var text = value.substr(value.length - textsize);
			$("#" + elemId).val(text);
			var len = $("#" + elemId).val().replace(/\r(?!\n)|\n(?!\r)/g, "\r\n").length;
			if(len >= 2000) {
				$("#" + elemId).parent().find("div").text(' you have reached the limit');
			} else {
				var char = 2000 - len;
				$("#" + elemId).parent().find("div").text(char + ' characters left');
			}
		}
		//else if(value.includes("otherAddText")) {
		else if(value.indexOf("otherAddText") > -1) {
			//The length of the text is available at the beginning of the value string
			//Format is <length of text><otherAddText-x-x-x><text>
			var textsize = value.substr(0, value.indexOf("otherAddText"));
			var elemId = value.substr(textsize.length, value.length - textsize - textsize.length);
			var text = value.substr(value.length - textsize);
			$("#" + elemId).val(text);
			var len = $("#" + elemId).val().replace(/\r(?!\n)|\n(?!\r)/g, "\r\n").length;
			if(len >= 2000) {
				$("#" + elemId).parent().find("div").text(' you have reached the limit');
			} else {
				var char = 2000 - len;
				$("#" + elemId).parent().find("div").text(char + ' characters left');
			}
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
	
	//Do not show DULs and DUL verified flag if provisional
	value  = $("#finalprov option:selected").text();
	  if ( value == 'Provisional') {
		  //Hide IC Memo for future use
		  $("#memo").hide();
		  
		  //Hide DUL verification dropdown
    	  $(".DULv").hide();
    	  
    	  //Hide the Add DUL message for all studies
    	  $(".DULinfo").hide();
    	  
    	  //Hide the DUL container for all studies
    	  $(".cloneDULinput").hide();
    	  
    	  //Hide addDul buttons from all studies
  	      $(".addDulSetButton").hide();
    	  
    	  //remove all dulTypes from all studies
    	  $(".dulTypes").remove();
    	    
      }
      
	

	  //Do not show any sections if no file uploaded.
	  /*if( !$.trim( $("#loadIcFileHistory").html() ).length ) {
		  $(".form-group").hide();
	  } else {
		  $(".form-group").show();
	  } 
	  */
	  
	  //set the correct length for text areas
	  showCharCount('#instCertComments', '#charNum6');

		//set Studies Comments length text area
		var studyItems = $(".studySections").length;
		for(var i=0; i< studyItems; i++) {
			var commentsElem = $("#comments-" + i);
			showCharCount(commentsElem, '#count-' + i);
		}
		
});

//comments kep up function
function countChar(elem) {
	var max= 2000;
	var len = $(elem).val().replace(/\r(?!\n)|\n(?!\r)/g, "\r\n").length;
	if (len >= max) {
		$(elem).parent().find("div").text(' you have reached the limit');
	} else {
		var char = max - len;
		$(elem).parent().find("div").text(char + ' characters left');
	}
}


//Invoked when a DULSet parent radio button is clicked. Displays
//the DUL selections available
function displayDuls(element) {
	
    var dulTypeDivId = $(element).attr("name").replace("parentDul-", "dulType");
	
	//First hide dul selections (checkboxes) from all parents (radio buttons) 
	$("#" + dulTypeDivId).find(".dulSetDiv").hide();
	//Uncheck all the selections
	$("#" + dulTypeDivId).find(".dulSet").prop('checked', false);
	$("#" + dulTypeDivId).find(".input_other").val('');
	
	//Show the text fields associated with the parent if any
	
	
	//Then show dul selections only from the checkboxes of the parent that was checked
	if($(element).is(":checked")) {
		var dulSetIds = $(element).attr('id').replace("parentDul", "dulSet");
		$("#" + dulSetIds).show();
	}
}


function addDulSet(elem)  {
	
	var studiesIdx = $(elem).attr("id").replace("btnAddDUL-", "");
	var dulItems = $("#studySection" + studiesIdx).find(".dulTypes").length;
	
	 // Right now you can only add 10 DULs. change '10' below to the max number of DULSets that
	//can be attached to a study
    if (dulItems == 10) {
    	$('#btnAddDUL-' + studiesIdx).attr('disabled', true).prop('value', "You've reached the limit");
    	return;
	};

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
	var newDulTypeDiv = $( "#dulTypeTemplate0-0").clone(true, true);
	//Set the correct ids and names
	newDulTypeDiv.attr("id", "dulType" + studiesIdx + "-" + newDulTypeIndex);
	newDulTypeDiv.attr("class", "dulTypes");
	newDulTypeDiv.html(function(i, oldHTML) {
	    return oldHTML.replace(/0-0/g, studiesIdx + "-" + newDulTypeIndex).replace(
	    	/instCertification.studies\[0\].studiesDulSets\[0\]/g, 
	    	"instCertification.studies[" + studiesIdx + "].studiesDulSets[" + newDulTypeIndex + "]").replace(
	    		/deleteDulSet\(0,0\)/g, "deleteDulSet(" + studiesIdx + "," + newDulTypeIndex + ")");
	})
	
	//Set the correct values
	newDulTypeDiv.find("#dulSetDisplayId" + studiesIdx + "-" + newDulTypeIndex).attr("value", newDulTypeIndex);
	
	//Append the new DUL Type to the end of the current set
	newDulTypeDiv.appendTo("#cloneDULInput-" + studiesIdx);
	$("#cloneDULInput-" + studiesIdx).show();
	
	newDulTypeDiv.find("a.pop").hover(function() {
		var value=$(this).children().first().val();
		$(this).attr('data-content', value);
		$(this).popover({ trigger: "manual" , html: true, animation:false})
	    .on("mouseenter", function () {
	        var _this = this;
	        $(".popover").on("mouseleave", function () {
	            $(_this).popover('hide');
	        });
	    }).on("mouseleave", function () {
	        var _this = this;
	        setTimeout(function () {
	            if (!$(".popover:hover").length) {
	                $(_this).popover("hide");
	            }
	        }, 300);
	          }).popover("show");
	});

	newDulTypeDiv.show();
};


function deleteDulSet(studiesIdx, dulSetIdx) {
	$("#dulType" + studiesIdx + "-" + dulSetIdx).find("#dulSetDisplayId" + studiesIdx + "-" + dulSetIdx).val('');
	$("#dulType" + studiesIdx + "-" + dulSetIdx).remove();
};


function addStudy(studyPk, studyName, studyInst) {
	
	var studyItems = $(".studySections").length;
	
	 // Right now you can  add 500 studies. change '500' below to the max number of studies
	//that are permitted
    if (studyItems == 500) {
    	$('#btnAdd').attr('disabled', true).prop('value', "You've reached the limit");
    	return;
	};

	//Get the first available study to select for cloning. It need not
	//be the first index because deletions may have occurred from top 
	//because we do not know which ones may have been deleted
	cloneDulTypeIndex = 0;
	for(var studySectionIndex=0; studySectionIndex < 500; studySectionIndex++) {
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
	
	//TBD - Use common string replace
	//Set the correct ids and names
	newStudySectionDiv.attr("id", "studySection" + newStudySectionIndex);
	newStudySectionDiv.html(function(i, oldHTML) {
	    return oldHTML.replace(/-0/g, "-" + newStudySectionIndex).replace(
	    	/instCertification.studies\[0\]/g, 
	    	"instCertification.studies[" + newStudySectionIndex + "]").replace(
		    		/deleteStudy\(0\)/g, "deleteStudy(" + newStudySectionIndex + ")");
	})
	
	//Remove values
	newStudySectionDiv.find("#studyDisplayId-" + newStudySectionIndex).attr("value", newStudySectionIndex);
	newStudySectionDiv.find("#studyId-" + newStudySectionIndex).val(studyPk);
	newStudySectionDiv.find("#studyName-" + newStudySectionIndex).val(studyName);
	newStudySectionDiv.find("#institution-" + newStudySectionIndex).val(studyInst);
	newStudySectionDiv.find("#dulVerificationId-" + newStudySectionIndex).val(-1);
	newStudySectionDiv.find("#comments-" + newStudySectionIndex).val("").removeAttr("value");
	newStudySectionDiv.find("#count-" + newStudySectionIndex).text("2000 Character limits")
	
	//Empty the class cloneDULInput that contains the DUL Types that also got cloned
	newStudySectionDiv.find("#cloneDULInput-" + newStudySectionIndex).empty();
	
	//Append the new Study to cloneStudyInput
	newStudySectionDiv.appendTo(".cloneStudyInput");
	
	newStudySectionDiv.find("a.hoverOver").hover(function(){
		var value=$(this).children().first().val();
		$(this).attr('data-original-title', value);
		$(this).tooltip({
            container: 'body',
            placement: 'right',
            trigger: 'hover'
        }).tooltip('show');
	});
	
	//Add the trash icon to all studies if there is more than one study, 
	//else remove trash can from the lone one
	var numItems = $(".studySections").length;
	var studySetArray = $(".studySections");
	if(numItems > 1) {		
		jQuery.each(studySetArray, function(index, val) {
			if($(this).find(".studyHeadingPanel").find(".deleteIcon").length == 0) {
				var elemIndex = $(this).attr("id").replace("studySection", "");
				$(this).find(".studyHeading").append('<a href="#" onclick="deleteStudy(' + elemIndex + ')" class="deleteIcon" style="float: right;"><i class="fa fa-trash fa-lg" aria-hidden="true" title="Delete" alt="Delete"></i></a>');
			}
		});
	} else {
		studySetArray.find(".studyHeadingPanel").find(".deleteIcon").remove();
	}
		
	//Add a new DUL Type to this study if provisional is not selected
	value  = $("#finalprov option:selected").text();
	if ( value != 'Provisional') {
        addDulSet($("#btnAddDUL-" + newStudySectionIndex));
	}
};



function deleteStudy(studiesIdx) {
	
	$("#studySection" + studiesIdx).find("#studyDisplayId" + studiesIdx).val('');
	$("#studySection" + studiesIdx).remove();
	var numItems = $(".studySections").length;
	if(numItems == 1) {
		$(".studySections").find(".studyHeadingPanel").find(".deleteIcon").remove();
	}
};


//toggle for Provisional/Final Dropdown box
//User clicked provisional or final
//When they click provisional remove all the DUL Sets, hide all DUL info and all buttons
//When they click final add one DUL set to every study, show all DULL info and add button

$('#finalprov').on('change', function() {
	value  = $("#finalprov option:selected").text();
	if ( value == 'Provisional') { 
	    //Hide IC Memo
		$("#memo").hide();
		$("#memo").find(".c-select").val(-1);
		
		//Hide DUL verification dropdown
	    $(".DULv").hide();
	    $(".DULv").find("c-select").val(-1);
	    	  
	    //Hide the Add DUL message
	    $(".DULinfo").hide();
	    	  
	    //Hide the DUL container
	    $(".cloneDULinput").hide();
	    	  
	    //Hide addDul buttons from all studies
	    $(".addDulSetButton").hide();
	    	  
	    //remove all dulTypes
	    $(".dulTypes").remove();
	} else {
		//Show IC memo
		$("#memo").show();
		
	    //Show the DUL verification
	    $(".DULv").show();
	        
	    //Show the add DUL message
	    $(".DULinfo").show();
	        
	    //Show the DUL container in each study with one set
	    var studySetArray = $(".studySections");
	    jQuery.each(studySetArray, function(index, val) {
	    	var elemIndex = $(this).attr("id").replace("studySection", "");
	    	var dulItems = $("#studySection" + elemIndex).find(".dulTypes").length;
	    	if(dulItems == 0) {
	          addDulSet($("#btnAddDUL-" + elemIndex));
	    	}
	    })
	        
	    //Show the add buttons
	    $(".addDulSetButton").show();
	}
});


$(document).ready(function () {

	// IC file upload Ajax
	$("#institutional_form").on('change', '#icUploadFile', function () {

		if ($('#icUploadFile').get(0).files.length === 0) {
		    return;
		}
		
		$("#messages").empty();
		
		var result = "";
		var $form, fd;
	    $form = $("#institutional_form");
	    fd = new FormData($form[0]);
	    $('button.has-spinner').toggleClass('active');
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
		$('button.has-spinner').toggleClass('active');
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

	
$(document).on('shown.bs.collapse', '.collapse', function() {
             $(this)
                 .parent()
                 .find('.fa:first')
                 .removeClass("fa-plus-square")
                 .addClass("fa-minus-square");
});
$(document).on('hidden.bs.collapse', '.collapse', function() {
             $(this)
                 .parent()
                 .find('.fa:first')
                 .removeClass("fa-minus-square")
                 .addClass("fa-plus-square");
});

$(".helpfile").click(function() {
	
	var url = "/documentation/application/Project_Only_Institutional_Certifications_help.pdf";
	var winName = "Submission IC Help File";
	var features = "menubar=yes,scrollbars=yes,resizable=yes,width=800,height=800";
	var newWin = window.open(url, winName, features);
});

$(".helpfileSubProject").click(function() {
	
	var url = "/documentation/application/Sub-project_Only_Institutional_Certifications_help.pdf";
	var winName = "Submission IC Sub-Project Help File";
	var features = "menubar=yes,scrollbars=yes,resizable=yes,width=800,height=800";
	var newWin = window.open(url, winName, features);
});

$("a.hoverOver").hover(function(){
	var value=$(this).children().first().val();
	$(this).attr('data-original-title', value);
});

$("a.pop").hover(function() {
	var value=$(this).children().first().val();
	$(this).attr('data-content', value);
});

//comments key up function
$('#instCertComments').keyup(function() {
	//set the correct length for text areas
	showCharCount(this, '#charNum6');
});

var table;
function initializeStudyTable() {
	table = $('#studySelectTable').DataTable({
		 "bPaginate": false,
			
	      columnDefs: [
	         { targets: ['status'], type: 'alt-string'},
	         { targets: 'no-sort', orderable: false }]
	});
	
	$("#no-sorting").removeClass('sorting_asc').addClass('sorting_disabled');

	$('.dataTables_filter').append("<div class='searchHelp'>(Enter at least 2 characters to search)</div>"); 
	   
}

function openStudy(element, type) {
	
	//Remove Action message if any
	$("#messages").hide();
	
	$("#selectType").val(type);
	$(".radioSelected").removeAttr('checked');
	$(".checkboxSelected").removeAttr('checked');
	$("div.noStudy").hide();
	
	if(type === "single") {
		index = $(element).parent().parent().parent().children(':first').attr('id').replace('studyName-','');
		$("#studyIndex").val(index);
		$(".radioSelect").show();
		$(".checkboxSelect").hide();
	} else {
		$(".radioSelect").hide();
		$(".checkboxSelect").show();
	}
	$(".studySelectRow").show();
	$(".studySelectedStudyId").each(function(){
		id = $(this).val();
		$("#studySelectRow-" + id).hide();
	});
	
	$("#submissionIcSection").hide();
	$("#reselectStudySection").show();
	$("#studyAvailable").show();
	
	//If there are studies to select, initialize the table
	if($(".studySelectRow:visible").length != 0) {
		$("#studyAvailable").show();
		$("#noStudyAvailable").hide();
		$(".nextButton").show();
		if($.fn.DataTable.isDataTable('#studySelectTable')) {
			table.destroy();
		}
		initializeStudyTable();
	} else {
		//If there are no more studies to show, show the message instead.
		$("#studyAvailable").hide();
		$("#noStudyAvailable").show();
		$(".nextButton").hide();
	}
}

function cancelStudy() {
	
	if($.fn.DataTable.isDataTable('#studySelectTable')) {
		table.destroy();
	}
	$("div.noStudy").hide();
	$("#submissionIcSection").show();
	$("#reselectStudySection").hide();
	
}

function selectStudy() {
	
	//If nothing is select, show error
	if($(".radioSelected:checked").length === 0 && $(".checkboxSelected:checked").length === 0) {
		$("div.noStudy").show();
		return;
	}
	type = $("#selectType").val();
	index = $("#studyIndex").val();
	
	//show everything first
	$("#submissionIcSection").show();
	
	if(type === "single") {
		studyPk = $(".radioSelected:checked").val();
		studyName = $(".radioSelected:checked").parent().parent().next('td').text();
		studyInst = $(".radioSelected:checked").parent().parent().next('td').next('td').text();
		$("#studyName-" + index).val(studyName);
		$("#institution-" + index).val(studyInst);
		$("#studyId-" + index).val(studyPk);
		$("[id^=dulSetId" + index + "]").val('');
	} else {
		$(".checkboxSelected:checked").each(function(){
			studyPk = $(this).val();
			studyName = $(this).parent().parent().next('td').text();
			studyInst = $(this).parent().parent().next('td').next('td').text();
			addStudy(studyPk, studyName, studyInst);
		});
	}
	
	if($.fn.DataTable.isDataTable('#studySelectTable')) {
		table.destroy();
	}
	$("div.noStudy").hide();
	$("#reselectStudySection").hide();


}

function enableStudy() {
	$(".input_sn").prop('disabled', false);
	$(".input_in").prop('disabled', false);
}
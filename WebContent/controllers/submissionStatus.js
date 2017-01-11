//for submissionStatus.htm page -- 

//This functions hides/shows Submission status drop down box based on selection made in Registration box.
function processRegistrationStatusSelected(registrationId){ 
	var submissionId = registrationId.substring(registrationId.indexOf("_")+1,registrationId.length); 
	var studyReleasedId = submissionId.substring(submissionId.indexOf("_")+1,submissionId.length);	
	var registrationStatus = $('#'+registrationId+' option:selected').text();
	
	if($("#dataSubmitted").val() == "N") {
		$("#projStatus_"+submissionId).val('15');
		$("#projStatus_"+submissionId).attr('disabled',true);
		if(registrationStatus == "Completed")  {
			$("#studyRel_"+studyReleasedId).attr('disabled',false);
		}
		else {
			$("#studyRel_"+studyReleasedId).attr('disabled',true);
		}
	} else {
		if(registrationStatus == "Completed" || registrationStatus == "Not Applicable")  {
			$("#projStatus_"+submissionId).attr('disabled',false);
		}
		else { 
			$("#projStatus_"+submissionId).val('13');
			$("#projStatus_"+submissionId).attr('disabled',true);
		}
		processSubmissionStatusSelected("projStatus_"+submissionId);
	}
	
	
}

//This functions hides/shows studyReleased  drop down box based on selection made in Submission status box.
function processSubmissionStatusSelected(submissionStatusId){ 
	var studyReleasedId = submissionStatusId.substring(submissionStatusId.indexOf("_")+1,submissionStatusId.length);
	var submissionStatus = $('#'+submissionStatusId+' option:selected').text();
	
	if(submissionStatus == "Completed" || submissionStatus == "Not Applicable"){   
		$("#studyRel_"+studyReleasedId).attr('disabled',false);
	} else{
		$("#studyRel_"+studyReleasedId).val('17');
		$("#studyRel_"+studyReleasedId).attr('disabled',true);
	}
	
}

 $(function () {
      $('#repositoryDate .input-group.date').datepicker({
         orientation: "bottom auto",
         todayHighlight: true,
         autoclose: true
      });
  });
  
  jQuery(function ($) {        
  $('form').bind('submit', function () {
    $(this).find(':input').prop('disabled', false);	
  });
});

$(document).ready(function () { 
	jQuery('#submission_status_form select[name*=lookupTByRegistrationStatusId]').each(function () { 
		processRegistrationStatusSelected(this.id);
	});
	
	$(".repoSelect").change(function(){
		if( $(this).is(':checked')) {
			$(this).parent().parent().parent().children('div.panel-body').show();
	    } else {
	    	$(this).parent().parent().parent().children('div.panel-body').hide();
	    }
	});
	$(".repoSelect").change();
	//Remove dirty 
	$("#submission_status_form").removeClass( "dirty" )
	
		//set Studies Comments length text area
		var repoItems = $(".repoCount").length;
		for(var i=0; i< repoItems; i++) {
			var max = 2000;
			var len = $("#repositoryComments_" + i).val().length;
			if (len >= max) {
				$("#countRepo_" +i).text(' you have reached the limit');
			} else {
				var char = max - len;
				$("#countRepo_" +i).text(char + ' characters left');
			}	
		}
});

if($("#subprojectFlag").val() == 'N'){
	$(".helpfile").click(function(){
		
		var url = "/documentation/application/Project_Only_Submission_Status_help.pdf";
		var winName = "Submission Status Help File";
		var features = "menubar=yes,scrollbars=yes,resizable=yes,width=800,height=800";
		var newWin = window.open(url, winName, features);
	});
	}
	else {
		$(".helpfile").click(function(){
			
			var url = "/documentation/application/Sub-project_Only_Submission_Status_help.pdf";
			var winName = "Submission Status Help File";
			var features = "menubar=yes,scrollbars=yes,resizable=yes,width=800,height=800";
			var newWin = window.open(url, winName, features);
		});
	}

$("a.hoverOver").hover(function(){
	var value=$(this).children().first().val();
	$(this).attr('data-original-title', value);
});

//comments kep up function
function countChar(elem) {
	var max= 2000;
	var len = $(elem).val().length;
	if (len >= max) {
		$(elem).parent().find("div").text(' you have reached the limit');
	} else {
		var char = max - len;
		$(elem).parent().find("div").text(char + ' characters left');
	}
}

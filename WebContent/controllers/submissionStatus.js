//for submissionStatus.htm page -- 

//This functions hides/shows Submission status drop down box based on selection made in Registration box.
function enableDisableSubmissionStatus(registrationId){ 
	var submissionId = registrationId.substring(registrationId.indexOf("_")+1,registrationId.length); 
	var registrationStatus = $('#'+registrationId+' option:selected').text();
	
	if($("#dataSubmitted").val() == "N") {
		//$("#projStatus_"+submissionId).val('15');
		$("#projStatus_"+submissionId).attr('disabled',true);
	} else {
		if(registrationStatus == "Completed" || registrationStatus == "Not Applicable")  {
			$("#projStatus_"+submissionId).attr('disabled',false);
		}
		else { 
			$("#projStatus_"+submissionId).val('13');
			$("#projStatus_"+submissionId).attr('disabled',true);
		}
	}
	
	enableDisableStudyReleased("projStatus_"+submissionId);
}

//This functions hides/shows studyReleased  drop down box based on selection made in Submission status box.
function enableDisableStudyReleased(submissionStatusId){ 
	var studyReleasedId = submissionStatusId.substring(submissionStatusId.indexOf("_")+1,submissionStatusId.length);
	var submissionStatus = $('#'+submissionStatusId+' option:selected').text();
	
	if($("#dataSubmitted").val() == "N") {
		//$("#studyRel_"+studyReleasedId).val('17');
		$("#studyRel_"+studyReleasedId).attr('disabled',false);
	}
	else {
		if(submissionStatus == "Completed" || submissionStatus == "Not Applicable"){   
			$("#studyRel_"+studyReleasedId).attr('disabled',false);
		} else{
			$("#studyRel_"+studyReleasedId).val('17');
			$("#studyRel_"+studyReleasedId).attr('disabled',true);
		}
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
		enableDisableSubmissionStatus(this.id);
	});
	
	if($("#subprojectFlag").val().toUpperCase() == 'Y') {
		$('#showMessage').show().css('display', 'inline'); 
	}
	
	$(".helpfile").click(function(){
		
		var url = "https://gds-dev.nci.nih.gov/documentation/application/Submission_Status_help.pdf";
		var winName = "Submission Status Help File";
		var features = "menubar=yes,scrollbars=yes,resizable=yes,width=800,height=800";
		var newWin = window.open(url, winName, features);
	});
});
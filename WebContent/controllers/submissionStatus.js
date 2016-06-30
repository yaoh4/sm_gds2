//for submissionStatus.htm page -- 

//This functions hides/shows Submission status drop down box based on selection made in Registration box.
function enableDisableSubmissionStatus(registrationId){ 
	var submissionId = registrationId.substring(registrationId.indexOf("_")+1,registrationId.length); 
	var registrationStatus = $('#'+registrationId+' option:selected').text();
		
	if(registrationStatus == "Completed" || registrationStatus == "Not Applicable"){   
		$("#projStatus_"+submissionId).attr('disabled',false);
	}
	else{
		$("#projStatus_"+submissionId).val('13');
		$("#projStatus_"+submissionId).attr('disabled',true);
		}	
	
	enableDisableStudyReleased("projStatus_"+submissionId);
}

//This functions hides/shows studyReleased  drop down box based on selection made in Submission status box.
function enableDisableStudyReleased(submissionStatusId){ 
	var studyReleasedId = submissionStatusId.substring(submissionStatusId.indexOf("_")+1,submissionStatusId.length);
	var submissionStatus = $('#'+submissionStatusId+' option:selected').text();
	
	if(submissionStatus == "Completed" || submissionStatus == "Not Applicable"){   
		$("#studyRel_"+studyReleasedId).attr('disabled',false);
	}
	else{
		$("#studyRel_"+studyReleasedId).val('17');
		$("#studyRel_"+studyReleasedId).attr('disabled',true);
		}
}

 $(function () {
      $('#repositoryDate .input-group.date').datepicker({
         orientation: "bottom auto",
         todayHighlight: true
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
});
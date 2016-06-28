//for submissionStatus.htm page -- 

//This functions hides/shows Submission status drop down box based on selection made in Registration box.
function enableDisableSubmissionStatus(registrationId){ 
	var submissionId = registrationId.substring(registrationId.indexOf("_")+1,registrationId.length); 
	var registrationStatus = $('#'+registrationId+' option:selected').text();
		
	if(registrationStatus == "Completed" || registrationStatus == "Not Applicable"){   
		$("#projStatus_"+submissionId).attr('disabled',false);
	}
	else{$("#projStatus_"+submissionId).attr('disabled',true);}	
	
	enableDisableStudyReleased("projStatus_"+submissionId);
}

//This functions hides/shows studyReleased  drop down box based on selection made in Submission status box.
function enableDisableStudyReleased(submissionStatusId){ 
	var studyReleasedId = submissionStatusId.substring(submissionStatusId.indexOf("_")+1,submissionStatusId.length);
	var submissionStatus = $('#'+submissionStatusId+' option:selected').text();
	
	if(submissionStatus == "Completed" || submissionStatus == "Not Applicable"){   
		$("#studyRel_"+studyReleasedId).attr('disabled',false);
	}
	else{$("#studyRel_"+studyReleasedId).attr('disabled',true);}
}


//for submissionStatus.htm page -- 

$(document).ready(function () {

  //rule for Registration Status Dropdown
 
$('#regStatus-1').change(function(e){
if($(this).val() == "Completed" | "Not Applicable"){
   
    $("#projStatus-1").attr('disabled',false);
}
else $("#projStatus-1").attr('disabled',true);
});

//rule for Project Submission Status Dropdown

  $('#projStatus-1').change(function(e){
if($(this).val() == "Completed" | "Not Applicable"){
   
    $("#studyRel-1").attr('disabled',false);
}
else $("studyRel-1").attr('disabled',true);
});



  //  $('form').on('dirty.areYouSure', function() {
      // Enable save button only as the form is dirty.
  //    $(this).find('input[type="submit"]').removeAttr('disabled');
  //  });
  //  $('form').on('clean.areYouSure', function() {
      // Form is clean so nothing to save - disable the save button.
   //   $(this).find('input[type="submit"]').attr('disabled', 'disabled');
  //  });



});


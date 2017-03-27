////for page institutional_dashboard.htm
$(document).ready(function() {
	
	if ($('.alert-danger').is(':visible')){
		$("#radioCertCompleteY").prop('checked', true);
		 $("#certFlag").val("Y");  
	      }
	

  if($("#icIds") != null && $("#icIds").val().length > 0) {
    var icIdArray = JSON.parse($("#icIds").val());
	
	//Set all selected ics to checked
	jQuery.each(icIdArray, function(index, value) {
		
	  //this represents value of the ic checkbox
	  $("#ic" + value).prop('checked', true);	
	});
	
	//this represents if all the ic`s are checked in a sub-projeect, then enable select all checkbox
		if($('input[name="ic-selected"]:checked').length == $('input[name="ic-selected"]').length) {
			$("#all").prop('checked', true);
	  }	
  }
  
  if ($("#radioCertCompleteY").prop("checked") == true) {
	  $("#certFlag").val("Y");
  	     }
  else if($("#radioCertCompleteN").prop("checked") == true){
	  	$("#certFlag").val("N");
  }
  
  if($("#subprojectFlag").val().toUpperCase() == 'Y') {
	  
	  var atLeastOneIsChecked = $('input[name="ic-selected"]:checked').length > 0;
	  $("#selectIcs").val(atLeastOneIsChecked);
	  $('.icSelect').change(function() {
   	   var atLeastOneIsChecked = $('input[name="ic-selected"]:checked').length > 0;
   		  $("#selectIcs").val(atLeastOneIsChecked);
   		if($('input[name="ic-selected"]:checked').length == $('input[name="ic-selected"]').length) {
			$("#all").prop('checked', true);
	  }	
   		else {
   			$("#all").prop('checked', false);
   		}
      });    
  }
  
    $("#all").change(function() {
	  var checked_status = this.checked;
	 $('input[name="ic-selected"]').each(function(){
		  this.checked =  checked_status;
	  });
  });
  
  $('input[type="radio"]').click(function() {
	   if ($("#radioCertCompleteY").prop("checked") == true) {
		   $("#certFlag").val("Y");
 		}
 else if($("#radioCertCompleteN").prop("checked") == true){
	  $("#certFlag").val("N");
 }
});
  
  //show/hide Add Additional Instititional Certificates link if not subproject
  if($("#subprojectFlag").val().toUpperCase() == 'N') {
/*	if ($("#radioCertCompleteY").prop("checked") == true) {
		$('#addICBtn').hide();
	} else {
		$('#addICBtn').show().css('display', 'inline');
	}
	
	
	$('input[type="radio"]').click(function() {
       if($(this).attr('id') == 'radioCertCompleteN') {
            $('#addICBtn').show().css('display', 'inline');           
       }
       else {
            $('#addICBtn').hide();
       }
   });*/
	
	//show the delete and edit icons only for projects
	$("#actionColumnIC").show();
	$(".editDeleteBtns").show();
	$(".projectColumn").show();
 } else {
	 //Show the checkbox select column only for subprojects
	 $("#subprojectColumn").show();
	 $(".subprojectSelect").show();
	 $(".projectColumn").hide();
	 $('#showSpan').show().css('display', 'inline'); 
	 
 }

//delete modal///


  $('.btnDelete').on('click', function (e) {
    e.preventDefault();
    var id = $(this).closest('tr').data('id');
    $('#myModal').data('id', id).modal('show');
});
  
  
  $('.icDetails').on('click', function(e) {
	  e.preventDefault();
	  var id = $(this).attr("id").replace("icDetails", "contentDivImg");
	  var expandId = $(this).attr("id").replace("icDetails", "");
	  $("#" + id).slideToggle('500');
	  $("#" + expandId + "expand").toggleClass('fa-plus-square fa-minus-square');
  })
  
  
 

$('#btnDelteYes').click(function () {
    var id = $('#myModal').data('id');
    var projId = $('#projectId').val();
    
  $.ajax({
		url : 'deleteIc.action',
		type : 'post',
		data : {instCertId: id, projectId: projId},
		async : false,
		success : function(msg) {
			result = $.trim(msg);
		},
		error : function() {
			alert("Could not delete file");
		}
	});
 
    $('[data-id=' + id + ']').remove();
    $("." + "remove" + id).remove();
    $('#myModal').modal('hide');
    
    var listSize = $("#icListSize").val() - 1;
    $("#icListSize").val(listSize);
    if(listSize === 0) {
    	$('#ic_dashboard_form').submit();
    }
    
});


//funtion for accordion study panels 



$(document).on('click', '.header', function () {

    $(this).next(".content").slideToggle(400);
    $(this).find('.fa').toggleClass('fa-plus-square fa-minus-square');
    $(this).show();
    $("#collapseOne").focus();

});

//set the correct length for text areas
showCharCount('#icComments', '#charNum');

//set the correct length for text areas
showCharCount('#additionalComments', '#charNum2');

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

$('#icComments').keyup(function() {
	//set the correct length for text areas
	showCharCount(this, '#charNum');
});

$('#additionalComments').keyup(function() {
	//set the correct length for text areas
	showCharCount(this, '#charNum2');
});

////for page institutional_dashboard.htm
$(document).ready(function() {

  if($("#icIds") != null && $("#icIds").val().length > 0) {
    var icIdArray = JSON.parse($("#icIds").val());
	
	//Set all selected ics to checked
	jQuery.each(icIdArray, function(index, value) {
		
	  //this represents value of the ic checkbox
	  $("#ic" + value).prop('checked', true);	
	});
  }
	
	
  //show/hide Add Additional Instititional Certificates link if not subproject
  if($("#subprojectFlag").val().toUpperCase() == 'N') {
	if ($("#radioCertCompleteY").prop("checked") == true) {
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
   });
	
	//show the delete and edit icons only for projects
	$("#actionColumn").show();
	$(".editDeleteBtns").show();
 } else {
	 //Show the checkbox select column only for subprojects
	 $("#subprojectColumn").show();
	 $(".subprojectSelect").show();
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
    $('#myModal').modal('hide');
});


//funtion for accordion study panels 



$(document).on('click', '.header', function () {

    $(this).next(".content").slideToggle(400);
    $(this).find('.fa').toggleClass('fa-plus-square fa-minus-square');
    $(this).show();
    $("#collapseOne").focus();

});

});



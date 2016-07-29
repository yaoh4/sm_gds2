////for page institutional_dashboard.htm
$(document).ready(function() {

//show/hide button for Add Additional Instititional Certificates	


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
  
  
  //Show and hide ic details
//$('body').on('click', 'a.icDetails', function() {
	//var id = $(this).attr("id").replace("icDetails", "contentDivImg");
   // $("#" + id).slideToggle('500');
   // $("i.expand.fa").toggleClass('fa-plus-square fa-minus-square');
//});
  
  

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



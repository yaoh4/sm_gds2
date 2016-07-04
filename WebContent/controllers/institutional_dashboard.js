////for page institutional_dashboard.htm
$(document).ready(function() {

//show/hide button for Add Additional Instititional Certificates	

$('input[type="radio"]').click(function() {
       if($(this).attr('id') == 'reviewedNo') {
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

$('#btnDelteYes').click(function () {
    var id = $('#myModal').data('id');
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



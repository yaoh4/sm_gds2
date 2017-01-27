window.onload = function() {
  $("#no-sorting").removeClass('sorting_asc').addClass('sorting_disabled');
};

$(document).ready(function(){

  

$('#example').dataTable( {
		
       columnDefs: [
         { targets: ['status'], type: 'alt-string'},
 
         { targets: 'no-sort', orderable: false }] 
        

    } );



$(".checkboxS").on("change", function(){
	$("#message").attr("style", "display:none");
    var studies = [];
    $('.checkboxS:checked').each(function(){        
        var studyTypes = $(this).val();
        studies.push(studyTypes);
    });
    $("#log").html(studies.join("<br/> "));


});
 

});
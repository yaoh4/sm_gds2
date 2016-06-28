
$(function() {

//Show and hide file history
$('body').on('click', 'a.history', function() {
    $(".uploadedHistory").slideToggle('500');
    $("i.expand.fa").toggleClass('fa-plus-square fa-minus-square');
});


//Function that shows Other box
$('#25').change(function () {
    if( $(this).is(':checked')) {
        $("#addRepo").show();
    } else {
    	$(".other").val('');
    	$(".otherWrapper").not(".otherWrapper:first").remove();
        $("#addRepo").hide();
    }
}); 
$('#25').change();

//Function that controls the Adding of Additional Repositories
$("#gds-form").on('click', '#addfield', function () {
	maxInputs = 10;
	fieldCount = $(".otherWrapper").length;
	if (fieldCount < maxInputs) // max input box allowed
	{
		// add input box
		$(".otherWrapper").last().after('<div class="otherWrapper" style="margin-bottom: 15px; margin-top: 15px;">'
			+ '<input type="text" class="other" name="otherText[25]" id="field_'
			+ fieldCount
			+ '" placeholder="Name of Repository" />'
			+ '<span class="fa fa-trash removeclass delete" title="Delete"  aria-hidden="true" alt="delete icon" style="font-size: 18px; padding-right: 3px;">'
			+ '</span></div>');
	}
});

//Function that controls the Deleting of Additional Repositories
$("#gds-form").on('click', '.removeclass', function () {//user click on remove text
	$(this).parent('div').remove(); //remove text box
}); 

});

$(function() {

//funtion that is triggered When Controlled is selected in Q6

/*$('#controlled').click(function() {
    if( $(this).is(':checked')) {
        $("#controlledText").show();
         $("#dGaP").prop("disabled", true);
         $( "#dGaP" ).prop( "checked", true );
    } else {
        $("#controlledText").hide();
       $("#dGaP").prop("disabled", false);
       $( "#dGaP" ).prop( "checked", false );
    }
}); */


//funtion that  show/hides textEditor and uploader for DSP

/*$('input[type="radio"]').click(function(){
        if($(this).attr("value")=="upload"){
            $("#DSPuploader").show();
    } else {
        $("#DSPuploader").hide();
    }
       
    });

$('input[type="radio"]').click(function(){
 if($(this).attr("value")=="paste"){
            $("#textEditor").show();
    } else {
        $("#textEditor").hide();
    }

     });
*/
//function for uploaded history





$('#expand a').click(function(){
    $("#uploadedHistory").slideToggle('500');
    $(this).find('i').toggleClass('fa-plus-square fa-minus-square')
});



//funtion that shows Other box

$('#other').click(function() {
    if( $(this).is(':checked')) {
        $("#addRepo").show();
    } else {
        $("#addRepo").hide();
    }
}); 

//funtion that Controlls the Adding/Deleting of Additional Repositories


var MaxInputs       = 8; //maximum input boxes allowed
var InputsWrapper   = $("#InputsWrapper"); //Input boxes wrapper ID
var AddButton       = $("#addfield"); //Add button ID

var x = InputsWrapper.length; //initlal text box count
var FieldCount=1; //to keep track of text box added

$(AddButton).click(function (e)  //on add input button click
{
        if(x <= MaxInputs) //max input box allowed
        {
            FieldCount++; //text box added increment
            //add input box
            $(InputsWrapper).append('<div style="margin-bottom: 15px; margin-top: 15px;"><input type="text" class="other" name="mytext[]" id="field_'+ FieldCount +'" placeholder="Name of Repository" ' +'"/><span class="fa fa-trash removeclass delete" title="Delete"  aria-hidden="true" alt="delete icon" style="font-size: 18px; padding-right: 3px;"></span></div>');
            x++; //text box increment
        }
return false;
});

$("body").on("click",".removeclass", function(e){ //user click on remove text
        if( x >= 1  ) {
                $(this).parent('div').remove(); //remove text box
                x--; //decrement textbox
        }
return false;
}); 

});
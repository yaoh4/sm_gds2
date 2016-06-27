  
//for institutional.htm page

  $(function() { 
var emptyStudy = ""

//toggle for Provisional/Final Dropdown box

    $('#provisional').on('change', function() {
      if ( this.value == 'Provisional')
    
       { $("#DULv, #DULinfo, #DULpanel").hide();
      }
      else
      {
        $("#DULv, #DULinfo, #DULpanel").show();
      }
    });
$( document ).ready(function() {
  emptyStudy = $("#entry1").clone();
});

//toggle for DUL radio buttons
$(document).on('click', '.input_radio', function(){
    var value = $(this).attr("value"); /* value of checkbox. generalAdd, otherAdd etc */
    var checkboxIds = $(this).attr("id").replace("radioitem",""); /* id of entry and dul minus radioitem entry1_dul1_ */
    var divId = $(this).attr("id").replace("radioitem","")+value; /* div to target (checkboxIds+value) entry1_dul1_generalAdd */
    var displayStatus = $("#"+divId).css("display"); /* display status (none or block) */

    /* get all 4 radio button sections and hide them */
    if (displayStatus=="none") {
        $("div[id^="+checkboxIds+"],"+"span[id^="+checkboxIds+"]").each(function(index) { 
            $(this).hide();
        });
        $("#"+divId).show(); /* show the clicked radio button's div */
    }
    /* hide radio button section */
    else {
        $("#"+divId).hide();
    };
});



//funtion for accordion study panels 



$(document).on('click', '.header', function () {

    $(this).next(".content").slideToggle(400);
    $(this).find('.fa').toggleClass('fa-plus-square fa-minus-square');
    $(this).show();
    $("#collapseOne").focus();

});


//function to clone study and increment form ids

$(function () {
   $('#btnAdd').click(function () {
    var emptyStudyClone = emptyStudy.clone();
    var study_num = $('.clonedInput').length;
    var new_entry_id = "entry"+(study_num+1);

    emptyStudyClone.attr("id",new_entry_id);
    $(emptyStudyClone).find("[id^=entry1],[name^=entry1],[for^=entry1]").each(function(index) {
        var id_attr = $(this).attr("id")
        var for_attr = $(this).attr("for")
        var name_attr = $(this).attr("name")
        if (for_attr) {
            $(this).attr("for",$(this).attr("for").replace("entry1",new_entry_id))
        };
        if (name_attr) {
            $(this).attr("name",$(this).attr("name").replace("entry1",new_entry_id))
        };
        if (id_attr) {
            $(this).attr("id",$(this).attr("id").replace("entry1",new_entry_id))
        };

    });    
    emptyStudyClone.find('.study').attr('id', new_entry_id + '_study').attr('name', new_entry_id + '_study').html('<i class="fa fa-minus-square" aria-hidden="true"></i>&nbsp;' + 'Study #' + (study_num+1) + '<a href="javascript:void" class="deleteIcon" style="float: right;"><i class="fa fa-trash" aria-hidden="true"></i></a>');
    emptyStudyClone.find('.content').show();

    // insert the new element after the last "duplicatable" input field
    $('#entry' + study_num).after(emptyStudyClone);
    $('#ID' + (study_num+1) + '_label_sn').focus();

        

    // right now you can only add 10 sections. change '10' below to the max number of times the form can be duplicated
    if (study_num+1 == 10)
        $('#btnAdd').attr('disabled', true).prop('value', "You've reached the limit");
    });

    // enable the "add" button
        $('#btnAdd').attr('disabled', false);
    });

 
//function to clone DUL and increment form ids
$(document).on('click', '#btnAddDUL', function(){
        var entry_id = $(this).parents("[id^=entry]").attr("id"); /* get entry id */
        console.log(entry_id)
        var numDUL     = $('#'+entry_id+' .DULclonedInput').length, // how many "duplicatable" input fields we currently have
            newNumDUL  = new Number(numDUL + 1),      // the numeric ID of the new input field being added
            newElemDUL = $('#'+entry_id+'_dul' + numDUL).clone().attr('id', entry_id+'_dul' + newNumDUL).fadeIn('slow'); // create the new element via clone(), and manipulate it's ID using newNum value
        console.log($('#'+entry_id+'_dul1'),$('.DULclonedInput').length)
        // manipulate the name/id values of the input inside the new element
        // H2 - section
        var new_entry_id = entry_id+'_dul'+newNumDUL; /* create new entry id. entry id + new dul number */
        newElemDUL.find('.heading-reference').attr('id', 'ID' + newNumDUL + '_reference').attr('name', 'ID' + newNumDUL + '_reference').html('DUL #' + newNumDUL + '<a href="javascript:void" class="deleteIcon2" style="float: right;"><i class="fa fa-trash" aria-hidden="true"></i></a>');

        // DUL Type - radio
        newElemDUL.find('.label_radio').attr('for', new_entry_id+'_radioitem');
        newElemDUL.find('.input_radio').attr('id', new_entry_id + '_radioitem').attr('name', new_entry_id +'_radioitem').val([]);

        //General div/////////////////////////////////
        newElemDUL.find('.general').attr('id', new_entry_id + '_generalAdd').css('display', 'none');
        // General - checkbox
        // Note that each input_checkboxitem has a unique identifier "-0". This helps pair up duplicated checkboxes and labels correctly. 
        newElemDUL.find('.input_checkboxitem-0').attr('id', new_entry_id + '_generalAdd_checkboxitem-0').attr('name', new_entry_id + '_generalAdd_checkboxitem-0').val([]);
        newElemDUL.find('.input_checkboxitem-1').attr('id', new_entry_id + '_generalAdd_checkboxitem-1').attr('name', new_entry_id + '_generalAdd_checkboxitem-1').val([]);
        newElemDUL.find('.input_checkboxitem-2').attr('id', new_entry_id + '_generalAdd_checkboxitem-2').attr('name', new_entry_id + '_generalAdd_checkboxitem-2').val([]);
        newElemDUL.find('.input_checkboxitem-3').attr('id', new_entry_id + '_generalAdd_checkboxitem-3').attr('name', new_entry_id + '_generalAdd_checkboxitem-3').val([]);
        // General - checkbox labels
        // Note that each checkboxitem has a unique identifier "-0". This helps pair up duplicated checkboxes and labels correctly. t.
        newElemDUL.find('.checkboxitem-0').attr('for', new_entry_id + '_generalAdd_checkboxitem-0');
        newElemDUL.find('.checkboxitem-1').attr('for', new_entry_id + '_generalAdd_checkboxitem-1');
        newElemDUL.find('.checkboxitem-2').attr('for', new_entry_id + '_generalAdd_checkboxitem-2');
        newElemDUL.find('.checkboxitem-3').attr('for', new_entry_id + '_generalAdd_checkboxitem-3');

        //Health div////////////////////////
        newElemDUL.find('.healthAdd').attr('id', new_entry_id + '_healthAdd').css('display', 'none');
        // Health - checkboxs
        // Note that each input_checkboxitem has a unique identifier "-0". This helps pair up duplicated checkboxes and labels correctly. 
        newElemDUL.find('.input_healthitem-0').attr('id', new_entry_id + '_healthAdd_checkboxitem-0').attr('name', new_entry_id + '_healthAdd_checkboxitem-0').val([]);
        newElemDUL.find('.input_healthitem-1').attr('id', new_entry_id + '_healthAdd_checkboxitem-1').attr('name', new_entry_id + '_healthAdd_checkboxitem-1').val([]);
        newElemDUL.find('.input_healthitem-2').attr('id', new_entry_id + '_healthAdd_checkboxitem-2').attr('name', new_entry_id + '_healthAdd_checkboxitem-2').val([]);
        newElemDUL.find('.input_healthitem-3').attr('id', new_entry_id + '_healthAdd_checkboxitem-3').attr('name', new_entry_id + '_healthAdd_checkboxitem-3').val([]);
        newElemDUL.find('.input_healthitem-4').attr('id', new_entry_id + '_healthAdd_checkboxitem-4').attr('name', new_entry_id + '_healthAdd_checkboxitem-4').val([]);
        newElemDUL.find('.input_healthitem-5').attr('id', new_entry_id + '_healthAdd_checkboxitem-5').attr('name', new_entry_id + '_healthAdd_checkboxitem-5').val([]);
        // Health - checkbox labels
        // Note that each checkboxitem has a unique identifier "-0". This helps pair up duplicated checkboxes and labels correctly. 
        newElemDUL.find('.healthitem-0').attr('for', new_entry_id + '_healthAdd_checkboxitem-0');
        newElemDUL.find('.healthitem-1').attr('for', new_entry_id + '_healthAdd_checkboxitem-1');
        newElemDUL.find('.healthitem-2').attr('for', new_entry_id + '_healthAdd_checkboxitem-2');
        newElemDUL.find('.healthitem-3').attr('for', new_entry_id + '_healthAdd_checkboxitem-3');
        newElemDUL.find('.healthitem-4').attr('for', new_entry_id + '_healthAdd_checkboxitem-4');   
        newElemDUL.find('.healthitem-5').attr('for', new_entry_id + '_healthAdd_checkboxitem-5');      


        //Disease div/////////////////////
        newElemDUL.find('.diseaseAdd').attr('id', new_entry_id + '_diseaseAdd').css('display', 'none');
        //Disease input box
        newElemDUL.find('.input_ds').attr('id', new_entry_id + '_diseaseAdd_diseaseSpecific').attr('name', new_entry_id + '_diseaseAdd_diseaseSpecific').val('');
        // Disease - checkboxs
        // Note that each input_checkboxitem has a unique identifier "-0". This helps pair up duplicated checkboxes and labels correctly. 
        newElemDUL.find('.input_diseaseitem-0').attr('id', new_entry_id + '_diseaseAdd_checkboxitem-0').attr('name', new_entry_id + '_diseaseAdd_checkboxitem-0').val([]);
        newElemDUL.find('.input_diseaseitem-1').attr('id', new_entry_id + '_diseaseAdd_checkboxitem-1').attr('name', new_entry_id + '_diseaseAdd_checkboxitem-1').val([]);
        newElemDUL.find('.input_diseaseitem-2').attr('id', new_entry_id + '_diseaseAdd_checkboxitem-2').attr('name', new_entry_id + '_diseaseAdd_checkboxitem-2').val([]);
        newElemDUL.find('.input_diseaseitem-3').attr('id', new_entry_id + '_diseaseAdd_checkboxitem-3').attr('name', new_entry_id + '_diseaseAdd_checkboxitem-3').val([]);
        newElemDUL.find('.input_diseaseitem-4').attr('id', new_entry_id + '_diseaseAdd_checkboxitem-4').attr('name', new_entry_id + '_diseaseAdd_checkboxitem-4').val([]);
        newElemDUL.find('.input_diseaseitem-5').attr('id', new_entry_id + '_diseaseAdd_checkboxitem-5').attr('name', new_entry_id + '_diseaseAdd_checkboxitem-5').val([]);
        newElemDUL.find('.input_diseaseitem-6').attr('id', new_entry_id + '_diseaseAdd_checkboxitem-6').attr('name', new_entry_id + '_diseaseAdd_checkboxitem-6').val([]);
        // Disease - checkbox labels
        // Note that each checkboxitem has a unique identifier "-0". This helps pair up duplicated checkboxes and labels correctly. 
        newElemDUL.find('.diseaseitem-0').attr('for', new_entry_id + '_diseaseAdd_checkboxitem-0');
        newElemDUL.find('.diseaseitem-1').attr('for', new_entry_id + '_diseaseAdd_checkboxitem-1');
        newElemDUL.find('.diseaseitem-2').attr('for', new_entry_id + '_diseaseAdd_checkboxitem-2');
        newElemDUL.find('.diseaseitem-3').attr('for', new_entry_id + '_diseaseAdd_checkboxitem-3');
        newElemDUL.find('.diseaseitem-4').attr('for', new_entry_id + '_diseaseAdd_checkboxitem-4');   
        newElemDUL.find('.diseaseitem-5').attr('for', new_entry_id + '_diseaseAdd_checkboxitem-5'); 
        newElemDUL.find('.diseaseitem-6').attr('for', new_entry_id + '_diseaseAdd_checkboxitem-6');      

        //Other div/////////////
        newElemDUL.find('.otherAdd').attr('id', new_entry_id + '_otherAdd').css('display', 'none');
        //Other input box
        newElemDUL.find('.input_other').attr('id', new_entry_id + '_otherAdd_text').attr('name', new_entry_id + '_otherAdd_text').val('');

        newElemDUL.find('.deleteIcon2').click(function()
        {
            newElemDUL.slideUp('slow', function () {$(this).remove(); 
        }); 
        }); 

    newElemDUL.find('.content').show();
    newElemDUL.find('.checkboxitem').hide();
    // insert the new element after the last "duplicatable" input field
        $('#'+entry_id+'_dul'+numDUL).after(newElemDUL);
        $('#ID' + newNumDUL + '_label_radio').focus();



    // right now you can only add 10 DULs. change '10' below to the max number of times the form can be duplicated
        if (newNumDUL == 10)
        $('#btnAddDUL').attr('disabled', true).prop('value', "You've reached the limit");
    });
  

    // enable the "add" button
        $('#btnAddDUL').attr('disabled', false);



});
//for search.htm page -- shows/hids input field when Type of Submission is selected 
$(document).ready(function() {
    $("div.desc").hide();
    $("input[name$='optradio']").click(function() {
        var test = $(this).val();
        $("div.desc").hide();
        $("#" + test).show();
    });


//for legend icon

$('#myTable_wrapper').prepend('<div style="display:inline; float: right;"><img alt="legend for progress icons" src="images/legend-search.gif" /></div>')


    //for ellipsis on search results

$(".ellipsis").hide();

$("#eclick").mouseover(function () {
    $(".ellipsis").slideDown('slow');
});

$(".ellipsis").mouseleave(function () {
    $(".ellipsis").slideUp('slow');
});

});


//delete modal///




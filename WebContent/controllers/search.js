//for search.htm page -- shows/hids input field when Type of Submission is selected 
$(document).ready(function() {
    $("div.desc").hide();
    $("input[name$='optradio']").click(function() {
        var test = $(this).val();
        $("div.desc").hide();
        $("#" + test).show();
    });


    //for ellipsis on search results

$("#ellipsis").hide();

$("#eclick").mouseover(function () {
    $("#ellipsis").slideDown('slow');
});

$("#ellipsis").mouseleave(function () {
    $("#ellipsis").slideUp('slow');
});

});




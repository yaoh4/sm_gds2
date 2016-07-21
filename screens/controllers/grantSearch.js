



$('input[type="radio"]').click(function(){
        if($(this).attr("value")=="grantS"){
            $("#matchingSubmissions").show();
            $("#sgrant").attr('checked',true);
            $("#searchResults").hide();
    } else {
       $("#matchingSubmissions").hide();
       $("#searchResults").show();
    }
       
    });


$('#cancel').click(function(){
        if($(this).attr("id")=="cancel"){
            $("#matchingSubmissions").hide();
         
            $("#searchResults").show();
    } else {
       $("#matchingSubmissions").hide();
       $("#searchResults").show();
    }
       
    });




$('#reset').click(function(){
        if($(this).attr("id")=="reset"){
            $("#matchingSubmissions").hide();
         
            $("#searchResults").hide();
    } else {
       $("#matchingSubmissions").hide();
       $("#searchResults").show();
    }
       
    });




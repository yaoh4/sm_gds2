



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


$(function(){
    $('a.has-spinner, button.has-spinner').click(function() {
        $(this).toggleClass('active');
    });
});


$('.panel-heading span.clickable').click (function(){
    var $this = $(this);
  if(!$this.hasClass('panel-collapsed')) {
    $this.parents('.panel').find('.panel-body').slideUp();

    $this.addClass('panel-collapsed');
    $this.find('i').removeClass('fa-minus-square').addClass('fa-plus-square');
  } else {
    $this.parents('.panel').find('.panel-body').slideDown();
    $this.removeClass('panel-collapsed');
    $this.find('i').removeClass('fa-plus-square').addClass('fa-minus-square');
  }
});



$("#close").click(function(){
    window.close();
});






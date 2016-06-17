

/*
 * Image preview script 
 * powered by jQuery (http://www.jquery.com)
 * 
 * written by Alen Grakalic (http://cssglobe.com)
 * 
 * for more info visit http://cssglobe.com/post/1695/easiest-tooltip-and-image-preview-using-jquery
 *
 */
 
this.imagePreview = function(){ 
    /* CONFIG */
        
        xOffset = -20;
        yOffset = -130;
        
        // these 2 variable determine popup's distance from the cursor
        // you might want to adjust to get the right result
        
    /* END CONFIG */
    $("a.preview").hover(function(e){
        this.t = this.title;
        this.title = "";    
        var c = (this.t != "") ?  this.t : "" ;
        $("body").append("<p id='preview'>" + c + "<br/>"  +  "<img src='"+ this.href +"' alt='Icon Legend' />" + "</p>");                                  
        $("#preview")
            .css("top",(e.pageY - xOffset) + "px")
            .css("left",(e.pageX + yOffset) + "px")
            .fadeIn("fast");                        
    },
    function(){
        this.title = this.t;    
        $("#preview").remove();
    }); 
    $("a.preview").mousemove(function(e){
        $("#preview")
            .css("top",(e.pageY - xOffset) + "px")
            .css("left",(e.pageX + yOffset) + "px");
    });         
};


// starting the script on page load
$(document).ready(function(){
    imagePreview();
});


$(document).ready(function(){
    $(".hoverDiv").hover(function(){
        $(this).css("background", "#f5f5f5");
    }, function(){
        $(this).css("background", "#fff");
    });
});

//for all pages when user doesn't save on form//

//$(function() {

    // Enable on all forms
    //$('form').areYouSure( {'message':'Your profile details are not saved!'} );

});





                                
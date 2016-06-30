

/*
 * Image preview script 
 * powered by jQuery (http://www.jquery.com)
 * 
 * written by Alen Grakalic (http://cssglobe.com)
 * 
 * for more info visit http://cssglobe.com/post/1695/easiest-tooltip-and-image-preview-using-jquery
 *
 */
 



// starting the script on page load
	$(document).ready(function(){
    
//for all pages when user doesn't save on form//
		$('form.dirty-check').areYouSure();
		/*
	    formmodified=0;
	    $('form *').change(function(){
	        formmodified=1;
	    });
	    window.onbeforeunload = confirmExit;
	    function confirmExit() {
	        if (formmodified == 1) {
	            return "New information not saved. Do you wish to leave the page?";
	        }
	    }
	    $("input[name='commit']").click(function() {
	        formmodified = 0;
	    });
	   */ 

//pop up new window
        $('.js-newWindow').click(function (event) {
            event.preventDefault();

            var $this = $(this);

            var url = $this.attr("href");
            var windowName = "popUp";
            var windowSize = $this.data("popup");


            window.open(url, windowName, windowSize);
        });
	});

 
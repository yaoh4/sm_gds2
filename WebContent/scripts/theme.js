

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
        
        // We can attach the `fileselect` event to all file inputs on the page
        $(document).on('change', ':file', function() {
          var input = $(this),
              numFiles = input.get(0).files ? input.get(0).files.length : 1,
              label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
          input.trigger('fileselect', [numFiles, label]);
        });
        
        // We can watch for our custom `fileselect` event like this
        $(document).on('fileselect', ':file', function(event, numFiles, label) {

            var input = $(this).parents('.input-group').find(':text'),
                log = numFiles > 1 ? numFiles + ' files selected' : label;

            if( input.length ) {
                input.val(log);
            } else {
                if( log ) alert(log);
            }

        });
        
    });

//back to top button located in footer.jsp

$(function () {
                        $('[data-toggle="popover"]').popover()
                  })
                 
                        jQuery(document).ready(function($){
                  // browser window scroll (in pixels) after which the "back to top" link is shown
                  var offset = 300,
                        //browser window scroll (in pixels) after which the "back to top" link opacity is reduced
                        offset_opacity = 1200,
                        //duration of the top scrolling animation (in ms)
                        scroll_top_duration = 700,
                        //grab the "back to top" link
                        $back_to_top = $('.cd-top');
 
                  //hide or show the "back to top" link
                  $(window).scroll(function(){
                        ( $(this).scrollTop() > offset ) ? $back_to_top.addClass('cd-is-visible') : $back_to_top.removeClass('cd-is-visible cd-fade-out');
                        if( $(this).scrollTop() > offset_opacity ) {
                              $back_to_top.addClass('cd-fade-out');
                        }
                  });
 
                  //smooth scroll to top
                  $back_to_top.on('click', function(event){
                        event.preventDefault();
                        $('body,html').animate({
                              scrollTop: 0 ,
                             }, scroll_top_duration
                        );
                  });
 
            });




 


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

$(function() {

    $(document).ready(function() {
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
});

});


//pop up new window


        $(document).ready(function(){
            $('.js-newWindow').click(function (event) {
                event.preventDefault();
 
                var $this = $(this);
 
                var url = $this.attr("href");
                var windowName = "popUp";
                var windowSize = $this.data("popup");

 
                window.open(url, windowName, windowSize);
            });
        });
  });

$(function() {

  // We can attach the `fileselect` event to all file inputs on the page
  $(document).on('change', ':file', function() {
    var input = $(this),
        numFiles = input.get(0).files ? input.get(0).files.length : 1,
        label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
    input.trigger('fileselect', [numFiles, label]);
  });

  // We can watch for our custom `fileselect` event like this
  $(document).ready( function() {
      $(':file').on('fileselect', function(event, numFiles, label) {

          var input = $(this).parents('.input-group').find(':text'),
              log = numFiles > 1 ? numFiles + ' files selected' : label;

          if( input.length ) {
              input.val(log);
          } else {
              if( log ) alert(log);
          }

      });
  });
  
});

 
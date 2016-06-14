//for repository.htm page -- shows/hids input field when "other" is selected from Add a Data Repository select

$(document).ready(function () {
    toggleFields(); //call this first so we start out with the correct visibility depending on the selected form values
    //this will call our toggleFields function every time the selection value of our underAge field changes
    $("#repoType").change(function () {
        toggleFields();
    });

});
//this toggles the visibility of our parent permission fields depending on the current selected value of the underAge field
function toggleFields() {
    if ($("#repoType").val() == 6)
        $("#otherRepository").show();
    else
        $("#otherRepository").hide();
}


   

$(document).ready(
    function() {

    	var cloneCount = 1;;
   $(".addRepository").click(function(){
    	 var storageVal = sessionStorage.getItem ($( "#repoType option:selected" ).text());

          $('#newPanel').clone()
          .attr('id', 'id'+ cloneCount++)
          .insertAfter($('[id^=id]:last'))
          .text('id ' + (cloneCount-1));
          //$( "#repoType option:selected" ).text(); //<--For DEMO
          $('.pheader:last > h5').text(storageVal);

          
   		}); 
    }
);

//for general.htm page -- 

$(document).ready(function () {

//toggle for question 1

$('input[name="project"]').bind('change',function(){
    var showOrHide = ($(this).val() == 1) ? true : false;
    $('#whyProject').toggle(showOrHide);
 });

//date picker for Project Start
$('#pStartDate .input-group.date').datepicker({
          orientation: "bottom auto",
          todayHighlight: true
          });

//date picker for Project End    

$('#pEndDate .input-group.date').datepicker({
          orientation: "bottom auto",
          todayHighlight: true
          });      

//date picker for Scientific Review Approval Received   

$('#approvalDate .input-group.date').datepicker({
          orientation: "bottom auto",
          todayHighlight: true
          }); 

//show/hide form fields based on Division/Office/Center dropdown selection's class

$("select").change(function(){
        $(this).find("option:selected").each(function(){
            if($(this).attr("value")=="CCR" || $(this).attr("value")=="DCEG"){
                $(".pdates").hide();
                $(".pdirector").hide();
                 $(".sAsterisk").show();
                 $(".asterisk").show();
               
            }
            else if($(this).attr("value")=="DCB" || $(this).attr("value")=="DCCPS"
			|| $(this).attr("value")=="DCP" || $(this).attr("value")=="DCTD"
			|| $(this).attr("value")=="DEA" || $(this).attr("value")=="OD CCG"
			|| $(this).attr("value")=="OD CCT" || $(this).attr("value")=="OD CGH"
			|| $(this).attr("value")=="OD CRCHD" || $(this).attr("value")=="OD CSSI"
			||$(this).attr("value")=="OD OCC" || $(this).attr("value")=="OD OHAM" || $(this).attr("value")=="OD SBIR"){
               $(".pdirector").show();
                $(".approvalDate").hide();
                 $(".pdates").show();
                 $(".sAsterisk").hide();
                 $(".asterisk").show();
                 $(".eAsterisk").show();

            }         
            else{
                $(".pdates").show();
                 $(".pdirector").show();
                 $(".asterisk").hide();
                 $(".approvalDate").show();
                   $(".sAsterisk").hide();
                    $(".eAsterisk").hide();
                    $(".asterisk").hide();
            }
        });
    }).change();


});
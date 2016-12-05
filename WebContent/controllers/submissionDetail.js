////for page institutional_dashboard.htm
$(document).ready(function() {
	
	var answer=$("#answer").val();
	var projSub=$("#projAbbr").val();
	if(answer == "Optional Submission – Non-NIH Funded"){
		$(".display").hide();
		$(".conditionalDisplay").hide();
	}
	else if(projSub == 'M') {
		$(".display").show();
		$(".conditionalDisplay").hide();
	}
	else {
		$(".display").show();
		$(".conditionalDisplay").show();
	}

	var repoItemsSize = $(".repoItem").length;
	var icItemsSize=$(".icCount").length;
	setStatus("gdsPlan", "gdsPlanDiv");
	if($("#exceptionMemoDiv").is(":visible")) { 
	    setStatus("exceptionMemo", "exceptionMemoDiv");
	}
	setStatus("ic", "icDiv");
	setStatus("bsi", "bsiDiv");
	for(var index=0; index < repoItemsSize; index++) {
		setStatus("repoReg" + index, "repoRegDiv" + index);
		setStatus("repoSub" + index, "repoSubDiv" + index);
	}
	for(var size=0; size < icItemsSize; size++) {
		setStatus("icReg" + size, "icDiv" + size);
	}
	
	$(".helpfile").click(function(){
		
		var url = "/documentation/application/Project_and_Sub-project_Submission_Details_help.pdf";
		var winName = "Submission Details Help File";
		var features = "menubar=yes,scrollbars=yes,resizable=yes,width=800,height=800";
		var newWin = window.open(url, winName, features);
	});
	
	 if($("#subprojectFlag").val().toUpperCase() == 'N') {
		 $(".projectColumn").show();
	 }
	 else {
		 $(".projectColumn").hide();
	 }
	
});

//Show and hide subproject
$('body').on('click', 'a.subproject', function() {
    $(".relatedSubs").slideToggle('500');
    $("i.expandS.fa").toggleClass('fa-plus-square fa-minus-square');
});


//Show and hide Project
$('body').on('click', 'a.project', function() {
    $(".related").slideToggle('500');
    $("i.expand.fa").toggleClass('fa-plus-square fa-minus-square');
});

//Show and hide versions
$('body').on('click', 'a.versions', function() {
    $(".relatedVersions").slideToggle('500');
    $("i.expandV.fa").toggleClass('fa-plus-square fa-minus-square');
});

function setStatus(elem, elemDiv) {
	$("#" + elemDiv).empty();
	var elemVal = $("#" + elem).val();
	if($("#" + elem).val() == 'NOTSTARTED') {
		$("#" + elemDiv).prepend('<img src="../images/pending.png" alt="Not Started" width="18px" height="18px" title="Not Started"/>');
	} else if($("#" + elem).val() == 'INPROGRESS') {
		$("#" + elemDiv).prepend('<img src="../images/inprogress.png" alt="In Progress" width="18px" height="18px" title="In Progress"/>');
	} else if($("#" + elem).val() == 'COMPLETED') {
		$("#" + elemDiv).prepend('<img src="../images/complete.png" alt="Completed" width="18px" height="18px" title="Completed"/>');
	} else {
		$("#" + elemDiv).prepend('<span>N/A</span>');
	}
}
	
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


$('.icDetails').on('click', function(e) {
	  e.preventDefault();
	  var id = $(this).attr("id").replace("icDetails", "contentDivImg");
	  var expandId = $(this).attr("id").replace("icDetails", "");
	  $("#" + id).slideToggle('500');
	  $("#" + expandId + "expand").toggleClass('fa-plus-square fa-minus-square');
})

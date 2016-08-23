////for page institutional_dashboard.htm
$(document).ready(function() {

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
	
});

function setStatus(elem, elemDiv) {
	$("#" + elemDiv).empty();
	var elemVal = $("#" + elem).val();
	if($("#" + elem).val() == 'COMPLETED') {
		$("#" + elemDiv).prepend('<img src="../images/complete.png" alt="Complete" width="18px" height="18px" title="Completed"/>');
	} else if($("#" + elem).val() == 'INPROGRESS') {
		$("#" + elemDiv).prepend('<img src="../images/inprogress.png" alt="In Progress" width="18px" height="18px" title="In Progress"/>');
	} else {
		$("#" + elemDiv).prepend('<img src="../images/pending.png" alt="Not Started" width="18px" height="18px" title="Not Started"/>');
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



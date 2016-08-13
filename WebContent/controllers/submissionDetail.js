////for page institutional_dashboard.htm
$(document).ready(function() {

	var repoItemsSize = $(".repoItem").length;
	setStatus("gdsPlan", "gdsPlanDiv");
	setStatus("ic", "icDiv");
	setStatus("bsi", "bsiDiv");
	for(var index=0; index < repoItemsSize; index++) {
		setStatus("repoReg" + index, "repoRegDiv" + index);
		setStatus("repoSub" + index, "repoSubDiv" + index);
	}
	
});

function setStatus(elem, elemDiv) {
	$("#" + elemDiv).empty();
	var elemVal = $("#" + elem).val();
	if($("#" + elem).val() == 'COMPLETED') {
		$("#" + elemDiv).prepend('<img src="../images/complete.png" alt="Complete" width="18px" height="18px" title="Completed"/>');
	} else if($("#" + elem).val() == 'NOTSTARTED') {
		$("#" + elemDiv).prepend('<img src="../images/pending.png" alt="Not Started" width="18px" height="18px" title="Not Started"/>');
	} else {
		$("#" + elemDiv).prepend('<img src="../images/inprogress.png" alt="In Progress" width="18px" height="18px" title="In Progress"/>');
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


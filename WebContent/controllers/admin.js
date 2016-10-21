

//Search button
function searchUsers() {
	$("#messages").empty();
	var lastName = $('#lastName').val();
	var role = $('#role').val();
	var doc = $('#doc').val();
	var gdsUsersFlag = $('#gdsUsersOnly').is(':checked');
	if($('#lastName').val().length == 0 && $('#role').val().length == 0 && $('#doc').val() == "HNC") {
		var errorMsg = "Please enter at least one of Last Name or GDS User Role as search criteria";
		$("#messages").prepend('<div class="container"><div class="col-md-12"><div class="alert alert-danger"><h3><i class="fa fa-exclamation-triangle fa-lg" aria-hidden="true"></i>&nbsp;Error Status</h3><ul class="errorMessage"><li><span>' + errorMsg + '</span></li></ul></div></div></div>');
		window.scrollTo(0,0);
	} else {	
		if(gdsUsersFlag == true || role != "") {
			url = "searchGdsUsers.action";
		} else {
			url = "searchNedPersons.action";
		}
	    $('button.has-spinner').toggleClass('active');
	    //var lastName = $('#lastName').val().replace(/\s+/g, '');
		$form = $("#admin_form");
	    fd = new FormData($form[0]);
		$.ajax({
		  	url: url,
		  	type: 'post',
		  	processData: false,
		    contentType: false,
		    data: fd,
		  	async:   false,
		  	success: function(msg){
				result = $.trim(msg);
				$("#searchResults").html($(result).find("#searchResults").html());
			}, 
			error: function(){}	
		});
		$('button.has-spinner').toggleClass('active');
		//if(result.indexOf('<div') == 0) {
		//	$("#adminEdit").html(result);
		//}
		//else {
			bootbox.alert(result, function() {
	  			return true;
			});
		//}
	}
	
};


/*function deletePersonRole(userId) {
	//$form = $("#admin_form");
    //fd = new FormData($form[0]);
	$.ajax({
	  	url: url,
	  	type: 'post',
	  	processData: false,
	    contentType: false,
	    data: userId,
	  	async:   false,
	  	success: function(msg){
			result = $.trim(msg);
			$("#searchResults").html($(result).find("#searchResults").html());
		}, 
		error: function(){}	
	});
	$('button.has-spinner').toggleClass('active');

}*/

//Reset button
function resetData() {
	
	$("#messages").empty();
	$('#lastName').val('');
	$('#firstName').val('');
	var parent = $(".tableContent").parent();
	$(".tableContent").remove();
	$(".tableContentOdd").remove();
	parent.append('<tr class="tableContent"><td colspan="4">Nothing found to display.</td></tr>');
};

//confirm Edit
function confirmEdit(elem){
	if($(elem).attr("id") == 'confEdit') {
		var result = "By electing to edit the existing project or sub-project, the new submission will not be created.<br /> Do you wish to continue?";
		var id=$('#prevSubId').val();
		bootbox.confirm(result, function(ans) {
			if (ans) {
				  window.location = '../manage/navigateToSubmissionDetail.action?projectId='+id;
				return true;
			} else {
				return true;
			}
		});
	} 
}


$("#close").click(function(){
    window.close();
});






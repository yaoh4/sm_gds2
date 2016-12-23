$(document).ready(function(){

	$.fn.dataTable.ext.errMode = function ( settings, helpPage, message ) { 
		bootbox.alert(message.substr(message.indexOf("-") + 1), function() {
      		return true;
    	});
	};
	
	reinitTable();
      	
	$("#addNewUser").click(function(){
		$("#role").val("");
		$("#gdsUserRoleDivId").hide();
	});
	$("#gdsUsersOnly").click(function(){
		$("#gdsUserRoleDivId").show();
	});
	$("#addNewUser").click();
});

$(".helpfile").click(function(){
	
	var url = "/documentation/application/Manage_User_Accounts_help.pdf";
	var winName = "Submission Admin Help File";
	var features = "menubar=yes,scrollbars=yes,resizable=yes,width=800,height=800";
	var newWin = window.open(url, winName, features);
});

function reinitTable() {
	//data table initialization
	$("#adminTable").DataTable ( {
            "responsive": false,
            "autoWidth": false,
            "processing": false,
            "serverSide": false,
            "stateSave": true,
            "dom": "<'row'<'col-sm-12'l>>" + "<'row'<'col-sm-5'i><'col-sm-7'p><br/>>" +
            "<'row'<tr>>" + "<'row'<'col-sm-5'i><'col-sm-7'p>>" + "<'row'<'col-sm-12'l>>",
            "searching": false,
            "pageLength": 5,
            "lengthMenu": [5, 10, 25, 50, 100],
            "language": {
                "info": "_TOTAL_ records (_START_ to _END_)  ",
                "lengthMenu": "_MENU_ per page",
                "infoEmpty": " ",
                "emptyTable": "Nothing found to display.",
                "paginate": {
                	previous: '<i id="paginationicon" class="fa fa-caret-left" aria-hidden="true"></i>',
                	next: '<i id="paginationicon" class="fa fa-caret-right" aria-hidden="true"></i>'
                }
            },
            "columnDefs": [ 
            {
            "targets": [ -1 ],
            "orderable": false
            }],
            "initComplete": function(settings, json) {
    			$('button.has-spinner').removeClass('active');
  			}
        });
}
//Search button
function searchUsers() {
	$("#messages").empty();
	var lastName = $('#lastName').val();
	var role = $('#role').val();
	var doc = $('#doc').val();
	var gdsUsersFlag = $('#gdsUsersOnly').is(':checked');
	if(!gdsUsersFlag && $('#lastName').val().length == 0) {
		var errorMsg = "Last Name is required to complete the Search";
		$("#messages").prepend('<div class="container"><div class="col-md-12"><div class="alert alert-danger"><h3><i class="fa fa-exclamation-triangle fa-lg" aria-hidden="true"></i>&nbsp;Error Status</h3><ul class="errorMessage"><li><span>' + errorMsg + '</span></li></ul></div></div></div>');
		window.scrollTo(0,0);
	} else {
		$('button.has-spinner').addClass('active');	
		if(gdsUsersFlag == true || role != "") {
			url = "searchGdsUsers.action";
		} else {
			url = "searchNedPersons.action";
		}
	    
	    //var lastName = $('#lastName').val().replace(/\s+/g, '');
		$form = $("#admin_form");
	    fd = new FormData($form[0]);
		$.ajax({
		  	url: url,
		  	type: 'post',
		  	processData: false,
		    contentType: false,
		    data: fd,
		  	async:   true,
		  	success: function(msg){
				result = $.trim(msg);
				$("#searchResults").html($(result).find("#searchResults").html());
				reinitTable();
			}, 
			error: function(){}	
		});
	}
	
};


function deletePersonRole(nihNetworkId) {
	var warn = "Are you sure you want to delete this role ?"
	$('#userId').val(nihNetworkId);
	$form = $("#admin_form");
    fd = new FormData($form[0]);
	bootbox.confirm(warn, function(ans) {
		if (ans) {
			$.ajax({
				url: 'deleteGdsUser.action',
				type: 'post',
				processData: false,
				contentType: false,
				data: fd,
				async:   false,
				success: function(msg){
					result = $.trim(msg);
					$("#searchResults").html($(result).find("#searchResults").html());
					reinitTable();
				}, 
				error: function(){}	
			});
		}
		return true;
	});

}

//Reset button
function resetData() {
	$('#gdsUsersOnly').prop('checked', false);
	$("#messages").empty();
	$('#lastName').val('');
	$('#firstName').val('');
	$('#doc option:eq(0)').prop('selected', true);
	$('#role option:eq(0)').prop('selected', true);
	
	var parent = $(".tableContent").parent();
	$(".tableContent").remove();
	$(".tableContentOdd").remove();
	parent.append('<tr class="tableContent"><td colspan="4">Nothing found to display.</td></tr>');
};


function createEditRole(networkId) {
	$('#userId').val(networkId);
	if($('#userId').val().length == 0) {
		var errorMsg = "Cannot assign role to selected user: Incomplete NED record";
		$("#messages").prepend('<div class="container"><div class="col-md-12"><div class="alert alert-danger"><h3><i class="fa fa-exclamation-triangle fa-lg" aria-hidden="true"></i>&nbsp;Error Status</h3><ul class="errorMessage"><li><span>' + errorMsg + '</span></li></ul></div></div></div>');
		window.scrollTo(0,0);
		return;
	} 
	$form = $("#admin_form");
    fd = new FormData($form[0]);
	$.ajax({
	  	url: "selectGdsUser.action",
	  	type: 'post',
	  	processData: false,
	    contentType: false,
	    data: fd,
	  	async:   false,
	  	success: function(msg){
			result = $.trim(msg);
			$("#myModal").html($(result).find("#myModal").html());
			var gdsRole = $('#gdsRoleCode').val();
			$("#" + gdsRole).prop('checked', true);
			$('#myModal').modal('show');
		}, 
		error: function(){}	
	});
	$('#myModal').modal('show');
}


function savePersonRole() {
	$form = $("#admin_form");
    fd = new FormData($form[0]);
	$.ajax({
	  	url: "saveGdsUser.action",
	  	type: 'post',
	  	processData: false,
	    contentType: false,
	    data: fd,
	  	async:   false,
	  	success: function(msg){
			result = $.trim(msg);
			$("#searchResults").html($(result).find("#searchResults").html());
			reinitTable();
		}, 
		error: function(){}	
	});
}

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






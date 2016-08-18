//For Search Submission Result Data table
$(document).ready(function(){

	$.fn.dataTable.ext.errMode = function ( settings, helpPage, message ) { 
		bootbox.alert(message.substr(message.indexOf("-") + 1), function() {
      		return true;
    	});
	};
	
	// Converts object with "name" and "value" keys
	// into object with "name" key having "value" as value
	$.fn.serializeObject = function(){
	   var obj = {};
	    
	   $.each( this.serializeArray(), function(i,o){
	      var n = o.name, v = o.value;
	        
	      obj[n] = obj[n] === undefined ? v
	         : $.isArray( obj[n] ) ? obj[n].concat( v )
	         : [ obj[n], v ];
	   });
	    
	   return obj;
	};
	
	//data table initialization
	var submissionTable = $("#submissionTable").DataTable ( {
            "responsive": true,
            "processing": true,
            "serverSide": true,
            "stateSave": true,
            "destroy": true,
            "deferLoading": 0,
            "ajax": {
                "url": "search.action",
                "type": "POST",
                "data": function ( d ) {
                    $.extend( d, $("#search-form").serializeObject());
                },
                "error": function(xhr, error, thrown) {
                	bootbox.alert(error, function() {
              			return true;
            		});
                }
            },
             "buttons": [
                {
                extend: 'colvis',
                columns: [0, 1, 2, 3, 6, 7, 8, 9, 12, 13]
                }
             ],
       
            "dom": "<'row'<'col-sm-6'B <'export'>>>" + "<'row'<'col-sm-5'i><'col-sm-7'p>>" + 
            "<'row'<'col-sm-12'l <'legend'>><'col-sm-6'f>>" +
            "<'row'<'col-sm-12'tr>>" + "<'row'<'col-sm-12'l>>" +
            "<'row'<'col-sm-5'i><'col-sm-7'p>>",
            "columns": [
                { "data": "id"},
                { "data": "grantContractNum"},
                { "data": "projectTitle"},
                { "data": "piLastName"},
                { "data": "piFirstName"},
                { "data": "piEmailAddress"},
                { "data":  "gdsPlanPageStatus"},
                { "data":  "dataSharingExceptionStatus"},
                { "data":  "icPageStatus"},
                { "data":  "bsiPageStatus"},
                { "data":  "repoCount"},
                { "data":  "subprojectCount"},
                { "data":  null},
                { "data":  null}
            ],
            "searching": false,
            "pageLength": 5,
            "lengthMenu": [5, 10, 25, 50, 100],
            "language": {
                "info": "_TOTAL_ project submissions (_START_ to _END_)  ",
                        "lengthMenu":     "_MENU_ per page",
                "infoEmpty": "0 project submissions ",
                "emptyTable": "No project submissions found for the given search criteria",
                "paginate": {
                	previous: '<i id="paginationicon" class="fa fa-caret-left" aria-hidden="true"></i>',
                	next: '<i id="paginationicon" class="fa fa-caret-right" aria-hidden="true"></i>'
                }
            },
            "columnDefs": [ 
                {
                "targets": -1, // Last column, action
                "orderable": false,
                "render": function (data, type, row, meta) {
                    return '<div style="white-space: nowrap; font-size: 14px;"><a href="../manage/navigateToSubmissionDetail.action?projectId=' + row.id + '"><i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="edit" title="edit"></i></a>' +
                    '&nbsp;&nbsp;&nbsp;<a onclick="deleteSubmission(' + row.id + ')" href="javascript: void(0)"><i class="fa fa-trash fa-lg" aria-hidden="true" alt="delete" title="delete"></i></a>' +
                     
                    '&nbsp;&nbsp;&nbsp;<a href="javascript: void(0)"><i class="fa fa-clone fa-lg" aria-hidden="true" alt="Create New Version" alt="Create New Version"></i></a>' +
                    '&nbsp;&nbsp;&nbsp;<a href="../manage/createSubproject.action?projectId=' + row.id + '"><i class="fa fa-folder-open fa-lg" aria-hidden="true" alt="Create Sub-project" tile="Create Sub-project"></a></div></div></div>';
                } },
                {
                "targets": -2, // Repository
                "orderable": false,
                "render": function (data, type, row, meta) {
                	 {
                        return '<a data-toggle="modal" onclick="getRepoInfo(' + row.id + ')" href="#repoModal">' + '<i class="fa fa-file-text fa-lg" aria-hidden="true"></i></a>';
                	}
                	return "";
                } },
                {
                "targets": 0, // First column, view project id
                "render": function (data, type, row, meta) {
                	if(type === 'display') {
                		if(row.subprojectCount != null && row.subprojectCount > 0) {
                			return '<strong><a href="../manage/navigateToSubmissionDetail.action?projectId=' + data + '">'  + data + '</a></strong><br>' +
                			'<a data-toggle="modal" onclick="getSubprojects(' + data + ')" href="#existingSubProjects"><img src="../images/subfolder.gif" alt="sub-project"><i class="fa fa-folder-open" aria-hidden="true"></i>&nbsp;Sub-Projects</a>';
                		}
                		else {
                			return '<strong><a href="../manage/navigateToSubmissionDetail.action?projectId=' + data + '">'  + data + '</a></strong>';
                		}
                	}
                	return data;
                } },
                {
                "targets": 3, // PI email and name
                "render": function (data, type, row, width,meta) {
                    if (type === 'display' && row.piEmailAddress != null && row.piEmailAddress != "" &&
                    		row.piLastName != null && row.piLastName != "" &&
                    		row.piFirstName != null && row.piFirstName != "") {
                        return '<a href="mailto: ' + row.piEmailAddress + '">' + data + ', ' + row.piFirstName + '</a>';
                    } else if (row.piLastName != null && row.piLastName != "" &&
                    		row.piFirstName != null && row.piFirstName != ""){
                    	return data + ', ' + row.piFirstName;
                    } else {
                        return data;
                    }
                } },
                {
                "targets": [6, 7, 8, 9, 12], // Status columns
                "width": "7%",
                "orderable": true,
                "render": function (data, type, row, meta) {
                	if(type === 'display') {
                		if(data == "In Progress") {
                			return '<div class="searchProgess"><img src="../images/inprogress.png" alt="In Progress" title="In Progress" width="18px" height="18px" /></div>'
                		}
                		if(data == "Completed") {
                			return '<div class="searchProgess"><img src="../images/complete.png" alt="Completed" title="Completed" width="18px" height="18px"/></div>'
                		}
                		return '<div class="searchProgess"><img src="../images/pending.png" alt="Not Started" title="Not Started" width="18px" height="18px"></div>'
                	}
                	return data;
                } },
            ]
        });
        
	$("div.legend").html("<div style='display:inline; float: right;'><img alt='legend for progress icons' src='../images/legend-search.gif'></div>");

	$("div.export").html("<a id='export-btn' href='#' aria-controls='submissionTable' tabindex='0' class='dt-button buttons-excel buttons-html5'><span>Excel</span></a>");

	$("#search-form").on('click', '#search-btn', function () {
		submissionTable.ajax.reload(null , true);
		$("#searchResult").show();
	});
	
	$("#search-form").on('click', '#export-btn', function (e) {
		e.preventDefault();
		var param = $.param($('#submissionTable').DataTable().ajax.params());
		var url = "export.action?" + param;
		var winName = "document";
		var features = "menubar=yes,scrollbars=yes,resizable=yes,width=10,height=10";

		var newWin = window.open(url, winName ,features);
	});
	
	//for search.htm page -- shows/hids input field when Type of Submission is selected 
    $("div.desc").hide();
    $("input[name$='optradio']").click(function() {
        var test = $(this).val();
        $("div.desc").hide();
        $("#" + test).show();
    });


    //for legend icon
    $('#myTable_wrapper').prepend('<div style="display:inline; float: right;"><img alt="legend for progress icons" src="../images/legend-search.gif" /></div>')




    // Sub-Project Repository Submission Status
    $('body').on('click', 'a.repoExpand', function() {
        $(this).parent().next("div").slideToggle('500');
        $(this).children("i.expand.fa").toggleClass('fa-plus-square fa-minus-square');
    });

    $("#directorSelect").change(function() {
    	if($(this).find("option:selected").val() != "")
    		$("#directorName").val($(this).find("option:selected").text());
    	else
    		$("#directorName").val("");
    });

});

function getRepoInfo(id) {
	// Get html for modal display
	$.ajax({
	  	url: 'getRepoInfo.action',
	  	data: {projectId: id},
	  	type: 'post',
	  	async:   false,
	  	success: function(msg){
			result = $.trim(msg);
			$('#repoModal').html(result);
		}, 
		error: function(){}	
	});

}

function getSubprojects(id) {
	// Get html for modal display
	$.ajax({
	  	url: 'getSubprojects.action',
	  	data: {projectId: id},
	  	type: 'post',
	  	async:   false,
	  	success: function(msg){
			result = $.trim(msg);
			$('#existingSubProjects').html(result);
		}, 
		error: function(){}	
	});

}

function deleteSubmission(projectId)
{
	var result = "";
	bootbox.confirm("Are you sure you want to delete this submission?", function(ans) {
		  if (ans) {
			  $.ajax({
					url: "deleteProject.action",
					type: "post",
					data: {projectId: projectId},
					success: function(msg){
						result = $.trim(msg);
					}, 
					error: function(){}		
				}).done(function() {
					if(result.indexOf("success") > 0) {
						$('#submissionTable').DataTable().ajax.reload(null , false);
						if($('#existingSubProjects').length) { 
							$('#existingSubProjects').modal('hide');
						}
						bootbox.alert(result, function() {
				  			return true;
						});
					}
					else {
						bootbox.alert(result, function() {
				  			return true;
						});
					}
				});
			    return true;
		  }
	});
}



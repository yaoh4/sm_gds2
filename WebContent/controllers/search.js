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
                'colvis'
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
                { "data":  "dataSharingException"},
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
                    return '<div style="white-space: nowrap;"><a href="../manage/navigateToGeneralInfo.action?projectId=' + row.id + '"><i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="edit" title="edit"></i></a>' +
                    '&nbsp;&nbsp;&nbsp;<a href="deleteProject.action?projectId=' + row.id + '"><i class="fa fa-trash fa-lg" aria-hidden="true" alt="delete" title="delete"></i></a>' +
                    '&nbsp;&nbsp;&nbsp;<a href="#" id="eclick" class="ellipsisR"><img src="../images/ellipsis.png" height="17px" width="16x" alt="open ellipsis"><img src="../images/ellipsisOver.png" height="17px" width="16x" alt="open ellipsis"></a></div>' +
                    '<div class="ellipsis" style="display: none;"><div class="dropbottom"><div class="dropmid" style="white-space: nowrap; height: auto;">' +
                    '<a href="newProjectVersion.htm">Add New Version</a><br>' +
                    '<a href="parentProjectsearch.htm">Add New Subproject</a></div></div></div>';
                } },
                {
                "targets": -2, // Repository
                "orderable": false,
                "render": function (data, type, row, meta) {
                	if(row.repoCount != null && row.repoCount > 0) {
                        return '<a data-toggle="modal" onclick="getRepoInfo(' + row.id + ')" href="#repoModal"><span class="badge">' + row.repoCount + '</span>&nbsp;Submission Status</a>';
                	}
                	return "";
                } },
                {
                "targets": 0, // First column, view project id
                "render": function (data, type, row, meta) {
                	if(type === 'display') {
                		if(row.subprojectCount != null && row.subprojectCount > 0) {
                			return '<strong><a href="../manage/navigateToGeneralInfo.action?projectId=' + data + '">'  + data + '</a></strong><br>' +
                			'<a data-toggle="modal" onclick="getSubprojects(' + data + ')" href="#existingSubProjects"><img src="../images/subfolder.gif" alt="sub-project"><i class="fa fa-folder-open" aria-hidden="true"></i>&nbsp;Existing Sub-Projects</a>';
                		}
                		else {
                			return '<strong><a href="../manage/navigateToGeneralInfo.action?projectId=' + data + '">'  + data + '</a></strong>';
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
                "targets": [6, 7, 8, 9], // Status columns
                "width": "7%",
                "orderable": true,
                "render": function (data, type, row, meta) {
                	if(type === 'display') {
                		if(data == "In Progress") {
                			return '<div class="searchProgess"><img src="../images/inprogress.png" alt="In Progress" title="In Progress" width="18px" height="18px" /></div>'
                		}
                		if(data == "Completed") {
                			return '<div class="searchProgess"><img src="../images/complete.png" alt="Complete" title="Complete" width="18px" height="18px"/></div>'
                		}
                		return '<div class="searchProgess"><img src="../images/pending.png" alt="Pending" title="Pending" width="18px" height="18px"></div>'
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


    //for ellipsis on search results
    $(".ellipsis").hide();
    
    $('body').on('mouseover', 'a.ellipsisR', function() {
    	$(this).parent().next("div").slideDown('slow');
    });

    $('body').on('mouseleave', '.ellipsis', function() {
    	$(this).slideUp('slow');
    });

    // Sub-Project Repository Submission Status
    $('body').on('click', 'a.repoExpand', function() {
        $(this).parent().next("div").slideToggle('500');
        $(this).children("i.expand.fa").toggleClass('fa-plus-square fa-minus-square');
    });

    $("#directorSelect").change(function() {
    	$("#directorName").val($(this).find("option:selected").text());
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




//For Search Parent Submission Result Data table
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
	var parentTable = $("#parentTable").DataTable ( {
            "responsive": true,
            "processing": false,
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
            "dom": "<'row'<'col-sm-5'i><'col-sm-7'p>>" + 
            "<'row'<'col-sm-12'l <'legend'>><'col-sm-6'f>>" +
            "<'row'<'col-sm-12'tr>>" + "<'row'<'col-sm-12'l>>" +
            "<'row'<'col-sm-5'i><'col-sm-7'p>>",
            "columns": [
                { "data": null},
                { "data": "id"},
                { "data": "grantContractNum"},
                { "data": "projectTitle"},
                { "data": "piLastName"},
                { "data": "piFirstName"},
                { "data": "piEmailAddress"},
                { "data":  "gdsPlanPageStatusCode"},
                { "data":  "dataSharingExcepStatusCode"},
                { "data":  "icPageStatusCode"},
                { "data":  "bsiPageStatusCode"},
                { "data":  "subprojectCount"}
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
                "targets": 0, // First column, radio select
                "orderable": false,
                "render": function (data, type, row, meta) {
                	if(type === 'display') {
                		return '<input type="radio" name="projectId" value="' + row.id + '"/>';
                	}
                	return data;
                } },
                {
                "targets": 1, // Second column, view project id
                "render": function (data, type, row, meta) {
                	if(type === 'display') {
                		if(row.subprojectCount != null && row.subprojectCount > 0) {
                			return '<strong><a href="../manage/navigateToGeneralInfo.action?projectId=' + data + '">'  + data + '</a></strong><br>' +
                			'<a data-toggle="modal" onclick="getParentSubprojects(' + data + ')" href="#existingSubProjects"><img src="../images/subfolder.gif" alt="sub-project"><i class="fa fa-folder-open" aria-hidden="true"></i>&nbsp;Existing Sub-Projects</a>';
                		}
                		else {
                			return '<strong><a href="../manage/navigateToGeneralInfo.action?projectId=' + data + '">'  + data + '</a></strong>';
                		}
                	}
                	return data;
                } },
                {
                "targets": 4, // PI email and name
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
                "targets": [7, 8, 9, 10], // Status columns
                "width": "7%",
                "orderable": true,
                "render": function (data, type, row, meta) {
                	if(type === 'display') {
                		if(data == "INPROGRESS") {
                			return '<div class="searchProgess"><img src="../images/inprogress.png" alt="In Progress" title="In Progress" width="18px" height="18px" /></div>'
                		}
                		if(data == "COMPLETED") {
                			return '<div class="searchProgess"><img src="../images/complete.png" alt="Complete" title="Complete" width="18px" height="18px"/></div>'
                		}
                		else if(data == "NOTSTARTED") {
                			return '<div class="searchProgess"><img src="../images/pending.png" alt="Pending" title="Pending" width="18px" height="18px"></div>'
                		}
                	}
                	return data;
                } },
            ]
        });
        
	$("div.legend").html("<div style='display:inline; float: right;'><img alt='legend for progress icons' src='../images/legend-search.gif'></div>");

	$("#search-form").on('click', '#search-btn', function () {
		parentTable.ajax.reload(null , true);
		$("#searchResult").show();
	});
	
	$('#parentTable')
    .on( 'processing.dt', function ( e, settings, processing ) {
        if(processing) {
        	$('button.has-spinner').addClass('active');
        } else {
        	$('button.has-spinner').removeClass('active');
        }
    } )
    .dataTable();
	
	//for search.htm page -- shows/hids input field when Type of Submission is selected 
    $("div.desc").hide();
    $("input[name$='optradio']").click(function() {
        var test = $(this).val();
        $("div.desc").hide();
        $("#" + test).show();
    });


    //for legend icon
    $('#myTable_wrapper').prepend('<div style="display:inline; float: right;"><img alt="legend for progress icons" src="../images/legend-search.gif" /></div>')


    $("#directorSelect").change(function() {
    	if($(this).find("option:selected").val() != "")
    		$("#directorName").val($(this).find("option:selected").text());
    	else
    		$("#directorName").val("");
    });

});


function getParentSubprojects(id) {
	// Get html for modal display
	$.ajax({
	  	url: 'getParentSubprojects.action',
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

//This function executes on click of Next button on Link to Parent page.
function createNewSubProject()
{ 
    var myForm = document.getElementById("search-form");
    myForm.action="/gds/manage/createSubproject";
    myForm.submit();
}

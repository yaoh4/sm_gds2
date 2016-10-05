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
            "responsive": false,
            "autoWidth": false,
            "processing": false,
            "serverSide": true,
            "stateSave": true,
            "destroy": true,
            "fixedHeader": false,
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
                { "data": "id"},
                { "data": "projectSubmissionTitle"},
                { "data": "grantContractNum"},
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
            	"targets": [ 0, 4, 5, 10 ],
            	"visible": false
			   },
               {
                "targets": 1, // Second visible column, view project id link on submission title
                "render": function (data, type, row, meta) {
                	if(type === 'display') {
                		if(row.subprojectCount != null && row.subprojectCount > 0) {
                			if(row.expandSubproject || row.expandRepository) {
                				cssClass = 'detail-control match';
                			} else {
                				cssClass = 'detail-control';
                			}
                			return '<a style="margin-right: 5px;" class="' + cssClass + '" href="javascript: void(0)"><i class="expand fa fa-plus-square" aria-hidden="true"></i></a>' +
            				'<a style="font-weight: bold; font-size: 14px;" href="../manage/navigateToSubmissionDetail.action?projectId=' + row.id + '">' + data + '</a>';
                		}
                		return '<a style="font-weight: bold; font-size: 14px;" href="../manage/navigateToSubmissionDetail.action?projectId=' + row.id + '">' + data + '</a>';
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
                		if(data == "INPROGRESS") {
                			return '<div class="searchProgess"><img src="../images/inprogress.png" alt="In Progress" title="In Progress" width="18px" height="18px" /></div>'
                		}
                		if(data == "COMPLETED") {
                			return '<div class="searchProgess"><img src="../images/complete.png" alt="Complete" title="Complete" width="18px" height="18px"/></div>'
                		}
                		if(data == "NOTSTARTED") {
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
        	$('.detail-control').click();
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

    // Add event listener for opening and closing row details
	$('#parentTable tbody').on('click', 'a.detail-control', function() {
	  var tr = $(this).closest('tr');
	  var row = parentTable.row(tr);
	  if (row.child.isShown()) {
		  row.child.hide();
		  tr.removeClass('shown');
		  $(this).find("i.expand.fa").toggleClass('fa-plus-square fa-minus-square');
	  } else {
		if(row.data().subprojectCount != null && row.data().subprojectCount > 0) {
			row.child(
  			$(
  				'<div class="subproject-div"><a style="font-size: 12px; margin-left: 70px;" class="subproject-control" href="javascript: void(0)">' + '<i class="expand fa fa-plus-square" aria-hidden="true"></i>&nbsp;Sub-projects</a></div>'
  	         ), row.node().className + " subrow"
  			).show();
  		}
		$(this).find("i.expand.fa").toggleClass('fa-plus-square fa-minus-square');
		tr.next().find('.subproject-control').click();
	  }
	});
	
    // Add event listener for opening and closing subproject
    $('#parentTable tbody').on('click', 'a.subproject-control', function() {
    	var tr = $(this).closest('tr').prev();
    	var row = parentTable.row(tr);
        var id = row.data().id;

        // If subproject is shown, this is a toggle to close it.
    	if($(this).hasClass('shown')) {
    		row.child(
    		$(
    			'<div class="subproject-div"><a style="font-size: 12px; margin-left: 70px;" class="subproject-control" href="javascript: void(0)">' + '<i class="expand fa fa-plus-square" aria-hidden="true"></i>&nbsp;Sub-projects</a></div>'
    		 ), tr.get(0).className + " subrow"
    		).show();
    	} else {
    		// Need subproject expand, so retrieve the data
    		$.ajax({
    			url: 'getNewVersionSubprojects.action',
    			data: {'projectId': id},
    			type: 'post',
    			async:   false,
    			success: function(msg){
    				result = $.trim(msg);
    				table = $(result).find(".subproject-table").html();
    				row.child(table, tr.get(0).className + " subrow").show();
    			}, 
    			error: function(){}	
    		});
    		
    	}
    });
    
    //This function executes on click of Next button on Link to Parent page.
    $(".submitButton").click(function( event ) {
    	  event.preventDefault();
    	  if(!$('#selectedProject').val().length > 0) {
  				$('div#messages').show();
  				$(window).scrollTop(0);
  				return false;
    	  }
    	  var myForm = document.getElementById("search-form");
    	  myForm.action="/gds/manage/createNewSubprojectVersion";
    	  myForm.submit();
    });
    
    // Add event listener clicking the radio button
    $('#parentTable tbody').on('click', "input[name='projectId']", function() {
    	$('#selectedProject').val($(this).val());
    });
    
});


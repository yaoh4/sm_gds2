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
                'csv', 'excel', 'pdf', 'print','colvis'
             ],
            "dom": "<'row'<'col-sm-6'B>>" + "<'row'<'col-sm-5'i><'col-sm-7'p>>" + 
            "<'row'<'col-sm-12'l><'col-sm-6'f>>" +
            "<'row'<'col-sm-12'tr>>" + "<'row'<'col-sm-12'l>>" +
            "<'row'<'col-sm-5'i><'col-sm-7'p>>",
            "columns": [
                { "data": "id"},
                { "data": "applicationNum"},
                { "data": "projectTitle"},
                { "data": "piLastName"},
                { "data": "piFirstName"},
                { "data": "piEmailAddress"},
                { "data":  "gdsPlanStatus"},
                { "data":  "dataSharingException"},
                { "data":  "icStatus"},
                { "data":  "bsiStatus"},
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
                "sorting": false,
                "render": function (data, type, row, meta) {
                    return '<a href="../manage/navigateToGeneralInfo.action?projectId=' + row.id + '"><i class="fa fa-pencil-square fa-lg" aria-hidden="true" alt="edit" title="edit"></i></a>' +
                    '&nbsp;&nbsp;&nbsp;<a href="deleteProject.action?projectId=' + row.id + '"><i class="fa fa-trash fa-lg" aria-hidden="true" alt="delete" title="delete"></i></a>';
                } },
                {
                "targets": -2, // Repository
                "sorting": false,
                "render": function (data, type, row, meta) {
                        return '<a class="btn btn-default" data-toggle="modal" onclick="getRepoInfo(' + row.id + ')" href="#repoModal"><i class="fa fa-eye fa-lg" aria-hidden="true" alt="view" title="view"></i>&nbsp;Submission Status</a>';
                } },
                {
                "targets": 0, // First column, view project id
                "render": function (data, type, row, meta) {
                    return '<a href="../manage/navigateToGeneralInfo.action?projectId=' + data + '">'  + data + '</a>';
                } },
                {
                "targets": 3, // PI email and name
                "render": function (data, type, row, width,meta) {
                    if (row.piEmailAddress != null && row.piEmailAddress != "" &&
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
                "render": function (data, type, row, meta) {
                	if(data == "In Progress") {
                    	return '<div class="searchProgess"><img type="button" src="../images/inprogress.png" data-toggle="tooltip" data-content="In progress" alt="In Progress" width="18px" height="18px" /></div>'
                	}
                	if(data == "Completed") {
                    	return '<div class="searchProgess"><img src="../images/complete.png" alt="Complete" width="18px" height="18px"/></div>'
                	}
                    return '<div class="searchProgess"><img src="../images/pending.png" alt="Pending" width="18px" height="18px"></div>'
                } },
            ]
        });
        
	$("#search-form").on('click', '#search-btn', function () {
		submissionTable.ajax.reload(null , true );
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

    $("#eclick").mouseover(function () {
    	$(".ellipsis").slideDown('slow');
    });

    $(".ellipsis").mouseleave(function () {
    	$(".ellipsis").slideUp('slow');
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

function toggleStudy4(showHideDiv, switchImgTag) {
        var ele = document.getElementById(showHideDiv);
        var imageEle = document.getElementById(switchImgTag);
        if(ele.style.display == "block") {
                ele.style.display = "none";
    imageEle.innerHTML = '<img src="images/CriteriaOpen.gif" height="10px" width="10px">';
        }
        else {
                ele.style.display = "block";
                imageEle.innerHTML = '<img src="images/CriteriaClosed.gif" height="10px" width="10px">';
        }
} 


function toggleStudy5(showHideDiv, switchImgTag) {
        var ele = document.getElementById(showHideDiv);
        var imageEle = document.getElementById(switchImgTag);
        if(ele.style.display == "block") {
                ele.style.display = "none";
    imageEle.innerHTML = '<img src="images/CriteriaOpen.gif" height="10px" width="10px">';
        }
        else {
                ele.style.display = "block";
                imageEle.innerHTML = '<img src="images/CriteriaClosed.gif" height="10px" width="10px">';
        }
}




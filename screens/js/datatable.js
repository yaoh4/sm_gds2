/* Formatting function for row details - modify as you need */

function format ( d ) {
    // `d` is the original data object for the row
    return '<table width="100%" border="1" cellpadding="3" class="outsideT3">     '+
    '<tbody><tr>'+
    '<td class="detailsHeader"><strong>Repositories</strong></td>'+
    '<td class="detailsHeader"><strong>Registration Status</strong></td>'+
    '<td class="detailsHeader"><strong>Project Submission Status</strong></td>'+
    '<td class="detailsHeader"><strong>Study Released Status</strong></td>'+
    '</tr>'+
  '<tr>'+
    '<td class="detailsContent">Sequence Read Archive (SRA)</td>'+
    '<td class="detailsContent">In Progress</td>'+
    '<td class="detailsContent">In Progress</td>'+
    '<td class="detailsContent">Yes</td>'+
    '</tr>'+
  '<tr>'+
    '<td class="detailsContentOdd">Database of Genotypes and Phenotypes (dbGaP)</td>'+
    '<td class="detailsContentOdd">Not Started</td>'+
    '<td class="detailsContentOdd">Not Started</td>'+
    '<td class="detailsContentOdd">No</td>'+
    '</tr>'+
'</tbody></table>'+
    '<table>'+
        '<tr>'+
            '<td>Subprojects:</td>'+
            '<td class="subproject-control"></td>'+
        '</tr>'+
    '</table>';

}

function format2 ( ) {
    // `d` is the original data object for the row
    return '<table id="example" class="display" cellspacing="0" width="100%">'
    + '<thead>'
    + ' <tr>'
    + '<th>Name</th>'
        + '<th>Position</th>'
        + '<th>Office</th>'
        + '<th>Extn.</th>'
        + '<th>Start date</th>'
        + '<th>Salary</th>'
        + '</tr>'
    + '</thead>'
    + '<tfoot>'
    + ' <tr>'
    + '<th>Name</th>'
        + '<th>Position</th>'
        + '<th>Office</th>'
        + '<th>Extn.</th>'
        + '<th>Start date</th>'
        + '<th>Salary</th>'
        + '</tr>'
    + '</tfoot>'
    + '</table>';

}
//datatable initialization
$(document).ready(function(){
    var table = $('#myTable').DataTable( {
        responsive: true,
        processing: false,
        serverSide: false,
        select: true,
        stateSave: true,
        dom: 'Bfrtip',
        bFilter: false,
        buttons: [
             'csv', 'excel', 'pdf', 'print','colvis'
        ],
        columns: [
                    
                   
                    { 'data': 'projectId'},
                    { 'data': 'grant'},
                    { 'data': 'title'},
                    { 'data': 'pi'},
                    { 'data': 'gds'},
                    { 'data': 'dse'},
                    { 'data': 'ic'},
                    { 'data': 'bsi'},
                    { 'data': 'repository'},
                    { 'data': 'action'}
                ],
            });
    /*
    $('#myTable tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    } );
    */
     // Add event listener for opening and closing details
    $('#myTable tbody').on('click', 'td.details-control', function() {
        var tr = $(this).closest('tr');
        var row = table.row(tr);
        if (row.child.isShown()) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            // Open this row
            row.child(format(row.data())).show();
            tr.addClass('shown');
        }
    });
    
     // Add event listener for opening and closing subprojects
    $('#myTable tbody').on('click', 'td.subproject-control', function() {
        var tr =$(this).closest('tr');
        if (tr.hasClass("shown")) {
            // This row is already open - close it
            tr.children('.dataTables_wrapper').hide()
            tr.removeClass('shown');
        }
        else {
            // Open this row
            if(tr.find( ":hidden" ).length > 0)
                tr.children('.dataTables_wrapper').show();
            else {
                tr.append(format2());
                 $('#example').DataTable( {
                        //"ajax": '../scripts/objects.txt',
                        columns: [
                                      { 'data': 'name'},
                                      { 'data': 'position'},
                                      { 'data': 'office'},
                                      { 'data': 'extn'},
                                      { 'data': 'start_date'},
                                      { 'data': 'salary'}
                                  ]
                   } );
            }
            tr.addClass('shown');
        }

    });
});


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

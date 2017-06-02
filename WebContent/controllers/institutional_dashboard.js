////for page institutional_dashboard.htm
$(document)
		.ready(
				function() {

					if ($('.alert-danger').is(':visible')) {
						$("#radioCertCompleteY").prop('checked', true);
						$("#certFlag").val("Y");
					}

					// set the correct length for text areas
					showCharCount('#additionalComments', '#charNum2');
					if ($("#icIds").length && $("#icIds") != null && $("#icIds").val().length > 0) {
						var icIdArray = JSON.parse($("#icIds").val());

						// Set all selected ics to checked
						jQuery.each(icIdArray, function(index, value) {

							// this represents value of the ic checkbox
							$("#ic" + value).prop('checked', true);
						});

						// this represents if all the ic`s are checked in a
						// sub-projeect, then enable select all checkbox
						if ($('input[name="ic-selected"]:checked').length == $('input[name="ic-selected"]').length) {
							$("#all").prop('checked', true);
						}
					}

					if ($("#radioCertCompleteY").prop("checked") == true) {
						$("#certFlag").val("Y");
					} else if ($("#radioCertCompleteN").prop("checked") == true) {
						$("#certFlag").val("N");
					}

					if ($("#subprojectFlag").val().toUpperCase() == 'Y') {

						var atLeastOneIsChecked = $('input[name="ic-selected"]:checked').length > 0;
						$("#selectIcs").val(atLeastOneIsChecked);
						$('.icSelect')
								.change(
										function() {
											var atLeastOneIsChecked = $('input[name="ic-selected"]:checked').length > 0;
											$("#selectIcs").val(
													atLeastOneIsChecked);
											if ($('input[name="ic-selected"]:checked').length == $('input[name="ic-selected"]').length) {
												$("#all").prop('checked', true);
											} else {
												$("#all")
														.prop('checked', false);
											}
										});
					}

					$("#all").change(
							function() {
								var checked_status = this.checked;
								$('input[name="ic-selected"]').each(
										function() {
											this.checked = checked_status;
											$(this).parent().children().eq(1)
													.val(checked_status);
										});
							});

					$('input[type="radio"]')
							.click(
									function() {
										if ($("#radioCertCompleteY").prop(
												"checked") == true) {
											$("#certFlag").val("Y");
										} else if ($("#radioCertCompleteN")
												.prop("checked") == true) {
											$("#certFlag").val("N");
										}
									});

					// show/hide Add Additional Instititional Certificates link
					// if not subproject
					if ($("#subprojectFlag").val().toUpperCase() == 'N') {
						/*
						 * if ($("#radioCertCompleteY").prop("checked") == true) {
						 * $('#addICBtn').hide(); } else {
						 * $('#addICBtn').show().css('display', 'inline'); }
						 * 
						 * 
						 * $('input[type="radio"]').click(function() {
						 * if($(this).attr('id') == 'radioCertCompleteN') {
						 * $('#addICBtn').show().css('display', 'inline'); }
						 * else { $('#addICBtn').hide(); } });
						 */

						// show the delete and edit icons only for projects
						$("#actionColumnIC").show();
						$("#actionColumnStudy").show();
						$(".editDeleteBtns").show();
						$(".projectColumn").show();
					} else {
						// Show the checkbox select column only for subprojects
						$("#addStudyBtn").hide();
						$("#subprojectColumn").show();
						$(".subprojectSelect").show();
						$(".projectColumn").hide();
						$('#showSpan').show().css('display', 'inline');
						$(".displaySubProject").attr('colspan', '6');
					}

					if( $('#studyDataTable').length ) {

						initializeStudyTable();
					
					 // Add event listener for opening and closing row details
					 $('#studyDataTable tbody').on('click', 'a.studyDetails', function() {
					  var tr = $(this).closest('tr');
					  var row = table.row(tr);
					  if (row.child.isShown()) {
						  row.child.hide();
						  tr.removeClass('shown');
						  $(this).find("i.expand.fa").toggleClass('fa-plus-square fa-minus-square');
					  } else {
						row.child(
						$(
							$('#' + tr.data('child-name')).html()
						), 
				          row.node().className + " removeStudy" + tr.data('id')
						).show();

						$(this).find("i.expand.fa").toggleClass('fa-plus-square fa-minus-square');
					  }
					 });
					}

					// delete modal///

					$('.btnDelete').on('click', function(e) {
						e.preventDefault();
						var id = $(this).closest('tr').data('id');
						$('#myModal').data('id', id).modal('show');
					});

					$('.btnDeleteIc').on('click', function(e) {
						e.preventDefault();
						var id = $(this).closest('tr').data('id');
						$('#myModalIc').data('id', id).modal('show');
					});

					$('.icDetails').on(
							'click',
							function(e) {
								e.preventDefault();
								var id = $(this).attr("id").replace(
										"icDetails", "contentDivImg");
								var expandId = $(this).attr("id").replace(
										"icDetails", "");
								$("#" + id).slideToggle('500');
								$("#" + expandId + "expand").toggleClass(
										'fa-plus-square fa-minus-square');
							});

					$('#btnDelteYes')
							.click(
									function() {
										var id = $('#myModalIc').data('id');
										var projId = $('#projectId').val();

										$
												.ajax({
													url : 'deleteIc.action',
													type : 'post',
													data : {
														instCertId : id,
														projectId : projId
													},
													async : false,
													success : function(msg) {
														result = $.trim(msg);
														$(
																'img[src="'
																		+ '../images/complete.png'
																		+ '"]')
																.attr('src',
																		'../images/inprogress.png');
													},
													error : function() {
														alert("Could not delete file");
													}
												});

										$('[data-id=' + id + ']').remove();
										$("." + "remove" + id).remove();
										$('#myModalIc').modal('hide');

										var listSize = $("#icListSize").val() - 1;
										$("#icListSize").val(listSize);

										$('#ic_dashboard_form').submit();

									});

					$("#btnDelteYesStudy").click(function() {
						var id = $('#myModal').data('id');
						var projId = $('#projectId').val();
						$.ajax({
							url : 'deleteStudy.action',
							type : 'post',
							data : {
								studyId : id,
								projectId : projId
							},
							async : false,
							success : function(msg) {
								result = $.trim(msg);
							},
							error : function() {
								alert("Could not delete file");
							}
						});

						$('[data-id=' + id + ']').remove();
						$("." + "removeStudy" + id).remove();
						$('#myModal').modal('hide');

						$('#ic_dashboard_form').submit();
					});

					// funtion for accordion study panels

					$(document).on(
							'click',
							'.header',
							function() {

								$(this).next(".content").slideToggle(400);
								$(this).find('.fa').toggleClass(
										'fa-plus-square fa-minus-square');
								$(this).show();
								$("#collapseOne").focus();

							});

					// set the correct length for text areas
					if($("#icComments").length) {
						showCharCount('#icComments', '#charNum');
					}

				});

$(".helpfile")
		.click(
				function() {

					var url = "/documentation/application/Project_Only_Institutional_Certifications_help.pdf";
					var winName = "Submission IC Help File";
					var features = "menubar=yes,scrollbars=yes,resizable=yes,width=800,height=800";
					var newWin = window.open(url, winName, features);
				});

$(".helpfileSubProject")
		.click(
				function() {

					var url = "/documentation/application/Sub-project_Only_Institutional_Certifications_help.pdf";
					var winName = "Submission IC Sub-Project Help File";
					var features = "menubar=yes,scrollbars=yes,resizable=yes,width=800,height=800";
					var newWin = window.open(url, winName, features);
				});

$('#icComments').keyup(function() {
	//set the correct length for text areas
	showCharCount(this, '#charNum');
});

$('#additionalComments').keyup(function() {
	//set the correct length for text areas
	showCharCount(this, '#charNum2');
});

//Edit Study
function edit(elem) {
	var id = $(elem).first().children().val();
	var projectId = $("#projectId").val();
	window.location = '/gds/manage/editStudy.action?studyId=' + id
			+ '&projectId=' + projectId;

}

var table;
function initializeStudyTable() {
	table = $('#studyDataTable').DataTable({
		"dom": 't',
		
		"bPaginate" : false,

		columnDefs : [ {
			targets : [ 'status' ],
			type : 'alt-string'
		}, {
			targets : 'no-sort',
			orderable : false
		} ]
	});

	$("#no-sorting").removeClass('sorting_asc').addClass('sorting_disabled');


}

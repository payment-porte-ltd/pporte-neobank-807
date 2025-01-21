$(window).on("load", function(e) {
    $("#global-loader").fadeOut("slow");
})
$("button[data-dismiss=modal]").click(function(){
	$(".modal").modal('hide');
});
	
	function fnEditPlans(planid, plandesc, planduration, planstatus, planprice, plandesc1, plandesc2, plandesc3, plandesc4 ){
			//console.log('planid is ', planid);
			$('#EditPlanModal').on('show.bs.modal', function () {
				$('input[name="hdneditplanid"]').val(planid);
			$('#editplanid').val(planid);
			$('#editplanname').val(plandesc);
			$('#editplanduration').val(planduration);
			$('#editplanprice').val(planprice);
			
			$('#editplandesc1').val(plandesc1);
			$('#editplandesc2').val(plandesc2);
			$('#editplandesc3').val(plandesc3);
			$('#editplandesc4').val(plandesc4);
			
			$("#editplanselstatus").val(planstatus).change();	
			 });
		
			$('#EditPlanModal').modal('show');
		
		}
		
		$('#btn-editplan').click(function() {
			//Check for the data validation	
			$("#formeditplan").validate({
				rules: {
					editplanname: {
						required: true
					},
					editplanprice: {
						required: true
					},				
					editplandesc1: {
						required: true
					}				
				},
				messages: {
					editplanname: {
						required: 'Please enter Plan Name',
					},
					editplandesc1: {
						required: 'Please enter Plan Description 1',
					},
					editplandesc2: {
						required: 'Please enter Plan Description 2',
					},
					editplandesc3: {
						required: 'Please enter Plan Description 3',
					},
					editplandesc4: {
						required: 'Please enter Plan Description 4',
					},
					editplanprice: {
						required: 'Please enter Plan duration in days',
					}				
				},
				errorElement: "em",
				errorPlacement: function(error, element) {
					// Add the `invalid-feedback` class to the error element
					error.addClass("invalid-feedback");
					if (element.prop("type") === "checkbox") {
						error.insertAfter(element.next("label"));
					} else {
						error.insertAfter(element);
					}
				},
				highlight: function(element, errorClass, validClass) {
					$(element).addClass("is-invalid").removeClass("is-valid");
				},
				unhighlight: function(element, errorClass, validClass) {
					$(element).addClass("is-valid").removeClass("is-invalid");
				}
			});
		
		
			if ($("#formeditplan").valid()) {
		
				$('#formeditplan').attr('action', 'ms');
				$('input[name="qs"]').val('opspln');
				$('input[name="rules"]').val('opseditplanupdate');     
				$('input[name="hdneditplanstatus"]').val($('#editplanselstatus option:selected').val());
				var formData = new FormData($('#formeditplan')[0]);
				
				for (var pair of formData.entries()) {
        			//console.log(pair[0] + " - " + pair[1]);
    			}	
				  // Call Ajax here and submit the form 							
							$.ajaxSetup({
							  beforeSend: function(xhr) {
								  xhr.setRequestHeader('x-api-key' , getAPIKey());
							  }
						  });				
						  $.ajax({
							  url: 'ms',
							  data: formData,
							  processData: false,
							  contentType: false,
							  type: 'POST',
							  success: function (result) {
								  if (result) {
									  var data = JSON.parse(result);
									   if(data.error=='false'){
									//       console.log("no error");
											  Swal.fire({
													   icon: 'success',
													   title: 'Success',
													   text: 'Plan edited successfuly',
													   showConfirmButton: true,
													   confirmButtonText: "Ok",
												   }).then(function() {
														   $('#tempform').attr('action', fnGetOpsServletPath());
														   $('input[name="qs"]').val('opspln');
														   $('input[name="rules"]').val('Product Plans');
														   $("#tempform").submit();	
												   });	
										   }else{
										   //swal.fire('Error message');
												 Swal.fire({
												 title: $('#ops-manage-functions-error-swal-header').text(),              
												 text: $('#ops-manage-functions-error-swal-checkdata').text(), 
												 icon: "error",
												 footer: '<a href="#">'+$('#ops-manage-functions-error-swal-contact-admin').text()+'</a>',
											   showConfirmButton: true,
											   confirmButtonText: "Ok",
											   }).then(function() {
													   //$('#logout-form').attr('action', 'ws');
													  // $('input[name="qs"]').val('lgt');
													  // $('input[name="rules"]').val('lgtdefault');
													  // $("#logout-form").submit();
												// hide all modal
													$(".modal").modal('hide');												
											   });	
										   } 
								  }
							   },
							   error: function() {
								  Swal.fire({
											  icon: 'error',
											  title: 'Oops',
											  text: 'Problem with connection',
											  showConfirmButton: true,
											  confirmButtonText: "Ok",
											  }).then(function() {
												//  $('#logout-form').attr('action', 'ws');
												//  $('input[name="qs"]').val('lgt');
												//  $('input[name="rules"]').val('lgtdefault');
												//  $("#logout-form").submit();
													$(".modal").modal('hide');												
												  
									  });
							  }
						  });				
				
		
			} else {
				Swal.fire({
					icon: 'error',
					title: $('#ops-manage-functions-error-swal-header').text(),
					text: $('#ops-manage-functions-error-swal-checkdata').text()
					//footer: '<a href>Why do I have this issue?</a>'
				})
		
				return false;
			}
		});	
		
		
		$('#btn-add-pricing-plan').click(function() {
			//Check for the data validation	
			$("#addplan-form").validate({
				rules: {
					addplansname: {
						required: true
					},
					addplanid: {
						required: true
					},				
					addplandesc1: {
						required: true
					}
				},
				messages: {
					addplansname: {
						required: 'Please enter Plan Name',
					},	
					addplanid: {
						required: 'Please enter plan Id',
					},			
					addplandesc1: {
						required: 'Please enter Plan Description 1',
					},					
					addplandesc2: {
						required: 'Please enter Plan Description 2',
					},					
					addplandesc3: {
						required: 'Please enter Plan Description 3',
					},					
					addplandesc4: {
						required: 'Please enter Plan Description 4',
					}
				},
				errorElement: "em",
				errorPlacement: function(error, element) {
					// Add the `invalid-feedback` class to the error element
					error.addClass("invalid-feedback");
					if (element.prop("type") === "checkbox") {
						error.insertAfter(element.next("label"));
					} else {
						error.insertAfter(element);
					}
				},
				highlight: function(element, errorClass, validClass) {
					$(element).addClass("is-invalid").removeClass("is-valid");
				},
				unhighlight: function(element, errorClass, validClass) {
					$(element).addClass("is-valid").removeClass("is-invalid");
				}
			});
		
		
			if ($("#addplan-form").valid()) {
		
				$('#addplan-form').attr('action', 'ms');
				$('input[name="qs"]').val('opspln');
				$('input[name="rules"]').val('opsaddnewplan');     
				$('input[name="hdnadduplanstatus"]').val($('#seladdplantatus option:selected').val());
				var formData = new FormData($('#addplan-form')[0]);
				  // Call Ajax here and submit the form 							
							$.ajaxSetup({
							  beforeSend: function(xhr) {
								  xhr.setRequestHeader('x-api-key' , getAPIKey());
							  }
						  });				
						  $.ajax({
							  url: 'ms',
							  data: formData,
							  processData: false,
							  contentType: false,
							  type: 'POST',
							  success: function (result) {
								  if (result) {
									  var data = JSON.parse(result);
									   if(data.error=='false'){
									//       console.log("no error");
											  Swal.fire({
													   icon: 'success',
													   title: $('#ops-manage-pricingplan-success-header').text(),
													   text: data.message,
													   showConfirmButton: true,
													   confirmButtonText: "Ok",
												   }).then(function() {
														   $('#tempform').attr('action', fnGetOpsServletPath());
														   $('input[name="qs"]').val('opspln');
														   $('input[name="rules"]').val('Product Plans');
														   $("#tempform").submit();	
												   });	
										   }else{
										   //swal.fire('Error message');
												 Swal.fire({
												 title: $('#ops-manage-pricingplan-error-swal-header').text(),              
												 text: data.message, 
												 icon: "error",
												 footer: '<a href="#">'+$('#ops-manage-pricingplan-error-swal-contact-admin').text()+'</a>',
											   showConfirmButton: true,
											   confirmButtonText: "Ok",
											   }).then(function() {
													   //$('#logout-form').attr('action', 'ws');
													  // $('input[name="qs"]').val('lgt');
													  // $('input[name="rules"]').val('lgtdefault');
													  // $("#logout-form").submit();
												// hide all modal
													$(".modal").modal('hide');												
											   });	
										   } 
								  }
							   },
							   error: function() {
								  Swal.fire({
											  icon: 'error',
											  title: 'Oops',
											  text: 'Problem with connection',
											  showConfirmButton: true,
											  confirmButtonText: "Ok",
											  }).then(function() {
												 // $('#logout-form').attr('action', 'ws');
												 // $('input[name="qs"]').val('lgt');
												 // $('input[name="rules"]').val('lgtdefault');
												 // $("#logout-form").submit();
												  $(".modal").modal('hide');												
												  
									  }); 
							  }
						  });				
				
		
			} else {
				Swal.fire({
					icon: 'error',
					title: $('#ops-manage-pricingplan-error-swal-header').text(),
					text: $('#ops-manage-pricingplan-error-swal-checkdata').text()
					//footer: '<a href>Why do I have this issue?</a>'
				})
				return false;
			}
		});	

$(window).on("load", function(e) {
    $("#global-loader").fadeOut("slow");
})
// Dismiss modal
$("button[data-dismiss=modal]").click(function(){
	$(".modal").modal('hide');
});
function fnCreateOffers(){
		    $('#create_new_offer').attr('action', fnGetOpsServletPath());
		    $('input[name="qs"]').val('opsmngeoffers');
		    $('input[name="rules"]').val('Create Offers');
			$("#create_new_offer").submit();
	  }
function fnDeleteOffers(accountid,offerid,sellasset){
	if(sellasset==="XLM" ||sellasset==="xlm"){
			$('#div_porte_del_sel_amount').addClass("hidden");
			$('#div_porte_del_buy_amount').removeClass("hidden");
		}else{
			$('#div_porte_del_sel_amount').removeClass("hidden");
			$('#div_porte_del_buy_amount').addClass("hidden");
		}
	$('#delete-porte-form-modal').on('show.bs.modal', function () {
			$('#pporteaccountid').val(accountid);
			$('#pporteofferid').val(offerid);
			$('#portebuyingamount').val(0);
			$('#portesellingamount').val(0);
			 });
			$('#delete-porte-form-modal').modal('show');
	
}
	function fnVeslDeleteOffers(accountid,offerid,sellasset){
		console.log(accountid+'accountid'+offerid+'offerid'+sellasset+'sellasset')
		if(sellasset==="XLM" ||sellasset==="xlm"){
			$('#div_vesl_del_sel_amount').addClass("hidden");
			$('#div_vesl_del_buy_amount').removeClass("hidden");
		}else{
			$('#div_vesl_del_sel_amount').removeClass("hidden");
			$('#div_vesl_del_buy_amount').addClass("hidden");
		}
		$('#delete-vesl-form-modal').on('show.bs.modal', function () {
			$('#veslaccountid').val(accountid);
			$('#veslofferid').val(offerid);
			$('#veslbuyingamount').val(0);
			$('#veslsellingamount').val(0);
			 });
			$('#delete-vesl-form-modal').modal('show');
	}																				
	function fnUpdateOffers(accountid,sellasset,buyasset,amount,offerid,date ){
		if(sellasset==="XLM" ||sellasset==="xlm"){
			$('#div_sel_amount').addClass("hidden");
			$('#div_buy_amount').removeClass("hidden");
		}else{
			$('#div_sel_amount').removeClass("hidden");
			$('#div_buy_amount').addClass("hidden");
		}
			$('#user-form-modal').on('show.bs.modal', function () {
			$('#viewaccountid').val(accountid);
			$('#viewsellasset').val(sellasset);
			$('#viewbuyasset').val(buyasset);
			$('#viewofferid').val(offerid);
			$('#viewdatemodified').val(date);
			 });
			$('#user-form-modal').modal('show');
		
		}
		function fnUpdateVesselOffers(accountid,sellasset,buyasset,amount,offerid,date ){
		if(sellasset==="XLM" ||sellasset==="xlm"){
			$('#div_vesl_sel_amount').addClass("hidden");
			$('#div_vesl_buy_amount').removeClass("hidden");
		}else{
			$('#div_vesl_sel_amount').removeClass("hidden");
			$('#div_vesl_buy_amount').addClass("hidden");
		}
			$('#vessel-form-modal').on('show.bs.modal', function () {
			$('#vessel_viewaccountid').val(accountid);
			$('#vessel_viewsellasset').val(sellasset);
			$('#vessel_viewbuyasset').val(buyasset);
			$('#vessel_viewofferid').val(offerid);
			$('#vessel_viewdatemodified').val(date);
			 });
			$('#vessel-form-modal').modal('show');
		
		}
		$('#btn_delete_vesl_offer').click(function() {
			//Check for the data validation	
			$("#delete_vesl_offer_form").validate({
				rules: { 
					veslpriceunit: {
						required: true
					},
					pporteprivatekey: {
						required: true,
					}
				},
				messages: {
					veslpriceunit: {
						required: 'Please enter Price of 1 VESL unit per XLM',
					},
					pporteprivatekey: {
						required: 'Please enter the Private Key',
					}
				},
	errorElement: "em",
	errorPlacement: function ( error, element ) {
		// Add the `invalid-feedback` class to the error element
		error.addClass( "invalid-feedback" );
		if ( element.prop( "type" ) === "checkbox" ) {
			error.insertAfter( element.next( "label" ) );
		} else {
			error.insertAfter( element );
		}
	},
	highlight: function ( element, errorClass, validClass ) {
		$( element ).addClass( "is-invalid" ).removeClass( "is-valid" );
	},
	unhighlight: function (element, errorClass, validClass) {
		$( element ).addClass( "is-valid" ).removeClass( "is-invalid" );
	}
	
 });
	if ($("#delete_vesl_offer_form").valid()) {
		
				$('#delete_vesl_offer_form').attr('action', fnGetOpsServletPath());
				$('input[name="qs"]').val('opscrypto');
				$('input[name="rules"]').val('ops_delete_vesl_offer');     
				console.log('rule is ' + $('input[name="rules"]').val());
				  
				//$("#editmerchuser-form").submit();
				    $('#spinner-div').show();//Load button clicked show spinner
				var formData = new FormData($('#delete_vesl_offer_form')[0]);
				$.ajaxSetup({
					beforeSend: function(xhr) {
						xhr.setRequestHeader('x-api-key', getAPIKey());
					}
				});
					  $.ajax({
						  url: 'ms',
						  data: formData,
						  processData: false,
						  contentType: false,
						  type: 'POST',
						  success: function (result) {
							$('#spinner-div').hide();//Request is complete so hide spinner
							  if (result) {
								  var data = JSON.parse(result);
								   if(data.error=='false'){
								//       console.log("no error");
										  Swal.fire({
												   icon: 'success',
												   title: " Successful",
												   text: "VESL offer has been deleted.",
												   showConfirmButton: true,
												   confirmButtonText: "Ok",
												   closeOnConfirm: true,
											   }).then(function() {
													// Call login page
														$('#delete_vesl_offer_form').attr('action', fnGetOpsServletPath());
														$('input[name="qs"]').val('opscrypto');
														$('input[name="rules"]').val('View Offers');     
														$("#delete_vesl_offer_form").submit();
											   });	
									   }else{
									   //swal.fire('Error message');
											 Swal.fire({
											 title: "Oops",              
											 text: "There was a problem when deleting the offer, please try again later", 
											 icon: "error",
										   showConfirmButton: true,
										   confirmButtonText: "Ok",
										   closeOnConfirm: true,
										   }).then(function() {
												   $('#delete_vesl_offer_form').attr('action', fnGetOpsServletPath());
														$('input[name="qs"]').val('opscrypto');
														$('input[name="rules"]').val('View Offers');     
														$("#delete_vesl_offer_form").submit();
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
										  closeOnConfirm: true,
										  }).then(function() {
											$('#delete_pporte_offer_form').attr('action', fnGetOpsServletPath());
											$('input[name="qs"]').val('opscrypto');
											$('input[name="rules"]').val('View Offers');     
											$("#delete_pporte_offer_form").submit();
															  });
						  }
					  });
	
	
  }else  {
				  Swal.fire({
					  icon: 'error',
					  title: "Oops",
					  text: "Please check your data"
				  })
				  return false;
	 }
	
})
		
			$('#btn_delete_pporte_offer').click(function() {
			//Check for the data validation	
			$("#delete_pporte_offer_form").validate({
				rules: { 
					pportepriceunit: {
						required: true
					},
					pporteprivatekey: {
						required: true,
					}
				},
				messages: {
					pportepriceunit: {
						required: 'Please enter Price of 1 PORTE unit per XLM',
					},
					pporteprivatekey: {
						required: 'Please enter the Private Key',
					}
				},
	errorElement: "em",
	errorPlacement: function ( error, element ) {
		// Add the `invalid-feedback` class to the error element
		error.addClass( "invalid-feedback" );
		if ( element.prop( "type" ) === "checkbox" ) {
			error.insertAfter( element.next( "label" ) );
		} else {
			error.insertAfter( element );
		}
	},
	highlight: function ( element, errorClass, validClass ) {
		$( element ).addClass( "is-invalid" ).removeClass( "is-valid" );
	},
	unhighlight: function (element, errorClass, validClass) {
		$( element ).addClass( "is-valid" ).removeClass( "is-invalid" );
	}
	
 });
	if ($("#delete_pporte_offer_form").valid()) {
		
				$('#delete_pporte_offer_form').attr('action', fnGetOpsServletPath());
				$('input[name="qs"]').val('opscrypto');
				$('input[name="rules"]').val('ops_delete_pporte_offer');     
				console.log('rule is ' + $('input[name="rules"]').val());
				  
				//$("#editmerchuser-form").submit();
				   $('#spinner-div').show();//Load button clicked show spinner
				var formData = new FormData($('#delete_pporte_offer_form')[0]);
				$.ajaxSetup({
					beforeSend: function(xhr) {
						xhr.setRequestHeader('x-api-key', getAPIKey());
					}
				});
					  $.ajax({
						  url: 'ms',
						  data: formData,
						  processData: false,
						  contentType: false,
						  type: 'POST',
						  success: function (result) {
							$('#spinner-div').hide();//Request is complete so hide spinner
							  if (result) {
								  var data = JSON.parse(result);
								   if(data.error=='false'){
								//       console.log("no error");
										  Swal.fire({
												   icon: 'success',
												   title: " Successful",
												   text: "PORTE offer has been deleted.",
												   showConfirmButton: true,
												   confirmButtonText: "Ok",
												   closeOnConfirm: true,
											   }).then(function() {
													// Call login page
														$('#delete_pporte_offer_form').attr('action', fnGetOpsServletPath());
														$('input[name="qs"]').val('opscrypto');
														$('input[name="rules"]').val('View Offers');     
														$("#delete_pporte_offer_form").submit();
											   });	
									   }else{
									   //swal.fire('Error message');
											 Swal.fire({
											 title: "Oops",              
											 text: "There was a problem when deleting the offer, please try again later", 
											 icon: "error",
										   showConfirmButton: true,
										   confirmButtonText: "Ok",
										   closeOnConfirm: true,
										   }).then(function() {
												   $('#delete_pporte_offer_form').attr('action', fnGetOpsServletPath());
														$('input[name="qs"]').val('opscrypto');
														$('input[name="rules"]').val('View Offers');     
														$("#delete_pporte_offer_form").submit();
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
										  closeOnConfirm: true,
										  }).then(function() {
											$('#delete_pporte_offer_form').attr('action', fnGetOpsServletPath());
											$('input[name="qs"]').val('opscrypto');
											$('input[name="rules"]').val('View Offers');     
											$("#delete_pporte_offer_form").submit();
															  });
						  }
					  });
	
	
  }else  {
				  Swal.fire({
					  icon: 'error',
					  title: "Oops",
					  text: "Please check your data"
				  })
				  return false;
	 }
	
})
		
	$('#btn_update_vessel_offer').click(function() {
			//Check for the data validation	
			$("#update_vesl_offer_form").validate({
				rules: { 
					vesel_buyingamount: {
						required: true
					},
					vesel_sellingamount: {
						required: true
					},
					vesel_sellpriceunit: {
						required: true
					},
					vesel_sellprivatekey: {
						required: true,
					}
				},
				messages: {
					vesel_sellingamount: {
						required: 'Please enter amount you are selling',
					},
					
					vesel_buyingamount: {
						required: 'Please enter amount you are buying',
					},
					vesel_sellpriceunit: {
						required: 'Please enter Price of 1 VESL unit per XLM',
					},
					vesel_sellprivatekey: {
						required: 'Please enter the Private Key',
					}
				},
	errorElement: "em",
	errorPlacement: function ( error, element ) {
		// Add the `invalid-feedback` class to the error element
		error.addClass( "invalid-feedback" );
		if ( element.prop( "type" ) === "checkbox" ) {
			error.insertAfter( element.next( "label" ) );
		} else {
			error.insertAfter( element );
		}
	},
	highlight: function ( element, errorClass, validClass ) {
		$( element ).addClass( "is-invalid" ).removeClass( "is-valid" );
	},
	unhighlight: function (element, errorClass, validClass) {
		$( element ).addClass( "is-valid" ).removeClass( "is-invalid" );
	}
	
 });
	if ($("#update_vesl_offer_form").valid()) {
		
				$('#update_vesl_offer_form').attr('action', fnGetOpsServletPath());
				$('input[name="qs"]').val('opscrypto');
				$('input[name="rules"]').val('ops_update_vesl_offer');     
				console.log('rule is ' + $('input[name="rules"]').val());
				  
				//$("#editmerchuser-form").submit();
				 $('#spinner-div').show();//Load button clicked show spinner
				var formData = new FormData($('#update_vesl_offer_form')[0]);
				$.ajaxSetup({
					beforeSend: function(xhr) {
						xhr.setRequestHeader('x-api-key', getAPIKey());
					}
				});
					  $.ajax({
						  url: 'ms',
						  data: formData,
						  processData: false,
						  contentType: false,
						  type: 'POST',
						  success: function (result) {
							$('#spinner-div').hide();//Request is complete so hide spinner
							  if (result) {
								  var data = JSON.parse(result);
								   if(data.error=='false'){
								//       console.log("no error");
										  Swal.fire({
												   icon: 'success',
												   title: " Successful",
												   text: "VESL Offer was updated successfully",
												   showConfirmButton: true,
												   confirmButtonText: "Ok",
												   closeOnConfirm: true,
											   }).then(function() {
													// Call login page
														$('#update_vesl_offer_form').attr('action', fnGetOpsServletPath());
														$('input[name="qs"]').val('opscrypto');
														$('input[name="rules"]').val('View Offers');     
														$("#update_vesl_offer_form").submit();
											   });	
									   }else{
									   //swal.fire('Error message');
											 Swal.fire({
											 title: "Oops",              
											 text: "There was a problem when updating the offer, please try again later", 
											 icon: "error",
										   showConfirmButton: true,
										   confirmButtonText: "Ok",
										   closeOnConfirm: true,
										   }).then(function() {
												   $('#update_vesl_offer_form').attr('action', fnGetOpsServletPath());
														$('input[name="qs"]').val('opscrypto');
														$('input[name="rules"]').val('View Offers');     
														$("#update_vesl_offer_form").submit();
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
										  closeOnConfirm: true,
										  }).then(function() {
											$('#update_vesl_offer_form').attr('action', fnGetOpsServletPath());
											$('input[name="qs"]').val('opscrypto');
											$('input[name="rules"]').val('View Offers');     
											$("#update_vesl_offer_form").submit();
															  });
						  }
					  });
	
	
  }else  {
				  Swal.fire({
					  icon: 'error',
					  title: "Oops",
					  text: "Please check your data"
				  })
				  return false;
	 }
	
})

		$('#btn_update_pporte_offer').click(function() {
			//Check for the data validation	
			$("#update_pporte_offer_form").validate({
				rules: { 
					buyingamount: {
						required: true
					},
					sellingamount: {
						required: true
					},
					sellpriceunit: {
						required: true
					},
					sellprivatekey: {
						required: true,
					}
				},
				messages: {
					sellingamount: {
						required: 'Please enter amount you are selling',
					},
					
					buyingamount: {
						required: 'Please enter amount you are buying',
					},
					sellpriceunit: {
						required: 'Please enter Price of 1 PPORTE unit per XLM',
					},
					sellprivatekey: {
						required: 'Please enter the Private Key',
					}
				},
	errorElement: "em",
	errorPlacement: function ( error, element ) {
		// Add the `invalid-feedback` class to the error element
		error.addClass( "invalid-feedback" );
		if ( element.prop( "type" ) === "checkbox" ) {
			error.insertAfter( element.next( "label" ) );
		} else {
			error.insertAfter( element );
		}
	},
	highlight: function ( element, errorClass, validClass ) {
		$( element ).addClass( "is-invalid" ).removeClass( "is-valid" );
	},
	unhighlight: function (element, errorClass, validClass) {
		$( element ).addClass( "is-valid" ).removeClass( "is-invalid" );
	}
	
 });
	if ($("#update_pporte_offer_form").valid()) {
		
				$('#update_pporte_offer_form').attr('action', fnGetOpsServletPath());
				$('input[name="qs"]').val('opscrypto');
				$('input[name="rules"]').val('ops_update_pporte_offer');     
				console.log('rule is ' + $('input[name="rules"]').val());
				  
				//$("#editmerchuser-form").submit();
				$('#spinner-div').show();//Load button clicked show spinner
				var formData = new FormData($('#update_pporte_offer_form')[0]);
				$.ajaxSetup({
					beforeSend: function(xhr) {
						xhr.setRequestHeader('x-api-key', getAPIKey());
					}
				});
					  $.ajax({
						  url: 'ms',
						  data: formData,
						  processData: false,
						  contentType: false,
						  type: 'POST',
						  success: function (result) {
							$('#spinner-div').hide();//Request is complete so hide spinner
							  if (result) {
								  var data = JSON.parse(result);
								   if(data.error=='false'){
								//       console.log("no error");
										  Swal.fire({
												   icon: 'success',
												   title: " Successful",
												   text: "PORTE Offer was updated successfully",
												   showConfirmButton: true,
												   confirmButtonText: "Ok",
												   closeOnConfirm: true,
											   }).then(function() {
													// Call login page
														$('#update_pporte_offer_form').attr('action', fnGetOpsServletPath());
														$('input[name="qs"]').val('opscrypto');
														$('input[name="rules"]').val('View Offers');     
														$("#update_pporte_offer_form").submit();
											   });	
									   }else{
									   //swal.fire('Error message');
											 Swal.fire({
											 title: "Oops",              
											 text: "There was a problem when updating the offer, please try again later", 
											 icon: "error",
										   showConfirmButton: true,
										   confirmButtonText: "Ok",
										   closeOnConfirm: true,
										   }).then(function() {
												$('#update_vesl_offer_form').attr('action', fnGetOpsServletPath());
												$('input[name="qs"]').val('opscrypto');
												$('input[name="rules"]').val('View Offers');     
												$("#update_vesl_offer_form").submit();
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
										  closeOnConfirm: true,
										  }).then(function() {
											$('#update_vesl_offer_form').attr('action', fnGetOpsServletPath());
														$('input[name="qs"]').val('opscrypto');
														$('input[name="rules"]').val('View Offers');     
														$("#update_vesl_offer_form").submit();
															  });
						  }
					  });
	
	
  }else  {
				  Swal.fire({
					  icon: 'error',
					  title: "Oops",
					  text: "Please check your data"
				  })
				  return false;
	 }
	
})
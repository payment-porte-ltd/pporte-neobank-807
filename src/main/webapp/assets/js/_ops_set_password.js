
 $(window).on("load", function(e) {
  $("#global-loader").fadeOut("slow");
})

  $('#btn-test-submit').click(function() {
	  //Check for the data validation	
	  $("#set-password-form").validate({
		  rules: {
			  old_password: {
				  required: true,
			  },
			  new_password: {
				  required: true,
				   minlength : 5
			  },
			  confirm_password:{
                equalTo : "#new_password"
			},
		  },
		  messages: {
			   old_password: {
				  required: 'Please enter your current password',
			  },
			  new_password: {
				  required: 'Please enter your new password',
				   minlength : 'Your Password is too weak'
			  },
			  confirm_password:{
                equalTo : 'Confirm password does not match New password'
			},
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
  
  
	  if ($("#set-password-form").valid()) {
		  $('#set-password-form').attr('action', 'ms');
		  $('#set-password-form input[name="userid"]').val();
		  var formData = new FormData($('#set-password-form')[0]);

		  formData.append('qs', 'partdash');
		  formData.append('rules', 'ops_set_password');

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
												 text: data.message,
												 showConfirmButton: true,
												 confirmButtonText: "Ok",
											 }).then(function() {
													 $('#get-page-form').attr('action', fnGetOpsServletPath());
													 $('input[name="qs"]').val('lgt');
													 $('input[name="rules"]').val('opslgtdefault');
													 $("#get-page-form").submit();	
											 });	//lgt,Sign Out
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
	   
		  return false;
	  }
  });	

  



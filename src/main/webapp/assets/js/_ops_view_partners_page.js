$("button[data-dismiss=modal]").click(function(){
	$(".modal").modal('hide');
});

$(window).on("load", function(e) {
	$("#global-loader").fadeOut("slow");
})


function fnEditRow(userId, userName, email, contact, location, currency, publicKey, status){
	$('#exampleModal4').on('show.bs.modal', function () {
		$('#efname').val(userName);
		$('#euemail').val(email);
		$('#ephoneno').val(contact);
		$('#elocation').val(location);
		//$('#epublickey').val(publicKey);
		$('#selcurrency').val(currency).prop('selected', true);
		$('#status').val(status).prop('selected', true);
	});
	$('#exampleModal4').modal('show');
}

function editPartner(){
	$( "#edit-user-form" ).validate( {
		rules: {
			efname: {
				required: true,
				minlength: 5
			},
			euemail: {
				required: true,
				email: true
			},
			ephoneno: {
				required: true
			},
			
			epublickey: {
				required: true
			},
			selcurrency: {
				required: true
			},
			status:{
				required: true
			}
		},
		messages: {
			efname: {
				minlength: 'Full name must consist of at least 5 characters',
				required: 'Please enter full name'
			},
			euemail: {
				required: 'Please enter email',
				email: 'This must be email'
			},
			ephoneno: {
				required: 'Phone is required',
				
			},
			elocation: {
				required: 'Location is required'
			},
			
			selcurrency:{
				required: 'Currency is required'
			},
			status:{
				required: 'Status is required'
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
	if($( "#edit-patner-form" ).valid()){
		$('#edit-patner-form input[name="qs"]').val('opsremit');
		$('#edit-patner-form input[name="rules"]').val('editpartner');
		
		var form = $('#edit-patner-form')[0];
		var formData = new FormData(form);

		for (var pair of formData.entries()) {
		}	
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
						Swal.fire({
									icon: 'success',
									text: data.message,
									showConfirmButton: true,
									confirmButtonText: "Ok",
								}).then(function() {
									$('#get-page-form').attr('action', fnGetOpsServletPath());
									$('input[name="qs"]').val('opsremit');
									$('input[name="rules"]').val('View Partners');
									$("#get-page-form").submit();
								});	
						}else{
							Swal.fire({
							text: data.message, 
							icon: "error",
							showConfirmButton: true,
							confirmButtonText: "Ok",
							}).then(function() {
								
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
							
				});
			}
		}); 
	}
}

function addPatner(){

	$( "#add-partners-form" ).validate( {
		rules: {
			fname: {
				required: true,
				minlength: 5
			},
			uemail: {
				required: true,
				email: true
			},
			phoneno: {
				required: true
			},
			location: {
				required: true
			},
		
			publickey: {
				required: true
			},
			addselcurrency:{
				required: 'Currency is required'
			}
		},
		messages: {
				fname: {
					minlength: 'Full name must consist of at least 5 characters',
					required: 'Please enter full name'
				},
				euemail: {
					required: 'Please enter email',
					email: 'This must be email'
				},
				phoneno: {
					required: 'Phone number is required',
					
				},
				location: {
					required: 'Location is required'
				},
				publickey: {
					required: 'Public key is required'
				},
				addselcurrency:{
					required: 'Currency is required'
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
	if($( "#add-partners-form" ).valid()){
		$('#add-partners-form input[name="qs"]').val('opsremit');
		$('#add-partners-form input[name="rules"]').val('addpartner');
		
		var form = $('#add-partners-form')[0];
		var formData = new FormData(form);

		for (var pair of formData.entries()) {
		}	
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
						Swal.fire({
									icon: 'success',
									text: data.message,
									showConfirmButton: true,
									confirmButtonText: "Ok",
								}).then(function() {
									$('#get-page-form').attr('action', fnGetOpsServletPath());
									$('input[name="qs"]').val('opsremit');
									$('input[name="rules"]').val('View Partners');
									$("#get-page-form").submit();
								});	
						}else{
							Swal.fire({
							text: data.message, 
							icon: "error",
							showConfirmButton: true,
							confirmButtonText: "Ok",
							}).then(function() {
								
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
							
				});
			}
		}); 
	}
}

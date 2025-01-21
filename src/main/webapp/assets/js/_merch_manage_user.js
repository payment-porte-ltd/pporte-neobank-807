$("button[data-dismiss=modal]").click(function(){
	$(".modal").modal('hide');
});

$(window).on("load", function(e) {
	$("#global-loader").fadeOut("slow");
})

function fnEditRow(fullName, email, phoneNo, idNo, designation, status, userLvel){
	$('#editModal').on('show.bs.modal', function () {
		$('#efullname').val(fullName);
		$('#eemail').val(email);
		$('#ephoneno').val(phoneNo);
		$('#eidno').val(idNo);
		$('#edesignation').val(designation);
		$('#estatus').val(status).prop('selected', true);
		$('#euseraccess').val(userLvel).prop('selected', true);
	});
	$('#editModal').modal('show');
}

function addMerchantUser(){
	$( "#add-user-form" ).validate( {
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
				required: true,
				minlength: 5
			},
			idno: {
				required: true,
				minlength: 5
			},
			designation: {
				required: true,
				minlength: 3
			},
			seluserlevel: {
				required: true
			}
		},
		messages: {
			fname: {
				minlength: $('#add-user-data-validation-error-fname-length').text(),
				required: $('#add-user-data-validation-error-fname').text()
			},
			uemail: {
				required: $('#add-user-data-validation-error-uemail').text(),
				email: $('#add-user-data-validation-error-uemail-email').text()
			},
			phoneno: {
				required: $('#add-user-data-validation-error-phoneno').text(),
				minlength: $('#add-user-data-validation-error-phoneno-length').text()
			},
			idno: {
				required: $('#add-user-data-validation-error-idno').text(),
				minlength: $('#add-user-data-validation-error-idno-length').text()
			},
			designation: {
				required: $('#add-user-data-validation-error-designation').text(),
				minlength: $('#add-user-data-validation-error-designation-length').text()
			},
			seluserlevel: {
				required: $('#add-user-data-validation-error-seluserlevel').text()
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
	if($( "#add-user-form" ).valid()){
		$('#add-user-form input[name="qs"]').val('reg');
		$('#add-user-form input[name="rules"]').val('addmerchantuser');
		
		var form = $('#add-user-form')[0];
		var formData = new FormData(form);

		for (var pair of formData.entries()) {
			console.log(pair[0] + " - " + pair[1]);
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
					console.log('data', data);
					if(data.error=='false'){
						Swal.fire({
									icon: 'success',
									text: data.message,
									showConfirmButton: true,
									confirmButtonText: "Ok",
								}).then(function() {
									$('#get-page-form').attr('action', 'ws');
									$('input[name="qs"]').val('merchprf');
									$('input[name="rules"]').val('Manage users');
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

function editMerchantUser(){
	$( "#edit-user-form" ).validate( {
		rules: {
			efullname: {
				required: true,
				minlength: 5
			},
			eemail: {
				required: true,
				email: true
			},
			ephoneno: {
				required: true,
				minlength: 5
			},
			eidno: {
				required: true,
				minlength: 5
			},
			edesignation: {
				required: true,
				minlength: 3
			}
		},
		messages: {
			efullname: {
				minlength: $('#edit-user-data-validation-error-fname-length').text(),
				required: $('#edit-user-data-validation-error-fname').text()
			},
			eemail: {
				required: $('#edit-user-data-validation-error-uemail').text(),
				email: $('#edit-user-data-validation-error-uemail-email').text()
			},
			ephoneno: {
				required: $('#edit-user-data-validation-error-phoneno').text(),
				minlength: $('#edit-user-data-validation-error-phoneno-length').text()
			},
			eidno: {
				required: $('#edit-user-data-validation-error-idno').text(),
				minlength: $('#edit-user-data-validation-error-idno-length').text()
			},
			edesignation: {
				required: $('#edit-user-data-validation-error-designation').text(),
				minlength: $('#edit-user-data-validation-error-designation-length').text()
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
	if($( "#edit-user-form" ).valid()){
		$('#edit-user-form input[name="qs"]').val('reg');
		$('#edit-user-form input[name="rules"]').val('editmerchantuser');
		
		var form = $('#edit-user-form')[0];
		var formData = new FormData(form);

		for (var pair of formData.entries()) {
			console.log(pair[0] + " - " + pair[1]);
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
					console.log('data', data);
					if(data.error=='false'){
						Swal.fire({
									icon: 'success',
									text: data.message,
									showConfirmButton: true,
									confirmButtonText: "Ok",
								}).then(function() {
									$('#get-page-form').attr('action', 'ws');
									$('input[name="qs"]').val('merchprf');
									$('input[name="rules"]').val('Manage users');
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
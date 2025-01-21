$('#btn-addxlmaccount').click(function() {
		//Check for the data validation	
		$("#addassetpricing-from").validate({
			rules: {
				addamount: {
					required: true
				},
				addpublickey: {
					required: true,
				},
				addprivatekey: {
					required: true
				}
				,
				seladdtatus: {
					required: true
				}
			},
			messages: {
				addamount: {
					required: 'Please enter amount',
				},
				addpublickey: {
					required: 'Please enter public key',
				},
				addprivatekey: {
					required: 'Please enter private key',
				},
				seladdtatus: {
					required: 'Please select status',
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
	
		if ($("#addassetpricing-from").valid()) { 
			$('#addassetpricing-from').attr('action', 'ms');
			$('input[name="qs"]').val('fundacc');
			$('input[name="rules"]').val('addnewxlmaccount');
			$('input[name="hdnaddseladdtatus"]').val($('#seladdtatus :selected').val());
		
			//$("#addassetpricing-from").submit();
			var formData = new FormData($('#addassetpricing-from')[0]);
			$.ajaxSetup({
				beforeSend: function(xhr) {
					xhr.setRequestHeader('x-api-key', getAPIKey());
				}
			});
			$('#spinner-div').show();//Load button clicked show spinner
			$.ajax({
				url: 'ms',
				data: formData,
				processData: false,
				contentType: false,
				type: 'POST',
				success: function(result) {
					$('#spinner-div').hide();//Request is complete so hide spinner
					var data = JSON.parse(result);
					if (data.error == 'false') {
						//alert('lgtoken is '+data.token)
						Swal.fire({
							icon: 'success',
							title: 'Good',
							text: data.message,
							showConfirmButton: true,
							confirmButtonText: "Ok",
						}).then(function() {
							$('#addassetpricing-from').attr('action', fnGetOpsServletPath());
							$('#addassetpricing-from input[name="qs"]').val('fundacc');
							$('#addassetpricing-from input[name="rules"]').val('Fund Accounts');
							$("#addassetpricing-from").submit();
						});
	
					} else {
						Swal.fire({
							icon: 'error',
							title: 'Oops',
							text: data.message,
							showConfirmButton: true,
							confirmButtonText: "Ok",
						}).then(function() {
							//Do Nothing
						});
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
						//Do Nothing
					});
				}
			});
	
		} else {
			Swal.fire({
				icon: 'error',
				title: 'Oops..',
				text: 'Please check your data'
				//footer: '<a href>Why do I have this issue?</a>'
			})
	
			return false;
		}
	});	
		function fnEditStatus(publickey, accountbal, status,createdon){
			//console.log('planid is ', planid);
			$('#EditXLMAccModal').on('show.bs.modal', function () {
			$('#editpublickey').val(publickey);
			$('#editaccountbal').val(accountbal);
			$("#seleditstatus").val(status).change();
			$('#editcreatedon').val(createdon);
							
			 });
		
			$('#EditXLMAccModal').modal('show');
		
		}
		$('#btn-editxlmaccount').click(function() {
		//Check for the data validation	
		$("#editxlmacc-from").validate({
			rules: {
				editpublickey: {
					required: true
				},
				seleditstatus: {
					required: true
				}
			},
			messages: {
				editpublickey: {
					required: 'Please enter amount',
				},
				seleditstatus: {
					required: 'Please select status',
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
	
		if ($("#editxlmacc-from").valid()) { 
			$('#editxlmacc-from').attr('action', 'ms');
			$('input[name="qs"]').val('fundacc');
			$('input[name="rules"]').val('editxlmaccount');
			$('input[name="hdneditseleditstatus"]').val($('#seleditstatus :selected').val());
		
			//$("#addassetpricing-from").submit();
			var formData = new FormData($('#editxlmacc-from')[0]);
			$.ajaxSetup({
				beforeSend: function(xhr) {
					xhr.setRequestHeader('x-api-key', getAPIKey());
				}
			});
			$('#spinner-div').show();//Load button clicked show spinner
			$.ajax({
				url: 'ms',
				data: formData,
				processData: false,
				contentType: false,
				type: 'POST',
				success: function(result) {
					$('#spinner-div').hide();//Request is complete so hide spinner
					var data = JSON.parse(result);
					if (data.error == 'false') {
						//alert('lgtoken is '+data.token)
						Swal.fire({
							icon: 'success',
							title: 'Good',
							text: data.message,
							showConfirmButton: true,
							confirmButtonText: "Ok",
						}).then(function() {
							$('#editxlmacc-from').attr('action', fnGetOpsServletPath());
							$('#editxlmacc-from input[name="qs"]').val('fundacc');
							$('#editxlmacc-from input[name="rules"]').val('Fund Accounts');
							$("#editxlmacc-from").submit();
						});
	
					} else {
						Swal.fire({
							icon: 'error',
							title: 'Oops',
							text: data.message,
							showConfirmButton: true,
							confirmButtonText: "Ok",
						}).then(function() {
							//Do Nothing
						});
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
						//Do Nothing
					});
				}
			});
	
		} else {
			Swal.fire({
				icon: 'error',
				title: 'Oops..',
				text: 'Please check your data'
				//footer: '<a href>Why do I have this issue?</a>'
			})
	
			return false;
		}
	});	
		
		
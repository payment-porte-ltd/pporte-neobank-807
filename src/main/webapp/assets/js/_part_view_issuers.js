$(window).on("load", function(e) {
    $("#global-loader").fadeOut("slow");
})



$('#btn_create_trustline').click(function() {
	//Check for the data validation	
    //alert('Clicked')
	$("#create-trustline-form").validate({
		rules: {
			sel_issuers: {
				required: true,
			},
			input_private_key: {
				required: true,
			}

		},
		messages: {
			input_private_key: {
				required: 'Please select the issuer ',
			},
			input_private_key: {
				required: 'Please enter Private Key',
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

	if ($("#create-trustline-form").valid()) {
        $('#spinner-div').show();//Load button clicked show spinner
		var formData = new FormData($('#create-trustline-form')[0]);
        formData.append('qs', 'partdash');
        formData.append('rules', 'partner_create_trustline')
        formData.append('values',  $('#sel_issuers').val());
        // console.log( 'list is ',getSelectedOptions(sel_issuers));
        // console.log( 'list is ',getSelectedOptions(sel_issuers));
        //var values = $('#sel_issuers').val();
        //console.log('values '+values);
       
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
			success: function(result) {
				//alert('result is '+result);
				var data = JSON.parse(result);
                $('#spinner-div').hide();//Request is complete so hide spinner
				if (data.error == 'false') {
					//alert('lgtoken is '+data.token)
					Swal.fire({
						icon: 'success',
						title: 'Good',
						text: data.message,
						showConfirmButton: true,
						confirmButtonText: "Ok",
					}).then(function() {
						$('#post-form').attr('action', fnGetOpsServletPath());
						$('#post-form input[name="qs"]').val('partdash');
						$('#post-form input[name="rules"]').val('Create Trustline');
						$("#post-form").submit(); 
					});

				} else {
					Swal.fire({
						icon: 'error',
						title: 'Oops',
						text: data.error,
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

function getSelectedOptions(element) {
    // validate element
    if(!element || !element.options)
        return []; //or null?

    // return HTML5 implementation of selectedOptions instead.
    if (element.selectedOptions)
        return element.selectedOptions;

    // you are here because your browser doesn't have the HTML5 selectedOptions
    var opts = element.options;
    var selectedOptions = [];
    for(var i = 0; i < opts.length; i++) {
         if(opts[i].selected) {
             selectedOptions.push(opts[i]);
         }
    }
    return selectedOptions;
}
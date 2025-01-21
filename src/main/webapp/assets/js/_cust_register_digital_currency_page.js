$(window).on("load", function(e) {
    $("#global-loader").fadeOut("slow");
})

function fnRegCurrency(){
	  $( "#create-digital-currency-form" ).validate( {
        rules: {
            selcurrency: {
                required: true
            },
            input_private_key: {
                required: true
            }
        },
        messages: {
            selcurrency: {
                required: 'Please select coin to swap'
            },
            input_private_key: {
                required: 'Please enter amount'
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

    if($( "#create-digital-currency-form" ).valid()){
		$('#spinner-div').show();//Load button clicked show spinner
        var formData = new FormData($('#create-digital-currency-form')[0]);
        formData.append('qs', 'frx');
        formData.append('rules', 'add_digital_currency'); 
        for (var pair of formData.entries()) {
          	alert(pair[0] + " - " + pair[1]);
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
                $('#spinner-div').hide();//Request is complete so hide spinner
                var data = JSON.parse(result);
                console.log('data ',data);
                if(data.error=='false'){
                    Swal.fire({
                        icon: 'success',
                        title: 'Registration Complete',
                        text: data.message,
                        showConfirmButton: true,
                        confirmButtonText: "Ok",
                    }).then(function() {
                        $('#post-form').attr('action', 'ws');
                        $('input[name="qs"]').val('porte');
                        $('input[name="rules"]').val('Display Transactions');
                        $("#post-form").submit();
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
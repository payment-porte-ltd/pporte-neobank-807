$(window).on("load", function(e) {
    $("#global-loader").fadeOut("slow");
})

function fnSubmitDispute(){
    $( "#raise-dipute-form" ).validate( {
        rules: {
            hdnreasonid: {
                required: true
            },
            dsptcomment: {
                required: true,
                minlength: 10
            },
            inputreferenceno: {
                required: true,
            },
        },
        messages: {
            hdnreasonid: {
                required: "Please select the reason description"
            },
            dsptcomment: {
                required: "Please enter the dispute comment",
                minlength: "Dispute comment should exceed 10 characters"
            },
            inputreferenceno: {
                required: "Please Enter the Reference Number",
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
    if($( "#raise-dipute-form" ).valid()){
        $('#raise-dipute-form input[name="qs"]').val('opsdspt');
        $('#raise-dipute-form input[name="rules"]').val('opsadd_dispute');
        
        var formData = new FormData($('#raise-dipute-form')[0]);
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
                
                var data = JSON.parse(result);
                console.log('data ',data);
                if(data.error=='false'){
                    Swal.fire({
                        icon: 'success',
                        text: data.message,
                        showConfirmButton: true,
                        confirmButtonText: "Ok",
                    }).then(function() {
                        $('#get-page-form').attr('action', fnGetOpsServletPath());
                        $('input[name="qs"]').val('opsdspt');
                        $('input[name="rules"]').val('View Raised Disputes');
                        $("#get-page-form").submit();
                    });	
                }else{
                    Swal.fire({
                        text: data.message, 
                        icon: "error",
                        showConfirmButton: true,
                        confirmButtonText: "Ok",
                        }).then(function() {
                            // Do Nothing  
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
                            // Do Nothing    
                    });
            }
        });             	
    }
}
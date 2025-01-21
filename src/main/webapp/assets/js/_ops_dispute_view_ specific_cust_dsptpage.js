$(window).on("load", function(e) {
    $("#global-loader").fadeOut("slow");
})
function fnUpdateStatus(disputeId){
    $('#dispute-details-form input[name="qs"]').val('opsdspt');
    $('#dispute-details-form input[name="rules"]').val('ops_update_dispute_status');
    $('#dispute-details-form input[name="hdndispid"]').val(disputeId);
        
    var formData = new FormData($('#dispute-details-form')[0]);
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
                     $('#view-dispute-form').attr('action', fnGetOpsServletPath());
                    $('input[name="qs"]').val('opsdspt');
                    $('input[name="rules"]').val('ops_view_specific_cust_dispute');
                    $('input[name="hdnreqid"]').val(disputeId);
                    $("#view-dispute-form").submit(); 
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

function fnAddComment(disputeId){
	let refNo=$("#referenceno").val();
		console.log("refNo",refNo);
    $( "#add-comment-form" ).validate( {
        rules: {
            hdnreasonid: {
                required: true
            }
        },
        messages: {
            hdnreasonid: {
                required: $('#add-raise-dispute-data-validation-error-hdnreasonid').text()
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
    if($( "#add-comment-form" ).valid()){
        $('#add-comment-form input[name="qs"]').val('opsdspt');
        $('#add-comment-form input[name="rules"]').val('ops_add_dispute_comment');
        $('#add-comment-form input[name="hdndispid"]').val(disputeId);
        var formData = new FormData($('#add-comment-form')[0]);
			formData.append("referenceno",refNo);
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
                        $('#view-dispute-form').attr('action', fnGetOpsServletPath());
                        $('input[name="qs"]').val('opsdspt');
                        $('input[name="rules"]').val('ops_view_specific_cust_dispute');
                        $('input[name="hdnreqid"]').val(disputeId);
                        $("#view-dispute-form").submit();
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

$("button[data-dismiss=modal]").click(function(){
	$(".modal").modal('hide');
});
function fnShowUpdateButton(){
	$("#ops_specific_merch_dispute_btnupdate_status").show();
}
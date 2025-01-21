$(window).on("load", function(e) {
    $("#global-loader").fadeOut("slow");
})

var gbCustomerId ='';
var gbCustomerName ='';
var gbPublicKey ='';
var gbTxnAmount ='';
var gbCoinAmount ='';
var gbAssetCode ='';
var gbRelno ='';
var gbTxnCode ='';

function fnApproveTransactions(customerId, customerName, publicKey, txnAmount,
 coinAmount, assetCode, relno, txnCode ){
	gbCustomerId =customerId;
    gbCustomerName =customerName;
 	gbPublicKey =publicKey;
 	gbTxnAmount =txnAmount;
 	gbCoinAmount =coinAmount;
 	gbAssetCode =assetCode;
 	gbRelno =relno;
 	gbTxnCode =txnCode;
	$('#myModal').modal('show');
}

function fnCloseMyModal(){
	$('#myModal').modal('hide');
}

function fnSendToBanckend(){
		$( "#private-key-form" ).validate( {
        ignore: [],
        rules: {
            privatekey: {
                required: true
            },
			comment: {
			    required: true,
			    maxlength: 150,
			},
        },
        messages: {
            privatekey: {
                required: 'Please input the private key',
            },
            comment: {
                required: 'Please enter a comment',
                maxlength:'Comment should not be more than 150 characters',
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

    if($( "#private-key-form" ).valid()){
	
	var form = $('#post-form')[0];
    var formData = new FormData(form);	
	formData.append("qs", "tdarqst");
	formData.append("rules", "approve_fiat_to_btcx_transaction");
	formData.append("cuustmerid", gbCustomerId);
	formData.append("custname", gbCustomerName);
	formData.append("publickey", gbPublicKey);
	formData.append("txnamount", gbTxnAmount);
	formData.append("coinamount", gbCoinAmount);
	formData.append("assetcode", gbAssetCode);
	formData.append("relno", gbRelno);
	formData.append("txncode", gbTxnCode);
	formData.append("privatekey", $("#privatekey").val());
	formData.append("comment", $("#comment").val());
	$.ajaxSetup({
		beforeSend: function(xhr) {
			xhr.setRequestHeader('x-api-key' , getAPIKey());
		}
	});
		
	$('#spinner-div').show();//Load button clicked show spinner
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
				if(data.error==="false"){
                    Swal.fire({
                        icon: 'success',
                        text: data.message,
                        showConfirmButton: true,
                        confirmButtonText: "Ok",
                    }).then(function() {
                        $('#post-form-data').attr('action', fnGetOpsServletPath());
                        $('input[name="qs"]').val('tdaacct');
                        $('input[name="rules"]').val('Display Stellar Transactions');
                        $("#post-form-data").submit();
                    });	
					
				}else{
                        Swal.fire({
                            icon: 'error',
                            title: 'Oops',
                            text: data.message,
                            showConfirmButton: true,
                            confirmButtonText: "Ok",
                            closeOnConfirm: true,
                            }).then(function() {
                               
                    });
                }
			}
				
			
		},
		error: function() {
			$("body").removeClass("loading"); 
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


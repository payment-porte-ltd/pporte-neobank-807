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
	$("#dist_assetcode").html(assetCode);
	$('#myModal').modal('show');
}

function fnCloseMyModal(){
	$('#myModal').modal('hide');
}

function fnSendToBanckend(){
	var form = $('#post-form')[0];
    var formData = new FormData(form);	
	formData.append("qs", "opscrypto");
	formData.append("rules", "approve_porte_transactions");
	formData.append("cuustmerid", gbCustomerId);
	formData.append("custname", gbCustomerName);
	formData.append("publickey", gbPublicKey);
	formData.append("txnamount", gbTxnAmount);
	formData.append("coinamount", gbCoinAmount);
	formData.append("assetcode", gbAssetCode);
	formData.append("relno", gbRelno);
	formData.append("txncode", gbTxnCode);
	formData.append("privatekey", $("#privatekey").val());
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
                        $('input[name="qs"]').val('opscrypto');
                        $('input[name="rules"]').val('View Porte Request');
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


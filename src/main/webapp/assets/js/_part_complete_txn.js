$("button[data-dismiss=modal]").click(function(){
	$(".modal").modal('hide');
});

$(window).on("load", function(e) {
	$("#global-loader").fadeOut("slow");
})
var gbReceiverName='';
var gbReceiverEmail='';
var gbRelationshipNo='';
var gbReceiverBankName='';
var gbAmount='';
function fnViewMoreRow(txnCode, systemRef, txnUserCode, relationshipNo, payMode, sourceAssetCode, sourceAmount, destinationAssetCode, destinationAmount, publicKey,
    txnHash, senderComment, partnerComment, receiverName, receiverBankName,receiverBankCode, receiverAccountNo, receiverEmail, status   ){
	$('#largeModal').on('show.bs.modal', function () {
        $("#sp_txn_ref").text(systemRef);
        $("#sp_source_amount").text(sourceAmount +" "+sourceAssetCode);
        $("#sp_txn_hash").text(txnHash);
        $("#sp_des_amount").text(destinationAmount +" "+destinationAssetCode);
       
		$('#select_status').val(status).prop('selected', true); 
		gbReceiverName=receiverName;
		gbReceiverEmail=receiverEmail;
		gbRelationshipNo=relationshipNo;
		gbReceiverBankName=receiverBankName;
		gbAmount=destinationAmount +" "+destinationAssetCode;
	});
	$('#largeModal').modal('show');
}

function fnSubmitTxnBtn(){
	$( "#txn-status-form" ).validate( {
		rules: {
			select_status: {
				required: true
			}
		},
		messages: {
			select_status: {
				required: 'Please select status'
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
	if($( "#txn-status-form" ).valid()){
		
		
		var form = $('#txn-status-form')[0];
		var formData = new FormData(form);
        formData.append("qs", 'partdash');
        formData.append("rules", 'partner_change_txn_status_pending');
        formData.append("system_ref", $("#sp_txn_ref").text());
        formData.append("receiver_name",gbReceiverName);
        formData.append("receiver_email",gbReceiverEmail);
        formData.append("cust_relno", gbRelationshipNo);
        formData.append("bank_name", gbReceiverBankName);
        formData.append("amount", gbAmount);

		/*for (var pair of formData.entries()) {
		}*/	
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
									$('input[name="qs"]').val('partdash');
									$('input[name="rules"]').val('Pending Transactions');
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



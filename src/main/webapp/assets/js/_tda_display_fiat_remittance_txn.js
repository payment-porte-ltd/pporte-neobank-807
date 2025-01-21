$("button[data-dismiss=modal]").click(function(){
	$(".modal").modal('hide');
});

$(window).on("load", function(e) {
	$("#global-loader").fadeOut("slow");
})

function fnViewMoreRow(txnCode, systemRef, txnUserCode, relationshipNo, payMode, sourceAssetCode, sourceAmount, destinationAssetCode, destinationAmount, publicKey,
    txnHash, senderComment, partnerComment, receiverName, receiverBankName,receiverBankCode, receiverAccountNo, receiverEmail, status   ){
	$('#largeModal').on('show.bs.modal', function () {
        $("#sp_txn_ref").text(systemRef);
        $("#sp_source_amount").text(sourceAmount +" "+sourceAssetCode);
        $("#sp_txn_hash").text(txnHash);
        $("#sp_des_amount").text(destinationAmount +" "+destinationAssetCode);
        $("#sp_bank_name").text(receiverBankName);
        $("#sp_receiver_name").text(receiverName);
        $("#sp_bank_code").text(receiverBankCode);
        $("#sp_receiver_email").text(receiverEmail);
        $("#sp_bank_acount_no").text(receiverAccountNo);
		$('#select_status').val(status).prop('selected', true); 
		/* <button type="button" class="btn btn-primary" onclick="fnGetExpectedUSDC()">Complete Transaction</button> */
		$("#complete_txn_div").html("");
		$("#complete_txn_div").append(`<button type="button" class="btn btn-primary" onclick="fnGetExpectedUSDC(\``+sourceAmount+`\`,\``+publicKey+`\`,\``+systemRef+`\`)">Complete Transaction</button>`);
	});
	$('#largeModal').modal('show');
}

function fnSubmitBtn(amountUSD, patnerPublicKey, systemRef, 
			pvtKey, comment){
		var formData = new FormData();
        formData.append("rules", 'tda_complete_remitance_txn');
        formData.append("qs", 'tdarmt');
        formData.append("usdc_private_key", pvtKey);
		formData.append("partner_public_key", patnerPublicKey);
		formData.append("sys_ref", systemRef);
		formData.append("txn_comment", comment);
		formData.append("amount_in_usd", amountUSD);
		
		$('#spinner-div').show();//Load button clicked show spinner
		
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
					$('#spinner-div').hide();//Request is complete so hide spinner
					
					var data = JSON.parse(result);
					if(data.error=='false'){
						Swal.fire({
									icon: 'success',
									text: data.message,
									showConfirmButton: true,
									confirmButtonText: "Ok",
								}).then(function() {
									$('#get-page-form').attr('action', fnGetOpsServletPath());
									$('input[name="qs"]').val('tdarmt');
									$('input[name="rules"]').val('Fiat Remittance Request');
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



function fnGetExpectedUSDC(amountUSD, patnerPublicKey, systemRef ){
	var formData = new FormData();
    formData.append("qs", 'tdadash');
    formData.append("rules", 'get_usdc_to_transfer');
    formData.append("amount_in_usd", amountUSD);


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
				console.log('data ', data);
				if(data.error=='false'){
					$('#largeModal').modal('toggle');
					Swal.fire({
						title: data.data,
						showDenyButton: true,
						
						confirmButtonText: 'Transfer',
						
					}).then((result) => {
						/* Read more about isConfirmed, isDenied below */
						if (result.isConfirmed) {
						fnPrivateKey(amountUSD,patnerPublicKey, systemRef );
						} else if (result.isDenied) {
						
						}
					})
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

function fnPrivateKey(amountUSD, patnerPublicKey, systemRef){
	Swal.fire({
	  title: '<strong>Transfer USDC to partner</strong>',
	  html:
			'<input type="password" id="pvt_key" class="swal2-input" placeholder="Enter private key">' +
   			'<textarea id="comment" rows="3" class="swal2-input" placeholder="Enter Comment"></textarea>',
	  showConfirmButton: true,
	  confirmButtonText: "Submit",
	  showCancelButton: true,
	  }).then(function (result) {
			  if (result.value) {
			   fnSubmitBtn(amountUSD, patnerPublicKey, systemRef, 
			   document.getElementById('pvt_key').value, document.getElementById('comment').value)
			  } else {
			    // handle cancel
			  }
			
	  });
}


$("button[data-dismiss=modal]").click(function(){
	$(".modal").modal('hide');
});
var sequence_id ="";
$ (function(){
	$('#assets_table').dataTable({ "bSort" : false } );
});
function fnEditRow(assetCode, assetDesc, status, assetType, date, walletType, id, 
issuerId, distributionId, liquidityAccountId ){
	sequence_id=id;
	$('#editModal').on('show.bs.modal', function () {
		$('#edit_asset_code').val(assetCode);
		$('#edit_asset_desc').val(assetDesc);
		$('#edit_wallet_type').val(walletType);
		$('#edit_issuer_acc_id').val(issuerId);
		$('#edit_distribution_acc_id').val(distributionId);
		$('#edit_liquidity_acc_id').val(liquidityAccountId);
		$('#edit_status').val(status).prop('selected', true);
		$('#edit_asset_type').val(assetType).prop('selected', true);
	});
	$('#editModal').modal('show');
}

function addWalletAsset(){
	$( "#add-asset-form" ).validate( {
		rules: {
			asset_code: {
				required: true
			},
			asset_desc: {
				required: true
			},
			asset_type: {
				required: true
			},
			status: {
				required: true
			},
			wallet_type: {
				required: true
			}
		},
		messages: {
			asset_code: {
				required: 'Please enter Asset Code'
			},
			asset_desc: {
				required: 'Please enter Asset Description'
			},
			asset_type: {
				required: 'Please select Asset Type'
			},
			status: {
				required: 'Please select status '
			},
			wallet_type: {
				required:'Please enter wallet type'
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
	if($( "#add-asset-form" ).valid()){
		$('#add-asset-form input[name="qs"]').val('opswal');
		$('#add-asset-form input[name="rules"]').val('add_wallet_asset');
		
		var form = $('#add-asset-form')[0];
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
									$('#get-page-form').attr('action', fnGetOpsServletPath());
									$('input[name="qs"]').val('opswal');
									$('input[name="rules"]').val('Wallet Assets');
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

function editWallet(){
	$( "#edit-asset-form" ).validate( {
		rules: {
			edit_asset_code: {
				required: true
			},
			edit_asset_desc: {
				required: true
			},
			edit_status: {
				required: true
			},
			edit_asset_type: {
				required: true
			}
		},
		messages: {
			edit_asset_code: {
				required: $('#edit-user-data-validation-error-fname-length').text()
			},
			edit_asset_desc: {
				required: $('#edit-user-data-validation-error-uemail').text()
			},
			edit_status: {
				required: $('#edit-user-data-validation-error-phoneno').text()
			},
			edit_asset_type: {
				required: $('#edit-user-data-validation-error-idno').text()
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
	if($( "#edit-asset-form" ).valid()){
		$('#edit-asset-form input[name="qs"]').val('opswal');
		$('#edit-asset-form input[name="rules"]').val('edit_wallet_asset');
		
		var form = $('#edit-asset-form')[0];
		var formData = new FormData(form);
		formData.append('edit_asset_id',sequence_id);
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
									$('#get-page-form').attr('action', fnGetOpsServletPath());
									$('input[name="qs"]').val('opswal');
									$('input[name="rules"]').val('Wallet Assets');
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
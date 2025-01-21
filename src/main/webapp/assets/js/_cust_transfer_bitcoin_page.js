function fnGetCoinDetailsTransferBTC(){
	$('#post-form input[name="qs"]').val('btc');
	$('#post-form input[name="rules"]').val('get_btc_details');
	var form = $('#post-form')[0];
    var formData = new FormData(form);	
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
				var porteCoinList=data.data;	
				console.log(porteCoinList);
                var htmlOptions = '';
				$('#div_source_asset_trans_btc').html('');
				$('#div_destination_asset_trans_btc').html('');
			/* 	$('#receiver_asset').html('');
				$('#receiver_asset_cb').html('');
 */
                
				if(data.error="false"){ 
                    if(porteCoinList.length>0){
                        for (i=0; i<porteCoinList.length;i++){
                            htmlOptions+=`<div class="col-3">
                                <label>
                                    <input  type="radio" name="coin_asset_trans_btc" disabled value="`+porteCoinList[i].assetCode+`">
                                    <img src="assets/images/crypto/bitcoin.svg" alt="`+porteCoinList[i].assetCode+`" height="40" width="40" >
                                    
                                </label>
                            </div>
                            <div class="col" style="margin-top: 7px;">
                                <p>`+porteCoinList[i].assetCode+`</p>
                            </div>`;
                        }
                    }else{
                        htmlOptions+=`<p>`+$('#data-validation-no-assets-available').text()+`</p>`;
                    }
                    $('#div_source_asset_trans_btc').append(htmlOptions);
                    $('#div_destination_asset_trans_btc').append(htmlOptions);
                   /*  $('#receiver_asset').append(htmlOptions);
                    $('#receiver_asset_cb').append(htmlOptions); */
				}else{
                        
                }
			}
				
			
		},
		error: function() {
            Swal.fire({
                icon: 'error',
                title: $('#data-validation-error-swal-header').text(),
                text: $('#data-validation-problem-with-connection').text(),
                showConfirmButton: true,
                confirmButtonText: "Ok",
                }).then(function() {
                   
        });
		}
	}); 
}

function fnTransferCoin (security, hasMnemonic){
    $( "#transfer-coin-form" ).validate( {
        rules: {
            sender_asset: {
                required: true
            },
            input_receiver_address: {
                required: true
            },
            sendamount: {
                required: true
            }
            
        },
        messages: {
            sender_asset: {
                required: $('#data-validation-please-select-coin-to-transfer').text()
            },
            input_receiver_address: {
                required: $('#data-validation-please-enter-receiver').text()
            },
            sendamount: {
                required: $('#data-validation-please-enter-amount').text()
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
    if($( "#transfer-coin-form" ).valid()){
	 $('#spinner-div').show();//Load button clicked show spinner
        $('#transfer-coin-form input[name="qs"]').val('btc');
        $('#transfer-coin-form input[name="rules"]').val('transfer_bitcoin');
        $('#transfer-coin-form input[name="hdnlang"]').val($('#lang_def').text());

        var formData = new FormData($('#transfer-coin-form')[0]);
		formData.append('security',security);
		formData.append('hasMnemonic',hasMnemonic);
		formData.append('coin_asset_trans_btc','BTC');
		//formData.append("hdnlang", $('#lang_def').text());

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
                $('#spinner-div').hide();//Request is complete so hide spinner
                var data = JSON.parse(result);
                console.log('data ',data);
                if(data.error=='false'){
                    Swal.fire({
                        icon: 'success',
                        text: data.message,
                        showConfirmButton: true,
                        confirmButtonText: "Ok",
                    }).then(function() {
                        $('#post-form').attr('action', 'ws');
                        $('input[name="qs"]').val('btc');
                        $('input[name="rules"]').val('View Transactions');
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
                            title: $('#data-validation-error-swal-header').text(),
                            text: $('#data-validation-problem-with-connection').text(),
                            showConfirmButton: true,
                            confirmButtonText: "Ok",
                            }).then(function() {
                                
                    });
            }
        });             	
    }
}


function checkIfUserHasMneonicCodeTransferBTC(){
	var formData = new FormData();
	formData.append('qs','porte')
	formData.append('rules','check_if_customer_has_bitcoin_mnemonic_code')
    for (var pair of formData.entries()) {
      //console.log(pair[0] + " - " + pair[1]);
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
            //wallet_txn
            var data = JSON.parse(result);
            var htmlData ='';
            var txnList = data.data;
            if(data.error=='false'){
                if(data.hasmnemonic=='true'){
                    fnPasswordTransferBTC(data.hasmnemonic);
                }else{
                    fnPrivateKeyTransferBTC(data.hasmnemonic)
                }
               
            }else{
                Swal.fire({
                    icon: 'error',
                    title: $('#data-validation-error-swal-header').text(),
                    text: data.message,
                    showConfirmButton: true,
                    confirmButtonText: "Ok",
                    closeOnConfirm: true,
                    }).then(function() {
                       
            });
            }
        },
        error: function() {
            Swal.fire({
                        icon: 'error',
                        title: $('#data-validation-error-swal-header').text(),
                        text: $('#data-validation-problem-with-connection').text(),
                        showConfirmButton: true,
                        confirmButtonText: "Ok",
                        }).then(function() {
                            
                });
        }
    });             	
	
}

function fnPasswordTransferBTC(hasMnemonic){
	const { value: password   } = Swal.fire({
		title: $('#enter-your-passsword-tittle').text(),
		input: 'password',
		inputLabel:  $('#enter-your-passsword-label').text(),
		showCancelButton: true,
		inputAttributes: {
		autocapitalize: 'off',
		autocorrect: 'off'
		},
		inputValidator: (value) => {
		if (!value) {
			return $('#data-validation-enter-your-passsword-label').text();
		}
		}
	}).then((result) => {
			if (result.value) {
				var passwordVal = result.value;
                fnTransferCoin(passwordVal,hasMnemonic );
			}
		});
}

function fnPrivateKeyTransferBTC(hasMnemonic){
	const { value: password   } = Swal.fire({
		title: $('#enter-your-passsword-label').text(),
		input: 'password',
		inputLabel: $('#enter-your-pvt-key-label').text(),
		showCancelButton: true,
		inputAttributes: {
			autocapitalize: 'off',
			autocorrect: 'off'
		},
		inputValidator: (value) => {
		if (!value) {
			return $('#data-validation-enter-your-pvt-key-label').text();
		}
		}
	}).then((result) => {
			if (result.value) {
				var privatekey = result.value;
				fnTransferCoin(privatekey,hasMnemonic );
			}
		});
}

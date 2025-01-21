function fnUpdatesenderparams() {
    var assetcode = $('input[name="coin_source_asset"]:checked').val();
    console.log('assetcode ', assetcode);
    if (assetcode == '') {
        swal.fire('Select Destination Coin');
        return false;
    } else {
        cryptocoversionFromSourceSWapBTX();
        $("#spansendcode_swap_btcx").text(assetcode);
    }
    
}

function fnUpdateReceiverParams() {
    var assetcode = $('input[name="coin_source_asset"]:checked').val();
    console.log('assetcode ', assetcode);
    if (assetcode == '') {
        swal.fire('Select Destination Coin');
        return false;
    } else {
        cryptocoversionFromSourceSWapBTX();
        $("#spansendcode_swap_btcx").text(assetcode);
       // $("#receiver_asset").val(assetcode);
    }
    
}


function fnGetSourceCoinDetail(){
	$('#post-form input[name="qs"]').val('porte');
	$('#post-form input[name="rules"]').val('get_source_assets_details');
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
                var htmlOptions = '';
				$('#source_asset_swap_btcx').html('');
				
				if(data.error="false"){
                    if(porteCoinList.length>0){
                       for (i=0; i<porteCoinList.length;i++){

                        if(porteCoinList[i].assetCode === 'PORTE'){
                            htmlOptions+=`<div class="col-3">
                                            <label>
                                                <input type="radio" name="coin_source_asset" value="`+porteCoinList[i].assetCode+`" checked>
                                                <img src="assets/images/crypto/porte.svg" alt="`+porteCoinList[i].assetCode+`" height="40" width="40" >
                                                <p>`+porteCoinList[i].assetCode+`</p>
                                            </label>
                                        </div>`;

                        }else if(porteCoinList[i].assetCode === 'VESL'){
                            htmlOptions+=`<div class="col-3">
                                            <label>
                                                <input type="radio" name="coin_source_asset" value="`+porteCoinList[i].assetCode+`">
                                                <img src="assets/images/crypto/vessel.png" alt="`+porteCoinList[i].assetCode+`" height="40" width="40" >
                                                <p>`+porteCoinList[i].assetCode+`</p>
                                            </label>
                                        </div>`;

                        }else if(porteCoinList[i].assetCode === 'XLM'){
                            htmlOptions+=`<div class="col-3">
                                            <label>
                                                <input type="radio" name="coin_source_asset" value="`+porteCoinList[i].assetCode+`" >
                                                <img src="assets/images/crypto/xlm.svg" alt="`+porteCoinList[i].assetCode+`" height="40" width="40" >
                                                <p>`+porteCoinList[i].assetCode+`</p>
                                            </label>
                                     </div>`;
                        }else if(porteCoinList[i].assetCode === 'USDC'){
                            htmlOptions+=`<div class="col-3">
                                            <label>
                                                <input type="radio" name="coin_source_asset" value="`+porteCoinList[i].assetCode+`">
                                                <img src="assets/images/crypto/usdc.png" alt="`+porteCoinList[i].assetCode+`" height="40" width="40" >
                                                <p>`+porteCoinList[i].assetCode+`</p>
                                            </label>
                                        </div>`;
                        }

                    }

                      
                    }else{
                        htmlOptions+=`<option disabled="disabled" value="-1" selected>`+$('#no-assets-available').text()+`</option>`;
                    }
            
                    $('#source_asset_swap_btcx').append(htmlOptions);
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

function fnGetBTCxDetails(){
	$('#post-form input[name="qs"]').val('porte');
	$('#post-form input[name="rules"]').val('get_btcx_details');
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
                var htmlOptions = '';
				$('#destination_asset_swap_btcx').html('');
				
				if(data.error="false"){
                    if(porteCoinList.length>0){
                        
                        for (i=0; i<porteCoinList.length;i++){
                            htmlOptions+=`<div class="col-3">
                                                <label>
                                                    <input type="radio" name="coin_asset_distination_asset" value="`+porteCoinList[i].assetCode+`" checked>
                                                    <img src="assets/images/crypto/bitcoin.svg" alt="`+porteCoinList[i].assetCode+`" height="40" width="40" >
                                                    <p>`+porteCoinList[i].assetCode+`</p>
                                                </label>
                                         </div>`;
                        }
                    }else{
                        htmlOptions+=`<div>`+$('#no-assets-available').text()+`</div>`;
                    }
            
                    $('#destination_asset_swap_btcx').append(htmlOptions);
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

function fnExChangeBTCxWithDigitalAssets(security,hasMnemonic){
    $( "#exchange-btcx-coin-form" ).validate( {
        rules: {
            coin_source_asset: {
                required: true
            },
            destination_amount_swap_btcx: {
                required: true
            },
            coin_asset_distination_asset:{
                required: true
            }
        },
        messages: {
            coin_source_asset: {
                required: $('#data-validation-please-select-coin-to-swap').text()
            },
            destination_amount_swap_btcx: {
                required: $('#data-validation-please-enter-amount').text()
            },
            coin_asset_distination_asset:{
                required: $('#data-validation-please-select-destination-coin').text()
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

    if($( "#exchange-btcx-coin-form" ).valid()){
		$('#spinner-div').show();//Load button clicked show spinner
        var formData = new FormData($('#exchange-btcx-coin-form')[0]);
        formData.append('qs', 'porte');
        formData.append('rules', 'exchange_btcx_with_porte_coin'); 
        formData.append("hdnlang", $('#lang_def').text());
		formData.append('security',security);
		formData.append('hasMnemonic',hasMnemonic);
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
                // console.log('data ',data);
                if(data.error=='false'){
                    Swal.fire({
                        icon: 'success',
                        title: $('#transfer-complete-label').text(),
                        text: data.message,
                        showConfirmButton: true,
                        confirmButtonText: "Ok",
                    }).then(function() {
                        $('#post-form').attr('action', 'ws');
                        $('input[name="qs"]').val('btcx');
                        $('input[name="rules"]').val('view_pending_btcx_swapping_txn');
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


/*
function cryptocoversion(){
    var conversion = 1;
    var assetcode = $("#coin_asset option:selected").val();
   
    if(assetcode ==="PPT"){
     conversion =  1.35;
    }else if(assetcode ==="VSL"){
      conversion = 1.5;
    }
    var amount = $("#amount").val();
    var equivalence = amount*conversion;

     $("#receivedamount").val(equivalence);
    
}*/

/*
function cryptocoversionBackwards(){
    var conversion = 1;
    var assetcode = $("#coin_asset option:selected").val();
 
    if(assetcode ==="PPT"){
     conversion = 0.74074;
    }else if(assetcode ==="VSL"){
      conversion = 0.6667;
    }
    var amount = $("#receivedamount").val();
    var equivalence = amount*conversion;

     $("#amount").val(equivalence);
    
}*/

$( document ).ready(function() {
    fnGetSourceCoinDetail();
    fnGetBTCxDetails();
    $("#destination_amount_swap_btcx").keyup(function() {
        cryptocoversionFromSourceSWapBTX();
  });
});





function cryptocoversionFromSourceSWapBTX(){
  var sourceAssetcode = $('input[name="coin_source_asset"]:checked').val();
  var destAssetcode = $('input[name="coin_asset_distination_asset"]:checked').val();
  $("#spansendcode_swap_btcx").text(sourceAssetcode);
  var amount = $("#destination_amount_swap_btcx").val();

    console.log('sourceAssetcode ',sourceAssetcode);
  console.log('destAssetcode ',destAssetcode);
  console.log('amount ',amount);  

  var formData = new FormData($('#sell-porte-coin-form')[0]);//sell-porte-coin-form
  formData.append('qs', 'btcx');
  formData.append('rules', 'get_exchange_rate_with_markeup'); 
  formData.append('destination_amount', amount); 
  formData.append('source_asset', sourceAssetcode); 
  formData.append('destination_asset', destAssetcode); 
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
            $("#source_amount_swap_btcx").val(data.data);
            $("#offers_not_available_text").addClass('hidden');

          }else{
             console.log('Error',data.message)
			$("#offers_not_available_text").removeClass('hidden');
          }
      },
      error: function() {
        // console.log('Problem with connection')
      }
  });  
}


function checkIfUserHasMneonicCode(){
	var formData = new FormData();
	formData.append('qs','porte')
	formData.append('rules','check_if_customer_has_mnemonic_code')
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
                    fnPassword(data.hasmnemonic);
                }else{
                    fnPrivateKey(data.hasmnemonic)
                }
               
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

function fnPassword(hasMnemonic){
	const { value: password   } = Swal.fire({
		title: 'Enter your Password',
		input: 'password',
		inputLabel: 'Password',
		showCancelButton: true,
		inputAttributes: {
		autocapitalize: 'off',
		autocorrect: 'off'
		},
		inputValidator: (value) => {
		if (!value) {
			return 'Please input your password!';
		}
		}
	}).then((result) => {
			if (result.value) {
				var passwordVal = result.value;
				fnExChangeBTCxWithDigitalAssets (passwordVal,hasMnemonic);
			}
		});
}

function fnPrivateKey(hasMnemonic){
	const { value: password   } = Swal.fire({
		title: 'Enter your Private Key',
		input: 'password',
		inputLabel: 'Password',
		showCancelButton: true,
		inputAttributes: {
			autocapitalize: 'off',
			autocorrect: 'off'
		},
		inputValidator: (value) => {
		if (!value) {
			return 'Please input your Private Key!';
		}
		}
	}).then((result) => {
			if (result.value) {
				var privatekey = result.value;
				fnExChangeBTCxWithDigitalAssets (privatekey,hasMnemonic);
			}
		});
}

function fnCallPendingSwapBTCXPage(){
    $('#post-form').attr('action', 'ws');
    $('input[name="qs"]').val('btcx');
    $('input[name="rules"]').val('view_pending_btcx_swapping_txn');
    $("#post-form").submit();	
}


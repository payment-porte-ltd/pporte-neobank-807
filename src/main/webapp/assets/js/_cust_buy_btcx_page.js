function fnUpdatesenderparamsBuyBTCx() {
    var assetcode = $('input[name="coin_asset_buy_btcx"]:checked').val();
    //var assetcode1 =  $('input[name="coin_asset_buy_btcx"]:checked').val();
    //console.log('assetcode is '+assetcode+' assetcode1 '+assetcode1);
    if (assetcode == '') {
        swal.fire('Select Destination Coin');
        return false;
    } else {
        UpdateConversionRateBuyBTCx();
        $("#spansendcode_buy_btcx").text(assetcode);
        //$("#receiver_asset").val(assetcode);
    }
    
}

function fnGetPaymentMethodBuyBTCx(relno){
    var paymentMethod = $("#payment_method_buy_btcx option:selected").val();
    console.log('relno ', relno);
    console.log('paymentMethod ', paymentMethod);
    if (paymentMethod == 'T') {
        fnGetCardDetailsBuyBTCx(relno);
    }else{
        $('#cardsdiv_buy_btx').hide();
    }
}

function fnUpdatereceiveparamsBuyBTCx() {
    var assetcode = $('input[name="coin_asset_buy_btcx"]:checked').val();
    console.log('assetcode ia ', assetcode);
    if (assetcode == '') {
        swal.fire('Select Destination Coin');
        return false;
    } else {
        $("#spansendcode_buy_btcx").text(assetcode);
        
    }		
}

function fnGetCardDetailsBuyBTCx(relno){
	$('#post-form input[name="qs"]').val('card');
	$('#post-form input[name="rules"]').val('gettokenizedcards');
	$('#post-form input[name="relno"]').val(relno);
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
					var htmlData='';
	              $('#cardavailable_buy_btcx').html('');
				  console.log("Error is"+ data.error);
				if(data.error ==="false"){
						var cardDetails=data.data;
						console.table(cardDetails)
						var count = cardDetails.length;
						console.log("count is "+count);
						if(count>0){
                            for (i=0; i<count;i++){
                                /* htmlData+= `
                                    <label class="custom-control custom-radio">
                                            <input type="radio" class="custom-control-input" name="tokenid" id="tokenid" value="`+cardDetails[i].tokenId+`" >
                                            <span class="custom-control-label">`+cardDetails[i].maskedCardNumber+`</span>
                                      </label>
                                `;  */  if(cardDetails[i].maskedCardNumber.startsWith("5")){
                                    htmlData+= `
                                        <label class="custom-control custom-radio">
                                                <input type="radio" class="custom-control-input" name="tokenid" id="tokenid" value="`+cardDetails[i].tokenId+`" >
                                                <span class="custom-control-label"><img src="assets/images/cards/mastercard.png" alt="MasterCard" height="22" width="36">`+cardDetails[i].maskedCardNumber+`</span>
                                          </label>
                                    `;											
                                    }else if (cardDetails[i].maskedCardNumber.startsWith("4")){
                                        htmlData+= `
                                        <label class="custom-control custom-radio">
                                                <input type="radio" class="custom-control-input" name="tokenid" id="tokenid" value="`+cardDetails[i].tokenId+`"  >
                                                <span class="custom-control-label"><img src="assets/images/cards/visa.png" alt="Visa" height="22" width="36">`+cardDetails[i].maskedCardNumber+`</span>
                                          </label>
                                    `;	
                                    }else if (cardDetails[i].maskedCardNumber.startsWith("3")){
                                        htmlData+= `
                                        <label class="custom-control custom-radio">
                                                <input type="radio" class="custom-control-input" name="tokenid" id="tokenid" value="`+cardDetails[i].tokenId+`"  >
                                                <span class="custom-control-label"><img src="assets/images/cards/amex.png" alt="Amex" height="22" width="36">`+cardDetails[i].maskedCardNumber+`</span>
                                          </label>
                                    `;	
                                    }else{
                                        htmlData+= `
                                        <label class="custom-control custom-radio">
                                                <input type="radio" class="custom-control-input" name="tokenid" id="tokenid" value="`+cardDetails[i].tokenId+`"  >
                                                <span class="custom-control-label">`+cardDetails[i].maskedCardNumber+`</span>
                                          </label>
                                    `;	
                                    }
                                }
                        }else{
                            htmlData =`<p style="color:grey">No Cards Present,&nbsp; <a href="#" onclick="javascript: fnCallRegisterCardPage(); return false">Click here to register</a></p> `;
                        }

				}else if(data.error === "true"){
                    htmlData =`<p style="color:grey">No Cards Present,&nbsp; <a href="#" onclick="javascript: fnCallRegisterCardPage(); return false">Click here to register</a></p> `;
					
                }
               
                $('#cardsdiv_buy_btx').show();
				$('#cardavailable_buy_btcx').html(htmlData);
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

function fnCallRegisterCardPage(){
    $('#post-form').attr('action', 'ws');
    $('input[name="qs"]').val('card');
    $('input[name="rules"]').val('Register Card');
    $("#post-form").submit();	
}

function validateCardDetails(){
    if(document.getElementById('tokenid').checked == true) {
        if(!fnValidateCVV2){
            Swal.fire({
                text: 'CVV is invalid', 
                icon: "error",
                showConfirmButton: true,
                confirmButtonText: "Ok",
                }).then(function() {
                });	
        }
    }else{
        Swal.fire({
            text: $('#data-validation-please-card-pay-with').text(), 
            icon: "error",
            showConfirmButton: true,
            confirmButtonText: "Ok",
            }).then(function() {
            });	
    }   

}

function fnValidateCVV2(){
  let x = document.getElementById("cvv").value;
  if (isNaN(x)|| x.length != 3) {
    return false;
  } else {
   return true;
  }
}



function fnFetchPorteCoins(){
	$('#post-form input[name="qs"]').val('porte');
	$('#post-form input[name="rules"]').val('get_porte_coins_details');
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
				var porteDetails=data.data;	
				console.log(porteDetails);
				var htmlOptions = '';
				$('#coin_balances').html('');
				if(data.error="false"){
                    htmlOptions+=`<h2 class="dashboard-title">Coin Balance</h2>`;
					if(porteDetails.length>0){
						for (i=0; i<porteDetails.length;i++){
							if(porteDetails[i].walletType === "P"){
                                htmlOptions+=`<div class="wallet-transaction-box clearfix" >
                                                    <div class="wallet-balance-ico">
                                                        <img src="assets/images/crypto/porte.svg" alt="Litcoin" height="40" width="40" >
                                                    </div>
                                                    <div class="wallet-transaction-name">
                                                        <h3>`+porteDetails[i].assetCode+`</h3>
                                                        <span>Last Updated</span>
                                                    </div>
                                                    <div class="wallet-transaction-balance">
                                                        <h3> `+porteDetails[i].currentBalance+` `+porteDetails[i].assetCode+`</h3>
                                                        <span>`+porteDetails[i].lastUpdated+`</span>
                                                    </div>
                                                </div>`;
							}else if(porteDetails[i].walletType === "V"){
                                htmlOptions+=`<div class="wallet-transaction-box clearfix" >
                                                <div class="wallet-balance-ico">
                                                    <img src="assets/images/crypto/stable.png" alt="Litcoin" height="40" width="40" >
                                                </div>
                                                <div class="wallet-transaction-name">
                                                    <h3>`+porteDetails[i].assetCode+`</h3>
                                                    <span>Last Updated</span>
                                                </div>
                                                <div class="wallet-transaction-balance">
                                                    <h3> `+porteDetails[i].currentBalance+` `+porteDetails[i].assetCode+`</h3>
                                                    <span>`+porteDetails[i].lastUpdated+`</span>
                                                </div>
                                            </div>`;
							}
							
						}
				   }else{
						Swal.fire({
							icon: 'error',
							title: $('#data-validation-error-swal-header').text(),
							text: $('#data-validation-please-dont-have-porte-coin').text(),
							showConfirmButton: true,
							confirmButtonText: "Register",
							closeOnConfirm: true,
							}).then(function() {
								/* $('#post-form').attr('action', 'ws');
								$('input[name="qs"]').val('wal');
								$('input[name="rules"]').val('Create Wallet');
								$("#post-form").submit(); */
						});
				   } 
				   $('#coin_balances').append(htmlOptions);
                 

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

$( document ).ready(function() {
    fnGetBTCxDetailsBuyBTCx();
});


function fnCallPendingBuyBTCXPage(){
    $('#post-form').attr('action', 'ws');
    $('input[name="qs"]').val('btcx');
    $('input[name="rules"]').val('view_pending_btcx_fiat_txn');
    $("#post-form").submit();	
}
function UpdateConversionRateBuyBTCx(){
  var sourceAssetcode = 'USD';
  var destAssetcode = 'BTCX';
 
 /*sourceAssetcode = sourceAssetcode.split(",")
  sourceAssetcode = sourceAssetcode[1];
  destAssetcode = destAssetcode.split(",")
  destAssetcode = destAssetcode[1];*/
  var amount = $("#receivedamount_buy_btcx").val();
//alert('amount '+ amount)
  var formData = new FormData($('#buy-porte-coin-form')[0]);
  formData.append('qs', 'porte');
  formData.append('rules', 'porte_asset_conversion'); 
  formData.append('sourceamount', amount); 
  formData.append('source', sourceAssetcode); 
  formData.append('destination', destAssetcode); 
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
            $("#amount_buy_btcx").val(data.data);
          }else{
            console.log('Error',data.message)
	
          }
      },
      error: function() {
        console.log('Problem with connection')
      }
  });  
}


function fnGetBTCxDetailsBuyBTCx(){
	$('#post-form input[name="qs"]').val('btcx');
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
				console.log(porteCoinList);
                var htmlOptions = '';
				$('#div_asset_details_buy_btcx').html('');
				
				if(data.error="false"){
                    if(porteCoinList.length>0){
                        htmlOptions+=`<label style="margin-left: 13px;"><span id="sp_from_id">Select Coin</span> </label>
                        <div class="row" style="margin-left: 13px;">`;
                        for (i=0; i<porteCoinList.length;i++){
                            htmlOptions+=`<div class="col-3">
                                                <label>
                                                    <input type="radio" name="coin_asset_buy_btcx" value="`+porteCoinList[i].assetCode+`" checked>
                                                    <img src="assets/images/crypto/bitcoin.svg" alt="`+porteCoinList[i].assetCode+`" height="40" width="40" >
                                                    <p>`+porteCoinList[i].assetCode+`</p>
                                                </label>
                                         </div>`;
                        }
                    }else{
                        htmlOptions+=`<div>No assets available</div>`;
                    }
            
                    $('#div_asset_details_buy_btcx').append(htmlOptions);
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

function fnBuyBTCx (){
    var modeOfPayment = $("#payment_method_buy_btcx option:selected").val();
    $( "#buy-btcx-coin-form" ).validate( {
        rules: {
            coin_asset_buy_btcx: {
                required: true
            },
            payment_method_buy_btcx: {
                required: true
            },
            receivedamount_buy_btcx: {
                required: true
            }
        },
        messages: {
            coin_asset_buy_btcx: {
                required: $('#data-validation-please-coin-to-buy').text()
            },
            payment_method_buy_btcx: {
                required: $('#data-validation-please-payment-method').text()
            },
            receivedamount_buy_btcx: {
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

    if($( "#buy-btcx-coin-form" ).valid()){
        var formData = new FormData($('#buy-btcx-coin-form')[0]);
        formData.append('qs', 'porte');
        formData.append('rules', 'buy_btcx_using_fiat'); 
		formData.append("hdnlang",$('#lang_def').text());
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
                         $('#post-form').attr('action', 'ws');
						   $('input[name="qs"]').val('btcx');
						    $('input[name="rules"]').val('view_pending_btcx_fiat_txn');
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


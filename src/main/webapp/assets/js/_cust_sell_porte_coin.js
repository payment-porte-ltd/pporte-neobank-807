i18next.init({
	lng: 'EN',
	fallbackLng: 'EN',
	debug: false,
	resources: {
	  EN: {
		translation: {
             /* Menus */
			"idnavmenu_Dashboard":"Dashboard",
			"idnavmenu_ManageFiatWallet":"Manage Fiat Wallet",
			"idnavmenu_DigitalAssets":"Digital Assets",
			"idnavmenu_DigitalAssets":"Digital Assets",
			"idnavmenu_CurrencyRemittance":"Currency Remittance",
			"idnavmenu_ManageCards":"Manage Cards",
			"idnavmenu_Loyalty":"Loyalty",
			"idnavmenu_UpdatePlan":"Update Plan",
			"idnavmenu_Logout":"Logout",
			"idnavmenu_BuyNewPlan":"Buy New Plan",
			"header_buy_porte_token":"Buy Porte Token",
			"btn_buy_coin":"Buy Coin",
			"idnavmenu_Profile":"Profile",
			"idnavmenu_Disputes":"Disputes",
             /* Sub Menus */
            "idnavsubmenu_Dashboard":"Dashboard",
            "idnavsubmenu_ViewandEdit":"View and Edit",
            "idnavsubmenu_ViewWallet":"View Wallet",
            "idnavsubmenu_TopupWallet":"Topup Wallet",
            "idnavsubmenu_SendMoney":"Send Money",
            "idnavsubmenu_CashTransactions":"Cash Transactions",
            "idnavsubmenu_SetupWallet":"Set up Wallet",
            "idnavsubmenu_Bitcoin":"Bitcoin",
            "idnavsubmenu_Assets":"Assets",
            "idnavsubmenu_BuyAsset":"Buy Asset",
            "idnavsubmenu_TransferCoin":"Transfer Coin",
            "idnavsubmenu_Swap":"Swap",
            "idnavsubmenu_SellAssettoFiat":"Sell Asset to Fiat",
            "idnavsubmenu_DisplayTransactions":"Display Transactions",
            "idnavsubmenu_DigitalRemittance":"Digital Remittance",
            "idnavsubmenu_FiatRemittance":"Fiat Remittance",
            "idnavsubmenu_ShowCards":"Show Cards",
            "idnavsubmenu_RedeemPoints":"Redeem Points",
            "idnavsubmenu_ViewDispute":"View Dispute",
            "idnavsubmenu_RaiseDisputes":"Raise Disputes",
            "idnavsubmenu_ChangeYourPlan":"Change Your Plan",
            "idnavsubmenu_BuyNewPlan":"Buy New Plan",
			
            /* Quick links */
            "idnav_UserSettings":"User Settings",
            "idnav_UpdateProfile":"Update Profile",
            "idnav_Logout":"Logout",
             /* Specific Page Content */
			//"greeting":"Customer",
			"breadcrumb-item-digital-assets":"Digital Assets",
			"breadcrumb-item-swap":"Swap",
			"header-swap":"Swap",
			"destination_label":"Destination",
			"amount_label":"Amount",
			"btn_exchange_asset_label":"Exchange Asset",
			"ops_no_external_wallets_available":"No External Wallets",
			"data-validation-error-swal-header":"Oops..",
			"data-validation-error-failed-to-get-details":"Failed to get details",
			"data-validation-problem-with-connection":"Problem with connection",
			"data-validation-error-porte-coin":"You dont have porte coin, click Register to register",
			"You dont have porte coin, click Register to register":"Please check your data",
			"swap-data-validation-error-from":"Please select coin to swap",
			"swap-data-validation-error-destionation-coin":"Please select destination coin",
			"swap-data-validation-error-amount-to-swap":"Please enter amount",
			"swap-data-no-assets-available":"No assets available",
			"enter-your-passsword-tittle":"Enter your Password",
			"enter-your-passsword-label":"Password",
			"data-validation-enter-your-passsword-label":"Please input your password!",
			"data-validation-enter-your-pvt-key-label":"Please input your Private Key!",
			"enter-your-pvt-key-label":"Enter your Private Key",
			"spn_from_label":"From",
			 "span_select_coin":"Select coin"

			
		}
	  },
	  ES: {
		  translation: {
			/* Menus */
			"idnavmenu_Dashboard":"Tablero",
			"idnavmenu_ManageFiatWallet":"Administrar Monedero Fiat",
			"idnavmenu_DigitalAssets":"Recursos digitales",
			"idnavmenu_CurrencyRemittance":"Remesa de divisas",
			"idnavmenu_ManageCards":"Administrar tarjetas",
			"idnavmenu_Loyalty":"Lealtad",
			"idnavmenu_UpdatePlan":"Actualizar plan",
			"idnavmenu_Logout":"Cerrar sesión",
			"idnavmenu_BuyNewPlan":"Comprar nuevo plan",
			"header_buy_porte_token":"Comprar Token de Porte",
			"btn_buy_coin":"Comprar moneda",
			"idnavmenu_Profile":"Perfil",
             /* Sub Menus */
            "idnavsubmenu_Dashboard":"Tablero",
            "idnavsubmenu_ViewandEdit":"Ver y editar",
            "idnavsubmenu_ViewWallet":"Ver billetera",
            "idnavsubmenu_TopupWallet":"Monedero de recarga",
            "idnavsubmenu_SendMoney":"Enviar dinero",
            "idnavsubmenu_CashTransactions":"Transacciones en efectivo",
            "idnavsubmenu_SetupWallet":"Configurar Monedero",
            "idnavsubmenu_Bitcoin":"Bitcoin",
            "idnavsubmenu_Assets":"Activos",
            "idnavsubmenu_BuyAsset":"Comprar activo",
            "idnavsubmenu_TransferCoin":"Comprar juego de monedas ATransfer",
            "idnavsubmenu_Swap":"Intercambio",
            "idnavsubmenu_SellAssettoFiat":"Vender activos a Fiat",
            "idnavsubmenu_DisplayTransactions":"Mostrar transacciones",
            "idnavsubmenu_DigitalRemittance":"Remesas digitales",
            "idnavsubmenu_FiatRemittance":"Remesa fiduciaria",
            "idnavsubmenu_ShowCards":"Mostrar cartas",
            "idnavsubmenu_RedeemPoints":"Canjear puntos",
            "idnavsubmenu_ViewDispute":"Ver disputa",
            "idnavsubmenu_RaiseDisputes":"Plantear disputas",
            "idnavsubmenu_ChangeYourPlan":"Cambia tu plan",
            "idnavsubmenu_BuyNewPlan":"Comprar nuevo plan",
            /* Quick links */
            "idnav_UserSettings":"Ajustes de usuario",
            "idnav_UpdateProfile":"Actualización del perfil",
            "idnav_Logout":"Cerrar sesión",
             /* Specific Page Content */
			//"greeting":"Cliente",
			"breadcrumb-item-digital-assets":"Recursos digitales",
			"breadcrumb-item-swap":"Intercambio",
			"header-swap":"Intercambio",
			"destination_label":"Destino",
			"amount_label":"Monto",
			"btn_exchange_asset_label":"Activo de intercambio",
			"ops_no_external_wallets_available":"Sin carteras externas",
			"data-validation-error-swal-header":"Ups..",
			"data-validation-error-failed-to-get-details":"Error al obtener detalles",
			"data-validation-problem-with-connection":"problema con la conexion",
			"data-validation-error-porte-coin":"No tienes porte coin, haz click en Registrar para registrarte",
			"data-validation-check-your-data":"Por favor revisa tus datos",
			"swap-data-validation-error-from":"Seleccione la moneda para intercambiar",
			"swap-data-validation-error-destionation-coin":"Seleccione la moneda de destino",
			"swap-data-validation-error-amount-to-swap":"Por favor ingrese la cantidad",
			"swap-data-no-assets-available":"No hay activos disponibles",
			"enter-your-passsword-tittle":"Ingresa tu contraseña",
			"enter-your-passsword-label":"Clave",
			"data-validation-enter-your-passsword-label":"¡Por favor ingrese su contraseña!",
			"data-validation-enter-your-pvt-key-label":"¡Ingrese su clave privada!",
			"enter-your-pvt-key-label":"Ingrese su clave privada",
            "spn_from_label":"De",
            "span_select_coin":"Seleccionar moneda"

			}
		},
	}
  }, function(err, t) {
	   updateContent();
  });
  
  function updateContent() {
        /* Menus */
        $('#idnavmenu_Dashboard').text(i18next.t('idnavmenu_Dashboard')); 
        $('#idnavmenu_ManageFiatWallet').text(i18next.t('idnavmenu_ManageFiatWallet')); 
        $('#idnavmenu_DigitalAssets').text(i18next.t('idnavmenu_DigitalAssets')); 
        $('#idnavmenu_CurrencyRemittance').text(i18next.t('idnavmenu_CurrencyRemittance')); 
        $('#idnavmenu_ManageCards').text(i18next.t('idnavmenu_ManageCards')); 
        $('#idnavmenu_Loyalty').text(i18next.t('idnavmenu_Loyalty')); 
        $('#idnavmenu_UpdatePlan').text(i18next.t('idnavmenu_UpdatePlan')); 
        $('#idnavmenu_Logout').text(i18next.t('idnavmenu_Logout')); 
        $('#idnavmenu_BuyNewPlan').text(i18next.t('idnavmenu_BuyNewPlan')); 
        $('#header_buy_porte_token').text(i18next.t('header_buy_porte_token')); 
        $('#btn_buy_coin').text(i18next.t('btn_buy_coin')); 
        $('#idnavmenu_Profile').text(i18next.t('idnavmenu_Profile')); 
        $('#idnavmenu_Disputes').text(i18next.t('idnavmenu_Disputes')); 
        /* Sub Menus */
        $('#idnavsubmenu_Dashboard').text(i18next.t('idnavsubmenu_Dashboard')); 
        $('#idnavsubmenu_ViewandEdit').text(i18next.t('idnavsubmenu_ViewandEdit')); 
        $('#idnavsubmenu_ViewWallet').text(i18next.t('idnavsubmenu_ViewWallet')); 
        $('#idnavsubmenu_TopupWallet').text(i18next.t('idnavsubmenu_TopupWallet')); 
        $('#idnavsubmenu_SendMoney').text(i18next.t('idnavsubmenu_SendMoney')); 
        $('#idnavsubmenu_CashTransactions').text(i18next.t('idnavsubmenu_CashTransactions')); 
        $('#idnavsubmenu_SetupWallet').text(i18next.t('idnavsubmenu_SetupWallet')); 
        $('#idnavsubmenu_Bitcoin').text(i18next.t('idnavsubmenu_Bitcoin')); 
        $('#idnavsubmenu_Assets').text(i18next.t('idnavsubmenu_Assets')); 
        $('#idnavsubmenu_BuyAsset').text(i18next.t('idnavsubmenu_BuyAsset')); 
        $('#idnavsubmenu_TransferCoin').text(i18next.t('idnavsubmenu_TransferCoin')); 
        $('#idnavsubmenu_Swap').text(i18next.t('idnavsubmenu_Swap')); 
        $('#idnavsubmenu_SellAssettoFiat').text(i18next.t('idnavsubmenu_SellAssettoFiat')); 
        $('#idnavsubmenu_DisplayTransactions').text(i18next.t('idnavsubmenu_DisplayTransactions')); 
        $('#idnavsubmenu_DigitalRemittance').text(i18next.t('idnavsubmenu_DigitalRemittance')); 
        $('#idnavsubmenu_FiatRemittance').text(i18next.t('idnavsubmenu_FiatRemittance')); 
        $('#idnavsubmenu_ShowCards').text(i18next.t('idnavsubmenu_ShowCards')); 
        $('#idnavsubmenu_RedeemPoints').text(i18next.t('idnavsubmenu_RedeemPoints')); 
        $('#idnavsubmenu_ViewDispute').text(i18next.t('idnavsubmenu_ViewDispute')); 
        $('#idnavsubmenu_RaiseDisputes').text(i18next.t('idnavsubmenu_RaiseDisputes')); 
        $('#idnavsubmenu_ChangeYourPlan').text(i18next.t('idnavsubmenu_ChangeYourPlan')); 
        $('#idnavsubmenu_BuyNewPlan').text(i18next.t('idnavsubmenu_BuyNewPlan')); 
        /* Quick links */
        $('#idnav_UserSettings').text(i18next.t('idnav_UserSettings'));        
        $('#idnav_UpdateProfile').text(i18next.t('idnav_UpdateProfile'));        
        $('#idnav_Logout').text(i18next.t('idnav_Logout'));        
        /* Specific Page Content */
       // $('#greeting').text(i18next.t('greeting'));
        $('#breadcrumb-item-digital-assets').text(i18next.t('breadcrumb-item-digital-assets'));
        $('#breadcrumb-item-swap').text(i18next.t('breadcrumb-item-swap'));
        $('#header-swap').text(i18next.t('header-swap'));
        $('#destination_label').text(i18next.t('destination_label'));
        $('#amount_label').text(i18next.t('amount_label'));
        $('#btn_exchange_asset_label').text(i18next.t('btn_exchange_asset_label'));
        $('#ops_no_external_wallets_available').text(i18next.t('ops_no_external_wallets_available'));
        $('#data-validation-error-swal-header').text(i18next.t('data-validation-error-swal-header'));
        $('#data-validation-error-failed-to-get-details').text(i18next.t('data-validation-error-failed-to-get-details'));
        $('#data-validation-problem-with-connection').text(i18next.t('data-validation-problem-with-connection'));
        $('#data-validation-error-porte-coin').text(i18next.t('data-validation-error-porte-coin'));
        $('#data-validation-check-your-data').text(i18next.t('data-validation-check-your-data'));
        $('#swap-data-validation-error-from').text(i18next.t('swap-data-validation-error-from'));
        $('#swap-data-validation-error-destionation-coin').text(i18next.t('swap-data-validation-error-destionation-coin'));
        $('#swap-data-validation-error-amount-to-swap').text(i18next.t('swap-data-validation-error-amount-to-swap'));
        $('#swap-data-no-assets-available').text(i18next.t('swap-data-no-assets-available'));
        $('#enter-your-passsword-tittle').text(i18next.t('enter-your-passsword-tittle'));
        $('#enter-your-passsword-label').text(i18next.t('enter-your-passsword-label'));
        $('#data-validation-enter-your-passsword-label').text(i18next.t('data-validation-enter-your-passsword-label'));
        $('#data-validation-enter-your-pvt-key-label').text(i18next.t('data-validation-enter-your-pvt-key-label'));
        $('#enter-your-pvt-key-label').text(i18next.t('enter-your-pvt-key-label'));       
        $('#spn_from_label').text(i18next.t('spn_from_label'));   
        $('#span_select_coin').text(i18next.t('span_select_coin'));
    
  }
	  function fnChangePageLanguage(lng){
		console.log('lng ',lng);
		  i18next.changeLanguage(lng, fnChangeLanguage(lng))
		  $('#hdnlangpref').val(lng);
	  }
	  i18next.on('languageChanged', function(lng) {
		updateContent(lng);
	  });
	  function fnChangeLanguage(lang){
		  if(lang=='EN')  $('#lang_def').text('EN') 
			else if(lang=='ES')  $('#lang_def').text('ES')
	  }





$(window).on("load", function(e) {
    $("#global-loader").fadeOut("slow");
})
let source_Assetcode;
let dest_Assetcode;
function fnUpdatesenderparams(assetCodeVal) {
    var assetcode = assetCodeVal;
    if (assetcode == '') {
        swal.fire('Select Destination Coin');
        return false;
    } else {
       // cryptocoversion();
        $("#spansendcode").text(assetcode);
        //$("#receiver_asset").val(assetcode);

		if(assetcode==="VESL"||assetcode==="vesl"){
			document.getElementById("sell_amount").style.backgroundImage = "url('./assets/images/crypto/vessel.png')";
        }
        if(assetcode==="PORTE" || assetcode==="porte"){
			document.getElementById("sell_amount").style.backgroundImage = "url('./assets/images/crypto/porte.svg')";
        }
        if(assetcode==="XLM"||assetcode==="xlm"){
			document.getElementById("sell_amount").style.backgroundImage = "url('./assets/images/crypto/xlm.svg')";
        }
        if(assetcode==="USDC"||assetcode==="usdc"){
			document.getElementById("sell_amount").style.backgroundImage = "url('./assets/images/crypto/usdc.png')";
        }
    }
    
}

function fnUpdateReceiverParams(assetCodeVal) {
    var assetcode = assetCodeVal;
    if (assetcode == '') {
        swal.fire('Select Destination Coin');
        return false;
    } else {
       // cryptocoversion();
        $("#spanreceivedcode").text(assetcode);
       // $("#receiver_asset").val(assetcode);

		if(assetcode==="VESL"||assetcode==="vesl"){
			document.getElementById("receivedamount").style.backgroundImage = "url('./assets/images/crypto/vessel.png')";
        }
        if(assetcode==="PORTE" || assetcode==="porte"){
			document.getElementById("receivedamount").style.backgroundImage = "url('./assets/images/crypto/porte.svg')";
        }
        if(assetcode==="XLM"||assetcode==="xlm"){
			document.getElementById("receivedamount").style.backgroundImage = "url('./assets/images/crypto/xlm.svg')";
        }
        if(assetcode==="USDC"||assetcode==="usdc"){
			document.getElementById("receivedamount").style.backgroundImage = "url('./assets/images/crypto/usdc.png')";
        }
    }
    
}


function fnGetCoinDetails(){
	$('#post-form input[name="qs"]').val('porte');
	$('#post-form input[name="rules"]').val('get_crypto_assets_details_without_btc');
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
				$('#buy_coin_asset').html('');				
				if(data.error="false"){
                   if (porteCoinList.length > 0) {
              		htmlOptions+=`
                		<select id="select-asset-id" onChange="javascript: fnOnchange(); return false">
                  		<option selected disabled>Select Coin</option>
                  		`;
                  for (i = 0; i < porteCoinList.length; i++) {
                    if(porteCoinList[i].assetCode==="PORTE"){
                    htmlOptions+=`<option value="PORTE" data-imagesrc="assets/images/crypto/porte.svg" value="` +
                      porteCoinList[i].accountId +
                      `,` +
                      porteCoinList[i].assetCode +
                      `">` +
                      porteCoinList[i].assetCode +
                      `</option>`;
                    }
                    if(porteCoinList[i].assetCode==="VESL"){
                    htmlOptions+=` <option value="VESL" data-imagesrc="assets/images/crypto/vessel.png"value="` +
                      porteCoinList[i].accountId +
                      `,` +
                      porteCoinList[i].assetCode +
                      `">` +
                      porteCoinList[i].assetCode +
                      `</option>`;
                    }
                    if(porteCoinList[i].assetCode==="XLM"){
                    htmlOptions+=` <option value="XLM" data-imagesrc="assets/images/crypto/xlm.svg"value="` +
                      porteCoinList[i].accountId +
                      `,` +
                      porteCoinList[i].assetCode +
                      `">` +
                      porteCoinList[i].assetCode +
                      `</option>`;
                    }
                    if(porteCoinList[i].assetCode==="USDC"){
                    htmlOptions+=`<option value="USDC" data-imagesrc="assets/images/crypto/usdc.png"value="` +
                      porteCoinList[i].accountId +
                      `,` +
                      porteCoinList[i].assetCode +
                      `">` +
                      porteCoinList[i].assetCode +
                      `</option>`;
                    }
                  }
                 
                  htmlOptions+=` </select>`;
         		 }else{
                        htmlOptions+=`<option disabled="disabled" value="-1" selected>`+$('#swap-data-no-assets-available').text()+`</option>`;
                    }
            		$("#buy_coin_asset").append(htmlOptions);
		            $('#select-asset-id').ddslick({
		              onSelected: function(data){
		                displaySelectedData(data);
		            }
		            });
                   /* $('#coin_asset').append(htmlOptions);
                    $('#receiver_asset').append(htmlOptions);*/
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
function fnGetDestCoinDetails(){
	$('#post-form input[name="qs"]').val('porte');
	$('#post-form input[name="rules"]').val('get_crypto_assets_details_without_btc');
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
				$('#sel_currency_trading').html('');
				
				if(data.error="false"){
                   if (porteCoinList.length > 0) {
              		htmlOptions+=`
                		<select id="select-asset-id-dest" onChange="javascript: fnOnchange(); return false">
                  		<option selected disabled>Select Coin</option>
                  		`;
                  for (i = 0; i < porteCoinList.length; i++) {
                    if(porteCoinList[i].assetCode==="PORTE"){
                    htmlOptions+=`<option value="PORTE" data-imagesrc="assets/images/crypto/porte.svg" value="` +
                      porteCoinList[i].accountId +
                      `,` +
                      porteCoinList[i].assetCode +
                      `">` +
                      porteCoinList[i].assetCode +
                      `</option>`;
                    }
                    if(porteCoinList[i].assetCode==="VESL"){
                    htmlOptions+=` <option value="VESL" data-imagesrc="assets/images/crypto/vessel.png"value="` +
                      porteCoinList[i].accountId +
                      `,` +
                      porteCoinList[i].assetCode +
                      `">` +
                      porteCoinList[i].assetCode +
                      `</option>`;
                    }
                    if(porteCoinList[i].assetCode==="XLM"){
                    htmlOptions+=` <option value="XLM" data-imagesrc="assets/images/crypto/xlm.svg"value="` +
                      porteCoinList[i].accountId +
                      `,` +
                      porteCoinList[i].assetCode +
                      `">` +
                      porteCoinList[i].assetCode +
                      `</option>`;
                    }
                    if(porteCoinList[i].assetCode==="USDC"){
                    htmlOptions+=`<option value="USDC" data-imagesrc="assets/images/crypto/usdc.png"value="` +
                      porteCoinList[i].accountId +
                      `,` +
                      porteCoinList[i].assetCode +
                      `">` +
                      porteCoinList[i].assetCode +
                      `</option>`;
                    }
                  }
                 
                  htmlOptions+=` </select>`;
         		 }else{
                        htmlOptions+=`<option disabled="disabled" value="-1" selected>`+$('#swap-data-no-assets-available').text()+`</option>`;
                    }
					$("#sel_currency_trading").append(htmlOptions);
		            $('#select-asset-id-dest').ddslick({
		              onSelected: function(data){
		                displaySelectedData2(data);
		            }
		            });
                   /* $('#coin_asset').append(htmlOptions);
                    $('#receiver_asset').append(htmlOptions);*/
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
 function displaySelectedData(data){
    console.log('displaySelectedData data is ',data)
    var selectedValue = data.selectedData.value
    //$("#sender_asset").text(assetcode);
   fnUpdatesenderparams(selectedValue)
   source_Assetcode = selectedValue
  }
 function displaySelectedData2(data){
    console.log('displaySelectedData data is ',data)
    var selectedValue = data.selectedData.value
    //$("#sender_asset").text(assetcode);
   fnUpdateReceiverParams(selectedValue)
   dest_Assetcode = selectedValue

  }
function fnSellPorteCoin (security,hasMnemonic){
    $( "#sell-porte-coin-form" ).validate( {
        rules: {
            coin_asset: {
                required: true
            },
            sell_amount: {
                required: true
            },
            receivedamount: {
                required: true
            },
            receiver_asset:{
                required: true
            }
        },
        messages: {
            coin_asset: {
                required: $('#swap-data-validation-error-from').text()
            },
            sell_amount: {
                required: $('#swap-data-validation-error-amount-to-swap').text()
            },
            receivedamount: {
                required: $('#swap-data-validation-error-amount-to-swap').text()
            },
            receiver_asset:{
                required: $('#swap-data-validation-error-destionation-coin').text()
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

    if($( "#sell-porte-coin-form" ).valid()){
		$('#spinner-div').show();//Load button clicked show spinner
		
		
		var sourceAssetcode = source_Assetcode
	  	var destCurrency = dest_Assetcode
		
        var formData = new FormData($('#sell-porte-coin-form')[0]);
        formData.append('qs', 'porte');
        formData.append('rules', 'sell_porte_coin'); 
        formData.append('relno', relno); 
        formData.append("hdnlang", $('#lang_def').text());
		formData.append('security',security);
		formData.append('hasMnemonic',hasMnemonic);
		formData.append('coin_asset',sourceAssetcode);
		formData.append('receiver_asset',destCurrency);
        for (var pair of formData.entries()) {
	 			console.log(pair[0]+ ', ' + pair[1]);
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
					  title: 'Swap Complete',
					  text: data.message,
					  imageUrl: 'assets/images/crypto/success.svg',
					  imageWidth: 400,
					  imageHeight: 200,
					  imageAlt: 'Custom image',
					}).then(function() {
                        $('#post-form').attr('action', 'ws');
                        $('input[name="qs"]').val('porte');
                        $('input[name="rules"]').val('Display Transactions');
                        $("#post-form").submit();
                    });
                }else{
                    Swal.fire({
						  text: data.message,
						  imageUrl: 'assets/images/crypto/error.svg',
						  imageWidth: 400,
						  imageHeight: 200,
						  imageAlt: 'Custom image',
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
                                htmlOptions+=`<div class="wallet-transaction-box clearfix"  >
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
                                htmlOptions+=`<div class="wallet-transaction-box clearfix">
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
							text: $('#data-validation-error-porte-coin').text(),
							showConfirmButton: true,
							confirmButtonText: "Register",
							closeOnConfirm: true,
							}).then(function() {
								$('#post-form').attr('action', 'ws');
								$('input[name="qs"]').val('wal');
								$('input[name="rules"]').val('Create Wallet');
								$("#post-form").submit();
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
    //exchange_icon();
    fnGetCoinDetails();
    fnFetchPorteCoins ();
	fnGetDestCoinDetails()
    $("#sell_amount").keyup(function() {
    cryptocoversionFromSource();
  });

  $("#receivedamount").keyup(function() {
    cryptocoversionFromDestination();
  });
});



function fnGetExpectedAmount(){
    $( "#sell-porte-coin-form" ).validate( {
        rules: {
            coin_asset: {
                required: true
            },
            amount: {
                required: true
            },
            receiver_asset:{
                required: true
            }
        },
        messages: {
            coin_asset: {
                required: $('#swap-data-validation-error-from').text()
            },
            amount: {
                required: $('#swap-data-validation-error-amount-to-swap').text()
            },
            receiver_asset:{
                required: $('#swap-data-validation-error-destionation-coin').text()

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

    if($( "#sell-porte-coin-form" ).valid()){

        $('#post-form input[name="qs"]').val('porte');
        $('#post-form input[name="rules"]').val('get_expected_amount');
        var form = $('#post-form')[0];
        var formData = new FormData(form);
      
        formData.append("coin_asset", $("#coin_asset option:selected").val());
        formData.append("receiver_asset",  $("#receiver_asset option:selected").val());
        formData.append("amount",  $("#amount").val());

        for (var pair of formData.entries()) {
            //alert(pair[0] + " - " + pair[1]);
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
                   
                    var destinationAmount = '';
                    if(data.error="false"){
                        destinationAmount=data.destination_amount;
                    }else{
                        destinationAmount="";
                    }
                    $("#receivedamount").val(destinationAmount);
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

function cryptocoversionFromSource(){
	// Validate inputs required before sending
	
	 $( "#sell-porte-coin-form" ).validate( {
        rules: {
            coin_asset: {
                required: true
            },
            amount: {
                required: true
            },
            receiver_asset:{
                required: true
            }
        },
        messages: {
            coin_asset: {
                required: $('#swap-data-validation-error-from').text()
            },
            amount: {
                required: $('#swap-data-validation-error-amount-to-swap').text()
            },
            receiver_asset:{
                required: $('#swap-data-validation-error-destionation-coin').text()

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
	
	 if($( "#sell-porte-coin-form" ).valid()){
		 /* var sourceAssetcode = $("#coin_asset option:selected").val();
		  var destAssetcode = $("#receiver_asset option:selected").val();*/
		  /*sourceAssetcode = sourceAssetcode.split(",")
		  sourceAssetcode = sourceAssetcode[1];
		  destAssetcode = destAssetcode.split(",")
		  destAssetcode = destAssetcode[1];*/

			var sourceAssetcode = source_Assetcode
	  		var destAssetcode = dest_Assetcode
		
		  var amount = $("#sell_amount").val();
		  var formData = new FormData($('#sell-porte-coin-form')[0]);//sell-porte-coin-form
		  formData.append('qs', 'porte');
		  formData.append('rules', 'asset_exchange_conversion'); 
		  //formData.append('hdnapikey', getAPIKEy()); 
		  formData.append('amount', amount); 
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
					let destAmount='';
					destAmount=data.data;
		             console.log('destAmount ',destAmount);
		        	if (destAmount){
						console.log("dest has value")
						$("#offers_not_available_text").addClass('hidden');
						$("#exchange_coin_btn").removeClass("hidden");
						$("#exchange_coin_btn_disabled").addClass("hidden");
					}else{			
						console.log("dest has no value")
						$("#offers_not_available_text").removeClass('hidden');
						$("#exchange_coin_btn").addClass("hidden");
						$("#exchange_coin_btn_disabled").removeClass("hidden");
						destAmount=0;
					}
		            $("#receivedamount").val(destAmount);
		
		        
		      },
		      error: function() {
		        console.log('Problem with connection')
		      }
		  });  
		
	}
}

function cryptocoversionFromDestination(){
	// Validate inputs required before sending
	
	 $( "#sell-porte-coin-form" ).validate( {
        rules: {
            coin_asset: {
                required: true
            },
            amount: {
                required: true
            },
            receiver_asset:{
                required: true
            }
        },
        messages: {
            coin_asset: {
                required: $('#swap-data-validation-error-from').text()
            },
            amount: {
                required: $('#swap-data-validation-error-amount-to-swap').text()
            },
            receiver_asset:{
                required: $('#swap-data-validation-error-destionation-coin').text()

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
	
	
	if($( "#sell-porte-coin-form" ).valid()){
		
	  var sourceAssetcode =  $("#receive_asset option:selected").val(); 
	  var destAssetcode = $("#sell_coin_asset option:selected").val();
	  sourceAssetcode = sourceAssetcode.split(",")
	  sourceAssetcode = sourceAssetcode[1];
	  destAssetcode = destAssetcode.split(",")
	  destAssetcode = destAssetcode[1];
	  var amount = $("#receivedamount").val();
	  var formData = new FormData($('#sell-porte-coin-form')[0]);
	  formData.append('qs', 'porte');
	  formData.append('rules', 'asset_exchange_conversion'); 
	  formData.append('amount', amount); 
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
					let destAmount='';
					destAmount=data.data;
		             console.log('destAmount ',destAmount);
		        	if (destAmount){
						console.log("dest has value")
						$("#offers_not_available_text").addClass('hidden');
						$("#exchange_coin_btn").removeClass("hidden");
						$("#exchange_coin_btn_disabled").addClass("hidden");
					}else{			
						console.log("dest has no value")
						$("#offers_not_available_text").removeClass('hidden');
						$("#exchange_coin_btn").addClass("hidden");
						$("#exchange_coin_btn_disabled").removeClass("hidden");
						destAmount=0;
					}
		            $("#sell_amount").val(destAmount);
	
	      },
	      error: function() {
	        console.log('Problem with connection')
	      }
	  });  
  }
}


function checkIfUserHasMneonicCode(){
	// validation of form
	
	
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

function fnPassword(hasMnemonic){
	const { value: password   } = Swal.fire({
		title: $('#enter-your-passsword-tittle').text(),
		input: 'password',
		inputLabel: $('#enter-your-passsword-label').text(),
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
				fnSellPorteCoin (passwordVal,hasMnemonic);
			}
		});
}

function fnPrivateKey(hasMnemonic){
	const { value: password   } = Swal.fire({
		title: $('#enter-your-pvt-key-label').text(),
		input: 'password',
		inputLabel: $('#enter-your-passsword-label').text(),
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
				var privatekey = result.value;
				fnSellPorteCoin (privatekey,hasMnemonic);
			}
		});
}








   



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
			"breadcrumb-item-currency-remittance":"Currency Remittance",
			"breadcrumb-item-trde-currency":"Trade Currency",
			"header-view-remittance-txn":"View Remittance Transactions",
			"header-fiat-currency-remittance":"Fiat Currency Remittance",
			"header-currency-details":"Currency Details",
			"select-source-currency-label":"Select Source Currency",
			"spn_option_fiat_currency":"No Fiat Currency to trade available at the Moment",
			"span_destination_currency_label":"Select Destination Currency",
			"spn_no_currency_to_trade":"No Currency to trade available at the Moment",
			"source_amount_label":"Source Amount in ",
			"spn_expected_amount":"Expected Amount in",
			"expected_amount_warning_text":"This rate is valid for the next 24 hours",
			"receiver_details_header":"Enter Receiver Details",
			"receiver_fullname_label":"Receiver's Name",
			"receiver_email_label":"Receiver's Email",
			"receiver_bank_name":"Receiver's Bank Name",
			"receiver_bank_code":"Receiver's Bank Code",
			"receiver_account_number":"Receiver's Account Number",
			"data-validation-error-swal-header":"Oops..",
			"data-validation-error-failed-to-get-details":"Failed to get details",
			"data-validation-problem-with-connection":"Problem with connection",
			"data-validation-check-your-data":"Please check your data",
			"data-validation-please-select-currency":"Please select Currency",
			"data-validation-please-select-coin":"Please select Coin",
			"data-validation-please-enter-amount":"Please enter amount",
			"data-validation-required-field":"This field is required",
			"data-validation-email":"This field has to be email",
			"no-offers-available":"No offers available",
			"no-do-not-have-trustline":"You do not have a Trustline with this issuer",
			"do-you-want-to-create-trustline":"Do you want to create Trustline?",
			"enter-your-secret-key":"Enter your Secret Key",
			"password-label":"Password",
			"password-validation-label":"Please input your Secret Key!",
			"enter-password-label":"Enter your Password",
			"enter-password-input-validation":"Please input your password!",
			"exchange-is-successful":"Currency Exchange Successful",
			"label_choose":"Choose one",

		
			
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
			"breadcrumb-item-currency-remittance":"Remesa de divisas",
			"breadcrumb-item-trde-currency":"Moneda comercial",
			"header-view-remittance-txn":"Ver transacciones de remesas",
			"header-fiat-currency-remittance":"Remesa de moneda fiduciaria",
			"header-currency-details":"Detalles de moneda",
			"select-source-currency-label":"Seleccione la moneda de origen",
			"spn_option_fiat_currency":"No hay moneda fiduciaria para negociar disponible en este momento",
			"span_destination_currency_label":"Seleccione la moneda de destino",
			"spn_no_currency_to_trade":"No hay moneda para operar disponible en este momento",
			"source_amount_label":"Fuente Importe en",
			"spn_expected_amount":"Cantidad esperada en",
			"expected_amount_warning_text":"Esta tarifa es válida durante las próximas 24 horas",
			"receiver_details_header":"Ingrese los detalles del receptor",
			"receiver_fullname_label":"Nombre del destinatario",
			"receiver_email_label":"Correo electrónico del destinatario",
			"receiver_bank_name":"Nombre del banco del receptor",
			"receiver_bank_code":"Código bancario del receptor",
			"receiver_account_number":"Número de cuenta del destinatario",
			"data-validation-error-swal-header":"Ups..",
			"data-validation-error-failed-to-get-details":"Error al obtener detalles",
			"data-validation-problem-with-connection":"problema con la conexion",
			"data-validation-check-your-data":"Por favor revisa tus datos",
			"data-validation-please-select-currency":"Por favor seleccione Moneda",
			"data-validation-please-select-coin":"Por favor seleccione Moneda",
			"data-validation-please-enter-amount":"Por favor ingrese la cantidad",
			"data-validation-required-field":"Este campo es obligatorio",
			"data-validation-email":"Este campo tiene que ser correo electrónico",
			"no-offers-available":"no hay ofertas disponibles",
			"no-do-not-have-trustline":"No tienes un Trustline con este emisor",
			"do-you-want-to-create-trustline":"¿Quieres crear Trustline?",
			"enter-your-secret-key":"Ingrese su clave secreta",
			"password-label":"Clave",
			"password-validation-label":"¡Por favor ingrese su clave secreta!",
			"enter-password-label":"Ingresa tu contraseña",
			"enter-password-input-validation":"¡Por favor ingrese su contraseña!",
			"exchange-is-successful":"Cambio de divisas exitoso",
			"label_choose":"Elige uno",

			
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
        $('#breadcrumb-item-currency-remittance').text(i18next.t('breadcrumb-item-currency-remittance'));
        $('#breadcrumb-item-trde-currency').text(i18next.t('breadcrumb-item-trde-currency'));
        $('#header-view-remittance-txn').text(i18next.t('header-view-remittance-txn'));
        $('#header-fiat-currency-remittance').text(i18next.t('header-fiat-currency-remittance'));
        $('#header-currency-details').text(i18next.t('header-currency-details'));
        $('#select-source-currency-label').text(i18next.t('select-source-currency-label'));
        $('#spn_option_fiat_currency').text(i18next.t('spn_option_fiat_currency'));
        $('#span_destination_currency_label').text(i18next.t('span_destination_currency_label'));
        $('#spn_no_currency_to_trade').text(i18next.t('spn_no_currency_to_trade'));
        $('#source_amount_label').text(i18next.t('source_amount_label'));
        $('#spn_expected_amount').text(i18next.t('spn_expected_amount'));
        $('#expected_amount_warning_text').text(i18next.t('expected_amount_warning_text'));
        $('#receiver_details_header').text(i18next.t('receiver_details_header'));
        $('#receiver_fullname_label').text(i18next.t('receiver_fullname_label'));
        $('#receiver_email_label').text(i18next.t('receiver_email_label'));
        $('#receiver_bank_name').text(i18next.t('receiver_bank_name'));
        $('#receiver_bank_code').text(i18next.t('receiver_bank_code'));
        $('#receiver_account_number').text(i18next.t('receiver_account_number'));
        $('#data-validation-error-swal-header').text(i18next.t('data-validation-error-swal-header'));
        $('#data-validation-error-failed-to-get-details').text(i18next.t('data-validation-error-failed-to-get-details'));
        $('#data-validation-problem-with-connection').text(i18next.t('data-validation-problem-with-connection'));
        $('#data-validation-check-your-data').text(i18next.t('data-validation-check-your-data'));
        $('#data-validation-please-select-currency').text(i18next.t('data-validation-please-select-currency'));
        $('#data-validation-please-select-coin').text(i18next.t('data-validation-please-select-coin'));
        $('#data-validation-please-enter-amount').text(i18next.t('data-validation-please-enter-amount'));
        $('#data-validation-required-field').text(i18next.t('data-validation-required-field'));
        $('#data-validation-email').text(i18next.t('data-validation-email'));
        $('#no-offers-available').text(i18next.t('no-offers-available'));
        $('#no-do-not-have-trustline').text(i18next.t('no-do-not-have-trustline'));
        $('#do-you-want-to-create-trustline').text(i18next.t('do-you-want-to-create-trustline'));
        $('#enter-your-secret-key').text(i18next.t('enter-your-secret-key'));
        $('#password-label').text(i18next.t('password-label'));
        $('#password-validation-label').text(i18next.t('password-validation-label'));
        $('#enter-password-label').text(i18next.t('enter-password-label'));
        $('#enter-password-input-validation').text(i18next.t('enter-password-input-validation'));
        $('#exchange-is-successful').text(i18next.t('exchange-is-successful'));
       		$('#label_choose').text(i18next.t('label_choose')); 

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





(function($) {
	"use strict";
	
	//accordion-wizard
	var options = {
		mode: 'wizard',
		autoButtonsNextClass: 'btn btn-primary float-right',
		autoButtonsPrevClass: 'btn btn-info',
		stepNumberClass: 'badge badge-pill badge-primary mr-1',
		onSubmit: function() {
			//checkIfUserHasMneonicCode();
            fnSubmitPayment();
           // alert('hohoho');

		  return false;
		}
	}
	$( "#form" ).accWizard(options);
	$('#next_btn').click(function() {
		// alert('hohoho');
		//getStellarOffers();
	}); 

	fetchCurrencyandPorteAssets();	
})(jQuery);   
let source_Assetcode;
let dest_Assetcode;

function fetchCurrencyandPorteAssets(){

  var formData = new FormData();
  formData.append('qs', 'frx');
  formData.append('rules', 'get_fiat_currency_and_assets_to_exchange'); 

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
    type: "POST",
    success: function (result) {
      if (result) {
        var data = JSON.parse(result);
        var assetsCoins = data.fiatcurrencies;
        console.log("assetsCoins",assetsCoins);
        console.table(assetsCoins);
        var digitalCurrencies = data.digitalcurrencies;
        var assetCoinsHtml = "";
        var digitalCurrenciesHtml = "";
        

        $("#sel_currency_trading").html("");
          $("#buy_coin_asset").html("");
          if (digitalCurrencies.length > 0) {
            digitalCurrenciesHtml+=`
            <select id="select-asset-digital-id">
              <option selected disabled>Select Coin</option>
              `;
            for (var i = 0; i < digitalCurrencies.length; i++) {
              console.log(`getting the currency id ${digitalCurrencies[i].assetCode}`)

              if(digitalCurrencies[i].assetCode ==="HKD"){
                digitalCurrenciesHtml+=`<option  data-imagesrc="assets/images/crypto/hong-kong.png"
                value="` +digitalCurrencies[i].assetCode +`">`+ digitalCurrencies[i].assetCode +`</option>`;
              }
              if(digitalCurrencies[i].assetCode ==="AUD"){
                digitalCurrenciesHtml+=` <option data-imagesrc="assets/images/crypto/australia.png"
                value="` +digitalCurrencies[i].assetCode +`">`+ digitalCurrencies[i].assetCode +`</option>`;
              }
            }
          }
           else {
            digitalCurrenciesHtml += `<option selected disabled id="sel_no_currency">`+$("#sel_no_currency").text()+`</option>`;
          }
          // if (assetsCoins.length > 0) {
          //   assetCoinsHtml += `<option disabled="disabled" selected id="sel_choose_one">`+$("#sel_choose_one").text()+`</option>`;
          //   for ( var j = 0; j < assetsCoins.length; j++) {
          //       assetCoinsHtml +=
          //       `<option class="icon-btcoin" value="` +assetsCoins[j].assetCode +`">`+ assetsCoins[j].assetDescription +`</option>`;
          //   }
          // }
          if (assetsCoins.length > 0) {
            assetCoinsHtml+=`
              <select id="select-asset-id">
                <option selected disabled>Select Coin</option>
                `;
                for ( var j = 0; j < assetsCoins.length; j++) {
                  if(assetsCoins[j].assetCode==="USD"){
                    assetCoinsHtml+=`<option value="USD" data-imagesrc="assets/images/crypto/usd.png" 
                    value="` +assetsCoins[j].assetCode +`">`+ assetsCoins[j].assetDescription +`</option>`;
                  }
                }
                assetCoinsHtml+=` </select>`;

            } 

           else {
            assetCoinsHtml += `<option selected disabled id="sel_no_coin">No Source Coins at the Moment</option>`;
          }

            $("#buy_coin_asset").append(assetCoinsHtml);
            $('#select-asset-id').ddslick({
			defaultSelectedIndex:1,
              onSelected: function(data){
                displaySelectedData(data);
            }
            });
			$('#select-asset-id').ddslick('select', {index: 0 });
            $("#sel_currency_trading").append(digitalCurrenciesHtml);
            $('#select-asset-digital-id').ddslick({
              onSelected: function(data){
                displaySelectedDataTwo(data);
            }
            });
      }
    },
    error: function () {
      Swal.fire({
        icon: "error",
        title: $("#swal_validation_title_error").text(),
        text:  $("#swal_validation_connection_problem").text(),
        showConfirmButton: true,
        confirmButtonText: "Ok",
      }).then(function () {});
    },
  });
}
function displaySelectedData(data){
  var selectedValue = data.selectedData.value
  fnUpdateSourceParams(selectedValue)
 source_Assetcode = selectedValue  
}
function displaySelectedDataTwo(data){
  var selectedValue = data.selectedData.value
  console.log(`selected value is ${selectedValue}`)
  fnUpdateDestinationParams(selectedValue)
  dest_Assetcode=selectedValue;

}

function fnSubmitPayment (){
    $( "#form" ).validate( {

         rules: {
            sel_fiat_currency: {
                required: true
            },
            sel_digital_currency: {
                required: true
            },
            source_amount: {
                required: true
            },
            
            receiver_name: {
                required: true
            },
            receiver_email: {
                required: true,
				email: true
            },
            receiver_bank_name: {
                required: true
            },
            receiver_bank_code: {
                required: true
            },
            receiver_account_no: {
                required: true
            }
        },
        messages: {
			sel_fiat_currency: {
                required: $('#data-validation-please-select-currency').text()
            },
            sel_digital_currency: {
                required: $('#data-validation-please-select-currency').text()
            },
            source_amount: {
                required: $('#data-validation-please-enter-amount').text()
            },
            
            receiver_name: {
                required: $('#data-validation-required-field').text()
            },
            receiver_email: {
                required: $('#data-validation-required-field').text(),
				email: $('#data-validation-email').text()
            },
            receiver_bank_name: {
                required: $('#data-validation-required-field').text()
            },
            receiver_bank_code: {
                required: $('#data-validation-required-field').text()
            },
            receiver_account_no: {
                required: $('#data-validation-required-field').text()
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
	
	
    if($("#form").valid()){
		var sel_fiat_currency = source_Assetcode
	    var sel_digital_currency = dest_Assetcode

        var formData = new FormData($('#form')[0]);
        formData.append('qs', 'frx');
        formData.append('rules', 'cust_fiat_remmittance'); 
        formData.append('sel_fiat_currency', sel_fiat_currency); 
        formData.append('sel_digital_currency', sel_digital_currency); 
        formData.append("hdnlang", $('#lang_def').text());
     	
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
                         $('#post-form-2').attr('action', 'ws');
						   $('input[name="qs"]').val('frx');
						    $('input[name="rules"]').val('get_pending_currency_trading_page');
							$('input[name="hdnlang"]').val($('#lang_def').text());
						    $("#post-form-2").submit();	

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


function fnUpdateSourceParams(assetcodeVal) {
/*    var assetcode = $("#sel_digital_currency option:selected").val();
*/    
	var assetcode = assetcodeVal;
	if (assetcode == '') {
        swal.fire('Select Destination Coin');
        return false;
    } else {
        //UpdateConversionRate();
        $("#span_expected_code").text(assetcode);
        // $("#span_expected_code").val(assetcode);
		if(assetcode==="VESL"||assetcode==="vesl"){
			document.getElementById("source_amount").style.backgroundImage = "url('./assets/images/crypto/vessel.png')";
        }
        if(assetcode==="PORTE" || assetcode==="porte"){
			document.getElementById("source_amount").style.backgroundImage = "url('./assets/images/crypto/porte.svg')";
        }
        if(assetcode==="XLM"||assetcode==="xlm"){
			document.getElementById("source_amount").style.backgroundImage = "url('./assets/images/crypto/xlm.svg')";
        }
        if(assetcode==="USDC"||assetcode==="usdc"){
			document.getElementById("source_amount").style.backgroundImage = "url('./assets/images/crypto/usdc.png')";
        }
    }
    
}
function fnUpdateDestinationParams(assetcodeVal) {
/*    var assetcode = $("#sel_digital_currency option:selected").val();
*/    
	var assetcode = assetcodeVal;
	if (assetcode == '') {
        swal.fire('Select Destination Coin');
        return false;
    } else {
        //UpdateConversionRate();
        $("#span_expected_code").text(assetcode);
        // $("#span_expected_code").val(assetcode);
		if(assetcode==="AUD"||assetcode==="aud"){
			document.getElementById("expected_amount").style.backgroundImage = "url('./assets/images/crypto/australia.png')";
        }
        if(assetcode==="HKD" || assetcode==="hkd"){
			document.getElementById("expected_amount").style.backgroundImage = "url('./assets/images/crypto/hong-kong.png')";
        }
    }
    
}

function getExpectedAmount(){
    $( "#form" ).validate( {
        rules: {
           sel_fiat_currency: {
               required: true
           },
           sel_digital_currency: {
               required: true
           },
           source_amount: {
               required: true
           }
       },
       messages: {
           sel_fiat_currency: {
               required: $('#data-validation-please-select-currency').text()
           },
           sel_digital_currency: {
               required: $('#data-validation-please-select-currency').text()
           },
           source_amount: {
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

   if($( "#form" ).valid()){
        var sel_fiat_currency = source_Assetcode
	    var sel_digital_currency = dest_Assetcode

       var formData = new FormData($('#form')[0]);
       formData.append('qs', 'frx');
       formData.append('rules', 'get_expected_amount_fiat_remittance'); 
       formData.append('sel_fiat_currency', sel_fiat_currency); 
       formData.append('sel_digital_currency', sel_digital_currency); 
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
               var expectedAmount = '';
               if(data.error=='false'){
                expectedAmount = data.expectedAmount;
                $("#expected_amount").val(expectedAmount);
                $("#expected_amount_warning_text").show();
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

function fnCallRemittanceTransactionPage(){
	$('#post-form-2').attr('action', 'ws');
	$('input[name="qs"]').val('frx');
	$('input[name="rules"]').val('get_pending_currency_trading_page');
	$('input[name="hdnlang"]').val($('#lang_def').text());
	$("#post-form-2").submit();	
}

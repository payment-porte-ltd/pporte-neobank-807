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
			"breadcrumb-item-trade-currency":"Trade Currency",
			"header_view_remittance_txn":"View Remittance Transactions",
			"header_trade_digital_currency":"Trade Digital Currency",
			"step_1_header":"Step 1",
			"select_currency_trade":"Select Currency to Trade",
			"option_no_currency_to_trade":"No Currency to trade available at the Moment",
			"select_source_coin_label":"Select Source Coin",
			"no_source_coins":"No Source Coins at the Moment",
			"amount_willing_to_send":"Enter amount you are willing to send in",
			"spn_enter_receiver_details":"Enter Receiver Details",
			"register-label-fullname":"Receiver's Name",
			"register-label-email":"Receiver's Email",
			"register-label-bank-name":"Receiver's Bank Name",
			"register-label-bank-code":"Receiver's Bank Code",
			"register-label-account-number":"Receiver's Account Number",
			"step-2-header":"Step 2",
			"header_best_offer":"Select Best Offer",
			"data-validation-error-swal-header":"Oops..",
			"data-validation-error-failed-to-get-details":"Failed to get details",
			"data-validation-problem-with-connection":"Problem with connection",
			"data-validation-check-your-data":"Please check your data",
			"data-validation-please-select-currency":"Please select Currency",
			"data-validation-please-select-coin":"Please select Coin",
			"no-offers-available":"No offers available",
			"no-do-not-have-trustline":"You do not have a Trustline with this issuer",
			"do-you-want-to-create-trustline":"Do you want to create Trustline?",
			"enter-your-secret-key":"Enter your Secret Key",
			"password-label":"Password",
			"password-validation-label":"Please input your Secret Key!",
			"enter-password-label":"Enter your Password",
			"enter-password-input-validation":"Please input your password!",
			"exchange-is-successful":"Currency Exchange Successful",
			"data_validation_amount_expected":"Amount expected not inputed",
			"data_validation_receiver_name":"Please enter receiver's name",
			"data_validation_receiver_email":"Please enter receiver's email",
			"data_validation_receiver_bank_name":"Please enter receiver's bank name",
			"data_validation_receiver_bank_code":"Please enter receiver's bank code",
			"data_validation_receiver_account_no":"Please enter receiver's account number",
			"btn_exchange_label":"Exchange Coin",
			"label_expected_amount":"Expected Amount in",
			"label_choose_one":"Choose one",
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
			"breadcrumb-item-trade-currency":"Moneda comercial",
			"header_view_remittance_txn":"Ver transacciones de remesas",
			"header_trade_digital_currency":"Comercio de moneda digital",
			"step_1_header":"Paso 1",
			"select_currency_trade":"Seleccione la moneda para negociar",
			"option_no_currency_to_trade":"No hay moneda para operar disponible en este momento",
			"select_source_coin_label":"Seleccionar moneda de origen",
			"no_source_coins":"No hay monedas de origen en este momento",
			"amount_willing_to_send":"Ingrese la cantidad que desea enviar",
			"spn_enter_receiver_details":"Ingrese los detalles del receptor",
			"register-label-fullname":"Nombre del destinatario",
			"register-label-email":"Correo electrónico del destinatario",
			"register-label-bank-name":"Nombre del banco del receptor",
			"register-label-bank-code":"Código bancario del receptor",
			"register-label-account-number":"Número de cuenta del destinatario",
			"step-2-header":"Paso 2",
			"header_best_offer":"Seleccione la mejor oferta",
			"data-validation-error-swal-header":"Ups..",
			"data-validation-error-failed-to-get-details":"Error al obtener detalles",
			"data-validation-problem-with-connection":"problema con la conexion",
			"data-validation-check-your-data":"Por favor revisa tus datos",
			"data-validation-please-select-currency":"Por favor seleccione Moneda",
			"data-validation-please-select-coin":"Por favor seleccione Moneda",
			"no-offers-available":"no hay ofertas disponibles",
			"no-do-not-have-trustline":"No tienes un Trustline con este emisor",
			"do-you-want-to-create-trustline":"¿Quieres crear Trustline?",
			"enter-your-secret-key":"Ingrese su clave secreta",
			"password-label":"Clave",
			"password-validation-label":"¡Por favor ingrese su clave secreta!",
			"enter-password-label":"Ingresa tu contraseña",
			"enter-password-input-validation":"¡Por favor ingrese su contraseña!",
			"exchange-is-successful":"Cambio de moneda exitoso",
			"data_validation_amount_expected":"Cantidad esperada no ingresada",
			"data_validation_receiver_name":"Ingrese el nombre del destinatario",
			"data_validation_receiver_email":"Ingrese el correo electrónico del destinatario",
			"data_validation_receiver_bank_name":"Ingrese el nombre del banco del destinatario",
			"data_validation_receiver_bank_code":"Ingrese el código bancario del destinatario",
			"data_validation_receiver_account_no":"Ingrese el número de cuenta del destinatario",
			"btn_exchange_label":"Moneda de Cambio",
			"label_expected_amount":"Cantidad esperada en",
			"label_choose_one":"Elige uno",
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
        $('#breadcrumb-item-trade-currency').text(i18next.t('breadcrumb-item-trade-currency'));
        $('#header_view_remittance_txn').text(i18next.t('header_view_remittance_txn'));
        $('#header_trade_digital_currency').text(i18next.t('header_trade_digital_currency'));
        $('#step_1_header').text(i18next.t('step_1_header'));
        $('#select_currency_trade').text(i18next.t('select_currency_trade'));
        $('#option_no_currency_to_trade').text(i18next.t('option_no_currency_to_trade'));
        $('#select_source_coin_label').text(i18next.t('select_source_coin_label'));
        $('#no_source_coins').text(i18next.t('no_source_coins'));
        $('#amount_willing_to_send').text(i18next.t('amount_willing_to_send'));
        $('#spn_enter_receiver_details').text(i18next.t('spn_enter_receiver_details'));
        $('#register-label-fullname').text(i18next.t('register-label-fullname'));
        $('#register-label-email').text(i18next.t('register-label-email'));
        $('#register-label-bank-name').text(i18next.t('register-label-bank-name'));
        $('#register-label-bank-code').text(i18next.t('register-label-bank-code'));
        $('#register-label-account-number').text(i18next.t('register-label-account-number'));
        $('#step-2-header').text(i18next.t('step-2-header'));
        $('#header_best_offer').text(i18next.t('header_best_offer'));
        $('#data-validation-error-swal-header').text(i18next.t('data-validation-error-swal-header'));
        $('#data-validation-error-failed-to-get-details').text(i18next.t('data-validation-error-failed-to-get-details'));
        $('#data-validation-problem-with-connection').text(i18next.t('data-validation-problem-with-connection'));
        $('#data-validation-check-your-data').text(i18next.t('data-validation-check-your-data'));
        $('#data-validation-please-select-currency').text(i18next.t('data-validation-please-select-currency'));
        $('#data-validation-please-select-coin').text(i18next.t('data-validation-please-select-coin'));
        $('#no-offers-available').text(i18next.t('no-offers-available'));
        $('#no-do-not-have-trustline').text(i18next.t('no-do-not-have-trustline'));
        $('#do-you-want-to-create-trustline').text(i18next.t('do-you-want-to-create-trustline'));
        $('#enter-your-secret-key').text(i18next.t('enter-your-secret-key'));
        $('#password-label').text(i18next.t('password-label'));
        $('#password-validation-label').text(i18next.t('password-validation-label'));
        $('#enter-password-label').text(i18next.t('enter-password-label'));
        $('#enter-password-input-validation').text(i18next.t('enter-password-input-validation'));
        $('#exchange-is-successful').text(i18next.t('exchange-is-successful'));       
		$('#data_validation_amount_expected').text(i18next.t('data_validation_amount_expected')); 
		$('#data_validation_receiver_name').text(i18next.t('data_validation_receiver_name')); 
		$('#data_validation_receiver_email').text(i18next.t('data_validation_receiver_email')); 
		$('#data_validation_receiver_bank_name').text(i18next.t('data_validation_receiver_bank_name')); 
		$('#data_validation_receiver_bank_code').text(i18next.t('data_validation_receiver_bank_code')); 
		$('#data_validation_receiver_account_no').text(i18next.t('data_validation_receiver_account_no')); 
		$('#btn_exchange_label').text(i18next.t('btn_exchange_label')); 
		$('#label_expected_amount').text(i18next.t('label_expected_amount')); 
		$('#label_choose_one').text(i18next.t('label_choose_one')); 
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
			checkIfUserHasMneonicCode();
		  return false;
		}
	}
	$( "#form" ).accWizard(options);
	$('#next_btn').click(function() {
		// alert('hohoho');
		getStellarOffers();
	}); 

		
})(jQuery);   

$(function(){
	// Document Ready
	 $("#amount_to_spend").keyup(function() {
    		fnConvertAssetToRequiredCurrency();
  	});
     fetchCurrencyandPorteAssets();
})
var usdcDestianationAmount='';
let source_Assetcode;
let dest_Assetcode;

 function fnConvertAssetToRequiredCurrency(){
	// vAlidate the two required fields
	  $( "#exchange-currency-form" ).validate( {
        rules: {
            sel_currency: {
                required: true
            },
            source_coin: {
                required: true
            },
           
        },
        messages: {
            sel_currency: {
                required: $('#data-validation-please-select-currency').text()
            },
            source_coin: {
                required: $('#data-validation-please-select-coin').text()
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

    if($( "#exchange-currency-form" ).valid()){
	 /* var sourceAssetcode = $("#source_coin option:selected").val();
	  var destCurrency = $("#sel_currency option:selected").val();*/


		let buyAsset = document.getElementsByClassName("dd-selected-text");
      let assCode = document.getElementsByClassName("dd-selected-value");
      console.log(`buy asset code ${buyAsset}, assCode ${assCode}`)
      let buyAssetCode;
      for (var i = 0; i < buyAsset.length; i++) {
        buyAssetCode = buyAsset[i].innerText;
      }
      let asscode;
      for (var i = 0; i < assCode.length; i++) {
        asscode = assCode[i].innerText;
      }
      console.log(`buy asset code ****** ${buyAssetCode} and asscode ${asscode}`)

       

	  var sourceAssetcode = source_Assetcode
	  var destCurrency = dest_Assetcode

      console.log(`asset code ${sourceAssetcode}, dest currency ${destCurrency}`)
	
	
	
	  var amount = $("#amount_to_spend").val();
	  var formData = new FormData();
	  formData.append('qs', 'porte'); 
	  formData.append('rules', 'exchange_asset_to_currency');  
	  //formData.append('hdnapikey', getAPIKEy()); 
	  formData.append('amount', amount); 
	  formData.append('sourceasset', sourceAssetcode); 
	  formData.append('destcurrency', destCurrency); 

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
	            $("#amount_expected").val(data.amount_expected);
				usdcDestianationAmount=data.usdc_destination_amount;
	          }else{
	            console.log('Error',data.message)
		
	          }
	      },
	      error: function() {
	        console.log('Problem with connection')
	      }
	  });  
}
}

var offersData = '';


function getStellarOffers(){
    $( "#form" ).validate( {
        rules: {
            sel_currency: {
                required: true
            },
            source_coin: {
                required: true
            },
            amount_to_spend: {
                required: true
            }
        },
        messages: {
            sel_currency: {
                required: $('#data-validation-please-select-currency').text()
            },
            source_coin: {
                required: $('#data-validation-please-select-coin').text()
            },
            amount_to_spend: {
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
        var formData = new FormData($('#form')[0]);
        formData.append('qs', 'frx');
        formData.append('rules', 'get_offers_from_stellar'); 
        for (var pair of formData.entries()) {
			//alert(pair[0] + " - " + pair[1]);
        }	
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
				var offers = '';
				var size = 0;
				var counter =3;
				var htmlOptions = '';
				$('#div_offers_options').html('');
                if(data.error=='false'){
					offers	= data.data;
					offersData =offers;
					size = offers.length;
					if(size<counter){
						counter = size;
					}
									
					for (i=0; i<counter;i++){
						htmlOptions+=`<label class=" card custom-control custom-radio   justify-content-center" style="height: auro;padding: 40px;" >
						<input type="radio" class="custom-control-input" name="example-radios" value="`+offers[i].sourceAsset+","+offers[i].sourceAssetIssuer+","+offers[i].sourceAmount+","+offers[i].destinationAsset+","+offers[i].destinationIssuer+","+offers[i].destinationAmount+"-"+i+`" >
							<span class="custom-control-label">`+offers[i].destinationAsset+`</span>
							<div class="col-lg-12">
								<div class="row mt-3">
									<div class="col-8  card p-3 text-center shadow-md ">
										<h6  class="text-warning">Asset Issuer</h6>
										<p>`+offers[i].destinationIssuer+`</p>
									</div>
									<div class="col  card p-3 shadow-md ">
										<h6  class="text-warning">Expected Amount</h6>
										<p>`+offers[i].destinationAsset+": "+offers[i].destinationAmount+`</p>
									</div>
								</div>
							</div>
						</label>`
					}					
                }else{
					htmlOptions+='<div>'+$('#no-offers-available').text()+'</div>';
                }
				$('#div_offers_options').append(htmlOptions);
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

function checkTrustLine(){
	var radioValue = $("input[name='example-radios']:checked").val();
	var formData = new FormData($('#post-form')[0]);
	formData.append('qs', 'frx');
	formData.append('rules', 'check_trustline'); 
	formData.append('radio_value', radioValue); //Get this from radio button
	
	for (var pair of formData.entries()) {
	  //console.log(pair[0] + " - " + pair[1]);
	}	
	$.ajaxSetup({
		beforeSend: function(xhr) {
			xhr.setRequestHeader('x-api-key' , getAPIKey());
		}
	});
	//submit_btn
	$('#spinner-div').show();//Load button clicked show spinner
	$.ajax({
		url: 'ms',
		data: formData,
		processData: false,
		contentType: false,
		type: 'POST',
		success: function (result) {
			var data = JSON.parse(result);
			$('#spinner-div').hide();//Request is complete so hide spinner
			if(data.error=='false'){
				if(!data.has_trustline){
					Swal.fire({
						title: $('#no-do-not-have-trustline').text(),
						html: $('#do-you-want-to-create-trustline').text(),
						icon: 'info',
						showCancelButton: true,
						confirmButtonColor: '#3085d6',
						cancelButtonColor: '#808080',
						cancelButtonText:'No',
						confirmButtonText: 'Yes'
					  }).then((result) => {
						if (result.isConfirmed) {

							const { value: password   } = Swal.fire({
								title: $('#enter-your-secret-key').text(),
								input: 'password',
								inputLabel: $('#password-label').text(),
								showCancelButton: true,
								inputAttributes: {
								autocapitalize: 'off',
								autocorrect: 'off'
								},
								inputValidator: (value) => {
								if (!value) {
									return $('#password-validation-label').text();
								}
								}
							}).then((result) => {
									if (result.value) {
										var txnpin = result.value;
										//createTrustLine(radioValue, txnpin);
									}
								});

						}
					  })
				}else{
					fnPrivateKeyInput(radioValue);
				}
			}else{
			   
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

function createTrustLine(radioValue, password){
	var formData = new FormData($('#post-form')[0]);
	formData.append('qs', 'frx');
	formData.append('rules', 'create_trustline'); 
	formData.append('radio_value', radioValue); 
	formData.append('private_key', password); 
	
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
			var data = JSON.parse(result);
			$('#spinner-div').hide();//Request is complete so hide spinner
			if(data.error=='false'){
				fnPrivateKeyInput(radioValue);
			}else{
				Swal.fire({
					icon: 'error',
					title: $('#data-validation-error-swal-header').text(),
					text: data.message,
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


function fnPrivateKeyInput(hasmnemonic){
	
	const { value: password   } = Swal.fire({
		title: $('#enter-your-secret-key').text(),
		input: 'password',
		inputLabel: $('#password-label').text(),
		showCancelButton: true,
		inputAttributes: {
		autocapitalize: 'off',
		autocorrect: 'off'
		},
		inputValidator: (value) => {
		if (!value) {
			return $('#password-validation-label').text();
		}
		}
	}).then((result) => {
		if (result.value) {
				var txnpin3 = result.value;
				exchangeDigitalAssetToCurrency( txnpin3, hasmnemonic);
			}
		});
}


function fnPasswordInput(hasmnemonic){
	
	const { value: password   } = Swal.fire({
		title: $('#enter-password-label').text(),
		input: 'password',
		inputLabel: $('#password-label').text(),
		showCancelButton: true,
		inputAttributes: {
			autocapitalize: 'off',
			autocorrect: 'off'
		},
		inputValidator: (value) => {
		if (!value) {
			return $('#enter-password-input-validation').text();
		}
		}
	}).then((result) => {
		if (result.value) {
				var txnpin3 = result.value;
				exchangeDigitalAssetToCurrency( txnpin3, hasmnemonic);
			}
		});
}




function exchangeDigitalAssetToCurrency( password, hasMnemonic){
	
	  var selectedSourceAsset= source_Assetcode;
	  var selectedCurrency= dest_Assetcode;
	  var sourceAmount= $("#amount_to_spend").val();
	  var expectedAmount= $("#amount_expected").val();

  	  console.log("selectedSourceAsset ",selectedSourceAsset," selectedCurrency ",
           selectedCurrency," sourceAmount ",sourceAmount," expectedAmount ",expectedAmount);
	
	var formData = new FormData($('#exchange-currency-form')[0]);
	formData.append('qs', 'frx');
	formData.append('rules', 'exchange_digital_asset_to_currency'); 
	//formData.append('private_key', password); 
	
	formData.append('security',password);
	formData.append('hasMnemonic',hasMnemonic);
	formData.append("hdnlang", $('#lang_def').text());
	formData.append("usdcdestamount", usdcDestianationAmount);
	formData.append('amount_to_spend', sourceAmount);
	formData.append('source_coin', selectedSourceAsset);
    formData.append('sel_currency', selectedCurrency);


	/* for (var pair of formData.entries()) {
		alert(pair[0] + " - " + pair[1]);
	  }	 */
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
			if(data.error=='false'){
				Swal.fire({
					icon: 'success',
					title: 'Currency Exchange Successful',
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
					icon: 'error',
					title: $('#data-validation-error-swal-header').text(),
					text: data.message,
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
					    confirmButtonText: "Ok"
						}).then(function() {
							
				});
		}
	}); 
}

function fnCallRemittanceTransactionPage(){
	$('#post-form-2').attr('action', 'ws');
	$('input[name="qs"]').val('frx');
	$('input[name="rules"]').val('get_pending_currency_trading_page');
	$('input[name="hdnlang"]').val($('#lang_def').text());
	$("#post-form-2").submit();	
}

function fnSubmitPayment(){
	// Validate form
	 $( "#exchange-currency-form").validate( {
		
        rules: {
            sel_currency: {
                required: true
            },
            source_coin: {
                required: true
            },
            amount_to_spend: {
                required: true
            },
            amount_expected: {
                required: true
            },
            receiver_name: {
                required: true
            },
            receiver_email: {
                required: true
            }, 
            receiver_bank_name: {
                required: true
            },
            receiver_bank_code: {
                required: true
            },
            receiver_account_no: {
                required: true
            },
        },
 
  	    
        messages: {
            sel_currency: {
                required: $('#data-validation-please-select-currency').text()
            },
            source_coin: {
                required: $('#data-validation-please-select-coin').text()
            },
            amount_to_spend: {
                required: $('#data-validation-please-enter-amount').text()
            },
 			amount_expected: {
                required: $('#data_validation_amount_expected').text()
            },
            receiver_name: {
                required:$('#data_validation_receiver_name').text()
            },
            receiver_email: {
                required: $('#data_validation_receiver_email').text()
            }, 
            receiver_bank_name: {
                required: $('#data_validation_receiver_bank_name').text()
            },
            receiver_bank_code: {
                required: $('#data_validation_receiver_bank_code').text()
            },
            receiver_account_no: {
                required:$('#data_validation_receiver_account_no').text()
            },
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
 if($( "#exchange-currency-form" ).valid()){
		var formData = new FormData();
		formData.append('qs','porte');
		formData.append('rules','check_if_customer_has_mnemonic_code');
		
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
	                    fnPasswordInput(data.hasmnemonic);
	                }else{
	                    fnPrivateKeyInput(data.hasmnemonic)
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
}

   
function fnUpdateSourceAssetLabel(){
	 let sourceCoin = $("#source_coin option:selected").val();
		$("#selected_source_asset").text(sourceCoin);
		
}

function fnUpdateSelectedCurrencyLabel(){
	let selectedCurrency = $("#sel_currency option:selected").val();
		$("#selected_expected_currency").text(selectedCurrency);
}

function fetchCurrencyandPorteAssets(){

  var formData = new FormData();
  formData.append('qs', 'frx');
  formData.append('rules', 'get_currency_and_assets_to_exchange'); 

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
        var assetsCoins = data.assetscoins;
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
                  if(assetsCoins[j].assetCode==="PORTE"){
                    assetCoinsHtml+=`<option value="PORTE" data-imagesrc="assets/images/crypto/porte.svg" 
                    value="` +assetsCoins[j].assetCode +`">`+ assetsCoins[j].assetDescription +`</option>`;
                  }
                  if(assetsCoins[j].assetCode==="VESL"){
                    assetCoinsHtml+=` <option value="VESL" data-imagesrc="assets/images/crypto/vessel.png"
                    value="` +assetsCoins[j].assetCode +`">`+ assetsCoins[j].assetDescription +`</option>`;
                  }
                  if(assetsCoins[j].assetCode==="XLM"){
                    assetCoinsHtml+=` <option value="XLM" data-imagesrc="assets/images/crypto/xlm.svg"
                    value="` +assetsCoins[j].assetCode +`">`+ assetsCoins[j].assetDescription +`</option>`;
                  }
                  if(assetsCoins[j].assetCode==="USDC"){
                    assetCoinsHtml+=`<option value="USDC" data-imagesrc="assets/images/crypto/usdc.png"
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
              onSelected: function(data){
                displaySelectedData(data);
            }
            });
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
  fnUpdateSourceparams(selectedValue)
  source_Assetcode = selectedValue  
}
function displaySelectedDataTwo(data){
  var selectedValue = data.selectedData.value
  fnUpdateDestinationCurrencyparams(selectedValue)
  dest_Assetcode=selectedValue;

}
function fnUpdateSourceparams(assetCode) {
  let sourceAssetcode = assetCode
  if (sourceAssetcode == '') {
      swal.fire('Select asset Coin');
      return false;
  } else {
      $("#spansourceasset").text(sourceAssetcode);
      let assetcode=sourceAssetcode;

		if(assetcode==="VESL"||assetcode==="vesl"){
			document.getElementById("amount_to_spend").style.backgroundImage = "url('./assets/images/crypto/vessel.png')";
        }
        if(assetcode==="PORTE" || assetcode==="porte"){
			document.getElementById("amount_to_spend").style.backgroundImage = "url('./assets/images/crypto/porte.svg')";
        }
        if(assetcode==="XLM"||assetcode==="xlm"){
			document.getElementById("amount_to_spend").style.backgroundImage = "url('./assets/images/crypto/xlm.svg')";
        }
        if(assetcode==="USDC"||assetcode==="usdc"){
			document.getElementById("amount_to_spend").style.backgroundImage = "url('./assets/images/crypto/usdc.png')";
        }
  }
}

function fnUpdateDestinationCurrencyparams(assetCode) {
  let selectedCurrency = assetCode;
  console.log('selectedCurrency is ',selectedCurrency)
if (selectedCurrency == '') {
    swal.fire('Select asset Coin');
    return false;
} else {
    $("#spandestinationcurrency").text(selectedCurrency);
    let assetcode = selectedCurrency;
   	
		if(assetcode==="AUD"||assetcode==="aud"){
			document.getElementById("amount_expected").style.backgroundImage = "url('./assets/images/crypto/australia.png')";
        }
        if(assetcode==="HKD" || assetcode==="hkd"){
			document.getElementById("amount_expected").style.backgroundImage = "url('./assets/images/crypto/hong-kong.png')";
        }
         
}

}









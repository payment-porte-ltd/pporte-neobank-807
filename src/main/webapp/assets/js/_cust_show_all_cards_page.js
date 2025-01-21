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
			"breadcrumb_item_label":"Manage Cards",				
			"breadcrumb_item_active_label":"Show Cards",				
			"label_add_new_card_btn":"Add New Card",				
			"validation_error_swal_header":"Oops..",				
			"validation_error_swal_checkdata":"Please check your data",				
			"swal_connection_prob":"Problem with connection",				
			"label_valid":"VALID",				
			"label_to":"TO",				
			"label_card_number":"Card Number:",				
			"label_account_name":"Account Name:",				
			"label_no_card":"No cards registered.Click Add card to register.",			
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
			"breadcrumb_item_label":"Gestionar tarjetas",
			"breadcrumb_item_active_label":"Mostrar tarjetas",
			"label_add_new_card_btn":"Agregar nueva tarjeta",
			"validation_error_swal_header":"Vaya...",
			"validation_error_swal_checkdata":"Verifique sus datos",
			"swal_connection_prob":"Problema con la conexión",
			"label_valid":"VÁLIDO",
			"label_to":"PARA",
			"label_card_number":"Número de tarjeta:",
			"label_account_name":"Nombre de la cuenta:",
			"label_no_card":"No hay tarjetas registradas.Haga clic en Agregar tarjeta para registrarse.",
			
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
		 
	   /* Specific Page Content */
			 $('#breadcrumb_item_label').text(i18next.t('breadcrumb_item_label')); 
			 $('#breadcrumb_item_active_label').text(i18next.t('breadcrumb_item_active_label')); 
			 $('#label_add_new_card_btn').text(i18next.t('label_add_new_card_btn')); 
			 $('#validation_error_swal_header').text(i18next.t('validation_error_swal_header')); 
			 $('#validation_error_swal_checkdata').text(i18next.t('validation_error_swal_checkdata')); 
			 $('#swal_connection_prob').text(i18next.t('swal_connection_prob')); 
			 $('#label_valid').text(i18next.t('label_valid')); 
			 $('#label_to').text(i18next.t('label_to')); 
			 $('#label_card_number').text(i18next.t('label_card_number')); 
			 $('#label_account_name').text(i18next.t('label_account_name')); 
			 $('#label_no_card').text(i18next.t('label_no_card')); 
  }
	  function fnChangePageLanguage(lng){
		console.log('lng ',lng);
		  i18next.changeLanguage(lng, fnChangeLanguage(lng))
		  $('#hdnlangpref1').val(lng);
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
function fnCallRegisterCardPage(){
	$('#showcards-form ').attr('action', 'ws');
	$('input[name="qs"]').val('card');
	$('input[name="rules"]').val('Register Card');
	$('input[name="hdnlang"]').val($('#lang_def').text());
	$("#showcards-form ").submit();
	
}

function fnGetCards(){
	$('#showcards-form input[name="qs"]').val('card');
	$('#showcards-form input[name="rules"]').val('gettokenizedcards');
	$('#showcards-form input[name="relno"]').val(relno);
	var form = $('#showcards-form')[0];
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
 				var htmlDoc='';
				var htmlDetails='';
								
				$('#card_div').html('');
				if(data.error === "false"){
					
					var cardDetails = data.data;
					var count = cardDetails.length;  
		       
		       
		        htmlDetails=`<div class="row justify-content-start mt-5">`;
						
							for (var i=0; i<count;i++) {
												 if(cardDetails[i].maskedCardNumber.startsWith("5")){
													 htmlDetails+=`
													<div class="col-12" style="margin-bottom:18px;">
														<div class="mastercard-background">
															<div class="card-no-text">`+cardDetails[i].maskedCardNumber +`</div>
																<div class="card-name-header">Cardholder</div>
																<div class="card-name-text">`+cardDetails[i].cardName +`</div>
																<div class="doe-header">Exp. Date</div>
																<div class="doe-text">`+cardDetails[i].dateOfExpiry +`</div>
														</div>
													 </div>
												`;
										}else if (cardDetails[i].maskedCardNumber.startsWith("4")){
											htmlDetails+=`
												<div class="col-12" style="margin-bottom:18px;">
														<div class="visacard-background">
															<div class="card-no-text">`+cardDetails[i].maskedCardNumber +`</div>
															<div class="card-name-header">Cardholder</div>
															<div class="card-name-text">`+cardDetails[i].cardName +`</div>
															<div class="doe-header">Exp. Date</div>
															<div class="doe-text">`+cardDetails[i].dateOfExpiry +`</div>
														</div>
												</div>
											`;
													
										}else {
											// Card that is neither Mastercard or Visa
											htmlDetails+=`
											<div class="col-12">
												<div class="card  mt-5" style="width: 453px;">
													<h5 class="card-title" style="display: flex;margin:20px;"><span>`+cardDetails[i].cardAlias+`</span></h5>
													  <div class="floating2">
													    <div class="visa_thickness"></div>
													    <div class="card_body2">
													      <div class="paywave2 svg"></div>
													      <div class="chips2 svg"></div>
													      <div class="card_no2 text">
													        <span>`+cardDetails[i].maskedCardNumber+`</span>
													      </div>
													      <div class="valid2 text">
													        <span id="label_valid">VALID</span> <br>   <span id="label_to">`+$("#label_to").text()+`</span>
													      </div>
													      <div class="valid_date2 text">
													        12/20
													      </div>
													      <div class="holder2 text"><span>`+cardDetails[i].customerName+`</span></div>
													      <!-- NO SVG -->
													    </div>
													  </div>
													   <hr>
													  <div class="card_details mb-5 ml-3">
													        <p class="card-text" style="font-size: 16px;color:#a5a6bb  ;"><span id="label_card_number">`+$("#label_card_number").text()+`</span>&nbsp;&nbsp;<span id="cardnumber" 
																style="color:rgb(0,0,0)">`+cardDetails[i].maskedCardNumber+`</span></p>
													        <p class="card-text" style="font-size: 16px;color:#a5a6bb  ;"><span id="label_account_name">`+$("#label_account_name").text()+`</span>&nbsp;&nbsp;
																<span id="custname" style="color:rgb(0,0,0)">`+cardDetails[i].customerName+`</span></p>
														</div>
													</div>
												</div>
											`;
													
										}
									}
									htmlDetails+=`</div>`;
						$('#no_cards').addClass("hidden");
						$('#card_div').append(htmlDetails); 										
								
				}else if(data.error === "nodata"){
					htmlDoc=`<div style="width: 20rem;">
								  <div class="card-body">
								    <p class="card-text"><span class="text-danger" id="label_no_card">`+$("#label_no_card").text()+`</span></p>
								  </div>
								</div>`;
								$('#no_cards').removeClass("hidden");
								$('#no_cards').append(htmlDoc); 
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
                    $('#showcards-form').attr('action', 'ws');
                    $('input[name="qs"]').val('lgt');
                    $('input[name="rules"]').val('lgtdefault');
                    $("#showcards-form").submit();	
        });
		}
	}); 
}
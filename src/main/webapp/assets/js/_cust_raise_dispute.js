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
			"breadcrumb_item_label":"Disputes",
			"breadcrumb_item_active_label":"Raise Disputes",
			"label_select_dispute":"Select Dispute Reason",
			"label_transaction_code":"Enter Transaction code (Optional)",
			"label_sel_disp_reason":"Select Dispute reason",
			"label_description":"Enter Dispute description",
			"label_send_btn":"Send",
			"add-raise-dispute-data-validation-error-hdnreasonid":"Please select dispute reason",
			"add-raise-dispute-data-validation-error-dsptcomment-length":"Dispute description must consist of at least 10 characters",
			"add-raise-dispute-data-validation-error-dsptcomment-max-length":"Dispute description must not exceed 100 characters",
			"add-raise-dispute-data-validation-error-dsptcomment":"Please enter Dispute description",
			"swal_problem_connection":"Problem with connection",			
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
			"breadcrumb_item_label":"Disputas",
			"breadcrumb_item_active_label":"Plantear disputas",
			"label_select_dispute":"Seleccionar motivo de disputa",
			"label_transaction_code":"Ingrese el código de transacción (Opcional)",
			"label_description":"Ingrese la descripción de la disputa",
			"label_send_btn":"Enviar",
			"label_sel_disp_reason":"Seleccionar motivo de disputa",
			"add-raise-dispute-data-validation-error-hdnreasonid":"Seleccione el motivo de la disputa",
			"add-raise-dispute-data-validation-error-dsptcomment-length":"La descripción de la disputa debe tener al menos 10 caracteres",
			"add-raise-dispute-data-validation-error-dsptcomment-max-length":"La descripción de la disputa no debe exceder los 100 caracteres",
			"add-raise-dispute-data-validation-error-dsptcomment":"Ingrese la descripción de la disputa",
			"swal_problem_connection":"Problema con la conexión",
			
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
		$('#breadcrumb_item_label').text(i18next.t('breadcrumb_item_label')); 
		$('#breadcrumb_item_active_label').text(i18next.t('breadcrumb_item_active_label')); 
		$('#label_select_dispute').text(i18next.t('label_select_dispute')); 
		$('#label_transaction_code').text(i18next.t('label_transaction_code')); 
		$('#label_send_btn').text(i18next.t('label_send_btn')); 
		$('#label_sel_disp_reason').text(i18next.t('label_sel_disp_reason')); 
		$('#label_description').text(i18next.t('label_description'));  
		$('#add-raise-dispute-data-validation-error-hdnreasonid').text(i18next.t('add-raise-dispute-data-validation-error-hdnreasonid')); 
		$('#dd-raise-dispute-data-validation-error-dsptcomment-length').text(i18next.t('dd-raise-dispute-data-validation-error-dsptcomment-length')); 
		$('#add-raise-dispute-data-validation-error-dsptcomment-max-length').text(i18next.t('add-raise-dispute-data-validation-error-dsptcomment-max-length')); 
		$('#add-raise-dispute-data-validation-error-dsptcomment').text(i18next.t('add-raise-dispute-data-validation-error-dsptcomment'));
		$('#swal_problem_connection').text(i18next.t('swal_problem_connection'));
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

function fnSubmitDispute(){
    $( "#raise-dipute-form" ).validate( {
        rules: {
            hdnreasonid: {
                required: true
            },
            dsptcomment: {
                required: true,
                minlength: 10,
                maxlength: 100
            }
        },
        messages: {
            hdnreasonid: {
                required: $('#add-raise-dispute-data-validation-error-hdnreasonid').text()
            },
            dsptcomment: {
                required: $('#add-raise-dispute-data-validation-error-dsptcomment').text(),
                minlength: $('add-raise-dispute-data-validation-error-dsptcomment-length').text(),
                maxlength: $('add-raise-dispute-data-validation-error-dsptcomment-max-length').text()
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
    if($( "#raise-dipute-form" ).valid()){
        $('#raise-dipute-form input[name="qs"]').val('custdspt');
        $('#raise-dipute-form input[name="rules"]').val('custadd_dispute');
        
        var formData = new FormData($('#raise-dipute-form')[0]);
			formData.append("hdnlang",$('#lang_def').text());
        
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
                        $('#get-page-form').attr('action', 'ws');
                        $('input[name="qs"]').val('custdspt');
                        $('input[name="rules"]').val('View Dispute');
 						$('input[name="hdnlang"]').val($('#lang_def').text());
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
            },
            error: function() {
                Swal.fire({
                            icon: 'error',
                            title: 'Oops',
                            text: $('#swal_problem_connection').text(),
                            showConfirmButton: true,
                            confirmButtonText: "Ok",
                            }).then(function() {
                                
                    });
            }
        });             	
    }
}
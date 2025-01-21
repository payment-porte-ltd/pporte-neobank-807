$(window).on("load", function(e) {
    $("#global-loader").fadeOut("slow");
})

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
			"breadcrumb_item_active_label":"View Specific Dispute",
			"label_specific_dispute":"Specific Dispute",
			"label_disputeid":"Dispute Id",
			"label_dispute_reason":"Dispute Reason",
			"label_transaction_id":"Transaction Id",
			"label_raisedon":"Raised on",
			"label_dispute_desc":"Dispute description",
			"label_dispute_status":"Dispute Status",
			"label_header_dispute_comment":"Dispute Comment",
			"label_date":"Date:",
			"label_commented_by":"Commented By:",
			"label_add_comment":"Add comment",
			"label_update_status":"Update Status",
			"label_add_dispute_comment":"Add Dispute Comment",
			"label_close":"Close",
			"label_send_comment":"Send Comment",
			"add-comment-data-validation-error":"Please enter Dispute Comment",
			"add-comment-data-validation-error-length":"Dispute Comment must not exceed 250 characters",		
			"swal_connection_prob":"Dispute Comment must not exceed 250 characters"		
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
			"breadcrumb_item_active_label":"Ver disputa específica",
			"label_specific_dispute":"Disputa Específica",
			"label_disputeid":"Id. de disputa",
			"label_dispute_reason":"Motivo de la disputa",
			"label_transaction_id":"Id de transacción",
			"label_raisedon":"Elevado en",
			"label_dispute_desc":"Descripción de la disputa",
			"label_dispute_status":"Estado de disputa",
			"label_header_dispute_comment":"Comentario de disputa",
			"label_date":"Fecha:",
			"label_commented_by":"Comentado por:",
			"label_add_comment":"Agregar comentario",
			"label_update_status":"Actualizar estado",
			"label_add_dispute_comment":"Agregar comentario de disputa",
			"label_close":"Cerrar",
			"label_send_comment":"Enviar comentario",
			"add-comment-data-validation-error":"Ingrese el comentario de disputa",
			"add-comment-data-validation-error-length":"El comentario de disputa no debe exceder los 250 caracteres",
			"swal_connection_prob":"problema con la conexion"
			
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
		$('#label_specific_dispute').text(i18next.t('label_specific_dispute')); 
		$('#label_disputeid').text(i18next.t('label_disputeid')); 
		$('#label_dispute_reason').text(i18next.t('label_dispute_reason')); 
		$('#label_transaction_id').text(i18next.t('label_transaction_id')); 
		$('#label_raisedon').text(i18next.t('label_raisedon')); 
		$('#label_dispute_desc').text(i18next.t('label_dispute_desc')); 
		$('#label_dispute_status').text(i18next.t('label_dispute_status')); 
		$('#label_header_dispute_comment').text(i18next.t('label_header_dispute_comment')); 
		$('#label_date').text(i18next.t('label_date')); 
		$('#label_commented_by').text(i18next.t('label_commented_by')); 
		$('#label_add_comment').text(i18next.t('label_add_comment')); 
		$('#label_update_status').text(i18next.t('label_update_status')); 
		$('#label_add_dispute_comment').text(i18next.t('label_add_dispute_comment')); 
		$('#label_close').text(i18next.t('label_close')); 
		$('#label_send_comment').text(i18next.t('label_send_comment')); 
		$('#add-comment-data-validation-error').text(i18next.t('add-comment-data-validation-error')); 
		$('#add-comment-data-validation-error-length').text(i18next.t('add-comment-data-validation-error-length')); 
		$('#swal_connection_prob').text(i18next.t('swal_connection_prob')); 
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

function fnUpdateStatus(disputeId){
    $('#dispute-details-form input[name="qs"]').val('custdspt');
    $('#dispute-details-form input[name="rules"]').val('cust_update_dispute_status');
    $('#dispute-details-form input[name="hdndispid"]').val(disputeId);
        
    var formData = new FormData($('#dispute-details-form')[0]);
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
                     $('#view-dispute-form').attr('action', 'ws');
                    $('input[name="qs"]').val('custdspt');
                    $('input[name="rules"]').val('view_cust_specific_dispute');
 					$('input[name="hdnlang"]').val($('#lang_def').text());
                    $('input[name="hdnreqid"]').val(disputeId);
                    $("#view-dispute-form").submit(); 
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
                        text: $("#swal_connection_prob").text(),
                        showConfirmButton: true,
                        confirmButtonText: "Ok",
                        }).then(function() {
                            
                		});
        }
    });             	
}

function fnAddComment(disputeId){
    $( "#add-comment-form" ).validate( {
        rules: {
            comment: {
                required: true,
                maxlength: 250,
            }
        },
        messages: {
            comment: {
                required: $('#add-comment-data-validation-error').text(),
                maxlength: $('#add-comment-data-validation-error-length').text(),
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
    if($( "#add-comment-form" ).valid()){
        $('#add-comment-form input[name="qs"]').val('custdspt');
        $('#add-comment-form input[name="rules"]').val('cust_add_dispute_comment');
        $('#add-comment-form input[name="hdndispid"]').val(disputeId);
        var formData = new FormData($('#add-comment-form')[0]);
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
                        $('#view-dispute-form').attr('action', 'ws');
                        $('input[name="qs"]').val('custdspt');
                        $('input[name="rules"]').val('view_cust_specific_dispute');
						$('input[name="hdnlang"]').val($('#lang_def').text());
                        $('input[name="hdnreqid"]').val(disputeId);
                        $("#view-dispute-form").submit();
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
                            text: $("#swal_connection_prob").text(),
                            showConfirmButton: true,
                            confirmButtonText: "Ok",
                            }).then(function() {
                                
                    });
            }
        });             	
    }
}
function fnShowUpdateButton(){
	$('#updatebutton').removeClass('hidden');
}
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
			"breadcrumb-item-porte-coins":"Digital Assets",
			"breadcrumb-item-claim-claimable-balance":"Claim Claimable Balance",
			"card-title-claimable-balance":"Claim Claimable Balance",
			"th-asset-code":"Asset Code",
			"th-amount":"Amount",
			"th-txn-date":"Transaction Date",
			"th-src-account":"Source Account",
			"th-src-action":"Action",
			"th-claimable-balance-id":"Claimable Balance Id",
			"th-asset-issuer-account":"Asset Issuer Account",
			"spn_no_data-present":"No data Present",
			"data-validation-error-swal-header":"Oops..",
			"data-validation-error-failed-to-get-details":"Failed to get details",
			"data-validation-problem-with-connection":"Problem with connection",
			"data-validation-check-your-data":"Please check your data",
			"data-validation-success":"Success",
			"enter-your-passsword-tittle":"Enter your Password",
			"enter-your-passsword-label":"Password",
			"data-validation-enter-your-passsword-label":"Please input your password!",
			"data-validation-enter-your-pvt-key-label":"Please input your Private Key!",
			"enter-your-pvt-key-label":"Enter your Private Key"
			
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
			"breadcrumb-item-porte-coins":"Recursos digitales",
			"breadcrumb-item-claim-claimable-balance":"Reclamar saldo reclamable",
			"card-title-claimable-balance":"Reclamar saldo reclamable",
			"th-asset-code":"Código de activo",
			"th-amount":"Monto",
			"th-txn-date":"Fecha de Transacción",
			"th-src-account":"Cuenta de origen",
			"th-src-action":"Acción",
			"th-claimable-balance-id":"ID de saldo reclamable",
			"th-asset-issuer-account":"Cuenta de emisor de activos",
			"spn_no_data-present":"Sin datos Presente",
            "data-validation-error-swal-header":"Ups..",
			"data-validation-error-failed-to-get-details":"Error al obtener detalles",
			"data-validation-problem-with-connection":"problema con la conexion",
			"data-validation-check-your-data":"Por favor revisa tus datos",
			"data-validation-success":"Éxito",
			"enter-your-passsword-tittle":"Ingresa tu contraseña",
			"enter-your-passsword-label":"Clave",
			"data-validation-enter-your-passsword-label":"¡Por favor ingrese su contraseña!",
			"data-validation-enter-your-pvt-key-label":"¡Ingrese su clave privada!",
			"enter-your-pvt-key-label":"Ingrese su clave privada"
			
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
        $('#breadcrumb-item-porte-coins').text(i18next.t('breadcrumb-item-porte-coins'));
        $('#breadcrumb-item-claim-claimable-balance').text(i18next.t('breadcrumb-item-claim-claimable-balance'));
        $('#card-title-claimable-balance').text(i18next.t('card-title-claimable-balance'));
        $('#th-asset-code').text(i18next.t('th-asset-code'));
        $('#th-amount').text(i18next.t('th-amount'));
        $('#th-txn-date').text(i18next.t('th-txn-date'));
        $('#th-src-account').text(i18next.t('th-src-account'));
        $('#th-src-action').text(i18next.t('th-src-action'));
        $('#th-claimable-balance-id').text(i18next.t('th-claimable-balance-id'));
        $('#th-asset-issuer-account').text(i18next.t('th-asset-issuer-account'));
        $('#spn_no_data-present').text(i18next.t('spn_no_data-present'));
        $('#data-validation-error-swal-header').text(i18next.t('data-validation-error-swal-header'));
        $('#data-validation-error-failed-to-get-details').text(i18next.t('data-validation-error-failed-to-get-details'));
        $('#data-validation-problem-with-connection').text(i18next.t('data-validation-problem-with-connection'));
        $('#data-validation-check-your-data').text(i18next.t('data-validation-check-your-data'));
        $('#data-validation-success').text(i18next.t('data-validation-success'));
        $('#enter-your-passsword-tittle').text(i18next.t('enter-your-passsword-tittle'));
        $('#enter-your-passsword-label').text(i18next.t('enter-your-passsword-label'));
        $('#data-validation-enter-your-passsword-label').text(i18next.t('data-validation-enter-your-passsword-label'));
        $('#data-validation-enter-your-pvt-key-label').text(i18next.t('data-validation-enter-your-pvt-key-label'));
        $('#enter-your-pvt-key-label').text(i18next.t('enter-your-pvt-key-label'));
       
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

$("button[data-dismiss=modal]").click(function(){
	$(".modal").modal('hide');
});

$(window).on("load", function(e) {
	$("#global-loader").fadeOut("slow");
})


function checkIfUserHasMneonicCode(claimableBalaceId, assetCode, assetIssuer){
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
                    fnPassword(data.hasmnemonic, claimableBalaceId, assetCode, assetIssuer);
                }else{
                    fnPrivateKey(data.hasmnemonic, claimableBalaceId, assetCode, assetIssuer)
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

function fnPassword(hasMnemonic, claimableBalaceId, assetCode, assetIssuer){
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
				fnClaimClaimableBalance(passwordVal,hasMnemonic, claimableBalaceId, assetCode, assetIssuer );
			}
		});
}

function fnPrivateKey(hasMnemonic, claimableBalaceId, assetCode, assetIssuer){
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
			return $('#data-validation-enter-your-pvt-key-label').text();
		}
		}
	}).then((result) => {
			if (result.value) {
				var privatekey = result.value;
				fnClaimClaimableBalance(privatekey,hasMnemonic, claimableBalaceId, assetCode, assetIssuer );
			}
		});
}

function fnClaimClaimableBalance (security,hasMnemonic, claimableBalaceId, assetCode, assetIssuer){
	var formData = new FormData();
	formData.append('qs','porte');
	formData.append('rules','claim_claimable_balance');
	formData.append('security',security);
	formData.append('hasMnemonic',hasMnemonic);
	formData.append('claimableBalaceId',claimableBalaceId);
	formData.append('assetCode',assetCode);
	formData.append('assetIssuer',assetIssuer);
    formData.append("hdnlang", $('#lang_def').text());
    for (var pair of formData.entries()) {
      //console.log(pair[0] + " - " + pair[1]);
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
            var data = JSON.parse(result);
			$('#spinner-div').hide();//Request is complete so hide spinner
            if(data.error=='false'){
              Swal.fire({
						icon: 'success',
						title: $('#data-validation-success').text(),
						text: data.message,
						showConfirmButton: true,
						confirmButtonText: "Ok",
					}).then(function() {
						$('#get-page-form').attr('action', 'ws');
						$('#get-page-form input[name="qs"]').val('porte');
						$('#get-page-form input[name="rules"]').val('view_claimable_balance');
						$("#get-page-form").submit();
					});
               
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
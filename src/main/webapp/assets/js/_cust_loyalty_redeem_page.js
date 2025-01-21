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
			"breadcrumb_item_label":"Manage Loyalty",
			"breadcrumb_item_active_label":"Redeem Points",
			"label_point_balance":"Point Balance",
			"label_redeem_amount":"Redeem Amount",
			"label_redeem_all_btn":"Redeem All",
			"label_date":"Date",
			"label_payment_type":"Payment Type",
			"label_ref_no":"Reference Number",
			"label_points_awarded":"Points Awarded",
			"label_point_bal":"Points Balance",
			"label_action":"Action",
			"label_claim_btn":"Claim Points",
			"label_no_points":"No points awarded yet. Perform transactions to earn points",
			"validation_point_balance":"Your point balance is 0. Kindly transact to get Loyalty Points",
			"success_redemtion":"Points Redeemed Successfully",			
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
			"breadcrumb_item_label":"Administrar lealtad",
			"breadcrumb_item_active_label":"Canjear puntos",
			"label_point_balance":"Saldo de puntos",
			"label_redeem_amount":"canjear cantidad",
			"label_redeem_all_btn":"Canjear todo",
			"label_date":"Fecha",
			"label_payment_type":"Tipo de pago",
			"label_ref_no":"Número de referencia",
			"label_points_awarded":"Puntos Otorgados",
			"label_point_bal":"Saldo de Puntos",
			"label_action":"Acción",
			"label_claim_btn":"Reclamar puntos",
			"label_no_points":"Aún no se han otorgado puntos. Realice transacciones para ganar puntos",
			"validation_point_balance":"Su saldo de puntos es 0. Realice una transacción para obtener Puntos de fidelidad",
			"success_redemtion":"Puntos canjeados con éxito",
			
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
		$('#label_point_balance').text(i18next.t('label_point_balance')); 
		$('#label_redeem_amount').text(i18next.t('label_redeem_amount')); 
		$('#label_redeem_all_btn').text(i18next.t('label_redeem_all_btn')); 
		$('#label_date').text(i18next.t('label_date')); 
		$('#label_payment_type').text(i18next.t('label_payment_type')); 
		$('#label_ref_no').text(i18next.t('label_ref_no')); 
		$('#label_points_awarded').text(i18next.t('label_points_awarded')); 
		$('#label_point_bal').text(i18next.t('label_point_bal')); 
		$('#label_action').text(i18next.t('label_action')); 
		$('#label_claim_btn').text(i18next.t('label_claim_btn')); 
		$('#label_no_points').text(i18next.t('label_no_points')); 
		$('#validation_point_balance').text(i18next.t('validation_point_balance')); 
		$('#success_redemtion').text(i18next.t('success_redemtion')); 
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


		$(document).ready ( function(){
			
			$('#custloyaltytable').dataTable({ "bSort" : false } );
			$('#redeemamount').attr('disabled','disabled');
			$('#receivedamount_loyalty').attr('disabled','disabled');

		});
		
		function fnUpdatePointBalance(balance){
			$("#point-balance").text(balance);
			$("#redeemamount").val(balance);
			loyaltyPointsCoversion(balance);
		}

		function loyaltyPointsCoversion(pointbalance) {
			//$("#receivedamount_loyalty").val(pointbalance);
			var formData = new FormData($('#cust-redeem_points-form')[0]);
			formData.append('qs', 'porte');
			formData.append('rules', 'loyalty_points_conversion');
			formData.append('pointbalance', pointbalance);
						
			$.ajaxSetup({
				beforeSend: function(xhr) {
					xhr.setRequestHeader('x-api-key', getAPIKey());
				}
			});
			$.ajax({
				url: 'ms',
				data: formData,
				processData: false,
				contentType: false,
				type: 'POST',
				success: function(result) {
		
					var data = JSON.parse(result);
					console.log('data ', data);
					if (data.error == 'false') {
						$("#receivedamount_loyalty").val(data.data);
					} else {
						console.log('Error', data.message)
		
					}
				},
				error: function() {
					console.log('Problem with connection')
				}
			});
		}
		
		function fnRedeemAllLoyalty(pointbalance, relno) {
		
			if (pointbalance <= 0) {
				Swal.fire({
					title:  "Failed",
					text: $("#validation_point_balance").text(),
					icon: "error",
					showConfirmButton: true,
					confirmButtonText: "Ok",
					closeOnConfirm: true,
				})
		
			} else {
				
		       var formData = new FormData($('#cust-redeem_points-form')[0]);
				formData.append('qs', 'custltly');
			    formData.append('rules', 'custredeemallpoints');
		        formData.append('relno', relno);
		        formData.append('pointsbalance', pointbalance);
				formData.append("hdnlang",$('#lang_def').text());

				$.ajaxSetup({
					beforeSend: function(xhr) {
						xhr.setRequestHeader('x-api-key', getAPIKey());
					}
				});
				$.ajax({
					url: 'ms',
					data: formData,
					processData: false,
					contentType: false,
					type: 'POST',
					success: function(result) {
						//alert('result is '+result);
						var data = JSON.parse(result);
						if (data.error == 'false') {
							//alert('lgtoken is '+data.token)
							Swal.fire({
								icon: 'success',
								title: $("#success_redemtion").text(),
								text: data.message,
								showConfirmButton: true,
								confirmButtonText: "Ok",
							}).then(function() {
								//location.reload();
								$('#cust-loyalty-points-form').attr('action', 'ws');
								$('#cust-loyalty-points-form input[name="qs"]').val('custltly');
								$('#cust-loyalty-points-form input[name="rules"]').val('Redeem Points');
								$("#cust-loyalty-points-form").submit();
							});
		
						} else {
							Swal.fire({
								icon: 'error',
								title: 'Oops',
								text: data.message,
								showConfirmButton: true,
								confirmButtonText: "Ok",
							}).then(function() {
								//Do Nothing
							});
						}
					},
					error: function() {
						Swal.fire({
							icon: 'error',
							title: 'Oops',
							text: $("#swal_problem_connection").text(),
							showConfirmButton: true,
							confirmButtonText: "Ok",
						}).then(function() {
							//Do Nothing
						});
					}
				});
			
			}
		
		}
		
		function fnClaimAReward(seqno, acruedpoints, paymode, systemRef,walletid) {
			var formData = new FormData($('#cust-redeem_points-form')[0]);
			formData.append('qs', 'custltly');
			formData.append('rules', 'custredeemspecificpoints');
			formData.append('seqno', seqno);
			formData.append('acruedpoints', acruedpoints);
			formData.append('paymode', paymode);
			formData.append('systemRef', systemRef);
			formData.append('walletid', walletid);
		  	formData.append("hdnlang",$('#lang_def').text());

	 		for (var pair of formData.entries()) {
		          console.log(pair[0] + " - " + pair[1]);
		        }	
		
		
			$.ajaxSetup({
				beforeSend: function(xhr) {
					xhr.setRequestHeader('x-api-key', getAPIKey());
				}
			});
			$.ajax({
				url: 'ms',
				data: formData,
				processData: false,
				contentType: false,
				type: 'POST',
				success: function(result) {
					//alert('result is '+result);
					var data = JSON.parse(result);
					if (data.error == 'false') {
						//alert('lgtoken is '+data.token)
						Swal.fire({
							icon: 'success',
							title: $("#success_redemtion").text(),
							text: data.message,
							showConfirmButton: true,
							confirmButtonText: "Ok",
						}).then(function() {
							//location.reload();
							$('#cust-loyalty-points-form').attr('action', 'ws');
							$('#cust-loyalty-points-form input[name="qs"]').val('custltly');
							$('#cust-loyalty-points-form input[name="rules"]').val('Redeem Points');
							$("#cust-loyalty-points-form").submit();
						});
		
					} else {
						Swal.fire({
							icon: 'error',
							title: 'Oops',
							text: data.message,
							showConfirmButton: true,
							confirmButtonText: "Ok",
						}).then(function() {
							//Do Nothing
						});
					}
				},
				error: function() {
					Swal.fire({
						icon: 'error',
						title: 'Oops',
						text: $("#swal_problem_connection").text(),
						showConfirmButton: true,
						confirmButtonText: "Ok",
					}).then(function() {
						//Do Nothing
					});
				}
			});
		
		
		
		}
		

		
		
		
	




 

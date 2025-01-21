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
			"welcome_label":"Welcome",
			"last_five_transaction":"Last 5 Transactions",
			"fiat_wallet_label":"Fiat Wallet",
			"last_15_transactions_label":"Last 15 days of Transactions",
			"table-label-date":"Date",
			"table-label-description":"Description",
			"table-label-amount":"Amount",
			"table-label-nodata-present":"No Transaction Present at the moment",
			"data-validation-error-swal-header":"Oops..",
			"failed-to-get-data-validation-error-swal-header":"Failed to get Transactions",
			"data-validation-problem-with-connection":"Problem with connection",
			"fiat_wallet_stats_label":"Fiat Wallet Statistics",
			
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
			"welcome_label":"Bienvenidas",
			"last_five_transaction":"Últimas 5 transacciones",
			"fiat_wallet_label":"Monedero Fiat",
			"last_15_transactions_label":"Últimos 15 días de Transacciones",
			"table-label-date":"Fecha",
			"table-label-description":"Descripción",
			"table-label-amount":"Monto",
			"table-label-nodata-present":"No hay transacción presente en este momento",
			"data-validation-error-swal-header":"Ups..",
			"failed-to-get-data-validation-error-swal-header":"Error al obtener transacciones",
			"data-validation-problem-with-connection":"problema con la conexion",
			"fiat_wallet_stats_label":"Estadísticas de la billetera Fiat",
			"idnavmenu_Disputes":"disputas",
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
        $('#welcome_label').text(i18next.t('welcome_label'));
        $('#last_five_transaction').text(i18next.t('last_five_transaction'));
        $('#fiat_wallet_label').text(i18next.t('fiat_wallet_label'));
        $('#last_15_transactions_label').text(i18next.t('last_15_transactions_label'));
        $('#table-label-date').text(i18next.t('table-label-date'));
        $('#table-label-description').text(i18next.t('table-label-description'));
        $('#table-label-amount').text(i18next.t('table-label-amount'));
        $('#table-label-nodata-present').text(i18next.t('table-label-nodata-present'));
        $('#data-validation-error-swal-header').text(i18next.t('data-validation-error-swal-header'));
        $('#failed-to-get-data-validation-error-swal-header').text(i18next.t('failed-to-get-data-validation-error-swal-header'));
        $('#data-validation-problem-with-connection').text(i18next.t('data-validation-problem-with-connection'));
        $('#fiat_wallet_stats_label').text(i18next.t('fiat_wallet_stats_label'));
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
			
			// Greetings translation
		var thehours = new Date().getHours();
		var themessage;
		var morning;
		var afternoon;
		var evening;
		console.log('lang pref  is ',lang)
		$('#greeting').html('');// flushing the text that was initially there
		if(lang==="EN") {
			morning = ('Good morning');
			afternoon = ('Good afternoon');
			evening = ('Good evening');
		}else {
			morning = ('Buenos dias');
			afternoon = ('Buenas tardes');
			evening = ('Buenas noches');
		}
		
	
		if (thehours >= 0 && thehours < 12) {
			themessage = morning; 
	
		} else if (thehours >= 12 && thehours < 17) {
			themessage = afternoon;
	
		} else if (thehours >= 17 && thehours < 24) {
			themessage = evening;
		}
	
		$('#greeting').append(themessage);
	  }

$(window).on("load", function(e) {
	    $("#global-loader").fadeOut("slow");
	
		})
	
$(function(){
	// Document ready
	fnGetlastTenFiatTxn();
})
function fnGetlastTenFiatTxn() {
   		$('#get-txn-form input[name="qs"]').val('wal');
		$('#get-txn-form input[name="rules"]').val('get_last_ten_txn');
	
		var formData = new FormData($('#get-txn-form')[0]);
		formData.append("hdnlang", $("#lang_def").val());
		
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
				//wallet_txn
				var data = JSON.parse(result);
				var htmlTable = '';
				var txnList = data.data;
				$('#wallet_txn').html('');
				var transactionDate='';
				var splittedTransactionDate='';
				//console.log('data ', data);
				if (data.error == 'false') {
					console.log('length is ', txnList.length)
					if (txnList.length > 0) {
						htmlTable += `
						
						<table class="table card-table table-vcenter text-nowrap table-nowrap">
													<thead >
														<tr>
															<th>`+$('#table-label-date').text()+`</th>
															<th>`+$('#table-label-description').text()+`</th>
															<th>`+$('#table-label-amount').text()+`</th>
														</tr>
													</thead>
													<tbody>`;
						for (i = 0; i < txnList.length; i++) {
							transactionDate=txnList[i].txnDateTime;
							splittedTransactionDate=transactionDate.split(",");
							htmlTable += `<tr>
	                                        <td><p class="option1-text-color">`+splittedTransactionDate[0]+`</p></td>
	                                        <td>`+ txnList[i].txnDescription + `</td>`;
											if (txnList[i].txnMode==="D"){
											htmlTable+=`<td><button class="btn btn-pill btn-danger btn-sm"> - $`+ txnList[i].txnAmount+` </button></td>`;	
											}else{
												htmlTable+=`<td><button class="btn btn-pill btn-success btn-sm"> + $`+ txnList[i].txnAmount +` </button></td>`;	
											}
	                                     htmlTable+=`</tr>`;
						}
						htmlTable += `</tbody></table>
								
					`;
					} else {
						console.log("inside here")
						htmlTable = `<span id="table-label-nodata-present" style="color:red">No Transaction Present at the moment</span>`;

					}
					
					$('#wallet_txn').append(htmlTable);
				} else {
					Swal.fire({
						icon: 'error',
						title: $('#data-validation-error-swal-header').text(),
						text: $('#failed-to-get-data-validation-error-swal-header').text(),
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
	function fnShowRequestDetail(disputeId) {
		//alert(disputeId)
		$('#dispute-form').attr('action', 'ws');
		$('input[name="qs"]').val('custdspt');
		$('input[name="rules"]').val('view_cust_specific_dispute');
		$('input[name="hdnreqid"]').val(disputeId);
		$("#dispute-form").submit();
	}
	function fnOpenRaiseDisputes() {
		//alert(disputeId)
		$('#dispute-form').attr('action', 'ws');
		$('input[name="qs"]').val('custdspt');
		$('input[name="rules"]').val('Raise Disputes');
		$("#dispute-form").submit();
	}
/*	
	function fnGetDisputes(relno) {
		$('#dispute-form input[name="qs"]').val('custdspt');
		$('#dispute-form input[name="rules"]').val('fetchcustdisputes');
		$('#dispute-form input[name="relno"]').val(relno);
	
		var formData = new FormData($('#dispute-form')[0]);
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
				//wallet_txn
				var data = JSON.parse(result);
				var htmlTable = '';
				var disputeDetails = data.data;
				$('#cust-disputes').html('');
				$('#card_disp_header').html('');
				$('#card_disp_header').html('Recent Disputes')
				if (data.error == 'false') {
					//console.log('length is ', txnList.length)
					if (disputeDetails.length > 0) {
						htmlTable += ` <table id="dispute_table" class="table card-table table-striped table-vcenter table-outline table-bordered text-nowrap ">
											<thead>
												<tr>
													<th scope="col" class="border-top-0">Dispute ID</th>
													<th scope="col" class="border-top-0">Reason</th>
													<th scope="col" class="border-top-0">Status</th>
													<th scope="col" class="border-top-0">Raised On</th>
													<th scope="col" class="border-top-0">Details </th>
												</tr>
											</thead>
											<tbody>`
						for (i = 0; i < disputeDetails.length; i++) {
							htmlTable += `<tr>
		                                        <td>`+ i + `</td>
		                                        <td>`+ disputeDetails[i].disputeId + `</td>
		                                        <td>`+ disputeDetails[i].reasonDesc + `</td>
		                                        <td>`+ disputeDetails[i].status + `</td>
		                                        <td>`+ disputeDetails[i].raisedOn + `</td>
												<td><a class="btn btn-sm btn-primary" style="background-color:#00008B" href="#" onclick="javascript:fnShowRequestDetail(`+ disputeDetails[i].disputeId + `);return false;">
												    <i class="fa fa-info-circle"></i> Details</a> 
												</td>												
		                                    </tr>`;
						}
					} else {
						htmlTable = `<tr><td> <span>No data Present</span></td></tr>`
					}
					htmlTable += `</tbody></table>`;
					$('#cust_disputes').append(htmlTable);
					//$('#dispute_table').DataTable();
	
	
				} else {
					Swal.fire({
						icon: 'error',
						title: 'Oops',
						text: 'Failed to get Recent Disputes',
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
*/
	
	
	
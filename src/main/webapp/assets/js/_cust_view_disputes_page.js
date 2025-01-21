
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
			"breadcrumb_item_active_label":"View Dispute",
			"label_raise_dispute_btn":"Raise Dispute",
			"label_disputes":"Disputes",
			"label_disputeid":"Dispute ID ",
			"label_reason":"Reason",
			"label_status":"Status",
			"label_raisedon":"Raised On",
			"label_action":"Action",
			"label_no_dispute_raised":"No dispute yet raised",
			"swal_connection_prob":"Problem with connection",	
						
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
			"breadcrumb_item_active_label":"Ver disputa",
			"label_raise_dispute_btn":"Generar disputa",
			"label_disputes":"Disputas",
			"label_disputeid":"ID de disputa",
			"label_reason":"Motivo",
			"label_status":"Estado",
			"label_raisedon":"Elevado en",
			"label_action":"Acción",
			"label_no_dispute_raised":"Todavía no se ha presentado ninguna disputa",
			"swal_connection_prob":"Problema con la conexión",
			
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
		$('#label_raise_dispute_btn').text(i18next.t('label_raise_dispute_btn')); 
		$('#label_disputes').text(i18next.t('label_disputes')); 
		$('#label_disputeid').text(i18next.t('label_disputeid')); 
		$('#label_reason').text(i18next.t('label_reason')); 
		$('#label_status').text(i18next.t('label_status')); 
		$('#label_raisedon').text(i18next.t('label_raisedon')); 
		$('#label_action').text(i18next.t('label_action')); 
		$('#label_no_dispute_raised').text(i18next.t('label_no_dispute_raised')); 
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



$(window).on("load", function(e) {
    $("#global-loader").fadeOut("slow");
})
function fnGetDisputes(relno){
	$('#dispute-form input[name="qs"]').val('custdspt');
	$('#dispute-form input[name="rules"]').val('fetchcustdisputes');
	$('#dispute-form input[name="relno"]').val(relno);
	var form = $('#dispute-form')[0];
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

				if(data.error === "false"){
					
					var disputeDetails = data.data;
					var count = disputeDetails.length; 
						
						$('#disputetable').html('');
						
				
                     var disputeTable=     `
							<div class="e-table">
											<div class="table-responsive table-lg">
												<table  id="example" class="table card-table table-vcenter text-nowrap table-nowrap">
													<thead>
														<tr>
															<th id="label_disputeid">`+$("#label_disputeid").text()+` </th>
															<th id="label_reason">`+$("#label_reason").text()+`</th>
															<th id="label_status">`+$("#label_status").text()+`</th>
															<th id="label_raisedon">`+$("#label_raisedon").text()+`</th>
															<th id="label_action" class="text-center">`+$("#label_action").text()+`</th>
														</tr>
													</thead>
													<tbody>
													`;				 
									for (var i=0; i<count;i++) {
											disputeTable+=`		<tr>
																<td class="text-nowrap align-middle">`+disputeDetails[i].disputeId+`</td>
																<td class="text-nowrap align-middle">`+disputeDetails[i].reasonDesc+`</td>
																<td class="text-nowrap align-middle">`+disputeDetails[i].status+`</td>
																<td class="text-nowrap align-middle">`+disputeDetails[i].raisedOn+`</td>
																<td class="text-center align-middle">
																	<div class="btn-group align-top">
																		<button class="btn theme-btn-sm badge"onclick="javascript:fnShowRequestDetail(`+disputeDetails[i].disputeId+`);return false;" type="button"><span id="label_view_btn">View</span></button> 
																	</div>
																</td>
															</tr>`;
														}
											
							 disputeTable+=	`				</tbody>
												</table>
											</div>
											
										</div>`	;

				}else if(data.error === "nodata"){
                        disputeTable =`
											       <span class="text-danger" id="label_no_dispute_raised" >No dispute yet raised</span>											   
								`;
								
							}
						$('#disputetable').append(disputeTable);
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
                    $('#dispute-form').attr('action', 'ws');
                    $('input[name="qs"]').val('lgt');
                    $('input[name="rules"]').val('lgtdefault');
                    $("#dispute-form").submit();	
        });
		}
	}); 
}



function fnShowRequestDetail(disputeId){
    console.log('disputeId is ', disputeId);

   $('#view-dispute-form').attr('action', 'ws');
   $('input[name="qs"]').val('custdspt');
   $('input[name="rules"]').val('view_cust_specific_dispute');
   $('input[name="hdnlang"]').val($('#lang_def').text());
   $('input[name="hdnreqid"]').val(disputeId);
   $("#view-dispute-form").submit();
}

function fnCallAddDisputePage(){
	$('#dispute-form').attr('action', 'ws');
	$('input[name="qs"]').val('card');
	$('input[name="rules"]').val('Raise Disputes');
	$('input[name="hdnlang"]').val($('#lang_def').text());
	$("#dispute-form").submit();
}
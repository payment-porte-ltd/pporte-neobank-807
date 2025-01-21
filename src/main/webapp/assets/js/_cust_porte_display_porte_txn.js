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
			"breadcrumb-item-display-txn":"Display Transactions",
			"table_header_ops_id":"Operation Id",
			"table_header_time":"Time",
			"table_header_ac":"Account",
			"table_header_txn_type":"Transaction Type",
			"table_header_sr_account":"Destination account",
			"table_header_amount":"Amount",
			"table_header_txn_fee":"Transaction Fee",
			"no_available_txn_details":"No available Transactions Details"
			
			
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
			"breadcrumb-item-display-txn":"Mostrar transacciones",
			"table_header_ops_id":"Identificación de la operación",
			"table_header_time":"Tiempo",
			"table_header_ac":"Cuenta",
			"table_header_txn_type":"tipo de transacción",
			"table_header_sr_account":"Cuenta de destino",
			"table_header_amount":"Monto",
			"table_header_txn_fee":"Tarifa de transacción",
			"no_available_txn_details":"No hay detalles de transacciones disponibles"
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
        $('#breadcrumb-item-display-txn').text(i18next.t('breadcrumb-item-display-txn'));
        $('#table_header_ops_id').text(i18next.t('table_header_ops_id'));
        $('#table_header_time').text(i18next.t('table_header_time'));
        $('#table_header_ac').text(i18next.t('table_header_ac'));
        $('#table_header_txn_type').text(i18next.t('table_header_txn_type'));
        $('#table_header_sr_account').text(i18next.t('table_header_sr_account'));
        $('#table_header_txn_fee').text(i18next.t('table_header_txn_fee'));
        $('#no_available_txn_details').text(i18next.t('no_available_txn_details'));  
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

function shortenPublicKey(pkey) {
  return pkey.substr(0, 3) + '...' + pkey.substr(53, pkey.length);
}
var nextLink ='';
var prevLink ='';
function fnNextPage(link){
	let linkToSend="";
	 if (nextLink === "" ||nextLink === undefined){
		linkToSend=link;
	  }else{
		linkToSend=nextLink;
	}
	
	console.log("link to send is "+linkToSend);
    $('#get-txn-form input[name="qs"]').val('porte');
    $('#get-txn-form input[name="rules"]').val('get_cust_next_transactions');
    $('#get-txn-form input[name="link"]').val(linkToSend);
    
    var formData = new FormData($('#get-txn-form')[0]);
		formData.append("publickey",pubKey);	
		console.log("publickey next",pubKey);	
  
    $.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader('x-api-key' , getAPIKey());
        }
    });
		$("#prev_btn").removeClass("disabled");
		$("#next_btn").addClass("btn-loadding");
		$('#spinner-div').show();//Load button clicked show spinner
    $.ajax({
        url: 'ms',
        data: formData,
        processData: false,
        contentType: false,
        type: 'POST',
        success: function (result) {
		$("#next_btn").removeClass("remove-loadding");
	    $('#spinner-div').hide();//Request is complete so hide spinner
            //wallet_txn
            var data = JSON.parse(result);
            var htmlTable ='';
            
            var txnList = data.data;
			console.table(txnList);
            $('#htmltable').html('');
  
            if(data.error=='false'){
                htmlTable+=`<table id="stellar_txns" class="table table-striped table-bordered text-nowrap" >
												<thead>
													<tr>
														<th>Operation Id</th>
														<th>Time</th>
														<th>Transaction Type</th>
														<th>Source account</th>
														<th>Destination account</th>
														<th>Amount </th>
														<th>Transaction Fee</th>
													</tr>
												</thead>
												<tbody>`;
                if(txnList.length>0){
                    for (i=0; i<txnList.length;i++){
                        htmlTable+=`<tr>
                                        <td>`+txnList[i].operationId+`</td>
                                        <td>`+txnList[i].createdOn+`</td>
                                        <td>`+txnList[i].type+`</td>`;
										if( txnList[i].type==="payment") {
										 htmlTable+=`
													<td>`+shortenPublicKey(txnList[i].fromAccount)+`</td>
													<td>`+shortenPublicKey(txnList[i].toAccount)+`</td>
													<td>`+txnList[i].amount+` `+txnList[i].assetCode+`</td>`;
										}else if(txnList[i].type==="manage_sell_offer"){
											htmlTable+=`
													<td>`+txnList[i].sellingAsset+`</td>
													<td>`+txnList[i].buyingAsset+`</td>
													<td>`+txnList[i].offerPrice+`</td>`;
										}else if(txnList[i].type==="manage_buy_offer"){
											htmlTable+=`
													<td>`+txnList[i].sellingAsset+`</td>
													<td>`+txnList[i].buyingAsset+`</td>
													<td>`+txnList[i].offerPrice+`</td>`;
										}else if(txnList[i].type==="change_trust"){
											htmlTable+=`
													<td>`+shortenPublicKey(txnList[i].trustee)+`</td>
													<td>`+shortenPublicKey(txnList[i].trustor)+`</td>
													<td>`+txnList[i].assetCode+`</td>`;
										}else if(txnList[i].type==="create_account"){
											htmlTable+=`
													<td></td>
													<td></td>
													<td></td>`;
										}else if(txnList[i].type==="path_payment_strict_send"){
											htmlTable+=`
													<td>`+shortenPublicKey(txnList[i].fromAccount)+`</td>
													<td>`+shortenPublicKey(txnList[i].toAccount)+`</td>
													<td>`+txnList[i].amount+` `+txnList[i].assetCode+`</td>`;
										}else if(txnList[i].type==="path_payment_strict_receive"){
											htmlTable+=`
													<td>`+shortenPublicKey(txnList[i].fromAccount)+`</td>
													<td>`+shortenPublicKey(txnList[i].toAccount)+`</td>
													<td>`+txnList[i].amount+` `+txnList[i].assetCode+`</td>`;
										}else if(txnList[i].type==="create_claimable_balance"){
											htmlTable+=`
													<td>`+shortenPublicKey(txnList[i].fromAccount)+`</td>
													<td></td>
													<td>`+txnList[i].amount+` `+txnList[i].assetCode+`</td>`;
										}else if(txnList[i].type==="claim_claimable_balance"){
											htmlTable+=`
													<td>`+shortenPublicKey(txnList[i].fromAccount)+`</td>
													<td>`+txnList[i].toAccount+`</td>
													<td></td>`;
										}else if(txnList[i].type==="Claimable Balance to Claim"){
											htmlTable+=`
													<td>`+shortenPublicKey(txnList[i].fromAccount)+`</td>
													<td></td>
													<td>`+txnList[i].amount+` `+txnList[i].assetCode+`</td>`;
										}
								htmlTable+=`<td>`+txnList[i].feeCharged+` Stroops</td>
								</tr>`;
								
								nextLink=txnList[i].nextLink;
								prevLink=txnList[i].previousLink;
                    }
                }else{
                    htmlTable=`<tr><td> <span>No data Present</span></td></tr>`
                }
                htmlTable+=`</tbody></table>`;
                $('#htmltable').append(htmlTable);
               
            }else{
                Swal.fire({
                    icon: 'error',
                    title: 'Oops',
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
                        title: 'Oops',
                        text: 'Problem with connection',
                        showConfirmButton: true,
                        confirmButtonText: "Ok",
                        }).then(function() {
                            
                });
        }
    });             	
    
}
function fnPreviousPage(previouslink){
		let prevlinkToSend="";
	 if (prevLink === "" ||prevLink === undefined){
		prevlinkToSend=previouslink;
	  }else{
		prevlinkToSend=prevLink;
	}
	
	
		console.log("Previous to send is "+prevlinkToSend);

    $('#get-txn-form input[name="qs"]').val('porte');
    $('#get-txn-form input[name="rules"]').val('get_cust_prev_transactions');
    $('#get-txn-form input[name="link"]').val(prevlinkToSend);
    
    var formData = new FormData($('#get-txn-form')[0]);
		formData.append("publickey",pubKey)	;
		console.log("pubkey "+pubKey);

    $.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader('x-api-key' , getAPIKey());
        }
    });
		
		$("#prev_btn").addClass("btn-loadding");
		$('#spinner-div').show();//Load button clicked show spinner 
    $.ajax({
        url: 'ms',
        data: formData,
        processData: false,
        contentType: false,
        type: 'POST',
        success: function (result) {
		$("#prev_btn").removeClass("remove-loadding");
		$('#spinner-div').hide();//Request is complete so hide spinner
            //wallet_txn
            var data = JSON.parse(result);
            var htmlTable ='';
            
            var txnList = data.data;
			console.table(txnList);
            $('#htmltable').html('');
  
            if(data.error=='false'){
                htmlTable+=`<table id="stellar_txns" class="table table-striped table-bordered text-nowrap" >
												<thead>
													<tr>
														<th>Operation Id</th>
														<th>Time</th>
														<th>Transaction Type</th>
														<th>Source account</th>
														<th>Destination account</th>
														<th>Amount </th>
														<th>Transaction Fee</th>
													</tr>
												</thead>
												<tbody>`;
                if(txnList.length>0){
                    for (i=0; i<txnList.length;i++){
                        htmlTable+=`<tr>
                                        <td>`+txnList[i].operationId+`</td>
                                        <td>`+txnList[i].createdOn+`</td>
                                        <td>`+txnList[i].type+`</td>`;
										if( txnList[i].type==="payment") {
										 htmlTable+=`
													<td>`+shortenPublicKey(txnList[i].fromAccount)+`</td>
													<td>`+shortenPublicKey(txnList[i].toAccount)+`</td>
													<td>`+txnList[i].amount+` `+txnList[i].assetCode+`</td>`;
										}else if(txnList[i].type==="manage_sell_offer"){
											htmlTable+=`
													<td>`+txnList[i].sellingAsset+`</td>
													<td>`+txnList[i].buyingAsset+`</td>
													<td>`+txnList[i].offerPrice+`</td>`;
										}else if(txnList[i].type==="manage_buy_offer"){
											htmlTable+=`
													<td>`+txnList[i].sellingAsset+`</td>
													<td>`+txnList[i].buyingAsset+`</td>
													<td>`+txnList[i].offerPrice+`</td>`;
										}else if(txnList[i].type==="change_trust"){
											htmlTable+=`
													<td>`+shortenPublicKey(txnList[i].trustee)+`</td>
													<td>`+shortenPublicKey(txnList[i].trustor)+`</td>
													<td>`+txnList[i].assetCode+`</td>`;
										}else if(txnList[i].type==="create_account"){
											htmlTable+=`
													<td></td>
													<td></td>
													<td></td>`;
										}else if(txnList[i].type==="path_payment_strict_send"){
											htmlTable+=`
													<td>`+shortenPublicKey(txnList[i].fromAccount)+`</td>
													<td>`+shortenPublicKey(txnList[i].toAccount)+`</td>
													<td>`+txnList[i].amount+` `+txnList[i].assetCode+`</td>`;
										}else if(txnList[i].type==="path_payment_strict_receive"){
											htmlTable+=`
													<td>`+shortenPublicKey(txnList[i].fromAccount)+`</td>
													<td>`+shortenPublicKey(txnList[i].toAccount)+`</td>
													<td>`+txnList[i].amount+` `+txnList[i].assetCode+`</td>`;
										}else if(txnList[i].type==="create_claimable_balance"){
											htmlTable+=`
													<td>`+shortenPublicKey(txnList[i].fromAccount)+`</td>
													<td></td>
													<td>`+txnList[i].amount+` `+txnList[i].assetCode+`</td>`;
										}else if(txnList[i].type==="claim_claimable_balance"){
											htmlTable+=`
													<td>`+shortenPublicKey(txnList[i].fromAccount)+`</td>
													<td>`+shortenPublicKey(txnList[i].toAccount)+`</td>
													<td></td>`;
										}else if(txnList[i].type==="Claimable Balance to Claim"){
											htmlTable+=`
													<td>`+shortenPublicKey(txnList[i].fromAccount)+`</td>
													<td></td>
													<td>`+txnList[i].amount+` `+txnList[i].assetCode+`</td>`;
										}
								htmlTable+=`<td>`+txnList[i].feeCharged+` Stroops</td>
								</tr>`;
								
								nextLink=txnList[i].nextLink;
								prevLink=txnList[i].previousLink;
                    }
                }else{
                    htmlTable=`<tr><td> <span>No data Present</span></td></tr>`
                }
                htmlTable+=`</tbody></table>`;
                $('#htmltable').append(htmlTable);
               
            }else{
                Swal.fire({
                    icon: 'error',
                    title: 'Oops',
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
                        title: 'Oops',
                        text: 'Problem with connection',
                        showConfirmButton: true,
                        confirmButtonText: "Ok",
                        }).then(function() {
                            
                });
        }
    });             	
    
}


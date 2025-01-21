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
			"breadcrumb-item-cash-transaction":"Cash Transactions",
			"breadcrumb-item-search-transaction":"Search Transactions",
			"transaction-filter-header":"Transaction Filter",
			"date-from-label":"Date From",
			"date-to-label":"Date To",
			"seach_btn_label":"Search Transactions",
			"data-validation-error-swal-header":"Oops..",
			"data-validation-error-failed-to-get-details":"Failed to get details",
			"data-validation-problem-with-connection":"Problem with connection",
			"data-validation-check-your-data":"Please check your data",
			"data-validation-failed-to-get-transactions-data":"Failed to get Transactions",
			"last-five-transaction-tabel-label":"Last Five Transactions",
			"date-table-label":"Date",
			"transaction-code-table-label":"Transaction Code",
			"description-table-label":"Description",
			"amount-table-label":"Amount(USD)",
			"no-data-present-table-label":"No data Present",
			"filtered-transaction-table-label":"Filtered Transactions",
			"data-validation-date-from":"Please enter date from",
			"data-validation-date-to":"Please enter date to"
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
			"breadcrumb-item-cash-transaction":"Transacciones en efectivo",
			"breadcrumb-item-search-transaction":"Buscar transacciones",
			"transaction-filter-header":"Filtro de transacciones",
			"date-from-label":"Fecha de",
			"date-to-label":"Fecha hasta",
			"seach_btn_label":"Buscar transacciones",
			"data-validation-error-swal-header":"Ups..",
			"data-validation-error-failed-to-get-details":"Error al obtener detalles",
			"data-validation-problem-with-connection":"problema con la conexion",
			"data-validation-check-your-data":"Por favor revisa tus datos",
			"data-validation-failed-to-get-transactions-data":"Error al obtener transacciones",
			"last-five-transaction-tabel-label":"Últimas cinco transacciones",
			"date-table-label":"Fecha",
			"transaction-code-table-label":"Codigo de transacción",
			"description-table-label":"Descripción",
			"amount-table-label":"Monto (USD)",
			"no-data-present-table-label":"Sin datos Presente",
			"filtered-transaction-table-label":"Transacciones filtradas",
            "data-validation-date-from":"Por favor, introduzca la fecha de",
			"data-validation-date-to":"Por favor ingrese la fecha para"
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
        $('#breadcrumb-item-cash-transaction').text(i18next.t('breadcrumb-item-cash-transaction'));
        $('#breadcrumb-item-search-transaction').text(i18next.t('breadcrumb-item-search-transaction'));
        $('#transaction-filter-header').text(i18next.t('transaction-filter-header'));
        $('#date-from-label').text(i18next.t('date-from-label'));
        $('#date-to-label').text(i18next.t('date-to-label'));
        $('#seach_btn_label').text(i18next.t('seach_btn_label'));
        $('#data-validation-error-swal-header').text(i18next.t('data-validation-error-swal-header'));
        $('#data-validation-error-failed-to-get-details').text(i18next.t('data-validation-error-failed-to-get-details'));
        $('#data-validation-problem-with-connection').text(i18next.t('data-validation-problem-with-connection'));
        $('#welcome_data-validation-check-your-datalabel').text(i18next.t('data-validation-check-your-data'));
        $('#data-validation-failed-to-get-transactions-data').text(i18next.t('data-validation-failed-to-get-transactions-data'));
        $('#last-five-transaction-tabel-label').text(i18next.t('last-five-transaction-tabel-label'));
        $('#date-table-label').text(i18next.t('date-table-label'));
        $('#transaction-code-table-label').text(i18next.t('transaction-code-table-label'));
        $('#description-table-label').text(i18next.t('description-table-label'));
        $('#amount-table-label').text(i18next.t('amount-table-label'));
        $('#no-data-present-table-label').text(i18next.t('no-data-present-table-label'));
        $('#filtered-transaction-table-label').text(i18next.t('filtered-transaction-table-label'));
        $('#data-validation-date-from').text(i18next.t('data-validation-date-from'));
        $('#data-validation-date-to').text(i18next.t('data-validation-date-to'));       
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

function fnGetlastTenTxn(){
    $('#get-txn-form input[name="qs"]').val('wal');
    $('#get-txn-form input[name="rules"]').val('get_last_ten_txn');
    
    var formData = new FormData($('#get-txn-form')[0]);
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
            //wallet_txn
            var data = JSON.parse(result);
            var htmlTable ='';
            var txnList = data.data;
            $('#wallet_txn').html('');
            $('#card_txn_header').html('');
            console.log('data ',data);
            $('#card_txn_header').html($('#last-five-transaction-tabel-label').text());
            if(data.error=='false'){
                console.log('length is ', txnList.length)
                if(txnList.length>0){
                    htmlTable+=`<table id="txn_589_example" class="table card-table table-vcenter text-nowrap table-nowrap">
                <thead>
                    <tr>
                        <th class="wd-15p">`+$('#date-table-label').text()+`</th>
                        <th class="wd-15p">`+$('#transaction-code-table-label').text()+`</th>
						<th class="wd-15p">`+$('#amount-table-label').text()+`</th>
                        <th class="wd-20p">`+$('#description-table-label').text()+`</th>
                     </tr>
                </thead>
                <tbody>`
                    for (i=0; i<txnList.length;i++){
                        htmlTable+= `<tr>
                                        <td>`+txnList[i].txnDateTime+`</td>
                                        <td>`+txnList[i].txnUserCode+`</td>`;
										if (txnList[i].txnMode==="D"){
											htmlTable+=`<td><button class="btn btn-pill btn-danger btn-sm"> - $`+ txnList[i].txnAmount+` </button></td>`;	
											}else {
												htmlTable+=`<td><button class="btn btn-pill btn-success btn-sm"> + $`+ txnList[i].txnAmount +` </button></td>`;	
											}	
                             htmlTable+= `<td>`+txnList[i].txnDescription+`</td>
                        
                                    </tr>`;
                    }
                }else{
                    htmlTable=`<tr><td> <span>`+$('#no-data-present-table-label').text()+`</span></td></tr>`
                }
                htmlTable+=`</tbody></table>`;
                $('#div_table').append(htmlTable);
                $('#txn_589_example').dataTable({ "bSort" : false } ); 
				//$('#viewbtcxpricingtable').dataTable({ "bSort" : false } );
               
            }else{
                Swal.fire({
                    icon: 'error',
                    title: $('#data-validation-error-swal-header').text(),
                    text: $('#data-validation-failed-to-get-transactions-data').text(),
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

function fnGetFilteredTxn(){
   $( "#filter-txn-form" ).validate( {
        rules: {
            datefrom: {
                required: true
            },
            dateto: {
                required: true
            }
        },
        messages: {
            datefrom: {
                required: $('#data-validation-date-from').text()
            },
            dateto: {
                required: $('#data-validation-date-to').text()
               
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
    if($( "#filter-txn-form" ).valid()){
        $('#filter-txn-form input[name="qs"]').val('wal');
        $('#filter-txn-form input[name="rules"]').val('get_txn_btn_dates');
        
        var formData = new FormData($('#filter-txn-form')[0]);
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
                var htmlTable ='';
                var txnList = data.data;
                $('#div_table').html('');
                $('#card_txn_header').html('');
                $('#card_txn_header').html($('#filtered-transaction-table-label').text())
                console.log('length is ', txnList.length)
                if(data.error=='false'){
                    htmlTable+=`<table id="txn_example" class="table card-table table-vcenter text-nowrap table-nowrap">
                        <thead>
                            <tr>
                                <th class="wd-15p">`+$('#date-table-label').text()+`</th>
                                <th class="wd-15p">`+$('#transaction-code-table-label').text()+`</th>
                                <th class="wd-20p">`+$('#description-table-label').text()+`</th>
                                <th class="wd-15p">`+$('#amount-table-label').text()+`</th>
                             </tr>
                        </thead>
                        <tbody>`
                if(txnList.length>0){
                    for (i=0; i<txnList.length;i++){
                         htmlTable+= `<tr>
                                        <td>`+txnList[i].txnDateTime+`</td>
                                        <td>`+txnList[i].txnUserCode+`</td>`;
										if (txnList[i].txnMode==="D"){
											htmlTable+=`<td><button class="btn btn-pill btn-danger btn-sm"> - $`+ txnList[i].txnAmount+` </button></td>`;	
											}else {
												htmlTable+=`<td><button class="btn btn-pill btn-success btn-sm"> + $`+ txnList[i].txnAmount +` </button></td>`;	
											}	
                             htmlTable+= `<td>`+txnList[i].txnDescription+`</td>
                        
                                    </tr>`;
                    }
                }else{
                    htmlTable=`<tr><td> <span>`+$('#no-data-present-table-label').text()+`</span></td></tr>`
                }
                htmlTable+=`</tbody>
                </table>`;
                $('#div_table').append(htmlTable);
                $('#txn_example').dataTable({ "bSort" : false } );
                    
                }else{
                   
                }
            },
            error: function() {
                Swal.fire({
                            icon: 'error',
                            title: $('#no-data-present-table-label').text(),
                            text: $('#data-validation-problem-with-connection').text(),
                            showConfirmButton: true,
                            confirmButtonText: "Ok",
                            }).then(function() {
                                
                    });
            }
        });             	
    }
    
}
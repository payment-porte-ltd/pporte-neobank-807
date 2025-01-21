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
            "header_please_wait_page":"Please wait while your wallet is being generated",
            "card_digital_asset_title":"Creating Digital Assets",
            "header_time":"(It will take some time)",
            "label_bitcoin_address":"Bitcoin Address",
            "label_btc_pub_key":"Bitcoin Public Key",
            "label_btc_private_key":"Bitcoin Private Key",
            "label_stellar_pub_key":"Stellar Public Key",
            "label_stl_private_key":"Stellar Private Key",
            "btncopy":"Click to copy",
 			 "swal_stellar_btc_copy_title":"Stellar and Bitcoin Keys Copied",
            "swal_stellar_btc_copy_text":"You have successfully copied Stellar and Bitcoin Keys, Please Paste and Store in a safe place",

			
			
			
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
            "header_please_wait_page":"Espere mientras se genera su billetera",
            "card_digital_asset_title":"Creación de activos digitales",
            "header_time":"(Tomará un poco de tiempo)",
            "label_bitcoin_address":"dirección de Bitcoin",
            "label_btc_pub_key":"Clave pública de Bitcoin",
            "label_btc_private_key":"Clave privada de Bitcoin",
            "label_stellar_pub_key":"Clave pública estelar",
            "label_stl_private_key":"Clave privada estelar",
            "btncopy":"Haga clic para copiar",

			"swal_stellar_btc_copy_title":"Claves estelares y bitcoins copiados",
            "swal_stellar_btc_copy_text":"Ha copiado con éxito las claves Stellar y Bitcoin, pegue y almacene en un lugar seguro",
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
        $('#header_please_wait_page').text(i18next.t('header_please_wait_page'));        
        $('#card_digital_asset_title').text(i18next.t('card_digital_asset_title'));        
        $('#header_time').text(i18next.t('header_time'));        
        $('#label_bitcoin_address').text(i18next.t('label_bitcoin_address'));        
        $('#label_btc_pub_key').text(i18next.t('label_btc_pub_key'));        
        $('#label_btc_private_key').text(i18next.t('label_btc_private_key'));        
        $('#label_stellar_pub_key').text(i18next.t('label_stellar_pub_key'));        
        $('#label_stl_private_key').text(i18next.t('label_stl_private_key'));        
        $('#btncopy').text(i18next.t('btncopy'));   

		$('#swal_stellar_btc_copy_title').text(i18next.t('swal_stellar_btc_copy_title')); 
        $('#swal_stellar_btc_copy_text').text(i18next.t('swal_stellar_btc_copy_text'));      

       
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
var functionCallCounter = 1;
$( document ).ready(function() {
    fnRelationshipNo();
    createDigitalAssets();
   
});



function checkDigitalAssetIfAreCreated(){
    console.log('check ing');
	var formData = new FormData();
	formData.append('qs','porte')
	formData.append('rules','check_if_digital_assets_are_created');
	formData.append('relno',relno)
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
            console.log('data is ',data );
            if(data.error=='false'){
                 if(data.process_status ==='C'){
                    functionCallCounter =12;
                    $("#div_please_wait_circle").hide();
                    $("#div_keys").show();
                    $('#idbitcoinaddress').val(data.btc_address);
                    $('#idbtcpublickkey').val(data.btc_public_key);
                    $('#idbtcprivatekey').val(data.btc_private_key);
                    $('#idstlpublickkey').val(data.public_key);
                    $('#idstlprivatekey').val(data.private_key);
                 }
            }else{
               
            }
        },
        error: function() {
          
        }
    });             	
}

function createDigitalAssets(){
	var formData = new FormData();
	formData.append('qs','porte')
	formData.append('rules','create_digital_asset')
	formData.append('json_string',data)
	formData.append('relno',relno)
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
           /* var data = JSON.parse(result);
            console.log('data is ',data );
            if(data.error=='false'){
                 
            }else{
               
            }*/
        },
        error: function() {
           
        }
    });       
    checkDigitalAssetIfAreCreated();
}

setInterval(function(){
    //if(functionCallCounter<12){
        checkDigitalAssetIfAreCreated()
    //}
}, 5000);

var copyBtn = document.querySelector('#btncopy');
copyBtn.addEventListener('click', function () {
	console.log("Inside copy")
	$("#allinfo").removeClass("hidden");
	$("#allinfo").val("Bitcoin Adress: "+$("#idbitcoinaddress").val() + "\r\n Bitcoin Public Key: " + $("#idbtcpublickkey").val() +
	 "\r\n Bitcoin Private Key: " + $("#idbtcprivatekey").val()+"\r\n Stellar Public Key: "+$("#idstlpublickkey").val()+"\r\n Stellar Private Key: "+$("#idstlprivatekey").val()).val();
	var infoField = document.querySelector('#allinfo');
	console.log("getting here222")
	infoField .select();
	document.execCommand('copy');
	$("#allinfo").addClass("hidden");
		Swal.fire({
                        icon: 'success',
                        title:  $("#swal_stellar_btc_copy_title").text(),
                        text: $("#swal_stellar_btc_copy_text").text(),
                        showConfirmButton: true,
                        confirmButtonText: "Ok",
						timer: 5000
                        }).then(function() {
                           $('#get-page-form').attr('action', 'ws');
	                        $('input[name="qs"]').val('porte');
	                        $('input[name="rules"]').val('Assets');
	                        $("#get-page-form").submit();  
                });
	}, false);



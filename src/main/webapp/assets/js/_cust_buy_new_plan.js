
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
			"breadcrumb_item_label":"Plans",
			"breadcrumb_item_active_label":"Buy Plan",
			"label_select_plan":"Select Your Account Plan below",
			"label_buy_now_btn":"Buy Now",			
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
			"breadcrumb_item_label":"Planes",
			"breadcrumb_item_active_label":"Comprar plan",
			"label_select_plan":"SSeleccione su plan de cuenta a continuación",
			"label_buy_now_btn":"Comprar ahora",	
			
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
		$('#label_select_plan').text(i18next.t('label_select_plan'));
		$('#label_buy_now_btn').text(i18next.t('label_buy_now_btn'));
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

	function fnConfirmBuy(planid, plandesc, planprice, planduration, ishavingplanid ){
		console.log("planprice ",planprice)
	   $('#tempform').attr('action', 'ws');
	   if(ishavingplanid==''){
			$('input[name="qs"]').val('plb');
			 $('input[name="hdnoriginalplanid"]').val(ishavingplanid);
	   }else{
		   $('input[name="qs"]').val('plu');
		    $('input[name="hdnoriginalplanid"]').val(ishavingplanid);
	   }
	   $('input[name="rules"]').val('custconfirmbuyplan');
	   $('input[name="hdnplanid"]').val(planid);
	   $('input[name="hdnplandesc"]').val(plandesc);
	   $('input[name="hdnplanval"]').val(planprice);
	   $('input[name="hdnlang"]').val($('#lang_def').text());
	   $('input[name="hdnplanduration"]').val(planduration);
	   $("#tempform").submit();	
		}
		
	




 

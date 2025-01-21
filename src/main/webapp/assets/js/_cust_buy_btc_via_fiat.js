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
			"breadcrumb-item-bitcoin":"Bitcoin",
			"card-title-bitcoin":"Bitcoin",
			"tab-header-native-bitcoin":"Native Bitcoin",
			"tab-header-native-stellar-bitcoin":"Stellar Bitcoin",
			"tab-buy-btc":"Buy BTC",
			"tab-transfer-btc":"Transfer BTC",
			"tab-swap-btc":"Swap BTC",
			"tab-transactions-btc":"Transactions",
			"sp_select_mode_of_payment_899":"Select mode of payment",
			"spn_select_paymode":"Select paymode",
			"spn_select_option_wallet":"Fiat Wallet",
			"spn_select_option_card":"Card",
			"sp_sel_card_to_pay_with_78855":"Select Card to pay with",
			"sp_ccv2855":"Enter CVV",
			"amount_to_label_784569":"Amount to Buy",
			"btn_buy_btc_label":"Buy BTC",
			"spn_view_pending_transaction":"View Pending Transactions",
			"spn_you_dont_have_btc_wallet":"You don't have Bitcoin Wallet",
			"spn_source_785869":"Source",
			"destination_label_25364":"Destination",
			"send_to_label_4758":"Send To",
			"amount_to_send_label_45":"Amount to send",
			"commet_trans_btc_label_71":"Comment",
			"spn_btn_coinspayanyone_trans_btc":"Send Coin",
			"spn_btc_wallet_not_available":"You don't have Bitcoin Wallet",
			"source_asset_label_21":"Source Asset",
			"destination_asset_label_36":"Destination Asset",
			"amount_label_58":"Amount",
			"spn_exchange_asset":"Exchange Asset",
			"span_view_pending_txn":"View Pending Transactions",
			"spn_you_dont_have_btc_wallet":"You don't have Bitcoin Wallet",
			"p_final_balance":"Final Balance",
			"p_total_received":"Total Received",
			"p_total_amount_sent":"Total Sent",
			"p_number_of_txn":"Number of Transactions",
			"p_you_dont_have_btc_account":"You don't have BTC Account",
			"th_txn_hash":"Transaction Hash",
			"th_txn_date":"date",
			"th_txn_status":"Status",
			"th_txn_amount":"Transaction Amount",
			"ops_no_availabele_txn_dtls":"No available Transactions Details",
			"spn_buy_btcx_tab":"Buy BTCx",
			"spn_swap_btcx_tab":"Swap BTCx",
			"sp_sel_mode_of_payment":"Select mode of payment",
			"spn_option_sel_paymode":"Select paymode",
			"spn_option_sel_wallet":"Fiat Wallet",
			"spn_option_sel_card":"Card",
			"sp_sel_card_pay_with":"Select Card to pay with",
			"sp_ccv_buy_btcx":"Enter CVV",
			"amount_to_buy_btcx":"Amount to Buy",
			"span_buy_btcx_btn":"Buy BTCx",
			"spn_view_pending_transaction_label":"View Pending Transactions",
			"ops_you_dont_have_stellar_btc_wal":"You don't have Stellar Bitcoin Wallet ",
			"spn_destination_asset_label":"Destination Asset",
			"spn_sel_source_coin_label":"Select Source Coin",
			"spn_label_amount_label_14":"Amount",
			"spn_exchange_asset_btn":"Exchange Asset",
			"spn_view_pending_transaction_label_47":"View Pending Transactions",
			"ops_no_external_wallets_78":"No External Wallets",
			"data-validation-error-swal-header":"Oops..",
			"data-validation-error-failed-to-get-details":"Problem with connection",
			"data-validation-check-your-data":"Please check your data",
			"data-validation-please-card-pay-with":"Please Select Card to pay with",
			"data-validation-please-coin-to-buy":"Please select coin to buy",
			"data-validation-please-payment-method":"Please select payment method",
			"data-validation-please-enter-amount":"Please enter amount",
			"data-validation-please-select-coin-to-transfer":"Please select coin to transfer",
			"data-validation-please-enter-receiver":"Please enter receiver",
			"enter-your-passsword-tittle":"Enter your Password",
			"enter-your-passsword-label":"Password",
			"data-validation-enter-your-passsword-label":"Please input your password!",
			"data-validation-enter-your-pvt-key-label":"Please input your Private Key!",
			"enter-your-pvt-key-label":"Enter your Private Key",
			"no-assets-available":"No assets available",
			"transfer-complete-label":"Transfer Complete",
			"data-validation-please-dont-have-porte-coin":"You dont have porte coin, click Register to register",
			"data-validation-please-select-coin-to-swap":"Please select coin to swap",
			"data-validation-please-select-destination-coin":"Please select destination coin",
			"data-validation-select-coin":"Select Coin",
			"data-validation-no-assets-available":"No assets available",
			
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
			"breadcrumb-item-bitcoin":"Bitcoin",
			"card-title-bitcoin":"Bitcoin",
			"tab-header-native-bitcoin":"Bitcoin nativo",
			"tab-header-native-stellar-bitcoin":"Stellar Bitcoin",
			"tab-buy-btc":"Comprar BTC",
			"tab-transfer-btc":"Transferir BTC",
			"tab-swap-btc":"Intercambiar BTC",
			"tab-transactions-btc":"Actas",
			"sp_select_mode_of_payment_899":"Seleccionar modo de pago",
			"spn_select_paymode":"Seleccionar modo de pago",
			"spn_select_option_wallet":"Monedero Fiat",
			"spn_select_option_card":"Tarjeta",
			"sp_sel_card_to_pay_with_78855":"Seleccione Tarjeta para pagar con",
			"sp_ccv2855":"Introducir CVV",
			"amount_to_label_784569":"Cantidad a comprar",
			"btn_buy_btc_label":"Comprar BTC",
			"spn_view_pending_transaction":"Ver transacciones pendientes",
			"spn_you_dont_have_btc_wallet":"No tienes Monedero Bitcoin",
			"spn_source_785869":"Fuente",
			"destination_label_25364":"Destino",
			"send_to_label_4758":"Enviar a",
			"amount_to_send_label_45":"cantidad a enviar",
			"commet_trans_btc_label_71":"Comentario",
			"spn_btn_coinspayanyone_trans_btc":"enviar moneda",
			"spn_btc_wallet_not_available":"No tienes Monedero Bitcoin",
			"source_asset_label_21":"Activo de origen",
			"destination_asset_label_36":"Activo de destino",
			"amount_label_58":"Monto",
			"spn_exchange_asset":"Activo de intercambio",
			"span_view_pending_txn":"Ver transacciones pendientes",
			"spn_you_dont_have_btc_wallet":"No tienes Monedero Bitcoin",
			"p_final_balance":"Saldo final",
			"p_total_received":"Total recibida",
			"p_total_amount_sent":"Total recibido",
			"p_number_of_txn":"Número de transacciones",
			"p_you_dont_have_btc_account":"No tienes cuenta BTC",
			"th_txn_hash":"Hash de transacción",
			"th_txn_date":"fecha",
			"th_txn_status":"Estado",
			"th_txn_amount":"cantidad de transacción",
			"ops_no_availabele_txn_dtls":"No hay detalles de transacciones disponibles",
			"spn_buy_btcx_tab":"Comprar BTCx",
			"spn_swap_btcx_tab":"Intercambiar BTCx",
			"sp_sel_mode_of_payment":"Seleccionar modo de pago",
			"spn_option_sel_paymode":"Seleccionar modo de pago",
			"spn_option_sel_wallet":"Cartera",
			"spn_option_sel_card":"Tarjeta",
			"sp_sel_card_pay_with":"Seleccione Tarjeta para pagar con",
			"sp_ccv_buy_btcx":"Introducir CVV",
			"amount_to_buy_btcx":"Cantidad a comprar",
			"span_buy_btcx_btn":"Comprar BTCx",
			"spn_view_pending_transaction_label":"Ver transacciones pendientes",
			"ops_you_dont_have_stellar_btc_wal":"No tienes Stellar Bitcoin Wallet",
			"spn_destination_asset_label":"Activo de destino",
			"spn_sel_source_coin_label":"Seleccionar moneda de origen",
			"spn_label_amount_label_14":"Monto",
			"spn_exchange_asset_btn":"Activo de intercambio",
			"spn_view_pending_transaction_label_47":"Ver transacciones pendientes",
			"ops_no_external_wallets_78":"Sin carteras externas",
			"data-validation-error-swal-header":"Ups..",
			"data-validation-error-failed-to-get-details":"problema con la conexion",
			"data-validation-check-your-data":"Por favor revisa tus datos",
			"data-validation-please-card-pay-with":"Seleccione la tarjeta para pagar con",
			"data-validation-please-coin-to-buy":"Seleccione la moneda para comprar",
			"data-validation-please-payment-method":"Por favor seleccione el método de pago",
			"data-validation-please-enter-amount":"Por favor ingrese la cantidad",
			"data-validation-please-select-coin-to-transfer":"Seleccione la moneda para transferir",
			"data-validation-please-enter-receiver":"Por favor ingrese el receptor",
			"enter-your-passsword-tittle":"Ingresa tu contraseña",
			"enter-your-passsword-label":"Clave",
			"data-validation-enter-your-passsword-label":"¡Por favor ingrese su contraseña!",
			"data-validation-enter-your-pvt-key-label":"¡Ingrese su clave privada!",
			"enter-your-pvt-key-label":"¡Ingrese su clave privada!",
			"no-assets-available":"No hay activos disponibles",
			"transfer-complete-label":"transferencia completa",
			"data-validation-please-dont-have-porte-coin":"No tienes porte coin, haz click en Registrar para registrarte",
			"data-validation-please-select-coin-to-swap":"Seleccione la moneda para intercambiar",
			"data-validation-please-select-destination-coin":"Seleccione la moneda de destino",
            "data-validation-select-coin":"Seleccionar moneda",
            "data-validation-no-assets-available":"No hay activos disponibles",
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
        $('#breadcrumb-item-bitcoin').text(i18next.t('breadcrumb-item-bitcoin'));
        $('#card-title-bitcoin').text(i18next.t('card-title-bitcoin'));
        $('#tab-header-native-bitcoin').text(i18next.t('tab-header-native-bitcoin'));
        $('#tab-header-native-stellar-bitcoin').text(i18next.t('tab-header-native-stellar-bitcoin'));
        $('#tab-buy-btc').text(i18next.t('tab-buy-btc'));
        $('#tab-transfer-btc').text(i18next.t('tab-transfer-btc'));
        $('#tab-swap-btc').text(i18next.t('tab-swap-btc'));
        $('#tab-transactions-btc').text(i18next.t('tab-transactions-btc'));
        $('#sp_select_mode_of_payment_899').text(i18next.t('sp_select_mode_of_payment_899'));
        $('#spn_select_paymode').text(i18next.t('spn_select_paymode'));
        $('#spn_select_option_wallet').text(i18next.t('spn_select_option_wallet'));
        $('#spn_select_option_card').text(i18next.t('spn_select_option_card'));
        $('#sp_sel_card_to_pay_with_78855').text(i18next.t('sp_sel_card_to_pay_with_78855'));
        $('#sp_ccv2855').text(i18next.t('sp_ccv2855'));
        $('#amount_to_label_784569').text(i18next.t('amount_to_label_784569'));
        $('#btn_buy_btc_label').text(i18next.t('btn_buy_btc_label'));
        $('#spn_view_pending_transaction').text(i18next.t('spn_view_pending_transaction'));
        $('#spn_you_dont_have_btc_wallet').text(i18next.t('spn_you_dont_have_btc_wallet'));
        $('#spn_source_785869').text(i18next.t('spn_source_785869'));
        $('#destination_label_25364').text(i18next.t('destination_label_25364'));
        $('#send_to_label_4758').text(i18next.t('send_to_label_4758'));
        $('#amount_to_send_label_45').text(i18next.t('amount_to_send_label_45'));
        $('#commet_trans_btc_label_71').text(i18next.t('commet_trans_btc_label_71'));
        $('#spn_btn_coinspayanyone_trans_btc').text(i18next.t('spn_btn_coinspayanyone_trans_btc'));
        $('#spn_btc_wallet_not_available').text(i18next.t('spn_btc_wallet_not_available'));
        $('#source_asset_label_21').text(i18next.t('source_asset_label_21'));
        $('#destination_asset_label_36').text(i18next.t('destination_asset_label_36'));
        $('#amount_label_58').text(i18next.t('amount_label_58'));
        $('#spn_exchange_asset').text(i18next.t('spn_exchange_asset'));
        $('#span_view_pending_txn').text(i18next.t('span_view_pending_txn'));
        $('#spn_you_dont_have_btc_wallet').text(i18next.t('spn_you_dont_have_btc_wallet'));
        $('#p_final_balance').text(i18next.t('p_final_balance'));
        $('#p_total_received').text(i18next.t('p_total_received'));
        $('#p_total_amount_sent').text(i18next.t('p_total_amount_sent'));
        $('#p_number_of_txn').text(i18next.t('p_number_of_txn'));
        $('#p_you_dont_have_btc_account').text(i18next.t('p_you_dont_have_btc_account'));
        $('#th_txn_hash').text(i18next.t('th_txn_hash'));
        $('#th_txn_date').text(i18next.t('th_txn_date'));
        $('#th_txn_status').text(i18next.t('th_txn_status'));
        $('#th_txn_amount').text(i18next.t('th_txn_amount'));
        $('#ops_no_availabele_txn_dtls').text(i18next.t('ops_no_availabele_txn_dtls'));
        $('#spn_buy_btcx_tab').text(i18next.t('spn_buy_btcx_tab'));
        $('#spn_swap_btcx_tab').text(i18next.t('spn_swap_btcx_tab'));
        $('#sp_sel_mode_of_payment').text(i18next.t('sp_sel_mode_of_payment'));
        $('#spn_option_sel_paymode').text(i18next.t('spn_option_sel_paymode'));
        $('#spn_option_sel_wallet').text(i18next.t('spn_option_sel_wallet'));
        $('#spn_option_sel_card').text(i18next.t('spn_option_sel_card'));
        $('#sp_sel_card_pay_with').text(i18next.t('sp_sel_card_pay_with'));
        $('#sp_ccv_buy_btcx').text(i18next.t('sp_ccv_buy_btcx'));
        $('#amount_to_buy_btcx').text(i18next.t('amount_to_buy_btcx'));
        $('#span_buy_btcx_btn').text(i18next.t('span_buy_btcx_btn'));
        $('#spn_view_pending_transaction_label').text(i18next.t('spn_view_pending_transaction_label'));
        $('#ops_you_dont_have_stellar_btc_wal').text(i18next.t('ops_you_dont_have_stellar_btc_wal'));
        $('#spn_destination_asset_label').text(i18next.t('spn_destination_asset_label'));
        $('#spn_sel_source_coin_label').text(i18next.t('spn_sel_source_coin_label'));
        $('#spn_label_amount_label_14').text(i18next.t('spn_label_amount_label_14'));
        $('#spn_exchange_asset_btn').text(i18next.t('spn_exchange_asset_btn'));
        $('#spn_view_pending_transaction_label_47').text(i18next.t('spn_view_pending_transaction_label_47'));
        $('#ops_no_external_wallets_78').text(i18next.t('ops_no_external_wallets_78'));
        $('#data-validation-error-swal-header').text(i18next.t('data-validation-error-swal-header'));
        $('#data-validation-error-failed-to-get-details').text(i18next.t('data-validation-error-failed-to-get-details'));
        $('#data-validation-check-your-data').text(i18next.t('data-validation-check-your-data'));
        $('#data-validation-please-card-pay-with').text(i18next.t('data-validation-please-card-pay-with'));
        $('#data-validation-please-coin-to-buy').text(i18next.t('data-validation-please-coin-to-buy'));
        $('#data-validation-please-payment-method').text(i18next.t('data-validation-please-payment-method'));
        $('#data-validation-please-enter-amount').text(i18next.t('data-validation-please-enter-amount'));
        $('#data-validation-please-select-coin-to-transfer').text(i18next.t('data-validation-please-select-coin-to-transfer'));
        $('#data-validation-please-enter-receiver').text(i18next.t('data-validation-please-enter-receiver'));
        $('#enter-your-passsword-tittle').text(i18next.t('enter-your-passsword-tittle'));
        $('#enter-your-passsword-label').text(i18next.t('enter-your-passsword-label'));
        $('#data-validation-enter-your-passsword-label').text(i18next.t('data-validation-enter-your-passsword-label'));
        $('#data-validation-enter-your-pvt-key-label').text(i18next.t('data-validation-enter-your-pvt-key-label'));
        $('#enter-your-pvt-key-label').text(i18next.t('enter-your-pvt-key-label'));
        $('#no-assets-available').text(i18next.t('no-assets-available'));
        $('#transfer-complete-label').text(i18next.t('transfer-complete-label'));
        $('#data-validation-please-dont-have-porte-coin').text(i18next.t('data-validation-please-dont-have-porte-coin'));
        $('#data-validation-please-select-coin-to-swap').text(i18next.t('data-validation-please-select-coin-to-swap'));
        $('#data-validation-please-select-destination-coin').text(i18next.t('data-validation-please-select-destination-coin'));
        $('#data-validation-select-coin').text(i18next.t('data-validation-select-coin'));
        $('#data-validation-no-assets-available').text(i18next.t('data-validation-no-assets-available'));
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
let source_Assetcode;
let dest_Assetcode;
function fnUpdatesenderparamsBuyBTC() {
    var assetcode = 'BTC';
    if (assetcode == '') {
        swal.fire('Select Destination Coin');
        return false;
    } else {
        UpdateConversionRateBuyBTC();
        $("#spansendcode").text(assetcode);
        $("#receiver_asset").val(assetcode);
    }
    
}

function fnGetPaymentMethodBuyBTC(relno){
    var paymentMethod = $("#payment_method_buy_btc option:selected").val();
    console.log('relno ', relno);
    console.log('paymentMethod ', paymentMethod);
    if (paymentMethod == 'T') {
        fnGetCardDetailsBuyBTC(relno);
    }else{
        $('#cardsdiv_buy_btc').hide();
    }
}

function fnUpdatereceiveparams() {
    var assetcode = $("#sender_asset option:selected").val();
    console.log('assetcode ia ', assetcode);
    if (assetcode == '') {
        swal.fire('Select Destination Coin');
        return false;
    } else {
        $("#spanreceivedcode").text(assetcode);
    }		
}

function fnGetCardDetailsBuyBTC(relno){
	$('#post-form input[name="qs"]').val('card');
	$('#post-form input[name="rules"]').val('gettokenizedcards');
	$('#post-form input[name="relno"]').val(relno);
	var form = $('#post-form')[0];
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
					var htmlData='';
	              $('#cardavailable_buy_btc').html('');
				  console.log("Error is"+ data.error);
				if(data.error ==="false"){
						var cardDetails=data.data;
						console.table(cardDetails)
						var count = cardDetails.length;
						console.log("count is "+count);
						if(count>0){
                            for (i=0; i<count;i++){
                                if(cardDetails[i].maskedCardNumber.startsWith("5")){
                                    htmlData+= `
                                        <label class="custom-control custom-radio">
                                                <input type="radio" class="custom-control-input" name="tokenid" id="tokenid" value="`+cardDetails[i].tokenId+`" >
                                                <span class="custom-control-label" style="font-size: 18px;"><img src="assets/images/cards/mastercard.png" alt="MasterCard" height="45" width="50">`+cardDetails[i].maskedCardNumber+`</span>
                                          </label>
                                    `;											
                                    }else if (cardDetails[i].maskedCardNumber.startsWith("4")){
                                        htmlData+= `
                                        <label class="custom-control custom-radio">
                                                <input type="radio" class="custom-control-input" name="tokenid" id="tokenid" value="`+cardDetails[i].tokenId+`"  >
                                                <span class="custom-control-label" style="font-size: 18px;"><img src="assets/images/cards/visa.png" alt="Visa" height="45" width="50">`+cardDetails[i].maskedCardNumber+`</span>
                                          </label>
                                    `;	
                                    }else if (cardDetails[i].maskedCardNumber.startsWith("3")){
                                        htmlData+= `
                                        <label class="custom-control custom-radio">
                                                <input type="radio" class="custom-control-input" name="tokenid" id="tokenid" value="`+cardDetails[i].tokenId+`"  >
                                                <span class="custom-control-label" style="font-size: 18px;"><img src="assets/images/cards/amex.png" alt="Amex" height="45" width="50">`+cardDetails[i].maskedCardNumber+`</span>
                                          </label>
                                    `;	
                                    }else{
                                        htmlData+= `
                                        <label class="custom-control custom-radio">
                                                <input type="radio" class="custom-control-input" name="tokenid" id="tokenid" value="`+cardDetails[i].tokenId+`"  >
                                                <span class="custom-control-label" style="font-size: 18px;">`+cardDetails[i].maskedCardNumber+`</span>
                                          </label>
                                    `;	
                                    }
                                }
                        }else{
                            htmlData =`<p style="color:grey">No Cards Present,&nbsp; <a href="#" onclick="javascript: fnCallRegisterCardPage(); return false">Click here to register</a></p> `;
                        }

				}else if(data.error === "true"){
                    htmlData =`<p style="color:grey">No Cards Present,&nbsp; <a href="#" onclick="javascript: fnCallRegisterCardPage(); return false">Click here to register</a></p> `;
					
                }
               
                $('#cardsdiv_buy_btc').show();
				$('#cardavailable_buy_btc').append(htmlData);
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

function fnCallRegisterCardPage(){
    $('#post-form').attr('action', 'ws');
    $('input[name="qs"]').val('card');
    $('input[name="rules"]').val('Register Card');
    $("#post-form").submit();	
}

function validateCardDetails(){
    if(document.getElementById('tokenid').checked == true) {
        if(!fnValidateCVV2){
            Swal.fire({
                text: 'CVV is invalid', 
                icon: "error",
                showConfirmButton: true,
                confirmButtonText: "Ok",
                }).then(function() {
                });	
        }
    }else{
        Swal.fire({
            text: $('#data-validation-please-card-pay-with').text(), 
            icon: "error",
            showConfirmButton: true,
            confirmButtonText: "Ok",
            }).then(function() {
            });	
    }   

}

function fnValidateCVV2(){
  let x = document.getElementById("cvv").value;
  if (isNaN(x)|| x.length != 3) {
    return false;
  } else {
   return true;
  }
}



function fnFetchPorteCoins(){
	$('#post-form input[name="qs"]').val('porte');
	$('#post-form input[name="rules"]').val('get_porte_coins_details');
	var form = $('#post-form')[0];
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
				var porteDetails=data.data;	
				console.log(porteDetails);
				var htmlOptions = '';
				$('#coin_balances').html('');
				if(data.error="false"){
                    htmlOptions+=`<h2 class="dashboard-title">Coin Balance</h2>`;
					if(porteDetails.length>0){
						for (i=0; i<porteDetails.length;i++){
							if(porteDetails[i].walletType === "P"){
                                htmlOptions+=`<div class="wallet-transaction-box clearfix" >
                                                    <div class="wallet-balance-ico">
                                                        <img src="assets/images/crypto/porte.svg" alt="Litcoin" height="40" width="40" >
                                                    </div>
                                                    <div class="wallet-transaction-name">
                                                        <h3>`+porteDetails[i].assetCode+`</h3>
                                                        <span>Last Updated</span>
                                                    </div>
                                                    <div class="wallet-transaction-balance">
                                                        <h3> `+porteDetails[i].currentBalance+` `+porteDetails[i].assetCode+`</h3>
                                                        <span>`+porteDetails[i].lastUpdated+`</span>
                                                    </div>
                                                </div>`;
							}else if(porteDetails[i].walletType === "V"){
                                htmlOptions+=`<div class="wallet-transaction-box clearfix" >
                                                <div class="wallet-balance-ico">
                                                    <img src="assets/images/crypto/stable.png" alt="Litcoin" height="40" width="40" >
                                                </div>
                                                <div class="wallet-transaction-name">
                                                    <h3>`+porteDetails[i].assetCode+`</h3>
                                                    <span>Last Updated</span>
                                                </div>
                                                <div class="wallet-transaction-balance">
                                                    <h3> `+porteDetails[i].currentBalance+` `+porteDetails[i].assetCode+`</h3>
                                                    <span>`+porteDetails[i].lastUpdated+`</span>
                                                </div>
                                            </div>`;
							}
							
						}
				   }else{
						Swal.fire({
							icon: 'error',
							title: 'Oops',
							text: 'You dont have porte coin, click Register to register',
							showConfirmButton: true,
							confirmButtonText: "Register",
							closeOnConfirm: true,
							}).then(function() {
								$('#post-form').attr('action', 'ws');
								$('input[name="qs"]').val('wal');
								$('input[name="rules"]').val('Create Wallet');
								$("#post-form").submit();
						});
				   } 
				   $('#coin_balances').append(htmlOptions);
                 

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


function fnCallPendingBuyBTCPage(){
    $('#post-form').attr('action', 'ws');
    $('input[name="qs"]').val('btc');
    $('input[name="rules"]').val('view_pending_btc_fiat_txn');
    $("#post-form").submit();	
}
function UpdateConversionRateBuyBTC(){
    var sourceAssetcode = 'USD';
    var destAssetcode = 'BTC';
    //alert('destAssetcode '+destAssetcode);
 
 /*sourceAssetcode = sourceAssetcode.split(",")
  sourceAssetcode = sourceAssetcode[1];
  destAssetcode = destAssetcode.split(",")
  destAssetcode = destAssetcode[1];*/
  var amount = $("#receivedamounte_buy_btc").val();
    //alert('amount '+ amount)
  var formData = new FormData();
  formData.append('qs', 'porte');
  formData.append('rules', 'porte_asset_conversion'); 
  formData.append('sourceamount', amount); 
  formData.append('source', sourceAssetcode); 
  formData.append('destination', destAssetcode); 
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
            $("#amounte_buy_btc").val(data.data);
          }else{
            console.log('Error',data.message)
	
          }
      },
      error: function() {
        console.log('Problem with connection')
      }
  });  
}





function fnBuyBTC (){
    var modeOfPayment = $("#payment_method_buy_btc option:selected").val();
    $( "#buy-btc-coin-form" ).validate( {
        rules: {
           
            payment_method: {
                required: true
            },
            amount: {
                required: true
            }
        },
        messages: {
         
            payment_method: {
                required:  $('#data-validation-please-payment-method').text()
            },
            amount: {
                required: $('#data-validation-please-enter-amount').text()
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

    if($( "#buy-btc-coin-form" ).valid()){
        var formData = new FormData($('#buy-btc-coin-form')[0]);
        formData.append('qs', 'btc');
        formData.append('rules', 'buy_btc_using_fiat'); 
        formData.append('payment_method', modeOfPayment); 
        formData.append("hdnlang", $('#lang_def').text());

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
                         $('#post-form').attr('action', 'ws');
						   $('input[name="qs"]').val('btc');
						    $('input[name="rules"]').val('view_pending_btc_fiat_txn');
						    $("#post-form").submit();	
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
                            title: $('#data-validation-error-swal-header').text(),
                            text: $('#data-validation-problem-with-connection').text(),
                            showConfirmButton: true,
                            confirmButtonText: "Ok",
                            }).then(function() {
                                
                    });
            }
        });             	
    }
}



function fnGetCoinDetailsTransferBTC(){
	$('#post-form input[name="qs"]').val('btc');
	$('#post-form input[name="rules"]').val('get_btc_details');
	var form = $('#post-form')[0];
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
				var porteCoinList=data.data;	
				console.log(porteCoinList);
                var htmlOptions = '';
				$('#div_source_asset_trans_btc').html('');
				$('#div_destination_asset_trans_btc').html('');
			/* 	$('#receiver_asset').html('');
				$('#receiver_asset_cb').html('');
 */
                
				if(data.error="false"){ 
                    if(porteCoinList.length>0){
                        for (i=0; i<porteCoinList.length;i++){
                            htmlOptions+=`<div class="col-3">
                                <label>
                                    <input  type="radio" name="coin_asset_trans_btc" disabled value="`+porteCoinList[i].assetCode+`">
                                    <img src="assets/images/crypto/bitcoin.svg" alt="`+porteCoinList[i].assetCode+`" height="40" width="40" >
                                    
                                </label>
                            </div>
                            <div class="col" style="margin-top: 7px;">
                                <p>`+porteCoinList[i].assetCode+`</p>
                            </div>`;
                        }
                    }else{
                        htmlOptions+=`<p>`+$('#data-validation-no-assets-available').text()+`</p>`;
                    }
                    $('#div_source_asset_trans_btc').append(htmlOptions);
                    $('#div_destination_asset_trans_btc').append(htmlOptions);
                   /*  $('#receiver_asset').append(htmlOptions);
                    $('#receiver_asset_cb').append(htmlOptions); */
				}else{
                        
                }
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

function fnTransferCoin (security, hasMnemonic){
    $( "#transfer-coin-form" ).validate( {
        rules: {
            sender_asset: {
                required: true
            },
            input_receiver_address: {
                required: true
            },
            sendamount: {
                required: true
            }
            
        },
        messages: {
            sender_asset: {
                required: $('#data-validation-please-select-coin-to-transfer').text()
            },
            input_receiver_address: {
                required: $('#data-validation-please-enter-receiver').text()
            },
            sendamount: {
                required: $('#data-validation-please-enter-amount').text()
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
    if($( "#transfer-coin-form" ).valid()){
	 $('#spinner-div').show();//Load button clicked show spinner
        $('#transfer-coin-form input[name="qs"]').val('btc');
        $('#transfer-coin-form input[name="rules"]').val('transfer_bitcoin');
        //$('#transfer-coin-form input[name="hdnlang"]').val($('#lang_def').text());

        var formData = new FormData($('#transfer-coin-form')[0]);
		formData.append('security',security);
		formData.append('hasMnemonic',hasMnemonic);
		formData.append('coin_asset_trans_btc','BTC');
        formData.append("hdnlang", $('#lang_def').text());
	
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
               $('#spinner-div').hide();//Request is complete so hide spinner
                var data = JSON.parse(result);
                console.log('data ',data);
                if(data.error=='false'){
                    Swal.fire({
                         title: 'Swap Complete',
					  	text: data.message,
					  	imageUrl: 'assets/images/crypto/success.svg',
					  	imageWidth: 400,
					  	imageHeight: 200,
					  	imageAlt: 'Custom image',
                    }).then(function() {
                        $('#post-form').attr('action', 'ws');
                        $('input[name="qs"]').val('btc');
                        $('input[name="rules"]').val('View Transactions');
                        $("#post-form").submit();
                    });	
                }else{
                    Swal.fire({
	                      text: data.message,
						  imageUrl: 'assets/images/crypto/error.svg',
						  imageWidth: 400,
						  imageHeight: 200,
						  imageAlt: 'Custom image',
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
}


function checkIfUserHasMneonicCodeTransferBTC(){
	var formData = new FormData();
	formData.append('qs','porte')
	formData.append('rules','check_if_customer_has_bitcoin_mnemonic_code')
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
                    fnPasswordTransferBTC(data.hasmnemonic);
                }else{
                    fnPrivateKeyTransferBTC(data.hasmnemonic)
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

function fnPasswordTransferBTC(hasMnemonic){
	const { value: password   } = Swal.fire({
		title: $('#enter-your-passsword-tittle').text(),
		input: 'password',
		inputLabel:  $('#enter-your-passsword-label').text(),
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
                fnTransferCoin(passwordVal,hasMnemonic );
			}
		});
}

function fnPrivateKeyTransferBTC(hasMnemonic){
	const { value: password   } = Swal.fire({
		title: $('#enter-your-passsword-label').text(),
		input: 'password',
		inputLabel: $('#enter-your-pvt-key-label').text(),
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
				fnTransferCoin(privatekey,hasMnemonic );
			}
		});
}
function fnGetSourceCoinDetailBTCSwap(){
	$('#post-form input[name="qs"]').val('btc');
	$('#post-form input[name="rules"]').val('get_btc_details');
	var form = $('#post-form')[0];
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
                console.log('data ', data);
				var porteCoinList=data.data;	
                var htmlOptions = '';
				$('#div_source_asset_swap_btc').html('');
				
				if(data.error="false"){
                    if(porteCoinList.length>0){
                     //  htmlOptions+=`<option disabled="disabled" value="-1" selected>Select Coin</option>`;
                        for (i=0; i<porteCoinList.length;i++){
                            
                                htmlOptions+=`<div class="col-3">
                                <label>
                                    <input  type="radio" name="coin_asset_swap_btc" disabled value="`+porteCoinList[i].assetCode+`">
                                    <img src="assets/images/crypto/bitcoin.svg" alt="`+porteCoinList[i].assetCode+`" height="40" width="40" >
                                    
                                </label>
                            </div>
                            <div class="col" style="margin-top: 7px;">
                                <p>`+porteCoinList[i].assetCode+`</p>
                            </div>`;
                            
                        }
                    }else{
                        htmlOptions+=`<p>No assets available</p>`;
                    }
            
                    $('#div_source_asset_swap_btc').append(htmlOptions);
				}else{
                        
                }
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

function fnGetBTCxDetailsSwapBTC(){
	$('#post-form input[name="qs"]').val('porte');$('#data-validation-problem-with-connection').text()
	$('#post-form input[name="rules"]').val('get_btcx_details');
	var form = $('#post-form')[0];
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
				var porteCoinList=data.data;	
                console.log('data', data);
                var htmlOptions = '';
				$('#div_destination_asset_swap_btc').html('');
				
				if(data.error="false"){
                    if(porteCoinList.length>0){
                        for (i=0; i<porteCoinList.length;i++){
                            htmlOptions+=`<div class="col-3">
                            <label>
                                <input  type="radio" name="test" disabled value="`+porteCoinList[i].assetCode+`">
                                <img src="assets/images/crypto/btcx.svg" alt="`+porteCoinList[i].assetCode+`" height="40" width="40" >
                                
                            </label>
                            </div>
                            <div class="col" style="margin-top: 7px;">
                                <p>`+porteCoinList[i].assetCode+`</p>
                            </div>`;
                        }
                       
                    }else{
                        htmlOptions+=`<p>`+$('#no-assets-available').text()+`</p>`;
                    }
            
                    $('#div_destination_asset_swap_btc').append(htmlOptions);
				}else{
                        
                }
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

function fnExChangeBTCToBTCx(security,hasMnemonic){
    $( "#exchange-btc-coin-form" ).validate( {
        rules: {
           
            destination_amount_swap_btc: {
                required: true
            }
           
        },
        messages: {
           
            destination_amount_swap_btc: {
                required: $('#data-validation-please-enter-amount').text()
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

    if($( "#exchange-btc-coin-form" ).valid()){
		$('#spinner-div').show();//Load button clicked show spinner
        var formData = new FormData($('#exchange-btc-coin-form')[0]);
        formData.append('qs', 'btc');
        formData.append('rules', 'exchange_btc_with_btcx'); 
        formData.append('source_asset', 'BTC'); 
        formData.append('destination_asset', 'BTCX'); 
        formData.append("hdnlang", $('#lang_def').text());

		formData.append('security',security);
		formData.append('hasMnemonic',hasMnemonic);
        for (var pair of formData.entries()) {
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
               $('#spinner-div').hide();//Request is complete so hide spinner
                var data = JSON.parse(result);
                // console.log('data ',data);
                if(data.error=='false'){
                    Swal.fire({
                        title: $('#transfer-complete-label').text(),
					  	title: 'Swap Complete',
					  	text: data.message,
					  	imageUrl: 'assets/images/crypto/success.svg',
					  	imageWidth: 400,
					  	imageHeight: 200,
					  	imageAlt: 'Custom image',
                    }).then(function() {
                        $('#post-form').attr('action', 'ws');
                        $('input[name="qs"]').val('btc');
                        $('input[name="rules"]').val('view_pending_btc_swapping_txn');
                        $("#post-form").submit();
                    });	
                }else{
                    Swal.fire({
                        text: data.message,
						  imageUrl: 'assets/images/crypto/error.svg',
						  imageWidth: 400,
						  imageHeight: 200,
						  imageAlt: 'Custom image',
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
}


function cryptocoversionFromSourceBTCSwap(){
  //var sourceAssetcode = $("#source_asset option:selected").val();
  //var destAssetcode = $("#destination_asset option:selected").val();
  var amount = $("#destination_amount_swap_btc").val();
 // alert(amount);
  $("#source_amount_swap_btc").val(amount);
}


function checkIfUserHasMneonicCodeSwapBTC(){
	var formData = new FormData();
	formData.append('qs','porte')
	formData.append('rules','check_if_customer_has_bitcoin_mnemonic_code')
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
                    fnPasswordSwapBTC(data.hasmnemonic);
                }else{
                    fnPrivateKeySwapBTC(data.hasmnemonic)
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

function fnPasswordSwapBTC(hasMnemonic){
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
				fnExChangeBTCToBTCx (passwordVal,hasMnemonic);
			}
		});
}

function fnPrivateKeySwapBTC(hasMnemonic){
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
				fnExChangeBTCToBTCx (privatekey,hasMnemonic);
			}
		});
}

function fnCallPendingSwapBTCTOXPage(){
    $('#post-form').attr('action', 'ws');
    $('input[name="qs"]').val('btc');
    $('input[name="rules"]').val('view_pending_btc_swapping_txn');
    $("#post-form").submit();	
}



function fnUpdatesenderparamsBuyBTCx() {
    var assetcode = 'BTCX';
    //var assetcode1 =  $('input[name="coin_asset_buy_btcx"]:checked').val();
    //console.log('assetcode is '+assetcode+' assetcode1 '+assetcode1);
    if (assetcode == '') {
        swal.fire('Select Destination Coin');
        return false;
    } else {
        UpdateConversionRateBuyBTCx();
        $("#spansendcode_buy_btcx").text(assetcode);
        //$("#receiver_asset").val(assetcode);
    }
    
}

function fnGetPaymentMethodBuyBTCx(relno){
    var paymentMethod = $("#payment_method_buy_btcx option:selected").val();
    console.log('relno ', relno);
    console.log('paymentMethod ', paymentMethod);
    if (paymentMethod == 'T') {
        fnGetCardDetailsBuyBTCx(relno);
    }else{
        $('#cardsdiv_buy_btx').hide();
    }
}

function fnUpdatereceiveparamsBuyBTCx() {
    var assetcode = 'BTCX';
    console.log('assetcode ia ', assetcode);
    if (assetcode == '') {
        swal.fire('Select Destination Coin');
        return false;
    } else {
        $("#spansendcode_buy_btcx").text(assetcode);
        
    }		
}

function fnGetCardDetailsBuyBTCx(relno){
	$('#post-form input[name="qs"]').val('card');
	$('#post-form input[name="rules"]').val('gettokenizedcards');
	$('#post-form input[name="relno"]').val(relno);
	var form = $('#post-form')[0];
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
					var htmlData='';
	              $('#cardavailable_buy_btcx').html('');
				  console.log("Error is"+ data.error);
				if(data.error ==="false"){
						var cardDetails=data.data;
						console.table(cardDetails)
						var count = cardDetails.length;
						console.log("count is "+count);
						if(count>0){
                            for (i=0; i<count;i++){
                                /* htmlData+= `
                                    <label class="custom-control custom-radio">
                                            <input type="radio" class="custom-control-input" name="tokenid" id="tokenid" value="`+cardDetails[i].tokenId+`" >
                                            <span class="custom-control-label">`+cardDetails[i].maskedCardNumber+`</span>
                                      </label>
                                `;  */  if(cardDetails[i].maskedCardNumber.startsWith("5")){
                                    htmlData+= `
                                        <label class="custom-control custom-radio">
                                                <input type="radio" class="custom-control-input" name="tokenid" id="tokenid" value="`+cardDetails[i].tokenId+`" >
                                                <span class="custom-control-label" style="font-size: 18px;"><img src="assets/images/cards/mastercard.png" alt="MasterCard" height="45" width="50">`+cardDetails[i].maskedCardNumber+`</span>
                                          </label>
                                    `;											
                                    }else if (cardDetails[i].maskedCardNumber.startsWith("4")){
                                        htmlData+= `
                                        <label class="custom-control custom-radio">
                                                <input type="radio" class="custom-control-input" name="tokenid" id="tokenid" value="`+cardDetails[i].tokenId+`"  >
                                                <span class="custom-control-label" style="font-size: 18px;"><img src="assets/images/cards/visa.png" alt="Visa" height="45" width="50">`+cardDetails[i].maskedCardNumber+`</span>
                                          </label>
                                    `;	
                                    }else if (cardDetails[i].maskedCardNumber.startsWith("3")){
                                        htmlData+= `
                                        <label class="custom-control custom-radio">
                                                <input type="radio" class="custom-control-input" name="tokenid" id="tokenid" value="`+cardDetails[i].tokenId+`"  >
                                                <span class="custom-control-label" style="font-size: 18px;"><img src="assets/images/cards/amex.png" alt="Amex" height="45" width="50">`+cardDetails[i].maskedCardNumber+`</span>
                                          </label>
                                    `;	
                                    }else{
                                        htmlData+= `
                                        <label class="custom-control custom-radio">
                                                <input type="radio" class="custom-control-input" name="tokenid" id="tokenid" value="`+cardDetails[i].tokenId+`"  >
                                                <span class="custom-control-label" style="font-size: 18px;">`+cardDetails[i].maskedCardNumber+`</span>
                                          </label>
                                    `;	
                                    }
                                }
                        }else{
                            htmlData =`<p style="color:grey">No Cards Present,&nbsp; <a href="#" onclick="javascript: fnCallRegisterCardPage(); return false">Click here to register</a></p> `;
                        }

				}else if(data.error === "true"){
                    htmlData =`<p style="color:grey">No Cards Present,&nbsp; <a href="#" onclick="javascript: fnCallRegisterCardPage(); return false">Click here to register</a></p> `;
					
                }
               
                $('#cardsdiv_buy_btx').show();
				$('#cardavailable_buy_btcx').html(htmlData);
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

function fnCallRegisterCardPage(){
    $('#post-form').attr('action', 'ws');
    $('input[name="qs"]').val('card');
    $('input[name="rules"]').val('Register Card');
    $("#post-form").submit();	
}

function validateCardDetails(){
    if(document.getElementById('tokenid').checked == true) {
        if(!fnValidateCVV2){
            Swal.fire({
                text: 'CVV is invalid', 
                icon: "error",
                showConfirmButton: true,
                confirmButtonText: "Ok",
                }).then(function() {
                });	
        }
    }else{
        Swal.fire({
            text: $('#data-validation-please-card-pay-with').text(), 
            icon: "error",
            showConfirmButton: true,
            confirmButtonText: "Ok",
            }).then(function() {
            });	
    }   

}

function fnValidateCVV2(){
  let x = document.getElementById("cvv").value;
  if (isNaN(x)|| x.length != 3) {
    return false;
  } else {
   return true;
  }
}



function fnFetchPorteCoins(){
	$('#post-form input[name="qs"]').val('porte');
	$('#post-form input[name="rules"]').val('get_porte_coins_details');
	var form = $('#post-form')[0];
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
				var porteDetails=data.data;	
				console.log(porteDetails);
				var htmlOptions = '';
				$('#coin_balances').html('');
				if(data.error="false"){
                    htmlOptions+=`<h2 class="dashboard-title">Coin Balance</h2>`;
					if(porteDetails.length>0){
						for (i=0; i<porteDetails.length;i++){
							if(porteDetails[i].walletType === "P"){
                                htmlOptions+=`<div class="wallet-transaction-box clearfix" >
                                                    <div class="wallet-balance-ico">
                                                        <img src="assets/images/crypto/porte.svg" alt="Litcoin" height="40" width="40" >
                                                    </div>
                                                    <div class="wallet-transaction-name">
                                                        <h3>`+porteDetails[i].assetCode+`</h3>
                                                        <span>Last Updated</span>
                                                    </div>
                                                    <div class="wallet-transaction-balance">
                                                        <h3> `+porteDetails[i].currentBalance+` `+porteDetails[i].assetCode+`</h3>
                                                        <span>`+porteDetails[i].lastUpdated+`</span>
                                                    </div>
                                                </div>`;
							}else if(porteDetails[i].walletType === "V"){
                                htmlOptions+=`<div class="wallet-transaction-box clearfix" >
                                                <div class="wallet-balance-ico">
                                                    <img src="assets/images/crypto/stable.png" alt="Litcoin" height="40" width="40" >
                                                </div>
                                                <div class="wallet-transaction-name">
                                                    <h3>`+porteDetails[i].assetCode+`</h3>
                                                    <span>Last Updated</span>
                                                </div>
                                                <div class="wallet-transaction-balance">
                                                    <h3> `+porteDetails[i].currentBalance+` `+porteDetails[i].assetCode+`</h3>
                                                    <span>`+porteDetails[i].lastUpdated+`</span>
                                                </div>
                                            </div>`;
							}
							
						}
				   }else{
						Swal.fire({
							icon: 'error',
							title: $('#data-validation-error-swal-header').text(),
							text: $('#data-validation-please-dont-have-porte-coin').text(),
							showConfirmButton: true,
							confirmButtonText: "Register",
							closeOnConfirm: true,
							}).then(function() {
								/* $('#post-form').attr('action', 'ws');
								$('input[name="qs"]').val('wal');
								$('input[name="rules"]').val('Create Wallet');
								$("#post-form").submit(); */
						});
				   } 
				   $('#coin_balances').append(htmlOptions);
                 

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


function fnCallPendingBuyBTCXPage(){
    $('#post-form').attr('action', 'ws');
    $('input[name="qs"]').val('btcx');
    $('input[name="rules"]').val('view_pending_btcx_fiat_txn');
    $("#post-form").submit();	
}
function UpdateConversionRateBuyBTCx(){
  var sourceAssetcode = 'USD';
  var destAssetcode = 'BTCX';
 
 /*sourceAssetcode = sourceAssetcode.split(",")
  sourceAssetcode = sourceAssetcode[1];
  destAssetcode = destAssetcode.split(",")
  destAssetcode = destAssetcode[1];*/
  var amount = $("#receivedamount_buy_btcx").val();
//alert('amount '+ amount)
  var formData = new FormData($('#buy-porte-coin-form')[0]);
  formData.append('qs', 'porte');
  formData.append('rules', 'porte_asset_conversion'); 
  formData.append('sourceamount', amount); 
  formData.append('source', sourceAssetcode); 
  formData.append('destination', destAssetcode); 
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
            $("#amount_buy_btcx").val(data.data);
          }else{
            console.log('Error',data.message)
	
          }
      },
      error: function() {
        console.log('Problem with connection')
      }
  });  
}




function fnBuyBTCx (){
    var modeOfPayment = $("#payment_method_buy_btcx option:selected").val();
    $( "#buy-btcx-coin-form" ).validate( {
        rules: {
            
            payment_method_buy_btcx: {
                required: true
            },
            receivedamount_buy_btcx: {
                required: true
            }
        },
        messages: {
            payment_method_buy_btcx: {
                required: $('#data-validation-please-payment-method').text()
            },
            receivedamount_buy_btcx: {
                required: $('#data-validation-please-enter-amount').text()
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

    if($( "#buy-btcx-coin-form" ).valid()){
        var formData = new FormData($('#buy-btcx-coin-form')[0]);
        formData.append('qs', 'porte');
        formData.append('rules', 'buy_btcx_using_fiat'); 
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
                        text: data.message,
					  	imageUrl: 'assets/images/crypto/success.svg',
					  	imageWidth: 400,
					  	imageHeight: 200,
					  	imageAlt: 'Custom image',
                    }).then(function() {
                         $('#post-form').attr('action', 'ws');
						   $('input[name="qs"]').val('btcx');
						    $('input[name="rules"]').val('view_pending_btcx_fiat_txn');
						    $("#post-form").submit();	
                    });	
                }else{
                    Swal.fire({
                        text: data.message,
						imageUrl: 'assets/images/crypto/error.svg',
						imageWidth: 400,
						imageHeight: 200,
						imageAlt: 'Custom image',
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
}

function fnUpdatesenderparams() {
    var assetcode = $('input[name="coin_source_asset"]:checked').val();
    console.log('assetcode ', assetcode);
    if (assetcode == '') {
        swal.fire('Select Destination Coin');
        return false;
    } else {
        cryptocoversionFromSourceSWapBTX();
        $("#spansendcode_swap_btcx").text(assetcode);
    }
    
}

function fnUpdateReceiverParams() {
    var assetcode = $('input[name="coin_source_asset"]:checked').val();
    console.log('assetcode ', assetcode);
    if (assetcode == '') {
        swal.fire('Select Destination Coin');
        return false;
    } else {
        cryptocoversionFromSourceSWapBTX();
        $("#spansendcode_swap_btcx").text(assetcode);
       // $("#receiver_asset").val(assetcode);
    }
    
}


function fnGetSourceCoinDetail(){
	$('#post-form input[name="qs"]').val('porte');
	$('#post-form input[name="rules"]').val('get_source_assets_details');
	var form = $('#post-form')[0];
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
				var porteCoinList=data.data;	
                var htmlOptions = '';
				$('#source_asset_swap_btcx').html('');
				
				if(data.error="false"){
                    if (porteCoinList.length > 0) {
              		htmlOptions+=`
                		<select id="select-asset-id-dest" onChange="javascript: fnOnchange(); return false">
                  		<option selected disabled>Select Coin</option>
                  		`;
                  for (i = 0; i < porteCoinList.length; i++) {
                    if(porteCoinList[i].assetCode==="PORTE"){
                    htmlOptions+=`<option value="PORTE" data-imagesrc="assets/images/crypto/porte.svg" value="` +
                      porteCoinList[i].accountId +
                      `,` +
                      porteCoinList[i].assetCode +
                      `">` +
                      porteCoinList[i].assetCode +
                      `</option>`;
                    }
                    if(porteCoinList[i].assetCode==="VESL"){
                    htmlOptions+=` <option value="VESL" data-imagesrc="assets/images/crypto/vessel.png"value="` +
                      porteCoinList[i].accountId +
                      `,` +
                      porteCoinList[i].assetCode +
                      `">` +
                      porteCoinList[i].assetCode +
                      `</option>`;
                    }
                    if(porteCoinList[i].assetCode==="XLM"){
                    htmlOptions+=` <option value="XLM" data-imagesrc="assets/images/crypto/xlm.svg"value="` +
                      porteCoinList[i].accountId +
                      `,` +
                      porteCoinList[i].assetCode +
                      `">` +
                      porteCoinList[i].assetCode +
                      `</option>`;
                    }
                    if(porteCoinList[i].assetCode==="USDC"){
                    htmlOptions+=`<option value="USDC" data-imagesrc="assets/images/crypto/usdc.png"value="` +
                      porteCoinList[i].accountId +
                      `,` +
                      porteCoinList[i].assetCode +
                      `">` +
                      porteCoinList[i].assetCode +
                      `</option>`;
                    }
                  }
                 
                  htmlOptions+=` </select>`;
                      
                    }else{
                        htmlOptions+=`<option disabled="disabled" value="-1" selected>`+$('#no-assets-available').text()+`</option>`;
                    }
				$("#source_asset_swap_btcx").append(htmlOptions);
		            $('#select-asset-id-dest').ddslick({
		              onSelected: function(data){
		                displaySelectedData2(data);
		            }
		            });
				}else{
                        
                }
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
 function displaySelectedData2(data){
    console.log('displaySelectedData data is ',data)
    var selectedValue = data.selectedData.value
    //$("#sender_asset").text(assetcode);
   fnUpdateReceiverParamsBTCx(selectedValue)
   dest_Assetcode = selectedValue

  }
function fnUpdateReceiverParamsBTCx(assetCodeVal) {
    var assetcode = assetCodeVal;
    if (assetcode == '') {
        swal.fire('Select Destination Coin');
        return false;
    } else {
       // cryptocoversion();
        $("#spansendcode_swap_btcx").text(assetcode);
       // $("#receiver_asset").val(assetcode);

		if(assetcode==="VESL"||assetcode==="vesl"){
			document.getElementById("source_amount_swap_btcx").style.backgroundImage = "url('./assets/images/crypto/vessel.png')";
        }
        if(assetcode==="PORTE" || assetcode==="porte"){
			document.getElementById("source_amount_swap_btcx").style.backgroundImage = "url('./assets/images/crypto/porte.svg')";
        }
        if(assetcode==="XLM"||assetcode==="xlm"){
			document.getElementById("source_amount_swap_btcx").style.backgroundImage = "url('./assets/images/crypto/xlm.svg')";
        }
        if(assetcode==="USDC"||assetcode==="usdc"){
			document.getElementById("source_amount_swap_btcx").style.backgroundImage = "url('./assets/images/crypto/usdc.png')";
        }
    }
    
}
function fnGetBTCxDetails(){
	$('#post-form input[name="qs"]').val('porte');
	$('#post-form input[name="rules"]').val('get_btcx_details');
	var form = $('#post-form')[0];
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
				var porteCoinList=data.data;	
                var htmlOptions = '';
				$('#destination_asset_swap_btcx').html('');

				if(data.error="false"){
                    if(porteCoinList.length>0){
	
						htmlOptions+=`
                		<select id="select-asset-id" onChange="javascript: fnOnchange(); return false">
                  		<option selected disabled>Select Coin</option>
                  		`;
                  for (i = 0; i < porteCoinList.length; i++) {
                    htmlOptions+=`<option value="BTCx" data-imagesrc="assets/images/crypto/btcx.svg" value="` +
                      porteCoinList[i].assetCode +
                      `">` +
                      porteCoinList[i].assetCode +
                      `</option>`;
                    
                   
                  }
                 
                  htmlOptions+=` </select>`;
                    }else{
                        htmlOptions+=`<div>`+$('#no-assets-available').text()+`</div>`;
                    }
            		$("#destination_asset_swap_btcx").append(htmlOptions);
		            $('#select-asset-id').ddslick({
		              onSelected: function(data){
		                displaySelectedData(data);
		            }
		            });
				}else{
                        
                }
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
 function displaySelectedData(data){
    console.log('displaySelectedData data is ',data)
    var selectedValue = data.selectedData.value
    //$("#sender_asset").text(assetcode);
   fnUpdatesenderparamsBTCx(selectedValue)
   source_Assetcode = selectedValue
  }
function fnUpdatesenderparamsBTCx(assetCodeVal) {
    var assetcode = assetCodeVal;
    if (assetcode == '') {
        swal.fire('Select Destination Coin');
        return false;
    } else {
       // cryptocoversion();
        $("#spanreceivedcode").text(assetcode);
        //$("#receiver_asset").val(assetcode);

			document.getElementById("destination_amount_swap_btcx").style.backgroundImage = "url('./assets/images/crypto/btcx.svg')";
        

    }
    
}
function fnExChangeBTCxWithDigitalAssets(security,hasMnemonic){
		var sourceAssetcode = source_Assetcode;
		var destAssetcode = dest_Assetcode;
		
    $( "#exchange-btcx-coin-form" ).validate( {
        rules: {
            coin_source_asset: {
                required: true
            },
            destination_amount_swap_btcx: {
                required: true
            },
            coin_asset_distination_asset:{
                required: true
            }
        },
        messages: {
            coin_source_asset: {
                required: $('#data-validation-please-select-coin-to-swap').text()
            },
            destination_amount_swap_btcx: {
                required: $('#data-validation-please-enter-amount').text()
            },
            coin_asset_distination_asset:{
                required: $('#data-validation-please-select-destination-coin').text()
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
		

    if($( "#exchange-btcx-coin-form" ).valid()){
		$('#spinner-div').show();//Load button clicked show spinner
        var formData = new FormData($('#exchange-btcx-coin-form')[0]);
        formData.append('qs', 'porte');
        formData.append('rules', 'exchange_btcx_with_porte_coin'); 
        formData.append("hdnlang", $('#lang_def').text());
		formData.append('security',security);
		formData.append('coin_source_asset',sourceAssetcode);
		formData.append('coin_asset_distination_asset',destAssetcode);
		formData.append('hasMnemonic',hasMnemonic);
		
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
                $('#spinner-div').hide();//Request is complete so hide spinner
                var data = JSON.parse(result);
                // console.log('data ',data);
                if(data.error=='false'){
                    Swal.fire({
                        title: $('#transfer-complete-label').text(),
                        text: data.message,
					  	imageUrl: 'assets/images/crypto/success.svg',
					  	imageWidth: 400,
					  	imageHeight: 200,
					  	imageAlt: 'Custom image',
                    }).then(function() {
                        $('#post-form').attr('action', 'ws');
                        $('input[name="qs"]').val('btcx');
                        $('input[name="rules"]').val('view_pending_btcx_swapping_txn');
                        $("#post-form").submit();
                    });	
                }else{
                    Swal.fire({
                        text: data.message,
						imageUrl: 'assets/images/crypto/error.svg',
						imageWidth: 400,
						imageHeight: 200,
						imageAlt: 'Custom image',
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
}



$( document ).ready(function() {
    fnGetCoinDetailsTransferBTC();
    fnGetSourceCoinDetailBTCSwap();
    fnGetBTCxDetailsSwapBTC();
    $("#destination_amount_swap_btc").keyup(function() {
        
    cryptocoversionFromSourceBTCSwap();
});
    fnGetSourceCoinDetail();
    fnGetBTCxDetails();
    $("#destination_amount_swap_btcx").keyup(function() {
        cryptocoversionFromSourceSWapBTX();
  });
});





function cryptocoversionFromSourceSWapBTX(){
/*  var sourceAssetcode = $('input[name="coin_source_asset"]:checked').val();
  var destAssetcode = $('input[name="coin_asset_distination_asset"]:checked').val();
*/
	var sourceAssetcode = source_Assetcode;
	var destAssetcode = dest_Assetcode;
  $("#spansendcode_swap_btcx").text(sourceAssetcode);
  var amount = $("#destination_amount_swap_btcx").val();

    console.log('sourceAssetcode ',sourceAssetcode);
  console.log('destAssetcode ',destAssetcode);
  console.log('amount ',amount);  

  var formData = new FormData($('#sell-porte-coin-form')[0]);//sell-porte-coin-form
  formData.append('qs', 'btcx');
  formData.append('rules', 'get_exchange_rate_with_markeup'); 
  formData.append('destination_amount', amount); 
  formData.append('source_asset', sourceAssetcode); 
  formData.append('destination_asset', destAssetcode); 
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
            $("#source_amount_swap_btcx").val(data.data);
            $("#offers_not_available_text").addClass('hidden');

          }else{
             console.log('Error',data.message)
			$("#offers_not_available_text").removeClass('hidden');
          }
      },
      error: function() {
        // console.log('Problem with connection')
      }
  });  
}


function checkIfUserHasMneonicCode(){
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
                    fnPassword(data.hasmnemonic);
                }else{
                    fnPrivateKey(data.hasmnemonic)
                }
               
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

function fnPassword(hasMnemonic){
	const { value: password   } = Swal.fire({
		title: 'Enter your Password',
		input: 'password',
		inputLabel: 'Password',
		showCancelButton: true,
		inputAttributes: {
		autocapitalize: 'off',
		autocorrect: 'off'
		},
		inputValidator: (value) => {
		if (!value) {
			return 'Please input your password!';
		}
		}
	}).then((result) => {
			if (result.value) {
				var passwordVal = result.value;
				fnExChangeBTCxWithDigitalAssets (passwordVal,hasMnemonic);
			}
		});
}

function fnPrivateKey(hasMnemonic){
	const { value: password   } = Swal.fire({
		title: 'Enter your Private Key',
		input: 'password',
		inputLabel: 'Password',
		showCancelButton: true,
		inputAttributes: {
			autocapitalize: 'off',
			autocorrect: 'off'
		},
		inputValidator: (value) => {
		if (!value) {
			return 'Please input your Private Key!';
		}
		}
	}).then((result) => {
			if (result.value) {
				var privatekey = result.value;
				fnExChangeBTCxWithDigitalAssets (privatekey,hasMnemonic);
			}
		});
}

function fnCallPendingSwapBTCXPage(){
    $('#post-form').attr('action', 'ws');
    $('input[name="qs"]').val('btcx');
    $('input[name="rules"]').val('view_pending_btcx_swapping_txn');
    $("#post-form").submit();	
}




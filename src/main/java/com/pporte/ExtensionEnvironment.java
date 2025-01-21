package com.pporte;


public class ExtensionEnvironment  {
	// # System params and Environment defined params
	public static  boolean getDebugFlag() throws Exception{ 			return Boolean.parseBoolean(NeoBankEnvironment.getParameters("debugOn")); }	
	public static  String getXLMFundAccountAmount() throws Exception { return NeoBankEnvironment.XLM_FUND_ACCOUNT_AMOUNT; }
    public static  String getXLMFundAccThreshold() throws Exception { return NeoBankEnvironment.XLM_FUND_ACCOUNT_THRESHOLD; }
    public static  String getUSDCurrencyId() throws Exception { return NeoBankEnvironment.CURRENCY_USD; }
    public static  String getAUDCurrencyId() throws Exception { return NeoBankEnvironment.CURRENCY_AUD; }
    public static  String getHKDCurrencyId() throws Exception { return NeoBankEnvironment.CURRENCY_HKD; }
    public static  String getXLMCode() throws Exception { return NeoBankEnvironment.STELLARLUMENS; }
    public static  String getVesselCoinCode() throws Exception { return NeoBankEnvironment.VESSEL_COIN_CODE; }
    public static  String getPorteTokenCode() throws Exception { return NeoBankEnvironment.PORTE_TOKEN_CODE; }
    public static  String getUSDCCode() throws Exception { return NeoBankEnvironment.USDC_CODE; }
    public static  String getStellarBTCxCode() throws Exception { return NeoBankEnvironment.STELLAR_BITCOIN_CODE; }
    public static  String getDefaultCustomerUserType() throws Exception { return NeoBankEnvironment.DEFAULT_CUSTMOMER_USERTYPE; }
    public static  String getDefaultMerchantUserType() throws Exception { return NeoBankEnvironment.DEFAULT_MERCHANT_USERTYPE; }
    public static String getInitialWalletBalanceOfPorteUtilityCoin() throws Exception{	return NeoBankEnvironment.PORTE_TOKEN_INITIAL_BALANCE;}
    public static  String getBitcoinCode() throws Exception { return NeoBankEnvironment.BITCOIN_CODE; }
	public static  String getEthereumCode() throws Exception { return NeoBankEnvironment.ETHEREUM_CODE; }
	public static  String geLitecoinCode() throws Exception { return NeoBankEnvironment.LITECOIN_CODE; }
	public static  String getBussinessLedgerThreshold() throws Exception { return NeoBankEnvironment.BUSSINESS_LEDGER_THRESHOLD; }
	public static  String getDefaultPlanReason() throws Exception { return NeoBankEnvironment.DEFAULT_PLAN_ALLOCATION_REASON_AFTER_OPS_APPROVAL; }
	public static  String getPlanReasonAfterPurchasebyCustomer() throws Exception { return NeoBankEnvironment.PLAN_ALLOCATION_REASON_AFTER_PLAN_PURCHASE_BY_CUSTOMER; }

	public static  String getFileUploadPath() throws Exception{ 			return NeoBankEnvironment.FILE_UPLOAD_PATH; }													
	public static  String getFileDownloadPath() throws Exception{ 			return NeoBankEnvironment.FILE_DOWNLOAD_PATH; }
	public static  String getEmailTemplatePath() throws Exception{ 			return NeoBankEnvironment.EMAIL_TEMPLATE_PATH; }
	public static  String getTemporaryFilePath() throws Exception{ 			return NeoBankEnvironment.TEMP_FILE_PATH; }
	
	public static  String getDBUser() throws Exception{ 					return NeoBankEnvironment.getParameters("DBUSER"); }														
	public static  String getDBPwd() throws Exception{ 					    return NeoBankEnvironment.getParameters("DBPWD"); }
	public static  String getMYSQLDriver() throws Exception{ 				return NeoBankEnvironment.getParameters("MYSQL_DRIVER"); }						
	public static  String getPostGreSQLDriver() throws Exception{ 			return NeoBankEnvironment.getParameters("POSTGRESQL_DRIVER"); }
	public static  String getKeyValue() throws Exception{					return NeoBankEnvironment.KEYVALUE; }															
	public static  String getDBURL() throws Exception{ 					    return NeoBankEnvironment.getParameters("DB_URL"); }
	public static  String getLocalDateFormat() throws Exception{ 			return NeoBankEnvironment.getParameters("LOCAL_DATEFORMAT"); }
	// Email Settings
	public static  String getSMTPUserId() throws Exception{ 				return NeoBankEnvironment.getParameters("stmpuserid"); }						
	public static  String getSMTPUserPwd() throws Exception{ 				return NeoBankEnvironment.getParameters("smtppwd"); }						
	public static  String getSMTPHost() throws Exception{ 					return NeoBankEnvironment.getParameters("smtphost"); }			
	public static  String getSMTPTLSPort() throws Exception{ 				return NeoBankEnvironment.getParameters("smtptlsport"); }
	public static  String getSMTPSSLPort() throws Exception{ 				return NeoBankEnvironment.getParameters("smtpsslport"); }				
	public static  String getSendFromEmailId() throws Exception{ 			return NeoBankEnvironment.getParameters("smtpsendfromemail"); }						
	public static  String getEmailThreadCount() throws Exception{ 			return NeoBankEnvironment.getParameters("EMAIL_THREADS_COUNT"); }
		
	
	//Blockchain
	public static  String getBlockChainInsert() throws Exception{ 			return NeoBankEnvironment.getParameters("BLOCKCHAIN_INSERT"); }
	public static  String getBlockChainView() throws Exception{ 			return NeoBankEnvironment.getParameters("BLOCKCHAIN_VIEW"); }
	public static  String getWalletLedgerChainMultiChainUser() throws Exception{ 			return NeoBankEnvironment.getParameters("WALLETLEDGERCHAIN_MULTICHAINUSER"); }
	public static  String getCardVaultChainMultiChainUser() throws Exception{ 			return NeoBankEnvironment.getParameters("CARDVAULTCHAIN_MULTICHAINUSER"); }
	public static  String getWalletLedgerChainRPCAuthKey() throws Exception{ 				return NeoBankEnvironment.getParameters("WALLETLEDGERCHAIN_MUTIRPCKEY"); }
	public static  String getCardVaultChainRPCAuthKey() throws Exception{ 				return NeoBankEnvironment.getParameters("CARDVAULTCHAIN_MUTIRPCKEY"); }
	public static  String getMultiChainWalletLedgerChainRPCURLPORT() throws Exception{ 		return NeoBankEnvironment.getParameters("WALLETLEDGERCHAIN_MULTIURLPORT"); }	
	public static  String getMultiChainCardVaultChainRPCURLPORT() throws Exception{ 		return NeoBankEnvironment.getParameters("CARDVAULTCHAIN_MULTIURLPORT"); }	
	public static  String getMultiChainWalletLedgerChainRPCIP() throws Exception{ 			return NeoBankEnvironment.getParameters("WALLETLEDGERCHAIN_MULTIIP"); }	
	public static  String getMultiChainCardVaultChainRPCIP() throws Exception{ 			return NeoBankEnvironment.getParameters("CARDVAULTCHAIN_MULTIIP"); }	
	public static  String getMultiChainWalletLedgerChainRPCPort() throws Exception{ 			return NeoBankEnvironment.getParameters("WALLETLEDGERCHAIN_MULTIPORT"); }
	public static  String getMultiChainCardVaultChainRPCPort() throws Exception{ 			return NeoBankEnvironment.getParameters("CARDVAULTCHAIN_MULTIPORT"); }
	public static  String getWalletLedgerBlockChainName() throws Exception{ 			return NeoBankEnvironment.getParameters("WALLET_LEDGER_CHAIN_NAME"); }
	public static  String getCardVaultBlockChainName() throws Exception{ 			return NeoBankEnvironment.getParameters("CARD_VAULT_CHAIN_NAME"); }
	public static String getBlockChainCardVaultStreamName() throws Exception{ 				return NeoBankEnvironment.getParameters("CARD_VAULT_STREAM_NAME"); }
	public static String getBlockChainCustomerWalletStreamName() throws Exception{		return NeoBankEnvironment.getParameters("CUSTOMER_WALLET_STREAM_NAME"); }
	public static String getBlockChainMerchantWalletStreamName() throws Exception{		return NeoBankEnvironment.getParameters("MERCHANT_WALLET_STREAM_NAME"); }

    public  static  String getServletPath() throws Exception{     			return NeoBankEnvironment.getParameters("SERVLET_PATH"); }	
    public  static  String getMutipartServletPath() throws Exception{     	return NeoBankEnvironment.getParameters("MULTIPARTSERVLET_PATH"); }
    public  static  String getJSONServletPath() throws Exception{     		return NeoBankEnvironment.getParameters("JSON_SERVLET_PATH"); }
	public static  String getAPIKeyPublic() throws Exception{ 				return NeoBankEnvironment.getParameters("APIKEYPUB"); }	
	public static  String getAPIKeyPrivate() throws Exception{ 				return NeoBankEnvironment.getParameters("APIKEYPVT"); }
	public static  String getFDocServerURL() throws Exception{ 				return NeoBankEnvironment.getParameters("DOCUMENT_MANAGER_SERVER_URL"); }
    
    public static  String getErrorPage() throws Exception{     				return NeoBankEnvironment.getParameters("ERROR_PAGE"); }
	public static  String getLoginPage() throws Exception{     				return NeoBankEnvironment.getParameters("LOGIN_PAGE"); }
	public static String getUserDashboardPage() throws Exception {			return NeoBankEnvironment.getParameters("DASHBOARD_PAGE");	}
	public static String getManageAssetPage() throws Exception{				return NeoBankEnvironment.getParameters("MANAGE_ASSET_PAGE");	}
	public static String getDocUploadPage() throws Exception{				return NeoBankEnvironment.getParameters("DOC_UPLOAD_PAGE");	}
	public static String getListAllDocPage() throws Exception{				return NeoBankEnvironment.getParameters("DOC_ALLDOC_PAGE");	}
	public static String getManageUsersPage() throws Exception {			return NeoBankEnvironment.getParameters("MANAGE_USERS_PAGE");	}
	public static String getUserProfilePage() throws Exception{				return NeoBankEnvironment.getParameters("PROFILE_PAGE");	}
	public static String getOpsNewUsersPage() throws Exception{				return NeoBankEnvironment.getParameters("NEWUSERS_PAGE"); }
	public static String getMerchantRegistrationPage() throws Exception{				return NeoBankEnvironment.getParameters("MERCHANT_REGISTRATION_PAGE"); }
	public static String getCustomerRegistrationPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_REGISTRATION_PAGE"); }
	
	// Customer Pages
	public static String getCustomerDashboadPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_DASHBOARD_PAGE"); }
	public static String getCustomerProfilePage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_PROFILE_PAGE"); }
	public static String getCustomerViewWalletPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_VIEW_WALLET_PAGE"); }
	public static String getCustomerCreateWalletPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_CREATE_WALLET_PAGE"); }
	public static String getCustomerTopupWalletPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_TOPUP_WALLET_PAGE"); }
	public static String getCustomerRegisterCardPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_REGISTER_CARD_PAGE"); }
	public static String getCustomerShowAllCards() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_SHOW_ALL_CARDS_PAGE"); }
	public static String getCustomerViewDisputePage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_VIEW_DISPUTE_PAGE"); }
	public static String getCustomerViewSpecificDisputePage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_VIEW_SPECIFIC_DISPUTE_PAGE"); }
	public static String getCustomerRaiseDisputePage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_RAISE_DISPUTE_PAGE"); }
	public static String getCustomerWalletPayAnyonePage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_PAY_ANYONE_PAGE"); }
	public static String getCustomerWalletCashTransactionsPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_CASH_TRASACTION_PAGE"); }
	public static String getCustomerShpwtPorteCoinsPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_SHOW_PORTE_COINS_PAGE"); }
	public static String getCustomerPorteBuyCoinsPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_BUY_PORTE_COINS_PAGE"); }
	public static String getCustomerPorteDisplayTransactions() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_PORTE_DISPLAY_TRANSACTION_PAGE"); }
	public static String getCustomerPorteTransferCoin() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_PORTE_TRANSFER_COIN_PAGE"); }
	public static String getCustomerSellPorteCoin() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_SELL_PORTE_COIN_PAGE"); }
	public static String getCustomerCryptoRegCoinPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_CTYPTO_REG_COIN_PAGE"); }
	public static String getCustomerCryptoViewCoinPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_CTYPTO_VIEW_COIN_PAGE"); }
	public static String getCustomerCryptoBuyCoinPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_CTYPTO_BUY_COIN_PAGE"); }
	public static String getCustomerCryptoSellCoinPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_CTYPTO_SELL_COIN_PAGE"); }
	public static String getCustomerCryptoPayAnyonePage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_CTYPTO_PAY_ANYONE_PAGE"); }
	public static String getCustomerCryptoViewTransPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_CTYPTO_VIEW_TRANS_PAGE"); }
	public static String getCustomerLoyaltyViewPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_LOYALTY_VIEW_PAGE"); }
	public static String getCustomerRegisterReceiver() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_REGISTER_RECEIVER_PAGE"); }
	public static String getCustomerBuyPendingTransactionPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_PENDING_TRANSACTION_PAGE"); }
	public static String getCustomerRegisterStellarAccountPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_REGISTER_STELLAR_ACCOUNT_PAGE"); }
	public static String getCustomerRegisterDigitalCurrencyPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_REGISTER_DIGITAL_CURRENCY_PAGE"); }
	public static String getCustomerExchangeDigitalCurrencyPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_EXCHANGE_DIGITAL_CURRENCY_PAGE"); }
	public static String getCustomerLoyaltyRedeemPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_LOYALTY_REDEEM_PAGE"); }
	public static String getCustomerPendingCurrencyTradingPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_PENDING_CURRENCY_TRADING_PAGE"); }
	public static String getCustomerBuyNewPlanPage() throws Exception{	return NeoBankEnvironment.getParameters("CUSTOMER_BUY_NEW_PLAN_PAGE");	}
	public static String getCustomerConfirmBuyPlanPage() throws Exception {	return NeoBankEnvironment.getParameters("CUSTOMER_CONFIRM_BUY_PLAN_PAGE");		}
	public static String getOffRampPorteCoinPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_OFFRAMP_PORTE_COIN_PAGE"); }
	public static String getCustomerViewClaimableBalancesPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_VIEW_CLAIMABLE_BALANCE_PAGE"); }
	public static String getCustomerBuyBTCxPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_BUY_BTCX_PAGE"); }
	public static String getCustomerViewBTCxRequest() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_VIEW_PENDING_BTCX_PAGE"); }
	public static String getCustomerViewBTCxSwapRequest() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_VIEW_PENDING_BTCX_EXCHANGE_PAGE"); }
	public static String getCustomerExchangeBTCxPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_EXCHANGE_BTCX_PAGE"); }
	public static String getCustomerSetPasswordPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_SET_PASSWORD_PAGE"); }
	public static String getBitcoinP2PTransferPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_P2P_TRANSFER_BITCOIN"); }
	public static String getCustomerCreateBitcoinAcPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_CREATE_BITCOIN_ACCOUNT"); }
	public static String getCustomerBuyBTCPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_BUY_BTC_PAGE"); }
	public static String getCustomerViewBTCRequest() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_VIEW_PENDING_BTC_PAGE"); }
	public static String getCustomerTransferBitCoinPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_TRANSFER_BITCOIN_PAGE"); }
	public static String getCustomerViewBTCTransactionsPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_VIEW_BTC_TXN_PAGE"); }
	public static String getCustomerSwapBTCToBTCxPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_SWAP_BTC_TO_BTCX_PAGE"); }
	public static String getCustomerViewBTCToBTCxSwapTxnPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_VIEW_BTC_TO_BTCX_SWAP_PAGE"); }
	public static String getCustomerBitcoinPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_BITCOIN_PAGE"); }
	public static String getCustomerFiatRemittancePage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_FIAT_REMITTANCE_PAGE"); }
	public static String getCustomerSuccessfulRegistrationPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_SUCCESSFUL_REGISTRATION_PAGE"); }
	public static String getCustomerCreateAssetWaitingPage() throws Exception{				return NeoBankEnvironment.getParameters("CUSTOMER_CREATE_ASSET_WAITING_PAGE"); }
	public static String getPartnerCreateAssetWaitingPage() throws Exception{				return NeoBankEnvironment.getParameters("PARTNER_CREATE_ASSET_WAITING_PAGE"); }

	//Ops Pages
	public static String getOPSLoginPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_LOGIN_PAGE"); }
	public static String getOperationsDashboardPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_DASHBOARD_PAGE"); }
	public static String getOpsErrorPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_ERROR_PAGE"); }
	public static String getOpsPendingMerchantPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_PENDING_MERCHANT_PAGE"); }
	public static String getOpsEditSpecificMerchanPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_EDIT_SPECIFIC_MERCHANT_PAGE"); }
	public static String getOpsMerchantRiskProfilePage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_MERCHANT_RISK_PROFILE_PAGE"); }
	public static String getOpsMerchanMCCPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_MERCHANT_MCC_GROUP_PAGE"); }
	public static String getOpsAllMerchantPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_ALL_MERCHANT_PAGE"); }
	public static String getOpsPendingCustomerPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_CUSTOMER_PENDING_PAGE"); }
	public static String getOpsEditSpecificCustomerPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_CUSTOMER_EDIT_CUSTOMER_PAGE"); }
	public static String getOpsAllCustomerDetailsPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_CUSTOMER_ALL_CUSTOMER_PAGE"); }
	public static String getOpsSetPricingPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_SETPRICING_PAGE"); }
	public static String getOpsTransactionLimitsPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_TRANSACTION_LIMITS_PAGE"); }
	public static String getOpsViewLoyaltyPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_LOYALTY_PAGE"); }
	public static String getOpsViewCustomerLoyaltyPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_CUSTOMER_LOYALTY_PAGE"); }
	public static String getOpsViewMerchantPlanPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_MERCHANT_PLAN_PAGE"); }
	public static String getOpsSysProfilePage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_PROFILE_PAGE"); }
	public static String getOpsManageUsersPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_MANAGE_USERS_PAGE"); }
	public static String getOpsCustViewWalletPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_CUST_VIEW_WALLET_PAGE"); }
	public static String getOpsAllDisputeReasonsPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_DISPUTE_REASON_PAGE"); }
	public static String getViewCustomerDisputePageOps() throws Exception{		return NeoBankEnvironment.getParameters("OPS_SHOW_CUSTOMERS_DISPUTES_PAGE"); }
	public static String getOpsSpecificCustomerDisputePage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_SHOW_CUSTOMER_SPECIFIC_DISPUTES_PAGE"); }
	public static String getViewMerchantDisputePageOps() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_MERCHANT_DISPUTES_PAGE");} 
	public static String getOpsRaiseDisputePage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_RAISE_DISPUTES_PAGE"); }
	public static String getOpsViewTransactionRulePage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_TRANSACTION_RULE_PAGE"); }
	public static String getSlabRatesForSpecificPlan() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_SLAB_RATE_FORPLAN_PAGE"); }
	
	public static String getOpsViewWalletsPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_WALLETS_PAGE"); }
	public static String getOpsViewSpecificCustomerWalletsPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_SPECIFIC_CUSTOMER_WALLETS_PAGE"); }
	public static String getOpsVieCustomerTokenizedCardsPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_CUSTOMER_TOKENIZED_PAGE"); }
	public static String getOpsViewSpecificCustomerTokenizedCardsPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_SPECIFIC_CUSTOMER_TOKENIZED_PAGE"); }
	public static String getOpsViewWalletAssetsPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_WALLET_ASSETS_PAGE"); }
	public static String getOpsFiatWalletTransactionsPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_FIAT_WALLET_TRANSACTION_PAGE"); }
	public static String getOptWalsCryptoletTransactionsPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_CRYPTO_WALLET_TRANSACTION_PAGE"); }
	public static String getOptWalsCardTransactionsPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_CARD_WALLET_TRANSACTION_PAGE"); }
	public static String getOpsViewCardsPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_CARDS_PAGE"); }
	public static String getOpsViewPorteRequestPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_PORTE_REQUEST_PAGE"); }
	public static String getOpsGetPorteTransactions() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_PORTE_TRANSACTIONS_PAGE"); }
	public static String getOpsViewSpecificUserPortTxn() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_SPECIFIC_USER_PORTE_TXN_PAGE"); }
	public static String getOpsViewPorteWalletsPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_PORTE_WALLETS_PAGE"); }
	public static String getOpsViewSpecificUserPortWal() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_SPECIFIC_USER_PORTE_WAL_PAGE"); }
		
	public static String getOpsBlockCodePage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_BLOCK_CODE_PAGE"); }
	public static String getFundRegisteredAccountsPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_FUND_REGISTERED_ACCOUNTS_PAGE"); }
	public static String getCreateOffersPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_CREATE_OFFERS_PAGE"); }
	public static String getViewadEditOffersPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_EDIT_PAGE"); }
	public static String getFundedAccountsAccountsPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_ALL_FUNDED_ACCOUNTS"); }
	public static String getOpsBlockWalletsPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_BLOCK_WALLETS_PAGE"); }
	
	public static String getOpsBusinessLedgerPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_BUSINESS_LEDGER_PAGE"); }
	public static String getOpsSystemAuditTrailPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_SYSTEM_AUDIT_TRAIL_PAGE"); }
	public static String getOpsDistributionPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_DISTRIBUTION_PAGE"); }
	public static String getOpsLiquidityAccountPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_LIQUIDITY_ACCOUNT_PAGE"); }
	public static String getOpsViewBlockchainTransactionsPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_BLOCKCHAIN_TRANSACTIONS_PAGE"); }
	public static String getOpsAuthorizedWalletTransactionsPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_AUTHORIZED_WALLET_TRANSACTION_PAGE"); }

	public static String getOpsViewManageFunctionsPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_MANAGE_FUNCTIONS_PAGE");	}
	public static String getOpsManagePricingPlansPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_MANAGE_PRICING_PLAN_PAGE");		}
	public static String getOpsAllocateRulestoPlansPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_RULES_TO_PLAN_ALLOCATE_PAGE");	}
	public static String getOpsSetPasswordPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_SET_PASSWORD_PAGE");	}
	//Remittance Module ops
	public static String getOpsViewPartnersPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_PARTNERS_PAGE"); }
	
	public static String getOpsPricingPlansAllocatePage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_PRICING_PLAN_ALLOCATE_PAGE");	}
	public static String getOpsCreateClaimableBalancePage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_CREATE_CLAIMABLE_BALANCE_PAGE");	}
	public static String getRemittanceTransactionPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_REMITTANCE_TRANSACTION_PAGE");	}
	
	//iteration five
	public static String getOpsWalletAssetAccountsPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_WALLET_ASSET_ACCOUNTS_PAGE");	}
	
	//Partners module
	public static String getPartnersDashboardPage() throws Exception{		return NeoBankEnvironment.getParameters("PART_DASHBOARD_PAGE"); }
	public static String getPartnersProfilePage() throws Exception{		return NeoBankEnvironment.getParameters("PART_PARTNERS_PROFILE_PAGE"); }
	public static String getPartnersPendingTransactionPage() throws Exception{		return NeoBankEnvironment.getParameters("PART_PARTNERS_PENDING_TRANSACTION_PAGE"); }
	public static String getPartnersCompleteTransactionPage() throws Exception{		return NeoBankEnvironment.getParameters("PART_PARTNERS_COMPLETE_TRANSACTION_PAGE"); }
	public static String getPartnersViewIssuesPage() throws Exception{		return NeoBankEnvironment.getParameters("PART_PARTNERS_VIEW_ISSUERS_PAGE"); }
	public static String getPartnerSetPasswordPage() throws Exception{		return NeoBankEnvironment.getParameters("PART_PARTNERS_SET_PASSWORD_PAGE"); }
	public static String getCreateTrustlinePage() throws Exception{		return NeoBankEnvironment.getParameters("PART_CREATE_TRUSTLINE_PAGE"); }
	
	// TDA Module
	public static String getTDADashboardPage() throws Exception { return NeoBankEnvironment.getParameters("TDA_DASHBOARD_PAGE");}
	public static String getTDAAccountConfigurationPage() throws Exception { return NeoBankEnvironment.getParameters("TDA_ACCOUNT_CONFIGURATION_PAGE");}
	public static String getTDAFiatToBTCxPricingPage() throws Exception { return NeoBankEnvironment.getParameters("TDA_FIAT_TO_BTCx_PRICING_PAGE");}
	public static String getTDAPorteAssetToBTCxPricingPage() throws Exception { return NeoBankEnvironment.getParameters("TDA_PORTE_ASSETS_TO_BTCX_PRICING_PAGE");}
	public static String getTDARequestFromFiatPage() throws Exception { return NeoBankEnvironment.getParameters("TDA_REQUEST_FROM_FIAT_PAGE");}
	public static String getTDARequestFromPorteAssetsPage() throws Exception { return NeoBankEnvironment.getParameters("TDA_REQUEST_FROM_PORTE_ASSETS_PAGE");}
	public static String getTDAPurchaseBtcxPage() throws Exception { return NeoBankEnvironment.getParameters("TDA_PURCHASE_BTCX_PAGE");}
	public static String getTDADisplayStellarTransactionsPage() throws Exception { return NeoBankEnvironment.getParameters("TDA_DISPLAY_STELLAR_TRANSACTION_PAGE");}
	public static String getTDABTCAccountInformationPage() throws Exception { return NeoBankEnvironment.getParameters("TDA_BTC_ACCOUNT_INFORMATION_PAGE");}
	public static String getTDAFiatToBTCPricingPage() throws Exception { return NeoBankEnvironment.getParameters("TDA_FIAT_TO_BTC_PRICING_PAGE");}
	public static String getTDABitcoinToStellarBitcoinPricingPage() throws Exception { return NeoBankEnvironment.getParameters("TDA_BITCOIN_TO_STELLAR_BITCOIN_PRICING_PAGE");}
	public static String getTDAFiatToBTCRequestPage() throws Exception { return NeoBankEnvironment.getParameters("TDA_FIAT_TO_BTC_REQUEST_PAGE");}
	public static String getTDABitcoinToStellarBitcoinRequestPage() throws Exception { return NeoBankEnvironment.getParameters("TDA_BITCOIN_TO_STELLAR_BITCOIN_REQUEST_PAGE");}
	public static String getTDADisplayBitcoinTransactionPage() throws Exception { return NeoBankEnvironment.getParameters("TDA_DISPLAY_BTC_TRANSACTIONS_PAGE");}
	public static String getTDADisplayFiatRemittanceTxnPage() throws Exception { return NeoBankEnvironment.getParameters("TDA_DISPLAY_FIAT_REMITTANCE_TRANSACTIONS_PAGE");}
	public static String getTDAFundAccountsPage() throws Exception { return NeoBankEnvironment.getParameters("TDA_FUND_ACCOUNTS_PAGE");}
	
	// Merchant Pages
	public static String getMerchantDashboardPage() throws Exception{				return NeoBankEnvironment.getParameters("MERCHANT_DASHBOARD_PAGE"); }
	public static String getMerchantProfilePage() throws Exception{				return NeoBankEnvironment.getParameters("MERCHANT_PROFILE_PAGE"); }
	public static String getMerchantManageBranchesPage() throws Exception{				return NeoBankEnvironment.getParameters("MERCHANT_MANAGE_BRANCHES_PAGE"); }
	public static String getMerchantManageUsersPage() throws Exception{				return NeoBankEnvironment.getParameters("MERCHANT_MANAGE_USERS_PAGE"); }
	public static String getMerchantAddBranchPage() throws Exception{				return NeoBankEnvironment.getParameters("MERCHANT_ADD_BRANCH_PAGE"); }
	public static String getMerchantStorePaymentPage() throws Exception{				return NeoBankEnvironment.getParameters("MERCHANT_STORE_PAYMENTS_PAGE"); }
	public static String getMerchantCashOutPage() throws Exception{				return NeoBankEnvironment.getParameters("MERCHANT_CASH_OUT_PAGE"); }
	public static String getMerchantTopUpPage() throws Exception{				return NeoBankEnvironment.getParameters("MERCHANT_TOP_UP_PAGE"); }
	public static String getMerchantRaiseDisputePage() throws Exception{				return NeoBankEnvironment.getParameters("MERCHANT_RAISE_DISPUTE_PAGE"); }
	public static String getMerchantViewDisputePage() throws Exception{				return NeoBankEnvironment.getParameters("MERCHANT_VIEW_DISPUTE_PAGE"); }
	public static String getMerchantViewSpecificDisputePage() throws Exception{				return NeoBankEnvironment.getParameters("MERCHANT_VIEW_SPECIFIC_DISPUTE_PAGE"); }
	public static String getMerchantTransctionHistoryPagePage() throws Exception{				return NeoBankEnvironment.getParameters("MERCHANT_TRANSCTION_HISTORY_PAGE"); }
	
	//TRANSACTION RULES-- 
	public static String getTokenRegistrationCodeForCustomer() throws Exception{				return NeoBankEnvironment.getParameters("CODE_TOKEN_REGISTRATION_CARD_CUSTOMER"); }
	public static String getCodeTokenWalletTopup() throws Exception{				return NeoBankEnvironment.getParameters("CODE_TOKEN_WALLET_TOPUP"); }
	public static String getAmountForTokenRegistration() throws Exception{				return NeoBankEnvironment.TOKEN_REGISTRATION_AMOUNT ;}
	public static String getCodeBitCoinP2P() throws Exception{				return NeoBankEnvironment.getParameters("CODE_BITCOIN_WALLET_P2P"); }
	public static String getCodeEthereumCoinP2P() throws Exception{				return NeoBankEnvironment.getParameters("CODE_ETHEREUM_WALLET_P2P"); }
	public static String getCodeLitecoinCoinP2P() throws Exception{				return NeoBankEnvironment.getParameters("CODE_LITECOIN_WALLET_P2P"); }
	public static String getCodeBuyBitCoin() throws Exception{				return NeoBankEnvironment.getParameters("CODE_BUY_BITCOIN"); }
	public static String getCodeBuyEthereumCoin() throws Exception{				return NeoBankEnvironment.getParameters("CODE_BUY_ETHEREUM"); }
	public static String getCodeBuyLitecoinCoin() throws Exception{				return NeoBankEnvironment.getParameters("CODE_BUY_LITECOIN"); }
	public static String getCodeSellBitCoin() throws Exception{				return NeoBankEnvironment.getParameters("CODE_SELL_BITCOIN"); }
	public static String getCodeSellEthereumCoin() throws Exception{				return NeoBankEnvironment.getParameters("CODE_SELL_ETHEREUM"); }
	public static String getCodeSellLitecoinCoin() throws Exception{				return NeoBankEnvironment.getParameters("CODE_SELL_LITECOIN"); }
	public static String getCodeExchangePorteAssetForBTCx() throws Exception{				return NeoBankEnvironment.getParameters("CODE_EXCHANGE_PORTE_ASSETS_FOR_BTCX"); }
	//
	public static String getCodeFiatWalletP2P() throws Exception{				return NeoBankEnvironment.getParameters("CODE_FIAT_WALLET_P2P"); }
	public static String getCodePorteUtilityCoinP2P() throws Exception{				return NeoBankEnvironment.getParameters("CODE_PORTE_UTILITY_COIN_P2P"); }
	public static String getCodeVesselCoinP2P() throws Exception{				return NeoBankEnvironment.getParameters("CODE_VESSEL_COIN_P2P"); }
	//Buy Assets Via Token
	public static String getCodeBuyPorteCoinViaToken() throws Exception{				return NeoBankEnvironment.getParameters("CODE_BUY_PORTE_COIN_VIA_TOKEN"); }
	public static String getCodeBuyVesselCoinViaToken() throws Exception{				return NeoBankEnvironment.getParameters("CODE_BUY_VESSEL_COIN_VIA_TOKEN"); }
	public static String getCodeBuyXLMViaToken() throws Exception{				return NeoBankEnvironment.getParameters("CODE_BUY_XLM_VIA_TOKEN"); }
	public static String getCodeBuyUSDCViaToken() throws Exception{				return NeoBankEnvironment.getParameters("CODE_BUY_USDC_VIA_TOKEN"); }
	public static String getCodeBuyBTCxViaToken() throws Exception{				return NeoBankEnvironment.getParameters("CODE_BUY_BTCX_VIA_TOKEN"); }
	public static String getCodeBuyBTCxViaFiat() throws Exception{				return NeoBankEnvironment.getParameters("CODE_BUY_BTCX_VIA_FIAT"); }
	
	public static String getCodeBuyBTCViaToken() throws Exception{				return NeoBankEnvironment.getParameters("CODE_BUY_BTC_VIA_TOKEN"); }
	public static String getCodeBuyBTCViaFiatWallet() throws Exception{				return NeoBankEnvironment.getParameters("CODE_BUY_BTC_VIA_FIAT_WALLET"); }
	//Buy Assets Via Wallet
	public static String getCodeBuyPorteCoinViaFiatWallet() throws Exception{				return NeoBankEnvironment.getParameters("CODE_BUY_PORTE_COIN_VIA_FIAT_WALLET"); }
	public static String getCodeBuyVesselCoinViaFiatWallet() throws Exception{				return NeoBankEnvironment.getParameters("CODE_BUY_VESSEL_COIN_VIA_FIAT_WALLET"); }
	public static String getCodeBuyXLMViaFiatWallet() throws Exception{				return NeoBankEnvironment.getParameters("CODE_BUY_XLM_VIA_FIAT_WALLET"); }
	public static String getCodeBuyUSDCViaFiatWallet() throws Exception{				return NeoBankEnvironment.getParameters("CODE_BUY_USDC_VIA_FIAT_WALLET"); }
	
	public static String getCodeOfframpPorteCoinToFiatWallet() throws Exception{				return NeoBankEnvironment.getParameters("CODE_OFFRAMP_PORTE_COIN_TO_FIAT_WALLET"); }
	public static String getCodeOfframpVesselCoinToFiatWallet() throws Exception{				return NeoBankEnvironment.getParameters("CODE_OFFRAMP_VESSEL_COIN_TO_FIAT_WALLET"); }
	public static String getCodeOfframpUSDCToFiatWallet() throws Exception{				return NeoBankEnvironment.getParameters("CODE_OFFRAMP_USDC_TO_FIAT_WALLET"); }

	
	public static String getCodeSellVesselCoin() throws Exception{				return NeoBankEnvironment.getParameters("CODE_SELL_VESSEL_COIN"); }
	public static String getCodeSellPorteCoin() throws Exception{				return NeoBankEnvironment.getParameters("CODE_SELL_PORTE_COIN"); }
	public static String getCodeUSDCCoinP2P() throws Exception{				return NeoBankEnvironment.getParameters("CODE_USDC_COIN_P2P"); }
	public static String getCodeXLMCoinP2P() throws Exception{				return NeoBankEnvironment.getParameters("CODE_XLM_COIN_P2P"); }
	// New- TODO- Make sure this new Transaction rule is updated in the database
	public static String getCodeCustomerCurrencyRemittance() throws Exception{				return NeoBankEnvironment.getParameters("CODE_CUSTOMER_CURRENCY_REMITTANCE"); }
	// New- TODO- Make sure this new Transaction rule is updated in the database
	public static String getCodeTDASwapPorteAssetsForBTCx() throws Exception{				return NeoBankEnvironment.getParameters("CODE_TDA_SWAP_PORTE_ASSETS_FOR_BTCX"); }
	public static String getCodeCustomerBuyPlanViaWallet() throws Exception{				return NeoBankEnvironment.getParameters("CODE_CUSTOMER_BUY_PLAN_VIA_WALLET"); }
	public static String getCodeCustomerBuyPlanViaToken() throws Exception{				return NeoBankEnvironment.getParameters("CODE_CUSTOMER_BUY_PLAN_VIA_TOKEN"); }
	public static String getFiatRemittanceTxnCode() throws Exception{				return NeoBankEnvironment.getParameters("CODE_FIAT_REMMITTACE"); }
	
	
	//Stellar
//	public static String getPorteIssuerAccountId() throws Exception{				return NeoBankEnvironment.getParameters("PORTE_ISSUER_ACCOUNT_ID"); }
//	public static String getVesselIssuerAccountId() throws Exception{				return NeoBankEnvironment.getParameters("VESL_ISSUER_ACCOUNT_ID"); }
//	public static String getBTCIssuerAccountId() throws Exception{				return NeoBankEnvironment.getParameters("BTC_ISSUER_ACCOUNT_ID"); }
//	public static String getUSDCIssuerAccountId() throws Exception{				return NeoBankEnvironment.getParameters("USDC_ISSUER_ACCOUNT_ID"); }
//	public static String getPorteDistributorAccountId() throws Exception{				return NeoBankEnvironment.getParameters("PORTE_DISTRIBUTION_ACCOUNT_ID"); }
//	public static String getVesselDistributorAccountId() throws Exception{				return NeoBankEnvironment.getParameters("VESL_DISTRIBUTION_ACCOUNT_ID"); }
//	public static String getUSDCDistributorAccountId() throws Exception{				return NeoBankEnvironment.getParameters("USDC_DISTRIBUTION_ACCOUNT_ID"); }
//	public static String getXLMDistributorAccountId() throws Exception{				return NeoBankEnvironment.getParameters("XLM_DISTRIBUTION_ACCOUNT_ID"); }
//	public static String getPorteLiquidityAccountId() throws Exception{				return NeoBankEnvironment.getParameters("PORTE_LIQUIDITY_ACCOUNT_ID"); }
//	public static String getVesselLiquidityAccountId() throws Exception{				return NeoBankEnvironment.getParameters("VESSEL_LIQUIDITY_ACCOUNT_ID"); }
	public static String getMaxStellarAssetWalletLimit() throws Exception{				return NeoBankEnvironment.getParameters("STELLAR_ASSET_ACCOUNT_LIMIT"); }
	public static String getFriendBootStellarSDKUrl() throws Exception{				return NeoBankEnvironment.getParameters("FRIEND_BOOT_STELLAR_URL"); }
	public static String getStellarTestEviromentUrl() throws Exception{				return NeoBankEnvironment.getParameters("STELLAR_TEST_URL"); }
	public static String getPorteAccountLimit() throws Exception{				return NeoBankEnvironment.getParameters("PORTE_ACCOUNT_LIMIT"); }
	public static String getFundPorteAccountAmount() throws Exception{				return NeoBankEnvironment.getParameters("FUND_PORTE_ACCOUNT_AMOUNT"); }
	public static String getVesselAccountId() throws Exception{				return NeoBankEnvironment.getParameters("VESSEL_ISSUER_ACCOUNT_ID"); }
	public static String getDigitalCurrencyIssuerAccountId() throws Exception{				return NeoBankEnvironment.getParameters("DIGITAL_CURRENCY_ISSUER_ACCOUNT_ID"); }
	public static String getStellarCustomerTransactionLimit () throws Exception{				return NeoBankEnvironment.STELLAR_CUSTOMER_TRANSACTION_LIMIT; }
	public static String getStellarTdaTransactionLimit () throws Exception{				return NeoBankEnvironment.STELLAR_TDA_TRANSACTION_LIMIT; }

	public static String getOpsViewAssetPricingPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_ASSET_PRICING_PAGE"); }
	public static String getOpsViewLoyaltyRatesPage() throws Exception{		return NeoBankEnvironment.getParameters("OPS_VIEW_LOYALTY_RATE_PAGE"); }
	public static String getCodeLoyaltyRedemption() throws Exception{				return NeoBankEnvironment.getParameters("CODE_LOYALTY_REDEMPTION"); }
	
	public static String getInvoiceFilePath() throws Exception { 				return NeoBankEnvironment.getParameters("PARTNER_INVOICE_FILE_PATH");}
	public static String getPorteLogoPath() throws Exception { 				return NeoBankEnvironment.getParameters("PORTE_LOGO_PATH");}
	
	 // BlockCypher APIs
	public static String getBlockCypherBitcoinMainNetURL() throws Exception { return NeoBankEnvironment.getParameters("BLOCKCYPHER_BITCOIN_MAINNET_URL");}
	public static String getBlockCypherBitcoinTest3NetURL() throws Exception { return NeoBankEnvironment.getParameters("BLOCKCYPHER_BITCOIN_TEST3NET_URL");}
	public static String getBlockCypherAccessToken() throws Exception { return NeoBankEnvironment.getParameters("BLOCKCYPHER_ACCESS_TOKEN");}

	//Bitcoin module
	public static String getBitCoinEnviroment() throws Exception { 				return NeoBankEnvironment.getParameters("GET_BITCOIN_ENVIROMENT");}
	public static String getBitCoinCreateTransactionBlockCypherUrl() throws Exception { 				return NeoBankEnvironment.getParameters("CREATE_TRANSACTION_BLOCKCYPHER_URL");}
	public static String getBitCoinSignTransactionBlockCypherUrl() throws Exception { 				return NeoBankEnvironment.getParameters("SIGN_TRANSACTION_BLOCKCYPHER_URL");}
	public static String getBitCoinBlockCypherAPIToken() throws Exception { 				return NeoBankEnvironment.getParameters("BLOCKCYPHER_API_TOKEN");}
	public static String getBTCP2PTxnCode() throws Exception { 				return NeoBankEnvironment.getParameters("CODE_CUSTOMER_BTC_P2P");}
	public static String getBTCToBTCxSwapCode() throws Exception { 				return NeoBankEnvironment.getParameters("CODE_CUSTOMER_BTC_TO_BTCX_SWAP");}
	public static String getBlockCypherBaseUrl() throws Exception { 				return NeoBankEnvironment.getParameters("BLOCK_CYPHER_BASE_URL");}
	public static String getStellarBaseUrlForApacheClientRequest() throws Exception { 				return NeoBankEnvironment.getParameters("STELLAR_BASE_URL_FOR_APACHE_CLIENT_REQUEST");}
	public static String getCoingeckoBaseUrlForApacheClientRequest() throws Exception { 				return NeoBankEnvironment.getParameters("COINGECKO_BASE_URL_FOR_APACHE_CLIENT_REQUEST");}
	
	
	public static String getPartenerCreateStellarAcPage() throws Exception { 				return NeoBankEnvironment.getParameters("PARTNER_REGISTER_STELLAR_ACC");}
	
	// OPS NOTIFICATIONS EMAILS
	public static String getOPsNewRegistrationEmailAddressNotifier() throws Exception { return NeoBankEnvironment.getParameters("OPS_NEW_REG_EMAIL_ADDRESS_NOTIFIER");}
	public static String getOPsNewDisputeRaisedEmailAddressNotifier() throws Exception { return NeoBankEnvironment.getParameters("OPS_NEW_DISPUTE_RAISED_ADDRESS_NOTIFIER");}
	public static String getAdminEmailAddressNotifier() throws Exception { return NeoBankEnvironment.getParameters("OPS_ADMIN_EMAIL_ADDRESS_NOTIFIER");}
	
}

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
String relationshipNo=null;
String publicKey=null;
List<AssetCoin> digitalCurrencies = null;
List<CryptoAssetCoins> assetCoins= null;
ServletContext context = null;
String langPref=null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callException(request, response, context,"Session has expired, please login again.");
		return;
	}
	relationshipNo= ((User)session.getAttribute("SESS_USER")).getRelationshipNo();
	if(request.getAttribute("publickey")!=null)
		publicKey = (String)request.getAttribute("publickey");
	if(request.getAttribute("digitalcurrencies")!=null)
		digitalCurrencies = (List<AssetCoin> )request.getAttribute("digitalcurrencies");
	if(request.getAttribute("assetcoins")!=null)
		assetCoins = (List<CryptoAssetCoins> )request.getAttribute("assetcoins");
	if(request.getAttribute("langpref")!=null) langPref = (String)request.getAttribute("langpref");
%>
<!doctype html>
<html lang="en" dir="ltr">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Favicon -->
<link rel="icon" href="assets/images/brand/pporte.png"
	type="image/x-icon" />
<link rel="shortcut icon" type="image/x-icon"
	href="assets/images/brand/pporte.png" />

<!-- Title -->
<title>Digital Remittance</title>

<!--Bootstrap.min css-->
<link rel="stylesheet"
	href="assets/plugins/bootstrap/css/bootstrap.min.css">

<!-- Dashboard css -->
<link href="assets/css/style.css" rel="stylesheet" />

<!-- Custom scroll bar css-->
<link href="assets/plugins/scroll-bar/jquery.mCustomScrollbar.css"
	rel="stylesheet" />

<!-- Sidemenu css -->
<link href="assets/plugins/toggle-sidebar/sidemenu.css" rel="stylesheet" />


<!-- Sidebar Accordions css -->
<link href="assets/plugins/accordion1/css/easy-responsive-tabs.css"
	rel="stylesheet">

<!-- Rightsidebar css -->
<link href="assets/plugins/sidebar/sidebar.css" rel="stylesheet">

<!---Font icons css-->
<link href="assets/plugins/iconfonts/plugin.css" rel="stylesheet" />
<link href="assets/plugins/iconfonts/icons.css" rel="stylesheet" />
<link href="assets/fonts/fonts/font-awesome.min.css" rel="stylesheet">



<!-- Custom css -->
<link href="assets/css/style2.css" rel="stylesheet" />
<!---Custom Dropdown css-->
<link href="assets/css/style5.css" rel="stylesheet">
<!-- Responsive css -->
<link href="assets/css/responsive.css" rel="stylesheet" />
<!-- Custom css -->
<link href="assets/css/app.css" rel="stylesheet" />
<!---Sweetalert2 css-->
<link href="assets/plugins/sweetalert2/sweetalert2.css" rel="stylesheet" />
<style>

		.image-exchange{
display: flex;
justify-content: center;
}

</style>
</head>

<body class="app sidebar-mini rtl"
	style="position: relative; min-height: 100%; top: 0px;">


	<!--Global-Loader-->
	<div id="global-loader">
		<img src="assets/images/icons/loader.svg" alt="loader">
	</div>
	<div id="spinner-div">
		<img src="assets/images/icons/loader.svg" alt="loader">
	</div>
	<div class="page">
		<div class="page-main">
			<!--app-header and sidebar-->
			<jsp:include page="cust_topandleftmenu.jsp" />

			<!-- app-content-->
			<div class="app-content  my-3 my-md-5 toggle-content">
				<div class="side-app" style="height: 89vh;">

					<!-- page-header -->
					<div class="page-header" style="margin: auto">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#"><span
									id="breadcrumb-item-currency-remittance">Currency
										Remittance</span></a></li>
							<li class="breadcrumb-item active" aria-current="page"><span
								id="breadcrumb-item-trade-currency">Trade Currency</span></li>
						</ol>
						<!-- End breadcrumb -->
					</div>
				
					<!-- End page-header -->
							<!--row open-->
							<div class="row">
								<div class="col-lg-12">
									<div class="card porte-card-content">
										<div class="card-header">
											<h3 class="card-title">
												<span id="header_trade_digital_currency">Trade
													Digital Currency</span>
											</h3>
										</div>
										<div class="card-body">
										 <div class="col-4">
											<div style="margin-left: -11px;margin-bottom: 20px;">
												<div class="input-group">
													<button class="theme-btn mr-2" 
														onclick="javascript:fnCallRemittanceTransactionPage();return false;"> <span> <i
														class="fa fa-th-list"></i> <span
														id="header_view_remittance_txn">View Remittance
														Transactions</span>
														</span>
													</button>
												</div>
										     </div>
										   </div>
											<form id="exchange-currency-form" method="post">
	
															<div class="my-3">
																<h3>
																	<span id="header-currency-details" style="color:#27A89D;">Currency
																		Details</span>
																</h3>
																<div class="row">
															
																	
																	<div class="col-lg-6 col-md-12">
																		<div class="form-group ">
																		<label class="form-label" for="source_coin"><span id="label_select_source_coin">Source Coin </span><span class="asterick">*</span></label>
									                                      <!-- <select class="form-control source_coin" name="source_coin" id="source_coin" onChange="javascript: fnUpdateSourceparams(); return false" form="currency_form" required></select> -->
									                                        <div id="buy_coin_asset"></div>
																	
																		</div>
																	</div>
																	<div class="col-lg-6 col-md-12">
																		<div class="form-group ">
																			<label class="form-label" id="select_currency_trade">Select
																				Currency to Trade</label>
																		     <div id="sel_currency_trading"></div>
																		
																		</div>
																	</div> 
																</div>
																	<div class="image-exchange">
																<img src="assets/images/crypto/dropdown.svg" alt="" srcset="">
															</div>
															<div class="transfer-coin-input">
																<div class="input-two clearfix">
																	<div class="input-two-box">
																		<input type="number" name="amount_to_spend" id="amount_to_spend"> 
																	</div>
																	<i class="fa fa-arrow-right" id="exchange_icon" style="color: #28a59a; cursor: pointer;"></i>
																	<div class="input-two-box">
																		<input type="number" name="amount_expected" id="amount_expected" readonly="readonly"> 
																	</div>
																</div>
															</div>

																<h3>
																	<span id="spn_enter_receiver_details" style="color:#27A89D;">Enter
																		Receiver Details</span>
																</h3>
																<div class="row">
																	<div class="col-lg-6 col-md-12">
																		<div class="form-group">
																			<label id="register-label-fullname">Receiver's
																				Name <span style="color: red;">*</span>
																			</label> <input type="text" name="receiver_name"
																				id="receiver_name" class="form-control styled-input-field">
																		</div>
																	</div>
																	<div class="col-lg-6 col-md-12">
																		<div class="form-group"> 
																			<label id="register-label-email">Receiver's
																				Email <span style="color: red;">*</span>
																			</label> <input type="email" name="receiver_email"
																				id="receiver_email" class="form-control styled-input-field">
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-lg-6 col-md-12">
																		<div class="form-group">
																			<label id="register-label-bank-name">Receiver's
																				Bank Name<span style="color: red;">*</span>
																			</label> <input type="text" name="receiver_bank_name"
																				id="receiver_bank_name" class="form-control styled-input-field">
																		</div>
																	</div>
																	<div class="col-lg-6 col-md-12">
																		<div class="form-group">
																			<label id="register-label-bank-code">Receiver's
																				Bank Code <span style="color: red;">*</span>
																			</label> <input type="text" name="receiver_bank_code"
																				id="receiver_bank_code" class="form-control styled-input-field">
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-lg-6 col-md-12">
																		<div class="form-group">
																			<label id="register-label-account-number">Receiver's
																				Account Number<span style="color: red;">*</span>
																			</label> <input type="text" name="receiver_account_no"
																				id="receiver_account_no" class="form-control styled-input-field">
																		</div>
																	</div>
																</div>

															</div>
											</form>
											<div class="transfer-coin-button">
													<button class="theme-btn" id="btn-coinspayanyone"
														onclick="javascript:fnSubmitPayment();return false;">
														<span id="btn_exchange_label">Exchange Coin</span>
													</button>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!--row closed-->
				</div>
			</div>
			<!-- End app-content-->

		</div>

	</div>

	<div id="error-msg" style="display: none;"></div>
	<form method="post" id="post-form"></form>
	<form method="post" id="post-form-2">
		<input type="hidden" name="qs"> <input type="hidden"
			name="rules"> <input type="hidden" name="hdnlang"
			>
	</form>
	<!-- End Page -->

	<div id="error-msg-login-page" style="display: none;">
		<span id="data-validation-error-swal-header">Oops..</span> <span
			id="data-validation-error-failed-to-get-details">Failed to get
			details</span> <span id="data-validation-problem-with-connection">Problem
			with connection</span> <span id="data-validation-check-your-data">Please
			check your data</span> <span id="data-validation-please-select-currency">Please
			select Currency</span> <span id="data-validation-please-select-coin">Please
			select Coin</span> <span id="data-validation-please-enter-amount">Please
			enter amount</span> <span id="no-offers-available">No offers
			available</span> <span id="no-do-not-have-trustline">You do not have
			a Trustline with this issuer</span> <span
			id="do-you-want-to-create-trustline">Do you want to create
			Trustline?</span> <span id="enter-your-secret-key">Enter your Secret
			Key</span> <span id="password-label">Password</span> <span
			id="password-validation-label">Please input your Secret Key!</span> <span
			id="enter-password-label">Enter your Password</span> <span
			id="enter-password-input-validation">Please input your
			password!</span> <span id="exchange-is-successful">Currency Exchange
			Successful</span>
			<span id="data_validation_amount_expected">Amount expected not inputed</span>
			<span id="data_validation_receiver_name">Please enter receiver's name</span>
			<span id="data_validation_receiver_email">Please enter receiver's email</span>
			<span id="data_validation_receiver_bank_name">Please enter receiver's bank name</span>
			<span id="data_validation_receiver_bank_code">Please enter receiver's bank code</span>
			<span id="data_validation_receiver_account_no">Please enter receiver's account number</span>
	</div>

	<!-- Back to top -->
	<a href="#top" id="back-to-top"><i class="fa fa-angle-up"></i></a>
	<!-- Jquery js-->
	<script src="assets/js/vendors/jquery-3.2.1.min.js"></script>
	<!--Bootstrap.min js-->
	<script src="assets/plugins/bootstrap/popper.min.js"></script>
	<script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>

	<!--Side-menu js-->
	<script src="assets/plugins/toggle-sidebar/sidemenu.js"></script>
		
	<!-- ddslick -->	
    <script type="text/javascript" src="assets/plugins/ddslick/jquery.ddslick.min.js" ></script>
	<!-- Sidebar Accordions js -->
	<script src="assets/plugins/accordion1/js/easyResponsiveTabs.js"></script>
	<!-- Custom scroll bar js-->
	<script
		src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>
	<script type="text/javascript"
		src="assets/plugins/dropdown/jquery.dropdown2.js"></script>
	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>
	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>

	<!--Accordion-Wizard-Form js-->
	<script
		src="assets/plugins/accordion-Wizard-Form/jquery.accordion-wizard.min.js"></script>
	<!-- Custom js-->
	<script src="assets/js/_cust_exchange_digital_currency_page.js"></script>
	<script src="assets/js/custom.js"></script>
	<script>
			$( document ).ready(function() {
				console.log('language is ','<%=langPref%>')
				fnChangePageLanguage('<%=langPref%>');
			});
		</script>
</body>
</html>
<%
}catch (Exception e){
	out.println ("Exception : "+e.getMessage());
}finally{
	if (langPref!=null) langPref=null;
	if ( relationshipNo!=null); relationshipNo=null;
	if ( digitalCurrencies!=null); digitalCurrencies=null;
	if ( assetCoins!=null); assetCoins=null;
	if ( publicKey!=null); publicKey=null;if (context!=null)context=null;
}
%>
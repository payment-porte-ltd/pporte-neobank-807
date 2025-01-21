<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*, com.pporte.utilities.Utilities,org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
String relationshipNo=null;
String publicKey=null;
ArrayList<AssetAccount> accountBalances = null;
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
	if(request.getAttribute("externalwallets")!=null)
		accountBalances = (ArrayList<AssetAccount> )request.getAttribute("externalwallets");
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

<!-- Title -->
<title>Buy Asset</title>

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

<!---Sweetalert2 css-->
<link href="assets/plugins/sweetalert2/sweetalert2.css" rel="stylesheet" />

<!-- Custom css -->
<link href="assets/css/app.css" rel="stylesheet" />
<!-- Custom css -->
<link href="assets/css/style2.css" rel="stylesheet" />
<!-- Custom css -->
<link href="assets/css/style5.css" rel="stylesheet">
<!-- Responsive css -->
<link href="assets/css/responsive.css" rel="stylesheet" />
<style>
.col-rate{
	background: white;
	border-radius: 10px;
	padding-top: 10px;
	height: 84px;
}
.wallet-transaction-box .wallet-transaction-name h3{
	color: #fff;
}
.wallet-transaction-box .wallet-transaction-name h3,
	.wallet-transaction-balance h3 {
	font-size: 16px;
	color: #fff;
	margin: 0px 0px;
}
.transfer-coin-input input {
  height: 3rem !important;
  border-radius: 10px !important;
}
select.form-control:not([size]):not([multiple]) {
  height: 3rem;
  border-radius: 10px;
  background: white !important;
}
.custom-radio .custom-control-input:checked ~ .custom-control-label::before {
background-color:#27a89d !important;
}
</style>
</head>

<body class="app sidebar-mini rtl"
	onload="fnGetCoinDetails('<%=relationshipNo%>')">

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
					<div class="page-header ml-5 pl-2">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#"><span
									id="breadcrumb-item-digital-assets">Digital Assets</span></a></li>
							<li class="breadcrumb-item active" aria-current="page"><span
								id="breadcrumb-item-buy-assets">Buy Asset</span></li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- page -->
					<div>
						<!-- page-content -->
						<div>						
							<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
								<div class="card">
									<div class="card-header">
										<h4 id="buy_coin_header">Buy Coin</h4>
									</div>
									<div class="row card-body">
										<div class="col-xl-8 col-lg-8 col-md-8 col-sm-8">
											<div class="transfercoin-left-box">
											<div class="transfer-coin-content">
												<div class="transfer-coin-content-box active porte-card-content" id="sent-coin"
													style="margin-top: 0%;" >
													<div class="input-group">
														<a class="theme-btn mr-2" id="btn-add-branch"
															onclick="fnCallPendingTransactionPage()"> <span> <i
																class="fa fa-th-list"></i> <span
																id="spn_view_pending_transaction">View Pending
																	Transactions</span>
														</span>
														</a>
													</div>
													<form id="buy-porte-coin-form" method="post" class="mt-3">
														<div
															class="transfer-coin-input transfer-coin-select clearfix">
															<div class="dropdown" style="width: 94%;">
																<!-- <label id="sp_select_coin">Select
																		Coin> </label> --> 
																		<!-- <select class="form-control" name="coin_asset"
																	id="coin_asset"
																	onChange="javascript: fnUpdatesenderparams(); return false">

																</select> -->
																<div id="buy_coin_asset" style="margin-bottom: -20px;"></div>
															</div>

														</div>

														<div
															class="transfer-coin-input transfer-coin-select clearfix">
															<div class="dropdown" style="width: 94%;">
																<label><span id="sp_select_mode_of_payment">Select
																		mode of payment</span> </label> <select class="form-control"
																	name="payment_method" id="payment_method"
																	onChange="javascript: fnGetPaymentMethod('<%=relationshipNo%>'); return false">
																	<option value="" disabled selected id="spn_select_paymode">Select paymode></option>
																	<option value="F" selected id="spn_select_wallet">Fiat Wallet</option>
																	<option value="T" id="spn_select_card">Card</option>
																</select>
															</div>
														</div>
														<div
															class="transfer-coin-input transfer-coin-select clearfix"
															id="cardsdiv" style="display: none;">
															<div class="dropdown" style="width: 94%;">
																<label><span id="sp_card_pay_with">Select
																		Card to pay with</span> </label>
																<div id="cardavailable"></div>

																<label><span id="sp_ccv2">Enter CVV</span> </label>
																<div class="input-two-box">
																	<input type="password" maxlength="3" name="cvv"
																		id="cvv" value="" placeholder="">

																</div>


															</div>
														</div>

														<div class="transfer-coin-input">
															<div class="input-two clearfix">
																<div class="input-two-box">
																	<input type="number"
																		name="receivedamount"
																		id="receivedamount" value=""
																		placeholder="">
																	<!-- <span id="coinbuyequivalent" class="text-white">USD</span> -->

																</div>
																<i class="fa fa-arrow-right"
																	id="exchange_icon"
																	style="color: #28a59a; cursor: pointer;"></i>
																<div class="input-two-box">
																	<input type="number" name="amount"
																		id="amount" value=""

																		placeholder="" readonly> <!-- <span
																		id="spansendcode" class="text-white">BTC</span> -->

																</div>

															</div>
														</div>

<!-- 
														<div class="transfer-coin-input">
															<div class="row justify-content-around">
																<div class="col-5 col-rate">
																	<div class="custom-input-wrap" id="buy_amount_error_message">
																	<label for="receivedamount">
																		<div class="row row-buycoin-exchange">
																		<div class="col-20 col-usdc"><img src="assets/images/crypto/usd.png" alt="" width="20" srcset="" class=""></div>
																		<div class="col-80 col-usdc-text"><span id="coinbuyequivalent">USD </span><span class="asterick">*</span></div>
																		</div>
																		</label>
																		<input type="text" class="text" id="receivedamount" name="receivedamount" required>
																	</div>
																</div>
																<div class="col-5 col-rate">
																	<label for="amount">
																	<div class="row row-buycoin-exchange">
																		<div class="col-20 col-usdc"><img src="" alt="" srcset="" class="image-canvas"></div>
																		<div class="col-80 col-usdc-text"><span id="spansendcode"> </span><span class="asterick">*</span></span></div>
																	</div>
																	</label>
																	<div class="custom-input-wrap">
																		<input type="text" class="text " id="amount" name="amount" readonly>
																	</div>
																</div>
																</div> 


															<label id="amount_to_buy_label">&nbsp; Amount to
																Buy</label>
															<div class="input-two clearfix">
																<div class="input-two-box">
																	<input type="" name="receivedamount"
																		id="receivedamount" value="" placeholder="">
																	<span id="coinbuyequivalent">USD</span>
																</div>
																<i class="fa fa-exchange"></i>
																<div class="input-two-box">
																	<label for="receivedamountbuy">
																		<div class="row row-buycoin-exchange">
																		<div class="col col-usdc"><img src="" alt="" srcset="" class="image-canvas"></div>
																		<div class="col col-usdc-text"><span id="spansendcode"></span></div>
																		</div>
																	</label>
																	<input type="" name="amount" id="amount" value=""
																		placeholder=""> 
																</div>

															</div>
														</div> -->


													</form>
													<div class="transfer-coin-button">
														<button class="theme-btn" id="btn-coinspayanyone"
															onclick="javascript:fnBuyPorteCoin()">
															<span id="btn_buy_coin_label">Buy Coin</span>
														</button>
													</div>
												</div>
											</div>


									</div>
										</div>
										<div class="col-xl-4 col-lg-4 col-md-4 col-sm-4">
											<div class="wallet-transaction">
												<% if( accountBalances!=null){
															for(int i=0;i<accountBalances.size();i++){   
															if(accountBalances.get(i).getAssetCode().equals("PORTE")){ %>
															<div class="wallet-transaction-box clearfix">
																<div class="wallet-transaction-name">
																	<img src="assets/images/crypto/porte.svg"
																		height="50" width="50">
																	<!--  <span>Last Updated</span> -->
																</div>
																<div class="wallet-transaction-balance" style="float: none !important;padding-top: 3px;padding-left: 20px;" >
																	<h3 style="font-size: 21px !important;color:white;font-weight: bold; margin-top: 10px;"><%=accountBalances.get(i).getAssetBalance()+" "%>PORTE </h3>
																	<!-- <span>`+porteDetails[i].lastUpdated+`</span> -->
																</div>
															</div>
												<%}else if(accountBalances.get(i).getAssetCode().equals("USDC")){ %>
															<div class="wallet-transaction-box clearfix">
																<div class="wallet-transaction-name">
																	<img src="assets/images/crypto/usdc.png"
																		height="50" width="50">
																	<!--  <span>Last Updated</span> -->
																</div>
																<div class="wallet-transaction-balance" style="float: none !important;padding-top: 3px;padding-left: 20px;" >
																	<h3 style="font-size: 21px !important;color:white;font-weight: bold; margin-top: 10px;"><%=accountBalances.get(i).getAssetBalance()+" "%>USDC </h3>
																	<!-- <span>`+porteDetails[i].lastUpdated+`</span> -->
																</div>
															</div>
												

												<%}else if(accountBalances.get(i).getAssetCode().equals("VESL")){ %>
												<div class="wallet-transaction-box clearfix">
																<div class="wallet-transaction-name">
																	<img src="assets/images/crypto/vessel.png"
																		height="50" width="50">
																	<!--  <span>Last Updated</span> -->
																</div>
																<div class="wallet-transaction-balance" style="float: none !important;padding-top: 3px;padding-left: 20px;" >
																	<h3 style="font-size: 21px !important;color:white;font-weight: bold; margin-top: 10px;"><%=accountBalances.get(i).getAssetBalance()+" "%>VESL </h3>
																	<!-- <span>`+porteDetails[i].lastUpdated+`</span> -->
																</div>
															</div>
												<%}else if(accountBalances.get(i).getAssetCode().equals("XLM")){ %>
															<div class="wallet-transaction-box clearfix">
																<div class="wallet-transaction-name">
																	<img src="assets/images/crypto/xlm.svg"
																		height="50" width="50">
																	<!--  <span>Last Updated</span> -->
																</div>
																<div class="wallet-transaction-balance" style="float: none !important;padding-top: 3px;padding-left: 20px;" >
																	<h3 style="font-size: 21px !important;color:white;font-weight: bold; margin-top: 10px;"><%=accountBalances.get(i).getAssetBalance()+" "%>XLM </h3>
																	<!-- <span>`+porteDetails[i].lastUpdated+`</span> -->
																</div>
															</div>


												<%} %>
												<% }							
														}else{ %>
												<div>
													<span id="ops_no_external_wallets">No External
														Wallets </span>
												</div>
												<% } %>

											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>


				</div>
			</div>
			<!-- End app-content-->

		</div>

	</div>
	<div id="error-msg-login-page" style="display: none;">
		<span id="data-validation-error-swal-header">Oops..</span> <span
			id="data-validation-error-failed-to-get-details">Failed to get
			details</span> <span id="data-validation-problem-with-connection">Problem
			with connection</span> <span id="data-validation-check-your-data">Please
			check your data</span> <span id="data-validation-select-coin-to-buy">Please
			select coin to buy</span> <span id="data-validation-select-payment-method">Please
			select payment method</span> <span id="data-validation-enter-amount">Please
			enter amount</span> <span id="data-validation-select-card-to-pay-with">Please
			Select Card to pay with</span> <span id="data-validation-of-cvv">CVV
			is invalid</span> <span id="data-validation-of-porte-coin">You dont
			have porte coin, click Register to register</span>
	</div>
	<!-- End Page -->
	<form method="post" id="post-form">
		<input type="hidden" name="qs" value=""> <input type="hidden"
			name="rules" value=""> <input type="hidden" name="relno"
			value=""> <input type="hidden" name="hdnlang"
			>
	</form>

	<!-- Back to top -->
	<a href="#top" id="back-to-top"><i class="fa fa-angle-up"></i></a>
	<!-- Jquery js-->
	<script src="assets/js/vendors/jquery-3.2.1.min.js"></script>
	<!--Bootstrap.min js-->
	<script src="assets/plugins/bootstrap/popper.min.js"></script>
	<script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>
	
	<!-- ddslick -->	
    <script type="text/javascript" src="assets/plugins/ddslick/jquery.ddslick.min.js" ></script>

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>

	<!--Side-menu js-->
	<script src="assets/plugins/toggle-sidebar/sidemenu.js"></script>

	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>

	<!-- Sidebar Accordions js -->
	<script src="assets/plugins/accordion1/js/easyResponsiveTabs.js"></script>
	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>

	<!-- Custom scroll bar js-->
	<script
		src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>
	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_cust_buy_porte_coins.js"></script>
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
	if ( relationshipNo!=null); relationshipNo=null;
	if ( accountBalances!=null); accountBalances=null;
	if ( publicKey!=null); publicKey=null;
	if (context!=null)context=null;
	if (langPref!=null) langPref=null;
}
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
String relationshipNo=null;
String publicKey=null;
ArrayList<AssetAccount> accountBalances = null;
ServletContext context = null;
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
<title>Buy Stellar Bitcoin</title>

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
<link href="assets/css/style2.css" rel="stylesheet" />
<!-- Custom css -->
<link href="assets/css/style5.css" rel="stylesheet">
<!-- Responsive css -->
<link href="assets/css/responsive.css" rel="stylesheet" />

</head>

<body class="app sidebar-mini rtl" onload="fnGetBTCxDetails()">

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
							<li class="breadcrumb-item"><a href="#">Stellar Bitcoin</a></li>
							<li class="breadcrumb-item active" aria-current="page">Buy
								BTCx using Fiat</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<div class="ml-auto">
						<div class="input-group">
							<a class="btn btn-primary text-white mr-2" id="btn-add-branch"
								onclick="fnCallPendingTransactionPage()"> <span> <i
									class="fa fa-th-list"></i>View Pending BTCx Transactions
							</span>
							</a>
						</div>
					</div>
					<!-- End page-header -->
					<!-- page -->
					<div class="page">
						<!-- page-content -->
						<div class="page-content">
							<div class="tranfer-coin-box">
								<div class="row">
									<div class="col-xl-7">
										<div class="transfercoin-left-box">
											<div class="transfer-coin-content">
												<div class="transfer-coin-content-box active" id="sent-coin"
													style="margin-top: 0%;">
													<form id="buy-btcx-coin-form" method="post">
														<div
															class="transfer-coin-input transfer-coin-select clearfix">
															<div class="dropdown" style="width: 94%;">
																<label><span id="sp_from_id">Select Coin</span>
																</label> <select class="form-control" name="coin_asset"
																	id="coin_asset"
																	onChange="javascript: fnUpdatesenderparams(); return false">

																</select>
															</div>

														</div>

														<div
															class="transfer-coin-input transfer-coin-select clearfix">
															<div class="dropdown" style="width: 94%;">
																<label><span id="sp_from_id">Select mode
																		of payment</span> </label> <select class="form-control"
																	name="payment_method" id="payment_method"
																	onChange="javascript: fnGetPaymentMethod('<%=relationshipNo%>'); return false">
																	<option value="" disabled selected>Select
																		paymode</option>
																	<option value="F" selected>Wallet</option>
																	<option value="T">Card</option>
																</select>
															</div>
														</div>
														<div
															class="transfer-coin-input transfer-coin-select clearfix"
															id="cardsdiv" style="display: none;">
															<div class="dropdown" style="width: 94%;">
																<label><span id="sp_from_id">Select Card
																		to pay with</span> </label>
																<div id="cardavailable"></div>

																<label><span id="sp_ccv2">Enter CVV</span> </label>
																<div class="input-two-box">
																	<input type="password" maxlength="3" name="cvv"
																		id="cvv" value="" placeholder="">

																</div>


															</div>
														</div>

														<div class="transfer-coin-input">
															<label>&nbsp; Amount to Buy</label>
															<div class="input-two clearfix">
																<div class="input-two-box">
																	<input type="" name="receivedamount"
																		id="receivedamount" value="" placeholder=""
																		onkeyup="javascript: UpdateConversionRate(); return false;">
																	<span id="coinbuyequivalent">USD</span>
																</div>
																<i class="fa fa-exchange"></i>
																<div class="input-two-box">
																	<input type="" name="amount" id="amount" value=""
																		placeholder=""> <span id="spansendcode"></span>
																</div>

															</div>
														</div>


													</form>
													<div class="transfer-coin-button">
														<button class="theme-btn" id="btn-coinspayanyone"
															onclick="javascript:fnBuyBTCx()">Buy BTCx</button>
													</div>
												</div>
											</div>


										</div>
									</div>
									<div class="col-xl-5">
										<div class="wallet-transaction">
											<% if( accountBalances!=null){
														for(int i=0;i<accountBalances.size();i++){   
														if(accountBalances.get(i).getAssetCode().equals("BTC")){ %>

											<div class="wallet-transaction-box clearfix">
												<div class="wallet-balance-ico">
													<img src="assets/images/crypto/bitcoin.svg" alt="Litcoin"
														height="40" width="40">
												</div>
												<div class="wallet-transaction-name">
													<h3>STELLAR Bitcoin</h3>
													<!--  <span>Last Updated</span> -->
												</div>
												<div class="wallet-transaction-balance">
													<h3>
														<%=accountBalances.get(i).getAssetBalance()+" "%>BTC
													</h3>
													<!-- <span>`+porteDetails[i].lastUpdated+`</span> -->
												</div>
											</div>


											<%} %>
											<% }							
													}else{ %>
											<div>
												<span id="ops_all_bin_list_errormsg1">You don't have
													Stellar Bitcoin Wallet </span>
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
			<!-- End app-content-->

		</div>

	</div>
	<!-- End Page -->
	<form method="post" id="post-form">
		<input type="hidden" name="qs" value=""> <input type="hidden"
			name="rules" value=""> <input type="hidden" name="relno"
			value="">
	</form>

	<!-- Back to top -->
	<a href="#top" id="back-to-top"><i class="fa fa-angle-up"></i></a>
	<!-- Jquery js-->
	<script src="assets/js/vendors/jquery-3.2.1.min.js"></script>
	<!--Bootstrap.min js-->
	<script src="assets/plugins/bootstrap/popper.min.js"></script>
	<script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>
	<!--Jquery Sparkline js-->
	<script src="assets/js/vendors/jquery.sparkline.min.js"></script>
	<!--Side-menu js-->
	<script src="assets/plugins/toggle-sidebar/sidemenu.js"></script>
	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>
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
	<script src="assets/js/_cust_buy_btcx_page.js"></script>

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
}
%>
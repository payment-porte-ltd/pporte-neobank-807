<%@page import="com.google.gson.JsonArray"%>
<%@page import="com.pporte.utilities.Utilities"%>
<%@page import="com.google.gson.JsonObject"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
String relationshipNo=null;
JsonObject balanceObj = null;
String balanceAccount = null;
String langPref=null;

JsonObject accountDetails = null; 
JsonArray txnArray = null;

ArrayList<BTCTransction> arrBTCTransaction  = null;
String finalBalance = null;
String totalReceived = null;
String totalSent = null;
String totalTransactions = null;
String publicKey=null;
ArrayList<AssetAccount> accountBalances = null;
ServletContext context = null;
try{
	if (request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callException(request, response, context,"Session has expired, please login again.");
		return;
	}
	
	relationshipNo= ((User)session.getAttribute("SESS_USER")).getRelationshipNo();	
	if (request.getAttribute("btc_balance") != null) balanceObj = (JsonObject) request.getAttribute("btc_balance");
	if(request.getAttribute("langpref")!=null) langPref = (String)request.getAttribute("langpref");
	if(balanceObj!=null){
		balanceAccount = Utilities.convertSatoshiToBTC(balanceObj.get("balance").getAsString());
	}
	if (request.getAttribute("transactions") != null) arrBTCTransaction =  (ArrayList<BTCTransction>) request.getAttribute("transactions");
	if (request.getAttribute("accountDetails") != null) accountDetails =  (JsonObject) request.getAttribute("accountDetails");
	if(accountDetails!=null){
		finalBalance = accountDetails.get("final_balance").getAsString();
		totalReceived = accountDetails.get("total_received").getAsString();
		totalSent = accountDetails.get("total_sent").getAsString();
		totalTransactions = accountDetails.get("final_n_tx").getAsString();
	}
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
<title>Bitcoin</title>

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

<!--Daterangepicker css-->
<link
	href="assets/plugins/bootstrap-daterangepicker/daterangepicker.css"
	rel="stylesheet" />

<!-- Sidebar Accordions css -->
<link href="assets/plugins/accordion1/css/easy-responsive-tabs.css"
	rel="stylesheet">

<!-- Rightsidebar css -->
<link href="assets/plugins/sidebar/sidebar.css" rel="stylesheet">

<!-- Data table css -->
<link href="assets/plugins/datatable/dataTables.bootstrap4.min.css"
	rel="stylesheet" />
<link href="assets/plugins/datatable/responsivebootstrap4.min.css"
	rel="stylesheet" />

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
<style>
.sw-theme-dots .sw-toolbar-bottom {
	padding: 15px;
}

/* HIDE RADIO */
[type=radio] {
	position: absolute;
	opacity: 0;
	width: 0;
	height: 0;
}

/* IMAGE STYLES */
[type=radio]+img {
	cursor: pointer;
}

/* CHECKED STYLES */
[type=radio]:checked+img {
	outline: 4px solid #DC0000;
}

select.form-control:not([size]):not([multiple]) {
  height: 3rem;
  border-radius: 10px;
  background: white !important;
}
.table-striped tbody tr:nth-of-type(2n) {
background: transparent !important;
}
body{
}
.transfer-coin-input label{
}
.transfer-coin-input input {
	height: 3rem;
}

#sendamount_trans_btc {
  background-image: url('./assets/images/crypto/bitcoin.svg');
  background-repeat: no-repeat;
  background-position-x: 92%;
  background-position-y: center;
  background-size: 35px;
}
#destination_amount_swap_btc{
	background-image: url('./assets/images/crypto/bitcoin.svg');
  background-repeat: no-repeat;
  background-position-x: 77%;
  background-position-y: center;
  background-size: 35px;
}
#source_amount_swap_btc{
  background-image: url('./assets/images/crypto/btcx.svg');
  background-repeat: no-repeat;
  background-position-x: 73%;
  background-position-y: center;
  background-size: 35px;
}
.custom-radio .custom-control-input:checked ~ .custom-control-label::before {
background-color:#27a89d !important;
}
</style>
</head>

<body class="app sidebar-mini rtl">

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
					<div class="page-header ml-5" style="margin: auto">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#"><span
									id="breadcrumb-item-digital-assets">Digital Assets</span></a></li>
							<li class="breadcrumb-item active" aria-current="page"><span
								id="breadcrumb-item-bitcoin">Bitcoin</span></li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- page -->
					<div class="page">
						<!-- page-content -->
						<div class="page-content">
							<div class="container text-dark">
								<div class="row">
									<div class="col-md-12">
										<div class="card">
											<div class="card-header">
												<h3 class="card-title">
													<span id="card-title-bitcoin">Bitcoin</span>
												</h3>
											</div>
											<div class="card-body p-6">
												<div class="panel panel-primary">
													<div class="tab-menu-heading">
														<div class="tabs-menu ">
															<!-- Tabs -->
															<ul class="nav panel-tabs">
																<li class=""><a href="#tab11" class="active"
																	data-toggle="tab"><span
																		id="tab-header-native-bitcoin" class="text-white">Native Bitcoin</span></a></li>
																<li><a href="#tab21" data-toggle="tab"><span
																		id="tab-header-native-stellar-bitcoin" class="text-white">Stellar
																			Bitcoin</span></a></li>
															</ul>
														</div>
													</div>

													<div class="panel-body tabs-menu-body">
														<div class="tab-content">
															<div class="tab-pane active " id="tab11">
																<div class="panel panel-primary">
																	<div class=" tab-menu-heading">
																		<div class="tabs-menu1 ">
																			<!-- Tabs -->
																			<ul class="nav panel-tabs">
																				<li class=""><a href="#tab5" class="active"
																					data-toggle="tab"><span class="text-white" id="tab-buy-btc">Buy
																							BTC</span></a></li>
																				<li><a href="#tab6" data-toggle="tab"><span
																						id="tab-transfer-btc" class="text-white">Transfer BTC</span></a></li>
																				<li><a href="#tab7" data-toggle="tab"><span
																						id="tab-swap-btc" class="text-white">Swap BTC</span></a></li>
																				<li><a href="#tab8" data-toggle="tab"><span
																						id="tab-transactions-btc" class="text-white">Transactions</span></a></li>
																			</ul>
																		</div>
																	</div>
																	<!--Buy BTC-->
																	<div class="panel-body tabs-menu-body">
																		<div class="tab-content">
																			<div class="tab-pane active " id="tab5">
																				<div class="tranfer-coin-box">
																					<div class="row">
																						<div class="col-xl-8">
																							<div class="transfercoin-left-box">
																								<div class="transfer-coin-content">
																									<div class="transfer-coin-content-box active"
																										id="sent-coin" style="margin-top: 0%;">
																										<form id="buy-btc-coin-form" method="post">
																											
																											<label><span
																												id="sp_select_mode_of_payment_899" class="text-white" style="font-weight:500;">Select
																													mode of payment</span> </label>
																											<div
																												class="transfer-coin-input transfer-coin-select clearfix">
																												<div class="dropdown" style="width: 94%;">
																													 <select
																														class="form-control"
																														name="payment_method_buy_btc"
																														id="payment_method_buy_btc"
																														onChange="javascript: fnGetPaymentMethodBuyBTC('<%=relationshipNo%>'); return false">
																														<option value="" disabled selected id="spn_select_paymode">Select paymode</option>
																														<option value="F" selected id="spn_select_option_wallet">Fiat Wallet</option>
																														<option value="T" id="spn_select_option_card">Card</option>
																													</select>
																												</div>
																											</div>
																											<div
																												class="transfer-coin-input transfer-coin-select clearfix"
																												id="cardsdiv_buy_btc" style="display: none;">
																												<div class="dropdown" style="width: 94%;">
																													<label><span
																														id="sp_sel_card_to_pay_with_78855" class="text-white">Select
																															Card to pay with</span> </label>
																													<div id="cardavailable_buy_btc"></div>

																													<label><span id="sp_ccv2855" class="text-black">Enter
																															CVV</span> </label>
																													<div class="input-two-box">
																														<input type="password" maxlength="3"
																															name="cvve_buy_btc" id="cvve_buy_btc"
																															value="">

																													</div>


																												</div>
																											</div>

																											<div class="transfer-coin-input">
																												<label id="amount_to_label_784569" class="text-white">&nbsp;
																													Amount to Buy</label>
																												<div class="input-two clearfix">
																													<div class="input-two-box">
																														<input type="number"
																															name="receivedamounte_buy_btc"
																															id="receivedamounte_buy_btc" value=""
																															placeholder=""
																															onkeyup="javascript: UpdateConversionRateBuyBTC(); return false;">
																														<!-- <span id="coinbuyequivalent" class="text-white">USD</span> -->

																													</div>
																													<i class="fa fa-arrow-right"
																														id="exchange_icon"
																														style="color: #28a59a; cursor: pointer;"></i>
																													<div class="input-two-box">
																														<input type="number" name="amounte_buy_btc"
																															id="amounte_buy_btc" value=""

																															placeholder=""> <!-- <span
																															id="spansendcode" class="text-white">BTC</span> -->

																													</div>

																												</div>
																											</div>
																										</form>
																										<div class="transfer-coin-button">
																											<button class="theme-btn"
																												id="btn-coinspayanyone"
																												onclick="javascript:fnBuyBTC()">
																												<span id="btn_buy_btc_label">Buy BTC</span>
																											</button>
																										</div>
																									</div>
																								</div>


																							</div>
																						</div>
																						
																						
																							<div class="col-xl-4">
																								<div class="transfer-coin-button mb-2">
																											<button class="theme-btn"
																												id="btn-coinspayanyone_trans_btc"
																												name="btn-coinspayanyone_trans_btc"
																												onclick="fnCallPendingBuyBTCPage()">
																												<span id="spn_view_pending_transaction_label">View
																											Pending Transactions</span>
																											</button>
																								</div>
																								<!-- <div class="text-center" style="margin-bottom:8px;">
																										
																										<span  id="btn-add-branch" style="color:#27A79C"
																											onclick="fnCallPendingBuyBTCPage()"> <span>
																												<i class="fa fa-th-list"></i> <span
																												id="spn_view_pending_transaction">View
																													Pending Transactions</span>
																										</span>
																										</span>
																									</div> -->
																									<div class="wallet-transaction">
															
																										<% if( balanceAccount!=null){   %>
																										<div class="wallet-transaction-box clearfix">
																											<div class="wallet-transaction-name">
																												<img src="assets/images/crypto/bitcoin.svg" alt="Bitcoin"
																													height="50" width="50">
																												<!--  <span>Last Updated</span> -->
																											</div>
																											<div class="wallet-transaction-balance" >
																												<h3 style="color:white;font-weight: bold;font-size: 25px;"><%=balanceAccount+" "%>BTC
																												</h3>
																												<!-- <span>`+porteDetails[i].lastUpdated+`</span> -->
																											</div>
																										</div>
																																						
																										
																										<% 							
																												}else{ %>
																										<div>
																											<span id="spn_you_dont_have_btc_wallet">You don't have Bitcoin Wallet </span>
																										</div>
																										<% } %>
															
																									</div>
																								</div>
																					</div>
																				</div>
																			</div>
																			<!-- Transfer BTC -->
																			<div class="tab-pane " id="tab6">
																				<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
																					<div class="row ">
																						<div class="col-xl-8">
																							<div class="transfercoin-left-box">
																								<div class="transfer-coin-content">
																									<div class="transfer-coin-content-box active"
																										id="sent-coin" style="margin-top: 0%;">
																										<form id="transfer-coin-form" method="post">
																											<div class="transfer-coin-input">
																												<label id="send_to_label_4758" class="text-white">Send
																													To</label> <input type=""
																													name="input_receiver_address_trans_btc"
																													id="input_receiver_address_trans_btc"
																													value="">
																											</div>

																											<div class="transfer-coin-input">
																												<label id="amount_to_send_label_45" class="text-white">Amount
																													to send (BTC)</label>
																												<div class="input-two clearfix">
																													<div class="input-two-box"
																														style="width: 93%;">
																														<input type="" name="sendamount_trans_btc"
																															id="sendamount_trans_btc" value=""
																															placeholder="">
																													</div>

																												</div>
																											</div>
																											<div class="transfer-coin-input">
																												<label id="commet_trans_btc_label_71" class="text-white">Comment</label>
																												<input type="" name="commet_trans_btc"
																													id="commet_trans_btc" value=""
																													>
																											</div>

																											<input type="hidden" name="qs" value="">
																											<input type="hidden" name="rules" value="">
																										</form>
																										<div class="transfer-coin-button">
																											<button class="theme-btn"
																												id="btn-coinspayanyone_trans_btc"
																												name="btn-coinspayanyone_trans_btc"
																												onclick="javascript:checkIfUserHasMneonicCodeTransferBTC()">
																												<span id="spn_btn_coinspayanyone_trans_btc">Send
																													Coin</span>
																											</button>
																										</div>
																									</div>
																								</div>


																							</div>
																						</div>

																						<div class="col-xl-4">
																							<div class="wallet-transaction"
																								id="div_transactions">
																								<% if( balanceAccount!=null){ %>
																								<div class="wallet-transaction-box clearfix">
																											<div class="wallet-transaction-name">
																												<img src="assets/images/crypto/bitcoin.svg" alt="Bitcoin"
																													height="50" width="50">
																												<!--  <span>Last Updated</span> -->
																											</div>
																											<div class="wallet-transaction-balance" >
																												<h3 style="color: white; font-size: 20px; font-weight: bold; margin-top: 10px;"><%=balanceAccount+" "%>BTC
																												</h3>
																												<!-- <span>`+porteDetails[i].lastUpdated+`</span> -->
																											</div>
																										</div>
																								<%}else{ %>
																								<div>
																									<span id="spn_btc_wallet_not_available" class="text-white">You
																										don't have Bitcoin Wallet </span>
																								</div>
																								<% } %>
																							</div>
																						</div>

																					</div>

																				</div>
																			</div>
																			<!-- Swap BTC -->
																			<div class="tab-pane " id="tab7">
																				<div class="tranfer-coin-box">
																					<div class="row">
																						<div class="col-xl-8">
																							<div class="transfercoin-left-box">
																								<div class="transfer-coin-content">
																									<div class="transfer-coin-content-box active"
																										id="sent-coin">
																										<form id="exchange-btc-coin-form"
																											method="post">
																											<div class="transfer-coin-input">
																												<label id="amount_label_58" class="text-white">Amount</label>
																												<div class="input-two clearfix">
																													<div class="input-two-box">
																														<input type=""
																															name="destination_amount_swap_btc"
																															id="destination_amount_swap_btc" value=""
																															placeholder=""> <span
																															id="spanreceivedcode" class="text-black">BTC</span>
																													</div>

																													<i class="fa fa-arrow-right"
																														id="exchange_icon"
																														style="color: #28a59a; cursor: pointer;"
																														onclick="javascript: cryptocoversionFromSource(); return false"></i>
																													<div class="input-two-box">
																														<input type=""
																															name="source_amount_swap_btc"
																															id="source_amount_swap_btc" value=""
																															placeholder="" readonly="readonly">
																														<span id="spansendcode" class="text-black">BTCx</span>
																													</div>
																												</div>
																											</div>

																											<!-- <div class="transfer-coin-input">
																											 <label>Private Key</label>
																											 <input type="password" name="private_key" id="private_key" value="" placeholder="Enter Your Private Key">
																										 </div> -->

																											<div class="transfer-coin-button">
																												<button type="button" class="theme-btn"
																													id="btn-swap-coins_swap_btc"
																													onclick="javascript: checkIfUserHasMneonicCodeSwapBTC(); return false">
																													<span id="spn_exchange_asset">Exchange
																														Asset</span>
																												</button>
																											</div>
																										</form>
																									</div>
																								</div>


																							</div>
																						</div>



																						<div class="col-xl-4">
																							<div class="transfer-coin-button mb-2">
																								<button class="theme-btn"
																									id="btn-coinspayanyone_trans_btc"
																									name="btn-coinspayanyone_trans_btc"
																									onclick="fnCallPendingSwapBTCTOXPage()">
																									<span id="spn_view_pending_transaction_label">View
																								Pending Transactions</span>
																								</button>
																							</div>
																							<div class="wallet-transaction">
																								<% if( balanceAccount!=null){ %>
																									<div class="wallet-transaction-box clearfix">
																											<div class="wallet-transaction-name">
																												<img src="assets/images/crypto/bitcoin.svg" alt="Bitcoin"
																													height="50" width="50">
																												<!--  <span>Last Updated</span> -->
																											</div>
																											<div class="wallet-transaction-balance" >
																												<h3 style="color: white; font-size: 20px; font-weight: bold;"><%=balanceAccount+" "%>BTC
																												</h3>
																												<!-- <span>`+porteDetails[i].lastUpdated+`</span> -->
																											</div>
																										</div>
																								<%}else{ %>
																								<div>
																									<span id="spn_you_dont_have_btc_wallet" class="text-white">You don't have Bitcoin Wallet </span>
																								</div>
																								<% } %>


																							</div>
																						</div>

																					</div>
																				</div>
																			</div>
																			<!-- Transactions -->
																			<div class="tab-pane " id="tab8">
																				<div class="card">
																					<%if(accountDetails!=null){ %>
																					<div class="row"
																						style="margin-left: 10px; margin-right: 10px; margin-top: 10px; margin-bottom: -27px;">
																						<div class="col-sm-12 col-lg-6 col-xl-3">
																							<div class="card">
																								<div class="row">
																									<div class="col-4">
																										<div class="feature">
																											<div
																												class="fa-stack fa-lg fa-2x icon bg-orange">
																												<i class="fa fa-btc fa-stack-1x text-white"></i>
																											</div>
																										</div>
																									</div>
																									<div class="col-8">
																										<div class="card-body p-3  d-flex">
																											<div>
																												<p class="text-muted mb-1"
																													id="p_final_balance" class="text-white">Final Balance</p>
																												<h6 class="mb-0 text-dark text-white" ><%=finalBalance+" BTC" %></h6>
																											</div>
																										</div>
																									</div>
																								</div>
																							</div>
																						</div>
																						<!-- col end -->
																						<div class="col-sm-12 col-lg-6 col-xl-3">
																							<div class="card">
																								<div class="row">
																									<div class="col-4">
																										<div class="feature">
																											<div
																												class="fa-stack fa-lg fa-2x icon bg-green">
																												<i
																													class="fa fa-arrow-circle-up fa-stack-1x text-white"></i>
																											</div>
																										</div>
																									</div>
																									<div class="col-8">
																										<div class="card-body p-3  d-flex">
																											<div>
																												<p class="text-muted mb-1"
																													id="p_total_received"  class="text-white">Total Received</p>
																												<h6 class="mb-0 text-dark text-white"  ><%=totalReceived+" BTC" %></h6>
																											</div>
																										</div>
																									</div>
																								</div>
																							</div>
																						</div>
																						<!-- col end -->
																						<div class="col-sm-12 col-lg-6 col-xl-3">
																							<div class="card">
																								<div class="row">
																									<div class="col-4">
																										<div class="feature">
																											<div
																												class="fa-stack fa-lg fa-2x icon bg-danger">
																												<i
																													class="fa fa-arrow-circle-down fa-stack-1x text-white"></i>
																											</div>
																										</div>
																									</div>
																									<div class="col-8">
																										<div class="card-body p-3  d-flex">
																											<div>
																												<p class="text-muted mb-1"
																													id="p_total_amount_sent"  class="text-white">Total Sent</p>
																												<h6 class="mb-0 text-dark text-white"  ><%=totalSent+" BTC" %></h6>
																											</div>
																										</div>
																									</div>
																								</div>
																							</div>
																						</div>
																						<!-- col end -->
																						<div class="col-sm-12 col-lg-6 col-xl-3">
																							<div class="card">
																								<div class="row">
																									<div class="col-4">
																										<div class="feature">
																											<div
																												class="fa-stack fa-lg fa-2x icon bg-blue">
																												<i
																													class="fa fa-exchange fa-stack-1x text-white"></i>
																											</div>
																										</div>
																									</div>
																									<div class="col-8">
																										<div class="card-body p-3  d-flex">
																											<div>
																												<p class="text-muted mb-1"
																													id="p_number_of_txn" class="text-white">Number of
																													Transactions</p>
																												<h6 class="mb-0 text-dark text-white" ><%=totalTransactions %></h6>
																											</div>
																										</div>
																									</div>
																								</div>
																							</div>
																						</div>
																						<!-- col end -->
																					</div>
																					<%}else{ %>
																					<p id="p_you_dont_have_btc_account" class="text-white">You don't
																						have BTC Account</p>
																					<%} %>
																					<div class="card-body">
																						<div class="table-responsive"
																							id="transactiontable">
																							<table id="stellar_txns"
																								class="table table-striped table-bordered text-nowrap">
																								<thead>
																									<tr>
																										<th><span id="th_txn_hash" class="text-white">Transaction
																												Hash</span></th>
																										<th><span id="th_txn_date" class="text-white">date</span></th>
																										<th><span id="th_txn_status" class="text-white">Status</span></th>
																										<th><span id="th_txn_amount" class="text-white">Transaction
																												Amount</span></th>

																									</tr>
																								</thead>
																								<tbody>
																									<% if( arrBTCTransaction!=null){
																									for(int i=0;i<arrBTCTransaction.size();i++){       
																									%>
																									<%if(((BTCTransction)arrBTCTransaction.get(i)).getTxnMode().equals("D")){ %>
																									<tr>
																										<td><%=((BTCTransction)arrBTCTransaction.get(i)).getHash()%>
																										</td>
																										<td><%=((BTCTransction)arrBTCTransaction.get(i)).getTxnDateTime()%>
																										</td>
																										<%if(((BTCTransction)arrBTCTransaction.get(i)).getConfirmedStatus().equals("CONFIRMED")){ %>
																										<td class="text-success"><%=((BTCTransction)arrBTCTransaction.get(i)).getConfirmedStatus()%>
																										</td>
																										<%}else{ %>
																										<td class="text-danger"><%=((BTCTransction)arrBTCTransaction.get(i)).getConfirmedStatus()%>
																										</td>
																										<%}%>
																										<td class="text-danger"><%="-"+((BTCTransction)arrBTCTransaction.get(i)).getTxnAmaount()+" "%>BTC
																										</td>
																										<!-- <td><%=((BTCTransction)arrBTCTransaction.get(i)).getTxnFees()+" "%>BTC </td>				 	
																											 <td> </td>
																											 <td><%=((BTCTransction)arrBTCTransaction.get(i)).getDestinatioAddress()%> </td>
																											 <td><%=((BTCTransction)arrBTCTransaction.get(i)).getConfirmations()%> </td> -->
																									</tr>
																									<%}else{ %>
																									<tr>
																										<td><%=((BTCTransction)arrBTCTransaction.get(i)).getHash()%>
																										</td>
																										<td><%=((BTCTransction)arrBTCTransaction.get(i)).getTxnDateTime()%>
																										</td>
																										<%if(((BTCTransction)arrBTCTransaction.get(i)).getConfirmedStatus().equals("CONFIRMED")){ %>
																										<td class="text-success"><%=((BTCTransction)arrBTCTransaction.get(i)).getConfirmedStatus()%>
																										</td>
																										<%}else{ %>
																										<td class="text-danger"><%=((BTCTransction)arrBTCTransaction.get(i)).getConfirmedStatus()%>
																										</td>
																										<%}%>
																										<td class="text-success"><%="+"+((BTCTransction)arrBTCTransaction.get(i)).getTxnAmaount()+" "%>BTC
																										</td>
																										<!-- <td><%=((BTCTransction)arrBTCTransaction.get(i)).getTxnFees()+" "%>BTC </td>	
																											<td><%=((BTCTransction)arrBTCTransaction.get(i)).getSourceAddress()%> </td>			 	
																											 <td> </td>
																											 <td><%=((BTCTransction)arrBTCTransaction.get(i)).getConfirmations()%> </td> -->

																									</tr>
																									<%} %>

																									<% }
																									}else{ %>
																									<tr>
																										<td colspan="9"><span
																											id="ops_no_availabele_txn_dtls" class="text-white">No
																												available Transactions Details </span></td>
																									</tr>
																									<% } %>

																								</tbody>
																							</table>
																						</div>
																					</div>
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
															<div class="tab-pane  " id="tab21">
																<div class="panel panel-primary">
																	<div class=" tab-menu-heading">
																		<div class="tabs-menu1 ">
																			<!-- Tabs -->
																			<ul class="nav panel-tabs">
																				<li class=""><a href="#tab9" class="active"
																					data-toggle="tab"><span id="spn_buy_btcx_tab" class="text-white">Buy
																							BTCx</span></a></li>
																				<li><a href="#tab10" data-toggle="tab"><span
																						id="spn_swap_btcx_tab" class="text-white">Swap BTCx</span></a></li>

																			</ul>
																		</div>
																	</div>
																	<div class="panel-body tabs-menu-body">
																		<div class="tab-content">
																			<!-- Buy BTCx -->
																			<div class="tab-pane active " id="tab9">
																				<div class="tranfer-coin-box">
																					<div class="row">
																						<div class="col-xl-8">
																							<div class="transfercoin-left-box">
																								<div class="transfer-coin-content">
																									<div class="transfer-coin-content-box active"
																										id="sent-coin" style="margin-top: 0%;">
																										<form id="buy-btcx-coin-form" method="post">
																		
																											<label><span
																												id="sp_sel_mode_of_payment" class="text-white">Select
																													mode of payment</span> </label>
																											<div
																												class="transfer-coin-input transfer-coin-select clearfix">
																												<div class="dropdown" style="width: 94%;">
																													 <select
																														class="form-control"
																														name="payment_method_buy_btcx"
																														id="payment_method_buy_btcx"
																														onChange="javascript: fnGetPaymentMethodBuyBTCx('<%=relationshipNo%>'); return false">
																														<option value="" disabled selected id="spn_option_sel_paymode">Select paymode</option>
																														<option value="F" selected id="spn_option_sel_wallet">Fiat Wallet</option>
																														<option value="T" id="spn_option_sel_card">Card</option>
																													</select>
																												</div>
																											</div>
																											<div
																												class="transfer-coin-input transfer-coin-select clearfix"
																												id="cardsdiv_buy_btx" style="display: none;">
																												<div class="dropdown" style="width: 94%;">
																													<label><span
																														id="sp_sel_card_pay_with" class="text-white">Select
																															Card to pay with</span> </label>
																													<div id="cardavailable_buy_btcx"></div>

																													<label><span id="sp_ccv_buy_btcx" class="text-white">Enter
																															CVV</span> </label>
																													<div class="input-two-box">
																														<input type="password" maxlength="3"
																															name="cvv_buy_btcx" id="cvv_buy_btcx"
																															value="" placeholder="">

																													</div>


																												</div>
																											</div>

																											<div class="transfer-coin-input">
																												<label id="amount_to_buy_btcx" class="text-white">&nbsp;
																													Amount to Buy</label>
																												<div class="input-two clearfix">
																													<div class="input-two-box">
																														<input type="number"
																															name="receivedamount_buy_btcx"
																															id="receivedamount_buy_btcx" value=""
																															placeholder=""
																															onkeyup="javascript: fnUpdatesenderparamsBuyBTCx(); return false;">
																														<!-- <span id="coinbuyequivalent" class="text-white">USD</span> -->
																													</div>
																													<i class="fa fa-arrow-right"
																														id="exchange_icon"
																														style="color: #28a59a; cursor: pointer;"></i>
																													<div class="input-two-box">
																														<input type="number" name="amount_buy_btcx"
																															id="amount_buy_btcx" value=""
																															placeholder=""> <!-- <span
																															id="spansendcode_buy_btcx"></span> -->
																													</div>

																												</div>
																											</div>


																										</form>
																										<div class="transfer-coin-button">
																											<button class="theme-btn"
																												id="btn-coinspayanyone_buy_btcx"
																												onclick="javascript:fnBuyBTCx()">
																												<span id="span_buy_btcx_btn" class="text-white">Buy
																													BTCx</span>
																											</button>
																										</div>
																									</div>
																								</div>


																							</div>
																						</div>
																						<div class="col-xl-4">
																							<div class="transfer-coin-button mb-2">
																											<button class="theme-btn"
																												id="btn-coinspayanyone_trans_btc"
																												name="btn-coinspayanyone_trans_btc"
																												onclick="fnCallPendingBuyBTCXPage()">
																												<span id="spn_view_pending_transaction_label">View
																											Pending Transactions</span>
																											</button>
																							</div>
																							<!-- <div class="text-center" style="margin-bottom:8px;">
																								<span style="color:#27A79C" id="btn-add-branch"
																									onclick="fnCallPendingBuyBTCXPage()"> <span>
																										<i class="fa fa-th-list"></i><span
																										id="spn_view_pending_transaction_label" >View
																											Pending Transactions</span>
																								</span>
																							</span>
																							</div> -->
																							<div class="wallet-transaction">
																								<% if( accountBalances!=null){
																								for(int i=0;i<accountBalances.size();i++){   
																								if(accountBalances.get(i).getAssetCode().equals("BTC")){ %>

																								<div class="wallet-transaction-box clearfix">
																									
																									<div class="wallet-transaction-name">
																										<img src="assets/images/crypto/btcx.svg"
																											alt="Stellar Bitcoin" height="50" width="50">
																									</div>
																									<div class="wallet-transaction-balance">
																										<h3 style="color: white; font-size: 20px; font-weight: bold;">
																											<%=accountBalances.get(i).getAssetBalance()+" "%>BTCX
																										</h3>
																										<!-- <span>`+porteDetails[i].lastUpdated+`</span> -->
																									</div>
																								</div>


																								<%} %>
																								<% }							
																							}else{ %>
																								<div>
																									<span id="ops_you_dont_have_stellar_btc_wal" class="text-white">You
																										don't have Stellar Bitcoin Wallet </span>
																								</div>
																								<% } %>

																							</div>
																						</div>
																					</div>
																				</div>
																			</div>
																			<!-- Swap BTCx -->
																			<div class="tab-pane " id="tab10">
																				<div class="tranfer-coin-box">
																					<div class="row">
																						<div class="col-xl-8">
																							<div class="transfercoin-left-box">
																								<div class="transfer-coin-content">
																									<div class="transfer-coin-content-box active" id="sent-coin">
																										<form id="exchange-btcx-coin-form" method="post">
																											<div
																												class="transfer-coin-input transfer-coin-select clearfix">
																												<div class="dropdown">
																													<label><span id="spn_from_label">From</span> </label>
																													<div id="destination_asset_swap_btcx"></div>
																													<!--  <select
																														class="form-control" name="coin_asset" id="coin_asset"
																														onChange="javascript: fnUpdatesenderparams(); return false">
																													</select> -->
																												</div>
																												<i class="fa fa-arrow-right"
																														id="exchange_icon"
																														style="color: #28a59a; cursor: pointer;" ></i>
																												<div class="dropdown">
																													<label id="destination_label">Destination </label> 
																													<div id="source_asset_swap_btcx"></div>
																													<!-- <select
																														class="form-control" name="receiver_asset"
																														id="receiver_asset"
																														onChange="javascript: fnUpdateReceiverParams(); return false"></select> -->
																												</div>
																											</div>
																											<div class="transfer-coin-input">
																												<label id="amount_to_label_784569" class="text-white">&nbsp;
																													Amount</label>
																												<div class="input-two clearfix">
																													<div class="input-two-box">
																														<input type="number"
																															name="destination_amount_swap_btcx"
																															id="destination_amount_swap_btcx" value=""
																															placeholder="">
													
																													</div>
																													<i class="fa fa-arrow-right"
																														id="exchange_icon"
																														style="color: #28a59a; cursor: pointer;"></i>
																													<div class="input-two-box">
																														<input type="number" name="source_amount_swap_btcx"
																															id="source_amount_swap_btcx" value=""
													
																															placeholder="" readonly="readonly"> 
													
																													</div>
													
																												</div>
																												<em class="hidden text-danger"id="offers_not_available_text">Swap is not available for the specified assets and amounts. <br> Try again later</em>
																											</div>
																											<div class="transfer-coin-button">
																												<div  id="exchange_coin_btn">
																													<button class="theme-btn"
																														id="btn-swap-coins"
																														onclick="javascript: checkIfUserHasMneonicCode(); return false">
																														<span id="spn_exchange_asset_btn">Exchange
																															Asset</span>
																													</button>
																												</div>	
																												<div class="hidden" id="exchange_coin_btn_disabled">
																													<button style="width: 75%;margin: 0px auto;"  class="hidden btn btn-default d-flex justify-content-center" disabled>
																												      <span id="btn_exchange_asset_label">Exchange
																															Asset</span>
																											     	</button>
																											    </div>
																											</div>
																											
																										</form>
																									</div>
																								</div>
													
													
																							</div>

																						</div>



																						<div class="col-xl-4">
																							<div class="transfer-coin-button mb-2 mt-5">
																								<button class="theme-btn"
																									id="btn-coinspayanyone_trans_btc"
																									name="btn-coinspayanyone_trans_btc"
																									onclick="fnCallPendingSwapBTCXPage()">
																									<span id="spn_view_pending_transaction_label">View
																								Pending Transactions</span>
																								</button>
																							</div>

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
																									<div class="wallet-transaction-balance" style="float:none;padding-top: 3px;padding-left: 20px;" >
																										<h3 style="font-size: 21px !important;color:white;font-weight: bold; margin-top: 10px;"><%=accountBalances.get(i).getAssetBalance()+" "%>PORTE </h3>
																										<!-- <span>`+porteDetails[i].lastUpdated+`</span> -->
																									</div>
																								</div>
																								<%}else if(accountBalances.get(i).getAssetCode().equals("USDC")){ %>
																									<div class="wallet-transaction-box clearfix">
																										<div class="wallet-transaction-name">
																											<img src="assets/images/crypto/usdc.png"
																											height="50" width="50">
																										</div>
																										<div class="wallet-transaction-balance" style="float:none;padding-top: 3px;padding-left: 20px;" >
																											<h3 style="font-size: 21px !important;color:white;font-weight: bold; margin-top: 10px;"><%=accountBalances.get(i).getAssetBalance()+" "%>USDC </h3>
																											<!-- <span>`+porteDetails[i].lastUpdated+`</span> -->
																										</div>
																									</div>

																								<%}else if(accountBalances.get(i).getAssetCode().equals("VESL")){ %>
																									<div class="wallet-transaction-box clearfix">
																										<div class="wallet-transaction-name">
																											<img src="assets/images/crypto/vessel.png"
																											height="50" width="50">
																										</div>
																										<div class="wallet-transaction-balance" style="float:none;padding-top: 3px;padding-left: 20px;" >
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
																										<div class="wallet-transaction-balance" style="float:none;padding-top: 3px;padding-left: 20px;" >
																											<h3 style="font-size: 21px !important;color:white;font-weight: bold; margin-top: 10px;"><%=accountBalances.get(i).getAssetBalance()+" "%>XLM </h3>
																											<!-- <span>`+porteDetails[i].lastUpdated+`</span> -->
																										</div>
																									</div>



																								<%}else if(accountBalances.get(i).getAssetCode().equals("BTC")){ %>

																									<div class="wallet-transaction-box clearfix">
																										<div class="wallet-transaction-name">
																											<img src="assets/images/crypto/btcx.svg"
																											height="50" width="50">
																											<!--  <span>Last Updated</span> -->
																										</div>
																										<div class="wallet-transaction-balance"style="float:none;padding-top: 3px;padding-left: 20px;" >
																											<h3 style="font-size: 21px !important;color:white;font-weight: bold; margin-top: 10px;"><%=accountBalances.get(i).getAssetBalance()+" "%>BTCx </h3>
																											<!-- <span>`+porteDetails[i].lastUpdated+`</span> -->
																										</div>
																									</div>												
																								<%} %>
																								<% }							
																						 }else{ %>
																								<div>
																									<span id="ops_no_external_wallets_78" class="text-white">No
																										External Wallets </span>
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
														</div>
													</div>
												</div>
											</div>

										</div>
									</div>

								</div>
								<!-- row -->


							</div>
							<!-- row end -->
						</div>
					</div>
				</div>


			</div>
		</div>
		<!-- End app-content-->

	</div>

	<form method="post" id="post-form">
		<input type="hidden" name="qs" value=""> <input type="hidden"
			name="rules" value=""> <input type="hidden" name="relno"
			value=""> <input type="hidden" name="hdnlang"
			>
	</form>
	<!-- End Page -->
	<div id="error-msg-login-page" style="display: none;">
		<span id="data-validation-error-swal-header">Oops..</span> <span
			id="data-validation-error-failed-to-get-details">Failed to get
			details</span> <span id="data-validation-problem-with-connection">Problem
			with connection</span> <span id="data-validation-check-your-data">Please
			check your data</span> <span id="data-validation-please-card-pay-with">Please
			Select Card to pay with</span> <span id="data-validation-please-coin-to-buy">Please
			select coin to buy</span> <span id="data-validation-please-payment-method">Please
			select payment method</span> <span id="data-validation-please-enter-amount">Please
			enter amount</span> <span
			id="data-validation-please-select-coin-to-transfer">Please
			select coin to transfer</span> <span
			id="data-validation-please-enter-receiver">Please enter
			receiver</span> <span id="enter-your-passsword-tittle">Enter your
			Password</span> <span id="enter-your-passsword-label">Password</span> <span
			id="data-validation-enter-your-passsword-label">Please input
			your password!</span> <span id="data-validation-enter-your-pvt-key-label">Please
			input your Private Key!</span> <span id="enter-your-pvt-key-label">Enter
			your Private Key</span> <span id="no-assets-available">No assets
			available</span> <span id="transfer-complete-label">Transfer Complete</span>
		<span id="data-validation-please-dont-have-porte-coin">You dont
			have porte coin, click Register to register</span> <span
			id="data-validation-please-select-coin-to-swap">Please select
			coin to swap</span> <span
			id="data-validation-please-select-destination-coin">Please
			select destination coin</span> <span id="data-validation-select-coin">Select
			Coin</span> <span id="data-validation-no-assets-available">No assets
			available</span>
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

	<!-- Sidebar Accordions js -->
	<script src="assets/plugins/accordion1/js/easyResponsiveTabs.js"></script>

	<!-- Custom scroll bar js-->
	<script
		src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>

	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>
	<!-- ddslick -->	
    <script type="text/javascript" src="assets/plugins/ddslick/jquery.ddslick.min.js" ></script>
	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>
	
	<script type="text/javascript"
		src="assets/plugins/dropdown/jquery.dropdown2.js"></script>
	<!-- Data tables js-->
	<script src="assets/plugins/datatable/jquery.dataTables.min.js"></script>
	<script src="assets/plugins/datatable/dataTables.bootstrap4.min.js"></script>
	<script src="assets/plugins/datatable/datatable-2.js"></script>
	<script src="assets/plugins/datatable/dataTables.responsive.min.js"></script>

	<!-- Custom js for BTC-->
	<script src="assets/js/_cust_buy_btc_via_fiat.js"></script>
	<!-- <script src="assets/js/_cust_transfer_bitcoin_page.js"></script> -->
	<!-- <script src="assets/js/_cust_swap_btc_to_btcx_page.js"></script> -->
	<!-- Custom js for BTCx-->
	<!-- <script src="assets/js/_cust_buy_btcx_page.js"></script> -->
	<!-- <script src="assets/js/_cust_exchange_btcx_page.js"></script> -->

	<script>
		  $(document).ready(function(){
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
	if(relationshipNo != null) relationshipNo =null;if(balanceObj != null) balanceObj =null;
	if(balanceAccount != null) balanceAccount =null;if(accountDetails != null) accountDetails =null;
	if(txnArray != null) txnArray =null; if(arrBTCTransaction != null) arrBTCTransaction =null;
	if(finalBalance != null) finalBalance =null;if(totalReceived != null) totalReceived =null;
	if(totalSent != null) totalSent =null;if(totalTransactions != null) totalTransactions =null;
	if ( accountBalances!=null); accountBalances=null; if ( publicKey!=null); publicKey=null;
	if (context!=null)context=null;
	if (langPref!=null) langPref=null;
}
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
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
	if(request.getParameter("hdnlang")!=null)	langPref = StringUtils.trim(request.getParameter("hdnlang"));
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
<title>Exchange Asset</title>

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
</head>
	<style>


		.wallet-transaction {
			margin: 0px 30px;
		}
		
		@media screen and (max-width: 767px) {
			.wallet-transaction {
				margin: 30px 0px 0px !important;
			}
		}
		
		@media screen and (max-width: 1199px) and (min-width: 768px) {
			.tranfer-coin-box .wallet-transaction {
				margin-top: 30px;
			}
		}

		.wallet-transaction-box .wallet-transaction-name h3{
	color: #fff !important;
}
.wallet-transaction-box .wallet-transaction-name h3,
	.wallet-transaction-balance h3 {
	font-size: 16px;
	color: #fff !important;
	margin: 0px 0px;
}
.wallet-bradcrumb h2 {
	color: #27a89d;
}
select.form-control:not([size]):not([multiple]) {
  height: 3rem;
  border-radius: 10px;
  background: white !important;
}
 </style>
<body class="app sidebar-mini rtl"
	style="position: relative; min-height: 100%; top: 0px;"
	onload="fnGetRelno()">


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
					<div class="page-header ml-5 pl-3" style="margin: auto;">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#"><span
									id="breadcrumb-item-digital-assets">Digital Assets</span></a></li>
							<li class="breadcrumb-item active" aria-current="page"><span
								id="breadcrumb-item-swap">Swap</span></li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- page -->
					<div class="">

						<!-- page-content -->
						<div class="">
							<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
								<div class="card">
									<div class="card-header">
										<h4 id="header-swap">Swap</h4>
									</div>
									<div class="row card-body">
										<div class="col-xl-8 col-lg-8 col-md-8 col-sm-8">
											<div class="transfercoin-left-box">
											<div class="transfer-coin-content">
												<div class="transfer-coin-content-box active" id="sent-coin">
													<form id="sell-porte-coin-form" method="post">

														<div
															class="transfer-coin-input transfer-coin-select clearfix">
															<div class="dropdown">
																<label style="color:#fff;"><span id="spn_from_label">From</span> </label>
																<div id="buy_coin_asset"></div>
															</div>
															<i class="fa fa-exchange"
																style="color: #28a59a; display: none;"></i>
															<div class="dropdown">
																<label style="color:#fff" id="destination_label">Destination </label> 
																<div id="sel_currency_trading"></div>
															</div>
														</div>
														<div class="transfer-coin-input">
															<label id="amount_to_label_784569" class="text-white">&nbsp;
																Amount</label>
															<div class="input-two clearfix">
																<div class="input-two-box">
																	<input type="number"
																		name="sell_amount"
																		id="sell_amount" value=""
																		placeholder="">
																</div>
																<i class="fa fa-arrow-right"
																	id="exchange_icon"
																	style="color: #28a59a; cursor: pointer;"></i>
																<div class="input-two-box">
																	<input type="number" name="receivedamount"
																		id="receivedamount" value=""
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
																	<span id="btn_exchange_asset_label">Exchange
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
										<div class="col-xl-4 col-lg-4 col-md-4 col-sm-4 mt-4">
											<div class="wallet-transaction" style="margin: 0;">
												<% if( accountBalances!=null){
														for(int i=0;i<accountBalances.size();i++){   
														if(accountBalances.get(i).getAssetCode().equals("PORTE")){ %>
													<div class="wallet-transaction-box clearfix">
															<div class="wallet-transaction-name">
																<img src="assets/images/crypto/porte.svg"
																	height="50" width="50">
															</div>
															<div class="wallet-transaction-balance" style="float: none !important;padding-top: 3px;padding-left: 20px;" >
																<h3 style="font-size: 21px !important;color:white;font-weight: bold; margin-top: 10px;"><%=accountBalances.get(i).getAssetBalance()+" "%>PORTE </h3>
															</div>
														</div>

												<%}else if(accountBalances.get(i).getAssetCode().equals("USDC")){ %>
														<div class="wallet-transaction-box clearfix">
															<div class="wallet-transaction-name">
																<img src="assets/images/crypto/usdc.png"
																	height="50" width="50">
															</div>
															<div class="wallet-transaction-balance" style="float: none !important;padding-top: 3px;padding-left: 20px;" >
																<h3 style="font-size: 21px !important;color:white;font-weight: bold; margin-top: 10px;"><%=accountBalances.get(i).getAssetBalance()+" "%>USDC </h3>
															</div>
														</div>
											

												<%}else if(accountBalances.get(i).getAssetCode().equals("VESL")){ %>
													<div class="wallet-transaction-box clearfix">
														<div class="wallet-transaction-name">
															<img src="assets/images/crypto/vessel.png"
																height="50" width="50">
														</div>
														<div class="wallet-transaction-balance" style="float: none !important;padding-top: 3px;padding-left: 20px;" >
															<h3 style="font-size: 21px !important;color:white;font-weight: bold; margin-top: 10px;"><%=accountBalances.get(i).getAssetBalance()+" "%>VESL </h3>
														</div>
													</div>
												<%}else if(accountBalances.get(i).getAssetCode().equals("XLM")){ %>
													<div class="wallet-transaction-box clearfix">
															<div class="wallet-transaction-name">
																<img src="assets/images/crypto/xlm.svg"
																	height="50" width="50">
															</div>
															<div class="wallet-transaction-balance" style="float: none !important;padding-top: 3px;padding-left: 20px;" >
																<h3 style="font-size: 21px !important;color:white;font-weight: bold; margin-top: 10px;"><%=accountBalances.get(i).getAssetBalance()+" "%>XLM </h3>
															</div>
													</div>

												<%} %>
												<% }							
												}else{ %>
												<div>
													<span id="ops_no_external_wallets_available"class="text-white">No
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
			<!-- End app-content-->

		</div>

	</div>

	<div id="error-msg-cust-crypt-pay-anyone-page" style="display: none;">
		<span id="data-validation-error-swal-header">Ups..</span> <span
			id="data-validation-error-failed-to-get-details">Failed to get
			details</span> <span id="data-validation-problem-with-connection">Problem
			with connection</span> <span id="data-validation-error-porte-coin">You
			dont have porte coin, click Register to register</span> <span
			id="data-validation-check-your-data">Please check your data</span> <span
			id="swap-data-validation-error-from">Please select coin to
			swap</span> <span id="swap-data-validation-error-destionation-coin">Please
			select destination coin</span> <span
			id="swap-data-validation-error-amount-to-swap">Please enter
			amount</span> <span id="swap-data-no-assets-available">No assets
			available</span> <span id="enter-your-passsword-tittle">Enter your
			Password</span> <span id="enter-your-passsword-label">Password</span> <span
			id="data-validation-enter-your-passsword-label">Please input
			your password!</span> <span id="data-validation-enter-your-pvt-key-label">Please
			input your Private Key!</span> <span id="enter-your-pvt-key-label">Enter
			your Private Key</span>
			<span id="span_select_coin">Select coin</span>
			
	</div>
	<form method="post" id="post-form">
		<input type="hidden" name="qs" value=""> <input type="hidden"
			name="rules" value=""> <input type="hidden" name="hdnlang"
			>
	</form>
	<!-- End Page -->
	<!-- Back to top -->
	<a href="#top" id="back-to-top"><i class="fa fa-angle-up"></i></a>
	<!-- Jquery js-->
	<script src="assets/js/vendors/jquery-3.2.1.min.js"></script>
	<!--Bootstrap.min js-->
	<script src="assets/plugins/bootstrap/popper.min.js"></script>
	<script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>
	<!-- ddslick -->	
    <script type="text/javascript" src="assets/plugins/ddslick/jquery.ddslick.min.js" ></script>
	<!--Side-menu js-->
	<script src="assets/plugins/toggle-sidebar/sidemenu.js"></script>
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
	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_cust_sell_porte_coin.js"></script>

	<script>
		$( document ).ready(function() {
			fnChangePageLanguage('<%=langPref%>');
		});
			var relno='';
			function fnGetRelno(){
				 relno = '<%=relationshipNo%>';
			}
		</script>

</body>
</html>
<%
}catch (Exception e){
	out.println ("Exception : "+e.getMessage());
}finally{
	if ( relationshipNo!=null); relationshipNo=null;
	if ( accountBalances!=null); accountBalances=null;
	if ( publicKey!=null); publicKey=null;if (langPref!=null) langPref=null;if (context!=null)context=null;
}
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*, com.pporte.utilities.Utilities,com.pporte.model.*, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%

String langPref=null;
ServletContext context = null;
ArrayList<AssetAccount> accountBalances = null;

try{
	if (request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callException(request, response, context,"Session has expired, please login again.");
		return;
	}
	if(request.getAttribute("externalwallets")!=null)
		accountBalances = (ArrayList<AssetAccount> )request.getAttribute("externalwallets");
	if(request.getAttribute("langpref")!=null) langPref = (String)request.getAttribute("langpref");
	NeoBankEnvironment.setComment(3, "JSP", "Language is "+langPref);
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
<title>Transfer Coin</title>

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

<!---Sweetalert2 css-->
<link href="assets/plugins/sweetalert2/sweetalert2.css" rel="stylesheet" />

<!---Font icons css-->
<link href="assets/plugins/iconfonts/plugin.css" rel="stylesheet" />
<link href="assets/plugins/iconfonts/icons.css" rel="stylesheet" />
<link href="assets/fonts/fonts/font-awesome.min.css" rel="stylesheet">

<!-- Custom css -->
<link href="assets/css/app.css" rel="stylesheet" />
<!-- Custom css -->
<link href="assets/css/style2.css" rel="stylesheet" />
<!-- Custom css -->
<link href="assets/css/style5.css" rel="stylesheet">
<!-- Responsive css -->
<link href="assets/css/responsive.css" rel="stylesheet" />
</head>

<body class="app sidebar-mini rtl"
	style="position: relative; min-height: 100%; top: 0px;"
	onload="fnGetCoinDetails()">
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
.transfer-coin-input {
margin-top: -14px;
}

.animation-box {
  outline: 8px ridge;
  border-radius: 2rem;
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
.transfer-coin-input input{
	box-shadow: none !important;
}
#sendamount #sendamount_cb {
background-position-x: 96%;
}
</style>

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
									id="breadcrumb-item-digital-assets">Digital Assets</span></a></li>
							<li class="breadcrumb-item active" aria-current="page"><span
								id="breadcrumb-item-transfer-coin">Transfer Coin</span></li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- page -->
					<div class="">

						<!-- page-content -->
						<div class="">
							<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
								<div class="input-group mb-3">
									<a class="theme-btn mr-2" id="btn-add-branch"
										onclick="fnCallClaimaleBalancePage()"> <span> <i
											class="fa fa-th-list"></i> <span
											id="spn_view_claimable_balance">View Claimable Balances</span>
									</span>
									</a>
								</div>
								<div class="card">
									<div class="card-header">
										<h4 id="transfer_coin_header">Transfer Coin</h4>
									</div>
									<div class="card-body myTab">

										<div class="tab-menu-heading">
											<div class="tabs-menu ">
												<!-- Tabs -->
												<ul class="nav panel-tabs">
													<li class=""><a href="#contact" class="active"
														data-toggle="tab"><span
														id="tab-header-direct-transfer">Direct Transfer</span></a></li>
													<li><a href="#profile" data-toggle="tab"><span
														id="tab-header-create-claimable-balance">Create
														Claimable Balance</span></a></li>
												</ul>
											</div>
										</div>
										<div class="tab-content border p-3" id="myTabContent">
											<div class="tab-pane fade p-0 active show" id="contact"
												role="tabpanel" aria-labelledby="contact-tab">
												<div class="tranfer-coin-box" style="margin-top: 0px">
													<div class="row">
														<div class="col-xl-7 col-lg-7 col-md-7 col-sm-7">
															<div class="transfercoin-left-box">
																<div class="transfer-coin-content">
																	<div class="transfer-coin-content-box active porte-card-content"
																		id="sent-coin" style="margin-top: 0%;" >
																		<form id="transfer-coin-form" method="post">
																			<div
																				class="transfer-coin-input transfer-coin-select clearfix">
																				<div class="dropdown" style="width: 94%;">
																				<label><span id="from_label_1">From</span>
																					</label> 
																					<!-- 
																					<select class="form-control" name="sender_asset"
																						id="sender_asset"
																						onChange="javascript: fnUpdatesenderparams(); return false">

																					</select> -->
																					<div id="sender_asset"></div>
																				</div>
																			</div>

																			<div class="transfer-coin-input">
																				<label id="send_to_label" class="text-white">Send To</label> <input
																					type="text" name="input_receiver" id="input_receiver"
																					>
																			</div>
																			<div class="image-exchange">
																				<img src="assets/images/crypto/dropdown.svg" alt="" srcset="">
																			</div>
																			<div class="transfer-coin-input">
																				<label id="amount_to_send_label" class="text-white">Amount</label> <input
																					type="text" name="sendamount" id="sendamount"
																					>
																			</div>
																			<div class="transfer-coin-input">
																				<label id="comment-label" class="text-white">Comment</label> <input
																					type="" name="commet" id="commet" value=""
																					>
																			</div>

																			<input type="hidden" name="qs" value=""> <input
																				type="hidden" name="rules" value="">
																		</form>
																		<div class="transfer-coin-button">
																			<button class="theme-btn" id="btn-coinspayanyone"
																				name="btn-coinspayanyone"
																				onclick="javascript:checkIfUserHasMneonicCode('dt')">
																				<span id="btn_send_coin">Send Coin</span>
																			</button>
																		</div>
																	</div>
																</div>


															</div>
														</div>
											<div class="col-xl-5 col-lg-5 col-md-5 col-sm-5">
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
											<div class="tab-pane fade p-0" id="profile" role="tabpanel"
												aria-labelledby="profile-tab">
												<div class="tranfer-coin-box" style="margin-top: 0px">
													<div class="row">
														<div class="col-xl-7 col-lg-7 col-md-7 col-sm-7">
															<div class="transfercoin-left-box">
																<div class="transfer-coin-content">
																	<div class="transfer-coin-content-box active porte-card-content"
																		id="sent-coin" style="margin-top: 0%;">
																		<form id="transfer-coin-form-cb" method="post">
																			<div
																				class="transfer-coin-input transfer-coin-select clearfix">
																				<div class="dropdown" style="width: 94%;">
																					<label><span id="from_label_2">From</span>
																					</label> 
																					<!-- <select class="form-control"
																						name="sender_asset_cb" id="sender_asset_cb"
																						onChange="javascript: fnUpdatesenderparamsCB(); return false">

																					</select> -->
																					<div id="sender_asset_cb"></div>
																				</div>
																			</div>
																			<div class="transfer-coin-input">
																				<label id="send_to_label_2" class="text-white">Send To</label> <input
																					type="" name="input_receiver_cb"
																					id="input_receiver_cb" value="">
																			</div>
																			 <div class="image-exchange">
																				<img src="assets/images/crypto/dropdown.svg" alt="" srcset="">
																			</div>
																			<div class="transfer-coin-input">
																				<label id="send_to_label_2" class="text-white">Amount</label> <input
																					type="" name="sendamount_cb"
																					id="sendamount_cb" value="">
																			</div>
																			<div class="transfer-coin-input">
																				<label id="comment_label_2" class="text-white">Comment</label> <input
																					type="" name="commet_cb" id="commet_cb" value="">
																			</div>

																			<input type="hidden" name="qs" value=""> <input
																				type="hidden" name="rules" value="">
																		</form>
																		<div class="transfer-coin-button">
																			<button class="theme-btn" id="btn-coinspayanyone_cb"
																				name="btn-coinspayanyone_cb"
																				onclick="javascript:checkIfUserHasMneonicCode('cb')">
																				<span id="send_coin_label_12">Send Coin</span>
																			</button>
																		</div>
																	</div>
																</div>


															</div>
														</div>
														<div class="col-xl-5 col-lg-5 col-md-5 col-sm-5">
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
						</div>
					</div>


				</div>
			</div>
			<!-- End app-content-->

		</div>

	</div>
	<form method="post" id="post-form">
		<input type="hidden" name="qs" value=""> <input type="hidden"
			name="rules" value=""> <input type="hidden" name="hdnlang"
			>
	</form>

	<div id="error-msg-cust-crypt-pay-anyone-page" style="display: none;">
		<span id="data-validation-error-swal-header">Oops..</span> <span
			id="data-validation-error-failed-to-get-details">Failed to get
			details</span> <span id="data-validation-problem-with-connection">Problem
			with connection</span> <span id="data-validation-check-your-data">Please
			check your data</span> <span id="data-validation-no-assets-available">No
			assets available</span> <span id="pay-anyone-data-validation-error-from">Please
			select the Coin</span> <span id="pay-anyone-data-validation-error-sendto">Please
			enter receiver</span> <span
			id="pay-anyone-data-validation-error-amounttosend">Please
			enter Amount to send</span> <span
			id="pay-anyone-data-validation-coin-destination">Select
			Destination Coin</span> <span id="enter-your-passsword-tittle">Enter
			your Password</span> <span id="enter-your-passsword-label">Password</span> <span
			id="data-validation-enter-your-passsword-label">Please input
			your password!</span> <span id="data-validation-enter-your-pvt-key-label">Please
			input your Private Key!</span> <span id="enter-your-pvt-key-label">Enter
			your Private Key</span>
			<span id="span_select_coin">Select coin</span>
	</div>
	<!-- End Page -->

	<!-- Back to top -->
	<a href="#top" id="back-to-top"><i class="fa fa-angle-up"></i></a>
	<!-- Jquery js-->
	<script src="assets/js/vendors/jquery-3.2.1.min.js"></script>
	<!--Bootstrap.min js-->
	<script src="assets/plugins/bootstrap/popper.min.js"></script>
	<script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>
	<!-- ddslick -->	
    <script type="text/javascript" src="assets/plugins/ddslick/jquery.ddslick.min.js" ></script>
	<!--Side-menu js-->
	<script src="assets/plugins/toggle-sidebar/sidemenu.js"></script>

	<!-- Sidebar Accordions js -->
	<script src="assets/plugins/accordion1/js/easyResponsiveTabs.js"></script>

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>

	<!-- Custom scroll bar js-->
	<script
		src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>
	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>
	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>

	<!-- Custom js-->
	<script src="assets/js/_cust_porte_transfer_coin.js"></script>
	<script src="assets/js/custom.js"></script>

	<script src="assets/plugins/dropdown/jquery.dropdown2.js"></script>

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
	if (langPref!=null) langPref=null; if (context!=null)context=null;
}
%>
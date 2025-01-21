<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
String relationshipNo=null;
String publicKey=null;
List<AssetCoin> digitalCurrencies = null;
List<AssetCoin> fiatCurrencies = null;
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
	if(request.getAttribute("fiatcurrencies")!=null)
		fiatCurrencies = (List<AssetCoin> )request.getAttribute("fiatcurrencies");
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
<title>Fiat Remittance</title>

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
		#sel_fiat_currency{
	background-image: url('./assets/images/crypto/drop-down.svg');
	    background-repeat: no-repeat;
	    background-position-x: 96%;
	    background-position-y: center;
	    background-size: 16px;
	    appearance: none;
		}
#sel_digital_currency{
background-image: url('./assets/images/crypto/drop-down.svg');
	    background-repeat: no-repeat;
	    background-position-x: 96%;
	    background-position-y: center;
	    background-size: 16px;
	    appearance: none;
}
div[data-acc-step]:not(.open) h5 {
  color: black;
}
.badge-primary {
  color: black;
  }

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
								id="breadcrumb-item-trde-currency">Trade Currency</span></li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
							<!--row open-->
							<div class="row">
								<div class="col-lg-12">
									<div class="porte-card-content">
										<div class="card-header">
											<h3 class="card-title">
												<span id="header-fiat-currency-remittance">Fiat
													Currency Remittance</span>
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
												<h3>
													<span id="header-currency-details" style="color:#27A89D;">Currency
														Details</span>
												</h3>
														<form id="form" method="post">
															<div class="my-3">
																<div class="row">
																	<div class="col-lg-6 col-md-12">
																		<div class="form-group ">
																			<label class="form-label text-white"
																				id="select-source-currency-label">Select
																				Source Currency </label>
																				<div id="buy_coin_asset"></div>
																			
																		</div>
																	</div>
																	<div class="col-lg-6 col-md-12">
																		<div class="form-group ">
																			<label class="form-label text-white"
																				id="span_destination_currency_label">Select
																				Destination Currency</label>
																				 <div id="sel_currency_trading"></div>
														
																		</div>
																	</div>
																</div>
																<div class="image-exchange">
																<img src="assets/images/crypto/dropdown.svg" alt="" srcset="">
															</div>
																<div class="row">
																		<div class="col-lg-6 col-md-6">
																			<label class="form-label text-white"> <span id="source_amount_label">Source Amount
																					in </span> <strong>USD</strong>
																				</label>
																		</div>
																		<div class="col-lg-6 col-md-6">
																			<label class="form-label text-white" > <span id="spn_expected_amount" class="text-white">Expected
																							Amount in </span> <span> <strong
																							id="span_expected_code"></strong>
																					</span> <em class="hidden text-warning"
																						id="expected_amount_warning_text"> This rate
																							is valid for the next 24 hours </em>
																			</label>
																		</div>
																	</div>															
																	<div class="transfer-coin-input">
																		<div class="input-two clearfix">
																		
																			<div class="input-two-box">
																				<input type="number" name="source_amount"
																				id="source_amount"
																				onkeyup="javascript: getExpectedAmount(); return false;"
																				class="form-control styled-input-field">
																			</div>
																			<i class="fa fa-arrow-right" id="exchange_icon" style="color: #28a59a; cursor: pointer;"></i>
																			
																			<div class="input-two-box">
																				 <input type="number" name="expected_amount"
																				id="expected_amount" class="form-control styled-input-field" value="0" readonly>
																			</div>
																		</div>
																	</div>
										
														<h3>
															<span id="receiver_details_header" style="color:#27A89D;">Enter Receiver
															Details</span>
														</h3>
															<div class="row">
																<div class="col-lg-6 col-md-12">
																	<div class="form-group">
																		<label id="receiver_fullname_label" class="text-white">Receiver's
																			Name <span style="color: red;">*</span>
																		</label> <input type="text" name="receiver_name"
																			id="receiver_name" class="form-control styled-input-field">
																	</div>
																</div>
																<div class="col-lg-6 col-md-12">
																	<div class="form-group">
																		<label id="receiver_email_label" class="text-white">Receiver's
																			Email <span style="color: red;">*</span>
																		</label> <input type="email" name="receiver_email"
																			id="receiver_email" class="form-control styled-input-field">
																	</div>
																</div>
															</div>
															<div class="row">
																<div class="col-lg-6 col-md-12">
																	<div class="form-group">
																		<label id="receiver_bank_name" class="text-white">Receiver's Bank
																			Name<span style="color: red;">*</span>
																		</label> <input type="text" name="receiver_bank_name"
																			id="receiver_bank_name" class="form-control styled-input-field">
																	</div>
																</div>
																<div class="col-lg-6 col-md-12">
																	<div class="form-group">
																		<label id="receiver_bank_code" class="text-white">Receiver's Bank
																			Code <span style="color: red;">*</span>
																		</label> <input type="text" name="receiver_bank_code"
																			id="receiver_bank_code" class="form-control styled-input-field">
																	</div>
																</div>
															</div>
															<div class="row">
																<div class="col-lg-6 col-md-12">
																	<div class="form-group">
																		<label id="receiver_account_number" class="text-white">Receiver's
																			Account Number<span style="color: red;">*</span>
																		</label> <input type="text" name="receiver_account_no"
																			id="receiver_account_no" class="form-control styled-input-field">
																	</div>
																</div>
															</div>
											</form>
											<div class="transfer-coin-button">
													<button class="theme-btn" id="btn-coinspayanyone"
														onclick="javascript:checkIfUserHasMneonicCode()">
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
			name="rules"> 
			<input type="hidden" name="hdnlang">
	</form>
	<!-- End Page -->
	<!-- Back to top -->
	<a href="#top" id="back-to-top"><i class="fa fa-angle-up"></i></a>
	<div id="error-msg-login-page" style="display: none;">
		<span id="data-validation-error-swal-header">Oops..</span> <span
			id="data-validation-error-failed-to-get-details">Failed to get
			details</span> <span id="data-validation-problem-with-connection">Problem
			with connection</span> <span id="data-validation-check-your-data">Please
			check your data</span> <span id="data-validation-please-select-currency">Please
			select Currency</span> <span id="data-validation-please-select-coin">Please
			select Coin</span> <span id="data-validation-please-enter-amount">Please
			enter amount</span> <span id="data-validation-required-field">This
			field is required</span> <span id="data-validation-email">This field
			has to be email</span> <span id="no-offers-available">No offers
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
	</div>
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
	<script type="text/javascript"
		src="assets/plugins/dropdown/jquery.dropdown2.js"></script>
	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>
	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>
	<!-- ddslick -->	
    <script type="text/javascript" src="assets/plugins/ddslick/jquery.ddslick.min.js" ></script>
	<!--Accordion-Wizard-Form js-->
	<script
		src="assets/plugins/accordion-Wizard-Form/jquery.accordion-wizard.min.js"></script>
	<!-- Custom js-->
	<script src="assets/js/_cust_fiat_remittance_page.js"></script>
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
	if ( relationshipNo!=null); relationshipNo=null;
	if ( digitalCurrencies!=null); digitalCurrencies=null;
	if ( fiatCurrencies!=null); fiatCurrencies=null;
	if ( publicKey!=null); publicKey=null;
	if (context!=null)context=null;
	if (langPref!=null) langPref=null;
}
%>

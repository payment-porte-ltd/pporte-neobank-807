<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*, org.apache.commons.lang3.StringUtils, com.pporte.utilities.Utilities, java.util.concurrent.*, java.util.*"%>
<%
String relationshipNo=null;
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
<title>Set up Wallet</title>

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

<!---Font icons css-->
<link href="assets/plugins/iconfonts/plugin.css" rel="stylesheet" />
<link href="assets/plugins/iconfonts/icons.css" rel="stylesheet" />
<link href="assets/fonts/fonts/font-awesome.min.css" rel="stylesheet">
<!---Sweetalert2 css-->
<link href="assets/plugins/sweetalert2/sweetalert2.css" rel="stylesheet" />

<!-- Forn-wizard css-->
<link href="assets/plugins/forn-wizard/css/forn-wizard.css"
	rel="stylesheet" />
<link href="assets/plugins/formwizard/smart_wizard.css" rel="stylesheet">
<link href="assets/plugins/formwizard/smart_wizard_theme_dots.css"
	rel="stylesheet">

<!-- Custom css -->
<link href="assets/css/app.css" rel="stylesheet" />
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
</style>

</head>

<body class="app sidebar-mini rtl" onload="getCustomerCryptoAssets()">

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
								id="breadcrumb-item-set-up-wallet">Set up Wallet</span></li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- page -->
							
								<div class="row">
									<div class="col-md-12">
										<div class="porte-card-content">
											<div class="card-header">
												<h3 class="card-title">
													<span id="card-title-set-up-wallet">Set up Wallet</span>
												</h3>
											</div>
											<div class="card-body p-6">
												<div class="panel panel-primary">
													<div class="panel-body tabs-menu-body">
														<div class="tab-content">
															<div class="tab-pane active " id="tab11">
																<div class="col-lg-12">
																	<div id="card_register_mnemonic" >
																	<div class="row">
																	<p style="color:#27A79C;font-size:18px;"> <span id="setup_label">To set up your bitcoin and stellar wallet, answer the questions below </span><br> <span id="proceed_label">and proceed with the required steps:</span> </p>
																	</div>
																		<div id="hide_stellar">
																			
																				<div class="form-group text-center"
																					id="stellar-account-choice">
																					<label class="form-label"> <span style="font-size:24px;"
																						id="question_label">Do you have either
																							Bitcoin Wallet or Stellar Account?</span></label>
																					<div class="row justify-content-md-center">
																							<div class="col-3">
																								<button type="button" class="btn btn-block float-left "
																									 onclick="javascript:fnBitcoinPresent('Yes');return false;" style="background:#27A79C;border-radius: 10px;">
																									<span  style="color: white;">Yes</span>
																								</button>
																							</div>
																							<div class="col-3">
																								<button type="button" class="btn btn-block float-right "
																									 onclick="javascript:fnBitcoinPresent('No');return false;" style="background:#0c0a36;border-radius: 10px;">
																									<span style="color: white;">No</span>
																								</button>
																							</div>
																					</div>
																				</div>
																				<div id="stellar-not-present" style="display: none;">
																					<form id="register-mnemonic" method="post" style="margin-top:25px;">
																						<div class="form-group text-center">
																							<label class="custom-control custom-checkbox">
																								<input type="checkbox"
																								class="custom-control-input"
																								name="create_stellar" id="create_stellar" value=""> <span
																								class="custom-control-label" id="checkbox_label" style="font-size:18px">Click
																									the check box if you want to proceed to register
																									Bitcoin and Stellar account</span>
																							</label>
																						</div>
																						<input type="hidden" name="qs" value="">
																						<input type="hidden" name="rules" value="">
																							
																					</form>	
																					<div class="row justify-content-md-center"
																									id="div-btn-registr-stellar">
																									<div class="col-6">
																										<button class="btn new-default-btn" style="width:100%"
																										id="btn-register-stellar-ac"
																										name="btn-register-bitcoin-ac"
																										onclick="javascript:fnRegisterMneumonicSetUpWallet()">
																										<span id="register_label">Register</span>
																									</button>
																									</div>
																									
																					</div>
																				</div>
																			
																			
																			
																		</div>
																		<div class="hidden" id="mneumonic_card">
																			<div class="col-lg-12"
																				style="height: 213px; margin-bottom: 200px;">
																				<div class="card-header-text" style="margin: 30px;">
																					<h2 class="card-title text-center"
																						id="scroll-header-label">Scroll to get the
																						next Mnemonic code</h2>
																					<p>
																						<span id="spn_pragraph_words">Please write
																							these words in the order they follow each other
																							and store in a safe place <br> Scroll to
																							your left to reveal the next words >>
																						</span>
																					</p>
																				</div>
																				<div class="card-body" style="background: darkcyan;">
																					<div id="carousel-controls" class="carousel slide"
																						data-interval="false">
																						<div class="carousel-inner" id="carousel-font">
																							<div class="carousel-item active">
																								<div
																									style="font-size: 20px; text-align: center; font-weight: bold; color: white;">
																									1. <span id="first_corousel"></span>
																								</div>
																							</div>
																							<div class="carousel-item">
																								<div
																									style="font-size: 20px; text-align: center; font-weight: bold; color: white;">
																									2. <span id="second_corousel"></span>
																								</div>
																							</div>
																							<div class="carousel-item">
																								<div
																									style="font-size: 20px; text-align: center; font-weight: bold; color: white;">
																									3. <span id="third_corousel"></span>
																								</div>
																							</div>
																							<div class="carousel-item">
																								<div
																									style="font-size: 20px; text-align: center; font-weight: bold; color: white;">
																									4. <span id="fourth_corousel"></span>
																								</div>
																							</div>
																							<div class="carousel-item">
																								<div
																									style="font-size: 20px; text-align: center; font-weight: bold; color: white;">
																									5. <span id="fifth_corousel"></span>
																								</div>
																							</div>
																							<div class="carousel-item">
																								<div
																									style="font-size: 20px; text-align: center; font-weight: bold; color: white;">
																									6. <span id="sixth_corousel"></span>
																								</div>
																							</div>
																							<div class="carousel-item">
																								<div
																									style="font-size: 20px; text-align: center; font-weight: bold; color: white;">
																									7. <span id="seventh_corousel"></span>
																								</div>
																							</div>
																							<div class="carousel-item">
																								<div
																									style="font-size: 20px; text-align: center; font-weight: bold; color: white;">
																									8. <span id="eighth_corousel"></span>
																								</div>
																							</div>
																							<div class="carousel-item">
																								<div
																									style="font-size: 20px; text-align: center; font-weight: bold; color: white;">
																									9. <span id="nineth_corousel"></span>
																								</div>
																							</div>
																							<div class="carousel-item">
																								<div
																									style="font-size: 20px; text-align: center; font-weight: bold; color: white;">
																									10. <span id="10th_corousel"></span>
																								</div>
																							</div>
																							<div class="carousel-item">
																								<div
																									style="font-size: 20px; text-align: center; font-weight: bold; color: white;">
																									11. <span id="11th_corousel"></span>
																								</div>
																							</div>
																							<div class="carousel-item">
																								<div
																									style="font-size: 20px; text-align: center; font-weight: bold; color: white;">
																									12. <span id="12th_corousel"></span>
																								</div>
																							</div>
																						</div>
																						<a class="carousel-control-prev"
																							href="#carousel-controls" role="button"
																							data-slide="prev"> <span
																							class="carousel-control-prev-icon"
																							aria-hidden="true"></span> <span class="sr-only"
																							id="spn_previous_btn">Previous</span>
																						</a> <a class="carousel-control-next"
																							href="#carousel-controls" role="button"
																							data-slide="next"> <span
																							class="carousel-control-next-icon"
																							aria-hidden="true"></span> <span class="sr-only"
																							id="next_btn">Next</span>
																						</a>
																					</div>

																				</div>
																				<div class="card-footer"
																					style="color: white; margin-left: -12px; margin-right: -13px;">
																					<form id="register-bitcoin" method="post">

																						<div class="form-group">
																							<div class="custom-checkbox custom-control">
																								<input type="checkbox" data-checkboxes="mygroup"
																									class="custom-control-input"
																									id="saved_mneumonic" name="saved_mneumonic">
																								<label for="saved_mneumonic"
																									id="confirmation-check-box-label"
																									class="custom-control-label"  style="font-size:18px;" >Click to
																									confirm that you have saved the mnemonic code</label>
																							</div>
																						</div>
																						<div id="div_btn_reg_stellar">
																								<div  class="alert alert-warning alert-dismissible fade show" role="alert">
																									 <div class="row"> <!-- add no-gutters to make it narrower -->
																								        <div class="col-auto align-self-start"> <!-- or align-self-center -->
																								             <i class="fa fa-exclamation-circle" style="color: #6E5404;font-size:36px;"></i>
																								        </div>
																								        <div class="col" style="margin:auto;">
																								           <span id="spn_we_dont_store_private">We never store your private key in our system, please save your 12 words safely and in correct sequence</span>
																								        </div>
																								     </div> 
																									  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
																									    <span aria-hidden="true">&times;</span>
																									  </button>
																							   </div>
																							<!-- <div class="form-group">
																										<label class="form-label">Enter your password</label> 
																										<input type="password" class="form-control styled-input-field" name="password" id="password" value="">
																									</div> -->
																						</div>
																						<input type="hidden" name="qs" value=""> <input
																							type="hidden" name="rules" value="">
																					</form>
																					<div class="transfer-coin-button">
																						<button class="btn  btn-block new-default-btn"
																							id="btn_register_btc" name="btn_register_btc"
																							onclick="javascript: fnPasswordSetUpNewWallet()">
																							<span id="register_label_reg">Register</span>
																						</button>
																					</div>
																				</div>
																			</div>
																		</div>
																		<div id="stellar-present" style="display: none;margin-top:30px">
																			<form id="register-has-mnemonic-code" method="post">
																				<div class="form-group text-center">
																					<label class="form-label"> <span style="font-size:24px;"
																						id="question_label_1">Do you have Mnemonic
																							code generated by a previous wallet?</span></label>
																						<div class="row justify-content-md-center">
																							<div class="col-3">
																								<button type="button" class="btn btn-block float-left "
																									 onclick="javascript:fmnemoniccodePresent('Yes');return false;" style="background:#27A79C;border-radius: 10px;">
																									<span  style="color: white;">Yes</span>
																								</button>
																							</div>
																							<div class="col-3">
																								<button type="button" class="btn btn-block float-right "
																									 onclick="javascript:fmnemoniccodePresent('No');return false;" style="background:#0c0a36;border-radius: 10px;">
																									<span style="color: white;">No</span>
																								</button>
																							</div>
																					</div>
																				</div>

																				<div class="hidden" id="mnemonic-present">
																				<p style="color:#27A79C;font-size:18px;"> Enter the Mnemonic Code From your previously generated wallet. </p>
																					<div id="smartwizard-3">
																						<ul class="mt-0" style="margin: 0;">
																							<li><a href="#step-1"><span
																									id="1st_to_4th_word" class="stepper_label">1st Word to 4th
																										Word</span></a></li>
																							<li><a href="#step-2"><span
																									id="5th_to_8th_word"  class="stepper_label" >5th Word to 8th
																										Word</span></a></li>
																							<li><a href="#step-3"><span
																									id="9th_to_12th_word"  class="stepper_label">9th Word to 12th
																										Word</span></a></li>
																							<li><a href="#step-4"><span
																									id="btc_account_details"  class="stepper_label" >BTC Account
																										Details</span></a></li>
																							
																						</ul>
																						<div>
																							<div id="step-1" class="container">
																								<div class="row justify-content-center">
																									<div class="col-5 form-group">
																										<label id="1st_mnemonic_code">First
																											Mnemonic Code</label> <input type="text"
																											class="form-control styled-input-field" id="first_code"
																											name="first_code"
																											placeholder="Enter First Mnemonic Code">
																									</div>
																									<div class="col-5 form-group">
																										<label id="2nd_mnemonic_code">Second
																											Mnemonic Code</label> <input type="text"
																											class="form-control styled-input-field" id="second_code"
																											name="second_code"
																											placeholder="Enter Second Mnemonic Code">
																									</div>
																								</div>

																								<div class="row justify-content-center">
																									<div class="col-5 form-group">
																										<label id="3rd_mnemonic_code">Third
																											Mnemonic Code</label> <input type="text"
																											class="form-control styled-input-field" id="third_code"
																											name="third_code"
																											placeholder="Enter Third Mnemonic Code">
																									</div>

																									<div class="col-5 form-group">
																										<label id="4th_mnemonic_code">Fourth
																											Mnemonic Code</label> <input type="text"
																											class="form-control styled-input-field" id="fourth_code"
																											name="fourth_code"
																											placeholder="Enter Fourth Mnemonic Code">
																									</div>
																								</div>
																							</div>

																							<div id="step-2" class="">
																								<div class="row justify-content-center">
																									<div class="col-5 form-group">
																										<label id="5th_mnemonic_code">Fifth
																											Mnemonic Code</label> <input type="text"
																											class="form-control styled-input-field" id="fifth_code"
																											name="fifth_code"
																											placeholder="Enter Fifth Mnemonic Code">
																									</div>
																									<div class="col-5 form-group">
																										<label id="6th_mnemonic_code">Sixth
																											Mnemonic Code</label> <input type="text"
																											class="form-control styled-input-field" id="sixth_code"
																											name="sixth_code"
																											placeholder="Enter Sixth Mnemonic Code">
																									</div>
																								</div>
																								<div class="row justify-content-center">
																									<div class="col-5 form-group">
																										<label id="7th_mnemonic_code">Seventh
																											Mnemonic Code</label> <input type="text"
																											class="form-control styled-input-field" id="seventh_code"
																											name="seventh_code"
																											placeholder="Enter Seventh Mnemonic Code">
																									</div>
																									<div class="col-5 form-group">
																										<label id="8th_mnemonic_code">Eighth
																											Mnemonic Code</label> <input type="text"
																											class="form-control styled-input-field" id="eight_code"
																											name="eight_code"
																											placeholder="Enter Eighth Mnemonic Code">
																									</div>
																								</div>

																							</div>

																							<div id="step-3" class="">
																								<div class="row justify-content-center">
																									<div class="col-5 form-group">
																										<label id="9th_mnemonic_code">Ninth
																											Mnemonic Code</label> <input type="text"
																											class="form-control styled-input-field" id="nineth_code"
																											name="nineth_code"
																											placeholder="Enter Ninth Mnemonic Code">
																									</div>
																									<div class="col-5 form-group">
																										<label id="10th_mnemonic_code">Tenth
																											Mnemonic Code</label> <input type="text"
																											class="form-control styled-input-field" id="tenth_code"
																											name="tenth_code"
																											placeholder="Enter Tenth Mnemonic Code">
																									</div>
																								</div>
																								<div class="row justify-content-center">
																									<div class="col-5 form-group">
																										<label id="11th_mnemonic_code">Eleventh
																											Mnemonic Code</label> <input type="text"
																											class="form-control styled-input-field" id="eleventh_code"
																											name="eleventh_code"
																											placeholder="Enter Eleventh Mnemonic Code">
																									</div>
																									<div class="col-5 form-group">
																										<label id="12th_mnemonic_code">Twelveth
																											Mnemonic Code</label> <input type="text"
																											class="form-control styled-input-field" id="twelve_code"
																											name="twelve_code"
																											placeholder="Enter Twelveth Mnemonic Code">
																									</div>
																								</div>
																							</div>

																							<div id="step-4" class="">
																								<label><strong
																									id="Enter_bitcoin_details">Enter
																										Bitcoin Details</strong></label>
																								
																									
																								<div class="row" id="div_btc_inputs">


																									<div class="col-5 form-group">
																										<label class="form-label"><span
																											id="btc_address_label">BTC Address</span><span
																											style="color: red;">*</span></label> <input
																											type="text" name="input_btc_address"
																											id="input_btc_address" class="form-control styled-input-field"
																											placeholder="Enter your BTC Address">
																									</div>
																									<div class="col-5 form-group">
																										<label class="form-label"><span
																											id="public_key_label"> BTC Public Key</span><span
																											style="color: red;">*</span></label> <input
																											type="text" name="input_btc_pubkey"
																											id="input_btc_pubkey" class="form-control styled-input-field"
																											placeholder="Enter password to store mnemonic code">
																									</div>
																									<div class="col-12 form-group">
																									<div id="stellar-generate-stellar-account">
																										<div class="form-group">
																											<label class="custom-control custom-checkbox">
																												<input type="checkbox"
																												class="custom-control-input"
																												name="generate_stellar_account" value="">
																												<span class="custom-control-label"
																												id="checkbox_label_1">Click the check
																													box if you want to proceed to register
																													Stellar Account</span>
																											</label>
																										</div>
																										<div  class="alert alert-warning alert-dismissible fade show" role="alert">
																										 <div class="row"> <!-- add no-gutters to make it narrower -->
																									        <div class="col-auto align-self-start"> <!-- or align-self-center -->
																									             <i class="fa fa-exclamation-circle" style="color: #6E5404;font-size:36px;"></i>
																									        </div>
																									        <div class="col" style="margin:auto;">
																									           <span id="spn_dont_store_cvv">We never store your private key in our system</span><br />
																									        </div>
																									     </div> 
																										  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
																										    <span aria-hidden="true">&times;</span>
																										  </button>
																								   </div>
																									</div>
																								</div>
																								
																									
																								</div>
																							</div>

						
																						</div>
																					</div>
																				</div>
																				<input type="hidden" name="qs" value=""> <input
																					type="hidden" name="rules" value="">

																			</form>
																			<div class="hidden" id="mnemoni-not-present"
																				style="margin-bottom: 51px; margin-top: 52px;">
																				<form id="has_no_mnenmonic_code" method="post">
																					<div class="row">
																						<div class="col">
																							<div class="form-group">
																								<label class="form-label"><span
																									id="btc_address_label_1">BTC Address</span><span
																									style="color: red;">*</span></label> <input type="text"
																									name="btc_address" id="btc_address"
																									class="form-control styled-input-field"
																									placeholder="Enter your BTC Address">
																							</div>
																							<div class="form-group">
																								<label class="form-label"><span
																									id="password_key_label_1">BTC Public Key</span><span
																									style="color: red;">*</span></label> <input type="text"
																									name="btc_public_key" id="btc_public_key"
																									class="form-control styled-input-field"
																									placeholder="Enter BTC Public Key">
																							</div>
																						</div>
																						<div class="col">
																							<div class="form-group">
																								<label class="form-label"><span
																									id="stellar_pub_key_label">Stellar
																										Public Key</span><span style="color: red;">*</span></label> <input
																									type="text" name="stellar_public_key"
																									id="stellar_public_key" class="form-control styled-input-field"
																									placeholder="Enter Stellar Public Key">
																							</div>
																							<div class="form-group">
																								<label class="form-label"><span
																									id="stellar_private_key_label">Stellar
																										Private Keys</span><span style="color: red;">*</span></label>
																								<input type="password"
																									name="stellar_private_key"
																									id="stellar_private_key" class="form-control styled-input-field"
																									placeholder="Enter Stellar Private Key">
																							</div>
																						</div>
																					</div>

																					<div  class="alert alert-warning alert-dismissible fade show" role="alert">
																					 <div class="row"> <!-- add no-gutters to make it narrower -->
																				        <div class="col-auto align-self-start"> <!-- or align-self-center -->
																				             <i class="fa fa-exclamation-circle" style="color: #6E5404;font-size:36px;"></i>
																				        </div>
																				        <div class="col" style="margin:auto;">
																				           <span id="spn_we_are_unable_to_generate_nmonic_label">The system is unable to create any mnemonic code with the existing private key.</span><br />
																						   <span id="spn_always_use_private_key">You should always use the private key for signing the transaction.</span>
																				        </div>
																				     </div> 
																					  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
																					    <span aria-hidden="true">&times;</span>
																					  </button>
																					</div>

																					<input type="hidden" name="qs" value=""> <input
																						type="hidden" name="rules" value="">
																				</form>
																				<div class="transfer-coin-button">
																					<button type="button" class="btn btn-block"
																						id="no_mnemonic_btn_register_stellar"onclick="javascript: fnPasswordSetUpWallet()" style="background:#27A89D;border-radius: 10px;" onclick="javascript:fnLogin();return false;">
																						<span id="register_label_2" style="color: white;">Register</span>
																					</button>
																				</div>
																			</div>
																		</div>
																	</div>
																</div>

															</div>
															<!-- Create Wallet Tab -->
															<div class="tab-pane  " id="tab21">
																<!-- row -->
																<div
																	class="row justify-content-center align-self-center">
																	<div class="col-xl-6 col-lg-12 col-md-12 col-sm-12">
																		<div class="tab-content border p-3" id="myTabContent">
																			<div class="tab-pane fade p-0" id="profile"
																				role="tabpanel" aria-labelledby="profile-tab">

																			</div>
																			<div class="tab-pane fade p-0 active show"
																				id="contact" role="tabpanel"
																				aria-labelledby="contact-tab">
																				<div class="card-body">
																					<form id="create-wal-form" method="post">
																						<!-- <div class="form-group">
																								<label class="form-label">Select Wallet Asset <span style="color: red;">*</span></label>
																								<select name="asset_value" id="asset_value" class="form-control styled-input-field custom-select" >
																								</select>	
																							</div> -->
																						<div class="dropdown" style="width: 94%;">
																							<label class="form-label"
																								id="select_wallet_asset_label">Select
																								Wallet Asset <span style="color: red;">*</span>
																							</label>
																							<div class="row" style="margin-left: 13px;"
																								id="wallet_assets_div"></div>
																						</div>


																						<div class="form-group">
																							<label class="form-label"><span
																								id="spn_user_wallet_name_label">User
																									Wallet Name</span><span style="color: red;">*</span></label> <input
																								type="text" name="input_wallet_desc"
																								id="input_wallet_desc" class="form-control styled-input-field"
																								placeholder="Enter Description">
																						</div>

																						<input type="hidden" name="qs" value=""> <input
																							type="hidden" name="rules" value="">
																					</form>
																					<div class="transfer-coin-button">
																						<button class="theme-btn" id="btn_create_wal"
																							name="btn_create_wal"
																							style="background: 00008B; border: none; border-radius: 26px;"
																							onclick="javascript:checkIfUserHasMneonicCodeCreateWal()">
																							<span id="spn_create_wallet_btn">Create
																								Wallet</span>
																						</button>
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
											<div id="cardavailable" class="hidden">
												<!-- Modal -->
												<div id="showResultsModal" class="modal fade" role="dialog">
													<div class="modal-dialog modal-dialog-centered"
														role="document">
														<!-- Modal content-->
														<div class="modal-content">
															<div class="modal-header">
																<h5 class="modal-title"
																	id="spn_bitcoin_and_stellar_keys">Bitcoin and
																	Stellar Keys</h5>
																<button type="button" class="btn-close"
																	data-bs-dismiss="modal" aria-label="Close"></button>
															</div>
															<div class="modal-body">
																<form>
																	<div class="mb-3">
																		<label for="bitcoin-address" class="form-label"
																			id="bitcoin_address_45">Bitcoin Address</label> <input
																			type="text" class="form-control styled-input-field"
																			id="idbitcoinaddress" readonly />
																	</div>
																	<div class="mb-3">
																		<label for="bitcoin-address" class="form-label"
																			id="btc_public_key_label">BTC Public Key</label> <input
																			type="text" class="form-control styled-input-field" id="idbtcpublickkey"
																			readonly />
																	</div>
																	<div class="mb-3">
																		<label for="bitcoin-address" class="form-label"
																			id="btc_private_key_label">BTC Private Key</label> <input
																			type="text" class="form-control styled-input-field" id="idbtcprivatekey"
																			readonly />
																	</div>
																	<div class="mb-3">
																		<label for="bitcoin-address" class="form-label"
																			id="stellar_public_key_label">Stellar Public
																			Key</label> <input type="text" class="form-control styled-input-field"
																			id="idstlpublickkey" readonly />
																	</div>
																	<div class="mb-3">
																		<label for="bitcoin-address" class="form-label"
																			id="stellar_private_key_34">Stellar Private
																			Key</label> <input type="text" class="form-control styled-input-field"
																			id="idstlprivatekey" readonly />
																	</div>
																	<input id="allinfo" type="text" class="hidden" />
																</form>
															</div>
															<div class="modal-footer">
																<button type="button" class="btn btn-secondary"
																	data-bs-dismiss="modal">
																	<span id="modal_close_btn_label">Close</span>
																</button>
																<button type="button" class="btn btn-primary"
																	id="btncopy">
																	<span id="modal_click_copy_btn_label">Click to
																		copy</span>
																</button>
															</div>
														</div>

													</div>
												</div>
											</div>
											<div id="stlcardavailable" class="hidden">
												<!-- Modal -->
												<div id="showStlResultsModal" class="modal fade"
													role="dialog">
													<div class="modal-dialog modal-dialog-centered"
														role="document">
														<!-- Modal content-->
														<div class="modal-content">
															<div class="modal-header">
																<h5 class="modal-title" id="modal_title_stellar_keys">Stellar
																	Keys</h5>
																<button type="button" class="btn-close"
																	data-bs-dismiss="modal" aria-label="Close"></button>
															</div>
															<div class="modal-body">
																<form>
																	<div class="mb-3">
																		<label for="bitcoin-address" class="form-label"
																			id="stellar_public_label_25">Stellar Public
																			Key</label> <input type="text" class="form-control styled-input-field"
																			id="stlidpublickkey" readonly />
																	</div>
																	<div class="mb-3">
																		<label for="bitcoin-address" class="form-label"
																			id="stellar_private_key_label_25">Stellar
																			Private Key</label> <input type="text" class="form-control styled-input-field"
																			id="stlidprivatekey" readonly />
																	</div>
																	<input id="allstlinfo" type="text" class="hidden" />
																</form>
															</div>
															<div class="modal-footer">
																<button type="button" class="btn btn-secondary"
																	data-bs-dismiss="modal">
																	<span id="modal_footer_close_btn_label">Close</span>
																</button>
																<button type="button" class="btn btn-primary"
																	id="stlbtncopy">
																	<span id="modal_footer_copy_btn_label">Click to
																		copy</span>
																</button>
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

	<form id="post_data" method="post">
		<input type="hidden" name="qs" value=""> <input type="hidden"
			name="rules" value="">
	</form>

	<form id="form-post-data" method="post">
		<input type="hidden" name="qs" value=""> <input type="hidden"
			name="rules" value=""> <input type="hidden"
			name="json_string" id="json_string" value=""> <input
			type="hidden" name="hdnlangpref" id="hdnlangpref" >
	</form>

	<!-- End Page -->

	<div id="error-msg-login-page" style="display: none;">
		<span id="data-validation-error-swal-header">Oops..</span> <span
			id="data-validation-error-failed-to-get-details">Failed to get
			details</span> <span id="data-validation-problem-with-connection">Problem
			with connection</span> <span id="data-validation-check-your-data">Please
			check your data</span> <span id="data-validation-select-one-these">Select
			one of these</span> <span id="data-validation-checkbox-before-you-proceed">Select
			the checkbox before you proceed</span> <span id="copy-confirmation-text">Please
			check to confirm that you have copied the codes in a correct
			sequence.</span> <span id="data-great-text">Great!</span> <span
			id="data-thumbs-up-text">Thumbs up, great!</span> <span
			id="mnemonic-not-generate-text">There is no Mnemonic code
			generated</span> <span id="data-validation-mnemonic-code">This is
			required</span> <span id="data-validation-btc-address">Please enter
			BTC address</span> <span id="data-validation-btc-public-key">Please
			enter BTC public key</span> <span id="data-validation-check-box">Please
			check this box</span> <span id="data-validation-public-key">Please
			enter Public Key</span> <span id="data-validation-private-key">Please
			enter Private Key</span> <span id="wallet-creation-header">Wallet
			Creation</span> <span id="non-custodial-wallet-header">Please note
			that we are going to create Pporte,VESL,BTCx and USDC Wallets. The
			Wallets are non-custodial.</span> <span id="enter-your-passsword-tittle">Enter
			your Password</span> <span id="enter-your-passsword-label">Password</span> <span
			id="data-validation-enter-your-passsword-label">Please input
			your password!</span> <span id="copy-stellar-bitcoin-keys-label">Copying
			Stellar and Bitcoin Keys</span> <span id="copy-success-label">You
			have successfully copied Stellar and Bitcoin Keys, Please Paste and
			Store in a safe place</span> <span
			id="data-validation-enter-your-passsword-label">You have
			successfully copied Stellar and Bitcoin Keys, Please Paste and Store
			in a safe place</span> <span id="data-validation-select-asset-coin">Please
			select the Asset Coin</span> <span
			id="data-validation-select-asset-coin-desc">Please enter the
			Asset Description</span> <span id="data-validation-wallet-desc">Wallet
			description is required</span> <span id="data-validation-asset">Asset
			is required</span> <span id="data-validation-password-input">Please
			input your password!</span> <span id="data-validation-pvt-input">Please
			input your Private Key!</span>
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

	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>
	<!-- Forn-wizard js-->
	<script src="assets/plugins/formwizard/jquery.smartWizard.js"></script>


	<!-- Custom js-->
	<script src="assets/js/_cust_create_bitcoin_account.js"></script>
	<!--<script src="assets/js/_cust_create_wallet_page.js"></script>-->
	<script>
		$( document ).ready(function() {
			console.log('language is ','<%=langPref%>')
			fnChangePageLanguage('<%=langPref%>');
		});
		
		var relno ='';
		function fnRelationshipNo(){
			 relno = '<%=relationshipNo%>'; 
			 console.log('relno',relno);
		}
		</script>
</body>
</html>


<%
}catch (Exception e){
	out.println ("Exception : "+e.getMessage());
}finally{
	if (relationshipNo!=null)relationshipNo=null;if (context!=null)context=null;
	if (langPref!=null) langPref=null;

}
%>
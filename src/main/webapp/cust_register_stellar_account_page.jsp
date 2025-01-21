<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*, com.pporte.utilities.Utilities,org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
String relationshipNo=null;
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callException(request, response, context,"Session has expired, please login again.");
		return;
	}
	
	relationshipNo= ((User)session.getAttribute("SESS_USER")).getRelationshipNo();
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
<title>Register Stellar Account</title>

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
<!-- Forn-wizard css-->
<!-- Forn-wizard css-->
<link href="assets/plugins/forn-wizard/css/forn-wizard.css"
	rel="stylesheet" />
<link href="assets/plugins/formwizard/smart_wizard.css" rel="stylesheet">
<link href="assets/plugins/formwizard/smart_wizard_theme_dots.css"
	rel="stylesheet">
<style>
.sw-theme-dots .sw-toolbar-bottom {
	padding: 15px;
}
</style>
</head>

<body class="app sidebar-mini rtl" onload="fnGetRelNo()">

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
							<li class="breadcrumb-item"><a href="#">Digital Assets</a></li>
							<li class="breadcrumb-item active" aria-current="page">Register
								Stellar Account</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- page -->
					<div class="page">
						<!-- page-content -->
						<div class="page-content">
							<div class="container text-dark">
								<!-- row -->
								<div class="row justify-content-center align-self-center">
									<div class="col-lg-12">
										<div class="card" id="card_register_mnemonic">
											<div class="card-header">
												<h4>
													<span id="title_label">Register or Link Stellar
														Account</span>
												</h4>
											</div>
											<div class="card-body" id="hide_stellar">
												<form id="register-mnemonic" method="post">
													<div class="form-group text-center"
														id="stellar-account-choice">
														<label class="form-label"> <span
															id="question_label">Do you have Stellar account?</span><span
															style="color: red;">*</span></label>
														<div class="row justify-content-md-center">
															<div class="col-4">
																<label class="custom-control custom-radio"> <input
																	type="radio" class="custom-control-input"
																	name="has_account" onchange="fnStellarPresent('Yes')"
																	value="true"> <span
																	class="custom-control-label" id="yes_label">Yes</span>
																</label>
															</div>
															<div class="col-4">
																<label class="custom-control custom-radio"> <input
																	type="radio" class="custom-control-input"
																	name="has_account" onchange="fnStellarPresent('No')"
																	value="false"> <span
																	class="custom-control-label" id="no_label">No</span>
																</label>
															</div>
														</div>
													</div>
													<div id="stellar-not-present" style="display: none;">
														<div class="form-group text-center">
															<label class="custom-control custom-checkbox"> <input
																type="checkbox" class="custom-control-input"
																name="create_stellar" value=""> <span
																class="custom-control-label" id="checkbox_label">Click
																	the check box if you want to proceed to register
																	stellar account</span>
															</label>
														</div>
													</div>
													<input type="hidden" name="qs" value=""> <input
														type="hidden" name="rules" value="">
												</form>
												<div class="d-flex justify-content-center"
													id="div-btn-registr-stellar">
													<button class="btn btn-primary btn-lg"
														id="btn-register-stellar-ac"
														name="btn-register-stellar-ac"
														onclick="javascript:fnRegisterMneumonic()">
														<span id="register_label">Register</span>
													</button>
												</div>
											</div>
											<div class="hidden" id="mneumonic_card">
												<div class="card col-lg-12"
													style="height: 213px; margin-bottom: 200px;">
													<div class="card-header-text" style="margin: 30px;">
														<h2 class="card-title text-center">Scroll to get the
															next Mnemonic code</h2>
														<p>
															Please write these words in the order they follow each
															other and store in a safe place <br> Scroll to your
															left to reveal the next words >>
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
																class="carousel-control-prev-icon" aria-hidden="true"></span>
																<span class="sr-only">Previous</span>
															</a> <a class="carousel-control-next"
																href="#carousel-controls" role="button"
																data-slide="next"> <span
																class="carousel-control-next-icon" aria-hidden="true"></span>
																<span class="sr-only">Next</span>
															</a>
														</div>

													</div>
													<div class="card-footer"
														style="background: white; margin-left: -12px; margin-right: -13px;">
														<form id="register-stellar" method="post">

															<div class="form-group">
																<div class="custom-checkbox custom-control">
																	<input type="checkbox" data-checkboxes="mygroup"
																		class="custom-control-input" id="saved_mneumonic"
																		name="saved_mneumonic"> <label
																		for="saved_mneumonic" id="register-label-terms"
																		class="custom-control-label">Click to confirm
																		that you have saved the mnemonic code</label>
																</div>
															</div>
															<div id="div_btn_reg_stellar">
																<p>
																	<i class="fa fa-warning" style="color: red"></i>We
																	never store your private key in our system, please save
																	your 12 words safely and in correct sequence
																</p>
																<div class="form-group">
																	<label class="form-label">Enter your password</label> <input
																		type="password" class="form-control" name="password"
																		id="password" value="">
																</div>
															</div>
															<input type="hidden" name="qs" value=""> <input
																type="hidden" name="rules" value="">
														</form>
														<div class="transfer-coin-button">
															<button class="theme-btn"
																style="background: 00008B; border: none; border-radius: 26px;"
																id="btn_register_stellar" name="btn_register_stellar"
																onclick="javascript: fnRegisterStellarAccount()">
																<span id="register_label">Register</span>
															</button>
														</div>
													</div>
												</div>
											</div>
											<div id="stellar-present" style="display: none;">
												<form id="register-has-account" method="post">
													<div class="form-group text-center">
														<label class="form-label"> <span
															id="question_label">Do you have Mnemonic code
																generated by a previous wallet?</span><span style="color: red;">*</span></label>
														<div class="row justify-content-center">
															<div class="col-5">
																<label class="custom-control custom-radio"> <input
																	type="radio" class="custom-control-input"
																	name="has_mnemonic"
																	onchange="fmnemoniccodePresent('Yes')" value="true">
																	<span class="custom-control-label" id="yes_label">Yes</span>
																</label>
															</div>
															<div class="col-5">
																<label class="custom-control custom-radio"> <input
																	type="radio" class="custom-control-input"
																	name="has_mnemonic"
																	onchange="fmnemoniccodePresent('No')" value="false">
																	<span class="custom-control-label" id="no_label">No</span>
																</label>
															</div>
														</div>
													</div>

													<div class="hidden" id="mnemonic-present">
														<div id="smartwizard-3">
															<ul class="mt-0" style="margin: 0;">
																<li><a href="#step-1">1st Word to 4th Word</a></li>
																<li><a href="#step-2">5th Word to 8th Word</a></li>
																<li><a href="#step-3">9th Word to 12th Word</a></li>
																<li><a href="#step-4">Final Step</a></li>

															</ul>
															<div>
																<div id="step-1" class="container">
																	<div class="row justify-content-center">
																		<div class="col-5 form-group">
																			<label>First Mnemonic Code</label> <input type="text"
																				class="form-control" id="first_code"
																				name="first_code"
																				placeholder="Enter First Mnemonic Code">
																		</div>
																		<div class="col-5 form-group">
																			<label>Second Mnemonic Code</label> <input
																				type="text" class="form-control" id="second_code"
																				name="second_code"
																				placeholder="Enter Second Mnemonic Code">
																		</div>
																	</div>

																	<div class="row justify-content-center">
																		<div class="col-5 form-group">
																			<label>Third Mnemonic Code</label> <input type="text"
																				class="form-control" id="third_code"
																				name="third_code"
																				placeholder="Enter Third Mnemonic Code">
																		</div>

																		<div class="col-5 form-group">
																			<label>Fourth Mnemonic Code</label> <input
																				type="text" class="form-control" id="fourth_code"
																				name="fourth_code"
																				placeholder="Enter Fourth Mnemonic Code">
																		</div>
																	</div>
																</div>

																<div id="step-2" class="">
																	<div class="row justify-content-center">
																		<div class="col-5 form-group">
																			<label>Fifth Mnemonic Code</label> <input type="text"
																				class="form-control" id="fifth_code"
																				name="fifth_code"
																				placeholder="Enter Fifth Mnemonic Code">
																		</div>
																		<div class="col-5 form-group">
																			<label>Sixth Mnemonic Code</label> <input type="text"
																				class="form-control" id="sixth_code"
																				name="sixth_code"
																				placeholder="Enter Sixth Mnemonic Code">
																		</div>
																	</div>
																	<div class="row justify-content-center">
																		<div class="col-5 form-group">
																			<label>Seventh Mnemonic Code</label> <input
																				type="text" class="form-control" id="seventh_code"
																				name="seventh_code"
																				placeholder="Enter Seventh Mnemonic Code">
																		</div>
																		<div class="col-5 form-group">
																			<label>Eighth Mnemonic Code</label> <input
																				type="text" class="form-control" id="eight_code"
																				name="eight_code"
																				placeholder="Enter Eighth Mnemonic Code">
																		</div>
																	</div>

																</div>

																<div id="step-3" class="">
																	<div class="row justify-content-center">
																		<div class="col-5 form-group">
																			<label>Ninth Mnemonic Code</label> <input type="text"
																				class="form-control" id="nineth_code"
																				name="nineth_code"
																				placeholder="Enter Ninth Mnemonic Code">
																		</div>
																		<div class="col-5 form-group">
																			<label>Tenth Mnemonic Code</label> <input type="text"
																				class="form-control" id="tenth_code"
																				name="tenth_code"
																				placeholder="Enter Tenth Mnemonic Code">
																		</div>
																	</div>
																	<div class="row justify-content-center">
																		<div class="col-5 form-group">
																			<label>Eleventh Mnemonic Code</label> <input
																				type="text" class="form-control" id="eleventh_code"
																				name="eleventh_code"
																				placeholder="Enter Eleventh Mnemonic Code">
																		</div>
																		<div class="col-5 form-group">
																			<label>Twelveth Mnemonic Code</label> <input
																				type="text" class="form-control" id="twelve_code"
																				name="twelve_code"
																				placeholder="Enter Twelveth Mnemonic Code">
																		</div>
																	</div>
																</div>

																<div id="step-4" class="">
																	<div class="row justify-content-center">
																		<p>
																			<i class="fa fa-warning" style="color: red"></i>We
																			never store your private key in our system, please
																			save your 12 words safely and in correct sequence
																		</p>
																		<div class="col-5 form-group">
																			<label class="form-label"><span
																				id="public_key_label"> Public Key</span><span
																				style="color: red;">*</span></label> <input type="text"
																				name="input_public_key" id="input_public_key"
																				class="form-control"
																				placeholder="Enter your Stellar account public key">
																		</div>
																		<div class="col-5 form-group">
																			<label class="form-label"><span
																				id="password_key_label"> Password</span><span
																				style="color: red;">*</span></label> <input type="password"
																				name="pwsd" id="pwsd" class="form-control"
																				placeholder="Enter password to store mnemonic code">
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
														<div class="col">
															<div class="form-group">
																<label class="form-label"><span
																	id="nomnemoni_public_key_label"> Public Key</span><span
																	style="color: red;">*</span></label> <input type="text"
																	name="nomnemonic_input_public_key"
																	id="nomnemonic_input_public_key" class="form-control"
																	placeholder="Enter your Stellar account public key">
															</div>
														</div>
														<div class="col">
															<div class="form-group">
																<label class="form-label"><span
																	id="password_key_label"> Password</span><span
																	style="color: red;">*</span></label> <input type="password"
																	name="nomnemonicpwsd" id="nomnemonicpwsd"
																	class="form-control"
																	placeholder="Enter password to store mnemonic code">
															</div>
														</div>
														<p style="margin-left: 16px; margin-top: 40px;">
															<i class="fa fa-warning" style="color: red"></i>The
															system is unable to create any mnemonic code with the
															existing private key.<br /> You should always use the
															private key for signing the transaction.
														</p>

														<input type="hidden" name="qs" value=""> <input
															type="hidden" name="rules" value="">
													</form>
													<div class="transfer-coin-button">
														<button class="theme-btn"
															style="background: 00008B; border: none; border-radius: 26px;"
															id="no_mnemonic_btn_register_stellar"
															name="no_mnemonic_btn_register_stellar"
															onclick="javascript: fnNoMnemonicRegisterStellarAccount()">
															<span id="register_label">Register</span>
														</button>
													</div>
												</div>
											</div>
										</div>
									</div>


								</div>

							</div>
							<!-- row end -->
						</div>
					</div>
				</div>


			</div>
		</div>
		<!-- End app-content-->

	</div>
	</div>
	<!-- End Page -->

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

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>

	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>


	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>
	<!-- Forn-wizard js-->
	<script src="assets/plugins/formwizard/jquery.smartWizard.js"></script>

	<!--Advanced Froms js-->
	<script src="assets/js/advancedform.js"></script>
	<!-- Custom js-->
	<script src="assets/js/_cust_register_stellar_account_page.js"></script>

	<script>
		  $(document).ready(function(){
              
	        });
		function fnChangePageLanguage(lang){
			//alert('inside lang change: ' +lang);
			fnChangePageLang(lang)
			//fnChangePageLang(lang)
		}
		var relno='';
		function fnGetRelNo(){
			 relno = '<%=relationshipNo%>';
			 //console.log("REl no "+relno);
		}
		
		</script>
</body>
</html>


<%
}catch (Exception e){
	out.println ("Exception : "+e.getMessage());
}finally{
	if ( relationshipNo!=null); relationshipNo=null;
	if (context!=null)context=null;
}
%>
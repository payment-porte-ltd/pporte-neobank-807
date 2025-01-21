<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*, com.pporte.model.*, com.pporte.utilities.*, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
String planId = ""; String planDesc = ""; String planDuration = ""; String planPrice = "";
ArrayList<CardDetails> arrCustCardDetails  = null; Wallet custWallet = null; String relationshipNo = null; String originalPlanId = "";
ServletContext context = null;String langPref = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callException(request, response, context,"Session has expired, please login again.");
		return;
	}
		if(request.getAttribute("langPref")!=null) langPref = (String)request.getAttribute("langPref");
		if(request.getAttribute("custcards")!=null)	arrCustCardDetails = (ArrayList<CardDetails>)request.getAttribute("custcards");
		if(request.getAttribute("custwallet")!=null)	custWallet = (Wallet)request.getAttribute("custwallet");
		if(request.getAttribute("confirmplanid")!=null)	planId = 	(String)request.getAttribute("confirmplanid");
		if(request.getAttribute("confirmplandesc")!=null)	planDesc = (String)request.getAttribute("confirmplandesc");
		if(request.getAttribute("confirmplanduration")!=null)	planDuration = (String)request.getAttribute("confirmplanduration");
		if(request.getAttribute("confirmplanprice")!=null)	planPrice = (String)request.getAttribute("confirmplanprice");
		if(request.getAttribute("originalplanid")!=null)	originalPlanId = (String)request.getAttribute("originalplanid");
		if((User)session.getAttribute("SESS_USER")!=null) relationshipNo = ((User)session.getAttribute("SESS_USER")).getRelationshipNo();
	
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
<title>Buy Plan</title>

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
<link href="assets/css/app.css" rel="stylesheet" />
<!-- Responsive css -->
<link href="assets/css/responsive.css" rel="stylesheet" />
<!-- Data table css -->
<link href="assets/plugins/datatable/dataTables.bootstrap4.min.css"
	rel="stylesheet" />
<link href="assets/plugins/datatable/responsivebootstrap4.min.css"
	rel="stylesheet" />

<!-- Custom css -->
<link href="assets/css/style2.css" rel="stylesheet" />
<!---Sweetalert2 css-->
<link href="assets/plugins/sweetalert2/sweetalert2.css" rel="stylesheet" />

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
					<div class="page-header">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#"
								id="breadcrumb_item_label">Plans</a></li>
							<li class="breadcrumb-item active" aria-current="page"
								id="breadcrumb_item_active_label">Buy Plan</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<div class="page">
						<!-- page-content -->
						<div class="page-content">
							<div class="wallet-box-scroll">
								<div class="row">
									<div class="col-lg-12">
										<div class="card card-hover">
											<div class="card-header">
												<h3 class="card-title">
													<span id="label_select_plan">Select Your Plan below</span>
												</h3>
												<div class="card-options">
													<a href="#" class="card-options-collapse"
														data-toggle="card-collapse"><i
														class="fe fe-chevron-up"></i></a>
												</div>
											</div>
											
											<div class="card-body">

												<div class="row">
													<div class="col-md-12">
														<div class="card">
															<div class="card-body">
																<div class="d-flex">
																	<a class="header-brand" href="index.html"> <img
																		alt="logo" class="header-brand-img"
																		src="assets/images/brand/logo.svg">
																	</a>
																	<div class="text-right ml-auto">
																		<p class="mb-1">
																			<span class="font-weight-semibold"
																				id="label_conf_payments">Confirm your Payment
																				Details</span>
																	</div>
																</div>
																<hr>
																<div class="table-responsive push">
																	<table class="table table-bordered mb-0">
																		<tbody>
																			<tr class=" ">
																				<th class="text-center " style="width: 1%"></th>
																				<th id="label_item">Item</th>
																				<th class="text-center" style="width: 1%"
																					id="label_quantity">Quantity</th>
																				<th class="text-right" id="label_unit_price">Unit
																					Price</th>
																				<th class="text-right" id="label_sub_total">Sub
																					Total</th>
																			</tr>
																			<tr>
																				<td class="text-center">1</td>
																				<td>
																					<p class="font-w600 mb-1"><%=planDesc%></p>
																				</td>
																				<td class="text-center">1</td>
																				<td class="text-right">$<%=planPrice%></td>
																				<td class="text-right">$<%=planPrice%></td>
																			</tr>
																			<tr>
																				<td colspan="4"
																					class="font-weight-bold text-uppercase text-right"
																					id="label_total">Total</td>
																				<td class="font-weight-bold text-right h4">$<%=planPrice%></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</div>
														</div>
													</div>
												</div>
												<!-- end row -->

												<div class="row">
													<div class="col-md-12">
														<div class="card">
															<div class="card-header">
																<h3 class="card-title">
																	<span id="label_payment_option">Payment Options</span>
																</h3>
															</div>
															<div class="card-body p-6">
																<div class="panel panel-primary">
																	<div class="tab-menu-heading">
																		<div class="tabs-menu ">
																			<!-- Tabs -->
																			<ul class="nav panel-tabs">
																				<li class=""><a href="#tabwallet"
																					class="active" data-toggle="tab"><span
																						id="label_wallet_option" class="text-white">Wallet Option</span></a></li>
																				<li><a href="#tabtoken" data-toggle="tab"><span
																						id="label_card_option" class="text-white">Card Option</span></a></li>
																			</ul>
																		</div>
																	</div>
																	<div class="panel-body tabs-menu-body">
																		<div class="tab-content">
																			<div class="tab-pane active " id="tabwallet">
																				<!-- display and select the current wallet -->
																				<div class="row">
																					<!-- row -->
																					<div class="col-md-12 col-lg-12">
																						<div class="card">
																							<div class="card-header">
																								<h3 class="card-title">
																									<span id="label_select_wallet">Select
																										Your Wallet to Buy Plan :</span>
																								</h3>
																							</div>
																							<div class="table-responsive">
																								<table
																									class="table card-table table-vcenter text-nowrap table-nowrap">
																									<thead class="bg-porte-main-color text-white">
																										<tr>
																											<th class="text-white" id="label_wallet_plan">Wallet
																												Number</th>
																											<th class="text-white" id="label_wallet_desc">Wallet
																												Description</th>
																											<th class="text-white" id="label_wallet_bal">Wallet
																												Balance</th>
																											<th class="text-white"
																												id="label_last_updated">Last Updated</th>
																											<th class="text-white" id="label_action">Action</th>
																										</tr>
																									</thead>
																									<tbody>
																										<% if(custWallet!=null){
																										%>
																										<tr>
																											<td><%=custWallet.getWalletId()%></td>
																											<td><%=custWallet.getWalletDesc()%></td>
																											<td><%=custWallet.getCurrencyId() + " "+ custWallet.getCurrentBalance()%></td>
																											<td><%=Utilities.getMySQLDateTimeConvertor(custWallet.getLastUpdated())%></td>
																											<td class="text-center align-middle">
																												<div class="btn-group align-top">
																													<button
																														class="btn btn-sm btn-primary badge"
																														type="button"
																														onClick="javascript:fnConfirmWallet('<%=custWallet.getWalletId()%>', '<%=custWallet.getWalletDesc()%>', '<%=custWallet.getCurrentBalance()%>', '<%=planId%>', '<%=planDesc%>', '<%=planDuration%>', '<%=planPrice%>');return false;">
																														<i class="fa fa-edit"></i><span
																															id="label_select_btn">Select</span>
																													</button>
																												</div>
																											</td>
																										</tr>
																										<%
																										}else{
																										%>
																										<tr>
																											<td colspan="3"><span
																												id="label_no_wallet">No Wallet
																													Present</span></td>
																										</tr>
																										<%
																										}
																										%>
																									</tbody>
																								</table>
																							</div>
																						</div>
																					</div>
																				</div>
																				<!-- row -->
																			</div>
																			<div class="tab-pane  " id="tabtoken">
																				<!-- display and select the toknized card -->
																				<div class="row">
																					<!-- row -->
																					<div class="col-md-12 col-lg-12">
																						<div class="card">
																							<div class="row">
																								<!-- row -->
																								<div class="col-md-12 col-lg-12">
																									<div class="porte-card-content">
																										<div class="card-header">
																											<h3 class="card-title">
																												<span id="label_select_card">Select
																													Your Card to Buy Plan</span>
																											</h3>
																										</div>
																										<div class="table-responsive">
																											<table
																												class="table card-table table-vcenter text-nowrap table-nowrap">
																												<thead class="bg-porte-main-color text-white">
																													<tr>
																														<th class="text-white" id="label_cardno">Card
																															Number</th>
																														<th class="text-white"
																															id="label_card_alias">Card Alias</th>
																														<th class="text-white"
																															id="label_card_action">Action</th>
																													</tr>
																												</thead>
																												<tbody>
																													<%
																											if (arrCustCardDetails != null) {
																												for (int i = 0; i < arrCustCardDetails.size(); i++) { 
																											%>
																													<tr>
																														<td><%=((CardDetails) arrCustCardDetails.get(i)).getMaskedCardNumber()%></td>
																														<td><%=((CardDetails) arrCustCardDetails.get(i)).getCardAlias()%></td>
																														<td class="text-center align-middle">
																															<div class="btn-group align-top">
																																<button
																																	class="btn btn-sm btn-primary badge"
																																	type="button"
																																	onClick="javascript:fnConfirmCard('<%=((CardDetails) arrCustCardDetails.get(i)).getTokenId()%>','<%=((CardDetails) arrCustCardDetails.get(i)).getCardAlias()%>','<%=((CardDetails) arrCustCardDetails.get(i)).getMaskedCardNumber()%>', '<%=planId%>', '<%=planDesc%>', '<%=planDuration%>', '<%=planPrice%>');return false;">
																																	<i class="fa fa-edit"></i><span
																																		id="label_select_card_btn">Select</span>
																																</button>
																															</div>
																														</td>
																													</tr>
																													<%
																												 }
																												} else {
																												%>
																													<tr>
																														<td colspan="3"><span
																															id="label_no_cards">No Cards
																																Present</span></td>
																													</tr>
																													<%
																											}
																											%>

																												</tbody>
																											</table>
																										</div>
																									</div>
																								</div>
																							</div>
																							<!-- row -->

																						</div>
																					</div>
																				</div>
																				<!-- row -->
																			</div>


																		</div>
																	</div>
																</div>
															</div>
														</div>

													</div>
												</div>

												<!-- Modal -->
												<div class="modal fade" id="PayWithCardsModal" tabindex="-1"
													role="dialog" aria-labelledby="largemodal"
													aria-hidden="true">
													<div class="modal-dialog modal-lg " role="document">
														<div class="modal-content" style="background: linear-gradient(0deg, rgba(0, 0, 0, 0.2), rgba(0, 0, 0, 0.2)), linear-gradient(180deg, #060441 0%, #2D2C4A 37.71%, #2E2E3C 84.56%);">
															<div class="modal-header">
																<h5 class="modal-title" id="largemodal1">
																	<span id="label_card_conf_payment">Confirm your
																		payment using Card</span>
																</h5>
																<button type="button" class="close" data-dismiss="modal"
																	aria-label="Close">
																	<span aria-hidden="true">X</span>
																</button>
															</div>
															<div class="modal-body">
																<!-- row -->
																<div class="row">
																	<div class="col-md-12">
																		<div class="porte-card-content">

																			<form id="paybycardtoken-form" method="post">
																				<div class="card-body">
																					<div class="row">
																						<div class="col-xl-6">
																							<div class="form-group">
																								<label class="form-label"
																									id="label_card_plan_name">Plan Name</label> <input
																									type="text" class="form-control styled-input-field"
																									name="tokenplandesc" id="tokenplandesc"
																									value="" readonly>
																							</div>
																						</div>
																						<div class="col-xl-6">
																							<div class="form-group">
																								<label class="form-label"
																									id="label_card_plan_price">Plan Price</label> <input
																									type="text" class="form-control styled-input-field"
																									name="tokenplanprice" id="tokenplanprice"
																									value="" readonly>
																							</div>
																						</div>
																					</div>

																					<div class="row">
																						<div class="col-xl-6">
																							<div class="form-group">
																								<label class="form-label"
																									id="label_card_details">Card Details</label> <img
																									src="1.png" id="tokencardimage" height="22"
																									width="36"> <input type="text"
																									class="form-control styled-input-field" name="tokencarddetails"
																									id="tokencarddetails" value="" readonly>
																							</div>

																							<div class="form-group">
																								<label class="form-label"><span id="label_card_cvv">CVV</span> <span id="dont-store-cvv-label" class="text-warning">(We don't store CVV)</span></label>
																								<input type="password" class="form-control styled-input-field"
																									name="tokencvv2" id="tokencvv2" value="">
																							</div>
																						</div>
																						<div class="col-xl-6">
																							<div class="form-group">
																								<label class="form-label"
																									id="label_card_comments">Comments (max
																									50 chars)</label> <input type="text"
																									class="form-control styled-input-field" name="tokencomments"
																									id="tokencomments" value="">
																							</div>

																						</div>

																					</div>

																				</div>
																				<input type="hidden" name="qs" value=""> <input
																					type="hidden" name="rules" value=""> <input
																					type="hidden" name="hdnplanidbycard" value="">
																				<input type="hidden" name="hdntokenidbycard"
																					value=""> <input type="hidden"
																					name="hdntokenrelno" value="<%=relationshipNo%>">
																				<input type="hidden" name="hdnlangpref"
																					id="hdnlangpref3" value="en"> <input
																					type="hidden" name="hdncardoriginalplanid"
																					value="<%=originalPlanId%>">
																			</form>
																		</div>

																	</div>
																</div>
																<!-- row -->
															</div>
															<div class="modal-footer">
																<button type="button" class="btn btn-secondary"
																	data-dismiss="modal">
																	<span id="label_card_close_btn">Close</span>
																</button>
																<button type="button" class="btn btn-primary"
																	id="btn-submit-card-final">
																	<span id="label_card_pay_btn">Pay</span>
																</button>
															</div>
														</div>
													</div>
												</div>

												<!-- End Model -->

												<!-- Modal -->
												<div class="modal fade" id="PayWithWalletModal"
													tabindex="-1" role="dialog" aria-labelledby="largemodal"
													aria-hidden="true">
													<div class="modal-dialog modal-lg " role="document">
														<div class="modal-content" style="background: linear-gradient(0deg, rgba(0, 0, 0, 0.2), rgba(0, 0, 0, 0.2)), linear-gradient(180deg, #060441 0%, #2D2C4A 37.71%, #2E2E3C 84.56%);">
															<div class="modal-header">
																<h5 class="modal-title" id="largemodal1">
																	<span id="label_wallet_conf_payment">Confirm
																		your payment using Wallet</span>
																</h5>
																<button type="button" class="close" data-dismiss="modal"
																	aria-label="Close">
																	<span aria-hidden="true">X</span>
																</button>
															</div>
															<div class="modal-body">
																<!-- row -->
																<div class="row">
																	<div class="col-md-12">
																		<div class="card">
																			<form id="paybywallet-form" method="post">
																				<div class="card-body">
																					<div class="row">
																						<div class="col-xl-6">
																							<div class="form-group">
																								<label class="form-label"
																									id="label_wallet_plan_name">Plan Name</label> <input
																									type="text" class="form-control styled-input-field"
																									name="walletplandesc" id="walletplandesc"
																									value="" readonly>
																							</div>
																						</div>
																						<div class="col-xl-6">
																							<div class="form-group">
																								<label class="form-label"
																									id="label_wallet_plan_price">Plan Price</label>
																								<input type="text" class="form-control styled-input-field"
																									name="walletplanprice" id="walletplanprice"
																									value="" readonly>
																							</div>
																						</div>
																					</div>

																					<div class="row">
																						<div class="col-xl-6">
																							<div class="form-group">
																								<label class="form-label" id="label_walletid">Wallet
																									Id</label> <input type="text" class="form-control styled-input-field"
																									name="paywalletid" id="paywalletid" value=""
																									readonly>
																							</div>

																							<div class="form-group">
																								<label class="form-label"
																									id="label_wallet_description">Wallet
																									Description</label> <input type="text"
																									class="form-control styled-input-field" name="paywalletdesc"
																									id="paywalletdesc" value="" readonly>
																							</div>
																						</div>
																						<div class="col-xl-6">
																							<div class="form-group">
																								<label class="form-label"
																									id="label_wallet_comments">Comments
																									(max 50 chars)</label> <input type="text"
																									class="form-control styled-input-field" name="paywalletcomments"
																									id="paywalletcomments" value="">
																							</div>

																						</div>

																					</div>

																				</div>
																				<input type="hidden" name="qs" value=""> <input
																					type="hidden" name="rules" value=""> <input
																					type="hidden" name="hdnplanidbywallet" value="">
																				<input type="hidden" name="hdnwalletbal" value="">
																				<input type="hidden" name="hdnwalletrelno"
																					value="<%=relationshipNo%>"> <input
																					type="hidden" name="hdnlangpref" id="hdnlangpref3"
																					value="en"> <input type="hidden"
																					name="hdnwalletoriginalplanid"
																					value="<%=originalPlanId%>">
																			</form>
																		</div>

																	</div>
																</div>
																<!-- row -->
															</div>
															<div class="modal-footer">
																<button type="button" class="btn btn-secondary"
																	data-dismiss="modal">
																	<span id="label_wallet_close_btn">Close</span>
																</button>
																<button type="button" class="btn btn-primary"
																	id="btn-submit-wallet-final">
																	<span id="label_wallet_pay_btn">Pay</span>
																</button>



															</div>
														</div>
													</div>
												</div>

												<!-- End Model -->


											</div>
										</div>
									</div>
								</div>
								<div id="temodiv" style="display: none;">
									<form id="tempform" method="post">
										<input type="hidden" name="qs" value=""> <input
											type="hidden" name="rules" value=""> <input
											type="hidden" name="hdnplanid" value=""> <input
											type="hidden" name="hdnplandesc" value=""> <input
											type="hidden" name="hdnplanval" value=""> <input
											type="hidden" name="hdnplanduration" value=""> <input
											type="hidden" name="hdnlang" id="hdnlangnav" value="">
									</form>
								</div>
							</div>
						</div>
					</div>

				</div>
			</div>
			<!-- End app-content-->

		</div>
		<div style="display: none">
			<span id="validation_cvv">Please enter CVV</span> <span
				id="validation_comments">Please enter Comments</span> <span
				id="payment_done_successful">Payment Successfully Done</span> <span
				id="login_again">Please log in again to access new functions</span>
			<span id="wal_bal_insufficient">Your wallet balance
				insufficient</span> <span id="topup_wallet">Please top-up your
				wallet</span> <span id="success_header">Success</span> <span
				id="error_swal_header">Oops</span> <span id="error_swal_checkdata">Please
				check data</span> <span id="swal_prob_connection">Problem with
				connection</span>

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

		<!--Jquery Validator js-->
		<script
			src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>
		<!--Sweetalert2 js-->
		<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>

		<!-- i18next js-->
		<script src="assets/plugins/i18next/i18next.min.js"></script>

		<!-- Custom js-->
		<script src="assets/js/custom.js"></script>
		<script src="assets/js/_cust_confirm_buy_plan.js"></script>

		<!-- Data tables js-->
		<script src="assets/plugins/datatable/jquery.dataTables.min.js"></script>
		<script src="assets/plugins/datatable/dataTables.bootstrap4.min.js"></script>
		<script src="assets/plugins/datatable/datatable.js"></script>
		<script src="assets/plugins/datatable/datatable-2.js"></script>
		<script src="assets/plugins/datatable/dataTables.responsive.min.js"></script>


		<script>
			$(function() {
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
	if(arrCustCardDetails !=null ) arrCustCardDetails= null;    if(custWallet !=null ) custWallet= null;
	if (context!=null)context=null;
	if (langPref!=null)langPref=null;
}
%>
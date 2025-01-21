<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*,com.pporte.utilities.Utilities,com.pporte.utilities.*, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
BitcoinDetails btcDetails= null;

ServletContext context = null;
try{
	/*
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
	}
	*/
		
	if (request.getAttribute("btcaccountdetails") !=null) btcDetails = (BitcoinDetails) request.getAttribute("btcaccountdetails");

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
<title>BTC Account Information</title>

<!--Bootstrap.min css-->
<link rel="stylesheet"
	href="assets/plugins/bootstrap/css/bootstrap.min.css">

<!-- Dashboard css -->
<link href="assets/css/ops_style.css" rel="stylesheet" />

<!-- Custom scroll bar css-->
<link href="assets/plugins/scroll-bar/jquery.mCustomScrollbar.css"
	rel="stylesheet" />

<!-- Sidemenu css -->
<link href="assets/plugins/toggle-sidebar/ops_sidemenu.css" rel="stylesheet" />

<!-- Sidebar Accordions css -->
<link href="assets/plugins/accordion1/css/easy-responsive-tabs.css"
	rel="stylesheet">

<!-- Rightsidebar css -->
<link href="assets/plugins/sidebar/sidebar.css" rel="stylesheet">

<!---Font icons css-->
<link href="assets/plugins/iconfonts/plugin.css" rel="stylesheet" />
<link href="assets/plugins/iconfonts/icons.css" rel="stylesheet" />
<link href="assets/fonts/fonts/font-awesome.min.css" rel="stylesheet">

<!--Sweetaler css-->
<link href="assets/plugins/sweetalert2/sweetalert2.css">
<!-- Custom css -->
<link href="assets/css/app.css" rel="stylesheet" />
<!-- Custom css -->
<link href="assets/css/style2.css" rel="stylesheet" />
<!-- Custom css -->
<link href="assets/css/style5.css" rel="stylesheet">
<!-- Responsive css -->
<link href="assets/css/responsive.css" rel="stylesheet" />

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
			<jsp:include page="ops_topandleftmenu.jsp" />

			<!-- app-content-->
			<div class="app-content  my-3 my-md-5 toggle-content">
				<div class="side-app" style="height: 89vh;">

					<!-- page-header -->
					<div class="page-header" style="margin: auto">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#">Manage Bitcoin</a></li>
							<li class="breadcrumb-item active" aria-current="page">Account
								Information</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- page -->
					<div class="page">
						<!-- page-content -->
						<div class="page-content">

							<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
								<div class="card">
									<div class="card-header">
										<div class="card-title">Account Information</div>
										<div class="ml-auto">
											<div class="input-group">
												<%if (btcDetails!=null) {%>
												<a class="btn btn-primary text-white mr-2"
													onclick="fnOpenEditModal('<%=NeoBankEnvironment.getBitcoinCode()%>','<%=btcDetails.getAddress()%>','<%=btcDetails.getStatus()%>','<%=btcDetails.getCreatedOn()%>' );"
													data-toggle="modal" data-target="#editBTCAddressModal">
													<span> <i class="typcn typcn-plus"></i> Edit Address
												</span>
												</a>
												<%}else { %>
												<a class="btn btn-primary text-white mr-2"
													data-toggle="modal" data-target="#addBTCAddressModal">
													<span> <i class="typcn typcn-plus"></i> Add Address
												</span>
												</a>
												<%} %>
											</div>
										</div>
									</div>
									<div class="card-body">
										<%if (btcDetails!=null) {%>
										<div class="table-responsive mb-3">
											<table class="table row table-borderless w-100 m-0">
												<div class="wallet-transaction-box clearfix">
													<div class="wallet-transaction-name"
														style="font-size: 16px; font-weight: 600;">
														<img src="assets/images/crypto/bitcoin.svg" alt="Bitcoin"
															height="40" width="40"> Bitcoin Account Details
													</div>
												</div>
												<tbody class="col-lg-6 p-0">
													<tr>
														<td><strong>Address :</strong><span> <%=btcDetails.getAddress()%>
														</span></td>
													</tr>
													<tr>
														<td><strong>Total Received :</strong> <span><%=Utilities.convertSatoshiToBTC(btcDetails.getTotalReceived())%></span>
															BTC</td>
													</tr>
													<tr>
														<td><strong>Total Sent :</strong> <span><%=Utilities.convertSatoshiToBTC(btcDetails.getTotalSent())%></span>
															BTC</td>
													</tr>
													<tr>
														<td><strong>Balance :</strong> <span
															class="text-success"><%=Utilities.convertSatoshiToBTC(btcDetails.getBalance())%></span>
															BTC</td>
													</tr>
													<tr>
														<td><strong>Unconfirmed Balance :</strong> <span
															class="text-danger"><%=Utilities.convertSatoshiToBTC(btcDetails.getUnconfirmedBalance())%>
																BTC</span></td>
													</tr>
												</tbody>
												<tbody class="col-lg-6 p-0">
													<tr>
														<td><strong>Final Balance :</strong> <span
															class="text-success"><%=Utilities.convertSatoshiToBTC(btcDetails.getFinalBalance())%>
																BTC</span></td>
													</tr>
													<tr>
														<td><strong>No of Transactions Balance :</strong> <span><%=btcDetails.getNumberOfConfirmedTransactions()%></span></td>
													</tr>
													<tr>
														<td><strong>Unconfirmed No. of Transactions
																:</strong> <span class="text-danger"><%=btcDetails.getNumberOfUnconfirmedTransactions()%></span></td>
													</tr>
													<tr>
														<td><strong>Final No. of Transactions :</strong><span>
																<%=btcDetails.getFinalNumberOfTransactions()%></span></td>
													</tr>
												</tbody>
											</table>
										</div>

										<%} else { %>
										<p>No Account Information</p>
										<%} %>
									</div>
									<!-- table-wrapper -->
								</div>
								<!-- section-wrapper -->
							</div>


						</div>
						<!--End of page content -->
					</div>
					<!--End of page -->

					<!-- Modal -->
					<div class="modal fade" id="editBTCAddressModal" tabindex="-1"
						role="dialog" aria-labelledby="largemodal" aria-hidden="true">
						<div class="modal-dialog modal-lg " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="largemodal1">Edit BTC Address</h5>
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
												<form id="edit_btcaddress_form" method="post">
													<div class="card-body">
														<div class="row">
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Asset Code</label> <input
																		type="text" class="form-control" name="editassetcode"
																		id="editassetcode" readonly>
																</div>

																<div class="form-group">
																	<label class="form-label">BTC Address</label> <input
																		type="text" class="form-control" name="editbtcaddress"
																		id="editbtcaddress" readonly>
																</div>

																<div class="form-group">
																	<label class="form-label">Created On</label> <input
																		type="text" class="form-control" name="editcreatedon"
																		id="editcreatedon" readonly>
																</div>
															</div>
															<div class="col-xl-6">

																<div class="form-group">
																	<label class="form-label">Status</label> <select
																		class="form-control" id="selleditstatus"
																		name="selleditstatus">
																		<option selected disabled value="">Select
																			Status</option>
																		<option value="A">Active</option>
																		<option value="I">Inactive</option>
																	</select>
																</div>
															</div>
														</div>
													</div>
												</form>
											</div>

										</div>
									</div>
									<!-- row -->
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary"
										data-dismiss="modal">Close</button>
									<button type="button" class="btn btn-primary" id="btn_edit"
										onClick="javascript:fnEditAddress();return false;">Submit</button>
								</div>
							</div>
						</div>
					</div>

					<!-- End Model -->
					<!-- Modal -->
					<div class="modal fade" id="addBTCAddressModal" tabindex="-1"
						role="dialog" aria-labelledby="largemodal" aria-hidden="true">

						<div class="modal-dialog modal-lg " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="largemodal1">Add BTC Address</h5>
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
												<form id="addbtc_address_form" method="post">
													<div class="card-body">
														<div class="row">
															<div class="col-xl-6">
																<div class="form-group">

																	<label class="form-label">Asset Code</label> <select
																		class="form-control" name="seladdassetcode"
																		id="seladdassetcode">
																		<option selected disabled value="">Select
																			Asset</option>
																		<option
																			value="<%=NeoBankEnvironment.getBitcoinCode()%>"><%=NeoBankEnvironment.getBitcoinCode()%></option>
																	</select>

																</div>

																<div class="form-group ">
																	<label class="form-label">Address</label>
																	<div class="input-two-box">
																		<input type="text" class="form-control"
																			name="btcaddress" id="btcaddress"
																			placeholder="Add BTC Address">
																	</div>

																</div>
															</div>
															<div class="col-xl-6">

																<div class="form-group">
																	<label class="form-label">Status</label> <select
																		class="form-control" id="seladdstatus"
																		name="seladdstatus">
																		<option selected disabled value="">Select
																			Status</option>
																		<option value="A">Active</option>
																		<option value="I">Inactive</option>
																	</select>
																</div>
															</div>
														</div>
													</div>
												</form>
											</div>

										</div>
									</div>
									<!-- row -->
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary"
										data-dismiss="modal">Close</button>
									<button type="button" class="btn btn-primary" id="btn_add"
										onClick="javascript:fnAddAdress();return false;">Submit</button>
								</div>
							</div>
						</div>
					</div>

					<!-- End Model -->


				</div>
			</div>
			<!-- End app-content-->

		</div>

	</div>

	<form id="post_form">
		<input type="hidden" name="qs" value=""> <input type="hidden"
			name="rules" value="">
	</form>
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

	<!-- Parsley js-->
	<script src="assets/plugins/parsley/parsley.min.js"></script>
	<!-- Sweetalert js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.all.min.js"></script>

	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>

	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_tda_btc_account_information_page.js"></script>

	<script>
		function fnChangePageLanguage(lang){
			//alert('inside lang change: ' +lang);
			fnChangePageLang(lang)
			//fnChangePageLang(lang)
		}
		</script>
</body>
</html>


<%
}catch (Exception e){
	out.println ("Exception : "+e.getMessage());
}finally{
	if (btcDetails!=null)btcDetails=null;if (context!=null)context=null;
	}
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.utilities.Utilities, com.pporte.model.*, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
ArrayList<Transaction> arrAuthorizedWalletTransactions=null;
ConcurrentHashMap<String,String> hashAuthStatus = null;  
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}
		
	if (request.getAttribute("transactions") != null) arrAuthorizedWalletTransactions = (ArrayList<Transaction>) request.getAttribute("transactions");
	
	hashAuthStatus = new ConcurrentHashMap<String,String>();
	hashAuthStatus.put("S","Success");	hashAuthStatus.put("F","Fail"); 
	
	
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
<title>Authorized Wallet Transactions</title>

<!--Bootstrap.min css-->
<link rel="stylesheet"
	href="assets/plugins/bootstrap/css/bootstrap.min.css">

<!-- Dashboard css -->
<link href="assets/css/ops_style.css" rel="stylesheet" />

<!-- Custom scroll bar css-->
<link href="assets/plugins/scroll-bar/jquery.mCustomScrollbar.css"
	rel="stylesheet" />

<!---Sweetalert2 css-->
<link href="assets/plugins/sweetalert2/sweetalert2.css" rel="stylesheet" />

<!-- Sidemenu css -->
<link href="assets/plugins/toggle-sidebar/ops_sidemenu.css" rel="stylesheet" />

<!--Daterangepicker css-->
<link
	href="assets/plugins/bootstrap-daterangepicker/daterangepicker.css"
	rel="stylesheet" />

<!-- Sidebar Accordions css -->
<link href="assets/plugins/accordion1/css/easy-responsive-tabs.css"
	rel="stylesheet">

<!-- Date Picker css-->
<link href="assets/plugins/date-picker/spectrum.css" rel="stylesheet" />
<!-- Rightsidebar css -->
<link href="assets/plugins/sidebar/sidebar.css" rel="stylesheet">

<!---Font icons css-->
<link href="assets/plugins/iconfonts/plugin.css" rel="stylesheet" />
<link href="assets/plugins/iconfonts/icons.css" rel="stylesheet" />
<link href="assets/fonts/fonts/font-awesome.min.css" rel="stylesheet">
<!-- Data table css -->
<link href="assets/plugins/datatable/dataTables.bootstrap4.min.css"
	rel="stylesheet" />
<link href="assets/plugins/datatable/responsivebootstrap4.min.css"
	rel="stylesheet" />

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
					<div class="page-header">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#">Manage Wallet</a></li>
							<li class="breadcrumb-item active" aria-current="page">View
								Authorized Transactions</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- row -->
					<div class="row ">
						<div class="col-md-12 col-lg-12">
							<div class="card">
								<div class="card-header">
									<div class="card-title">View Authorized Transactions</div>
								</div>
								<div class="card-body">
									<div class="row">
										<div class="col-lg-3">
											<div class="card">
												<div class="card-header">
													<h4>Transaction Filter</h4>
												</div>
												<div class="card-body">
													<form id="filter-txn-form" method="post">
														<div class="form-group">
															<label class="form-label">Date From</label>
															<div class="wd-200 mg-b-30">
																<div class="input-group">
																	<div class="input-group">
																		<div class="input-group-prepend">
																			<div class="input-group-text">
																				<i class="fa fa-calendar tx-16 lh-0 op-6"></i>
																			</div>
																		</div>
																		<input class="form-control fc-datepicker"
																			name="datefrom" id="datefrom"
																			placeholder="MM/DD/YYYY" type="text">
																	</div>
																</div>
															</div>
														</div>
														<div class="form-group">
															<label class="form-label">Date To</label>
															<div class="wd-200 mg-b-30">
																<div class="input-group">
																	<div class="input-group">
																		<div class="input-group-prepend">
																			<div class="input-group-text">
																				<i class="fa fa-calendar tx-16 lh-0 op-6"></i>
																			</div>
																		</div>
																		<input class="form-control fc-datepicker"
																			name="dateto" id="dateto" placeholder="MM/DD/YYYY"
																			type="text">
																	</div>
																</div>
															</div>
														</div>
														<a class="btn theme-btn" href="#"
															onclick="javascript:fnGetFilteredTxn();return false;">Search
															Transactions</a> <input type="hidden" name="qs" value="">
														<input type="hidden" name="rules" value="">
													</form>
												</div>
											</div>


										</div>
										<div class="col-lg-9">
											<div class="table-responsive" id="div_table">
												<table id="txn_example"
													class="table table-striped table-bordered text-nowrap">
													<thead>
														<tr>
															<th>Date and Time</th>
															<th>Customer Id</th>
															<th>Wallet Id</th>
															<th>Currency</th>
															<th>Amount</th>
															<th>System Reference</th>
															<th>Auth Status</th>
															<th>Reason</th>
														</tr>
													</thead>
													<tbody>
														<%
												if (arrAuthorizedWalletTransactions != null) {
													for (int i = 0; i < arrAuthorizedWalletTransactions.size(); i++) {
												%>
														<tr>
															<td><%=((Transaction) arrAuthorizedWalletTransactions.get(i)).getTxnDateTime()%></td>
															<td><%=((Transaction) arrAuthorizedWalletTransactions.get(i)).getCustomerId()%></td>
															<td><%=((Transaction) arrAuthorizedWalletTransactions.get(i)).getCustomerWalletId()%></td>
															<td><%=((Transaction) arrAuthorizedWalletTransactions.get(i)).getTxnCurrencyId()%></td>
															<td><%=((Transaction) arrAuthorizedWalletTransactions.get(i)).getTxnAmount()%></td>
															<td><%=((Transaction) arrAuthorizedWalletTransactions.get(i)).getSystemReferenceInt()%></td>
															<td><%=hashAuthStatus.get(((Transaction) arrAuthorizedWalletTransactions.get(i)).getStatus())%></td>
															<td><%=((Transaction) arrAuthorizedWalletTransactions.get(i)).getComment()%></td>
														</tr>
														<%
													 }
													} else {
													%>

														<tr>
															<td colspan="9"><span
																id="ops_all_bin_list_errormsg1">No transactions
																	available </span></td>
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
								<!-- table-wrapper -->
							</div>
							<!-- section-wrapper -->
						</div>
					</div>
					<!-- row end -->
					<!-- End Model -->

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

	<script src="assets/plugins/select2/select2.full.min.js"></script>

	<!-- Custom scroll bar js-->
	<script
		src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>
	<!-- Rightsidebar js -->
	<script src="assets/plugins/sidebar/sidebar.js"></script>
	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>
	<script src="assets/js/select2.js"></script>

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>

	<!-- Data tables js-->
	<script src="assets/plugins/datatable/jquery.dataTables.min.js"></script>
	<script src="assets/plugins/datatable/dataTables.bootstrap4.min.js"></script>
	<script src="assets/plugins/datatable/datatable.js"></script>
	<script src="assets/plugins/datatable/datatable-2.js"></script>
	<script src="assets/plugins/datatable/dataTables.responsive.min.js"></script>
	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>
	<!-- Timepicker js -->
	<script src="assets/plugins/time-picker/jquery.timepicker.js"></script>
	<script src="assets/plugins/time-picker/toggles.min.js"></script>
	<!-- Datepicker js -->
	<script src="assets/plugins/date-picker/spectrum.js"></script>
	<script src="assets/plugins/date-picker/jquery-ui.js"></script>
	<script src="assets/plugins/input-mask/jquery.maskedinput.js"></script>

	<!-- Sidebar Accordions js -->
	<script src="assets/plugins/accordion1/js/easyResponsiveTabs.js"></script>

	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_ops_view_authorized_wallet_transaction_page.js"></script>



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
	if (arrAuthorizedWalletTransactions!=null)  arrAuthorizedWalletTransactions = null;
	if (hashAuthStatus!=null)  hashAuthStatus = null;if (context!=null)context=null;
}
%>
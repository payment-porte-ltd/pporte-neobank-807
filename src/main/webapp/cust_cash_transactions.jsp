<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*, com.pporte.utilities.Utilities,org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>

<%
 String langPref=null;
ServletContext context = null;
try{
	if (request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callException(request, response, context,"Session has expired, please login again.");
		return;
	}
	if(request.getAttribute("langpref")!=null) langPref = (String)request.getAttribute("langpref");
%>

<!doctype html>
<html lang="en" dir="ltr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Favicon -->
<link rel="icon" href="assets/images/brand/pporte.png"
	type="image/x-icon" />

<!-- Title -->
<title>Cash Transactions</title>

<!--Bootstrap.min css-->
<link rel="stylesheet"
	href="assets/plugins/bootstrap/css/bootstrap.min.css">

<!-- Dashboard css -->
<link href="assets/css/style.css" rel="stylesheet" />

<!-- Custom scroll bar css-->
<link href="assets/plugins/scroll-bar/jquery.mCustomScrollbar.css"
	rel="stylesheet" />

<!---Sweetalert2 css-->
<link href="assets/plugins/sweetalert2/sweetalert2.css" rel="stylesheet" />

<!-- Sidemenu css -->
<link href="assets/plugins/toggle-sidebar/sidemenu.css" rel="stylesheet" />

<!--Daterangepicker css-->
<link
	href="assets/plugins/bootstrap-daterangepicker/daterangepicker.css"
	rel="stylesheet" />

<!-- Sidebar Accordions css -->
<link href="assets/plugins/accordion1/css/easy-responsive-tabs.css"
	rel="stylesheet">

<!-- Time picker css-->
<link href="assets/plugins/time-picker/jquery.timepicker.css"
	rel="stylesheet" />

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

<!-- Responsive css -->
<link href="assets/css/responsive.css" rel="stylesheet" />

</head>

<body class="app sidebar-mini rtl" onload="fnGetlastTenTxn()">

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
				<div class="side-app">
					<!-- page-header -->
					<div class="page-header">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#"><span
									id="breadcrumb-item-cash-transaction">Cash Transactions</span></a></li>
							<li class="breadcrumb-item active" aria-current="page"><span
								id="breadcrumb-item-search-transaction">Search
									Transactions</span></li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->

					<!-- row -->
					<div class="row">
						<div class="col-sm-12 col-lg-4">
									<div class="porte-card-content">
										<div class="card-header">
											<h4>
												<span id="transaction-filter-header">Transaction
													Filter</span>
											</h4>
										</div>
										<div class="card-body">
											<form id="filter-txn-form" method="post">
												<div class="form-group">
													<label class="form-label" id="date-from-label">Date
														From</label>
													<div class="wd-200 mg-b-30">
														<div class="input-group">
															<div class="input-group-prepend">
														      <div class="input-group-text appended-icon" >
														        <img width="17" src="assets/images/icons/calendar.svg" >
														      </div>
														    </div>
													      <input type="text" class="form-control fc-datepicker appended-input" placeholder="MM/DD/YYYY"
															name="datefrom" id="datefrom"  >
														</div>
													</div>
												</div>
												<div class="form-group">
													<label class="form-label" id="date-to-label">Date
														To</label>
													<div class="wd-200 mg-b-30">
														<div class="input-group">
															<div class="input-group">
															<div class="input-group-prepend">
														      <div class="input-group-text appended-icon" >
														        <img width="17" src="assets/images/icons/calendar.svg" >
														      </div>
														    </div>
													      <input type="text" class="form-control fc-datepicker appended-input" placeholder="MM/DD/YYYY"
															name="dateto" id="dateto"  >
														</div>
														</div>
													</div>
												</div>
												<button class="btn btn-block new-default-btn"
													onclick="javascript:fnGetFilteredTxn()"><span
													id="seach_btn_label">Search Transactions</span></button> <input
													type="hidden" name="qs" value=""> <input
													type="hidden" name="rules" value="">
											</form>
										</div>
									</div>
							
						</div>
						<div class="col-sm-12 col-lg-8">
							<div class="porte-card-content">
								<div class="card-header">
									<h4>
										<span id="card_txn_header"></span>
									</h4>
								</div>
								<div class="card-body" id="div_table"></div>
							</div>

						</div>
					</div>
					<!-- row end -->

				</div>


				<form method="post" id="get-txn-form">
					<input type="hidden" name="qs" value=""> <input
						type="hidden" name="rules" value="">
				</form>



				<!--footer-->

				<!-- End Footer-->

			</div>
			<!-- End app-content-->
		</div>
	</div>
	<!-- End Page -->
	<div id="error-msg-login-page" style="display: none;">
		<span id="data-validation-error-swal-header">Oops..</span> <span
			id="data-validation-error-failed-to-get-details">Failed to get
			details</span> <span id="data-validation-problem-with-connection">Problem
			with connection</span> <span id="data-validation-check-your-data">Please
			check your data</span> <span
			id="data-validation-failed-to-get-transactions-data">Failed to
			get Transactions</span> <span id="last-five-transaction-tabel-label">Last
			Five Transactions</span> <span id="date-table-label">Date</span> <span
			id="transaction-code-table-label">Transaction Code</span> <span
			id="description-table-label">Description</span> <span
			id="amount-table-label">Amount(USD)</span> <span
			id="no-data-present-table-label">No data Present</span> <span
			id="filtered-transaction-table-label">Filtered Transactions</span> <span
			id="data-validation-date-from">Please enter date from</span> <span
			id="data-validation-date-to">Please enter date to</span>
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
	<script src="assets/js/_cust_cash_transactions.js"></script>
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
	if (langPref!=null) langPref=null; if (context!=null)context=null;
}
%>

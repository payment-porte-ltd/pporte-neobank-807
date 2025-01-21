<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
String relationshipNo=null;
Wallet wallet = null;
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
	if(request.getAttribute("wallet")!=null) wallet = (Wallet)request.getAttribute("wallet");
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
<title>View Wallet Page</title>

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
<link  href="assets/fonts/fonts/font-awesome.min.css" rel="stylesheet">

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
					<div class="page-header" style="margin: auto">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#"><span
									id="breadcrumb-item-manage-wallet">Manage Wallet</span></a></li>
							<li class="breadcrumb-item active" aria-current="page"><span
								id="breadcrumb-item-view-wallet">View Wallet</span></li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->

					<!-- row -->
					<div class="row " id="div_wallets">
						<% if( wallet!=null){%>
						
						
								<div class="col-lg-6 col-12 d-block" style="margin-left:16px;" >
										<div class="fiat-card-wal-page" >
							           	 	<div class="asset_balance_wal_page"><%=wallet.getCurrentBalance() +" "+wallet.getAssetCode()%></div>
							       		</div>
								 </div>
							
				
						
						<%} %>
						<div class="col-lg-12 col-12 d-block" style="margin:16px;" >
								<div class="porte-card-content" >
									<div class="card-header custom-header">
										<div>
											<h3 class="card-title" > <span id="last_five_transaction">Last 5 Transactions</span></h3>
											<h6 class="card-subtitle"><span id="fiat_wallet_label"> Fiat Wallet</span></h6>
										</div>
									</div>
									<div class="card-body">
										<div class="table-responsive" id="wallet_txn"></div>
									</div>
								</div>
							</div>

					</div>
					<!-- row end -->
					
					


				</div>
			</div>
			<!-- End app-content-->

		</div>
		<form method="post" id="wallet-form">
			<input type="hidden" name="qs" value=""> <input type="hidden"
				name="rules" value=""> <input type="hidden" name="relno"
				value="">
		</form>
		<form method="post" id="wallet-bal-form">
			<input type="hidden" name="qs" value=""> <input type="hidden"
				name="rules" value=""> <input type="hidden" name="walletid"
				value="">
		</form>
		<form method = "post" id="get-txn-form">
					<input type="hidden" name="qs" value="">
					<input type="hidden" name="rules" value="">
					<input type="hidden" name="hdnlang" >
				</form>
	</div>
	<!-- End Page -->
	<div id="error-msg-login-page" style="display: none;">
		<span id="data-validation-error-swal-header">Oops..</span> <span
			id="data-validation-error-failed-to-get-details">Failed to get
			details</span> <span id="data-validation-problem-with-connection">Problem
			with connection</span> <span id="data-validation-check-your-data">Please
			check your data</span>
			<span id="table-label-date">Date</span>
			<span id="table-label-description">Description</span>
			<span id="table-label-amount">Amount</span>
			<span id="data-validation-error-swal-header">Oops..</span>
			<span id="failed-to-get-data-validation-error-swal-header">Failed to get Transactions</span>
			<span id="data-validation-problem-with-connection">Problem with connection</span>

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

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>

	<!-- Sidebar Accordions js -->
	<script src="assets/plugins/accordion1/js/easyResponsiveTabs.js"></script>

	<!-- Custom scroll bar js-->
	<script src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>
	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>
	<!-- Custom js-->
	<script src="assets/js/_cust_view_wallet_page.js"></script>

	<script>
	
		$(document).ready(function() {
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

	if ( relationshipNo!=null) relationshipNo=null;
	if ( wallet!=null)  wallet=null;
	if (langPref!=null) langPref=null;	if (context!=null)context=null;

}
%>
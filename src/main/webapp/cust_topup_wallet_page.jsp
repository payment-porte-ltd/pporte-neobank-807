<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
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
<title>Topup Wallet Page</title>

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

</head>

<style>
.hidden {
	display: none;
}
</style>

<body class="app sidebar-mini rtl" onload="fnRelationshipNo()">

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
									id="breadcrumb-item-manage-wallets">Manage Wallet</span></a></li>
							<li class="breadcrumb-item active" aria-current="page"><span
								id="breadcrumb-item-topup-wallet">Topup Wallet</span></li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
							<div class="col-lg-8 col-12 d-block">
								<div class="porte-card-content">
									<div class="card-header">
										<h4>
											<span id="topupwallet_label">Topup Wallet by:-</span>
										</h4>
									</div>
									<div class="card-body myTab">
									
										
										<div class="tab-menu-heading">
														<div class="tabs-menu " style="margin-left: -23px;">
															<!-- Tabs -->
															<ul class="nav panel-tabs">
																<li class=""><a href="#home" class="active"
																	data-toggle="tab"><span
																		id="spn-nav-link-cards" class="text-white">Cards</span></a></li>
																<li><a href="#profile" data-toggle="tab"><span
																		id="spn-nav-link-bank" class="text-white">Bank</span></a></li>
															</ul>
														</div>
											</div>
										<div class="tab-content border p-3" id="myTabContent">
											<div class="tab-pane fade p-0 active show" id="home"
												role="tabpanel" aria-labelledby="home-tab">
												<form method="post" id="card-topup-form">
													<h5>
														<span id="spn-select-card"> Select card</span>
													</h5>
													<div id="showdetails"></div>
													<div id="cardavailable">
														<div class="form-group">
															<label class="form-label" id="amount-label">
																Amount</label> <input type="number" class="form-control styled-input-field"
																name="amount" id="amount" name="amount">
														</div>

														<div class="form-group">

															<label class="form-label" ><span id="cvv-label">CVV</span> <span id="dont-store-cvv-label" class="text-warning">(We don't store CVV)</span></label> <input
																type="password" class="form-control styled-input-field" name="cvv" id="cvv"
																name="cvv">
														</div>
														<div class="row"  style="margin-top: 40px;">
																<div class="col-12">
																	<button class="btn btn-block new-default-btn"
																		onclick="javascript:fnTopUpWalletByCard();return false;">
																		<span id="btn-topup-label">Topup</span>
																	</button>
																</div>														
															</div>
														
													</div>

													<input type="hidden" name="qs"> <input
														type="hidden" name="rules"> <input type="hidden"
														name="relno">
												</form>
											</div>
											<div class="tab-pane fade p-0" id="profile" role="tabpanel"
												aria-labelledby="profile-tab">
												<span style="color: red" id="spn-coming-soon">Coming
													Soon</span>
											</div>

										</div>
									</div>
								</div>
							</div>
				</div>
			</div>
			<!-- End app-content-->

			<form method="post" id="get-card-form">
				<input type="hidden" name="qs"> <input type="hidden"
					name="rules"> <input type="hidden" name="relno">
				<input type="hidden" name="hdnlang" >
			</form>

		</div>

	</div>
	<!-- End Page -->
	<div id="error-msg-login-page" style="display: none;">
		<span id="data-validation-error-swal-header">Oops..</span> <span
			id="data-validation-error-failed-to-get-details">Failed to get
			details</span> <span id="data-validation-problem-with-connection">Problem
			with connection</span> <span id="data-validation-check-your-data">Please
			check your data</span> <span id="data-no-cards-present-data">No Cards
			Present Please</span> <span id="data-no-click-here-to-register">Click
			here to register.</span> <span id="data-validation-select-card">Please
			select card</span> <span id="data-no-click-enter-amount">Please enter
			amount</span> <span id="data-no-click-enter-cvv">Please enter cvv</span> <span
			id="data-success-header">Success</span> <span
			id="data-topup-successful">Topup Successful</span>
	</div>



	<!-- Back to top -->
	<a href="#top" id="back-to-top"><i class="fa fa-angle-up"></i></a>

	<!-- Jquery js-->
	<script src="assets/js/vendors/jquery-3.2.1.min.js"></script>

	<!--Bootstrap.min js-->
	<script src="assets/plugins/bootstrap/popper.min.js"></script>
	<script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>

	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>

	<!--Side-menu js-->
	<script src="assets/plugins/toggle-sidebar/sidemenu.js"></script>

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>

	<!-- Sidebar Accordions js -->
	<script src="assets/plugins/accordion1/js/easyResponsiveTabs.js"></script>

	<!-- Custom scroll bar js-->
	<script
		src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>

	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>

	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_cust_topup_wallet_page.js"></script>



	<script>
		var relno =''
		function fnRelationshipNo(){
			 relno = '<%=relationshipNo%>';
			fnGetCardDetails(relno);
		}

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
	if (relationshipNo!=null)relationshipNo=null;
	if (langPref!=null) langPref=null;if (context!=null)context=null;

}
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%

ArrayList<Wallet> arrWalletlist = null;  ArrayList<Customer> arrRegisteredReceivers = null;
ServletContext context = null; String langPref=null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callException(request, response, context,"Session has expired, please login again.");
		return;
	}
	
	
	
	if(request.getAttribute("walletlist")!=null)	arrWalletlist = (ArrayList<Wallet>)request.getAttribute("walletlist");
	if(request.getAttribute("registeredusers")!=null)	arrRegisteredReceivers = (ArrayList<Customer>)request.getAttribute("registeredusers");
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
<title>Send Money</title>

<!--Bootstrap.min css-->
<link rel="stylesheet"
	href="assets/plugins/bootstrap/css/bootstrap.min.css">

<!-- Dashboard css -->
<link href="assets/css/style.css" rel="stylesheet" />
<link href="assets/css/wallets.css" rel="stylesheet" />
<!-- Custom scroll bar css-->
<link href="assets/plugins/scroll-bar/jquery.mCustomScrollbar.css"
	rel="stylesheet" />

<!-- Sidemenu css -->
<link href="assets/plugins/toggle-sidebar/sidemenu.css" rel="stylesheet" />

<!---Sweetalert2 css-->
<link href="assets/plugins/sweetalert2/sweetalert2.css" rel="stylesheet" />

<!-- Sidebar Accordions css -->
<link href="assets/plugins/accordion1/css/easy-responsive-tabs.css"
	rel="stylesheet">

<!-- Rightsidebar css -->
<link href="assets/plugins/sidebar/sidebar.css" rel="stylesheet">

<!---Font icons css-->
<link href="assets/plugins/iconfonts/plugin.css" rel="stylesheet" />
<link href="assets/plugins/iconfonts/icons.css" rel="stylesheet" />
<link href="assets/fonts/fonts/font-awesome.min.css" rel="stylesheet">


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
								id="breadcrumb-item-Send-money">Send Money</span></li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- page -->

						
							<div class="col-lg-8 col-12 d-block">
								<div class="porte-card-content">
									<div class="card-header">
										<h3 class="card-title">
											<span id="spn-card-title">Send Money</span>
										</h3>
										<a href="#" class="btn theme-btn-sm ml-auto" style="background-color:#27A89D;border-color:#27A89D ;"
											onClick="javascript: fnAddReceiver();return false;"> <i
											class="fe fe-plus mr-2"></i> <span
											id="register-receiver-label">Register receiver</span>
										</a>
									</div>
									<div class="card-body">
										<form id="pay-anyone-form" method="post"
											enctype="multipart/form-data">
											<div class="row">

												<div class="col-xl-12">
													<div class="form-group">

														<% if(arrRegisteredReceivers!=null ) { %>
														<label class="form-label" id="id_address">To Address</label> <select
															class="form-control select2-show-search styled-input-field"
															name="selregistereduser" id="selregistereduser">
															<option value="" selected disabled id="opt_please_select">Please select</option>
															<% for (int i=0; i<arrRegisteredReceivers.size();i++){ %>

															<option
																value="<%= ((Customer)arrRegisteredReceivers.get(i)).getEmail()%>"><%= ((Customer)arrRegisteredReceivers.get(i)).getEmail() %></option>
															<%  } %>
														</select>
													</div>
												</div>
												<div class="col-xl-12">
													<div class="form-group">
														<label class="form-label" id="amount_label">Amount
															to send(USD) </label> <input type="number" class="form-control styled-input-field"
															name="amount" id="amount">
													</div>
												</div>
												<div class="col-xl-12">
													<div class="form-group mb-0">
														<label class="form-label" id="comment_label">Add
															Comment </label>
														<textarea class="form-control styled-input-field" rows="4"name="comment"
															id="comment"></textarea>
													</div>
												</div>
												<div class="col-xl-12" style="margin-top: 40px;">
													<button class="btn  btn-block new-default-btn"
														onclick="javascript:fnPayAnyoneFiatWallet();return false;">
														<span id="spn_send_money_btn">Send Money</span>
													</button>
												</div>

												<% } else{ %>
												<p class="text-center mt-1" style="color: #27A89D">
													<span id="spn_register_receiver">Kindly register
														receiver</span>
												</p>
												<% }%>
												<input type="hidden" name="qs" value=""> <input
													type="hidden" name="rules" value=""> <input
													type="hidden" name="hdnselregistereduser" value="">
											</div>
										</form>

									</div>
								</div>
							</div>
						
					
				</div>
				<!-- End app-content-->
			</div>
		</div>
	</div>
	<form id="post-form" method="post">
		<input type="hidden" name="qs" value=""> <input type="hidden"
			name="rules" value=""> <input type="hidden" name="hdnlang"
			>
	</form>

	<!-- End Page -->

	<div id="error-msg-login-page" style="display: none;">
		<span id="data-validation-error-swal-header">Oops..</span> <span
			id="data-validation-error-failed-to-get-details">Failed to get
			details</span> <span id="data-validation-problem-with-connection">Problem
			with connection</span> <span id="data-validation-check-your-data">Please
			check your data</span> <span id="data-validation-email-id">Please
			receiver email is required</span> <span
			id="data-validation-must-be-email-id">Receiver id must be
			email</span> <span id="data-validation-amount-is-required">Amount is
			required</span> <span id="data-validation-comment-is-required">Comment
			is required</span> <span id="data-validation-comment-must-be-30-characters">Maximum
			length is 30 characters</span>
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

	<!-- Custom scroll bar js-->
	<script
		src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>

	<!-- Sidebar Accordions js -->
	<script src="assets/plugins/accordion1/js/easyResponsiveTabs.js"></script>
	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>
	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_cust_wallet_pay_anyone.js"></script>
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

	if (arrWalletlist!=null)arrWalletlist=null;if (arrRegisteredReceivers!=null)arrRegisteredReceivers=null;
	if (context!=null)context=null;
	if (langPref!=null) langPref=null;

}
%>
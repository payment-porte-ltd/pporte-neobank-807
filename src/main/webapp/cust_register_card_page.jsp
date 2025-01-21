<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*, com.pporte.utilities.Utilities,org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
String relationshipNo=null;
ServletContext context = null;
String langPref = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callException(request, response, context,"Session has expired, please login again.");
		return;
	}
	relationshipNo= ((User)session.getAttribute("SESS_USER")).getRelationshipNo();
	if(request.getAttribute("langPref")!=null) langPref = (String)request.getAttribute("langPref");
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
<title>Register Card Page</title>

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
<!---Card icons css-->
<link href="assets/plugins/creditcard/card.css" rel="stylesheet">
<!---Sweetalert2 css-->
<link href="assets/plugins/sweetalert2/sweetalert2.css" rel="stylesheet" />

</head>

<body class="app sidebar-mini rtl" onload="fnGetRelationshipNo();">

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
							<li class="breadcrumb-item"><a href="#"
								id="breadcrumb_item_label">Manage Card</a></li>
							<li class="breadcrumb-item active" aria-current="page"
								id="breadcrumb_item_active_label">Register Card</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
							<div class="row">
								<div class="col-lg-8 col-12 d-block">
									<div class="card">
										<div class="card-header">
											<h3 class="mb-0 card-title">
												<span id="label_register_card">Register your Credit
													or Debit Bank Card here</span>
											</h3>
										</div>
										<div class="card-body">
											<div class="row">
												<div class="">
													<div class="card-wrapper" style="margin-left: 12px;margin-bottom: 12px;"></div>
												</div>
											</div>
											<div class="row">
											
												<div class="col-12">
													
													<form method="POST" id="cardform">
														<div class="form-group">
															<label><span id="label_card_number">Card
																	Number</span> </label> <input
																type="tel" name="number" id="number"
																class="form-control styled-input-field">
														</div>
														<div class="form-group">
															<label><span id="label_full_name">Full
																	Name</span></label> <input
																type="text" name="name" id="name" class="form-control styled-input-field">
														</div>
														<div class="form-group">
															<label><span id="label_expiry_date">Expiry
																	Date</span> <span class="text-warning">(MM/YY)</span> </label> <input
																type="text" name="expiry" id="expiry"
																class="form-control styled-input-field">
														</div>
														<div class="form-group">
															<label><span id="label_cvv">CVV</span> <span id="dont-store-cvv-label" class="text-warning">(We don't store CVV)</span></label> <input type="password"
																name="cvc" id="cvc" class="form-control styled-input-field" minlength="3"
																maxlength="4">
														</div>
														<div class="form-group">
															<label><span id="label_deescription">Description,
																	e.g. My Card</span> </label> <input
																type="text" name="cardalias" id="cardalias"
																class="form-control styled-input-field">
														</div>

														<input type="hidden" name="qs"> <input
															type="hidden" name="rules"> <input type="hidden"
															name="relno">
													</form>
														<button class="btn btn-block new-default-btn"
															onclick="javascript:fnRegisterCard();return false;">
															<span id="label_register_card_btn">Register Card</span>
													</button>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
				</div>
			</div>
			<!-- End app-content-->
			<div style="display: none">
				<span id="validation_error_swal_header">Oops..</span> <span
					id="validation_error_swal_checkdata">Please check your data</span>
				<span id="swal_connection_prob">Problem with connection</span> <span
					id="validation_error_card_no">Please Enter Card Number</span> <span
					id="validation_error_card_no_length">Please Enter Number of
					not less than 16 digits</span> <span id="validation_error_card_expiry">Please
					Enter Date of Expiry</span> <span id="validation_error_card_name">Please
					Enter full name</span> <span id="validation_error_cvv">Please Enter
					CVV</span> <span id="validation_error_card_alias">Please Enter Card
					Alias</span> <span id="validation_error_expired_card">Card has
					already expired</span> <span id="validation_error_valid_card">Please
					enter a valid card number</span> <span
					id="card_registered_successful_title">Registration
					Successful</span> <span id="card_registered_successful_text">Card
					Registered Successful</span> <span id="card_registration_failed">There
					was a problem registering your card, please try again</span>


			</div>
		</div>

	</div>
	<!-- End Page -->

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

	<!-- Sidebar Accordions js -->
	<script src="assets/plugins/accordion1/js/easyResponsiveTabs.js"></script>

	<!-- Custom scroll bar js-->
	<script
		src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>

	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>
	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<!-- Card js-->
	<script src="assets/plugins/creditcard/jquery.card.js"></script>
	<!-- Custom js-->
	<script src="assets/js/_cust_register_card_page.js"></script>
	<script src="assets/js/custom.js"></script>

	<script>
		$(function() {
			console.log('language is ','<%=langPref%>')
			fnChangePageLanguage('<%=langPref%>');
		});
			   var relno='';
			
			function fnGetRelationshipNo(){
				relno = '<%=relationshipNo%>';
				console.log("Rel no "+ relno);
			}
		</script>
</body>
</html>


<%
}catch (Exception e){
	out.println ("Exception : "+e.getMessage()); 
	
}finally{
	if(relationshipNo!=null);relationshipNo=null;if (context!=null)context=null;
	if (langPref!=null)langPref=null;
}
%>
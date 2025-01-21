<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>

<%
try{
  
%>

<!doctype html>
<html lang="en" dir="ltr">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- <meta http-equiv="Content-Type" content="text/html; charset=windows-1252" /> -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Title -->
<title>Add branch Page</title>
<!--Bootstrap.min css-->
<link rel="stylesheet"
	href="assets/plugins/bootstrap/css/bootstrap.min.css">
<!-- Dashboard css -->
<link href="assets/css/ops_style.css" rel="stylesheet" />
<!---Font icons css-->
<link href="assets/plugins/iconfonts/plugin.css" rel="stylesheet" />
<link href="assets/plugins/iconfonts/icons.css" rel="stylesheet" />
<link href="assets/fonts/fonts/font-awesome.min.css" rel="stylesheet">
<!---Sweetalert2 css-->
<link href="assets/plugins/sweetalert2/sweetalert2.css" rel="stylesheet" />

<!-- Sidemenu css -->
<link href="assets/plugins/toggle-sidebar/ops_sidemenu.css" rel="stylesheet" />

<!-- Forn-wizard css-->
<link href="assets/plugins/forn-wizard/css/forn-wizard.css"
	rel="stylesheet" />
<link href="assets/plugins/formwizard/smart_wizard.css" rel="stylesheet">
<link href="assets/plugins/formwizard/smart_wizard_theme_dots.css"
	rel="stylesheet">
<!-- File Uploads css-->
<link href="assets/plugins/fileuploads/css/dropify.css" rel="stylesheet"
	type="text/css" />
<!-- Rightsidebar css -->
<link href="assets/plugins/sidebar/sidebar.css" rel="stylesheet">
</head>
<body class="app sidebar-mini rtl"
	onload="javascript:getMerchantCategory()">

	<!--Global-Loader-->
	<div id="global-loader">
		<img src="assets/images/icons/loader.svg" alt="loader">
	</div>

	<div class="page">
		<div class="page-main">
			<!-- Sidebar menu-->
			<jsp:include page="merch_topandleftmenu.jsp" />
			<!--sidemenu end-->
			<!-- app-content-->
			<div class="app-content  my-3 my-md-5 toggle-content">
				<div class="side-app">
					<!-- page-header -->
					<div class="page-header">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#">Profile</a></li>
							<li class="breadcrumb-item active" aria-current="page">Add
								branch</li>
						</ol>
						<!-- End breadcrumb -->

					</div>
					<!-- End page-header -->

					<!-- row -->


					<!--app-header end-->
					<div class="row">
						<div class="col-12">
							<div class="card">
								<div class="card-header">
									<h3 class="card-title">
										<span id="merchernt-registration-title">Add branch</span>
									</h3>
								</div>
								<div class="card-body">
									<form id="merch-add-branch-form" method="post">
										<div id="smartwizard-3">
											<ul>
												<li><a href="#step-10"><span
														id="merchernt-registration-form-title1">Business
															details</span></a></li>
												<li><a href="#step-11"><span
														id="merchernt-registration-form-title2">Merchant
															user details</span></a></li>
												<li><a href="#step-12"><span
														id="merchernt-registration-form-title3">Merchant
															file upload</span></a></li>
											</ul>
											<div>
												<div id="step-10" class="">
													<div class="row">
														<div class="col-6 form-group">
															<label><span
																id="merchernt-registration-companyname-label">Company
																	name</span></label> <input type="text" class="form-control"
																id="companyname" name="companyname"
																placeholder="Enter Company Name">
														</div>
														<div class="col-6 form-group">
															<label><span
																id="merchernt-registration-bsndesc-label">Business
																	description</span></label> <input type="text" class="form-control"
																id="bsndesc" name="bsndesc"
																placeholder="Enter Business description">
														</div>
													</div>
													<div class="row">
														<div class="col-6 form-group">
															<label><span
																id="merchernt-registration-physic-add-label">Business
																	physical address</span></label> <input type="text"
																class="form-control" id="physicaladd" name="physicaladd"
																placeholder="Enter Business physical addres">
														</div>
														<div class="col-6 form-group">
															<label><span
																id="merchernt-registration-bsnphoneno-label">Business
																	phone number</span></label> <input type="number" class="form-control"
																id="bsnphoneno" name="bsnphoneno"
																placeholder="Enter Business phone number">
														</div>
													</div>
													<div class="row">
														<div class="col-6 form-group">
															<label><span
																id="merchernt-registration-mcc-label">Merchant
																	category</span></label> <select
																class="form-control select2 custom-select"
																id="selectmcc" name="selectmcc"
																data-placeholder="Select Merchant category">
																<option value="" disabled selected>Select your
																	merchant category</option>


															</select>
														</div>
														<div class="col-6 form-group">
															<label><span
																id="merchernt-registration-pricing-label">Select
																	pricing plan</span></label> <select
																class="form-control select2 custom-select"
																id="selectpricingplan" name="selectpricingplan"
																data-placeholder="Select merchant pricing plan">
																<option value="" disabled selected>Select your
																	pricing plan</option>
																<option value="39">$39/month package</option>
																<option value="99">$99/month package</option>
															</select>
														</div>
													</div>

												</div>
												<div id="step-11" class="">
													<div class="row">
														<div class="col-6 form-group">
															<label><span
																id="merchernt-registration-fulname-label">Full
																	name</span></label> <input type="text" class="form-control"
																id="fullname" name="fullname"
																placeholder="Enter Full name">
														</div>
														<div class="col-6 form-group">
															<label><span
																id="merchernt-registration-email-label">Email
																	address</span></label> <input type="email" class="form-control"
																id="email" name="email"
																placeholder="Enter email address">
														</div>
													</div>
													<div class="row">
														<div class="col-6 form-group">
															<label><span
																id="merchernt-registration-phono-label">Phone
																	number</span></label> <input type="number" class="form-control"
																id="merchphoneno" name="merchphoneno"
																placeholder="Enter Phone number">
														</div>
														<div class="col-6 form-group">
															<label><span
																id="merchernt-registration-idno-label">National
																	identification number</span></label> <input type="text"
																class="form-control" id="nationalId" name="nationalId"
																placeholder="Enter identification number">
														</div>
													</div>

												</div>
												<div id="step-12" class="">
													<div class="col-xl-12 col-lg-12 col-md-12">
														<div class="card shadow">
															<div class="card-body">
																<input type="file" name="file1" id="file1"
																	class="dropify" />
															</div>
														</div>
													</div>
												</div>


											</div>
										</div>
										<input type="hidden" name="qs" value=""> <input
											type="hidden" name="rules" value=""> <input
											type="hidden" name="hdnmccid" value=""> <input
											type="hidden" name="hdnplan" value=""> <input
											type="hidden" name="hdnhavebranches" value="">
									</form>
								</div>
							</div>
						</div>
					</div>
					<div id="error-msg-add-branch-page" style="display: none;">
						<span id="add-branch-data-validation-error-swal-header">Oops..</span>
						<span id="add-branch-data-validation-error-swal-checkdata">Please
							check your data</span> <span id="add-branch-data-validation-companyname">Please
							enter Company name </span> <span
							id="add-branch-data-validation-companyname-length">Company
							name must consist of at least 4 characters</span> <span
							id="add-branch-data-validation-bsndesc">Please enter
							Business description</span> <span
							id="add-branch-data-validation-bsndesc-length">Business
							description must consist of at least 10 characters</span> <span
							id="add-branch-data-validation-physicaladd">Please
							Physical address</span> <span
							id="add-branch-data-validation-physicaladd-length">Physical
							address must consist of at least 4 characters</span> <span
							id="add-branch-data-validation-bsnphoneno">Please enter
							Business number</span> <span
							id="add-branch-data-validation-bsnphoneno-length">Business
							number must consist of at least 8 numbers</span> <span
							id="add-branch-data-validation-merchphoneno">Please enter
							phone number</span> <span
							id="add-branch-data-validation-merchphoneno-length">Phone
							number must consist of at least 8 numbers</span> <span
							id="add-branch-data-validation-selectmcc">Please select
							merchant category</span> <span
							id="add-branch-data-validation-selectpricingplan">Please
							select pricing plan</span> <span
							id="add-branch-data-validation-fullname">Please enter Full
							name</span> <span id="add-branch-data-validation-fullname-length">Full
							name must be at least 5 characters</span> <span
							id="add-branch-data-validation-email">Please Enter valid
							email</span> <span id="add-branch-data-validation-email-required">Please
							Enter email</span> <span id="add-branch-data-validation-nationalId">Please
							enter National identification number</span> <span
							id="add-branch-branch-data-validation-nationalId-length">National
							identification number must be at least 5 numbers</span> <span
							id="add-branch-data-validation-file1">Please upload file</span>
					</div>
					<form method="post" id="get-mcc-form">
						<input type="hidden" name="qs" value=""> <input
							type="hidden" name="rules" value="">
					</form>

					<form method="post" id="managebranches-form">
						<input type="hidden" name="qs" value=""> <input
							type="hidden" name="rules" value=""> <input type="hidden"
							name="hdnuserid" value=""> <input type="hidden"
							name="hdnlangpref" id="hdnlangpref3" value="en">
					</form>

					<form method="post" id="get-mcc-form">
						<input type="hidden" name="qs" value=""> <input
							type="hidden" name="rules" value="">
					</form>

					<form method="post" id="get-add-branch-form">
						<input type="hidden" name="qs" value=""> <input
							type="hidden" name="rules" value="">
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- End Page -->
	<!--row closed-->
	<!-- Jquery js-->
	<script src="assets/js/vendors/jquery-3.2.1.min.js"></script>
	<!--Bootstrap.min js-->
	<script src="assets/plugins/bootstrap/popper.min.js"></script>
	<script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>

	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>
	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>

	<!-- Forn-wizard js-->
	<script src="assets/plugins/formwizard/jquery.smartWizard.js"></script>

	<!--Accordion-Wizard-Form js-->
	<script
		src="assets/plugins/accordion-Wizard-Form/jquery.accordion-wizard.min.js"></script>

	<!-- File uploads js -->
	<script src="assets/plugins/fileuploads/js/dropify.js"></script>
	<script src="assets/plugins/fileuploads/js/dropify-demo.js"></script>
	<!-- Custom js-->
	<script src="assets/js/_merch_add_branch.js"></script>
	<script>
                $(window).on("load", function(e) {
                    $("#global-loader").fadeOut("slow");
                })
            </script>
</body>
</html>

<% 
}catch(Exception e){

}finally{
	
	
}

%>

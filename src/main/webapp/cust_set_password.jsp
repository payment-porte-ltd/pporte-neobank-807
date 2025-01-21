<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<% String userId=null;
 
 try{
	 if (request.getAttribute("userid") != null) userId = (String) request.getAttribute("userid");
 %>
<!doctype html>
<html lang="en" dir="ltr">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Favicon -->
<link rel="icon" href="assets/images/brand/pporte.png"
	type="image/x-icon" />

<!-- Title -->
<title>Set Password</title>

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

<link href="assets/plugins/sweetalert2/sweetalert2.css" rel="stylesheet" />

</head>
<body class="bg-account"
	style="background-image: url('assets/images/background/Login-BG.jpg'); background-repeat: no-repeat; background-attachment: fixed; background-size: cover; padding-right: 0; padding-left: 0;">
	<!-- page -->
	<div id="spinner-div">
		<img src="assets/images/icons/loader.svg" alt="loader">
	</div>
	<div class="page">
		<!-- page-content -->
		<div class="page-content">
			<div class="container text-center text-dark">
				<div class="row">
					<div class="col-lg-4 d-block mx-auto">
						<div class="row">
							<div class="col-xl-12 col-md-12 col-md-12">
								<div class="card">
									<div class="card-body">
										<!-- <div class="text-center mb-6">
												<img src="assets/images/brand/logo.svg" class="" alt="">
											</div> -->
										<h3>Customer Reset Password</h3>
										<p class="text-muted">Set password</p>
										<form id="set-password-form" method="post">
											<div class="form-group">
												<div class="input-group mb-4">
													<span class="input-group-addon bg-white"><i
														class="fa fa-unlock-alt  w-4"></i></span> <input type="password"
														name="old_password" id="old_password" class="form-control"
														placeholder="Enter Your Current Password">
												</div>
											</div>

											<div class="input-group mb-4">
												<span class="input-group-addon bg-white"><i
													class="fa fa-unlock-alt  w-4"></i></span> <input type="password"
													name="new_password" id="new_password" class="form-control"
													placeholder="Enter Your New Password">
											</div>

											<div class="input-group mb-4">
												<span class="input-group-addon bg-white"><i
													class="fa fa-unlock-alt  w-4"></i></span> <input type="password"
													name="confirm_password" id="confirm_password"
													class="form-control"
													placeholder="Confirm Your New Password">
											</div>
											<input type="hidden" name="userid" value="<%=userId%>">
										</form>
										<div class="row">
											<div class="col-12">
												<button type="button" id="btn-test-submit"
													name="btn-test-submit"
													class="btn btn-primary btn-block px-4">Submit</button>
											</div>
										</div>

									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>
		<!-- page-content end -->
	</div>
	<!-- page End-->
	<form method="post" id="get-page-form">
		<input type="hidden" name="qs" value=""> <input type="hidden"
			name="rules" value="">
	</form>

	<!-- Jquery js-->
	<script src="assets/js/vendors/jquery-3.2.1.min.js"></script>

	<!--Bootstrap.min js-->
	<script src="assets/plugins/bootstrap/popper.min.js"></script>
	<script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>

	<!-- Sidebar Accordions js -->
	<script src="assets/plugins/accordion1/js/easyResponsiveTabs.js"></script>

	<!-- Daterangepicker js-->
	<script
		src="assets/plugins/bootstrap-daterangepicker/daterangepicker.js"></script>

	<!-- Custom scroll bar js-->
	<script
		src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>

	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_cust_set_password.js"></script>

	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>

	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>

</body>
</html>
<%
}catch (Exception e){
	out.println ("Exception : "+e.getMessage());
}finally{
	if(userId!=null) userId=null; 
}
%>
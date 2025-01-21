<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
String lang=null;
try{	
	if(request.getAttribute("lang")!=null) lang = (String)request.getAttribute("lang");
%>
<!doctype html>
<html lang="en" dir="ltr">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- <meta http-equiv="Content-Type" content="text/html; charset=windows-1252" /> -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Porte Icon -->
<link rel="icon" href="assets/images/brand/pporte.png"
	type="image/x-icon" />
<link rel="shortcut icon" type="image/x-icon"
	href="assets/images/brand/pporte.png" />
<!-- Title -->
<title>Successful Registration Page</title>

<!--Bootstrap.min css-->

<link rel="stylesheet"
	href="assets/plugins/bootstrap/css/bootstrap.min.css">
<link href="assets/css/style.css" rel="stylesheet">
<link href="assets/fonts/fonts/font-awesome.min.css" rel="stylesheet">

</head>
<body
	style="background-image: url('assets/images/background/login-background.svg'); background-repeat: no-repeat; background-attachment: fixed; background-size: cover; padding-right: 0; padding-left: 0;">
	<!-- page -->
	<div id="spinner-div">
		<img src="assets/images/icons/loader.svg" alt="loader">
	</div>
	<div class="page">

		<!-- page-content -->
		<div class="page-content">


			<div class="row">
				<div class="col-lg-8 d-block mx-auto">
					<div class="row">
						<div class="col-xl-12 col-md-12 col-md-12">
							<div class="porte-card-content " style="opacity: 0.9">
								<div class="card-body">
									<div class="text-center mb-6">
										<img src="assets/images/brand/logo.svg" class="" alt=""
											style="margin-bottom: -67px">
									</div>
									<div class="text-center mb-6">
										<i class="fa fa-check-circle"
											style="font-size: 120px; color: #27A89D"></i>
									</div>
									<div class="text-center mb-6">
										<h1 class="display-3" class="font-weight-bold"
											style="color: #fff">
											<span id="spn_registration_successful">Registration
												Successful</span>
										</h1>
										<p class="lead">
											<span id="spn_credential_aprovred_label">Your
												credentials have been received by our team and are currently
												being verified. You'll get an email notification once your
												details have been approved.</span>
										<p>
										<hr class="my-4">
										<h3 style="color: #27A89D" class="font-weight-bold">
											<span id="spn_welcome_label">WELCOME TO PAYMENT PORTE!</span>
										</h3>
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
	<form id="post-form" method="post">
		<input type="hidden" id="qs" name="qs"> <input type="hidden"
			id="rules" name="rules">
	</form>

	<!-- Jquery js-->
	<script src="assets/js/vendors/jquery-3.2.1.min.js"></script>

	<!--Bootstrap.min js-->
	<script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>
	<script src="assets/plugins/int-tel-input/intlTelInput.min.js"></script>

	<!-- Custom js-->
	<script src="assets/js/_cust_success_reg_page.js"></script>

	<script>
        
        $(document).ready(function(){
        	 fnChangePageLanguage('<%=lang%>');
        	 setTimeout(function(){
        		  $('#post-form').attr('action', 'ws');
        		    $('input[name="qs"]').val('lgt');
        		    $('input[name="rules"]').val('lgtdefault');
        		    $("#post-form").submit();

           },4000); 
        });
         
        </script>
</body>
</html>
<%
}catch (Exception e){
	out.println ("Exception : "+e.getMessage());
}finally{
	if ( lang!=null) lang=null;
	
}
%>

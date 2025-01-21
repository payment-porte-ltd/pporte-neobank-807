<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
String relationshipNo=null;
String jsonString=null;
ServletContext context = null;
String langPref=null;

try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callException(request, response, context,"Session has expired, please login again.");
		return;
	}
	if (context!=null)context=null;
	if(request.getAttribute("langpref")!=null) langPref = (String)request.getAttribute("langpref");
	relationshipNo= ((User)session.getAttribute("SESS_USER")).getRelationshipNo();
	if(request.getAttribute("data")!=null)	jsonString = (String)request.getAttribute("data");
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
<title>Create Digital Assets</title>
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
<!-- Data table css -->
<link href="assets/plugins/datatable/dataTables.bootstrap4.min.css"
	rel="stylesheet" />
<link href="assets/plugins/datatable/responsivebootstrap4.min.css"
	rel="stylesheet" />
<link href="assets/plugins/sweetalert2/sweetalert2.css" rel="stylesheet" />
</head>
<body class="app sidebar-mini rtl">
	<!--Global-Loader
		<div id="global-loader">
			<img src="assets/images/icons/loader.svg" alt="loader">
		</div>-->
	<div id="spinner-div">
		<img src="assets/images/icons/loader.svg" alt="loader">
	</div>
	<div class="page">
		<div class="page-main">
			<!-- Sidebar menu-->
			<jsp:include page="cust_topandleftmenu.jsp" />
			<!--sidemenu end-->
			<!-- app-content-->
			<div class="app-content  my-3 my-md-5 toggle-content">
				<div class="side-app">
					<!-- page-header -->
					<div class="page-header">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#">Digital Assets</a></li>
							<li class="breadcrumb-item active" aria-current="page">Create
								Digital Assets</li>
						</ol>
						<!-- End breadcrumb -->

					</div>
					<!-- End page-header -->

					<!-- row -->
					<div class="row">
						<div class="col-md-12 col-lg-12">
							<div class="card">
								<div class="card-header">
									<div class="card-title" id="card_digital_asset_title">Creating Digital Assets</div>
								</div>
								<div class="card-body">
									<div id="div_please_wait_circle">
										<div class="h-100 d-flex align-items-center justify-content-center">
											<h2 id="header_please_wait_page">Please wait while your wallet is being generated</h2>
										</div>
										<div class="h-100 d-flex align-items-center justify-content-center">
											<h3 id="header_time">(It will take some time)</h3>
										</div>
										
										<div>
											<div class="sk-circle">
												<div class="sk-circle1 sk-child"></div>
												<div class="sk-circle2 sk-child"></div>
												<div class="sk-circle3 sk-child"></div>
												<div class="sk-circle4 sk-child"></div>
												<div class="sk-circle5 sk-child"></div>
												<div class="sk-circle6 sk-child"></div>
												<div class="sk-circle7 sk-child"></div>
												<div class="sk-circle8 sk-child"></div>
												<div class="sk-circle9 sk-child"></div>
												<div class="sk-circle10 sk-child"></div>
												<div class="sk-circle11 sk-child"></div>
												<div class="sk-circle12 sk-child"></div>
											</div>
										</div>						

									</div>
									<div class="hidden col-md-8 col-lg-8" id="div_keys">
										<div class="porte-card-content">
											<form>
												<div class="mb-3">
													<label for="bitcoin-address" class="form-label" id="label_bitcoin_address">Bitcoin
														Address</label> <input type="text" class="form-control styled-input-field"
														id="idbitcoinaddress" readonly />
												</div>
												<div class="mb-3">
													<label for="bitcoin-address" class="form-label" id="label_btc_pub_key">BTC
														Public Key</label> <input type="text" class="form-control styled-input-field"
														id="idbtcpublickkey" readonly />
												</div>
												<div class="mb-3">
													<label for="bitcoin-address" class="form-label" id="label_btc_private_key">BTC
														Private Key</label> <input type="text" class="form-control styled-input-field"
														id="idbtcprivatekey" readonly />
												</div>
												<div class="mb-3">
													<label for="bitcoin-address" class="form-label" id="label_stellar_pub_key">Stellar
														Public Key</label> <input type="text" class="form-control styled-input-field"
														id="idstlpublickkey" readonly />
												</div>
												<div class="mb-3">
													<label for="bitcoin-address" class="form-label" id="label_stl_private_key">Stellar
														Private Key</label> <input type="text" class="form-control styled-input-field"
														id="idstlprivatekey" readonly />
												</div>
												<input id="allinfo" type="text" class="hidden" />
											</form>
											<button type="button" class="btn btn-primary" id="btncopy">
												Click to copy &nbsp;&nbsp; <i class="fa fa-clone"></i>
											</button>
										</div>
									</div>
								</div>
								<!-- table-wrapper -->
							</div>
							<!-- section-wrapper -->
						</div>
					</div>
					<!-- row end -->
				</div>
			</div>
			<!-- End app-content-->
		</div>
	</div>
			<div id="error-msg-login-page" style="display: none;">
				<span id="data-validation-error-swal-header">Oops..</span> <span
					id="data-validation-error-failed-to-get-details">Failed to
					get details</span> <span id="data-validation-problem-with-connection">Problem
					with connection</span> <span id="data-validation-check-your-data">Please
					check your data</span> <span id="data-validation-email-id">Please
					enter email id</span>
					<span id="swal_stellar_btc_copy_title">Stellar and Bitcoin Keys Copied</span>
        			<span id="swal_stellar_btc_copy_text">You have successfully copied Stellar and Bitcoin Keys, Please Paste and Store in a safe place</span>
			</div>
	<form method="post" id="get-page-form">
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
	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>

	<!--Side-menu js-->
	<script src="assets/plugins/toggle-sidebar/sidemenu.js"></script>
	<!-- Sidebar Accordions js -->
	<script src="assets/plugins/accordion1/js/easyResponsiveTabs.js"></script>

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>

	<!-- Custom scroll bar js-->
	<script
		src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>
	<!-- Rightsidebar js -->
	<script src="assets/plugins/sidebar/sidebar.js"></script>
	<!-- Data tables js-->
	<script src="assets/plugins/datatable/jquery.dataTables.min.js"></script>
	<script src="assets/plugins/datatable/dataTables.bootstrap4.min.js"></script>
	<script src="assets/plugins/datatable/datatable.js"></script>
	<script src="assets/plugins/datatable/datatable-2.js"></script>
	<script src="assets/plugins/datatable/dataTables.responsive.min.js"></script>
	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>

	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_cust_create_digital_asset_waiting_page.js"></script>
	<script>
		function fnChangePageLanguage(lang){
			//alert('inside lang change: ' +lang);
			fnChangePageLang(lang)
			//fnChangePageLang(lang)
		}
		var relno ='';
		var data = '';
		function fnRelationshipNo(){
			 relno = '<%=relationshipNo%>'; 
			 data = '<%=jsonString%>';
			 console.log('data', data );
			
		}
		</script>
</body>
</html>

<% 
}catch(Exception e){
	out.println ("Exception : "+e.getMessage());
}finally{
	if (relationshipNo!=null)relationshipNo=null;
	if (context!=null)context=null;
}

%>
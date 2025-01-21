<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
List<DisputeReasons> aryDisputeReasons = null;
ConcurrentHashMap<String,String> hashStatus = null;
Iterator itStatus=null;

try{
	if(request.getAttribute("dsptreason")!=null)
		aryDisputeReasons=(List<DisputeReasons>)request.getAttribute("dsptreason");
	hashStatus = new ConcurrentHashMap<String,String>();	hashStatus.put("A","Active"); hashStatus.put("C","Closed"); 
	hashStatus.put("P","In Progress");	itStatus = hashStatus.keySet().iterator();

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
<title>Store Payments</title>

<!--Bootstrap.min css-->
<link rel="stylesheet"
	href="assets/plugins/bootstrap/css/bootstrap.min.css">

<!-- Dashboard css -->
<link href="assets/css/ops_style.css" rel="stylesheet" />
<!---Sweetalert2 css-->
<link href="assets/plugins/sweetalert2/sweetalert2.css" rel="stylesheet" />

<!-- Custom scroll bar css-->
<link href="assets/plugins/scroll-bar/jquery.mCustomScrollbar.css"
	rel="stylesheet" />

<!-- Sidemenu css -->
<link href="assets/plugins/toggle-sidebar/ops_sidemenu.css" rel="stylesheet" />

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

	<div class="page">
		<div class="page-main">
			<!--app-header and sidebar-->
			<jsp:include page="merch_topandleftmenu.jsp" />
			<!-- app-content-->
			<div class="app-content  my-3 my-md-5 toggle-content">
				<div class="side-app">
					<!-- page-header -->
					<div class="page-header">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#">QR Code</a></li>
							<li class="breadcrumb-item active" aria-current="page">Store
								Payment</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->

					<!-- row -->
					<div class="row">
						<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
							<div class="card">
								<div class="card-header">
									<h4>Store Payment</h4>
								</div>
								<div class="card-body myTab">

									<ul class="nav nav-tabs" id="myTab" role="tablist">

										<li class="nav-item"><a class="nav-link"
											onclick="javascript:fnGenerateStaticQRCode()"
											id="profile-tab" data-toggle="tab" href="#profile" role="tab"
											aria-controls="profile" aria-selected="false">Static QR</a></li>
										<li class="nav-item"><a class="nav-link active show"
											id="contact-tab" data-toggle="tab" href="#contact" role="tab"
											aria-controls="contact" aria-selected="true">Dynamic QR</a></li>
									</ul>
									<div class="tab-content border p-3" id="myTabContent">

										<div class="tab-pane fade p-0" id="profile" role="tabpanel"
											aria-labelledby="profile-tab">
											<div class="product-gallery border">
												<div class=" text-center">
													<img src="" id="staticdisplayimage" alt="img"
														style="max-width: 400px; max-height: 400px; border: 1px solid black">
													<a href="#" class="btn btn-primary mt-1 mb-3 float-none"
														onclick="javascript:printimagestatic()">Print QR Code</a>
												</div>


											</div>
										</div>
										<div class="tab-pane fade p-0 active show" id="contact"
											role="tabpanel" aria-labelledby="contact-tab">
											<div class="product-gallery border">
												<div class="text-center">
													<img src="" id="dynamicdisplayimage" alt="img"
														style="max-width: 400px; max-height: 400px; border: 1px solid black">
												</div>
												<div class="justify-content-center align-self-center">
													<div class="form-group">
														<label class="form-label">Enter Amount</label> <input
															type="number" class="form-control" name="retailamount"
															id="retailamount" placeholder="Enter Amount">
													</div>
													<button type="button" class="btn btn-primary"
														onclick="javascript:fnGenerateDynamicQRCode()">Generate
														QR Code</button>
												</div>

											</div>
										</div>
									</div>
								</div>
							</div>
						</div>

					</div>
					<!-- row end -->

				</div>

				<form method="post" id="get-kyc-form">
					<input type="hidden" name="qs" value=""> <input
						type="hidden" name="rules" value="">
				</form>

				<form method="post" id="get-mcc-form">
					<input type="hidden" name="qs" value=""> <input
						type="hidden" name="rules" value="">
				</form>

				<form id="download-form" method="post">
					<input type="hidden" name="qs" value=""> <input
						type="hidden" name="rules" value=""> <input type="hidden"
						name="hdnassetpath" value="">
				</form>

				<!--footer-->

				<!-- End Footer-->

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

	<!-- Sidebar Accordions js -->
	<script src="assets/plugins/accordion1/js/easyResponsiveTabs.js"></script>

	<!-- Custom scroll bar js-->
	<script
		src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>

	<!-- Rightsidebar js -->
	<script src="assets/plugins/sidebar/sidebar.js"></script>

	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>
	<script src="assets/plugins/jspdf/jspdf.min.js"></script>


	<!-- Custom js-->
	<script src="assets/js/_merch_store_payments.js"></script>

</body>
</html>

<% 
}catch(Exception e){

}finally{
	if(aryDisputeReasons!=null) aryDisputeReasons=null; 
	if(hashStatus!=null) hashStatus = null; 
	if(itStatus!=null) itStatus = null;
	
}

%>

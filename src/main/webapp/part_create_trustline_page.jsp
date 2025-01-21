<%@page import="com.google.gson.JsonArray"%>
<%@page import="com.google.gson.JsonObject"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*, com.pporte.utilities.Utilities,org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
JsonObject issuers = null;
JsonArray recordsArray = null;
JsonObject assetObj = null;
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
	}
		
	if(request.getAttribute("issuers")!=null)	issuers = (JsonObject)request.getAttribute("issuers");
	recordsArray = issuers.get("records").getAsJsonArray();
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
<title>Create Trustline</title>

<!--Bootstrap.min css-->
<link rel="stylesheet"
	href="assets/plugins/bootstrap/css/bootstrap.min.css">

<!-- Dashboard css -->
<link href="assets/css/ops_style.css" rel="stylesheet" />


<!-- Custom scroll bar css-->
<link href="assets/plugins/scroll-bar/jquery.mCustomScrollbar.css"
	rel="stylesheet" />

<!-- Sidemenu css -->
<link href="assets/plugins/toggle-sidebar/ops_sidemenu.css" rel="stylesheet" />

<!--Daterangepicker css-->
<link
	href="assets/plugins/bootstrap-daterangepicker/daterangepicker.css"
	rel="stylesheet" />

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

<!-- Custom css -->
<link href="assets/css/app.css" rel="stylesheet" />
<!-- Custom css -->
<link href="assets/css/style2.css" rel="stylesheet" />
<!-- Custom css -->
<link href="assets/css/style5.css" rel="stylesheet">
<!-- Responsive css -->
<link href="assets/css/responsive.css" rel="stylesheet" />
<!--Mutipleselect css-->
<link rel="stylesheet"
	href="assets/plugins/multipleselect/multiple-select.css">
</head>
<body class="bg-account">
	<!-- page -->
	<div id="spinner-div">
		<img src="assets/images/icons/loader.svg" alt="loader">
	</div>
	<div class="page">

		<!-- page-content -->
		<div class="page-content">
			<div class="page">
				<!-- page-content -->
				<div class="page-content">
					<div class="container text-dark">
						<!-- row -->
						<div class="row justify-content-center align-self-center">
							<div class="col-xl-8 col-lg-12 col-md-12 col-sm-12">
								<div class="card">
									<div class="card-header">
										<h4>Create Trustline</h4>
									</div>
									<div class="card-body">
										<form id="create-trustline-form" method="post">

											<div class="form-group">
												<label>Select Issuer</label>
												<%if(recordsArray!=null){ %>
												<select multiple="multiple" id="sel_issuers"
													name="sel_issuers" class="multi-select">
													<%for(int i=0; i<recordsArray.size(); i++){ 
																assetObj = new JsonObject();
																assetObj = (JsonObject) recordsArray.get(i);%>

													<option
														value="<%=assetObj.get("asset_issuer").getAsString()%>"><%=assetObj.get("asset_code").getAsString()+" "+assetObj.get("asset_issuer").getAsString() %></option>
													<%} %>
												</select>
												<%} %>
											</div>

											<div class="form-group">
												<label class="form-label"><span>Private Key</span> <span
													style="color: red;">*</span></label> <input type="password"
													name="input_private_key" id="input_private_key"
													class="form-control" placeholder="Enter Private Key">
											</div>

										</form>
										<div class="transfer-coin-button d-grid gap-2 col-8 mx-auto">
											<button class="btn btn-info" type="button"
												id="btn_create_trustline" name="btn_create_trustline"
												onclick="javascript: fnPasswordSetUpWallet()">Create
												Trustline</button>
										</div>
									</div>

								</div>
							</div>

						</div>
						<!-- row end -->
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
	<form method="post" id="post-form">
		<input type="hidden" name="qs" value=""> <input type="hidden"
			name="rules" value="">
	</form>
	<!-- Jquery js-->
	<script src="assets/js/vendors/jquery-3.2.1.min.js"></script>

	<!--Bootstrap.min js-->
	<script src="assets/plugins/bootstrap/popper.min.js"></script>
	<script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>
	<!-- Star Rating js-->
	<script src="assets/plugins/rating/jquery.rating-stars.js"></script>
	<!--Jquery Sparkline js-->
	<script src="assets/js/vendors/jquery.sparkline.min.js"></script>

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

	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<!-- Custom js-->
	<script src="assets/js/_part_create_trustline_page.js"></script>
	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>
	<!--MutipleSelect js-->
	<script src="assets/plugins/multipleselect/multiple-select.js"></script>
	<script src="assets/plugins/multipleselect/multi-select.js"></script>

</body>
</html>
<%
}catch (Exception e){
	out.println ("Exception : "+e.getMessage());
}finally{
	if(issuers !=null) issuers= null;
	if(recordsArray !=null) recordsArray= null;
	if(assetObj !=null) assetObj= null;
	if (context!=null)context=null;
}
%>
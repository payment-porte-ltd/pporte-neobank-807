<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
String relationshipNo=null;
ArrayList<AssetCoin> arrAssetCoins = null;
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callException(request, response, context,"Session has expired, please login again.");
		return;
	}
	if (request.getAttribute("digitalcurrencies") != null) 
		arrAssetCoins = (ArrayList<AssetCoin>) request.getAttribute("digitalcurrencies");
	 relationshipNo= ((User)session.getAttribute("SESS_USER")).getRelationshipNo();
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
<title>Register Digital Currency</title>

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
<!-- Custom css -->
<link href="assets/css/app.css" rel="stylesheet" />
<!-- Custom css -->
<link href="assets/css/style2.css" rel="stylesheet" />
<!-- Custom css -->
<link href="assets/css/style5.css" rel="stylesheet">
<!-- Responsive css -->
<link href="assets/css/responsive.css" rel="stylesheet" />

</head>

<body class="app sidebar-mini rtl" onload="fnGetRelNo()">

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
							<li class="breadcrumb-item"><a href="#">Forex Currencies</a></li>
							<li class="breadcrumb-item active" aria-current="page">Add
								Currency</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- page -->
					<div class="page">
						<!-- page-content -->
						<div class="page-content">
							<div class="container text-dark">
								<!-- row -->
								<div class="row justify-content-center align-self-center">
									<div class="col-xl-6 col-lg-12 col-md-12 col-sm-12">
										<div class="card">
											<div class="card-header">
												<h4>
													<span id="title_label">Forex Digital Currencies</span>
												</h4>
											</div>
											<div class="card-body">
												<form id="create-digital-currency-form" method="post">
													<% if(arrAssetCoins!=null){%>
													<div class="form-group">
														<label>Choose Currency </label> <select
															class="form-control" name="selcurrency" id="selcurrency">
															<option disabled="disabled" value="-1" selected>Select
																Coin</option>
															<% for (int i=0; i<arrAssetCoins.size();i++){ %>
															<option class="icon-btcoin"
																value="<%=((AssetCoin)arrAssetCoins.get(i)).getAssetCode()%>"><%=((AssetCoin)arrAssetCoins.get(i)).getAssetDescription()%>
															</option>
															<% 
																				} 
																			  %>
														</select>
													</div>
													<%	 } %>

													<div class="form-group">
														<label class="form-label"><span>Private Key</span>
															<span style="color: red;">*</span></label> <input type="password"
															name="input_private_key" id="input_private_key"
															class="form-control" placeholder="Enter Private Key">
													</div>

												</form>
												<div class="transfer-coin-button">
													<button class="theme-btn" id="btn_reg_currency"
														name="btn_reg_currency"
														style="background: 00008B; border: none; border-radius: 26px;"
														onclick="javascript:fnRegCurrency()">Submit</button>
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

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>

	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>


	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>


	<!-- Custom js-->
	<script src="assets/js/_cust_register_digital_currency_page.js"></script>

	<script>
		function fnChangePageLanguage(lang){
			//alert('inside lang change: ' +lang);
			fnChangePageLang(lang)
			//fnChangePageLang(lang)
		}
		var relno='';
		function fnGetRelNo(){
			 relno = '<%=relationshipNo%>';
			 console.log("REl no "+relno);
		}
		
		</script>
</body>
</html>


<%
}catch (Exception e){
	out.println ("Exception : "+e.getMessage());
}finally{
	if ( relationshipNo!=null); relationshipNo=null;
	if ( arrAssetCoins!=null); arrAssetCoins=null;
	if (context!=null)context=null;
}
%>
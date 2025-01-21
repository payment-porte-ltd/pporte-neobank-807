<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*, com.pporte.utilities.Utilities,org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
String relationshipNo=null;
String customerName=null;
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

<!-- Title -->
<title>Show All Cards Page</title>

<!--Bootstrap.min css-->
<link rel="stylesheet"
	href="assets/plugins/bootstrap/css/bootstrap.min.css">

<!-- Dashboard css -->
<link href="assets/css/style.css" rel="stylesheet" />
<link href="assets/css/cards.css" rel="stylesheet" />

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
							<li class="breadcrumb-item"><a href="#"
								id="breadcrumb_item_label">Manage Cards</a></li>
							<li class="breadcrumb-item active" aria-current="page"
								id="breadcrumb_item_active_label">Show Cards</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->

					<!-- Container -->
					<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
						<div class="card">
							<div class="card-header">
										<h4 id="view_cards_header">View Cards</h4>
									</div>
							<div class="row card-body">
								<div class="col col-auto">
									<a class="btn new-default-btn mt-4 mt-sm-0 float-right" href="#"
										onclick="javscript:fnCallRegisterCardPage();return false"><i
										class="fe fe-plus mr-1 mt-1"></i> <span
										id="label_add_new_card_btn">Add New Card</span></a>
								</div>
							</div>
							
								<div id="card_div" style="margin-left:40px;"></div>
						
								
							
							<div class="hidden mt-5" id="no_cards"></div>
						
					</div>
				</div>
				<!-- row end -->


			</div>
		</div>
		<!-- End app-content-->

	</div>
	<form method="post" id="showcards-form">
		<input type="hidden" name="qs" value=""> <input type="hidden"
			name="rules" value=""> <input type="hidden" name="relno"
			value=""> <input type="hidden" name="hdnlang" value="">

	</form>
	<!-- End Page -->
	<div style="display: none">
		<span id="validation_error_swal_header">Oops..</span> <span
			id="validation_error_swal_checkdata">Please check your data</span> <span
			id="swal_connection_prob">Problem with connection</span> <span
			id="label_valid">VALID</span> <span id="label_to"> TO</span> <span
			id="label_card_number">Card Number:</span> <span
			id="label_account_name">Account Name:</span>
			<span id="label_no_card">No cards registered.Click Add card to register</span>
			<span id="hdnlang"></span>
	</div>

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

	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>

	<!-- Custom js-->
	<script src="assets/js/_cust_show_all_cards_page.js"></script>

	<script>
		
			$(function() {
				console.log('language is ','<%=langPref%>')
				fnChangePageLanguage('<%=langPref%>');
			});
			
		var relno='';
		function fnGetRelNo(){
			 relno = '<%=relationshipNo%>';
			 console.log("REl no "+relno);
			 fnGetCards();
		}
		</script>
</body>
</html>


<%
}catch (Exception e){
	out.println ("Exception : "+e.getMessage());
}finally{
	if ( relationshipNo!=null); relationshipNo=null;
	if (context!=null)context=null; if (langPref!=null)langPref=null;
}
%>
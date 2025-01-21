<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
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
<!-- Title -->
<title>View Dispute</title>
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
<!-- Data table css -->
<link href="assets/plugins/datatable/dataTables.bootstrap4.min.css"
	rel="stylesheet" />
<link href="assets/plugins/datatable/responsivebootstrap4.min.css"
	rel="stylesheet" />

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
								id="breadcrumb_item_label">Disputes</a></li>
							<li class="breadcrumb-item active" aria-current="page"
								id="breadcrumb_item_active_label">View Dispute</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->

					<!-- row -->
					<div class="row">
						<div class="col col-auto"></div>
						<div class="col-lg-12">
							<div class="e-panel card">
								<div class="card-header">
									<h4>
										<span id="label_disputes">Disputes</span>
									</h4>
									<div class="ml-auto">
										<div class="input-group">
											<a class="btn new-default-btn mt-4 mt-sm-0 float-right" href="#"
												onclick="javscript:fnCallAddDisputePage();return false">
												<i class="fe fe-plus mr-1 mt-1"></i> <span
												id="label_raise_dispute_btn">Raise Dispute</span>
											</a>
										</div>
									</div>
								</div>

								<div class="card-body">
									<div id="disputetable"></div>
								</div>
							</div>
						</div>
					</div>
					<!-- row end -->


				</div>
			</div>
			<!-- End app-content-->

		</div>
		<form method="post" id="dispute-form">
			<input type="hidden" name="qs" value=""> <input type="hidden"
				name="rules" value=""> <input type="hidden" name="relno"
				value=""> <input type="hidden" name="hdnlang"
				id="hdnlang" value="">
		</form>
		<form method="post" id="view-dispute-form">
			<input type="hidden" name="qs" value=""> <input type="hidden"
				name="rules" value=""> <input type="hidden" name="hdnreqid"
				value=""> <input type="hidden" name="hdnlang"
				id="hdnlangnav" value="">
		</form>
		<div style="display: none">
			<span id="swal_connection_prob">Problem with connection</span>
			<span id="label_disputeid">Dispute ID</span>
			<span id="label_reason">Reason</span>
			<span id="label_status">Status</span>
			<span id="label_raisedon">Raised On</span>
			<span id="label_action">Action</span>
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
	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>

	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>
	<!-- Data tables js-->
	<script src="assets/plugins/datatable/jquery.dataTables.min.js"></script>
	<script src="assets/plugins/datatable/dataTables.bootstrap4.min.js"></script>
	<script src="assets/plugins/datatable/datatable.js"></script>
	<!-- Custom js-->
	<script src="assets/js/_cust_view_disputes_page.js"></script>

	<script>
		$(function() {
			console.log('language is ','<%=langPref%>')
			fnChangePageLanguage('<%=langPref%>');
		});
		
		var relno='';
		function fnGetRelNo(){
			 relno = '<%=relationshipNo%>';
			 fnGetDisputes(relno);
		}
		
		</script>
</body>
</html>


<%
}catch (Exception e){
	out.println ("Exception : "+e.getMessage());
}finally{
	if ( relationshipNo!=null); relationshipNo=null;if (context!=null)context=null;
	if (langPref!=null)langPref=null;
}
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*, com.pporte.utilities.Utilities,com.pporte.model.*, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
List<DisputeReasons> aryDisputeReasons = null; 
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}
	
	if(request.getAttribute("dsptreason")!=null)
		aryDisputeReasons=(List<DisputeReasons>)request.getAttribute("dsptreason");
	
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
<title>Raise Customer Disputes</title>

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


<!-- Sidebar Accordions css -->
<link href="assets/plugins/accordion1/css/easy-responsive-tabs.css"
	rel="stylesheet">

<!-- Rightsidebar css -->
<link href="assets/plugins/sidebar/sidebar.css" rel="stylesheet">

<!---Font icons css-->
<link href="assets/plugins/iconfonts/plugin.css" rel="stylesheet" />
<link href="assets/plugins/iconfonts/icons.css" rel="stylesheet" />
<link href="assets/fonts/fonts/font-awesome.min.css" rel="stylesheet">


<!--Sweetaler css-->
<link href="assets/plugins/sweetalert2/sweetalert2.css">

</head>

<body class="app sidebar-mini rtl">

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
			<jsp:include page="ops_topandleftmenu.jsp" />

			<!-- app-content-->
			<div class="app-content  my-3 my-md-5 toggle-content">
				<div class="side-app" style="height: 89vh;">

					<!-- page-header -->
					<div class="page-header">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#">Manage Dispute</a></li>
							<li class="breadcrumb-item active" aria-current="page">Raise
								Customer Disputes</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- row -->
					<!-- row -->
					<div class="row justify-content-center align-self-center">
						<div class="d-flex h-100">
							<div class="card card-profile  overflow-hidden ">
								<div class="card-body">
									<form id="raise-dipute-form" method="post">
										<% if(aryDisputeReasons!=null){%>
										<div class="form-group">
											<label>Select Dispute reason</label> <select
												class="form-control select2 custom-select" id="hdnreasonid"
												name="hdnreasonid"
												data-placeholder="Select merchant pricing plan">
												<option disabled="disabled" selected="selected">Select
													Dispute Reason</option>
												<% for (int i=0; i<aryDisputeReasons.size();i++){ 
																			if(StringUtils.equals( ((DisputeReasons)aryDisputeReasons.get(i)).getStatus(),"A" )){ %>
												<option
													value="<%=((DisputeReasons)aryDisputeReasons.get(i)).getDisputeReasonId()%>"><%=((DisputeReasons)aryDisputeReasons.get(i)).getDisputeReasonDesc()%>
												</option>
												<% } 
																		}  %>
											</select>
										</div>
										<%	 } %>
										<div class="form-group">
											<label class="form-label">Enter Transaction code
												(Optional)</label> <input type="text" name="inputtransactionid"
												id="inputtransactionid" class="form-control"
												placeholder="Enter Transaction code">
										</div>
										<div class="form-group mb-0">
											<label class="form-label">Enter Dispute description</label>
											<textarea class="form-control" name="dsptcomment"
												id="dsptcomment" rows="4"
												placeholder="Enter Dispite description.."></textarea>
										</div>
										<div class="form-group mb-0">
											<label class="form-label">Reference Number</label> <input
												type="text" name="inputreferenceno" id="inputreferenceno"
												class="form-control"
												placeholder="Enter Customer Relationship Number">
										</div>
										<a href="#" class="btn btn-primary float-right mt-1"
											onclick="javascript:fnSubmitDispute()">Send</a> <input
											type="hidden" name="qs" value=""> <input
											type="hidden" name="rules" value="">
									</form>
								</div>
							</div>
						</div>

					</div>
					<!-- row end -->


					<form method="post" id="get-page-form">
						<input type="hidden" name="qs" value=""> <input
							type="hidden" name="rules" value="">
					</form>
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

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>

	<!-- Sidebar Accordions js -->
	<script src="assets/plugins/accordion1/js/easyResponsiveTabs.js"></script>

	<!-- Custom scroll bar js-->
	<script
		src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>

	<!-- Sweetalert js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.all.min.js"></script>

	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>
	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_ops_dispute_opsraise_dsptpage.js"></script>


	<script>
			function fnChangePageLanguage(lang){
				//alert('inside lang change: ' +lang);
				fnChangePageLang(lang)
				//fnChangePageLang(lang)
			}
		</script>
</body>
</html>


<%
}catch (Exception e){
	out.println ("Exception : "+e.getMessage());
}finally{
	if (aryDisputeReasons!=null) aryDisputeReasons=null;if (context!=null)context=null;
}
%>
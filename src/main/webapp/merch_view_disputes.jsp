<%@page import="com.pporte.utilities.Utilities"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
List<Disputes> aryAllDisputes = null;
	try{
	if(request.getAttribute("alldisputes")!=null)  	aryAllDisputes=(ArrayList)request.getAttribute("alldisputes");

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
<title>View Disputes</title>
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
<!-- Data table css -->
<link href="assets/plugins/datatable/dataTables.bootstrap4.min.css"
	rel="stylesheet" />
<link href="assets/plugins/datatable/responsivebootstrap4.min.css"
	rel="stylesheet" />
<!--Mutipleselect css-->
<link rel="stylesheet"
	href="assets/plugins/multipleselect/multiple-select.css">

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
							<li class="breadcrumb-item"><a href="#">Disputes</a></li>
							<li class="breadcrumb-item active" aria-current="page">View
								Disputes</li>
						</ol>
						<!-- End breadcrumb -->

					</div>
					<!-- End page-header -->

					<!-- row -->
					<div class="row">
						<div class="col-lg-12">
							<div class="e-panel card">
								<div class="card-header">
									<h4>Disputes</h4>
								</div>
								<div class="card-body">
									<div class="e-table">
										<div class="table-responsive table-lg">
											<table table id="example"
												class="table table-striped table-bordered text-nowrap w-100">
												<thead>
													<tr>
														<th>Dispute ID</th>
														<th>Reason</th>
														<th>Status</th>
														<th>Raised On</th>
														<th class="text-center">Actions</th>
													</tr>
												</thead>
												<tbody>
													<% if(aryAllDisputes!=null){
															for(int i=0;i<aryAllDisputes.size();i++){
														%>
													<tr>
														<td class="text-nowrap align-middle"><%=((Disputes)aryAllDisputes.get(i)).getDisputeId()%>
														</td>
														<td class="text-nowrap align-middle"><%=((Disputes)aryAllDisputes.get(i)).getReasonDesc()%></td>
														<td class="text-nowrap align-middle"><%=((Disputes)aryAllDisputes.get(i)).getStatus()%></td>
														<td class="text-nowrap align-middle"><%=((Disputes)aryAllDisputes.get(i)).getRaisedOn() %></td>
														<td class="text-center align-middle">
															<div class="btn-group align-top">
																<button class="btn btn-sm btn-primary badge"
																	onclick="javascript:fnShowRequestDetail('<%=((Disputes)aryAllDisputes.get(i)).getDisputeId()%>');return false;"
																	type="button">View</button>
															</div>
														</td>
													</tr>
													<% }
														}else{ %>
													<tr class="text-nowrap align-middle">
														<td><span>No data Present</span></td>
													</tr>
													<% } %>
												</tbody>
											</table>
										</div>

									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- row end -->

				</div>

				<form method="post" id="get-page-form">
					<input type="hidden" name="qs" value=""> <input
						type="hidden" name="rules" value="">
				</form>
				<form method="post" id="view-dispute-form">
					<input type="hidden" name="qs" value=""> <input
						type="hidden" name="rules" value=""> <input type="hidden"
						name="hdnreqid" value="">
				</form>
				<!--footer-->

				<!-- End Footer-->

			</div>
			<!-- End app-content-->
		</div>
	</div>
	<!-- End Page -->

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
	<script src="assets/js/_merch_view_disputes.js"></script>
</body>
</html>

<% 
}catch (Exception e){
	out.println("Exception is "+e.getMessage());
}finally{
	if(aryAllDisputes!=null) aryAllDisputes=null; 
}

%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
ArrayList<Merchant> listMerchant = null;
ConcurrentHashMap<String, String> hashStatus = null;
try{
	if(request.getAttribute("branchdata")!=null)	listMerchant = (ArrayList<Merchant>)request.getAttribute("branchdata");
	hashStatus = new ConcurrentHashMap<String, String>();
   	hashStatus.put("A", "Active"); hashStatus.put("I", "Inactive");
   	hashStatus.put("V", "Waiting for Verification"); 
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
<title>Manage Merchant</title>
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
<!-- Data table css -->
<link href="assets/plugins/datatable/dataTables.bootstrap4.min.css"
	rel="stylesheet" />
<link href="assets/plugins/datatable/responsivebootstrap4.min.css"
	rel="stylesheet" />
</head>
<body class="app sidebar-mini rtl">
	<!--Global-Loader-->
	<div id="global-loader">
		<img src="assets/images/icons/loader.svg" alt="loader">
	</div>
	<div class="page">
		<div class="page-main">
			<!-- Sidebar menu-->
			<jsp:include page="merch_topandleftmenu.jsp" />
			<!--sidemenu end-->
			<!-- app-content-->
			<div class="app-content  my-3 my-md-5 toggle-content">
				<div class="side-app">
					<!-- page-header -->
					<div class="page-header">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#">Profile</a></li>
							<li class="breadcrumb-item active" aria-current="page">Manage
								branches</li>
						</ol>
						<!-- End breadcrumb -->
						<div class="ml-auto">
							<div class="input-group">
								<a class="btn btn-primary text-white mr-2" id="btn-add-branch"
									onclick="javascript:fnGetAddBranchPage()"> <span> <i
										class="fa fa-plus"></i> Add Branch
								</span>
								</a>
							</div>
						</div>
					</div>
					<!-- End page-header -->

					<!-- row -->
					<div class="row">
						<div class="col-md-12 col-lg-12">
							<div class="card">
								<div class="card-header">
									<div class="card-title">Branches</div>
								</div>
								<div class="card-body">
									<div class="table-responsive">
										<table id="example"
											class="table table-striped table-bordered text-nowrap w-100">
											<thead>
												<tr>
													<th class="wd-15p">Company Name</th>
													<th class="wd-15p">Account Number</th>
													<th class="wd-20p">Created On</th>
													<th class="wd-15p">Status</th>
												</tr>
											</thead>
											<tbody>
												<% if(listMerchant!=null){
													for(int i=0;i<listMerchant.size();i++){
											%>
												<tr>
													<td><%= ((Merchant)listMerchant.get(i)).getCompanyName() %></td>
													<td><%= ((Merchant)listMerchant.get(i)).getMerchantCode() %></td>
													<td><%= ((Merchant)listMerchant.get(i)).getCreatedOn() %></td>
													<td><%=hashStatus.get(((Merchant)listMerchant.get(i)).getStatus())%></td>
												</tr>

												<% }
											}else{ %>
												<tr>
													<td><span>No data Present</span></td>
												</tr>
												<% } %>

											</tbody>
										</table>
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
			<form method="post" id="get-add-branch-form">
				<input type="hidden" name="qs" value=""> <input
					type="hidden" name="rules" value="">
			</form>
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
	<!-- Data tables js-->
	<script src="assets/plugins/datatable/jquery.dataTables.min.js"></script>
	<script src="assets/plugins/datatable/dataTables.bootstrap4.min.js"></script>
	<script src="assets/plugins/datatable/datatable.js"></script>
	<script src="assets/plugins/datatable/datatable-2.js"></script>
	<script src="assets/plugins/datatable/dataTables.responsive.min.js"></script>
	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_merch_manage_branches.js"></script>
</body>
</html>

<% 
}catch(Exception e){

}finally{
	if(listMerchant!=null) listMerchant=null;
	if(hashStatus!=null) hashStatus=null;
	
}

%>
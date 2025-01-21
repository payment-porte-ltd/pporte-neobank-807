<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*, com.pporte.model.*,com.pporte.utilities.Utilities,org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%

ArrayList<Loyalty> arrLoyaltyPoints = null;	 ConcurrentHashMap<String, String> hashPaymode = null;

ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callException(request, response, context,"Session has expired, please login again.");
		return;
	}
	if(request.getAttribute("loyaltypointsdetails")!=null)	arrLoyaltyPoints = (ArrayList<Loyalty>)request.getAttribute("loyaltypointsdetails");
	if(request.getAttribute("loyaltyrulesdesc")!=null)	hashPaymode = (ConcurrentHashMap<String, String>)request.getAttribute("loyaltyrulesdesc");
	
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
<title>View Points</title>

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

<!-- Custom css -->
<link href="assets/css/app.css" rel="stylesheet" />
<!-- Responsive css -->
<link href="assets/css/responsive.css" rel="stylesheet" />
<!-- Data table css -->
<link href="assets/plugins/datatable/dataTables.bootstrap4.min.css"
	rel="stylesheet" />
<link href="assets/plugins/datatable/responsivebootstrap4.min.css"
	rel="stylesheet" />

<!-- Custom css -->
<link href="assets/css/style2.css" rel="stylesheet" />

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
			<jsp:include page="cust_topandleftmenu.jsp" />

			<!-- app-content-->
			<div class="app-content  my-3 my-md-5 toggle-content">
				<div class="side-app" style="height: 89vh;">

					<!-- page-header -->
					<div class="page-header">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#">Manage Loyalty</a></li>
							<li class="breadcrumb-item active" aria-current="page">View
								Points</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<div class="page">
						<!-- page-content -->
						<div class="page-content">
							<div class="wallet-box-scroll">
								<div class="wallet-bradcrumb">
									<h3>
										<i class="fa fa-wallet"></i> View Points
									</h3>
								</div>

								<div class="row">
									<div class="col-lg-12">
										<div class="card card-hover">
											<!-- <div class="card-header">
												<div class="card-title">Transactions Details</div>
											</div> -->
											<div class="card-body">
												<div class="table-responsive">
													<table id="custloyaltytable"
														class="table table-striped table-bordered text-nowrap w-100">
														<thead>
															<tr>
																<th>Date</th>
																<th>Wallet Id</th>
																<th>Payment Type</th>
																<th>Reference Number</th>
																<th>Points Awarded</th>
																<th>Points Balance</th>
															</tr>
														</thead>
														<tbody id="wallet_loyalty_txns">
															<% if(arrLoyaltyPoints!=null){
																for(int i=0;i<arrLoyaltyPoints.size();i++){
															%>
															<tr>
																<td><%= ((Loyalty)arrLoyaltyPoints.get(i)).getTxnDate() %></td>
																<td><%= ((Loyalty)arrLoyaltyPoints.get(i)).getWalletId() %></td>
																<%if((hashPaymode.get( ((Loyalty)arrLoyaltyPoints.get(i)).getPayMode() )) != null){ %>
																<td><%=hashPaymode.get( ((Loyalty)arrLoyaltyPoints.get(i)).getPayMode() ) %>
																</td>
																<%}else{ %>
																<td><%=((Loyalty)arrLoyaltyPoints.get(i)).getPayMode()  %>
																</td>
																<%} %>
																<td><%= ((Loyalty)arrLoyaltyPoints.get(i)).getTxnReference() %></td>
																<td><%= ((Loyalty)arrLoyaltyPoints.get(i)).getPointAccrued() %></td>
																<td><%= ((Loyalty)arrLoyaltyPoints.get(i)).getPointBalance() %></td>


															</tr>
															<% }
															}else{ %>
															<tr>
																<td colspan="6">No data Present</td>
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

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>

	<!-- Custom scroll bar js-->
	<script
		src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>

	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_cust_loyalty_view_page.js"></script>

	<!-- Data tables js-->
	<script src="assets/plugins/datatable/jquery.dataTables.min.js"></script>
	<script src="assets/plugins/datatable/dataTables.bootstrap4.min.js"></script>
	<script src="assets/plugins/datatable/datatable.js"></script>
	<script src="assets/plugins/datatable/datatable-2.js"></script>
	<script src="assets/plugins/datatable/dataTables.responsive.min.js"></script>


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
	if(arrLoyaltyPoints !=null ) arrLoyaltyPoints= null;
	if (context!=null)context=null;
}
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*, com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
OpsDashboard totalUsers=null; String numberOfUsers="";
OpsDashboard fiatWalletBalance=null; String totalFiatWalletBalance=null;


OpsDashboard totalDisputes=null; String numberOfDisputes="";
ArrayList<Customer> arrPendingCustomer = null;
ArrayList<DisputeTracker> arrDispTracker=null;
ServletContext context = null;
try{
	/*if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}*/
	
	if (request.getAttribute("totalusers") != null) totalUsers = (OpsDashboard) request.getAttribute("totalusers");
	if (request.getAttribute("fiatwalletbal") != null) fiatWalletBalance = (OpsDashboard) request.getAttribute("fiatwalletbal");
	if (request.getAttribute("totaldisputes") != null) totalDisputes = (OpsDashboard) request.getAttribute("totaldisputes");
	if(request.getAttribute("pendingcustomers")!=null)	arrPendingCustomer = (ArrayList<Customer>)request.getAttribute("pendingcustomers");
	if(request.getAttribute("tenlatestactivedisputes")!=null)	arrDispTracker = (ArrayList<DisputeTracker>)request.getAttribute("tenlatestactivedisputes");


	if(totalUsers!=null){
		numberOfUsers =totalUsers.getTotalUsers();
		//if(numberOfUsers==null)numberOfUsers = "0";
	}else{
		numberOfUsers = "0";
	}
	if(fiatWalletBalance!=null){
		if(fiatWalletBalance.getFiatWalBal()==null){
			totalFiatWalletBalance ="0";
		}else{
			totalFiatWalletBalance =fiatWalletBalance.getFiatWalBal();
		}
		
	}else{
		totalFiatWalletBalance = "0";
	}

	if(totalDisputes!=null){
		numberOfDisputes =totalDisputes.getTotalDisputes();
		//if(numberOfDisputes==null)numberOfDisputes = "0";
	}else{
		numberOfDisputes = "0";
	}
%>
<!doctype html>
<html lang="en" dir="ltr">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Porte Icon -->
<link rel="icon" href="assets/images/brand/pporte.png"
	type="image/x-icon" />
<link rel="shortcut icon" type="image/x-icon"
	href="assets/images/brand/pporte.png" />

<!-- Title -->
<title>Dashboard Page</title>

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

<!-- Owl Theme css-->
<link href="assets/plugins/owl-carousel/owl.carousel.css"
	rel="stylesheet">

<!-- Custom css -->
<link href="assets/css/style2.css" rel="stylesheet" />
<!-- Responsive css -->
<link href="assets/css/responsive.css" rel="stylesheet" />

</head>

<body class="app sidebar-mini rtl" onload="fnGetBusinessLegderTxn();">

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
					<div class="page-header" style="margin: auto">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#">Welcome</a></li>
							<li class="breadcrumb-item active" aria-current="page">Home</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!--Row-->
					<div class="row">
						<div class="col-md-12">
							<div class="owl-carousel  profiles owl-theme mb-5">

								<div class="item">
									<div class="card overflow-hidden border p-0 mb-0">
										<div class="card-body">
											<div class="wallet-balance-ico"
												style="position: static; text-align: center; margin: 0px auto 15px; width: 60px; height: 60px;">
												<span
													class="bg-success-transparent icon-service text-success"
													style="width: 60px; height: 60px;"> <i
													class="si si-layers fs-2"></i>
												</span>
											</div>
											<div class="mt-3">
												<div class="text-center">
													<div class="item-card2-text">
														<a href="" class="text-dark"><h5
																class="font-weight-semibold mb-1">Total Users</h5></a>
													</div>
													<p class="mb-0"><%=numberOfUsers +" "%>Users
													</p>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="item">
									<div class="card overflow-hidden border p-0 mb-0">
										<div class="card-body">
											<div class="wallet-balance-ico"
												style="position: static; text-align: center; margin: 0px auto 15px; width: 60px; height: 60px;">
												<span class="bg-danger-transparent icon-service text-danger"
													style="width: 60px; height: 60px;"> <i
													class="si si-note  fs-2"></i>
												</span>
											</div>
											<div class="mt-3">
												<div class="text-center">
													<div class="item-card2-text">
														<a href="" class="text-dark"><h5
																class="font-weight-semibold mb-1">Total Disputes
																Open</h5></a>
													</div>
													<p class="mb-0"><%=numberOfDisputes +" "%>Disputes Open
													</p>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="item">
									<div class="card overflow-hidden border p-0 mb-0">
										<div class="card-body">
											<div class="wallet-balance-ico"
												style="position: static; text-align: center; margin: 0px auto 15px; width: 60px; height: 60px;">
												<img src="assets/images/crypto/usd.png" alt="USD"
													height="55" width="55">
											</div>
											<div class="mt-3">
												<div class="text-center">
													<div class="item-card2-text">
														<a href="" class="text-dark"><h5
																class="font-weight-semibold mb-1">Fiat Wallet
																Balance</h5></a>
													</div>
													<p class="mb-0"><%=totalFiatWalletBalance+" "%>USD
													</p>
												</div>
											</div>
										</div>
									</div>
								</div>
								<%if(fiatWalletBalance ==null ){ %>
								<div class="item">
									<div class="card overflow-hidden border p-0 mb-0">
										<div class="card-body">
											<div class="mt-3">
												<div class="text-center">
													<div class="item-card2-text">
														<a href="" class="text-dark"><h5
																class="font-weight-semibold mb-1">No Wallets
																Present</h5></a>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<%} %>

							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xl-12 col-lg-12 col-md-12">
							<div class="card">
								<div class="card-header custom-header">
									<div>
										<h3 class="card-title">Business Ledger Yearly Statistics</h3>
									</div>
								</div>
								<div id="transactions_present">
									<div class="card-body">
										<div class="row">
											<div class="col-xl-12 col-lg-12 col-md-12 border-right">
												<canvas id="businessledgerchart" class="chartsh "></canvas>
											</div>
										</div>
									</div>
								</div>
								<div style="display: none" id="transactions_not_present">
									<div class="card-body">
										<div class="row">
											<div class="col-xl-12 col-lg-12 col-md-12 border-right">
												<h3 class="card-title text-danger">No transactions has
													been done yet</h3>
											</div>
										</div>
									</div>
								</div>

							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-12">
							<div class="card">
								<div class="card-body">
									<div class="card-header custom-header">
										<h4 class="card-title">Customers To Be Approved</h4>
									</div>
									<div class="table-responsive">
										<table id="example3"
											class="table table-striped table-bordered text-nowrap">
											<thead>
												<tr>
													<th>CustomerCode</th>
													<th>Customer Name</th>
													<th>License No/Passport No</th>
													<th>PhoneNumber</th>
													<th>email</th>
													<th>Status</th>
													<th>Created On</th>
													<th>Action</th>
												</tr>
											</thead>
											<tbody>
												<% if( arrPendingCustomer!=null){
															for(int i=0;i<arrPendingCustomer.size();i++){       
														%>
												<tr>
													<td><%=((Customer)arrPendingCustomer.get(i)).getRelationshipNo()%>
													</td>
													<td><%=((Customer)arrPendingCustomer.get(i)).getCustomerName()%>
													</td>
													<%if (((Customer)arrPendingCustomer.get(i)).getNationalId().equals("")){ %>
													<td><%=((Customer)arrPendingCustomer.get(i)).getPassportNo()%>
													</td>
													<% }else { %>
													<td><%=((Customer)arrPendingCustomer.get(i)).getNationalId()%>
													</td>
													<%} %>
													<td><%=((Customer)arrPendingCustomer.get(i)).getContact()%>
													</td>
													<td><%=((Customer)arrPendingCustomer.get(i)).getEmail()%>
													</td>
													<%if (((Customer)arrPendingCustomer.get(i)).getStatus().equals("P")) {%>
													<td>Pending</td>
													<%}else if (((Customer)arrPendingCustomer.get(i)).getStatus().equals("A")){ %>
													<td>Active</td>
													<%}else if (((Customer)arrPendingCustomer.get(i)).getStatus().equals("I")) {%>
													<td>Inactive</td>
													<%} %>

													<td><%=((Customer)arrPendingCustomer.get(i)).getCreatedOn()%>
													</td>
													<td class="text-center align-middle">
														<div class="btn-group align-top">
															<button class="btn btn-sm btn-primary badge"
																type="button"
																onClick="fnEditCustomer('<%=((Customer) arrPendingCustomer.get(i)).getRelationshipNo()%>' );">Verify</button>
														</div>
													</td>

													<% }
														}else{ %>
												
												<tr>
													<td colspan="9"><span id="ops_all_bin_list_errormsg1">No
															Pending Customers </span></td>
												</tr>
												<% } %>

											</tbody>
											<tbody class="border border-warning">

												<tr onClick="javascript: showCustProfile();return false;">

												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-12">
							<div class="card">
								<div class="card-body">
									<div class="card-header custom-header">
										<h4 class="card-title">Pending Disputes</h4>
									</div>
									<div class="table-responsive">
										<table id="example"
											class="table table-striped table-bordered text-nowrap">
											<thead>
												<tr>
													<th>Dispute Id</th>
													<th>Raised By</th>
													<th>UserType</th>
													<th>Ref No</th>
													<th>Reason</th>
													<th>Status</th>
													<th>Action</th>
												</tr>

											</thead>
											<tbody>
												<% if(arrDispTracker!=null){ %>
												<% for(int i=0; i<arrDispTracker.size(); i++){ %>
												<tr>
													<td><%= ((DisputeTracker)arrDispTracker.get(i)).getDisputeId() %></td>
													<td><%=((DisputeTracker)arrDispTracker.get(i)).getRaisedbyUserId() %></td>
													<td><%=((DisputeTracker)arrDispTracker.get(i)).getUserType() %></td>
													<td><%=((DisputeTracker)arrDispTracker.get(i)).getRefNo() %></td>
													<td><%=((DisputeTracker)arrDispTracker.get(i)).getReasonDesc() %></td>
													<%if (((DisputeTracker)arrDispTracker.get(i)).getStatus().equals("P")) {%>
													<td>In Progress</td>
													<%}else if (((DisputeTracker)arrDispTracker.get(i)).getStatus().equals("A")){ %>
													<td>Active</td>
													<%}else if (((DisputeTracker)arrDispTracker.get(i)).getStatus().equals("C")) {%>
													<td>Close</td>
													<%} %>
													<td>
														<div class="btn-group align-top">
															<button class="btn btn-sm btn-primary badge"
																type="button"
																onClick="javascript: fnShowRequestDetail('<%=((DisputeTracker)arrDispTracker.get(i)).getDisputeId()%>');return false;">View</button>
														</div>
													</td>

												</tr>
												<%} %>
												<%} else{ %>
												<tr>
													<td><span> No Dispute Raised Yet</span></td>
												</tr>
												<%} %>

											</tbody>
										</table>

									</div>
								</div>
							</div>
						</div>
					</div>


				</div>
			</div>
			<!-- End app-content-->
			<form method="post" id="get-txn-form">
				<input type="hidden" name="qs" value=""> <input
					type="hidden" name="rules" value="">
			</form>
			<form method="post" id="quick-link-form">
				<input type="hidden" name="qs" value=""> <input
					type="hidden" name="rules" value=""> <input type="hidden"
					name="hdncustomercode" value=""> <input type="hidden"
					name="hdnreqid" value="">
			</form>
			<form method="post" id="get-txn-form">
				<input type="hidden" name="qs" value=""> <input
					type="hidden" name="rules" value="">
			</form>
			<form method="post" id="dispute-form">
				<input type="hidden" name="qs" value=""> <input
					type="hidden" name="rules" value=""> <input type="hidden"
					name="hdnlangref" value="">
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

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>


	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<!-- Custom-charts js-->


	<!--Owl Carousel js -->
	<script src="assets/plugins/owl-carousel/owl.carousel.js"></script>
	<script src="assets/plugins/owl-carousel/owl-main.js"></script>



	<!-- Charts js-->
	<script src="assets/plugins/chart/chart.bundle.js"></script>
	<script src="assets/plugins/chart/utils.js"></script>

	<!-- Custom-charts js-->
	<script src="assets/js/_userdashboard.js"></script>
	<script src="assets/js/index2.js"></script>

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
	if(totalUsers!=null)totalUsers=null;if(numberOfUsers!=null)numberOfUsers=null;
	if(fiatWalletBalance!=null)fiatWalletBalance=null;if(totalFiatWalletBalance!=null)totalFiatWalletBalance=null;
	if(totalDisputes!=null)totalDisputes=null;if(numberOfDisputes!=null)numberOfDisputes=null;
	if(arrPendingCustomer!=null)arrPendingCustomer=null;if(arrDispTracker!=null)arrDispTracker=null;
	if (context!=null)context=null;
}
%>
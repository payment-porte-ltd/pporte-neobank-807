<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*, com.pporte.model.*, com.pporte.utilities.Utilities,org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%

ArrayList<Customer> arrCustomerDetails = null;  
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}

	if(request.getAttribute("allcustomers")!=null)
		arrCustomerDetails = (ArrayList<Customer>)request.getAttribute("allcustomers");

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
<title>Customers Details</title>

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
							<li class="breadcrumb-item"><a href="#">Manage Customers</a></li>
							<li class="breadcrumb-item active" aria-current="page">All
								Approved Customers Details</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->

					<div class="row">
						<div class="col-md-12 col-lg-12">
							<div class="card">
								<div class="card-header">
									<div class="card-title">Search Customers</div>
								</div>
								<div class="card-body">
									<form class="m-t-40" id="searchspecificcust-form"
										name="searchspecificcust-form" method="post">
										<div class="row">
											<div class="col-6">
												<div class="form-group">
													<label class="form-label"> Search search type </label> <select
														class="form-control select2-show-search"
														data-placeholder="Choose search type" id="selsearchtype"
														name="selsearchtype"
														onChange="javascript: fnGetSearchType(); return false">
														<optgroup label="Search type">
															<option selected disabled>Please select</option>
															<option value="Customer_Name">Customer Name</option>
															<option value="Relationship_Number">Relationship
																Number</option>
															<option value="Customer_Id">Customer ID</option>
															<option value="Mobile_Number">Mobile Number</option>
														</optgroup>
													</select>
												</div>
											</div>
											<div class="col-6" id="searchbycard"></div>
											<div class="col text-center">
												<button type="button" class="btn btn-info mb-1 btn-block "
													onclick="javascript:fnSearchForCustomer();return false;">Search</button>
											</div>
										</div>
										<input type="hidden" name="qs" value=""> <input
											type="hidden" name="rules" value="">
									</form>
								</div>
							</div>
						</div>
					</div>

					<!-- row -->
					<div class="row">
						<div class="col-md-12 col-lg-12">
							<div class="card">
								<div class="card-header">
									<div class="card-title">Customers Details</div>
								</div>
								<div class="card-body">
									<div class="table-responsive">
										<table id="example"
											class="table table-striped table-bordered text-nowrap w-100">
											<thead>
												<tr>
													<th>CustomerCode</th>
													<th>Customer Name</th>
													<th>License No/ Passport No</th>
													<th>PhoneNumber</th>
													<th>Email</th>
													<th>Status</th>
													<th>Created On</th>
													<th>Action</th>
													<th>Account Status</th>
													<th>PIN Status</th>
												</tr>
											</thead>
											<tbody>
												<% if( arrCustomerDetails!=null){
													for(int i=0;i<arrCustomerDetails.size();i++){       
												%>
												<tr>
													<td><%=((Customer)arrCustomerDetails.get(i)).getRelationshipNo()%>
													</td>
													<td><%=((Customer)arrCustomerDetails.get(i)).getCustomerName()%>
													</td>
													<%if(((Customer)arrCustomerDetails.get(i)).getNationalId().equals("")) {%>
													<td><%=((Customer)arrCustomerDetails.get(i)).getPassportNo()%>
													</td>
													<%}else { %>
													<td><%=((Customer)arrCustomerDetails.get(i)).getNationalId()%>
													</td>
													<%} %>
													<td><%=((Customer)arrCustomerDetails.get(i)).getContact()%>
													</td>
													<td><%=((Customer)arrCustomerDetails.get(i)).getEmail()%>
													</td>
													<%if (((Customer)arrCustomerDetails.get(i)).getStatus().equals("P")) {%>
													<td>Pending</td>
													<%}else if (((Customer)arrCustomerDetails.get(i)).getStatus().equals("A")){ %>
													<td>Active</td>
													<%}else if (((Customer)arrCustomerDetails.get(i)).getStatus().equals("I")) {%>
													<td>Inactive</td>
													<%} %>


													<td><%=((Customer)arrCustomerDetails.get(i)).getCreatedOn()%>
													</td>
													<td class="text-center align-middle">
														<div class="btn-group align-top">
															<button class="btn btn-sm btn-primary badge"
																type="button"
																onClick="javascript:fnEditCustomer('<%=((Customer) arrCustomerDetails.get(i)).getRelationshipNo()%>' );return false;">Edit
																Customer</button>
														</div>

														<div class="btn-group align-top">
															<button class="btn btn-sm btn-primary badge"
																type="button"
																onClick="javascript:fnOpsViewWallet('<%=((Customer) arrCustomerDetails.get(i)).getRelationshipNo()%>' );return false;">View
																Wallet</button>
														</div>
													</td>
													<% if(  (((Customer)arrCustomerDetails.get(i)).getLoginTries()).equals("3") ) { %>
													<td>Locked&nbsp;&nbsp; &nbsp;
														<div class="btn-group align-top">
															<button class="btn btn-sm btn-primary badge"
																type="button"
																onClick="javascript:fnOpsUnlockCustAccount('<%=((Customer) arrCustomerDetails.get(i)).getRelationshipNo()%>','<%=((Customer) arrCustomerDetails.get(i)).getContact()%>'
																,'<%=((Customer) arrCustomerDetails.get(i)).getEmail()%>'  );return false;">Unlock
																Account</button>
														</div>
													</td>

													<% }else{  %>
													<td><span style="color: black"> Not Locked</span>&nbsp;
													</td>

													<%}%>

													<% if(  (((Customer)arrCustomerDetails.get(i)).getPinTries()).equals("3") ) { %>
													<td>Blocked&nbsp;&nbsp;&nbsp;
														<div class="btn-group align-top">
															<button class="btn btn-sm btn-primary badge"
																type="button"
																onClick="javascript:fnOpsUnblockPin('<%=((Customer) arrCustomerDetails.get(i)).getRelationshipNo()%>','<%=((Customer) arrCustomerDetails.get(i)).getContact()%>'
																,'<%=((Customer) arrCustomerDetails.get(i)).getEmail()%>' );return false;">Unblock
																PIN</button>
														</div>
													</td>

													<% }else{  %>
													<td><span style="color: black">PIN Not Locked</span>&nbsp;
													</td>

													<%}%>

													<% }
												}else{ %>
												
												<tr>
													<td colspan="9"><span id="ops_all_bin_list_errormsg1">No
															available Customers Details </span></td>
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
					<form method="post" id="pendingcust-form">
						<input type="hidden" name="qs" value=""> <input
							type="hidden" name="rules" value=""> <input type="hidden"
							name="hdncustomercode" value=""> <input type="hidden"
							name="hdncustphone" value=""> <input type="hidden"
							name="hdncustemail" value=""> <input type="hidden"
							name="hdnlangpref" id="hdnlangpref3" value="en">
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

	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>

	<!--Side-menu js-->
	<script src="assets/plugins/toggle-sidebar/sidemenu.js"></script>

	<!-- Sidebar Accordions js -->
	<script src="assets/plugins/accordion1/js/easyResponsiveTabs.js"></script>

	<!-- Custom scroll bar js-->
	<script
		src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>

	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_ops_cust_viewallcustpage.js"></script>

	<!-- Data tables js-->
	<script src="assets/plugins/datatable/jquery.dataTables.min.js"></script>
	<script src="assets/plugins/datatable/dataTables.bootstrap4.min.js"></script>
	<script src="assets/plugins/datatable/datatable.js"></script>
	<script src="assets/plugins/datatable/dataTables.responsive.min.js"></script>
	<!-- Sweetalert js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.all.min.js"></script>
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
	if(arrCustomerDetails !=null) arrCustomerDetails=null;	if (context!=null)context=null;
}
%>
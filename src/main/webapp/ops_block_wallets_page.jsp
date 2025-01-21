<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*, com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%

ArrayList<Customer> arrWalletDetails = null;
ArrayList<Customer>arrBlockDetails = null;
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}

	if(request.getAttribute("allwallets")!=null)	arrWalletDetails = (ArrayList<Customer>)request.getAttribute("allwallets");
	if(request.getAttribute("allblockiddetails")!=null)	arrBlockDetails = (ArrayList<Customer>)request.getAttribute("allblockiddetails");

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
<title>Block Wallets</title>

<!--Bootstrap.min css-->
<link rel="stylesheet"
	href="assets/plugins/bootstrap/css/bootstrap.min.css">

<!-- Dashboard css -->
<link href="assets/css/ops_style.css" rel="stylesheet" />

<!-- Custom scroll bar css-->
<link href="assets/plugins/scroll-bar/jquery.mCustomScrollbar.css"
	rel="stylesheet" />

<!---Sweetalert2 css-->
<link href="assets/plugins/sweetalert2/sweetalert2.css" rel="stylesheet" />

<!-- Sidemenu css -->
<link href="assets/plugins/toggle-sidebar/ops_sidemenu.css" rel="stylesheet" />

<!--Daterangepicker css-->
<link
	href="assets/plugins/bootstrap-daterangepicker/daterangepicker.css"
	rel="stylesheet" />

<!-- Sidebar Accordions css -->
<link href="assets/plugins/accordion1/css/easy-responsive-tabs.css"
	rel="stylesheet">

<!-- Time picker css-->
<link href="assets/plugins/time-picker/jquery.timepicker.css"
	rel="stylesheet" />

<!-- Date Picker css-->
<link href="assets/plugins/date-picker/spectrum.css" rel="stylesheet" />



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

<!-- Custom css -->
<link href="assets/css/app.css" rel="stylesheet" />
<!-- Custom css -->
<link href="assets/css/style2.css" rel="stylesheet" />
<!-- Custom css -->
<link href="assets/css/style5.css" rel="stylesheet">
<!-- Responsive css -->
<link href="assets/css/responsive.css" rel="stylesheet" />

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
							<li class="breadcrumb-item"><a href="#">Manage Wallets</a></li>
							<li class="breadcrumb-item active" aria-current="page">Block
								Wallets</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- row -->
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
														class="form-control " id="selsearchtype"
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
					<div class="row">
						<div class="col-md-12 col-lg-12">
							<div class="card">
								<div class="card-header">
									<div class="card-title">All Wallets</div>
								</div>
								<div class="card-body">
									<div class="table-responsive">
										<table id="txn_datatable"
											class="table table-striped table-bordered text-nowrap w-100">
											<thead>
												<tr>
													<th>Customer Id</th>
													<th>Relationship Number</th>
													<th>Wallet Id</th>
													<th>Wallet Description</th>
													<th>Action</th>
												</tr>
											</thead>
											<tbody>
												<% if( arrWalletDetails!=null){
												int cont=arrWalletDetails.size();
													for(int i=0;i<cont;i++){       
												%>
												<tr>
													<td><%=((Customer)arrWalletDetails.get(i)).getCustomerId()%>
													</td>
													<td><%=((Customer)arrWalletDetails.get(i)).getRelationshipNo()%>
													</td>
													<td><%=((Customer)arrWalletDetails.get(i)).getWalletId()%>
													</td>
													<td><%=((Customer)arrWalletDetails.get(i)).getWalletDesc()%>
													</td>
													<%if (((Customer)arrWalletDetails.get(i)).getBlockCodeId().equals("1")){  %>
													<td class="text-center align-middle">
														<div class="btn-group align-top">
															<button class="btn btn-sm btn-primary badge"
																data-target="#user-form-modal" data-toggle="modal"
																type="button"
																onClick="javascript:fnBlockWallet(
																	'<%=((Customer) arrWalletDetails.get(i)).getRelationshipNo()%>',
																	'<%=((Customer) arrWalletDetails.get(i)).getCustomerId()%>' ,
																	'<%=((Customer) arrWalletDetails.get(i)).getWalletId()%>' ,
																	'<%=((Customer) arrWalletDetails.get(i)).getWalletDesc()%>'
																	);return false;">Block</button>
														</div>
													</td>
													<%}else { %>
													<td class="text-center align-middle">
														<div class="btn-group align-top">
															<button class="btn btn-sm btn-danger badge"
																data-target="#unblockWalletModal" data-toggle="modal"
																type="button"
																onClick="javascript:fnUnBlockWallet(
																	'<%=((Customer) arrWalletDetails.get(i)).getRelationshipNo()%>',
																	'<%=((Customer) arrWalletDetails.get(i)).getCustomerId()%>' ,
																	'<%=((Customer) arrWalletDetails.get(i)).getWalletId()%>' ,
																	'<%=((Customer) arrWalletDetails.get(i)).getWalletDesc()%>',
																	'<%=((Customer) arrWalletDetails.get(i)).getBlockCodeId()%>',
																	'<%=((Customer) arrWalletDetails.get(i)).getBlockCodeDesc()%>',
																	);return false;">Unblock</button>
														</div>
													</td>
													<%} %>

													<% }
												}else{ %>
												
												<tr>
													<td colspan="9"><span class="text-danger"
														id="ops_all_bin_list_errormsg1">Wallet not present
													</span></td>
												</tr>
												<% } %>

											</tbody>
										</table>
									</div>
								</div>
								<!-- table-wrapper -->
							</div>
							<!-- Modal -->
							<div class="modal fade" id="user-form-modal" tabindex="-1"
								role="dialog" aria-labelledby="largemodal" aria-hidden="true">
								<div class="modal-dialog modal-lg " role="document">
									<div class="modal-content">
										<div class="modal-header">
											<h5 class="modal-title" id="largemodal1">Block Wallet</h5>
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">X</span>
											</button>
										</div>
										<div class="modal-body">
											<!-- row -->
											<div class="row">
												<div class="col-md-12">
													<div class="card">
														<form id="block_wallet_form" method="post">
															<div class="card-body">
																<div class="row">
																	<div class="col-xl-6">
																		<div class="form-group">
																			<label class="form-label">Relationship No</label> <input
																				type="text" class="form-control"
																				name="viewrelationshipno" id="viewrelationshipno"
																				readonly>
																		</div>
																		<div class="form-group">
																			<label class="form-label">Customer Id</label> <input
																				type="text" class="form-control"
																				name="viewcustomerid" id="viewcustomerid" readonly>
																		</div>
																		<div class="form-group">
																			<label class="form-label">Wallet Id</label> <input
																				type="text" class="form-control" name="viewwalletid"
																				id="viewwalletid" readonly>
																		</div>
																		<div class="form-group">
																			<% if(arrBlockDetails!=null) { %>
																			<label class="form-label">Block Code
																				Description</label> <select class="form-control"
																				name="selblockdesc" id="selblockdesc"
																				onchange="javascript: fnUpdateBlockId(); return false">
																				<option value="" selected disabled>Please
																					select</option>
																				<% for (int i=0; i<arrBlockDetails.size();i++){ %>
																				<%if(((Customer)arrBlockDetails.get(i)).getBlockCodeId().equals("1")==false){ %>
																				<option
																					value="<%= ((Customer)arrBlockDetails.get(i)).getBlockCodeDesc()+","+ ((Customer)arrBlockDetails.get(i)).getBlockCodeId()%>"><%= ((Customer)arrBlockDetails.get(i)).getBlockCodeDesc()%></option>
																				<%} %>
																				<%  } }%>
																			</select>
																		</div>
																		<div class="form-group">
																			<label class="form-label">Block Code Id</label> <input
																				type="text" class="form-control"
																				name="editblockcoddeid" id="editblockcoddeid"
																				readonly>
																		</div>
																	</div>
																</div>
															</div>
															<input type="hidden" name="qs" value=""> <input
																type="hidden" name="rules" value=""> <input
																type="hidden" name="hdnuserid" value=""> <input
																type="hidden" name="hdncustomercode" value=""> <input
																type="hidden" name="hdnblockcodedesc" value="">
															<input type="hidden" name="hdnlangpref" id="hdnlangpref3"
																value="en">
														</form>
													</div>
												</div>
											</div>
											<!-- row -->
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-secondary"
												data-dismiss="modal">Close</button>
											<button type="button" class="btn btn-danger"
												id="btn_block_wallet">Block</button>
										</div>
									</div>
								</div>
							</div>
							<!-- Modal -->
							<div class="modal fade" id="unblockWalletModal" tabindex="-1"
								role="dialog" aria-labelledby="largemodal" aria-hidden="true">
								<div class="modal-dialog modal-lg " role="document">
									<div class="modal-content">
										<div class="modal-header">
											<h5 class="modal-title" id="largemodal1">Unblock Wallet</h5>
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">X</span>
											</button>
										</div>
										<div class="modal-body">
											<!-- row -->
											<div class="row">
												<div class="col-md-12">
													<div class="card">
														<form id="unblock_wallet_form" method="post">
															<div class="card-body">
																<div class="row">
																	<div class="col-xl-6">
																		<div class="form-group">
																			<label class="form-label">Relationship No</label> <input
																				type="text" class="form-control"
																				name="viewrelationshipno_unblock"
																				id="viewrelationshipno_unblock" readonly>
																		</div>
																		<div class="form-group">
																			<label class="form-label">Customer Id</label> <input
																				type="text" class="form-control"
																				name="viewcustomerid_unblock"
																				id="viewcustomerid_unblock" readonly>
																		</div>
																		<div class="form-group">
																			<label class="form-label">Wallet Id</label> <input
																				type="text" class="form-control"
																				name="viewwalletid_unblock"
																				id="viewwalletid_unblock" readonly>
																		</div>
																		<div class="form-group">
																			<label class="form-label">Block Code
																				Description</label> <input type="text" class="form-control"
																				name="viewblockcodedesc_unblock"
																				id="viewblockcodedesc_unblock" readonly>
																		</div>

																		<div class="form-group">
																			<label class="form-label">Block Code Id</label> <input
																				type="text" class="form-control"
																				name="editblockcoddeid_unblock"
																				id="editblockcoddeid_unblock" readonly>
																		</div>
																	</div>
																</div>
															</div>
															<input type="hidden" name="qs" value=""> <input
																type="hidden" name="rules" value="">
														</form>
													</div>
												</div>
											</div>
											<!-- row -->
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-secondary"
												data-dismiss="modal">Close</button>
											<button type="button" class="btn btn-success"
												id="btn_unblock_wallet">Unblock Wallet</button>
										</div>
									</div>
								</div>
							</div>
							<!-- section-wrapper -->
						</div>

					</div>
					<!-- row end -->
					<form method="post" id="block_wallet_id_form">
						<input type="hidden" name="qs" value=""> <input
							type="hidden" name="rules" value=""> <input type="hidden"
							name="hdncustomercode" value=""> <input type="hidden"
							name="hdnblockcodeid" value=""> <input type="hidden"
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


	<!--Side-menu js-->
	<script src="assets/plugins/toggle-sidebar/sidemenu.js"></script>

	<script src="assets/plugins/select2/select2.full.min.js"></script>

	<!-- Custom scroll bar js-->
	<script
		src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>
	<!-- Rightsidebar js -->
	<script src="assets/plugins/sidebar/sidebar.js"></script>
	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>
	<script src="assets/js/select2.js"></script>

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>

	<!-- Data tables js-->
	<script src="assets/plugins/datatable/jquery.dataTables.min.js"></script>
	<script src="assets/plugins/datatable/dataTables.bootstrap4.min.js"></script>
	<script src="assets/plugins/datatable/datatable.js"></script>
	<script src="assets/plugins/datatable/datatable-2.js"></script>
	<script src="assets/plugins/datatable/dataTables.responsive.min.js"></script>
	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>
	<!-- Timepicker js -->
	<script src="assets/plugins/time-picker/jquery.timepicker.js"></script>
	<script src="assets/plugins/time-picker/toggles.min.js"></script>
	<!-- Datepicker js -->
	<script src="assets/plugins/date-picker/spectrum.js"></script>
	<script src="assets/plugins/date-picker/jquery-ui.js"></script>
	<script src="assets/plugins/input-mask/jquery.maskedinput.js"></script>

	<!-- Sidebar Accordions js -->
	<script src="assets/plugins/accordion1/js/easyResponsiveTabs.js"></script>

	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_ops_block_wallets_page.js"></script>


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
	if(arrWalletDetails !=null) arrWalletDetails=null;
	if(arrBlockDetails !=null) arrBlockDetails=null;if (context!=null)context=null;
}
%>
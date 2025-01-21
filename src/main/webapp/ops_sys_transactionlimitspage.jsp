<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*, 	com.pporte.utilities.Utilities,com.pporte.model.*,com.pporte.utilities.*, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
ArrayList<TransactionLimitDetails> arrTransactionLimit = null; ConcurrentHashMap<String, String> hashStatus = null; 
ConcurrentHashMap<String, String> hashUserType = null;
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}
		
	if (request.getAttribute("arrtxnlimits") !=null) arrTransactionLimit = (ArrayList<TransactionLimitDetails>) request.getAttribute("arrtxnlimits");
	
	hashStatus = new ConcurrentHashMap<String, String>();
	hashUserType = new ConcurrentHashMap<String, String>();
	
	hashStatus.put("A", "Active");
	hashStatus.put("I", "Inactive");
	hashStatus.put("P", "Pending");
	
	hashUserType.put("C", "Customer");
	hashUserType.put("M", "Merchant");
	
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
<title>Transaction Pricing</title>

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
<!-- Parsley -->
<link href="assets/plugins/parsley/parsley.css" rel="stylesheet">
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
							<li class="breadcrumb-item"><a href="#">Transaction
									Pricing</a></li>
							<li class="breadcrumb-item active" aria-current="page">Set
								Transaction Limits</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- row -->
					<div class="row">
						<div class="col-md-12 col-lg-12">
							<div class="card">
								<div class="card-header">
									<div class="card-title">Transaction Limit</div>
									<div class="ml-auto">
										<div class="input-group">
											<a class="btn btn-primary text-white mr-2"
												data-toggle="modal" data-target="#addtxnlimitModal"> <span>
													<i class="typcn typcn-plus"></i> Add Transaction Limit
											</span>
											</a>

										</div>
									</div>
								</div>
								<div class="card-body">
									<div class="table-responsive">
										<table id="txn_datatable"
											class="table table-striped table-bordered text-nowrap w-100">

											<thead>
												<tr>
													<th>Limit ID</th>
													<th>Limit Type</th>
													<th>Limit Description</th>
													<th>Limit Amount</th>
													<th>User Type</th>
													<th>Status</th>
													<th>Created On</th>
													<th>Action</th>

												</tr>
											</thead>
											<tbody>
												<%
												if (arrTransactionLimit != null) {
													for (int i = 0; i < arrTransactionLimit.size(); i++) {
												%>
												<tr>
													<td><%=((TransactionLimitDetails) arrTransactionLimit.get(i)).getTxnLimitId()%></td>
													<td><%=((TransactionLimitDetails) arrTransactionLimit.get(i)).getLimitType()%></td>
													<td><%=((TransactionLimitDetails) arrTransactionLimit.get(i)).getLimitDescription()%></td>
													<td>$ <%=Utilities.getMoneyinDecimalFormat(((TransactionLimitDetails) arrTransactionLimit.get(i)).getLimitAmount())%></td>
													<td><%=hashUserType.get( ((TransactionLimitDetails) arrTransactionLimit.get(i)).getUserType())%></td>
													<td><%=hashStatus.get( ((TransactionLimitDetails) arrTransactionLimit.get(i)).getStatus())%></td>
													<td><%=((TransactionLimitDetails) arrTransactionLimit.get(i)).getCreatedOn()%></td>
													<td class="text-center align-middle">
														<div class="btn-group align-top">
															<button class="btn btn-sm btn-primary badge"
																data-target="#user-form-modal" data-toggle="modal"
																type="button"
																onClick="javascript:fnEditTransactionLimit('<%=((TransactionLimitDetails) arrTransactionLimit.get(i)).getTxnLimitId()%>',
																'<%=((TransactionLimitDetails) arrTransactionLimit.get(i)).getLimitType()%>',
																'<%=((TransactionLimitDetails) arrTransactionLimit.get(i)).getLimitDescription()%>','<%=((TransactionLimitDetails) arrTransactionLimit.get(i)).getLimitAmount()%>',
																'<%=((TransactionLimitDetails) arrTransactionLimit.get(i)).getUserType()%>','<%=((TransactionLimitDetails) arrTransactionLimit.get(i)).getStatus()%>',
																'<%=((TransactionLimitDetails) arrTransactionLimit.get(i)).getCreatedOn()%>');return false;">
																<i class="fa fa-edit"></i>Edit
															</button>
														</div>
													</td>
												</tr>

												<%
													 }
													} else {
													%>

												<tr>
													<td colspan="9"><span id="ops_all_bin_list_errormsg1">No
															Transaction Limits available </span></td>
												</tr>
												<%
												}
												%>

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
					<!-- Modal -->
					<div class="modal fade" id="edittxnlimitModal" tabindex="-1"
						role="dialog" aria-labelledby="largemodal" aria-hidden="true">
						<div class="modal-dialog modal-lg " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="largemodal1">Edit Transaction
										Limits</h5>
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
												<form id="edittxnlimit-form" method="post">
													<div class="card-body">
														<div class="row">
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Transaction Limit ID</label>
																	<input type="text" class="form-control"
																		name="txnlimitid" id="txnlimitid" readonly>
																</div>
																<div class="form-group">
																	<label class="form-label">Limit Type</label> <input
																		type="text" class="form-control" name="limit_type"
																		id="limit_type">
																</div>
																<div class="form-group">
																	<label class="form-label">Limit Description</label> <input
																		type="text" class="form-control" name="limitdesc"
																		id="limitdesc">
																</div>
																<div class="form-group">
																	<label class="form-label">Status</label> <select
																		class="form-control" id="sellstatus" name="sellstatus">
																		<option selected disabled>Select Status</option>
																		<option value="A">Active</option>
																		<option value="I">Inactive</option>
																	</select>
																</div>
															</div>
															<div class="col-xl-6">

																<div class="form-group">
																	<label class="form-label">Limit Amount in USD</label> <input
																		type="number" class="form-control" name="limitamount"
																		id="limitamount">
																</div>

																<div class="form-group">
																	<label class="form-label">User Type</label> <select
																		class="form-control" id="sellusertype"
																		name="sellusertype">
																		<option selected disabled>User Type</option>
																		<option value="C">Customer</option>
																		<option value="M">Merchant</option>
																	</select>
																</div>
																<div class="form-group">
																	<label class="form-label">Created On</label> <input
																		type="text" class="form-control" name="txncreatedon"
																		id="txncreatedon" readonly>
																</div>
															</div>
														</div>
													</div>
													<input type="hidden" name="qs" value=""> <input
														type="hidden" name="rules" value=""> <input
														type="hidden" name="hdnuserid" value=""> <input
														type="hidden" name="hdnlimitstatus" value=""> <input
														type="hidden" name="hdnusertype" value=""> <input
														type="hidden" name="hdnlangpref" id="hdnlangpref3"
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
									<button type="button" class="btn btn-primary"
										id="btn-edittxnlimits">Submit</button>
								</div>
							</div>
						</div>
					</div>

					<!-- End Model -->
					<!-- Modal -->
					<div class="modal fade" id="addtxnlimitModal" tabindex="-1"
						role="dialog" aria-labelledby="largemodal" aria-hidden="true">
						<div class="modal-dialog modal-lg " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="largemodal1">Add Transaction
										Limit</h5>
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
												<form id="addtxnlimitdetails-from" method="post">
													<div class="card-body">
														<div class="row">
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">User Type</label> <select
																		class="form-control" id="selladdusertype"
																		name="selladdusertype">
																		<option value="">Select User Type</option>
																		<option value="C">Customer</option>
																		<option value="M">Merchant</option>
																	</select>
																</div>
																<div class="form-group">
																	<label class="form-label">Limit Type</label> <input
																		type="text" class="form-control" name="addlimit_type"
																		id="addlimit_type"
																		placeholder="Eg:- MTA, WL, LPT etc. (Maximum of 4 Characters)">
																</div>
																<div class="form-group">
																	<label class="form-label">Limit Description</label> <input
																		type="text" class="form-control" name="addlimitdesc"
																		id="addlimitdesc"
																		placeholder="Description of the Limit Type">
																</div>

															</div>
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Status</label> <select
																		class="form-control" id="selladdstatus"
																		name="selladdstatus">
																		<option selected disabled>Select Status</option>
																		<option value="A">Active</option>
																		<option value="P">Inactive</option>
																	</select>
																</div>
																<div class="form-group">
																	<label class="form-label">Limit Amount in USD</label> <input
																		type="number" class="form-control"
																		name="addlimitamount" id="addlimitamount">
																</div>
															</div>
														</div>
													</div>
													<input type="hidden" name="qs" value=""> <input
														type="hidden" name="rules" value=""> <input
														type="hidden" name="hdnuserid" value=""> <input
														type="hidden" name="hdnaddlimitstatus" value=""> <input
														type="hidden" name="hdnaddusertype" value=""> <input
														type="hidden" name="hdnlangpref" id="hdnlangpref3"
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
									<button type="button" class="btn btn-primary"
										id="btn-addtxnlimit">Submit</button>
								</div>
							</div>
						</div>
					</div>

					<!-- End Model -->

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



	<!-- Data tables js-->
	<script src="assets/plugins/datatable/jquery.dataTables.min.js"></script>
	<script src="assets/plugins/datatable/dataTables.bootstrap4.min.js"></script>
	<script src="assets/plugins/datatable/datatable.js"></script>
	<script src="assets/plugins/datatable/datatable-2.js"></script>
	<script src="assets/plugins/datatable/dataTables.responsive.min.js"></script>

	<!-- Parsley js-->
	<script src="assets/plugins/parsley/parsley.min.js"></script>
	<!-- Sweetalert js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.all.min.js"></script>

	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>

	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_ops_sys_transactionlimitspage.js"></script>



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
	if (arrTransactionLimit!=null) arrTransactionLimit=null;  if (hashUserType!=null) hashUserType=null; 
	if (hashStatus!=null) hashStatus=null; if (context!=null)context=null;
	
}
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*, com.pporte.model.*,com.pporte.utilities.Utilities,org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
ArrayList<Risk> arrRiskRisk = null; HashMap <String, String> hashRiskStatus = null; HashMap <String, String> hashPaymentAction = null;
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}
	if (request.getAttribute("merchantrisks") != null) arrRiskRisk = (ArrayList<Risk>) request.getAttribute("merchantrisks");
	
	hashRiskStatus = new HashMap<String, String>();
	hashPaymentAction = new HashMap<String, String>();
	
	hashRiskStatus.put("A", "Active");
	hashRiskStatus.put("P", "Pending");
	
	hashPaymentAction.put("A", "Approval");
	hashPaymentAction.put("D", "Denial");
	hashPaymentAction.put("R", "Report");
	
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
<title>Risk Profile</title>

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
							<li class="breadcrumb-item"><a href="#">Manage Risk</a></li>
							<li class="breadcrumb-item active" aria-current="page">Risk
								Profile</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- row -->
					<div class="row">
						<div class="col-md-12 col-lg-12">
							<div class="card">
								<div class="card-header">
									<div class="card-title">Pending Risks</div>
									<div class="ml-auto">
										<div class="input-group">
											<a class="btn btn-primary text-white mr-2"
												data-toggle="modal" data-target="#newriskModal"> <span>
													<i class="typcn typcn-plus"></i> Add Risk Factor
											</span>
											</a>

										</div>
									</div>
								</div>
								<div class="card-body">
									<div class="table-responsive">
										<table id="pendingmerchtable"
											class="table table-striped table-bordered text-nowrap w-100">
											<thead>
												<tr>
													<th>Risk</th>
													<th>Risk Description</th>
													<th>Status</th>
													<th>Payment Action</th>
													<th>Createdon</th>
													<th>Action</th>
												</tr>
											</thead>
											<tbody>
												<%
												if (arrRiskRisk != null) {
													for (int i = 0; i < arrRiskRisk.size(); i++) {
												%>
												<tr>
													<td><%=((Risk) arrRiskRisk.get(i)).getRiskId()%></td>
													<td><%=((Risk) arrRiskRisk.get(i)).getRiskDesc()%></td>
													<td><%=hashRiskStatus.get(((Risk) arrRiskRisk.get(i)).getRiskStatus())%></td>
													<td><%=hashPaymentAction.get( ((Risk) arrRiskRisk.get(i)).getRiskPaymentAction())%>
													</td>
													<td><%=((Risk) arrRiskRisk.get(i)).getCreatedon()%></td>
													<td class="text-center align-middle">
														<div class="btn-group align-top">
															<button class="btn btn-sm btn-primary badge"
																data-target="#user-form-modal" data-toggle="modal"
																type="button"
																onClick="javascript:fnEditMerchRiskPrf('<%=((Risk) arrRiskRisk.get(i)).getRiskId()%>','<%=((Risk) arrRiskRisk.get(i)).getRiskDesc()%>',
																'<%=((Risk) arrRiskRisk.get(i)).getRiskStatus()%>','<%=((Risk) arrRiskRisk.get(i)).getRiskPaymentAction()%>',
																'<%=((Risk) arrRiskRisk.get(i)).getCreatedon()%>' );return false;">
																<i class="fa fa-edit"></i>Edit
															</button>
														</div>
													</td>

													<%
													}
													} else {
													%>
												
												<tr>
													<td colspan="9"><span id="ops_all_bin_list_errormsg1">No
															Merchant Risk Profile available </span></td>
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
					<div class="modal fade" id="newriskModal" tabindex="-1"
						role="dialog" aria-labelledby="largemodal" aria-hidden="true">
						<div class="modal-dialog modal-lg " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="largemodal1">User Details</h5>
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
												<form id="addmerchrisk-from" method="post">
													<div class="card-body">
														<div class="row">

															<div class="col-xl-6">
																<!-- <div class="form-group">
																<label class="form-label">Risk Id</label> <input type="text" class="form-control" name="riskid" id="merchuid">
															</div> -->
																<div class="form-group">
																	<label class="form-label">Risk Description</label> <select
																		class="form-control" id="sellriskdesc"
																		name="sellriskdesc">
																		<option value="">Select Description</option>
																		<option value="Critical">Critical</option>
																		<option value="High">High</option>
																		<option value="Medium">Medium</option>
																		<option value="Fraud">Fraud</option>
																		<option value="VIP">VIP</option>

																	</select>
																</div>
																<div class="form-group">
																	<label class="form-label">Payment Action</label> <select
																		class="form-control" id="sellpaymentaction"
																		name="sellpaymentaction">
																		<option value="">Select Action</option>
																		<option value="A">Approval</option>
																		<option value="R">Report</option>
																		<option value="D">Denial</option>
																	</select>
																	<!-- <select name="selusertype" id="selusertype_e" required class="form-control">
																			<option value="M" selected>Merchants</option>
																			<option value="C">Customers</option>
																			<option value="O">Operations</option>
																		</select> -->

																</div>
															</div>
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Status</label> <select
																		class="form-control" id="sellriskstatus"
																		name="sellriskstatus">
																		<option value="">Select status</option>
																		<option value="A">Active</option>
																		<option value="P">Pending</option>
																	</select>
																</div>
															</div>


														</div>
													</div>
													<input type="hidden" name="qs" value=""> <input
														type="hidden" name="rules" value=""> <input
														type="hidden" name="hdnuserid" value=""> <input
														type="hidden" name="hdnriskdesc" id="hdnriskdesc" value="">
													<input type="hidden" name="hdnpaymentaction"
														id="hdnpaymentaction" value=""> <input
														type="hidden" name="hdnstatus" id="hdnstatus" value="">
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
									<button type="button" class="btn btn-primary" id="btn-addrisk">Submit</button>
								</div>
							</div>
						</div>
					</div>

					<!-- End Model -->
					<!-- Modal -->
					<div class="modal fade" id="editriskModal" tabindex="-1"
						role="dialog" aria-labelledby="largemodal" aria-hidden="true">
						<div class="modal-dialog modal-lg " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="largemodal1">User Details</h5>
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
												<form id="editmerchrisk-from" method="post">
													<div class="card-body">
														<div class="row">

															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Risk Id</label> <input
																		type="text" class="form-control" name="editriskid"
																		id="editriskid" readonly>
																</div>
																<div class="form-group">
																	<label class="form-label">Risk Description</label> <select
																		class="form-control" id="selleditriskdesc"
																		name="sellriskdesc">
																		<option value="">Select Description</option>
																		<option value="Critical">Critical</option>
																		<option value="High">High</option>
																		<option value="Medium">Medium</option>
																		<option value="Fraud">Fraud</option>
																		<option value="VIP">VIP</option>

																	</select>
																</div>

																<div class="form-group">
																	<label class="form-label">Createdon</label> <input
																		type="text" class="form-control"
																		name="editriskcreatedon" id="editriskcreatedon"
																		readonly>
																</div>
															</div>
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Status</label> <select
																		class="form-control" id="selleditriskstatus"
																		name="sellriskstatus">
																		<option value="">Select status</option>
																		<option value="A">Active</option>
																		<option value="P">Pending</option>
																	</select>
																</div>
																<div class="form-group">
																	<label class="form-label">Payment Action</label> <select
																		class="form-control" id="selleditpaymentaction"
																		name="sellpaymentaction">
																		<option value="">Select Action</option>
																		<option value="A">Approval</option>
																		<option value="R">Report</option>
																		<option value="D">Denial</option>
																	</select>
																</div>
															</div>
														</div>
													</div>
													<input type="hidden" name="qs" value=""> <input
														type="hidden" name="rules" value=""> <input
														type="hidden" name="hdnuserid" value=""> <input
														type="hidden" name="hdneditriskdesc" id="hdnriskdesc"
														value=""> <input type="hidden"
														name="hdneditpaymentaction" id="hdnpaymentaction" value="">
													<input type="hidden" name="hdneditstatus" id="hdnstatus"
														value=""> <input type="hidden" name="hdnlangpref"
														id="hdnlangpref3" value="en">

												</form>
											</div>

										</div>
									</div>
									<!-- row -->
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary"
										data-dismiss="modal">Close</button>
									<button type="button" class="btn btn-primary" id="btn-editrisk">Submit</button>
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

	<div id="error-msg-login-page" style="display: none;">
		<span id="risk-data-validation-error-swal-header">Oops..</span> <span
			id="risk-data-validation-riskid">Please enter Risk Id</span> <span
			id="risk-data-validation-riskdesc">Please enter risk
			description</span> <span id="risk-data-validation-riskstatus">Please
			enter Risk status</span> <span id="risk-data-validation-riskpaymentaction">Please
			enter Payment action</span>

		<!-- Add a form for page redirection upon registration -->
		<form method="post" id="logout-form">
			<input type="hidden" name="qs" value=""> <input type="hidden"
				name="rules" value=""> <input type="hidden" name="hdnuserid"
				value=""> <input type="hidden" name="hdnlangpref"
				id="hdnlangpref3" value="en">
		</form>

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

	<!-- Sweetalert js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.all.min.js"></script>

	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>

	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_ops_merchriskprofilepage.js"></script>


	<script>
		function fnChangePageLanguage(lang) {
			//alert('inside lang change: ' +lang);
			fnChangePageLang(lang)
			//fnChangePageLang(lang)
		}
	</script>
</body>
</html>


<%
} catch (Exception e) {
out.println("Exception : " + e.getMessage());
} finally {
	if(hashPaymentAction !=null) hashPaymentAction = null; if(hashRiskStatus !=null) hashRiskStatus = null;
	if(arrRiskRisk !=null) arrRiskRisk = null;if (context!=null)context=null;
}
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
ArrayList<AssetAccount> allAccounts=null;
ConcurrentHashMap<String,String> hashStatus = null; Iterator<String> itStatus = null; 
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}
	
	if (request.getAttribute("allaccounts") != null) allAccounts = (ArrayList<AssetAccount>) request.getAttribute("allaccounts");
	hashStatus = new ConcurrentHashMap<String,String>();
	hashStatus.put("I","Inactive");	hashStatus.put("A","Active"); 
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
<title>Fund New Accounts</title>

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
					<div class="page-header" style="margin: auto">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item active"><a href="#">Subsidiary
									XLM Accounts</a></li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- page -->
					<div class="page">

						<!-- page-content -->
						<div class="page-content">
							<!-- row -->
							<div class="row">
								<div class="col-md-12 col-lg-12">
									<div class="card" style="margin-top: -255px;">
										<div class="card-header">
											<div class="card-title">Registered Subsidiary XLM
												Accounts</div>
											<div class="ml-auto">
												<div class="input-group">
													<a class="btn btn-primary text-white mr-2"
														data-toggle="modal" data-target="#addBTCxpricingModal">
														<span> <i class="typcn typcn-plus"></i> Add new
															account
													</span>
													</a>

												</div>
											</div>
										</div>
										<div class="card-body">
											<div class="table-responsive">
												<table id="viewbtcxpricingtable"
													class="table table-striped table-bordered text-nowrap w-100">
													<thead>
														<tr>
															<th>Public Key</th>
															<th>Account Balance</th>
															<th>Status</th>
															<th>Date</th>
															<th>Action</th>
														</tr>
													</thead>
													<tbody>
														<%
																				if (allAccounts != null) {
																					for (int i = 0; i < allAccounts.size(); i++) {
																				%>
														<tr>
															<td><%=((AssetAccount) allAccounts.get(i)).getPublicKey()%></td>
															<td><%=((AssetAccount) allAccounts.get(i)).getAssetBalance()%></td>
															<td><%=hashStatus.get(((AssetAccount) allAccounts.get(i)).getStatus())%></td>
															<td><%=((AssetAccount) allAccounts.get(i)).getCreatedOn()%></td>
															<td class="text-center align-middle">
																<div class="btn-group align-top">
																	<button class="btn btn-sm btn-primary badge"
																		type="button"
																		onClick="javascript:fnEditStatus('<%=((AssetAccount) allAccounts.get(i)).getPublicKey()%>',
																								'<%=((AssetAccount) allAccounts.get(i)).getAssetBalance()%>',
																								'<%=((AssetAccount) allAccounts.get(i)).getStatus()%>',
																								'<%=((AssetAccount) allAccounts.get(i)).getCreatedOn()%>');return false;">
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
															<td colspan="6"><span
																id="ops_all_bin_list_errormsg1">No XLM Account
																	Created Yet</span></td>
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
								<!-- Modal -->
								<div class="modal fade" id="addBTCxpricingModal" tabindex="-1"
									role="dialog" aria-labelledby="largemodal" aria-hidden="true">
									<div class="modal-dialog modal-lg " role="document">
										<div class="modal-content">
											<div class="modal-header">
												<h5 class="modal-title" id="largemodal1">Add Account</h5>
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
															<form id="addassetpricing-from" method="post">
																<div class="card-body">
																	<div class="row">
																		<div class="col-xl-6">
																			<div class="form-group ">
																				<label class="form-label">Amount<i>(XLM)</i></label>
																				<div class="input-two-box">
																					<input type="number" class="form-control"
																						name="addamount" id="addamount" value="">
																				</div>
																			</div>

																			<div class="form-group ">
																				<label class="form-label">Public Key</label>
																				<div class="input-two-box">
																					<input type="text" class="form-control"
																						name="addpublickey" id="addpublickey" value="">
																				</div>
																			</div>
																		</div>
																		<div class="col-xl-6">
																			<div class="form-group ">
																				<label class="form-label">Private Key</label>
																				<div class="input-two-box">
																					<input type="password" class="form-control"
																						name="addprivatekey" id="addprivatekey" value="">
																				</div>
																			</div>
																			<div class="form-group">
																				<label class="form-label">Status</label> <select
																					class="form-control" id="seladdtatus"
																					name="seladdtatus">
																					<option value="">Select status</option>
																					<option value="A">Active</option>
																					<option value="I">Inactive</option>
																				</select>
																			</div>
																		</div>

																	</div>
																</div>
																<input type="hidden" name="qs" value=""> <input
																	type="hidden" name="rules" value=""> <input
																	type="hidden" name="hdnaddseladdtatus" value="">
																<input type="hidden" name="hdnaddassetcode" value="">
																<input type="hidden" name="hdnlangpref"
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
												<button type="button" class="btn btn-primary"
													id="btn-addxlmaccount">Submit</button>
											</div>
										</div>
									</div>
								</div>
								<!-- End Model -->
								<!-- Modal -->
								<div class="modal fade" id="EditXLMAccModal" tabindex="-1"
									role="dialog" aria-labelledby="largemodal" aria-hidden="true">
									<div class="modal-dialog modal-lg " role="document">
										<div class="modal-content">
											<div class="modal-header">
												<h5 class="modal-title" id="largemodal1">Edit Account</h5>
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
															<form id="editxlmacc-from" method="post">
																<div class="card-body">
																	<div class="row">
																		<div class="col-xl-6">
																			<div class="form-group ">
																				<label class="form-label">Public Key</label>
																				<div class="input-two-box">
																					<input type="text" class="form-control"
																						name="editpublickey" id="editpublickey" value=""
																						readonly>
																				</div>
																			</div>
																			<div class="form-group ">
																				<label class="form-label">Amount<i>(XLM)</i></label>
																				<div class="input-two-box">
																					<input type="text" class="form-control"
																						name="editaccountbal" id="editaccountbal" value=""
																						readonly>
																				</div>
																			</div>
																		</div>
																		<div class="col-xl-6">
																			<div class="form-group">
																				<label class="form-label">Status</label> <select
																					class="form-control" id="seleditstatus"
																					name="seleditstatus">
																					<option value="">Select status</option>
																					<option value="A">Active</option>
																					<option value="I">Inactive</option>
																				</select>
																			</div>
																			<div class="form-group ">
																				<label class="form-label">Date</label>
																				<div class="input-two-box">
																					<input type="text" class="form-control"
																						name="editcreatedon" id="editcreatedon" value=""
																						readonly>
																				</div>
																			</div>
																		</div>
																		<!-- Choose status -->
																	</div>
																</div>
																<input type="hidden" name="qs" value=""> <input
																	type="hidden" name="rules" value=""> <input
																	type="hidden" name="hdneditseleditstatus" value="">
																<input type="hidden" name="hdnlangpref"
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
												<button type="button" class="btn btn-primary"
													id="btn-editxlmaccount">Submit</button>
											</div>
										</div>
									</div>
								</div>
								<!-- End Model -->
							</div>
							<!-- row end -->
						</div>
					</div>

					<!-- Modal -->

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
	<script src="assets/plugins/datatable/dataTables.responsive.min.js"></script>

	<!-- Sweetalert js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.all.min.js"></script>

	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>

	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_tda_fund_accounts_page.js"></script>

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
	if (context!=null)context=null;if (allAccounts!=null)allAccounts=null;if (hashStatus!=null)hashStatus=null;
	if (itStatus!=null)itStatus=null;
}
%>
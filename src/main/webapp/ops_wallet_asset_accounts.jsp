<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*, com.pporte.model.*, com.pporte.utilities.Utilities,org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
ArrayList<WalletAssetAccounts> arrAccounts = null;
ArrayList<CryptoAssetCoins> arrAssets = null;
ConcurrentHashMap<String, String> hashStatus = null; 
ConcurrentHashMap<String, String> hashAccountType = null; 

ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}
	
	
	if (request.getAttribute("accounts") !=null) 
		arrAccounts = (ArrayList<WalletAssetAccounts>) request.getAttribute("accounts");
	if (request.getAttribute("assets") !=null) 
		arrAssets = (ArrayList<CryptoAssetCoins>) request.getAttribute("assets");

	hashStatus = new ConcurrentHashMap<String, String>();
	hashAccountType = new ConcurrentHashMap<String, String>();
	
	hashStatus.put("A", "Active");
	hashStatus.put("I", "Inactive");
	
	hashAccountType.put("IA", "Issuing account");
	hashAccountType.put("DA", "Distribution Account");
	hashAccountType.put("LA", "Liquidity Account");
	
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
<title>Asset Pricing</title>

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
							<li class="breadcrumb-item"><a href="#">Manage Digital
									Assets</a></li>
							<li class="breadcrumb-item active" aria-current="page">Wallet
								Asset Accounts</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- row -->
					<div class="row">
						<div class="col-md-12 col-lg-12">
							<div class="card">
								<div class="card-header">
									<div class="card-title">Wallet Assets Account
										Configuration</div>
									<div class="ml-auto">
										<div class="input-group">
											<a class="btn btn-primary text-white mr-2"
												data-toggle="modal" data-target="#addassetaccountmodal">
												<span> <i class="typcn typcn-plus"></i> Add Asset
													Account
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
													<th>ID</th>
													<th>ASSET CODE</th>
													<th>ACCOUNT TYPE</th>
													<th>PUBLIC KEY</th>
													<th>STATUS</th>
													<th>CREATED ON</th>
													<th>ACTION</th>
												</tr>
											</thead>
											<tbody>
												<%
												if (arrAccounts != null) {
													for (int i = 0; i < arrAccounts.size(); i++) {
												%>
												<tr>
													<td><%=((WalletAssetAccounts) arrAccounts.get(i)).getSequenceId()%></td>
													<td><%=((WalletAssetAccounts) arrAccounts.get(i)).getAssetCode()%></td>
													<td><%=hashAccountType.get( ((WalletAssetAccounts) arrAccounts.get(i)).getAccountType())%></td>
													<td><%=((WalletAssetAccounts) arrAccounts.get(i)).getPublicKey()%></td>

													<%if(((WalletAssetAccounts) arrAccounts.get(i)).getStatus().equals("A")){ %>
													<td style="color: #2962ff"><%=hashStatus.get( ((WalletAssetAccounts) arrAccounts.get(i)).getStatus())%></td>
													<%}else{ %>
													<td><%=hashStatus.get( ((WalletAssetAccounts) arrAccounts.get(i)).getStatus())%></td>
													<%} %>
													<td><%=((WalletAssetAccounts) arrAccounts.get(i)).getCreatedOn()%></td>
													<%if(((WalletAssetAccounts) arrAccounts.get(i)).getStatus().equals("A")){ %>

													<td class="text-center align-middle">
														<div class="btn-group align-top">
															<button class="btn btn-sm btn-primary badge"
																data-target="#user-form-modal" data-toggle="modal"
																type="button"
																onClick="javascript:fnEditAssetAccount('<%=((WalletAssetAccounts) arrAccounts.get(i)).getSequenceId()%>',
																'<%=((WalletAssetAccounts) arrAccounts.get(i)).getAssetCode() %>','<%=((WalletAssetAccounts) arrAccounts.get(i)).getAccountType() %>',
																'<%=((WalletAssetAccounts) arrAccounts.get(i)).getPublicKey()%>', '<%=((WalletAssetAccounts) arrAccounts.get(i)).getStatus()%>');return false;">
																<i class="fa fa-edit"></i>Edit
															</button>
														</div>
													</td>
													<%}else{ %>
													<td class="text-center align-middle"><span
														class="text-danger">Can't be Edited</span></td>

													<%} %>
												</tr>

												<%
													 }
													} else {
													%>

												<tr>
													<td colspan="9"><span id="ops_all_bin_list_errormsg1">No
															Data available </span></td>
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
					<div class="modal fade" id="editAssetAccount" tabindex="-1"
						role="dialog" aria-labelledby="largemodal" aria-hidden="true">
						<div class="modal-dialog modal-lg " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="largemodal1">Edit Asset
										Pricing</h5>
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
												<form id="editaccount_form" method="post">
													<div class="card-body">
														<div class="row">
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Asset Code</label> <input
																		type="text" class="form-control" name="editassetcode"
																		id="editassetcode" readonly>
																</div>

																<div class="form-group">
																	<label class="form-label">Account Type</label> <select
																		class="form-control" id="selleditaccounttype"
																		name="selleditaccounttype">
																		<option selected disabled value="">Select
																			Account Type</option>
																		<option value="IA">Issuing account</option>
																		<option value="DA">Distribution Account</option>
																		<option value="LA">Liquidity Account</option>
																	</select>
																</div>

																<div class="form-group">
																	<label class="form-label">Public Key</label> <input
																		type="text" class="form-control" name="editpublickey"
																		id="editpublickey">
																</div>
															</div>
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Status</label> <select
																		class="form-control" id="selleditstatus"
																		name="selleditstatus">
																		<option selected disabled value="">Select
																			Status</option>
																		<option value="A">Active</option>
																		<option value="I">Inactive</option>
																	</select>
																</div>
															</div>
														</div>
													</div>
													<input type="hidden" name="hdnsequenceno"
														id="hdnsequenceno" value="">
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
										id="btn_editassetaccount">Submit</button>
								</div>
							</div>
						</div>
					</div>

					<!-- End Model -->
					<!-- Modal -->
					<div class="modal fade" id="addassetaccountmodal" tabindex="-1"
						role="dialog" aria-labelledby="largemodal" aria-hidden="true">

						<div class="modal-dialog modal-lg " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="largemodal1">Add Asset Account</h5>
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
												<form id="add_wallet_asset_account_from" method="post">
													<div class="card-body">
														<div class="row">
															<div class="col-xl-6">
																<div class="form-group">
																	<% if(arrAssets!=null) { %>
																	<label class="form-label">Asset Code</label> <select
																		class="form-control" name="seladdassetcode"
																		id="seladdassetcode"
																		onchange="javascript:UpdateassetDescParam(); return false;">
																		<option value="">Select Asset</option>
																		<% for (int i=0; i<arrAssets.size();i++){ %>
																		<option
																			value="<%=((CryptoAssetCoins) arrAssets.get(i)).getAssetCode()%>"><%=((CryptoAssetCoins) arrAssets.get(i)).getAssetDescription() %></option>
																		<%  } %>
																	</select>
																	<%} %>
																</div>

																<div class="form-group">
																	<label class="form-label">Account Type</label> <select
																		class="form-control" id="selladdaccounttype"
																		name="selladdaccounttype">
																		<option selected disabled value="">Select
																			Account Type</option>
																		<option value="IA">Issuing account</option>
																		<option value="DA">Distribution Account</option>
																		<option value="LA">Liquidity Account</option>
																	</select>
																</div>


															</div>
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Public Key</label> <input
																		type="text" class="form-control" name="addpublickey"
																		id="addpublickey" value="">
																</div>

																<div class="form-group">
																	<label class="form-label">Status</label> <select
																		class="form-control" id="selladdstatus"
																		name="selladdstatus">
																		<option selected disabled value="">Select
																			Status</option>
																		<option value="A">Active</option>
																		<option value="I">Inactive</option>
																	</select>
																</div>
															</div>
														</div>
													</div>

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
										id="btn-addassetaccounts">Submit</button>
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
	<form id="submit_form" method="post">
		<input type="hidden" name="qs" value=""> <input type="hidden"
			name="rules" value="">
	</form>
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
	<script src="assets/js/_ops_wallet_asset_accounts.js"></script>
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
	
	if(arrAccounts !=null) arrAccounts=null; if(arrAssets !=null) arrAssets=null;
	if(hashStatus !=null) hashStatus=null; if(hashAccountType !=null) hashAccountType=null; if (context!=null)context=null;
	
}
%>
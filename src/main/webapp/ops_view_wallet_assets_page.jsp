<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
List<CryptoAssetCoins> walletAssets = null;
ConcurrentHashMap<String, String> hashStatus = null;
ConcurrentHashMap<String, String> hashType = null;
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}
	
	
	
	if(request.getAttribute("walletAssets")!=null)	walletAssets = (List<CryptoAssetCoins>)request.getAttribute("walletAssets");
	hashStatus = new ConcurrentHashMap<String, String>();
	hashType = new ConcurrentHashMap<String, String>();
   	hashStatus.put("A", "Active"); hashStatus.put("I", "Inactive");
   	hashType.put("C", "Fiat"); 
   	hashType.put("F", "Fiat"); 
   	hashType.put("P", "Digital Asset");
   	hashType.put("E", "Digital Asset");
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
<link rel="shortcut icon" type="image/x-icon"
	href="assets/images/brand/pporte.png" />
<!-- Title -->
<title>Wallet Assets</title>
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


</head>
<body class="app sidebar-mini rtl">
	<!--Global-Loader-->
	<!-- <div id="global-loader">
			<img src="assets/images/icons/loader.svg" alt="loader">
		</div> -->
	<div id="spinner-div">
		<img src="assets/images/icons/loader.svg" alt="loader">
	</div>
	<div class="page">
		<div class="page-main">
			<!-- Sidebar menu-->
			<jsp:include page="ops_topandleftmenu.jsp" />
			<!--sidemenu end-->
			<!-- app-content-->
			<div class="app-content  my-3 my-md-5 toggle-content">
				<div class="side-app">
					<!-- page-header -->
					<div class="page-header">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#">Manage Wallets</a></li>
							<li class="breadcrumb-item active" aria-current="page">Wallet
								Assets</li>
						</ol>
						<!-- End breadcrumb -->
						<div class="ml-auto">
							<div class="input-group">
								<a class="btn btn-primary text-white mr-2" id="btn-add-branch"
									data-toggle="modal" data-target="#exampleModal3"> <span>
										<i class="fa fa-plus"></i> Add Wallet Asset
								</span>
								</a>
							</div>
						</div>
					</div>
					<!-- End page-header -->

					<!-- row -->
					<div class="row">
						<div class="col-lg-12">
							<div class="e-panel card">
								<div class="card-header">
									<h4>Wallet Assets</h4>
								</div>

								<div class="card-body">
									<div class="e-table">
										<div class="table-responsive table-lg">
											<table id="assets_table"
												class="table table-striped table-bordered text-nowrap w-100">
												<thead>
													<tr>
														<th>Asset Code</th>
														<th>Description</th>
														<th>Type</th>
														<th>Status</th>
														<th>Created On</th>
														<!-- <th >Issuer Account</th>
															<th >Distribution Account</th>
															<th >Liquidity Account</th> -->
														<th class="text-center">Actions</th>
													</tr>
												</thead>
												<tbody>
													<% if(walletAssets!=null){
															NeoBankEnvironment.setComment(3, "JSP", "Size "+walletAssets.size());
															for(int i=0;i<walletAssets.size();i++){
														%>
													<tr>
														<td class="text-nowrap align-middle"><%= ((CryptoAssetCoins)walletAssets.get(i)).getAssetCode() %></td>
														<td class="text-nowrap align-middle"><%= ((CryptoAssetCoins)walletAssets.get(i)).getAssetDescription() %></td>
														<td class="text-nowrap align-middle"><%= hashType.get(((CryptoAssetCoins)walletAssets.get(i)).getAssetType())%></td>
														<td class="text-nowrap align-middle"><%=hashStatus.get(((CryptoAssetCoins)walletAssets.get(i)).getStatus())%></td>
														<td class="text-nowrap align-middle"><%= ((CryptoAssetCoins)walletAssets.get(i)).getCreatedOn() %></td>
														<%-- 																<td class="text-nowrap align-middle"><%= ((CryptoAssetCoins)walletAssets.get(i)).getStellarIssuerAccount() %></td>
																<td class="text-nowrap align-middle"><%= ((CryptoAssetCoins)walletAssets.get(i)).getStellarDistributionAccount() %></td>
																<td class="text-nowrap align-middle"><%= ((CryptoAssetCoins)walletAssets.get(i)).getStellarLiquidityAccount() %></td> --%>
														<td class="text-center align-middle">
															<div class="btn-group align-top">
																<button class="btn btn-sm btn-primary badge"
																	onClick="javascript:fnEditRow('<%= ((CryptoAssetCoins)walletAssets.get(i)).getAssetCode() %>',
																		'<%= ((CryptoAssetCoins)walletAssets.get(i)).getAssetDescription() %>','<%= ((CryptoAssetCoins)walletAssets.get(i)).getStatus() %>', 
																		'<%= ((CryptoAssetCoins)walletAssets.get(i)).getAssetType() %>', '<%= ((CryptoAssetCoins)walletAssets.get(i)).getCreatedOn() %>',
																		'<%= ((CryptoAssetCoins)walletAssets.get(i)).getWalletType() %>', '<%= ((CryptoAssetCoins)walletAssets.get(i)).getSequenceId() %>',
																		'<%= ((CryptoAssetCoins)walletAssets.get(i)).getStellarIssuerAccount() %>', '<%= ((CryptoAssetCoins)walletAssets.get(i)).getStellarDistributionAccount() %>',
																		'<%= ((CryptoAssetCoins)walletAssets.get(i)).getStellarLiquidityAccount() %>')"
																	type="button">Edit</button>
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
					<!-- row  end -->
				</div>
			</div>
			<!-- End app-content-->
			<form method="post" id="get-page-form">
				<input type="hidden" name="qs" value=""> <input
					type="hidden" name="rules" value="">
			</form>

		</div>
	</div>
	<!-- Add User Modal -->
	<div class="modal fade" id="exampleModal3" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="example-Modal3">Add Asset</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form id="add-asset-form" method="post">
						<div class="form-group">
							<label class="form-label">Asset Code</label> <input type="text"
								name="asset_code" id="asset_code" class="form-control"
								placeholder="Enter Asset Type">
						</div>
						<div class="form-group">
							<label class="form-label">Asset Description</label> <input
								type="text" name="asset_desc" id="asset_desc"
								class="form-control" placeholder="Enter Asset Description">
						</div>
						<div class="form-group">
							<label class="form-label">Wallet Type</label> <input type="text"
								name="wallet_type" id="wallet_type" class="form-control"
								placeholder="Enter Wallet type">
						</div>
						<!-- 	<div class="form-group">
								<label class="form-label">Issuer Account Id</label>
								<input type="text" name="issuer_acc_id" id="issuer_acc_id" class="form-control" placeholder="Enter Issuer Account Id">
							</div>
							<div class="form-group">
								<label class="form-label">Distribution Account Id</label>
								<input type="text" name="distribution_acc_id" id="distribution_acc_id" class="form-control" placeholder="Enter Distribution Account Id">
							</div>
							<div class="form-group">
								<label class="form-label">Liquidity Account Id</label>
								<input type="text" name="liquidity_acc_id" id="liquidity_acc_id" class="form-control" placeholder="Enter Liquidity Account Id">
							</div> -->

						<div class="form-group">
							<label>Asset Type</label> <select
								class="form-control select2 custom-select" id="asset_type"
								name="asset_type" data-placeholder="Select Asset Type">
								<option value="" disabled selected>Select Asset Type</option>
								<option value="E">External Wallets</option>
								<option value="P">Porte Asset</option>
								<option value="C">Digital Currencies</option>
							</select>
						</div>

						<div class="form-group">
							<label>Status</label> <select
								class="form-control select2 custom-select" id="status"
								name="status" data-placeholder="Select Status ">
								<option value="" disabled selected>Select Status</option>
								<option value="A">Active</option>
								<option value="I">Inactive</option>
							</select>
						</div>

						<input type="hidden" name="qs" value=""> <input
							type="hidden" name="rules" value="">
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary"
						onclick="javascript:addWalletAsset()">Save</button>
				</div>
			</div>
		</div>
	</div>
	<!-- Edit User Modal -->
	<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="example-Modal3">Edit Asset</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form id="edit-asset-form" method="post">
						<div class="form-group">
							<label class="form-label">Asset Code</label> <input type="text"
								class="form-control" name="edit_asset_code" id="edit_asset_code">
						</div>

						<div class="form-group">
							<label class="form-label">Asset Description</label> <input
								type="text" name="edit_asset_desc" id="edit_asset_desc"
								class="form-control">
						</div>
						<div class="form-group">
							<label class="form-label">Wallet Type </label> <input type="text"
								name="edit_wallet_type" id="edit_wallet_type"
								class="form-control">
						</div>
						<!-- <div class="form-group">
								<label class="form-label">Issuer Account Id</label>
								<input type="text" name="edit_issuer_acc_id" id="issuer_acc_id" class="form-control" placeholder="Enter Issuer Account Id">
							</div>
							<div class="form-group">
								<label class="form-label">Distribution Account Id</label>
								<input type="text" name="edit_distribution_acc_id" id="distribution_acc_id" class="form-control" placeholder="Enter Distribution Account Id">
							</div>
							<div class="form-group">
								<label class="form-label">Liquidity Account Id</label>
								<input type="text" name="edit_liquidity_acc_id" id="liquidity_acc_id" class="form-control" placeholder="Enter Liquidity Account Id">
							</div> -->
						<div class="form-group">
							<label>Asset Type</label> <select
								class="form-control select2 custom-select" id="edit_asset_type"
								name="edit_asset_type">
								<option value="" disabled selected>Select Asset Type</option>
								<option value="E">External Wallets</option>
								<option value="P">Porte Asset</option>
								<option value="C">Digital Currencies</option>
							</select>
						</div>

						<div class="form-group">
							<label>Status</label> <select
								class="form-control select2 custom-select" id="edit_status"
								name="edit_status">
								<option value="" disabled selected>Select Status</option>
								<option value="A">Active</option>
								<option value="I">Inactive</option>
							</select>
						</div>
						<input type="hidden" name="qs" value=""> <input
							type="hidden" name="rules" value="">
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary"
						onclick="javascript:editWallet()">Save</button>
				</div>
			</div>
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

	<!--Side-menu js-->
	<script src="assets/plugins/toggle-sidebar/sidemenu.js"></script>
	<!-- Sidebar Accordions js -->
	<script src="assets/plugins/accordion1/js/easyResponsiveTabs.js"></script>
	<!--Time Counter js-->
	<script src="assets/plugins/counters/jquery.missofis-countdown.js"></script>
	<script src="assets/plugins/counters/counter.js"></script>
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
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_ops_view_wallet_assets_page.js"></script>
</body>
</html>

<% 
}catch(Exception e){
	out.println ("Exception : "+e.getMessage());
}finally{
	if (context!=null)context=null;
	if(hashStatus!=null) hashStatus=null;
	if(hashType!=null) hashType=null;
	if(walletAssets!=null) walletAssets=null;
}

%>
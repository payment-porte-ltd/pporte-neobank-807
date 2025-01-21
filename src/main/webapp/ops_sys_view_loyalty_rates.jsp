<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*, 	com.pporte.utilities.Utilities,com.pporte.model.*, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
ArrayList<Loyalty> arrLoyaltyRates = null; ConcurrentHashMap<String, String> hashStatus = null; 
ConcurrentHashMap<String, String> hashUserType = null; ArrayList<CryptoAssetCoins> arrAssets = null;
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}

	if (request.getAttribute("loyaltyconversion") !=null) arrLoyaltyRates = (ArrayList<Loyalty>) request.getAttribute("loyaltyconversion");
	if (request.getAttribute("assets") !=null) arrAssets = (ArrayList<CryptoAssetCoins>) request.getAttribute("assets");

	hashStatus = new ConcurrentHashMap<String, String>();
	hashUserType = new ConcurrentHashMap<String, String>();
	
	hashStatus.put("A", "Active");
	hashStatus.put("I", "Inactive");
	
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
<title>Loyalty Rates</title>

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
							<li class="breadcrumb-item"><a href="#">Redeem Rates</a></li>
							<li class="breadcrumb-item active" aria-current="page">Create
								Redeem Rate</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- row -->
					<div class="row">
						<div class="col-md-12 col-lg-12">
							<div class="card">
								<div class="card-header">
									<div class="card-title">Redeem Rates</div>
									<div class="ml-auto">
										<div class="input-group">
											<a class="btn btn-primary text-white mr-2"
												data-toggle="modal" data-target="#addloyaltyConvRatesModal">
												<span> <i class="typcn typcn-plus"></i> Add Redeem
													Rates
											</span>
											</a>

										</div>
									</div>
								</div>
								<div class="card-body">
									<div class="table-responsive">
										<table id="viewassetpricingtable"
											class="table table-striped table-bordered text-nowrap w-100">

											<thead>
												<tr>
													<th>Destination asset</th>
													<th>Conversion Rate</th>
													<th>Status</th>
													<th>Createdon</th>
													<th>Expiry</th>
													<th>Action</th>

												</tr>
											</thead>
											<tbody>
												<%
												if (arrLoyaltyRates != null) {
													for (int i = 0; i < arrLoyaltyRates.size(); i++) {
												%>
												<tr>
													<td><%=((Loyalty) arrLoyaltyRates.get(i)).getDestinationAsset() %></td>
													<td><%=((Loyalty) arrLoyaltyRates.get(i)).getConversionRate() %></td>
													<%if(((Loyalty) arrLoyaltyRates.get(i)).getStatus().equals("A")){ %>
													<td style="color: #2962ff"><%=hashStatus.get( ((Loyalty) arrLoyaltyRates.get(i)).getStatus())%></td>
													<%}else{ %>
													<td><%=hashStatus.get( ((Loyalty) arrLoyaltyRates.get(i)).getStatus())%></td>
													<%} %>
													<td><%=((Loyalty) arrLoyaltyRates.get(i)).getCreatedon()%></td>
													<td><%=((Loyalty) arrLoyaltyRates.get(i)).getExpiry()%></td>
													<%if(((Loyalty) arrLoyaltyRates.get(i)).getStatus().equals("A")){ %>

													<td class="text-center align-middle">
														<div class="btn-group align-top">
															<button class="btn btn-sm btn-primary badge"
																data-target="#user-form-modal" data-toggle="modal"
																type="button"
																onClick="javascript:fnEditLoyaltyPrice('<%=((Loyalty) arrLoyaltyRates.get(i)).getDestinationAsset() %>',
																'<%=((Loyalty) arrLoyaltyRates.get(i)).getConversionRate() %>','<%=((Loyalty) arrLoyaltyRates.get(i)).getStatus()%>',
																'<%=((Loyalty) arrLoyaltyRates.get(i)).getCreatedon()%>', '<%=((Loyalty) arrLoyaltyRates.get(i)).getSequenceId()%>',
																'<%=((Loyalty) arrLoyaltyRates.get(i)).getDestinationAsset() %>','<%=((Loyalty) arrLoyaltyRates.get(i)).getExpiry() %>'
																);return false;">
																<i class="fa fa-edit"></i>Edit
															</button>
														</div>
													</td>
													<%}else{ %>
													<td class="text-center align-middle">
														<div class="btn-group align-top">
															<button class="btn btn-sm btn-primary badge"
																data-target="#user-form-modal" data-toggle="modal"
																type="button">
																<i class="fa fa-edit"></i>Edit
															</button>
														</div>
													</td>

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
					<div class="modal fade" id="editLoyaltyPricingModal" tabindex="-1"
						role="dialog" aria-labelledby="largemodal" aria-hidden="true">
						<div class="modal-dialog modal-lg " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="largemodal1">Edit Redeem Rates</h5>
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
												<form id="editconversionrates-form" method="post">
													<div class="card-body">
														<div class="row">
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Destination Asset</label> <input
																		type="text" class="form-control" name="editdestasset"
																		id="editdestasset" readonly>
																</div>

																<div class="form-group">
																	<label class="form-label">Conversion Rate</label> <input
																		type="text" class="form-control"
																		name="editconversionrate" id="editconversionrate">
																</div>

																<div class="form-group">
																	<label class="form-label">Created On</label> <input
																		type="text" class="form-control" name="editcreatedon"
																		id="editcreatedon" readonly>
																</div>
															</div>
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Asset Description</label> <input
																		type="text" class="form-control" name="editassetdesc"
																		id="editassetdesc" readonly>
																</div>

																<div class="form-group">
																	<label class="form-label">Status</label> <select
																		class="form-control" id="selleditstatus"
																		name="selleditstatus">
																		<option value="">Select Status</option>
																		<option value="A">Active</option>
																		<option value="I">Inactive</option>
																	</select>
																</div>
																<div class="form-group">
																	<label class="form-label">Expiry</label> <input
																		type="text" class="form-control" name="editexpirydate"
																		id="editexpirydate" readonly>
																</div>

															</div>
														</div>
													</div>
													<input type="hidden" name="qs" value=""> <input
														type="hidden" name="rules" value=""> <input
														type="hidden" name="hdneditconversionstatus" value="">
													<input type="hidden" name="hdnsequenceno"
														id="hdnsequenceno" value=""> <input type="hidden"
														name="hdnlangpref" id="hdnlangpref3" value="en">
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
										id="btn-editconversionrates">Submit</button>
								</div>
							</div>
						</div>
					</div>

					<!-- End Model -->
					<!-- Modal -->
					<div class="modal fade" id="addloyaltyConvRatesModal" tabindex="-1"
						role="dialog" aria-labelledby="largemodal" aria-hidden="true">

						<div class="modal-dialog modal-lg " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="largemodal1">Add Redeem Rate</h5>
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
												<form id="addloyaltyconvrates-from" method="post">
													<div class="card-body">
														<div class="row">
															<div class="col-xl-6">
																<div class="form-group">
																	<% if(arrAssets!=null) { %>
																	<label class="form-label">Destination Asset</label> <select
																		class="form-control" name="seladddestinationasset"
																		id="seladddestinationasset"
																		onchange="javascript:UpdateassetDescParam(); return false;">
																		<option value="">Select Asset</option>
																		<% for (int i=0; i<arrAssets.size();i++){ %>
																		<option
																			value="<%=((CryptoAssetCoins) arrAssets.get(i)).getAssetCode()%>,<%=((CryptoAssetCoins) arrAssets.get(i)).getAssetDescription()%>"><%=((CryptoAssetCoins) arrAssets.get(i)).getAssetDescription() %></option>
																		<%  } %>
																	</select>
																	<%} %>
																</div>

																<div class="form-group ">
																	<label class="form-label">Conversion Rate </label>
																	<div class="input-two-box">
																		<input type="text" class="form-control"
																			name="addconversionrate" id="addconversionrate"
																			value=""
																			onkeyup="javascript: UpdateConversionamount(); return false;">
																		<span style="color: #2b65ec; display: none"
																			id="lytconversionrate"> 1 <span
																			id="spanloyaltypoints"> Point </span> = <span
																			id=spanconversionrate> 0</span> <span
																			id=spandestassetcode></span></span>
																	</div>

																</div>
															</div>
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Asset Description</label> <input
																		type="text" class="form-control" name="addassetdesc"
																		id="addassetdesc" value="" readonly>
																</div>

																<div class="form-group">
																	<label class="form-label">Status</label> <select
																		class="form-control" id="selladdstatus"
																		name="selladdstatus">
																		<option value="">Select Status</option>
																		<option value="A">Active</option>
																		<option value="I">Inactive</option>
																	</select>
																</div>
															</div>
														</div>
													</div>
													<input type="hidden" name="qs" value=""> <input
														type="hidden" name="rules" value=""> <input
														type="hidden" name="hdnaddconversionstatus" value="">
													<input type="hidden" name="hdnadddestasset" value="">
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
									<button type="button" class="btn btn-primary"
										id="btn-addedeemrate">Submit</button>
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
	<script src="assets/js/_ops_sys_view_loyalty_rates.js"></script>


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
	
	if(arrLoyaltyRates !=null) arrLoyaltyRates=null; if(hashStatus !=null) hashStatus=null;
	if(hashUserType !=null) hashUserType=null; 	if (context!=null)context=null;
}
%>
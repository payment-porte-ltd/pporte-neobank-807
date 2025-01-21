<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*,	com.pporte.utilities.Utilities, com.pporte.model.*, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
ArrayList<AssetCoin> arrAssetPricing = null; ConcurrentHashMap<String, String> hashStatus = null; 
ConcurrentHashMap<String, String> hashUserType = null; ArrayList<CryptoAssetCoins> arrAssets = null;
ServletContext context = null; ArrayList<CryptoAssetCoins> arrFiatAssets = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}
	
	if (request.getAttribute("assetpricing") !=null) arrAssetPricing = (ArrayList<AssetCoin>) request.getAttribute("assetpricing");
	if (request.getAttribute("assets") !=null) arrAssets = (ArrayList<CryptoAssetCoins>) request.getAttribute("assets");
	if (request.getAttribute("fiatassets") !=null) arrFiatAssets = (ArrayList<CryptoAssetCoins>) request.getAttribute("fiatassets");

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
							<li class="breadcrumb-item"><a href="#">Asset Pricing</a></li>
							<li class="breadcrumb-item active" aria-current="page">Create
								Asset Price</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- row -->
					<div class="row">
						<div class="col-md-12 col-lg-12">
							<div class="card">
								<div class="card-header">
									<div class="card-title">Asset Pricing against Currency</div>
									<div class="ml-auto">
										<div class="input-group">
											<a class="btn btn-primary text-white mr-2"
												data-toggle="modal" data-target="#addassetpricingModal">
												<span> <i class="typcn typcn-plus"></i> Add Asset
													Price
											</span>
											</a> <a class="btn btn-primary text-white mr-2"
												data-toggle="modal" data-target="#add_asset_markuprate">
												<span> <i class="typcn typcn-plus"></i> Add Asset
													Markup rates
											</span>
											</a> <a class="btn btn-primary text-white mr-2"
												id="btn_update_asset_prices" name="btn_update_asset_prices"
												onclick="javascript:fnUpdatePricingFromCoingecko();return false;">
												<span> </i> Update Prices From CoinGecko
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
													<th>Exchange Rate</th>
													<th>Deposit Markup Rate</th>
													<th>Withdraw Markup Rate</th>
													<th>Status</th>
													<th>Created On</th>
													<th>Action</th>

												</tr>
											</thead>
											<tbody>
												<%
												if (arrAssetPricing != null) {
													for (int i = 0; i < arrAssetPricing.size(); i++) {
												%>
												<tr>
													<td>1 <%=((AssetCoin) arrAssetPricing.get(i)).getAssetCode()%>
														= <%=((AssetCoin) arrAssetPricing.get(i)).getSellingRate()%>
														<%=((AssetCoin) arrAssetPricing.get(i)).getCurrency()%></td>
													<td><%=((AssetCoin) arrAssetPricing.get(i)).getOnMarkupRate()%></td>
													<td><%=((AssetCoin) arrAssetPricing.get(i)).getOffMarkupRate()%></td>
													<%if(((AssetCoin) arrAssetPricing.get(i)).getStatus().equals("A")){ %>
													<td style="color: #2962ff"><%=hashStatus.get( ((AssetCoin) arrAssetPricing.get(i)).getStatus())%></td>
													<%}else{ %>
													<td><%=hashStatus.get( ((AssetCoin) arrAssetPricing.get(i)).getStatus())%></td>
													<%} %>
													<td><%=((AssetCoin) arrAssetPricing.get(i)).getCreatedOn()%></td>
													<%if(((AssetCoin) arrAssetPricing.get(i)).getStatus().equals("A")){ %>

													<td class="text-center align-middle">
														<div class="btn-group align-top">
															<button class="btn btn-sm btn-primary badge"
																data-target="#user-form-modal" data-toggle="modal"
																type="button"
																onClick="javascript:fnEditAssetPrice('<%=((AssetCoin) arrAssetPricing.get(i)).getAssetCode()%>',
																'<%=((AssetCoin) arrAssetPricing.get(i)).getAssetCode() %>','<%=((AssetCoin) arrAssetPricing.get(i)).getSellingRate() %>',
																'<%=((AssetCoin) arrAssetPricing.get(i)).getStatus()%>','<%=((AssetCoin) arrAssetPricing.get(i)).getCreatedOn()%>',
																'<%=((AssetCoin) arrAssetPricing.get(i)).getSequenceNo()%>','<%=((AssetCoin) arrAssetPricing.get(i)).getCurrency()%>'
																);return false;">
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
					<div class="modal fade" id="editAssetPricingModal" tabindex="-1"
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
												<form id="editassetrate-form" method="post">
													<div class="card-body">
														<div class="row">
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Asset Code</label> <input
																		type="text" class="form-control" name="editassetcode"
																		id="editassetcode" readonly>
																</div>

																<div class="form-group">
																	<label class="form-label">Selling Rate</label> <input
																		type="text" class="form-control" name="editsellrate"
																		id="editsellrate" readonly>
																</div>

																<div class="form-group">
																	<label class="form-label">Created On</label> <input
																		type="text" class="form-control" name="editcreatedon"
																		id="editcreatedon" readonly>
																</div>
															</div>
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Fiat Currency</label> <input
																		type="text" class="form-control"
																		name="editfiatcurrency" id="editfiatcurrency" readonly>
																</div>

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
													<input type="hidden" name="qs" value=""> <input
														type="hidden" name="rules" value=""> <input
														type="hidden" name="hdnsellratetystatus" value="">
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
										id="btn-editeditassetpricing">Submit</button>
								</div>
							</div>
						</div>
					</div>

					<!-- End Model -->
					<!-- Modal -->
					<div class="modal fade" id="addassetpricingModal" tabindex="-1"
						role="dialog" aria-labelledby="largemodal" aria-hidden="true">

						<div class="modal-dialog modal-lg " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="largemodal1">Add Asset Pricing</h5>
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
																<div class="form-group">
																	<% if(arrAssets!=null) { %>
																	<label class="form-label">Asset Code</label> <select
																		class="form-control" name="seladdassetcode"
																		id="seladdassetcode"
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
																	<label class="form-label">Selling Rate</label>
																	<div class="input-two-box">
																		<input type="text" class="form-control"
																			name="addexhangerate" id="addexhangerate" value=""
																			onkeyup="javascript: UpdateSellamount(); return false;">
																		<span style="color: #2b65ec; display: none"
																			id="exchangerate"> 1 <span
																			id="spansellassetcode"> </span> = <span
																			id=spansexchangerate> 0</span> <span
																			id="spanfiatwalletasset"></span></span>
																	</div>

																</div>
															</div>
															<div class="col-xl-6">
																<% if(arrFiatAssets!=null) { %>
																<label class="form-label">Fiat Currency</label> <select
																	class="form-control" name="selfiatcurrency"
																	id="selfiatcurrency"
																	onchange="javascript:fnUpdateFiatDesc(); return false;">
																	<option value="">Select Asset</option>
																	<% for (int i=0; i<arrFiatAssets.size();i++){ %>
																	<option
																		value="<%=((CryptoAssetCoins) arrFiatAssets.get(i)).getAssetCode()%>,<%=((CryptoAssetCoins) arrFiatAssets.get(i)).getAssetDescription()%>"><%=((CryptoAssetCoins) arrFiatAssets.get(i)).getAssetDescription() %></option>
																	<%  } %>
																</select>
																<%} %>

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
													<input type="hidden" name="qs" value=""> <input
														type="hidden" name="rules" value=""> <input
														type="hidden" name="hdnaddsellratetystatus" value="">
													<input type="hidden" name="hdnaddassetcode" value="">
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
										id="btn-addassetpricing">Submit</button>
								</div>
							</div>
						</div>
					</div>

					<!-- End Model -->
					<!-- Modal -->
					<div class="modal fade" id="add_asset_markuprate" tabindex="-1"
						role="dialog" aria-labelledby="largemodal" aria-hidden="true">

						<div class="modal-dialog modal-lg " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="largemodal1">Add Asset Markup
										Rate</h5>
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
												<form id="add_asset_markup_from" method="post">
													<div class="card-body">
														<div class="row">
															<div class="col-xl-6">
																<div class="form-group">
																	<% if(arrAssets!=null) { %>
																	<label class="form-label">Asset Code</label> <select
																		class="form-control" name="selmarkupassetcode"
																		id="selmarkupassetcode"
																		onchange="javascript:updateMarkupAssetDescParam(); return false;">
																		<option value="">Select Asset</option>
																		<% for (int i=0; i<arrAssets.size();i++){ %>
																		<option
																			value="<%=((CryptoAssetCoins) arrAssets.get(i)).getAssetCode()%>,<%=((CryptoAssetCoins) arrAssets.get(i)).getAssetDescription()%>"><%=((CryptoAssetCoins) arrAssets.get(i)).getAssetDescription() %></option>
																		<%  } %>
																	</select>
																	<%} %>
																	<span style="color: #2b65ec; display: none;"
																		id="markup_exchangerate">Original Rate is: 1 <span
																		id="span_markup_rate_assetcode"> </span> = <span
																		id=span_markup_rate_original_price> 0</span> <span
																		id="span_markup_rate_fiat_currency"></span></span>
																</div>

																<div class="form-group ">
																	<label class="form-label">Deposit Markup Rate</label>
																	<div class="input-two-box">
																		<input type="number" class="form-control"
																			name="onramp_markuprate" id="onramp_markuprate"
																			value=""
																			onkeyup="javascript: updateOnrampMarkupRate('on'); return false;">
																		<span style="color: #2b65ec; display: none"
																			id="exchangerate_after_exchange">Final Rate
																			is: 1 <span id="span_after_markup_rate_assetcode">
																		</span> = <span id=spans_after_markup_rate_exchangerate>
																				0</span> <span id="span_after_markup_rate_fiat_currency"></span>
																		</span>
																	</div>

																</div>

																<div class="form-group">
																	<label class="form-label">Status</label> <select
																		class="form-control" id="selmarkupstatus"
																		name="selladdstatus">
																		<option selected disabled value="">Select
																			Status</option>
																		<option value="A">Active</option>
																		<option value="I">Inactive</option>
																	</select>
																</div>


																<!-- <div class="form-group">
																	<label class="form-label">Created On</label> 
																	<input type="text" class="form-control" name="addcreatedon" id="addcreatedon" value= "">
																</div> -->
															</div>
															<div class="col-xl-6">
																<% if(arrFiatAssets!=null) { %>
																<label class="form-label">Fiat Currency</label> <select
																	class="form-control" name="selmarkupfiatcurrency"
																	id="selmarkupfiatcurrency"
																	onchange="javascript:updateMarkupFiatCurrencyParam(); return false;">
																	<option value="">Select Asset</option>
																	<% for (int i=0; i<arrFiatAssets.size();i++){ %>
																	<option
																		value="<%=((CryptoAssetCoins) arrFiatAssets.get(i)).getAssetCode()%>,<%=((CryptoAssetCoins) arrFiatAssets.get(i)).getAssetDescription()%>"><%=((CryptoAssetCoins) arrFiatAssets.get(i)).getAssetDescription() %></option>
																	<%  } %>
																</select>
																<%} %>

																<div class="form-group ">
																	<label class="form-label">Withdraw Markup Rate</label>
																	<div class="input-two-box">
																		<input type="number" class="form-control"
																			name="markup_burning_rate" id="markup_burning_rate"
																			value=""
																			onkeyup="javascript: updateOnrampMarkupRate('off'); return false;">
																		<span style="color: #2b65ec; display: none"
																			id="off_ramp_exchangerate">Final Rate is: 1 <span
																			id="off_ramp_spansellassetcode"> </span> = <span
																			id="off_ramp_spansexchangerate"> 0</span> <span
																			id="off_ramp_spansellfiatcurrency"></span></span>
																	</div>

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
										onclick="javascript:addMarkupRate();return false;">Submit</button>
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
	<script src="assets/js/_ops_sys_view_assets_pricing.js"></script>




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
	
	if(arrAssetPricing !=null) arrAssetPricing=null; if(hashStatus !=null) hashStatus=null;
	if(hashUserType !=null) hashUserType=null; if(arrAssets !=null) arrAssets=null;if (context!=null)context=null;
	if (arrFiatAssets!=null)arrFiatAssets=null;
	
}
%>
<%@page import="com.pporte.utilities.Utilities"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page import="com.pporte.*, com.pporte.utilities.Utilities,com.pporte.model.*, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
ArrayList<AssetOffer> arrOffers=null;
ArrayList<AssetOffer> arrVessleOffers=null;
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}
	
	if (request.getAttribute("alloffers") != null) arrOffers = (ArrayList<AssetOffer>) request.getAttribute("alloffers");
	if (request.getAttribute("allvessleoffers") != null) arrVessleOffers = (ArrayList<AssetOffer>) request.getAttribute("allvessleoffers");

	
%>
<!doctype html>
<html lang="en" dir="ltr">
	<head>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- Favicon -->
		<link rel="icon" href="assets/images/brand/pporte.png" type="image/x-icon"/>
		<link rel="shortcut icon" type="image/x-icon" href="assets/images/brand/pporte.png" />

		<!-- Title -->
		<title>View and Edit</title>

		<!--Bootstrap.min css-->
		<link rel="stylesheet" href="assets/plugins/bootstrap/css/bootstrap.min.css">

		<!-- Dashboard css -->
		<link href="assets/css/ops_style.css" rel="stylesheet" />

		<!-- Custom scroll bar css-->
		<link href="assets/plugins/scroll-bar/jquery.mCustomScrollbar.css" rel="stylesheet" />

		<!-- Sidemenu css -->
		<link href="assets/plugins/toggle-sidebar/ops_sidemenu.css" rel="stylesheet" />

		<!-- Sidebar Accordions css -->
		<link href="assets/plugins/accordion1/css/easy-responsive-tabs.css" rel="stylesheet">

		<!-- Rightsidebar css -->
		<link href="assets/plugins/sidebar/sidebar.css" rel="stylesheet">

		<!---Font icons css-->
		<link href="assets/plugins/iconfonts/plugin.css" rel="stylesheet" />
		<link href="assets/plugins/iconfonts/icons.css" rel="stylesheet" />
		<link  href="assets/fonts/fonts/font-awesome.min.css" rel="stylesheet">
		
		<!-- Data table css -->
		<link href="assets/plugins/datatable/dataTables.bootstrap4.min.css" rel="stylesheet" />
		<link href="assets/plugins/datatable/responsivebootstrap4.min.css" rel="stylesheet" />
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
								<li class="breadcrumb-item"><a href="#">Manage Offers></li>
								<li class="breadcrumb-item active" aria-current="page">View Offers</li>
							</ol>
							<!-- End breadcrumb -->
						</div>
						<!-- End page-header -->
							<!-- row -->
					<div class="row">
						<div class="col-md-12 col-lg-12">
							<div class="card card-profile  overflow-hidden">
								<div class="card-body">
									<div class="nav-wrapper p-0">
										<ul class="nav nav-pills nav-fill flex-column flex-md-row" id="tabs-icons-text" role="tablist">
											<li class="nav-item">
												<a class="nav-link mb-sm-3 mb-md-0 mt-md-2 mt-0 mt-lg-0 active" id="tabs-icons-text-1-tab" data-toggle="tab" href="#tabs-icons-text-1" role="tab" aria-controls="tabs-icons-text-1" aria-selected="true">View Porte Offers</a>
											</li>
											<li class="nav-item">
												<a class="nav-link mb-sm-3 mb-md-0 mt-md-2 mt-0 mt-lg-0" id="tabs-icons-text-2-tab" data-toggle="tab" href="#tabs-icons-text-2" role="tab" aria-controls="tabs-icons-text-2" aria-selected="false">View Vessel Offers</a>
											</li>
										</ul>
									</div>
								</div>
							</div> 
							<div class="card">
										<div class="card-body pb-0">
											<div class="tab-content" id="myTabContent">
												<div class="tab-pane fade active show" id="tabs-icons-text-1" role="tabpanel" aria-labelledby="tabs-icons-text-1-tab">
													<div class="card">
														<div class="card-body">
															<div class="table-responsive">
																<table id="example3" style="color:black" class="table table-striped table-bordered text-nowrap" >
																	<thead>
																		<tr>
																			<th>Sell</th>
																			<th>Buy</th>
																			<th>Amount</th>
																			<th>Offer ID</th>
																			<th>Price</th>
																			<th>Date</th>
																			<th>Account</th>
																			<th>Action</th>
																			
																		</tr>
																	</thead>
																	<tbody>
																		<%
																		if (arrOffers != null) {
																			for (int i = 0; i < arrOffers.size(); i++) {
																		%>
																		<tr>
																			<td><%=((AssetOffer) arrOffers.get(i)).getSellAsset().split(":")[0]%></td>
																			<td><%=((AssetOffer) arrOffers.get(i)).getBuyAsset().split(":")[0]%></td>
																			<td><%=((AssetOffer) arrOffers.get(i)).getAmount()%></td>
																			<td><%=((AssetOffer) arrOffers.get(i)).getId()%></td>
																			<td><%=((AssetOffer) arrOffers.get(i)).getPrice()%></td>
																			<td><%=Utilities.getStellarDateConvertor(((AssetOffer) arrOffers.get(i)).getDate()) %></td>
																			<td><%=((AssetOffer) arrOffers.get(i)).getAccountId()%></td>
																			<td class="text-center align-middle">
																					<button class="btn btn-sm btn-primary badge"
																					data-target="#user-form-modal" data-toggle="modal" type="button"  onClick="javascript:fnUpdateOffers(
																					'<%=((AssetOffer) arrOffers.get(i)).getAccountId()%>',
																					'<%=((AssetOffer) arrOffers.get(i)).getSellAsset().split(":")[0]%>',
																					'<%=((AssetOffer) arrOffers.get(i)).getBuyAsset().split(":")[0]%>',
																					'<%=((AssetOffer) arrOffers.get(i)).getAmount()%>',
																					'<%=((AssetOffer) arrOffers.get(i)).getId()%>',
																					'<%=Utilities.getStellarDateConvertor(((AssetOffer) arrOffers.get(i)).getDate())%>'
																					);return false;">Update Offer</button>
																					<button class="btn btn-sm btn-warning badge"
																					data-target="#delete-porte-form-modal" data-toggle="modal" type="button"  onClick="javascript:fnDeleteOffers(
																					'<%=((AssetOffer) arrOffers.get(i)).getAccountId()%>',
																					'<%=((AssetOffer) arrOffers.get(i)).getId()%>',
																					'<%=((AssetOffer) arrOffers.get(i)).getSellAsset().split(":")[0]%>'
																					);return false;">Delete Offer</button>
																				
																			</td>
																			</tr>
						
																			<%
																			 }
																			} else {
																			%>
																		
																		<tr>
																			<td colspan="9"><span id="ops_all_bin_list_errormsg1">No Offers Created</span></td>
																			<p><span style="color:#1753fc"><a href=""onclick="javascript:fnCreateOffers();return false;">Click here to Create a new offer.</a></span></p>  
																			
																		</tr>
																		<%
																		}
																		%>
						
																	</tbody>
																</table>
															</div>
														</div>
													</div>
												</div>
												<div aria-labelledby="tabs-icons-text-2-tab" class="tab-pane fade" id="tabs-icons-text-2" role="tabpanel">
													<div class="row">
														<div class="card">
															<div class="card-body">
																<div class="table-responsive">
																	<table id="example" style="color:black" class="table table-striped table-bordered text-nowrap" >
																		<thead>
																			<tr>
																				<th>Sell</th>
																				<th>Buy</th>
																				<th>Amount</th>
																				<th>Offer ID</th>
																				<th>Price</th>
																				<th>Date</th>
																				<th>Account</th>
																				<th>Action</th>
																				
																			</tr>
																		</thead>
																		<tbody>
																			<%
																			if (arrVessleOffers != null) {
																				for (int i = 0; i < arrVessleOffers.size(); i++) {
																			%>
																			<tr>
																				<td><%=((AssetOffer) arrVessleOffers.get(i)).getSellAsset().split(":")[0]%></td>
																				<td><%=((AssetOffer) arrVessleOffers.get(i)).getBuyAsset().split(":")[0]%></td>
																				<td><%=((AssetOffer) arrVessleOffers.get(i)).getAmount()%></td>
																				<td><%=((AssetOffer) arrVessleOffers.get(i)).getId()%></td>
																				<td><%=((AssetOffer) arrVessleOffers.get(i)).getPrice()%></td>
																				<td><%=Utilities.getMySQLDateConvertor(((AssetOffer) arrVessleOffers.get(i)).getDate())%></td>
																				<td><%=((AssetOffer) arrVessleOffers.get(i)).getAccountId()%></td>
																				<td class="text-center align-middle">
																					<button class="btn btn-sm btn-primary badge"
																					data-target="#vessel-form-modal" data-toggle="modal" type="button"  onClick="javascript:fnUpdateVesselOffers(
																					'<%=((AssetOffer) arrVessleOffers.get(i)).getAccountId()%>',
																					'<%=((AssetOffer) arrVessleOffers.get(i)).getSellAsset().split(":")[0]%>',
																					'<%=((AssetOffer) arrVessleOffers.get(i)).getBuyAsset().split(":")[0]%>',
																					'<%=((AssetOffer) arrVessleOffers.get(i)).getAmount()%>',
																					'<%=((AssetOffer) arrVessleOffers.get(i)).getId()%>',
																					'<%=Utilities.getStellarDateConvertor(((AssetOffer) arrVessleOffers.get(i)).getDate())%>'
																					);return false;">Update Offers</button>
																					<button class="btn btn-sm btn-warning badge"
																					data-target="#delete-vesl-form-modal" data-toggle="modal" type="button"  onClick="javascript:fnVeslDeleteOffers(
																					'<%=((AssetOffer) arrVessleOffers.get(i)).getAccountId()%>',
																					'<%=((AssetOffer) arrVessleOffers.get(i)).getId()%>',
																					'<%=((AssetOffer) arrVessleOffers.get(i)).getSellAsset().split(":")[0]%>'
																					);return false;">Delete Offer</button>
																			</td>
																				</tr>
							
																				<%
																				 }
																				} else {
																				%>
																			
																			<tr>
																				<td colspan="9"><span id="ops_all_bin_list_errormsg1">No Offers Created</span></td>
																				<p><span style="color:#1753fc"><a href=""onclick="javascript:fnCreateOffers();return false;">Click here to Create a new offer.</a></span></p>  
																				
																			</tr>
																			<%
																			}
																			%>
							
																		</tbody>
																	</table>
																</div>
															</div>
														</div>
													</div>
												</div>
																<!-- Modal -->
													<div class="modal fade" id="user-form-modal" tabindex="-1" role="dialog" aria-labelledby="largemodal" aria-hidden="true">
														<div class="modal-dialog modal-lg " role="document">
															<div class="modal-content">
																<div class="modal-header">
																	<h5 class="modal-title" id="largemodal1">Update Porte Offer</h5>
																	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
																		<span aria-hidden="true">X</span>
																	</button>
																</div>
																<div class="modal-body">
																<!-- row -->
																<div class="row">
																	<div class="col-md-12">
																		<div class="card">
																		    <form id="update_pporte_offer_form" method = "post">
																				<div class="card-body">
																					<div class="row">
																						<div class="col-xl-6">
																						    <div class="form-group">
																								<label class="form-label">Account Public Key</label> 
																								<input type="text" class="form-control" name="viewaccountid" id="viewaccountid" readonly> 
																							</div>
																							<div class="form-group">
																								<label class="form-label">Sell Asset</label> 
																								<input type="text" class="form-control" name="viewsellasset" id="viewsellasset" readonly >
																							</div>
																							<div class="form-group">
																								<label class="form-label">Buy Asset</label> 
																								<input type="text" class="form-control" name="viewbuyasset" id="viewbuyasset" readonly >
																							</div>
																							<div class="form-group">
																								<label class="form-label">Offer ID</label> 
																								<input type="text" class="form-control" name="viewofferid" id="viewofferid" readonly >
																							</div>
																						</div>
																						<div class="col-xl-6">
																							<div class="form-group hidden" id="div_buy_amount">
																								<label class="form-label">Amount you are buying</label>
																								<input type="number" class="form-control" name="buyingamount" id="buyingamount" placeholder="Amount you are buying">
																							</div>
																							<div class="form-group hidden" id="div_sel_amount">
																								<label class="form-label">Amount you are selling</label>
																								<input type="number" class="form-control" name="sellingamount" id="sellingamount" placeholder="Amount you are selling">
																							</div>
																							
																							<div class="form-group">
																								<label class="form-label">Price of 1 unit in XLM</label>
																								<input type="number" class="form-control" name="sellpriceunit" id="sellpriceunit" placeholder="Price unit">
																							</div>
																							<div class="form-group">
																								<label class="form-label">Business Private Key</label>
																								<input type="password" class="form-control" name="sellprivatekey" id="sellprivatekey" placeholder="Enter Business Private Key">
																							</div>
																						</div>
																				</div>
																				</div>
																				<input type="hidden" name="qs" value="">
														                        <input type="hidden" name="rules" value="">
														                        <input type="hidden" name="hdnlangpref" id="hdnlangpref3" value="en">
																			</form>
																		</div>
																	</div>
																</div>
														    	<!-- row -->
																</div>
																<div class="modal-footer">
																	<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
																	<button type="button" class="btn btn-danger" id="btn_update_pporte_offer">Update</button>
																</div>
															</div>
														</div>
													</div>
													<div class="modal fade" id="vessel-form-modal" tabindex="-1" role="dialog" aria-labelledby="largemodal" aria-hidden="true">
														<div class="modal-dialog modal-lg " role="document">
															<div class="modal-content">
																<div class="modal-header">
																	<h5 class="modal-title" id="largemodal1">Update Vessel Offer</h5>
																	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
																		<span aria-hidden="true">X</span>
																	</button>
																</div>
																<div class="modal-body">
																<!-- row -->
																<div class="row">
																	<div class="col-md-12">
																		<div class="card">
																		    <form id="update_vesl_offer_form" method = "post">
																				<div class="card-body">
																					<div class="row">
																						<div class="col-xl-6">
																						    <div class="form-group">
																								<label class="form-label">Account Public Key</label> 
																								<input type="text" class="form-control" name="vessel_viewaccountid" id="vessel_viewaccountid" readonly> 
																							</div>
																							<div class="form-group">
																								<label class="form-label">Sell Asset</label> 
																								<input type="text" class="form-control" name="vessel_viewsellasset" id="vessel_viewsellasset" readonly >
																							</div>
																							<div class="form-group">
																								<label class="form-label">Buy Asset</label> 
																								<input type="text" class="form-control" name="vessel_viewbuyasset" id="vessel_viewbuyasset" readonly >
																							</div>
																							<div class="form-group">
																								<label class="form-label">Offer ID</label> 
																								<input type="text" class="form-control" name="vessel_viewofferid" id="vessel_viewofferid" readonly >
																							</div>
																						</div>
																						<div class="col-xl-6">
																							<div class="form-group hidden" id="div_vesl_buy_amount">
																								<label class="form-label">Amount you are buying</label>
																								<input type="number" class="form-control" name="vesel_buyingamount" id="vesel_buyingamount" placeholder="Amount you are buying">
																							</div>
																							<div class="form-group hidden" id="div_vesl_sel_amount">
																								<label class="form-label">Amount you are selling</label>
																								<input type="number" class="form-control" name="vesel_sellingamount" id="vesel_sellingamount" placeholder="Amount you are selling">
																							</div>
																							
																							<div class="form-group">
																								<label class="form-label">Price of 1 unit in XLM</label>
																								<input type="number" class="form-control" name="vesel_sellpriceunit" id="vesel_sellpriceunit" placeholder="Price unit">
																							</div>
																							<div class="form-group">
																								<label class="form-label">Business Private Key</label>
																								<input type="password" class="form-control" name="vesel_sellprivatekey" id="vesel_sellprivatekey" placeholder="Enter Business Private Key">
																							</div>
																						</div>
																				</div>
																				</div>
																				<input type="hidden" name="qs" value="">
														                        <input type="hidden" name="rules" value="">
														                        <input type="hidden" name="hdnlangpref" id="hdnlangpref3" value="en">
																			</form>
																		</div>
																	</div>
																</div>
														    	<!-- row -->
																</div>
																<div class="modal-footer">
																	<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
																	<button type="button" class="btn btn-danger" id="btn_update_vessel_offer">Update</button>
																</div>
															</div>
														</div>
													</div>
													<!-- Delete pporte offer -->
																	<!-- Modal -->
													<div class="modal fade" id="delete-porte-form-modal" tabindex="-1" role="dialog" aria-labelledby="largemodal" aria-hidden="true">
														<div class="modal-dialog modal-lg " role="document">
															<div class="modal-content">
																<div class="modal-header">
																	<h5 class="modal-title" id="largemodal1">Delete Porte Offer</h5>
																	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
																		<span aria-hidden="true">X</span>
																	</button>
																</div>
																<div class="modal-body">
																<!-- row -->
																<div class="row">
																	<div class="col-md-12">
																		<div class="card">
																		    <form id="delete_pporte_offer_form" method = "post">
																				<div class="card-body">
																					<div class="row">
																						<div class="col-xl-6">
																							<div class="form-group">
																								<label class="form-label">Offer ID</label> 
																								<input type="text" class="form-control" name="pporteofferid" id="pporteofferid" readonly> 
																							</div>
																						    <div class="form-group">
																								<label class="form-label">Account Public Key</label> 
																								<input type="text" class="form-control" name="pporteaccountid" id="pporteaccountid" readonly> 
																							</div>
																							<div class="form-group">
																								<label class="form-label">Business Private Key</label>
																								<input type="password" class="form-control" name="pporteprivatekey" id="pporteprivatekey" placeholder="Enter Business Private Key">
																							</div>
																						</div>
																						<div class="col-xl-6">	
																							<div class="form-group">
																								<label class="form-label">Price of 1 unit in XLM</label>
																								<input type="number" class="form-control" name="pportepriceunit" id="pportepriceunit" placeholder="Price unit">
																							</div>
																							<div class="form-group hidden" id="div_porte_del_buy_amount">
																								<label class="form-label">Amount you are buying</label>
																								<input type="number" class="form-control" name="portebuyingamount" id="portebuyingamount" readonly>
																							</div>
																							<div class="form-group hidden" id="div_porte_del_sel_amount">
																								<label class="form-label">Amount you are selling</label>
																								<input type="number" class="form-control" name="portesellingamount" id="portesellingamount" readonly>
																							</div>
																						</div>
																				</div>
																				</div>
																				<input type="hidden" name="qs" value="">
														                        <input type="hidden" name="rules" value="">
														                        <input type="hidden" name="hdnlangpref" id="hdnlangpref3" value="en">
																			</form>
																		</div>
																	</div>
																</div>
														    	<!-- row -->
																</div>
																<div class="modal-footer">
																	<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
																	<button type="button" class="btn btn-danger" id="btn_delete_pporte_offer">Delete</button>
																</div>
															</div>
														</div>
													</div>
													<!-- Delete vesl offer -->
																	<!-- Modal -->
													<div class="modal fade" id="delete-vesl-form-modal" tabindex="-1" role="dialog" aria-labelledby="largemodal" aria-hidden="true">
														<div class="modal-dialog modal-lg " role="document">
															<div class="modal-content">
																<div class="modal-header">
																	<h5 class="modal-title" id="largemodal1">Delete VESL Offer</h5>
																	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
																		<span aria-hidden="true">X</span>
																	</button>
																</div>
																<div class="modal-body">
																<!-- row -->
																<div class="row">
																	<div class="col-md-12">
																		<div class="card">
																		    <form id="delete_vesl_offer_form" method = "post">
																				<div class="card-body">
																					<div class="row">
																						<div class="col-xl-6">
																							<div class="form-group">
																								<label class="form-label">Offer ID</label> 
																								<input type="text" class="form-control" name="veslofferid" id="veslofferid" readonly> 
																							</div>
																						    <div class="form-group">
																								<label class="form-label">Account Public Key</label> 
																								<input type="text" class="form-control" name="veslaccountid" id="veslaccountid" readonly> 
																							</div>
																							<div class="form-group">
																								<label class="form-label">Business Private Key</label>
																								<input type="password" class="form-control" name="veslprivatekey" id="veslprivatekey" placeholder="Enter Business Private Key">
																							</div>
																						</div>
																						<div class="col-xl-6">	
																							<div class="form-group">
																								<label class="form-label">Price of 1 unit in XLM</label>
																								<input type="number" class="form-control" name="veslpriceunit" id="veslpriceunit" placeholder="Price unit">
																							</div>
																							<div class="form-group hidden" id="div_vesl_del_buy_amount">
																								<label class="form-label">Amount you are buying</label>
																								<input type="number" class="form-control" name="veslbuyingamount" id="veslbuyingamount" readonly>
																							</div>
																							<div class="form-group hidden" id="div_vesl_del_sel_amount">
																								<label class="form-label">Amount you are selling</label>
																								<input type="number" class="form-control" name="veslsellingamount" id="veslsellingamount" readonly>
																							</div>
																						</div>
																				</div>
																				</div>
																				<input type="hidden" name="qs" value="">
														                        <input type="hidden" name="rules" value="">
														                        <input type="hidden" name="hdnlangpref" id="hdnlangpref3" value="en">
																			</form>
																		</div>
																	</div>
																</div>
														    	<!-- row -->
																</div>
																<div class="modal-footer">
																	<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
																	<button type="button" class="btn btn-danger" id="btn_delete_vesl_offer">Delete</button>
																</div>
															</div>
														</div>
													</div>
											</div>
										</div>
									</div>
							<!-- section-wrapper -->
						</div>
						<form id="create_new_offer" method="post">
							<input type="hidden" name="qs">
							<input type="hidden" name="rules">
						<input type="hidden"name="hdnlangpref" id="hdnlangpref3" value="en">
						</form>
					</div>
					<!-- row end -->					
	
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
		<script src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>

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
		<script src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>
		
		<!-- Custom js-->
		<script src="assets/js/custom.js"></script>
		<script src="assets/js/_ops_view_offers_page.js"></script>
		
		
		
		
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
	if (context!=null)context=null;
	if (arrVessleOffers!=null)arrVessleOffers=null;
	if (arrOffers!=null)arrOffers=null;
}
%>
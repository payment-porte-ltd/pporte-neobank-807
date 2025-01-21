<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*, com.pporte.model.*,	com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
ArrayList<LoyaltyRules> arrLoyaltyRules = null; ConcurrentHashMap<String, String> hashStatus = null; 
ConcurrentHashMap<String, String> hashUserType = null;
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}
	
	if (request.getAttribute("merchantpricingplan") !=null) arrLoyaltyRules = (ArrayList<LoyaltyRules>) request.getAttribute("merchantpricingplan");
	
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
<title>Merchant Plan</title>

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
							<li class="breadcrumb-item"><a href="#">Merchant Pricing</a></li>
							<li class="breadcrumb-item active" aria-current="page">View
								Merchant Plan</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- row -->
					<div class="row">
						<p>Comming soon!</p>
					</div>
					<%--<div class="col-md-12 col-lg-12">
							<div class="card">
								<div class="card-header">
									<div class="card-title">Merchant Plan </div>
									<div class="ml-auto">
										<div class="input-group">
											<a class="btn btn-primary text-white mr-2" data-toggle="modal" data-target="#addloyaltyruleModal">
												<span> <i class="typcn typcn-plus"></i> Allocate Merchant Plan</span>
											</a>

										</div>
									</div>
								</div>
								<div class="card-body">
									<div class="table-responsive">
										<table id="viewmcctable" class="table table-striped table-bordered text-nowrap w-100">
										
											<thead>
												<tr>
													<th>Pay Mode</th>
													<th>Rule Description</th>
													<th>Points Conversion</th>
													<th>Crypto Conversion</th>
													<th>User Type</th>
													<th>Status</th>
													<th>Createdon</th>
													<th>Action</th>
													
												</tr>
											</thead>
											<tbody>
												<%
												if (arrLoyaltyRules != null) {
													for (int i = 0; i < arrLoyaltyRules.size(); i++) {
												%>
												<tr>
													<td><%=((LoyaltyRules) arrLoyaltyRules.get(i)).getPayMode()%></td>
													<td><%=((LoyaltyRules) arrLoyaltyRules.get(i)).getRuleDesc()%></td>
													<td><%=((LoyaltyRules) arrLoyaltyRules.get(i)).getPointsConvertRatio()%></td>
													<td><%=((LoyaltyRules) arrLoyaltyRules.get(i)).getCryptoConvertRatio()%></td>
													<td><%=hashUserType.get( ((LoyaltyRules) arrLoyaltyRules.get(i)).getUserType())%></td>
													<td><%=hashStatus.get( ((LoyaltyRules) arrLoyaltyRules.get(i)).getStatus())%></td>													
													<td><%=((LoyaltyRules) arrLoyaltyRules.get(i)).getCreatedon()%></td>													
													<td class="text-center align-middle">
														<div class="btn-group align-top">
															<button class="btn btn-sm btn-primary badge"
																data-target="#user-form-modal" data-toggle="modal" type="button" 
																onClick="javascript:fnEditLoyaltyRules('<%=((LoyaltyRules) arrLoyaltyRules.get(i)).getPayMode()%>',
																'<%=((LoyaltyRules) arrLoyaltyRules.get(i)).getRuleDesc()%>','<%=((LoyaltyRules) arrLoyaltyRules.get(i)).getPointsConvertRatio()%>',
																'<%=((LoyaltyRules) arrLoyaltyRules.get(i)).getCryptoConvertRatio()%>','<%=((LoyaltyRules) arrLoyaltyRules.get(i)).getUserType()%>',
																'<%=((LoyaltyRules) arrLoyaltyRules.get(i)).getStatus()%>','<%=((LoyaltyRules) arrLoyaltyRules.get(i)).getCreatedon()%>'
																);return false;"><i class="fa fa-edit"></i>Edit</button>
														</div>
													</td>
													</tr>

													<%
													 }
													} else {
													%>
												
												<tr>
													<td colspan="9"><span id="ops_all_bin_list_errormsg1">No Loyalty Rules available </span></td>
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
					</div> --%>
					<!-- row end -->
					<!-- Modal -->
					<!--  <div class="modal fade" id="editLoyaltyRulesModal" tabindex="-1" role="dialog" aria-labelledby="largemodal" aria-hidden="true">
							<div class="modal-dialog modal-lg " role="document">
								<div class="modal-content">
									<div class="modal-header">
										<h5 class="modal-title" id="largemodal1">Edit Loyalty Rules</h5>
										<button type="button" class="close" data-dismiss="modal" aria-label="Close">
											<span aria-hidden="true">X</span>
										</button>
									</div>
									<div class="modal-body">
									row
									<div class="row">
										<div class="col-md-12">
											<div class="card">
											    <form id="editloyalty-form" method="post">
													<div class="card-body">
														<div class="row">
													      <div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Pay Mode</label> 
																	<input type="text" class="form-control" name="editpaymode" id="editpaymode" readonly >
																</div>
																<div class="form-group">
																	<label class="form-label">Point Conversion</label> 
																	<input type="text" class="form-control" name="editpointconvtn" id="editpointconvtn" >
																</div>
																<div class="form-group">
																	<label class="form-label">User Type</label> <select
																		class="form-control" id="selleditusertype" name="selleditusertype">
																		<option value="">Select PayType</option>
																		<option value="C">Customer</option>
																		<option value="M">Merchant</option>
																	</select>
																</div>
															</div>
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Rule Description</label> 
																	<input type="text" class="form-control" name="editruledesc" id="editruledesc" >
																</div>
																<div class="form-group">
																	<label class="form-label">Crypto Conversion</label> 
																	<input type="text" class="form-control" name="editcryptoconvtn" id="editcryptoconvtn" >
																</div>
																<div class="form-group">
																	<label class="form-label">Status</label> <select
																		class="form-control" id="selleditstatus" name="selleditstatus">
																		<option value="">Select PayType</option>
																		<option value="A">Active</option>
																		<option value="I">	Inactive</option>
																	</select>
																</div>	
															</div>
														</div>
													</div>
													<input type="hidden" name="qs" value="">
							                        <input type="hidden" name="rules" value="">
                 							        <input type="hidden" name="hdnloyaltystatus" value="">
                 							        <input type="hidden" name="hdnusertype" value="">
							                        <input type="hidden" name="hdnlangpref" id="hdnlangpref3" value="en">
												</form>
											</div>
											
										</div>
									</div>
							    	row
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>										
										<button type="button" class="btn btn-primary" id="btn-editeditloyaltyrule">Submit</button>
									</div>
								</div>
							</div>
						</div> -->

					<!-- End Model -->
					<!-- Modal -->
					<!-- <div class="modal fade" id="addloyaltyruleModal" tabindex="-1" role="dialog" aria-labelledby="largemodal" aria-hidden="true">
							<div class="modal-dialog modal-lg " role="document">
								<div class="modal-content">
									<div class="modal-header">
										<h5 class="modal-title" id="largemodal1">Add Loyalty Rule</h5>
										<button type="button" class="close" data-dismiss="modal" aria-label="Close">
											<span aria-hidden="true">X</span>
										</button>
									</div>
									<div class="modal-body">
									row
									<div class="row">
										<div class="col-md-12">
											<div class="card">
											    <form id="addloyaltyrules-from" method = "post">
													<div class="card-body">
														<div class="row">
														 <div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Pay Mode</label> 
																	<input type="text" class="form-control" name="addpaymode" id="addpaymode" >
																</div>
																<div class="form-group">
																	<label class="form-label">Point Conversion</label> 
																	<input type="text" class="form-control" name="addpointconvtn" id="addpointconvtn" >
																</div>
																<div class="form-group">
																	<label class="form-label">User Type</label> <select
																		class="form-control" id="selladdusertype" name="selladdusertype">
																		<option value="">Select PayType</option>
																		<option value="C">Customer</option>
																		<option value="M">Merchant</option>
																	</select>
																</div>
															</div>
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Rule Description</label> 
																	<input type="text" class="form-control" name="addruledesc" id="addruledesc" >
																</div>
																<div class="form-group">
																	<label class="form-label">Crypto Conversion</label> 
																	<input type="text" class="form-control" name="addcryptoconvtn" id="addcryptoconvtn" >
																</div>
																<div class="form-group">
																	<label class="form-label">Status</label> <select
																		class="form-control" id="selladdstatus" name="selladdstatus">
																		<option value="">Select Status</option>
																		<option value="A">Active</option>
																		<option value="I">Inactive</option>
																	</select>
																</div>	
															</div>
														</div>
													</div>
													<input type="hidden" name="qs" value="">
							                        <input type="hidden" name="rules" value="">
                 							        <input type="hidden" name="hdnaddloyaltystatus" value="">
                 							        <input type="hidden" name="hdnaddusertype" value="">
							                        <input type="hidden" name="hdnlangpref" id="hdnlangpref3" value="en">
												</form>
											</div>
											
										</div>
									</div>
							    	row
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
										<button type="button" class="btn btn-primary" id="btn-addloyaltyrules">Submit</button>
									</div>
								</div>
							</div>
						</div> -->

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
	<script src="assets/js/_ops_sys_viewloyaltypage.js"></script>

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
	

}
%>
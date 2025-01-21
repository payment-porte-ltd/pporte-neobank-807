<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.utilities.Utilities, com.pporte.model.*, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
ArrayList<PricingPlan> arrPricingPlan=null;	 
ConcurrentHashMap<String,String> hashUserType = null; 
ConcurrentHashMap<String,String> hashStatus = null; Iterator<String> itStatus = null; 
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}
	
	if (request.getAttribute("pricingplans") != null) arrPricingPlan = (ArrayList<PricingPlan>) request.getAttribute("pricingplans");
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
<title>Product Plan</title>

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
							<li class="breadcrumb-item"><a href="#">Customer Plan</a></li>
							<li class="breadcrumb-item active" aria-current="page">Product
								Plan</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- row -->
					<div class="row">
						<div class="col-md-12 col-lg-12">
							<div class="card">
								<div class="card-header">
									<div class="card-title">View All Product Plan for
										Customers</div>
									<div class="ml-auto">
										<div class="input-group">
											<a class="btn btn-primary text-white mr-2"
												data-toggle="modal" data-target="#AddPlanModal"> <span>
													<i class="typcn typcn-plus"></i>Add New Plan
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
													<th>Plan Id</th>
													<th>Plan Description</th>
													<th>Price</th>
													<th>Status</th>
													<th>Action</th>
												</tr>
											</thead>
											<tbody>
												<%
												if (arrPricingPlan != null) {
													for (int i = 0; i < arrPricingPlan.size(); i++) {
												%>
												<tr>
													<td><%=((PricingPlan) arrPricingPlan.get(i)).getPlanId()%></td>
													<td><%=((PricingPlan) arrPricingPlan.get(i)).getPlanName()%></td>
													<td><%=((PricingPlan) arrPricingPlan.get(i)).getPlanPrice()%></td>
													<td><%=hashStatus.get(((PricingPlan) arrPricingPlan.get(i)).getStatus())%></td>
													<td class="text-center align-middle">
														<div class="btn-group align-top">
															<button class="btn btn-sm btn-primary badge"
																type="button"
																onClick="javascript:fnEditPlans('<%=((PricingPlan) arrPricingPlan.get(i)).getPlanId()%>','<%=((PricingPlan) arrPricingPlan.get(i)).getPlanName()%>','<%=((PricingPlan) arrPricingPlan.get(i)).getPlanDuration()%>','<%=((PricingPlan) arrPricingPlan.get(i)).getStatus()%>', '<%=((PricingPlan) arrPricingPlan.get(i)).getPlanPrice()%>', '<%=((PricingPlan) arrPricingPlan.get(i)).getPlanDesc1()%>', '<%=((PricingPlan) arrPricingPlan.get(i)).getPlanDesc2()%>', '<%=((PricingPlan) arrPricingPlan.get(i)).getPlanDesc3()%>', '<%=((PricingPlan) arrPricingPlan.get(i)).getPlanDesc4()%>');return false;">
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
													<td colspan="6"><span id="ops_all_bin_list_errormsg1">No
															Pricing Plan created</span></td>
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
					<div class="modal fade" id="AddPlanModal" tabindex="-1"
						role="dialog" aria-labelledby="largemodal" aria-hidden="true">
						<div class="modal-dialog modal-lg " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="largemodal1">Add New Pricing
										Plan</h5>
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
												<div class="card-header">
													<h3 class="card-title">Add Plan</h3>
													<div class="card-options">
														<a href="#" class="card-options-collapse"
															data-toggle="card-collapse"><i
															class="fe fe-chevron-up"></i></a>
													</div>
												</div>
												<div class="card-alert alert alert-success mb-0">
													Note: Provide Plan Name, Duration, Price as mandatory
													fields. Planid and duration in number only.</div>
												<form id="addplan-form" method="post">
													<div class="card-body">
														<div class="row">
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Plan ID</label> <input
																		type="number" class="form-control" name="addplanid"
																		id="addplanid">
																</div>
																<div class="form-group">
																	<label class="form-label">Plan Name</label> <input
																		type="text" class="form-control" name="addplansname"
																		id="addplansname">
																</div>
															</div>
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Plan Price</label> <input
																		type="text" class="form-control" name="addplanprice"
																		id="addplanprice">
																</div>
																<div class="form-group">
																	<label class="form-label">Status</label> <select
																		class="form-control" id="seladdplantatus"
																		name="seladdplantatus">
																		<option value="">Select status</option>
																		<option value="A">Active</option>
																		<option value="I">Inactive</option>
																	</select>
																</div>
															</div>

														</div>

														<div class="row">
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Plan Description 1</label> <input
																		type="text" class="form-control" name="addplandesc1"
																		id="addplandesc1" value="">
																</div>
																<div class="form-group">
																	<label class="form-label">Plan Description 2</label> <input
																		type="text" class="form-control" name="addplandesc2"
																		id="addplandesc2" value="">
																</div>
															</div>
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Plan Description 3</label> <input
																		type="text" class="form-control" name="addplandesc3"
																		id="addplandesc3" value="">
																</div>
																<div class="form-group">
																	<label class="form-label">Plan Description 4</label> <input
																		type="text" class="form-control" name="addplandesc4"
																		id="addplandesc4" value="">
																</div>
															</div>

														</div>

													</div>
													<input type="hidden" name="qs" value=""> <input
														type="hidden" name="rules" value=""> <input
														type="hidden" name="hdnadduplanstatus" value=""> <input
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
										id="btn-add-pricing-plan">Submit</button>
								</div>
							</div>
						</div>
					</div>

					<!-- End Model -->
					<!-- Modal -->
					<div class="modal fade" id="EditPlanModal" tabindex="-1"
						role="dialog" aria-labelledby="largemodal" aria-hidden="true">
						<div class="modal-dialog modal-lg " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="largemodal1">Edit Plan</h5>
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
												<div class="card-header">
													<h3 class="card-title">Edit Plan</h3>
													<div class="card-options">
														<a href="#" class="card-options-collapse"
															data-toggle="card-collapse"><i
															class="fe fe-chevron-up"></i></a>
													</div>
												</div>
												<div class="card-alert alert alert-success mb-0">
													Note: Change price,status price and description.</div>
												<form id="formeditplan" method="post">
													<div class="card-body">
														<div class="row">
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Plan ID</label> <input
																		type="text" class="form-control" name="editplanid"
																		id="editplanid" readonly>
																</div>
																<div class="form-group">
																	<label class="form-label">Plan Name</label> <input
																		type="text" class="form-control" name="editplanname"
																		id="editplanname">
																</div>
																<div class="form-group">
																	<label class="form-label">Plan Price</label> <input
																		type="text" class="form-control" name="editplanprice"
																		id="editplanprice">
																</div>
																<div class="form-group">
																	<label class="form-label">Status</label> <select
																		class="form-control" id="editplanselstatus"
																		name="editplanselstatus">
																		<option value="">Select status</option>
																		<option value="A">Active</option>
																		<option value="I">Inactive</option>
																	</select>
																</div>
															</div>
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Plan Description 1</label> <input
																		type="text" class="form-control" name="editplandesc1"
																		id="editplandesc1">
																</div>
																<div class="form-group">
																	<label class="form-label">Plan Description 2</label> <input
																		type="text" class="form-control" name="editplandesc2"
																		id="editplandesc2">
																</div>
																<div class="form-group">
																	<label class="form-label">Plan Description 3</label> <input
																		type="text" class="form-control" name="editplandesc3"
																		id="editplandesc3">
																</div>
																<div class="form-group">
																	<label class="form-label">Plan Description 4</label> <input
																		type="text" class="form-control" name="editplandesc4"
																		id="editplandesc4">
																</div>
															</div>
														</div>
													</div>
													<input type="hidden" name="qs" value=""> <input
														type="hidden" name="rules" value=""> <input
														type="hidden" name="hdneditplanstatus" value=""> <input
														type="hidden" name="hdneditplanid" value=""> <input
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
									<button type="button" class="btn btn-primary" id="btn-editplan">Submit</button>
								</div>
							</div>
						</div>
					</div>

					<!-- End Model -->
					<div id="error-msg-list" style="display: none;">
						<span id="ops-manage-pricingplan-error-swal-header">Oops..</span>
						<span id="ops-manage-pricingplan-error-swal-checkdata">Please
							check your data</span> <span
							id="ops-manage-pricingplan-error-swal-contact-admin">If
							issue persists please contact admin</span> <span
							id="ops-manage-pricingplan-success-header">All Good</span> <span
							id="ops-manage-pricingplan-success-message">Update
							Succesful</span>
						<form id="tempform" method="post">
							<input type="hidden" name="qs" value=""> <input
								type="hidden" name="rules" value="">
						</form>
					</div>
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
	<script src="assets/js/_ops_pricing_plan.js"></script>



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
if(arrPricingPlan!=null) if(arrPricingPlan.size()>0) arrPricingPlan=null;
if(hashUserType!=null) if(hashUserType.size()>0) hashUserType=null;
if(hashStatus!=null) if(hashStatus.size()>0) hashStatus=null;
if(itStatus!=null)  itStatus=null;if (context!=null)context=null;
		
}
%>
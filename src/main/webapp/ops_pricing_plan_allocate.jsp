<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*, 	com.pporte.utilities.Utilities,com.pporte.model.*, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
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
<title>Pricing Plan Allocation</title>

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
							<li class="breadcrumb-item active" aria-current="page">Customer
								Plan Allocation</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- row -->
					<div class="row">
						<div class="col-md-12 col-lg-12">
							<div class="card">
								<div class="card-header">
									<div class="card-title">Search for customer</div>
								</div>
								<div class="card-body">
									<form class="m-t-40" id="searchspecificcust-form"
										name="searchspecificcust-form" method="post">
										<div class="row">
											<div class="col-6">
												<div class="form-group">
													<label class="form-label"> Search search type </label> <select
														class="form-control" data-placeholder="Choose search type"
														id="selsearchtype" name="selsearchtype"
														onChange="javascript: fnGetSearchType(); return false">
														<optgroup label="Search type">
															<option disabled selected>Please select</option>
															<option value="Relationship_Number">Relationship
																Number</option>
															<option value="Customer_Id">Customer ID</option>
															<option value="Mobile_Number">Mobile Number</option>
														</optgroup>
													</select>
												</div>
											</div>
											<div class="col-6" id="searchbycard">
												<div class="form-group">
													<label class="form-label" id="searchbytype">Search
														By:-</label>

												</div>
											</div>
											<div class="col text-center">
												<button class="btn btn-info mb-1 btn-block "
													id="btn-search-customer">Search</button>
											</div>
										</div>
										<input type="hidden" name="qs" value=""> <input
											type="hidden" name="rules" value="">
									</form>
								</div>
							</div>
						</div>
					</div>
					<div id="search_results"></div>
				</div>
				<!-- row end -->
				<!-- Modal -->
				<div class="modal fade" id="AddPlanModal" tabindex="-1"
					role="dialog" aria-labelledby="largemodal" aria-hidden="true">
					<div class="modal-dialog modal-lg " role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="largemodal1">Change Pricing
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
												<h3 class="card-title">Current Customer Plan</h3>
												<div class="card-options">
													<a href="#" class="card-options-collapse"
														data-toggle="card-collapse"><i
														class="fe fe-chevron-up"></i></a>
												</div>
											</div>

											<form id="addplan-form" method="post">
												<div class="card-body">
													<div class="row">
														<div class="col-xl-6">
															<div class="form-group">
																<label class="form-label">Customer Relationship
																	No</label> <input type="text" class="form-control"
																	name="addcustid" id="addcustid" readonly>
															</div>
															<div class="form-group">
																<label class="form-label">Plan Name</label> <input
																	type="text" class="form-control" name="planname"
																	id="planname" readonly>
															</div>

														</div>
														<div class="col-xl-6">
															<div class="form-group">
																<label class="form-label">Plan Start Date</label> <input
																	type="text" class="form-control" name="planstartdate"
																	id="planstartdate" readonly>
															</div>
															<div class="form-group">
																<label class="form-label">Plan Price</label> <input
																	type="text" class="form-control" name="planprice"
																	id="planprice" readonly>
															</div>

														</div>
														<div class="col-xl-6">
															<div class="form-group">
																<label class="form-label">Status</label> <input
																	type="text" class="form-control" name="status"
																	id="status" readonly>
															</div>

														</div>
														<div class="col-xl-6">

															<div class="form-group">
																<label class="form-label">Reason</label>
																<textarea class="form-control" name="orig_reason"
																	id="orig_reason" readonly></textarea>
															</div>
														</div>
														<div class="col-xl-12">
															<div class="card-alert alert alert-info mb-0">
																Note: Changing a plan of the customer,will activate the
																plan automatically.</div>
															<div class="form-group">
																<label class="form-label fw-bolder">Change Plan</label>

																<select class="form-control" id="seladdplanid"
																	name="seladdplanid"
																	onchange="javascript:fnshowAddReasonForChange();return false;">
																	<option value="">Select Plan</option>
																	<%
																			if (arrPricingPlan != null) {
																				for (int i = 0; i < arrPricingPlan.size(); i++) {
																			%>
																	<option
																		value="<%=((PricingPlan) arrPricingPlan.get(i)).getPlanId()%>"><%=((PricingPlan) arrPricingPlan.get(i)).getPlanName()%></option>
																	<%
																				}
																			} 
																			%>
																</select>
															</div>
														</div>
														<div id="reason_div" style="display: none" class="col-12">
															<div class="form-group">
																<label class="form-label">Reason for allocation</label>
																<textarea class="form-control" id="reason" name="reason"
																	rows="3"
																	placeholder="Write reason for changing plan of customer.."></textarea>
															</div>
														</div>

													</div>
												</div>
												<input type="hidden" name="qs" value=""> <input
													type="hidden" name="rules" value=""> <input
													type="hidden" name="hdnadduplanid" value=""> <input
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
									id="btn-add-pricing-plan-allocate">Submit</button>
							</div>
						</div>
					</div>
				</div>

				<!-- End Model -->



				<div id="error-msg-list" style="display: none;">
					<span id="ops-manage-pricingplan-error-swal-header">Oops..</span> <span
						id="ops-manage-pricingplan-error-swal-checkdata">Please
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
	<script src="assets/js/_ops_pricing_plan_allocate.js"></script>



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
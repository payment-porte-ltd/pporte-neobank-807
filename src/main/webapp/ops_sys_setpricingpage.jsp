<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*, com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
ArrayList<PricingDetails> arrPricingDetails = null; 
ArrayList<TransactionRules> arrTransactionRules = null;
ConcurrentHashMap<String, String> hashPricingtatus = null; 
ConcurrentHashMap<String, String> hashPayType = null; ConcurrentHashMap<String, String> hashisDefault = null;
ConcurrentHashMap<String, String> hashUserType = null;
ConcurrentHashMap<String, String> hashPayMode = null;Iterator<String> itPayMode = null; 
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}
		
	if (request.getAttribute("arrpricingplans") != null) arrPricingDetails = (ArrayList<PricingDetails>) request.getAttribute("arrpricingplans");
	if (request.getAttribute("arrtxnrules") != null) arrTransactionRules = (ArrayList<TransactionRules>) request.getAttribute("arrtxnrules");
	
	hashPricingtatus = new ConcurrentHashMap<String, String>();
	hashPayType = new ConcurrentHashMap<String, String>();
	hashisDefault = new ConcurrentHashMap<String, String>();
	hashUserType = new ConcurrentHashMap<String, String>();
	hashPayMode = new ConcurrentHashMap<String, String>();
	
	hashPricingtatus.put("A", "Active");
	hashPricingtatus.put("P", "Pending");
	hashPricingtatus.put("I", "Inactive");
	
	hashPayType.put("D", "Debit");
	hashPayType.put("C", "Credit");
	
	hashisDefault.put("Y", "Yes");
	hashisDefault.put("N", "No");
	
	hashUserType.put("M0", "Merchant");
	hashUserType.put("C0", "Customer");
	
	itPayMode = hashPayMode.keySet().iterator();

	
	
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
<title>Transaction Pricing</title>

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
<!--Select2 css -->
<link href="assets/plugins/select2/select2.min.css" rel="stylesheet" />

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
							<li class="breadcrumb-item"><a href="#">Transaction
									Pricing</a></li>
							<li class="breadcrumb-item active" aria-current="page">Set
								Pricing</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- row -->
					<div class="row">
						<div class="col-md-12 col-lg-12">
							<div class="card">
								<div class="card-header">
									<div class="card-title">Set Pricing</div>
									<div class="ml-auto">
										<div class="input-group">
											<a class="btn btn-primary text-white mr-2"
												data-toggle="modal" data-target="#newmccModal"> <span>
													<i class="typcn typcn-plus"></i> Add New Pricing
											</span>
											</a>
										</div>
									</div>
								</div>
								<div class="card-body">
									<div class="table-responsive">
										<table id="example3"
											class="table table-striped table-bordered text-nowrap">
											<thead>
												<tr>
													<th>PlanId</th>
													<th>User Type</th>
													<th>Plan value</th>
													<th>Plan Description</th>
													<th>Variable Fee</th>
													<th>Fixed Fee</th>
													<th>Minimum amount</th>
													<th>Slab Applicable</th>
													<th>Pay Type</th>
													<th>IsDefault</th>
													<th>Slab Rate Action</th>
													<th>status</th>
													<th>Action</th>
												</tr>
											</thead>
											<tbody>
												<%
												if (arrPricingDetails != null) {
													for (int i = 0; i < arrPricingDetails.size(); i++) {
												%>
												<tr>
													<td><%=((PricingDetails) arrPricingDetails.get(i)).getPlanId()%></td>
													<td><%=hashUserType.get(((PricingDetails) arrPricingDetails.get(i)).getUsetType())%></td>
													<td><%=((PricingDetails) arrPricingDetails.get(i)).getPlanValue()%></td>
													<td><%=((PricingDetails) arrPricingDetails.get(i)).getPlanDesc()%></td>
													<td><%=((PricingDetails) arrPricingDetails.get(i)).getVariableFee()%></td>
													<td><%=((PricingDetails) arrPricingDetails.get(i)).getFixedFee()%></td>
													<td><%=((PricingDetails) arrPricingDetails.get(i)).getMinimumTxnAmount()%></td>
													<td><%=hashisDefault.get(((PricingDetails)arrPricingDetails.get(i)).getSlabApplicable())%></td>
													<td><%=hashPayType.get(((PricingDetails) arrPricingDetails.get(i)).getPayType())%></td>
													<td><%=hashisDefault.get(((PricingDetails) arrPricingDetails.get(i)).getIsDefault())%></td>
													<% if(((PricingDetails)arrPricingDetails.get(i)).getSlabApplicable().equals("Y")){%>
													<td><a href="#"
														onClick="javascript:fnViewSlabRate('<%= ((PricingDetails)arrPricingDetails.get(i)).getPlanId() %>');return false;">View
															Slab</a></td>
													<% }else{%>
													<td>&nbsp;</td>
													<% } %>
													<td><%=hashPricingtatus.get(((PricingDetails) arrPricingDetails.get(i)).getStatus())%></td>
													<td class="text-center align-middle">
														<div class="btn-group align-top">
															<button class="btn btn-sm btn-primary badge"
																data-target="#user-form-modal" data-toggle="modal"
																type="button"
																onClick="javascript:fnEditPricingDetails('<%=((PricingDetails) arrPricingDetails.get(i)).getPlanId()%>','<%=((PricingDetails) arrPricingDetails.get(i)).getUsetType()%>',
																'<%=((PricingDetails) arrPricingDetails.get(i)).getPlanValue()%>','<%=((PricingDetails) arrPricingDetails.get(i)).getPlanDesc()%>',
																'<%=((PricingDetails) arrPricingDetails.get(i)).getVariableFee()%>','<%=((PricingDetails) arrPricingDetails.get(i)).getFixedFee()%>','<%=((PricingDetails) arrPricingDetails.get(i)).getMinimumTxnAmount()%>',
																 '<%=((PricingDetails) arrPricingDetails.get(i)).getPayType()%>','<%=((PricingDetails) arrPricingDetails.get(i)).getIsDefault()%>',
																 '<%=((PricingDetails) arrPricingDetails.get(i)).getStatus()%>', '<%=((PricingDetails) arrPricingDetails.get(i)).getPayMode()%>',
																 '<%=((PricingDetails) arrPricingDetails.get(i)).getSlabApplicable()%>');return false;">
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
													<td colspan="9"><span id="ops_all_bin_list_errormsg1">No
															Pricing data available </span></td>
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
					<div class="modal fade" id="newmccModal" tabindex="-1"
						role="dialog" aria-labelledby="largemodal" aria-hidden="true">
						<div class="modal-dialog modal-lg " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="largemodal1">Add Pricing Plan</h5>
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
												<form id="addenewpricingdetails-form" method="post">
													<div class="card-body">
														<div class="row">
															<div class="col-xl-6">
																<div class="form-group">
																	<% if(arrTransactionRules!=null) { %>
																	<label class="form-label">Pay Mode</label> <select
																		class="form-control" name="seladdpaymode"
																		id="seladdpaymode">
																		<option selected disabled>Select Pay Mode</option>
																		<% for (int i=0; i<arrTransactionRules.size();i++){ %>
																		<option
																			value="<%=((TransactionRules) arrTransactionRules.get(i)).getPayMode()%>"><%=((TransactionRules) arrTransactionRules.get(i)).getRuleDesc()%></option>
																		<%  } %>
																	</select>
																	<%} %>
																</div>
																<div class="form-group">
																	<label class="form-label">Plan value</label> <input
																		type="text" class="form-control" name="addplanvalue"
																		id="addplanvalue">
																</div>
																<div class="form-group">
																	<label class="form-label">Variable Fee<i>(Should
																			be 0)</i></label> <input type="text" class="form-control"
																		name="addvarfee" id="addmccgeneric">
																</div>
																<div class="form-group">
																	<label class="form-label">Minimum amount</label> <input
																		type="text" class="form-control" name="addminamount"
																		id="addminamount">
																</div>
																
																<div class="form-group">
																	<label class="form-label">User Type</label> <select
																		class="form-control" id="sellusertype"
																		name="sellusertype">
																		<option selected disabled>Select User Type</option>
																		<option value="M0">Merchant</option>
																		<option value="C0">Customer</option>
																	</select>
																</div>

																<div class="form-group">
																	<label class="form-label">IsDefault</label> <select
																		class="form-control" id="sellisdefault"
																		name="sellisdefault">
																		<option selected disabled>Select isDefault</option>
																		<option value="Y">Yes</option>
																		<option value="N">No</option>
																	</select>
																</div>

															</div>
															<div class="col-xl-6">

																<div class="form-group">
																	<label class="form-label">Plan Description</label> <input
																		type="text" class="form-control" name="addplandesc"
																		id="addplandesc">
																</div>
																<div class="form-group">
																	<label class="form-label">Fixed Fee</label> <input
																		type="text" class="form-control" name="addfixedfee"
																		id="addfixedfee">
																</div>

																<div class="form-group">
																	<label class="form-label">Pay Type</label> <select
																		class="form-control" id="sellpaytype"
																		name="sellpaytype">
																		<option selected disabled>Select PayType</option>
																		<option value="C">Credit</option>
																		<option value="D">Debit</option>
																	</select>
																</div>
																<div class="form-group">
																	<label class="form-label">Slab Applicable</label> <select
																		class="form-control" id="sellslabapplicable"
																		name="sellslabapplicable">
																		<option selected disabled>Select Slab Applicable</option>
																		<option value="Y">Yes</option>
																		<option value="N">No</option>
																	</select>
																</div>
																<div class="form-group">
																	<label class="form-label">Status</label> <select
																		class="form-control" id="sellpricingstatus"
																		name="sellpricingstatus">
																		<option selected disabled>Select status</option>
																		<option value="A">Active</option>
																		<option value="I">Inactive</option>
																	</select>
																</div>

															</div>
														</div>
													</div>
													<input type="hidden" name="qs" value=""> <input
														type="hidden" name="rules" value=""> <input
														type="hidden" name="hdnuserid" value=""> <input
														type="hidden" name="hdnpricingstatus" value=""> <input
														type="hidden" name="hdnslabapllicable" value=""> <input
														type="hidden" name="hdnpaytype" value=""> <input
														type="hidden" name="hdnusertype" value=""> <input
														type="hidden" name="hdnisdefault" value=""> <input
														type="hidden" name="hdnselpaymode" value=""> <input
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
										id="btn-addnewpricing">Submit</button>
								</div>
							</div>
						</div>
					</div>

					<!-- End Model -->
					<!-- Modal -->
					<div class="modal fade" id="editpricingModal" tabindex="-1"
						role="dialog" aria-labelledby="largemodal" aria-hidden="true">
						<div class="modal-dialog modal-lg " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="largemodal1">Edit Pricing
										Details</h5>
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
												<form id="editPricingdetails-from" method="post">
													<div class="card-body">
														<div class="row">
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Plan Id</label> <input
																		type="text" class="form-control" name="editplanid"
																		id="editplanid" readonly>
																</div>
																<div class="form-group">
																	<label class="form-label">Plan value</label> <input
																		type="text" class="form-control" name="editplanvalue"
																		id="editplanvalue">
																</div>
																<div class="form-group">
																	<label class="form-label">Variable Fee</label> <input
																		type="text" class="form-control" name="editvarfee"
																		id="editvarfee">
																</div>
																<div class="form-group">
																	<label class="form-label">Minimum amount</label> <input
																		type="text" class="form-control" name="editminamount"
																		id="editminamount">
																</div>
																<div class="form-group">
																	<% if(arrTransactionRules!=null) { %>
																	<label class="form-label">Pay Mode</label> <select
																		class="form-control" name="seleditpaymode"
																		id="seleditpaymode">
																		<% for (int i=0; i<arrTransactionRules.size();i++){ %>
																		<option
																			value="<%= ((TransactionRules) arrTransactionRules.get(i)).getPayMode()%>"><%=((TransactionRules) arrTransactionRules.get(i)).getRuleDesc()%></option>
																		<%  } %>
																	</select>
																</div>
																<%} %>

																<div class="form-group">
																	<label class="form-label">IsDefault</label> <select
																		class="form-control" id="selleditisdefault"
																		name="selleditisdefault">
																		<option value="">Select IsDefault</option>
																		<option value="Y">Yes</option>
																		<option value="N">No</option>
																	</select>
																</div>

															</div>
															<div class="col-xl-6">

																<div class="form-group">
																	<label class="form-label">User Type</label> <select
																		class="form-control" id="selleditusertype"
																		name="selleditusertype">
																		<option value="">Select User Type</option>
																		<option value="M0">Merchant</option>
																		<option value="C0">Customer</option>
																	</select>
																</div>
																<div class="form-group">
																	<label class="form-label">Plan Description</label> <input
																		type="text" class="form-control" name="editplandesc"
																		id="editplandesc">
																</div>
																<div class="form-group">
																	<label class="form-label">Fixed Fee</label> <input
																		type="text" class="form-control" name="editfixedfee"
																		id="editfixedfee">
																</div>

																<div class="form-group">
																	<label class="form-label">Pay Type</label> <select
																		class="form-control" id="selleditpaytype"
																		name="selleditpaytype">
																		<option  selected disabled>Select PayType</option>
																		<option value="C">Credit</option>
																		<option value="D">Debit</option>
																	</select>
																</div>
																<div class="form-group">
																	<label class="form-label">Slab Applicable</label> <select
																		class="form-control" id="selleditslabapplicable"
																		name="selleditslabapplicable">
																		<option  selected disabled >Select Slab Applicable</option>
																		<option value="Y">Yes</option>
																		<option value="N">No</option>
																	</select>
																</div>
																<div class="form-group">
																	<label class="form-label">Status</label> <select
																		class="form-control" id="selleditpricingstatus"
																		name="selleditpricingstatus">
																		<option  selected disabled>Select status</option>
																		<option value="A">Active</option>
																		<option value="I">Inactive</option>
																	</select>
																</div>

															</div>
														</div>
													</div>
													<input type="hidden" name="qs" value=""> <input
														type="hidden" name="rules" value=""> <input
														type="hidden" name="hdnuserid" value=""> <input
														type="hidden" name="hdnpricingstatus" value=""> <input
														type="hidden" name="hdnslabapllicable" value=""> <input
														type="hidden" name="hdnpaytype" value=""> <input
														type="hidden" name="hdnusertype" value=""> <input
														type="hidden" name="hdneditisdefault" value=""> <input
														type="hidden" name="hdnseleditpaymode" value=""> <input
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
										id="btn-editpricing">Submit</button>
								</div>
							</div>
						</div>
					</div>
					<form class="m-t-40" id="slabrate-form" method="post">
						<input type="hidden" name="qs" value=""> <input
							type="hidden" name="rules" value=""> <input type="hidden"
							name="hdnplanid" value="">
					</form>

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

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>


	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_ops_syssetpricingpage.js"></script>


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
	if (arrPricingDetails!=null)  arrPricingDetails = null;if (hashPricingtatus!=null)  hashPricingtatus = null;
	if (hashPayType!=null)  hashPayType = null;if (hashisDefault!=null)  hashisDefault = null;
	if (hashUserType!=null)  hashUserType = null;if (context!=null)context=null;
}
%>
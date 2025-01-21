<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*, com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
ArrayList<DisputeTracker> arrCustDisputes=null;
ConcurrentHashMap<String,String> hashUserType = null; Iterator<String> itUserTypes = null;
ConcurrentHashMap<String,String> hashPayMode = null; Iterator<String> itPayMode = null; 
ConcurrentHashMap<String,String> hashStatus = null; Iterator<String> itStatus = null; 
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}
	
	
	
	if (request.getAttribute("allcustdisputes") != null) arrCustDisputes = (ArrayList<DisputeTracker>) request.getAttribute("allcustdisputes");
	hashUserType = new ConcurrentHashMap<String, String>();
	hashStatus = new ConcurrentHashMap<String,String>();	
	
	hashStatus.put("A","Active"); 
 	hashStatus.put("C","Closed"); 
	hashStatus.put("P","In Progress");
	
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
<title>Merchant Dispute</title>

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
							<li class="breadcrumb-item"><a href="#">Manage Disputes</a></li>
							<li class="breadcrumb-item active" aria-current="page">Merchant
								Disputes</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- row -->
					<div class="row">
						<div class="col-md-12 col-lg-12">
							<div class="card">
								<div class="card-header">
									<div class="card-title">Merchant Disputes</div>
									<!-- 	<div class="ml-auto">
										<div class="input-group">
											<a class="btn btn-primary text-white mr-2" data-toggle="modal" data-target="#newmccModal">
												<span> <i class="typcn typcn-plus"></i> Add New Dispute</span>
											</a>

										</div>
									</div> -->
								</div>
								<div class="card-body">
									<div class="table-responsive">
										<table id="viewmcctable"
											class="table table-striped table-bordered text-nowrap w-100">
											<thead>
												<tr>
													<th>Dispute ID</th>
													<th>Transaction ID</th>
													<th>Reason</th>
													<th>Raised By</th>
													<th>User Comments</th>
													<th>Status</th>
													<th>Raised On</th>
													<th>Action</th>
												</tr>
											</thead>
											<tbody>

												<% if(arrCustDisputes!=null){  
						                    for(int i=0;i<arrCustDisputes.size();i++ ){    %>
												<tr>

													<td><%=((DisputeTracker)arrCustDisputes.get(i)).getDisputeId()%></td>
													<td><%=((DisputeTracker)arrCustDisputes.get(i)).getTransactionId()%></td>
													<td><%=((DisputeTracker)arrCustDisputes.get(i)).getReasonDesc()%></td>
													<td><%=((DisputeTracker)arrCustDisputes.get(i)).getRaisedbyUserId()%></td>
													<td><%=((DisputeTracker)arrCustDisputes.get(i)).getUserComment()%></td>
													<td><%=hashStatus.get(((DisputeTracker)arrCustDisputes.get(i)).getStatus())%>
													</td>
													<td><%=((DisputeTracker)arrCustDisputes.get(i)).getRaisedOn()%></td>
													<td><a href="#"
														onclick="javascript:fnShowSpecificCustDispt('<%=((DisputeTracker)arrCustDisputes.get(i)).getDisputeId()%>');return false;">View</a>
													</td>

												</tr>
												<%  }
							                }else{    %>
												<tr>
													<td colspan="7">No Data present</td>
												</tr>
												<% }  %>
											</tbody>
											<tfoot>
												<tr>
													<th>Dispute ID</th>
													<th>Transaction ID</th>
													<th>Reason</th>
													<th>Raised By</th>
													<th>User type</th>
													<th>Status</th>
													<th>Raised On</th>
													<th>Action</th>

												</tr>
											</tfoot>
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
									<h5 class="modal-title" id="largemodal1">Add Dispute
										Reason</h5>
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
												<form id="adddisputes-form" method="post">
													<div class="card-body">
														<div class="row">
															<div class="col-xl-6">
																<!-- <div class="form-group"> 
																	<label class="form-label">MCC Id</label> 
																	<input type="text" class="form-control" name="mccid" id="mccid">
																</div> -->
																<div class="form-group">
																	<label class="form-label">Reason Description</label> <input
																		type="text" class="form-control"
																		name="disputereasondesc" id="disputereasondesc">
																</div>
																<div class="form-group">
																	<label class="form-label">User Type</label> <select
																		class="form-control" id="seldisputeusertype"
																		name="seldisputeusertype">
																		<option value="">Select User Type</option>
																		<option value="C">Customer</option>
																		<option value="M">Merchant</option>
																	</select>
																</div>
															</div>
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Pay Mode</label> <select
																		class="form-control" id="seldisputepaymode"
																		name="seldisputepaymode">
																		<option value="">Select pay mode</option>
																		<option value="C">Credit</option>
																		<option value="D">Debit</option>
																	</select>
																</div>
																<div class="form-group">
																	<label class="form-label">Status</label> <select
																		class="form-control" id="seldisputestatus"
																		name="seldisputestatus">
																		<option value="">Select status</option>
																		<option value="A">Active</option>
																		<option value="P">Pending</option>
																	</select>
																</div>
															</div>
														</div>
													</div>
													<input type="hidden" name="qs" value=""> <input
														type="hidden" name="rules" value=""> <input
														type="hidden" name="hdnuserid" value=""> <input
														type="hidden" name="hdnadddisputestatus" value="">
													<input type="hidden" name="hdnaddpaymode" value="">
													<input type="hidden" name="hdnaddusertype" value="">
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
										id="btn-add-dreasons">Submit</button>
								</div>
							</div>
						</div>
					</div>

					<!-- End Model -->
					<!-- Modal -->
					<div class="modal fade" id="user-form-modal" tabindex="-1"
						role="dialog" aria-labelledby="largemodal" aria-hidden="true">
						<div class="modal-dialog modal-lg " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="largemodal1">Edit Dispute
										Reason</h5>
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
												<form id="editdispute-from" method="post">
													<div class="card-body">
														<div class="row">
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Reason Id</label> <input
																		type="text" class="form-control"
																		name="editdisputereason" id="editdisputereason"
																		readonly>
																</div>
																<div class="form-group">
																	<label class="form-label">Reason Description</label> <input
																		type="text" class="form-control" name="editreasondesc"
																		id="editreasondesc">
																</div>
																<div class="form-group">
																	<label class="form-label">User Type</label> <select
																		class="form-control" id="hdneditusertype"
																		name="hdneditusertype">
																		<option value="">Select User Type</option>
																		<option value="C">Customer</option>
																		<option value="M">Merchant</option>
																	</select>
																</div>
															</div>
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Pay Mode</label> <select
																		class="form-control" id="hdneditpaymode"
																		name="hdneditpaymode">
																		<option value="">Select pay mode</option>
																		<option value="C">Credit</option>
																		<option value="D">Debit</option>
																	</select>
																</div>
																<div class="form-group">
																	<label class="form-label">Status</label> <select
																		class="form-control" id="hdneditstatus"
																		name="hdneditstatus">
																		<option value="">Select status</option>
																		<option value="A">Active</option>
																		<option value="P">Pending</option>
																	</select>
																</div>
																<div class="form-group">
																	<label class="form-label">Date created</label> <input
																		type="text" class="form-control" name="editcreatedon"
																		id="editcreatedon" readonly>
																</div>
															</div>
														</div>
													</div>
													<input type="hidden" name="qs" value=""> <input
														type="hidden" name="rules" value=""> <input
														type="hidden" name="hdnuserid" value=""> <input
														type="hidden" name="hdneditusertype" value=""> <input
														type="hidden" name="hdneditpaymode" value=""> <input
														type="hidden" name="hdneditstatus" value=""> <input
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
										id="btn-editdisputereasons">Submit</button>
								</div>
							</div>
						</div>
					</div>
					<form id="showspecfcdispute-from" method="post">
						<input type="hidden" name="qs" value=""> <input
							type="hidden" name="rules" value=""> <input type="hidden"
							name="hdndspid" value=""> <input type="hidden"
							name="hdnlangpref" id="hdnlangpref3" value="en">
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

	<!-- Data tables js-->
	<script src="assets/plugins/datatable/jquery.dataTables.min.js"></script>
	<script src="assets/plugins/datatable/dataTables.bootstrap4.min.js"></script>
	<script src="assets/plugins/datatable/datatable.js"></script>
	<script src="assets/plugins/datatable/datatable-2.js"></script>
	<script src="assets/plugins/datatable/dataTables.responsive.min.js"></script>

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>

	<!-- Parsley js-->
	<script src="assets/plugins/parsley/parsley.min.js"></script>
	<!-- Sweetalert js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.all.min.js"></script>

	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>

	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_ops_dispute_cust_disputespage.js"></script>




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
	if (arrCustDisputes!=null)arrCustDisputes=null;
	if (hashUserType!=null)hashUserType=null;
	if (itUserTypes!=null)itUserTypes=null;
	if (hashPayMode!=null)hashPayMode=null;
	if (itPayMode!=null)itPayMode=null;
	if (hashStatus!=null)hashStatus=null;
	if (itStatus!=null)itStatus=null;
}
%>
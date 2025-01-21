<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*, com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
ArrayList<BlockCodes> arrBlockCodes=null;
ConcurrentHashMap<String,String> hashStatus = null; Iterator<String> isStatus = null;
ConcurrentHashMap<String,String> hashAuthentication = null; Iterator<String> itAuthentication = null; 
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}
	if (request.getAttribute("blockcodes") != null) arrBlockCodes = (ArrayList<BlockCodes>) request.getAttribute("blockcodes");
	hashStatus = new ConcurrentHashMap<String,String>();
	hashStatus.put("A","Active");	hashStatus.put("I","Inactive"); 
	
	hashAuthentication = new ConcurrentHashMap<String,String>();
	hashAuthentication.put("F","Fail");	hashAuthentication.put("S","Success"); 
	
%>
<!doctype html>
<html lang="en" dir="ltr">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Favicon -->
<link rel="icon" href="assets/images/brand/pporte.png"
	type="image/x-icon" />

<!-- Title -->
<title>Manage Block Codes</title>

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
							<li class="breadcrumb-item"><a href="#">Manage Block
									Codes></li>
							<li class="breadcrumb-item active" aria-current="page">View
								Block Codes</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- row -->
					<div class="row">
						<div class="col-md-12 col-lg-12">
							<div class="card">
								<div class="card-header">
									<div class="card-title">Block Codes</div>
									<div class="ml-auto">
										<div class="input-group">
											<a class="btn btn-primary text-white mr-2"
												data-toggle="modal" data-target="#newmccModal"> <span>
													<i class="typcn typcn-plus"></i> Add New Block Code
											</span>
											</a>

										</div>
									</div>
								</div>
								<div class="card-body">
									<div class="table-responsive">
										<table id="viewmcctable"
											class="table table-striped table-bordered text-nowrap w-100">
											<thead>
												<tr>
													<th>Block Code ID</th>
													<th>Block Code Description</th>
													<th>Status</th>
													<th>Authentication</th>
													<th>Created On</th>
													<th>Action</th>
												</tr>
											</thead>
											<tbody>
												<%
												if (arrBlockCodes != null) {
													for (int i = 0; i < arrBlockCodes.size(); i++) {
												%>
												<tr>
													<td><%=((BlockCodes) arrBlockCodes.get(i)).getBlockCodeId()%></td>
													<td><%=((BlockCodes) arrBlockCodes.get(i)).getBlockCodeDesc()%></td>
													<td><%=hashStatus.get(((BlockCodes) arrBlockCodes.get(i)).getStatus())%></td>
													<td><%=hashAuthentication.get(((BlockCodes) arrBlockCodes.get(i)).getAuthAction())%></td>
													<td><%=((BlockCodes) arrBlockCodes.get(i)).getCreatedOn()%></td>
													<td class="text-center align-middle">
														<div class="btn-group align-top">
															<button class="btn btn-sm btn-primary badge"
																data-target="#user-form-modal" data-toggle="modal"
																type="button"
																onClick="javascript:fnEditBlockCodes('<%=((BlockCodes) arrBlockCodes.get(i)).getBlockCodeId()%>','<%=((BlockCodes) arrBlockCodes.get(i)).getBlockCodeDesc()%>',
																'<%=((BlockCodes) arrBlockCodes.get(i)).getStatus()%>','<%=((BlockCodes) arrBlockCodes.get(i)).getAuthAction()%>',
																'<%=((BlockCodes) arrBlockCodes.get(i)).getCreatedOn()%>');return false;">
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
															Block Codes</span></td>
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
									<h5 class="modal-title" id="largemodal1">Add Block Codes</h5>
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
												<form id="addblockcode-form" method="post">
													<div class="card-body">
														<div class="row">
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Block Code
																		Description</label> <input type="text" class="form-control"
																		name="blockcodedesc" id="blockcodedesc">
																</div>
																<div class="form-group">
																	<label class="form-label">Authentication</label> <select
																		class="form-control" id="selauthentication"
																		name="selauthentication">
																		<option value="">Select Authentication</option>
																		<option value="S">Success</option>
																		<option value="F">Fail</option>
																	</select>
																</div>
															</div>
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Status</label> <select
																		class="form-control" id="selblockstatus"
																		name="selblockstatus">
																		<option value="">Select status</option>
																		<option value="A">Active</option>
																		<option value="I">Inactive</option>
																	</select>
																</div>
															</div>


														</div>
													</div>
													<input type="hidden" name="qs" value=""> <input
														type="hidden" name="rules" value=""> <input
														type="hidden" name="hdnaddblockstatus" value=""> <input
														type="hidden" name="hdnaddselauthentication" value="">
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
										id="btn-add-blockcodes">Submit</button>
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
									<h5 class="modal-title" id="largemodal1">Edit Block Codes</h5>
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
												<form id="editblockcodes-from" method="post">
													<div class="card-body">
														<div class="row">
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Block Code Id</label> <input
																		type="text" class="form-control" name="editblockid"
																		id="editblockid" readonly>
																</div>
																<div class="form-group">
																	<label class="form-label">Block Code
																		Description</label> <input type="text" class="form-control"
																		name="editblockcodedesc" id="editblockcodedesc">
																</div>
																<div class="form-group">
																	<label class="form-label">Status</label> <select
																		class="form-control" id="seleditstatus"
																		name="seleditstatus">
																		<option disabled>Select Status</option>
																		<option value="A">Active</option>
																		<option value="I">Inactive</option>
																	</select>
																</div>
															</div>
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Authenticate</label> <select
																		class="form-control" id="seleditauthenticate"
																		name="seleditauthenticate">
																		<option disabled>Select Authentication</option>
																		<option value="F">Fail</option>
																		<option value="S">Success</option>
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
														type="hidden" name="hdneditstatus" value=""> <input
														type="hidden" name="hdnauthenticate" value=""> <input
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
										id="btn-editblockcode">Submit</button>
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

	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_ops_block_code_page.js"></script>

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
	if (arrBlockCodes!=null)arrBlockCodes=null;if (hashStatus!=null)hashStatus=null;
	if (isStatus!=null)isStatus=null; if (itAuthentication!=null)itAuthentication=null;
	if (hashAuthentication!=null)hashAuthentication=null;if (context!=null)context=null;
}
%>
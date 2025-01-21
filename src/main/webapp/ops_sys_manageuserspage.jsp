<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*,	com.pporte.utilities.Utilities, com.pporte.model.*, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
ArrayList <User> arrOpsAllUserDetails = null; ConcurrentHashMap<String, String> hashStatus = null; Iterator<String> itstatus = null; 
Iterator<String> itaAccessType = null; ConcurrentHashMap<String, String> hashAccessType = null;ConcurrentHashMap<String, String> hashAccountStatus = null;
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}
	
	if (request.getAttribute("allopsusers") !=null) arrOpsAllUserDetails = (ArrayList<User>) request.getAttribute("allopsusers");
	
	hashStatus = new ConcurrentHashMap<String, String>();
	hashAccessType = new ConcurrentHashMap<String, String>();
	hashAccountStatus = new ConcurrentHashMap<String, String>();
	
	hashStatus.put("A", "Active");
	hashStatus.put("I", "Inactive");
	
	hashAccessType.put("S", "Superuser");
	hashAccessType.put("O", "Operation");
	hashAccessType.put("T", "Trading Desk Admin");
	
	hashAccountStatus.put("0", "Active");
	hashAccountStatus.put("1", "Active");
	hashAccountStatus.put("2", "Active");
	hashAccountStatus.put("3", "Blocked");
	
	itstatus = hashStatus.keySet().iterator();
	itaAccessType = hashAccessType.keySet().iterator();

	
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
<title>Manage Ops Users</title>

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
							<li class="breadcrumb-item"><a href="#">Profile</a></li>
							<li class="breadcrumb-item active" aria-current="page">Manage
								Ops Users</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->

					<!-- row -->
					<div class="row">
						<div class="col-md-12 col-lg-12">
							<div class="card">
								<div class="card-header">
									<div class="card-title">Manage Ops Users</div>
									<div class="ml-auto">
										<div class="input-group">
											<a class="btn btn-primary text-white mr-2"
												data-toggle="modal" data-target="#newopsuserModal"> <span>
													<i class="typcn typcn-plus"></i> Add New User
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
													<th>UserId</th>
													<th>User Type</th>
													<th>User Name</th>
													<th>Email</th>
													<th>Contact</th>
													<th>Status</th>
													<th>Createdon</th>
													<th>Expiry</th>
													<th>Account Status</th>
													<th>Action</th>
												</tr>
											</thead>
											<tbody>
												<%
													if (arrOpsAllUserDetails != null) {
														for (int i = 0; i < arrOpsAllUserDetails.size(); i++) {
													%>
												<tr>
													<td><%=((User) arrOpsAllUserDetails.get(i)).getUserId()%></td>
													<td><%=hashAccessType.get(((User) arrOpsAllUserDetails.get(i)).getAccessType())%></td>
													<td><%=((User) arrOpsAllUserDetails.get(i)).getUserName()%></td>
													<td><%=((User) arrOpsAllUserDetails.get(i)).getUserEmail()%></td>
													<td><%=((User) arrOpsAllUserDetails.get(i)).getUserContact()%></td>
													<td><%=hashStatus.get( ((User) arrOpsAllUserDetails.get(i)).getUserStatus())%></td>
													<td><%=((User) arrOpsAllUserDetails.get(i)).getUserCreatedOn()%></td>
													<td><%=((User) arrOpsAllUserDetails.get(i)).getExpiryDate()%></td>
													<td><%=hashAccountStatus.get(((User) arrOpsAllUserDetails.get(i)).getPasswdTries())%>
														<div class="btn-group align-top">
															<button class="btn btn-sm btn-primary badge"
																data-target="#user-
																	form-modal"
																data-toggle="modal" type="button"></button>
														</div></td>
													<td class="text-center align-middle">
														<div class="btn-group align-top">
															<button class="btn btn-sm btn-primary badge"
																data-target="#user-form-modal" data-toggle="modal"
																type="button"
																onClick="javascript:fnEditOpsUser('<%=((User) arrOpsAllUserDetails.get(i)).getUserId()%>',
																	'<%=((User) arrOpsAllUserDetails.get(i)).getUserName()%>','<%=((User) arrOpsAllUserDetails.get(i)).getUserEmail()%>',
																	'<%=((User) arrOpsAllUserDetails.get(i)).getUserContact()%>','<%=((User) arrOpsAllUserDetails.get(i)).getUserStatus()%>',
																	 '<%=((User) arrOpsAllUserDetails.get(i)).getUserCreatedOn()%>','<%=((User) arrOpsAllUserDetails.get(i)).getExpiryDate()%>',
																	 '<%=((User) arrOpsAllUserDetails.get(i)).getPasswdTries()%>','<%=((User) arrOpsAllUserDetails.get(i)).getAccessType()%>',);return false;">
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
															Users available </span></td>
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
					<!-- End Model -->
					<!-- Modal -->
					<div class="modal fade" id="editopsuserModal" tabindex="-1"
						role="dialog" aria-labelledby="largemodal" aria-hidden="true">
						<div class="modal-dialog modal-lg " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="largemodal1">
										Manage <span id="adduserdesc"> </span>Ops users
									</h5>
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
												<form id="editopsuser-form" method="post">
													<div class="card-body">
														<div class="row">
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">UserId</label> <input
																		type="text" class="form-control" name="editopsuid"
																		id="editopsuid" readonly>
																</div>
																<div class="form-group">
																	<label class="form-label">User Name</label> <input
																		type="text" class="form-control" name="editusername"
																		id="editusername">
																</div>
																<div class="form-group">
																	<label class="form-label">Contact</label> <input
																		type="text" class="form-control"
																		name="editusercontact" id="editusercontact">
																</div>
																<div class="form-group">
																	<label class="form-label">Createdon</label> <input
																		type="text" class="form-control" name="editcreatedon"
																		id="editcreatedon" readonly>
																</div>
															</div>
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Access Type</label> <select
																		class="form-control" id="seledituseraccesstype"
																		name="seledituseraccesstype">
																		<option value="">Select Access Type</option>
																		<option value="S">Superuser</option>
																		<option value="O">Operation</option>
																	</select>
																</div>

																<div class="form-group">
																	<label class="form-label">User Email</label> <input
																		type="text" class="form-control" name="edituseremail"
																		id="edituseremail">
																</div>

																<div class="form-group">
																	<label class="form-label">Status</label> <select
																		class="form-control" id="seledituserstatus"
																		name="seledituserstatus">
																		<option value="">Select Status</option>
																		<option value="A">Active</option>
																		<option value="I">Inactive</option>
																	</select>
																</div>

																<div class="form-group">
																	<label class="form-label">Expiry Date</label>
																	<div class="wd-200 mg-b-30">
																		<div class="input-group">
																			<div class="input-group-prepend">
																				<div class="input-group-text">
																					<i class="fa fa-calendar tx-16 lh-0 op-6"></i>
																				</div>
																			</div>
																			<input class="form-control fc-datepicker"
																				placeholder="MM/DD/YYYY" type="text"
																				name="editdatofexpiry" id="editdatofexpiry">
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>
													<input type="hidden" name="qs" value=""> <input
														type="hidden" name="rules" value=""> <input
														type="hidden" name="hdnlangpref" id="hdnlangpref3"
														value="en"> <input type="hidden"
														name="hdneditusertype" value=""> <input
														type="hidden" name="hdneditstatus" value="">
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
										id="btn-editopsuser">Submit</button>
								</div>
							</div>
						</div>
					</div>

					<!-- End Model -->


					<!-- End Model -->
					<!-- Modal -->
					<div class="modal fade" id="newopsuserModal" tabindex="-1"
						role="dialog" aria-labelledby="largemodal" aria-hidden="true">
						<div class="modal-dialog modal-lg " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="largemodal1">
										Edit <span id="adduserdesc"></span> Profile
									</h5>
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
												<form id="addopsuser-form" method="post">
													<div class="card-body">
														<div class="row">
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">UserId</label> <input
																		type="text" class="form-control" name="addopsuid"
																		id="addopsuid">
																</div>
																<div class="form-group">
																	<label class="form-label">User Name</label> <input
																		type="text" class="form-control" name="addusername"
																		id="addusername">
																</div>
																<div class="form-group">
																	<label class="form-label">Contact</label> <input
																		type="text" class="form-control" name="addusercontact"
																		id="addusercontact">
																</div>
																<!-- <div class="form-group">
																	<label class="form-label">Expiry Date</label>
																	<div class="wd-200 mg-b-30">
																		<div class="input-group">
																			<div class="input-group-prepend">
																				<div class="input-group-text">
																					<i class="fa fa-calendar tx-16 lh-0 op-6"></i>
																				</div>
																			</div>
																			<input class="form-control fc-datepicker" placeholder="MM/DD/YYYY" type="text"
																				name="adddatofexpiry" id="adddatofexpiry">
																		</div>
																	</div>
																</div> -->
															</div>
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Access Type</label> <select
																		class="form-control" id="seladduseraccesstype"
																		name="seladduseraccesstype">
																		<option value="">Select Access Type</option>
																		<option value="S">Superuser</option>
																		<option value="O">Operation</option>
																		<option value="T">Trading Desk Admin</option>
																	</select>
																</div>

																<div class="form-group">
																	<label class="form-label">User Email</label> <input
																		type="text" class="form-control" name="adduseremail"
																		id="adduseremail">
																</div>

																<div class="form-group">
																	<label class="form-label">Status</label> <select
																		class="form-control" id="seladduserstatus"
																		name="seladduserstatus">
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
														type="hidden" name="hdnlangpref" id="hdnlangpref3"
														value="en"> <input type="hidden"
														name="hdnaddusertype" value=""> <input
														type="hidden" name="hdnaddstatus" value="">
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
										id="btn-addopsuser">Submit</button>
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

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>

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
	<script src="assets/js/_ops_sys_manageuserpage.js"></script>



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
	if (arrOpsAllUserDetails!=null)  arrOpsAllUserDetails = null; if (hashStatus!=null)  hashStatus = null;
	if (itstatus!=null)  itstatus = null;if (itaAccessType!=null)  itaAccessType = null; 
	if (hashAccessType!=null)  hashAccessType = null; if (hashAccountStatus!=null)  hashAccountStatus = null;
	if (context!=null)context=null;

}
%>
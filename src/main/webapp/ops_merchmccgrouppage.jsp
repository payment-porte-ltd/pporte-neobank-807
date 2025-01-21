<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*, com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
ArrayList<MccGroup> arrMCCGroup = null;
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}
	
	if (request.getAttribute("merchantmccc") != null) arrMCCGroup = (ArrayList<MccGroup>) request.getAttribute("merchantmccc");

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
<title>Pending Customers</title>

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
							<li class="breadcrumb-item"><a href="#">Manage MccGroup</a></li>
							<li class="breadcrumb-item active" aria-current="page">View
								MCC Group</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- row -->
					<div class="row">
						<div class="col-md-12 col-lg-12">
							<div class="card">
								<div class="card-header">
									<div class="card-title">MccGroup Mcc Group</div>
									<div class="ml-auto">
										<div class="input-group">
											<a class="btn btn-primary text-white mr-2"
												data-toggle="modal" data-target="#newmccModal"> <span>
													<i class="typcn typcn-plus"></i> Add New Mcc Group
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
													<th>MccId</th>
													<th>MCC Group Name</th>
													<th>Mcc From Range</th>
													<th>Mcc To Range</th>
													<th>Mcc Generic</th>
													<th>Createdon</th>
													<th>Action</th>
												</tr>
											</thead>
											<tbody>
												<%
												if (arrMCCGroup != null) {
													for (int i = 0; i < arrMCCGroup.size(); i++) {
												%>
												<tr>
													<td><%=((MccGroup) arrMCCGroup.get(i)).getMccId()%></td>
													<td><%=((MccGroup) arrMCCGroup.get(i)).getMccName()%></td>
													<td><%=((MccGroup) arrMCCGroup.get(i)).getMccFromRange()%></td>
													<td><%=((MccGroup) arrMCCGroup.get(i)).getMccToRange()%></td>
													<td><%=((MccGroup) arrMCCGroup.get(i)).getMccGeneric()%></td>
													<td><%=((MccGroup) arrMCCGroup.get(i)).getCreatedon()%></td>
													<td class="text-center align-middle">
														<div class="btn-group align-top">
															<button class="btn btn-sm btn-primary badge"
																data-target="#user-form-modal" data-toggle="modal"
																type="button"
																onClick="javascript:fnEditMCCGroup('<%=((MccGroup) arrMCCGroup.get(i)).getMccId()%>','<%=((MccGroup) arrMCCGroup.get(i)).getMccName()%>',
																'<%=((MccGroup) arrMCCGroup.get(i)).getMccFromRange()%>','<%=((MccGroup) arrMCCGroup.get(i)).getMccToRange()%>',
																'<%=((MccGroup) arrMCCGroup.get(i)).getMccGeneric()%>','<%=((MccGroup) arrMCCGroup.get(i)).getCreatedon()%>' );return false;">
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
															Mcc Group </span></td>
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
									<h5 class="modal-title" id="largemodal1">Add MCC Group</h5>
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
												<form id="addmccgroup-form" method="post">
													<div class="card-body">
														<div class="row">
															<div class="col-xl-6">
																<!-- <div class="form-group">
																	<label class="form-label">MCC Id</label> 
																	<input type="text" class="form-control" name="mccid" id="mccid">
																</div> -->
																<div class="form-group">
																	<label class="form-label">MCC Group Name</label> <input
																		type="text" class="form-control" name="mccgroupname"
																		id="mccgroupname">
																</div>

																<div class="form-group">
																	<label class="form-label">MCC To Range</label> <input
																		type="text" class="form-control" name="mcctorange"
																		id="mcctorange">
																</div>

															</div>
															<div class="col-xl-6">


																<div class="form-group">
																	<label class="form-label">MCC From Range</label> <input
																		type="text" class="form-control" name="mccfromrange"
																		id="mccfromrange">
																</div>
																<div class="form-group">
																	<label class="form-label">MCC Generic</label> <input
																		type="text" class="form-control" name="mccgeneric"
																		id="mccgeneric">
																</div>
															</div>
														</div>
													</div>
													<input type="hidden" name="qs" value=""> <input
														type="hidden" name="rules" value=""> <input
														type="hidden" name="hdnuserid" value=""> <input
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
										id="btn-addmccgrp">Submit</button>
								</div>
							</div>
						</div>
					</div>

					<!-- End Model -->
					<!-- Modal -->
					<div class="modal fade" id="editmccModal" tabindex="-1"
						role="dialog" aria-labelledby="largemodal" aria-hidden="true">
						<div class="modal-dialog modal-lg " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="largemodal1">Edit MCC Group</h5>
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
												<form id="editmccgroup-from" method="post">
													<div class="card-body">
														<div class="row">
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">MCC Id</label> <input
																		type="text" class="form-control" name="editmccid"
																		id="editmccid" readonly>
																</div>
																<div class="form-group">
																	<label class="form-label">MCC From Range</label> <input
																		type="text" class="form-control"
																		name="editmccfromrange" id="editmccfromrange">
																</div>
																<div class="form-group">
																	<label class="form-label">MCC Generic</label> <input
																		type="text" class="form-control" name="editmccgeneric"
																		id="editmccgeneric">
																</div>

															</div>
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">MCC Group Name</label> <input
																		type="text" class="form-control"
																		name="editmccgroupname" id="editmccgroupname">
																</div>
																<div class="form-group">
																	<label class="form-label">MCC To Range</label> <input
																		type="text" class="form-control" name="editmcctorange"
																		id="editmcctorange">
																</div>
																<div class="form-group">
																	<label class="form-label">Date created</label> <input
																		type="text" class="form-control" name="editcreatedon"
																		id="editcreatedon">
																</div>
															</div>
														</div>
													</div>
													<input type="hidden" name="qs" value=""> <input
														type="hidden" name="rules" value=""> <input
														type="hidden" name="hdnuserid" value=""> <input
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
									<button type="button" class="btn btn-primary" id="btn-editmcc">Submit</button>
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
	<script src="assets/js/_ops_merchmccgrouppage.js"></script>



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
	if(arrMCCGroup!=null)arrMCCGroup=null;if (context!=null)context=null;
}
%>
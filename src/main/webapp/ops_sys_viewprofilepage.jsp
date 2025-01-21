<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.utilities.Utilities, com.pporte.model.*, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
User opsUserDetails = null; ConcurrentHashMap<String, String> hashStatus = null; Iterator<String> itstatus = null; 
Iterator<String> itaAccessType = null; ConcurrentHashMap<String, String> hashAccessType = null;
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}

	if (request.getAttribute("opsuser") !=null) opsUserDetails = (User) request.getAttribute("opsuser");
	
	hashStatus = new ConcurrentHashMap<String, String>();
	hashAccessType = new ConcurrentHashMap<String, String>();
	
	hashStatus.put("A", "Active");
	hashStatus.put("I", "Inactive");
	
	hashAccessType.put("S", "Superuser");
	hashAccessType.put("O", "Operation");
	hashAccessType.put("T", "Trading Desk Admin");
	
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
<title>Operation Profile</title>

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
							<li class="breadcrumb-item"><a href="#">View Profile</a></li>
							<li class="breadcrumb-item active" aria-current="page">View
								Profile</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- row -->
					<div class="row">
						<div class="col-md-12">
							<div class="card">
								<form id="editmerchant-form" method="post">

									<div class="card-header">
										<h3 class="mb-0 card-title">
											<%=hashAccessType.get(opsUserDetails.getAccessType())%>
											Profile
										</h3>
									</div>
									<div class="card-body">
										<div class="row">
											<div class="col-xl-6">
												<div class="form-group">
													<label class="form-label">UserId</label> <input type="text"
														class="form-control" name="opsuid" id="opsuid"
														value="<%=opsUserDetails.getUserId()%>" readonly>
												</div>
												<div class="form-group">
													<label class="form-label">User Name</label> <input
														type="text" class="form-control" name="username"
														id="username" value="<%=opsUserDetails.getUserName()%>"
														readonly>
												</div>
												<div class="form-group">
													<label class="form-label">Contact</label> <input
														type="text" class="form-control" name="usercontact"
														id="usercontact"
														value="<%=opsUserDetails.getUserContact()%>" readonly>
												</div>
												<div class="form-group">
													<label class="form-label">Createdon</label> <input
														type="text" class="form-control" name="createdon"
														id="createdon"
														value="<%=opsUserDetails.getUserCreatedOn()%>" readonly>
												</div>
											</div>


											<div class="col-xl-6">
												<div class="form-group">
													<label class="form-label">User Type</label> <input
														type="text" class="form-control" name="useraccesstype"
														id="useraccesstype"
														value="<%=hashAccessType.get(opsUserDetails.getAccessType())%>"
														readonly>
												</div>
												<div class="form-group">
													<label class="form-label">User Email</label> <input
														type="text" class="form-control" name="useremail"
														id="useremail" value="<%=opsUserDetails.getUserEmail()%>"
														readonly>
												</div>

												<div class="form-group">
													<label class="form-label">Status</label> <input type="text"
														class="form-control" name="userstatus" id="userstatus"
														value="<%=hashStatus.get(opsUserDetails.getUserStatus())%>"
														readonly>
												</div>

												<div class="form-group">
													<label class="form-label">Expiry Date</label> <input
														type="text" class="form-control" name="userstatus"
														id="userstatus"
														value="<%=opsUserDetails.getExpiryDate()%>" readonly>
												</div>

												<div class="form-group">
													<div class="col-xl-6">
														<a class="btn btn-primary text-white mr-2"
															id="btn-verifymerch" data-toggle="modal"
															data-target="#opsEditPrfModal" style="width: 150px;">
															<span> Edit profile </span>
														</a>
													</div>
												</div>

											</div>

										</div>
									</div>
									<input type="hidden" name="qs" value=""> <input
										type="hidden" name="rules" value=""> <input
										type="hidden" name="hdnuserid" value=""> <input
										type="hidden" name="hdnlangpref" id="hdnlangpref3" value="en">
								</form>
							</div>
						</div>
					</div>
					<!-- row -->


					<!-- End Model -->
					<!-- Modal -->
					<div class="modal fade" id="opsEditPrfModal" tabindex="-1"
						role="dialog" aria-labelledby="largemodal" aria-hidden="true">
						<div class="modal-dialog modal-lg " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="largemodal1">
										Edit
										<%=hashAccessType.get(opsUserDetails.getAccessType())%>
										Profile
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
												<form id="editopsprofile-form" method="post">
													<div class="card-body">
														<div class="row">
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">UserId</label> <input
																		type="text" class="form-control" name="editopsuid"
																		id="editopsuid"
																		value="<%=opsUserDetails.getUserId()%>" readonly>
																</div>
																<div class="form-group">
																	<label class="form-label">User Name</label> <input
																		type="text" class="form-control" name="editusername"
																		id="editusername"
																		value="<%=opsUserDetails.getUserName()%>">
																</div>
																<div class="form-group">
																	<label class="form-label">Contact</label> <input
																		type="text" class="form-control"
																		name="editusercontact" id="editusercontact"
																		value="<%=opsUserDetails.getUserContact()%>">
																</div>
																<div class="form-group">
																	<label class="form-label">Createdon</label> <input
																		type="text" class="form-control" name="editcreatedon"
																		id="editcreatedon"
																		value="<%=opsUserDetails.getUserCreatedOn()%>"
																		readonly>
																</div>
															</div>
															<div class="col-xl-6">
																<%-- 	<div class="form-group">
													<label class="form-label">Access Type</label>
														<select class="form-control" id="selaccesstype" name="selaccesstype">
															<% while (itaAccessType.hasNext()){
																String tempAccessType = itaAccessType.next();
																if(tempAccessType.equals(opsUserDetails.getAccessType())){ %>
																	<option value="<%=tempAccessType%>" selected> <%=hashAccessType.get(tempAccessType)%> </option>
															<% }else{ %>
																	<option value="<%=tempAccessType%>" > <%=hashAccessType.get(tempAccessType) %> </option>
														 <% }
														  }%>
														</select> 
												</div> --%>

																<div class="form-group">
																	<label class="form-label">User Type</label> <input
																		type="text" class="form-control"
																		name="edituseraccesstype" id="edituseraccesstype"
																		value="<%=hashAccessType.get(opsUserDetails.getAccessType())%>"
																		readonly>
																</div>
																<div class="form-group">
																	<label class="form-label">User Email</label> <input
																		type="text" class="form-control" name="edituseremail"
																		id="edituseremail"
																		value="<%=opsUserDetails.getUserEmail()%>">
																</div>
																<%-- 	<div class="form-group">
													<label class="form-label">Status</label>
														<select class="form-control" id="selaccesstype" name="selaccesstype">
															<% while (itstatus.hasNext()){
																String tempStatus = itstatus.next();
																if(tempStatus.equals(opsUserDetails.getUserStatus())){ %>
																	<option value="<%=tempStatus%>" selected> <%=hashStatus.get(tempStatus)%> </option>
															<% }else{ %>
																	<option value="<%=tempStatus%>" > <%=hashAccessType.get(tempStatus) %> </option>
														 <% }
														  }%>
														</select>
												</div> --%>

																<div class="form-group">
																	<label class="form-label">Status</label> <input
																		type="text" class="form-control" name="edituserstatus"
																		id="edituserstatus"
																		value="<%=hashStatus.get(opsUserDetails.getUserStatus())%>"
																		readonly>
																</div>
																<div class="form-group">
																	<label class="form-label">Expiry Date</label> <input
																		type="text" class="form-control"
																		name="editdatofexpiry" id="editdatofexpiry"
																		value="<%=opsUserDetails.getExpiryDate()%>" readonly>
																</div>
															</div>
														</div>
													</div>
													<input type="hidden" name="qs" value=""> <input
														type="hidden" name="rules" value=""> <input
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
										id="btn-editopsprf">Submit</button>
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

	<!-- Daterangepicker js-->
	<script
		src="assets/plugins/bootstrap-daterangepicker/daterangepicker.js"></script>

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
	<script src="assets/js/_ops_sys_viewprofilepage.js"></script>

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
	if (opsUserDetails!=null)opsUserDetails=null; if (hashStatus!=null)hashStatus=null;
	if (itstatus!=null)itstatus=null; if (itaAccessType!=null)itaAccessType=null;
	if (hashAccessType!=null)hashAccessType=null;	if (context!=null)context=null;
}
%>
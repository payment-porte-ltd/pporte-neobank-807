<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
Partner partner = null;
String status ="";
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
	}
		
	if (request.getAttribute("profiledetails") != null) partner = (Partner) request.getAttribute("profiledetails");
	if(partner.getStatus().equals("A")){
		status = "Active";
	}else{
		status = "Inactive";
	}
	
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
<title>Profile Page</title>

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
<!---Sweetalert2 css-->
<link href="assets/plugins/sweetalert2/sweetalert2.css" rel="stylesheet" />

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
			<jsp:include page="part_topandleftmenu.jsp" />

			<!-- app-content-->
			<div class="app-content  my-3 my-md-5 toggle-content">
				<div class="side-app" style="height: 89vh;">

					<!-- page-header -->
					<div class="page-header" style="margin: auto">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#">Dashboard</a></li>
							<li class="breadcrumb-item active" aria-current="page">Partner
								Profile</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->

					<!-- row -->
					<div class="row">
						<div class="col-md-12">
							<div class="card card-profile  overflow-hidden">
								<div class="card-body text-center profile-bg">
									<div class=" card-profile">
										<div class="row justify-content-center">
											<div class="">
												<div class="">
													<a href="#"> <img
														src="assets/images/users/female/5.jpg"
														class="avatar-xxl rounded-circle" alt="profile">
													</a>
												</div>
											</div>
										</div>
									</div>
									<h3 class="mt-3 text-white">
										<span id="part_name"><%=partner.getParnerName() %></span>
									</h3>
									<p class="mb-2 text-white">
										<span id="userid"><%=partner.getUserId() %></span>
									</p>
									<a href="#" class="btn btn-info btn-sm"
										onclick="javascript:fnEditProfileButton();return false;"><i
										class="fas fa-pencil-alt" aria-hidden="true"
										data-toggle="modal" data-target="#exampleModal3"></i> Edit
										profile</a>
								</div>
								<div class="card-body">
									<div class="nav-wrapper p-0">
										<ul class="nav nav-pills nav-fill flex-column flex-md-row"
											id="tabs-icons-text" role="tablist">
											<li class="nav-item"><a
												class="nav-link mb-sm-3 mb-md-0 mt-md-2 mt-0 mt-lg-0"
												id="tabs-icons-text-1-tab" data-toggle="tab"
												href="#tabs-icons-text-1" role="tab"
												aria-controls="tabs-icons-text-1" aria-selected="false"><i
													class="fa fa-user mr-2"></i>Partner Details</a></li>

											<li class="nav-item"><a
												class="nav-link mb-sm-0 mb-md-0 mt-md-2 mt-0 mt-lg-0"
												id="tabs-icons-text-3-tab" data-toggle="tab"
												href="#tabs-icons-text-3" role="tab"
												aria-controls="tabs-icons-text-3" aria-selected="false"><i
													class="fa fa-cog mr-2"></i>Security</a></li>
										</ul>
									</div>
								</div>
							</div>
							<div class="card">
								<div class="card-body pb-0">
									<div class="tab-content" id="myTabContent">
										<div class="tab-pane fade active show" id="tabs-icons-text-1"
											role="tabpanel" aria-labelledby="tabs-icons-text-1-tab">
											<div class="table-responsive mb-3">
												<table class="table row table-borderless w-100 m-0 border">
													<tbody class="col-lg-6 p-0">
														<tr>
															<td><strong>Name :</strong> <span id="name"><%=partner.getParnerName() %></span></td>
														</tr>
														<tr>
															<td><strong>Email :</strong> <span id="email"><%=partner.getParnerEmail() %></span></td>
														</tr>
														<tr>
															<td><strong>Contact :</strong> <span id="contact"><%=partner.getParnerContact() %></span></td>
														</tr>

														<tr>
															<td><strong>Status :</strong><span id="status"><%=status %></span></td>
														</tr>
													</tbody>
													<tbody class="col-lg-6 p-0">
														<tr>
															<td><strong>Stellar Account Id :</strong> <span
																id="account_id"><%=partner.getParnerPublicKey() %></span></td>
														</tr>
														<tr>
															<td><strong>Currency :</strong> <span
																id="passportno"><%=partner.getParnerCurrency() %></span></td>
														</tr>
														<tr>
															<td><strong>Location :</strong> <span
																id="span_loaction"><%=partner.getParnerLocation() %></span></td>
														</tr>

													</tbody>
												</table>
											</div>
										</div>

										<div class="tab-pane fade" id="tabs-icons-text-3"
											role="tabpanel" aria-labelledby="tabs-icons-text-3-tab">
											<span style="color: red">Coming Soon</span>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- row end -->


					<!-- Message Modal -->
					<div class="modal fade" id="exampleModal3" tabindex="-1"
						role="dialog" aria-hidden="true">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="example-Modal3">Edit Profile</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">X</span>
									</button>
								</div>
								<div class="modal-body">
									<form method="post" id="edit-form">
										<div class="form-group">
											<label for="recipient-name" class="form-control-label">Name:</label>
											<input type="text" class="form-control" name="editname"
												id="editname">
										</div>

										<div class="form-group">
											<label for="recipient-name" class="form-control-label">Contact:</label>
											<input type="number" class="form-control" name="editcontact"
												id="editcontact">
										</div>
										<div class="form-group">
											<label for="recipient-name" class="form-control-label">Location:</label>
											<input type="text" class="form-control" name="location"
												id="location">
										</div>
										<div class="form-group">
											<label for="recipient-name" class="form-control-label">Stellar
												Account Id:</label> <input type="text" class="form-control"
												name="editpublickey" id="editpublickey">
										</div>
										<input type="hidden" name="qs" value=""> <input
											type="hidden" name="rules" value=""> <input
											type="hidden" name="relno" value="">
									</form>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary"
										data-dismiss="modal">Close</button>
									<button type="button" class="btn btn-primary"
										onclick="javascript:fnSubmitEditDetails();return false;">Submit</button>
								</div>
							</div>
						</div>
					</div>


				</div>
			</div>
			<!-- End app-content-->

		</div>
		<form method="post" id="profile-form">
			<input type="hidden" name="qs" value=""> <input type="hidden"
				name="rules" value=""> <input type="hidden" name="relno"
				value=""> <input type="hidden" name="hdnassetpath" value="">
		</form>
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
	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>


	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>

	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_part_profile_page.js"></script>

	<script>
		
		
		</script>
</body>
</html>


<%
}catch (Exception e){
	out.println ("Exception : "+e.getMessage());
}finally{
	if ( partner!=null); partner=null;if (context!=null)context=null;
}
%>
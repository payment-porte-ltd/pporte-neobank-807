<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
Merchant  user_merchant = null;
String emailId = ""; String merchantCode = ""; 
String userName = "";	String billerRef = null;
try{
    if(request.getAttribute("merchfullprofile")!=null)	
    	user_merchant = (Merchant)request.getAttribute("merchfullprofile"); 
    if(user_merchant!=null){
		emailId = user_merchant.getEmail();
		merchantCode = user_merchant.getMerchantCode();
		billerRef = user_merchant.getBillerRef();
	}
	userName = ((User) session.getAttribute("SESS_USER")).getUserId();
    

%>
<!doctype html>
<html lang="en" dir="ltr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Favicon -->
<link rel="icon" href="assets/images/brand/pporte.png"
	type="image/x-icon" />

<!-- Title -->
<title>Merchant Profile</title>

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

</head>

<body class="app sidebar-mini rtl">

	<!--Global-Loader-->
	<div id="global-loader">
		<img src="assets/images/icons/loader.svg" alt="loader">
	</div>

	<div class="page">
		<div class="page-main">
			<!--app-header and sidebar-->
			<jsp:include page="merch_topandleftmenu.jsp" />
			<!-- app-content-->
			<div class="app-content  my-3 my-md-5 toggle-content">
				<div class="side-app">
					<!-- page-header -->
					<div class="page-header">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#">Profile</a></li>
							<li class="breadcrumb-item active" aria-current="page">View
								Profile</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->

					<!-- row -->
					<div class="row">
						<div class="col-md-12">
							<div class="card card-profile  overflow-hidden">
								<div class="card-body">
									<div class="nav-wrapper p-0">
										<ul class="nav nav-pills nav-fill flex-column flex-md-row"
											id="tabs-icons-text" role="tablist">
											<li class="nav-item"><a
												class="nav-link mb-sm-3 mb-md-0 mt-md-2 mt-0 mt-lg-0 active show"
												id="tabs-icons-text-1-tab" data-toggle="tab"
												href="#tabs-icons-text-1" role="tab"
												aria-controls="tabs-icons-text-1" aria-selected="true"><i
													class="fa fa-home mr-2"></i>Business Details</a></li>
											<li class="nav-item"><a
												class="nav-link mb-sm-3 mb-md-0 mt-md-2 mt-0 mt-lg-0"
												id="tabs-icons-text-2-tab" data-toggle="tab"
												href="#tabs-icons-text-2" role="tab"
												aria-controls="tabs-icons-text-2" aria-selected="false"><i
													class="fa fa-user mr-2"></i>User Details</a></li>
											<li class="nav-item" onclick="javascript:fnGetMerchKycDocs()">
												<a class="nav-link mb-sm-3 mb-md-0 mt-md-2 mt-0 mt-lg-0"
												id="tabs-icons-text-3-tab" data-toggle="tab"
												href="#tabs-icons-text-3" role="tab"
												aria-controls="tabs-icons-text-3" aria-selected="false"><i
													class="fa fa-picture-o mr-2"></i>Business Documents</a>
											</li>
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
															<td><strong>Company Name :</strong> <%=user_merchant.getCompanyName()%></td>
														</tr>
														<tr>
															<td><strong>Physical Address :</strong> <%=user_merchant.getPhysicalAddress()%></td>
														</tr>
														<tr>
															<td><strong>Business Description :</strong> <%=user_merchant.getBusinessDescription()%>
															</td>
														</tr>

													</tbody>
													<tbody class="col-lg-6 p-0">
														<tr>
															<td><strong>Account Number :</strong> <%=user_merchant.getMerchantCode()%></td>
														</tr>
														<tr>
															<td><strong>Business Phone Number :</strong> <%=user_merchant.getBusinessPhoneNumber()%></td>
														</tr>

														<tr>
															<td id="td_mcc_name"></td>
														</tr>


													</tbody>

												</table>
											</div>


										</div>
										<div aria-labelledby="tabs-icons-text-2-tab"
											class="tab-pane fade" id="tabs-icons-text-2" role="tabpanel">
											<div class="row">
												<div class="col-md-12">
													<div class="content content-full-width" id="content">
														<!-- begin profile-content -->
														<div class="profile-content">
															<!-- begin tab-content -->
															<div class="tab-content p-0">
																<!-- begin #profile-friends tab -->
																<div class="tab-pane fade in active show"
																	id="profile-friends">

																	<table
																		class="table row table-borderless w-100 m-0 border">
																		<tbody class="col-lg-6 p-0">
																			<tr>
																				<td><strong>Full Name :</strong> <%=user_merchant.getMerchantName()%></td>
																			</tr>
																			<tr>
																				<td><strong>Phone Number :</strong> <%=user_merchant.getContact()%></td>
																			</tr>

																		</tbody>
																		<tbody class="col-lg-6 p-0">
																			<tr>
																				<td><strong>Merchant Email :</strong> <%=user_merchant.getEmail()%></td>
																			</tr>
																			<tr>
																				<td><strong>National ID Number :</strong> <%=user_merchant.getNationalId()%></td>
																			</tr>
																		</tbody>
																	</table>


																</div>
																<!-- end #profile-friends tab -->
															</div>
															<!-- end tab-content -->
														</div>
														<!-- end profile-content -->
													</div>
												</div>
											</div>
										</div>
										<div class="tab-pane fade" id="tabs-icons-text-3"
											role="tabpanel" aria-labelledby="tabs-icons-text-3-tab">
											<div class="row">
												<div class="col-lg-4 profile-image" id="document_holder">

												</div>

											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- row end -->

				</div>

				<form method="post" id="get-kyc-form">
					<input type="hidden" name="qs" value=""> <input
						type="hidden" name="rules" value="">
				</form>

				<form method="post" id="get-mcc-form">
					<input type="hidden" name="qs" value=""> <input
						type="hidden" name="rules" value="">
				</form>

				<form id="download-form" method="post">
					<input type="hidden" name="qs" value=""> <input
						type="hidden" name="rules" value=""> <input type="hidden"
						name="hdnassetpath" value="">
				</form>

				<!--footer-->

				<!-- End Footer-->

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

	<!-- Rightsidebar js -->
	<script src="assets/plugins/sidebar/sidebar.js"></script>

	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>

	<script src="assets/js/_merch_profile.js"></script>

</body>
</html>

<% 
}catch(Exception e){

}finally{
	if(user_merchant!=null) user_merchant=null;
	if(emailId!=null) emailId=null; if(merchantCode!=null) merchantCode = null; 
	
}

%>

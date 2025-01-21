<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
ArrayList<Merchant> listUsers = null;
ConcurrentHashMap<String, String> hashStatus = null;
ConcurrentHashMap<String, String> hashUserLevel = null;
try{
	if(request.getAttribute("usersdata")!=null)	listUsers = (ArrayList<Merchant>)request.getAttribute("usersdata");
	hashStatus = new ConcurrentHashMap<String, String>();
	hashUserLevel = new ConcurrentHashMap<String, String>();
	hashUserLevel.put("S", "Super User"); hashUserLevel.put("N", "Normal User");
   	hashStatus.put("A", "Active"); hashStatus.put("I", "Inactive");
   	hashStatus.put("V", "Waiting for Verification"); 
%>


<!doctype html>
<html lang="en" dir="ltr">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Favicon -->
<link rel="icon" href="assets/images/brand/pporte.png"
	type="image/x-icon" />

<!-- Title -->
<title>Manage Merchant</title>
<!--Bootstrap.min css-->
<link rel="stylesheet"
	href="assets/plugins/bootstrap/css/bootstrap.min.css">
<!-- Dashboard css -->
<link href="assets/css/ops_style.css" rel="stylesheet" />
<!---Sweetalert2 css-->
<link href="assets/plugins/sweetalert2/sweetalert2.css" rel="stylesheet" />
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


</head>
<body class="app sidebar-mini rtl">
	<!--Global-Loader-->
	<div id="global-loader">
		<img src="assets/images/icons/loader.svg" alt="loader">
	</div>
	<div class="page">
		<div class="page-main">
			<!-- Sidebar menu-->
			<jsp:include page="merch_topandleftmenu.jsp" />
			<!--sidemenu end-->
			<!-- app-content-->
			<div class="app-content  my-3 my-md-5 toggle-content">
				<div class="side-app">
					<!-- page-header -->
					<div class="page-header">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#">Profile</a></li>
							<li class="breadcrumb-item active" aria-current="page">Manage
								Users</li>
						</ol>
						<!-- End breadcrumb -->
						<div class="ml-auto">
							<div class="input-group">
								<a class="btn btn-primary text-white mr-2" id="btn-add-branch"
									data-toggle="modal" data-target="#exampleModal3"> <span>
										<i class="fa fa-plus"></i> Add User
								</span>
								</a>
							</div>
						</div>
					</div>
					<!-- End page-header -->

					<!-- row -->
					<div class="row">
						<div class="col-lg-12">
							<div class="e-panel card">
								<div class="card-header">
									<h4>Users</h4>
								</div>

								<div class="card-body">
									<div class="e-table">
										<div class="table-responsive table-lg">
											<table class="table table-bordered">
												<thead>
													<tr>
														<th>Full Name</th>
														<th>Email</th>
														<th>User Access Level</th>
														<th>designation</th>
														<th>Status</th>
														<th class="text-center">Actions</th>
													</tr>
												</thead>
												<tbody>
													<% if(listUsers!=null){
															for(int i=0;i<listUsers.size();i++){
														%>
													<tr>
														<td class="text-nowrap align-middle"><%= ((Merchant)listUsers.get(i)).getMerchantName() %></td>
														<td class="text-nowrap align-middle"><%= ((Merchant)listUsers.get(i)).getEmail() %></td>
														<td class="text-nowrap align-middle"><%=hashUserLevel.get(((Merchant)listUsers.get(i)).getHierchachyLevel())%></td>
														<td class="text-nowrap align-middle"><%= ((Merchant)listUsers.get(i)).getMerchUserDesignation() %></td>
														<td class="text-nowrap align-middle"><%=hashStatus.get(((Merchant)listUsers.get(i)).getStatus())%></td>
														<td class="text-center align-middle">
															<div class="btn-group align-top">
																<button class="btn btn-sm btn-primary badge"
																	onClick="javascript:fnEditRow('<%= ((Merchant)listUsers.get(i)).getMerchantName() %>','<%= ((Merchant)listUsers.get(i)).getEmail() %>','<%= ((Merchant)listUsers.get(i)).getContact() %>', '<%= ((Merchant)listUsers.get(i)).getNationalId() %>', '<%= ((Merchant)listUsers.get(i)).getMerchUserDesignation() %>', '<%= ((Merchant)listUsers.get(i)).getStatus() %>', '<%= ((Merchant)listUsers.get(i)).getHierchachyLevel() %>')"
																	type="button">Edit</button>
															</div>
														</td>
													</tr>
													<% }
														}else{ %>
													<tr class="text-nowrap align-middle">
														<td><span>No data Present</span></td>
													</tr>
													<% } %>
												</tbody>
											</table>
										</div>

									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- row  end -->
				</div>
			</div>
			<!-- End app-content-->
			<form method="post" id="get-page-form">
				<input type="hidden" name="qs" value=""> <input
					type="hidden" name="rules" value="">
			</form>

		</div>
	</div>
	<!-- Add User Modal -->
	<div class="modal fade" id="exampleModal3" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="example-Modal3">Add User</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form id="add-user-form" method="post">
						<div class="form-group">
							<label class="form-label">Full Name</label> <input type="text"
								name="fname" id="fname" class="form-control"
								name="example-text-input" placeholder="Enter Full Name">
						</div>
						<div class="form-group">
							<label class="form-label">Email</label> <input type="email"
								name="uemail" id="uemail" class="form-control"
								name="example-text-input" placeholder="Enter Email">
						</div>
						<div class="form-group">
							<label class="form-label">Phone Number</label> <input
								type="number" name="phoneno" id="phoneno" class="form-control"
								name="example-text-input" placeholder="Enter Phone Number">
						</div>
						<div class="form-group">
							<label class="form-label">National identification number</label>
							<input type="number" name="idno" id="idno" class="form-control"
								name="example-text-input"
								placeholder="Enter National identification number">
						</div>
						<div class="form-group">
							<label class="form-label">Designation</label> <input type="text"
								name="designation" id="designation" class="form-control"
								name="example-text-input" placeholder="Enter Designation">
						</div>
						<div class="form-group">
							<label>User Level</label> <select
								class="form-control select2 custom-select" id="seluserlevel"
								name="seluserlevel"
								data-placeholder="Select merchant pricing plan">
								<option value="" disabled selected>Select User Level</option>
								<option value="S">Super User</option>
								<option value="N">Normal User</option>
							</select>
						</div>
						<input type="hidden" name="qs" value=""> <input
							type="hidden" name="rules" value="">
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary"
						onclick="javascript:addMerchantUser()">Save</button>
				</div>
			</div>
		</div>
	</div>
	<!-- Edit User Modal -->
	<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="example-Modal3">Edit User</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form id="edit-user-form" method="post">
						<div class="form-group">
							<label class="form-label">Full Name</label> <input type="text"
								name="efullname" id="efullname" class="form-control"
								name="example-text-input" placeholder="Enter Full Name">
						</div>
						<div class="form-group">
							<label class="form-label">Email</label> <input type="email"
								name="eemail" id="eemail" class="form-control"
								name="example-text-input" placeholder="Enter Email" readonly>
						</div>
						<div class="form-group">
							<label class="form-label">Phone Number</label> <input
								type="number" name="ephoneno" id="ephoneno" class="form-control"
								name="example-text-input" placeholder="Enter Phone Number">
						</div>
						<div class="form-group">
							<label class="form-label">National identification number</label>
							<input type="number" name="eidno" id="eidno" class="form-control"
								name="example-text-input"
								placeholder="Enter National identification number">
						</div>
						<div class="form-group">
							<label class="form-label">Designation</label> <input type="text"
								name="edesignation" id="edesignation" class="form-control"
								name="example-text-input" placeholder="Enter Designation">
						</div>
						<div class="form-group">
							<label>User Level</label> <select
								class="form-control select2 custom-select" id="estatus"
								name="estatus" data-placeholder="Select merchant pricing plan">
								<option value="A">Active</option>
								<option value="I">Inactive</option>
							</select>
						</div>
						<div class="form-group">
							<label>User Level</label> <select
								class="form-control select2 custom-select" id="euseraccess"
								name="euseraccess"
								data-placeholder="Select merchant pricing plan">
								<option value="S">Super User</option>
								<option value="N">Normal User</option>
							</select>
						</div>
						<input type="hidden" name="qs" value=""> <input
							type="hidden" name="rules" value="">
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary"
						onclick="javascript:editMerchantUser()">Save</button>
				</div>
			</div>
		</div>
	</div>
	<div id="error-msg-add-user-page" style="display: none;">
		<span id="add-user-data-validation-error-fname">Please enter
			Full Name</span> <span id="add-user-data-validation-error-fname-length">Full
			name must consist of at least 5 characters</span> <span
			id="add-user-data-validation-error-uemail">Please enter Email</span>
		<span id="add-user-data-validation-error-uemail-email">Please
			enter valid Email</span> <span id="add-user-data-validation-error-phoneno">Please
			enter Phone number</span> <span
			id="add-user-data-validation-error-phoneno-length">Phone
			number must consist of at least 8 numbers</span> <span
			id="add-user-data-validation-error-designation">Please enter
			designation</span> <span
			id="add-user-data-validation-error-designation-length">Phone
			designation must consist of at least 3 characters</span> <span
			id="add-user-data-validation-error-idno">Please enter National
			identification number</span> <span
			id="add-user-data-validation-error-idno-length">National
			identification number must consist of at least 5 numbers</span> <span
			id="add-user-data-validation-error-seluserlevel">Please select
			user level</span>
	</div>
	<div id="error-msg-edit-user-page" style="display: none;">
		<span id="edit-user-data-validation-error-fname">Please enter
			Full Name</span> <span id="edit-user-data-validation-error-fname-length">Full
			name must consist of at least 5 characters</span> <span
			id="edit-user-data-validation-error-uemail">Please enter Email</span>
		<span id="edit-user-data-validation-error-uemail-email">Please
			enter valid Email</span> <span id="edit-user-data-validation-error-phoneno">Please
			enter Phone number</span> <span
			id="edit-user-data-validation-error-phoneno-length">Phone
			number must consist of at least 8 numbers</span> <span
			id="edit-user-data-validation-error-designation">Please enter
			designation</span> <span
			id="edit-user-data-validation-error-designation-length">Phone
			designation must consist of at least 3 characters</span> <span
			id="edit-user-data-validation-error-idno">Please enter
			National identification number</span> <span
			id="edit-user-data-validation-error-idno-length">National
			identification number must consist of at least 5 numbers</span> <span
			id="edit-user-data-validation-error-seluserlevel">Please
			select user level</span>
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
	<!-- Data tables js-->
	<script src="assets/plugins/datatable/jquery.dataTables.min.js"></script>
	<script src="assets/plugins/datatable/dataTables.bootstrap4.min.js"></script>
	<script src="assets/plugins/datatable/datatable.js"></script>
	<script src="assets/plugins/datatable/datatable-2.js"></script>
	<script src="assets/plugins/datatable/dataTables.responsive.min.js"></script>
	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>


	<!-- Custom js-->
	<script src="assets/js/_merch_manage_user.js"></script>
</body>
</html>

<% 
}catch(Exception e){

}finally{
	if(listUsers!=null) listUsers=null;
	if(hashStatus!=null) hashStatus=null;
	if(hashUserLevel!=null) hashUserLevel=null;
}

%>
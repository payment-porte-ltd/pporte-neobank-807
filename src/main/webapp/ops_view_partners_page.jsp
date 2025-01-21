<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
List<Partner> listPartners = null;
ConcurrentHashMap<String, String> hashStatus = null;
List<AssetCoin> digitalCurrencies = null;
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}
	
	if(request.getAttribute("arrpartnerslist")!=null)	listPartners = (ArrayList<Partner>)request.getAttribute("arrpartnerslist");
	hashStatus = new ConcurrentHashMap<String, String>();
   	hashStatus.put("A", "Active"); hashStatus.put("I", "Inactive");
   	if(request.getAttribute("digitalcurrencies")!=null)
		digitalCurrencies = (List<AssetCoin> )request.getAttribute("digitalcurrencies");
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
<link rel="shortcut icon" type="image/x-icon"
	href="assets/images/brand/pporte.png" />
<!-- Title -->
<title>View Partners</title>
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
			<!-- Sidebar menu-->
			<jsp:include page="ops_topandleftmenu.jsp" />
			<!--sidemenu end-->
			<!-- app-content-->
			<div class="app-content  my-3 my-md-5 toggle-content">
				<div class="side-app">
					<!-- page-header -->
					<div class="page-header">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#">Ops Remittance</a></li>
							<li class="breadcrumb-item active" aria-current="page">View
								Partners</li>
						</ol>
						<!-- End breadcrumb -->
						<div class="ml-auto">
							<div class="input-group">
								<a class="btn btn-primary text-white mr-2" id="add_partner"
									data-toggle="modal" data-target="#exampleModal3"> <span>
										<i class="fa fa-plus"></i> Add Partner
								</span>
								</a>
							</div>
						</div>
					</div>
					<!-- End page-header -->

					<!-- row -->
					<div class="row">
						<div class="col-md-12 col-lg-12">
							<div class="card">
								<div class="card-header">
									<div class="card-title">Partners</div>
								</div>
								<div class="card-body">
									<div class="table-responsive">
										<table id="example3"
											class="table table-striped table-bordered text-nowrap">
											<thead>
												<tr>
													<th class="wd-15p">Name</th>
													<th class="wd-15p">UserId</th>
													<th class="wd-15p">Email</th>
													<th class="wd-20p">Contact</th>
													<th class="wd-15p">Location</th>
													<th class="wd-15p">Currency</th>
													<th class="wd-15p">Status</th>
													<th class="wd-15p">Created On</th>
													<th class="wd-15p">Action</th>

												</tr>
											</thead>
											<tbody>
												<% if(listPartners!=null){
													for(int i=0;i<listPartners.size();i++){
											%>
												<tr>
													<td><%= ((Partner)listPartners.get(i)).getParnerName() %></td>
													<td><%= ((Partner)listPartners.get(i)).getUserId() %></td>
													<td><%= ((Partner)listPartners.get(i)).getParnerEmail() %></td>
													<td><%= ((Partner)listPartners.get(i)).getParnerContact() %></td>
													<td><%= ((Partner)listPartners.get(i)).getParnerLocation() %></td>
													<td><%= ((Partner)listPartners.get(i)).getParnerCurrency() %></td>
													<td><%=hashStatus.get(((Partner)listPartners.get(i)).getStatus())%></td>
													<td><%= ((Partner)listPartners.get(i)).getCreatedOn() %></td>
													<td class="text-center align-middle">
														<div class="btn-group align-top">
															<button class="btn btn-sm btn-primary badge"
																onClick="javascript:fnEditRow('<%= ((Partner)listPartners.get(i)).getUserId() %>','<%= ((Partner)listPartners.get(i)).getParnerName() %>',
															'<%= ((Partner)listPartners.get(i)).getParnerEmail() %>', '<%= ((Partner)listPartners.get(i)).getParnerContact() %>',
															'<%= ((Partner)listPartners.get(i)).getParnerLocation() %>', '<%= ((Partner)listPartners.get(i)).getParnerCurrency() %>',
															'<%= ((Partner)listPartners.get(i)).getParnerPublicKey() %>', 
															'<%= ((Partner)listPartners.get(i)).getStatus() %>' )"
																type="button">Edit</button>
														</div>
													</td>
												</tr>

												<% }
											}else{ %>
												<tr>
													<td><span>No data Present</span></td>
												</tr>
												<% } %>

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
				</div>
			</div>
			<!-- End app-content-->
			<form method="post" id="get-add-branch-form">
				<input type="hidden" name="qs" value=""> <input
					type="hidden" name="rules" value="">
			</form>
		</div>
	</div>
	<!-- Add Partners Modal -->
	<div class="modal fade" id="exampleModal3" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="example-Modal3">Add Partner</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form id="add-partners-form" method="post">
						<div class="form-group">
							<label class="form-label">Full Name</label> <input type="text"
								name="fname" id="fname" class="form-control"
								placeholder="Enter Full Name">
						</div>
						<div class="form-group">
							<label class="form-label">Email</label> <input type="email"
								name="uemail" id="uemail" class="form-control"
								placeholder="Enter Email">
						</div>
						<div class="form-group">
							<label class="form-label">Phone Number</label> <input
								type="number" name="phoneno" id="phoneno" class="form-control"
								placeholder="Enter Phone Number">
						</div>
						<div class="form-group">
							<label class="form-label">Location</label> <input type="text"
								name="location" id="location" class="form-control"
								placeholder="Enter Location">
						</div>
						<!-- <div class="form-group">
								<label class="form-label">Public Key</label>
								<input type="text" name="publickey" id="publickey" class="form-control"  placeholder="Enter Public Key">
							</div> -->
						<div class="form-group">
							<label>Select Currency</label> <select
								class="form-control select2 custom-select" id="addselcurrency"
								name="addselcurrency" data-placeholder="Currency">
								<option value="" disabled selected>Select Currency</option>
								<% if(digitalCurrencies!=null){%>
								<% for (int i=0; i<digitalCurrencies.size();i++){ %>
								<option
									value="<%=((AssetCoin)digitalCurrencies.get(i)).getAssetCode()%>"><%=((AssetCoin)digitalCurrencies.get(i)).getAssetDescription()%></option>
								<%}  %>
								<%	 } %>
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
						onclick="javascript:addPatner()">Save</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Edit Partners Modal -->
	<div class="modal fade" id="exampleModal4" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="example-Modal3">Edit Partner</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form id="edit-patner-form" method="post">
						<div class="form-group">
							<label class="form-label">Full Name</label> <input type="text"
								name="efname" id="efname" class="form-control"
								placeholder="Enter Full Name">
						</div>
						<div class="form-group">
							<label class="form-label">Email</label> <input type="email"
								name="euemail" id="euemail" class="form-control"
								placeholder="Enter Email" readonly="readonly">
						</div>
						<div class="form-group">
							<label class="form-label">Phone Number</label> <input
								type="number" name="ephoneno" id="ephoneno" class="form-control"
								placeholder="Enter Phone Number">
						</div>
						<div class="form-group">
							<label class="form-label">Location</label> <input type="text"
								name="elocation" id="elocation" class="form-control"
								placeholder="Enter Location">
						</div>
						<!-- <div class="form-group">
								<label class="form-label">Public Key</label>
								<input type="text" name="epublickey" id="epublickey" class="form-control" placeholder="Enter Public Key">
							</div> -->
						<div class="form-group">
							<label>Select Currency</label> <select
								class="form-control select2 custom-select" id="selcurrency"
								name="selcurrency" data-placeholder="Currency">
								<option value="" disabled selected>Select Currency</option>
								<% if(digitalCurrencies!=null){%>
								<% for (int i=0; i<digitalCurrencies.size();i++){ %>
								<option
									value="<%=((AssetCoin)digitalCurrencies.get(i)).getAssetCode()%>"><%=((AssetCoin)digitalCurrencies.get(i)).getAssetDescription()%></option>
								<%}  %>
								<%	 } %>
							</select>
						</div>
						<div class="form-group">
							<label>Status</label> <select
								class="form-control select2 custom-select" id="status"
								name="status" data-placeholder="Status">
								<option value="" disabled selected>Select status</option>
								<option value="A">Active</option>
								<option value="I">Inactive</option>
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
						onclick="javascript:editPartner()">Save</button>
				</div>
			</div>
		</div>
	</div>
	<form method="post" id="get-page-form">
		<input type="hidden" name="qs" value=""> <input type="hidden"
			name="rules" value="">
	</form>
	<!-- End Page -->
	<!-- Back to top -->
	<a href="#top" id="back-to-top"><i class="fa fa-angle-up"></i></a>
	<!-- Jquery js-->
	<script src="assets/js/vendors/jquery-3.2.1.min.js"></script>
	<!--Bootstrap.min js-->
	<script src="assets/plugins/bootstrap/popper.min.js"></script>
	<script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>
	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>

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
	<script src="assets/js/_ops_view_partners_page.js"></script>
</body>
</html>

<% 
}catch(Exception e){

}finally{
	if(listPartners!=null) listPartners=null;
	if(hashStatus!=null) hashStatus=null;
	if(digitalCurrencies!=null) digitalCurrencies=null;
	if (context!=null)context=null;
}

%>
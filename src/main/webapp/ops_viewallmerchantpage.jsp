<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*, com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%

ArrayList<Merchant> arrMerchants = null; HashMap<String,String> hashMCCGroup = null;  ConcurrentHashMap<String, String> hashStatus = null; 
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}
	
	if(request.getAttribute("allmerchants")!=null)	arrMerchants = (ArrayList<Merchant>)request.getAttribute("allmerchants");
	if (request.getAttribute("hashmccgroup") != null)  hashMCCGroup = (HashMap<String,String>) request.getAttribute("hashmccgroup");
	
	hashStatus = new ConcurrentHashMap<String, String>();
	
	hashStatus.put("A", "Active");		
	hashStatus.put("I", "Inactive");		
	hashStatus.put("V", "Verification");
	hashStatus.put("P", "Pending");
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
<title>Pending Merchant</title>

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
							<li class="breadcrumb-item"><a href="#">Dashboard</a></li>
							<li class="breadcrumb-item active" aria-current="page">Pending
								Merchants</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- row -->
					<div class="row">
						<div class="col-md-12 col-lg-12">
							<div class="card">
								<div class="card-header">
									<div class="card-title">Pending Merchants</div>
								</div>
								<div class="card-body">
									<div class="table-responsive">
										<table id="pendingmerchtable"
											class="table table-striped table-bordered text-nowrap w-100">

											<thead>
												<tr>
													<th>MerchantCode</th>
													<th>MerchantRef</th>
													<th>Company Name</th>
													<th>MCC Category</th>
													<th>PhoneNumber</th>
													<!-- 													 <th>Description</th>
 -->
													<th>Status</th>

													<!-- <th>Address</th>
													<th>Merchant Type</th>
													<th>Description</th>
													 -->
													<th>Createdon</th>
													<!-- 													<th>Expiry</th>
 -->
													<th>Action</th>
												</tr>
											</thead>
											<tbody>
												<% if( arrMerchants!=null){
													for(int i=0;i<arrMerchants.size();i++){       
												%>
												<tr>
													<td><%=((Merchant)arrMerchants.get(i)).getMerchantCode()%>
													</td>
													<td><%=((Merchant)arrMerchants.get(i)).getMerchRef()%>
													</td>
													<td><%=((Merchant)arrMerchants.get(i)).getCompanyName()%>
													</td>
													<td><%=hashMCCGroup.get(((Merchant)arrMerchants.get(i)).getMccCategoryId())%>
													</td>
													<td><%=((Merchant)arrMerchants.get(i)).getBusinessPhoneNumber()%>
													</td>
													<%-- 														<td><%=((Merchant)arrMerchants.get(i)).getBusinessDescription()%> </td>
 --%>
													<td><%=hashStatus.get(((Merchant)arrMerchants.get(i)).getStatus())%>
													</td>
													<%-- <td><%=((Merchant)arrMerchants.get(i)).getPhysicalAddress() %> </td>
														<td><%=((Merchant)arrMerchants.get(i)).getMerchantType()%> </td>
														<td><%=((Merchant)arrMerchants.get(i)).getBusinessDescription()%> </td>
														--%>
													<%-- 													<td><%=((Merchant)arrMerchants.get(i)).getExpiryDate()%> </td>
 --%>
													<td><%=((Merchant)arrMerchants.get(i)).getCreatedOn()%>
													</td>
													<td class="text-center align-middle">
														<div class="btn-group align-top">
															<button class="btn btn-sm btn-primary badge"
																type="button"
																onClick="javascript:fnEditMerchant('<%=((Merchant) arrMerchants.get(i)).getMerchantCode()%>' );return false;">
																<i class="fa fa-view"></i>View
															</button>
														</div>
													</td>

													<% }
												}else{ %>
												
												<tr>
													<td colspan="9"><span id="ops_all_bin_list_errormsg1">No
															Merchants Availabe </span></td>
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
					<form method="post" id="pendingmerch-form">
						<input type="hidden" name="qs" value=""> <input
							type="hidden" name="rules" value=""> <input type="hidden"
							name="hdnmerchantcode" value=""> <input type="hidden"
							name="hdnlangpref" id="hdnlangpref3" value="en">
					</form>

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
	<script src="assets/js/_ops_viewallmerchpage.js"></script>

	<!-- Data tables js-->
	<script src="assets/plugins/datatable/jquery.dataTables.min.js"></script>
	<script src="assets/plugins/datatable/dataTables.bootstrap4.min.js"></script>
	<script src="assets/plugins/datatable/datatable.js"></script>
	<script src="assets/plugins/datatable/datatable-2.js"></script>
	<script src="assets/plugins/datatable/dataTables.responsive.min.js"></script>
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
	if(arrMerchants !=null) arrMerchants=null;if (context!=null)context=null;
	
}
%>
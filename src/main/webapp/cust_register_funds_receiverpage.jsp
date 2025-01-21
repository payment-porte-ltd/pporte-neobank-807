<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
ArrayList<Customer> arrCustomer=null;
String relationshipNo=null;
ServletContext context = null;
String langPref=null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callException(request, response, context,"Session has expired, please login again.");
		return;
	}
	if (request.getAttribute("searchedcustomer") != null) arrCustomer = (ArrayList<Customer>) request.getAttribute("searchedcustomer");
	relationshipNo= ((User)session.getAttribute("SESS_USER")).getRelationshipNo();
	if(request.getAttribute("langpref")!=null) langPref = (String)request.getAttribute("langpref");
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
<title>Customer Register Receiver</title>

<!--Bootstrap.min css-->
<link rel="stylesheet"
	href="assets/plugins/bootstrap/css/bootstrap.min.css">

<!-- Dashboard css -->
<link href="assets/css/style.css" rel="stylesheet" />

<!-- Custom scroll bar css-->
<link href="assets/plugins/scroll-bar/jquery.mCustomScrollbar.css"
	rel="stylesheet" />

<!-- Sidemenu css -->
<link href="assets/plugins/toggle-sidebar/sidemenu.css" rel="stylesheet" />

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

<body class="app sidebar-mini rtl" onload="fnGetRelno()">

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
			<jsp:include page="cust_topandleftmenu.jsp" />

			<!-- app-content-->
			<div class="app-content  my-3 my-md-5 toggle-content">
				<div class="side-app" style="height: 89vh;">

					<!-- page-header -->
					<div class="page-header" style="margin: auto">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#"><span
									id="breadcrumb-item-send-money">Send Money</span></a></li>
							<li class="breadcrumb-item active" aria-current="page"><span
								id="breadcrumb-item-register-receiver">Register Receiver</span></li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->

					<!-- row -->
					<div class="row">
						<div class="col-md-12">
							<div class="card">
								<div class="card-header">
										<h4>
											<span id="spn-register-receiver">Register Receiver</span>
										</h4>
								</div>
								<div class="card-body pb-0">
									<div class="tab-content" id="myTabContent">
										<div class="tab-pane fade active show" id="tabs-icons-text-1"
											role="tabpanel" aria-labelledby="tabs-icons-text-1-tab">
											<form method="post" id="register_receiver_form">
												<div class="row justify-content-start">
													<div class="col col-lg-4">
														<div class="form-group">
															<input type="text" class="form-control"
																name="input_register_receiver"
																id="input_register_receiver">
														</div>
													</div>
													<div class="col-md-auto">
														<button class="btn new-default-btn" id="btn_search_customer"
															type="submit">
															<span id="search-btn-label">Search</span>
														</button>
													</div>
												</div>
												<input type="hidden" name="qs" value=""> <input
													type="hidden" name="rules" value=""> <input
													type="hidden" name="relno" value="">
													<input type="hidden" name="hdnlang" value="">
											</form>
											<div class="table-responsive">
												<table id="pendingmerchtable"
													class="table table-striped table-bordered text-nowrap w-100">
													<thead>
														<tr>
															<th><span id="name-label">Name</span></th>
															<th><span id="email-label">Email</span></th>
															<th><span id="wallet-label">Wallet ID</span></th>
															<th><span id="action-label">Action</span></th>
														</tr>
													</thead>
													<tbody>
														<%
																if (arrCustomer != null) {
																	NeoBankEnvironment.setComment(3, "(jsp)"," is arrCustomer size is  "+arrCustomer.size());
																	for (int i = 0; i < arrCustomer.size(); i++) {
																		NeoBankEnvironment.setComment(3, "(jsp)"," is arrCustomer size is  "+((Customer) arrCustomer.get(i)).getCustomerName());
																%>
														<tr>

															<td><%=((Customer) arrCustomer.get(i)).getCustomerName()%></td>
															<td><%=((Customer) arrCustomer.get(i)).getEmail()%></td>
															<td><%=((Customer) arrCustomer.get(i)).getWalletId()%></td>
															<td class="text-center align-middle">
																<div class="btn-group align-top">
																	<button class="btn btn-sm theme-btn-sm badge" id="btnAdd"
																		type="button"
																		onClick="javascript:fnAddReceiver('<%=((Customer) arrCustomer.get(i)).getWalletId()%>','<%=((Customer) arrCustomer.get(i)).getRelationshipNo()%>');return false;">
																		<i class="fa fa-edit"></i>Add
																	</button>
																</div>
															</td>
														</tr>

														<%
																	 }
																	} else {
																	%>

														<tr>
															<td colspan="9"><span id="no-regiestred-user-label">No
																	registered Users</span></td>
														</tr>
														<%
																}
																%>

													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- row end -->



				</div>
			</div>
			<!-- End app-content-->
			<div id="error-msg-login-page" style="display: none;">
				<span id="data-validation-error-swal-header">Oops..</span> <span
					id="data-validation-error-failed-to-get-details">Failed to
					get details</span> <span id="data-validation-problem-with-connection">Problem
					with connection</span> <span id="data-validation-check-your-data">Please
					check your data</span> <span id="data-validation-email-id">Please
					enter email id</span>
					<!-- <span id="lang_def"></span> -->
			</div>

		</div>
		<form method="post" id="receiver_page_form">
			<input type="hidden" name="qs" value=""> <input type="hidden"
				name="rules" value=""> <input type="hidden"
				name="hdnwalletid" value=""> <input type="hidden"
				name="hdnreceiverrelno" value=""> <input type="hidden"
				name="hdnlang" >
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
	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>

	<!-- Custom js-->
	<script src="assets/js/_cust_register_funds_receiverpage.js"></script>

	<script>
		var relno='';
		function fnGetRelno(){
			 relno = '<%=relationshipNo%>';
			 fnCallCustDetails();
		}
		$( document ).ready(function() {
			console.log('language is ','<%=langPref%>')
			fnChangePageLanguage('<%=langPref%>');
		});
		
		</script>
</body>
</html>


<%
}catch (Exception e){
	out.println ("Exception : "+e.getMessage());
}finally{
	if ( relationshipNo!=null); relationshipNo=null;
	if (context!=null)context=null;
	if (langPref!=null) langPref=null;
}
%>
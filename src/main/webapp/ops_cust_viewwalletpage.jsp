<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*, com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils, com.pporte.utilities.*, java.util.concurrent.*, java.util.*"%>
<%

ArrayList<Wallet> arrWallet = null; 
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}
	
	if(request.getAttribute("custwallet")!=null)	arrWallet = (ArrayList<Wallet>)request.getAttribute("custwallet");

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
<title>View Customer Wallets</title>

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
			<jsp:include page="ops_topandleftmenu.jsp" />

			<!-- app-content-->
			<div class="app-content  my-3 my-md-5 toggle-content">
				<div class="side-app" style="height: 89vh;">

					<!-- page-header -->
					<div class="page-header">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#">Manage Customer</a></li>
							<li class="breadcrumb-item"><a href="#">View Customer</a></li>
							<li class="breadcrumb-item active" aria-current="page">View
								Wallet</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->

					<% if(arrWallet!=null) {%>
					<div class="row">

						<% for (int i=0; i<arrWallet.size();i++){ %>
						<div class="col-4">
							<div class="card">
								<div class="card-header">
									<div class="card-title">View Wallet</div>
								</div>
								<div class="card-body">
									<div class="form-group">
										<label><span class="help" id="cust_profview_input1"><b>Wallet
													ID</b> </span></label>
										<div class="col-xs-12">
											<span> <%= ((Wallet)arrWallet.get(i)).getWalletId() %>
											</span>
										</div>
									</div>
									<div class="form-group">
										<label><span class="help" id="cust_profview_input1"><b>Wallet
													Description</b></span></label>
										<div class="col-xs-12">
											<span><%= ((Wallet)arrWallet.get(i)).getWalletDesc() %></span>
										</div>
									</div>
									<div class="form-group">
										<label><span class="help" id="cust_profview_input1"><b>Wallet
													Amount</b></span></label>
										<div class="col-xs-12">
											<span><%= ((Wallet)arrWallet.get(i)).getCurrencyId() + " " + Utilities.getMoneyinDecimalFormat( ((Wallet)arrWallet.get(i)).getCurrentBalance() )  %></span>
										</div>
									</div>
									<div class="form-group">
										<label><span class="help" id="cust_profview_input1">
												<b>Last Updated</b>
										</span></label>
										<div class="col-xs-12">
											<span><%= ((Wallet)arrWallet.get(i)).getLastUpdated() %></span>
										</div>
									</div>
								</div>
								<div class="card-footer">
									<%-- 	<button class="btn btn-primary" onclick="javascript : fnViewTransactions('<%=((Wallet)arrWallet.get(i)).getWalletId() %>'); return false;"> Transactions</button> --%>

									<!-- <div class="col-12 align-self-center p-l-0">
                                         <a href="#" class="btn btn-info" onclick="javascript : fnViewTransactions('2108192687939135'); return false;"><span id="cust_walmain_button1">Transactions</span></a> 
                                    </div> -->
								</div>


							</div>
						</div>

						<%} %>
					</div>
					<!-- End of row -->
					<%}else {%>
					<div class="row">
						<div class="col-lg-12 col-md-6">
							<div class="card">
								<div class="card-body">
									<!-- Row -->
									<div class="row">
										<div class="col-8">
											<h4 class="">
												<span id="cust_walmain_errtext3">No Wallet Present</span>
											</h4>
											<p class="card-text">
												<span id="cust_walmain_errtext4">Currently the
													customer do not have any wallet present</span>
											</p>
											<br>
										</div>

									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- End of row -->
					<%} %>

					<div class="row" id="divtxnwalletview" style="display: none">
						<div class="col-lg-12 col-md-6">
							<div class="card">
								<div class="card-header">
									<div class="card-title">Wallet Transactions</div>
								</div>
								<div class="card-body">
									<!-- Row -->
									<div id="divtabletransactions"></div>
								</div>
							</div>
						</div>
					</div>

					<!-- <div class="row"  id="divtxnwalletview">
					
	                    Column
	                    <div class="col-lg-12">
	                        <div class="card">
	                            <div class="card-body">
                                     <div id="divtabletransactions" >
									 </div>
	                            </div>
	                        </div>
	                    </div>
	
	                </div> -->
					<!-- End of row -->

					<form method="post" id="pendingcust-form">
						<input type="hidden" name="qs" value=""> <input
							type="hidden" name="rules" value=""> <input type="hidden"
							name="hdncustomercode" value=""> <input type="hidden"
							name="hdnlangpref" id="hdnlangpref3" value="en">
					</form>
					<form method="post" id="walltxn-form">
						<input type="hidden" name="qs" value=""> <input
							type="hidden" name="rules" value=""> <input type="hidden"
							name="hdnwalletid" value=""> <input type="hidden"
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
	<script src="assets/js/_ops_cust_viewwalletpage.js"></script>

	<!-- Data tables js-->
	<script src="assets/plugins/datatable/jquery.dataTables.min.js"></script>
	<script src="assets/plugins/datatable/dataTables.bootstrap4.min.js"></script>
	<script src="assets/plugins/datatable/datatable.js"></script>
	<script src="assets/plugins/datatable/datatable-2.js"></script>
	<script src="assets/plugins/datatable/dataTables.responsive.min.js"></script>

	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>
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
	if(arrWallet !=null) arrWallet=null;if (context!=null)context=null;
}
%>
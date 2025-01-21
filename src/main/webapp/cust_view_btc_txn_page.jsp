<%@page import="com.google.gson.JsonArray"%>
<%@page import="com.google.gson.JsonObject"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>

<%
JsonObject accountDetails = null; 
JsonArray txnArray = null;
String langPref=null;
ArrayList<BTCTransction> arrBTCTransaction  = null;
String finalBalance = null;
String totalReceived = null;
String totalSent = null;
String totalTransactions = null;
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callException(request, response, context,"Session has expired, please login again.");
		return;
	}
	if (request.getAttribute("transactions") != null) arrBTCTransaction =  (ArrayList<BTCTransction>) request.getAttribute("transactions");
	if (request.getAttribute("accountDetails") != null) accountDetails =  (JsonObject) request.getAttribute("accountDetails");
	if(request.getAttribute("langpref")!=null) langPref = (String)request.getAttribute("langpref");
	if(accountDetails!=null){
		finalBalance = accountDetails.get("final_balance").getAsString();
		totalReceived = accountDetails.get("total_received").getAsString();
		totalSent = accountDetails.get("total_sent").getAsString();
		totalTransactions = accountDetails.get("final_n_tx").getAsString();
	}
	
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
<title>View Transactions</title>

<!--Bootstrap.min css-->
<link rel="stylesheet"
	href="assets/plugins/bootstrap/css/bootstrap.min.css">

<!-- Dashboard css -->
<link href="assets/css/style.css" rel="stylesheet" />

<!-- Custom scroll bar css-->
<link href="assets/plugins/scroll-bar/jquery.mCustomScrollbar.css"
	rel="stylesheet" />

<!---Sweetalert2 css-->
<link href="assets/plugins/sweetalert2/sweetalert2.css" rel="stylesheet" />

<!-- Sidemenu css -->
<link href="assets/plugins/toggle-sidebar/sidemenu.css" rel="stylesheet" />

<!--Daterangepicker css-->
<link
	href="assets/plugins/bootstrap-daterangepicker/daterangepicker.css"
	rel="stylesheet" />

<!-- Sidebar Accordions css -->
<link href="assets/plugins/accordion1/css/easy-responsive-tabs.css"
	rel="stylesheet">

<!-- Date Picker css-->
<link href="assets/plugins/date-picker/spectrum.css" rel="stylesheet" />
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


<!-- Custom css -->
<link href="assets/css/style2.css" rel="stylesheet" />
<!-- Custom css -->
<link href="assets/css/style5.css" rel="stylesheet">
<!-- Responsive css -->
<link href="assets/css/responsive.css" rel="stylesheet" />


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
			<jsp:include page="cust_topandleftmenu.jsp" />
			<!-- app-content-->
			<div class="app-content  my-3 my-md-5 toggle-content">
				<div class="side-app">
					<!-- page-header -->
					<div class="page-header">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#"><span
									id="breadcrumb-item-btc">Bitcoin</span></a></li>
							<li class="breadcrumb-item active" aria-current="page"><span
								id="breadcrumb-item-view-txn">View Transactions</span></li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->

					<!-- row -->
					<div class="row row-cards">

						<div class="col-lg-12">
							<div class="card">
								<div class="card-header">
									<h4>
										<span id="card_txn_header">Bitcoin Account Details</span>
									</h4>
								</div>
								<%if(accountDetails!=null){ %>
								<div class="row"
									style="margin-left: 10px; margin-right: 10px; margin-top: 10px; margin-bottom: -27px;">
									<div class="col-sm-12 col-lg-6 col-xl-3">
										<div class="card">
											<div class="row">
												<div class="col-4">
													<div class="feature">
														<div class="fa-stack fa-lg fa-2x icon bg-orange">
															<i class="fa fa-btc fa-stack-1x text-white"></i>
														</div>
													</div>
												</div>
												<div class="col-8">
													<div class="card-body p-3  d-flex">
														<div>
															<p class="text-muted mb-1" id="p_final_balance">Final
																Balance</p>
															<h4 class="mb-0 text-dark"><%=finalBalance+" BTC" %></h4>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<!-- col end -->
									<div class="col-sm-12 col-lg-6 col-xl-3">
										<div class="card">
											<div class="row">
												<div class="col-4">
													<div class="feature">
														<div class="fa-stack fa-lg fa-2x icon bg-green">
															<i class="fa fa-arrow-circle-up fa-stack-1x text-white"></i>
														</div>
													</div>
												</div>
												<div class="col-8">
													<div class="card-body p-3  d-flex">
														<div>
															<p class="text-muted mb-1" id="p_total_received">Total
																Received</p>
															<h4 class="mb-0 text-dark"><%=totalReceived+" BTC" %></h4>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<!-- col end -->
									<div class="col-sm-12 col-lg-6 col-xl-3">
										<div class="card">
											<div class="row">
												<div class="col-4">
													<div class="feature">
														<div class="fa-stack fa-lg fa-2x icon bg-danger">
															<i class="fa fa-arrow-circle-down fa-stack-1x text-white"></i>
														</div>
													</div>
												</div>
												<div class="col-8">
													<div class="card-body p-3  d-flex">
														<div>
															<p class="text-muted mb-1" id="p_total_sent">Total
																Sent</p>
															<h4 class="mb-0 text-dark"><%=totalSent+" BTC" %></h4>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<!-- col end -->
									<div class="col-sm-12 col-lg-6 col-xl-3">
										<div class="card">
											<div class="row">
												<div class="col-4">
													<div class="feature">
														<div class="fa-stack fa-lg fa-2x icon bg-blue">
															<i class="fa fa-exchange fa-stack-1x text-white"></i>
														</div>
													</div>
												</div>
												<div class="col-8">
													<div class="card-body p-3  d-flex">
														<div>
															<p class="text-muted mb-1" id="p_no_of_txn">Number of
																Transactions</p>
															<h4 class="mb-0 text-dark"><%=totalTransactions %></h4>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<!-- col end -->
								</div>
								<%}else{ %>
								<p id="p_no_btc_ac">You don't have BTC Account</p>
								<%} %>
								<div class="card-body">
									<div class="table-responsive" id="transactiontable">
										<table id="stellar_txns"
											class="table table-striped table-bordered text-nowrap">
											<thead>
												<tr>
													<th><span id="th_txn_hash">Transaction Hash</span></th>
													<th><span id="th_txn_date">date</span></th>
													<th><span id="th_txn_status">Status</span></th>
													<th><span id="th_txn_amount">Transaction
															Amount</span></th>
													<th><span id="th_txn_fee">Transaction Fee</span></th>
													<th><span id="th_txn_src_account">Source
															account</span></th>
													<th><span id="th_txn_dest_acc">Destination
															account</span></th>
													<th><span id="th_txn_confirmation">Confirmations</span>
													</th>
												</tr>
											</thead>
											<tbody>
												<% if( arrBTCTransaction!=null){
												for(int i=0;i<arrBTCTransaction.size();i++){       
												%>
												<%if(((BTCTransction)arrBTCTransaction.get(i)).getTxnMode().equals("D")){ %>
												<tr>
													<td><%=((BTCTransction)arrBTCTransaction.get(i)).getHash()%>
													</td>
													<td><%=((BTCTransction)arrBTCTransaction.get(i)).getTxnDateTime()%>
													</td>
													<%if(((BTCTransction)arrBTCTransaction.get(i)).getConfirmedStatus().equals("CONFIRMED")){ %>
													<td class="text-success"><%=((BTCTransction)arrBTCTransaction.get(i)).getConfirmedStatus()%>
													</td>
													<%}else{ %>
													<td class="text-danger"><%=((BTCTransction)arrBTCTransaction.get(i)).getConfirmedStatus()%>
													</td>
													<%}%>
													<td class="text-danger"><%="-"+((BTCTransction)arrBTCTransaction.get(i)).getTxnAmaount()+" "%>BTC
													</td>
													<td><%=((BTCTransction)arrBTCTransaction.get(i)).getTxnFees()+" "%>BTC
													</td>
													<td></td>
													<td><%=((BTCTransction)arrBTCTransaction.get(i)).getDestinatioAddress()%>
													</td>
													<td><%=((BTCTransction)arrBTCTransaction.get(i)).getConfirmations()%>
													</td>
												</tr>
												<%}else{ %>
												<tr>
													<td><%=((BTCTransction)arrBTCTransaction.get(i)).getHash()%>
													</td>
													<td><%=((BTCTransction)arrBTCTransaction.get(i)).getTxnDateTime()%>
													</td>
													<%if(((BTCTransction)arrBTCTransaction.get(i)).getConfirmedStatus().equals("CONFIRMED")){ %>
													<td class="text-success"><%=((BTCTransction)arrBTCTransaction.get(i)).getConfirmedStatus()%>
													</td>
													<%}else{ %>
													<td class="text-danger"><%=((BTCTransction)arrBTCTransaction.get(i)).getConfirmedStatus()%>
													</td>
													<%}%>
													<td class="text-success"><%="+"+((BTCTransction)arrBTCTransaction.get(i)).getTxnAmaount()+" "%>BTC
													</td>
													<td><%=((BTCTransction)arrBTCTransaction.get(i)).getTxnFees()+" "%>BTC
													</td>
													<td><%=((BTCTransction)arrBTCTransaction.get(i)).getSourceAddress()%>
													</td>
													<td></td>
													<td><%=((BTCTransction)arrBTCTransaction.get(i)).getConfirmations()%>
													</td>

												</tr>
												<%} %>

												<% }
												}else{ %>
												<tr>
													<td colspan="9"><span id="no_txn_details_available">No
															available Transactions Details </span></td>
												</tr>
												<% } %>

											</tbody>
										</table>
									</div>
								</div>
							</div>

						</div>
					</div>
					<!-- row end -->

				</div>


				<form method="post" id="get-txn-form">
					<input type="hidden" name="qs" value=""> <input
						type="hidden" name="rules" value=""> <input type="hidden"
						name="link" value=""> <input type="hidden" name="hdnlang"
						>
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

	<!--Moment js-->
	<script src="assets/plugins/moment/moment.min.js"></script>

	<!--Side-menu js-->
	<script src="assets/plugins/toggle-sidebar/sidemenu.js"></script>

	<!-- Custom scroll bar js-->
	<script
		src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>
	<!-- Rightsidebar js -->
	<script src="assets/plugins/sidebar/sidebar.js"></script>
	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>
	<script src="assets/js/select2.js"></script>

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>

	<!-- Data tables js-->
	<script src="assets/plugins/datatable/jquery.dataTables.min.js"></script>
	<script src="assets/plugins/datatable/dataTables.bootstrap4.min.js"></script>
	<script src="assets/plugins/datatable/datatable-2.js"></script>
	<script src="assets/plugins/datatable/dataTables.responsive.min.js"></script>

	<!-- Sidebar Accordions js -->
	<script src="assets/plugins/accordion1/js/easyResponsiveTabs.js"></script>

	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_cust_view_btc_txn_page.js"></script>
	<script>
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
	if(arrBTCTransaction !=null) arrBTCTransaction = null; 
	if(accountDetails !=null) accountDetails = null; 
	if(finalBalance !=null) finalBalance = null; 
	if(totalReceived !=null) totalReceived = null; 
	if(totalSent !=null) totalSent = null; 
	if(totalTransactions !=null) totalTransactions = null; 
	if (context!=null)context=null;
}
%>
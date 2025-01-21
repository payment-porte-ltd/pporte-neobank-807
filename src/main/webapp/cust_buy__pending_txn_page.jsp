<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
List<Transaction> transactions = null;
ConcurrentHashMap<String, String> hashStatus = null;
ServletContext context = null;
String langPref=null;

try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callException(request, response, context,"Session has expired, please login again.");
		return;
	}
	if(request.getAttribute("transactions")!=null)	transactions = (List<Transaction>)request.getAttribute("transactions");
	if(request.getAttribute("langpref")!=null) langPref = (String)request.getAttribute("langpref");
	hashStatus = new ConcurrentHashMap<String, String>();
	hashStatus.put("P", "Processed"); hashStatus.put("N", "Pending");
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
<title>View Pending Transactions</title>
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
			<!-- Sidebar menu-->
			<jsp:include page="cust_topandleftmenu.jsp" />
			<!--sidemenu end-->
			<!-- app-content-->
			<div class="app-content  my-3 my-md-5 toggle-content">
				<div class="side-app">
					<!-- page-header -->
					<div class="page-header">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#"><span
									id="breadcrumb-item-digital-assets">Digital Assets</span></a></li>
							<li class="breadcrumb-item active" aria-current="page"><span
								id="breadcrumb-item-pending-txn">View Pending
									Transactions</span></li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->

					<!-- row -->
					<div class="row">
						<div class="col-md-12 col-lg-12">
							<div class="card">
								<div class="card-header">
									<div class="card-title">
										<span id="card-title-buy-pending-txn">Buy Coins
											Pending Transactions</span>
									</div>
								</div>
								<div class="card-body">
									<div class="table-responsive">
										<table id="pendingtxntable"
											class="table card-table table-vcenter text-nowrap table-nowrap">
											<thead>
												<tr>
													<th class="wd-15p"><span id="th-txn-code">Transaction
															Code</span></th>
													<th class="wd-15p"><span id="th-txn-amount">Amount</span></th>
													<th class="wd-15p"><span id="th-asset-code">Asset
															Code</span></th>
													<th class="wd-20p"><span id="th-txn-status">Status</span></th>
													<th class="wd-15p"><span id="th-txn-date">Date</span></th>
												</tr>
											</thead>
											<tbody>
												<% if(transactions!=null){
													for(int i=0;i<transactions.size();i++){
											%>
												<tr>
													<td><%= ((Transaction)transactions.get(i)).getTxnCode() %></td>
													<td><%= ((Transaction)transactions.get(i)).getCoinAmount() %></td>
													<td><%= ((Transaction)transactions.get(i)).getAssetCode() %></td>
													<td><%=hashStatus.get(((Transaction)transactions.get(i)).getStatus())%></td>
													<td><%= ((Transaction)transactions.get(i)).getTxnDateTime() %></td>
												</tr>

												<% }
											}else{ %>
												<tr>
													<td><span id="spn-no-data-present">No data
															Present</span></td>
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
					type="hidden" name="rules" value=""> <input type="hidden"
					name="hdnlang" >
			</form>
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
	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>
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
	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_cust_buy__pending_txn_page.js"></script>
	<script>
			$( document ).ready(function() {
				console.log('language is ','<%=langPref%>')
				fnChangePageLanguage('<%=langPref%>');
			});
		</script>
</body>
</html>

<% 
}catch(Exception e){
	out.println ("Exception : "+e.getMessage());
}finally{
	if(transactions!=null) transactions=null;
	if(hashStatus!=null) hashStatus=null;
	if (context!=null)context=null;
	if (langPref!=null) langPref=null;
	
}

%>
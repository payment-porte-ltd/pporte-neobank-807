<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
String publicKey=null;
ArrayList<AssetAccount> accountBalances = null;
List<Transaction> transactions = null;
List<Transaction> completeTransactions = null;
String partnerID=null;
ConcurrentHashMap<String,String> hashSumOfTxns = null; 
String [] txnWalletValuesArray = null; String [] txnWalletDatesArray = null;
String name=null;
String firstName=null;
String [] fullName=null;
ServletContext context = null;
try{
	/*
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
	}
	*/
	
	if(request.getAttribute("publickey")!=null) publicKey = (String)request.getAttribute("publickey");
	if(request.getAttribute("externalwallets")!=null) accountBalances = (ArrayList<AssetAccount> )request.getAttribute("externalwallets");

	partnerID= ((User)session.getAttribute("SESS_USER")).getUserId();
	name=((User)session.getAttribute("SESS_USER")).getUserName();
	fullName=name.split(" ");
	firstName=fullName[0];
	if(request.getAttribute("lastwalletxns")!=null)	hashSumOfTxns = (ConcurrentHashMap<String,String>)request.getAttribute("lastwalletxns");
	if(request.getAttribute("transactions")!=null)	transactions = (List<Transaction>)request.getAttribute("transactions");
	if(request.getAttribute("completedtransactions")!=null)	completeTransactions = (List<Transaction>)request.getAttribute("completedtransactions");
	
	if(hashSumOfTxns!=null && hashSumOfTxns.size()>0) {
		
		txnWalletDatesArray = new String [hashSumOfTxns.size()];
		txnWalletValuesArray  = new String [hashSumOfTxns.size()];
		// dateoftxns and amount in array
		Iterator<String> itKeysDateOfTxn = hashSumOfTxns.keySet().iterator();
		int count = 0;
		while(itKeysDateOfTxn.hasNext()) {
			String tempKeyDate = itKeysDateOfTxn.next();
			txnWalletDatesArray[count] = tempKeyDate;
			txnWalletValuesArray[count] = hashSumOfTxns.get(tempKeyDate);
			count++;
		}
		
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
<title>Dashboard Page</title>

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

<!-- Owl Theme css-->
<link href="assets/plugins/owl-carousel/owl.carousel.css"
	rel="stylesheet">

<!-- Custom css -->
<link href="assets/css/style2.css" rel="stylesheet" />
<!-- Responsive css -->
<link href="assets/css/responsive.css" rel="stylesheet" />

<!-- Jvectormap css -->
<link href="assets/plugins/jvectormap/jquery-jvectormap-2.0.2.css"
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
			<jsp:include page="part_topandleftmenu.jsp" />

			<!-- app-content-->
			<div class="app-content  my-3 my-md-5 toggle-content">
				<div class="side-app" style="height: 89vh;">

					<!-- page-header -->
					<div class="page-header" style="margin: auto">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item header-text-color"><a href="#">Welcome</a></li>
							<!-- 	<li class="breadcrumb-item active" aria-current="page">Home</li> -->
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!--Row-->



					<div class="row">
						<div class="col-md-12">
							<h2 class="sub-text-color">
								<span id="greeting"></span>, <span class="header-text-color"><%=firstName%></span>
							</h2>
							<div class="owl-carousel  profiles owl-theme mb-5">
								<%if(accountBalances !=null){ 
										 for(int i=0; i< accountBalances.size(); i++){
										   if(accountBalances.get(i).getAssetCode().equals("PORTE")){%>
								<div class="card">
									<div class="row">
										<div class="col-4">
											<div class="feature">
												<div class="fa-stack fa-lg fa-2x icon pporte-main-bg">
													<img class="centre-icon"
														src="assets/images/crypto/porte.svg" alt="PORTE"
														height="55" width="55">
												</div>
											</div>
										</div>
										<div class="col-8">
											<div class="card-body p-3  d-flex">
												<div>
													<div class="item-card2-text">
														<a href="" class="text-dark"><h5
																class="font-weight-semibold mb-1">PORTE</h5></a>
													</div>
													<p class="mb-0"><%=accountBalances.get(i).getAssetBalance()+" "%>PORTE
													</p>
												</div>
											</div>
										</div>
									</div>
								</div>
								<%}else if(accountBalances.get(i).getAssetCode().equals("USDC")){ %>
								<div class="card">
									<div class="row">
										<div class="col-4">
											<div class="feature">
												<div class="fa-stack fa-lg fa-2x icon pporte-main-bg">
													<img class="centre-icon"
														src="assets/images/crypto/usdc.png" alt="USDC" height="55"
														width="55">
												</div>
											</div>
										</div>
										<div class="col-8">
											<div class="card-body p-3  d-flex">
												<div>
													<div class="item-card2-text">
														<a href="" class="text-dark"><h5
																class="font-weight-semibold mb-1">USDC</h5></a>
													</div>
													<p class="mb-0"><%=accountBalances.get(i).getAssetBalance()+" "%>USDC
													</p>
												</div>
											</div>
										</div>
									</div>
								</div>
								<% }else if(accountBalances.get(i).getAssetCode().equals("VESL")){%>
								<div class="card">
									<div class="row">
										<div class="col-4">
											<div class="feature">
												<div class="fa-stack fa-lg fa-2x icon pporte-main-bg">
													<img class="centre-icon"
														src="assets/images/crypto/vessel.png" alt="VESL"
														height="55" width="55">
												</div>
											</div>
										</div>
										<div class="col-8">
											<div class="card-body p-3  d-flex">
												<div>
													<div class="item-card2-text">
														<a href="" class="text-dark"><h5
																class="font-weight-semibold mb-1">VESL</h5></a>
													</div>
													<p class="mb-0"><%=accountBalances.get(i).getAssetBalance()+" "%>VESL
													</p>
												</div>
											</div>
										</div>
									</div>
								</div>
								<% }else if(accountBalances.get(i).getAssetCode().equals("XLM")){%>
								<div class="card">
									<div class="row">
										<div class="col-4">
											<div class="feature">
												<div class="fa-stack fa-lg fa-2x icon pporte-main-bg">
													<img class="centre-icon" src="assets/images/crypto/xlm.svg"
														alt="XLM" height="55" width="55">
												</div>
											</div>
										</div>
										<div class="col-8">
											<div class="card-body p-3  d-flex">
												<div>
													<div class="item-card2-text">
														<a href="" class="text-dark"><h5
																class="font-weight-semibold mb-1">XLM</h5></a>
													</div>
													<p class="mb-0"><%=accountBalances.get(i).getAssetBalance()+" "%>XLM
													</p>
												</div>
											</div>
										</div>
									</div>
								</div>

								<% }
									     }
									  } %>
								<%if(accountBalances ==null){ %>
								<div class="item">
									<div class="card overflow-hidden border p-0 mb-0">
										<div class="card-body">
											<div class="mt-3">
												<div class="text-center">
													<div class="item-card2-text">
														<a href="" class="text-dark"><h5
																class="font-weight-semibold mb-1">No wallet present</h5></a>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<%} %>

							</div>
						</div>
					</div>
					<div class="row">

						<div class="col-xl-12 col-lg-12 col-md-12">
							<div class="card">
								<div class="card-header">
									<div class="card-title">View Pending Transactions</div>
								</div>
								<div class="card-body">
									<div class="table-responsive">
										<table id="example3"
											class="table table-striped table-bordered text-nowrap w-100">
											<thead>
												<tr>
													<th class="wd-15p">Transaction Reference</th>
													<th class="wd-20p">Destination Amount</th>
													<th class="wd-15p">Destination Currency</th>
													<th class="wd-15p">Created On</th>
													<th class="wd-15p">Action</th>

												</tr>
											</thead>
											<tbody>
												<% if(transactions!=null){
													for(int i=0;i<transactions.size();i++){
											%>
												<tr>
													<td><%= ((Transaction)transactions.get(i)).getSystemReferenceInt() %></td>

													<td><%= ((Transaction)transactions.get(i)).getDestinationAmount() %></td>
													<td><%= ((Transaction)transactions.get(i)).getDestinationAssetCode() %></td>
													<td><%= ((Transaction)transactions.get(i)).getTxnDateTime() %></td>
													<td class="text-center align-middle">
														<div class="btn-group align-top">
															<button class="btn btn-sm btn-primary badge"
																onClick="javascript:fnViewMorePendingTxns();return false;"
																type="button">View More</button>
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
						</div>
						<div class="col-xl-12 col-lg-12 col-md-12">
							<div class="card">
								<div class="card-header">
									<div class="card-title">Complete Transaction</div>
								</div>
								<div class="card-body">
									<div class="table-responsive">
										<table id="example5"
											class="table table-striped table-bordered text-nowrap w-100">
											<thead>
												<tr>
													<th class="wd-15p">Transaction Reference</th>
													<th class="wd-20p">Destination Amount</th>
													<th class="wd-15p">Destination Currency</th>
													<th class="wd-15p">Created On</th>
													<th class="wd-15p">Action</th>

												</tr>
											</thead>
											<tbody>
												<% if(completeTransactions!=null){
													for(int i=0;i<completeTransactions.size();i++){
											%>
												<tr>
													<td><%= ((Transaction)completeTransactions.get(i)).getSystemReferenceInt() %></td>

													<td><%= ((Transaction)completeTransactions.get(i)).getDestinationAmount() %></td>
													<td><%= ((Transaction)completeTransactions.get(i)).getDestinationAssetCode() %></td>
													<td><%= ((Transaction)completeTransactions.get(i)).getTxnDateTime() %></td>
													<td class="text-center align-middle">
														<div class="btn-group align-top">
															<button class="btn btn-sm btn-primary badge"
																onClick="javascript:fnViewCompleteTxn();return false;"
																type="button">View More</button>
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
						</div>

					</div>
				</div>
			</div>
			<!-- End app-content-->
			<form method="post" id="get-txn-form">
				<input type="hidden" name="qs" value=""> <input
					type="hidden" name="rules" value="">
			</form>
			<form method="post" id="dispute-form">
				<input type="hidden" name="qs" value=""> <input
					type="hidden" name="rules" value=""> <input type="hidden"
					name="relno" value=""> <input type="hidden" name="hdnreqid"
					value="">
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

	<!-- Sidebar Accordions js -->
	<script src="assets/plugins/accordion1/js/easyResponsiveTabs.js"></script>

	<!-- Custom scroll bar js-->
	<script
		src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>

	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>

	<!--Owl Carousel js -->
	<script src="assets/plugins/owl-carousel/owl.carousel.js"></script>
	<script src="assets/plugins/owl-carousel/owl-main.js"></script>

	<!-- Charts js-->
	<script src="assets/plugins/chart/chart.bundle.js"></script>
	<script src="assets/plugins/chart/utils.js"></script>

	<!-- ECharts js -->
	<script src="assets/plugins/echarts/echarts.js"></script>
	<!-- Custom js-->
	<!--Peitychart js-->
	<script src="assets/plugins/peitychart/jquery.peity.min.js"></script>
	<script src="assets/plugins/peitychart/peitychart.init.js"></script>

	<!-- Custom-charts js-->
	<script src="assets/js/_part_dashboard.js"></script>
	<script src="assets/js/_part_dashboard_page_graphs.js"></script>
	<!-- Data tables js-->
	<!-- 		<script src="assets/plugins/datatable/jquery.dataTables.min.js"></script>
		<script src="assets/plugins/datatable/dataTables.bootstrap4.min.js"></script>
		<script src="assets/plugins/datatable/datatable.js"></script>
		<script src="assets/plugins/datatable/datatable-2.js"></script>
		<script src="assets/plugins/datatable/dataTables.responsive.min.js"></script> -->

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
	if(publicKey !=null) publicKey =null;
	if(accountBalances !=null) accountBalances =null; if(partnerID !=null) partnerID =null;
	if (firstName!=null) firstName=null;if (hashSumOfTxns!=null) hashSumOfTxns=null;
	if (txnWalletValuesArray!=null) txnWalletValuesArray=null;if (txnWalletDatesArray!=null) txnWalletDatesArray=null;
	if (name!=null) name=null; if (fullName!=null) fullName=null;
	if (transactions!=null) transactions=null;if (context!=null)context=null;
}
%>
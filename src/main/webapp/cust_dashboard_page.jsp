<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page import="com.pporte.*,com.pporte.model.*, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
String publicKey=null;
Wallet wallet = null;
ArrayList<AssetAccount> accountBalances = null;
String relationshipNo=null;
ConcurrentHashMap<String,String> hashSumOfTxns = null; 
String [] txnWalletValuesArray = null; String [] txnWalletDatesArray = null;
String name=null;
String firstName=null;
String [] fullName=null;
String langPref=null;


try{
	if(request.getAttribute("wallet")!=null) wallet = (Wallet)request.getAttribute("wallet");
	if(request.getAttribute("publickey")!=null) publicKey = (String)request.getAttribute("publickey");
	if(request.getAttribute("langpref")!=null) langPref = (String)request.getAttribute("langpref");
	if(request.getAttribute("externalwallets")!=null) accountBalances = (ArrayList<AssetAccount> )request.getAttribute("externalwallets");
	relationshipNo= ((User)session.getAttribute("SESS_USER")).getRelationshipNo();
	name=((User)session.getAttribute("SESS_USER")).getName();
	fullName=name.split(" ");
	firstName=fullName[0];
	
	if(request.getAttribute("lastwalletxns")!=null)	hashSumOfTxns = (ConcurrentHashMap<String,String>)request.getAttribute("lastwalletxns");

	
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
		<!-- Porte Icon -->
		<link rel="icon" href="assets/images/brand/pporte.png" type="image/x-icon"/>
		<link rel="shortcut icon" type="image/x-icon" href="assets/images/brand/pporte.png" />

		<!-- Title -->
		<title>Dashboard Page</title>

		<!--Bootstrap.min css-->
		<link rel="stylesheet" href="assets/plugins/bootstrap/css/bootstrap.min.css">

		<!-- Dashboard css -->
		<link href="assets/css/style.css" rel="stylesheet" />

		<!-- Custom scroll bar css-->
		<link href="assets/plugins/scroll-bar/jquery.mCustomScrollbar.css" rel="stylesheet" />

		<!-- Sidemenu css -->
		<link href="assets/plugins/toggle-sidebar/sidemenu.css" rel="stylesheet" />

		<!-- Sidebar Accordions css -->
		<link href="assets/plugins/accordion1/css/easy-responsive-tabs.css" rel="stylesheet">

		<!-- Rightsidebar css -->
		<link href="assets/plugins/sidebar/sidebar.css" rel="stylesheet">

		<!---Font icons css-->
		<link href="assets/plugins/iconfonts/plugin.css" rel="stylesheet" />
		<link href="assets/plugins/iconfonts/icons.css" rel="stylesheet" />
		<link  href="assets/fonts/fonts/font-awesome.min.css" rel="stylesheet">
		
		<!-- Owl Theme css-->
		<link href="assets/plugins/owl-carousel/owl.carousel.css" rel="stylesheet">
		
		<!-- Custom css -->
		<link href="assets/css/style2.css" rel="stylesheet" />
 		<!-- Responsive css -->
 		<link href="assets/css/responsive.css" rel="stylesheet" />
		
		<!-- Jvectormap css -->
        <link href="assets/plugins/jvectormap/jquery-jvectormap-2.0.2.css" rel="stylesheet" />

	</head>

	<body class="app sidebar-mini rtl" onload="fnGetWeeklyTxns('<%=relationshipNo%>');">

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
					<div class="side-app" style="height:89vh;">

						<!-- page-header -->
						<div class="page-header" style="margin:auto">
							<ol class="breadcrumb"><!-- breadcrumb -->
								<li class="breadcrumb-item header-text-color"><a href="#"> <span id="welcome_label">Welcome</span> </a></li>
							<!-- 	<li class="breadcrumb-item active" aria-current="page">Home</li> -->
 							</ol><!-- End breadcrumb -->
						</div>
						<!-- End page-header -->
						<!--Row-->
						<%if (wallet !=null && accountBalances==null) {%>
						<div class="row">
							<div class="col-md-12">
								<h2 ><span id="greeting" style="color:#27A89D"></span>, <span class="text-white"><%=firstName%></span></h2>
								
								</div>
								<div class="col-lg-6 col-12 d-block" style="margin-bottom:12px;">
								 <div class="fiat-card-wal-page">
					                   <div class="asset_balance_wal_page"><%=wallet.getCurrentBalance() +" "+wallet.getAssetCode()%></div>
					               </div>
								</div>	
						</div>
								<%} else {%>
						
						
						<div class="row">
							<div class="col-md-12">
								<h2 class="sub-text-color"><span id="greeting" style="color:#27A89D"></span>, <span class="text-white"><%=firstName%></span></h2>
								
								<div class="owl-carousel  profiles owl-theme mb-5">
									<%if(wallet !=null){ %>
										<div class="fiat-card">
					                  		 <div class="asset_balance"><%=wallet.getCurrentBalance()%> <strong><%=wallet.getAssetCode()%></strong></div>
					                    </div>
									<%} %>
									
									<%if(accountBalances !=null){ 
										 for(int i=0; i< accountBalances.size(); i++){
										   if(accountBalances.get(i).getAssetCode().equals("PORTE")){%>	
											<div class="porte-card">
						                  		 <div class="asset_balance"><%=accountBalances.get(i).getAssetBalance()+" "%><strong>PORTE</strong></div>
						                    </div>
										<%}else if(accountBalances.get(i).getAssetCode().equals("USDC")){ %>
											<div class="usdc-card">
						                  		 <div class="asset_balance"><%=accountBalances.get(i).getAssetBalance()+" "%><strong>USDC</strong></div>
						                    </div>										
										<% }else if(accountBalances.get(i).getAssetCode().equals("VESL")){%>		
											<div class="vesl-card">
						                  		 <div class="asset_balance"><%=accountBalances.get(i).getAssetBalance()+" "%><strong>VESL</strong></div>
						                    </div>	
										<% }else if(accountBalances.get(i).getAssetCode().equals("XLM")){%>
											<div class="stellar-card">
						                  		 <div class="asset_balance"><%=accountBalances.get(i).getAssetBalance()+" "%><strong>XLM</strong></div>
						                    </div>	
										
										  <% }else if(accountBalances.get(i).getAssetCode().equals("BTC")){ %>
											<div class="btc-card">
					                  		 <div class="asset_balance"><%=accountBalances.get(i).getAssetBalance()+" "%><strong>BTCX</strong></div>
					                    </div>
									<%} %>
										   
									     <%}
									  } %>
									  
									  <%if(accountBalances ==null && wallet== null){ %>
									  		<div class="item">
												<div class="card overflow-hidden border p-0 mb-0">
													<div class="card-body">
														<div class="mt-3">
															<div class="text-center">
																<div class="item-card2-text">
																	<a href="" class="text-dark"><h5 class="font-weight-semibold mb-1">No wallet present</h5></a>
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
						<%} %>
						<div class="row">
						
						<div class="col-xl-6 col-lg-6 col-md-12" style=" margin-bottom: -267px;">
								<div class="porte-card-content" style="height:57%; overflow:auto;">
									<div class="card-header custom-header">
										<div>
											<h3 class="card-title" > <span id="last_five_transaction">Last 5 Transactions</span></h3>
											<h6 class="card-subtitle"><span id="fiat_wallet_label"> Fiat Wallet</span></h6>
										</div>
									</div>
									<div class="card-body">
										<div class="table-responsive" id="wallet_txn"></div>
									</div>
								</div>
							</div>
							<div class="col-xl-6 col-lg-6 col-md-12">
								<div class="porte-card-content">
									<div class="card-header custom-header">
										<div>
											<h3 class="card-title"><span id="fiat_wallet_stats_label">Fiat Wallet Statistics</span></h3>
											<h6 class="card-subtitle"><span id="last_15_transactions_label">Last 15 days of Transactions</span></h6>
										</div>
									</div>
									<div class="card-body">
<!-- 									<div id="Highchart9" class="chartsh chart-dropshadow"></div>
 -->
									<div id="echart1" class="chartsh chart-dropshadow"></div>
 										
									</div>
								</div>
							</div>
							
						</div>
						
					</div>
				</div>
				<!-- End app-content-->
				<form method = "post" id="get-txn-form">
					<input type="hidden" name="qs" value="">
					<input type="hidden" name="rules" value="">
					<input type="hidden" name="hdnlang" >
				</form>
				<form method = "post" id="dispute-form">
					<input type="hidden" name="qs" value="">
					<input type="hidden" name="rules" value="">
					<input type="hidden" name="relno" value="">
					<input type="hidden" name="hdnreqid" value="">
				</form>

			</div>
			
		</div>
		<!-- End Page -->
		
        <div id="error-msg-login-page" style="display: none;">
			<span id="table-label-date">Date</span>
			<span id="table-label-description">Description</span>
			<span id="table-label-amount">Amount</span>
			<span id="data-validation-error-swal-header">Oops..</span>
			<span id="failed-to-get-data-validation-error-swal-header">Failed to get Transactions</span>
			<span id="data-validation-problem-with-connection">Problem with connection</span>
			<span id="lang_def"></span> 
		</div>


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
		<script src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>
		
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
	
		<!-- Custom-charts js-->
		<script src="assets/js/_custdashboard.js"></script>
		<script src="assets/js/_cust_dashboard_page_graphs.js"></script>
		
		
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
	if(publicKey !=null) publicKey =null; if(wallet !=null) wallet =null;
	if(accountBalances !=null) accountBalances =null; if(relationshipNo !=null) relationshipNo =null;
	if (firstName!=null) firstName=null;if (hashSumOfTxns!=null) hashSumOfTxns=null;
	if (txnWalletValuesArray!=null) txnWalletValuesArray=null;if (txnWalletDatesArray!=null) txnWalletDatesArray=null;
	if (name!=null) name=null; if (fullName!=null) fullName=null;if (langPref!=null) langPref=null;
}
%>
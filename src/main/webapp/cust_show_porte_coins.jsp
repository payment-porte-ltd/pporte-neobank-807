<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*, com.pporte.utilities.Utilities,org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
String langPref=null;
String publicKey=null;
ArrayList<AssetAccount> accountBalances = null;
ServletContext context = null;
ArrayList<AssetTransactions> assetTransactions = null; 
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callException(request, response, context,"Session has expired, please login again.");
		return;
	}

	if(request.getAttribute("publickey")!=null)
		publicKey = (String)request.getAttribute("publickey");
	if(request.getAttribute("externalwallets")!=null)
		accountBalances = (ArrayList<AssetAccount> )request.getAttribute("externalwallets");
	if (request.getAttribute("assetTransactions") != null) assetTransactions = 
			(ArrayList<AssetTransactions>) request.getAttribute("assetTransactions");
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
<title>Assets</title>

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
<link  href="assets/fonts/fonts/font-awesome.min.css" rel="stylesheet">

<!---Sweetalert2 css-->
<link href="assets/plugins/sweetalert2/sweetalert2.css" rel="stylesheet" />
<!-- Owl Theme css-->
<link href="assets/plugins/owl-carousel/owl.carousel.css" rel="stylesheet">

<!-- Custom css -->
<link href="assets/css/style2.css" rel="stylesheet" />
<!-- Responsive css -->
<link href="assets/css/responsive.css" rel="stylesheet" />
<!---Sweetalert2 css-->
<link href="assets/plugins/sweetalert2/sweetalert2.css" rel="stylesheet" />
<!-- Data table css -->
<link href="assets/plugins/datatable/dataTables.bootstrap4.min.css"
	rel="stylesheet" />
<link href="assets/plugins/datatable/responsivebootstrap4.min.css"
	rel="stylesheet" />
<style>
	.carousel-content{
	font-size: 39px;
	position: absolute;
	bottom: 17%;
	left: 12%;
	z-index: 20;
	color: white;
	font-weight: bold;
	}
	.animation-box {
	  outline: 8px ridge;
	  border-radius: 2rem;
	}
	.dtr-modal .dtr-modal-display{
		background: linear-gradient(0deg, rgba(0, 0, 0, 0.2), rgba(0, 0, 0, 0.2)), linear-gradient(180deg, #060441 0%, #2D2C4A 37.71%, #2E2E3C 84.56%);
	}
	div.dtr-modal div.dtr-modal-close:hover {
  		background-color: #27A79C;
	}
	div.dtr-modal div.dtr-modal-close {
	  border: 1px solid #27A79C;
	  background-color: #27A79C;
	}
</style>
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
				<div class="side-app" style="height: 89vh;">

					<!-- page-header -->
					<div class="page-header" style="margin: auto">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#"><span
									id="breadcrumb-item-digital-assets">Digital Assets</span></a></li>
							<li class="breadcrumb-item active" aria-current="page"><span
								id="breadcrumb-item-assets">Assets</span></li>
						</ol>
						<!-- End breadcrumb -->
					</div>

					<div class="row mt-5">
							<div class="col-12">
					<% if( accountBalances!=null){%>
							<div class="owl-carousel  profiles owl-theme mb-5">
								<%	for(int i=0;i<accountBalances.size();i++){   
									if(accountBalances.get(i).getAssetCode().equals("PORTE")){ %>
										<div class="porte-card" style="width: 24rem;">
								              <div class="asset_balance"><%=accountBalances.get(i).getAssetBalance()+" "%>PORTE</div>
								        </div>
									<%}else if(accountBalances.get(i).getAssetCode().equals("USDC")){ %>
										<div class="usdc-card" style="width: 24rem;">
							                <div class="asset_balance"><%=accountBalances.get(i).getAssetBalance()+" "%>USDC</div>
							            </div>
									<%}else if(accountBalances.get(i).getAssetCode().equals("VESL")){ %>
										<div class="vesl-card" style="width: 24rem;">
							                <div class="asset_balance"><%=accountBalances.get(i).getAssetBalance()+" "%>VESL</div>
							            </div>
									<%}else if(accountBalances.get(i).getAssetCode().equals("XLM")){ %>
										<div class="stellar-card" style="width: 24rem;">
						                   <div class="asset_balance"><%=accountBalances.get(i).getAssetBalance()+" "%>XLM</div>
						                </div>
									<%}else if(accountBalances.get(i).getAssetCode().equals("BTC")){ %>
										<div class="btc-card" style="width: 24rem;">
					                  		 <div class="asset_balance"><%=accountBalances.get(i).getAssetBalance()+" "%>BTCX</div>
					                    </div>
									<%} %>
							<% }	 %>
						<%	}else{ %>
							<div>
								<span id="spn_no_wallets_available">No External Wallets </span>
							</div>
							<% } %>
						</div>
						
						</div>
						</div>
						<div class="row">
						<div class="col-12">
							<!-- <div >
								<video width=105%  class="animation-box" preload="auto" autoplay loop muted="muted" volume="0"> 
									<source src="assets/images/brand/animation.mp4" type="video/mp4"> 
								</video>
						 </div> -->
						 <div class="card pb-4">
						 	<div class="card-header">Last Five Transactions</div>
						 	<div class="card-body">
									<div class="table-responsive">
										<table id="example3"
											class="table card-table table-vcenter text-nowrap table-nowrap">
											<thead>
												<tr>
													<th><span id="table_header_ops_id">Operation
															Id</span></th>
													<th><span id="table_header_time">Time</span></th>
													<th><span id="table_header_ac">Account </span></th>
													<th><span id="table_header_txn_type">Transaction
															Type</span></th>
													<th><span id="table_header_sr_account">Source
															account</span></th>
													<th><span id="table_header_destination_ac">Destination
															account</span></th>
													<th><span id="table_header_amount">Amount </span></th>
													<th><span id="table_header_txn_fee">Transaction
															Fee</span></th>
												</tr>
											</thead>
											<tbody id="wallet_txn">
												<% if( assetTransactions!=null){
													for(int i=0;i<assetTransactions.size();i++){       
												%>
												<tr>
													<td><%=((AssetTransactions)assetTransactions.get(i)).getOperationId()%>
													</td>
													<td><%=((AssetTransactions)assetTransactions.get(i)).getCreatedOn()%>
													</td>
													<td><%=((AssetTransactions)assetTransactions.get(i)).getSourceAccount()%>
													</td>
													<td><%=((AssetTransactions)assetTransactions.get(i)).getType()%>
													</td>
													<%if( (assetTransactions.get(i).getType().equals("payment"))) {%>

													<td><%=((AssetTransactions)assetTransactions.get(i)).getFromAccount()%>
													</td>
													<td><%=((AssetTransactions)assetTransactions.get(i)).getToAccount()%>
													</td>
													<td><%=((AssetTransactions)assetTransactions.get(i)).getAmount() +" "+((AssetTransactions)assetTransactions.get(i)).getAssetCode()%>
													</td>

													<%}else if( (assetTransactions.get(i).getType().equals("manage_sell_offer"))) {%>

													<td><%=((AssetTransactions)assetTransactions.get(i)).getSellingAsset()%>
													</td>
													<td><%=((AssetTransactions)assetTransactions.get(i)).getBuyingAsset()%>
													</td>
													<td><%=((AssetTransactions)assetTransactions.get(i)).getOfferPrice()%>
													</td>

													<%}else if( (assetTransactions.get(i).getType().equals("manage_buy_offer"))) {%>

													<td><%=((AssetTransactions)assetTransactions.get(i)).getSellingAsset()%>
													</td>
													<td><%=((AssetTransactions)assetTransactions.get(i)).getBuyingAsset()%>
													</td>
													<td><%=((AssetTransactions)assetTransactions.get(i)).getOfferPrice()%>
													</td>

													<%}else if( (assetTransactions.get(i).getType().equals("change_trust"))) {%>

													<td><%=((AssetTransactions)assetTransactions.get(i)).getTrustee()%>
													</td>
													<td><%=((AssetTransactions)assetTransactions.get(i)).getTrustor()%>
													</td>
													<td><%=((AssetTransactions)assetTransactions.get(i)).getAssetCode()	%>
													</td>

													<%}else if( (assetTransactions.get(i).getType().equals("create_account"))) {%>
													<td></td>
													<td></td>
													<td></td>
													<%}else if( (assetTransactions.get(i).getType().equals("path_payment_strict_send"))) {%>
													<td><%=((AssetTransactions)assetTransactions.get(i)).getFromAccount()%></td>
													<td><%=((AssetTransactions)assetTransactions.get(i)).getToAccount()%></td>
													<td><%=((AssetTransactions)assetTransactions.get(i)).getAmount()  +" "+((AssetTransactions)assetTransactions.get(i)).getAssetCode()%></td>
													<%}else if( (assetTransactions.get(i).getType().equals("path_payment_strict_receive"))) {%>
													<td><%=((AssetTransactions)assetTransactions.get(i)).getFromAccount()%></td>
													<td><%=((AssetTransactions)assetTransactions.get(i)).getToAccount()%></td>
													<td><%=((AssetTransactions)assetTransactions.get(i)).getAmount()  +" "+((AssetTransactions)assetTransactions.get(i)).getAssetCode()%></td>
													<%}else if( (assetTransactions.get(i).getType().equals("create_claimable_balance"))) {%>
													<td><%=((AssetTransactions)assetTransactions.get(i)).getFromAccount()%></td>
													<td></td>
													<td><%=((AssetTransactions)assetTransactions.get(i)).getAmount()  +" "+((AssetTransactions)assetTransactions.get(i)).getAssetCode()%></td>
													<%}else if( (assetTransactions.get(i).getType().equals("claim_claimable_balance"))) {%>
													<td><%=((AssetTransactions)assetTransactions.get(i)).getFromAccount()%></td>
													<td><%=((AssetTransactions)assetTransactions.get(i)).getToAccount()%></td>
													<td></td>
													<%}else if( (assetTransactions.get(i).getType().equals("Claimable Balance to Claim"))) {%>
													<td><%=((AssetTransactions)assetTransactions.get(i)).getFromAccount()%></td>
													<td></td>
													<td><%=((AssetTransactions)assetTransactions.get(i)).getAmount()  +" "+((AssetTransactions)assetTransactions.get(i)).getAssetCode()%></td>
													<%}%>

													<td><%=((AssetTransactions)assetTransactions.get(i)).getFeeCharged() +" Stroops"%>
													</td>

													<% }
												}else{ %>
												
												<tr>
													<td colspan="9"><span id="no_available_txn_details">No
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
			<!-- End app-content-->

		</div>

	</div>
	<!-- End Page -->

	<form method="post" id="post-form">
		<input type="hidden" name="qs" value=""> <input type="hidden"
			name="rules" value="">
	</form>

	<!-- Back to top -->
	<a href="#top" id="back-to-top"><i class="fa fa-angle-up"></i></a>

	<!-- Jquery js-->
	<script src="assets/js/vendors/jquery-3.2.1.min.js"></script>

	<!--Bootstrap.min js-->
	<script src="assets/plugins/bootstrap/popper.min.js"></script>
	<script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>
	
	<!--Owl Carousel js -->
	<script src="assets/plugins/owl-carousel/owl.carousel.js"></script>
	<script src="assets/plugins/owl-carousel/owl-main.js"></script>

	<!--Side-menu js-->
	<script src="assets/plugins/toggle-sidebar/sidemenu.js"></script>

	<!-- Sidebar Accordions js -->
	<script src="assets/plugins/accordion1/js/easyResponsiveTabs.js"></script>

	<!-- Custom scroll bar js-->
	<script
		src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>
		<!-- Data tables js-->
	<script src="assets/plugins/datatable/jquery.dataTables.min.js"></script>
	<script src="assets/plugins/datatable/dataTables.bootstrap4.min.js"></script>
	<script src="assets/plugins/datatable/datatable.js"></script>
	<script src="assets/plugins/datatable/datatable-2.js"></script>
	<script src="assets/plugins/datatable/dataTables.responsive.min.js"></script>

	<!-- Custom js-->
	<script src="assets/js/_cust_show_porte_coins.js"></script>
	<script src="assets/js/custom.js"></script>

	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>
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
	if ( accountBalances!=null); accountBalances=null;
	if ( publicKey!=null); publicKey=null;if (langPref!=null) langPref=null;if (context!=null)context=null;
}
%>
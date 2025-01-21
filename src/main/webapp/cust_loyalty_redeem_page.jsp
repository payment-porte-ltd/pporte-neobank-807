<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*, com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%

ArrayList<Loyalty> arrLoyaltyPoints = null;	 ConcurrentHashMap<String, String> hashPaymode = null;
 String loyaltyBalance = null; String relationshipNo = null;
 ServletContext context = null;String langPref = null;
 try{
 	if (request.getRequestedSessionId() != null
 	        && !request.isRequestedSessionIdValid()) {
 	    // Session is expired
 	    context = getServletContext();
 		Utilities.callException(request, response, context,"Session has expired, please login again.");
 		return;
 	}
 	if(request.getAttribute("langPref")!=null) langPref = (String)request.getAttribute("langPref");
	if(request.getAttribute("loyaltypointsdetails")!=null)	arrLoyaltyPoints = (ArrayList<Loyalty>)request.getAttribute("loyaltypointsdetails");
	if(request.getAttribute("loyaltyrulesdesc")!=null)	hashPaymode = (ConcurrentHashMap<String, String>)request.getAttribute("loyaltyrulesdesc");
	if(request.getAttribute("loyaltypointsbalance")!=null)	loyaltyBalance = (String)request.getAttribute("loyaltypointsbalance");
	
	relationshipNo= ((User)session.getAttribute("SESS_USER")).getRelationshipNo();
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
<title>View Points</title>

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

<!-- Custom css -->
<link href="assets/css/app.css" rel="stylesheet" />
<!-- Responsive css -->
<link href="assets/css/responsive.css" rel="stylesheet" />
<!-- Data table css -->
<link href="assets/plugins/datatable/dataTables.bootstrap4.min.css"
	rel="stylesheet" />
<link href="assets/plugins/datatable/responsivebootstrap4.min.css"
	rel="stylesheet" />

<!-- Custom css -->
<link href="assets/css/style2.css" rel="stylesheet" />
<!-- Responsive css -->
<link href="assets/css/responsive.css" rel="stylesheet" />
<!---Sweetalert2 css-->
<link href="assets/plugins/sweetalert2/sweetalert2.css" rel="stylesheet" />

</head>

<body class="app sidebar-mini rtl"
	onload="javascript:fnUpdatePointBalance('<%=loyaltyBalance%>'); return false">

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
					<div class="page-header">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#"
								id="breadcrumb_item_label">Manage Loyalty</a></li>
							<li class="breadcrumb-item active" aria-current="page"
								id="breadcrumb_item_active_label">Redeem Points</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<div class="">
						<!-- page-content -->
						<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
							<div class="card">
									<div class="card-header">
										<h4 id="redeem_loyalty_header">Redeem Loyalty Points</h4>
									</div>
								<div class="row card-body">
									<div class="col-lg-12">
										<div class="porte-card-content card-hover">

											<div class="card-body">

												<div class="row">
													<div class="col-xl-7 points-balance">
														<label class="text-white" id="label_point_balance">Point Balance</label>
														<div>
															<p class="span-points-balance text-white" id="point-balance"></p>
															<p>
																<span class="text-white" id="walletideerror"></span>
															</p>
														</div>
													</div>

													<div class="col-xl-5">
														<div class="transfer-coin-input">
															<label id="label_redeem_amount" class="text-white">Redeem Amount</label>
															<div class="input-two clearfix">
																<div class="input-two-box">
																	<input name="redeemamount"class="styled-input-field" id="redeemamount" value=""
																		placeholder="" readonly> <span>POINTS</span>
																</div>
																<i class="fa fa-arrow-right" id="exchange_icon" style="color: #28a59a; cursor: pointer;top:23%"></i>

																<div class="input-two-box">
																	<input name="receivedamount_loyalty" id="receivedamount_loyalty"
																		value="" placeholder="" readonly class="styled-input-field"> <!-- <span
																		id="spanreceivedcode" class="text-white" >VESL</span> -->
																</div>
															</div>
														</div>
														<div class="transfer-coin-button">
															<button type="button" class="theme-btn"
																onclick="javascript:fnRedeemAllLoyalty('<%=loyaltyBalance%>','<%=relationshipNo %>'); return false"
																style="width: 100%">
																<span id="label_redeem_all_btn">Redeem All</span>
															</button>
														</div>
													</div>

												</div>
											</div>
										</div>
									</div>
									
								<div class="row">
									<div class="col-lg-12" style="margin-top: 20px;">
										<div class="porte-card-content card-hover">
											<!-- <div class="card-header">
												<div class="card-title">Transactions Details</div>
											</div> -->
											<div class="card-body">
												<% if(arrLoyaltyPoints!=null){%>
												<div class="table-responsive">
													<table id="custloyaltytable"
														class="table card-table table-vcenter text-nowrap table-nowrap">
														<thead>
															<tr>
																<th><span id="label_date">Date</span></th>
																<th><span id="label_payment_type">Payment
																		Type</span></th>
																<th><span id="label_ref_no">Reference Number</span></th>
																<th><span id="label_points_awarded">Points
																		Awarded</span></th>
																<th><span id="label_point_bal">Points
																		Balance</span></th>
																<th><span id="label_action">Action</span></th>
															</tr>
														</thead>
														<tbody id="wallet_loyalty_txns">

															<%	for(int i=0;i<arrLoyaltyPoints.size();i++){
															%>
															<tr>
																<td><%= ((Loyalty)arrLoyaltyPoints.get(i)).getTxnDate() %></td>
																<%-- 														     <td> <%= ((Loyalty)arrLoyaltyPoints.get(i)).getWalletId() %></td>
 --%>
																<%if((hashPaymode.get( ((Loyalty)arrLoyaltyPoints.get(i)).getPayMode() )) != null){ %>
																<td><%=hashPaymode.get( ((Loyalty)arrLoyaltyPoints.get(i)).getPayMode() ) %>
																</td>
																<%}else{ %>
																<td><%=((Loyalty)arrLoyaltyPoints.get(i)).getPayMode()  %>
																</td>
																<%} %>
																<td><%= ((Loyalty)arrLoyaltyPoints.get(i)).getTxnReference() %></td>
																<td><%= ((Loyalty)arrLoyaltyPoints.get(i)).getPointAccrued() %></td>
																<td><%= ((Loyalty)arrLoyaltyPoints.get(i)).getPointBalance() %></td>
																<td>
																	<button type="button" class="btn-claim-rewards" id="label_claim_btn"
																		onclick="javascript:fnClaimAReward('<%= ((Loyalty)arrLoyaltyPoints.get(i)).getSequenceId() %>',
																	 '<%= ((Loyalty)arrLoyaltyPoints.get(i)).getPointAccrued() %>', '<%=((Loyalty)arrLoyaltyPoints.get(i)).getPayMode()  %>',
																	  '<%= ((Loyalty)arrLoyaltyPoints.get(i)).getTxnReference() %>','<%= ((Loyalty)arrLoyaltyPoints.get(i)).getWalletId() %>');return false;">
																	Claim Points</button>
																</td>

															</tr>
															<% }%>
														</tbody>
													</table>
												</div>
												<%}else{ %>
												<span class="text-danger" id="label_no_points">No
													points awarded yet. Perform transactions to earn points</span>
												<% } %>
											</div>
										</div>
									</div>
								</div>
								</div>


								<form action="post" id="cust-loyalty-points-form">
									<input type="hidden" name="qs" value=""> <input
										type="hidden" name="rules" value="">
								</form>
								<form action="post" id="cust-redeem_points-form"></form>

							</div>
						</div>
					</div>
					<div style="display: none">
						<span id="label_claim_btn">Claim Points</span> <span
							id="validation_point_balance">Your point balance is 0.
							Kindly transact to get Loyalty Points</span> <span
							id="success_redemtion">Points Redeemed Successfully</span> <span
							id="swal_problem_connection">Points Redeemed Successfully</span>
							<span id="lang_def"></span>
					</div>
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


	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>
	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>
	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_cust_loyalty_redeem_page.js"></script>
	<!-- Data tables js-->
	<script src="assets/plugins/datatable/jquery.dataTables.min.js"></script>
	<script src="assets/plugins/datatable/dataTables.bootstrap4.min.js"></script>
	<script src="assets/plugins/datatable/datatable.js"></script>
	<script src="assets/plugins/datatable/datatable-2.js"></script>
	<script src="assets/plugins/datatable/dataTables.responsive.min.js"></script>
	<script>
		$(function() {
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
	if(arrLoyaltyPoints !=null ) arrLoyaltyPoints= null;
	if (context!=null)context=null;if (langPref!=null)langPref=null;

}
%>
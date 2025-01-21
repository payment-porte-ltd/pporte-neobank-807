<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>

<%
ArrayList<AssetTransactions> assetTransactions = null; 
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}
		
	if (request.getAttribute("assetTransactions") != null) assetTransactions = (ArrayList<AssetTransactions>) request.getAttribute("assetTransactions");
	/* if(assetTransactions!=null)
		Collections.reverse(assetTransactions); */
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
<link rel="shortcut icon" type="image/x-icon"
	href="assets/images/brand/pporte.png" />

<!-- Title -->
<title>Display Stellar Transactions</title>

<!--Bootstrap.min css-->
<link rel="stylesheet"
	href="assets/plugins/bootstrap/css/bootstrap.min.css">

<!-- Dashboard css -->
<link href="assets/css/ops_style.css" rel="stylesheet" />

<!-- Custom scroll bar css-->
<link href="assets/plugins/scroll-bar/jquery.mCustomScrollbar.css"
	rel="stylesheet" />

<!---Sweetalert2 css-->
<link href="assets/plugins/sweetalert2/sweetalert2.css" rel="stylesheet" />

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

<!-- Custom css -->
<link href="assets/css/app.css" rel="stylesheet" />
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
			<jsp:include page="ops_topandleftmenu.jsp" />
			<!-- app-content-->
			<div class="app-content  my-3 my-md-5 toggle-content">
				<div class="side-app">
					<!-- page-header -->
					<div class="page-header">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#">BTCx Account</a></li>
							<li class="breadcrumb-item active" aria-current="page">Display
								Stellar Transactions</li>
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
										<span id="card_txn_header"></span>
									</h4>
								</div>
								<div class="card-body">
									<div class="table-responsive">
										<table id="example3"
											class="table table-striped table-bordered text-nowrap">
											<thead>
												<tr>
													<th>Operation Id</th>
													<th>Time</th>
													<th>Account</th>
													<th>Transaction Type</th>
													<th>Source account</th>
													<th>Destination account</th>
													<th>Amount</th>
													<th>Transaction Fee</th>
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
													<%if (((AssetTransactions)assetTransactions.get(i)).getType()!=null) {%>
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
													<%}%>
													<%} %>
													<td><%=((AssetTransactions)assetTransactions.get(i)).getFeeCharged() +" Stroops"%>
													</td>

													<% }
												}else{ %>
												
												<tr>
													<td colspan="9"><span id="ops_all_bin_list_errormsg1">No
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
						type="hidden" name="rules" value="">
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

	<!--Side-menu js-->
	<script src="assets/plugins/toggle-sidebar/sidemenu.js"></script>

	<!-- Custom scroll bar js-->
	<script
		src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>
	<!-- Rightsidebar js -->
	<script src="assets/plugins/sidebar/sidebar.js"></script>
	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>

	<!-- Data tables js-->
	<script src="assets/plugins/datatable/jquery.dataTables.min.js"></script>
	<script src="assets/plugins/datatable/dataTables.bootstrap4.min.js"></script>
	<script src="assets/plugins/datatable/datatable.js"></script>
	<script src="assets/plugins/datatable/datatable-2.js"></script>
	<script src="assets/plugins/datatable/dataTables.responsive.min.js"></script>
	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>


	<script>
			$(window).on("load", function(e) {
			    $("#global-loader").fadeOut("slow");
			})
		</script>
</body>
</html>


<%
}catch (Exception e){
	out.println ("Exception : "+e.getMessage());
}finally{
	if(assetTransactions !=null) assetTransactions = null; if (context!=null)context=null;
}
%>
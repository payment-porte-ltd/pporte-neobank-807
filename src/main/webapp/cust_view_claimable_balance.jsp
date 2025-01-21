<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%

ArrayList<ClaimableBalance> claimableBalances = null;ServletContext context = null;
String langPref=null;

try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callException(request, response, context,"Session has expired, please login again.");
		return;
	}
	if(request.getAttribute("claimable_balances")!=null)	
		claimableBalances = (ArrayList<ClaimableBalance>)request.getAttribute("claimable_balances");
	if(request.getAttribute("langpref")!=null) langPref = (String)request.getAttribute("langpref");
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
<title>Claim Claimable Balance</title>
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
									id="breadcrumb-item-porte-coins">Digital Assets</span></a></li>
							<li class="breadcrumb-item active" aria-current="page"><span
								id="breadcrumb-item-claim-claimable-balance">Claim
									Claimable Balance</span></li>
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
										<span id="card-title-claimable-balance">Claim
											Claimable Balance</span>
									</div>
								</div>
								<div class="card-body">
									<div class="table-responsive">
										<table id="example3"
											class="table table-striped table-bordered text-nowrap">
											<thead>
												<tr>
													<th class="wd-15p"><span id="th-asset-code">Asset
															Code</span></th>
													<th class="wd-15p"><span id="th-amount">Amount</span></th>
													<th class="wd-15p"><span id="th-txn-date">Transaction
															Date</span></th>
													<th class="wd-15p"><span id="th-src-account">Source
															Account</span></th>
													<th class="wd-20p"><span id="th-src-action">Action</span></th>
													<th class="wd-15p"><span id="th-claimable-balance-id">Claimable
															Balance Id</span></th>
													<th class="wd-15p"><span id="th-asset-issuer-account">Asset
															Issuer Account</span></th>
												</tr>
											</thead>
											<tbody>
												<% if(claimableBalances!=null){
													for(int i=0;i<claimableBalances.size();i++){
											%>
												<tr>
													<td><%= ((ClaimableBalance)claimableBalances.get(i)).getAssetCode() %></td>
													<td><%= ((ClaimableBalance)claimableBalances.get(i)).getAmount() %></td>
													<td><%= ((ClaimableBalance)claimableBalances.get(i)).getCreatedOn() %></td>
													<td><%= ((ClaimableBalance)claimableBalances.get(i)).getSourceAccount() %></td>
													<td class="text-center align-middle">
														<div class="btn-group align-top">
															<button class="btn btn-sm btn-primary badge"
																onClick="javascript:checkIfUserHasMneonicCode(
															'<%= ((ClaimableBalance)claimableBalances.get(i)).getClaimableBalanceId() %>',
															'<%= ((ClaimableBalance)claimableBalances.get(i)).getAssetCode() %>',
															'<%= ((ClaimableBalance)claimableBalances.get(i)).getAssetIssuer() %>')"
																type="button">Claim</button>
														</div>
													</td>
													<td><%= ((ClaimableBalance)claimableBalances.get(i)).getClaimableBalanceId() %></td>
													<td><%= ((ClaimableBalance)claimableBalances.get(i)).getAssetIssuer() %></td>
												</tr>

												<% }
											}else{ %>
												<tr>
													<td><span id="spn_no_data-present">No data
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
		</div>
	</div>

	<form method="post" id="get-page-form">
		<input type="hidden" name="qs" value=""> <input type="hidden"
			name="rules" value=""> <input type="hidden" name="hdnlang"
			>
	</form>
	<!-- End Page -->
	<div id="error-msg-cust-crypt-pay-anyone-page" style="display: none;">
		<span id="data-validation-error-swal-header">Oops..</span> <span
			id="data-validation-error-failed-to-get-details">Failed to get
			details</span> <span id="data-validation-problem-with-connection">Problem
			with connection</span> <span id="data-validation-check-your-data">Please
			check your data</span> <span id="data-validation-success">Success</span> <span
			id="enter-your-passsword-tittle">Enter your Password</span> <span
			id="enter-your-passsword-label">Password</span> <span
			id="data-validation-enter-your-passsword-label">Please input
			your password!</span> <span id="data-validation-enter-your-pvt-key-label">Please
			input your Private Key!</span> <span id="enter-your-pvt-key-label">Enter
			your Private Key</span>
	</div>

	<!-- Back to top -->
	<a href="#top" id="back-to-top"><i class="fa fa-angle-up"></i></a>
	<!-- Jquery js-->
	<script src="assets/js/vendors/jquery-3.2.1.min.js"></script>
	<!--Bootstrap.min js-->
	<script src="assets/plugins/bootstrap/popper.min.js"></script>
	<script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>
	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>

	<!--Side-menu js-->
	<script src="assets/plugins/toggle-sidebar/sidemenu.js"></script>
	<!-- Sidebar Accordions js -->
	<script src="assets/plugins/accordion1/js/easyResponsiveTabs.js"></script>

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>

	<!-- Custom scroll bar js-->
	<script
		src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>
	<!-- Rightsidebar js -->
	<script src="assets/plugins/sidebar/sidebar.js"></script>
	<!-- Data tables js-->
	<script src="assets/plugins/datatable/jquery.dataTables.min.js"></script>
	<script src="assets/plugins/datatable/dataTables.bootstrap4.min.js"></script>
	<script src="assets/plugins/datatable/datatable.js"></script>
	<script src="assets/plugins/datatable/datatable-2.js"></script>
	<script src="assets/plugins/datatable/dataTables.responsive.min.js"></script>
	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>

	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_cust_view_claimable_balance.js"></script>
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
	if(claimableBalances!=null) claimableBalances=null;	
	if (langPref!=null) langPref=null;if (context!=null)context=null;

}

%>
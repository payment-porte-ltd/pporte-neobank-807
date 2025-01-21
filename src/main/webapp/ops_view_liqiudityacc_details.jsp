<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.utilities.Utilities, com.pporte.model.*, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%

String portePublicKey=null;
String vesselPublicKey=null;
String porteDistributionAccountBal =null;
String vesselDistributionAccountBal =null;

ArrayList<AssetTransactions> arrPorteDistributionTxn = null;
ArrayList<AssetTransactions> arrVesselDistributionTxn = null;
ConcurrentHashMap<String,String> hashPayMode = null;Iterator<String> itPayMode = null; 
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}
	
	if(request.getAttribute("portepubkey")!=null)	portePublicKey = (String)request.getAttribute("portepubkey");
	if(request.getAttribute("vesselpubkey")!=null)	vesselPublicKey = (String)request.getAttribute("vesselpubkey");
	
	if(request.getAttribute("porteaccbal")!=null)	porteDistributionAccountBal = (String)request.getAttribute("porteaccbal");
	if(request.getAttribute("vesselaccbal")!=null)	vesselDistributionAccountBal = (String)request.getAttribute("vesselaccbal");
	
	if(request.getAttribute("portetxn")!=null)	arrPorteDistributionTxn = (ArrayList<AssetTransactions>)request.getAttribute("portetxn");
	if(request.getAttribute("veseltxn")!=null)	arrVesselDistributionTxn = (ArrayList<AssetTransactions>)request.getAttribute("veseltxn");

	hashPayMode = new ConcurrentHashMap<String,String>();
	hashPayMode.put("C","Credit");	hashPayMode.put("D","Debit");
	
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
<title>Liquidity Account</title>

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
<!-- Parsley -->
<link href="assets/plugins/parsley/parsley.css" rel="stylesheet">
<!--Sweetaler css-->
<link href="assets/plugins/sweetalert2/sweetalert2.css">

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
							<li class="breadcrumb-item"><a href="#">Manage Digital
									Assets</a></li>
							<li class="breadcrumb-item active" aria-current="page">Liquidity
								Account Details</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- row -->
					<div class="row">
						<div class="col-md-12 col-lg-12">
							<div class="card">
								<div class="card-header">
									<h3 class="card-title">Liquidity Account Details</h3>
								</div>
								<div class="card-body p-6">
									<div class="panel panel-primary">
										<div class="tab-menu-heading">
											<div class="tabs-menu ">
												<!-- Tabs -->
												<ul class="nav panel-tabs">
													<li class=""><a href="#tab11" class="active"
														data-toggle="tab">PORTE Token</a></li>
													<li><a href="#tab21" data-toggle="tab">Vessel Coin</a></li>

												</ul>
											</div>
										</div>
										<div class="panel-body tabs-menu-body">
											<div class="tab-content">
												<div class="tab-pane active " id="tab11">
													<div class="row">
														<!-- row -->
														<div class="col-lg-12">
															<div class="card card-profile  overflow-hidden">
																<div class="card-body  profile-bg">
																	<div class=" card-profile">
																		<form id="distribution_acc" method="post">
																			<div class="row">
																				<div class="col">
																					<div class="form-label">
																						<label class="form-label">Liquidity
																							Account Public Key</label> <input type="text"
																							class="form-control" name="distributionpubkey"
																							id="distributionpubkey"
																							value="<%=portePublicKey%>" readonly>
																					</div>
																					<%
																								if (porteDistributionAccountBal != null) {
																									
																								%>
																					<div class="form-group">
																						<label class="form-label">Liquidity
																							Account Balance</label>
																						<div style="color: white;"><%=porteDistributionAccountBal +" PORTE"%></div>
																					</div>
																					<%} %>
																				</div>
																			</div>
																		</form>
																	</div>

																</div>
															</div>
														</div>
													</div>
													<div class="row row-cards">
														<div class="col-md-12 col-lg-12">
															<div class="card">
																<div class="card-body">
																	<div class="table-responsive">
																		<table id="porte_txns"
																			class="table table-striped table-bordered text-nowrap w-100">
																			<thead>
																				<tr>
																					<th>Operation Id</th>
																					<th>Source Account</th>
																					<th>To Account</th>
																					<th>Amount</th>
																					<th>Transaction Type</th>
																					<th>Asset Code</th>
																					<th>Transaction Mode</th>
																					<th>Created On</th>

																				</tr>
																			</thead>
																			<tbody>
																				<%
																					if (arrPorteDistributionTxn != null) {
																						for (int i = 0; i < arrPorteDistributionTxn.size(); i++) {
																					%>
																				<tr>
																					<%if( (arrPorteDistributionTxn.get(i).getToAccount()==null)||(arrPorteDistributionTxn.get(i).getAmount()==null)
																								||(arrPorteDistributionTxn.get(i).getAssetCode()==null)||(arrPorteDistributionTxn.get(i).getTxnMode()==null)){%>
																					<td><%=((AssetTransactions) arrPorteDistributionTxn.get(i)).getOperationId()%></td>
																					<td><%=((AssetTransactions) arrPorteDistributionTxn.get(i)).getSourceAccount()%></td>
																					<td></td>
																					<td></td>
																					<td><%=((AssetTransactions) arrPorteDistributionTxn.get(i)).getType()%></td>
																					<td></td>
																					<td></td>
																					<td><%=((AssetTransactions) arrPorteDistributionTxn.get(i)).getCreatedOn()%></td>
																					<%}else{ %>
																					<td><%=((AssetTransactions) arrPorteDistributionTxn.get(i)).getOperationId()%></td>
																					<td><%=((AssetTransactions) arrPorteDistributionTxn.get(i)).getSourceAccount()%></td>
																					<td><%=((AssetTransactions) arrPorteDistributionTxn.get(i)).getToAccount()%></td>
																					<td><%=((AssetTransactions) arrPorteDistributionTxn.get(i)).getAmount()%></td>
																					<td><%=((AssetTransactions) arrPorteDistributionTxn.get(i)).getType()%></td>
																					<td><%=((AssetTransactions) arrPorteDistributionTxn.get(i)).getAssetCode()%></td>
																					<td><%=hashPayMode.get(((AssetTransactions) arrPorteDistributionTxn.get(i)).getTxnMode())%></td>
																					<td><%=((AssetTransactions) arrPorteDistributionTxn.get(i)).getCreatedOn()%></td>
																					<% } %>

																				</tr>
																				<%
																						 }
																						} else {
																						%>

																				<tr>
																					<td colspan="9"><span
																						id="ops_all_bin_list_errormsg1">No Porte
																							Distribution Transactions made</span></td>

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
												<div class="tab-pane  " id="tab21">
													<div class="row">
														<!-- row -->
														<div class="col-lg-12">
															<div class="card card-profile  overflow-hidden">
																<div class="card-body profile-bg">
																	<div class=" card-profile">
																		<form id="distribution_acc">
																			<div class="row ">
																				<div class="col">
																					<div class="form-group">
																						<label class="form-label">Liquidity
																							Account Public Key</label> <input type="text"
																							class="form-control"
																							name="distributionvesselpubkey"
																							id="distributionvesselpubkey"
																							value="<%=vesselPublicKey%>" readonly>
																					</div>
																					<%
																									if (vesselDistributionAccountBal != null) {
																										
																									%>
																					<div class="form-group">
																						<label class="form-label">Liquidity
																							Account Balance</label>
																						<div style="color: white;"><%=vesselDistributionAccountBal +" VESL"%></div>
																					</div>
																					<%} %>
																				</div>
																			</div>
																		</form>
																	</div>

																</div>
															</div>
														</div>
													</div>
													<div class="row row-cards">
														<div class="col-md-12 col-lg-12">
															<div class="card">
																<div class="card-body">
																	<div class="table-responsive">
																		<table id="example3" style="color: black"
																			class="table table-striped table-bordered text-nowrap">
																			<thead>
																				<tr>
																					<th>Operation Id</th>
																					<th>Source Account</th>
																					<th>To Account</th>
																					<th>Amount</th>
																					<th>Transaction Type</th>
																					<th>Asset Code</th>
																					<th>Transaction Mode</th>
																					<th>Created On</th>

																				</tr>
																			</thead>
																			<tbody>
																				<%
																					if (arrVesselDistributionTxn != null) {
																						for (int i = 0; i < arrVesselDistributionTxn.size(); i++) {
																					%>

																				<tr>
																					<%if( (arrVesselDistributionTxn.get(i).getToAccount()==null)||(arrVesselDistributionTxn.get(i).getAmount()==null)
																								||(arrVesselDistributionTxn.get(i).getAssetCode()==null)||(arrVesselDistributionTxn.get(i).getTxnMode()==null)){%>
																					<td><%=((AssetTransactions) arrVesselDistributionTxn.get(i)).getOperationId()%></td>
																					<td><%=((AssetTransactions) arrVesselDistributionTxn.get(i)).getSourceAccount()%></td>
																					<td></td>
																					<td></td>
																					<td><%=((AssetTransactions) arrVesselDistributionTxn.get(i)).getType()%></td>
																					<td></td>
																					<td></td>
																					<td><%=((AssetTransactions) arrVesselDistributionTxn.get(i)).getCreatedOn()%></td>
																					<%}else{ %>
																					<td><%=((AssetTransactions) arrVesselDistributionTxn.get(i)).getOperationId()%></td>
																					<td><%=((AssetTransactions) arrVesselDistributionTxn.get(i)).getSourceAccount()%></td>
																					<td><%=((AssetTransactions) arrVesselDistributionTxn.get(i)).getToAccount()%></td>
																					<td><%=((AssetTransactions) arrVesselDistributionTxn.get(i)).getAmount()%></td>
																					<td><%=((AssetTransactions) arrVesselDistributionTxn.get(i)).getType()%></td>
																					<td><%=((AssetTransactions) arrVesselDistributionTxn.get(i)).getAssetCode()%></td>
																					<td><%=hashPayMode.get(((AssetTransactions) arrVesselDistributionTxn.get(i)).getTxnMode())%></td>
																					<td><%=((AssetTransactions) arrVesselDistributionTxn.get(i)).getCreatedOn()%></td>
																					<% } %>

																				</tr>

																				<%
																						 }
																						} else {
																						%>

																				<tr>
																					<td colspan="9"><span
																						id="ops_all_bin_list_errormsg1">No Vessel
																							Distribution Transactions made</span></td>

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
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<form method="post" id="get-txn-form">
				<input type="hidden" name="qs" value=""> <input
					type="hidden" name="rules" value="">
			</form>
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



	<!-- Data tables js-->
	<script src="assets/plugins/datatable/jquery.dataTables.min.js"></script>
	<script src="assets/plugins/datatable/dataTables.bootstrap4.min.js"></script>
	<script src="assets/plugins/datatable/datatable-2.js"></script>
	<script src="assets/plugins/datatable/dataTables.responsive.min.js"></script>

	<!-- Parsley js-->
	<script src="assets/plugins/parsley/parsley.min.js"></script>
	<!-- Sweetalert js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.all.min.js"></script>

	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>

	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_ops_view_distribution_page.js"></script>



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
	
	if(portePublicKey!=null)portePublicKey= null;if(vesselPublicKey!=null)vesselPublicKey= null;
	if(porteDistributionAccountBal!=null)porteDistributionAccountBal= null;if(vesselDistributionAccountBal!=null)vesselDistributionAccountBal= null;
	if(arrPorteDistributionTxn!=null)arrPorteDistributionTxn= null;if(arrVesselDistributionTxn!=null)arrVesselDistributionTxn= null;
	if(hashPayMode!=null)hashPayMode= null;if(itPayMode!=null)itPayMode= null;if (context!=null)context=null;

}
%>
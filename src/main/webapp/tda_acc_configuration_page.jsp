<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*, com.pporte.utilities.Utilities,org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
String btcxAccount=null;
ArrayList<AssetAccount> accountBalances = null;
ArrayList <AssetAccount> arrAssetAccountDetails =null; 
ConcurrentHashMap<String,String> hashAccountType=null;
ConcurrentHashMap<String,String> hashStatus=null;
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
	}
	
	if(request.getAttribute("btcxaccount")!=null)	btcxAccount = (String)request.getAttribute("btcxaccount");
	if(request.getAttribute("accountbalances")!=null) accountBalances = (ArrayList<AssetAccount> )request.getAttribute("accountbalances");
	if(request.getAttribute("arraccountdetails")!=null) arrAssetAccountDetails = (ArrayList<AssetAccount>)request.getAttribute("arraccountdetails");
	
	hashAccountType=new ConcurrentHashMap<String, String>();
	hashStatus=new ConcurrentHashMap<String, String>();
	
	hashAccountType.put("IA", "Issuing Account");
	hashAccountType.put("DA", "Distribution Account");
	hashAccountType.put("LA", "Liquidity Account");
	
	hashStatus.put("A","Active");
	hashStatus.put("I","Inactive");
	
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
<title>Account Configuration Page</title>

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
<!---Sweetalert2 css-->
<link href="assets/plugins/sweetalert2/sweetalert2.css" rel="stylesheet" />
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
				<div class="side-app" style="height: 89vh;">

					<!-- page-header -->
					<div class="page-header" style="margin: auto">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#">BTCx Account</a></li>
							<li class="breadcrumb-item active" aria-current="page">Configuration</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- page -->
					<div class="page">

						<!-- page-content -->
						<div class="page-content">
							<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
								<div class="card">
									<div class="card-body myTab">
										<ul class="nav nav-tabs" id="myTab" role="tablist">
											<li class="nav-item"><a class="nav-link active show"
												id="account-tab" data-toggle="tab" href="#account"
												role="tab" aria-controls="account" aria-selected="true">BTCx
													Account</a></li>
											<li class="nav-item"><a class="nav-link"
												id="accountdetails-tab" data-toggle="tab"
												href="#accountdetails" role="tab"
												aria-controls="accountdetails" aria-selected="true">BTCx
													Account Details</a></li>
											<li class="nav-item"><a class="nav-link"
												id="trustline-tab" data-toggle="tab" href="#trustline"
												role="tab" aria-controls="trustline" aria-selected="false">Create
													Trustline</a></li>

										</ul>
										<div class="tab-content border p-3" id="myTabContent">
											<div class="tab-pane fade p-0 active show" id="account"
												role="tabpanel" aria-labelledby="account-tab">
												<%if (btcxAccount!=null) {%>
												<div class="col-xl-5">
													<div class="wallet-transaction">
														<% if( accountBalances!=null){
																							for(int i=0;i<accountBalances.size();i++){   
																								if(accountBalances.get(i).getAssetCode().equals("BTC")){ %>
														<div class="wallet-transaction-box clearfix">
															<div class="wallet-balance-ico">
																<img src="assets/images/crypto/bitcoin.svg"
																	alt="Litcoin" height="40" width="40">
															</div>
															<div class="wallet-transaction-name">
																<h3>Stellar Bitcoin</h3>
																<!--  <span>Last Updated</span> -->
															</div>
															<div class="wallet-transaction-balance">
																<h3><%=accountBalances.get(i).getAssetBalance()+" "%>BTC
																</h3>
																<!-- <span>`+porteDetails[i].lastUpdated+`</span> -->
															</div>
														</div>

														<%} else if(accountBalances.get(i).getAssetCode().equals("PORTE")){ %>
														<div class="wallet-transaction-box clearfix">
															<div class="wallet-balance-ico">

																<img src="assets/images/crypto/porte.svg" alt="PORTE"
																	height="40" width="40">
															</div>
															<div class="wallet-transaction-name">
																<h3>PORTE token</h3>
																<!--  <span>Last Updated</span> -->
															</div>
															<div class="wallet-transaction-balance">
																<h3><%=accountBalances.get(i).getAssetBalance()+" "%>PORTE
																</h3>
																<!-- <span>`+porteDetails[i].lastUpdated+`</span> -->
															</div>
														</div>

														<%}else if(accountBalances.get(i).getAssetCode().equals("USDC")){ %>

														<div class="wallet-transaction-box clearfix">
															<div class="wallet-balance-ico">
																<img src="assets/images/crypto/usdc.png" alt="Litcoin"
																	height="40" width="40">
															</div>
															<div class="wallet-transaction-name">
																<h3>USDC</h3>
																<!-- <span>Last Updated</span> -->
															</div>
															<div class="wallet-transaction-balance">
																<h3>
																	<%=accountBalances.get(i).getAssetBalance()+" "%>USDC
																</h3>
																<!-- <span>`+porteDetails[i].lastUpdated+`</span> -->
															</div>
														</div>

														<%}else if(accountBalances.get(i).getAssetCode().equals("VESL")){ %>
														<div class="wallet-transaction-box clearfix">
															<div class="wallet-balance-ico">
																<img src="assets/images/crypto/vessel.png" alt="VESL"
																	height="40" width="40">
															</div>
															<div class="wallet-transaction-name">
																<h3>Vessel</h3>
																<!--  <span>Last Updated</span> -->
															</div>
															<div class="wallet-transaction-balance">
																<h3>
																	<%=accountBalances.get(i).getAssetBalance()+" "%>VESL
																</h3>
																<!-- <span>`+porteDetails[i].lastUpdated+`</span> -->
															</div>
														</div>
														<%}else if(accountBalances.get(i).getAssetCode().equals("XLM")){ %>

														<div class="wallet-transaction-box clearfix">
															<div class="wallet-balance-ico">
																<img src="assets/images/crypto/xlm.svg" alt="Litcoin"
																	height="40" width="40">
															</div>
															<div class="wallet-transaction-name">
																<h3>STELLAR</h3>
																<!--  <span>Last Updated</span> -->
															</div>
															<div class="wallet-transaction-balance">
																<h3>
																	<%=accountBalances.get(i).getAssetBalance()+" "%>XLM
																</h3>
																<!-- <span>`+porteDetails[i].lastUpdated+`</span> -->
															</div>
														</div>


														<%} %>
														<% }							
																						}else{ %>
														<div>
															<span id="ops_all_bin_list_errormsg1">No External
																Wallets </span>
														</div>
														<% } %>

													</div>
												</div>

												<%}else{ %>
												<div class="container text-dark">
													<!-- row -->
													<div class="row justify-content-center align-self-center">
														<div class="col-xl-6 col-lg-12 col-md-12 col-sm-12">

															<div class="card-header">
																<h4>
																	<span id="title_label">Register or Link Stellar
																		BTCx Distribution Account</span>
																</h4>
															</div>
															<div class="card-body">
																<form id="register-stellar" method="post">

																	<div class="form-group">
																		<label class="form-label"> <span
																			id="question_label">Do you have a Stellar BTCx
																				Distribution Account?</span><span style="color: red;">*</span></label>
																		<div class="row">
																			<div class="col-3">
																				<label class="custom-control custom-radio">
																					<input type="radio" class="custom-control-input"
																					name="has_account"
																					onchange="fnStellarPresent('Yes')" value="true">
																					<span class="custom-control-label" id="yes_label">Yes</span>
																				</label>
																			</div>
																			<div class="col-3">
																				<label class="custom-control custom-radio">
																					<input type="radio" class="custom-control-input"
																					name="has_account"
																					onchange="fnStellarPresent('No')" value="false">
																					<span class="custom-control-label" id="no_label">No</span>
																				</label>
																			</div>
																		</div>
																	</div>
																	<div id="stellar-present" style="display: none;">
																		<div class="form-group">
																			<label class="form-label"><span
																				id="public_key_label"> Public Key</span><span
																				style="color: red;">*</span></label> <input type="text"
																				name="input_public_key" id="input_public_key"
																				class="form-control"
																				placeholder="Enter your Stellar account public key">
																		</div>
																	</div>
																	<div id="stellar-not-present" style="display: none;">
																		<div class="form-group">
																			<label class="custom-control custom-checkbox">
																				<input type="checkbox" class="custom-control-input"
																				name="create_stellar" value=""> <span
																				class="custom-control-label" id="checkbox_label">Click
																					the check box if you want to proceed to register
																					account</span>
																			</label>
																		</div>
																	</div>

																	<input type="hidden" name="qs" value=""> <input
																		type="hidden" name="rules" value="">
																</form>
																<div class="transfer-coin-button">
																	<button class="theme-btn"
																		style="background: 00008B; border: none; border-radius: 26px;"
																		id="btn-register-stellar-ac"
																		name="btn-register-stellar-ac"
																		onclick="javascript:fnRegisterStellarBTCxAccount()">
																		<span id="register_label">Register</span>
																	</button>
																</div>
															</div>

														</div>

													</div>
													<!-- row end -->
												</div>
												<%} %>
											</div>
											<div class="tab-pane fade p-0" id="trustline" role="tabpanel"
												aria-labelledby="trustline-tab">
												<div class="card-body">
													<form id="create-trustline-form" method="post">
														<div class="form-group">
															<label class="form-label">Select Wallet Asset <span
																style="color: red;">*</span></label> <select name="asset_value"
																id="asset_value" class="form-control custom-select"
																onchange="javascript:fnShowBtcxIssuerInput();return false;">
																<option selected disabled value="">Please
																	Select</option>
																<option
																	value="<%=NeoBankEnvironment.getStellarBTCxCode()%>">Stellar
																	Bitcoin</option>
																<option
																	value="<%=NeoBankEnvironment.getPorteTokenCode()%>"><%=NeoBankEnvironment.getPorteTokenCode()%></option>
																<option
																	value="<%=NeoBankEnvironment.getVesselCoinCode()%>"><%=NeoBankEnvironment.getVesselCoinCode()%></option>
															</select>
														</div>
														<div class="hidden" id="btcissueridinput">
															<div class="form-group">
																<label class="form-label"><span>BTCx
																		Issuer Account</span><span style="color: red;">*</span></label> <input
																	type="text" name="btcx_issuer" id="btcx_issuer"
																	class="form-control"
																	placeholder="Enter BTCx Issuer Public Key">
															</div>
														</div>
														<div class="form-group">
															<label class="form-label"><span>Private
																	Key</span> <span style="color: red;">*</span></label> <input
																type="password" name="input_private_key"
																id="input_private_key" class="form-control"
																placeholder="Enter Private Key">
														</div>
														<input type="hidden" name="qs" value=""> <input
															type="hidden" name="rules" value="">
													</form>
													<div class="transfer-coin-button">
														<button class="theme-btn" id="btn_create_trustline"
															name="btn_create_trustline"
															style="background: 00008B; border: none; border-radius: 26px;"
															onclick="javascript:fnCreateTrustline()">Create
															Trustline</button>
													</div>
												</div>
											</div>
											<div class="tab-pane fade p-0" id="accountdetails"
												role="tabpanel" aria-labelledby="accountdetails-tab">
												<div class="card-body">
													<div class="table-responsive">
														<table id="pendingmerchtable"
															class="table table-striped table-bordered text-nowrap w-100">
															<thead>
																<tr>
																	<th>Asset Code</th>
																	<th>Account Type</th>
																	<th>Public Key</th>
																	<th>Status</th>
																	<th>Created On</th>
																	<!--TODO:- Add Action button to edit details  -->
																</tr>
															</thead>
															<tbody>
																<% if( arrAssetAccountDetails!=null){%>
																<% for (int i=0;i<arrAssetAccountDetails.size();i++){ %>
																<tr>
																	<td><%=arrAssetAccountDetails.get(i).getAssetCode()%>
																	</td>
																	<td><%=hashAccountType.get(arrAssetAccountDetails.get(i).getAccountType())%>
																	</td>
																	<td><%=arrAssetAccountDetails.get(i).getPublicKey()%>
																	</td>
																	<td><%=hashStatus.get(arrAssetAccountDetails.get(i).getStatus())%>
																	</td>
																	<td><%=arrAssetAccountDetails.get(i).getCreatedOn()%>
																	</td>
																	<%} %>
																	<%}else{ %>
																
																<tr>
																	<td colspan="9"><span
																		id="ops_all_bin_list_errormsg1 text-danger"
																		style="color: red;"> Account for BTCX is yet to
																			be setup</span></td>
																</tr>
																<%}%>

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


	<!--Side-menu js-->
	<script src="assets/plugins/toggle-sidebar/sidemenu.js"></script>

	<!-- Sidebar Accordions js -->
	<script src="assets/plugins/accordion1/js/easyResponsiveTabs.js"></script>

	<!-- Custom scroll bar js-->
	<script
		src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>

	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>


	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>

	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_tda_account_configuration_page.js"></script>

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
	if (btcxAccount!=null)btcxAccount=null; if(accountBalances!=null) accountBalances=null; if (arrAssetAccountDetails!=null)arrAssetAccountDetails=null;
	if (hashStatus!=null) hashStatus=null;if (context!=null)context=null;
}
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*, com.pporte.utilities.Utilities,org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
List<Transaction> transactions = null;
ConcurrentHashMap<String, String> hashStatus = null;
ServletContext context = null;
String langPref=null;
String btnName="Details";
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callException(request, response, context,"Session has expired, please login again.");
		return;
	}
	if(request.getAttribute("transactions")!=null)	transactions = (List<Transaction>)request.getAttribute("transactions");
	if(request.getAttribute("langpref")!=null) langPref = (String)request.getAttribute("langpref");
	if(langPref.equalsIgnoreCase("en")) {
		 btnName="Details";
	}else{
		 btnName="Detalles";
	}
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
<title>View Remittance Transactions</title>
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
<!--Daterangepicker css-->
<link
	href="assets/plugins/bootstrap-daterangepicker/daterangepicker.css"
	rel="stylesheet" />
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
<style>
.modal_ly {
	color: #000;
	overflow-x: hidden;
	height: 100%;
	background-color: #FFF;
	background-repeat: no-repeat;
}

.card {
	z-index: 0;
	background-color: #ECEFF1;
	padding-bottom: 20px;
	margin-top: 90px;
	margin-bottom: 90px;
	border-radius: 10px;
}

.top {
	padding-top: 40px;
	padding-left: 13% !important;
	padding-right: 13% !important;
}

/*Icon progressbar*/
#progressbar {
	margin-bottom: 30px;
	overflow: hidden;
	color: #27a89d;
	padding-left: 0px;
	margin-top: 30px;
}

#progressbar li {
	list-style-type: none;
	font-size: 13px;
	width: 25%;
	float: left;
	position: relative;
	font-weight: 400;
}

#progressbar .step0:before {
	font-family: FontAwesome;
	content: "\f10c";
	color: #fff;
}

#progressbar li:before {
	width: 40px;
	height: 40px;
	line-height: 45px;
	display: block;
	font-size: 20px;
	background: #C5CAE9;
	border-radius: 50%;
	margin: auto;
	padding: 0px;
}

/*ProgressBar connectors*/
#progressbar li:after {
	content: '';
	width: 100%;
	height: 12px;
	background: #C5CAE9;
	position: absolute;
	left: 0;
	top: 16px;
	z-index: -1;
}

#progressbar li:last-child:after {
	border-top-right-radius: 10px;
	border-bottom-right-radius: 10px;
	position: absolute;
	left: -50%;
}

#progressbar li:nth-child(2):after, #progressbar li:nth-child(3):after {
	left: -50%;
}

#progressbar li:first-child:after {
	border-top-left-radius: 10px;
	border-bottom-left-radius: 10px;
	position: absolute;
	left: 50%;
}

#progressbar li:last-child:after {
	border-top-right-radius: 10px;
	border-bottom-right-radius: 10px;
}

#progressbar li:first-child:after {
	border-top-left-radius: 10px;
	border-bottom-left-radius: 10px;
}

/*Color number of the step and the connector before it*/
#progressbar li.active:before, #progressbar li.active:after {
	background: #27a89d;
}

#progressbar li.active:before {
	font-family: FontAwesome;
	content: "\f00c";
}

.icon {
	width: 60px;
	height: 60px;
	margin-right: 15px;
}

.icon-content {
	padding-bottom: 20px;
}

@media screen and (max-width: 992px) {
	.icon-content {
		width: 50%;
	}
}
.modal_ly {
	background: linear-gradient(0deg, rgba(0, 0, 0, 0.2), rgba(0, 0, 0, 0.2)), linear-gradient(180deg, #060441 0%, #2D2C4A 37.71%, #2E2E3C 84.56%);
}
#modal_title_transfer_details{
	color: #27a89d;
}
.close:not(:disabled):not(.disabled):focus, .close:not(:disabled):not(.disabled):hover {
	color: #fff;
	text-decoration: none;
	opacity: 1;
}
.modal-header {
	border-bottom: 1px solid #e9ecef;
}
.table-striped tbody tr:nth-of-type(2n) {
  background-color: transparent;
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
									id="breadcrumb-item-trade-currency">Trade Currency</span></a></li>
							<li class="breadcrumb-item active" aria-current="page"><span
								id="breadcrumb-item-view-remittance-transaction">View
									Remittance Transactions</span></li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->

					<!-- row -->
					<div class="row">
						<div class="col-md-12 col-lg-12">
							<div class="card" style="background: none;">
								<div class="card-header">
									<div class="card-title">
										<span id="spn_remittance_txn">Remittance Transactions</span>
									</div>
								</div>
								<div class="card-body">
									<div class="table-responsive">
										<table id="pendingtxntable"
											class="table card-table table-vcenter text-nowrap table-nowrap">
											<thead>
												<tr>
													<th class="wd-20p"><span id="th_date">Date</span></th>
													<th class="wd-15p"><span id="th_txn_code">Transaction
															Code</span></th>
													<th class="wd-15p"><span id="th_amunt">Amount</span></th>
													<th class="wd-15p"><span id="th_currdncy">Currency</span></th>
													<th class="wd-20p"><span id="th_status">Status</span></th>
													<th class="wd-20p"><span id="th_action">Action</span></th>
												</tr>
											</thead>
											<tbody>
												<% if(transactions!=null){
													for(int i=0;i<transactions.size();i++){
											%>
												<tr>
													<td><%= ((Transaction)transactions.get(i)).getTxnDateTime() %></td>
													<td><%= ((Transaction)transactions.get(i)).getTxnUserCode() %></td>
													<td><%= ((Transaction)transactions.get(i)).getTxnAmount() %></td>
													<td><%= ((Transaction)transactions.get(i)).getTxnCurrencyId() %></td>
													<%if (((Transaction)transactions.get(i)).getStatus().equals("PP")&& langPref.equalsIgnoreCase("en") ){ %>
													<td><button type="button"
															class="btn btn-warning btn-sm">Processing</button></td>
													<%}else if (((Transaction)transactions.get(i)).getStatus().equals("PP")&& langPref.equalsIgnoreCase("es") ){ %>
													<td><button type="button"
															class="btn btn-warning btn-sm">Procesando</button></td>
													<%}else if (((Transaction)transactions.get(i)).getStatus().equals("PT")&& langPref.equalsIgnoreCase("en")){ %>
													<td><button type="button"
															class="btn btn-danger btn-sm">Pending</button></td>
													<%}else if (((Transaction)transactions.get(i)).getStatus().equals("PT")&& langPref.equalsIgnoreCase("es")){ %>
													<td><button type="button"
															class="btn btn-danger btn-sm">Pendiente</button></td>
													<%}else if (((Transaction)transactions.get(i)).getStatus().equals("C")&& langPref.equalsIgnoreCase("en")){%>
													<td><button type="button"
															class="btn btn-success btn-sm">Completed</button></td>
													<%}else if (((Transaction)transactions.get(i)).getStatus().equals("C")&& langPref.equalsIgnoreCase("es")){%>
													<td><button type="button"
															class="btn btn-success btn-sm">Terminado</button></td>
													<%} %>
													<td class="text-center align-middle">
														<div class="btn-group align-top">
															<button class="btn btn-sm btn-primary badge"
																onClick="javascript:fnViewMore('<%= ((Transaction)transactions.get(i)).getStatus() %>',
															'<%= ((Transaction)transactions.get(i)).getTxnUserCode() %>', '<%= ((Transaction)transactions.get(i)).getShortTxnDate() %>')"
																type="button"><%=btnName %></button>
														</div>
													</td>
												</tr>

												<% }
											}else{ %>
												<tr>
													<td><span id="td_no_data_present">No data
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
			<!-- Message Modal -->
			<div class="modal fade" id="exampleModal3" tabindex="-1"
				role="dialog" aria-hidden="true">
				<div class="modal-dialog modal-lg " role="document">
					<div class="modal_ly modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="example-Modal3">
								<span id="modal_title_transfer_details">Transfer Details</span>
							</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">
							<div class="container px-1 px-md-4 py-5 mx-auto">
								<div class="card">
									<div class="row d-flex justify-content-between px-3 top">
										<div class="d-flex">
											<h5>
												<span id="spn_txn_code_header">Transaction Code</span> <span
													class="font-weight-bold" id="txn_code" style="color: darkblue !important;"></span>
											</h5>
										</div>
										<!-- <div class="d-flex flex-column text-sm-right">
												<p class="mb-0">Expected Arrival <span>01/12/19</span></p>
												<p>USPS <span class="font-weight-bold">234094567242423422898</span></p>
											</div> -->
									</div>
									<!-- Add class 'active' to progress -->
									<div class="row d-flex justify-content-center">
										<div class="col-12">
											<ul id="progressbar" class="text-center">
												<li class="active step0"></li>
												<li class="active step0"></li>
												<li class="step0" id="second_last_list"></li>
												<li class="step0" id="final_list"></li>
											</ul>
										</div>
									</div>
									<div class="row justify-content-between top">
										<div class="row d-flex icon-content">
											<!-- <img class="icon" src="https://i.imgur.com/9nnc9Et.png"> -->
											<div class="d-flex flex-column">
												<span id="date1"></span>
												<p style="font-weight: 400;">
													<span id="p_transfer">Transfer</span><br>
													<span id="p_set_up">Set up</span>
												</p>
											</div>
										</div>
										<div class="row d-flex icon-content">
											<!-- <img class="icon" src="https://i.imgur.com/u1AzR7w.png"> -->
											<div class="d-flex flex-column">
												<span id="date2"></span>
												<p style="font-weight: 400;" id="p_processing_transfer">Processing
													Transfer</p>

											</div>
										</div>
										<div class="row d-flex icon-content">
											<!-- <img class="icon" src="https://i.imgur.com/TkPm63y.png"> -->
											<div class="d-flex flex-column">
												<span></span>
												<p style="font-weight: 400; display: none;"
													id="money_on_its_way">
													<span id="spn_your_money">Your money</span> <br> <span
														id="spn_its_way">is on its way</span>
												</p>
											</div>
										</div>
										<div class="row d-flex icon-content">

											<div class="d-flex flex-column">
												<span></span>
												<p style="font-weight: 400; color: green; display: none;"
													id="received_money">
													<span id="spn_money_should_have">Money should have</span> <br>
													<span id="spn_arrived_receiver">arrived in receiver
														bank</span>
												</p>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">
								<span id="spn_close_close_btn">Close</span>
							</button>
							<!-- <button type="button" class="btn btn-primary">Send message</button> -->
						</div>
					</div>
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
	<script src="assets/plugins/datatable/dataTables.responsive.min.js"></script>
	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_cust_pending_currency_txn.js"></script>
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
	if (langPref!=null) langPref=null;if (context!=null)context=null;
}

%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
List<DisputeReasons> aryDisputeReasons = null;
ConcurrentHashMap<String,String> hashStatus = null;
Iterator itStatus=null;
ServletContext context = null;
String langPref = null;

try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callException(request, response, context,"Session has expired, please login again.");
		return;
	}
	if(request.getAttribute("dsptreason")!=null)
		aryDisputeReasons=(List<DisputeReasons>)request.getAttribute("dsptreason");
	hashStatus = new ConcurrentHashMap<String,String>();	hashStatus.put("A","Active"); hashStatus.put("C","Closed"); 
	hashStatus.put("P","In Progress");	itStatus = hashStatus.keySet().iterator();
	if(request.getAttribute("langPref")!=null) langPref = (String)request.getAttribute("langPref");
	
	NeoBankEnvironment.setComment(3, "[JSP]", "lang pref is "+langPref);

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
<title>Raise Dispute</title>

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
<!---Sweetalert2 css-->
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
			<!--app-header and sidebar-->
			<jsp:include page="cust_topandleftmenu.jsp" />
			<!-- app-content-->
			<div class="app-content  my-3 my-md-5 toggle-content">
				<div class="side-app">
					<!-- page-header -->
					<div class="page-header">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#"
								id="breadcrumb_item_label">Disputes</a></li>
							<li class="breadcrumb-item active" aria-current="page"
								id="breadcrumb_item_active_label">Raise Disputes</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->

					<!-- row -->
					<div class="row">
						<div class="col-lg-8 col-12 d-block">
							<div class="card">
							<div class="card-header">
											<h3 class="mb-0 card-title">
												<span id="label_raise_dispute">Raise Dispute</span>
											</h3>
										</div>
								<div class="card-body">
									<form id="raise-dipute-form" method="post">
										<% if(aryDisputeReasons!=null){%>
										<div class="form-group">
											<label id="label_sel_disp_reason">Select Dispute
												reason</label> <select class="form-control select2 custom-select"
												id="hdnreasonid" name="hdnreasonid" style="border-radius: 10px;"
												data-placeholder="Select Dispute Reason" >
												<option disabled="disabled" selected="selected"
													id="label_select_dispute">Select Dispute Reason</option>
												<% for (int i=0; i<aryDisputeReasons.size();i++){ 
																if(StringUtils.equals( ((DisputeReasons)aryDisputeReasons.get(i)).getStatus(),"A" )){ %>
												<option
													value="<%=((DisputeReasons)aryDisputeReasons.get(i)).getDisputeReasonId()%>"><%=((DisputeReasons)aryDisputeReasons.get(i)).getDisputeReasonDesc()%>
												</option>
												<% } 
															}  %>
											</select>
										</div>
										<%	 } %>
										<div class="form-group">
											<label class="form-label" id="label_transaction_code">Enter
												Transaction code (Optional)</label> <input type="text"
												name="inputtransactionid" id="inputtransactionid"
												class="form-control" name="example-text-input" style="border-radius: 10px;">
										</div>
										<div class="form-group">
											<label class="form-label" id="label_description">Enter
												Dispute description</label>
											<textarea class="form-control" name="dsptcomment"
												id="dsptcomment" rows="2" style="border-radius: 10px;"></textarea>
										</div>
											<button class="btn btn-block new-default-btn"onclick="javascript:fnSubmitDispute();return false;">
												<span id="label_send_btn">Send</span>
											 </button>
											 <input type="hidden" name="qs" value=""> <input type="hidden" name="rules" value="">
									</form>
								</div>
							</div>
						</div>

					</div>
					<!-- row end -->


				</div>

				<form method="post" id="get-page-form">
					<input type="hidden" name="qs" value=""> <input
						type="hidden" name="rules" value=""> <input type="hidden"
						name="hdnlang" id="hdnlangnav" value="">
				</form>

				<!--footer-->

				<!-- End Footer-->

			</div>
			<!-- End app-content-->
		</div>
	</div>
	<div id="error-msg-raise-dispute-page" style="display: none;">
		<span id="add-raise-dispute-data-validation-error-hdnreasonid">Please
			select dispute reason</span> <span
			id="add-raise-dispute-data-validation-error-dsptcomment-length">Dispute
			description must consist of at least 10 characters</span> <span
			id="add-raise-dispute-data-validation-error-dsptcomment-max-length">Dispute
			description must not exceed 100 characters</span> <span
			id="add-raise-dispute-data-validation-error-dsptcomment">Please
			enter Dispute description</span> <span id="swal_problem_connection">Problem
			with connection</span>
	</div>
	<!-- End Page -->

	<!-- Back to top -->
	<a href="#top" id="back-to-top"><i class="fa fa-angle-up"></i></a>

	<!-- End Page -->
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

	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>
	<script src="assets/js/_cust_raise_dispute.js"></script>
	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>

		<script>
		$(function() {
			console.log('language is ','<%=langPref%>')
			fnChangePageLanguage('<%=langPref%>');
		});
		
		</script>
</body>
</html>

<% 
}catch(Exception e){

}finally{
	if(aryDisputeReasons!=null) aryDisputeReasons=null; 
	if(hashStatus!=null) hashStatus = null; 
	if(itStatus!=null) itStatus = null;
	if (context!=null)context=null;
	
}

%>

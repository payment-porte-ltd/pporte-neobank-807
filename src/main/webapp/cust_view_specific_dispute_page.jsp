<%@page import="com.pporte.utilities.Utilities"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
List<DisputeTracker> aryDisputeTrackers = null;
Disputes m_Disputes = null;
ConcurrentHashMap<String,String> hashStatus = null;

Iterator<String> itStatus=null;
String disputeId = ""; 
String transactionId = ""; 
String reasonDesc = "";
String disputeStatus = "";
String disputeRaiseOn = "";	
String userComment = ""; 	
ServletContext context = null;
String langPref=null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callException(request, response, context,"Session has expired, please login again.");
		return;
	}
	
if(request.getAttribute("disputethreads")!=null)
	aryDisputeTrackers = (List)request.getAttribute("disputethreads");

if(request.getAttribute("showdispute")!=null)		m_Disputes = (Disputes)request.getAttribute("showdispute");
if(request.getAttribute("langPref")!=null) langPref = (String)request.getAttribute("langPref");
if(m_Disputes!=null){
	disputeId = m_Disputes.getDisputeId();
	transactionId = m_Disputes.getTransactionId();	
	reasonDesc = m_Disputes.getReasonDesc();
	disputeStatus = m_Disputes.getStatus();
	disputeRaiseOn = m_Disputes.getRaisedOn();
	userComment = m_Disputes.getUserComment();

}
hashStatus = new ConcurrentHashMap<String,String>();	
hashStatus.put("A","Active"); hashStatus.put("P","In Progress"); 
hashStatus.put("C","Closed"); 	
itStatus = hashStatus.keySet().iterator();

%>
<!doctype html>
<html lang="en" dir="ltr">
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Favicon -->
<link rel="icon" href="assets/images/brand/pporte.png"
	type="image/x-icon" />

<!-- Title -->
<title>View Specific Dispute</title>

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

<!---Sweetalert2 css-->
<link href="assets/plugins/sweetalert2/sweetalert2.css" rel="stylesheet" />

</head>

<style>
.hidden {
	display: none;
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
								id="breadcrumb_item_active_label">View Specific Dispute</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->

					<!-- row -->
					<div class="row justify-content-center align-self-center">
						<div class="col-lg-12 col-xl-8 col-md-12 col-sm-12 ">
							<div class="card">
								<form id="dispute-details-form" method="post">
									<div class="card-header">
										<h3 class="card-title">
											<span id="label_specific_dispute">Specific Dispute</span>
										</h3>
									</div>
									<div class="card-body">
										<div class="row">
											<div class="col-lg-6 col-md-12">
												<div class="form-group">
													<label for="exampleInputname" id="label_disputeid">Dispute
														Id</label> <input type="text" class="form-control"
														name="disputeid" value="<%=disputeId%>" readonly>
												</div>
											</div>
											<div class="col-lg-6 col-md-12">
												<div class="form-group">
													<label for="exampleInputname1" id="label_dispute_reason">Dispute
														Reason</label> <input type="text" class="form-control"
														value="<%=reasonDesc%>" readonly>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-lg-6 col-md-12">
												<div class="form-group">
													<label for="exampleInputname" id="label_transaction_id">Transaction
														Id</label> <input type="text" class="form-control"
														value="<%=transactionId%>" readonly>
												</div>
											</div>
											<div class="col-lg-6 col-md-12">
												<div class="form-group">
													<label for="exampleInputname1" id="label_raisedon">Raised
														on</label> <input type="text" class="form-control"
														value="<%=disputeRaiseOn%>" readonly>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-lg-6 col-md-12">
												<div class="form-group">
													<label for="exampleInputnumber" id="label_dispute_desc">Dispute
														description</label> <input type="text" class="form-control"
														value="<%=userComment%>" readonly>
												</div>
											</div>
											<div class="col-lg-6 col-md-12">
												<div class="form-group">
													<label for="exampleInputname1" id="label_dispute_status">Dispute
														Status</label>
													<%if(!disputeStatus.equalsIgnoreCase("C")){ %>
													<select class="form-control select2 custom-select"
														id="selstatus" name="selstatus"
														onChange="javascript: fnShowUpdateButton(); return false">
														<% if(hashStatus!=null){%>
														<% while (itStatus.hasNext()){
																		String tempStatus = (String)itStatus.next();
																		if( StringUtils.equals(tempStatus , disputeStatus)   ){ %>
														<option value="<%=disputeStatus%>" selected><%=hashStatus.get(disputeStatus)%>
														</option>
														<% }else{  %>
														<option value="<%=tempStatus%>"><%=hashStatus.get(tempStatus)%>
														</option>
														<% } 
																	}
																	}  %>
													</select>
													<%}else{ %>
													<input type="text" class="form-control" value="Closed"
														readonly>
													<%} %>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-lg-12 col-md-12">
												<div class="form-group">
													<h4>
														<span id="label_header_dispute_comment">Dispute
															Comment</span>
													</h4>
													<%  if(aryDisputeTrackers!=null) { 
															for(int i=0; i < aryDisputeTrackers.size(); i++ ){%>
													<p class="list-group-item">
														<strong><span id="label_date">Date:</span> </strong><%= ((DisputeTracker)aryDisputeTrackers.get(i)).getLastUpdated() %></p>
													<p class="list-group-item">
														<strong><span id="label_commented_by">Commented
																By:</span> </strong><%= ((DisputeTracker)aryDisputeTrackers.get(i)).getUpdaterId() %></p>
													<p class="list-group-item"><%= ((DisputeTracker)aryDisputeTrackers.get(i)).getUpdaterComment()%></p>
													<%}
														} %>
												</div>
											</div>
										</div>


									</div>
									<div class="card-footer">
										<%if(!disputeStatus.equalsIgnoreCase("C")){ %>
										<a href="#" class="btn theme-btn mt-1 mb-3 float-left"
											data-toggle="modal" data-target="#exampleModal"><span
											id="label_add_comment">Add comment</span></a>
										<div class="hidden" id="updatebutton">
											<a href="#" class="btn theme-btn-secondary mt-1 mb-3 float-right"
												onclick="javascript:fnUpdateStatus('<%=disputeId%>')"> <span
												id="label_update_status">Update Status</span></a>
										</div>

										<%}%>
									</div>
									<input type="hidden" name="qs" value=""> <input
										type="hidden" name="rules" value=""> <input
										type="hidden" name="hdndispid" value="">
								</form>
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
				<form method="post" id="update-status-form">
					<input type="hidden" name="qs" value=""> <input
						type="hidden" name="rules" value=""> <input type="hidden"
						name="hdndispid" value=""> <input type="hidden"
						name="hdnstatus" value=""> <input type="hidden"
						name="hdnlang" id="hdnlangnav" value="">
				</form>
				<form method="post" id="view-dispute-form">
					<input type="hidden" name="qs" value=""> <input
						type="hidden" name="rules" value=""> <input type="hidden"
						name="hdnreqid" value=""> <input type="hidden"
						name="hdnlang" id="hdnlangnav" value="">
				</form>
				<!--footer-->

				<!-- End Footer-->

			</div>
			<!-- End app-content-->
		</div>
	</div>
	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content" style="background: linear-gradient(0deg, rgba(0, 0, 0, 0.2), rgba(0, 0, 0, 0.2)), linear-gradient(180deg, #060441 0%, #2D2C4A 37.71%, #2E2E3C 84.56%);">
				<form id="add-comment-form" method="post">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">
							<span id="label_add_dispute_comment" class="text-white">Add Dispute Comment</span>
						</h5>
						<button type="button" class=" text-white close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">X</span>
						</button>
					</div>
					<div class="modal-body">
						<textarea class="form-control" name="comment" id="comment"
							rows="6"></textarea>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn theme-btn-secondary"
							data-dismiss="modal">
							<span id="label_close" class="text-white">Close</span>
						</button>
						<button type="button" class="btn theme-btn"
							onclick="javascript:fnAddComment('<%=disputeId%>')">
							<span id="label_send_comment" >Send Comment</span>
						</button>
					</div>
					<input type="hidden" name="qs" value=""> <input
						type="hidden" name="rules" value=""> <input type="hidden"
						name="hdndispid" value="">
				</form>
			</div>
		</div>
	</div>
	<div id="error-msg-add-commet-page" style="display: none;">
		<span id="add-comment-data-validation-error">Please enter
			Dispute Comment</span> <span id="add-comment-data-validation-error-length">Dispute
			Comment must not exceed 250 characters</span> <span
			id="swal_connection_prob">Problem with connection</span>
	</div>
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

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>

	<!-- Sidebar Accordions js -->
	<script src="assets/plugins/accordion1/js/easyResponsiveTabs.js"></script>

	<!-- Custom scroll bar js-->
	<script
		src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>

	<!-- Rightsidebar js -->
	<script src="assets/plugins/sidebar/sidebar.js"></script>
	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>

	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_cust_view_specific_dispute.js"></script>
	<script type="text/javascript">
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
	if(aryDisputeTrackers!=null) aryDisputeTrackers=null; if(m_Disputes!=null) m_Disputes = null; if(hashStatus!=null) hashStatus = null;
	if(itStatus!=null) itStatus=null; if(disputeId!=null) disputeId = null; if(transactionId!=null) transactionId = null;
	if(reasonDesc!=null) reasonDesc = null; 
	if(disputeRaiseOn!=null) disputeRaiseOn=null;  if(userComment!=null) userComment = null;
	if(disputeStatus!=null) disputeStatus=null; if (context!=null)context=null;
	if (langPref!=null)langPref=null;	
}

%>

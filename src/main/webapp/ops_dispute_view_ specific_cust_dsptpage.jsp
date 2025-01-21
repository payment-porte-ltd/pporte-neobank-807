<%@page import="com.pporte.utilities.Utilities"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
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
String referenceNo="";
String raisedBy="";
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}
	
if(request.getAttribute("disputethreads")!=null)
	aryDisputeTrackers = (List)request.getAttribute("disputethreads");

if(request.getAttribute("showdispute")!=null)		m_Disputes = (Disputes)request.getAttribute("showdispute");
if(m_Disputes!=null){
	disputeId = m_Disputes.getDisputeId();
	transactionId = m_Disputes.getTransactionId();	
	reasonDesc = m_Disputes.getReasonDesc();
	disputeStatus = m_Disputes.getStatus();
	disputeRaiseOn = m_Disputes.getRaisedOn();
	userComment = m_Disputes.getUserComment();
	referenceNo = m_Disputes.getReferenceNo();
	raisedBy = m_Disputes.getRaisedbyUserId();

}
hashStatus = new ConcurrentHashMap<String,String>();	
hashStatus.put("A","Active"); hashStatus.put("P","In Progress"); 
hashStatus.put("C","Closed"); 	
itStatus = hashStatus.keySet().iterator();

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
<title>View Specific Dispute</title>

<!--Bootstrap.min css-->
<link rel="stylesheet"
	href="assets/plugins/bootstrap/css/bootstrap.min.css">
<!---Sweetalert2 css-->
<link href="assets/plugins/sweetalert2/sweetalert2.css" rel="stylesheet" />

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
							<li class="breadcrumb-item"><a href="#">Manage Disputes</a></li>
							<li class="breadcrumb-item active" aria-current="page">View
								Specific Dispute</li>
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
										<h3 class="card-title">Specific Dispute</h3>
									</div>
									<div class="card-body">
										<div class="row">
											<div class="col-lg-6 col-md-12">
												<div class="form-group">
													<label for="exampleInputname">Reference Number</label> <input
														type="text" class="form-control" id="referenceno"
														name="referenceno" value="<%=referenceNo%>" readonly>
												</div>
											</div>
											<div class="col-lg-6 col-md-12">
												<div class="form-group">
													<label for="exampleInputname1">Raised By </label> <input
														type="text" class="form-control" value="<%=raisedBy%>"
														readonly>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-lg-6 col-md-12">
												<div class="form-group">
													<label for="exampleInputname">Dispute Id</label> <input
														type="text" class="form-control" name="disputeid"
														value="<%=disputeId%>" readonly>
												</div>
											</div>
											<div class="col-lg-6 col-md-12">
												<div class="form-group">
													<label for="exampleInputname1">Dispute Reason</label> <input
														type="text" class="form-control" value="<%=reasonDesc%>"
														readonly>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-lg-6 col-md-12">
												<div class="form-group">
													<label for="exampleInputname">Transaction Id</label> <input
														type="text" class="form-control"
														value="<%=transactionId%>" readonly>
												</div>
											</div>
											<div class="col-lg-6 col-md-12">
												<div class="form-group">
													<label for="exampleInputname1">Raised on</label> <input
														type="text" class="form-control"
														value="<%=disputeRaiseOn%>" readonly>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-lg-6 col-md-12">
												<div class="form-group">
													<label for="exampleInputnumber">Dispute description</label>
													<input type="text" class="form-control"
														value="<%=userComment%>" readonly>
												</div>
											</div>
											<div class="col-lg-6 col-md-12">
												<div class="form-group">
													<label for="exampleInputname1">Dispute Status</label>
													<%if(!disputeStatus.equalsIgnoreCase("C")){ %>
													<select class="form-control select2 custom-select"
														id="selstatus" name="selstatus"
														onChange="javascript: fnShowUpdateButton(); return false;">
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
													<h4>Dispute Comment</h4>
													<%  if(aryDisputeTrackers!=null) { 
															for(int i=0; i < aryDisputeTrackers.size(); i++ ){%>
													<p class="list-group-item">
														<strong>Date: </strong><%= ((DisputeTracker)aryDisputeTrackers.get(i)).getLastUpdated() %></p>
													<p class="list-group-item">
														<strong>Commented By: </strong><%= ((DisputeTracker)aryDisputeTrackers.get(i)).getUpdaterId() %></p>
													<p class="list-group-item"><%= ((DisputeTracker)aryDisputeTrackers.get(i)).getUpdaterComment()%></p>
													<%}
														} %>
												</div>
											</div>
										</div>


									</div>
									<div class="card-footer">
										<%if(!disputeStatus.equalsIgnoreCase("C")){ %>
										<a href="#" class="btn btn-primary mt-1 mb-3 float-left"
											data-toggle="modal" data-target="#exampleModal">Add
											comment</a> <a href="#"
											id="ops_specific_merch_dispute_btnupdate_status"
											style="display: none"
											class="btn btn-primary mt-1 mb-3 float-right"
											onclick="javascript:fnUpdateStatus('<%=disputeId%>')">Update
											Status</a>
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
						type="hidden" name="rules" value="">
				</form>
				<form method="post" id="update-status-form">
					<input type="hidden" name="qs" value=""> <input
						type="hidden" name="rules" value=""> <input type="hidden"
						name="hdndispid" value=""> <input type="hidden"
						name="hdnstatus" value="">
				</form>
				<form method="post" id="view-dispute-form">
					<input type="hidden" name="qs" value=""> <input
						type="hidden" name="rules" value=""> <input type="hidden"
						name="hdnreqid" value="">
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
			<div class="modal-content">
				<form id="add-comment-form" method="post">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">Add Dispute
							Comment</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">X</span>
						</button>
					</div>
					<div class="modal-body">
						<textarea class="form-control" name="comment" id="comment"
							rows="6"></textarea>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-primary"
							onclick="javascript:fnAddComment('<%=disputeId%>')">Send
							Comment</button>
					</div>
					<input type="hidden" name="qs" value=""> <input
						type="hidden" name="rules" value=""> <input type="hidden"
						name="hdndispid" value="">
				</form>
			</div>
		</div>
	</div>
	<div id="error-msg-add-commet-page" style="display: none;">
		<span id="add-user-data-validation-error-fname">Please enter
			Dispute Comment</span> <span
			id="add-user-data-validation-error-fname-length">Dispute
			Comment must consist of at least 10 characters</span>
	</div>
	<!-- End Page -->

	<!-- Back to top -->
	<a href="#top" id="back-to-top"><i class="fa fa-angle-up"></i></a>

	<!-- Jquery js-->
	<script src="assets/js/vendors/jquery-3.2.1.min.js"></script>

	<!--Bootstrap.min js-->
	<script src="assets/plugins/bootstrap/popper.min.js"></script>
	<script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>

	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>

	<!--Side-menu js-->
	<script src="assets/plugins/toggle-sidebar/sidemenu.js"></script>

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
	<script src="assets/js/_ops_dispute_view_ specific_cust_dsptpage.js"></script>

</body>
</html>

<% 
}catch(Exception e){

}finally{
	if(aryDisputeTrackers!=null) aryDisputeTrackers=null; if(m_Disputes!=null) m_Disputes = null; if(hashStatus!=null) hashStatus = null;
	if(itStatus!=null) itStatus=null; if(disputeId!=null) disputeId = null; if(transactionId!=null) transactionId = null;
	if(reasonDesc!=null) reasonDesc = null; 
	if(disputeRaiseOn!=null) disputeRaiseOn=null;  if(userComment!=null) userComment = null;
	if(disputeStatus!=null) disputeStatus=null; if(raisedBy!=null) raisedBy=null; if (referenceNo!=null)referenceNo=null;
	if (context!=null)context=null;
}

%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*, com.pporte.model.*,	com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%

ArrayList<MccGroup> allMCCList = null; 	Customer m_Customer = null; 	ArrayList<String> arrkycDocs = null; 
ConcurrentHashMap<String, String> hashStatus = null; ConcurrentHashMap<String, String> hashGender = null; 

String relationshipno = null;  String customerid = null; String customerName = null; String nationalId = null; String passportNo = null;
String gender = null; String custEmail = null; String contact = null; String physicalAddress = null; String dateOfBirth = null; String status = null;
String loginTries = null; String  pinTries = null; String expirydate = null; String createdOn = null; String activatedon = null; 
Iterator<String> itstatus = null; Iterator<String> itGender = null; String approvedBy = null;
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}
		
	if (request.getAttribute("kycdocslist") != null)  arrkycDocs = (ArrayList<String>) request.getAttribute("kycdocslist");
	if (request.getAttribute("specificcustomer") != null)  m_Customer = (Customer) request.getAttribute("specificcustomer");
	
	hashStatus = new ConcurrentHashMap<String, String>();	
	hashGender= new ConcurrentHashMap<String,String>();
	
	hashStatus.put("A", "Active");		
	hashStatus.put("I", "Inactive");		
	hashStatus.put("P", "Pending");
	
	hashGender.put("M", "Male");
	hashGender.put("F", "Female");
	hashGender.put("O", "Other");
    
	itGender = hashGender.keySet().iterator();
	itstatus = hashStatus.keySet().iterator();
    
    if(m_Customer!=null){
    	relationshipno = m_Customer.getRelationshipNo();
    	customerid = m_Customer.getCustomerId();
    	customerName= m_Customer.getCustomerName();
    	nationalId= m_Customer.getNationalId();
    	passportNo= m_Customer.getPassportNo();
    	gender= m_Customer.getGender();
    	custEmail = m_Customer.getEmail();
    	contact = m_Customer.getContact();
		physicalAddress = m_Customer.getAddress();
		dateOfBirth= m_Customer.getDateOfBirth();
		status = m_Customer.getStatus();
		loginTries = m_Customer.getLoginTries();
		pinTries = m_Customer.getPinTries();
		expirydate = m_Customer.getExpiry();
		createdOn = m_Customer.getCreatedOn();
		activatedon = m_Customer.getActivatedOn();
		approvedBy = m_Customer.getApprovedBy();
	
	}
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
<title>Customer Details</title>

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
<!-- Parsley -->
<link href="assets/plugins/parsley/parsley.css" rel="stylesheet">
<!--Sweetaler css-->
<link href="assets/plugins/sweetalert2/sweetalert2.css">
<!-- Date Picker css-->
<link href="assets/plugins/date-picker/spectrum.css" rel="stylesheet" />


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
							<li class="breadcrumb-item"><a href="#">Dashboard</a></li>
							<li class="breadcrumb-item active" aria-current="page">Pending
								Customer</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- row -->
					<div class="row">
						<div class="col-md-12">
							<div class="card">
								<form id="editcust-form" method="post">
									<div class="card-header">
										<h3 class="mb-0 card-title">Customer Details</h3>
									</div>
									<div class="card-body">
										<div class="row">
											<div class="col-xl-6">
												<div class="form-group">
													<label class="form-label">Customer Code</label> <input
														type="text" class="form-control" name="relno" id="relno"
														value="<%=relationshipno%>" readonly>
												</div>
												<div class="form-group">
													<label class="form-label">License Number</label> <input
														type="text" class="form-control" name="nationalid"
														id="nationalid" value="<%=nationalId%>">
												</div>
												<div class="form-group">
													<label class="form-label">Passport Number</label> <input
														type="text" class="form-control" name="passportno"
														id="passportno" value="<%=passportNo%>">
												</div>
												<div class="form-group">
													<label class="form-label">Phone Number</label> <input
														type="text" class="form-control" name="custphoneno"
														id="custphoneno" value="<%=contact%>">
												</div>
												<div class="form-group">
													<label class="form-label">Gender</label> <select
														class="form-control" id="selgender" name="selgender">
														<% while (itGender.hasNext()){
																String tempGender = itGender.next();
																if(tempGender.equals(gender)){ %>
														<option value="<%=tempGender%>" selected>
															<%=hashGender.get(tempGender)%>
														</option>
														<% }else{ %>
														<option value="<%=tempGender%>">
															<%=hashGender.get(tempGender) %>
														</option>
														<% }
														  }%>
													</select>
												</div>
												<div class="form-group">
													<label class="form-label">Status</label>
													<%if (status.equals("P")){ %>
													<select class="form-control" id="selcuststatus"
														name="selcuststatus" disabled>
														<% while (itstatus.hasNext()){
																String tempStatus = itstatus.next();
																if(tempStatus.equals(status)){ %>
														<option value="<%=tempStatus%>" selected>
															<%=hashStatus.get(tempStatus)%>
														</option>
														<% }else{ %>
														<option value="<%=tempStatus%>">
															<%=hashStatus.get(tempStatus) %>
														</option>
														<% }
														  }%>
													</select>
													<%}else { %>
													<select class="form-control" id="selcuststatus"
														name="selcuststatus">
														<% while (itstatus.hasNext()){
																String tempStatus = itstatus.next();
																if(tempStatus.equals(status)){ %>
														<option value="<%=tempStatus%>" selected>
															<%=hashStatus.get(tempStatus)%>
														</option>
														<% }else{ %>
														<option value="<%=tempStatus%>">
															<%=hashStatus.get(tempStatus) %>
														</option>
														<% }
														  }%>
													</select>
													<%} %>

												</div>

												<%-- 	<div class="form-group">
													<label class="form-label">PIN Attempt</label>
													<input type="text" class="form-control" name="pintries"  id="pintries" value="<%=pinTries%>">
												</div> --%>
												<div class="form-group">
													<label class="form-label">Date Created</label>
													<div class="wd-200 mg-b-30">
														<div class="input-group">
															<div class="input-group-prepend">
																<div class="input-group-text">
																	<i class="fa fa-calendar tx-16 lh-0 op-6"></i>
																</div>
															</div>
															<input class="form-control fc-datepicker"
																placeholder="MM/DD/YYYY" type="text" name="createdon"
																id="createdon" value="<%=createdOn%>">
														</div>
													</div>
												</div>
											</div>
											<div class="col-xl-6">
												<div class="form-group">
													<label class="form-label">Customer Name</label> <input
														type="text" class="form-control" name="custname"
														id="custname" value="<%=customerName%>">
												</div>
												<div class="form-group">
													<label class="form-label">Email Address</label> <input
														type="text" class="form-control" name="custemail"
														id="custemail" value="<%=custEmail%>">
												</div>
												<div class="form-group">
													<label class="form-label">Physical Address</label> <input
														type="text" class="form-control" name="physicaladdress"
														id="physicaladdress" value="<%=physicalAddress%>">
												</div>

												<div class="form-group">
													<label class="form-label">Date of Birth</label> <input
														type="text" class="form-control" name="custdob"
														id="custdob" value="<%=dateOfBirth%>">
												</div>


												<div class="form-group">
													<label class="form-label">Expiry Date</label>
													<div class="wd-200 mg-b-30">
														<div class="input-group">
															<div class="input-group-prepend">
																<div class="input-group-text">
																	<i class="fa fa-calendar tx-16 lh-0 op-6"></i>
																</div>
															</div>
															<input class="form-control fc-datepicker"
																placeholder="MM/DD/YYYY" type="text" name="expiry"
																id="expiry" value="<%=expirydate%>">
														</div>
													</div>
												</div>
												<%if (status.equals("P")==false){ %>
												<div class="form-group">
													<label class="form-label">Approved By</label> <input
														type="text" class="form-control" name="approvedby"
														id="approvedby" value="<%=approvedBy%>" readonly>
												</div>
												<%} %>
											</div>
										</div>
										<div class="row justify-content-md-center">
											<div class="form-group">
												<label class="form-label" style="color: #fff;">Submit</label>
												<%if (status.equals("P")){ %>
												<div class="col-xl-8">
													<a class="btn btn-primary text-white mr-2"
														onclick="javascript:fnApproveCustomer();"
														style="width: 200px;"> <span> Approve Customer</span>
													</a>
												</div>
												<%} else { %>
												<div class="col-xl-8">
													<a class="btn btn-primary text-white mr-2"
														id="btn-editcustomer" style="width: 200px;"> <span>
															Edit Details </span>
													</a>
												</div>
												<%} %>
											</div>
										</div>
									</div>
									<input type="hidden" name="qs" value=""> <input
										type="hidden" name="rules" value=""> <input
										type="hidden" name="hdnuserid" value=""> <input
										type="hidden" name="hdnlangpref" id="hdnlangpref3" value="en">
									<input type="hidden" name="hdncuststatus" value=""> <input
										type="hidden" name="hdncustgender" value="">
								</form>
							</div>
						</div>
					</div>
					<!-- row -->
					<!-- row -->
					<div class="row">
						<div class="col-md-12 col-lg-12">
							<div class="e-panel card">
								<div class="card-header">
									<h4>Documents</h4>
								</div>
								<div class="card-body">
									<div class="table-responsive">
										<table class="table" id="zero_config">
											<thead class="bg-info text-white">
												<tr>
													<th>File name</th>
												</tr>
											</thead>
											<tbody>
												<% if (arrkycDocs!=null){
							                            for(int i=0; i<arrkycDocs.size(); i++ ) { 
							                                String temp =  arrkycDocs.get(i);	%>

												<tr>
													<td><a href="#"
														onClick="javascript: fngetFile('<%=temp%>','<%=relationshipno%>' );return false;">
															<%=temp.substring(temp.lastIndexOf("/")+1, temp.length())%>
													</a></td>
												</tr>

												<%	}
							                            }else{		%>
												<tr>
													<td>No Documents present</td>
												</tr>

												<%} %>
											</tbody>
										</table>
									</div>

								</div>
							</div>
							<!-- section-wrapper -->
						</div>
					</div>
					<!-- row end -->

					<form id="getfile-form" method="post">
						<input type="hidden" name="qs" value=""> <input
							type="hidden" name="rules" value=""> <input type="hidden"
							name="hdnassetpath" value=""> <input type="hidden"
							name="relno" value="">
					</form>

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

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>

	<!-- Sidebar Accordions js -->
	<script src="assets/plugins/accordion1/js/easyResponsiveTabs.js"></script>

	<!-- Custom scroll bar js-->
	<script
		src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>

	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_ops_cust_editcustomerpage.js"></script>
	<!-- Sweetalert js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.all.min.js"></script>

	<!--Jquery Validator js-->
	<script
		src="assets/plugins/jquery-validation-1.19.2/dist/jquery.validate.min.js"></script>


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
}catch(Exception e){ 
	out.println ("Exception : "+e.getMessage());
}finally{
	if(allMCCList !=null) allMCCList=null; if(m_Customer !=null) m_Customer=null; if(arrkycDocs !=null) arrkycDocs=null; if(hashStatus !=null) hashStatus=null;
	if(hashGender !=null) hashGender=null;if(loginTries !=null) loginTries=null; if(itGender !=null) itGender=null;
	if(customerid !=null) customerid=null; if(customerName !=null) customerName=null;if(itstatus !=null) itstatus=null;
	if(nationalId !=null) nationalId=null; if(passportNo !=null) passportNo=null; if(gender !=null) gender=null; 
	if(custEmail !=null) custEmail=null; if(physicalAddress !=null) physicalAddress=null; if(contact !=null) contact=null;
	if(dateOfBirth !=null) dateOfBirth=null; if(createdOn !=null) createdOn=null; if(activatedon !=null) activatedon=null;
	if(expirydate !=null) expirydate=null; if (context!=null)context=null;

}
%>
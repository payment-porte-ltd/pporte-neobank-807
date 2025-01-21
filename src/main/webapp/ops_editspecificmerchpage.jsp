<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="ops_error.jsp"%>
<%@ page
	import="com.pporte.*, com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%

ArrayList<MccGroup> allMCCList = null; 	Merchant m_Merchant = null; 	ArrayList<String> arrkycDocs = null; ArrayList<Merchant> arrMerchUserDetails = null;
ConcurrentHashMap<String, String> hashStatus = null; ConcurrentHashMap<String, String> hashMerchantType = null; ConcurrentHashMap<String, String> hashMerchantHierarchy = null;
String companyName = null; String merchantCode = null; String merchantRef = null; String physicalAddress = null; String mccId = null; String status = null;
String businessDescription = null; String businessType = null; String createdon = null; String merchantType = null; String merchantId = null; String businessPhone = null;
String approvedBy = null; Iterator<String> itMerchType = null; Iterator<String> itstatus = null; String expirydate = null;	String verifyMerchFlag = null;
ArrayList<Risk> arrMerchRisks = null;String riskid = "";
ServletContext context = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callOpsException(request, response, context,"Session has expired, please login again.");
		return;
	}
	if (request.getAttribute("mcclist") != null)  allMCCList = (ArrayList<MccGroup>) request.getAttribute("mcclist");
	if (request.getAttribute("kycdocslist") != null)  arrkycDocs = (ArrayList<String>) request.getAttribute("kycdocslist");
	if (request.getAttribute("specificmerchant") != null)  m_Merchant = (Merchant) request.getAttribute("specificmerchant");
	if (request.getAttribute("verifymerchantflag") != null)  verifyMerchFlag = (String) request.getAttribute("verifymerchantflag");
	if (request.getAttribute("userdetails") != null)  arrMerchUserDetails = (ArrayList<Merchant>) request.getAttribute("userdetails");
	if (request.getAttribute("merchrisk") != null)  arrMerchRisks = (ArrayList<Risk>) request.getAttribute("merchrisk");
	
	hashStatus = new ConcurrentHashMap<String, String>();	
	hashMerchantType= new ConcurrentHashMap<String,String>();
	hashMerchantHierarchy= new ConcurrentHashMap<String,String>();
	
	hashStatus.put("A", "Active");		
	hashStatus.put("I", "Inactive");		
	hashStatus.put("V", "Verification");
	hashStatus.put("P", "Pending");
	
	hashMerchantType.put("PM", "Principal Merchant");
	hashMerchantType.put("SM", "Subsidiary Merchant");
	hashMerchantType.put("SA", "Stand-Alone Merchant");
	
	hashMerchantHierarchy.put("S", " Super User ");
    hashMerchantHierarchy.put("N", "Normal User");
    
	itMerchType = hashMerchantType.keySet().iterator();
	itstatus = hashStatus.keySet().iterator();
    
    if(m_Merchant!=null){
    	 		
		merchantCode = 	m_Merchant.getMerchantCode();
		merchantRef = m_Merchant.getMerchRef();
		companyName= m_Merchant.getCompanyName();
		businessDescription = m_Merchant.getBusinessDescription();
		businessPhone = m_Merchant.getBusinessPhoneNumber();
		physicalAddress = m_Merchant.getPhysicalAddress();
		approvedBy= m_Merchant.getApprovedBy();
		merchantType =  m_Merchant.getMerchantType();
		status = m_Merchant.getStatus();
		mccId = m_Merchant.getMccCategoryId();
		createdon = m_Merchant.getCreatedOn();
		expirydate = m_Merchant.getExpiryDate();
		expirydate = m_Merchant.getExpiryDate();
		//riskid = m_Merchant.getMccCategoryId();	
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
<link rel="shortcut icon" type="image/x-icon"
	href="assets/images/brand/pporte.png" />

<!-- Title -->
<title>Merchant Details</title>

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
								Merchant</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<!-- row -->
					<div class="row">
						<div class="col-md-12">
							<div class="card">
								<form id="editmerchant-form" method="post">

									<div class="card-header">
										<h3 class="mb-0 card-title">Merchant Details</h3>
									</div>
									<div class="card-body">
										<div class="row">
											<div class="col-xl-6">
												<div class="form-group">
													<label class="form-label">Merchant Code</label> <input
														type="text" class="form-control" name="merchantcode"
														id="merchantcode" value="<%=merchantCode%>" readonly>
												</div>
												<div class="form-group">
													<label class="form-label">Company Name</label> <input
														type="text" class="form-control" name="companyname"
														id="companyname" value="<%=companyName%>">
												</div>
												<div class="form-group">
													<label class="form-label">Business Phone Number</label> <input
														type="text" class="form-control" name="businessphone"
														id="businessphone" value="<%=businessPhone%>">
												</div>
												<div class="form-group">
													<label class="form-label">Mcc Group</label> <select
														class="form-control" id="selmcccat" name="selmcccat">
														<% for(int i=0;i<allMCCList.size(); i++){ 
															if( ((MccGroup)allMCCList.get(i)).getMccId().equals(mccId)){ %>
														<option
															value="<%=((MccGroup)allMCCList.get(i)).getMccId()%>"
															selected>
															<%=((MccGroup)allMCCList.get(i)).getMccName()%></option>
														<% }else {%>
														<option
															value="<%=((MccGroup)allMCCList.get(i)).getMccId()%>">
															<%= ((MccGroup)allMCCList.get(i)).getMccName()   %></option>
														<% } 
														}%>
													</select>
												</div>
												<div class="form-group">
													<label class="form-label">Createdon</label> <input
														type="text" class="form-control" name="createdon"
														id="createdon" value="<%=createdon%>">
												</div>
												<div class="form-group">
													<label class="form-label">Status</label> <select
														class="form-control" id="selstatus" name="selstatus">
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
												</div>
												<div class="form-group">
													<label class="form-label">Risk Profile</label> <select
														class="form-control" id="selmerchrisk" name="selmerchrisk">
														<% for(int i=0;i<arrMerchRisks.size(); i++){ 
															if( ((Risk)arrMerchRisks.get(i)).getRiskId().equals(riskid)){ %>
														<option
															value="<%=((Risk)arrMerchRisks.get(i)).getRiskId()%>"
															selected>
															<%=((Risk)arrMerchRisks.get(i)).getRiskDesc()%></option>
														<% }else {%>
														<option
															value="<%=((Risk)arrMerchRisks.get(i)).getRiskId()%>">
															<%= ((Risk)arrMerchRisks.get(i)).getRiskDesc()   %></option>
														<% } 
														}%>
													</select>
												</div>
											</div>
											<div class="col-xl-6">
												<div class="form-group">
													<label class="form-label">Bill Reference</label> <input
														type="text" class="form-control" name="billref"
														id="billref" value="<%=merchantRef%>" readonly>
												</div>
												<div class="form-group">
													<label class="form-label">Business Description</label> <input
														type="text" class="form-control"
														name="bussinessdescription" id="bussinessdescription"
														value="<%=businessDescription%>">
												</div>
												<div class="form-group">
													<label class="form-label">Physical Address</label> <input
														type="text" class="form-control" name="physicaladdress"
														id="physicaladdress" value="<%=physicalAddress%>">
												</div>
												<div class="form-group">
													<label class="form-label">Merchant Type</label> <select
														class="form-control" id="selsmerchtype"
														name="selsmerchtype">
														<% while (itMerchType.hasNext()){
																String tempMerchType = itMerchType.next();
																if(tempMerchType.equals(merchantType)){ %>
														<option value="<%=tempMerchType%>" selected>
															<%=hashMerchantType.get(tempMerchType)%>
														</option>
														<% }else{ %>
														<option value="<%=tempMerchType%>">
															<%=hashMerchantType.get(tempMerchType) %>
														</option>
														<% }
														  }%>
													</select>
												</div>
												<%-- <div class="wd-200 mg-b-30">
												 <div class="form-group">
													<label class="form-label">Expiry Date</label>
													<input type="text" class="form-control" name="expiry"  id="expiry" value="<%=expirydate%>" >
	
 												<div class="input-group-prepend">
													<div class="input-group-text">
														<i class="fa fa-calendar tx-16 lh-0 op-6"></i>
													</div>
												</div>
 												<input class="form-control fc-datepicker" placeholder="MM/DD/YYYY" type="text" name="expiry"  id="expiry" value="<%=expirydate%>">
													
												</div> 
												</div> --%>
												<%-- <div class="input-group">
													<div class="input-group-prepend">
														<div class="input-group-text">
															<i class="fa fa-calendar tx-16 lh-0 op-6"></i>
														</div>
													</div>
													<input class="form-control fc-datepicker" placeholder="MM/DD/YYYY" type="text" name="approvedby"  id="approvedby" value="<%=expirydate%>">
												</div> --%>
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
												<div class="form-group">
													<label class="form-label">Approved By</label> <input
														type="text" class="form-control" name="approvedby"
														id="approvedby" value="<%= approvedBy%>" readonly>
												</div>
												<div class="form-group">
													<div class="col-xl-6">
														<a class="btn btn-primary text-white mr-2"
															id="btn-verifymerch" data-toggle="modal"
															data-target="#newriskModal" style="width: 150px";> <span>
																Submit </span>
														</a>
													</div>
												</div>

											</div>

										</div>
									</div>
									<input type="hidden" name="qs" value=""> <input
										type="hidden" name="rules" value=""> <input
										type="hidden" name="hdnuserid" value=""> <input
										type="hidden" name="hdnlangpref" id="hdnlangpref3" value="en">
									<input type="hidden" name="hdnmerchtype" value=""> <input
										type="hidden" name="hdnmerchstatus" value=""> <input
										type="hidden" name="hdnmccgroup" value=""> <input
										type="hidden" name="hdnriskprf" value=""> <input
										type="hidden" name="hdnactivatemerch"
										value="<%=verifyMerchFlag%>">
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
									<h4>Users</h4>
								</div>

								<div class="card-body">
									<div class="e-table">
										<div class="table-responsive table-lg">
											<table class="table table-bordered">
												<thead>
													<tr>
														<th>UserId</th>
														<th>Username</th>
														<th>Contact</th>
														<th>National ID</th>
														<th>Email Address</th>
														<th>Roles</th>
														<th>Status</th>
														<th>Designation</th>
														<th>Action</th>
													</tr>
												</thead>
												<tbody>
													<%
														if (arrMerchUserDetails != null) {
															for (int i = 0; i < arrMerchUserDetails.size(); i++) {
														%>
													<tr>
														<td class="text-nowrap align-middle"><%=((Merchant) arrMerchUserDetails.get(i)).getMerchanUsertId()%>
														</td>
														<td class="text-nowrap align-middle"><%=((Merchant) arrMerchUserDetails.get(i)).getMerchanUsertName()%>
														</td>
														<td class="text-nowrap align-middle"><%=((Merchant) arrMerchUserDetails.get(i)).getMerchanUsercontact()%>
														</td>
														<td class="text-nowrap align-middle"><%=((Merchant) arrMerchUserDetails.get(i)).getMerchanUserNationalId()%>
														</td>
														<td class="text-nowrap align-middle"><%=((Merchant) arrMerchUserDetails.get(i)).getMerchanUserEmail()%>
														</td>
														<td class="text-nowrap align-middle"><%=hashMerchantHierarchy.get(((Merchant) arrMerchUserDetails.get(i)).getMerchanUserHierarchy())%>
														</td>
														<td class="text-nowrap align-middle"><%=hashStatus.get(((Merchant) arrMerchUserDetails.get(i)).getStatus())%>
														</td>
														<td class="text-nowrap align-middle"><%=((Merchant) arrMerchUserDetails.get(i)).getMerchUserDesignation()%>
														</td>
														<td class="text-center align-middle">
															<div class="btn-group align-top">
																<button class="btn btn-sm btn-primary badge"
																	data-target="#user-form-modal" data-toggle="modal"
																	type="button"
																	onClick="javascript:fnEditMerchantUser('<%=((Merchant) arrMerchUserDetails.get(i)).getMerchanUsertId()%>','<%=((Merchant) arrMerchUserDetails.get(i)).getMerchanUsertName()%>',
																		'<%=((Merchant) arrMerchUserDetails.get(i)).getMerchanUsercontact()%>','<%=((Merchant) arrMerchUserDetails.get(i)).getMerchanUserEmail()%>',
																		'<%=((Merchant) arrMerchUserDetails.get(i)).getMerchanUserHierarchy()%>','<%=((Merchant) arrMerchUserDetails.get(i)).getStatus()%>',
																		'<%=((Merchant) arrMerchUserDetails.get(i)).getMerchUserDesignation()%>','<%=((Merchant) arrMerchUserDetails.get(i)).getMerchanUserNationalId()%>',
																		'<%=((Merchant) arrMerchUserDetails.get(i)).getMerchanUserNationalId()%>' );return false;">
																	<i class="fa fa-edit"></i>Edit
																</button>
															</div>
														</td>

														<%
																	}
																} else {
																%>
													
													<tr>
														<td colspan="9"><span id="ops_all_bin_list_errormsg1">No
																Merchant users </span></td>
													</tr>
													<%
														}
														%>

												</tbody>
											</table>
										</div>
									</div>
									<!-- table-wrapper -->
								</div>
							</div>
							<!-- section-wrapper -->
						</div>
					</div>
					<!-- row end -->
					<!-- Modal -->
					<div class="modal fade" id="editMerchUserModal" tabindex="-1"
						role="dialog" aria-labelledby="largemodal" aria-hidden="true">
						<div class="modal-dialog modal-lg " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="largemodal1">User Details</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">X</span>
									</button>
								</div>
								<div class="modal-body">
									<!-- row -->
									<div class="row">
										<div class="col-md-12">
											<div class="card">
												<form id="editmerchuser-form" method="post">
													<div class="card-body">
														<div class="row">
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">Merchant UserId</label> <input
																		type="text" class="form-control" name="merchuid"
																		id="merchuid" readonly>
																</div>
																<div class="form-group">
																	<label class="form-label">Contact</label> <input
																		type="text" class="form-control"
																		name="merchusercontact" id="merchusercontact">
																</div>
																<div class="form-group">
																	<label class="form-label">User Role</label> <select
																		class="form-control" id="sellmerchuserherarchy"
																		name="sellmerchuserherarchy">
																		<option value="S">Super User</option>
																		<option value="N">Normal User</option>
																	</select>
																</div>
																<div class="form-group">
																	<label class="form-label">Designation</label> <input
																		type="text" class="form-control" name="merchuserdesig"
																		id="merchuserdesig">

																</div>
																<div class="form-group">
																	<label class="form-label">Createdon</label> <input
																		type="text" class="form-control" name="expiry"
																		id="expiry" value="<%=createdon%>" readonly>
																</div>
															</div>
															<div class="col-xl-6">
																<div class="form-group">
																	<label class="form-label">User Name</label> <input
																		type="text" class="form-control" name="merchusername"
																		id="merchusername">
																</div>
																<div class="form-group">
																	<label class="form-label">Email Address</label> <input
																		type="text" class="form-control" name="merchuseremail"
																		id="merchuseremail">
																</div>
																<div class="form-group">
																	<label class="form-label">Status</label> <select
																		class="form-control" id="sellmerchuserstatus"
																		name="sellmerchuserstatus">
																		<option value="A">Active</option>
																		<option value="P">Pending</option>
																		<option value="I">Inactive</option>
																	</select>
																</div>
																<div class="form-group">
																	<label class="form-label">National Id</label> <input
																		type="text" class="form-control" name="nationalid"
																		id="nationalid">
																</div>

															</div>

														</div>
													</div>
													<input type="hidden" name="qs" value=""> <input
														type="hidden" name="rules" value=""> <input
														type="hidden" name="hdnuserid" value=""> <input
														type="hidden" name="hdnlangpref" id="hdnlangpref3"
														value="en"> <input type="hidden" name="hdnstatus"
														value=""> <input type="hidden" name="hdnuserrole"
														value="">
												</form>
											</div>
										</div>
									</div>
									<!-- row -->
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary"
										data-dismiss="modal">Close</button>
									<button type="button" class="btn btn-primary"
										id="btn-editmerchuser">Submit</button>
								</div>
							</div>
						</div>
					</div>
					<!-- End Model -->
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
							                                String temp =  arrkycDocs.get(i);	
							                                
							                                NeoBankEnvironment.setComment(3, "jsp", "file is "+ arrkycDocs.get(i));%>

												<tr>
													<td><a
														onClick="javascript: fngetFile('<%=arrkycDocs.get(i)%>');return false;">
															<%=arrkycDocs.get(i)%>
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

					<form id="getfile-form" method="post">
						<input type="hidden" name="qs" value=""> <input
							type="hidden" name="rules" value=""> <input type="hidden"
							name="hdnassetpath" value="">
					</form>

					<!-- row end -->
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

	<!-- i18next js-->
	<script src="assets/plugins/i18next/i18next.min.js"></script>

	<!--Side-menu js-->
	<script src="assets/plugins/toggle-sidebar/sidemenu.js"></script>

	<!-- Sidebar Accordions js -->
	<script src="assets/plugins/accordion1/js/easyResponsiveTabs.js"></script>

	<!-- Custom scroll bar js-->
	<script
		src="assets/plugins/scroll-bar/jquery.mCustomScrollbar.concat.min.js"></script>

	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<script src="assets/js/_ops_editspecificmerchpage.js"></script>
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
	if(allMCCList !=null) allMCCList=null; if(m_Merchant !=null) m_Merchant=null; if(arrkycDocs !=null) arrkycDocs=null;
	if(arrMerchUserDetails !=null) arrMerchUserDetails=null;  if(arrMerchRisks !=null) arrMerchRisks=null;
	 if (context!=null)context=null;
}
%>
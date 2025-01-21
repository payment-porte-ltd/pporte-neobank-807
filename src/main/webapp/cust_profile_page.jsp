<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*, com.pporte.utilities.Utilities,org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
String relationshipNo=null;
ServletContext context = null;
String langPref=null;
try{
	
	if ( !request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callException(request, response, context,"Session has expired, please login again.");
		return;
	}
	if(request.getAttribute("langpref")!=null) langPref = (String)request.getAttribute("langpref");

	relationshipNo= ((User)session.getAttribute("SESS_USER")).getRelationshipNo();
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
<title>Profile Page</title>

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

<!-- Forn-wizard css-->
<link href="assets/plugins/forn-wizard/css/forn-wizard.css"
	rel="stylesheet" />
<link href="assets/plugins/formwizard/smart_wizard.css" rel="stylesheet">
<link href="assets/plugins/formwizard/smart_wizard_theme_dots.css"
	rel="stylesheet">

<style>
.tabs-menu1 ul li .active {
	border-bottom: 3px solid #00a89d;
	color: #00a89d;
}

.tabs-menu1 ul li a {
	color: grey;
}

.breadcrumb-item+.breadcrumb-item::before {
	content: ">";
}
</style>

</head>

<body class="app sidebar-mini rtl" onload="fnGetRelno()">

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
				<div class="side-app" style="height: 89vh;">

					<!-- page-header -->

					<div class="page-header"
						style="margin: auto; margin-top: 20px; margin-bottom: 20px;">
						<ol class="breadcrumb">
							<!-- breadcrumb -->
							<li class="breadcrumb-item"><a href="#"
								style="font-size: 15px;"><span
									id="id_profile_breadcrumb">Profile</span></a></li>
							<li class="breadcrumb-item active" aria-current="page"
								style="font-size: 15px; margin: 3px;"><span
								id="id_view_and_edit_breadcrumb">View and Edit</span></li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->

					<!-- row -->
					<div class="row">
						<div class="col-md-12">
							<div class="card-profile  overflow-hidden"
								style="background-image: url('assets/images/background/profile_picture background.svg'); background-repeat: no-repeat; background-attachment: fixed; background-size: auto; padding-right: 0; padding-left: 0;border-top-left-radius:18px;border-top-right-radius:18px;">
								<div class="card-body"
									style="padding-bottom: 0;">
									<div class="row">
										<div class="col-md-12">
											<div class="row">
												<div class="col-md-8">
													 <a class="media-left" ><img alt="Logo" style="margin-bottom:10px;border-radius:0;" class="avatar avatar-md brround" src="assets/images/icons/pporte-logo.svg"></a>
													<h3 style="margin-bottom:0px;">
														<span id="custname"
															style="font-size: 25px; color: #fff;"></span>
													</h3>
													<p>
														<span id="userid" style="font-size: 18px; color: white;"></span>
													</p>
													<button class="btn theme-btn-sm"
														onclick="javascript:fnEditProfileButton();return false;"><i
														class="fas fa-pencil-alt" aria-hidden="true"
														data-toggle="modal" data-target="#exampleModal3"></i><span
														id="edit_profile_btn_label"> Edit profile</span>
													</button>
												</div>
											</div>
										</div>
									</div>
									<div class="row justify-content-end">
										<div class=" tab-menu-heading" style="border: none;">
											<div class="tabs-menu1 ">
												<!-- Tabs -->
												<ul class="nav panel-tabs">

													<li><a href="#tabs-icons-text-1" class="active"
														data-toggle="tab"><i class="fa fa-user mr-2"></i><span
															id="spn_personal_details" class="text-white">Personal Details</span></a></li>
													<li><a href="#tabs-icons-text-2" data-toggle="tab"><i
															class="fa fa-picture-o mr-2"></i><span id="spn_document"  class="text-white">Documents</span></a></li>
													<li><a href="#tabs-icons-text-3" data-toggle="tab"><i
															class="fa fa-cog mr-2"></i><span id="spn_security" class="text-white">Security</span></a></li>
												</ul>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="card">
								<div class="card-body pb-0">
									<div class="tab-content" id="myTabContent">
										<div class="tab-pane fade active show" id="tabs-icons-text-1"
											role="tabpanel" aria-labelledby="tabs-icons-text-1-tab">
											<div class="table-responsive mb-3">
												<table class="table row table-borderless porte-card-content w-100 m-0">
													<tbody class="col-lg-6 p-0">
														<tr>
															<td><strong id="full_name_label">Full Name
																	:</strong> <span id="name"></span></td>
														</tr>
														<tr>
															<td><strong id="address_label">Address :</strong> <span
																id="address"></span></td>
														</tr>
														<tr>
															<td><strong id="contact_label">Contact :</strong> <span
																id="contact"></span></td>
														</tr>
														<tr>
															<td><strong id="dob_label">DOB :</strong><span
																id="dob"></span></td>
														</tr>
														<tr>
															<td><strong id="status_label">Status :</strong><span
																id="status"></span></td>
														</tr>
													</tbody>
													<tbody class="col-lg-6 p-0">
														<tr id="license_div">
															<td><strong id="license_no_label">License
																	No :</strong> <span id="nationalid"></span></td>
														</tr>
														<tr id="passport_div">
															<td><strong id="passport_no_label">Passport
																	No :</strong> <span id="passportno"></span></td>
														</tr>
														<tr>
															<td><strong id="email_label">Email :</strong> <span
																id="email"></span></td>
														</tr>
														<tr>
															<td><strong id="gender_label">Gender :</strong> <span
																id="gender"></span></td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
										<div aria-labelledby="tabs-icons-text-2-tab"
											class="tab-pane fade" id="tabs-icons-text-2" role="tabpanel">
											<div class="row">
												<div class="col-md-12">
													<div class="content content-full-width" id="content">
														<!-- begin profile-content -->
														<div class="profile-content">
															<!-- begin tab-content -->
															<div class="tab-content p-0">
																<!-- begin #profile-friends tab -->
																<div class="tab-pane fade in active show"
																	id="profile-friends">
																	<h4 class="mt-0 mb-4" id="documents_uploaded_label">Documents
																		Uploaded</h4>
																	<!-- begin row -->
																	<div class="row row-space-2">
																		<!-- end col-6 -->
																		<div class="col-xl-8">
																			<div class="mb-2 border shadow">
																				<div id="docarea"></div>

																			</div>
																		</div>
																	</div>
																	<!-- end row -->
																</div>
																<!-- end #profile-friends tab -->
															</div>
															<!-- end tab-content -->
														</div>
														<!-- end profile-content -->
													</div>
												</div>
											</div>
										</div>


										<div class="tab-pane fade" id="tabs-icons-text-3"
											role="tabpanel" aria-labelledby="tabs-icons-text-3-tab">
											<p style="font-size:18px;">Select one of the following to: </p>
											<div class="row">
											
												<div class="col-6">
												 
													<button type="button"  class="theme-btn btn-block float-left" 
														 onclick="javascript:fnRetrieveKeys();return false;" >
														<span  style="color: white;">Retrieve Private Keys</span>
													</button>
												</div>
												<div class="col-6">
													<button type="button" class="theme-btn-secondary btn-block float-right" 
														 onclick="javascript:fnChangePassword();return false;" >
														<span style="color: white;">Change Password</span>
													</button>
												</div>
												
												
											</div>
											<!--row open-->
											<div class="hidden row" id="div_wizard">
												<div class="col-12">
													<div class="card" style="margin-top:20px;">
														<div class="card-body">
														<h3 class="card-title" id="keys_retrival_header">Keys
																Retrieval</h3>
															<form method="post" id="mnemonic-words-form">
																<div id="smartwizard-3">
																	<ul>
																		<li><a href="#step-10" id="first_to_fourth_label">1st
																				to 4th Word</a></li>
																		<li><a href="#step-11" id="5th_to_8th_label">5th
																				to 8th Word</a></li>
																		<li><a href="#step-12"
																			id="9th_to_12th_word_label">9th to 12th Word</a></li>
																	</ul>
																	<div>

																		<div id="step-10" class="">
																			<div class="row">
																				<div class="col form-group">
																					<label id="1st_word_label">1st Word</label> <input
																						type="text" class="form-control styled-input-field" id="input1word"
																						name="input1word" placeholder="Enter 1st Word"
																						required>
																				</div>
																				<div class="col form-group">
																					<label id="2nd_word_label">2nd Word</label> <input
																						type="text" class="form-control styled-input-field" id="input2word"
																						name="input2word" placeholder="Enter 2nd Word"
																						required>
																				</div>
																			</div>

																			<div class="row">
																				<div class="col form-group">
																					<label id="3rd_word_label">3rd Word</label> <input
																						type="text" class="form-control styled-input-field" id="input3word"
																						name="input3word" placeholder="Enter 3rd Word"
																						required>
																				</div>

																				<div class="col form-group">
																					<label id="4th_word_label">4th Word</label> <input
																						type="text" class="form-control styled-input-field" id="input4word"
																						name="input4word" placeholder="Enter 4th Word"
																						required>
																				</div>
																			</div>
																		</div>
																		<div id="step-11" class="">
																			<div class="row">
																				<div class="col form-group">
																					<label id="5th_word_label">5th Word</label> <input
																						type="text" class="form-control styled-input-field" id="input5word"
																						name="input5word" placeholder="Enter 5th Word"
																						required>
																				</div>

																				<div class="col form-group">
																					<label id="6th_word_label">Enter 6th Word</label>
																					<input type="text" class="form-control styled-input-field"
																						id="input6word" name="input6word"
																						placeholder="Enter 6th Word" required>
																				</div>
																			</div>

																			<div class="row">
																				<div class="col form-group">
																					<label id="7th_word_label">7th Word</label> <input
																						type="text" class="form-control styled-input-field" id="input7word"
																						name="input7word" placeholder="Enter 7th Word"
																						required>
																				</div>

																				<div class="col form-group">
																					<label id="8th_word_label">8th Word</label> <input
																						type="text" class="form-control styled-input-field" id="input8word"
																						name="input8word" placeholder="Enter 8th Word"
																						required>
																				</div>
																			</div>

																		</div>
																		<div id="step-12" class="">
																			<div class="row">
																				<div class="col form-group">
																					<label id="9th_word_label">9th Word</label> <input
																						type="text" class="form-control styled-input-field" id="input9word"
																						name="input9word" placeholder="Enter 9th Word"
																						required>
																				</div>

																				<div class="col form-group">
																					<label id="10th_word_label">10th Word</label> <input
																						type="text" class="form-control styled-input-field" id="input10word"
																						name="input10word" placeholder="Enter 10th Word"
																						required>
																				</div>
																			</div>

																			<div class="row">
																				<div class="col form-group">
																					<label id="11th_word_label">11th Word</label> <input
																						type="text" class="form-control styled-input-field" id="input11word"
																						name="input11word" placeholder="Enter 11th Word"
																						required>
																				</div>

																				<div class="col form-group">
																					<label id="12th_word_label">12th Word</label> <input
																						type="text" class="form-control styled-input-field" id="input12word"
																						name="input12word" placeholder="Enter 12th Word"
																						required>
																				</div>
																			</div>

																		</div>


																	</div>
																</div>
															</form>
														</div>
													</div>
												</div>
											</div>
											<div class="hidden" id="div_password">
												<div class="col-12">
													<div class="card" style="margin-top:20px;">
														<div class="card-body">
														<h3 class="card-title" id="change_password_label">Change
																Password</h3>
															<form id="set-password-form" method="post">
																<div class="form-group mb-4">
																<label class="text-white"><span id="password-label">Password</span></label>
																<div class="input-group">
																	<div class="input-group-prepend">
																      <div class="input-group-text appended-icon" >
																        <img width="17" src="assets/images/icons/password.svg" >
																      </div>
																    </div>
																	<input type="password" style=" border-bottom-right-radius: 10px; border-top-right-radius: 10px;"class="form-control appended-input" placeholder="Enter your Current Password"
																	name="old_password" id="old_password" > 
																	 <span id="show-password" class="eye-slash-postion"><i class="fa fa-eye" style="font-size: 17px; color:#27A89D;"
									                                  onclick="showc()"></i></span>
									                                 <span id="hide-password" class="hidden eye-slash-postion"><i class="fa fa-eye-slash" style="font-size: 17px;color:#27A89D;"
									                                  onclick="hidec()"></i></span>
																</div>
															</div>
																
																<input type="hidden" name="relno"
																	value="<%=relationshipNo%>">
															</form>
															<div class="row">
																<div class="col-12">
																	<button type="button" id="btn-test-submit"
																		name="btn-test-submit"
																		class="theme-btn btn-block">
																		<span id="id_submit_btn_label">Submit</span>
																	</button>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
											<!--row closed-->
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- row end -->


					<!-- Message Modal -->
					<div class="modal fade" id="exampleModal3" tabindex="-1"
						role="dialog" aria-hidden="true">
						<div class="modal-dialog" role="document">
							<div class="modal-content"  style="background: linear-gradient(0deg, rgba(0, 0, 0, 0.2), rgba(0, 0, 0, 0.2)), linear-gradient(180deg, #060441 0%, #2D2C4A 37.71%, #2E2E3C 84.56%);">
								<div class="modal-header">
									<h5 class="modal-title" id="example-Modal3">
										<span id="spn_edit_profile" class="text-white">Edit Profile</span>
									</h5>
									<button type="button" class="text-white close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">X</span>
									</button>
								</div>
								<div class="modal-body">
									<form method="post" id="edit-form">
										<div class="form-group">
											<label for="recipient-name" class="form-control-label text-white"
												id="address_label" >Address:</label> <input type="text"
												class="form-control styled-input-field" name="editaddress" id="editaddress">
										</div>
										<input type="hidden" name="qs" value=""> <input
											type="hidden" name="rules" value=""> <input
											type="hidden" name="relno" value="">
									</form>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn theme-btn-secondary"
										data-dismiss="modal">
										<span id="spn_close_btn">Close</span>
									</button>
									<button type="button" class="btn theme-btn"
										onclick="javascript:fnSubmitEditDetails();return false;">
										<span id="spn_submit_btn">Submit</span>
									</button>
								</div>
							</div>
						</div>
					</div>


				</div>
			</div>
			<!-- End app-content-->

		</div>
		<form method="post" id="profile-form">
			<input type="hidden" name="qs" value=""> <input type="hidden"
				name="rules" value=""> <input type="hidden" name="relno"
				value=""> <input type="hidden" name="hdnassetpath" value="">
			<input type="hidden" name="usertype" value=""> <input
				type="hidden" name="relno" value="">
		</form>
		<form method="post" id="get-page-form">
			<input type="hidden" name="qs" value=""> <input type="hidden"
				name="rules" value=""> <input type="hidden" name="hdnlang"
				>
		</form>

	</div>
	<!-- End Page -->
	<div id="error-msg-login-page" style="display: none;">
		<span id="data-validation-error-swal-header">Oops..</span> <span
			id="data-validation-problem-with-connection">Problem with
			connection</span> <span id="data-validation-check-your-data">Please
			check your data</span> <span
			id="data-validation-failed-toget-customer-details">Failed to
			get customer details</span> <span id="data-validation-please-enter-address">Please
			Enter Address</span> <span id="data-validation-success-header">Success</span>
		<span id="data-validation-profile-edited-successful-text">Profile
			Edited Successful</span> <span
			id="data-validation-profile-edite-failed-text">There was a
			problem in updating your profile, please try again</span> <span
			id="data-validation-please-fill-out-this-field">Please fill
			out this field</span> <span id="data-validation-are-you-sure">Are you
			sure?</span> <span id="data-validation-this-the-correct-sequence">This
			the correct sequence</span> <span id="data-validation-yes-button">Yes</span>
		<span id="data-validation-no-button">No</span> <span
			id="data-validation-enter-password-label">Enter your Password</span>
		<span id="data-validation-password-input-label">Password</span> <span
			id="data-validation-password-inpu-error">Please input your
			password!</span> <span id="data-validation-enter-your-current-passsword">Please
			enter your current password</span> <span
			id="data-validation-enter-your-password">Please enter your new
			password</span> <span id="data-validation-password-is-too-weak">Your
			Password is too weak</span> <span
			id="data-validation-password-does-not-match">Confirm password
			does not match New password</span> <span id="data-validation-ok-btn">Ok</span>
		<span id="data-validation-cancel-btn">Cancel</span> <span
			id="data-validation-finish-btn">Finish</span>
	</div>


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
	<!-- Custom js-->
	<script src="assets/js/custom.js"></script>
	<!--Sweetalert2 js-->
	<script src="assets/plugins/sweetalert2/sweetalert2.js"></script>

	<!-- Forn-wizard js-->
	<script src="assets/plugins/formwizard/jquery.smartWizard.js"></script>

	<!-- Custom js-->
	<script src="assets/js/_cust_profile_page.js"></script>


	<script>
		var relno='';
		function fnGetRelno(){
			 relno = '<%=relationshipNo%>';
			 fnCallCustDetails();
		}
		
		 $(document).ready(function(){
			 console.log('language is ','<%=langPref%>')
			 fnChangePageLanguage('<%=langPref%>');
		 });
		
		
		</script>
</body>
</html>


<%
}catch (Exception e){
	out.println ("Exception : "+e.getMessage());
}finally{
	if ( relationshipNo!=null); relationshipNo=null;
	if (context!=null) context=null;
	if (langPref!=null) langPref=null;

}
%>
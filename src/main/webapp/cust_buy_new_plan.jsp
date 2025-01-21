<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*, com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
ArrayList<PricingPlan> arrPricingPlan = null;	   String isActivePlanId = "";
Double currentPlanPrice=0.0;
ServletContext context = null;
String langPref = null;
try{
	if(!request.isRequestedSessionIdValid()) {
	    // Session is expired
	    context = getServletContext();
		Utilities.callException(request, response, context,"Session has expired, please login again.");
		return;
	}
		if(request.getAttribute("langPref")!=null) langPref = (String)request.getAttribute("langPref");
		if(request.getAttribute("pricingplans")!=null)	arrPricingPlan = (ArrayList<PricingPlan>)request.getAttribute("pricingplans");
		if(request.getAttribute("currentplanprice")!=null)	currentPlanPrice = (Double)request.getAttribute("currentplanprice");
	
		
	 isActivePlanId = ((User)session.getAttribute("SESS_USER")).getPricingPlanid();
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
<title>Plans</title>

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

<!-- Custom css -->
<link href="assets/css/app.css" rel="stylesheet" />
<!-- Responsive css -->
<link href="assets/css/responsive.css" rel="stylesheet" />

<!-- Custom css -->
<link href="assets/css/style2.css" rel="stylesheet" />

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
				<div class="side-app" style="height: 89vh;">

					<!-- page-header -->
					<div class="page-header">
						<ol class="breadcrumb">
							<!-- breadcrumb -->

							<li class="breadcrumb-item"><a href="#"
								id="breadcrumb_item_label">Plans</a></li>
							<li class="breadcrumb-item active" aria-current="page"
								id="breadcrumb_item_active_label">Buy Plan</li>
						</ol>
						<!-- End breadcrumb -->
					</div>
					<!-- End page-header -->
					<div class="">
						<!-- page-content -->
						<div class="">
							<div class="">
								<div class="row">
									<div class="col-lg-12">
										<div class="porte-card-content card-hover">
											<div class="card-header">
												<h3 class="card-title">
													<span id="label_select_plan">Select Your  Account Plan below</span>
												</h3>
											</div>
											<div class="card-body">
												<div class="row">
													<!-- start of loop -->
													<% if(arrPricingPlan!=null){
														for (int i=0;i<arrPricingPlan.size();i++){
															if (((PricingPlan) arrPricingPlan.get(i)).getPlanId().equalsIgnoreCase("0")==false){
												%>
													<div >
														<div class="buy-plan-card" style="margin-right:80px;margin-left:60px;">
															<div class="card-body ">
																	<%if(isActivePlanId.equalsIgnoreCase("1")&&((PricingPlan) arrPricingPlan.get(i)).getPlanId().equalsIgnoreCase("2")){ %>
																	<h2 style="font-size: xx-large;font-weight:bold;margin-left:-12px;">
																		$<%=currentPlanPrice%></h2>
																	<%}else{ %>
																	<h2 style="font-size: xx-large;font-weight:bold;margin-left:-12px;">
																		$<%=((PricingPlan) arrPricingPlan.get(i)).getPlanPrice()%></h2>
																	<%} %>
																	<div class="ptih-title" style="font-size: large;font-weight:bold;margin-left:-12px;margin-top:-10px"><%=((PricingPlan) arrPricingPlan.get(i)).getPlanName()%></div>
																
																<div class="pti-body" style="margin-top: 140px;">
																
																	<div class="ptib-item text-white"> 
																		<img src="assets/images/cards/plan_bullet.svg" alt="bullet" class="src">
																		<span><%=((PricingPlan) arrPricingPlan.get(i)).getPlanDesc1()%></span>
																	</div>
																	<div class="ptib-item text-white">
																		<img src="assets/images/cards/plan_bullet.svg" alt="bullet" class="src">
																		<span style="overflow-wrap: break-word;"><%=((PricingPlan) arrPricingPlan.get(i)).getPlanDesc2()%></span>
																	</div>
																	<div class="ptib-item text-white">
																		<img src="assets/images/cards/plan_bullet.svg" alt="bullet" class="src">
																		<span style="overflow-wrap: break-word;"><%=((PricingPlan) arrPricingPlan.get(i)).getPlanDesc3()%></span>
																	</div>
																	<%if(((PricingPlan) arrPricingPlan.get(i)).getPlanDesc4().equals("")==false){ %>
																		<div class="ptib-item text-white">
																			<img src="assets/images/cards/plan_bullet.svg" alt="bullet" class="src">
																			<span><%=((PricingPlan) arrPricingPlan.get(i)).getPlanDesc4()%></span>
																		</div>
																	<%}else{ %>
																		<div class="ptib-item text-white" style="visibility:hidden" >
																			<img src="assets/images/cards/plan_bullet.svg" alt="bullet" class="src">
																			<span><%=((PricingPlan) arrPricingPlan.get(i)).getPlanDesc4()%></span>
																		</div>
																	<%} %>
																</div>
																<% if((!isActivePlanId.equalsIgnoreCase("2"))&&isActivePlanId.equals(((PricingPlan) arrPricingPlan.get(i)).getPlanId()) == false){
																	%>
																<div class="pti-footer ">
																	<%if(isActivePlanId.equalsIgnoreCase("1")&&((PricingPlan) arrPricingPlan.get(i)).getPlanId().equalsIgnoreCase("2")){ %>
																	<a href="#" class="btn new-default-btn" style="width: 57%;margin-top: 26px;"
																		onClick="javascript: fnConfirmBuy('<%=((PricingPlan) arrPricingPlan.get(i)).getPlanId()%>',
																	  '<%=((PricingPlan) arrPricingPlan.get(i)).getPlanName()%>',
																	  '<%=currentPlanPrice%>',
																	   '<%=((PricingPlan) arrPricingPlan.get(i)).getPlanDuration()%>',
																	   '<%=isActivePlanId%>');return false;">Buy
																		Now</a>

																	<%}else{ %>
																	<a href="#" class="btn new-default-btn" style="width: 70%;margin-top: 10px;"
																		onClick="javascript: fnConfirmBuy('<%=((PricingPlan) arrPricingPlan.get(i)).getPlanId()%>','<%=((PricingPlan) arrPricingPlan.get(i)).getPlanName()%>','<%=((PricingPlan) arrPricingPlan.get(i)).getPlanPrice()%>', '<%=((PricingPlan) arrPricingPlan.get(i)).getPlanDuration()%>','<%=isActivePlanId%>');return false;"><span
																		id="label_buy_now_btn">Buy Now</span></a>
																</div>
																<%
																	}}
																	%>

															</div>
														</div>
													</div>
													<!-- col-end -->


													<%	}
													}
													}
												%>
												</div>
												<!-- end of loop -->

											</div>
										</div>
									</div>
								</div>
								<div id="temodiv" style="display: none;">
									<form id="tempform" method="post">
										<input type="hidden" name="qs" value=""> <input
											type="hidden" name="rules" value=""> <input
											type="hidden" name="hdnplanid" value=""> <input
											type="hidden" name="hdnplandesc" value=""> <input
											type="hidden" name="hdnplanval" value=""> <input
											type="hidden" name="hdnplanduration" value=""> <input
											type="hidden" name="hdnoriginalplanid" value="">
									</form>
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
	<script src="assets/js/_cust_buy_new_plan.js"></script>

	<script>
		$(function() {
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
	if(arrPricingPlan !=null ) arrPricingPlan= null;   
	if(isActivePlanId !=null ) isActivePlanId= null;   if (context!=null)context=null;
	if (langPref!=null)langPref=null;
}
%>
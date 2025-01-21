<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
User user_leftmenu = null; String lastaction=""; String strsubmenu=null;  String lastrule = "";  String userType = null;
String topnavUserName = "";	String topnavUserEmail="";  String topnavRelNo=""; String userId = ""; String companyName = null;

ArrayList <String> arrMenuUser = null;	 ArrayList <String> arrMenuIconUser = null;
ConcurrentHashMap <String, String> hashSubMenuUser  = null;
ConcurrentHashMap <String, String> hashSubMenuUserRules  = null;
ConcurrentHashMap <String, String> hashUserTypes  = null;
try{
	if(session.getAttribute("SESS_USER")!=null)		user_leftmenu = (User)session.getAttribute("SESS_USER");
	if(request.getAttribute("lastaction")!=null)		lastaction=(String)request.getAttribute("lastaction");
	if(request.getAttribute("lastrule")!=null)		lastrule=(String)request.getAttribute("lastrule");
	if(user_leftmenu!=null){
		userType = user_leftmenu.getUserType();
		companyName = user_leftmenu.getCompanyName();
		topnavUserName = user_leftmenu.getUserName();	
		userId = user_leftmenu.getUserId(); 
	}

		arrMenuUser = new ArrayList <String>();	arrMenuIconUser = new ArrayList <String>();
		hashSubMenuUser = new ConcurrentHashMap <String, String>();
		hashSubMenuUserRules = new ConcurrentHashMap <String, String>();
		hashUserTypes = new ConcurrentHashMap <String, String>();
		//******** Populate the menu list and its icon - re fer Functional Spec Items.docx.... no whitespaces
		arrMenuUser.add(0, "dash,Dashboard");					arrMenuIconUser.add(0, "side-menu__icon typcn typcn-device-desktop");
		arrMenuUser.add(1, "merchprf,Profile");						arrMenuIconUser.add(1, "side-menu__icon typcn typcn-th-large-outline");
		arrMenuUser.add(2, "merchqrcode,QR code");			        arrMenuIconUser.add(2, "side-menu__icon typcn typcn-briefcase");
	/* 	arrMenuUser.add(3, "emkt,E-market place");			        arrMenuIconUser.add(3, "side-menu__icon typcn typcn-shopping-cart"); */
		arrMenuUser.add(3, "merchtxn, Transaction History");		arrMenuIconUser.add(3, "side-menu__icon typcn typcn-news");
	/* 	arrMenuUser.add(5, "merchrpt, Statements and reports");		arrMenuIconUser.add(5, "side-menu__icon typcn typcn typcn-arrow-move-outline"); */
	/* 	arrMenuUser.add(6, "merchprc, Pricing");			        arrMenuIconUser.add(6, "side-menu__icon typcn typcn typcn-arrow-move-outline"); */
		arrMenuUser.add(4, "merchdspt,Disputes");			        arrMenuIconUser.add(4, "side-menu__icon typcn typcn-support");
		arrMenuUser.add(5, "lgt,Logout");			            arrMenuIconUser.add(5, "side-menu__icon typcn typcn-eject-outline");

		hashSubMenuUserRules.put("merchprf", "View and Update,Manage branches,Manage users");
		hashSubMenuUserRules.put("merchqrcode", "Store Payment,Cash Out,Top Up");
		hashSubMenuUserRules.put("merchdspt","Raise Dispute,View Disputes");
		hashSubMenuUserRules.put("merchtxn","Transactions History");
		
		hashUserTypes.put("O","Operations");	hashUserTypes.put("C","Customer");	hashUserTypes.put("M","Merchant");

%>
<!--app-header-->
<div class="app-header header d-flex">
	<div class="container-fluid">
		<div class="d-flex">
			<a class="header-brand" href="index.html"> <img
				src="assets/images/brand/logo.svg"
				class="header-brand-img main-logo" alt="logo"
				style="width: 190px; height: 35px;"> <!--<img src="assets/images/brand/icon.png" class="header-brand-img icon-logo" alt="logo">-->
			</a>
			<!-- logo-->
			<a aria-label="Hide Sidebar" class="app-sidebar__toggle"
				data-toggle="sidebar" href="#"></a> <a href="#" data-toggle="search"
				class="nav-link nav-link  navsearch"><i class="fa fa-search"></i></a>
			<!-- search icon -->
			<div class="header-form">
				<form class="form-inline">
					<div class="search-element mr-3">
						<input class="form-control" type="search" placeholder="Search"
							aria-label="Search"> <span class="Search-icon"><i
							class="fa fa-search"></i></span>
					</div>
				</form>
				<!-- search-bar -->
			</div>
			<div class="d-flex order-lg-2 ml-auto header-rightmenu">
				<div class="dropdown">
					<a class="nav-link icon full-screen-link" id="fullscreen-button">
						<i class="fe fe-maximize-2"></i>
					</a>
				</div>
				<!-- full-screen -->
				<div class="dropdown mt-2 mb-2">
					<button type="button"
						class="btn btn-outline-primary dropdown-toggle"
						data-toggle="dropdown">
						<span id="lang_def">EN</span>
					</button>
					<div class="dropdown-menu">
						<a class="dropdown-item" href="#"
							onclick="javascript: fnChangePageLanguage('en');return false;">English</a>
						<a class="dropdown-item" href="#"
							onclick="javascript: fnChangePageLanguage('es');return false;">Spanish</a>
					</div>
				</div>
				<div class="dropdown header-notify">
					<a class="nav-link icon" data-toggle="dropdown"
						aria-expanded="false"> <i class="fe fe-settings "></i>

					</a>
					<div class="dropdown-menu dropdown-menu-right dropdown-menu-arrow ">
						<a href="#" class="dropdown-item text-center"><span
							id='idnav_UserSettings'>User Settings</span></a>
						<div class="dropdown-divider"></div>
						<a href="#" class="dropdown-item d-flex pb-3">
							<div class="notifyimg bg-green">
								<i class="fe fe-user-check"></i>
							</div>
							<div>
								<strong><span id='idnav_UpdateProfile'>Update
										Profile</span></strong>
							</div>
						</a> <a href="#" class="dropdown-item d-flex pb-3"
							onclick="javascript:fnGoToSubMenuPageJQ('Logout', 'lgt');return false;">
							<div class="notifyimg bg-orange">
								<i class="fe fe-external-link"></i>
							</div>
							<div>
								<strong><span id='idnav_Logout'>Logout</span></strong>
							</div>
						</a>
					</div>
				</div>
				<!-- notifications -->

			</div>
		</div>
	</div>
</div>
<!--app-header end-->

<!-- Sidebar menu-->
<div class="app-sidebar__overlay" data-toggle="sidebar"></div>
<aside class="app-sidebar toggle-sidebar">
	<div class="app-sidebar__user pb-0">
		<div class="user-info">
			<a href="#" class="ml-2"><span
				class="text-dark app-sidebar__user-name font-weight-semibold"><%=companyName %></span><br>
				<span class="text-muted app-sidebar__user-name text-sm">
					Merchant</span> </a>
		</div>
	</div>

	<div class="panel-body p-0 border-0 ">
		<div class="tab-content">
			<ul class="side-menu toggle-menu">


				<% for (int i=0;i<arrMenuUser.size();i++){ 
									 String menukey = arrMenuUser.get(i).substring(0, arrMenuUser.get(i).indexOf(","));
									 String menuvalue = arrMenuUser.get(i).substring(arrMenuUser.get(i).indexOf(",")+1); %>

				<%  if(lastaction.equals(menukey)){ %>
				<li class="slide is-expanded">
					<% if(hashSubMenuUserRules.get(menukey)!=null){ %> <a
					class="side-menu__item active" data-toggle="slide" href="#"><i
						class="<%=arrMenuIconUser.get(i)%>"></i><span
						class="side-menu__label"
						id='idnavmenu_<%=StringUtils.replace(menuvalue, " ", "")%>'><%=menuvalue %></span><i
						class="angle fa fa-angle-right"></i></a> <% }else { %> <a
					class="side-menu__item active" href="#"
					onclick="javascript:fnGoToSubMenuPageJQ('<%=menuvalue%>', '<%=menukey%>');return false;"><i
						class="<%=arrMenuIconUser.get(i)%>"></i><span
						class="side-menu__label"
						id='idnavmenu_<%=StringUtils.replace(menuvalue, " ", "")%>'><%=menuvalue %></span></a>
					<% } %> <% }else{ %>
				
				<li class="slide">
					<% if(hashSubMenuUserRules.get(menukey)!=null){ %> <a
					class="side-menu__item" data-toggle="slide" href="#"><i
						class="<%=arrMenuIconUser.get(i)%>"></i><span
						class="side-menu__label"
						id='idnavmenu_<%=StringUtils.replace(menuvalue, " ", "")%>'><%=menuvalue %></span><i
						class="angle fa fa-angle-right"></i></a> <% }else { %> <a
					class="side-menu__item" href="#"
					onclick="javascript:fnGoToSubMenuPageJQ('<%=menuvalue%>', '<%=menukey%>');return false;"><i
						class="<%=arrMenuIconUser.get(i)%>"></i><span
						class="side-menu__label"
						id='idnavmenu_<%=StringUtils.replace(menuvalue, " ", "")%>'><%=menuvalue %></span></a>
					<% } %> <% } %>

					<ul class="slide-menu">
						<%	
													if(hashSubMenuUserRules.get(menukey)!=null) {	
														String strEachSubmenu[] =   hashSubMenuUserRules.get(menukey).split(",");
														for(int j=0; j<strEachSubmenu.length; j++){  
															 if(strEachSubmenu[j].trim().equals(lastrule)){ %>
						<li><a href="#"
							onclick="javascript:fnGoToSubMenuPageJQ('<%=strEachSubmenu[j].trim()%>', '<%=menukey%>');return false;"
							class="slide-item"><b><span
									id='idnavsubmenu_<%=StringUtils.replace(strEachSubmenu[j], " ", "")%>'><%=strEachSubmenu[j].trim()%></span></b></a></li>
						<% } else { %>
						<li><a href="#"
							onclick="javascript:fnGoToSubMenuPageJQ('<%=strEachSubmenu[j].trim()%>', '<%=menukey%>');return false;"
							class="slide-item"> <span
								id='idnavsubmenu_<%=StringUtils.replace(strEachSubmenu[j], " ", "")%>'><%=strEachSubmenu[j].trim()%></span></a></li>
						<% }
														} 
													}	
												%>
					</ul>
				</li>
				<% } %>
			</ul>

		</div>
	</div>
</aside>
<!--sidemenu end-->
<form id="form-leftmenu" method="post">
	<input type="hidden" name="qs" value=""> <input type="hidden"
		name="rules" value=""> <input type="hidden" name="hdnlang"
		id="hdnlangnav" value="">
</form>
<script>
		  function fnGoToSubMenuPageJQ(submenu,menu){
			$('#form-leftmenu').attr('action', 'ws');
			$('input[name="qs"]').val(menu);
			$('input[name="rules"]').val(submenu);
			$('#form-leftmenu input[name="hdnlang"]').val($('#lang_def').text());
			//$('#hdnlangnav').val($('#lang_def').text());
			$("#form-leftmenu").submit();
		}	
	</script>
<%
}catch (Exception e){
	out.println ("Exception : "+e.getMessage());
}finally{
if(user_leftmenu!=null) user_leftmenu=null; if(lastaction!=null) lastaction=null; if(strsubmenu!=null) strsubmenu=null; if(lastrule!=null) lastrule=null; 
if(userType!=null) userType=null;  if(arrMenuUser!=null) arrMenuUser=null; 
if(arrMenuIconUser!=null) arrMenuIconUser=null; if(hashSubMenuUser!=null) hashSubMenuUser=null; if(hashSubMenuUserRules!=null) hashSubMenuUserRules=null; 
}
%>
<%@page import="com.pporte.utilities.JSPUtilities"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page
	import="com.pporte.*,com.pporte.model.*,com.pporte.utilities.Utilities, org.apache.commons.lang3.StringUtils,  java.util.concurrent.*, java.util.*"%>
<%
User user_leftmenu = null; String lastaction=""; String strsubmenu=null;  String lastrule = "";  String userType = null;
String topnavUserName = "";	String topnavUserEmail="";  String topnavRelNo=""; String userId = "";
String maskedEmailAddress="";
try{

	if(session.getAttribute("SESS_USER")!=null)		user_leftmenu = (User)session.getAttribute("SESS_USER");
	if(request.getAttribute("lastaction")!=null)		lastaction=(String)request.getAttribute("lastaction");
	if(request.getAttribute("lastrule")!=null)		lastrule=(String)request.getAttribute("lastrule");
	if(user_leftmenu!=null){
		userType = "C";
		topnavUserName = user_leftmenu.getName();	
		userId = user_leftmenu.getCustomerId(); 
		maskedEmailAddress=user_leftmenu.getMaskedEmailId();
		topnavUserEmail=user_leftmenu.getCustomerId();
	}
%>
<style>
/********Tooltip***************/

.customtooltip {
  position: relative;
  display: inline-block;
}

.customtooltip .tooltiptext {
    visibility: hidden;
	width: 228px;
    background-color: black;
    color: #fff;
    text-align: center;
    border-radius: 6px;
    padding: 5px 0;
    position: absolute;
    z-index: 1;
    bottom: 101%;
    left: -106%;
    font-size: small;
    margin-left: 114px;
}
.customtooltip .tooltiptext::after {
  content: "";
  position: absolute;
  top: 100%;
  left: 50%;
  margin-left: -5px;
  border-width: 5px;
  border-style: solid;
  border-color: black transparent transparent transparent;
}

.customtooltip:hover .tooltiptext {
  visibility: visible;
}

/********End of Tooltip********/
}
</style>
<!--app-header-->
<div class="app-header header d-flex">
	<div class="container-fluid">
		<div class="d-flex">
			<a class="header-brand" style="margin-left: -3px"
				onclick="javascript:fnCallDashboardPage();return false;"> <img
				src="assets/images/brand/logo.svg"
				class="header-brand-img main-logo" alt="logo"> <!--<img src="assets/images/brand/icon.png" class="header-brand-img icon-logo" alt="logo">-->
			</a>
			<!-- logo-->
			<a aria-label="Hide Sidebar" class="app-sidebar__toggle"
				data-toggle="sidebar" href="#"></a>

			<div class="d-flex order-lg-2 ml-auto header-rightmenu">
				<div class="dropdown">
					<a class="nav-link icon full-screen-link" id="fullscreen-button">
						<i class="fe fe-maximize-2"></i>
					</a>
				</div>
				<!-- full-screen -->
				<div class="drop-down">
					 <div class="text-right mb-4" style="margin-top: 16px;">
									<div class="btn-group mt-0 mb-0">
										<button type="button"
											class="btn btn-pill btn-sm btn-outline-primary">
											<span id="lang_def">EN</span>
										</button>
										<button type="button"
											class="btn btn-pill btn-sm btn-outline-primary dropdown-toggle"
											data-toggle="dropdown">
											<span class="caret"></span>
										</button>
										<ul class="dropdown-menu" role="menu">
											<li class="dropdown-plus-title"><span
												id="login-language-title">Languages</span> <b
												class=" fa fa-angle-up"></b></li>
											<li><a href="#"
												onclick="javascript: fnChangePageLanguage('EN');return false;">English</a></li>
											<li><a href="#"
												onclick="javascript: fnChangePageLanguage('ES');return false;">Spanish</a></li>
										</ul>
									</div>
			
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
						<a href="#" class="dropdown-item d-flex pb-3"
							onclick="javascript:fnGoToSubMenuPageJQ('View and Edit', 'lgt');return false;">
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
				class="text-dark app-sidebar__user-name font-weight-semibold"><%=topnavUserName %></span><br>
				 <div class="customtooltip"><%=maskedEmailAddress%><span class="tooltiptext"> <%=topnavUserEmail%></span></div>
				</a>
		</div>
	</div>

	<div class="panel-body p-0 border-0 ">
		<div class="tab-content">
			<ul class="side-menu toggle-menu">

				<%if(user_leftmenu.getPricingPlanid().equals("0") == true){ 
									 for (int i=0;i<JSPUtilities.getFreeTireCustomerMenu().size();i++){ 
											String menukey = JSPUtilities.getFreeTireCustomerMenu().get(i).substring(0, JSPUtilities.getFreeTireCustomerMenu().get(i).indexOf(","));
											String menuvalue = JSPUtilities.getFreeTireCustomerMenu().get(i).substring(JSPUtilities.getFreeTireCustomerMenu().get(i).indexOf(",")+1); 
												
											if(lastaction.equals(menukey)){ %>
				<li class="slide is-expanded">
					<% if( JSPUtilities.getFreeTireCustomerSubMenu().get(menukey)!=null){ %>
					<a class="side-menu__item active" data-toggle="slide" href="#"><i
						class="<%=JSPUtilities.getFreeTireCustomerMenuIcons().get(i)%>"></i><span
						class="side-menu__label"
						id='idnavmenu_<%=StringUtils.replace(menuvalue, " ", "")%>'><%=menuvalue %></span><i
						class="angle fa fa-angle-right"></i></a> <% }else { %> <a
					class="side-menu__item active" href="#"
					onclick="javascript:fnGoToSubMenuPageJQ('<%=menuvalue%>', '<%=menukey%>');return false;"><i
						class="<%=JSPUtilities.getFreeTireCustomerMenuIcons().get(i)%>"></i><span
						class="side-menu__label"
						id='idnavmenu_<%=StringUtils.replace(menuvalue, " ", "")%>'><%=menuvalue %></span></a>
					<% } %> <% }else{ %>
				
				<li class="slide">
					<% if(JSPUtilities.getFreeTireCustomerSubMenu().get(menukey)!=null){ %>
					<a class="side-menu__item" data-toggle="slide" href="#"><i
						class="<%=JSPUtilities.getFreeTireCustomerMenuIcons().get(i)%>"></i><span
						class="side-menu__label"
						id='idnavmenu_<%=StringUtils.replace(menuvalue, " ", "")%>'><%=menuvalue %></span><i
						class="angle fa fa-angle-right"></i></a> <% }else { %> <a
					class="side-menu__item" href="#"
					onclick="javascript:fnGoToSubMenuPageJQ('<%=menuvalue%>', '<%=menukey%>');return false;"><i
						class="<%=JSPUtilities.getFreeTireCustomerMenuIcons().get(i)%>"></i><span
						class="side-menu__label"
						id='idnavmenu_<%=StringUtils.replace(menuvalue, " ", "")%>'><%=menuvalue %></span></a>
					<% } %> <% } %>

					<ul class="slide-menu">
						<%	
																if(JSPUtilities.getFreeTireCustomerSubMenu().get(menukey)!=null) {	
																	String strEachSubmenu[] =   JSPUtilities.getFreeTireCustomerSubMenu().get(menukey).split(",");
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

				<%}else if (user_leftmenu.getPricingPlanid().equals("1")){ 
									  for (int i=0;i<JSPUtilities.getBasicCustomerMenu().size();i++){ 
											String menukey = JSPUtilities.getBasicCustomerMenu().get(i).substring(0, JSPUtilities.getBasicCustomerMenu().get(i).indexOf(","));
											String menuvalue = JSPUtilities.getBasicCustomerMenu().get(i).substring(JSPUtilities.getBasicCustomerMenu().get(i).indexOf(",")+1); 
												
											if(lastaction.equals(menukey)){ %>
				<li class="slide is-expanded">
					<% if( JSPUtilities.getBasicCustomerSubMenu().get(menukey)!=null){ %>
					<a class="side-menu__item active" data-toggle="slide" href="#"><i
						class="<%=JSPUtilities.getBasicCustomerMenuIcons().get(i)%>"></i><span
						class="side-menu__label"
						id='idnavmenu_<%=StringUtils.replace(menuvalue, " ", "")%>'><%=menuvalue %></span><i
						class="angle fa fa-angle-right"></i></a> <% }else { %> <a
					class="side-menu__item active" href="#"
					onclick="javascript:fnGoToSubMenuPageJQ('<%=menuvalue%>', '<%=menukey%>');return false;"><i
						class="<%=JSPUtilities.getBasicCustomerMenuIcons().get(i)%>"></i><span
						class="side-menu__label"
						id='idnavmenu_<%=StringUtils.replace(menuvalue, " ", "")%>'><%=menuvalue %></span></a>
					<% } %> <% }else{ %>
				
				<li class="slide">
					<% if(JSPUtilities.getBasicCustomerSubMenu().get(menukey)!=null){ %>
					<a class="side-menu__item" data-toggle="slide" href="#"><i
						class="<%=JSPUtilities.getBasicCustomerMenuIcons().get(i)%>"></i><span
						class="side-menu__label"
						id='idnavmenu_<%=StringUtils.replace(menuvalue, " ", "")%>'><%=menuvalue %></span><i
						class="angle fa fa-angle-right"></i></a> <% }else { %> <a
					class="side-menu__item" href="#"
					onclick="javascript:fnGoToSubMenuPageJQ('<%=menuvalue%>', '<%=menukey%>');return false;"><i
						class="<%=JSPUtilities.getBasicCustomerMenuIcons().get(i)%>"></i><span
						class="side-menu__label"
						id='idnavmenu_<%=StringUtils.replace(menuvalue, " ", "")%>'><%=menuvalue %></span></a>
					<% } %> <% } %>

					<ul class="slide-menu">
						<%	
																if(JSPUtilities.getBasicCustomerSubMenu().get(menukey)!=null) {	
																	String strEachSubmenu[] =   JSPUtilities.getBasicCustomerSubMenu().get(menukey).split(",");
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

				<%}else if (user_leftmenu.getPricingPlanid().equals("2")){ 
									
									 for (int i=0;i<JSPUtilities.getPlatinumCustomerMenu().size();i++){ 
											String menukey = JSPUtilities.getPlatinumCustomerMenu().get(i).substring(0, JSPUtilities.getPlatinumCustomerMenu().get(i).indexOf(","));
											String menuvalue = JSPUtilities.getPlatinumCustomerMenu().get(i).substring(JSPUtilities.getPlatinumCustomerMenu().get(i).indexOf(",")+1); 
												
											if(lastaction.equals(menukey)){ %>
				<li class="slide is-expanded">
					<% if( JSPUtilities.getPlatinumCustomerSubMenu().get(menukey)!=null){ %>
					<a class="side-menu__item active" data-toggle="slide" href="#"><i
						class="<%=JSPUtilities.getPlatinumCustomerMenuIcons().get(i)%>"></i><span
						class="side-menu__label"
						id='idnavmenu_<%=StringUtils.replace(menuvalue, " ", "")%>'><%=menuvalue %></span><i
						class="angle fa fa-angle-right"></i></a> <% }else { %> <a
					class="side-menu__item active" href="#"
					onclick="javascript:fnGoToSubMenuPageJQ('<%=menuvalue%>', '<%=menukey%>');return false;"><i
						class="<%=JSPUtilities.getPlatinumCustomerMenuIcons().get(i)%>"></i><span
						class="side-menu__label"
						id='idnavmenu_<%=StringUtils.replace(menuvalue, " ", "")%>'><%=menuvalue %></span></a>
					<% } %> <% }else{ %>
				
				<li class="slide">
					<% if(JSPUtilities.getPlatinumCustomerSubMenu().get(menukey)!=null){ %>
					<a class="side-menu__item" data-toggle="slide" href="#"><i
						class="<%=JSPUtilities.getPlatinumCustomerMenuIcons().get(i)%>"></i><span
						class="side-menu__label"
						id='idnavmenu_<%=StringUtils.replace(menuvalue, " ", "")%>'><%=menuvalue %></span><i
						class="angle fa fa-angle-right"></i></a> <% }else { %> <a
					class="side-menu__item" href="#"
					onclick="javascript:fnGoToSubMenuPageJQ('<%=menuvalue%>', '<%=menukey%>');return false;"><i
						class="<%=JSPUtilities.getPlatinumCustomerMenuIcons().get(i)%>"></i><span
						class="side-menu__label"
						id='idnavmenu_<%=StringUtils.replace(menuvalue, " ", "")%>'><%=menuvalue %></span></a>
					<% } %> <% } %>

					<ul class="slide-menu">
						<%	
																if(JSPUtilities.getPlatinumCustomerSubMenu().get(menukey)!=null) {	
																	String strEachSubmenu[] =   JSPUtilities.getPlatinumCustomerSubMenu().get(menukey).split(",");
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

				<%} %>
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
	var username = '<%=topnavUserName%>';
	//console.log("User name is "+username);
		  function fnGoToSubMenuPageJQ(submenu,menu){
			$('#form-leftmenu').attr('action', 'ws');
			$('input[name="qs"]').val(menu);
			$('input[name="rules"]').val(submenu);
			$('#form-leftmenu input[name="hdnlang"]').val($('#lang_def').text());
			//$('#hdnlangnav').val($('#lang_def').text());
			$("#form-leftmenu").submit();
		}	
		  function fnBuyCoin(){
			  $('#form-leftmenu').attr('action', 'ws');
              $('input[name="qs"]').val('porte');
              $('input[name="rules"]').val('Buy Asset');
              $('#form-leftmenu input[name="hdnlang"]').val($('#lang_def').text());
              $("#form-leftmenu").submit();
		  } 
		  function fnCallDashboardPage(){
			  $('#form-leftmenu').attr('action', 'ws');
              $('input[name="qs"]').val('dash');
              $('input[name="rules"]').val('Dashboard');
              $('#form-leftmenu input[name="hdnlang"]').val($('#lang_def').text());
              $("#form-leftmenu").submit();
		  }
		 
	</script>
<%
}catch (Exception e){
	out.println ("Exception : "+e.getMessage());
}finally{
if(user_leftmenu!=null) user_leftmenu=null; if(lastaction!=null) lastaction=null; if(strsubmenu!=null) strsubmenu=null; if(lastrule!=null) lastrule=null; 
if(userType!=null) userType=null; if(topnavUserName!=null) topnavUserName=null; if(topnavUserEmail!=null) topnavUserEmail=null; 
if(topnavRelNo!=null) topnavRelNo=null; if(userId!=null) userId=null; if(maskedEmailAddress!=null) maskedEmailAddress=null;

}
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	String errorMsg = "";
	try{
	
		if(request.getAttribute("errormsg")!=null)	errorMsg = (String)request.getAttribute("errormsg");
	
	%>
<!doctype html>
<html lang="en" dir="ltr">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Title -->
<title>Error Page</title>

<!--Bootstrap.min css-->
<link rel="stylesheet"
	href="assets/plugins/bootstrap/css/bootstrap.min.css">

<!-- Dashboard css -->
<link href="assets/css/style.css" rel="stylesheet" />

</head>
<body>
	<!-- page -->
	<div class="page">

		<!-- page-content -->
		<div class="page-content">
			<div class="container text-center text-dark">
				<div class="display-1  text-dark mb-5">503</div>
				<%if (errorMsg.startsWith("Session has expired")) {%>
				<p class="h5 font-weight-normal mb-7 leading-normal"><%=errorMsg%></p>
				<%}else if(errorMsg.startsWith("Token")){%>
				<p class="h5 font-weight-normal mb-7 leading-normal"><%=errorMsg%></p>
				<%}else{ %>
				<p class="h5 font-weight-normal mb-7 leading-normal">Something
					went wrong,but we're on it.</p>
				<%}%>

				<a class="btn btn-primary  mb-5" href="#"
					onClick="fnSubmit();return false;">Back To Login Page</a>
			</div>
		</div>
		<!-- page-content end -->
	</div>
	<!-- page End-->
	<form id="form-error" method="post">
		<input type="hidden" name="qs" value="lgt"> <input
			type="hidden" name="rules" value="Logout"> <input
			type="hidden" name="hdnlang" value="en">
	</form>
	<!-- Jquery js-->
	<script src="assets/js/vendors/jquery-3.2.1.min.js"></script>

	<!--Bootstrap.min js-->
	<script src="assets/plugins/bootstrap/popper.min.js"></script>
	<script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>

	<!--Jquery Sparkline js-->
	<script src="assets/js/vendors/jquery.sparkline.min.js"></script>

	<!-- Chart Circle js-->
	<script src="assets/js/vendors/circle-progress.min.js"></script>
	<script>
		  function fnSubmit(){
			$('#form-error').attr('action', 'ws');
			$("#form-error").submit();
		}	
	</script>
</body>
</html>
<%
	}catch(Exception e){
	
	}finally{
		if(errorMsg !=null ) errorMsg =null;
		
	}
%>
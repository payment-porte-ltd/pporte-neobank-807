<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	String errorMsg = "";
	try{
	
		if(request.getAttribute("errormsg")!=null)	errorMsg = (String)request.getAttribute("errormsg");
	
	%>

<!DOCTYPE html>
<html>
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
<link href="assets/css/ops_style.css" rel="stylesheet" />

</head>
<body>
	<!-- page -->
	<div class="page">

		<!-- page-content -->
		<div class="page-content">
			<div class="container text-center text-dark">
				<div class="display-1  text-dark mb-5">503</div>
				<p class="h5 font-weight-normal mb-7 leading-normal"><%=errorMsg %></p>
				<a class="btn btn-primary  mb-5" href="#"
					onClick="fnSubmit();return false;">Back To Login Page</a>
			</div>
		</div>
		<!-- page-content end -->
	</div>
	<!-- page End-->
	<form id="form-error" method="post">
		<input type="hidden" name="qs" value="lgt"> <input
			type="hidden" name="rules" value="Sign Out"> <input
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
		function fnSubmit() {
			$('#form-error').attr('action', 'ws');
			 $('input[name="qs"]').val('lgt');
             $('input[name="rules"]').val('Sign Out');
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
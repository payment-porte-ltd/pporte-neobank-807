i18next.init({
	lng: 'EN',
	fallbackLng: 'EN',
	debug: false,
	resources: {
	  EN: {
		translation: {
		  "login-language-title" : "Languages", 
		  "login-link-sign-to-acccount" : "Sign In to your account",
		  "login-input-label-userid" : "User Id",
		  "login-input-label-upwd" : "Password",
		  "login-input-label-captcha-msg" : "Enter Text from above",
		  "login-input-label-customer" : "Customer",
		  "login-input-label-merchant" : "Merchant",
		  "login-input-label-operation" : "Operations",
		  "login-button-submit" : "Login",
		  "login-link-forgot-password" : "Forgot password?",
		  "login-link-register" : "Sign Up",
		  "login-user-register-modal-title" : "User Registration",
		  "login-register-header" : "Register User",
		  "register-input-label-customer" : "Customer",
		  "login-register-label-reguserid" : "User Id",
		  "login-register-label-reguserpwd" : "Password",
		  "login-register-label-firstname": "First Name",
		  "login-register-label-lastname" : "Last Name",
		  "login-register-label-email" : "Email address",
		  "login-register-label-contact": "Contact Number",
		  "login-register-label-address1" : "Address 1",
		  "login-register-label-address2" :"Address 2",
		  "login-register-label-city" : "City",
		  "login-register-button-save" : "Save",
		  "login-register-button-cancel" : "Cancel",
		  "login-register-button-close" : "Close",
		  "login-data-validation-error-swal-header" : "Oops..",
		  "login-data-validation-error-swal-checkdata" : "Please check your data",
		  "login-data-validation-username" : "Your User id must consist of at least 4 characters",
		  "login-data-validation-username-length" : "Please enter user id",
		  "login-data-validation-upwd" : "Please enter password",
		  "login-data-validation-upwd-length" : "Your password must consist of at least 6 characters",
		  "login-data-validation-captcha" : "Please enter captcha",
		  "login-data-validation-captcha-length" : "Your captcha must consist of at least 6 characters",
		  "login-label":"Login",
		  "login-header":"Sign in to manage your Assets.",
		  "sign-in-your-label":"Sign In to your account",
		  "user-id-label":"Email",
		  "password-label":"Password",
		  "sp_forgot_password_instruction":"Enter Your Registered User Id where your temporary password will be sent to.",
		  "sp-enter-email":"Enter email",
		  "sp-submit-btn":"Submit",
		  "login-data-text-does-not-match":"Text does not match",
		  "login-data-text-problem-with-connection":"Problem with connection",
		  "label_create_account":"Create New Account",
		}
	  },
	  ES: {
		  translation: {
		  "login-language-title" : "Idiomas", 
		  "login-link-sign-to-acccount" : "Iniciar sesion en su cuenta",
		  "login-input-label-userid" : "ID de usuario",
		  "login-input-label-upwd" : "Contraseña",
		  "login-input-label-captcha-msg" : "Ingrese texto desde arriba",
		  "login-input-label-customer" : "Cliente",
		  "login-input-label-merchant" : "Comerciante",
		  "login-input-label-operation" : "Operaciones",
		  "login-button-submit" : "Acceso",
		  "login-link-forgot-password" : "¿Se te olvidó tu contraseña?",
		  "login-link-register" : "Inscribirse",
		   "login-user-register-modal-title" : "registro de usuario",
		  "login-register-header" : "Registrar usuario",
		  "register-input-label-customer" : "Cliente",
		  "login-register-label-reguserid" : "ID de usuario",
		  "login-register-label-reguserpwd" : "Contraseña",
		  "login-register-label-firstname": "Nombre de pila",
		  "login-register-label-lastname" : "Apellido",
		  "login-register-label-email" : "Dirección de correo electrónico",
		  "login-register-label-contact": "Número de contacto",
		   "login-register-label-address1" : "Dirección 1",
		  "login-register-label-address2" :"Dirección 2",
		  "login-register-label-city" : "Ciudad",
		  "login-register-button-save" : "Salvar",
		  "login-register-button-cancel" : "Cancelar",
		  "login-register-button-close" : "Cerca",			
		  "login-data-validation-error-swal-header" : "Ups..",
		  "login-data-validation-error-swal-checkdata" : "Por favor revise sus datos",
		  "login-data-validation-username" : "Su identificación de usuario debe constar de al menos 4 caracteres",
		  "login-data-validation-username-length" : "Ingrese el ID de usuario",
		  "login-data-validation-upwd" : "Por favor, ingrese contraseña",
		  "login-data-validation-upwd-length" : "Tu contraseña debe tener al menos 6 caracteres",
		  "login-data-validation-captcha" : "Por favor ingrese captcha",
		  "login-data-validation-captcha-length" : "Tu captcha debe constar de al menos 6 caracteres",
		  "login-label":"Acceso",
		  "login-header":"Regístrese para administrar sus activos.",
		  "sign-in-your-label":"Iniciar sesión en su cuenta",
		  "user-id-label":"Correo electrónico",
		  "password-label":"Clave",
		  "sp_forgot_password_instruction":"Ingrese su ID de usuario registrado donde se le enviará su contraseña temporal.",
		  "sp-enter-email":"Ingrese correo electrónico",
		  "sp-submit-btn":"Enviar",
		  "login-data-text-does-not-match":"El texto no coincide",
		  "login-data-text-problem-with-connection":"Problema con la conexion", 
 		  "label_create_account":"Crear nueva cuenta",
		}
		}
	}
  }, function(err, t) {
	   updateContent();
  });

	  function updateContent() {
		  $('#login-language-title').text(i18next.t('login-language-title')); 
		  $('#login-link-sign-to-acccount').text(i18next.t('login-link-sign-to-acccount'));
		  $('#login-input-label-userid').text(i18next.t('login-input-label-userid'));
		  $('#login-input-label-upwd').text(i18next.t('login-input-label-upwd'));
		  $('#login-input-label-captcha-msg').text(i18next.t('login-input-label-captcha-msg'));
		  $('#login-input-label-customer').text(i18next.t('login-input-label-customer'));
		  $('#login-input-label-notary').text(i18next.t('login-input-label-notary'));
		  $('#login-input-label-operation').text(i18next.t('login-input-label-operation'));
		  $('#login-button-submit').text(i18next.t('login-button-submit'));
		  $('#login-link-forgot-password').text(i18next.t('login-link-forgot-password'));
		  $('#login-link-register').text(i18next.t('login-link-register'));
		  $('#login-user-register-modal-title').text(i18next.t('login-user-register-modal-title'));
		  $('#login-register-header').text(i18next.t('login-register-header'));
		  $('#register-input-label-customer').text(i18next.t('register-input-label-customer'));
		  $('#login-register-label-reguserid').text(i18next.t('login-register-label-reguserid'));
		  $('#login-register-label-reguserpwd').text(i18next.t('login-register-label-reguserpwd'));
		  $('#login-register-label-firstname').text(i18next.t('login-register-label-firstname'));
		  $('#login-register-label-lastname').text(i18next.t('login-register-label-lastname'));
		  $('#login-register-label-email').text(i18next.t('login-register-label-email'));
		  $('#login-register-label-contact').text(i18next.t('login-register-label-contact'));
		  $('#login-register-label-address1').text(i18next.t('login-register-label-address1'));
		  $('#login-register-label-address2').text(i18next.t('login-register-label-address2'));
		  $('#login-register-label-city').text(i18next.t('login-register-label-city'));
		  $('#login-register-button-save').text(i18next.t('login-register-button-save'));
		  $('#login-register-button-cancel').text(i18next.t('login-register-button-cancel'));
		  $('#login-register-button-close').text(i18next.t('login-register-button-close'));
		  $('#login-data-validation-error-swal-header').text(i18next.t('login-data-validation-error-swal-header'));
		  $('#login-data-validation-error-swal-checkdata').text(i18next.t('login-data-validation-error-swal-checkdata'));
		  $('#login-data-validation-username').text(i18next.t('login-data-validation-username'));
		  $('#login-data-validation-username-length').text(i18next.t('login-data-validation-username-length'));
		  $('#login-data-validation-upwd').text(i18next.t('login-data-validation-upwd'));
		  $('#login-data-validation-upwd-length').text(i18next.t('login-data-validation-upwd-length'));
		  $('#login-data-validation-captcha').text(i18next.t('login-data-validation-captcha'));
		  $('#login-data-validation-captcha-length').text(i18next.t('login-data-validation-captcha-length'));

		  $('#login-label').text(i18next.t('login-label'));
		  $('#login-header').text(i18next.t('login-header'));
		  $('#sign-in-your-label').text(i18next.t('sign-in-your-label'));
		  $('#user-id-label').text(i18next.t('user-id-label'));
		  $('#password-label').text(i18next.t('password-label'));
		  $('#sp_forgot_password_instruction').text(i18next.t('sp_forgot_password_instruction'));
		  $('#sp-enter-email').text(i18next.t('sp-enter-email'));
		  $('#sp-submit-btn').text(i18next.t('sp-submit-btn'));		
		  $('#login-data-text-does-not-match').text(i18next.t('login-data-text-does-not-match'));		
		  $('#sp-submit-btnlogin-data-text-problem-with-connection').text(i18next.t('login-data-text-problem-with-connection'));		
		  $('#label_create_account').text(i18next.t('label_create_account'));		
		  
	}
	var gobalLanguageValue ="";
	  function fnChangePageLanguage(lng){
		  i18next.changeLanguage(lng, fnChangeLanguage(lng))
	  }

	  i18next.on('languageChanged', function(lng) {
		updateContent(lng);
	  });
			 
	  function fnChangeLanguage(lang){
		  if(lang=='EN')  $('#lang_def').text('EN') 
			else if(lang=='ES')  $('#lang_def').text('ES')
		  $('#hdnlangpref1,#hdnlangpref2,#hdnlangpref3').val(lang);
		  gobalLanguageValue = lang;
	  }
	  $('#refresh').click(function(){
		  captcha.refresh();
	  });
	
	
		$("#ucaptcha").on("keydown", function (e) {
		    if (e.keyCode === 13) {  //checks whether the pressed key is "Enter"
		        fnLogin();
				return false;
		    }
		});
	  function fnLogin(){
		  //Check for the data validation

		  var userType = "C";
		  $( "#login-form" ).validate( {
			  rules: {
				  uname: {
					  required: true,
					  minlength: 4
				  },
				  upwd: {
					  required: true,
					  minlength: 4
				  },
				  ucaptcha: {
					  required: true,
					  minlength: 5
				  },
			  },
			  messages: {
				  uname: {
					  required: $('#login-data-validation-username').text(),
					  minlength: $('#login-data-validation-username-length').text()
				  },
				  upwd: {
					  required: $('#login-data-validation-upwd').text(),
					  minlength: $('#login-data-validation-upwd-length').text()
				  },
				  ucaptcha: {
					  required: $('#login-data-validation-captcha').text(),
					  minlength: $('#login-data-validation-captcha-length').text()
				  }
			  },
			  errorElement: "em",
			  errorPlacement: function ( error, element ) {
				  // Add the `invalid-feedback` class to the error element
				  error.addClass( "invalid-feedback" );
				  if ( element.prop( "type" ) === "checkbox" ) {
					  error.insertAfter( element.next( "label" ) );
				  } else {
					  error.insertAfter( element );
				  }
			  },
			  highlight: function ( element, errorClass, validClass ) {
				  $( element ).addClass( "is-invalid" ).removeClass( "is-valid" );
			  },
			  unhighlight: function (element, errorClass, validClass) {
				  $( element ).addClass( "is-valid" ).removeClass( "is-invalid" );
			  }
		  });
		  if($( "#login-form" ).valid()){
			
			
			  $('#login-form input[name="qs"]').val('lgn');
			  $('#login-form input[name="rules"]').val('lgnvalidate');
			  $('#login-form input[name="hdnusertype"]').val(userType);
			  //alert('userType is '+userType);
			 
			  var formData = new FormData($('#login-form')[0]);
			  formData.append("hdnlang", gobalLanguageValue);
			 
			  if (captcha.valid($('#login-form input[name="ucaptcha"]').val()) == false) {
				  Swal.fire({
					  icon: 'error',
					  title: $('#login-data-validation-error-swal-header').text(),
					  text: $('#login-data-text-does-not-match').text(),
					  showConfirmButton: true,
					  confirmButtonText: "Ok",
					  }).then(function() {
						  captcha.refresh();	
					  });
					  return false;
				}
				formData.append("capmain", captcha.getCode());

			  $.ajaxSetup({
				  beforeSend: function(xhr) {
					  xhr.setRequestHeader('x-api-key' , getAPIKey());
				  }
			  });
			 $('#spinner-div').show();//Load button clicked show spinner
			  $.ajax({
				  url: 'ms',
				  data: formData,
				  processData: false,
				  contentType: false,
				  type: 'POST',
				  success: function (result) {			
					  var data = JSON.parse(result);
						$('#spinner-div').hide();//Request is complete so hide spinner
					  console.log("data is "+data)
					  var passwordtype=data.passwordtype;
						console.log("passwordtype is "+passwordtype)
					  if(data.error=='false'){
						  //alert('userType is '+userType)
						  $('#logout-form').attr('action', 'ws');
						  $('#logout-form input[name="qs"]').val('lgn');
						  $('#logout-form input[name="rules"]').val('lgnfetch');
						  $('#logout-form input[name="hdnuserid"]').val( $("#uname").val() );
						  $('#logout-form input[name="hdnlgusertype"]').val(userType);
						  $('#logout-form').append('<input type="hidden" name="lgtoken" value='+data.token+' />');
						  //alert('lgtoken is '+$('#logout-form input[name="lgtoken"]').val())
						  $("#logout-form").submit();						
						  }else{
							if(passwordtype=="T"){
								 $('#logout-form').attr('action', 'ws');
								  $('#logout-form input[name="qs"]').val('lgt');
								  $('#logout-form input[name="rules"]').val('cust_fetch_password_page');
								  $('#logout-form input[name="hdnuserid"]').val( $("#uname").val() );
								  $("#logout-form").submit();	
							}else{
							  Swal.fire({
								  icon: 'error',
								  title: $('#login-data-validation-error-swal-header').text(),
								  text: data.message,
								  showConfirmButton: true,
								  confirmButtonText: "Ok",
								  }).then(function() {
									
									  $('#logout-form').attr('action', 'ws');
									  $('#logout-form input[name="qs"]').val('lgt');
									  $('#logout-form input[name="rules"]').val('lgtdefault');
									  $("#logout-form").submit();	
							  });
						}				
					  }
				  },
				  error: function() {
					  Swal.fire({
								  icon: 'error',
								  title: $('#login-data-validation-error-swal-header').text(),
								  text: $('#login-data-text-problem-with-connection').text(),
								  showConfirmButton: true,
								  confirmButtonText: "Ok",
								  }).then(function() {
									  $('#logout-form').attr('action', 'ws');
									  $('input[name="qs"]').val('lgt');
									  $('input[name="rules"]').val('lgtdefault');
									  $("#logout-form").submit();	
						  });
				  }
			  });             	
				 }else{
				  Swal.fire({
						icon: 'error',
						title: $('#login-data-validation-error-swal-header').text(),
						text: $('#login-data-validation-error-swal-checkdata').text()
						//footer: '<a href>Why do I have this issue?</a>'
					  }) 
					 return false;
				 }
	  }

		  


		$('#btn-select-user').click(function(){
			$('#select-user-form').attr('action', 'ws');
			$('input[name="qs"]').val('reg');
			$('input[name="rules"]').val('loadregistrationpage');
			$('input[name="hdnregusertype"]').val($('C').val());
			$("#select-user-form").submit();
		});	

  		$('#sign_up_redirect_btn').click(function(){
			$('#select-user-form').attr('action', 'ws');
			$('input[name="qs"]').val('reg');
			$('input[name="rules"]').val('loadregistrationpage');
			$('input[name="hdnregusertype"]').val("C"); 
			$('input[name="hdnlangpref"]').val(gobalLanguageValue); 
			$("#select-user-form").submit();
		});

$('#btn-forgot-password').click(function(){
	 $("#login-bg").addClass("hidden");
	 $("#div-forgotten-password").removeClass("hidden");
	
});
$('#btn-retrive-password').click(function(){
		  //Check for the data validation
 			var userType = "C";
		  $( "#forgot-password-form" ).validate( {
			  rules: {
				  username: {
					  required: true,
					  minlength: 4
				  },
			  },
			  messages: {
				  username: {
					  required: $('#forgot-password-data-validation-userid').text(),
					  minlength: $('#login-data-validation-username-length').text()
				  }
			  },
			  errorElement: "em",
			  errorPlacement: function ( error, element ) {
				  // Add the `invalid-feedback` class to the error element
				  error.addClass( "invalid-feedback" );
				  if ( element.prop( "type" ) === "checkbox" ) {
					  error.insertAfter( element.next( "label" ) );
				  } else {
					  error.insertAfter( element );
				  }
			  },
			  highlight: function ( element, errorClass, validClass ) {
				  $( element ).addClass( "is-invalid" ).removeClass( "is-valid" );
			  },
			  unhighlight: function (element, errorClass, validClass) {
				  $( element ).addClass( "is-valid" ).removeClass( "is-invalid" );
			  }
		  });
		  if($( "#forgot-password-form" ).valid()){
			  $('#forgot-password-form input[name="qs"]').val('lgn');
			  $('#forgot-password-form input[name="rules"]').val('lgnforgotpassword');
			  $('#forgot-password-form input[name="hdnpasswprdusertype"]').val(userType);
			  //alert('userType is '+userType);
			 $('#spinner-div').show();//Load button clicked show spinner
			  var formData = new FormData($('#forgot-password-form')[0]);
			  formData.append("hdnlang", gobalLanguageValue);
			  for (var pair of formData.entries()) {
				console.log(pair[0] + " - " + pair[1]);
			  }	
			 
			  $.ajaxSetup({
				  beforeSend: function(xhr) {
					  xhr.setRequestHeader('x-api-key' , getAPIKey());
				  }
			  });
			 
			  $.ajax({
				  url: 'ms',
				  data: formData,
				  processData: false,
				  contentType: false,
				  type: 'POST',
				  success: function (result) {	
					 $('#spinner-div').hide();//Request is complete so hide spinner
					  var data = JSON.parse(result);
					  if(data.error=='false'){
						 Swal.fire({
								  icon: 'sucess',
								  text: data.message,
								  showConfirmButton: true,
								  confirmButtonText: "Ok",
								  }).then(function() {
									  $('#logout-form').attr('action', 'ws');
									  $('input[name="qs"]').val('lgt');
									  $('input[name="rules"]').val('lgtdefault');
									  $("#logout-form").submit();	
						  });
						  //alert('userType is '+userType)
						  /*$('#logout-form').attr('action', 'ws');
						  $('#logout-form input[name="qs"]').val('lgn');
						  $('#logout-form input[name="rules"]').val('lgnfetch');
						  $('#logout-form input[name="hdnuserid"]').val( $("#uname").val() );
						  $('#logout-form input[name="hdnlgusertype"]').val(userType);
						  $('#logout-form').append('<input type="hidden" name="lgtoken" value='+data.token+' />');
						  $("#logout-form").submit();*/						
						  }else{
							  Swal.fire({
								  icon: 'error',
								  title: $('#login-data-validation-error-swal-header').text(),
								  text: data.message,
								  showConfirmButton: true,
								  confirmButtonText: "Ok",
								  }).then(function() {
									 	
							  });						
					  }
				  },
				  error: function() {
					  Swal.fire({
								  icon: 'error',
								  title: $('#login-data-validation-error-swal-header').text(),
								  text: $('#login-data-text-problem-with-connection').text(),
								  showConfirmButton: true,
								  confirmButtonText: "Ok",
								  }).then(function() {
									  $('#logout-form').attr('action', 'ws');
									  $('input[name="qs"]').val('lgt');
									  $('input[name="rules"]').val('lgtdefault');
									  $("#logout-form").submit();	
						  });
				  }
			  });             	
				 }else{
				  Swal.fire({
						icon: 'error',
						title: $('#login-data-validation-error-swal-header').text(),
						text: $('#login-data-validation-error-swal-checkdata').text()
						//footer: '<a href>Why do I have this issue?</a>'
					  }) 
					 
					 return false;
				 }
	  });

function showc() {
    var a = document.getElementById("upwd");
    if (a.type == "password") {
      a.type = "text";
    } else {
      a.type = "password";

    }

    $("#hide-password").removeClass("hidden");
    $("#show-password").addClass("hidden");
  }

  function hidec() {
    var a = document.getElementById("upwd");
    if (a.type == "text") {
      a.type = "password";
    } else {
      a.type = "text";
    }
    $("#show-password").removeClass("hidden");
    $("#hide-password").addClass("hidden");
  }
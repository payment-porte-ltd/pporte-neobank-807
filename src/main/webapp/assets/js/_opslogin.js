/**
 * Ops Login page js
 */
i18next.init({
	lng: 'en',
	fallbackLng: 'en',
	debug: false,
	resources: {
	  en: {
		translation: {
		  "login-language-title" : "Languages", 
		  "login-link-sign-to-acccount" : "Sign In to your account",
		
		}
	  },
	  es: {
		  translation: {
		  "login-language-title" : "Idiomas", 
		  "login-link-sign-to-acccount" : "Iniciar sesion en su cuenta",
		 
		}
		}
	}
  }, function(err, t) {
	   updateContent();
  });

	  function updateContent() {
		  $('#login-language-title').text(i18next.t('login-language-title')); 
		  $('#login-link-sign-to-acccount').text(i18next.t('login-link-sign-to-acccount'));
		 
	}
	  function fnChangePageLanguage(lng){
		  i18next.changeLanguage(lng, fnChangeLanguage(lng))
	  }

	  i18next.on('languageChanged', function(lng) {
		updateContent(lng);
	  });
			 
	  function fnChangeLanguage(lang){
		  if(lang=='en')  $('#lang_def').text('EN') 
			else if(lang=='es')  $('#lang_def').text('ES')
		  $('#hdnlangpref1,#hdnlangpref2,#hdnlangpref3').val(lang);
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
			  $('#login-form input[name="rules"]').val('opslogin');
			  $('#login-form input[name="hdnusertype"]').val($('input[name=login-usertype]:checked', '#login-form').val());
			  var formData = new FormData($('#login-form')[0]);
			  if (captcha.valid($('#login-form input[name="ucaptcha"]').val()) == false) {
				  Swal.fire({
					  icon: 'error',
					  title: 'Oops',
					  text: 'Text does not match',
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
			  $.ajax({
				  url: '../ms',
				  data: formData,
				  processData: false,
				  contentType: false,
				  type: 'POST',
				  success: function (result) {
					  //alert('result is '+result);
					 	 var data = JSON.parse(result);
						 var passwordtype=data.passwordtype;
						 console.log(passwordtype)
					 	 if(data.error==='false'){
						  //alert('lgtoken is '+data.token)
						  $('#logout-form').attr('action', '../'+fnGetOpsServletPath());
						  $('#logout-form input[name="qs"]').val('lgn');
						  $('#logout-form input[name="rules"]').val('opslgnfetch');
						  $('#logout-form input[name="hdnuserid"]').val( $("#uname").val() );
						  $('#logout-form').append('<input type="hidden" name="lgtoken" value='+data.token+' />');
						  //alert('lgtoken is '+$('#logout-form input[name="lgtoken"]').val())
						  $("#logout-form").submit();						
						  }else if (data.error==='temp'){
								$('#logout-form').attr('action', '../'+fnGetOpsServletPath());
							  	$('#logout-form input[name="qs"]').val('lgt');
							  	$('#logout-form input[name="rules"]').val('ops_fetch_password_page');
							    $('#logout-form input[name="hdnuserid"]').val( $("#uname").val() );
							  	$("#logout-form").submit();	
							
					  }else{
						Swal.fire({
								  icon: 'error',
								  title: 'Oops',
								  text: data.message,
								  showConfirmButton: true,
								  confirmButtonText: "Ok",
								  }).then(function() {
									  $('#logout-form').attr('action','../'+fnGetOpsServletPath());
									  $('#logout-form input[name="qs"]').val('lgt');
									  $('#logout-form input[name="rules"]').val('opslgtdefault');
									  $("#logout-form").submit();	
							  });
					}
				  },
				  error: function() {
					  Swal.fire({
								  icon: 'error',
								  title: 'Oops',
								  text: 'Problem with connection',
								  showConfirmButton: true,
								  confirmButtonText: "Ok",
								  }).then(function() {
									 
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





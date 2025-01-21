$(function(){
	document.getElementById("custcontact").focus();
      // Vanilla Javascript
      var phoneno = document.querySelector("#custcontact");
      iti = window.intlTelInput(phoneno, ({
        // options here
      //  preferredCountries: ["ve"],
        utilsScript: "assets/plugins/int-tel-input/utils.js",
        autoHideDialCode: false,
        separateDialCode: true,
        formatOnDisplay: true
      }));
	 	$("#custcountry").countrySelect({
				// defaultCountry: "jp",
				// onlyCountries: ['us', 'gb', 'ch', 'ca', 'do'],
				preferredCountries: ['us', 'uk']
			});
});

	
i18next.init({
	lng: 'en',
	fallbackLng: 'en',
	debug: false,
	resources: {
	  en: {
		translation: {
			"register-language-title":"Language",
			"register-label-title-header":"Provide your registration details",
			"personal-information-label":"Personal Information",
			"register-label-fullname":"Full Name",
			"register-label-mobileno":"Mobile Number",
			"register-label-emailadd":"Email Address",
			"register-label-confirmemailadd":"Confirm Email Address",
			"register-label-pass":"Password",
			"register-label-confirmpass":"Confirm Password",
			"customer-details-label":"Customer Details",
			"register-label-idno":"Identification Number",
			"register-label-passportno":"Passport Number",
			"register-label-address":"Address",
			"register-label-dob":"Date Of Birth",
			"register-label-gender":"Gender",
			"register-label-please-select":"Please Select",
			"register-label-male":"Male",
			"register-label-female":"Female",
			"register-label-other":"Other",
			"register-label-file-upload":"File Upload",
			"register-label-upload-file":"Upload Driving License or Passport",
			"registration-data-validation-error-swal-header":"Oops..",
			"registration-data-validation-error-swal-checkdata":"Please check your data",
			"registration-data-validation-firstname":"Please enter first name",
			"registration-data-validation-lastname":"Please enter last name",
			"registration-data-validation-mobileno":"Please enter mobile number",
			"registration-data-validation-mobileno-length":"Your mobile number must consist of at least 7 digits",
			"registration-data-validation-email":"Please enter email address",
			"register-data-validation-email-valid":"Please enter a proper email address",
			"registration-data-validation-email-confirmation":"Please confirm your email address",
			"registration-data-validation-email-match":"Must be same as email entered",
			"register-data-validation-upwd":"Please enter password",
			"register-data-validation-upwd-length":"Your password must consist of at least 8 characters",
			"register-data-validation-upwd-confirmation":"Please confirm your password",
			"register-data-validation-upwd-match":"Your password does not match",
			"registration-data-validation-idno":"Please enter identification number",
			"registration-data-validation-passportno":"Please enter passport number",
			"registration-data-validation-street":"Please enter street",
			"registration-data-validation-city":"Please enter city",
			"registration-data-validation-postalcode":"Please enter postalcode",
			"registration-data-validation-country":"Please enter country",
			"registration-data-validation-dob":"Please enter date of birth",
			"registration-data-validation-gender":"Please enter gender",
			"registration-data-validation-file":"Please upload file",
			"register-data-validation-error-swal-checkdata":"Registration Failed, please try again",
			"register-data-validation-error-swal-contact-admin":"If issue persists please contact Admin",
			"register-data-validation-success-header":"All Good",
			"register-data-validation-success-message":"Registration Successful",
			"register-data-file-allowed":"Only files of type docx,rtf,doc,pdf,jpeg,PNG is allowed",
			"register-label-terms":"I agree terms & Conditions",
			"registration-data-validation-accept-terms":"Please accept terms & conditions before submitting your credentials",
			"login-link-register":"Login",
			"sign-up-header-1":"Sign Up",
			"sign-up-header-2":"Sign Up",
			"registration-instraction":"Please provide your registration details",
			"first-name-label":"First Name",
			"last-name-label":"Last Name",
			"mobile-label":"Mobile",
			"email-address-label":"Email Address",
			"confirm-email-address-label":"Confirm Email Address",
			"password-label":"Password",
			"confirm-password-label":"Confirm Password",
			"pesornal-details-next-button-1-submit":"Next",
			"license-no-radio-label":"License Number",
			"passport-no-radio-label":"Passport",
			"license-no-label":"License Number",
			"password-no-label":"Passport Number",
			"street-label":"Street",
			"city-label":"City",
			"postal-code-label":"Postal Code (Optional)",
			"country-label":"Country",
			"dob-label":"Date Of Birth",
			"register-label-gender":"Gender",
			"pesornal-details-previous-button-2-submit":"Previous",
			"pesornal-details-next-2-button-submit":"Next",
			"register-utility-label-upload-file":"Upload Utility Bill",
			"pesornal-details-previous-2-button-submit":"Previous",
			"spn-click-label":"Click",
			"spn-here-label":"here",
			"spn-read-terms":"to read terms and conditions",
			"pesornal-details-previous-3-button-submit":"Previous",
			"pesornal-details-complete-button-submit":"Complete Signup",
			"terms-condition-madal-title":"Terms and Condition",
			"registration-data-email-exist":"Email exists, please use another email",
			"registration-data-validation-problem-with-connection":"Problem with connection",
	
		}
	  },
	  es: {
		  translation: {
					"register-language-title":"Idioma",
					"register-label-title-header":"Proporcione sus datos de registro",
					"personal-information-label":"Informacion personal",
					"register-label-fullname":"Nombre completo",
					"register-label-mobileno":"Número de teléfono móvil",
					"register-label-emailadd":"Correo eléctronico ",
					"register-label-confirmemailadd":"Confirmar correo electrónico",
					"register-label-pass":"Contraseña",
					"register-label-confirmpass":"Confirmar contraseña",
					"customer-details-label":"Detalles del cliente",
					"register-label-idno":"Número de identificación",
					"register-label-passportno":"Número de pasaporte",
					"register-label-address":"Dirección",
					"register-label-dob":"Fecha de cumpleaños",
					"register-label-gender":"Género",
					"register-label-please-select":"Por favor seleccione",
					"register-label-male":"Masculino",
					"register-label-female":"Mujer",
					"register-label-other":"Otra",
					"register-label-file-upload":"Subir archivo",
					"register-label-upload-file":"Subir archivo",
					"registration-data-validation-error-swal-header":"Oops..",
					"registration-data-validation-error-swal-checkdata":"Por favor revise sus datos",
					"registration-data-validation-firstname":"Por favor, introduzca el nombre",
					"registration-data-validation-lastname":"Por favor ingrese apellido",
					"registration-data-validation-mobileno":"Ingrese el número de celular",
					"registration-data-validation-mobileno-length":"Tu número de móvil debe tener al menos 7 dígitos",
					"registration-data-validation-email":"Ingrese su correo electrónico",
					"register-data-validation-email-valid":"Ingrese un correo electrónico adecuado",
					"registration-data-validation-email-confirmation":"Por favor confirme su dirección de correo electrónico",
					"registration-data-validation-email-match":"Debe ser la misma que el correo electrónico anterior",
					"register-data-validation-upwd":"Ingrese contraseña",
					"register-data-validation-upwd-length":"Su contraseña debe tener al menos 8 caracteres",
					"register-data-validation-upwd-confirmation":"Por favor, confirme su contraseña",
					"register-data-validation-upwd-match":"Tu contraseña no coincide",
					"registration-data-validation-idno":"Ingrese el número de identificación",
					"registration-data-validation-passportno":"Please enter passport number",
					"registration-data-validation-dob":"Ingrese la fecha de nacimiento",
					"registration-data-validation-gender":"Ingrese el género",
					"registration-data-validation-file":"Cargue el archivo",
					"registration-data-validation-street":"Por favor ingrese la calle",
					"registration-data-validation-city":"Por favor ingrese la ciudad",
					"registration-data-validation-postalcode":"Por favor ingrese el código postal",
					"registration-data-validation-country":"Por favor ingrese el país",
					"register-data-validation-error-swal-checkdata":"Registro fallido, inténtelo de nuevo",
					"register-data-validation-error-swal-contact-admin":"Si el problema persiste, comuníquese con el administrador",
					"register-data-validation-success-header":"Toda buena",
					"register-data-validation-success-message":"Registro exitoso",
					"register-label-terms":"Acepto términos y condiciones",
					"registration-data-validation-accept-terms":"Acepte los términos y condiciones antes de enviar sus credenciales",
					"login-link-register":"Acceso",
					"sign-up-header-1":"Inscribirse",
					"sign-up-header-2":"Inscribirse",
					"registration-instraction":"Por favor proporcione sus datos de registro",
					"first-name-label":"Primer nombre",
					"last-name-label":"Apellido",
					"mobile-label":"Móvil",
					"email-address-label":"Dirección de correo electrónico",
					"confirm-email-address-label":"Confirmar el correo",
					"password-label":"Clave",
					"confirm-password-label":"Confirmar contraseña",
					"pesornal-details-next-button-1-submit":"Próximo",
					"license-no-radio-label":"Número de licencia",
					"passport-no-radio-label":"Pasaporte",
					"license-no-label":"Número de licencia",
					"password-no-label":"Número de pasaporte",
					"street-label":"Calle",
					"city-label":"Ciudad",
					"postal-code-label":"Código Postal (Opcional)",
					"country-label":"País",
					"dob-label":"Fecha de nacimiento",
					"register-label-gender":"Género",
					"pesornal-details-previous-button-2-submit":"Previo",
					"pesornal-details-next-2-button-submit":"Próximo",
					"register-utility-label-upload-file":"Cargar factura de servicios públicos",
					"pesornal-details-previous-2-button-submit":"Previo",
					"spn-click-label":"Hacer clic",
					"spn-here-label":"aquí",
					"spn-read-terms":"para leer términos y condiciones",
					"pesornal-details-previous-3-button-submit":"Previo",
					"pesornal-details-complete-button-submit":"Registro completo",
					"terms-condition-madal-title":"Términos y Condiciones",
					"registration-data-email-exist":"El correo electrónico existe, utilice otro correo electrónico",
					"registration-data-validation-problem-with-connection":"Problema con la conexion",
		}
		}
	}
  }, function(err, t) {
	   updateContent();
  });

	  function updateContent() {
			  $('#register-language-title').text(i18next.t('register-language-title')); 
			  $('#register-label-title-header').text(i18next.t('register-label-title-header')); 
			  $('#personal-information-label').text(i18next.t('personal-information-label')); 
			  $('#register-label-fullname').text(i18next.t('register-label-fullname')); 
			  $('#register-label-mobileno').text(i18next.t('register-label-mobileno')); 
			  $('#register-label-mobileno').text(i18next.t('register-label-mobileno')); 
			  $('#register-label-emailadd').text(i18next.t('register-label-emailadd')); 
			  $('#register-label-confirmemailadd').text(i18next.t('register-label-confirmemailadd')); 
			  $('#register-label-pass').text(i18next.t('register-label-pass')); 
			  $('#register-label-confirmpass').text(i18next.t('register-label-confirmpass')); 
			  $('#customer-details-label').text(i18next.t('customer-details-label')); 
			  $('#register-label-idno').text(i18next.t('register-label-idno')); 
			  $('#register-label-passportno').text(i18next.t('register-label-passportno')); 
			  $('#register-label-address').text(i18next.t('register-label-address')); 
			  $('#register-label-dob').text(i18next.t('register-label-dob')); 
			  $('#register-label-gender').text(i18next.t('register-label-gender')); 
			  $('#register-label-please-select').text(i18next.t('register-label-please-select')); 
			  $('#register-label-male').text(i18next.t('register-label-male')); 
			  $('#register-label-female').text(i18next.t('register-label-female')); 
			  $('#register-label-other').text(i18next.t('register-label-other')); 
			  $('#register-label-file-upload').text(i18next.t('register-label-file-upload')); 
			  $('#register-label-upload-file').text(i18next.t('register-label-upload-file')); 
			  $('#registration-data-validation-error-swal-header').text(i18next.t('registration-data-validation-error-swal-header')); 
			  $('#registration-data-validation-error-swal-checkdata').text(i18next.t('registration-data-validation-error-swal-checkdata')); 
			  $('#registration-data-validation-fullname').text(i18next.t('registration-data-validation-fullname')); 
			  $('#registration-data-validation-mobileno').text(i18next.t('registration-data-validation-mobileno')); 
			  $('#registration-data-validation-mobileno-length').text(i18next.t('registration-data-validation-mobileno-length')); 
			  $('#registration-data-validation-email').text(i18next.t('registration-data-validation-email')); 
			  $('#register-data-validation-email-valid').text(i18next.t('register-data-validation-email-valid')); 
			  $('#registration-data-validation-email-confirmation').text(i18next.t('registration-data-validation-email-confirmation')); 
			  $('#registration-data-validation-email-match').text(i18next.t('registration-data-validation-email-match')); 
			  $('#register-data-validation-upwd').text(i18next.t('register-data-validation-upwd')); 
			  $('#register-data-validation-upwd-length').text(i18next.t('register-data-validation-upwd-length')); 
			  $('#register-data-validation-upwd-confirmation').text(i18next.t('register-data-validation-upwd-confirmation')); 
			  $('#register-data-validation-upwd-match').text(i18next.t('register-data-validation-upwd-match')); 
			  $('#registration-data-validation-idno').text(i18next.t('registration-data-validation-idno')); 
			  $('#registration-data-validation-passportno').text(i18next.t('registration-data-validation-passportno')); 
			  $('#registration-data-validation-address').text(i18next.t('registration-data-validation-address')); 
			  $('#registration-data-validation-dob').text(i18next.t('registration-data-validation-dob')); 
			  $('#registration-data-validation-gender').text(i18next.t('registration-data-validation-gender')); 
			  $('#registration-data-validation-file').text(i18next.t('registration-data-validation-file')); 
			  $('#registration-data-validation-address').text(i18next.t('registration-data-validation-address')); 
			  $('#register-data-validation-error-swal-checkdata').text(i18next.t('register-data-validation-error-swal-checkdata')); 
			  $('#register-data-validation-error-swal-contact-admin').text(i18next.t('register-data-validation-error-swal-contact-admin')); 
			  $('#register-data-validation-success-header').text(i18next.t('register-data-validation-success-header')); 
			  $('#register-data-validation-success-message').text(i18next.t('register-data-validation-success-message')); 
			  $('#register-data-file-allowed').text(i18next.t('register-data-file-allowed')); 
			  $('#register-label-terms').text(i18next.t('register-label-terms')); 
			  $('#registration-data-validation-accept-terms').text(i18next.t('registration-data-validation-accept-terms')); 
			  $('#login-link-register').text(i18next.t('login-link-register')); 
			  $('#sign-up-header-1').text(i18next.t('sign-up-header-1')); 
			  $('#sign-up-header-2').text(i18next.t('sign-up-header-2')); 
			  $('#registration-instraction').text(i18next.t('registration-instraction')); 
			  $('#first-name-label').text(i18next.t('first-name-label')); 
			  $('#mobile-label').text(i18next.t('mobile-label')); 
			  $('#email-address-label').text(i18next.t('email-address-label')); 
			  $('#confirm-email-address-label').text(i18next.t('confirm-email-address-label')); 
			  $('#password-label').text(i18next.t('password-label')); 
			  $('#confirm-password-label').text(i18next.t('confirm-password-label')); 
			  $('#pesornal-details-next-button-1-submit').text(i18next.t('pesornal-details-next-button-1-submit')); 
			  $('#license-no-radio-label').text(i18next.t('license-no-radio-label')); 
			  $('#passport-no-radio-label').text(i18next.t('passport-no-radio-label')); 
			  $('#license-no-label').text(i18next.t('license-no-label')); 
			  $('#password-no-label').text(i18next.t('password-no-label')); 
			  $('#street-label').text(i18next.t('street-label')); 
			  $('#city-label').text(i18next.t('city-label')); 
			  $('#postal-code-label').text(i18next.t('postal-code-label')); 
			  $('#country-label').text(i18next.t('country-label')); 
			  $('#cdob-label').text(i18next.t('dob-label')); 
			  $('#register-label-gender').text(i18next.t('register-label-gender')); 
			  $('#pesornal-details-previous-button-2-submit').text(i18next.t('pesornal-details-previous-button-2-submit')); 
			  $('#pesornal-details-next-2-button-submit').text(i18next.t('pesornal-details-next-2-button-submit')); 
			  $('#register-utility-label-upload-file').text(i18next.t('register-utility-label-upload-file')); 
			  $('#pesornal-details-previous-2-button-submit').text(i18next.t('pesornal-details-previous-2-button-submit')); 
			  $('#spn-click-label').text(i18next.t('spn-click-label')); 
			  $('#spn-here-label').text(i18next.t('spn-here-label')); 
			  $('#spn-read-terms').text(i18next.t('spn-read-terms')); 
			  $('#pesornal-details-previous-3-button-submit').text(i18next.t('pesornal-details-previous-3-button-submit')); 
			  $('#pesornal-details-complete-button-submit').text(i18next.t('pesornal-details-complete-button-submit')); 
			  $('#terms-condition-madal-title').text(i18next.t('terms-condition-madal-title')); 
			  $('#registration-data-email-exist').text(i18next.t('registration-data-email-exist'));
			  $('#registration-data-validation-problem-with-connection').text(i18next.t('registration-data-validation-problem-with-connection'));
			  $('#last-name-label').text(i18next.t('last-name-label'));

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
		  $('#hdnlangpref1,#hdnlangpref2,#hdnlangpref3,hdnlangpref6').val(lang);
	  }
 $(document).ready(function () {
	$( "#personal_details_form" ).validate( {
		rules: {
			firstname: {
				required: true
			},
			lastname: {
				required: true
			},
			custcontact: {
				required: true,
				minlength:7
			},
			custemailadd: {
				required: true,
				email: true
			}, 
			confirmemailadd: {
				required: true,
				equalTo: "#custemailadd"
			},
			custpass: {
				required: true,
				minlength:8
			},
			confirmpass: {
				required: true,
				equalTo:"#custpass"
			}
		},	
		messages: {
			firstname: {
				required: $('#registration-data-validation-firstname').text()
			},
			lastname: {
				required: $('#registration-data-validation-lastname').text()
			},
			custcontact: {
				required: $('#registration-data-validation-mobileno').text(),
				minlength: $('#registration-data-validation-mobileno-length').text()
			},
			custemailadd: {
				required: $('#registration-data-validation-email').text(),
				email: $('#register-data-validation-email-valid').text()
			},
			confirmemailadd: {
				required: $('#registration-data-validation-email-confirmation').text(),
				equalTo: $('#registration-data-validation-email-match').text()
			},
			custpass: {
				required: $('#register-data-validation-upwd').text(),
				minlength: $('#register-data-validation-upwd-length').text()
			},
			confirmpass: {
				required: $('#register-data-validation-upwd-confirmation').text(),
				equalTo: $('#register-data-validation-upwd-match').text()
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

	 $( "#customer_details_form" ).validate({
		rules: {
			custidno: {
				required: true
			},
			custpassno: {
				required: true
			},
			custstreet: {
				required: true
			},
			custcity: {
				required: true
			},
			custcountry: {
				required: true
			},
			custdob: {
				required: true
			},
			gender: {
				required: true
			}
		},
		message:{
			custidno: {
				required: $('#registration-data-validation-idno').text()
			},
			custpassno: {
				required: $('#registration-data-validation-passportno').text()
			},
			custstreet: {
				required: $('#registration-data-validation-street').text()
			},
			custcity: {
				required: $('#registration-data-validation-city').text()
			},
			custcountry: {
				required: $('#registration-data-validation-country').text()
			},
			custdob: {
				required: $('#registration-data-validation-dob').text()
			},
			gender: {
				required: $('#registration-data-validation-gender').text()
			},
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
	 
	 $( "#upload_files_form" ).validate({
		rules: {
			custfile:{
				required: true,
			},
			custfile_1:{
				required: true,
			}
		},
		message:{
			custfile: {
				required: $('#registration-data-validation-file').text()
			},
			custfile_1: {
				required: $('#registration-data-validation-file').text()
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
	 $( "#terms_and_conditions_form" ).validate({
		rules: {
			checkbox2:{
				required: true
			}	
		},
		message:{
			checkbox2:{
				required: $('#registration-data-validation-accept-terms').text()
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

 });	
   function dispDiv(rrr){
        if(rrr == "passport"){
          $( "#custidno" ).hide();
          $( "#custpassno" ).show();

        } else{
          $( "#custidno" ).show();
          $( "#custpassno" ).hide();
        }
    }


 function fnPersonalDetailsNextBtn(){
	if ($('#personal_details_form').valid()) {
		// Check if user exists. 
		 var validateemail=   fnCheckIfEmailExists();
	
			  if (validateemail ==="true"){
					$("#personal-details").addClass("hidden");
					$("#customer-details").removeClass("hidden");
					$("#upload-files").addClass("hidden");
					$("#terms-and-conditions").addClass("hidden");
			}else if (validateemail ==="false"){
                Swal.fire({
                    title: $('#registration-data-validation-error-swal-header').text(),              
                    text: $('#registration-data-email-exist').text(), 
                    icon: "error",
                     showConfirmButton: true,
                     confirmButtonText: "Ok",
                     closeOnConfirm: true,
                  }).then(function() {

                  });

              }
	}else{
		Swal.fire({
			icon: 'error',
			title: $('#registration-data-validation-error-swal-header').text(),
			text: $('#registration-data-validation-error-swal-checkdata').text()
			//footer: '<a href>Why do I have this issue?</a>'
		})
		return false;
	}
	
 }


 function fnCustomerDetailsPreviousBtn(){
	 
			$("#personal-details").removeClass("hidden");
			$("#customer-details").addClass("hidden");
			$("#upload-files").addClass("hidden");
			$("#terms-and-conditions").addClass("hidden");

 }
 function fnCustomerDetailsNextBtn(){
	 var custdate = $("#custdob").val();
	  var age =getAge(custdate);
			if(age <18){
				 swal.fire({
                     icon: 'error',
                     text: 'The age should be 18 years and above',
                 });				return;
			}
	if ($('#customer_details_form').valid()) {
		$("#personal-details").addClass("hidden");
		$("#customer-details").addClass("hidden");
		$("#upload-files").removeClass("hidden");
		$("#terms-and-conditions").addClass("hidden");
	}else{
		Swal.fire({
			icon: 'error',
			title: $('#registration-data-validation-error-swal-header').text(),
			text: $('#registration-data-validation-error-swal-checkdata').text()
			//footer: '<a href>Why do I have this issue?</a>'
		})
		return false;
	}

 }
 function fnUploadFilesPreviousBtn(){
	$("#personal-details").addClass("hidden");
	$("#customer-details").removeClass("hidden");
	$("#upload-files").addClass("hidden");
	$("#terms-and-conditions").addClass("hidden");
 }
 function fnTermsandConditionPreviousBtn(){
	$("#personal-details").addClass("hidden");
	$("#customer-details").addClass("hidden");
	$("#upload-files").removeClass("hidden");
	$("#terms-and-conditions").addClass("hidden");
 }
 function fnUploadFilesNextBtn(){
	if ($('#upload_files_form').valid()) {
		$("#personal-details").addClass("hidden");
		$("#customer-details").addClass("hidden");
		$("#upload-files").addClass("hidden");
		$("#terms-and-conditions").removeClass("hidden");
	}else{
		Swal.fire({
			icon: 'error',
			title: $('#registration-data-validation-error-swal-header').text(),
			text: $('#registration-data-validation-error-swal-checkdata').text()
			//footer: '<a href>Why do I have this issue?</a>'
		})
		return false;
	}
 }


function fnCompleteSignUp(){
	  //Check for the data validation	
	  //validate data
	
	 if($( "#terms_and_conditions_form" ).valid()){
		
		
			$('#spinner-div').show();//Load button clicked show spinner
				 var name= $("#firstname").val()+" "+$("#lastname").val();
				 var contact= $("#custcontact").val();
				 var emailAddress= $("#custemailadd").val();
				 var password= $("#custpass").val();
				 var idNo= $("#custidno").val();
				 var passportNo= $("#custpassno").val();
				 var address= $("#custstreet").val()+" "+$("#custcity").val()+" "+$("#custpostalcode").val()+" "+$("#custcountry").val();
				 var dob= $("#custdob").val();
				 var gender= $("#gender").val();
				 var countryCode = iti.getSelectedCountryData().dialCode;
                 var custcontact = $("#custcontact").val();
                var customercontact="+"+countryCode+""+custcontact;

	               $('#upload_files_form').attr('action', 'ms');
					 $('input[name="hdncustname"]').val(name); 
					 $('input[name="hdncustcontact"]').val(customercontact); 
					 $('input[name="hdncustemailadd"]').val(emailAddress); 
					 $('input[name="hdncustpass"]').val(password); 
					 $('input[name="hdncustidno"]').val(idNo); 
					 $('input[name="hdncustaddress"]').val(address); 
					 $('input[name="hdncustdob"]').val(dob); 
					 $('input[name="hdncustpassno"]').val(passportNo); 
					 $('input[name="hdngender"]').val(gender); 
					 $('input[name="hdnapikey"]').val(getAPIKey()); 
					 $('input[name="qs"]').val('reg'); 
					 $('input[name="rules"]').val('regnewcust'); 
				     $("#upload_files_form").submit();
				  }else{
					  Swal.fire({
						  icon: 'error',
						  title: $('#registration-data-validation-error-swal-header').text(),
						  text: $('#registration-data-validation-error-swal-checkdata').text()
						  //footer: '<a href>Why do I have this issue?</a>'
					  })
					  return false;
				  }
		
	
}

var validateEmail = '';
function fnCheckIfEmailExists(){
  
    var emailid = $('#custemailadd').val();
    console.log(emailid);
    var newFormData = new FormData();
    newFormData.append("qs",'reg');
    newFormData.append("rules",'checkifuserexists');
    newFormData.append("email",emailid);
	$.ajaxSetup({
				beforeSend: function(xhr) {
					xhr.setRequestHeader('x-api-key' , getAPIKey());
				}
		});
		$('#spinner-div').show();//Load button clicked show spinner
    $.ajax({
        async: false,
        url: 'ms',
        data: newFormData,
        processData: false,
        contentType: false,
        type: 'POST',
        success: function (result) {
		$('#spinner-div').hide();//Request is complete so hide spinner
            if (result) {
                var data = JSON.parse(result);
                 if(data.error=='doesnotexist'){
                    $("#erroremailexist").text("");
                     validateEmail = 'true';
                 }else if (data.error =='userexist'){ 
                     //swal.fire('Error message');
                     console.log('Customer Exists')
                     $("#erroremailexist").text("");
                     validateEmail = 'false';
                      $("#erroremailexist").text($('#registration-data-email-exist').text());
                          	
                 } else if (data.error =='bad'){ 
                    //swal.fire('Error message');
                          Swal.fire({
                          title: $('#registration-data-validation-error-swal-header').text(),              
                          text: data.message, 
                          icon: "error",
                           showConfirmButton: true,
                           confirmButtonText: "Ok",
                           closeOnConfirm: true,
                        }).then(function() {
                        });	
                } 
            }
         },
         error: function() {
            Swal.fire({
                        icon: 'error',
                        title: $('#registration-data-validation-error-swal-header').text(),
                        text: $('#registration-data-validation-problem-with-connection').text(),
                        showConfirmButton: true,
                        confirmButtonText: "Ok",
                        closeOnConfirm: true,
                        }).then(function() {
                           
                });
                return;
        }
    });

return validateEmail;
}

   function getAge(dateString) {
		var today = new Date();
		var birthDate = new Date(dateString);
		var age = today.getFullYear() - birthDate.getFullYear();
		var m = today.getMonth() - birthDate.getMonth();
		if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
			age--;
		}
		return age;
	}
function fnRedirectToLoginPage() {
	  $('#terms_and_conditions_form').attr('action', 'ws');
	  $('input[name="qs"]').val('lgt');
	  $('input[name="rules"]').val('lgtdefault');
	  $("#terms_and_conditions_form").submit();
}

function show() {
    var a = document.getElementById("custpass");
    if (a.type == "password") {
      a.type = "text";
    } else {
      a.type = "password";

    }

    $("#hide-password").removeClass("hidden");
    $("#show-password").addClass("hidden");
  }

  function hide() {
    var a = document.getElementById("custpass");
    if (a.type == "text") {
      a.type = "password";
    } else {
      a.type = "text";
    }
    $("#show-password").removeClass("hidden");
    $("#hide-password").addClass("hidden");
  }
function showc() {
    var a = document.getElementById("confirmpass");
    if (a.type == "password") {
      a.type = "text";
    } else {
      a.type = "password";

    }

    $("#hide-confirm-password").removeClass("hidden");
    $("#show-confirm-password").addClass("hidden");
  }

  function hidec() {
    var a = document.getElementById("confirmpass");
    if (a.type == "text") {
      a.type = "password";
    } else {
      a.type = "text";
    }
    $("#show-confirm-password").removeClass("hidden");
    $("#hide-confirm-password").addClass("hidden");
  }
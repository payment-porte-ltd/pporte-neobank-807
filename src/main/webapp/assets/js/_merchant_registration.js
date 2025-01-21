i18next.init({
	lng: 'en',
	fallbackLng: 'en',
	debug: false,
	resources: {
	  en: {
		translation: {
		  "login-language-title" : "Languages", 
		  "merchernt-registration-title" : "Merchant Registration", 
		  "merchernt-registration-form-title1" : "Business details", 
		  "merchernt-registration-form-title2" : "Merchant user details", 
		  "merchernt-registration-form-title3" : "Merchant file upload", 
		  "merchernt-registration-form-title4" : "Merchant user credentials", 
		  "merchernt-registration-companyname-label" : "Company name", 
		  "merchernt-registration-bsndesc-label" : "Business description", 
		  "merchernt-registration-physic-add-label" : "Business physical address", 
		  "merchernt-registration-bsnphoneno-label" : "Business phone number", 
		  "merchernt-registration-mcc-label" : "Merchant category", 
		  "merchernt-registration-branches-label" : "Click here if your business has branches", 
		  "merchernt-registration-fulname-label" : "Full name", 
		  "merchernt-registration-email-label" : "Email address", 
		  "merchernt-registration-phono-label" : "Phone number", 
		  "merchernt-registration-idno-label" : "National identification number", 
		  "merchernt-registration-username-label" : "Merchant User Name", 
		  "merchernt-registration-password-label" : "Password", 
		  "merchernt-registration-confirm-password-label" : "Confirm Password", 
		  "merchernt-registration-terms-label" : "I agree terms & Conditions", 
		}
	  },
	  es: {
		  translation: {
		  "login-language-title" : "Idiomas",
		  "merchernt-registration-title" : "Registro de comerciante", 
		  "merchernt-registration-form-title1" : "Detalles de la empresa",
		  "merchernt-registration-form-title2" : "Detalles del usuario comercial", 
		  "merchernt-registration-form-title3" : "Carga de archivos del comerciante", 
		  "merchernt-registration-form-title4" : "Credenciales de usuario comercial",  
		  "merchernt-registration-companyname-label" : "Nombre de empresa",
		  "merchernt-registration-bsndesc-label" : "Descripción del negocio", 
		  "merchernt-registration-physic-add-label" : "Dirección física de la empresa", 
		  "merchernt-registration-bsnphoneno-label" : "Número de teléfono laboral", 
		  "merchernt-registration-mcc-label" : "Categoría de comerciante", 
		  "merchernt-registration-branches-label" : "Haga clic aquí si su empresa tiene sucursales", 
		  "merchernt-registration-fulname-label" : "Nombre completo", 
		  "merchernt-registration-email-label" : "Dirección de correo electrónico", 
		  "merchernt-registration-phono-label" : "Número de teléfono",
		  "merchernt-registration-idno-label" : "Número de Identificación Naciona", 
		  "merchernt-registration-username-label" : "Nombre de usuario del comerciante", 
		  "merchernt-registration-password-label" : "Contraseña", 
		  "merchernt-registration-confirm-password-label" : "Confirmar Contraseña", 
		  "merchernt-registration-terms-label" : "Acepto términos y condiciones", 
		}
		}
	}
  }, function(err, t) {
	   updateContent();
  });

function updateContent() {
    $('#login-language-title').text(i18next.t('login-language-title')); 
    $('#merchernt-registration-form-title1').text(i18next.t('merchernt-registration-form-title1')); 
    $('#merchernt-registration-title').text(i18next.t('merchernt-registration-title')); 
    $('#merchernt-registration-form-title2').text(i18next.t('merchernt-registration-form-title2')); 
    $('#merchernt-registration-form-title3').text(i18next.t('merchernt-registration-form-title3')); 
    $('#merchernt-registration-form-title3').text(i18next.t('merchernt-registration-form-title3')); 
    $('#merchernt-registration-form-title4').text(i18next.t('merchernt-registration-form-title4')); 
    $('#merchernt-registration-companyname-label').text(i18next.t('merchernt-registration-companyname-label')); 
    $('#merchernt-registration-physic-add-label').text(i18next.t('merchernt-registration-physic-add-label')); 
    $('#merchernt-registration-bsnphoneno-label').text(i18next.t('merchernt-registration-bsnphoneno-label')); 
    $('#merchernt-registration-mcc-label').text(i18next.t('merchernt-registration-mcc-label')); 
    $('#merchernt-registration-branches-label').text(i18next.t('merchernt-registration-branches-label')); 
    $('#merchernt-registration-fulname-label').text(i18next.t('merchernt-registration-fulname-label')); 
    $('#merchernt-registration-email-label').text(i18next.t('merchernt-registration-email-label')); 
    $('#merchernt-registration-phono-label').text(i18next.t('merchernt-registration-phono-label')); 
    $('#merchernt-registration-idno-label').text(i18next.t('merchernt-registration-idno-label')); 
    $('#merchernt-registration-username-label').text(i18next.t('merchernt-registration-username-label')); 
    $('#merchernt-registration-password-label').text(i18next.t('merchernt-registration-password-label')); 
    $('#merchernt-registration-confirm-password-label').text(i18next.t('merchernt-registration-confirm-password-label')); 
    $('#merchernt-registration-terms-label').text(i18next.t('merchernt-registration-terms-label')); 
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

(function($) {
	"use strict";
	// Toolbar extra buttons
	var btnFinish = $('<button></button>').text('Finish')
		.addClass('btn btn-success')
		.on('click', function(){
			// alert("Submit clicked")
			registerUser(); 
			return false;
			
		});
	var btnCancel = $('<button></button>').text('Cancel')
		.addClass('btn btn-danger')
		.on('click', function(){
			 $('#smartwizard').smartWizard("reset"); 
		return false;
		});


	// Smart Wizard
	$('#smartwizard').smartWizard({
			selected: 0,
			theme: 'default',
			transitionEffect:'fade',
			showStepURLhash: true,
			toolbarSettings: {
							  toolbarButtonPosition: 'end',
							  toolbarExtraButtons: [btnFinish, btnCancel]
							}
	});
		
	// Arrows Smart Wizard 1
	$('#smartwizard-1').smartWizard({
			selected: 0,
			theme: 'arrows',
			transitionEffect:'fade',
			showStepURLhash: false,
			toolbarSettings: {
							  toolbarExtraButtons: [btnFinish, btnCancel]
							}
	});
			
	// Circles Smart Wizard 1
	$('#smartwizard-2').smartWizard({
			selected: 0,
			theme: 'circles',
			transitionEffect:'fade',
			showStepURLhash: false,
			toolbarSettings: {
							  toolbarExtraButtons: [btnFinish, btnCancel]
							}
	});
			
	// Dots Smart Wizard 1
	$('#smartwizard-3').smartWizard({
			selected: 0,
			theme: 'dots',
			transitionEffect:'fade',
			showStepURLhash: false,
			toolbarSettings: {
							  toolbarExtraButtons: [btnFinish, btnCancel]
							}
	});
	
})(jQuery);


 /* Register method*/
function registerUser(){
	//validate data
 	$( "#merch-register-form" ).validate( {
	  rules: {
		  companyname: {
			  required: true,
			  minlength: 2
		  },
		  bsndesc: {
			  required: true,
			  minlength: 10
		  },
		  physicaladd: {
			  required: true,
			  minlength: 5
		  },
		  bsnphoneno: {
			  required: true,
			  minlength: 8
		  },
		  selectmcc: {
			  required: true
		  },
		  fullname: {
			  required: true,
			  minlength: 5
		  },
		  email: {
			 email: true,
			 required: true
		  },
		  merchphoneno: {
			 minlength: 8,
			 required: true
		  },
		  nationalId: {
			  required: true,
			  minlength: 5
		  },
		  checkbox2: {
			 required: true
		  },
		  selectpricingplan:{
			  required: true
		  },
		  file1:{
			  required: true,
			  extension: "docx|rtf|doc|pdf|jpeg|PNG"
		  }
	  },
	  messages: {
		  companyname: {
			  required: $('#reg-data-validation-companyname').text(),
			  minlength: $('#reg-data-validation-companyname-length').text()
		  },
		  bsndesc: {
			  required: $('#reg-data-validation-bsndesc').text(),
			  minlength: $('#reg-data-validation-bsndesc-length').text()
		  },
		  physicaladd: {
			  required: $('#reg-data-validation-physicaladd').text(),
			  minlength: $('#reg-data-validation-physicaladd-length').text()
		  },
		  bsnphoneno: {
			  required: $('#reg-data-validation-bsnphoneno').text(),
			  minlength: $('#reg-data-validation-bsnphoneno-length').text()
		  },

		  fullname: {
			  required: $('#reg-data-validation-fullname').text(),
			  minlength: $('#reg-data-validation-fullname-length').text()
		  },
		  nationalId: {
			  required: $('#reg-data-validation-nationalId').text(),
			  minlength: $('#reg-data-validation-nationalId-length').text()
		  },
		  email: {
			  email: $('#reg-data-validation-email').text(),
			  required: $('#reg-data-validation-email-required').text()
		  },
		  selectmcc: {
			  required: $('#reg-data-validation-selectmcc').text()
		  },
		  checkbox2: {
			  required: $('#reg-data-validation-checkbox2').text()
		  },
		  selectpricingplan: {
			  required: $('#reg-data-validation-selectpricingplan').text()
		  },
		  merchphoneno: {
			required: $('#reg-data-validation-merchphoneno').text(),
			minlength:$('#reg-data-validation-merchphoneno-length').text()
		},
		file1: {
			required: $('#reg-data-validation-file1').text()
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
	if($( "#merch-register-form" ).valid()){
	$('#merch-register-form input[name="qs"]').val('reg');
    $('#merch-register-form input[name="rules"]').val('regmerchant');
    $('#merch-register-form input[name="hdnmccid"]').val($('#selectmcc :selected').val());
    $('#merch-register-form input[name="hdnplan"]').val($('#selectpricingplan :selected').val());
    $('#merch-register-form input[name="hdnhavebranches"]').val($('#checkbox3').is(':checked'));
   	var form = $('#merch-register-form')[0];
    var formData = new FormData(form);
   
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
			if (result) {
				var data = JSON.parse(result);
					console.log("result is ", result);
					if(data.error=='false'){
						Swal.fire({
									icon: 'success',
									text: data.message,
									showConfirmButton: true,
									confirmButtonText: "Ok",
								}).then(function() {
									$('#logout-form').attr('action', 'ws');
									$('input[name="qs"]').val('lgt');
									$('input[name="rules"]').val('lgtdefault');
									$("#logout-form").submit();	
								});	
						}else{
							Swal.fire({
							text: data.message, 
							icon: "error",
							showConfirmButton: true,
							confirmButtonText: "Ok",
							}).then(function() {
								$('#select-user-form').attr('action', 'ws');
								$('input[name="qs"]').val('reg');
								$('input[name="rules"]').val('loadregistrationpage');
								$('input[name="hdnregusertype"]').val('M');
								$("#select-user-form").submit();
							});	
						} 
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
	}
    
}

function getMerchantCategory(){
	$('#get-mcc-form input[name="qs"]').val('reg');
	$('#get-mcc-form input[name="rules"]').val('getmcccategory');
	
	var form = $('#get-mcc-form')[0];
    var formData = new FormData(form);

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
			if (result) {
				var data = JSON.parse(result);
				var listMccGroups = data.data;
				var htmlOptions = '';
				for(var i = 0; i < listMccGroups.length; i++) {
					 htmlOptions +='<option value="'+listMccGroups[i].mccId
					+'">'+listMccGroups[i].mccName+'</option>' ;
				}
				$('#selectmcc').append(htmlOptions);
			}
			},
			error: function() {
			
		}
	}); 

}
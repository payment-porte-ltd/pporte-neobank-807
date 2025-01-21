i18next.init({
	lng: 'en',
	fallbackLng: 'en',
	debug: false,
	resources: {
	  en: {
		translation: {
			"spn_registration_successful":"Registration Successful",
			"spn_credential_aprovred_label":"Your credentials have been received by our team and are currently being verified. You'll get an email notification once your details have been approved.",
			"spn_welcome_label":"WELCOME TO PAYMENT PORTE!"
		}
	  },
	  es: {
		  translation: {
			"spn_registration_successful":"Registro exitoso",
			"spn_credential_aprovred_label":"Nuestro equipo ha recibido sus credenciales y actualmente se están verificando. Recibirá una notificación por correo electrónico una vez que se hayan aprobado sus datos.",
			"spn_welcome_label":"¡BIENVENIDOS A PORTE DE PAGO!"		
		}
		}
	}
  }, function(err, t) {
	   updateContent();
  });

	  function updateContent() {
			  $('#register-language-title').text(i18next.t('register-language-title')); 
			  $('#spn_registration_successful').text(i18next.t('spn_registration_successful')); 
			  $('#pn_welcome_label').text(i18next.t('pn_welcome_label')); 
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
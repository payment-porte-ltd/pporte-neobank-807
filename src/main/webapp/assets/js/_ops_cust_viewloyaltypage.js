
   i18next.init({
      lng: 'en',
      fallbackLng: 'en',
      debug: false,
      resources: {
        en: {
          translation: {
			  /* Nav Pages start */
            "idnav_UserSettings" : "User Settings", 
			"idnav_UpdateProfile" : "Update Profile",
			"idnav_Logout" : "Logout",

			/* start Page specific changes */
          }
        },
        es: {
			/* Nav Pages start */
            translation: {
            "idnav_UserSettings" : "Ajustes de usuario", 
			"idnav_UpdateProfile" : "Actualizaci�n del perfil",
			"idnav_Logout" : "Cerrar sesi�n",

			/* start Page specific changes */

          }
          }
      }
    }, function(err, t) {
         updateContent();
    });

        function updateContent() {
			/* Nav Pages start */
            $('#idnav_UserSettings').text(i18next.t('idnav_UserSettings')); 
			$('#idnav_UpdateProfile').text(i18next.t('idnav_UpdateProfile'));
			$('#idnav_Logout').text(i18next.t('idnav_Logout'));

			/* end Page specific changes */

      }
         
		function fnChangePageLang(lng){
			//alert ('inside navpage :' +lng)
			i18next.changeLanguage(lng, fnChangeLanguage(lng))
		}

		i18next.on('languageChanged', function(lng) {
		  updateContent(lng);
		});
               
        function fnChangeLanguage(lang){
            if(lang=='en' )  $('#lang_def').text('EN') 
          	else if(lang=='es')  $('#lang_def').text('ES')
			$('input[name="hdnlang"]').val(lang);

            //$('#hdnlangpref1,#hdnlangpref2,#hdnlangpref3').val(lang);
        }
			
	function fnOpsRedeemCustLoyalty(customercode, pointsaccrued){
		
            $('#opsredeempoints-form input[name="qs"]').val('opslyt');
			$('#opsredeempoints-form input[name="rules"]').val('opscustredeemlytlty');
			$('#opsredeempoints-form input[name="hdncustcode"]').val(customercode);
			$('#opsredeempoints-form input[name="hdnpointsaccrued"]').val(pointsaccrued);
		
			var formData = new FormData($('#opsredeempoints-form')[0]);
			$.ajaxSetup({
				beforeSend: function(xhr) {
					xhr.setRequestHeader('x-api-key', getAPIKey());
				}
			});
			$.ajax({
				url: 'ms',
				data: formData,
				processData: false,
				contentType: false,
				type: 'POST',
				success: function(result) {
					//alert('result is '+result);
					var data = JSON.parse(result);
					if (data.error == 'false') {
						console.log('result is ',data)
						Swal.fire({
							icon: 'success',
							title: 'Reddemed Successful',
							text: data.message,
							showConfirmButton: true,
							confirmButtonText: "Ok",
						}).then(function() {
							// location.reload();
							$('#redirect-form').attr('action', fnGetOpsServletPath());
							$('input[name="qs"]').val('opslyt');
							$('input[name="rules"]').val('View Customer Loyalty');
							$("#redirect-form").submit();
						});
		
					} else {
						Swal.fire({
							icon: 'error',
							title: 'Oops',
							text: data.error,
							showConfirmButton: true,
							confirmButtonText: "Ok",
						}).then(function() {
								//Do Nothing
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
						//Do Nothing
					});
				}
			});
			
        }	
		
		
		
		
		
		
	

		





 
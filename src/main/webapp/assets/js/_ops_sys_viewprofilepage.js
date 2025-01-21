
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
					
		$('#btn-editopsprf').click(function() {
			//Check for the data validation	
			$("#editopsprofile-form").validate({
				rules: {
					editopsuid: {
						required: true
					},
					editusername: {
						required: true
					},
					editusercontact: {
						required: true,
						digits : true,
						minlength: 9
					},
					editcreatedon: {
						required: true
	
					},
					edituseraccesstype:{
						required: true,
					},
					edituseremail:{
						required: true,
						email: true
					},
					edituserstatus:{
						required: true
					},
					editdatofexpiry:{
						required: true
					}
															
				},
				messages: {
					editopsuid: {
						required: 'User Id is Required'
					},
					editusername: {
						required: 'Please enter the User Name'
					},
					editusercontact: {
						required: 'Please enter the User Contact'
					},
					editcreatedon: {
						required: 'Createdon cannot be empty'
					},
					edituseraccesstype: {
						required: 'User Type cannot be empty'
					},
					edituseremail: {
						required: 'Please enter the User email'
					},
					edituserstatus: {
						required: 'Status cannot be empty'
					},
					editdatofexpiry: {
						required: 'Date of Expiry cannot be empty'
					}
					
				},
				errorElement: "em",
				errorPlacement: function(error, element) {
					// Add the `invalid-feedback` class to the error element
					error.addClass("invalid-feedback");
					if (element.prop("type") === "checkbox") {
						error.insertAfter(element.next("label"));
					} else {
						error.insertAfter(element);
					}
				},
				highlight: function(element, errorClass, validClass) {
					$(element).addClass("is-invalid").removeClass("is-valid");
				},
				unhighlight: function(element, errorClass, validClass) {
					$(element).addClass("is-valid").removeClass("is-invalid");
				}
			});
		
		
			if ($("#editopsprofile-form").valid()) {
				$('#editopsprofile-form').attr('action', 'ms');
				$('input[name="qs"]').val('prf');
				$('input[name="rules"]').val('opseditprofile');
				//console.log('rule is ' + $('input[name="rules"]').val())
				//set required attribute on input to true
				//$('input').attr('data-parsley-required', 'true');
		
				//$("#editopsprofile-form").submit();
				
			  var formData = new FormData($('#editopsprofile-form')[0]);
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
					  //alert('result is '+result);
					  var data = JSON.parse(result);
					  if(data.error=='false'){
						  //alert('lgtoken is '+data.token)
						   Swal.fire({
								  icon: 'success',
								  title: 'Good',
								  text: data.message,
								  showConfirmButton: true,
								  confirmButtonText: "Ok",
								  }).then(function() {
									  $('#editopsprofile-form').attr('action', fnGetOpsServletPath());
									  $('#editopsprofile-form input[name="qs"]').val('prf');
									  $('#editopsprofile-form input[name="rules"]').val('View Profile');
									  $("#editopsprofile-form").submit();
							  });						
						  
				          }else{
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
		
			} else {
				Swal.fire({
					icon: 'error',
					title: 'Oops..',
					text: 'Please check your data'
					//footer: '<a href>Why do I have this issue?</a>'
				})
		
				return false;
			}
		});	
	


 $("button[data-dismiss=modal]").click(function(){
	$(".modal").modal('hide');
});
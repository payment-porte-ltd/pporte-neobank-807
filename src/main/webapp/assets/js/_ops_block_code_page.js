
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

	$(document).ready ( function(){
			$('#viewmcctable').dataTable( {
			    "order": []
			} );
		});

	function fnEditBlockCodes(blockcodeid, blockcodedesc, status,authentication,createdon ){
	
		console.log('blockcodedesc is ', blockcodedesc)
		console.log('status is ', status)
		console.log('authentication is ', authentication)
			$('#user-form-modal').on('show.bs.modal', function () {
			$('#editblockid').val(blockcodeid);
			$('#editblockcodedesc').val(blockcodedesc);
			$('#seleditstatus').val(status).attr('selected','selected');
			$('#seleditauthenticate').val(authentication).attr('selected','selected');
			$("#editcreatedon").val(createdon);
	
			 });
		
			$('#user-form-modal').modal('show');
		
		}
		
		$('#btn-add-blockcodes').click(function() {
			//Check for the data validation	
			$("#addblockcode-form").validate({
				rules: {
					blockcodedesc: {
						required: true
					},
					selauthentication: {
						required: true
					},
					selblockstatus: {
						required: true
					}
				},
				messages: {
					blockcodedesc: {
						required: 'Enter Block Code Description'
					},
					selauthentication: {
						required: 'Please select authentication type'
					},
					selblockstatus: {
						required: 'Please select status'
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
		
		
			if ($("#addblockcode-form").valid()) {
		
				$('#addblockcode-form').attr('action', fnGetOpsServletPath());
				$('input[name="qs"]').val('opswal');
				$('input[name="rules"]').val('opsnewblockcode');
				$('input[name="hdnaddblockstatus"]').val($('#selblockstatus :selected').val());
				$('input[name="hdnaddselauthentication"]').val($('#selauthentication :selected').val());
				//console.log('rule is ' + $('input[name="rules"]').val())
				//set required attribute on input to true
				//$('input').attr('data-parsley-required', 'true');
		
				$("#addblockcode-form").submit();
		
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

		$('#btn-editblockcode').click(function() {															    
			//Check for the data validation	
			$("#editblockcodes-from").validate({
				rules: {
					editblockid: {
						required: true
					},
					editblockcodedesc: {
						required: true,
					},
					seleditstatus: { 
						required: true,
					},
					seleditauthenticate: {
						required: true,
					},
					editcreatedon: {
						required: true,
					}
				},
				messages: {
					editblockid: {
						required: 'Block code id cannot empty',
					},
					editblockcodedesc: {
						required: 'Please ente block code reason',
					},
					seleditstatus: {
						required: 'Please select status',
					},
					seleditauthenticate: {
						required: 'Please select authenticate',
					},
					editcreatedon: {
						required: 'Created is required',
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
		
		
			if ($("#editblockcodes-from").valid()) {
		
				$('#editdispute-from').attr('action', fnGetOpsServletPath());
				$('input[name="qs"]').val('opswal');
				$('input[name="rules"]').val('opseditblockcodes');     
				$('input[name="hdneditstatus"]').val($('#seleditstatus :selected').val());
				$('input[name="hdnauthenticate"]').val($('#seleditauthenticate :selected').val());
				console.log('rule is ' + $('input[name="rules"]').val())
				//set required attribute on input to true
				//$('input').attr('data-parsley-required', 'true');
		
				$("#editblockcodes-from").submit();
		
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






 
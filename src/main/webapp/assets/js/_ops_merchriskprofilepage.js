
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

		$('#btn-addrisk').click(function() {
			//Check for the data validation	
			$("#addmerchrisk-from").validate({
				rules: {
					sellriskdesc: {
						required: true
					},
					sellpaymentaction: {
						required: true
					},
					sellriskstatus: {
						required: true
					}
				},
				messages: {
					sellriskdesc: {
						required: 'Please select risk description',
					},
					sellpaymentaction: {
						required: 'Please select payment action',
					},
					sellriskstatus: {
						required: 'Please select status',
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
		
			if ($("#addmerchrisk-from").valid()) {
				$('#addmccgroup-form').attr('action', fnGetOpsServletPath());
				$('input[name="qs"]').val('opsmerch');
				$('input[name="rules"]').val('opsaddnewrisk');
				$('input[name="hdnriskdesc"]').val($('#sellriskdesc :selected').val());
				$('input[name="hdnpaymentaction"]').val($('#sellpaymentaction :selected').val());
				$('input[name="hdnstatus"]').val($('#sellriskstatus :selected').val());
		
				//console.log('rule is ' + $('input[name="rules"]').val())
				console.log('sellriskstatus is ' + $('#sellriskstatus :selected'))
				//set required attribute on input to true
				//$('input').attr('data-parsley-required', 'true');
		
				$("#addmerchrisk-from").submit();
		
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
		
	function fnEditMerchRiskPrf(riskid, riskdesc, riskstatus, paymentstatus,createdon) {
		$('#editriskModal').on('show.bs.modal', function() {
			$('#editriskid').val(riskid);
			$('#selleditriskdesc').val(riskdesc);
			$('#selleditpaymentaction').val(paymentstatus);
			$('#selleditriskstatus').val(riskstatus);
			$('#editriskcreatedon').val(createdon);
	
		});
	
		$('#editriskModal').modal('show');
	
	}
	
	$('#btn-editrisk').click(function() {
			//Check for the data validation	
			$("#editmerchrisk-from").validate({
				rules: {
					sellriskdesc: {
						required: true
					},
					sellpaymentaction: {
						required: true
					},
					sellriskstatus: {
						required: true
					}
				},
				messages: {
					sellriskdesc: {
						required: 'Please select risk description',
					},
					sellpaymentaction: {
						required: 'Please select payment action',
					},
					sellriskstatus: {
						required: 'Please select status',
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
		
			if ($("#editmerchrisk-from").valid()) {
				$('#editmerchrisk-from').attr('action', fnGetOpsServletPath());
				$('input[name="qs"]').val('opsmerch');
				$('input[name="rules"]').val('opseditmerchrisk');
				$('input[name="hdneditriskdesc"]').val($('#selleditriskdesc :selected').val());
				$('input[name="hdneditpaymentaction"]').val($('#selleditpaymentaction :selected').val());
				$('input[name="hdneditstatus"]').val($('#selleditriskstatus :selected').val());
		
				//console.log('rule is ' + $('input[name="rules"]').val())
				console.log('hdneditpaymentaction is ' + $('#hdneditpaymentaction :selected'))
				//set required attribute on input to true
				//$('input').attr('data-parsley-required', 'true');
		
				$("#editmerchrisk-from").submit();
		
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
		





 

$(document).ready(function () {
	$('#viewtransactionrulestable').dataTable( {
			    "order": []
			} );
});
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

		$('#btn-addloyaltyrules').click(function() {
			//Check for the data validation	
			$("#addloyaltyrules-from").validate({
					rules: {
					addpaymode: {
						required: true
					},
					addpointconvtn: {
						required: true,
					},
					selladdusertype: {
						required: true,
					},
					addruledesc: {
						required: true
					},
					addcryptoconvtn: {
						required: true
					},
					selladdstatus: {
						required: true
					}
										
				},
				messages: {
					addpaymode: {
						required: 'Please enter the Pay Mode',

					},
					addpointconvtn: {
						required: 'Please enter the Point conversion ratio',
					},
					selladdusertype: {
						required: 'Please select the User Type',
					},
					addruledesc: {
						required: 'Please enter the Rule Description',
					},
					addcryptoconvtn: {
						required: 'Please select the Crypto conversion ratio',
					},
					selladdstatus: {
						required: 'Please select the status',
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
		
			if ($("#addloyaltyrules-from").valid()) {
				$('#aaddloyaltyrules-from').attr('action', fnGetOpsServletPath());
				$('input[name="qs"]').val('opsprc');
				$('input[name="rules"]').val('opsaddtransactionrules');
				$('input[name="hdnaddloyaltystatus"]').val($('#selladdstatus :selected').val());
				$('input[name="hdnaddusertype"]').val($('#selladdusertype :selected').val());		
		
				//console.log('rule is ' + $('input[name="rules"]').val())
				console.log('hdnaddlimitstatus is ' + $('#selladdstatus :selected'))
				//set required attribute on input to true
				//$('input').attr('data-parsley-required', 'true');
		
				$("#addloyaltyrules-from").submit();
		
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

	function fnEditTransactionRules(paymode, ruledesc, usertype, status ) {
		$('#editLoyaltyRulesModal').on('show.bs.modal', function() {
			$('#editpaymode').val(paymode);
			$('#editruledesc').val(ruledesc);
			$('#selleditusertype').val(usertype);
			$('#selleditstatus').val(status);
	
		});
	
		$('#editLoyaltyRulesModal').modal('show');
	
	}
	
	$('#btn-editeditloyaltyrule').click(function() {
			//Check for the data validation	
			$("#editloyalty-form").validate({
				rules: {
					editpaymode: {
						required: true
					},
					editpointconvtn: {
						required: true,
					},
					selleditusertype: {
						required: true,
					},
					editruledesc: {
						required: true
					},
					editcryptoconvtn: {
						required: true
					},
					selleditstatus: {
						required: true
					}
										
				},
				messages: {
					editpaymode: {
						required: 'Please enter the Pay Mode',

					},
					editpointconvtn: {
						required: 'Please enter the Point conversion ratio',
					},
					selleditusertype: {
						required: 'Please select the User Type',
					},
					editruledesc: {
						required: 'Please enter the Rule Description',
					},
					editcryptoconvtn: {
						required: 'Please select the Crypto conversion ratio',
					},
					selleditstatus: {
						required: 'Please select the status',
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
		
			if ($("#editloyalty-form").valid()) {
				$('#editloyalty-form').attr('action', fnGetOpsServletPath());
				$('input[name="qs"]').val('opsprc');
				$('input[name="rules"]').val('opsedittranactionrules');
				$('input[name="hdnloyaltystatus"]').val($('#selleditstatus :selected').val());
				$('input[name="hdnusertype"]').val($('#selleditusertype :selected').val());
		
				//console.log('txnlimitid is ' + $('input[name="txnlimitid"]').val())
				//console.log('hdnlimitstatus is ' + $('#sellstatus :selected'))
				//set required attribute on input to true
				//$('input').attr('data-parsley-required', 'true');
		
				$("#editloyalty-form").submit();
		
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
		





 
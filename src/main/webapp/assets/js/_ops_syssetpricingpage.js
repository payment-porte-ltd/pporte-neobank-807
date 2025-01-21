
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

		$('#btn-addnewpricing').click(function() {
			//Check for the data validation	
			$("#addenewpricingdetails-form").validate({
				rules:{
					addplanvalue: {
						required: true,
						digits :true
					},
					addvarfee: {
						required: true,
						digits :true
					},
					addminamount: {
						required: true,
						digits :true
					},
					selpaymode: {
						required: true
					},
					sellisdefault: {
						required: true
					},
					addplandesc:{
						required: true
					},
					sellusertype: {
						required: true
					},
					addplandesc: {
						required: true
					},
					addfixedfee: {
						required: true,
						digits :true
					},
					sellpaytype: {
						required: true
					},
					sellslabappstatus: {
						required: true
					},
					sellpricingstatus: {
						required: true
					},
					sellslabapplicable:{
						required: true
						
					}
					
				},
				messages: {
					
					addplanvalue: {
						required: 'Please enter the Plan value',
					},
					addminamount: {
						required: 'Please enter the Minimum amount',
					},
					seladdpaymode: {
						required: 'Please enter the Pay Mode',
					},
					sellisdefault: {
						required: 'Please select isDefault value',
					},
					sellusertype: {
						required: 'Please select the User Type',
					},
					addplandesc: {
						required: 'Please enter the Plan description',
					},
					addfixedfee: {
						required: 'Please enter the Fixed fee',
					},
					sellpaytype: {
						required: 'Please select the Pay Type',
					},
					sellslabapplicable: {
						required: 'Please select the Slab applicable',
					},
					addminamount: {
						required: 'Please enter the Minimum amount',
					},
					sellpricingstatus:{
						required: 'Please enter the Minimum amount',
						
					},
					addvarfee:{
						required: 'Please enter the Variable fee',
						
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
		
			if ($("#addenewpricingdetails-form").valid()) {
				$('#aaddenewpricingdetails-form').attr('action', fnGetOpsServletPath());
				$('input[name="qs"]').val('opsmerch');
				$('input[name="rules"]').val('opsaddpricingplan');
				$('input[name="hdnpricingstatus"]').val($('#sellpricingstatus :selected').val());
				$('input[name="hdnslabapllicable"]').val($('#sellslabapplicable :selected').val());
				$('input[name="hdnpaytype"]').val($('#sellpaytype :selected').val());
				$('input[name="hdnusertype"]').val($('#sellusertype :selected').val());
				$('input[name="hdnisdefault"]').val($('#sellisdefault :selected').val());
				$('input[name="hdnselpaymode"]').val($('#seladdpaymode :selected').val());
		
				//console.log('rule is ' + $('input[name="rules"]').val())
				console.log('hdnusertype is ' + $('#sellusertype :selected'))
				//set required attribute on input to true
				//$('input').attr('data-parsley-required', 'true');
		
				$("#addenewpricingdetails-form").submit();
		
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
		
	function fnEditPricingDetails(planid, usertype, planvalue, plandesc,varfee, fixedfee, minamount, paytype, isdefault, status, paymode, slabapp) {
		$('#editpricingModal').on('show.bs.modal', function() {
			$('#editplanid').val(planid);
			$('#selleditusertype').val(usertype);
			$('#editplanvalue').val(planvalue);
			$('#editplandesc').val(plandesc);
			$('#editvarfee').val(varfee);
			$('#editfixedfee').val(fixedfee);
			$('#editminamount').val(minamount);
			$('#selleditpaytype').val(paytype);
			$('#selleditisdefault').val(isdefault);
			$('#selleditpricingstatus').val(status);
			$('#seleditpaymode').val(paymode);
			$('#selleditslabapplicable').val(slabapp);
	
		});
	
		$('#editpricingModal').modal('show');
	
	}
	
	$('#btn-editpricing').click(function() {
			//Check for the data validation	
			$("#editPricingdetails-from").validate({
				rules: {
					editplanid: {
						required: true
					},
					editplanvalue: {
						required: true,
					},
					editminamount: {
						required: true,
					},
					seleditpaymode: {
						required: true
					},
					editisdefault: {
						required: true
					},
					selleditusertype: {
						required: true
					},
					editplandesc: {
						required: true
					},
					editfixedfee: {
						required: true,
					},
					selleditpaytype: {
						required: true
					},
					selleditslabapplicable: {
						required: true
					},
					selleditpricingstatus: {
						required: true
					},
					editvarfee:{
						required: true
					},
					selleditisdefault :{
						required: true
					}
					
					
				},
				messages: {
					editplanid: {
						required: 'Please enter the Plan Id',

					},
					editplanvalue: {
						required: 'Please enter the Plan value',
					},
					editminamount: {
						required: 'Please enter the Minimum amount',
					},
					seleditpaymode: {
						required: 'Please enter the Pay Mode',
					},
					selleditusertype: {
						required: 'Please select the User Type',
					},
					editplandesc: {
						required: 'Please enter the Plan description',
					},
					editfixedfee: {
						required: 'Please enter the Fixed fee',
					},
					editvarfee :{
						required: 'Please select the Pay Type',
					},
					selleditpaytype: {
						required: 'Please select the Pay Type',
					},
					sellslabappstatus: {
						required: 'Please select the Minimum amount',
					},
					editminamount: {
						required: 'Please enter the Minimum amount',
					},
					selleditpricingstatus: {
						required: 'Please enter the Minimum amount',
					}, 
					selleditisdefault:{
						required: 'Please enter the is Default value',

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
		
			if ($("#editPricingdetails-from").valid()) {
				$('#editPricingdetails-from').attr('action', fnGetOpsServletPath());
				$('input[name="qs"]').val('opsmerch');
				$('input[name="rules"]').val('opseditpricing');
				$('input[name="hdnpricingstatus"]').val($('#selleditpricingstatus :selected').val());
				$('input[name="hdnslabapllicable"]').val($('#selleditslabapplicable :selected').val());
				$('input[name="hdnpaytype"]').val($('#selleditpaytype :selected').val());
				$('input[name="hdnusertype"]').val($('#selleditusertype :selected').val());
				$('input[name="hdneditisdefault"]').val($('#selleditisdefault :selected').val());
				$('input[name="hdnseleditpaymode"]').val($('#seleditpaymode :selected').val());
						//console.log('rule is ' + $('input[name="rules"]').val())
				//console.log('hdnpricingstatus is ' + $('#sellpricingstatus :selected'))
				//set required attribute on input to true
				//$('input').attr('data-parsley-required', 'true');
		
				$("#editPricingdetails-from").submit();
		
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
		
	function fnViewSlabRate(planid){
		     //  console.log('slab rate '+planid);
		    $('#slabrate-form').attr('action', fnGetOpsServletPath());
		    $('input[name="qs"]').val('opsprc');
		    $('input[name="rules"]').val('viewslabrateforspecificplan');
		    $('input[name="hdnplanid"]').val(planid);
		    $("#slabrate-form").submit();
	  }
		
		$("button[data-dismiss=modal]").click(function() {
			$(".modal").modal('hide');
		});





 
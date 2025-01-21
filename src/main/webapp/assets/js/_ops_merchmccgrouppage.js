
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

	function fnEditMCCGroup(mccid, mccname, mccfromrange, mcctorange, mccgeneric, createdon ){
		console.log('mccid is ', mccid)
			$('#editmccModal').on('show.bs.modal', function () {
			$('#editmccid').val(mccid);
			$('#editmccgroupname').val(mccname);
			$('#editmccfromrange').val(mccfromrange);
			$('#editmcctorange').val(mcctorange);
			$('#editmccgeneric').val(mccgeneric);		
			$("#editcreatedon").val(createdon);
	
			 });
		
			$('#editmccModal').modal('show');
		
		}
		
		$('#btn-addmccgrp').click(function() {
			//Check for the data validation	
			$("#addmccgroup-form").validate({
				rules: {
					mccgroupname: {
						required: true,
						minlength: 4
					},
					mccfromrange: {
						required: true,
						minlength: 3,
						digits: true
					},
					mcctorange: {
						required: true,
						minlength: 3,
		
					},
					mccgeneric: {
						required: true,
						minlength: 3,
						digits: true
		
					},
				},
				messages: {
					mccgroupname: {
						required: 'Please enter the Mcc Name',
						minlength: 'Mcc Name should be at least 4 characters'
					},
					mccfromrange: {
						required: 'Please enter the Mcc From Range',
						minlength: 'Mcc From Range should be at least 4 characters'
					},
					mcctorange: {
						required: 'Please enter the Mcc To Range',
						minlength: 'Mcc To Range should be at least 3 characters'
					},
					mccgeneric: {
						required: 'Please enter the Mcc Generic',
						minlength: 'Mcc Generic should be at least 3 characters'
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
		
		
			if ($("#addmccgroup-form").valid()) {
		
				$('#addmccgroup-form').attr('action', fnGetOpsServletPath());
				$('input[name="qs"]').val('opsmerch');
				$('input[name="rules"]').val('opsnewmccgroup');
		
				//console.log('rule is ' + $('input[name="rules"]').val())
				//set required attribute on input to true
				//$('input').attr('data-parsley-required', 'true');
		
				$("#addmccgroup-form").submit();
		
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
		
		$('#btn-editmcc').click(function() {
			//Check for the data validation	
			$("#editmccgroup-from").validate({
				rules: {
					editmccid: {
						required: true
					},
					editmccgroupname: {
						required: true,
						minlength: 4
					},
					editmccfromrange: {
						required: true,
						minlength: 3,
						digits: true
					},
					editmcctorange: {
						required: true,
						minlength: 3,
						digits: true

					},
					editmccgeneric: {
						required: true,
						minlength: 3,
						digits: true
		
					},
					editcreatedon: {
						required: true
		
					},
				},
				messages: {
					editmccgroupname: {
						required: 'Please enter the Mcc Name',
						minlength: 'Mcc Name should be at least 4 characters'
					},
					editmccfromrange: {
						required: 'Please enter the Mcc From Range',
						minlength: 'Mcc From Range should be at least 4 characters'
					},
					editmcctorange: {
						required: 'Please enter the Mcc To Range',
						minlength: 'Mcc To Range should be at least 3 characters'
					},
					editmccgeneric: {
						required: 'Please enter the Mcc Generic',
						minlength: 'Mcc Generic should be at least 3 characters'
					},
					editcreatedon: {
						required: 'Created is required',
					},
					editmccid: {
						required: 'Mcc Id is required',
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
		
		
			if ($("#editmccgroup-from").valid()) {
		
				$('#editmccgroup-from').attr('action', fnGetOpsServletPath());
				$('input[name="qs"]').val('opsmerch');
				$('input[name="rules"]').val('opseditmccgroup');
		
				console.log('rule is ' + $('input[name="rules"]').val())
				//set required attribute on input to true
				//$('input').attr('data-parsley-required', 'true');
		
				$("#editmccgroup-from").submit();
		
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






 
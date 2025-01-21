
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

	function fnEditDisputeReasons(reasonid, reasondesc, usertype, paymode, status, createdon ){
		console.log('reasonid is ', reasonid)
			$('#user-form-modal').on('show.bs.modal', function () {
			$('#editdisputereason').val(reasonid);
			$('#editreasondesc').val(reasondesc);
			$('#hdneditusertype').val(usertype);
			$('#hdneditpaymode').val(paymode);
			$('#hdneditstatus').val(status);		
			$("#editcreatedon").val(createdon);
	
			 });
		
			$('#user-form-modal').modal('show');
		
		}
		
		$('#btn-add-dreasons').click(function() {
			//Check for the data validation	
			$("#adddisputes-form").validate({
				
				    
				rules: {
					disputereasonid: {
						required: true,
						minlength: 1,
						digits:true,
					},
					disputereasondesc: {
						required: true,
						minlength: 4,
					},
					seldisputeusertype: {
						required: true,
						minlength: 1,
		
					},
					seldisputepaymode: {
						required: true,
						minlength: 1,
		
					},seldisputestatus: {
						required: true,
						minlength: 1,
		
					},
					disputecreatedon: {
						required: true,
						minlength: 4,
		
					},
				},
				messages: {
					disputereasonid: {
						required: 'Please enter the Reason ID',
						minlength: 'Reason ID minimum should be at least 1 characters'
					},
					disputereasondesc: {
						required: 'Please enter the Reason Description',
						minlength: 'Dispute Description should be at least 4 characters'
					},
					seldisputeusertype: {
						required: 'Please enter the User Type',
						minlength: 'User Type should be at least 3 characters'
					},
					seldisputepaymode: {
						required: 'Please enter the pay mode',
						minlength: 'Pay Mode should be at least 3 characters'
					},
					seldisputestatus: {
						required: 'Please enter the dispute status',
						minlength: 'Pay Mode should be at least 3 characters'
					},
					disputecreatedon: {
						required: 'Please enter the Date of Creation',
						minlength: 'Pay Mode should be at least 3 characters'
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
		
		
			if ($("#adddisputes-form").valid()) {
		
				$('#adddisputes-form').attr('action', fnGetOpsServletPath());
				$('input[name="qs"]').val('opsmerch');
				$('input[name="rules"]').val('opsnewdispute');
				$('input[name="hdnadddisputestatus"]').val($('#seldisputestatus :selected').val());
				$('input[name="hdnaddpaymode"]').val($('#seldisputepaymode :selected').val());
				$('input[name="hdnaddusertype"]').val($('#seldisputeusertype :selected').val());
				//console.log('rule is ' + $('input[name="rules"]').val())
				//set required attribute on input to true
				//$('input').attr('data-parsley-required', 'true');
		
				$("#adddisputes-form").submit();
		
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

																			    
		$('#btn-editdisputereasons').click(function() {
			//Check for the data validation	
			$("#editdispute-from").validate({
				rules: {
					editdisputereason: {
						required: true
					},
					editreasondesc: {
						required: true,
					},
					hdneditusertype: { 
						required: true,
					},
					hdneditpaymode: {
						required: true,
					},
					hdneditstatus: {
						required: true,
					},
					editcreatedon: {
						required: true
					},
				},
				messages: {
					editreasondesc: {
						required: 'Please enter the dispute reason',
					},
					hdneditusertype: {
						required: 'Please select the user type',
					},
					hdneditpaymode: {
						required: 'Please select the pay mode',
					},
					hdneditstatus: {
						required: 'Please enter select the dispute status',
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
		
		
			if ($("#editdispute-from").valid()) {
		
				$('#editdispute-from').attr('action', fnGetOpsServletPath());
				$('input[name="qs"]').val('opsmerch');
				$('input[name="rules"]').val('opseditdispute');     
				$('input[name="hdnadddisputestatus"]').val($('#hdneditusertype :selected').val());
				$('input[name="hdneditpaymode"]').val($('#hdneditpaymode :selected').val());
				$('input[name="hdneditstatus"]').val($('#hdneditstatus :selected').val());
				console.log('rule is ' + $('input[name="rules"]').val())
				//set required attribute on input to true
				//$('input').attr('data-parsley-required', 'true');
		
				$("#editdispute-from").submit();
		
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






 
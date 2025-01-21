
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
$("button[data-dismiss=modal]").click(function(){
	$(".modal").modal('hide');
});




		$('#btn-addtxnlimit').click(function() {
			//Check for the data validation	
			//addlimit_type selladdusertype addlimitdesc selladdstatus addlimitamount
			$("#addtxnlimitdetails-from").validate({
					rules: {
					addlimit_type: {
						required: true,
						maxlength:4
					},
					addlimitdesc: {
						required: true,
					},
					addlimitamount: {
						required: true
					},
					selladdusertype: {
						required: true
					},
					selladdstatus: {
						required: true
					}
				
				},
				messages: {
					
					addlimit_type: {
						required: 'Please enter  Limit Type value',
						maxlength: 'Maximum of 4 Characters required'
					},
					addlimitdesc: {
						required: 'Please enter Limit Description',
					},
					addlimitamount: {
						required: 'Please enter the Limit Amount',
					},
					selladdusertype: {
						required: 'Please select the User Type',
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
		
			if ($("#addtxnlimitdetails-from").valid()) {
				$('#aaddtxnlimitdetails-from').attr('action', fnGetOpsServletPath());
				$('input[name="qs"]').val('opsmerch');
				$('input[name="rules"]').val('opsaddtxnlimitdetails');
				$('input[name="hdnaddlimitstatus"]').val($('#selladdstatus :selected').val());
				$('input[name="hdnaddusertype"]').val($('#selladdusertype :selected').val());		
		
				//console.log('rule is ' + $('input[name="rules"]').val())
				console.log('hdnaddlimitstatus is ' + $('#selladdstatus :selected'))
				//set required attribute on input to true
				//$('input').attr('data-parsley-required', 'true');
		
				$("#addtxnlimitdetails-from").submit();
		
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

	function fnEditTransactionLimit(txnlimitid, limittype, limitdesc,limitamount , usertype, status, createdon ) {
		$('#edittxnlimitModal').on('show.bs.modal', function() {
			$('#txnlimitid').val(txnlimitid);
			$('#limit_type').val(limittype);
			$('#limitdesc').val(limitdesc);
			$('#limitamount').val(limitamount);
			$('#sellusertype').val(usertype);
			$('#sellstatus').val(status);
			$('#txncreatedon').val(createdon);
	
		});
	
		$('#edittxnlimitModal').modal('show');
	
	}
	
	$('#btn-edittxnlimits').click(function() {
			//Check for the data validation	
			$("#edittxnlimit-form").validate({
				
				rules: {
					txnlimitid: {
						required: true,
						number :true
					},
					limit_type: {
						required: true,
						maxlength:4
					},
					limitdesc: {
						required: true,
					},
					limitamount: {
						required: true
					},
					sellusertype: {
						required: true
					},
					sellstatus: {
						required: true
					},
					txncreatedon: {
						required: true,
					}
					
					
					
				},
				messages: {
					txnlimitid: {
						required: 'Transaction Limit ID required',

					},
					limit_type: {
						required: 'Please enter Limit Type',
						maxlength: 'Maximum of 4 Characters required'
					},
					limitdesc: {
						required: 'Please enter Limit Description',
					},
					limitamount: {
						required: 'Please enter Limit Amount',
					},
					
					sellusertype: {
						required: 'Please select the User Type',
					},
					sellstatus: {
						required: 'Please select the status',
					},
					txncreatedon :{
						required: 'Date created is required',
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
		
			if ($("#edittxnlimit-form").valid()) {
				$('#edittxnlimit-form').attr('action', fnGetOpsServletPath());
				$('input[name="qs"]').val('opsmerch');
				$('input[name="rules"]').val('opsedittxnlimits');
				$('input[name="hdnlimitstatus"]').val($('#sellstatus :selected').val());
				$('input[name="hdnusertype"]').val($('#sellusertype :selected').val());
		
				//console.log('txnlimitid is ' + $('input[name="txnlimitid"]').val())
				//console.log('hdnlimitstatus is ' + $('#sellstatus :selected'))
				//set required attribute on input to true
				//$('input').attr('data-parsley-required', 'true');
		
				$("#edittxnlimit-form").submit();
		
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
		





 
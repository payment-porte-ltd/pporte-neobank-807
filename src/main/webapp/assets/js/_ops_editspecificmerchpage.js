
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

		function fnEditMerchantUser(merchuserid, usename, contact, email, hierachy, status, designation,nationalid) {
			$('#editMerchUserModal').on('show.bs.modal', function() {
				$('#merchuid').val(merchuserid);
				$('#merchusername').val(usename);
				$('#merchusercontact').val(contact);
				$('#merchuseremail').val(email);
				$('#merchuserherarchy').val(hierachy);
				//$('input[name="sellmerchuserherarchy"]').val($('#sellmerchuserherarchy option:selected').val());	
				//$('input[name="sellmerchuserstatus"]').val($('#sellmerchuserstatus option:selected').val());
				$("#sellmerchuserstatus").val(status);
				$("#sellmerchuserherarchy").val(hierachy);
				$('#merchuserdesig').val(designation);
				$('#nationalid').val(nationalid);
				console.log('selected status is ' + $('#sellmerchuserstatus option:selected').val())
		
			});
		
			$('#editMerchUserModal').modal('show');
		
		}
		
		
		$('#btn-editmerchuser').click(function() {
			//Check for the data validation	
			$("#editmerchuser-form").validate({
				rules: {
					merchuid: {
						required: true,
						minlength: 4
					},
					merchusercontact: {
						required: true,
						minlength: 9,
						digits: true
					},
					sellmerchuserherarchy: {
						required: true
					},
					merchuserdesig: {
						required: true,
						minlength: 3
	
					},
					merchusername:{
						required: true,
					},
					merchuseremail:{
						required: true,
						email: true
				
					},
					sellmerchuserstatus:{
						required: true
					},
					nationalid:{
						required: true,
						digits: true

					}
										
				},
				messages: {
					merchuid: {
						required: 'Please enter the User Id',
						minlength: 'User Id should be at least 4 characters'
					},
					merchusercontact: {
						required: 'Please enter the Merchant user contact',
						minlength: 'Merchant user contact should be at least 10 characters'
					},
					sellmerchuserherarchy: {
						required: 'Please enter the Access level',
					},
					merchuserdesig: {
						required: 'Please enter the Merchant user designation',
						minlength: 'Merchant user designation should be at least 3 characters'
					},
					merchusername: {
						required: 'Please enter the Merchant username',
						minlength: 'Merchant username should be at least 3 characters'
					},
					merchuseremail: {
						required: 'Please enter the User email',
						minlength: 'Merchant user email should be at least 5 characters'
					},
					sellmerchuserstatus: {
						required: 'Please enter the User Status',
					},
					nationalid: {
						required: 'Please enter the National Id',
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
		
		
			if ($("#editmerchuser-form").valid()) {
				$('#editmerchuser-form').attr('action', fnGetOpsServletPath());
				$('input[name="qs"]').val('opsmerch');
				$('input[name="rules"]').val('opseditmerchuser');
				$('input[name="hdnstatus"]').val($('#sellmerchuserstatus :selected').val());
				$('input[name="hdnuserrole"]').val($('#sellmerchuserherarchy :selected').val());

		
				//console.log('rule is ' + $('input[name="rules"]').val())
				//set required attribute on input to true
				//$('input').attr('data-parsley-required', 'true');
		
				//$("#editmerchuser-form").submit();
				
			  var formData = new FormData($('#editmerchuser-form')[0]);
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
									  $('#editmerchuser-form').attr('action', fnGetOpsServletPath());
									  $('#editmerchuser-form input[name="qs"]').val('opsmerch');
									  $('#editmerchuser-form input[name="rules"]').val('View Merchants');
									  $("#editmerchuser-form").submit();
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
		
		$('#btn-verifymerch').click(function() {
			//Check for the data validation	
			$("#editmerchant-form").validate({
				rules: {
					merchantcode: {
						required: true,
						minlength: 8
					},
					companyname: {
						required: true,
						minlength: 4,
					},
					businessphone: {
						required: true,
						minlength: 9,
						digits: true
					},
					selsmerchtype: {
						required: true
		
					},
					selstatus: {
						required: true,
					},
					billref: {
						required: true,
						minlength: 5,
					},
					bussinessdescription: {
						required: true
					},
					physicaladdress: {
						required: true
		
					}
		
				},
				messages: {
					merchantcode: {
						required: 'Merchant Code is Required',
						minlength: 'Merchant Code should be at least 8 characters'
					},
					companyname: {
						required: 'Please enter the Company Name',
						minlength: 'Company Name should be at least 4 characters'
					},
					businessphone: {
						required: 'Please enter the Phone Number',
						minlength: 'Phone Number should be at least 9 characters'

					},
					selsmerchtype: {
						required: 'Please select the Merchant type',
					},
					selstatus: {
						required: 'Please select the status',
					},
					selmcccat:{
					required: 'Please select the Merchant category',

					},
					billref: {
						required: 'Please enter the Bill Ref',
						minlength: 'Bill Ref should be at least 5 characters'
					},
					bussinessdescription: {
						required: 'Please select the Business description',
					},
					physicaladdress: {
						required: 'Please select the Physical address',
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
		
		
			if ($("#editmerchant-form").valid()) {
				$('#editmerchant-form').attr('action', fnGetOpsServletPath());
				$('input[name="qs"]').val('opsmerch');
				$('input[name="rules"]').val('opseditmerchdetails');
				$('input[name="hdnmerchstatus"]').val($('#selstatus :selected').val());
				$('input[name="hdnmerchtype"]').val($('#selsmerchtype :selected').val());
				$('input[name="hdnmccgroup"]').val($('#selmcccat :selected').val());
				$('input[name="hdnriskprf"]').val($('#selmerchrisk :selected').val());

		
				//console.log('rule is ' + $('input[name="rules"]').val())
				//set required attribute on input to true
				//$('input').attr('data-parsley-required', 'true');
		
				//$("#editmerchuser-form").submit();
				
			  var formData = new FormData($('#editmerchant-form')[0]);
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
									  $('#editmerchant-form').attr('action', fnGetOpsServletPath());
									  $('input[name="qs"]').val('opsmerch');
									  $('input[name="rules"]').val('View Merchants');
									  $("#editmerchant-form").submit();	
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
		
		  function fngetFile(filepath){
			console.log('inside fngetFile path is '+ filepath)
			$('#getfile-form').attr('action', fnGetOpsServletPath());
	        $('input[name="qs"]').val('opscust');
	        $('input[name="rules"]').val('assetdownload');
			$('input[name="hdnassetpath"]').val(filepath);			
			$("#getfile-form").submit(); 
		}
		
		
		



 
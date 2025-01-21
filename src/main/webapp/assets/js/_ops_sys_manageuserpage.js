
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


		function fnEditOpsUser(uid, uname, email, contact, status, createdon, expiry, passpwdtries,utype) {
			console.log('user Type is ',utype)
			$('#editopsuserModal').on('show.bs.modal', function() {
				$('#editopsuid').val(uid);
				$('#seledituseraccesstype').val(utype);
				$('#editusername').val(uname);
				$('#edituseremail').val(email);
				$('#editusercontact').val(contact);
				$("#seledituserstatus").val(status);
				$("#editcreatedon").val(createdon);
				$("#editdatofexpiry").val(expiry);
				//$("#editcreatedon").val(passpwdtries);
		
			});
		
			$('#editopsuserModal').modal('show');
		
		}

		$('#btn-editopsuser').click(function() {
			//Check for the data validation	
			$("#editopsuser-form").validate({
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
					seledituseraccesstype:{
						required: true,
					},
					edituseremail:{
						required: true,
						email: true
					},
					seledituserstatus:{
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
					seledituseraccesstype: {
						required: 'Please select the User Access'
					},
					edituseremail: {
						required: 'Please enter the User email'
					},
					seledituserstatus: {
						required: 'Please select the User Status'
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
		
		
			if ($("#editopsuser-form").valid()) {
				$('#editopsuser-form').attr('action', 'ms');
				$('input[name="qs"]').val('prf');
				$('input[name="rules"]').val('opseditopsuser');				
				$('input[name="hdneditusertype"]').val($('#seledituseraccesstype :selected').val());		
				$('input[name="hdneditstatus"]').val($('#seledituserstatus :selected').val());		


				//console.log('rule is ' + $('input[name="rules"]').val())
				//set required attribute on input to true
				//$('input').attr('data-parsley-required', 'true');
		
				//$("#editopsuser-form").submit();
				
			  var formData = new FormData($('#editopsuser-form')[0]);
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
									  $('#editopsuser-form').attr('action', fnGetOpsServletPath());
									  $('#editopsuser-form input[name="qs"]').val('prf');
									  $('#editopsuser-form input[name="rules"]').val('Manage Ops Users');
									  $("#editopsuser-form").submit();	
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
		
			$('#btn-addopsuser').click(function() {
			//Check for the data validation	
			$("#addopsuser-form").validate({
				rules: {
					addopsuid: {
						required: true
					},
					addusername: {
						required: true
					},
					addusercontact: {
						required: true,
						digits : true,
						minlength: 9
					},
					addcreatedon: {
						required: true
	
					},
					seladduseraccesstype:{
						required: true,
					},
					adduseremail:{
						required: true,
						email: true
					},
					seladduserstatus:{
						required: true
					},
					adddatofexpiry:{
						required: true
					},
					addpassword:{
						required: true, 
						minlength:8
					},
					confirmpassword:{
						required: true,
						equalTo: "#addpassword"
					},									
				},
				messages: {
					addopsuid: {
						required: 'User Id is Required'
					},
					addusername: {
						required: 'Please enter the User Name'
					},
					addusercontact: {
						required: 'Please enter the User Contact'
					},
					addcreatedon: {
						required: 'Createdon cannot be empty'
					},
					seladduseraccesstype: {
						required: 'Please select the User Access'
					},
					adduseremail: {
						required: 'Please enter the User email'
					},
					seladduserstatus: {
						required: 'Please select the User Status'
					},
					adddatofexpiry: {
						required: 'Date of Expiry cannot be empty'
					},
					addpassword:{
						required: 'Please enter the password', 
						minlength:'Password must consist of at least 8 characters'
					},
					confirmpassword:{
						required: 'Please confirm the password',
						equalTo: 'Password does not match',
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
		
		
			if ($("#addopsuser-form").valid()) {
				$('#addopsuser-form').attr('action', 'ms');
				$('input[name="qs"]').val('prf');
				$('input[name="rules"]').val('opsaddopsuser');
				$('input[name="hdnaddstatus"]').val($('#seladduserstatus :selected').val());
				$('input[name="hdnaddusertype"]').val($('#seladduseraccesstype :selected').val());

				//console.log('rule is ' + $('input[name="rules"]').val())
				//set required attribute on input to true
				//$('input').attr('data-parsley-required', 'true');
		
				//$("#addopsuser-form").submit();
				
			  var formData = new FormData($('#addopsuser-form')[0]);
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
								      $('#editopsuser-form').attr('action', fnGetOpsServletPath());
									  $('#editopsuser-form input[name="qs"]').val('prf');
									  $('#editopsuser-form input[name="rules"]').val('Manage Ops Users');
									  $("#editopsuser-form").submit();	
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
	


 
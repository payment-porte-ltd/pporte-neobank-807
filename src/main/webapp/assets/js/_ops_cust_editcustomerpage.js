
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

	function fnApproveCustomer(){
		$("#editcust-form").validate({
				rules: {
					relno: {
						required: true,
						digits : true
					},
					custphoneno: {
						required: true,
					},
					selgender: {
						required: true
		
					},
					selcuststatus: {
						required: true,
					},
					createdon: {
						required: true
					},
					custname: {
						required: true,

					},
					custemail: {
						required: true,
						 email :true
					},
					physicaladdress: {
						required: true,		
					},
					custdob: {
						required: true,
					},
					expiry:{
						required:true
					}
		
				},
				messages: {
					relno: {
						required: 'Customer Code is Required',
					},
					custphoneno: {
						required: 'Please enter the Phone Number',
					},
					selgender: {
						required: 'Please select the Gender'
					},
					selcuststatus: {
						required: 'Please select the status'
					},
				
					custname: {
						required: 'Please enter the Customer Name',
					},
					custemail: {
						required: 'Please enter the email Address',
					},
					physicaladdress: {
						required: 'Please enter the Physical address',
					},
					custdob:{
						required: 'Please enter the Date of Birth',
					},
					expiry:{
						required: 'Please enter the Date od expiry',
					},
					createdon:{
						required: 'Date created is required'
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
		
		
			if ($("#editcust-form").valid()) {
				$('#editcust-form').attr('action', fnGetOpsServletPath());
				$('input[name="qs"]').val('opscust');
				$('input[name="rules"]').val('opsapprovecustomer');
				
			  var formData = new FormData($('#editcust-form')[0]);
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
									//location.reload();
								      $('#editcust-form').attr('action', fnGetOpsServletPath());
									  $('#editcust-form input[name="qs"]').val('opscust');
									  $('#editcust-form input[name="rules"]').val('View Customers');
									  $("#editcust-form").submit();
							  });						
						  
				          }else{
							var message=data.message;
							console.log("message is "+message)
							  Swal.fire({
								  icon: 'error',
								  title: 'Oops',
								  text: data.message,
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
		
	}
		$('#btn-editcustomer').click(function() {
			//Check for the data validation	
			$("#editcust-form").validate({
				rules: {
					relno: {
						required: true,
						digits : true
					},
					custphoneno: {
						required: true,
					},
					selgender: {
						required: true
		
					},
					selcuststatus: {
						required: true,
					},
					createdon: {
						required: true
					},
					custname: {
						required: true,

					},
					custemail: {
						required: true,
						 email :true
					},
					physicaladdress: {
						required: true,		
					},
					custdob: {
						required: true,
					},
					expiry:{
						required:true
					}
		
				},
				messages: {
					relno: {
						required: 'Customer Code is Required',
					},
					custphoneno: {
						required: 'Please enter the Phone Number',
					},
					selgender: {
						required: 'Please select the Gender'
					},
					selcuststatus: {
						required: 'Please select the status'
					},
				
					custname: {
						required: 'Please enter the Customer Name',
					},
					custemail: {
						required: 'Please enter the email Address',
					},
					physicaladdress: {
						required: 'Please enter the Physical address',
					},
					custdob:{
						required: 'Please enter the Date of Birth',
					},
					expiry:{
						required: 'Please enter the Date od expiry',
					},
					createdon:{
						required: 'Date created is required'
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
		
		
			if ($("#editcust-form").valid()) {
				$('#editcust-form').attr('action', fnGetOpsServletPath());
				$('input[name="qs"]').val('opscust');
				$('input[name="rules"]').val('opseditcustdetails');
				$('input[name="hdncuststatus"]').val($('#selcuststatus :selected').val());
				$('input[name="hdncustgender"]').val($('#selgender :selected').val());

		
				//console.log('rule is ' + $('input[name="rules"]').val())
				//set required attribute on input to true
				//$('input').attr('data-parsley-required', 'true');
		
				//$("#editmerchuser-form").submit();
				
			  var formData = new FormData($('#editcust-form')[0]);
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
									//location.reload();
								      $('#editcust-form').attr('action', fnGetOpsServletPath());
									  $('#editcust-form input[name="qs"]').val('opscust');
									  $('#editcust-form input[name="rules"]').val('View Customers');
									  $("#editcust-form").submit();
							  });						
						  
				          }else{
							var message=data.message;
							console.log("message is "+message)
							  Swal.fire({
								  icon: 'error',
								  title: 'Oops',
								  text: data.message,
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
		
		  function fngetFile(filepath, relno){
			console.log('inside fngetFile path is '+ filepath)
			$('#getfile-form').attr('action', fnGetOpsServletPath());
	        $('input[name="qs"]').val('opscust');
	        $('input[name="rules"]').val('assetdownload');
			$('input[name="hdnassetpath"]').val(filepath);			
			$('input[name="relno"]').val(relno);			
			$("#getfile-form").submit(); 
		}
		



 
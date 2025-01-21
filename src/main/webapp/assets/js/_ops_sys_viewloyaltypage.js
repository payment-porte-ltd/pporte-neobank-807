
$(document).ready(function () {
	$('#viewloyaltyrulestable').dataTable( {
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
					seladdpaymode: {
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
					seladdpaymode: {
						required: 'Please select the Pay Mode',

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
				$('input[name="qs"]').val('opslyt');
				$('input[name="rules"]').val('opsaddloyaltyrules');
				$('input[name="hdnaddloyaltystatus"]').val($('#selladdstatus :selected').val());
				$('input[name="hdnaddusertype"]').val($('#selladdusertype :selected').val());
				$('input[name="hdnaddpaymode"]').val($('#seladdpaymode :selected').val());
						
				//console.log('rule is ' + $('input[name="rules"]').val())
				console.log('hdnaddlimitstatus is ' + $('#selladdstatus :selected'))
				//set required attribute on input to true
				//$('input').attr('data-parsley-required', 'true');
		
				//$("#addloyaltyrules-from").submit();
				var formData = new FormData($('#addloyaltyrules-from')[0]);
		        for (var pair of formData.entries()) {
		          console.log(pair[0] + " - " + pair[1]);
		        }	
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
		                
		                var data = JSON.parse(result);
		                console.log('data ',data);
		                if(data.error=='false'){
		                    Swal.fire({
		                        icon: 'success',
		                        text: data.message,
		                        showConfirmButton: true,
		                        confirmButtonText: "Ok",
		                    }).then(function() {
		                        $('#get-page-form').attr('action', fnGetOpsServletPath());
		                        $('input[name="qs"]').val('opslyt');
		                        $('input[name="rules"]').val('Create Loyalty Rule');
		                        $("#addloyaltyrules-from").submit();
		                    });	
		                }else{
		                    Swal.fire({
		                        text: data.message, 
		                        icon: "error",
		                        showConfirmButton: true,
		                        confirmButtonText: "Ok",
		                        }).then(function() {
		                            
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
				
	function fnEditLoyaltyRules(paymode, ruledesc, pointconversion, cryptoconversion, usertype, status ) {
		$('#editLoyaltyRulesModal').on('show.bs.modal', function() {
			$('#editpaymode').val(paymode);
			$('#editruledesc').val(ruledesc);
			$('#editpointconvtn').val(pointconversion);
			$('#editcryptoconvtn').val(cryptoconversion);
			$('#selleditusertype').val(usertype);
			$('#selleditstatus').val(status);
	
		});
	
		$('#editLoyaltyRulesModal').modal('show');
	
	}
	$("button[data-dismiss=modal]").click(function(){
	$(".modal").modal('hide');
});
	
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
				$('input[name="qs"]').val('opslyt');
				$('input[name="rules"]').val('opseditloyaltyrules');
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
		function UpdateDescriptionParam(){
		    var selectedValues = $("#seladdpaymode option:selected").val();
			var arrvalues =  selectedValues.split(",");
		   //var paymode = arrvalues[0];
			var paydescription = arrvalues[0];
			$("#addruledesc").val(paydescription);
			
		}





 
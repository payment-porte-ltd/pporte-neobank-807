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
				
		function fnUpdateParams(ac) {
			$('input[name="hdnassetcoincode"]').val(ac);
			//alert('here 4 ' + $('input[name="hdnassetcoincode"]').val());
		}
					
		$('#btn-coinspayanyone').click(function() {
			//Check for the data validation	
			$("#payanyone-form").validate({
				rules: {
					selcryptocoinsend: {
						required: true,
					},
					receiverwallet: {
						required: true,
					},
					sendamount: {
						required: true,
					}					
				},
				messages: {
					selcryptocoinsend: {
						 required: $('#pay-anyone-data-validation-error-from').text()

					},
					receiverwallet: {
						 required: $('#pay-anyone-data-validation-error-sendto').text()
					},
					sendamount: {
						 required: $('#pay-anyone-data-validation-error-amounttosend').text()
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
		
			if ($("#payanyone-form").valid()) {
				$('#payanyone-form input[name="qs"]').val('cryp');
				$('#payanyone-form input[name="rules"]').val('transfer_external_coin');
				//$('#payanyone-form input[name="hdnfrowalletid"]').val($('#selcryptocoinsend :selected').val());
			
				//alert(' hdnassetcoincode send is '+ $('input[name="hdnassetcoincode"]').val())
				//console.log('rule is ' + $('input[name="rules"]').val())
				//set required attribute on input to true
				//$('input').attr('data-parsley-required', 'true');
		
				//$("#editmerchuser-form").submit();
				var formData = new FormData($('#payanyone-form')[0]);
				$.ajaxSetup({
					beforeSend: function(xhr) {
						xhr.setRequestHeader('x-api-key', getAPIKey());
					}
				});
				$.ajax({
					url: 'ms',
					data: formData,
					processData: false,
					contentType: false,
					type: 'POST',
					success: function(result) {
						//alert('result is '+result);
						var data = JSON.parse(result);
						if (data.error == 'false') {
							//alert('lgtoken is '+data.token)
							Swal.fire({
								icon: 'success',
								title: 'Good',
								text: data.message,
								showConfirmButton: true,
								confirmButtonText: "Ok",
							}).then(function() {
								//location.reload();
								$('#payanyone-form').attr('action', 'ws');
								$('#payanyone-form input[name="qs"]').val('cryp');
								$('#payanyone-form input[name="rules"]').val('Pay Anyone');
								$("#payanyone-form").submit();
							});
		
						} else {
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
		
		function fnUpdatereceiveparams() {
			var assetcode = $("#selcryptocoinreceive option:selected").val();
			if (assetcode == '') {
				swal.fire('Select Destination Coin');
				return false;
			} else {
				cryptocoversion();
				$('input[name="hdnreceivercointype"]').val(assetcode);
				$("#spanreceivedcode").text(assetcode);
			}		
		}
		
		function fnUpdatesenderparams() {
			var assetcode = $("#selcryptocoinsend option:selected").val();
		
			if (assetcode == '') {
				swal.fire('Select Destination Coin');
				return false;
			} else {
		 cryptocoversion();
				var arrvalues = assetcode.split(",")
				$('input[name="hdnfrowalletid"]').val(arrvalues[0]);
				$('input[name="hdnsendercointype"]').val(arrvalues[1]);
				$("#spansendcode").text(arrvalues[1]);
						
			}
		}
		
		function cryptocoversion(){
				var conversion = 1;
			var assetcode = $("#selcryptocoinsend option:selected").val();
			var assetcodereceived = $("#selcryptocoinreceive option:selected").val();
			var arrvalues = assetcode.split(",")
			
			var assetcodevalue = arrvalues[1];
			if(assetcodevalue === assetcodereceived ){
			 conversion = 1;			
			}else if(assetcodevalue ==="BTC"){
			 conversion = 0.000023;
			}else if(assetcodevalue ==="ETH"){
			  conversion = 0.00033;
			}else if(assetcodevalue ==="LTC"){
			  conversion = 0.0066;
			}
			var amount = $("#sendamount").val();
			var equivalence = amount*conversion;
		
		     $("#receivedamount").val(equivalence);
		        ///$("#receivedamount").val($("#sendamount").val());
				//$("#receivedamount").text($("#sendamount").val());

			
		}
		
		function cryptocoversionBackwards(){
			var conversion = 1;
			var assetcode = $("#selcryptocoinsend option:selected").val();
			var arrvalues = assetcode.split(",")
			
			var assetcodevalue = arrvalues[1];
			if(assetcodevalue ==="BTC"){
			 conversion = 43392.90;
			}else if(assetcodevalue ==="ETH"){
			  conversion = 3013.25;
			}else if(assetcodevalue ==="LTC"){
			  conversion = 153.49;
			}
			var amount = $("#receivedamount").val();
			var equivalence = amount*conversion;
		
		     $("#sendamount").val(equivalence);
			
		}
		
		$(document).ready(function(){
		    $("#sendamount").keyup(function(){
		   cryptocoversion();
		    });

		$("#receivedamount").keyup(function(){
		   cryptocoversionBackwards();
		});
		    
		});



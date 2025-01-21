
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
   		 var modeOfPayment = $("#payment_method option:selected").val();
			$("#buyextcoin-form").validate({
				rules: {
					selbuycryptoasset: {
						required: true,
					},
					payment_method: {
						required: true,
					},
					sendamount: {
						required: true,
					}					
				},
				messages: {
					selbuycryptoasset: {
						 required: $('#pay-anyone-data-validation-error-from').text()

					},
					payment_method: {
						 required: $('#pay-anyone-data-validation-error-paymethod').text()
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
		
			if ($("#buyextcoin-form").valid()) {
				if (modeOfPayment === "T") {
					validateCardDetails();
				} 
				$('#buyextcoin-form input[name="qs"]').val('cryp');
				$('#buyextcoin-form input[name="rules"]').val('buy_external_coin');
			    $('#buyextcoin-form input[name="hdnfrowalletid"]').val($('#selcryptocoinsend :selected').val());
			    $('#buyextcoin-form input[name="hdntokenid"]').val($('input[name="tokenid"]:checked').val());
			    $('#buyextcoin-form input[name="hdnpaymentmode"]').val($("#payment_method option:selected").val());
		
				//$("#editmerchuser-form").submit();
				var formData = new FormData($('#buyextcoin-form')[0]);
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
								$('#buyextcoin-form').attr('action', 'ws');
								$('#buyextcoin-form input[name="qs"]').val('cryp');
								$('#buyextcoin-form input[name="rules"]').val('Buy Coins');
								$("#buyextcoin-form").submit();
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
		
		function cryptocoversion(){
		    var conversion = 1;
			var assetcode = $("#selbuycryptoasset option:selected").val();
			var arrvalues = assetcode.split(",")
			
			var assetcodevalue = arrvalues[1];
			if(assetcodevalue ==="BTC"){
			 conversion = 0.000023;
			}else if(assetcodevalue ==="ETH"){
			  conversion = 0.00033;
			}else if(assetcodevalue ==="LTC"){
			  conversion = 0.0066;
			}
			var amount = $("#sendamount").val();
			var equivalence = amount*conversion;
		
		     $("#receivedamount").val(equivalence);
			
		}
		
		function cryptocoversionBackwards(){
			var conversion = 1;
			var assetcode = $("#selbuycryptoasset option:selected").val();
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
		
		$(document).ready(function() {
			$("#sendamount").keyup(function() {
				cryptocoversion();
			});
		
			$("#receivedamount").keyup(function() {
				cryptocoversionBackwards();
			});
		
		});
		
		
		function fnUpdatereceiveparams() {
			var assetcode = $("#selcryptocoinreceive option:selected").val();
			if (assetcode == '') {
				swal.fire('Select Destination Coin');
				return false;
			} else {
				$('input[name="hdnreceivercointype"]').val(assetcode);
				$("#spanreceivedcode").text(assetcode);
			}		
		}
		
		function fnUpdatesenderparams() {
			var assetcode = $("#selbuycryptoasset option:selected").val();
		
			if (assetcode == '') {
				swal.fire('Select Destination Coin');
				return false;
			} else {
				cryptocoversion();
				var arrvalues = assetcode.split(",")
				$('input[name="hdnbuyforwalletid"]').val(arrvalues[0]);
				$('input[name="hdnbuyassetcode"]').val(arrvalues[1]);
				$("#spanreceivedcode").text(arrvalues[1]);
			}
		}
		
		$(document).ready(function() {
			$("#sendamount").keyup(function() {
				cryptocoversion();
			});
		
			$("#receivedamount").keyup(function() {
				cryptocoversionBackwards();
			});
		
		});
		
	//New
		function fnGetPaymentMethod(relno) {
			var paymentMethod = $("#payment_method option:selected").val();
			if (paymentMethod == 'T') {
				fnGetCardDetails(relno);
			} else {
				$('#cardsdiv').hide();
			}
		}
		
		function fnGetCardDetails(relno) {
			$('#post-form input[name="qs"]').val('cryp');
			$('#post-form input[name="rules"]').val('gettokenizedcards');
			$('#post-form input[name="relno"]').val(relno);
			var form = $('#post-form')[0];
			var formData = new FormData(form);
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
					if (result) {
						var data = JSON.parse(result);
						var htmlData = '';
						$('#cardavailable').html('');
						console.log("Error is" + data.error);
						if (data.error === "false") {
							var cardDetails = data.data;
							console.table(cardDetails)
							var count = cardDetails.length;
							console.log("count is " + count);
							if (count > 0) {
								for (i = 0; i < count; i++) {
									htmlData += `
	                                    <label class="custom-control custom-radio">
	                                            <input type="radio" class="custom-control-input" name="tokenid" id="tokenid" value="`+ cardDetails[i].tokenId + `" >
	                                            <span class="custom-control-label">`+ cardDetails[i].maskedCardNumber + `</span>
	                                      </label>
	                                `;
								}
							} else {
								htmlData = `<p style="color:grey">No Cards Present,&nbsp; <a href="#" onclick="javascript: fnCallRegisterCardPage(); return false">Click here to register</a></p> `;
							}
		
						} else if (data.error === "true") {
							htmlData = `<p style="color:grey">No Cards Present,&nbsp; <a href="#" onclick="javascript: fnCallRegisterCardPage(); return false">Click here to register</a></p> `;
		
						}
		
						$('#cardsdiv').show();
						$('#cardavailable').html(htmlData);
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
		}
		
		function fnCallRegisterCardPage() {
			$('#post-form').attr('action', 'ws');
			$('input[name="qs"]').val('card');
			$('input[name="rules"]').val('Register Card');
			$("#post-form").submit();
		}
		
		function validateCardDetails() {
			if (document.getElementById('tokenid').checked == true) {
				if (!fnValidateCVV2) {
					Swal.fire({
						text: 'CVV is invalid',
						icon: "error",
						showConfirmButton: true,
						confirmButtonText: "Ok",
					}).then(function() {
					});
				}
			} else {
				Swal.fire({
					text: 'Please Select Card to pay with',
					icon: "error",
					showConfirmButton: true,
					confirmButtonText: "Ok",
				}).then(function() {
				});
			}
		
		}
		
		function fnValidateCVV2() {
			let x = document.getElementById("cvv").value;
			if (isNaN(x) || x.length != 3) {
				return false;
			} else {
				return true;
			}
		}



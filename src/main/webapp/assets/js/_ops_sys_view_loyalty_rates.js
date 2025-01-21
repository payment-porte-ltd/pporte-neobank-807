
$(document).ready(function () {
	$('#addsellrate').attr('disabled','disabled');
	$('#viewassetpricingtable').dataTable( {
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

	$('#btn-addedeemrate').click(function() {
		//Check for the data validation	
		$("#addloyaltyconvrates-from").validate({
			rules: {
				seladddestinationasset: {
					required: true
				},
				addassetdesc: {
					required: true,
				},
				addconversionrate: {
					required: true,
				},
				selladdstatus: {
					required: true
				},
				addcreatedon: {
					required: true
				}
			},
			messages: {
				seladddestinationasset: {
					required: 'Please select the destination Asset for conversion',
				},
				addassetdesc: {
					required: 'Asset description is required',
				},
				addconversionrate: {
					required: 'Please enter the conversion rate',
				},
				selladdstatus: {
					required: 'Please  select the status',
				},
				addcreatedon: {
					required: 'Craeted on date is required',
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
	
		if ($("#addloyaltyconvrates-from").valid()) { 
			$('#addloyaltyconvrates-from').attr('action', 'ms');
			$('input[name="qs"]').val('opslyt');
			$('input[name="rules"]').val('opsaddloyaltyconvrates');
			$('input[name="hdnaddconversionstatus"]').val($('#selladdstatus :selected').val());
			$('input[name="hdnadddestasset"]').val($('#seladddestinationasset :selected').val());
		
			//$("#addassetpricing-from").submit();
			var formData = new FormData($('#addloyaltyconvrates-from')[0]);
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
							$('#addloyaltyconvrates-from').attr('action', fnGetOpsServletPath());
							$('#addloyaltyconvrates-from input[name="qs"]').val('opslyt');
							$('#addloyaltyconvrates-from input[name="rules"]').val('Redeem Rates');
							$("#addloyaltyconvrates-from").submit();
						});
	
					} else {
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

	$("button[data-dismiss=modal]").click(function(){
		$(".modal").modal('hide');
	});

	function fnEditLoyaltyPrice(destasset, conversionrate, status, createdon,sequenceno, assetdesc, expiry ) {
		$('#editLoyaltyPricingModal').on('show.bs.modal', function() {
		$('#editdestasset').val(destasset);
		$('#editassetdesc').val(assetdesc);
		$('#editconversionrate').val(conversionrate);
		$('#selleditstatus').val(status);
		$('#editcreatedon').val(createdon);	
		$('#hdnsequenceno').val(sequenceno);	
		$('#editexpirydate').val(expiry);	
		});
	
		$('#editLoyaltyPricingModal').modal('show');
	}
	
	$('#btn-editconversionrates').click(function() {
		//Check for the data validation	
		$("#editconversionrates-form").validate({
			rules: {
				editdestasset: {
					required: true
				},
				editassetdesc: {
					required: true,
				},
				editconversionrate: {
					required: true,
				},
				selleditstatus: {
					required: true
				},
				editcreatedon: {
					required: true
				}
			},
			messages: {
				editdestasset: {
					required: 'Asset Code is Required',
				},
				editassetdesc: {
					required: 'Asset description is required',
				},
				editconversionrate: {
					required: 'Please enter the conversion rates',
				},
				selleditstatus: {
					required: 'Please  select the status',
				},
				editcreatedon: {
					required: 'Craeted on date is required',
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
	
		if ($("#editconversionrates-form").valid()) {
			$('#editconversionrates-form').attr('action', 'ms');
			$('input[name="qs"]').val('opslyt');
			$('input[name="rules"]').val('opseditloyaltyconversionrates');
			$('input[name="hdneditconversionstatus"]').val($('#selleditstatus :selected').val());
		
			//$("#editassetrate-form").submit();
			var formData = new FormData($('#editconversionrates-form')[0]);
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
							$('#editconversionrates-form').attr('action', fnGetOpsServletPath());
							$('#editconversionrates-form input[name="qs"]').val('opslyt');
							$('#editconversionrates-form input[name="rules"]').val('Redeem Rates');
							$("#editconversionrates-form").submit();
						});
	
					} else {
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
		
		function UpdateassetDescParam(){
			var asset_desc =  $('#seladddestinationasset :selected').val();
			var arrasset = asset_desc.split(",");
			$("#addassetdesc").val(arrasset[1]);
			$("#spandestassetcode").text(arrasset[0]);	
			
			if(asset_desc !==''){
			  $("#lytconversionrate").hide();
			    $('#addconversionrate').removeAttr('disabled');
			}else{
				$('#addconversionrate').attr('disabled','disabled');
			}		
		}
		
		function UpdateConversionamount(){
			$("#lytconversionrate").show();
			
			var amount = $("#addconversionrate").val();
			$("#spanconversionrate").text(amount);
			
			if($("#addconversionrate").val() ===''){
				$("#lytconversionrate").hide();
			}


		}





 
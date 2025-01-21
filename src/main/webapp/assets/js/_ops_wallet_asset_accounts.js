
$(document).ready(function () {
	//$('#addsellrate').attr('disabled','disabled');
	
	//$('#view_wallet_assets_account').dataTable({ "bSort" : false } );

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

	$('#btn-addassetaccounts').click(function() {
		//Check for the data validation	
		$("#add_wallet_asset_account_from").validate({
			rules: {
				seladdassetcode: {
					required: true
				},
				selladdaccounttype: {
					required: true,
				},
				addpublickey: {
					required: true,
				},
				selladdstatus: {
					required: true
				}
			},
			messages: {
				seladdassetcode: {
					required: 'Please select Asset',
				},
				selladdaccounttype: {
					required: 'Please select Account type',
				},
				addpublickey: {
					required: 'Please enter public key',
				},
				selladdstatus: {
					required: 'Please  select the status',
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
	
		if ($("#add_wallet_asset_account_from").valid()) { 
			var formData = new FormData($('#add_wallet_asset_account_from')[0]);
			formData.append('qs', 'opsprc');
			formData.append('rules', 'ops_add_asset_accounts');
			for (var pair of formData.entries()) {
				console.log(pair[0] + " - " + pair[1]);
				}
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
							$('#submit_form').attr('action', fnGetOpsServletPath());
							$('#submit_form input[name="qs"]').val('opsprc');
							$('#submit_form input[name="rules"]').val('Wallet Asset Accounts');
							$("#submit_form").submit();
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

	$("button[data-dismiss=modal]").click(function(){
		$(".modal").modal('hide');
	});

	function fnEditAssetAccount(sequenceId, assetCode, accountType, publicKey, status ) {
		$('#editAssetAccount').on('show.bs.modal', function() {
		$('#selleditaccounttype').val(accountType);
		$('#editassetcode').val(assetCode);
		$('#editpublickey').val(publicKey);
		$('#selleditstatus').val(status);
		$('#hdnsequenceno').val(sequenceId);	
		});
	
		$('#editAssetAccount').modal('show');
	}
	
	$('#btn_editassetaccount').click(function() {
		//Check for the data validation	
		$("#editaccount_form").validate({
			rules: {
				editassetcode: {
					required: true
				},
				selleditaccounttype: {
					required: true,
				},
				editpublickey: {
					required: true,
				},
				selleditstatus: {
					required: true
				}
			},
			messages: {
				editassetcode: {
					required: 'Asset Code is Required',
				},
				selleditaccounttype: {
					required: 'Account type is required',
				},
				editpublickey: {
					required: 'Public Key is required',
				},
				selleditstatus: {
					required: 'Please  select the status',
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
	
		if ($("#editaccount_form").valid()) {
			var formData = new FormData($('#editaccount_form')[0]);
			formData.append('qs','opsprc');
			formData.append('rules','ops_edit_asset_accounts');
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
						Swal.fire({
							icon: 'success',
							title: 'Good',
							text: data.message,
							showConfirmButton: true,
							confirmButtonText: "Ok",
						}).then(function() {
							$('#submit_form').attr('action', fnGetOpsServletPath());
							$('#submit_form input[name="qs"]').val('opsprc');
							$('#submit_form input[name="rules"]').val('Wallet Asset Accounts');
							$("#submit_form").submit();
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
		




 
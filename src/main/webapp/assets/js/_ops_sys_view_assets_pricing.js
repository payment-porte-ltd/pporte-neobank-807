var assetPrice = '';
$(document).ready(function () {
	$('#addsellrate').attr('disabled','disabled');
	
	$('#viewassetpricingtable').dataTable({ "bSort" : false } );

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

	$('#btn-addassetpricing').click(function() {
		//Check for the data validation	
		$("#addassetpricing-from").validate({
			rules: {
				seladdassetcode: {
					required: true
				},
				selfiatcurrency: {
					required: true,
				},
				addexhangerate: {
					required: true,
				},
				selladdstatus: {
					required: true
				}
			},
			messages: {
				seladdassetcode: {
					required: 'Please select the selling Asset',
				},
				selfiatcurrency: {
					required: 'Please select the fiat currency',
				},
				addexhangerate: {
					required: 'Please enter the selling rate in terms of USD',
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
	
		if ($("#addassetpricing-from").valid()) { 
			$('#addassetpricing-from').attr('action', fnGetOpsServletPath());
			$('input[name="qs"]').val('opsprc');
			$('input[name="rules"]').val('opsaddassetpricing');
			$('input[name="hdnsellratetystatus"]').val($('#selladdstatus :selected').val());
			$('input[name="hdnaddassetcode"]').val($('#seladdassetcode :selected').val());
		
			//$("#addassetpricing-from").submit();
			var formData = new FormData($('#addassetpricing-from')[0]);
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
							$('#addassetpricing-from').attr('action', fnGetOpsServletPath());
							$('#addassetpricing-from input[name="qs"]').val('opsprc');
							$('#addassetpricing-from input[name="rules"]').val('Asset Pricing');
							$("#addassetpricing-from").submit();
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

	function fnEditAssetPrice(assetcode, assetdesc, sellrate, status, createdon,sequenceno,fiatcurrency ) {
		$('#editAssetPricingModal').on('show.bs.modal', function() {
		$('#editassetcode').val(assetcode);
		$('#editassetdesc').val(assetdesc);
		$('#editsellrate').val(sellrate);
		$('#selleditstatus').val(status);
		$('#editcreatedon').val(createdon);	
		$('#hdnsequenceno').val(sequenceno);	
		$('#editfiatcurrency').val(fiatcurrency);	
		});
	
		$('#editAssetPricingModal').modal('show');
	}
	
	$('#btn-editeditassetpricing').click(function() {
		//Check for the data validation	
		$("#editassetrate-form").validate({
			rules: {
				editassetcode: {
					required: true
				},
				editfiatcurrency: {
					required: true
				},
				editassetcode: {
					required: true,
				},
				editsellrate: {
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
				editassetcode: {
					required: 'Asset Code is Required',
				},
				editfiatcurrency: {
					required: 'Fiat Currency is Required',
				},
				editassetcode: {
					required: 'Asset description is required',
				},
				editsellrate: {
					required: 'Please enter the selling rate in terms of USD',
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
	
		if ($("#editassetrate-form").valid()) {
			$('#editassetrate-form').attr('action', fnGetOpsServletPath()); 
			$('input[name="qs"]').val('opsprc'); 
			$('input[name="rules"]').val('opseditassetpricing');
			$('input[name="hdnsellratetystatus"]').val($('#selleditstatus :selected').val());
		
			//$("#editassetrate-form").submit();
			var formData = new FormData($('#editassetrate-form')[0]);
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
							$('#addassetpricing-from').attr('action', fnGetOpsServletPath());
							$('#addassetpricing-from input[name="qs"]').val('opsprc');
							$('#addassetpricing-from input[name="rules"]').val('Asset Pricing');
							$("#addassetpricing-from").submit();
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
			var asset_desc =  $('#seladdassetcode :selected').val();
			var arrasset = asset_desc.split(",");
			$("#spansellassetcode").text(arrasset[0]);	
			
			if(asset_desc !==''){
			  $("#exchangerate").hide();
			    $('#addexhangerate').removeAttr('disabled');
			}else{
				$('#addexhangerate').attr('disabled','disabled');
			}		
		}
		function fnUpdateFiatDesc(){
			var fiat_desc =  $('#selfiatcurrency :selected').val();
			var arrfiat = fiat_desc.split(",");
			$("#spanfiatwalletasset").text(arrfiat[0]);	
			
			if(fiat_desc !==''){
			  $("#exchangerate").hide();
			    $('#addexhangerate').removeAttr('disabled');
			}else{
				$('#addexhangerate').attr('disabled','disabled');
			}		
		}
		
		function UpdateSellamount(){
			
			// Validate the two fields
			$("#addassetpricing-from").validate({
			rules: {
				seladdassetcode: {
					required: true
				},
				selfiatcurrency: {
					required: true,
				},
			},
			messages: {
				seladdassetcode: {
					required: 'Asset Code is Required',
				},
				selfiatcurrency: {
					required: 'Fiat currency is required',
				},
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
			if ($("#addassetpricing-from").valid()) {
			
				$("#exchangerate").show();
				
				var amount = $("#addexhangerate").val();
				
				$("#spansexchangerate").text(amount);
				
				if($("#addexhangerate").val() ===''){
					$("#exchangerate").hide();
				}
			}

		}
		
		function fnUpdatePricingFromCoingecko(){
			var formData = new FormData();
			formData.append("qs", "opsprc");
			formData.append("rules", "ops_update_asset_prices_from_coingecko");
			$('#spinner-div').show();//Load button clicked show spinner
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
					$('#spinner-div').hide();//Request is complete so hide spinner
					if (data.error == 'false') {
						//alert('lgtoken is '+data.token)
						Swal.fire({
							icon: 'success',
							title: 'Good',
							text: data.message,
							showConfirmButton: true,
							confirmButtonText: "Ok",
						}).then(function() {
							$('#addassetpricing-from').attr('action', fnGetOpsServletPath());
							$('#addassetpricing-from input[name="qs"]').val('opsprc');
							$('#addassetpricing-from input[name="rules"]').val('Asset Pricing');
							$("#addassetpricing-from").submit();
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
			
		}
		
	function addMarkupRate(){
		// alert('Markup rate');
		$("#add_asset_markup_from").validate({
			rules: {
				selmarkupassetcode: {
					required: true
				},
				selmarkupfiatcurrency: {
					required: true
				},
				markup_assetdesc: {
					required: true,
				},
				onramp_markuprate: {
					required: true,
				},
				selmarkupstatus: {
					required: true
				},
				markup_burning_rate: {
					required: true
				}
			},
			messages: {
				selmarkupassetcode: {
					required: 'Asset Code is Required',
				},
				selmarkupfiatcurrency: {
					required: 'Fiat Currency is Required',
				},
				markup_assetdesc: {
					required: 'Asset description is required',
				},
				onramp_markuprate: {
					required: 'Please enter the Markup rate',
				},
				selmarkupstatus: {
					required: 'Please  select the status',
				},
				markup_burning_rate: {
					required: 'Please enter the Markup rate',
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
	
		if ($("#add_asset_markup_from").valid()) {
			//$('#add_asset_markup_from').attr('action', fnGetOpsServletPath());
		
			//$("#editassetrate-form").submit();
			var formData = new FormData($('#add_asset_markup_from')[0]);
			formData.append("qs","opsprc");
			formData.append("rules","ops_add_markup_rate");
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
							$('#addassetpricing-from').attr('action', fnGetOpsServletPath());
							$('#addassetpricing-from input[name="qs"]').val('opsprc');
							$('#addassetpricing-from input[name="rules"]').val('Asset Pricing');
							$("#addassetpricing-from").submit();
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
	}

var selectedAssetCode='';
	function updateMarkupAssetDescParam(){
		var asset_desc =  $('#selmarkupassetcode :selected').val();
		console.log('asset_desc ',asset_desc);
		var arrasset = asset_desc.split(",");
		
		$("#spansellassetcode").val(arrasset[0]);	
		selectedAssetCode=arrasset[0];
		console.log("selectedAssetCode ",selectedAssetCode);
		/* if(asset_desc !==''){
		  $("#exchangerate").hide();
			$('#addexhangerate').removeAttr('disabled');
		}else{
			$('#addexhangerate').attr('disabled','disabled');
		}		 */
	}
	function updateMarkupFiatCurrencyParam(){
		var fiat_currency =  $('#selmarkupfiatcurrency :selected').val();
		console.log('fiat_currency ',fiat_currency);
		var arrfiatcurrency = fiat_currency.split(",");
		
		fnGetRatesWithoutMarkup(selectedAssetCode,arrfiatcurrency[0]);
		/* if(asset_desc !==''){
		  $("#exchangerate").hide();
			$('#addexhangerate').removeAttr('disabled');
		}else{
			$('#addexhangerate').attr('disabled','disabled');
		}		 */
	}

	function fnGetRatesWithoutMarkup(assetCode,fiatCurrency){
		//Validate two fields
		  
			$("#add_asset_markup_from").validate({
			rules: {
				selmarkupassetcode: {
					required: true
				},
				selmarkupfiatcurrency: {
					required: true,
				},
			},
			messages: {
				selmarkupassetcode: {
					required: 'Asset Code is Required',
				},
				selmarkupfiatcurrency: {
					required: 'Fiat currency is required',
				},
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
	if ($("#add_asset_markup_from").valid()) {
		
		var formData = new FormData();
		formData.append("qs", "opsprc");
		formData.append("rules", "ops_get_asset_price");
		formData.append("asset", assetCode);
		formData.append("fiat_currency", fiatCurrency);
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
					assetPrice = data.price;
					$("#markup_exchangerate").show();
					$("#span_markup_rate_assetcode").html(assetCode);
					$("#span_markup_rate_fiat_currency").html(fiatCurrency);
					$("#span_markup_rate_original_price").html(data.price);
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
	 }
		
	}

	function updateOnrampMarkupRate(param){
		$("#add_asset_markup_from").validate({
			rules: {
				selmarkupassetcode: {
					required: true
				},
				selmarkupfiatcurrency: {
					required: true
				}
			},
			messages: {
				selmarkupassetcode: {
					required: 'Asset Code is Required',
				},
				selmarkupfiatcurrency: {
					required: 'Fiat Currency is Required',
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
	
		if ($("#add_asset_markup_from").valid()) {
			if(param==='on'){
				//console.log('param is ', param);
				$("#exchangerate_after_exchange").show();
				var onMarkRate = $("#onramp_markuprate").val();
				var onFinalPrice = parseFloat(onMarkRate)*parseFloat(assetPrice);
				var asset_desc =  $('#selmarkupassetcode :selected').val();
				var fiat_currency =  $('#selmarkupfiatcurrency :selected').val();
				var arrasset = asset_desc.split(",");
				var arrfiatcurrency = fiat_currency.split(",");
				// exchangerate_after_exchange
				
				$("#span_after_markup_rate_assetcode").text(arrasset[0]);
				$("#span_after_markup_rate_fiat_currency").text(arrfiatcurrency[0]);
				$("#spans_after_markup_rate_exchangerate").text(Number((onFinalPrice).toFixed(6)));
				
				
				console.log('on final price ', onFinalPrice);
			}else if(param==='off'){
				$("#off_ramp_exchangerate").show();
				var offMarkRate = $("#markup_burning_rate").val();
				//console.log('param is ', param);
				var offFinalPrice = parseFloat(offMarkRate)*parseFloat(assetPrice);
				var asset_desc =  $('#selmarkupassetcode :selected').val();
				var fiat_currency =  $('#selmarkupfiatcurrency :selected').val();
				var arrasset = asset_desc.split(",");
				var arrfiatcurrency = fiat_currency.split(",");
				// exchangerate_after_exchange
				
				$("#off_ramp_spansellassetcode").text(arrasset[0]);
				$("#off_ramp_spansellfiatcurrency").text(arrfiatcurrency[0]);
				$("#off_ramp_spansexchangerate").text(Number((offFinalPrice).toFixed(6)));
				console.log('off final price ', offFinalPrice);
			}
			// assetPrice
		}
		
	}





 
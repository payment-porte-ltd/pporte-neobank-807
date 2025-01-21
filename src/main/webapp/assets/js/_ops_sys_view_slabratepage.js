
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
				
	function fnEditSlabRates(seqid, planid, fromrange, torange, rate, status) {
		$('#editplanslabrateModal').on('show.bs.modal', function() {
			//alert('status '+status+ ' seqid '+ seqid + ' planid '+planid+ ' fromrange '+ fromrange +' torange '+ torange+ ' rate '+ rate)
			$('#editslabid').val(seqid);
			$('#editplanid').val(planid);
			$('#editfromrange').val(fromrange);
			$('#edittorange').val(torange);
			$('#editrate').val(rate);
			$('#editselstatus').val(status);
		});
		$('#editplanslabrateModal').modal('show');
	}
	
		$('#btn-editeditslabrate').click(function() {
			//Check for the data validation	
			$("#editslabrate-form").validate({
				rules: {
					editplanid: {
						required: true
					},
					editfromrange: {
						required: true,
					},
					editrate: {
						required: true,
					},
					editslabid: {
						required: true
					},
					edittorange: {
						required: true
					},
					editselstatus: {
						required: true
					}
		
				},
				messages: {
					editplanid: {
						required: 'Please enter the Plan Id',
		
					},
					editfromrange: {
						required: 'Please enter the range from',
					},
					editrate: {
						required: 'Please enter the rate',
					},
					editslabid: {
						required: 'Slab Id is required',
					},
					edittorange: {
						required: 'Please enter the range to',
					},
					editselstatus: {
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
		
			if ($("#editslabrate-form").valid()) {
				$('input[name="qs"]').val('opsprc');
				$('input[name="rules"]').val('editpricingslabrate');
				$('input[name="hdneditstatus"]').val($('#editselstatus option:selected').val());
				
				var formData = new FormData($('#editslabrate-form')[0]);
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
							
								var planid = $('#editplanid').val();
								$('#editslabrate-form').attr('action', fnGetOpsServletPath());
							    $('input[name="qs"]').val('opsprc');
							    $('input[name="rules"]').val('viewslabrateforspecificplan');
							    $('input[name="hdnplanid"]').val(planid);
							    $("#editslabrate-form").submit();
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
		
		$('#btn-addslabrate').click(function() {
			//Check for the data validation	
			$("#addslabrate-form").validate({
				rules: {
					addslabrateplanid: {
						required: true
					},
					addtorange: {
						required: true,
					},
					addrate: {
						required: true,
					},
					editslabid: {
						required: true
					},
					addfromrange: {
						required: true
					},
					addselstatus: {
						required: true
					}
		
				},
				messages: {
					addslabrateplanid: {
						required: 'Slab rate Plan Id required',
		
					},
					addtorange: {
						required: 'Please enter the range to',
					},
					addrate: {
						required: 'Please enter the rate',
					},
					editslabid: {
						required: 'Slab Id is required',
					},
					addfromrange: {
						required: 'Please enter the range from',
					},
					addselstatus: {
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
		
			if ($("#addslabrate-form").valid()) {
				$('input[name="qs"]').val('opsprc');
				$('input[name="rules"]').val('addnewpricingslabrate');
				$('input[name="hdnnewstatus"]').val($('#addselstatus option:selected').val());
		
				var formData = new FormData($('#addslabrate-form')[0]);
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
							  var planid = $('#slabrateplanid').val();
								$('#editslabrate-form').attr('action', fnGetOpsServletPath());
							    $('input[name="qs"]').val('opsprc');
							    $('input[name="rules"]').val('viewslabrateforspecificplan');
							    $('input[name="hdnplanid"]').val(planid);
							    $("#editslabrate-form").submit();
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
		
		
		$("button[data-dismiss=modal]").click(function() {
			$(".modal").modal('hide');
		});

		
		
		





 
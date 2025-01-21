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

		$(document).ready ( function(){
			$('#pendingmerchtable').dataTable( {
			    "order": []
			} );
		});
		
		function fnEditCustomer(customercode){
			console.log('customercode is ',customercode)
			$('#pendingcust-form').attr('action', fnGetOpsServletPath());
		    $('input[name="qs"]').val('opscust');
		    $('input[name="rules"]').val('opsshowspecificcustomer');
			$('input[name="hdncustomercode"]').val(customercode);
			$("#pendingcust-form").submit(); 
		
		}
		
		
function fnGetSearchType(){
	
	var searchType = $("#selsearchtype option:selected").val();
	//alert(searchType)
	var serachHtml = '';
	$('#searchbycard').html('');
	
	if(searchType=='Customer_Name'){
		serachHtml +=`
				<div class="form-group">
					<label class="form-label" >Search By Customer Name</label>
					 <input type="text" class="form-control" name="searchcustname" id="searchcustname"  placeholder="Enter Customer Name"/>
				</div>`;
	}else if(searchType=='Relationship_Number'){
			serachHtml +=`
				<div class="form-group">
					<label class="form-label" >Search By Relationship Number </label>
					 <input type="text" class="form-control" name="searchrelnno" id="searchrelnno" placeholder="Enter Relationship Number"/>
				</div> `;
		
	}else if(searchType=='Customer_Id'){
			serachHtml +=`
				<div class="form-group">
					<label class="form-label" >Search By Customer Id </label>
					 <input type="text" class="form-control" name="searchcustid" id="searchcustid" placeholder="Enter Customer Id"/>
				</div> `;
		
	}else if(searchType=='Mobile_Number'){
		serachHtml +=`
				<div class="form-group">
					<label class="form-label" >Search By Mobile Number </label>
					 <input type="text" class="form-control" name="searchmobileno" id="searchmobileno" placeholder="Enter Mobile Number"/>
				</div> `;	
	}
	$('#searchbycard').append(serachHtml);
}
				
		
		
		function fnSearchForCustomer() {
			$("#searchspecificcust-form").validate({
					rules: {
							selsearchtype:{
								required:true,
							},
							searchmobileno:{
								required:true,
							},
							searchcustid:{
								required:true,
							},
							searchrelnno:{
								required:true,
							},
							searchcustname:{
								required:true,
							},
						
						},
					messages: {
							
							selsearchtype:{
								required:"Please search type"
							},
							searchmobileno:{
								required:"Please Enter Mobile Number",
							},
							searchcustid:{
								required:"Please Enter Customer Id",
							},
							searchrelnno:{
								required:"Please Enter Relationship Number",
							},
							searchcustname:{
								required:"Please Enter Customer Name",
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
		
			if ($("#searchspecificcust-form").valid()) {
					   $('#searchspecificcust-form').attr('action', fnGetOpsServletPath());
					   $('#searchspecificcust-form input[name="qs"]').val('opscust');
					   $('#searchspecificcust-form input[name="rules"]').val('ViewmorecustomersSearch');
			           $("#searchspecificcust-form").submit(); 
				}	else{
					 Swal.fire({
						  icon: 'error',
						  title: 'Oops',
						  text: 'Enter atleast one field',
						  showConfirmButton: true,
						  confirmButtonText: "Ok",
						  }).then(function() {
					  });
				}		
		}
		
			function fnOpsViewWallet(customercode){
			console.log('customercode is ',customercode)
			$('#pendingcust-form').attr('action', fnGetOpsServletPath());
		    $('input[name="qs"]').val('opscust');
		    $('input[name="rules"]').val('opsshowwalletforcustomer');
			$('input[name="hdncustomercode"]').val(customercode);
			$("#pendingcust-form").submit(); 
		
		}		
		
		function fnOpsUnblockPin(custcode,phone, email) {
			$('#pendingcust-form input[name="qs"]').val('opscust');
			$('#pendingcust-form input[name="rules"]').val('opsunblockcustpin');
			$('#pendingcust-form input[name="hdncustomercode"]').val(custcode);
			$('#pendingcust-form input[name="hdncustphone"]').val(phone);
			$('#pendingcust-form input[name="hdncustemail"]').val(email);
		
			Swal.fire({
				title: 'Are you sure?',
				text: "Are you sure you want to unblock user PIN for account "+custcode,
				icon: 'warning',
				confirmButtonText: 'Yes, Unblock!',
				showCancelButton: true,
				confirmButtonColor: '#3085d6',
				cancelButtonColor: '#d33',
			}).then((result) => {
				if (result.value) {
		
					var formData = new FormData($('#pendingcust-form')[0]);
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
									$('#pendingcust-form').attr('action', fnGetOpsServletPath());
									$('input[name="qs"]').val('opscust');
									$('input[name="rules"]').val('View Customers');
									$("#pendingcust-form").submit();
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
			});
		}
		
		function fnOpsUnlockCustAccount(custcode, phone, email) {
			$('#pendingcust-form input[name="qs"]').val('opscust');
			$('#pendingcust-form input[name="rules"]').val('opsunlockcustassword');
			$('#pendingcust-form input[name="hdncustomercode"]').val(custcode);
			$('#pendingcust-form input[name="hdncustphone"]').val(phone);
			$('#pendingcust-form input[name="hdncustemail"]').val(email);
		
			Swal.fire({
				title: 'Are you sure?',
				text: "Are you sure you want to unlock the Customer account " + custcode,
				icon: 'warning',
				confirmButtonText: 'Yes, Unlock!',
				showCancelButton: true,
				confirmButtonColor: '#3085d6',
				cancelButtonColor: '#d33',
			}).then((result) => {
				if (result.value) {
		
					var formData = new FormData($('#pendingcust-form')[0]);
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
									$('#pendingcust-form').attr('action', fnGetOpsServletPath());
									$('input[name="qs"]').val('opscust');
									$('input[name="rules"]').val('View Customers');
									$("#pendingcust-form").submit();
		
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
			});
		}
		
		
		





 
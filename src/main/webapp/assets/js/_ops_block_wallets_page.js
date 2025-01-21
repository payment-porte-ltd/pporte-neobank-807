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
			"idnav_UpdateProfile" : "Actualizaci n del perfil",
			"idnav_Logout" : "Cerrar sesi n",

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
		
																
		function fnBlockWallet(relationshipno,customerid , walletid, walletdesc ){
		console.log('relationshipno is ', relationshipno)
			$('#user-form-modal').on('show.bs.modal', function () {
			$('#viewrelationshipno').val(relationshipno);
			$('#viewcustomerid').val(customerid);
			$('#viewwalletid').val(walletid);
			$('#viewwalletdesc').val(walletdesc);
	
			 });
		
			$('#user-form-modal').modal('show');
		
		}
		function fnUnBlockWallet(relationshipno,customerid , walletid, walletdesc,blockcodedesc,blockcodeid ){
		console.log('relationshipno is ', relationshipno)
			$('#unblockWalletModal').on('show.bs.modal', function () {
			$('#viewrelationshipno_unblock').val(relationshipno);
			$('#viewcustomerid_unblock').val(customerid);
			$('#viewwalletid_unblock').val(walletid);
			$('#viewwalletdesc_unblock').val(walletdesc);
			$('#viewblockcodedesc_unblock').val(blockcodedesc);
			$('#editblockcoddeid_unblock').val(blockcodeid);
	
			 });
		
			$('#unblockWalletModal').modal('show');
		
		}
		 function fnUpdateBlockId() {
			alert('blockcodeid is ',blockcodeid)
		 		$('input[name="editblockcoddeid"]').val(blockcodeid);
		 }
	 
	 function fnUpdateBlockId() {
	 	 	var selectedblockdesc = $("#selblockdesc option:selected").val();
	 		var arrvalues = selectedblockdesc.split(",")
	 		//$('input[name="selblockdesc"]').val(arrvalues[0]);
	 		$('input[name="editblockcoddeid"]').val(arrvalues[1]);
	 	
	 }

$("button[data-dismiss=modal]").click(function(){
	$(".modal").modal('hide');
});




$('#btn_block_wallet').click(function() {
			//Check for the data validation	
			$("#block_wallet_form").validate({
				rules: {
					viewrelationshipno: {
						required: true
					},
					viewcustomerid: {
						required: true,
					},
					viewwalletid: { 
						required: true,
					},
					selblockdesc: {
						required: true,
					},
					editblockcoddeid: {
						required: true,
					}
				},
				messages: {
					viewrelationshipno: {
						required: 'Relationship number is required',
					},
					viewcustomerid: {
						required: 'Customer Id is required',
					},
					viewwalletid: {
						required: 'Wallet Id is required',
					},
					selblockdesc: {
						required: 'Please select Wallet Description',
					},
					editblockcoddeid: {
						required: 'Block Code Id is required',
					}
				},
	errorElement: "em",
	errorPlacement: function ( error, element ) {
		// Add the `invalid-feedback` class to the error element
		error.addClass( "invalid-feedback" );
		if ( element.prop( "type" ) === "checkbox" ) {
			error.insertAfter( element.next( "label" ) );
		} else {
			error.insertAfter( element );
		}
	},
	highlight: function ( element, errorClass, validClass ) {
		$( element ).addClass( "is-invalid" ).removeClass( "is-valid" );
	},
	unhighlight: function (element, errorClass, validClass) {
		$( element ).addClass( "is-valid" ).removeClass( "is-invalid" );
	}
	
 });
	if ($("#block_wallet_form").valid()) {
		
				$('#block_wallet_form').attr('action', fnGetOpsServletPath());
				$('input[name="qs"]').val('opswal');
				$('input[name="rules"]').val('opblockwalletid');     
				$('input[name="hdnblockcodedesc"]').val($('#selblockdesc :selected').val());
				console.log('rule is ' + $('input[name="rules"]').val());
				  
				//$("#editmerchuser-form").submit();
				 $('#spinner-div').show();//Load button clicked show spinner
				var formData = new FormData($('#block_wallet_form')[0]);
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
						  success: function (result) {
							$('#spinner-div').hide();//Request is complete so hide spinner
							  if (result) {
								  var data = JSON.parse(result);
								   if(data.error=='false'){
								//       console.log("no error");
										  Swal.fire({
												   icon: 'success',
												   title: " Successful",
												   text: "Wallet has been blocked",
												   showConfirmButton: true,
												   confirmButtonText: "Ok",
												   closeOnConfirm: true,
											   }).then(function() {
													// Call login page
													
														$('#block_wallet_form').attr('action', fnGetOpsServletPath());
														$('input[name="qs"]').val('opswal');
														$('input[name="rules"]').val('Block Wallets');     
														$("#block_wallet_form").submit();
											   });	
									   }else{
									   //swal.fire('Error message');
											 Swal.fire({
											 title: "Oops",              
											 text: "There was a problem when blocking this wallet, please try again later", 
											 icon: "error",
										   showConfirmButton: true,
										   confirmButtonText: "Ok",
										   closeOnConfirm: true,
										   }).then(function() {
												   // Reload the page
										   });	
									   } 
							  }
						   },
						   error: function() {
							  Swal.fire({
										  icon: 'error',
										  title: 'Oops',
										  text: 'Problem with connection',
										  showConfirmButton: true,
										  confirmButtonText: "Ok",
										  closeOnConfirm: true,
										  }).then(function() {
											/*$('#block_wallet_form').attr('action', 'ws');
											$('input[name="qs"]').val('opswal');
											$('input[name="rules"]').val('Block Wallets');     
											$("#block_wallet_form").submit();*/
															  });
						  }
					  });
	
	
  }else  {
				  Swal.fire({
					  icon: 'error',
					  title: "Oops",
					  text: "Please check your data"
				  })
				  return false;
	 }
	
})


$('#btn_unblock_wallet').click(function() {
	
	
				$('#unblock_wallet_form').attr('action', fnGetOpsServletPath());
				$('input[name="qs"]').val('opswal');
				$('input[name="rules"]').val('opunblockwalletid');     
				  
				//$("#editmerchuser-form").submit();
				$('#spinner-div').show();//Load button clicked show spinner
				var formData = new FormData($('#unblock_wallet_form')[0]);
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
						  success: function (result) {
							$('#spinner-div').hide();//Request is complete so hide spinner
							  if (result) {
								  var data = JSON.parse(result);
								   if(data.error=='false'){
								//       console.log("no error");
										  Swal.fire({
												   icon: 'success',
												   title: " Successful",
												   text: "Wallet has been unblocked",
												   showConfirmButton: true,
												   confirmButtonText: "Ok",
												   closeOnConfirm: true,
											   }).then(function() {
													// Call login page
													
														$('#unblock_wallet_form').attr('action', fnGetOpsServletPath());
														$('input[name="qs"]').val('opswal');
														$('input[name="rules"]').val('Block Wallets');     
														$("#unblock_wallet_form").submit();
											   });	
									   }else{
									   //swal.fire('Error message');
											 Swal.fire({
											 title: "Oops",              
											 text: "There was a problem when unblocking this wallet, please try again later", 
											 icon: "error",
										   showConfirmButton: true,
										   confirmButtonText: "Ok",
										   closeOnConfirm: true,
										   }).then(function() {
												   // Reload the page
										   });	
									   } 
							  }
						   },
						   error: function() {
							  Swal.fire({
										  icon: 'error',
										  title: 'Oops',
										  text: 'Problem with connection',
										  showConfirmButton: true,
										  confirmButtonText: "Ok",
										  closeOnConfirm: true,
										  }).then(function() {
											/*$('#block_wallet_form').attr('action', 'ws');
											$('input[name="qs"]').val('opswal');
											$('input[name="rules"]').val('Block Wallets');     
											$("#block_wallet_form").submit();*/
															  });
						  }
					  });
	
});

function fnGetFilteredTxn() {
			$( "#filter-txn-form" ).validate( {
		        rules: {
		            datefrom: {
		                required: true
		            },
		            dateto: {
		                required: true
		            }
		        },
		        messages: {
		            datefrom: {
		                required: 'Please enter date from'
		            },
		            dateto: {
		                required: 'Please enter date to'
		               
		            }
		        },
		        errorElement: "em",
		        errorPlacement: function ( error, element ) {
		            // Add the `invalid-feedback` class to the error element
		            error.addClass( "invalid-feedback" );
		            if ( element.prop( "type" ) === "checkbox" ) {
		                error.insertAfter( element.next( "label" ) );
		            } else {
		                error.insertAfter( element );
		            }
		        },
		        highlight: function ( element, errorClass, validClass ) {
		            $( element ).addClass( "is-invalid" ).removeClass( "is-valid" );
		        },
		        unhighlight: function (element, errorClass, validClass) {
		            $( element ).addClass( "is-valid" ).removeClass( "is-invalid" );
		        }
		    });
		
			if ($("#filter-txn-form").valid()) {
					   $('#filter-txn-form').attr('action', fnGetOpsServletPath());
					   $('#filter-txn-form input[name="qs"]').val('wal');
					   $('#filter-txn-form input[name="rules"]').val('get_filtered_cust_wallets');
						$('#filter-txn-form input[name="userlang"]').val($('#lang_def').text());
						$('#filter-txn-form input[name="hdnlang"]').val($('#lang_def').text());
			           $("#filter-txn-form").submit(); 
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

 
$(window).on("load", function(e) {
    $("#global-loader").fadeOut("slow");
})

var jsonStringData;
function fnBitcoinPresent(select){

	if(select ==='Yes'){
		 $('#stellar-present').show();
         $('#stellar-not-present').hide();
		document.getElementById( 'hide_stellar' ).style.display = 'none';
	}else{
		$('#stellar-present').hide();
		$('#stellar-not-present').show();
	}
}
function fmnemoniccodePresent(select){
	if(select ==='Yes'){
		 $('#mnemonic-present').removeClass("hidden");
	 	$('#stellar-account-choice').addClass("hidden");
		$('#mnemoni-not-present').addClass("hidden");
		document.getElementById( 'hide_stellar' ).style.display = 'none';
	}else{
		$('#mnemoni-not-present').removeClass("hidden");
		$('#mnemonic-present').addClass("hidden");
		$('#stellar-account-choice').addClass("hidden");
		document.getElementById( 'hide_stellar' ).style.display = 'none';
	}
}
function fnUserHasBTCAccount(select){
	if(select ==='Y'){
		$('#div_btc_inputs').show();
	}else{
		$('#div_btc_inputs').hide();
	}

}

function fnUserHasStellarAccountSetUpWallet(select){
	if(select ==='Y'){
		$('#div_stellar_public_key').show();
	}else{
		$('#div_stellar_public_key').hide();
	}
}
function fnRegisterMneumonicSetUpWallet(){
	$( "#register-mnemonic" ).validate( {
        ignore: [],
        rules: {
            has_account: {
                required: true
            },
            create_stellar: {
                required:function () {
                    return $("input[name='has_account']:checked").val()==="false";
                }
            },
           
        },
        messages: {
            has_account: {
                required: 'Select one of these',
            },
          
            create_stellar: {
                required: 'Select the checkbox before you proceed',
            },
          
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
	if($( "#register-mnemonic" ).valid()){
		console.log("I am getting here")
        	$('#spinner-div').show();//Load button clicked show spinner
			$('#register-mnemonic input[name="qs"]').val('btc');
      		$('#register-mnemonic input[name="rules"]').val('partner_create_bitcoin_mnemocic_code');
    		var formData = new FormData($('#register-mnemonic')[0]);
		     $.ajaxSetup({
		            beforeSend: function(xhr) {
		                xhr.setRequestHeader('x-api-key' , getAPIKey());
		            }
		        });
            $.ajax({
                url: "ms",
                data: formData,
                processData: false,
                contentType: false,
                type: 'POST',
                success: function (result) {
                    $('#spinner-div').hide();//Request is complete so hide spinner
                    var data = JSON.parse(result);
                    console.log('data ',data);
                    if(data.error=='false'){
						var jsonObjData = data.data;
						jsonStringData = JSON.stringify(jsonObjData);
						var mnemonicArray = jsonObjData.mnemonic_code;
						if(mnemonicArray.length>0){
							$('#first_corousel').append(mnemonicArray[0]);
							$('#second_corousel').append(mnemonicArray[1]);
							$('#third_corousel').append(mnemonicArray[2]);
							$('#fourth_corousel').append(mnemonicArray[3]);
							$('#fifth_corousel').append(mnemonicArray[4]);
							$('#sixth_corousel').append(mnemonicArray[5]);
							$('#seventh_corousel').append(mnemonicArray[6]);
							$('#eighth_corousel').append(mnemonicArray[7]);
							$('#nineth_corousel').append(mnemonicArray[8]);
							$('#10th_corousel').append(mnemonicArray[9]);
							$('#11th_corousel').append(mnemonicArray[10]);
							$('#12th_corousel').append(mnemonicArray[11]);
							$("#mneumonic_card").removeClass("hidden");
							$("#hide_stellar").addClass("hidden");
							//$("#btn_register_btc").prop("disabled", true);
						}else{
							`<p>There is no Mnemonic code generated</p>`
						}
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
    }
}
	console.log("Inside fnConfirmMneumonic ")
	var first_mnemonic =document.getElementById('first_corousel').innerHTML;
	console.log("first_mnemonic",first_mnemonic)
 	var second_corousel =document.getElementById('second_corousel').innerHTML;
	var third_corousel =document.getElementById('third_corousel').innerHTML;
	var fourth_corousel =document.getElementById('fourth_corousel').innerHTML;
	var fifth_corousel =document.getElementById('fifth_corousel').innerHTML;
	var sixth_corousel =document.getElementById('sixth_corousel').innerHTML;
	var seventh_corousel =document.getElementById('seventh_corousel').innerHTML;
	var eighth_corousel =document.getElementById('eighth_corousel').innerHTML;
	var nineth_corousel =document.getElementById('nineth_corousel').innerHTML;
	var tenth_corousel =document.getElementById('10th_corousel').innerHTML;
	var eleventh_corousel =document.getElementById('11th_corousel').innerHTML;
	var twelfth_corousel =document.getElementById('12th_corousel').innerHTML;
	var arr=first_mnemonic.concat(", ",second_corousel,", ",third_corousel,", ",fourth_corousel,", ",fifth_corousel,", ",sixth_corousel,", ",seventh_corousel,
	", ",eighth_corousel,", ",nineth_corousel,", ",tenth_corousel,", ",eleventh_corousel,", ",twelfth_corousel)
		
function fnConfirmMneumonicSetUpWallet(){
		Swal.fire({
		  text: arr,
		  icon: 'info',
		  html:
		    'Please check to confirm that you have copied the codes in a correct sequence.',
		  showCloseButton: true,
		  focusConfirm: false,
		  confirmButtonText:
		    '<i class="fa fa-thumbs-up"></i> Great!',
		  confirmButtonAriaLabel: 'Thumbs up, great!'
		}).then(function() {
				$("#div_btn_reg_stellar").removeClass("hidden");
				$("input[name='saved_mneumonic']:checked").val()==="true";  
         });
}

function fnRegisterBitcoinNewAccountSetUpWallet(passwordVal){
	$( "#register-bitcoin" ).validate( {
        ignore: [],
        rules: {
			
            saved_mneumonic: {
               required:true,
            },
            password: {
                required:true
            },
           
        },
        messages: {
          
            saved_mneumonic: {
                required: 'Select the checkbox before you proceed',
            },          
            password: {
                required: 'Please enter your password',
            },
          
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
    if($( "#register-bitcoin" ).valid()){
        $('#spinner-div').show();//Load button clicked show spinner
             $('#register-bitcoin input[name="qs"]').val('btc');
      		 $('#register-bitcoin input[name="rules"]').val('partner_add_stellar_new_account');
       		 var formData = new FormData($('#register-bitcoin')[0]);
            formData.append("data", jsonStringData);
			formData.append('security', passwordVal);
        for (var pair of formData.entries()) {
         console.log(pair[0] + " - " + pair[1]);
        }	
        	
		     $.ajaxSetup({
		            beforeSend: function(xhr) {
		                xhr.setRequestHeader('x-api-key' , getAPIKey());
		            }
		        });
            $.ajax({
                url: "ms",
                data: formData,
                processData: false,
                contentType: false,
                type: 'POST',
                success: function (result) {
                    $('#spinner-div').hide();//Request is complete so hide spinner
                    var data = JSON.parse(result);
                    console.log('data ',data);
                    if(data.error=='false'){
						$("#stlcardavailable").removeClass("hidden");
						//Modal
						$('#showStlResultsModal').on('show.bs.modal', function() {
							console.log("opening modal")
							$('#stlidpublickkey').val(data.stellar_pub_key);
							$('#stlidprivatekey').val(data.stellar_piv_key);
						});
					$('#showStlResultsModal').modal('show');	
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
    }

}



(function($) {
		"use strict";
		// Toolbar extra buttons
		var btnFinish = $('<button></button>').text('Finish')
			.addClass('btn btn-success')
			.on('click', function(){
				//alert("Submit clicked")
				 
				fnPasswordWithMnemonicSetUpWallet();
				return false;
				
			});
		var btnCancel = $('<button></button>').text('Cancel')
			.addClass('btn btn-danger')
			.on('click', function(){ $('#smartwizard').smartWizard("reset"); return false; });
	
	
		// Smart Wizard
		$('#smartwizard').smartWizard({
				selected: 0,
				theme: 'default',
				transitionEffect:'fade',
				showStepURLhash: true,
				toolbarSettings: {
						toolbarButtonPosition: 'end',
						toolbarExtraButtons: [btnFinish, btnCancel]
								}
		});
			
		// Arrows Smart Wizard 1
		$('#smartwizard-1').smartWizard({
				selected: 0,
				theme: 'arrows',
				transitionEffect:'fade',
				showStepURLhash: false,
				toolbarSettings: {
								  toolbarExtraButtons: [btnFinish, btnCancel]
								}
		});
				
		// Circles Smart Wizard 1
		$('#smartwizard-2').smartWizard({
				selected: 0,
				theme: 'circles',
				transitionEffect:'fade',
				showStepURLhash: false,
				toolbarSettings: {
								  toolbarExtraButtons: [btnFinish, btnCancel]
								}
		});
				
		// Dots Smart Wizard 1
		$('#smartwizard-3').smartWizard({
				selected: 0,
				theme: 'dots',
				transitionEffect:'fade',
				showStepURLhash: false,
				toolbarSettings: {
								  toolbarExtraButtons: [btnFinish, btnCancel]
								}
		});
		
	})(jQuery); 
	function fnSubmitFormSetUpWallet(passwordVal){
	  //Check for the data validation	
	  //validate data
	 
 	$( "#register-has-mnemonic-code" ).validate( {
		rules: {
			first_code: {
				required: true
			},
			second_code: {
				required: true,
			},
			third_code: {
				required: true,
			}, 
			fourth_code: {
				required: true,
			},
			fifth_code: {
				required: true,
			},
			sixth_code: {
				required: true,
			},
			seventh_code: {
				required: true
			},
			eight_code: {
				required: true
			},
			nineth_code: {
				required: true
			},
			tenth_code: {
				required: true
			},
			eleventh_code: {
				required: true
			},
			twelve_code: {
				required: true
			},
			 has_mnemonic: {
                required: true
            },
			input_btc_address:{
				required: true
			},
			input_btc_pubkey:{
				required: true
			},
			generate_stellar_account:{
				required: true
			}
		},
		messages: {
			first_code: {
				required: 'Please enter mnemonic code',
			},
			second_code: {
				required: 'Please enter mnemonic code',
			},
			third_code: {
				required: 'Please enter mnemonic code',
			},
			fourth_code: {
				required: 'Please enter mnemonic code',
			},
			fifth_code: {
				required: 'Please enter mnemonic code',
			},
			sixth_code: {
				required: 'Please enter mnemonic code',
			},
			seventh_code: {
				required: 'Please enter mnemonic code',
			},
			eight_code: {
				required: 'Please enter mnemonic code',
			},
			nineth_code: {
				required: 'Please enter mnemonic code',
			},
			tenth_code: {
				required: 'Please enter mnemonic code',
			},
			eleventh_code: {
				required: 'Please enter mnemonic code',
			},
			twelve_code: {
				required: 'Please enter mnemonic code',
			},
			has_mnemonic: {
                required: 'Select one of these',
            },
			input_btc_address:{
				required: 'Please enter BTC address',
			},
			input_btc_pubkey:{
				required: 'Please enter BTC public key ',
			},
			generate_stellar_account:{
				required: 'Please check this box',
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

	 if($( "#register-has-mnemonic-code" ).valid()){
	  			$('#register-has-mnemonic-code input[name="qs"]').val('btc');
				  $('#register-has-mnemonic-code input[name="rules"]').val('link_partner_account_with_mnemonic_code');
					  var formData = new FormData($('#register-has-mnemonic-code')[0]);
					//   formData.append("relno",relno);
					formData.append("security", passwordVal );
					/*  for (var pair of formData.entries()) {
						  console.log(pair[0] + " - " + pair[1]);
						} */		
				  // Call Ajax here and submit the form 							
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
								  if (result) {
									  var data = JSON.parse(result);
									   if(data.error=='false'){
										$("#stlcardavailable").removeClass("hidden");
										//Modal
										$('#showStlResultsModal').on('show.bs.modal', function() {
											console.log("opening modal")
											$('#stlidpublickkey').val(data.stellar_pub_key);
											$('#stlidprivatekey').val(data.stellar_piv_key);
										});
										$('#showStlResultsModal').modal('show');	
										   }else{
										    Swal.fire({
				                            text: data.message, 
				                            icon: "error",
				                            showConfirmButton: true,
				                            confirmButtonText: "Ok",
				                            }).then(function() {
				                                
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
												 
									  });
							  }
						  });
				  }else{
					  Swal.fire({
								  icon: 'error',
								  title: 'Oops',
								  text: 'Please fill required fields',
								  showConfirmButton: true,
								  confirmButtonText: "Ok",
								  closeOnConfirm: true,
								  }).then(function() {
									 
						  });
					  return false;
				  }
		
	
}

function fnLinkBitcoinAccountSetUpWallet(passwordVal){
	$( "#has_no_mnenmonic_code" ).validate( {
        ignore: [],
        rules: {
			has_account: {
                required: true
            },
            btc_address: {
               required:true,
            },
            btc_public_key: {
                required:true
            },
			stellar_public_key:{
				required:true
			}
           
        },
        messages: {
            has_account: {
                required: 'Select one of these'
            },
            btc_address: {
                required: 'Please enter BTC address'
            },          
            btc_public_key: {
                required: 'Please enter Public Key'
            },
			stellar_public_key:{
				required: 'Please enter Public Key'
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

    if($( "#has_no_mnenmonic_code" ).valid()){
        $('#spinner-div').show();//Load button clicked show spinner
             $('#has_no_mnenmonic_code input[name="qs"]').val('btc');
      		 $('#has_no_mnenmonic_code input[name="rules"]').val('link_partner_without_mnemonic');
       		 var formData = new FormData($('#has_no_mnenmonic_code')[0]);
			 formData.append("security",passwordVal);
        for (var pair of formData.entries()) {
         console.log(pair[0] + " - " + pair[1]);
        }	
        	
		     $.ajaxSetup({
		            beforeSend: function(xhr) {
		                xhr.setRequestHeader('x-api-key' , getAPIKey());
		            }
		        });
            $.ajax({
                url: "ms",
                data: formData,
                processData: false,
                contentType: false,
                type: 'POST',
                success: function (result) {
                    $('#spinner-div').hide();//Request is complete so hide spinner
                    var data = JSON.parse(result);
                    console.log('data ',data);
                    if(data.error=='false'){
                        Swal.fire({
                            icon: 'success',
                            text:  data.message,
                            showConfirmButton: true,
                            confirmButtonText: "Ok",
                        }).then(function() {
                          $('#register-bitcoin').attr('action', 'ws');
						$('input[name="qs"]').val('lgt');
						$('input[name="rules"]').val('opslgtdefault');
						$("#register-bitcoin").submit();
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
    }

}

function fnPasswordSetUpWallet(){
	 Swal.fire({
                title: 'Wallet Creation',
                text: 'Please note that we are going to create Pporte,VESL and USDC Wallets. The Wallets are non-custodial.',
                showConfirmButton: true,
                confirmButtonText: "Ok",
                }).then(function() {
                    const { value: password   } = Swal.fire({
					title: 'Enter your Password',
					input: 'password',
					inputLabel: 'Password',
					showCancelButton: true,
					inputAttributes: {
					autocapitalize: 'off',
					autocorrect: 'off'
					},
					inputValidator: (value) => {
					if (!value) {
						return 'Please input your password!';
					}
					}
				}).then((result) => {
					var hasAccount = $("input[name='has_account']:checked").val();
					if (result.value) {
						var passwordVal = result.value;
						if(hasAccount === 'true'){
							fnLinkBitcoinAccountSetUpWallet(passwordVal);
						}else{
							//fnRegisterBitcoinNewAccountSetUpWallet(passwordVal);
							createDigitalAssetViaWS();
							
						}				
					}
				});
        });		
	
}

function fnPasswordWithMnemonicSetUpWallet(){
	 		Swal.fire({
                        title: 'Wallet Creation',
                        text: 'Please note that we are going to create Pporte,VESL and USDC Wallets. The Wallets are non-custodial.',
                        showConfirmButton: true,
                        confirmButtonText: "Ok",
                        }).then(function() {
                         const { value: password} = Swal.fire({
						title: 'Enter your Password',
						input: 'password',
						inputLabel: 'Password',
						showCancelButton: true,
						inputAttributes: {
						autocapitalize: 'off',
						autocorrect: 'off'
						},
						inputValidator: (value) => {
						if (!value) {
							return 'Please input your password!';
						}
						}
					}).then((result) => {
						if (result.value) {
							var passwordVal = result.value;
							fnSubmitFormSetUpWallet(passwordVal);
						}
					});   
                });

}
var copyStlBtn = document.querySelector('#stlbtncopy');
copyStlBtn.addEventListener('click', function () {
	console.log("Inside copy")
	$("#allstlinfo").removeClass("hidden");
	$("#allstlinfo").val("Stellar Public Key: "+$("#stlidpublickkey").val()+"\r\n Stellar Private Key: "+$("#stlidprivatekey").val()).val();
	var infoField = document.querySelector('#allstlinfo');
	console.log("getting here222")
	infoField .select();
	document.execCommand('copy');
	$("#allstlinfo").addClass("hidden");
		Swal.fire({
                        icon: 'success',
                        title: 'Copying Stellar and Bitcoin Keys',
                        text: 'You have successfully copied Stellar and Bitcoin Keys, Please Paste and Store in a safe place',
                        showConfirmButton: true,
                        confirmButtonText: "Ok",
                        }).then(function() {
                            $('#register-bitcoin').attr('action', 'ws');
							$('input[name="qs"]').val('lgt');
							$('input[name="rules"]').val('opslgtdefault');
							$("#register-bitcoin").submit(); 
                });
	}, false);
function createDigitalAssetViaWS(){
	$('#form-post-data').attr('action', 'ws');
  	$('#form-post-data').attr('action', 'ws');
    $('input[name="qs"]').val('porte');
    $('input[name="rules"]').val('create_digital_asset_partner_page');
    $('input[name="json_string"]').val(jsonStringData);
    $("#form-post-data").submit();
}

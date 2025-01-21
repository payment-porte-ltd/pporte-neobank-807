i18next.init({
	lng: 'EN',
	fallbackLng: 'EN',
	debug: false,
	resources: {
	  EN: {
		translation: {
             /* Menus */
			"idnavmenu_Dashboard":"Dashboard",
			"idnavmenu_ManageFiatWallet":"Manage Fiat Wallet",
			"idnavmenu_DigitalAssets":"Digital Assets",
			"idnavmenu_DigitalAssets":"Digital Assets",
			"idnavmenu_CurrencyRemittance":"Currency Remittance",
			"idnavmenu_ManageCards":"Manage Cards",
			"idnavmenu_Loyalty":"Loyalty",
			"idnavmenu_UpdatePlan":"Update Plan",
			"idnavmenu_Logout":"Logout",
			"idnavmenu_BuyNewPlan":"Buy New Plan",
			"header_buy_porte_token":"Buy Porte Token",
			"btn_buy_coin":"Buy Coin",
			"idnavmenu_Profile":"Profile",
			"idnavmenu_Disputes":"Disputes",
             /* Sub Menus */
            "idnavsubmenu_Dashboard":"Dashboard",
            "idnavsubmenu_ViewandEdit":"View and Edit",
            "idnavsubmenu_ViewWallet":"View Wallet",
            "idnavsubmenu_TopupWallet":"Topup Wallet",
            "idnavsubmenu_SendMoney":"Send Money",
            "idnavsubmenu_CashTransactions":"Cash Transactions",
            "idnavsubmenu_SetupWallet":"Set up Wallet",
            "idnavsubmenu_Bitcoin":"Bitcoin",
            "idnavsubmenu_Assets":"Assets",
            "idnavsubmenu_BuyAsset":"Buy Asset",
            "idnavsubmenu_TransferCoin":"Transfer Coin",
            "idnavsubmenu_Swap":"Swap",
            "idnavsubmenu_SellAssettoFiat":"Sell Asset to Fiat",
            "idnavsubmenu_DisplayTransactions":"Display Transactions",
            "idnavsubmenu_DigitalRemittance":"Digital Remittance",
            "idnavsubmenu_FiatRemittance":"Fiat Remittance",
            "idnavsubmenu_ShowCards":"Show Cards",
            "idnavsubmenu_RedeemPoints":"Redeem Points",
            "idnavsubmenu_ViewDispute":"View Dispute",
            "idnavsubmenu_RaiseDisputes":"Raise Disputes",
            "idnavsubmenu_ChangeYourPlan":"Change Your Plan",
            "idnavsubmenu_BuyNewPlan":"Buy New Plan",
			
            /* Quick links */
            "idnav_UserSettings":"User Settings",
            "idnav_UpdateProfile":"Update Profile",
            "idnav_Logout":"Logout",
             /* Specific Page Content */
			"breadcrumb_item_label":"Manage Card",
			"breadcrumb_item_active_label":"Register Card",
			"label_register_card":"Register your Credit or Debit Bank Card here",
			"label_card_number":"Card Number",
			"label_expiry_date":"Expiry Date",
			"label_cvv":"CVV",
			"label_deescription":"Description, e.g. My Card",
			"label_register_card_btn":"Register Card",
			"validation_error_swal_header":"Oops..",
			"validation_error_swal_checkdata":"Please check your data",
			"swal_connection_prob":"Problem with connection",
			"validation_error_card_no":"Please Enter Card Number",
			"validation_error_card_no_length":"Please Enter Number of not less than 16 digits",
			"validation_error_card_expiry":"Please Enter Date of Expiry",
			"validation_error_card_name":"Please Enter full name",
			"validation_error_cvv":"Please Enter CVV",
			"validation_error_card_alias":"Please Enter Card Alias",
			"validation_error_expired_card":"Card has already expired",
			"validation_error_valid_card":"Please enter a valid card number",
			"card_registered_successful_title":"Registration Successful",
			"card_registered_successful_text":"Card Registered Successful",
			"card_registration_failed":"There was a problem registering your card, please try again",
			"dont-store-cvv-label":"(We don't store CVV)",		
		}
	  },
	  ES: {
		  translation: {
			/* Menus */
			"idnavmenu_Dashboard":"Tablero",
			"idnavmenu_ManageFiatWallet":"Administrar Monedero Fiat",
			"idnavmenu_DigitalAssets":"Recursos digitales",
			"idnavmenu_CurrencyRemittance":"Remesa de divisas",
			"idnavmenu_ManageCards":"Administrar tarjetas",
			"idnavmenu_Loyalty":"Lealtad",
			"idnavmenu_UpdatePlan":"Actualizar plan",
			"idnavmenu_Logout":"Cerrar sesión",
			"idnavmenu_BuyNewPlan":"Comprar nuevo plan",
			"header_buy_porte_token":"Comprar Token de Porte",
			"btn_buy_coin":"Comprar moneda",
			"idnavmenu_Profile":"Perfil",
             /* Sub Menus */
            "idnavsubmenu_Dashboard":"Tablero",
            "idnavsubmenu_ViewandEdit":"Ver y editar",
            "idnavsubmenu_ViewWallet":"Ver billetera",
            "idnavsubmenu_TopupWallet":"Monedero de recarga",
            "idnavsubmenu_SendMoney":"Enviar dinero",
            "idnavsubmenu_CashTransactions":"Transacciones en efectivo",
            "idnavsubmenu_SetupWallet":"Configurar Monedero",
            "idnavsubmenu_Bitcoin":"Bitcoin",
            "idnavsubmenu_Assets":"Activos",
            "idnavsubmenu_BuyAsset":"Comprar activo",
            "idnavsubmenu_TransferCoin":"Comprar juego de monedas ATransfer",
            "idnavsubmenu_Swap":"Intercambio",
            "idnavsubmenu_SellAssettoFiat":"Vender activos a Fiat",
            "idnavsubmenu_DisplayTransactions":"Mostrar transacciones",
            "idnavsubmenu_DigitalRemittance":"Remesas digitales",
            "idnavsubmenu_FiatRemittance":"Remesa fiduciaria",
            "idnavsubmenu_ShowCards":"Mostrar cartas",
            "idnavsubmenu_RedeemPoints":"Canjear puntos",
            "idnavsubmenu_ViewDispute":"Ver disputa",
            "idnavsubmenu_RaiseDisputes":"Plantear disputas",
            "idnavsubmenu_ChangeYourPlan":"Cambia tu plan",
            "idnavsubmenu_BuyNewPlan":"Comprar nuevo plan",
            /* Quick links */
            "idnav_UserSettings":"Ajustes de usuario",
            "idnav_UpdateProfile":"Actualización del perfil",
            "idnav_Logout":"Cerrar sesión",
             /* Specific Page Content */
			"breadcrumb_item_label":"Administrar tarjeta",
			"breadcrumb_item_active_label":"Registrar tarjeta",
			"label_register_card":"Registra tu Tarjeta Bancaria de Crédito o Débito aquí",
			"label_card_number":"Número de tarjeta",
			"label_expiry_date":"Fecha de vencimiento",
			"label_cvv":"CVV",
			"label_deescription":"Descripción, por ejemplo, Mi tarjeta",
			"label_register_card_btn":"Registrar tarjeta",
			"validation_error_swal_header":"Vaya...",
			"validation_error_swal_checkdata":"Verifique sus datos",
			"swal_connection_prob":"Problema con la conexión",
			"validation_error_card_no":"Ingrese el número de tarjeta",
			"validation_error_card_no_length":"Ingrese un número de no menos de 16 dígitos",
			"validation_error_card_expiry":"Ingrese la fecha de vencimiento",
			"validation_error_card_name":"Ingrese el nombre completo",
			"validation_error_cvv":"Ingrese CVV",
			"validation_error_card_alias":"Ingrese el alias de la tarjeta",
			"validation_error_expired_card":"La tarjeta ya venció",
			"validation_error_valid_card":"Ingrese un número de tarjeta válido",
			"card_registered_successful_title":"Registro Exitoso",
			"card_registered_successful_text":"Tarjeta registrada correctamente",
			"card_registration_failed":"Hubo un problema al registrar su tarjeta, intente nuevamente",
			"dont-store-cvv-label":"(No almacenamos CVV)",
			
			}
		},
	}
  }, function(err, t) {
	   updateContent();
  });
  
  function updateContent() {
        /* Menus */
        $('#idnavmenu_Dashboard').text(i18next.t('idnavmenu_Dashboard')); 
        $('#idnavmenu_ManageFiatWallet').text(i18next.t('idnavmenu_ManageFiatWallet')); 
        $('#idnavmenu_DigitalAssets').text(i18next.t('idnavmenu_DigitalAssets')); 
        $('#idnavmenu_CurrencyRemittance').text(i18next.t('idnavmenu_CurrencyRemittance')); 
        $('#idnavmenu_ManageCards').text(i18next.t('idnavmenu_ManageCards')); 
        $('#idnavmenu_Loyalty').text(i18next.t('idnavmenu_Loyalty')); 
        $('#idnavmenu_UpdatePlan').text(i18next.t('idnavmenu_UpdatePlan')); 
        $('#idnavmenu_Logout').text(i18next.t('idnavmenu_Logout')); 
        $('#idnavmenu_BuyNewPlan').text(i18next.t('idnavmenu_BuyNewPlan')); 
        $('#header_buy_porte_token').text(i18next.t('header_buy_porte_token')); 
        $('#btn_buy_coin').text(i18next.t('btn_buy_coin')); 
        $('#idnavmenu_Profile').text(i18next.t('idnavmenu_Profile')); 
        $('#idnavmenu_Disputes').text(i18next.t('idnavmenu_Disputes')); 
        /* Sub Menus */
        $('#idnavsubmenu_Dashboard').text(i18next.t('idnavsubmenu_Dashboard')); 
        $('#idnavsubmenu_ViewandEdit').text(i18next.t('idnavsubmenu_ViewandEdit')); 
        $('#idnavsubmenu_ViewWallet').text(i18next.t('idnavsubmenu_ViewWallet')); 
        $('#idnavsubmenu_TopupWallet').text(i18next.t('idnavsubmenu_TopupWallet')); 
        $('#idnavsubmenu_SendMoney').text(i18next.t('idnavsubmenu_SendMoney')); 
        $('#idnavsubmenu_CashTransactions').text(i18next.t('idnavsubmenu_CashTransactions')); 
        $('#idnavsubmenu_SetupWallet').text(i18next.t('idnavsubmenu_SetupWallet')); 
        $('#idnavsubmenu_Bitcoin').text(i18next.t('idnavsubmenu_Bitcoin')); 
        $('#idnavsubmenu_Assets').text(i18next.t('idnavsubmenu_Assets')); 
        $('#idnavsubmenu_BuyAsset').text(i18next.t('idnavsubmenu_BuyAsset')); 
        $('#idnavsubmenu_TransferCoin').text(i18next.t('idnavsubmenu_TransferCoin')); 
        $('#idnavsubmenu_Swap').text(i18next.t('idnavsubmenu_Swap')); 
        $('#idnavsubmenu_SellAssettoFiat').text(i18next.t('idnavsubmenu_SellAssettoFiat')); 
        $('#idnavsubmenu_DisplayTransactions').text(i18next.t('idnavsubmenu_DisplayTransactions')); 
        $('#idnavsubmenu_DigitalRemittance').text(i18next.t('idnavsubmenu_DigitalRemittance')); 
        $('#idnavsubmenu_FiatRemittance').text(i18next.t('idnavsubmenu_FiatRemittance')); 
        $('#idnavsubmenu_ShowCards').text(i18next.t('idnavsubmenu_ShowCards')); 
        $('#idnavsubmenu_RedeemPoints').text(i18next.t('idnavsubmenu_RedeemPoints')); 
        $('#idnavsubmenu_ViewDispute').text(i18next.t('idnavsubmenu_ViewDispute')); 
        $('#idnavsubmenu_RaiseDisputes').text(i18next.t('idnavsubmenu_RaiseDisputes')); 
        $('#idnavsubmenu_ChangeYourPlan').text(i18next.t('idnavsubmenu_ChangeYourPlan')); 
        $('#idnavsubmenu_BuyNewPlan').text(i18next.t('idnavsubmenu_BuyNewPlan')); 
        /* Quick links */
        $('#idnav_UserSettings').text(i18next.t('idnav_UserSettings'));        
        $('#idnav_UpdateProfile').text(i18next.t('idnav_UpdateProfile'));        
        $('#idnav_Logout').text(i18next.t('idnav_Logout'));        
        /* Specific Page Content */
		$('#breadcrumb_item_label').text(i18next.t('breadcrumb_item_label')); 
		$('#breadcrumb_item_active_label').text(i18next.t('breadcrumb_item_active_label')); 
		$('#label_register_card').text(i18next.t('label_register_card')); 
		$('#label_card_number').text(i18next.t('label_card_number')); 
		$('#label_expiry_date').text(i18next.t('label_expiry_date')); 
		$('#label_cvv').text(i18next.t('label_cvv')); 
		$('#label_deescription').text(i18next.t('label_deescription')); 
		$('#label_register_card_btn').text(i18next.t('label_register_card_btn')); 
		$('#validation_error_swal_header').text(i18next.t('validation_error_swal_header')); 
		$('#validation_error_swal_checkdata').text(i18next.t('validation_error_swal_checkdata')); 
		$('#swal_connection_prob').text(i18next.t('swal_connection_prob')); 
		$('#validation_error_card_no').text(i18next.t('validation_error_card_no')); 
		$('#validation_error_card_no_length').text(i18next.t('validation_error_card_no_length')); 
		$('#validation_error_card_expiry').text(i18next.t('validation_error_card_expiry')); 
		$('#validation_error_card_name').text(i18next.t('validation_error_card_name')); 
		$('#validation_error_cvv').text(i18next.t('validation_error_cvv')); 
		$('#validation_error_card_alias').text(i18next.t('validation_error_card_alias')); 
		$('#validation_error_expired_card').text(i18next.t('validation_error_expired_card')); 
		$('#card_registered_successful_title').text(i18next.t('card_registered_successful_title')); 
		$('#validation_error_valid_card').text(i18next.t('validation_error_valid_card')); 
		$('#card_registered_successful_text').text(i18next.t('card_registered_successful_text')); 
		$('#card_registration_failed').text(i18next.t('card_registration_failed')); 
		$('#dont-store-cvv-label').text(i18next.t('dont-store-cvv-label')); 
  }
	  function fnChangePageLanguage(lng){
		console.log('lng ',lng);
		  i18next.changeLanguage(lng, fnChangeLanguage(lng))
		  $('#hdnlangpref1').val(lng);
	  }
	  i18next.on('languageChanged', function(lng) {
		updateContent(lng);
	  });
	  function fnChangeLanguage(lang){
		  if(lang=='EN')  $('#lang_def').text('EN') 
			else if(lang=='ES')  $('#lang_def').text('ES')
	  }






$(window).on("load", function(e) {
    $("#global-loader").fadeOut("slow");
})

	$(document).ready(function() {
		 $('#cardform').card({
			// a selector or DOM element for the container
			// where you want the card to appear
			container: '.card-wrapper', // *required*
			placeholders: {
				number: ' .... .... ....',
				name: '........ ........',
				expiry: '../..',
			},
			});
	  });

function checkCardExpiry(mydate){
//get todays date
var today = new Date(); // gets the current date
var today_mm = today.getMonth() + 1; // extracts the month portion
var today_yy = today.getFullYear() % 100;// extracts the year portion and changes it from yyyy to yy format
var valid;
  
mm = mydate.substring(0, 2); 
yy = mydate.substring(3);
//console.log(mm);
  if(mm > 12){
  //check date validity
	return "invalid date";
  }
//comparing input date date with current date
  if (yy > today_yy || (yy == today_yy && mm >= today_mm)) {
	   return true;
  } else{
	   return false;
  }
}
	

function fnRegisterCard(){
 $( "#cardform" ).validate( {
	rules: {
		number: {
			required: true,
			minlength:16
		},
		expiry: {
			required: true
		},
		name: {
			required: true
		},
		cvc: {
			required: true
		}, 
		cardalias: {
			required: true
		},
	},
	messages: {
		
		number: {
			required: $("#validation_error_card_no").text(),
			minlength:$("#validation_error_card_no_length").text(),
		},
		expiry: {
			required:$("#validation_error_card_expiry").text(),
		
		},
		name: {
			required:$("#validation_error_card_name").text()
		},
		cvc: {
			required:$("#validation_error_cvv").text()
		},
		cardalias: {
			required:$("#validation_error_card_alias").text()
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

if($( "#cardform" ).valid()){
		var carddate = $("#expiry").val(); 
		var cardno = $("#number").val(); 
			carddate = carddate.replaceAll(" ", "");
		var result = checkCardExpiry(carddate);
		console.log(carddate +":"+result );
		
		 if (result===false){
			Swal.fire({
					  icon: 'error',
					  title: "Oops",
					  text: $("#validation_error_expired_card").text()
				  })
				  return false;
		 }
	
	var isValid=validateCreditCardNumber(cardno);
			console.log("Is valid is"+isValid);
			if (isValid === false){
			Swal.fire({
					  icon: 'error',
					  title: "Oops",
					  text: $("#validation_error_valid_card").text()
				  })
				  return false;
				
			}
			  $('#cardform input[name="qs"]').val('card');
			  $('#cardform input[name="rules"]').val('regnewcard');
			  $('#cardform input[name="relno"]').val(relno);
				  var formData = new FormData($('#cardform')[0]);
					  formData.append("hdnlang",$('#lang_def').text());	
						
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
								//       console.log("no error");
										  Swal.fire({
												
		 		
												   icon: 'success',
												   title: $("#card_registered_successful_title").text(),
												   text: 	$("#card_registered_successful_text").text(),
												   showConfirmButton: true,
												   confirmButtonText: "Ok",
												   closeOnConfirm: true,
											   }).then(function() {
													// Call login page
													  $('#cardform').attr('action', 'ws');
													  $('input[name="qs"]').val('card');
													  $('input[name="rules"]').val('Show Cards');
													  $("#cardform").submit();
											   });	
									   }else if (data.error==="bad"){
                                              //swal.fire('Error message');
                                                    Swal.fire({
                                                    title: "Oops",              
                                                    text: data.message, 
                                                    icon: "error",
                                                  showConfirmButton: true,
                                                  confirmButtonText: "Ok",
                                                  closeOnConfirm: true,
                                                  }).then(function() {
                                                          // Do Nothing
                                                  });
											}else{
									   //swal.fire('Error message');
											 Swal.fire({
											 title: "Oops",             
											 text: $("#card_registration_failed").text(), 
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
										  text: $("#swal_connection_prob").text() ,
										  showConfirmButton: true,
										  confirmButtonText: "Ok",
										  closeOnConfirm: true,
										  }).then(function() {
											 /* $('#cust-reg-form').attr('action', 'ws');
											  $('input[name="qs"]').val('lgt');
											  $('input[name="rules"]').val('lgtdefault');
											  $("#cust-reg-form").submit();*/
								  });
						  }
					  });
	
	
  }else  {
				  Swal.fire({
					  icon: 'error',
					  title: "Oops",
					  text: $("#validation_error_swal_checkdata").text()
			
				  })
				  return false;
	 }
}



String.prototype.replaceAll = function (stringToFind, stringToReplace) {
  if (stringToFind === stringToReplace) return this;
  var temp = this;
  var index = temp.indexOf(stringToFind);
  while (index != -1) {
	  temp = temp.replace(stringToFind, stringToReplace);
	  index = temp.indexOf(stringToFind);
  }
  return temp;
};


//returns true or false
function validateCreditCardNumber(cardNumber) {
	cardNumber = cardNumber.split(' ').join("");
	if (parseInt(cardNumber) <= 0 || (!/\d{15,16}(~\W[a-zA-Z])*$/.test(cardNumber)) || cardNumber.length > 16) {
		return false;
	}
	var carray = new Array();
	for (var i = 0; i < cardNumber.length; i++) {
		carray[carray.length] = cardNumber.charCodeAt(i) - 48;
	}
	carray.reverse();
	var sum = 0;
	for (var i = 0; i < carray.length; i++) {
		var tmp = carray[i];
		if ((i % 2) != 0) {
			tmp *= 2;
			if (tmp > 9) {
				tmp -= 9;
			}
		}
		sum += tmp;
	}
	return ((sum % 10) == 0);
}
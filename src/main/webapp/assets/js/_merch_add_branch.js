i18next.init({
	lng: 'en',
	fallbackLng: 'en',
	debug: false,
	resources: {
	  en: {
		translation: {
		  "login-language-title" : "Languages"
		}
	  },
	  es: {
		  translation: {
		  "login-language-title" : "Idiomas"
		}
		}
	}
  }, function(err, t) {
	   updateContent();
  });

function updateContent() {
    $('#login-language-title').text(i18next.t('login-language-title')); 
}
	   
function fnChangePageLanguage(lng){
    i18next.changeLanguage(lng, fnChangeLanguage(lng))
}

i18next.on('languageChanged', function(lng) {
updateContent(lng);
});
			 
function fnChangeLanguage(lang){
    if(lang=='en')  $('#lang_def').text('EN') 
    else if(lang=='es')  $('#lang_def').text('ES')
    $('#hdnlangpref1,#hdnlangpref2,#hdnlangpref3').val(lang);
}

(function($) {
	"use strict";
	// Toolbar extra buttons
	var btnFinish = $('<button></button>').text('Finish')
		.addClass('btn btn-success')
		.on('click', function(){
			//alert('Add branch');
			registerBranch(); 
			return false;
			
		});
	var btnCancel = $('<button></button>').text('Cancel')
		.addClass('btn btn-danger')
		.on('click', function(){
             $('#smartwizard').smartWizard("reset");
             return false;
        });


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


 /* Register method*/
function registerBranch(){
 	/* $( "#merch-add-branch-form" ).validate( {

	  rules: {
		  companyname: {
			  required: true,
			  minlength: 2
		  },
		  bsndesc: {
			  required: true,
			  minlength: 10
		  },
		  physicaladd: {
			  required: true,
			  minlength: 5
		  },
		  bsnphoneno: {
			  required: true,
			  minlength: 8
		  },
		  selectmcc: {
			  required: true
		  },
		  fullname: {
			  required: true,
			  minlength: 5
		  },
		  email: {
			 email: true,
			 required: true
		  },
		  merchphoneno: {
			 minlength: 8,
			 required: true
		  },
		  nationalId: {
			  required: true,
			  minlength: 5
		  },
		  selectpricingplan:{
			  required: true
		  },
		  file1:{
			  required: true,
			  extension: "docx|rtf|doc|pdf|jpeg|PNG"
		  }
	  },
	  messages: {
		  companyname: {
			  required: $('#add-branch-data-validation-companyname').text(),
			  minlength: $('#add-branch-data-validation-companyname-length').text()
		  },
		  bsndesc: {
			  required: $('#add-branch-data-validation-bsndesc').text(),
			  minlength: $('#add-branch-data-validation-bsndesc-length').text()
		  },
		  physicaladd: {
			  required: $('#add-branch-data-validation-physicaladd').text(),
			  minlength: $('#add-branch-data-validation-physicaladd-length').text()
		  },
		  bsnphoneno: {
			  required: $('#add-branch-data-validation-bsnphoneno').text(),
			  minlength: $('#add-branch-data-validation-bsnphoneno-length').text()
		  },

		  fullname: {
			  required: $('#add-branch-data-validation-fullname').text(),
			  minlength: $('#add-branch-data-validation-fullname-length').text()
		  },
		  nationalId: {
			  required: $('#add-branch-data-validation-nationalId').text(),
			  minlength: $('#add-branch-data-validation-nationalId-length').text()
		  },
		  email: {
			  email: $('#add-branch-data-validation-email').text(),
			  required: $('#add-branch-data-validation-email-required').text()
		  },
		  selectmcc: {
			  required: $('#add-branch-data-validation-selectmcc').text()
		  },
		  selectpricingplan: {
			  required: $('#add-branch-data-validation-selectpricingplan').text()
		  },
		  merchphoneno: {
			required: $('#add-branch-data-validation-merchphoneno').text(),
			minlength:$('#add-branch-data-validation-merchphoneno-length').text()
		},
		file1: {
			required: $('#add-branch-data-validation-file1').text()
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
	});  */
    // if($( "#merch-add-branch-form" ).valid()){
		$('#merch-add-branch-form input[name="qs"]').val('merchprf');
		$('#merch-add-branch-form input[name="rules"]').val('addbranch');
		$('#merch-add-branch-form input[name="hdnmccid"]').val($('#selectmcc :selected').val());
		$('#merch-add-branch-form input[name="hdnplan"]').val($('#selectpricingplan :selected').val());
		var form = $('#merch-add-branch-form')[0];
		var formData = new FormData(form);	
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
						console.log("result is ", result);
						if(data.error=='false'){
							Swal.fire({
										icon: 'success',
										text: data.message,
										showConfirmButton: true,
										confirmButtonText: "Ok",
									}).then(function() {
										$('#managebranches-form').attr('action', 'ws');
										$('input[name="qs"]').val('merchprf');
										$('input[name="rules"]').val('Manage branches');
										$("#managebranches-form").submit();	
									});	
							}else{
								Swal.fire({
								text: data.message, 
								icon: "error",
								showConfirmButton: true,
								confirmButtonText: "Ok",
								}).then(function() {
									$('#get-add-branch-form').attr('action', 'ws');
									$('input[name="qs"]').val('merchprf');
									$('input[name="rules"]').val('getaddbranchpage');
									$("#get-add-branch-form").submit();
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
							}).then(function() {
								
					});
				}
			});   
	// } 
    
}

function getMerchantCategory(){
	$('#get-mcc-form input[name="qs"]').val('reg');
	$('#get-mcc-form input[name="rules"]').val('getmcccategory');
	
	var form = $('#get-mcc-form')[0];
    var formData = new FormData(form);

	for (var pair of formData.entries()) {
        console.log(pair[0] + " - " + pair[1]);
    }	
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
				var listMccGroups = data.data;
				var htmlOptions = '';
				for(var i = 0; i < listMccGroups.length; i++) {
					 htmlOptions +='<option value="'+listMccGroups[i].mccId
					+'">'+listMccGroups[i].mccName+'</option>' ;
				}
				$('#selectmcc').append(htmlOptions);
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
$(window).on("load", function(e) {
    $("#global-loader").fadeOut("slow");
})
var name='';
var email='';
var address='';
var contact ='';

function fnEditProfileButton(){
	$('#editname').val($("#name").text());
	$('#editpublickey').val($("#account_id").text());
	$('#editcontact').val($("#contact").text());
	$('#location').val($("#span_loaction").text());
	$("#exampleModal3").modal('show');
}
$("button[data-dismiss=modal]").click(function(){
	$(".modal").modal('hide');
});



function fnSubmitEditDetails(){
	//alert('clicked')
	$( "#edit-form" ).validate( {
	rules: {
		editname: {
			required: true
		},
		editaddress: {
			required: true
		},
		editemail: {
			required: true
		},
		editcontact: {
			required: true
		}, 
	
	},
	messages: {
		editname: {
			required: "Please Enter Name"
		},
		editpublickey: {
			required: "Please Enter publi key",
		
		},
		editcontact: {
			required: "Please Enter Contacts"
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
	if($( "#edit-form" ).valid()){

			  $('#edit-form input[name="qs"]').val('prf');
			  $('#edit-form input[name="rules"]').val('editpartnerdetails');
				  var formData = new FormData($('#edit-form')[0]);
				  
				 /* for (var pair of formData.entries()) {
					  console.log(pair[0] + " - " + pair[1]);
					}	*/			
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
												   title: " Successful",
												   text: "Profile Edited Successful",
												   showConfirmButton: true,
												   confirmButtonText: "Ok",
												   closeOnConfirm: true,
											   }).then(function() {
													// Call login page
													  $('#edit-form').attr('action', fnGetOpsServletPath());
													  $('input[name="qs"]').val('partdash');
													  $('input[name="rules"]').val('Partner Profile');
													  $("#edit-form").submit();
											   });	
									   }else{
									   //swal.fire('Error message');
											 Swal.fire({
											 title: "Oops",              
											 text: "There was a problem in updating your profile, please try again", 
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
					  text: "Please check your data"
				  })
				  return false;
	 }
	
}
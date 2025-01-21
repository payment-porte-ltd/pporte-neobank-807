$(window).on("load", function(e) {
	$("#global-loader").fadeOut("slow");
})
var functionCallCounter = 1;
$( document ).ready(function() {
    fnRelationshipNo();
    createDigitalAssets();
   
});



function checkDigitalAssetIfAreCreated(){
    console.log('check ing');
	var formData = new FormData();
	formData.append('qs','porte')
	formData.append('rules','check_if_digital_assets_are_created_partner')
	formData.append('relno',relno)
    for (var pair of formData.entries()) {
      //console.log(pair[0] + " - " + pair[1]);
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
            //wallet_txn
            var data = JSON.parse(result);
            console.log('data is ',data );
            if(data.error=='false'){
                 if(data.process_status ==='C'){
                    functionCallCounter =12;
                    $("#div_please_wait_circle").hide();
                    $("#div_keys").show();
                   
                    $('#idstlpublickkey').val(data.public_key);
                    $('#idstlprivatekey').val(data.private_key);
                 }
            }else{
               
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

function createDigitalAssets(){
	var formData = new FormData();
	formData.append('qs','porte')
	formData.append('rules','partner_add_stellar_new_account')
	formData.append('json_string',data)
	formData.append('relno',relno)
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
            //wallet_txn
           /* var data = JSON.parse(result);
            console.log('data is ',data );
            if(data.error=='false'){
                 
            }else{
               
            }*/
        },
        error: function() {
           
        }
    });       
    checkDigitalAssetIfAreCreated();
}

setInterval(function(){
    if(functionCallCounter<12){
        checkDigitalAssetIfAreCreated()
    }
}, 5000);

var copyBtn = document.querySelector('#btncopy');
copyBtn.addEventListener('click', function () {
	console.log("Inside copy")
	$("#allinfo").removeClass("hidden");
	$("#allinfo").val("Bitcoin Adress: "+$("#idbitcoinaddress").val() + "\r\n Bitcoin Public Key: " + $("#idbtcpublickkey").val() +
	 "\r\n Bitcoin Private Key: " + $("#idbtcprivatekey").val()+"\r\n Stellar Public Key: "+$("#idstlpublickkey").val()+"\r\n Stellar Private Key: "+$("#idstlprivatekey").val()).val();
	var infoField = document.querySelector('#allinfo');
	console.log("getting here222")
	infoField .select();
	document.execCommand('copy');
	$("#allinfo").addClass("hidden");
		Swal.fire({
                        icon: 'success',
                        title: 'Copying Stellar and Bitcoin Keys',
                        text: 'You have successfully copied Stellar and Bitcoin Keys, Please Paste and Store in a safe place',
                        showConfirmButton: true,
                        confirmButtonText: "Ok",
						timer: 5000
                        }).then(function() {
                           $('#get-page-form').attr('action', 'ws');
	                        $('input[name="qs"]').val('lgt');
	                        $('input[name="rules"]').val('opslgtdefault');
	                        $("#get-page-form").submit();  
                });
	}, false);



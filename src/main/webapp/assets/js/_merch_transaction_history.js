$(window).on("load", function(e) {
    $("#global-loader").fadeOut("slow");
})

function fnGetTransaction(){
    $('#filter-txn-form input[name="qs"]').val('merchtxn');
    $('#filter-txn-form input[name="rules"]').val('get_merchant_txns');
    
    var formData = new FormData($('#filter-txn-form')[0]);
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
            
            var data = JSON.parse(result);
            console.log('data ',data);
            if(data.error=='false'){
                
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
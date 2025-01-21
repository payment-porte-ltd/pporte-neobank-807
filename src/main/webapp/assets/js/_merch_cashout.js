$(window).on("load", function(e) {
    $("#global-loader").fadeOut("slow");
})


function fnGenerateDynamicQRCode(){
    var amount = $("#amount").val();
    if (amount==""){
        swal.fire({
             text:'Please Enter Amount',
             type:'info',
             icon:'info',
         })
         
     }else{
     var dynamicQRUrl = 'ws?qs=merchqrcode&rules=cashout_dynamic_qr&amount='+amount;

      document.getElementById("dynamicdisplayimage").src = dynamicQRUrl;
            
     }
}
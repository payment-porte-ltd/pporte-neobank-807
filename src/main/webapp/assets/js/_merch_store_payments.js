$(window).on("load", function(e) {
    $("#global-loader").fadeOut("slow");
})

function fnGenerateStaticQRCode(){
    var staticQRCodeUrl = 'ws?qs=merchqrcode&rules=storepayment_static_qr';
     document.getElementById("staticdisplayimage").src = staticQRCodeUrl;
}



function fnGenerateDynamicQRCode(){
    var retailpayamount = $("#retailamount").val();
    if (retailpayamount==""){
        swal.fire({
             text:'Please Enter Amount',
             type:'info',
             icon:'info',
         })
         
     }else{
     var dynamicQRUrl = 'ws?qs=merchqrcode&rules=storepayment_dynamic_qr&amount='+retailpayamount;

      document.getElementById("dynamicdisplayimage").src = dynamicQRUrl;
            
     }
}

function printimagestatic(){
	var pdf = new jsPDF();
	var img = new Image;
	img.onload = function() {
        pdf.setFontSize(40);	
        pdf.text(35, 25, "Merchant Static QR");
        pdf.addImage(this, 15, 40, 180, 180);
        pdf.save("merchant Static QR.pdf");
	};
	img.crossOrigin = "";
	img.src = 'ws?qs=merchqrcode&rules=storepayment_static_qr';
	}

    
 
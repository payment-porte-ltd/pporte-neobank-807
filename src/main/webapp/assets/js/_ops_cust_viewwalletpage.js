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
		
		 function fnViewTransactions(walletid){
			//TODO- Update this Function to show transactions of the specific wallet
			
		// use Ajax to get last x transactions of the wallet and display in a data table.
		
		$('#walltxn-form input[name="qs"]').val('opscust');
	    $('#walltxn-form input[name="rules"]').val('opsgetcustwallettxns');
		$('input[name="hdnwalletid"]').val(walletid);
		
		console.log('inside fnViewTransactions '+ $('#walltxn-form input[name="rules"]').val() )
	
			  var formData = new FormData($('#walltxn-form')[0]);
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
					  //alert('result is '+result);
					  var data = JSON.parse(result);
					  if(data.error=='false'){
						  //console.log('result is ok'.data )
				          
						$('#divtxnwalletview').show();
					    $("#divtabletransactions").html("");
						 var tableHTML = '';

							alert('Transacrtions present')
						/* tableHTML += '<table id="table_transactions" class="table table-striped table-bordered" cellspacing="0" width="100%">';
						 tableHTML += '<thead><tr><th>TxnCode</th><th>UserTxnCode</th><th>Date and Time</th><th>Reference</th><th>Amount</th><th>Debit/Credit</th></tr></thead>'
						 tableHTML += '<tbody>'

						 if (result['error'] == 'false') {

							 $('#divtabletransactions').html('');
							 for (var i = 0; i < result.txncode.length; i++) {
								 tableHTML += '<tr><td>' + result.txncode[i] + '</td><td>' + result.txnusercode[i] + '</td><td>' + result.txndatetime[i] + '</td><td>' + result.txnsysref[i] + '</td><td>' + result.txnamount[i] + '</td><td>' + result.txnmode[i] + '</td></tr>'

							 }

						 } else if (result['error'] == 'true') {
							 //swal('No Transactions present for the wallet ');
							 tableHTML += '<tr><td colspan="5">No Wallet Transactions present for wallet : ' + walletid + '</td></tr>';

						 }
						 tableHTML += '</tbody></table>';

						 $('#divtabletransactions').append(tableHTML);
						 $('#divtxnwalletview').removeClass('hidden');
						 $('#table_transactions').DataTable();*/
						
						  }else{
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
		
		
		
		
		
		
		
		
		
		
		
		 /*    var qsjson='opscust';
			 var rulesjson = 'opsgetlasthundredtxnsforwallet';
			 var urljson = 'json';
			 var apipvt = getAPIKey();


			 //console.log($(this).val() + ' is now checked');
			 var jvariables = JSON.stringify({ qs: qsjson, rules: rulesjson, apikey: apipvt, walletid: walletid });
			 console.log(jvariables);
			 $.ajax({
				 url: urljson, type: "POST", dataType: "json", data: { objarray: jvariables },
				 success: function(result) {
					 if (result) {
						 
						 $("#divtabletransactions").html("");
						 var tableHTML = '';

						 tableHTML += '<table id="table_transactions" class="table table-striped table-bordered" cellspacing="0" width="100%">';
						 tableHTML += '<thead><tr><th>TxnCode</th><th>UserTxnCode</th><th>Date and Time</th><th>Reference</th><th>Amount</th><th>Debit/Credit</th></tr></thead>'
						 tableHTML += '<tbody>'

						 if (result['error'] == 'false') {

							 $('#divtabletransactions').html('');
							 for (var i = 0; i < result.txncode.length; i++) {
								 tableHTML += '<tr><td>' + result.txncode[i] + '</td><td>' + result.txnusercode[i] + '</td><td>' + result.txndatetime[i] + '</td><td>' + result.txnsysref[i] + '</td><td>' + result.txnamount[i] + '</td><td>' + result.txnmode[i] + '</td></tr>'

							 }

						 } else if (result['error'] == 'true') {
							 //swal('No Transactions present for the wallet ');
							 tableHTML += '<tr><td colspan="5">No Wallet Transactions present for wallet : ' + walletid + '</td></tr>';

						 }
						 tableHTML += '</tbody></table>';

						 $('#divtabletransactions').append(tableHTML);
						 $('#divtxnwalletview').removeClass('hidden');
						 $('#table_transactions').DataTable();


					 }
				 }


			 });*/

	}
		
		
		
		

		





 
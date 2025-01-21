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
			"idnavmenu_Dashboard" : "Dashboard",
			"idnavmenu_Profile" : "Profile",
			"idnavsubmenu_ViewProfile" : "View Profile",
			"idnavsubmenu_OtherFunction" : "Other Function",
			"idnavmenu_Documents" : "Documents",
			"idnavsubmenu_ViewDocuments" : "View Documents",
			"idnavsubmenu_UploadDocuments" : "Upload Documents",
			"idnavmenu_Operations" : "Operations",
			"idnavsubmenu_ViewUsers" : "View Users",
			"idnavsubmenu_NewUsers" : "New Users",
			"idnavsubmenu_ViewRecords" : "View Records",
			"idnavsubmenu_ManageAsset" : "Manage Asset",
			"idnavmenu_Logout" : "Logout",
			/* Nav Pages end */
			/* start Page specific changes */
			"dashboard-doc-title1" : "Documents",
			"dashboard-doc-title2" : "Overview",
			"dashboard-doc-label" : "Total Documents",
			"dashboard-doc-user-title1" : "Users",
			"dashboard-doc-user-title2" : "Overview",
			"dashboard-doc-user-totalusers" : "Total Users",
			"dashboard-doc-user-request-label1" : "Requests",
			"dashboard-doc-user-request-label2" : "Overview",
			"dashboard-doc-user-totalrequest" : "Total Request",
			"dashboard-doc-chart-title" : "Document Upload last 12 months",
			"dashboard-doc-chart-subtitle" : "Overview of this year live charts",
			"dashboard-doc-button-viewmore" : "View more"
			/* start Page specific changes */
          }
        },
        es: {
			/* Nav Pages start */
            translation: {
            "idnav_UserSettings" : "Ajustes de usuario", 
			"idnav_UpdateProfile" : "Actualizaci�n del perfil",
			"idnav_Logout" : "Cerrar sesi�n",
			"idnavmenu_Dashboard" : "Tablero",
			"idnavmenu_Profile" : "Perfil",
			"idnavsubmenu_ViewProfile" : "Ver perfil",
			"idnavsubmenu_OtherFunction" : "Otra funci�n",
			"idnavmenu_Documents" : "Documentos",
			"idnavsubmenu_ViewDocuments" : "Ver documentos",
			"idnavsubmenu_UploadDocuments" : "Subir Documentos",
			"idnavmenu_Operations" : "Operaciones",
			"idnavsubmenu_ViewUsers" : "Ver Usuarios",
			"idnavsubmenu_NewUsers" : "Nuevos usuarios",
			"idnavsubmenu_ViewRecords" : "Ver Registros",
			"idnavsubmenu_ManageAsset" : "Administrar Activos",
			"idnavmenu_Logout" : "Cerrar sesi�n",
			/* Nav Pages end */
			/* start Page specific changes */
			"dashboard-doc-title1" : "Documentos",
			"dashboard-doc-title2" : "Visi�n de conjunto",
			"dashboard-doc-label" : "Documentos totales",
			"dashboard-doc-user-title1" : "Usuarios",
			"dashboard-doc-user-title2" : "Visi�n de conjunto",
			"dashboard-doc-user-totalusers" : "Total de usuarios",
			"dashboard-doc-user-request-label1" : "Peticiones",
			"dashboard-doc-user-request-label2" : "Visi�n de conjunto",
			"dashboard-doc-user-totalrequest" : "Solicitud total",
			"dashboard-doc-chart-title" : "Carga de documentos en los �ltimos 12 meses",
			"dashboard-doc-chart-subtitle" : "Resumen de las listas en vivo de este a�o",
			"dashboard-doc-button-viewmore" : "Ver m�s"
			
			
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
			$('#idnavmenu_Dashboard').text(i18next.t('idnavmenu_Dashboard'));
			$('#idnavmenu_Profile').text(i18next.t('idnavmenu_Profile'));
 			$('#idnavsubmenu_ViewProfile').text(i18next.t('idnavsubmenu_ViewProfile'));
  			$('#idnavsubmenu_OtherFunction').text(i18next.t('idnavsubmenu_OtherFunction'));
  			$('#idnavmenu_Documents').text(i18next.t('idnavmenu_Documents'));
			$('#idnavsubmenu_ViewDocuments').text(i18next.t('idnavsubmenu_ViewDocuments'));
			$('#idnavsubmenu_UploadDocuments').text(i18next.t('idnavsubmenu_UploadDocuments'));
			$('#idnavmenu_Operations').text(i18next.t('idnavmenu_Operations'));
			$('#idnavsubmenu_ViewUsers').text(i18next.t('idnavsubmenu_ViewUsers'));
			$('#idnavsubmenu_NewUsers').text(i18next.t('idnavsubmenu_NewUsers'));
			$('#idnavsubmenu_ViewRecords').text(i18next.t('idnavsubmenu_ViewRecords'));
			$('#idnavsubmenu_ManageAsset').text(i18next.t('idnavsubmenu_ManageAsset'));
			$('#idnavmenu_Logout').text(i18next.t('idnavmenu_Logout'));
			/* Nav Pages end */
			/* start Page specific changes */
			$('#dashboard-doc-title1').text(i18next.t('dashboard-doc-title1')); 
			$('#dashboard-doc-title2').text(i18next.t('dashboard-doc-title2'));
			$('#dashboard-doc-label').text(i18next.t('dashboard-doc-label')); 
			$('#dashboard-doc-user-title1').text(i18next.t('dashboard-doc-user-title1'));
			$('#dashboard-doc-user-title2').text(i18next.t('dashboard-doc-user-title2')); 
			$('#dashboard-doc-user-totalusers').text(i18next.t('dashboard-doc-user-totalusers'));
			$('#dashboard-doc-user-request-label1').text(i18next.t('dashboard-doc-user-request-label1')); 
			$('#dashboard-doc-user-request-label2').text(i18next.t('dashboard-doc-user-request-label2'));
			$('#dashboard-doc-user-totalrequest').text(i18next.t('dashboard-doc-user-totalrequest')); 
			$('#dashboard-doc-chart-title').text(i18next.t('dashboard-doc-chart-title'));
			$('#dashboard-doc-chart-subtitle').text(i18next.t('dashboard-doc-chart-subtitle')); 
			$('#dashboard-doc-button-viewmore').text(i18next.t('dashboard-doc-button-viewmore'));
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
		function fnEditCustomer(customercode){
			//alert("clicked fnEditCustomer");
			console.log('customercode is ',customercode)
			$('#quick-link-form').attr('action', fnGetOpsServletPath());
		    $('input[name="qs"]').val('dash');
		    $('input[name="rules"]').val('opsshowspecificcustomer');
			$('input[name="hdncustomercode"]').val(customercode);
			$("#quick-link-form").submit(); 
		
		}
		function fnShowRequestDetail(mod1){
				//alert("clicked fnShowRequestDetail");
			    $('#quick-link-form').attr('action', fnGetOpsServletPath());
			    $('input[name="qs"]').val('opsdspt');
			    $('input[name="rules"]').val('ops_view_specific_cust_dispute');
			    $('input[name="hdnreqid"]').val(mod1);
			    $("#quick-link-form").submit();
		    }

function fnGetBusinessLegderTxn() {
	console.log("Inside function fnGetBusinessLegderTxn")
		$('#get-txn-form input[name="qs"]').val('dash');
		$('#get-txn-form input[name="rules"]').val('ops_businessledger_graph');
	
		var formData = new FormData($('#get-txn-form')[0]);
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
			success: function(result) {
				//wallet_txn
				var data = JSON.parse(result);
				var transactionList = data.data;
				$('#cust-disputes').html('');
				$('#card_disp_header').html('');
				$('#card_disp_header').html('Monthly Transactions')
				if (data.error == 'false') {
					if(transactionList !=null){
						if(transactionList.length >0){	
							var temparray = [];
							for(i=0; i< transactionList.length; i++){
								temparray = transactionList[i].split(',');
			            		xaxis[i] =  temparray[0];//month
			            		yaxis[i] = temparray[1];//amount
							 }
						
						    console.log(xaxis);
			            	console.log(yaxis);
 
							$("#transactions_present").show();
							$("#transactions_not_present").hide();
			            	LaodBusinesslMonthlyCharjs(xaxis,yaxis)
						 
						}else{
							$("#transactions_present").hide();
							$("#transactions_not_present").show();
						 console.log(' no transactions present')
						}
					}else{
						 console.log(' no transactions available')
					}
					
	
				}else if (data.error == 'nodata') {
					$("#transactions_present").hide();
					$("#transactions_not_present").show();
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
function LaodBusinesslMonthlyCharjs(xaxis,yaxis){
	/*Chartjs*/
	console.log("Inside LaodMonthlyCharjs")
	var ctx = document.getElementById("businessledgerchart").getContext('2d');
	ctx.height = 100;
	var myChart = new Chart(ctx, {
		type: 'line',
		data: {
			//labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul","Aug","Sep","Nov","Dec"],
			labels: xaxis,
			datasets: [{
				label: 'Earnings',
				//data: [20, 420, 210, 354, 580, 320, 480,210, 354, 580, 320, 480],
				data: yaxis,
				borderWidth: 2,
				backgroundColor: 'rgba(216, 211, 248,0.15)',
				borderColor: '#1753fc',
				borderWidth: 4,
				pointBackgroundColor: '#ffffff',
				pointRadius: 4
			}]
		},
		options: {
			responsive: true,
			maintainAspectRatio: false,

			scales: {
				xAxes: [{
					ticks: {
						fontColor: "#bbc1ca",
						reverse: true,
					 },
					display: true,
					gridLines: {
						color: 'rgba(0,0,0,0.03)'
					}
				}],
				yAxes: [{
					ticks: {
						fontColor: "#bbc1ca",
					 },
					display: true,
					gridLines: {
						color: 'rgba(0,0,0,0.03)'
					},
					scaleLabel: {
						display: false,
						labelString: 'Thousands',
						fontColor: 'rgba(0,0,0,0.03)'
					}
				}]
			},
			legend: {
				labels: {
					fontColor: "#bbc1ca"
				},
			},
		}
	});

	/*Chartjs*/
	
}
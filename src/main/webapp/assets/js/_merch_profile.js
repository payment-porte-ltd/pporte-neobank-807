function fnGetMerchKycDocs(){
	$('#get-kyc-form input[name="qs"]').val('merchprf');
	$('#get-kyc-form input[name="rules"]').val('getmerchkycdocs');
	var form = $('#get-kyc-form')[0];
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
				console.log("data is "+data.data)
				console.log("data is "+data.error)
				//var bsnDocumentList =data.data;
				$("#document_holder").html("");
				var htmlDocs = '';
				if(data.error="false"){
					 htmlDocs = '<div class="card shadow" onclick="javascript:fnDownloadDocument(\''+data.data+'\')"><strong>Business Document </strong><a><img alt="Qries" src="assets/images/photos/network-documents.png"width=150" height="70"><a href="#">Click here To Download</a></a></div>';
				}else{
					htmlDocs='<div>NO documents available</div>';
				}
				$('#document_holder').append(htmlDocs);
			}
			},
		error: function() {
			
		}
	}); 
}

function fnDownloadDocument(path){
	$('#download-form').attr('action', 'ws');
	$('input[name="qs"]').val('doc');
	$('input[name="rules"]').val('assetdownload');
	$('input[name="hdnassetpath"]').val(path);
	$("#download-form").submit();
}

function getMCCDataDetails(){
	$('#get-mcc-form input[name="qs"]').val('merchprf');
	$('#get-mcc-form input[name="rules"]').val('getmerchmccdata');
	var form = $('#get-mcc-form')[0];
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
				console.log("data is "+data)
				console.log("data is "+data.error)
				$("#td_mcc_name").html("");
				var htmlTableData = '';
				if(data.error="false"){
					 htmlTableData = '<strong>Merchant category :</strong> \''+data.data+'\'';
				}else{
					htmlTableData='<div>""</div>';
				}
				$('#td_mcc_name').append(htmlTableData);
			}
			},
		error: function() {
			
		}
	}); 
}
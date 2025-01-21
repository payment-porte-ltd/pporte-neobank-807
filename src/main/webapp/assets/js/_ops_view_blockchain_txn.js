$(window).on("load", function(e) {
    $("#global-loader").fadeOut("slow");
})
	var custrecordinfo='';
	var countvalue=10;
	var start=1;
	var end=countvalue;
	var total="";
	var totalcustrecordinfo="";
	var custbuttonvalue=10;
	var buttonvalue="";
function fnGetBlockchainTxn(){
    $('#get-txn-form input[name="qs"]').val('blockchain');
    $('#get-txn-form input[name="rules"]').val('opsgetblockchaininfo');
    
    var formData = new FormData($('#get-txn-form')[0]);
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
			console.log('data ',data)
                custrecordinfo=data.data;
				totalcustrecordinfo=data.data;
				console.log('custrecordinfo ',custrecordinfo)
				console.log('totalcustrecordinfo ',totalcustrecordinfo)
				$("#custrecord").html(custrecordinfo);
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
function fnShowBlockchainData(value, usertype) {
	console.log("value",value);
	console.log("usertype",usertype);
	
	$('#get-txn-form input[name="qs"]').val('blockchain');
	$('#get-txn-form input[name="rules"]').val('opsblockchaintxn');
	
	if (value === "Next") {
		if (usertype === "C") {
			start = start + countvalue;
			end = end + countvalue;
			custbuttonvalue = custbuttonvalue + countvalue;
			custrecordinfo = parseFloat(custrecordinfo) - countvalue;
			if (custrecordinfo < countvalue) {
				end = total;
			}
		}
	}
	if (value === "Previous") {
		countvalue=10;
		if (usertype === "C") {
			start = start - countvalue;
			end = end - countvalue;
			custbuttonvalue = custbuttonvalue - countvalue;
			custrecordinfo = parseFloat(custrecordinfo) + countvalue;
			if (custrecordinfo < countvalue) {
				end = total;
			}
			$("#custpreviousbtn").addClass("hidden");
		}
	}
		if (usertype === "C") {
			if (custrecordinfo < countvalue) {
				countvalue = custrecordinfo;
				buttonvalue = custrecordinfo;
			}
			buttonvalue = custbuttonvalue;
			total = totalcustrecordinfo;
			$("#usertype").html("Customer");

		}
		
		if (end >= total){
				end=total;
			}
		$("#total").html(total);
		$("#start").html(start);
		$("#end").html(end);
		console.log("buttonvalue" + buttonvalue);
		console.log("total" + total);
		console.log("start" + start);
		console.log("end" + end);
		
		if (start == 1) {
			
		} else {
			$("#custpreviousbtn").show();
		}
		if (end == total) {
			$("#custpreviousbtn").show();
		} else {
			$("#custnextbtn").show();
		}
		
		var formData = new FormData($('#get-txn-form')[0]);
		formData.append('buttonvalue', buttonvalue);
		formData.append('countvalue', countvalue);
		formData.append('usertype', usertype);
		for (var pair of formData.entries()) {
			console.log(pair[0] + " - " + pair[1]);
		}
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
				var htmlTable = '';
				var txnList = data.data;
				$('#blockchain_txn').html('');
				$('#card_txn_header').html('');
				$('#card_txn_header').html('Blockchain Transactions')
				if (data.error == 'false') {
					if (txnList != null) {
						if (txnList.length > 0) {
							htmlTable += `<table id="blockchaintable" class="table table-striped table-bordered text-nowrap w-100">
	                <thead>
	                    <tr>
	                        <th class="wd-15p">Transaction Date</th>
							<th class="wd-15p">Transacode Code</th>
	                        <th class="wd-15p">System Reference</th>
	                        <th class="wd-20p">Transaction Mode</th>
							<th class="wd-20p">Currency</th>
	                        <th class="wd-15p">Amount</th>
	                     </tr>
	                </thead>
	                <tbody>`
							for (i = 0; i < txnList.length; i++) {
								console.log('Data transaction code is ',txnList[i].txnCode)
								htmlTable += `<tr>
										<td>`+ txnList[i].txnDateTime+ `</td>
                                        <td>`+ txnList[i].txnCode + `</td>
                                        <td>`+ txnList[i].systemReference + `</td>
                                        <td>`+ txnList[i].txnMode+ `</td>
                                        <td>`+ txnList[i].txnCurrencyId+ `</td>
										<td>`+ txnList[i].txnAmount+ `</td>
	                                    </tr>`;
							}
						}else{
							htmlTable = `<tr><td> <span>No data Present</span></td></tr>`
						}
						htmlTable += `</tbody></table>`;
						$('#blockchain_txn').append(htmlTable);
						$('#blockchaintable').dataTable({
						    "paging": false,
						    "info": false,
							"lengthChange": false,
							"bSort" : false
						});
						$("#displaytxn").removeClass("hidden");
						$(".customer-records").addClass("hidden");
					}else{
						Swal.fire({
						icon: 'error',
						title: 'Oops',
						text: 'No Transactions Available',
						showConfirmButton: true,
						confirmButtonText: "Ok",
						closeOnConfirm: true,
					})
					}
					
				}else{
					Swal.fire({
						icon: 'error',
						title: 'Oops',
						text: 'Failed to get Transactions',
						showConfirmButton: true,
						confirmButtonText: "Ok",
						closeOnConfirm: true,
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

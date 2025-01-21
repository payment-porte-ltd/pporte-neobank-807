$(window).on("load", function(e) {
    $("#global-loader").fadeOut("slow");
})

function fnGetCardWalletTxn(){
    $('#get-txn-form input[name="qs"]').val('wal');
    $('#get-txn-form input[name="rules"]').val('get_card_wallet_txn');
    
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
            //wallet_txn
            var data = JSON.parse(result);
            var htmlTable ='';
            var txnList = data.data;
            $('#wallet_txn').html('');
            $('#card_txn_header').html('');
            console.log('data ',data);
            $('#card_txn_header').html('Card Wallet Transactions')
            if(data.error=='false'){
                console.log('length is ', txnList.length)
                if(txnList.length>0){
                    htmlTable+=`<table id="txn_589_example" class="table table-striped table-bordered text-nowrap w-100">
                <thead>
                    <tr>
                        <th class="wd-15p">Date	</th>
						<th class="wd-15p">Customer Name</th>
                        <th class="wd-15p">Transaction Code</th>
                        <th class="wd-20p">Description</th>
                        <th class="wd-15p">Amount</th>
                     </tr>
                </thead>
                <tbody>`
                    for (i=0; i<txnList.length;i++){
                        htmlTable+= `<tr>
										<td>`+txnList[i].txnDateTime+`</td>
                                        <td>`+txnList[i].customerName+`</td>
                                        <td>`+txnList[i].txnUserCode+`</td>
                                        <td>`+txnList[i].txnDescription+`</td>
                                        <td>`+txnList[i].txnAmount+`</td>
                                    </tr>`;
                    }
                }else{
                    htmlTable=`<tr><td> <span>No data Present</span></td></tr>`
                }
                htmlTable+=`</tbody></table>`;
                $('#div_table').append(htmlTable);
                $('#txn_589_example').dataTable({ "bSort" : false } );
            }else if(data.error==="nodata"){
					htmlTable=`<tr><td> <span class="text-danger">No data Present</span></td></tr>`;
					$('#div_table').append(htmlTable);
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
function fnGetFilteredTxn(){
   $( "#filter-txn-form" ).validate( {
        rules: {
            datefrom: {
                required: true
            },
            dateto: {
                required: true
            }
        },
        messages: {
            datefrom: {
                required: 'Please enter date from'
            },
            dateto: {
                required: 'Please enter date to'
               
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
    });
    if($( "#filter-txn-form" ).valid()){
        $('#filter-txn-form input[name="qs"]').val('wal');
        $('#filter-txn-form input[name="rules"]').val('get_card_txn_btn_dates');
        
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
                var htmlTable ='';
                var txnList = data.data;
                $('#div_table').html('');
                $('#card_txn_header').html('');
                $('#card_txn_header').html('Filtered Transactions')
               // console.log('length is ', txnList.length)
                if(data.error=='false'){
                    htmlTable+=`<table id="txn_example" class="table table-striped table-bordered text-nowrap w-100">
                        <thead>
                            <tr>
		                        <th class="wd-15p">Date	</th>
								<th class="wd-15p">Customer Name</th>
		                        <th class="wd-15p">Transaction Code</th>
		                        <th class="wd-20p">Description</th>
		                        <th class="wd-15p">Amount</th>
                             </tr>
                        </thead>
                        <tbody>`
                if(txnList.length>0){
                    for (i=0; i<txnList.length;i++){
                        htmlTable+= `<tr>
										<td>`+txnList[i].txnDateTime+`</td>
                                        <td>`+txnList[i].customerName+`</td>
                                        <td>`+txnList[i].txnUserCode+`</td>
                                        <td>`+txnList[i].txnDescription+`</td>
                                        <td>`+txnList[i].txnAmount+`</td>
                                    </tr>`;
                    }
                }else{
                    htmlTable=`<tr><td> <span>No data Present</span></td></tr>`
                }
                htmlTable+=`</tbody>
                </table>`;
                $('#div_table').append(htmlTable);
                $('#txn_example').dataTable({ "bSort" : false } );    
                }else if(data.error=='nodata'){
                    htmlTable=`<tr><td> <span class="text-danger">No data Present</span></td></tr>`;
					$('#div_table').append(htmlTable);
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
    
}
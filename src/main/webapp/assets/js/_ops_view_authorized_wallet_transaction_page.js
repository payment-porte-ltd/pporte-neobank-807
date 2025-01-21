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
        $('#filter-txn-form input[name="rules"]').val('get_auth_txn_btn_dates');
        
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
               
                if(data.error=='false'){
					 console.log('length is ', txnList.length)
                    htmlTable+=`<table id="txn_example" class="table table-striped table-bordered text-nowrap w-100">
                        <thead>
                            <tr>
								<th>Date and Time</th>
                               	<th>Customer Id</th>
								<th>Wallet Id</th>
								<th>Currency</th>
								<th>Amount</th>
								<th>System Reference</th>
								<th>Auth Status</th>
								<th>Reason</th>								
                             </tr>
                        </thead>
                        <tbody>`;
                if(txnList.length>0){
                    for (i=0; i<txnList.length;i++){
                        htmlTable+= `<tr>
										<td>`+txnList[i].txnDateTime+`</td>
                                        <td>`+txnList[i].customerId+`</td>
                                        <td>`+txnList[i].customerWalletId+`</td>
                                        <td>`+txnList[i].txnCurrencyId+`</td>
                                        <td>`+txnList[i].txnAmount+`</td>
                                        <td>`+txnList[i].systemReferenceInt+`</td>`;
											if (txnList[i].status==="F"){
												htmlTable+= `<td>Fail</td>`;
											}else if (txnList[i].status==="S"){
												htmlTable+= `<td>Success</td>`;		
											}
                                     htmlTable+= `   
                                        <td>`+txnList[i].comment+`</td>
                                    </tr>`;
                    }
                }else{
                    htmlTable=`<tr><td> <span>No data Present</span></td></tr>`
                }
                htmlTable+=`</tbody>
                </table>`;
                $('#div_table').append(htmlTable);
                fnCallDatatable();  
                }else if (data.error==='nodata'){
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
$(function (){
	fnCallDatatable();
});

function fnCallDatatable(){
		 $('#txn_example').DataTable( {
				 "ordering": false,
        responsive: {
            details: {
                display: $.fn.dataTable.Responsive.display.modal( {
                    header: function ( row ) {
                        var data = row.data();
                        return 'Details for '+data[0]+' '+data[1];
                    }
                } ),
                renderer: $.fn.dataTable.Responsive.renderer.tableAll( {
                    tableClass: 'table'
                } )
            }
        }
    } );
}
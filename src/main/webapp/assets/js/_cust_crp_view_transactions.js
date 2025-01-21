
	$(document).ready(function() {
		$('#custtxnstable').dataTable({
			"order": []
		});
	});
	function dataTableFun(){
		$('#custtxnstable2').dataTable({
			"order": []
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
        $('#filter-txn-form input[name="qs"]').val('cryp');
        $('#filter-txn-form input[name="rules"]').val('get_ext_txn_btn_dates');
        
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
				$('#masterdiv div').html('');

                var txnList = data.data;
                //$('#wallet_txn').html('');
				$('#custtxn').html('');

                $('#card_txn_header').html('');
                $('#card_txn_header').html('Filtered Transactions')
                if(data.error=='false'){
                if(txnList.length>0){
 			
				htmlTable+=`
				    <div class="table-responsive">
							<table id="custtxnstable2" class="table table-striped table-bordered text-nowrap w-100">
								<thead>
									<tr>
										<th>Date</th>
										<th>Transaction Code</th>
										<th>Description</th>
										<th>Amount</th>
									</tr>
								</thead>
								<tbody id="wallet_txn"> `;
						  for (i=0; i<txnList.length;i++){
			                        htmlTable+= `<tr>
			                                        <td>`+txnList[i].txnDateTime+`</td>
			                                        <td>`+txnList[i].txnUserCode+`</td>
			                                        <td>`+txnList[i].txnDescription+`</td>
			                                        <td>`+txnList[i].txnAmount+`</td>
			                                    </tr>`;
			                    }
			
					htmlTable+=`
							  </tbody>
							</table> 
						</div>`;
                  
                }else{
                    htmlTable=`<tr><td> <span>No data Present</span></td></tr>`
                }                    
                }else{
                    htmlTable=`<tr><td> <span>No data Present</span></td></tr>`
                }
 				$('#custtxnstableappend').hide();
                $('#custtxn').append(htmlTable);
				 dataTableFun();

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
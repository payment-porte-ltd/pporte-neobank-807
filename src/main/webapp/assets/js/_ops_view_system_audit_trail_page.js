$(window).on("load", function(e) {
    $("#global-loader").fadeOut("slow");
	
})

var datefrom="";
function fnGetAuditTrailDetails(){

	
	console.log("Getting here")
    $('#get-txn-form input[name="qs"]').val('wal');
    $('#get-txn-form input[name="rules"]').val('get_audit_trail_details');
    
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
            $('#card_txn_header').html('Audit Trail Logs')
            if(data.error=='false'){
                console.log('length is ', txnList.length)
                if(txnList.length>0){
                    htmlTable+=`<table id="example3" class="table table-striped table-bordered text-nowrap">
                <thead>
                    <tr>
						<th class="wd-15p">Trail Time</th>
                        <th class="wd-15p">Trail Id	</th>
						<th class="wd-15p">User Id</th>
                        <th class="wd-15p">User Type</th>
                        <th class="wd-15p">Comment</th>
						
                     </tr>
                </thead>
                <tbody>`
                    for (i=0; i<txnList.length;i++){
                        htmlTable+= `<tr>
										<td>`+txnList[i].trailTime+`</td>
										<td>`+txnList[i].traiId+`</td>
                                        <td>`+txnList[i].userId+`</td>
                                        <td>`+txnList[i].userType+`</td>
                                        <td>`+txnList[i].comment+`</td>
										
                                    </tr>`;
	
                    }
					htmlTable+=`</tbody></table>`;
                }else{
                    htmlTable=`<span class="text-danger">Transaction data not found</span>`;
                }
                
                $('#div_table').append(htmlTable);
                $('#example3').dataTable({ "bSort" : false } );
            }else if(data.error==='nodata'){
					console.log("getting here")
                    htmlTable=`<span id="no_data_present" class="text-danger">No data Present</span>`
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

 $("#datefrom").on("change",function(){
        datefrom = $(this).val();
		fnDatePicker(datefrom)
    });
	
function fnDatePicker(datefrom){
	$("#dateto").on("change",function(){
	 dateto = $(this).val();
		const oneDay = 24 * 60 * 60 * 1000;
		const dateFrom = new Date(datefrom);
		const dateTo = new Date(dateto);
		const diffDays = Math.round(Math.abs((dateFrom - dateTo) / oneDay));
	if (diffDays>8) {
				  Swal.fire({
					  icon: 'error',
					  title: 'Oops',
					  text: 'Search is limited to 7 days only',
					  showConfirmButton: true,
					  confirmButtonText: "Ok",
					  })
			return;
				}
	})
	}
function fnGetFilteredTxn(){
		
	fnDatePicker();
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
        $('#filter-txn-form input[name="rules"]').val('get_audit_trail_btn_dates');
        
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
                    htmlTable+=`<table id="txn_example" class="table table-striped table-bordered text-nowrap w-100">
                        <thead>
                            <tr>
								<th class="wd-15p">Trail Time</th>
                                <th class="wd-15p">Trail Id	</th>
								<th class="wd-15p">User Id</th>
		                        <th class="wd-15p">User Type</th>
		                        <th class="wd-15p">Comment</th>
								
                             </tr>
                        </thead>
                        <tbody>`
                if(txnList.length>0){
                    for (i=0; i<txnList.length;i++){
                        htmlTable+= `<tr>
                                        <td>`+txnList[i].traiId+`</td>
                                        <td>`+txnList[i].userId+`</td>
                                        <td>`+txnList[i].userType+`</td>
                                        <td>`+txnList[i].comment+`</td>
										<td>`+txnList[i].trailTime+`</td>
                                    </tr>`;
                    }
                }else{
                    htmlTable=`<tr><td> <span>No data Present</span></td></tr>`;
                }
                htmlTable+=`</tbody>
                </table>`;
                $('#div_table').append(htmlTable);   
                $('#txn_example').dataTable({ "bSort" : false } );   
                }else if(data.error==='nodata'){
					console.log("getting here")
                    htmlTable=`<span id="no_data_present" class="text-danger">No data Present</span>`
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
}
$(document).ready( function () {
  $('#example3').dataTable( {
  "ordering": false,
"bSortable": false,
	} );
	$('#txn_example').dataTable( {
  "ordering": false,
"bSortable": false,
	} );
} );

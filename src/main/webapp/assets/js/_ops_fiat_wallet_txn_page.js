$(window).on("load", function(e) {
    $("#global-loader").fadeOut("slow");
})

function fnGetSearchType(){
	
	var searchType = $("#selsearchtype option:selected").val();
	//alert(searchType)
	var serachHtml = '';
	$('#searchbycard').html('');
	
	if(searchType=='Customer_Name'){
		serachHtml +=`
				<div class="form-group">
					<label class="form-label" >Search By Customer Name</label>
					 <input type="text" class="form-control" name="searchbycustname" id="searchbycustname" value=" "/>
				</div>`;
	}else if(searchType=='Relationship_Number'){
			serachHtml +=`
				<div class="form-group">
					<label class="form-label" >Search By Relationship Number </label>
					 <input type="text" class="form-control" name="searchbyrelno" id="searchbyrelno" value=" "/>
				</div> `;
		
	}else if(searchType=='Customer_Id'){
			serachHtml +=`
				<div class="form-group">
					<label class="form-label" >Search By Customer Id </label>
					 <input type="text" class="form-control" name="searchbycustid" id="searchbycustid" value=" "/>
				</div> `;
		
	}else if(searchType=='Mobile_Number'){
		serachHtml +=`
				<div class="form-group">
					<label class="form-label" >Search By Mobile Number </label>
					 <input type="text" class="form-control" name="searchmobileno" id="searchmobileno" value=" "/>
				</div> `;	
	}else{
		serachHtml +=`
				<div class="form-group">
					<label class="form-label" >Search Default </label>
					 <input type="text" class="form-control" name="searchby" id="searchby" value=" "/>
				</div> `;
	}
	$('#searchbycard').append(serachHtml);
}

function fnSearchForWallet(){
	
	$("#searchspecificcust-form").validate({
					rules: {
							selsearchtype:{
								required:true,
							},
							searchmobileno:{
								required:true,
							},
							searchbycustid:{
								required:true,
							},
							searchrelnno:{
								required:true,
							},
							searchbycustname:{
								required:true,
							},
						
						},
					messages: {
							
							selsearchtype:{
								required:"Please search type"
							},
							searchmobileno:{
								required:"Please Enter Mobile Number",
							},
							searchbycustid:{
								required:"Please Enter Customer Id",
							},
							searchrelnno:{
								required:"Please Enter Relationship Number",
							},
							searchbycustname:{
								required:"Please Enter Customer Name",
							},
						},
						
						errorElement: "em",
						errorPlacement: function(error, element) {
							// Add the `invalid-feedback` class to the error element
							error.addClass("invalid-feedback");
							if (element.prop("type") === "checkbox") {
								error.insertAfter(element.next("label"));
							} else {
								error.insertAfter(element);
							}
						},
						highlight: function(element, errorClass, validClass) {
							$(element).addClass("is-invalid").removeClass("is-valid");
						},
						unhighlight: function(element, errorClass, validClass) {
							$(element).addClass("is-valid").removeClass("is-invalid");
						}
									
				});
		
			if ($("#searchspecificcust-form").valid()) {
				
						    var formData = new FormData($('#searchspecificcust-form')[0]);
								formData.append("qs","opswal");
								formData.append("rules","search_specific_wallet");

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
									$('#div_table').html('');	
						            $('#card_txn_header').html('');
						            console.log('data ',data);
						            $('#card_txn_header').html('Fiat Wallet Transactions')
						            if(data.error=='false'){
						                
						                if(txnList.length>0){
						                    htmlTable+=`<table id="table_txn" class="table table-striped table-bordered text-nowrap w-100">
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
										$('#table_txn').dataTable({ "bSort" : false } );
						             }else if (data.error==="nodata"){
											   htmlTable+=`<table id="table_txn" class="table table-striped table-bordered text-nowrap w-100">
						                <thead>
						                    <tr>
						                        <th class="wd-15p">Date	</th>
												<th class="wd-15p">Customer Name</th>
						                        <th class="wd-15p">Transaction Code</th>
						                        <th class="wd-20p">Description</th>
						                        <th class="wd-15p">Amount</th>
						                     </tr>
						                </thead>
						                <tbody><tr><td> <span class="text-danger">Customer details not found</span></td></tr></tbody></table>`;
										 $('#div_table').append(htmlTable);
										$('#table_txn').dataTable({ "bSort" : false } );	  
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
				 
				}	else{
					 Swal.fire({
						  icon: 'error',
						  title: 'Oops',
						  text: 'Enter atleast one field',
						  showConfirmButton: true,
						  confirmButtonText: "Ok",
						  }).then(function() {
					  });
				}	
}		
		
		
function fnGetFiatTxn(){
    $('#get-txn-form input[name="qs"]').val('wal');
    $('#get-txn-form input[name="rules"]').val('get_fiat_txn');
    
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
			$('#div_table').html('');	
            $('#card_txn_header').html('');
            $('#card_txn_header').html('Fiat Wallet Transactions')
            if(data.error=='false'){
                
                if(txnList.length>0){
                    htmlTable+=`<table id="table_txn" class="table table-striped table-bordered text-nowrap w-100">
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
              $('#table_txn').dataTable({ "bSort" : false } );  
               
            }else if (data.error==="nodata"){
											   htmlTable+=`<table id="table_txn" class="table table-striped table-bordered text-nowrap w-100">
						                <thead>
						                    <tr>
						                        <th class="wd-15p">Date	</th>
												<th class="wd-15p">Customer Name</th>
						                        <th class="wd-15p">Transaction Code</th>
						                        <th class="wd-20p">Description</th>
						                        <th class="wd-15p">Amount</th>
						                     </tr>
						                </thead>
						                <tbody><tr><td> <span class="text-danger">Transaction not present</span></td></tr></tbody></table>`;
										 $('#div_table').append(htmlTable);
										$('#table_txn').dataTable({ "bSort" : false } );	  
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
        $('#filter-txn-form input[name="rules"]').val('get_fiat_txn_btn_dates');
        
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
                    htmlTable+=`<table id="table_txn" class="table table-striped table-bordered text-nowrap w-100">
                        <thead>
                            <tr>
                                <th class="wd-15p">Date	</th>
                                <th class="wd-15p">Customer Name	</th>
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
               $('#table_txn').dataTable({ "bSort" : false } );
                    
              
          }else if (data.error==="nodata"){
					  htmlTable+=`<table id="table_txn" class="table table-striped table-bordered text-nowrap w-100">
						                <thead>
						                    <tr>
						                        <th class="wd-15p">Date	</th>
												<th class="wd-15p">Customer Name</th>
						                        <th class="wd-15p">Transaction Code</th>
						                        <th class="wd-20p">Description</th>
						                        <th class="wd-15p">Amount</th>
						                     </tr>
						                </thead>
						                <tbody><tr><td> <span class="text-danger">Transactions not present</span></td></tr></tbody></table>`;
										 $('#div_table').append(htmlTable);
										$('#table_txn').dataTable({ "bSort" : false } );	  
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
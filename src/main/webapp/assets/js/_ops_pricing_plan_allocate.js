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
					 <input type="text" class="form-control" name="searchcustid" id="searchcustid" value=" "/>
				</div> `;
		
	}else if(searchType=='Mobile_Number'){
		serachHtml +=`
				<div class="form-group">
					<label class="form-label" >Search By Mobile Number </label>
					 <input type="text" class="form-control" name="searchmobileno" id="searchmobileno" value=" "/>
				</div> `;	
	}
	$('#searchbycard').append(serachHtml);
}
var existingPlanId='';
	function fnAllocatePlan(customerid,planid,planname,planprice,startdate,status,origreason){
		existingPlanId=planid;
		$('#AddPlanModal').on('show.bs.modal', function () {
			$('#addcustid').val(customerid);
			$('#seladdplanid').val(planid);
			$("#planname").val(planname);	
			$('#planstartdate').val(startdate);
			$('#planprice').val(planprice);
			$('#orig_reason').val(origreason);
			if (status==="A"){
				$('#status').val("Active");
			}
			if (status==="I"){
				$('#status').val("Inaactive");
			}
			;
		 });
		$('#AddPlanModal').modal('show');		
		
	}

function fnshowAddReasonForChange(){
	
	$("#reason_div").show();
}

$("button[data-dismiss=modal]").click(function(){
	$(".modal").modal('hide');
});
		
		$('#btn-search-customer').click(function() {
			//alert('Clicked')
					$("#searchspecificcust-form").validate({
					rules: {
							selsearchtype:{
								required:true,
							},
							searchmobileno:{
								required:true,
							},
							searchcustid:{
								required:true,
							},
							searchbyrelno:{
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
							searchcustid:{
								required:"Please Enter Customer Id",
							},
							searchbyrelno:{
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
			
				$('#searchspecificcust-form').attr('action', 'ms');
				$('input[name="qs"]').val('opspln');
				$('input[name="rules"]').val('opspriceplansearchcustomer'); 
				
				var formData = new FormData($('#searchspecificcust-form')[0]);
				 for (var pair of formData.entries()) {
					console.log(pair[0] + " - " + pair[1]);
				  }	  
				  // Call Ajax here and submit the form 							
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
									var htmlData="";
										$('#search_results').html('');

									  var data = JSON.parse(result);
									   if(data.error=='false'){
										var pricingPlan=data.data;
										console.log("pricing plan", pricingPlan);
											htmlData=`
												<div class="row" >
														<div class="col-md-12 col-lg-12">
															<div class="card">
																<div class="card-header">
																	<div class="card-title">Pricing Plans for Customers</div>
																</div>
																<div class="card-body">					
																			<div class="row">
																				<div class="col-md-12 col-lg-12">
																					<div class="card">
																						<div class="card-header">
																							<div id="">Search Results:- </div>
																						</div>
																							<div class="table-responsive">
																								<table class="table card-table table-vcenter text-nowrap table-nowrap" >
																									<thead  class="bg-primary text-white">
																										<tr>
																											<th class="text-white">Customer Id</th>
																											<th class="text-white">Plan Id</th>
																											<th class="text-white">Plan Name</th>
																											<th class="text-white">Price</th>
																											<th class="text-white">Start Date</th>
																											<th class="text-white">Status</th>
																											<th class="text-white">Action</th>
																										</tr>
																									</thead>
																									<tbody>
																										<tr>
																											<th scope="row"><span id="customer_id">`+pricingPlan.customerid+`</span>  </th>
																											<td><span id="plan_id">`+pricingPlan.planId+`</span> </td>
																											<td><span id="plan_name">`+pricingPlan.planName+`</span> </td>
																											<td><span id="plan_price">`+pricingPlan.planPrice+`</span> </td>
																											<td><span id="start_date">`+pricingPlan.startDate+`</span> </td>`;
																											if (pricingPlan.status==="A"){
																												htmlData+=`<td><span id="plan_status">Active</span> </td>`;
																											}else if(pricingPlan.status==="I"){
																												htmlData+=`<td><span id="plan_status">Inactive</span> </td>`;
																											}
																											htmlData+=`
																											<td>
																												<div class="btn-group align-top">
																													<button class="btn btn-sm btn-primary badge" type="button" 
																														onClick="javascript:fnAllocatePlan('`+pricingPlan.customerid+`','`+pricingPlan.planId+`',
																														'`+pricingPlan.planName+`','`+pricingPlan.planPrice+`','`+pricingPlan.startDate+`','`+pricingPlan.status+`','`+pricingPlan.reason+`');return false;"><i class="fa fa-edit"></i>Change Plan</button>
																												</div>																		
																											</td>
																										</tr>															
																									</tbody>
																								</table>
																							</div>
																					</div>														
																				</div>
																			</div>
																		</div>
																</div>
																
															</div>
															<!-- section-wrapper -->
														</div>`;
												$("#search_results").append(htmlData);
														
										   }else if(data.error=='nodata'){
											 Swal.fire({
												 title: 'Search failed',              
												 text: 'Customer Details Not found', 
												 icon: "info",
											      showConfirmButton: true,
											     confirmButtonText: "Ok",
											   }).then(function() {
																								
											   });	
									 }else {
										   //swal.fire('Error message');
												 Swal.fire({
												 title: 'Error',              
												 text: 'Search failed', 
												 icon: "error",
												 footer: '<a href="#">'+$('#ops-manage-functions-error-swal-contact-admin').text()+'</a>',
											   showConfirmButton: true,
											   confirmButtonText: "Ok",
											   }).then(function() {
													   //$('#logout-form').attr('action', 'ws');
													  // $('input[name="qs"]').val('lgt');
													  // $('input[name="rules"]').val('lgtdefault');
													  // $("#logout-form").submit();
												// hide all modal
													//$(".modal").modal('hide');												
											   });	
										   } 
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
												//  $('#logout-form').attr('action', 'ws');
												//  $('input[name="qs"]').val('lgt');
												//  $('input[name="rules"]').val('lgtdefault');
												//  $("#logout-form").submit();
												//  $(".modal").modal('hide');												  
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
		});
		
		
		
		
		$('#btn-add-pricing-plan-allocate').click(function() {
			//Check for the data validation	
			
			$("#addplan-form").validate({
				rules: {
					addcustid: {
						required: true
					},
					seladdplanid: {
						required: true
					},
					planname: {
						required: true
					},
					planstartdate: {
						required: true
					},
					planprice: {
						required: true
					},
					status: {
						required: true
					},
					reason: {
						required: true
					}
				},
				messages: {
					// addcustid seladdplanid planname planstartdate planprice status reason
					addcustid: {
						required: 'Please enter Customer Id',
					},
					seladdplanid: {
						required: 'Please enter Plan Id',
					},
					planname: {
						required: 'Please enter Plan Name',
					},
					planstartdate: {
						required: 'Please enter Start Date',
					},
					planprice: {
						required: 'Please enter Plan Price',
					},
					status: {
						required: 'Please enter Status',
					},
					reason: {
						required: 'Please enter reason for allocation of plan',
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
		
		
			if ($("#addplan-form").valid()) {
				$('#addplan-form').attr('action', 'ms');
				$('input[name="qs"]').val('opspln');
				$('input[name="rules"]').val('opseditcustallocateupdate');     
				$('input[name="hdnadduplanid"]').val($('#seladdplanid option:selected').val()); 
				var formData = new FormData($('#addplan-form')[0]);
				    formData.append("currentplanid",existingPlanId);
				/* for (var pair of formData.entries()) {
					alert(pair[0] + " - " + pair[1]);
				  }	 */
				  // Call Ajax here and submit the form 							
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
									   if(data.error=='false'){
									//       console.log("no error");
											  Swal.fire({
													   icon: 'success',
													   title: $('#ops-manage-pricingplan-success-header').text(),
													   text: data.message,
													   showConfirmButton: true,
													   confirmButtonText: "Ok",
												   }).then(function() {
														   $('#tempform').attr('action', fnGetOpsServletPath());
														   $('input[name="qs"]').val('opspln');
														   $('input[name="rules"]').val('Customer Plan Allocation');
														   $("#tempform").submit();	
												   });	
										   }else{
										   //swal.fire('Error message');
												 Swal.fire({
												 title: $('#ops-manage-pricingplan-error-swal-header').text(),              
												 text: data.message, 
												 icon: "error",
												 footer: '<a href="#">'+$('#ops-manage-pricingplan-error-swal-contact-admin').text()+'</a>',
											   showConfirmButton: true,
											   confirmButtonText: "Ok",
											   }).then(function() {
													   //$('#logout-form').attr('action', 'ws');
													  // $('input[name="qs"]').val('lgt');
													  // $('input[name="rules"]').val('lgtdefault');
													  // $("#logout-form").submit();
												// hide all modal
													$(".modal").modal('hide');												
											   });	
										   } 
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
												//  $('#logout-form').attr('action', 'ws');
												//  $('input[name="qs"]').val('lgt');
												//  $('input[name="rules"]').val('lgtdefault');
												//  $("#logout-form").submit();
													$(".modal").modal('hide');												
												  
									  }); 
							  }
						  });				
				
		
			} else {
				Swal.fire({
					icon: 'error',
					title: $('#ops-manage-pricingplan-error-swal-header').text(),
					text: $('#ops-manage-pricingplan-error-swal-checkdata').text()
					//footer: '<a href>Why do I have this issue?</a>'
				})
				return false;
			}
		});	

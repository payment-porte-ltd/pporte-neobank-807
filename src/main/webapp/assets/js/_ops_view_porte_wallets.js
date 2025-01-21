$(window).on("load", function(e) {
    $("#global-loader").fadeOut("slow");
})

function fnGetRelationshipNo(){
	 var rel_no = $("#selcustomer option:selected").val();
	var relNo=rel_no.split(",");
	var relNoValue=relNo[1];
	$("#searchrelnno").val(relNoValue);
	
}
function setSearchbyRelNo(elem){
            var inputElem = document.getElementById(elem);
            inputElem.setAttribute('type', 'text');
			
        }
function setSearchbyCustName(elem){
            var inputElem = document.getElementById(elem);
            inputElem.setAttribute('type', 'text');
        }
function setSearchbyCustId(elem){
            var inputElem = document.getElementById(elem);
            inputElem.setAttribute('type', 'text');
        }
function setSearchbyMobileNo(elem){
            var inputElem = document.getElementById(elem);
            inputElem.setAttribute('type', 'text');
        }


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
		function fnViewMoreCustomers() {
	
				console.log('inside fnViewMoreCustomers')
				var username =   $('#searchspecificcust-form input[name="searchbycustname"]').val();
				var relno =   $('#searchspecificcust-form input[name="searchbyrelno"]').val();
				var phonenumber =   $('#searchspecificcust-form input[name="searchmobileno"]').val();
				var userid =   $('#searchspecificcust-form input[name="searchbycustid"]').val();
				
				$('#searchspecificcust-form').attr('action', fnGetOpsServletPath());
				$('#searchspecificcust-form input[name="qs"]').val('opswal');
				$('#searchspecificcust-form input[name="rules"]').val('view_more_porte_wal_details');
	
				if((userid==='' || userid ==='undefined') && (relno==='' || relno==='undefined') && 
				(phonenumber==='' || phonenumber=== 'undefined') && (username==='' || username==='undefined') ){
					 Swal.fire({
						  icon: 'error',
						  title: 'Oops',
						  text: 'Enter field field',
						  showConfirmButton: true,
						  confirmButtonText: "Ok",
						  }).then(function() {
							location.reload();	
							
					  });
				}else{
					$("#searchspecificcust-form").submit(); 

				}
		}
function fnOpsViewSpecificCustomerTxn(publicKey, custname, relno){
	$('#form-txn-page').attr('action', fnGetOpsServletPath());
	$('input[name="qs"]').val('opswal');
	$('input[name="rules"]').val('porte_wallets');
	$('input[name="hdncustname"]').val(custname);
	$('input[name="hdnrelno"]').val(relno);
	$('input[name="hdnpublickey"]').val(publicKey);
	$("#form-txn-page").submit();
}
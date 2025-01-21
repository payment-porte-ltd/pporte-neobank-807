	$(window).on("load", function(e) {
	    $("#global-loader").fadeOut("slow");
		var thehours = new Date().getHours();
		var themessage;
		var morning = ('Good morning');
		var afternoon = ('Good afternoon');
		var evening = ('Good evening');
	
		if (thehours >= 0 && thehours < 12) {
			themessage = morning; 
	
		} else if (thehours >= 12 && thehours < 17) {
			themessage = afternoon;
	
		} else if (thehours >= 17 && thehours < 24) {
			themessage = evening;
		}
	
		$('#greeting').append(themessage);
		})
	$( document ).ready(function() {
});
function fnViewMorePendingTxns() {
		//alert(disputeId)
		$('#dispute-form').attr('action', fnGetOpsServletPath());
		$('input[name="qs"]').val('partdash');
		$('input[name="rules"]').val('Pending Transactions');
		$("#dispute-form").submit();
	}
	function fnViewCompleteTxn() {
		//alert(disputeId)
		$('#dispute-form').attr('action', fnGetOpsServletPath());
		$('input[name="qs"]').val('partdash');
		$('input[name="rules"]').val('Completed Transactions');
		$("#dispute-form").submit();
	}
	
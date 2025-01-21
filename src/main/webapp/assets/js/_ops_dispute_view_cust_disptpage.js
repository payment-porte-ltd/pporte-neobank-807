$(window).on("load", function(e) {
    $("#global-loader").fadeOut("slow");
})

function fnShowRequestDetail(disputeId){

    $('#view-dispute-form').attr('action', fnGetOpsServletPath());
   $('input[name="qs"]').val('opsdspt');
   $('input[name="rules"]').val('ops_view_specific_cust_dispute');
   $('input[name="hdnreqid"]').val(disputeId);
   $("#view-dispute-form").submit();
}